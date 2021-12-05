package com.gaurav.restaurantsservice.entities;

import javax.persistence.Entity;

//@Entity
public class CouponsEntity {

    private Long id;
    private String couponCode;
    private String couponDescription;
    private Long restaurantId;

}
