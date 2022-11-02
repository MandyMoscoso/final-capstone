package com.mandy.capstone.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mandy.capstone.dtos.BorrowerDto;
import com.mandy.capstone.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "borrowers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Borrower {

//I kept all the column name as variable names so I skipped @Column for now. Will need @Column if wanna do more customization

//By default Spring uses org.springframework.boot.orm.jpa.SpringNamingStrategy to generate table names. This is a  extension of org.hibernate.cfg.ImprovedNamingStrategy.The ImprovedNamingStrategy will convert CamelCase to SNAKE_CASE. This explained why my table column's name is loan_amount, credit_score...

// could always just specify column name/ variable in lowercase to avoid this name change. or use spring.jpa.hibernate.naming_strategy=org.hibernate.cfg.EJB3NamingStrategy because EJB3NamingStrategy just uses the table name unchanged
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    ////we have User user because this is a many to one relationship. we may have many authorities for one user.
    // in users table, it will be the opposite. we  haveSet<Authorities>, since the other side of the relationship is one to many - one user can have many authorities.
    private User user;

    public Borrower(BorrowerDto borrowerDto) {
        if(borrowerDto.getBorrower_id()!=null){
            this.borrower_id= borrowerDto.getBorrower_id();
        }
        if(borrowerDto.getAddress()!=null){
            this.address= borrowerDto.getAddress();
        }

        this.loanAmount= borrowerDto.getLoanAmount();
        this.creditScore= borrowerDto.getCreditScore();
        this.propertyValue= borrowerDto.getPropertyValue();
        if(borrowerDto.getLoanType()!=null){
            this.loanType= borrowerDto.getLoanType();
        }
        if(borrowerDto.getPropertyType()!=null){
            this.propertyType= borrowerDto.getPropertyType();
        }
        if(borrowerDto.getOccupancyType()!=null){
            this.occupancyType= borrowerDto.getOccupancyType();
        }
        if(borrowerDto.getLoanPurpose()!=null){
            this.loanPurpose= borrowerDto.getLoanPurpose();
        }
        if(borrowerDto.getLoanTerm()!=null){
            this.loanTerm= borrowerDto.getLoanTerm();
        }


    }
    public  Borrower(UserDto userDto){
        if(userDto.getBorrowerDto()!=null){
            if(userDto.getBorrowerDto().getBorrower_id()!=null){
                this.borrower_id=userDto.getBorrowerDto().getBorrower_id();
            }
            if(userDto.getBorrowerDto().getAddress()!=null){
                this.address= userDto.getBorrowerDto().getAddress();
            }

            this.loanAmount= userDto.getBorrowerDto().getLoanAmount();
            this.creditScore= userDto.getBorrowerDto().getCreditScore();
            this.propertyValue= userDto.getBorrowerDto().getPropertyValue();

            if(userDto.getBorrowerDto().getLoanType()!=null){
                this.loanType= userDto.getBorrowerDto().getLoanType();
            }
            if(userDto.getBorrowerDto().getPropertyType()!=null){
                this.propertyType= userDto.getBorrowerDto().getPropertyType();
            }
            if(userDto.getBorrowerDto().getOccupancyType()!=null){
                this.occupancyType= userDto.getBorrowerDto().getOccupancyType();
            }
            if(userDto.getBorrowerDto().getLoanPurpose()!=null){
                this.loanPurpose= userDto.getBorrowerDto().getLoanPurpose();
            }
            if(userDto.getBorrowerDto().getLoanTerm()!=null){
                this.loanTerm= userDto.getBorrowerDto().getLoanTerm();
            }
        }


    }
}
