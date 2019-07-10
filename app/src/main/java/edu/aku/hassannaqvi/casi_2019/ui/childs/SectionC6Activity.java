package edu.aku.hassannaqvi.casi_2019.ui.childs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionC6Binding;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionC6Activity extends AppCompatActivity {

    ActivitySectionC6Binding bi;

    Boolean backPressed = false;

    FamilyMembersContract selectedChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c6);
        bi.setCallback(this);

        //Get Intent
        selectedChild = (FamilyMembersContract) getIntent().getSerializableExtra("selectedChild");
    }

    public void BtnContinue() {

//        Validation Boolean
        MainApp.validateFlag = true;

        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

//                finish();
                backPressed = true;
                startActivity(new Intent(this, SectionC3Activity.class)
                        .putExtra("selectedChild", selectedChild));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void BtnEnd() {
        if (SectionB1Activity.editWRAFlag) {
            finish();
            startActivity(new Intent(this, ViewMemberActivity.class)
                    .putExtra("flagEdit", false)
                    .putExtra("comingBack", true)
                    .putExtra("cluster", MainApp.mc.getCluster())
                    .putExtra("hhno", MainApp.mc.getHhno())
            );
        } else {
            MainApp.endActivityMother(this, this, false);
        }
    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionB6);
    }

    private void SaveDraft() throws JSONException {

        JSONObject sB6 = new JSONObject();

        if (backPressed) {
            sB6.put("updatedate_cic6", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        sB6.put("cic601", bi.ciw501.getText().toString());
        sB6.put("cic602", bi.ciw502a.isChecked() ? "1"
                : bi.ciw502b.isChecked() ? "2"
                : bi.ciw502c.isChecked() ? "3"
                : "0");

        sB6.put("cic6030101", bi.ciw5030101.getText().toString());
        sB6.put("cic6030102", bi.ciw5030102a.isChecked() ? "1"
                : bi.ciw5030102b.isChecked() ? "2"
                : bi.ciw5030102c.isChecked() ? "3"
                : bi.ciw5030102d.isChecked() ? "4"
                : bi.ciw5030102e.isChecked() ? "5"
                : bi.ciw5030102f.isChecked() ? "6"
                : bi.ciw5030102g.isChecked() ? "7"
                : bi.ciw5030102h.isChecked() ? "8"
                : bi.ciw5030102i.isChecked() ? "9"
                : "0");
        sB6.put("cic6030103", bi.ciw5030103a.isChecked() ? "1"
                : bi.ciw5030103b.isChecked() ? "2"
                : "0");


        sB6.put("cic6030201", bi.ciw5030201.getText().toString());
        sB6.put("cic6030202", bi.ciw5030202a.isChecked() ? "1"
                : bi.ciw5030202b.isChecked() ? "2"
                : bi.ciw5030202c.isChecked() ? "3"
                : bi.ciw5030202d.isChecked() ? "4"
                : bi.ciw5030202e.isChecked() ? "5"
                : bi.ciw5030202f.isChecked() ? "6"
                : bi.ciw5030202g.isChecked() ? "7"
                : bi.ciw5030202h.isChecked() ? "8"
                : bi.ciw5030202i.isChecked() ? "9"
                : "0");
        sB6.put("cic6030203", bi.ciw5030203a.isChecked() ? "1"
                : bi.ciw5030203b.isChecked() ? "2"
                : "0");
        sB6.put("cic6030301", bi.ciw5030301.getText().toString());
        sB6.put("cic6030302", bi.ciw5030302a.isChecked() ? "1"
                : bi.ciw5030302b.isChecked() ? "2"
                : bi.ciw5030302c.isChecked() ? "3"
                : bi.ciw5030302d.isChecked() ? "4"
                : bi.ciw5030302e.isChecked() ? "5"
                : bi.ciw5030302f.isChecked() ? "6"
                : bi.ciw5030302g.isChecked() ? "7"
                : bi.ciw5030302h.isChecked() ? "8"
                : bi.ciw5030302i.isChecked() ? "9"
                : "0");
        sB6.put("cic6030303", bi.ciw5030303a.isChecked() ? "1"
                : bi.ciw5030303b.isChecked() ? "2"
                : "0");


        sB6.put("cic6030401", bi.ciw5030401.getText().toString());
        sB6.put("cic6030402", bi.ciw5030402a.isChecked() ? "1"
                : bi.ciw5030402b.isChecked() ? "2"
                : bi.ciw5030402c.isChecked() ? "3"
                : bi.ciw5030402d.isChecked() ? "4"
                : bi.ciw5030402e.isChecked() ? "5"
                : bi.ciw5030402f.isChecked() ? "6"
                : bi.ciw5030402g.isChecked() ? "7"
                : bi.ciw5030402h.isChecked() ? "8"
                : bi.ciw5030402i.isChecked() ? "9"
                : "0");
        sB6.put("cic6030403", bi.ciw5030403a.isChecked() ? "1"
                : bi.ciw5030403b.isChecked() ? "2"
                : "0");


        sB6.put("cic6030501", bi.ciw5030501.getText().toString());
        sB6.put("cic6030502", bi.ciw5030502a.isChecked() ? "1"
                : bi.ciw5030502b.isChecked() ? "2"
                : bi.ciw5030502c.isChecked() ? "3"
                : bi.ciw5030502d.isChecked() ? "4"
                : bi.ciw5030502e.isChecked() ? "5"
                : bi.ciw5030502f.isChecked() ? "6"
                : bi.ciw5030502g.isChecked() ? "7"
                : bi.ciw5030502h.isChecked() ? "8"
                : bi.ciw5030502i.isChecked() ? "9"
                : "0");
        sB6.put("cic6030503", bi.ciw5030503a.isChecked() ? "1"
                : bi.ciw5030503b.isChecked() ? "2"
                : "0");


        sB6.put("cic6030601", bi.ciw5030601.getText().toString());
        sB6.put("cic6030602", bi.ciw5030602a.isChecked() ? "1"
                : bi.ciw5030602b.isChecked() ? "2"
                : bi.ciw5030602c.isChecked() ? "3"
                : bi.ciw5030602d.isChecked() ? "4"
                : bi.ciw5030602e.isChecked() ? "5"
                : bi.ciw5030602f.isChecked() ? "6"
                : bi.ciw5030602g.isChecked() ? "7"
                : bi.ciw5030602h.isChecked() ? "8"
                : bi.ciw5030602i.isChecked() ? "9"
                : "0");
        sB6.put("cic6030603", bi.ciw5030603a.isChecked() ? "1"
                : bi.ciw5030603b.isChecked() ? "2"
                : "0");

        sB6.put("cic6030701", bi.ciw5030701.getText().toString());
        sB6.put("cic6030702", bi.ciw5030702a.isChecked() ? "1"
                : bi.ciw5030702b.isChecked() ? "2"
                : bi.ciw5030702c.isChecked() ? "3"
                : bi.ciw5030702d.isChecked() ? "4"
                : bi.ciw5030702e.isChecked() ? "5"
                : bi.ciw5030702f.isChecked() ? "6"
                : bi.ciw5030702g.isChecked() ? "7"
                : bi.ciw5030702h.isChecked() ? "8"
                : bi.ciw5030702i.isChecked() ? "9"
                : "0");
        sB6.put("cic6030703", bi.ciw5030703a.isChecked() ? "1"
                : bi.ciw5030703b.isChecked() ? "2"
                : "0");

        sB6.put("cic6030801", bi.ciw5030801.getText().toString());
        sB6.put("cic6030802", bi.ciw5030802a.isChecked() ? "1"
                : bi.ciw5030802b.isChecked() ? "2"
                : bi.ciw5030802c.isChecked() ? "3"
                : bi.ciw5030802d.isChecked() ? "4"
                : bi.ciw5030802e.isChecked() ? "5"
                : bi.ciw5030802f.isChecked() ? "6"
                : bi.ciw5030802g.isChecked() ? "7"
                : bi.ciw5030802h.isChecked() ? "8"
                : bi.ciw5030802i.isChecked() ? "9"
                : "0");
        sB6.put("cic6030803", bi.ciw5030803a.isChecked() ? "1"
                : bi.ciw5030803b.isChecked() ? "2"
                : "0");

        MainApp.cc.setsC6(String.valueOf(sB6));

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSC6();

        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
//
//
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

}