package edu.aku.hassannaqvi.casi_2018.JSONModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ali.azaz on 3/31/2018.
 */

public class JSONC1ModelClass {

    private String respName = "";
    @SerializedName("resp_lno")
    private String respSerial = "";
    private String nc101 = "";
    private String nc201d = "";
    private String nc201m = "";
    private String nc201y = "";
    private String nc202 = "";
    private String nc203 = "";
    private String nc204a = "";
    private String nc204b = "";
    private String nc205 = "";
    private String cluster_no = "";
    private String hhno = "";
    private String wra_lno = "";
    @SerializedName("nc11801")
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

    public String getnc101() {
        return nc101;
    }

    public void setnc101(String nc101) {
        this.nc101 = nc101;
    }

    public String getnc201d() {
        return nc201d;
    }

    public void setnc201d(String nc201d) {
        this.nc201d = nc201d;
    }

    public String getnc201m() {
        return nc201m;
    }

    public void setnc201m(String nc201m) {
        this.nc201m = nc201m;
    }

    public String getnc201y() {
        return nc201y;
    }

    public void setnc201y(String nc201y) {
        this.nc201y = nc201y;
    }

    public String getnc202() {
        return nc202;
    }

    public void setnc202(String nc202) {
        this.nc202 = nc202;
    }

    public String getnc203() {
        return nc203;
    }

    public void setnc203(String nc203) {
        this.nc203 = nc203;
    }

    public String getnc204a() {
        return nc204a;
    }

    public void setnc204a(String nc204a) {
        this.nc204a = nc204a;
    }

    public String getnc204b() {
        return nc204b;
    }

    public void setnc204b(String nc204b) {
        this.nc204b = nc204b;
    }

    public String getnc205() {
        return nc205;
    }

    public void setnc205(String nc205) {
        this.nc205 = nc205;
    }
}
