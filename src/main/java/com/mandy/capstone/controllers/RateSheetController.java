package com.mandy.capstone.controllers;

import com.mandy.capstone.entities.ratesheets.*;
import com.mandy.capstone.repositories.ratesheetsrepo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rate")
public class RateSheetController {
    @Autowired
    private CashOutAdjRepositories cashOutAdjRepositories;
    @GetMapping("/cashout")
    public List<CashOutAdj> showCashOutRate(){
        return cashOutAdjRepositories.findAll();
    }
    @Autowired
    private Cf15Repositories cf15Repositories;
    @GetMapping("/cf15")
    public List<Cf15> showCf15Rate(){
        return cf15Repositories.findAll();
    }
    @Autowired
    private Cf20Repositories cf20Repositories;
    @GetMapping("/cf20")
    public List<Cf20> showCf20Rate(){
        return cf20Repositories.findAll();
    }
    @Autowired
    private Cf30hbRepositories cf30hbRepositories;
    @GetMapping("/cf30hb")
    public List<Cf30hb> showCf30hbRate(){
        return cf30hbRepositories.findAll();
    }
    @Autowired
    private Cf30Repositories cf30Repositories;
    @GetMapping("/cf30")
    public List<Cf30> showCf30Rate(){
        return cf30Repositories.findAll();
    }
    @Autowired
    private FicoAdjRepositories ficoAdjRepositories;
    @GetMapping("/fico")
    public List<FicoAdj> showFicoAdjRate(){ return ficoAdjRepositories.findAll();}
    @Autowired
    private OccupancyAdjRepositories occupancyAdjRepositories;
    @GetMapping("/occupancy")
    public List<OccupancyAdj> showOccupancyAdjRate(){ return occupancyAdjRepositories.findAll();}
    @Autowired
    private PropertyTypeAdjRepositories propertyTypeAdjRepositories;
    @GetMapping("/propertytype")
    public List<PropertyTypeAdj> showPropertyTypeAdjRate(){ return propertyTypeAdjRepositories.findAll();}


}
