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
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD4ABinding;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionD4AActivity extends AppCompatActivity {

    ActivitySectionD4ABinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d4_a);
        bi.setCallback(this);

        this.setTitle(getString(R.string.cid4h));

        MainApp.dWraType = getIntent().getStringExtra("fType");

        if (!MainApp.isAttitudeCheck) {
            bi.fldGrpcid401.setVisibility(View.VISIBLE);
        } else {
            bi.fldGrpcid401.setVisibility(View.GONE);
        }

        bi.cid401.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == bi.cid401a.getId()) {
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
                startActivity(new Intent(this, SectionD4BActivity.class)
                        .putExtra("fType", "d4b"));
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
//                e.printStackTrace();
            }
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SectionD4AActivity.this);
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
                                    startActivity(new Intent(SectionD4AActivity.this, SectionD4AActivity.class)
                                            .putExtra("fType", "d4a"));
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

        JSONObject sB9 = new JSONObject();
        MainApp.d4WRAc = new D4WRAContract();
        MainApp.d4WRAc.setDevicetagID(MainApp.fc.getDevicetagID());
        MainApp.d4WRAc.setFormDate(MainApp.fc.getFormDate());
        MainApp.d4WRAc.setUser(MainApp.fc.getUser());
        MainApp.d4WRAc.setDeviceId(MainApp.fc.getDeviceID());
        MainApp.d4WRAc.setApp_ver(MainApp.fc.getAppversion());
        MainApp.d4WRAc.set_UUID(MainApp.fc.getUID());
        MainApp.d4WRAc.setfType(MainApp.WRAD4A);
        MainApp.d4WRAc.setD1SerialNo(String.valueOf(MainApp.dwraSerial_no));
        if (!MainApp.isAttitudeCheck) {
            sB9.put("cid401", bi.cid401a.isChecked() ? "1"
                    : bi.cid401b.isChecked() ? "2"
                    : "0");
        }
        sB9.put("cid40202", bi.cid40202.getText().toString());
        sB9.put("cid40203", bi.cid40203.getText().toString());
        sB9.put("cid40204", bi.cid40204.getText().toString());
        sB9.put("cid40205", bi.cid40205.getText().toString());
        MainApp.d4WRAc.setsD1(String.valueOf(sB9));

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
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD4A);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
