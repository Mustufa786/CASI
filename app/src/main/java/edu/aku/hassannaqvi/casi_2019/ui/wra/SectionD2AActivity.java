package edu.aku.hassannaqvi.casi_2019.ui.wra;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD2ABinding;
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
            bi.fldGrpSectionD2A.setVisibility(View.GONE);
        } else {
            bi.fldGrpAttitudeCheck.setVisibility(View.GONE);
            bi.fldGrpSectionD2A.setVisibility(View.VISIBLE);
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
                if (!MainApp.isAttitudeCheck)
                    startActivity(new Intent(this, SectionD2BActivity.class).putExtra("fType", "d2b"));
                else
                    startActivity(new Intent(this, SectionD3AActivity.class)
                            .putExtra("fType", MainApp.dWraType));
                MainApp.isAttitudeCheck = false;
                finish();


            } else {
                Toast.makeText(this, "Error in updating DB", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private boolean UpdateDB() {

        return true;
    }

    private void SaveDraft() throws JSONException {

    }

    private boolean formValidation() {

        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD2A);
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
