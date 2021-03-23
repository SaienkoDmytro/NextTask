package com.example.nexttask.Network;

import com.google.gson.annotations.SerializedName;

public class Today {

    @SerializedName("DateTime")
    private String dateTime;
    @SerializedName("Temperature")
    private String temp;
    @SerializedName("IconPhrase")
    private String icon;
    @SerializedName("IsDaylight")
    private String isDay;


    public String getDateTime() {
        return dateTime;
    }

    public String getTemp() {
        return temp;
    }

    public String getIcon() {
        return icon;
    }

    public String getLight() {
        return isDay;
    }

}
