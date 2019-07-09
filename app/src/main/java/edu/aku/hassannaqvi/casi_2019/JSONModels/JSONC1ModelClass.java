package edu.aku.hassannaqvi.casi_2019.JSONModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ali.azaz on 3/31/2018.
 */

public class JSONC1ModelClass {

    private String respName = "";
    @SerializedName("resp_lno")
    private String respSerial = "";
    private String cic101 = "";
    private String cic201d = "";
    private String cic201m = "";
    private String cic201y = "";
    private String cic202 = "";
    private String cic203 = "";
    private String cic204a = "";
    private String cic204b = "";
    private String cic205 = "";
    private String cluster_no = "";
    private String hhno = "";
    private String wra_lno = "";
    @SerializedName("cic11801")
    private String cih11801 = "";

    public JSONC1ModelClass() {

    }

    public String getcih11801() {
        return cih11801;
    }

    public void setcih11801(String cih11801) {
        this.cih11801 = cih11801;
    }

    public String getWra_lno() {
        return wra_lno;
    }

    public void setWra_lno(String wra_lno) {
        this.wra_lno = wra_lno;
    }

    public String getCluster_no() {
        return cluster_no;
    }

    public void setCluster_no(String cluster_no) {
        this.cluster_no = cluster_no;
    }

    public String getHhno() {
        return hhno;
    }

    public void setHhno(String hhno) {
        this.hhno = hhno;
    }

    public String getRespName() {
        return respName;
    }

    public void setRespName(String respName) {
        this.respName = respName;
    }

    public String getRespSerial() {
        return respSerial;
    }

    public void setRespSerial(String respSerial) {
        this.respSerial = respSerial;
    }

    public String getcic101() {
        return cic101;
    }

    public void setcic101(String cic101) {
        this.cic101 = cic101;
    }

    public String getcic201d() {
        return cic201d;
    }

    public void setcic201d(String cic201d) {
        this.cic201d = cic201d;
    }

    public String getcic201m() {
        return cic201m;
    }

    public void setcic201m(String cic201m) {
        this.cic201m = cic201m;
    }

    public String getcic201y() {
        return cic201y;
    }

    public void setcic201y(String cic201y) {
        this.cic201y = cic201y;
    }

    public String getcic202() {
        return cic202;
    }

    public void setcic202(String cic202) {
        this.cic202 = cic202;
    }

    public String getcic203() {
        return cic203;
    }

    public void setcic203(String cic203) {
        this.cic203 = cic203;
    }

    public String getcic204a() {
        return cic204a;
    }

    public void setcic204a(String cic204a) {
        this.cic204a = cic204a;
    }

    public String getcic204b() {
        return cic204b;
    }

    public void setcic204b(String cic204b) {
        this.cic204b = cic204b;
    }

    public String getcic205() {
        return cic205;
    }

    public void setcic205(String cic205) {
        this.cic205 = cic205;
    }
}
