package com.mandy.capstone.dtos;

import com.mandy.capstone.entities.Borrower;
import com.mandy.capstone.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class BorrowerDto {
    private Long borrower_id;
    private String address;
    private double loanAmount;
    private int creditScore;
    private double propertyValue;
    private String loanType;
    private String propertyType;
    private String occupancyType;
    private String loanPurpose;
    private String loanTerm;
    private UserDto userDto;

    public BorrowerDto(Borrower borrower) {
        if(borrower.getBorrower_id()!=null){
            this.borrower_id=borrower.getBorrower_id();
        }
        if(borrower.getAddress()!=null){
            this.address= borrower.getAddress();
        }

        this.loanAmount= borrower.getLoanAmount();
        this.creditScore= borrower.getCreditScore();
        this.propertyValue= borrower.getPropertyValue();

        if(borrower.getLoanType()!=null){
            this.loanType= borrower.getLoanType();
        }
        if(borrower.getPropertyType()!=null){
            this.propertyType= borrower.getPropertyType();
        }
        if( borrower.getOccupancyType()!=null){
            this.occupancyType= borrower.getOccupancyType();
        }
        if( borrower.getLoanPurpose()!=null){
            this.loanPurpose= borrower.getLoanPurpose();
        }
        if( borrower.getLoanTerm()!=null){
            this.loanTerm= borrower.getLoanTerm();
        }
    }

    public  BorrowerDto(User user){
        if(user.getBorrower()!=null){

            if(user.getBorrower().getBorrower_id()!=null){
                this.borrower_id=user.getBorrower().getBorrower_id();
            }
            if(user.getBorrower().getAddress()!=null){
                this.address= user.getBorrower().getAddress();
            }
            this.loanAmount= user.getBorrower().getLoanAmount();
            this.creditScore= user.getBorrower().getCreditScore();
            this.propertyValue= user.getBorrower().getPropertyValue();
            if(user.getBorrower().getLoanType()!=null){
                this.loanType= user.getBorrower().getLoanType();
            }
            if(user.getBorrower().getPropertyType()!=null){
                this.propertyType= user.getBorrower().getPropertyType();
            }
            if(user.getBorrower().getOccupancyType()!=null){
                this.occupancyType= user.getBorrower().getOccupancyType();
            }
            if(user.getBorrower().getLoanPurpose()!=null){
                this.loanPurpose= user.getBorrower().getLoanPurpose();
            }
            if(user.getBorrower().getLoanTerm()!=null){
                this.loanTerm= user.getBorrower().getLoanTerm();
            }
        }

    }

}
