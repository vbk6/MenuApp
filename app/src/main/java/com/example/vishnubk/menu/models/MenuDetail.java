package com.example.vishnubk.menu.models;

/**
 * Created by vishnubk on 2/2/16.
 */
public class MenuDetail {


    String itemName;
    String itemPrice;
    String itemCategory;
    Integer id;



    public MenuDetail(String category,String items, String price,Integer id) {
        this.itemCategory=category;
        this.itemName=items;
        this.itemPrice=price;
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }


}
