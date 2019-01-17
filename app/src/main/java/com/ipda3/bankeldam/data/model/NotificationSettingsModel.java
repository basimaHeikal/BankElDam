package com.ipda3.bankeldam.data.model;

import com.ipda3.bankeldam.data.model.cities.Datum;

import java.util.ArrayList;

public class NotificationSettingsModel {



    private String headerTitle;
    private ArrayList<Datum> allItemsInSection;


    public NotificationSettingsModel() {

    }
    public NotificationSettingsModel(String headerTitle, ArrayList<Datum> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Datum> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Datum> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}