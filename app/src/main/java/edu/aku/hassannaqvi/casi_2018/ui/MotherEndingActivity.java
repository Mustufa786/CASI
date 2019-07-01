package edu.aku.hassannaqvi.casi_2018.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivityMotherEndingBinding;
import edu.aku.hassannaqvi.casi_2018.validation.validatorClass;

public class MotherEndingActivity extends AppCompatActivity {

    private static final String TAG = MotherEndingActivity.class.getSimpleName();

    ActivityMotherEndingBinding binding;
    Boolean flagMotherChild = false;
    Boolean flagNAChild = false;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_mother_ending);
        binding.setCallback(this);

/*
        flagMotherChild = getIntent().getBooleanExtra("checkingFlag", false);
        if (flagMotherChild) {
            binding.lblheaderName.setText(SectionB1Activity.wraName.toUpperCase());
        } else {
*/
        binding.lblheaderName.setText(SectionB1Activity.wraName.toUpperCase());

        db = new DatabaseHelper(this);

        //}

        Boolean check = getIntent().getExtras().getBoolean("complete");

        if (check) {
            binding.istatusa.setEnabled(true);
            binding.istatusb.setEnabled(false);
            binding.istatusc.setEnabled(false);
            binding.istatusd.setEnabled(false);
            binding.istatuse.setEnabled(false);
            binding.istatus96.setEnabled(false);
        } else {
            binding.istatusa.setEnabled(false);
            binding.istatusb.setEnabled(true);
            binding.istatusc.setEnabled(true);
            binding.istatusd.setEnabled(true);
            binding.istatuse.setEnabled(true);
            binding.istatus96.setEnabled(true);
        }


        flagNAChild = SectionC1Activity.counterPerMom <= 0;


    }

    public void BtnEnd() {

        //Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {
            SaveDraft();
            if (UpdateDB()) {

//                finish();


                startActivity(new Intent(this, ViewMemberActivity.class).putExtra("activity", 5));


                /*if (SectionB1Activity.WRAcounter == MainApp.mwra.size()) {

                    if (MainApp.childNA.size() > 0) {
                        SectionC1Activity.isNA = true;
                        startActivity(new Intent(this, SectionC1Activity.class));
                    } else {
                        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));
                    }

                } else {
                    startActivity(new Intent(this, SectionB1Activity.class)
                            .putExtra("mwraFlag", true)
                            .putExtra("wraName", SectionB1Activity.wraName));
                }*/

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void SaveDraft() {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();


        MainApp.mc.setMstatus(binding.istatusa.isChecked() ? "1"
                : binding.istatusb.isChecked() ? "2"
                : binding.istatusc.isChecked() ? "3"
                : binding.istatusd.isChecked() ? "4"
                : binding.istatuse.isChecked() ? "5"
                : binding.istatus96.isChecked() ? "96"
                : "0");

        MainApp.mc.setMstatus88x(binding.istatus96x.getText().toString());

        // Set summary fields
        MainApp.sumc = MainApp.AddSummary(MainApp.fc, 2);

        //Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {

        int updcount = db.updateMotherEnding();
        if (updcount == 1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            return MainApp.UpdateSummary(this, db, 2);

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

        //return true;

    }

    private boolean formValidation() {
        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (!validatorClass.EmptyRadioButton(this, binding.istatus, binding.istatusa, getString(R.string.istatus))) {
            return false;
        }

        if (binding.istatus96.isChecked()) {

            if (binding.istatus96x.getText().toString().isEmpty()) {
                Toast.makeText(this, "ERROR(empty): " + getString(R.string.other), Toast.LENGTH_SHORT).show();
                binding.istatus96x.setError("This data is Required!");    // Set Error on last radio button
                Log.i(TAG, "istatus96x: This data is Required!");
                return false;
            } else {
                binding.istatus96x.setError(null);
            }

        }


        return true;
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MotherEndingActivity.this);
        alertDialogBuilder
                .setMessage("Are you sure to go BACK??")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                MotherEndingActivity.super.onBackPressed();
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


}
