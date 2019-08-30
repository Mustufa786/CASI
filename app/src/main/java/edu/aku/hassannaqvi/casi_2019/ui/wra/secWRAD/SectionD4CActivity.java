package edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.D4WRAContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD4CBinding;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionD4CActivity extends AppCompatActivity {

    ActivitySectionD4CBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d4_c);
        bi.setCallback(this);

        MainApp.dWraType = getIntent().getStringExtra("fType");

        if (!MainApp.isAttitudeCheck) {
            bi.fldGrpcid405.setVisibility(View.VISIBLE);
        } else {
            bi.fldGrpcid405.setVisibility(View.GONE);
        }
        bi.cid405.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == bi.cid405a.getId()) {
                    bi.fldGrpCheck.setVisibility(View.VISIBLE);
                    bi.btnAddMore.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpCheck.setVisibility(View.GONE);
                    bi.btnAddMore.setVisibility(View.GONE);
                }
            }
        });
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
                startActivity(new Intent(this, SectionD4DActivity.class)
                        .putExtra("fType", "d4d"));
                MainApp.dwraSerial_no = 1;
                MainApp.isAttitudeCheck = false;
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
                SectionD4CActivity.this);
        alertDialogBuilder
                .setMessage("Are you sure to add new Entry?")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                finish();
                                if (UpdateDB()) {
                                    MainApp.isAttitudeCheck = true;
                                    startActivity(new Intent(SectionD4CActivity.this, SectionD4CActivity.class)
                                            .putExtra("fType", "d4c"));
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

    private void SaveDraft() throws JSONException {

        JSONObject sB11 = new JSONObject();
        MainApp.d4WRAc = new D4WRAContract();
        MainApp.d4WRAc.setDevicetagID(MainApp.fc.getDevicetagID());
        MainApp.d4WRAc.setFormDate(MainApp.fc.getFormDate());
        MainApp.d4WRAc.setUser(MainApp.fc.getUser());
        MainApp.d4WRAc.setDeviceId(MainApp.fc.getDeviceID());
        MainApp.d4WRAc.setApp_ver(MainApp.fc.getAppversion());
        MainApp.d4WRAc.set_UUID(MainApp.fc.getUID());
        MainApp.d4WRAc.setfType(MainApp.WRAD3B);
        MainApp.d4WRAc.setB1SerialNo(String.valueOf(MainApp.dwraSerial_no));
        if (!MainApp.isAttitudeCheck) {
            sB11.put("cid405", bi.cid405a.isChecked() ? "1"
                    : bi.cid405b.isChecked() ? "2"
                    : "0");
        }

        sB11.put("cid40602", bi.cid40602.getText().toString());
        sB11.put("cid40603", bi.cid40603.getText().toString());
        sB11.put("cid40604", bi.cid40604.getText().toString());
        sB11.put("cid40605", bi.cid40605.getText().toString());
        MainApp.d4WRAc.setsD1(String.valueOf(sB11));

        MainApp.dwraSerial_no++;

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
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD4C);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
