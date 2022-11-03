package com.mandy.capstone.service;

import com.mandy.capstone.dtos.UserDto;
import com.mandy.capstone.entities.Borrower;

import javax.transaction.Transactional;
import java.util.List;

public interface ValidationService {
    //validation for general field that can be applied to all account
    @Transactional
    List<String> newAccountGeneralCheck(UserDto userDto);
    //validation for fields that needed to calculate rate
    @Transactional
    List<String> rateFieldCheck(Borrower borrower);
    List<String> updateAccountGeneralCheck(UserDto updateUser);
}
