package com.mandy.capstone.controllers;

import com.mandy.capstone.entities.Borrower;
import com.mandy.capstone.entities.ratesheets.BaseRate;
import com.mandy.capstone.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rate")
public class RateSheetController {
  @Autowired
  RateService rateService;
    @PostMapping("/getrate")
        public List<?> rates (@RequestBody Borrower obj){
        return rateService.rates(obj);
    }
}
