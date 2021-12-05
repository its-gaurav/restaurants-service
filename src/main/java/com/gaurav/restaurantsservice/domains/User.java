package com.gaurav.restaurantsservice.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    @NotNull
    private String name;

    private String emailId;
    @NotNull
    private String contactNumber;

    private boolean isPrime = false;

    public User(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public User(Long id, String name, String emailId, String contactNumber) {
        this.id = id;
        this.name = name;
        this.emailId = emailId;
        this.contactNumber = contactNumber;
    }
}
