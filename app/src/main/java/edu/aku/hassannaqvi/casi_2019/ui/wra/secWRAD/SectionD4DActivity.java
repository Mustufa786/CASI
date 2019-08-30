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
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD4DBinding;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionD4DActivity extends AppCompatActivity {


    ActivitySectionD4DBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d4_d);
        bi.setCallback(this);

        MainApp.dWraType = getIntent().getStringExtra("fType");
        if (!MainApp.isAttitudeCheck) {
            bi.fldGrpcid407.setVisibility(View.VISIBLE);
        } else {
            bi.fldGrpcid407.setVisibility(View.GONE);
        }
        bi.cid407.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == bi.cid407a.getId()) {
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
                MainApp.dwraSerial_no = 1;
                MainApp.isAttitudeCheck = false;
                finish();
                startActivity(new Intent(this, SectionD4EActivity.class));
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
                SectionD4DActivity.this);
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
                                    startActivity(new Intent(SectionD4DActivity.this, SectionD4DActivity.class)
                                            .putExtra("fType", "d4d"));
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

        JSONObject sB12 = new JSONObject();
        MainApp.d4WRAc = new D4WRAContract();
        MainApp.d4WRAc.setDevicetagID(MainApp.fc.getDevicetagID());
        MainApp.d4WRAc.setFormDate(MainApp.fc.getFormDate());
        MainApp.d4WRAc.setUser(MainApp.fc.getUser());
        MainApp.d4WRAc.setDeviceId(MainApp.fc.getDeviceID());
        MainApp.d4WRAc.setApp_ver(MainApp.fc.getAppversion());
        MainApp.d4WRAc.set_UUID(MainApp.fc.getUID());
        MainApp.d4WRAc.setfType(MainApp.WRAD4D);
        MainApp.d4WRAc.setB1SerialNo(String.valueOf(MainApp.dwraSerial_no));
        if (!MainApp.isAttitudeCheck) {
            sB12.put("cid407", bi.cid407a.isChecked() ? "1"
                    : bi.cid407b.isChecked() ? "2"
                    : "0");

        }
        sB12.put("cid40802", bi.cid40802.getText().toString());
        sB12.put("cid40803", bi.cid40803.getText().toString());
        sB12.put("cid40804", bi.cid40804.getText().toString());
        sB12.put("cid40805", bi.cid40805.getText().toString());
        MainApp.d4WRAc.setsD1(String.valueOf(sB12));

        MainApp.dwraSerial_no++;


    }

    public void BtnEnd() {
        MainApp.endActivityMother(this, this, false);
    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD4D);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
