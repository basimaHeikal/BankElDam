
package com.ipda3.bankeldam.data.model.resetPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("pin_code_for_test")
    @Expose
    private Integer pinCodeForTest;

    public Integer getPinCodeForTest() {
        return pinCodeForTest;
    }

    public void setPinCodeForTest(Integer pinCodeForTest) {
        this.pinCodeForTest = pinCodeForTest;
    }

}
