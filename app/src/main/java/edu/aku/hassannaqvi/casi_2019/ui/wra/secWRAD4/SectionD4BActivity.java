package edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD4BBinding;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionD4BActivity extends AppCompatActivity {


    ActivitySectionD4BBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d4_b);
        bi.setCallback(this);

        MainApp.dWraType = getIntent().getStringExtra("fType");
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
                startActivity(new Intent(this, SectionD4CActivity.class)
                        .putExtra("fType", "d4c"));
                MainApp.dwraSerial_no = 0;
            }
        }

    }

    public void BtnAddMore() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SectionD4BActivity.this);
        alertDialogBuilder
                .setMessage("Are you sure to add new Entry?")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                finish();
                                if (UpdateDB()) {
                                    startActivity(new Intent(SectionD4BActivity.this, SectionD4BActivity.class)
                                            .putExtra("fType", "d4b"));
                                }
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }

    private void SaveDraft() throws JSONException {


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

    public void BtnEnd() {
        MainApp.endActivity(this, this);
    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionD4B);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
