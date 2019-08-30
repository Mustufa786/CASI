package edu.aku.hassannaqvi.casi_2019.ui.household;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD6Binding;

public class SectionD6Activity extends AppCompatActivity {

    ActivitySectionD6Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_d6);
    }


    private void SaveDraft() throws JSONException {

        JSONObject sS1 = new JSONObject();

        sS1.put("cid601", binding.cid601a.isChecked() ? "1" : binding.cid601b.isChecked() ? "2" : binding.cid601c.isChecked() ? "3" : "0");

        sS1.put("cid602", binding.cid602a.isChecked() ? "1"
                : binding.cid602b.isChecked() ? "2"
                : binding.cid602c.isChecked() ? "3"
                : binding.cid602d.isChecked() ? "4"
                : "0");

        sS1.put("cid603", binding.cid603a.isChecked() ? "1"
                : binding.cid603b.isChecked() ? "2"
                : binding.cid603c.isChecked() ? "3"
                : binding.cid603d.isChecked() ? "4"
                : "0");

        sS1.put("cid604", binding.cid604a.isChecked() ? "1" : binding.cid604b.isChecked() ? "2" : binding.cid604c.isChecked() ? "3" : "0");

        sS1.put("cid605", binding.cid605a.isChecked() ? "1"
                : binding.cid605b.isChecked() ? "2"
                : binding.cid605c.isChecked() ? "3"
                : binding.cid605d.isChecked() ? "4"
                : "0");

        sS1.put("cid606", binding.cid606a.isChecked() ? "1"
                : binding.cid606b.isChecked() ? "2"
                : binding.cid606c.isChecked() ? "3"
                : binding.cid606d.isChecked() ? "4"
                : binding.cid606e.isChecked() ? "5"
                : "0");

        sS1.put("cid607", binding.cid607a.isChecked() ? "1" : binding.cid607b.isChecked() ? "2" : binding.cid607c.isChecked() ? "3" : "0");

        sS1.put("cid608", binding.cid608a.isChecked() ? "1"
                : binding.cid608b.isChecked() ? "2"
                : binding.cid608c.isChecked() ? "3"
                : binding.cid608d.isChecked() ? "4"
                : "0");

        sS1.put("cid609", binding.cid609a.isChecked() ? "1" : binding.cid609b.isChecked() ? "2" : binding.cid609c.isChecked() ? "3" : "0");

        sS1.put("cid610", binding.cid610a.isChecked() ? "1" : binding.cid610b.isChecked() ? "2" : binding.cid610c.isChecked() ? "3" : "0");
//
        sS1.put("cid611", binding.cid611.getText().toString());

        sS1.put("cid612", binding.cid612a.isChecked() ? "1" : binding.cid612b.isChecked() ? "2" : binding.cid612c.isChecked() ? "3" : "0");

        sS1.put("cid613", binding.cid613.getText().toString());

        sS1.put("cid614", binding.cid614a.isChecked() ? "1" : binding.cid614b.isChecked() ? "2" : binding.cid614c.isChecked() ? "3" : "0");

        sS1.put("cid615", binding.cid615a.isChecked() ? "1" : binding.cid615b.isChecked() ? "2" : binding.cid615c.isChecked() ? "3" : "0");

        sS1.put("cid616", binding.cid616a.isChecked() ? "1" : binding.cid616b.isChecked() ? "2" : binding.cid616c.isChecked() ? "3" : "0");

        sS1.put("cid617", binding.cid617a.isChecked() ? "1" : binding.cid617b.isChecked() ? "2" : binding.cid617c.isChecked() ? "3" : "0");

        sS1.put("cid618", binding.cid618a.isChecked() ? "1" : binding.cid618b.isChecked() ? "2" : binding.cid618c.isChecked() ? "3" : "0");


        MainApp.cc.setsC1(String.valueOf(sS1));

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }


}
