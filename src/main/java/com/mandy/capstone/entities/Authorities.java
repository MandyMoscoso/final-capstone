package com.mandy.capstone.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mandy.capstone.dtos.AuthoritiesDto;
import com.mandy.capstone.dtos.BorrowerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table (name = "authorities")
@Data
@AllArgsConstructor
@NoArgsConstructor
//GrantedAuthority at some point need serial so we need a serial
public class Authorities implements GrantedAuthority {
//    private static final long serialVersionUID = -8123526131047887755L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authority_id;
    private String authority;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    ////we have User user because this is a many to one relationship. we may have many authorities for one user.
    // in users table, it will be the opposite. we  haveSet<Authorities>, since the other side of the relationship is one to many - one user can have many authorities.
    private User user;

    public Authorities(String role) {
        this.authority = role;
    }

    public Authorities(AuthoritiesDto authoritiesDto) {
        if(authoritiesDto.getAuthority_id()!=null){
            this.authority_id= authoritiesDto.getAuthority_id();
        }
        if(authoritiesDto.getAuthority()!=null){
            this.authority= authoritiesDto.getAuthority();
        }

    }

}
