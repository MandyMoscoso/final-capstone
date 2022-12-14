package com.mandy.capstone.service;
import com.mandy.capstone.entities.Borrower;
import com.mandy.capstone.entities.CustomSecurityUser;
import com.mandy.capstone.entities.User;
import com.mandy.capstone.entities.ratesheets.*;
import com.mandy.capstone.repositories.UserRepository;
import com.mandy.capstone.repositories.ratesheetsrepo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class RateServiceImpl  implements RateService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private CashOutAdjRepositories cashOutAdjRepositories;
    @Autowired
    private Cf15Repositories cf15Repositories;
    @Autowired
    private Cf20Repositories cf20Repositories;
    @Autowired
    private Cf30Repositories cf30Repositories;
    @Autowired
    private FicoAdjRepositories ficoAdjRepositories;
    @Autowired
    private OccupancyAdjRepositories occupancyAdjRepositories;
    @Autowired
    private PropertyTypeAdjRepositories propertyTypeAdjRepositories;
    @Autowired
    private ValidationService validationService;

    @Override
    @Transactional
    public  List<?> rates(Borrower obj, CustomSecurityUser user){
        List<String> validation = validationService.rateFieldCheck(obj);
        if(validation.get(0).equalsIgnoreCase("false")){
            return validation;
        }
        //update the borrower info to database
        double loanAmount = obj.getLoanAmount();
        double propertyValue = obj.getPropertyValue();
        String propertyType = obj.getPropertyType();
        String loanType = obj.getLoanType();
        String occupancy = obj.getOccupancyType();
        int credit = obj.getCreditScore();
        String loanTerm = obj.getLoanTerm();
        String loanPurpose = obj.getLoanPurpose();
// only those fields below needed to be updated. the rest should be the same.
        User savedUser = userRepository.findUserById(user.getId());
        Borrower borrower = savedUser.getBorrower();
        borrower.setLoanAmount(loanAmount);
        borrower.setPropertyValue(propertyValue);
        borrower.setLoanType(loanType);
        borrower.setPropertyType(propertyType);
        borrower.setOccupancyType(occupancy);
        borrower.setCreditScore(credit);
        borrower.setLoanTerm(loanTerm);
        borrower.setLoanPurpose(loanPurpose);
        //if all fields are valid, then do the math
        double ltv = loanAmount/propertyValue * 100;
        String ltvRange = "";
        //return the column name based on ltv. I created a special getter that take a string and return the column based on that string value. Have to do it this way because Lombok generated getter has the column name in the method name instead of a variable.
        if(ltv <=60){
            ltvRange = "ltv60";
        } else if (ltv <=70) {
            ltvRange = "ltv70";
        } else if (ltv<=75) {
            ltvRange = "ltv75";
        } else if (ltv<=80) {
            ltvRange = "ltv80";
        } else if (ltv<=85){
            ltvRange = "ltv85";
        } else if (ltv<=90) {
            ltvRange = "ltv90";
        } else if (ltv<=95) {
            ltvRange = "ltv95";
        }


        double ficoRate = 0;
//fico only matter for loan term over 15 years
        if(!"15".equals(loanTerm)){
            List<FicoAdj> ficoRateSheet = ficoAdjRepositories.findAllByOrderByFicoDesc();
            for (int i = 0; i < ficoRateSheet.size(); i++) {
                if(credit>=ficoRateSheet.get(i).getFico()){
                    ficoRate = ficoRateSheet.get(i).get(ltvRange);
                    break;
                }
//only happens when fico is below 620, which should be prevented by validationService.rateFieldCheck(obj) - will show alert that not qualified for conventional loans
                ficoRate=999;
            }
        }
        double cashoutRate = 0;
        if(loanPurpose.equalsIgnoreCase("Refinance-Cashout")){
            List<CashOutAdj> cashoutRateSheet = cashOutAdjRepositories.findAllByOrderByFicoDesc();
            for (int i = 0; i < cashoutRateSheet.size(); i++) {
                if(credit>=cashoutRateSheet.get(i).getFico()){
                    cashoutRate = cashoutRateSheet.get(i).get(ltvRange);
                    break;
                }
//only happens when cash out with ltv over 80%. this should be prevented by validationService.rateFieldCheck(obj)
                cashoutRate=999;
            }
        }
        double propertyTypeRate = 0;
        if(propertyType.equalsIgnoreCase("Duplex") ||propertyType.equalsIgnoreCase("Triplex") ||propertyType.equalsIgnoreCase("Fourplex")){
            List<PropertyTypeAdj> propertyTypeRateSheet = propertyTypeAdjRepositories.findAllByOrderByPropertyTypeAsc();
            if(obj.getPropertyType().equalsIgnoreCase("Duplex")){
                propertyTypeRate= propertyTypeRateSheet.get(0).get(ltvRange);
            }else{
                propertyTypeRate= propertyTypeRateSheet.get(1).get(ltvRange);
            }
        }

//occupancy is not owner occupied, they are 2nd home or investment, and it will have an adjustment rate
        double occupancyRate = 0;
//owner occupied doesn't have adjustment
        if(!obj.getOccupancyType().equalsIgnoreCase("owner-occupied")){
            List<OccupancyAdj> occupancyRateSheet = occupancyAdjRepositories.findAllByOrderByOccupancyAsc();

            if(occupancy.equalsIgnoreCase("Second Home")){
                occupancyRate=occupancyRateSheet.get(1).get(ltvRange);
            }else{
                occupancyRate=occupancyRateSheet.get(0).get(ltvRange);
            }
        }

// System.out.println("fico rate is " + ficoRate +"\ncashoutrate is " + cashoutRate + "\n propertytype rate is " +propertyTypeRate+"\noccupancyrate is " + occupancyRate);
        double adjRate = ficoRate + cashoutRate + propertyTypeRate + occupancyRate;

        //Have to use wildcards because unknown of return class (list of cf30 is not a child of list of baserate - cannot use generics)
        List<? extends BaseRate> baseRateSheet = new ArrayList<>();
        switch (obj.getLoanTerm()){
            case "30": baseRateSheet = cf30Repositories.findAll();  break;
            case "20": baseRateSheet = cf20Repositories.findAll();  break;
            default:  baseRateSheet= cf15Repositories.findAll();
        }

// Have to make a deep copy of baseRateSheet otherwise Hibernate will update the database when I change the rate (adding adjRate) to the object. This can also be done by using seesion.evict() to tell Hibernate not to do any db update on that. I want to change rates on baseRateSheet but not the db.
        List<BaseRate> copyRateSheet=new ArrayList<>();
        for (int i = 0; i < baseRateSheet.size(); i++) {
             BaseRate newObj = new BaseRate();
            newObj.setRate(baseRateSheet.get(i).getRate());
            newObj.setDay15(Math.round((baseRateSheet.get(i).getDay15()+adjRate)*100)/100D );
            newObj.setDay30( Math.round((baseRateSheet.get(i).getDay30()+adjRate)*100)/100D);
            newObj.setDay45(Math.round((baseRateSheet.get(i).getDay45()+adjRate)*100)/100D);
            copyRateSheet.add(newObj);
        }
        return copyRateSheet;
    }

}
