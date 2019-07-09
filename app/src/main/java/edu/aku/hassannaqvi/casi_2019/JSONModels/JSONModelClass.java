package edu.aku.hassannaqvi.casi_2019.JSONModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gul.sanober on 3/7/2018.
 */

public class JSONModelClass {
    @SerializedName("nh202")
    private String name = "";
    private String age = "";
    @SerializedName("nh2SerialNo")
    private String serialNo = "";
    @SerializedName("nh207")
    private String maritalStatus = "";
    @SerializedName("nh204")
    private String gender = "";
    private String mothername = "";
    private String nh210;

    public JSONModelClass() {

    }

    public String getNh210() {
        return nh210;
    }

    public void setNh210(String nh210) {
        this.nh210 = nh210;
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
}
