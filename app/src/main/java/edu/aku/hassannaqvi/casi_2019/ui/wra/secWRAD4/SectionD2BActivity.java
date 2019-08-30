package edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.D4WRAContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD2BBinding;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionD2BActivity extends AppCompatActivity {


    ActivitySectionD2BBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d2_b);
        bi.setCallback(this);

        MainApp.dWraType = getIntent().getStringExtra("fType");
    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, SectionD2AActivity.class));
                MainApp.isAttitudeCheck = true;
            }
        }

    }

    public void BtnAddMore() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SectionD2BActivity.this);
        alertDialogBuilder
                .setMessage("Are you sure to add new Entry?")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                finish();
                                if (UpdateDB()) {
                                    startActivity(new Intent(SectionD2BActivity.this, SectionD2BActivity.class));
                                }
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);
        long updcount = db.addD4WRA(MainApp.d4WRAc);
        if (updcount != 0) {
            MainApp.d4WRAc.set_ID(String.valueOf(updcount));
            MainApp.d4WRAc.set_UID(
                    (MainApp.d4WRAc.getDeviceId() + MainApp.d4WRAc.get_ID()));
            db.updateDWraID();
            return true;
        } else {
            Toast.makeText(this, "Error in updating DB", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void SaveDraft() throws JSONException {

        MainApp.dwraSerial_no++;
        MainApp.d4WRAc = new D4WRAContract();
        MainApp.d4WRAc.setDevicetagID(MainApp.fc.getDevicetagID());
        MainApp.d4WRAc.setFormDate(MainApp.fc.getFormDate());
        MainApp.d4WRAc.setUser(MainApp.fc.getUser());
        MainApp.d4WRAc.setDeviceId(MainApp.fc.getDeviceID());
        MainApp.d4WRAc.setApp_ver(MainApp.fc.getAppversion());
        MainApp.d4WRAc.set_UUID(MainApp.fc.getUID());
        MainApp.d4WRAc.setfType(MainApp.dWraType);
        MainApp.d4WRAc.setB1SerialNo(String.valueOf(MainApp.dwraSerial_no));

        JSONObject dwraC = new JSONObject();
        dwraC.put("cid20502", bi.cid20502.getText().toString());
        dwraC.put("cid20503", bi.cid20503a.isChecked() ? "1" : "2");
        dwraC.put("cid20504", bi.cid20504a.isChecked() ? "1"
                : bi.cid20504b.isChecked() ? "2"
                : bi.cid20504c.isChecked() ? "3"
                : bi.cid20504d.isChecked() ? "4"
                : bi.cid20504e.isChecked() ? "5"
                : bi.cid20504f.isChecked() ? "6"
                : bi.cid20504g.isChecked() ? "7"
                : bi.cid20504h.isChecked() ? "8"
                : bi.cid20504i.isChecked() ? "9"
                : bi.cid20504j.isChecked() ? "10"
                : bi.cid2050496.isChecked() ? "96"
                : "0");
        dwraC.put("cid2050496x", bi.cid2050496x.getText().toString());
        MainApp.d4WRAc.setsD1(String.valueOf(dwraC));

    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD2B);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
