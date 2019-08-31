package edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD3ABinding;
import edu.aku.hassannaqvi.casi_2019.other.JsonUtils;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionD3AActivity extends AppCompatActivity {

    ActivitySectionD3ABinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d3_a);
        bi.setCallback(this);


        if (MainApp.isAttitudeCheck) {
            bi.fldGrpAttitudeCheck.setVisibility(View.VISIBLE);
            bi.fldGrpCheck.setVisibility(View.GONE);
        } else {
            bi.fldGrpAttitudeCheck.setVisibility(View.GONE);
            bi.fldGrpCheck.setVisibility(View.VISIBLE);
        }

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
                if (bi.cid303a.isChecked()) {
                    if (!MainApp.isAttitudeCheck)
                        route = new Intent(this, SectionD3BActivity.class);
                    else
                        route = new Intent(this, SectionD4AActivity.class);
                    MainApp.isAttitudeCheck = false;
                    MainApp.dwraSerial_no = 1;
                } else {
                    if (bi.cid303.getVisibility() == View.VISIBLE) {
                        route = new Intent(this, SectionD3AActivity.class);
                        MainApp.isAttitudeCheck = true;
                    } else {
                        route = new Intent(this, SectionD4AActivity.class);
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

    private void SaveDraft() throws JSONException {

        JSONObject sB8 = new JSONObject();
        if (!MainApp.isAttitudeCheck) {
            sB8.put("cid301", bi.cid301a.isChecked() ? "1"
                    : bi.cid301b.isChecked() ? "2"
                    : bi.cid301c.isChecked() ? "3"
                    : bi.cid30198.isChecked() ? "98"
                    : "0");
            sB8.put("cid302", bi.cid302a.isChecked() ? "1"
                    : bi.cid302b.isChecked() ? "2"
                    : bi.cid302c.isChecked() ? "3"
                    : bi.cid30298.isChecked() ? "98"
                    : "0");
            sB8.put("cid303", bi.cid303a.isChecked() ? "1"
                    : bi.cid303b.isChecked() ? "2"
                    : "0");
            MainApp.mc.setsB8(String.valueOf(sB8));
        } else {
            sB8.put("cid305", bi.cid305a.isChecked() ? "1"
                    : bi.cid305b.isChecked() ? "2"
                    : bi.cid305c.isChecked() ? "3"
                    : "0");
            sB8.put("cid306", bi.cid306a.isChecked() ? "1"
                    : bi.cid306b.isChecked() ? "2"
                    : bi.cid306c.isChecked() ? "3"
                    : "0");
            sB8.put("cid307", bi.cid307a.isChecked() ? "1"
                    : bi.cid307b.isChecked() ? "2"
                    : bi.cid307c.isChecked() ? "3"
                    : "0");
            sB8.put("cid308", bi.cid308a.isChecked() ? "1"
                    : bi.cid308b.isChecked() ? "2"
                    : bi.cid308c.isChecked() ? "3"
                    : "0");
            sB8.put("cid309", bi.cid309a.isChecked() ? "1"
                    : bi.cid309b.isChecked() ? "2"
                    : bi.cid309c.isChecked() ? "3"
                    : "0");
            sB8.put("cid310", bi.cid310a.isChecked() ? "1"
                    : bi.cid310b.isChecked() ? "2"
                    : bi.cid310c.isChecked() ? "3"
                    : "0");
            sB8.put("cid311", bi.cid311a.isChecked() ? "1"
                    : bi.cid311b.isChecked() ? "2"
                    : bi.cid311c.isChecked() ? "3"
                    : "0");
            sB8.put("cid312", bi.cid312a.isChecked() ? "1"
                    : bi.cid312b.isChecked() ? "2"
                    : bi.cid312c.isChecked() ? "3"
                    : "0");
            JSONObject merged = JsonUtils.mergeJSONObjects(new JSONObject(MainApp.mc.getsB8()), sB8);
            MainApp.mc.setsB8(String.valueOf(merged));
        }


    }


    private boolean formValidation() {

        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD3A);
    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSB8();

        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

    public void BtnEnd() {
        MainApp.endActivityMother(this, this, false);
    }

}
