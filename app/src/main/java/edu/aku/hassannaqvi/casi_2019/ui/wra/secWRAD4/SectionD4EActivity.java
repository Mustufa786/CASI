package edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD4;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD4EBinding;

public class SectionD4EActivity extends AppCompatActivity {


    ActivitySectionD4EBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d4_e);
        bi.setCallback(this);
    }

    private void SaveDraft() throws JSONException {

        JSONObject sB13 = new JSONObject();

        sB13.put("cid409", binding.cid409a.isChecked() ? "1"
                : binding.cid409b.isChecked() ? "2"
                : binding.cid409c.isChecked() ? "3"
                : "0");
        sB13.put("cid410", binding.cid410a.isChecked() ? "1"
                : binding.cid410b.isChecked() ? "2"
                : binding.cid410c.isChecked() ? "3"
                : "0");
        sB13.put("cid411", binding.cid411a.isChecked() ? "1"
                : binding.cid411b.isChecked() ? "2"
                : binding.cid411c.isChecked() ? "3"
                : "0");
        sB13.put("cid412", binding.cid412a.isChecked() ? "1"
                : binding.cid412b.isChecked() ? "2"
                : binding.cid412c.isChecked() ? "3"
                : "0");
        sB13.put("cid413", binding.cid413a.isChecked() ? "1"
                : binding.cid413b.isChecked() ? "2"
                : binding.cid413c.isChecked() ? "3"
                : "0");
        sB13.put("cid414", binding.cid414a.isChecked() ? "1"
                : binding.cid414b.isChecked() ? "2"
                : binding.cid414c.isChecked() ? "3"
                : "0");
        sB13.put("cid415", binding.cid415a.isChecked() ? "1"
                : binding.cid415b.isChecked() ? "2"
                : binding.cid415c.isChecked() ? "3"
                : "0");

        MainApp.cc.setsB13(String.valueOf(sB13));

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
