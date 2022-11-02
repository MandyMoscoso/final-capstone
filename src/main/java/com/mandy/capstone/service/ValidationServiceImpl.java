package com.mandy.capstone.service;

import com.mandy.capstone.dtos.BorrowerDto;
import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.entities.Borrower;
import com.mandy.capstone.entities.User;
import com.mandy.capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Autowired
    UserRepository userRepository;

//validation for general field that can be applied to all account
    @Override
    @Transactional
    public List<String> newAccountGeneralCheck(UserDto userDto, String role){
        List<String> response = new ArrayList<>();

        //check for existing username (email)
        Optional<User> usernameCheck = userRepository.findByUsername(userDto.getUsername());
        if(usernameCheck.isPresent()){
            response.add("false");
            response.add("Email is already in use");
            response.add("danger");
            return response;
        };

        //check for blank fields
        if("".equals(userDto.getUsername())){
            response.add("false");
            response.add("Email cannot be blank");
            response.add("danger");
            return response;
        }
        if("".equals(userDto.getFirstname())){
            response.add("false");
            response.add("Firstname cannot be blank");
            response.add("danger");
            return response;
        }
        if("".equals(userDto.getLastname())){
            response.add("false");
            response.add("Lastname cannot be blank");
            response.add("danger");
            return response;
        }
        if("".equals(userDto.getPassword())){
            response.add("false");
            response.add("Password cannot be blank");
            response.add("danger");
            return response;
        }
        if("".equals(userDto.getPhonenumber())){
            response.add("false");
            response.add("Phone number cannot be blank");
            response.add("danger");
            return response;
        }

//if all checks passed then return true - all fields are valid
        response.add("true");
        return response;
    };


    //validation for fields that needed to calculate rate
    @Override
    @Transactional
    public List<String> rateFieldCheck(Borrower borrower) {
        List<String> response = new ArrayList<>();
        double loanAmount = borrower.getLoanAmount();
        double propertyValue = borrower.getPropertyValue();
        double ltv = loanAmount/propertyValue;
        String loanPurpose = borrower.getLoanPurpose();
        String occupancy = borrower.getOccupancyType();
        String propertyType = borrower.getPropertyType();


        if(loanAmount==0){
            response.add("false");
            response.add("Loan amount cannot be 0");
            response.add("danger");
            return response;
        };
        if(borrower.getCreditScore()<620){
            response.add("false");
            response.add("Conventional loan only available to credit score of 620 and higher");
            response.add("danger");
            return response;
        };
        if(propertyValue==0){
            response.add("false");
            response.add("Property must have value in order to qualify for a loan");
            response.add("danger");
            return response;
        };

        //to check if loan amount is higher than property value
        if(loanAmount>= propertyValue){
            response.add("false");
            response.add("Loan amount cannot be equal or higher than property value");
            response.add("danger");
            return response;
        }

        //cash out refinance is not available for LTV over 80%
        if("Refinance-Cashout".equalsIgnoreCase(loanPurpose) && ltv > 0.8){
            response.add("false");
            response.add("Refinance cash out is not available for LTV over 80%");
            response.add("danger");
            return response;
        }

        //investment is not available for LTV over 80%
        if("Investment".equalsIgnoreCase(occupancy) && ltv > 0.8){
            response.add("false");
            response.add("Occupancy of investment is not available for LTV over 80%");
            response.add("danger");
            return response;
        }
        //second home is not available for LTV over 80%
        if("Second Home".equalsIgnoreCase(occupancy) && ltv > 0.9){
            response.add("false");
            response.add("Occupancy of second home is not available for LTV over 90%");
            response.add("danger");
            return response;
        }

        //3-4 units is not available for LTV over 80%
        if("Triplex".equalsIgnoreCase(propertyType) || "Fourplex".equalsIgnoreCase(propertyType)){
            response.add("false");
            response.add("3-4 units is not available for LTV over 80%");
            response.add("danger");
            return response;
        }

//those field below are selections on my frontend. If the user using my frontend to send request, those should be all valid. The validations below are only to prevent against requests coming from other method like Postman, powershell, curl...
        if(!"Conventional".equalsIgnoreCase(borrower.getLoanType())){
            response.add("false");
            response.add("We only offer conventional loans at the moment");
            response.add("danger");
            return response;
        };

        List<String> propertyTypeList = Arrays.asList("Single House", "Townhouse", "Condo", "Duplex", "Triplex","Fourplex");
        if(!propertyTypeList.contains(borrower.getPropertyType())){
            response.add("false");
            response.add("Property type is not allowed");
            response.add("danger");
            return response;
        };

        List<String> occupancyTypeList = Arrays.asList("owner-occupied", "Second Home", "Investment");
        if(!occupancyTypeList.contains(occupancy)){
            response.add("false");
            response.add("Occupancy type is not allowed");
            response.add("danger");
            return response;
        };

        List<String> loanPurposeList = Arrays.asList("Purchase", "Refinance", "Refinance-Cashout");
        if(!loanPurposeList.contains(loanPurpose)){
            response.add("false");
            response.add("Loan purpose is not allowed");
            response.add("danger");
            return response;
        };

        List<String> loanTermList = Arrays.asList("30", "20", "15");
        if(!loanTermList.contains(borrower.getLoanTerm())){
            response.add("false");
            response.add("Loan term is not allowed");
            response.add("danger");
            return response;
        };



//if all checks passed then return true - all fields are valid
        response.add("true");
        return response;
    };
}
