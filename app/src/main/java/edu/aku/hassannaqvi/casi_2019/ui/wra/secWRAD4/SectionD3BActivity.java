package edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD4;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.D4WRAContract;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD3BBinding;

public class SectionD3BActivity extends AppCompatActivity {

    ActivitySectionD3BBinding bi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d3_b);
        bi.setCallback(this);
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

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
