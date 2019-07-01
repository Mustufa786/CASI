package edu.aku.hassannaqvi.casi_2018.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class DeviceContract {
    private String _ID = "";
    private String imei = "";
    private String appversion = "";
    private String tagID = "";

    public DeviceContract() {
    }

    public DeviceContract Sync(JSONObject jsonObject) throws JSONException {
        this._ID = jsonObject.getString(DeviceTable.COLUMN__ID);
        this.imei = jsonObject.getString(DeviceTable.COLUMN_IMEI);
        this.appversion = jsonObject.getString(DeviceTable.COLUMN_APPVERSION);
        this.tagID = jsonObject.getString(DeviceTable.COLUMN_TAGID);
        return this;
    }

    public DeviceContract Hydrate(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(DeviceTable.COLUMN__ID));
        this.imei = cursor.getString(cursor.getColumnIndex(DeviceTable.COLUMN_IMEI));
        this.appversion = cursor.getString(cursor.getColumnIndex(DeviceTable.COLUMN_APPVERSION));
        this.tagID = cursor.getString(cursor.getColumnIndex(DeviceTable.COLUMN_TAGID));
        return this;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(DeviceTable.COLUMN_IMEI, this.imei == null ? JSONObject.NULL : this.imei);
        json.put(DeviceTable.COLUMN_APPVERSION, this.appversion == null ? JSONObject.NULL : this.appversion);
        json.put(DeviceTable.COLUMN_TAGID, this.tagID == null ? JSONObject.NULL : this.tagID);
        return json;
    }

    public String getimei() {
        return imei;
    }

    public void setimei(String imei) {
        this.imei = imei;
    }

    public String getappversion() {
        return appversion;
    }

    public void setappversion(String appversion) {
        this.appversion = appversion;
    }

    public String gettagID() {
        return tagID;
    }

    public void settagID(String tagID) {
        this.tagID = tagID;
    }

    public static abstract class DeviceTable implements BaseColumns {

        public static final String TABLE_NAME = "device";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";

        public static final String COLUMN__ID = "_id";
        public static final String COLUMN_IMEI = "imei";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_TAGID = "tag";

        public static String _URL = "devices.php";
    }

}
