package edu.aku.hassannaqvi.casi_2019.JSONModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gul.sanober on 3/7/2018.
 */

public class JSONModelClass {
    @SerializedName("cih202")
    private String name = "";
    private String age = "";
    @SerializedName("cih2SerialNo")
    private String serialNo = "";
    @SerializedName("cih207")
    private String maritalStatus = "";
    @SerializedName("cih204")
    private String gender = "";
    @SerializedName("cih212")
    private String mothername = "";
    @SerializedName("cih2doby")
    private String year = "";
    @SerializedName("cih2dobm")
    private String month = "";
    @SerializedName("cih2dobd")
    private String day = "";
    private String cih210;

    public JSONModelClass() {

    }

    public String getcih210() {
        return cih210;
    }

    public void setcih210(String cih210) {
        this.cih210 = cih210;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
