package edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD;

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
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD3BBinding;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionD3BActivity extends AppCompatActivity {

    ActivitySectionD3BBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d3_b);
        bi.setCallback(this);
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
                startActivity(new Intent(this, SectionD3AActivity.class));
                MainApp.isAttitudeCheck = true;
            }
        }

    }

    private void SaveDraft() throws JSONException {
        MainApp.d4WRAc = new D4WRAContract();
        MainApp.d4WRAc.setDevicetagID(MainApp.fc.getDevicetagID());
        MainApp.d4WRAc.setFormDate(MainApp.fc.getFormDate());
        MainApp.d4WRAc.setUser(MainApp.fc.getUser());
        MainApp.d4WRAc.setDeviceId(MainApp.fc.getDeviceID());
        MainApp.d4WRAc.setApp_ver(MainApp.fc.getAppversion());
        MainApp.d4WRAc.set_UUID(MainApp.fc.getUID());
        MainApp.d4WRAc.setfType(MainApp.WRAD3B);
        MainApp.d4WRAc.setD1SerialNo(String.valueOf(MainApp.dwraSerial_no));

        JSONObject dwraC = new JSONObject();
        dwraC.put("cid30402", bi.cid30402.getText().toString());
        dwraC.put("cid30403", bi.cid30403a.isChecked() ? "1"
                : bi.cid30403b.isChecked() ? "2"
                : "0");
        dwraC.put("cid30404", bi.cid30404a.isChecked() ? "1"
                : bi.cid30404b.isChecked() ? "2"
                : bi.cid30404c.isChecked() ? "3"
                : bi.cid30404d.isChecked() ? "4"
                : bi.cid30404e.isChecked() ? "5"
                : bi.cid30404f.isChecked() ? "6"
                : bi.cid30404g.isChecked() ? "7"
                : bi.cid3040496.isChecked() ? "96"
                : "0");
        dwraC.put("cid3040496x", bi.cid3040496x.getText().toString());
        dwraC.put("cid30405", bi.cid30405.getText().toString());
        MainApp.d4WRAc.setsD1(String.valueOf(dwraC));

        MainApp.dwraSerial_no++;
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
                SectionD3BActivity.this);
        alertDialogBuilder
                .setMessage("Are you sure to add new Entry?")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                finish();
                                if (UpdateDB()) {
                                    startActivity(new Intent(SectionD3BActivity.this, SectionD3BActivity.class)
                                            .putExtra("fType", "d3b"));
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

    public void BtnEnd() {
        MainApp.endActivityMother(this, this, false);
    }


    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD3B);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
