package edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD4DBinding;
import edu.aku.hassannaqvi.casi_2019.other.JsonUtils;

public class SectionD4DActivity extends AppCompatActivity {


    ActivitySectionD4DBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d4_d);
        bi.setCallback(this);
    }

    private void SaveDraft() throws JSONException {

        JSONObject sB12 = new JSONObject();
        if (!MainApp.isAttitudeCheck) {
            sB12.put("cid407", bi.cid407a.isChecked() ? "1"
                    : bi.cid407b.isChecked() ? "2"
                    : "0");
            MainApp.mc.setsB12(String.valueOf(sB12));
        } else {
            sB12.put("cid40802", bi.cid40802.getText().toString());
            sB12.put("cid40803", bi.cid40803.getText().toString());
            sB12.put("cid40804", bi.cid40804.getText().toString());
            sB12.put("cid40805", bi.cid40805.getText().toString());
            JSONObject merged = JsonUtils.mergeJSONObjects(new JSONObject(MainApp.mc.getsB12()), sB12);
            MainApp.mc.setsB12(String.valueOf(merged));
        }


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
