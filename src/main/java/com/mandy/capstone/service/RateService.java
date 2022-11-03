package com.mandy.capstone.service;

import com.mandy.capstone.entities.Borrower;
import com.mandy.capstone.entities.CustomSecurityUser;
import com.mandy.capstone.entities.ratesheets.BaseRate;

import javax.transaction.Transactional;
import java.util.List;

public interface RateService {
    @Transactional
    public List<?> rates(Borrower obj, CustomSecurityUser user);
}
