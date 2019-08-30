package edu.aku.hassannaqvi.casi_2019.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class D4WRAContract {

    private final String projectName = "Central Asia Stunting Initiative 2019";
    private String _ID = "";
    private String _UID = "";
    private String _UUID = "";
    private String FMUID = "";
    private String formDate = "";
    private String updatedate = "";
    private String deviceId = "";
    private String devicetagID = "";
    private String user = "";
    private String app_ver = "";
    private String mstatus = "";
    private String mstatus88x = "";
    private String fType = "";
    private String b1SerialNo = "";
    private String sD1 = "";
    private String sb2flag = "";
    private boolean kishSelectWRA;
    private String cluster = "";
    private String hhno = "";
    private String synced = "";
    private String syncedDate = "";


    public D4WRAContract() {
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public boolean getKishSelectWRA() {
        return kishSelectWRA;
    }

    public void setKishSelectWRA(boolean kishSelectWRA) {
        this.kishSelectWRA = kishSelectWRA;
    }

    public String getFMUID() {
        return FMUID;
    }

    public void setFMUID(String FMUID) {
        this.FMUID = FMUID;
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

    public String get_UID() {
        return _UID;
    }

    public void set_UID(String _UID) {
        this._UID = _UID;
    }

    public String get_UUID() {
        return _UUID;
    }

    public void set_UUID(String _UUID) {
        this._UUID = _UUID;
    }

    public String getFormDate() {
        return formDate;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getApp_ver() {
        return app_ver;
    }

    public void setApp_ver(String app_ver) {
        this.app_ver = app_ver;
    }

    public String getsD1() {
        return sD1;
    }

    public void setsD1(String sD1) {
        this.sD1 = sD1;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(String syncedDate) {
        this.syncedDate = syncedDate;
    }

    public String getB1SerialNo() {
        return b1SerialNo;
    }

    public void setB1SerialNo(String b1SerialNo) {
        this.b1SerialNo = b1SerialNo;
    }

    public String getDevicetagID() {
        return devicetagID;
    }

    public void setDevicetagID(String devicetagID) {
        this.devicetagID = devicetagID;
    }

    public String getMstatus() {
        return mstatus;
    }

    public void setMstatus(String mstatus) {
        this.mstatus = mstatus;
    }

    public String getMstatus88x() {
        return mstatus88x;
    }

    public void setMstatus88x(String mstatus88x) {
        this.mstatus88x = mstatus88x;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getSb2flag() {
        return sb2flag;
    }

    public void setSb2flag(String sb2flag) {
        this.sb2flag = sb2flag;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getHhno() {
        return hhno;
    }

    public void setHhno(String hhno) {
        this.hhno = hhno;
    }

    public D4WRAContract Sync(JSONObject jsonObject) throws JSONException {

        this._ID = jsonObject.getString(D4WRATable.COLUMN_ID);
        this._UID = jsonObject.getString(D4WRATable.COLUMN_UID);
        this._UUID = jsonObject.getString(D4WRATable.COLUMN_UUID);
        this.FMUID = jsonObject.getString(D4WRATable.COLUMN_FM_UID);
        this.fType = jsonObject.getString(D4WRATable.COLUMN_FTYPE);
        this.formDate = jsonObject.getString(D4WRATable.COLUMN_FORMDATE);
        this.deviceId = jsonObject.getString(D4WRATable.COLUMN_DEVICEID);
        this.devicetagID = jsonObject.getString(D4WRATable.COLUMN_DEVICETAGID);
        this.user = jsonObject.getString(D4WRATable.COLUMN_USER);
        this.app_ver = jsonObject.getString(D4WRATable.COLUMN_APP_VER);
        this.b1SerialNo = jsonObject.getString(D4WRATable.COLUMN_DSERIALNO);
        this.sD1 = jsonObject.getString(D4WRATable.COLUMN_SD1);
        this.synced = jsonObject.getString(D4WRATable.COLUMN_SYNCED);
        this.syncedDate = jsonObject.getString(D4WRATable.COLUMN_SYNCEDDATE);
        this.mstatus = jsonObject.getString(D4WRATable.COLUMN_MSTATUS);
        this.mstatus88x = jsonObject.getString(D4WRATable.COLUMN_MSTATUS88x);


        return this;

    }

    public D4WRAContract Hydrate(Cursor cursor, int type) {

        this._ID = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_ID));
        this._UID = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_UID));
        this._UUID = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_UUID));
        this.FMUID = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_FM_UID));

        this.sD1 = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_SD1));

        this.user = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_USER));
        this.app_ver = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_APP_VER));
        this.deviceId = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_DEVICEID));
        this.fType = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_FTYPE));

        this.formDate = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_FORMDATE));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_DEVICETAGID));
        this.b1SerialNo = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_DSERIALNO));
        this.synced = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_SYNCED));
        this.syncedDate = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_SYNCEDDATE));
        this.mstatus = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_MSTATUS));
        this.mstatus88x = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_MSTATUS88x));
//        if (type == 0 || type == 1) {
//
////        }
//        }
//        if (type == 0 || type == 2) {
//            this.sb2flag = cursor.getString(cursor.getColumnIndex(D4WRATable.COLUMN_SB2FLAG));
//        }
//        if (type == 0 || type == 3) {
//
//        }
//        if (type == 0 || type == 4) {
//
//        }
//        if (type == 0 || type == 5) {
//
//        }
//        if (type == 0) {


        return this;

    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();

        json.put(D4WRATable.COLUMN_PROJECTNAME, this.projectName == null ? JSONObject.NULL : this.projectName);
        json.put(D4WRATable.COLUMN_ID, this._ID == null ? JSONObject.NULL : this._ID);
        json.put(D4WRATable.COLUMN_UID, this._UID == null ? JSONObject.NULL : this._UID);
        json.put(D4WRATable.COLUMN_UUID, this._UUID == null ? JSONObject.NULL : this._UUID);
        json.put(D4WRATable.COLUMN_FM_UID, this.FMUID == null ? JSONObject.NULL : this.FMUID);
        json.put(D4WRATable.COLUMN_FORMDATE, this.formDate == null ? JSONObject.NULL : this.formDate);

//        json.put(D4WRATable.COLUMN_UPDATEDATE, this.updatedate == null ? JSONObject.NULL : this.updatedate);

        json.put(D4WRATable.COLUMN_DEVICEID, this.deviceId == null ? JSONObject.NULL : this.deviceId);
        json.put(D4WRATable.COLUMN_FTYPE, this.fType == null ? JSONObject.NULL : this.fType);
        json.put(D4WRATable.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
        json.put(D4WRATable.COLUMN_USER, this.user == null ? JSONObject.NULL : this.user);
        json.put(D4WRATable.COLUMN_APP_VER, this.app_ver == null ? JSONObject.NULL : this.app_ver);
        json.put(D4WRATable.COLUMN_DSERIALNO, this.b1SerialNo == null ? JSONObject.NULL : this.b1SerialNo);

        if (!this.sD1.equals("")) {
            json.put(D4WRATable.COLUMN_SD1, this.sD1.equals("") ? JSONObject.NULL : new JSONObject(this.sD1));
        }

//        if (!this.sB2.equals("")) {
//            json.put(D4WRATable.COLUMN_SB2, this.sB2.equals("") ? JSONObject.NULL : new JSONObject(this.sB2));
//        }
//
//        if (!this.sB3.equals("")) {
//            json.put(D4WRATable.COLUMN_SB3, this.sB3.equals("") ? JSONObject.NULL : new JSONObject(this.sB3));
//        }
//
//        if (!this.sB4.equals("")) {
//            json.put(D4WRATable.COLUMN_SB4, this.sB4.equals("") ? JSONObject.NULL : new JSONObject(this.sB4));
//        }
//
//        if (!this.sB5.equals("")) {
//            json.put(D4WRATable.COLUMN_SB5, this.sB5.equals("") ? JSONObject.NULL : new JSONObject(this.sB5));
//        }
//
//        if (!this.sB6.equals("")) {
//            json.put(D4WRATable.COLUMN_SB6, this.sB6.equals("") ? JSONObject.NULL : new JSONObject(this.sB6));
//        }


        /*json.put(D4WRATable.COLUMN_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(D4WRATable.COLUMN_SYNCEDDATE, this.syncedDate == null ? JSONObject.NULL : this.syncedDate);*/
        json.put(D4WRATable.COLUMN_MSTATUS, this.mstatus == null ? JSONObject.NULL : this.mstatus);
        json.put(D4WRATable.COLUMN_MSTATUS88x, this.mstatus88x == null ? JSONObject.NULL : this.mstatus88x);


        return json;
    }

    public static abstract class D4WRATable implements BaseColumns {

        public static final String TABLE_NAME = "D4WRATable";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECTNAME = "projectname";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "uid";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_FM_UID = "fmuid";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_UPDATEDATE = "updatedate";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_FTYPE = "fType";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_APP_VER = "app_ver";
        public static final String COLUMN_DSERIALNO = "dserialno";
        public static final String COLUMN_SD1 = "sd";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCEDDATE = "synceddate";
        public static final String COLUMN_MSTATUS = "mstatus";
        public static final String COLUMN_MSTATUS88x = "mstatus88x";


        public static String _URL = "wras.php";
    }
}
