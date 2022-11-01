package com.mandy.capstone.service;

import com.mandy.capstone.entities.Borrower;
import com.mandy.capstone.entities.ratesheets.*;
import com.mandy.capstone.repositories.ratesheetsrepo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RateServiceImpl  implements RateService {
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

    @Override
    @Transactional
    public  List<?> rates(Borrower obj){
        double ltv = obj.getLoanAmount()/obj.getPropertyValue() * 100;
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
        } else if (ltv<90) {
            ltvRange = "ltv90";
        } else if (ltv<=95) {
            ltvRange = "ltv95";
        }
        int credit = obj.getCreditScore();

        List<FicoAdj> ficoRateSheet = ficoAdjRepositories.findAllByOrderByFicoDesc();
        double ficoRate = 0;
        for (int i = 0; i < ficoRateSheet.size(); i++) {
            if(credit>=ficoRateSheet.get(i).getFico()){
                ficoRate = ficoRateSheet.get(i).get(ltvRange);
                break;
            }
            ficoRate=999;
        }
        double cashoutRate = 0;
        if(obj.getLoanPurpose().equalsIgnoreCase("Refinance-Cashout")){
            List<CashOutAdj> cashoutRateSheet = cashOutAdjRepositories.findAllByOrderByFicoDesc();
            for (int i = 0; i < cashoutRateSheet.size(); i++) {
                if(credit>=cashoutRateSheet.get(i).getFico()){
                    cashoutRate = cashoutRateSheet.get(i).get(ltvRange);
                    break;
                }
                cashoutRate=999;
            }
        }
        double propertyTypeRate = 0;
        if(obj.getPropertyType().equalsIgnoreCase("Duplex") ||obj.getPropertyType().equalsIgnoreCase("Triplex") ||obj.getPropertyType().equalsIgnoreCase("Fourplex")){
            List<PropertyTypeAdj> propertyTypeRateSheet = propertyTypeAdjRepositories.findAllByOrderByPropertyTypeAsc();
            if(obj.getPropertyType().equalsIgnoreCase("Duplex")){
                propertyTypeRate= propertyTypeRateSheet.get(0).get(ltvRange);
            }else{
                propertyTypeRate= propertyTypeRateSheet.get(1).get(ltvRange);
            }
        }

//occupancy is not owner occupied, they are 2nd home or investment, and it will have an adjustment rate
        double occupancyRate = 0;
        if(!obj.getOccupancyType().equalsIgnoreCase("owner-occupied")){
            List<OccupancyAdj> occupancyRateSheet = occupancyAdjRepositories.findAllByOrderByOccupancyAsc();

            if(obj.getOccupancyType().equalsIgnoreCase("Second Home")){
                occupancyRate=occupancyRateSheet.get(1).get(ltvRange);
            }else{
                occupancyRate=occupancyRateSheet.get(0).get(ltvRange);
            }
        }

 System.out.println("fico rate is " + ficoRate +"\ncashoutrate is " + cashoutRate + "\n propertytype rate is " +propertyTypeRate+"\noccupancyrate is " + occupancyRate);
        double adjRate = ficoRate + cashoutRate + propertyTypeRate + occupancyRate;



        //Have to use wildcards because unsure of return class
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
        System.out.println(copyRateSheet);
        return (List<?>) copyRateSheet;
    }

}
