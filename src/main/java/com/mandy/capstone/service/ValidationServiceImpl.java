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
        List<String> propertyType = Arrays.asList("Single House", "Townhouse", "Condo", "Duplex", "Triplex","Fourplex");
        List<String> occupancyType = Arrays.asList("owner-occupied", "Second Home", "Investment");
        List<String> loanPurpose = Arrays.asList("Purchase", "Refinance", "Refinance-Cashout");
        List<String> loanTerm = Arrays.asList("30", "20", "15");

        List<String> response = new ArrayList<>();

        if(borrower.getLoanAmount()==0){
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
        if(borrower.getPropertyValue()==0){
            response.add("false");
            response.add("Property must have value in order to qualify for a loan");
            response.add("danger");
            return response;
        };

//those field below are selections on my frontend. If the user using my frontend to send request, those should be all valid. The validations below are only to prevent against requests coming from other method like Postman, powershell, curl...
        if(!"Conventional".equalsIgnoreCase(borrower.getLoanType())){
            response.add("false");
            response.add("We only offer conventional loans at the moment");
            response.add("danger");
            return response;
        };

        if(!propertyType.contains(borrower.getPropertyType())){
            response.add("false");
            response.add("Property type is not allowed");
            response.add("danger");
            return response;
        };

        if(!occupancyType.contains(borrower.getOccupancyType())){
            response.add("false");
            response.add("Occupancy type is not allowed");
            response.add("danger");
            return response;
        };
        if(!loanPurpose.contains(borrower.getLoanPurpose())){
            response.add("false");
            response.add("Loan purpose is not allowed");
            response.add("danger");
            return response;
        };
        if(!loanTerm.contains(borrower.getLoanTerm())){
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
