package edu.aku.hassannaqvi.casi_2019.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ali.azaz on 07/02/2018.
 */

public class SummaryContract {

    private final String projectName = "Central Asia Stunting Initiative 2019";
    private String ROW_ID;
    private String _uid;
    private String clusterno;
    private String hhno;
    private String hh;
    private String women;
    private String child;
    private String anthro;
    private String specimen;
    private String water;
    private String synced;
    private String synceddate;
    private String user;
    private String formdate;
    private String deviceid;
    private String devicetagID;
    private String appversion;

    public SummaryContract() {
    }

    public String getProjectName() {
        return projectName;
    }

    public String getROW_ID() {
        return ROW_ID;
    }

    public void setROW_ID(String ROW_ID) {
        this.ROW_ID = ROW_ID;
    }

    public String get_uid() {
        return _uid;
    }

    public void set_uid(String _uid) {
        this._uid = _uid;
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

    public String getHh() {
        return hh;
    }

    public void setHh(String hh) {
        this.hh = hh;
    }

    public String getWomen() {
        return women;
    }

    public void setWomen(String women) {
        this.women = women;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getAnthro() {
        return anthro;
    }

    public void setAnthro(String anthro) {
        this.anthro = anthro;
    }

    public String getSpecimen() {
        return specimen;
    }

    public void setSpecimen(String specimen) {
        this.specimen = specimen;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSynceddate() {
        return synceddate;
    }

    public void setSynceddate(String synceddate) {
        this.synceddate = synceddate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFormdate() {
        return formdate;
    }

    public void setFormdate(String formdate) {
        this.formdate = formdate;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicetagID() {
        return devicetagID;
    }

    public void setDevicetagID(String devicetagID) {
        this.devicetagID = devicetagID;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public SummaryContract Sync(JSONObject jsonObject) throws JSONException {
        this.ROW_ID = jsonObject.getString(singleSum.COLUMN_ROW_ID);
        this._uid = jsonObject.getString(singleSum.COLUMN__UID);
        this.clusterno = jsonObject.getString(singleSum.COLUMN_CLUSTERNO);
        this.hhno = jsonObject.getString(singleSum.COLUMN_HHNO);
        this.hh = jsonObject.getString(singleSum.COLUMN_HH);
        this.women = jsonObject.getString(singleSum.COLUMN_WOMEN);
        this.child = jsonObject.getString(singleSum.COLUMN_CHILD);
        this.anthro = jsonObject.getString(singleSum.COLUMN_ANTHRO);
        this.specimen = jsonObject.getString(singleSum.COLUMN_SPECIMEN);
        this.water = jsonObject.getString(singleSum.COLUMN_WATER);
        this.synced = jsonObject.getString(singleSum.COLUMN_SYNCED);
        this.synceddate = jsonObject.getString(singleSum.COLUMN_SYNCEDDATE);
        this.user = jsonObject.getString(singleSum.COLUMN_USER);
        this.formdate = jsonObject.getString(singleSum.COLUMN_FORMDATE);
        this.deviceid = jsonObject.getString(singleSum.COLUMN_DEVICEID);
        this.devicetagID = jsonObject.getString(singleSum.COLUMN_DEVICETAGID);
        this.appversion = jsonObject.getString(singleSum.COLUMN_APPVERSION);

        return this;
    }

    public SummaryContract Hydrate(Cursor cursor) {

        this.ROW_ID = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_ROW_ID));
        this._uid = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN__UID));
        this.clusterno = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_CLUSTERNO));
        this.hhno = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_HHNO));
        this.hh = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_HH));
        this.women = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_WOMEN));
        this.child = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_CHILD));
        this.anthro = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_ANTHRO));
        this.specimen = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_SPECIMEN));
        this.water = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_WATER));
        this.synced = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_SYNCED));
        this.synceddate = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_SYNCEDDATE));
        this.user = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_USER));
        this.formdate = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_FORMDATE));
        this.deviceid = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_DEVICEID));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_DEVICETAGID));
        this.appversion = cursor.getString(cursor.getColumnIndex(singleSum.COLUMN_APPVERSION));

        return this;
    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();

        json.put(singleSum.COLUMN_ROW_ID, this.ROW_ID == null ? JSONObject.NULL : this.ROW_ID);
        json.put(singleSum.COLUMN__UID, this._uid == null ? JSONObject.NULL : this._uid);
        json.put(singleSum.COLUMN_CLUSTERNO, this.clusterno == null ? JSONObject.NULL : this.clusterno);
        json.put(singleSum.COLUMN_HHNO, this.hhno == null ? JSONObject.NULL : this.hhno);
        json.put(singleSum.COLUMN_HH, this.hh == null ? JSONObject.NULL : this.hh);
        json.put(singleSum.COLUMN_WOMEN, this.women == null ? JSONObject.NULL : this.women);
        json.put(singleSum.COLUMN_CHILD, this.child == null ? JSONObject.NULL : this.child);
        json.put(singleSum.COLUMN_ANTHRO, this.anthro == null ? JSONObject.NULL : this.anthro);
        json.put(singleSum.COLUMN_SPECIMEN, this.specimen == null ? JSONObject.NULL : this.specimen);
        json.put(singleSum.COLUMN_WATER, this.water == null ? JSONObject.NULL : this.water);
        json.put(singleSum.COLUMN_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(singleSum.COLUMN_SYNCEDDATE, this.synceddate == null ? JSONObject.NULL : this.synceddate);
        json.put(singleSum.COLUMN_USER, this.user == null ? JSONObject.NULL : this.user);
        json.put(singleSum.COLUMN_FORMDATE, this.formdate == null ? JSONObject.NULL : this.formdate);
        json.put(singleSum.COLUMN_DEVICEID, this.deviceid == null ? JSONObject.NULL : this.deviceid);
        json.put(singleSum.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
        json.put(singleSum.COLUMN_APPVERSION, this.appversion == null ? JSONObject.NULL : this.appversion);

        return json;
    }

    public static abstract class singleSum implements BaseColumns {

        public static final String TABLE_NAME = "summary";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";

        public static final String COLUMN_PROJECTNAME = "projectname";
        //        public static final String COLUMN_ROW_ID = "row_id";
        public static final String COLUMN_ROW_ID = "row_id";
        public static final String COLUMN__UID = "_uid";
        public static final String COLUMN_CLUSTERNO = "clusterno";
        public static final String COLUMN_HHNO = "hhno";
        public static final String COLUMN_HH = "hh";
        public static final String COLUMN_WOMEN = "women";
        public static final String COLUMN_CHILD = "child";
        public static final String COLUMN_ANTHRO = "anthro";
        public static final String COLUMN_SPECIMEN = "specimen";
        public static final String COLUMN_WATER = "water";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCEDDATE = "synceddate";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_APPVERSION = "appversion";

        public static String _URL = "summary.php";
    }

}