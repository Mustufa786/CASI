package edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD4EBinding;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.MotherEndingActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionD4EActivity extends AppCompatActivity {


    ActivitySectionD4EBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d4_e);
        bi.setCallback(this);
        this.setTitle(getString(R.string.cid4h));
    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                if (SectionB1Activity.editWRAFlag) {
                    finish();
                    startActivity(new Intent(this, ViewMemberActivity.class)
                            .putExtra("flagEdit", false)
                            .putExtra("comingBack", true)
                            .putExtra("cluster", MainApp.mc.getCluster())
                            .putExtra("hhno", MainApp.mc.getHhno())
                    );
                } else {
                    startActivity(new Intent(this, MotherEndingActivity.class)
                            .putExtra("checkingFlag", true)
                            .putExtra("complete", true));
                }

            }
        }

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


    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD4E);
    }


    private void SaveDraft() throws JSONException {

//
        JSONObject sB13 = new JSONObject();

        sB13.put("cid409", bi.cid409a.isChecked() ? "1"
                : bi.cid409b.isChecked() ? "2"
                : bi.cid409c.isChecked() ? "3"
                : "0");
        sB13.put("cid410", bi.cid410a.isChecked() ? "1"
                : bi.cid410b.isChecked() ? "2"
                : bi.cid410c.isChecked() ? "3"
                : "0");
        sB13.put("cid411", bi.cid411a.isChecked() ? "1"
                : bi.cid411b.isChecked() ? "2"
                : bi.cid411c.isChecked() ? "3"
                : "0");
        sB13.put("cid412", bi.cid412a.isChecked() ? "1"
                : bi.cid412b.isChecked() ? "2"
                : bi.cid412c.isChecked() ? "3"
                : "0");
        sB13.put("cid413", bi.cid413a.isChecked() ? "1"
                : bi.cid413b.isChecked() ? "2"
                : bi.cid413c.isChecked() ? "3"
                : "0");
        sB13.put("cid414", bi.cid414a.isChecked() ? "1"
                : bi.cid414b.isChecked() ? "2"
                : bi.cid414c.isChecked() ? "3"
                : "0");
        sB13.put("cid415", bi.cid415a.isChecked() ? "1"
                : bi.cid415b.isChecked() ? "2"
                : bi.cid415c.isChecked() ? "3"
                : "0");
        sB13.put("cid501", bi.cid501a.isChecked() ? "1"
                : bi.cid501b.isChecked() ? "2"
                : bi.cid50198.isChecked() ? "98"
                : "0");
        sB13.put("cid502", bi.cid502a.isChecked() ? "1"
                : bi.cid502b.isChecked() ? "2"
                : bi.cid50296.isChecked() ? "96"
                : bi.cid50298.isChecked() ? "98"
                : "0");
        sB13.put("cid50296x", bi.cid50296x.getText().toString());

        MainApp.mc.setsB9(String.valueOf(sB13));

    }

    public void BtnEnd() {
        MainApp.endActivityMother(this, this, false);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
