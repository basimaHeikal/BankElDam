
package com.ipda3.bankeldam.data.model.notificationsSettings;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("cities")
    @Expose
    private List<String> cities = null;
    @SerializedName("bloodTypes")
    @Expose
    private List<String> bloodTypes = null;

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<String> getBloodTypes() {
        return bloodTypes;
    }

    public void setBloodTypes(List<String> bloodTypes) {
        this.bloodTypes = bloodTypes;
    }

}
