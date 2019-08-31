package edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD;

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
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD2ABinding;
import edu.aku.hassannaqvi.casi_2019.other.JsonUtils;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionD2AActivity extends AppCompatActivity {


    ActivitySectionD2ABinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d2_a);
        bi.setCallback(this);


        if (MainApp.isAttitudeCheck) {
            bi.fldGrpAttitudeCheck.setVisibility(View.VISIBLE);
            bi.fldGrpCheck.setVisibility(View.GONE);
        } else {
            bi.fldGrpAttitudeCheck.setVisibility(View.GONE);
            bi.fldGrpCheck.setVisibility(View.VISIBLE);

        }

        bi.cid202.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != bi.cid202a.getId()) {
                    ClearClass.ClearAllFields(bi.fldGrpcid203, null);
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
                Intent route;
                if (bi.cid204a.isChecked()) {
                    if (!MainApp.isAttitudeCheck)
                        route = new Intent(this, SectionD2BActivity.class);
                    else
                        route = new Intent(this, SectionD3AActivity.class);
                    MainApp.isAttitudeCheck = false;
                    MainApp.dwraSerial_no = 1;
                } else {
                    if (bi.fldGrpCheck.getVisibility() == View.VISIBLE) {
                        route = new Intent(this, SectionD2AActivity.class);
                        MainApp.isAttitudeCheck = true;
                    } else {
                        route = new Intent(this, SectionD3AActivity.class);
                        MainApp.isAttitudeCheck = false;
                        MainApp.dwraSerial_no = 1;
                    }
                }

                finish();
                startActivity(route);

            } else {
                Toast.makeText(this, "Error in updating DB", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSB7();

        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject sB7 = new JSONObject();
        if (!MainApp.isAttitudeCheck) {
            sB7.put("cid201", bi.cid201a.isChecked() ? "1"
                    : bi.cid201b.isChecked() ? "2"
                    : bi.cid201c.isChecked() ? "3"
                    : "0");
            sB7.put("cid202", bi.cid202a.isChecked() ? "1"
                    : bi.cid202b.isChecked() ? "2"
                    : bi.cid202c.isChecked() ? "3"
                    : bi.cid20298.isChecked() ? "98"
                    : "0");
            sB7.put("cid203", bi.cid203a.isChecked() ? "1"
                    : bi.cid203b.isChecked() ? "2"
                    : bi.cid203c.isChecked() ? "3"
                    : bi.cid20398.isChecked() ? "98"
                    : "0");
            sB7.put("cid204", bi.cid204a.isChecked() ? "1"
                    : bi.cid204b.isChecked() ? "2"
                    : "0");
            MainApp.mc.setsB7(String.valueOf(sB7));
        } else {
            sB7.put("cid206", bi.cid206a.isChecked() ? "1"
                    : bi.cid206b.isChecked() ? "2"
                    : bi.cid206c.isChecked() ? "3"
                    : "0");
            sB7.put("cid207", bi.cid207a.isChecked() ? "1"
                    : bi.cid207b.isChecked() ? "2"
                    : bi.cid207c.isChecked() ? "3"
                    : "0");
            JSONObject merged = JsonUtils.mergeJSONObjects(new JSONObject(MainApp.mc.getsB7()), sB7);
            MainApp.mc.setsB7(String.valueOf(merged));
        }


    }

    private boolean formValidation() {

        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD2A);
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

    public void BtnEnd() {
        MainApp.endActivityMother(this, this, false);
    }

}
