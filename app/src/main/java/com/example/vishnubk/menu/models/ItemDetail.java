package com.example.vishnubk.menu.models;

import android.util.Log;

/**
 * Created by vishnubk on 28/1/16.
 */
public class ItemDetail {

    private String itemName;
    private String itemPrice;
    private String count="";


    public ItemDetail(String indItems, String indPrice,String count) {

        this.itemName=indItems;
        this.itemPrice=indPrice;
        this.count=count;

    }


    public String getCount() {


        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

}
