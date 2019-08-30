package edu.aku.hassannaqvi.casi_2019.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class MWRAContract {

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

    private String b1SerialNo = "";
    private String sB1 = "";
    private String sB2 = "";
    private String sB3 = "";
    private String sB4 = "";
    private String sB5 = "";
    private String sB6 = "";
    private String sB7 = "";
    private String sB8 = "";
    private String sB9 = "";
    private String sB10 = "";
    private String sB11 = "";
    private String sb2flag = "";
    private boolean kishSelectWRA;
    private boolean kishSelectMWRA;
    private String cluster = "";
    private String hhno = "";

    private String synced = "";
    private String syncedDate = "";

    public MWRAContract() {
    }


    public String getsB11() {
        return sB11;
    }

    public void setsB11(String sB11) {
        this.sB11 = sB11;
    }


    public String getsB7() {
        return sB7;
    }

    public void setsB7(String sB7) {
        this.sB7 = sB7;
    }

    public String getsB8() {
        return sB8;
    }

    public void setsB8(String sB8) {
        this.sB8 = sB8;
    }

    public String getsB9() {
        return sB9;
    }

    public void setsB9(String sB9) {
        this.sB9 = sB9;
    }

    public String getsB10() {
        return sB10;
    }

    public void setsB10(String sB10) {
        this.sB10 = sB10;
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

    public String getsB1() {
        return sB1;
    }

    public void setsB1(String sB1) {
        this.sB1 = sB1;
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

    public String getsB2() {
        return sB2;
    }

    public void setsB2(String sB2) {
        this.sB2 = sB2;
    }

    public String getsB4() {
        return sB4;
    }

    public void setsB4(String sB4) {
        this.sB4 = sB4;
    }

    public String getsB5() {
        return sB5;
    }

    public void setsB5(String sB5) {
        this.sB5 = sB5;
    }

    public String getsB6() {
        return sB6;
    }

    public void setsB6(String sB6) {
        this.sB6 = sB6;
    }

    public String getsB3() {
        return sB3;
    }

    public void setsB3(String sB3) {
        this.sB3 = sB3;
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

    public boolean getKishSelectMWRA() {
        return kishSelectMWRA;
    }

    public void setKishSelectMWRA(boolean kishSelectMWRA) {
        this.kishSelectMWRA = kishSelectMWRA;
    }

    public MWRAContract Sync(JSONObject jsonObject) throws JSONException {

        this._ID = jsonObject.getString(MWRATable.COLUMN__ID);
        this._UID = jsonObject.getString(MWRATable.COLUMN_UID);
        this._UUID = jsonObject.getString(MWRATable.COLUMN_UUID);
        this.FMUID = jsonObject.getString(MWRATable.COLUMN_FM_UID);
        this.formDate = jsonObject.getString(MWRATable.COLUMN_FORMDATE);
        this.deviceId = jsonObject.getString(MWRATable.COLUMN_DEVICEID);
        this.devicetagID = jsonObject.getString(MWRATable.COLUMN_DEVICETAGID);
        this.user = jsonObject.getString(MWRATable.COLUMN_USER);
        this.app_ver = jsonObject.getString(MWRATable.COLUMN_APP_VER);
        this.b1SerialNo = jsonObject.getString(MWRATable.COLUMN_B1SERIALNO);
        this.sB1 = jsonObject.getString(MWRATable.COLUMN_SB1);
        this.sB2 = jsonObject.getString(MWRATable.COLUMN_SB2);
        this.sb2flag = jsonObject.getString(MWRATable.COLUMN_SB2FLAG);
        this.sB3 = jsonObject.getString(MWRATable.COLUMN_SB3);
        this.sB4 = jsonObject.getString(MWRATable.COLUMN_SB4);
        this.sB5 = jsonObject.getString(MWRATable.COLUMN_SB5);
        this.sB6 = jsonObject.getString(MWRATable.COLUMN_SB6);
        this.sB7 = jsonObject.getString(MWRATable.COLUMN_SB7);
        this.sB8 = jsonObject.getString(MWRATable.COLUMN_SB8);
        this.sB9 = jsonObject.getString(MWRATable.COLUMN_SB9);
        this.sB10 = jsonObject.getString(MWRATable.COLUMN_SB10);
        this.sB11 = jsonObject.getString(MWRATable.COLUMN_SB11);
        this.synced = jsonObject.getString(MWRATable.COLUMN_SYNCED);
        this.syncedDate = jsonObject.getString(MWRATable.COLUMN_SYNCEDDATE);
        this.mstatus = jsonObject.getString(MWRATable.COLUMN_MSTATUS);
        this.mstatus88x = jsonObject.getString(MWRATable.COLUMN_MSTATUS88x);


        return this;

    }

    public MWRAContract Hydrate(Cursor cursor, int type) {

        this._ID = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN__ID));
        this._UID = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_UID));
        this._UUID = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_UUID));
        this.FMUID = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_FM_UID));

        if (type == 0 || type == 1) {
            this.sB1 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB1));
            this.sB6 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB6));
            this.sb2flag = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB2FLAG));
            this.user = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_USER));
            this.app_ver = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_APP_VER));
            this.deviceId = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_DEVICEID));

            this.formDate = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_FORMDATE));
            this.devicetagID = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_DEVICETAGID));
            this.b1SerialNo = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_B1SERIALNO));
        }
        if (type == 0 || type == 2) {
            this.sB2 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB2));
            this.sB6 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB6));
            this.sb2flag = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB2FLAG));
        }
        if (type == 0 || type == 3) {
            this.sB3 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB3));
        }
        if (type == 0 || type == 4) {
            this.sB4 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB4));
        }
        if (type == 0 || type == 5) {
            this.sB5 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB5));
        }
        if (type == 0 || type == 6) {
            this.sB7 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB7));
            this.sB8 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB8));
            this.sB9 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB9));
            this.sB10 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB10));
            this.sB11 = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SB11));
        }
        if (type == 0) {
            this.synced = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SYNCED));
            this.syncedDate = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_SYNCEDDATE));
            this.mstatus = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_MSTATUS));
            this.mstatus88x = cursor.getString(cursor.getColumnIndex(MWRATable.COLUMN_MSTATUS88x));
        }

        return this;

    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();

        json.put(MWRATable.COLUMN_PROJECTNAME, this.projectName == null ? JSONObject.NULL : this.projectName);
        json.put(MWRATable.COLUMN__ID, this._ID == null ? JSONObject.NULL : this._ID);
        json.put(MWRATable.COLUMN_UID, this._UID == null ? JSONObject.NULL : this._UID);
        json.put(MWRATable.COLUMN_UUID, this._UUID == null ? JSONObject.NULL : this._UUID);
        json.put(MWRATable.COLUMN_FM_UID, this.FMUID == null ? JSONObject.NULL : this.FMUID);
        json.put(MWRATable.COLUMN_FORMDATE, this.formDate == null ? JSONObject.NULL : this.formDate);

//        json.put(MWRATable.COLUMN_UPDATEDATE, this.updatedate == null ? JSONObject.NULL : this.updatedate);

        json.put(MWRATable.COLUMN_DEVICEID, this.deviceId == null ? JSONObject.NULL : this.deviceId);
        json.put(MWRATable.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
        json.put(MWRATable.COLUMN_USER, this.user == null ? JSONObject.NULL : this.user);
        json.put(MWRATable.COLUMN_APP_VER, this.app_ver == null ? JSONObject.NULL : this.app_ver);
        json.put(MWRATable.COLUMN_B1SERIALNO, this.b1SerialNo == null ? JSONObject.NULL : this.b1SerialNo);

        if (!this.sB1.equals("")) {
            json.put(MWRATable.COLUMN_SB1, this.sB1.equals("") ? JSONObject.NULL : new JSONObject(this.sB1));
        }

        if (!this.sB2.equals("")) {
            json.put(MWRATable.COLUMN_SB2, this.sB2.equals("") ? JSONObject.NULL : new JSONObject(this.sB2));
        }

        if (!this.sB3.equals("")) {
            json.put(MWRATable.COLUMN_SB3, this.sB3.equals("") ? JSONObject.NULL : new JSONObject(this.sB3));
        }

        if (!this.sB4.equals("")) {
            json.put(MWRATable.COLUMN_SB4, this.sB4.equals("") ? JSONObject.NULL : new JSONObject(this.sB4));
        }

        if (!this.sB5.equals("")) {
            json.put(MWRATable.COLUMN_SB5, this.sB5.equals("") ? JSONObject.NULL : new JSONObject(this.sB5));
        }

        if (!this.sB6.equals("")) {
            json.put(MWRATable.COLUMN_SB6, this.sB6.equals("") ? JSONObject.NULL : new JSONObject(this.sB6));
        }
        if (!this.sB7.equals("")) {
            json.put(MWRATable.COLUMN_SB7, this.sB7.equals("") ? JSONObject.NULL : new JSONObject(this.sB7));
        }
        if (!this.sB8.equals("")) {
            json.put(MWRATable.COLUMN_SB8, this.sB8.equals("") ? JSONObject.NULL : new JSONObject(this.sB8));
        }
        if (!this.sB9.equals("")) {
            json.put(MWRATable.COLUMN_SB9, this.sB9.equals("") ? JSONObject.NULL : new JSONObject(this.sB9));
        }
        if (!this.sB10.equals("")) {
            json.put(MWRATable.COLUMN_SB10, this.sB10.equals("") ? JSONObject.NULL : new JSONObject(this.sB10));
        }
        if (!this.sB11.equals("")) {
            json.put(MWRATable.COLUMN_SB11, this.sB11.equals("") ? JSONObject.NULL : new JSONObject(this.sB11));
        }

        json.put(MWRATable.COLUMN_SB2FLAG, this.sb2flag == null ? JSONObject.NULL : this.sb2flag);

        /*json.put(MWRATable.COLUMN_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(MWRATable.COLUMN_SYNCEDDATE, this.syncedDate == null ? JSONObject.NULL : this.syncedDate);*/
        json.put(MWRATable.COLUMN_MSTATUS, this.mstatus == null ? JSONObject.NULL : this.mstatus);
        json.put(MWRATable.COLUMN_MSTATUS88x, this.mstatus88x == null ? JSONObject.NULL : this.mstatus88x);


        return json;
    }

    public static abstract class MWRATable implements BaseColumns {

        public static final String TABLE_NAME = "mwra";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";

        public static final String COLUMN_PROJECTNAME = "projectname";
        public static final String COLUMN__ID = "_id";
        public static final String COLUMN_UID = "uid";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_FM_UID = "fmuid";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_UPDATEDATE = "updatedate";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_APP_VER = "app_ver";
        public static final String COLUMN_B1SERIALNO = "b1serialno";
        public static final String COLUMN_SB1 = "sb1";
        public static final String COLUMN_SB2 = "sb2";
        public static final String COLUMN_SB3 = "sb3";
        public static final String COLUMN_SB4 = "sb4";
        public static final String COLUMN_SB5 = "sb5";
        public static final String COLUMN_SB6 = "sb6";
        public static final String COLUMN_SB7 = "sb7";
        public static final String COLUMN_SB8 = "sb8";
        public static final String COLUMN_SB9 = "sb9";
        public static final String COLUMN_SB10 = "sb10";
        public static final String COLUMN_SB11 = "sb11";
        public static final String COLUMN_SB2FLAG = "sb2flag";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCEDDATE = "synceddate";
        public static final String COLUMN_MSTATUS = "mstatus";
        public static final String COLUMN_MSTATUS88x = "mstatus88x";


        public static String _URL = "wras.php";
    }
}
