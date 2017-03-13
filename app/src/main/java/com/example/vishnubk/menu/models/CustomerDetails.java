package com.example.vishnubk.menu.models;

/**
 * Created by vishnubk on 4/3/16.
 */
public class CustomerDetails {

    private String customerName;
    private String id;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public CustomerDetails(String customerName, String id) {

        this.customerName=customerName;
        this.id=id;

    }



}
