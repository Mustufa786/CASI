package edu.aku.hassannaqvi.casi_2018.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gul.sanober on 4/14/2018.
 */

public class MicroContract {


    private final String projectName = "National Nutrition Survey 2018";
    private String _ID = "";
    private String _UID = "";
    private String _UUID = "";
    private String formDate = ""; // Date
    private String user = ""; // Interviewer
    private String WUID = "";

    private String sM = ""; // sB

    private String deviceID = "";
    private String devicetagID = "";
    private String synced = "";
    private String synced_date = "";
    private String appversion;

    private String clusterno = "";
    private String hhno = "";

    public MicroContract() {

    }


    public MicroContract Sync(JSONObject jsonObject) throws JSONException {
        this._ID = jsonObject.getString(MicroTable.COLUMN__ID);
        this._UID = jsonObject.getString(MicroTable.COLUMN__UID);
        this._UUID = jsonObject.getString(MicroTable.COLUMN__UUID);
        this.clusterno = jsonObject.getString(MicroTable.COLUMN_CLUSTER);
        this.hhno = jsonObject.getString(MicroTable.COLUMN_HH);
        this.WUID = jsonObject.getString(MicroTable.COLUMN_WUID);
        this.formDate = jsonObject.getString(MicroTable.COLUMN_FORMDATE);
        this.user = jsonObject.getString(MicroTable.COLUMN_USER);
        this.sM = jsonObject.getString(MicroTable.COLUMN_SM);
        this.deviceID = jsonObject.getString(MicroTable.COLUMN_DEVICEID);
        this.devicetagID = jsonObject.getString(MicroTable.COLUMN_DEVICETAGID);
        this.synced = jsonObject.getString(MicroTable.COLUMN_SYNCED);
        this.synced_date = jsonObject.getString(MicroTable.COLUMN_SYNCED_DATE);
        this.appversion = jsonObject.getString(MicroTable.COLUMN_APPVERSION);


        return this;

    }

    public MicroContract Hydrate(Cursor cursor, int type) {


        this._ID = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN__ID));
        this._UID = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN__UID));
        this._UUID = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN__UUID));
        this.clusterno = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_CLUSTER));
        this.hhno = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_HH));
        this.WUID = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_WUID));


        if (type == 0 || type == 1) {
            this.sM = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_SM));
        }

        if (type == 0) {
            this.formDate = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_FORMDATE));
            this.user = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_USER));
            this.deviceID = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_DEVICEID));
            this.devicetagID = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_DEVICETAGID));
            this.synced = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_SYNCED));
            this.synced_date = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_SYNCED_DATE));
            this.appversion = cursor.getString(cursor.getColumnIndex(MicroTable.COLUMN_APPVERSION));

        }
        // TODO:

        return this;

    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();

        json.put(MicroTable.COLUMN_PROJECTNAME, this.projectName == null ? JSONObject.NULL : this.projectName);
        json.put(MicroTable.COLUMN__ID, this._ID == null ? JSONObject.NULL : this._ID);
        json.put(MicroTable.COLUMN__UID, this._UID == null ? JSONObject.NULL : this._UID);
        json.put(MicroTable.COLUMN__UUID, this._UUID == null ? JSONObject.NULL : this._UUID);
        json.put(MicroTable.COLUMN_CLUSTER, this.clusterno == null ? JSONObject.NULL : this.clusterno);
        json.put(MicroTable.COLUMN_HH, this.hhno == null ? JSONObject.NULL : this.hhno);
        json.put(MicroTable.COLUMN_WUID, this.WUID == null ? JSONObject.NULL : this.WUID);

        json.put(MicroTable.COLUMN_FORMDATE, this.formDate == null ? JSONObject.NULL : this.formDate);
        json.put(MicroTable.COLUMN_USER, this.user == null ? JSONObject.NULL : this.user);

        if (!this.sM.equals("")) {
            json.put(MicroTable.COLUMN_SM, this.sM.equals("") ? JSONObject.NULL : new JSONObject(this.sM));
            /*  json.put(MicroTable.COLUMN_SC1, this.sC1 == null ? JSONObject.NULL : this.sC1);*/
        }


        json.put(MicroTable.COLUMN_DEVICEID, this.deviceID == null ? JSONObject.NULL : this.deviceID);
        json.put(MicroTable.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
        /*json.put(MicroTable.COLUMN_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(MicroTable.COLUMN_SYNCED_DATE, this.synced_date == null ? JSONObject.NULL : this.synced_date);*/
        json.put(MicroTable.COLUMN_APPVERSION, this.appversion == null ? JSONObject.NULL : this.appversion);


        return json;
    }


    public String getWUID() {
        return WUID;
    }

    public void setWUID(String WUID) {
        this.WUID = WUID;
    }

    public String getClusterno() {
        return clusterno;
    }

    public void setClusterno(String clusterno) {
        this.clusterno = clusterno;
    }

    public String getHhno() {
        return hhno;
    }

    public void setHhno(String hhno) {
        this.hhno = hhno;
    }


    public String getProjectName() {
        return projectName;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getUID() {
        return _UID;
    }

    public void setUID(String _UID) {
        this._UID = _UID;
    }


    public String getUUID() {
        return _UUID;
    }

    public void setUUID(String _UUID) {
        this._UUID = _UUID;
    }


    public String getFormDate() {
        return formDate;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDevicetagID() {
        return devicetagID;
    }

    public void setDevicetagID(String devicetagID) {
        this.devicetagID = devicetagID;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSynced_date() {
        return synced_date;
    }

    public void setSynced_date(String synced_date) {
        this.synced_date = synced_date;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }


    public String getsM() {
        return sM;
    }

    public void setsM(String sM) {
        this.sM = sM;
    }


    public static abstract class MicroTable implements BaseColumns {

        public static final String TABLE_NAME = "micro";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";

        public static final String COLUMN_PROJECTNAME = "projectname";
        public static final String COLUMN__ID = "_id";
        public static final String COLUMN__UID = "_uid";
        public static final String COLUMN__UUID = "_uuid";
        //public static final String COLUMN_FM_UID = "fmuid";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_WUID = "wuid";
        public static final String COLUMN_CLUSTER = "cluster";
        public static final String COLUMN_HH = "hh_no";
        public static final String COLUMN_SM = "sm";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";


        public static String _URL = "micro.php";
    }

}