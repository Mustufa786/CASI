package edu.aku.hassannaqvi.casi_2018.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONC5ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.ChildContract;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionC5Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.validation.validatorClass;

public class SectionC5Activity extends Menu2Activity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private final long DELAY = 1000;
    ActivitySectionC5Binding bi;
    FamilyMembersContract selectedChild;
    DatabaseHelper db;
    Boolean backPressed = false;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_c5);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c5);
        bi.setCallback(this);

        this.setTitle(getResources().getString(R.string.nc5heading));

        if (SectionC1Activity.editChildFlag) {
            bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                    + "\n\n" + SectionC1Activity.editMotherName + " : " + getString(R.string.nh212a));
        } else {
            if (!SectionC1Activity.isNA) {
                bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionB1Activity.wraName + " : " + getString(R.string.nh212a));
            } else {
                bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionC1Activity.careTaker + " : " + getString(R.string.nh113));
            }
        }

        db = new DatabaseHelper(this);

        //Get Intent
        selectedChild = (FamilyMembersContract) getIntent().getSerializableExtra("selectedChild");


        bi.nc501.setOnCheckedChangeListener(this);
        bi.nc502.setOnCheckedChangeListener(this);
        bi.nc503.setOnCheckedChangeListener(this);
        bi.nc504.setOnCheckedChangeListener(this);
        bi.nc505.setOnCheckedChangeListener(this);
        bi.nc506.setOnCheckedChangeListener(this);

//        Validation Boolean
        MainApp.validateFlag = false;

        autoPopulateFields();

    }

    private void autoPopulateFields() {

        ChildContract childContract = db.getsC5();

        if (!childContract.getsC5().equals("")) {

            JSONC5ModelClass jsonC5 = JSONUtilClass.getModelFromJSON(childContract.getsC5(), JSONC5ModelClass.class);

            if (!jsonC5.getnc501().equals("0")) {
                bi.nc501.check(
                        jsonC5.getnc501().equals("1") ? bi.nc501a.getId()
                                : jsonC5.getnc501().equals("2") ? bi.nc501b.getId()
                                : jsonC5.getnc501().equals("3") ? bi.nc501c.getId()
                                : bi.nc501d.getId()
                );
            }
            if (!jsonC5.getnc502().equals("0")) {
                bi.nc502.check(
                        jsonC5.getnc502().equals("1") ? bi.nc502a.getId()
                                : jsonC5.getnc502().equals("2") ? bi.nc502b.getId()
                                : jsonC5.getnc502().equals("3") ? bi.nc502c.getId()
                                : bi.nc502d.getId()
                );
            }
            if (!jsonC5.getnc503().equals("0")) {
                bi.nc503.check(
                        jsonC5.getnc503().equals("1") ? bi.nc503a.getId()
                                : jsonC5.getnc503().equals("2") ? bi.nc503b.getId()
                                : jsonC5.getnc503().equals("3") ? bi.nc503c.getId()
                                : bi.nc503d.getId()
                );
            }
            if (!jsonC5.getnc504().equals("0")) {
                bi.nc504.check(
                        jsonC5.getnc504().equals("1") ? bi.nc504a.getId()
                                : jsonC5.getnc504().equals("2") ? bi.nc504b.getId()
                                : jsonC5.getnc504().equals("3") ? bi.nc504c.getId()
                                : bi.nc504d.getId()
                );
            }
            if (!jsonC5.getnc505().equals("0")) {
                bi.nc505.check(
                        jsonC5.getnc505().equals("1") ? bi.nc505a.getId()
                                : jsonC5.getnc505().equals("2") ? bi.nc505b.getId()
                                : jsonC5.getnc505().equals("3") ? bi.nc505c.getId()
                                : bi.nc505d.getId()
                );
            }
            if (!jsonC5.getnc506().equals("0")) {
                bi.nc506.check(
                        jsonC5.getnc506().equals("1") ? bi.nc506a.getId()
                                : jsonC5.getnc506().equals("2") ? bi.nc506b.getId()
                                : jsonC5.getnc506().equals("3") ? bi.nc506c.getId()
                                : bi.nc506d.getId()
                );
            }

        }

    }

    public void BtnContinue() {

//        Validation Boolean
        MainApp.validateFlag = true;

        //Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                //Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();

//                finish();
                backPressed = true;

                //MainApp.endActivityMotherChild(this, this, false, true);
                if (SectionC1Activity.editChildFlag) {
                    finish();
                    startActivity(new Intent(this, ViewMemberActivity.class)
                            .putExtra("flagEdit", false)
                            .putExtra("comingBack", true)
                            .putExtra("cluster", MainApp.cc.getClusterno())
                            .putExtra("hhno", MainApp.cc.getHhno())
                    );
                } else {
                    startActivity(new Intent(this, ChildEndingActivity.class)
                            //.putExtra("checkingFlag", false)
                            .putExtra("complete", true));
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void BtnEnd() {
        if (SectionC1Activity.editChildFlag) {
            finish();
            startActivity(new Intent(this, ViewMemberActivity.class)
                    .putExtra("flagEdit", false)
                    .putExtra("comingBack", true)
                    .putExtra("cluster", MainApp.cc.getClusterno())
                    .putExtra("hhno", MainApp.cc.getHhno())
            );
        } else {
            MainApp.endChildActivity(this, this, false);
        }
    }

    private boolean formValidation() {
        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (!validatorClass.EmptyRadioButton(this, bi.nc501, bi.nc501a, getString(R.string.nc501))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.nc502, bi.nc502a, getString(R.string.nc502))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.nc503, bi.nc503a, getString(R.string.nc503))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.nc504, bi.nc504a, getString(R.string.nc504))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, bi.nc505, bi.nc505a, getString(R.string.nc505))) {
            return false;
        }
        return validatorClass.EmptyRadioButton(this, bi.nc506, bi.nc506a, getString(R.string.nc506));

    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sC5 = new JSONObject();

        if (backPressed) {
            sC5.put("updatedate_nc5", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        if (SectionC1Activity.editChildFlag) {
            sC5.put("edit_updatedate_sc2", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

//        nc301
        //sC5.put("nc501", selectedChild.getName());
//        nc302
        //sC5.put("nc502Serial", selectedChild.getSerialNo());

//        nc501
        sC5.put("nc501", bi.nc501a.isChecked() ? "1"
                : bi.nc501b.isChecked() ? "2"
                : bi.nc501c.isChecked() ? "3"
                : bi.nc501d.isChecked() ? "4"
                : "0");
//        nc502
        sC5.put("nc502", bi.nc502a.isChecked() ? "1"
                : bi.nc502b.isChecked() ? "2"
                : bi.nc502c.isChecked() ? "3"
                : bi.nc502d.isChecked() ? "4"
                : "0");
//        nc503
        sC5.put("nc503", bi.nc503a.isChecked() ? "1"
                : bi.nc503b.isChecked() ? "2"
                : bi.nc503c.isChecked() ? "3"
                : bi.nc503d.isChecked() ? "4"
                : "0");
//        nc504
        sC5.put("nc504", bi.nc504a.isChecked() ? "1"
                : bi.nc504b.isChecked() ? "2"
                : bi.nc504c.isChecked() ? "3"
                : bi.nc504d.isChecked() ? "4"
                : "0");
//        nc505
        sC5.put("nc505", bi.nc505a.isChecked() ? "1"
                : bi.nc505b.isChecked() ? "2"
                : bi.nc505c.isChecked() ? "3"
                : bi.nc505d.isChecked() ? "4"
                : "0");
//        nc506
        sC5.put("nc506", bi.nc506a.isChecked() ? "1"
                : bi.nc506b.isChecked() ? "2"
                : bi.nc506c.isChecked() ? "3"
                : bi.nc506d.isChecked() ? "4"
                : "0");

        MainApp.cc.setsC5(String.valueOf(sC5));

        //Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {

        //Long rowId;

        int updcount = db.updateSC5();

        if (updcount == 1) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
        try {
            SaveDraft();
            UpdateDB();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onBackPressed();

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            public void run() {
                                formValidation();
                            }
                        });

                    }
                },
                DELAY
        );
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        formValidation();
    }


}
