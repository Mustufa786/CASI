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
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivityChildEndingBinding;
import edu.aku.hassannaqvi.casi_2018.validation.validatorClass;

public class ChildEndingActivity extends AppCompatActivity {

    private static final String TAG = ChildEndingActivity.class.getSimpleName();

    ActivityChildEndingBinding binding;
    Boolean flagMotherChild = false;
    Boolean flagNAChild = false;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_child_ending);
        binding.setCallback(this);

        binding.lblheaderName.setText(SectionC1Activity.selectedChildName.toUpperCase());

        db = new DatabaseHelper(this);

        Boolean check = getIntent().getExtras().getBoolean("complete");

        if (check) {
            binding.istatusa.setEnabled(true);
            binding.istatusb.setEnabled(false);
            binding.istatusc.setEnabled(false);
            binding.istatusd.setEnabled(false);
            binding.istatuse.setEnabled(false);
            binding.istatusf.setEnabled(false);
            binding.istatus96.setEnabled(false);
        } else {

            if (getIntent().getBooleanExtra("childINEligibile", false)) {
                binding.istatusa.setEnabled(false);
                binding.istatusb.setEnabled(false);
                binding.istatusc.setEnabled(false);
                binding.istatusd.setEnabled(false);
                binding.istatuse.setEnabled(false);
                binding.istatusf.setEnabled(true);
                binding.istatus96.setEnabled(false);
            } else {
                binding.istatusa.setEnabled(false);
                binding.istatusb.setEnabled(true);
                binding.istatusc.setEnabled(true);
                binding.istatusd.setEnabled(true);
                binding.istatuse.setEnabled(true);
                binding.istatusf.setEnabled(false);
                binding.istatus96.setEnabled(true);
            }
        }

//        flagNAChild = SectionC1Activity.counterPerMom <= 0;

    }

    public void BtnEnd() {

        //Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {
            SaveDraft();
            if (UpdateDB()) {

//                finish();

                SectionC1Activity.childU5.remove(SectionC1Activity.selectedChildName);
                //MainApp.childUnder5_Del.remove(MainApp.cc);
                for (FamilyMembersContract fmc : MainApp.childUnder5_Del) {
                    if (MainApp.cc.getC1SerialNo().equals(fmc.getSerialNo())) {
                        MainApp.childUnder5_Del.remove(fmc);
                        break;
                    }
                }

                startActivity(new Intent(this, ViewMemberActivity.class).putExtra("activity", 4));


            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();


        MainApp.cc.setCstatus(binding.istatusa.isChecked() ? "1"
                : binding.istatusb.isChecked() ? "2"
                : binding.istatusc.isChecked() ? "3"
                : binding.istatusd.isChecked() ? "4"
                : binding.istatuse.isChecked() ? "5"
                : binding.istatusf.isChecked() ? "6"
                : binding.istatus96.isChecked() ? "96"
                : "0");

        MainApp.cc.setCstatus88x(binding.istatus96x.getText().toString());

        // Set summary fields
        MainApp.sumc = MainApp.AddSummary(MainApp.fc, 3);

        //Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {
        int updcount = db.updateChildEnding();
        if (updcount == 1) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return MainApp.UpdateSummary(this, db, 3);
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
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
                ChildEndingActivity.this);
        alertDialogBuilder
                .setMessage("Are you sure to go BACK??")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                ChildEndingActivity.super.onBackPressed();
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
