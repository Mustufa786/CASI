package edu.aku.hassannaqvi.casi_2019.ui.household;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.D6AdolesContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD6Binding;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionD6Activity extends AppCompatActivity {

    ActivitySectionD6Binding bi;
    FamilyMembersContract fmc;
    Map<String, FamilyMembersContract> respMap;
    ArrayList<String> respName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d6);
        bi.setCallback(this);

        fmc = (FamilyMembersContract) getIntent().getSerializableExtra("adolescent");

        respName = new ArrayList<>();
        respName.add("....");
        respMap = new HashMap<>();

        if (fmc.getAgeInYear().equals("15")) {
            bi.fldGrpresp.setVisibility(View.VISIBLE);

            for (FamilyMembersContract fmc : MainApp.respList) {
                respName.add(fmc.getName() + "-" + fmc.getSerialNo());
                respMap.put(fmc.getName() + "-" + fmc.getSerialNo(), fmc);
            }

            bi.resp.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, respName));

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
                finish();
                startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
            }
        }

    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);
        long updcount = db.addD6Adoles(MainApp.d6Adolesc);
        if (updcount != 0) {
            MainApp.d6Adolesc.set_ID(String.valueOf(updcount));
            MainApp.d6Adolesc.set_UID(
                    (MainApp.d6Adolesc.getDeviceId() + MainApp.d6Adolesc.get_ID()));
            db.updateDWraID();
            return true;
        } else {
            Toast.makeText(this, "Error in updating DB", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void SaveDraft() throws JSONException {
        MainApp.d6Adolesc = new D6AdolesContract();
        MainApp.d6Adolesc.setDevicetagID(MainApp.fc.getDevicetagID());
        MainApp.d6Adolesc.setFormDate(MainApp.fc.getFormDate());
        MainApp.d6Adolesc.setUser(MainApp.fc.getUser());
        MainApp.d6Adolesc.setDeviceId(MainApp.fc.getDeviceID());
        MainApp.d6Adolesc.setApp_ver(MainApp.fc.getAppversion());
        MainApp.d6Adolesc.set_UUID(MainApp.fc.getUID());
        MainApp.d6Adolesc.setFMUID(fmc.get_UID());
        MainApp.d6Adolesc.setFmSerialNo(fmc.getSerialNo());

        JSONObject sD6 = new JSONObject();

        sD6.put("respName", bi.resp.getSelectedItem().toString());
        sD6.put("resp_lno", respMap.get(bi.resp.getSelectedItem().toString()).getSerialNo());
        sD6.put("resp_uid", respMap.get(bi.resp.getSelectedItem().toString()).get_UID());

        sD6.put("cid601", bi.cid601a.isChecked() ? "1"
                : bi.cid601b.isChecked() ? "2"
                : bi.cid601c.isChecked() ? "3"
                : "0");
        sD6.put("cid602", bi.cid602a.isChecked() ? "1"
                : bi.cid602b.isChecked() ? "2"
                : bi.cid602c.isChecked() ? "3"
                : bi.cid602d.isChecked() ? "4"
                : "0");
        sD6.put("cid603", bi.cid603a.isChecked() ? "1"
                : bi.cid603b.isChecked() ? "2"
                : bi.cid603c.isChecked() ? "3"
                : bi.cid603d.isChecked() ? "4"
                : "0");
        sD6.put("cid604", bi.cid604a.isChecked() ? "1"
                : bi.cid604b.isChecked() ? "2"
                : bi.cid604c.isChecked() ? "3"
                : "0");
        sD6.put("cid605", bi.cid605a.isChecked() ? "1"
                : bi.cid605b.isChecked() ? "2"
                : bi.cid605c.isChecked() ? "3"
                : bi.cid605d.isChecked() ? "4"
                : "0");
        sD6.put("cid606", bi.cid606a.isChecked() ? "1"
                : bi.cid606b.isChecked() ? "2"
                : bi.cid606c.isChecked() ? "3"
                : bi.cid606d.isChecked() ? "4"
                : bi.cid606e.isChecked() ? "5"
                : "0");
        sD6.put("cid607", bi.cid607a.isChecked() ? "1"
                : bi.cid607b.isChecked() ? "2"
                : bi.cid607c.isChecked() ? "3"
                : "0");
        sD6.put("cid608", bi.cid608a.isChecked() ? "1"
                : bi.cid608b.isChecked() ? "2"
                : bi.cid608c.isChecked() ? "3"
                : bi.cid608d.isChecked() ? "4"
                : "0");
        sD6.put("cid609", bi.cid609a.isChecked() ? "1"
                : bi.cid609b.isChecked() ? "2"
                : bi.cid609c.isChecked() ? "3"
                : "0");
        sD6.put("cid610", bi.cid610a.isChecked() ? "1"
                : bi.cid610b.isChecked() ? "2"
                : bi.cid610c.isChecked() ? "3"
                : "0");
        sD6.put("cid611", bi.cid611.getText().toString());
        sD6.put("cid612", bi.cid612a.isChecked() ? "1"
                : bi.cid612b.isChecked() ? "2"
                : bi.cid612c.isChecked() ? "3"
                : "0");
        sD6.put("cid613", bi.cid613.getText().toString());
        sD6.put("cid614", bi.cid614a.isChecked() ? "1"
                : bi.cid614b.isChecked() ? "2"
                : bi.cid614c.isChecked() ? "3"
                : "0");
        sD6.put("cid615", bi.cid615a.isChecked() ? "1"
                : bi.cid615b.isChecked() ? "2"
                : bi.cid615c.isChecked() ? "3"
                : "0");
        sD6.put("cid616", bi.cid616a.isChecked() ? "1"
                : bi.cid616b.isChecked() ? "2"
                : bi.cid616c.isChecked() ? "3"
                : "0");
        sD6.put("cid617", bi.cid617a.isChecked() ? "1"
                : bi.cid617b.isChecked() ? "2"
                : bi.cid617c.isChecked() ? "3"
                : "0");
        sD6.put("cid618", bi.cid618a.isChecked() ? "1"
                : bi.cid618b.isChecked() ? "2"
                : bi.cid618c.isChecked() ? "3"
                : "0");

        MainApp.d6Adolesc.setsD6(String.valueOf(sD6));

    }

    public void BtnEnd() {
        MainApp.endActivityMother(this, this, false);
    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD6);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }


}
