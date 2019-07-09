package edu.aku.hassannaqvi.casi_2019.ui.childs;

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

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONC5ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.ChildContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionC5Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

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

        this.setTitle(getResources().getString(R.string.cic5heading));

        if (SectionC1Activity.editChildFlag) {
            bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                    + "\n\n" + SectionC1Activity.editMotherName + " : " + getString(R.string.cih212a));
        } else {
            if (!SectionC1Activity.isNA) {
                bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionB1Activity.wraName + " : " + getString(R.string.cih212a));
            } else {
                bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionC1Activity.careTaker + " : " + getString(R.string.cih113));
            }
        }

        db = new DatabaseHelper(this);

        //Get Intent
        selectedChild = (FamilyMembersContract) getIntent().getSerializableExtra("selectedChild");


        bi.cic501.setOnCheckedChangeListener(this);
        bi.cic502.setOnCheckedChangeListener(this);
        bi.cic503.setOnCheckedChangeListener(this);
        bi.cic504.setOnCheckedChangeListener(this);
        bi.cic505.setOnCheckedChangeListener(this);
        bi.cic506.setOnCheckedChangeListener(this);

//        Validation Boolean
        MainApp.validateFlag = false;

        autoPopulateFields();

    }

    private void autoPopulateFields() {

        ChildContract childContract = db.getsC5();

        if (!childContract.getsC5().equals("")) {

            JSONC5ModelClass jsonC5 = JSONUtilClass.getModelFromJSON(childContract.getsC5(), JSONC5ModelClass.class);

            if (!jsonC5.getcic501().equals("0")) {
                bi.cic501.check(
                        jsonC5.getcic501().equals("1") ? bi.cic501a.getId()
                                : jsonC5.getcic501().equals("2") ? bi.cic501b.getId()
                                : jsonC5.getcic501().equals("3") ? bi.cic501c.getId()
                                : bi.cic501d.getId()
                );
            }
            if (!jsonC5.getcic502().equals("0")) {
                bi.cic502.check(
                        jsonC5.getcic502().equals("1") ? bi.cic502a.getId()
                                : jsonC5.getcic502().equals("2") ? bi.cic502b.getId()
                                : jsonC5.getcic502().equals("3") ? bi.cic502c.getId()
                                : bi.cic502d.getId()
                );
            }
            if (!jsonC5.getcic503().equals("0")) {
                bi.cic503.check(
                        jsonC5.getcic503().equals("1") ? bi.cic503a.getId()
                                : jsonC5.getcic503().equals("2") ? bi.cic503b.getId()
                                : jsonC5.getcic503().equals("3") ? bi.cic503c.getId()
                                : bi.cic503d.getId()
                );
            }
            if (!jsonC5.getcic504().equals("0")) {
                bi.cic504.check(
                        jsonC5.getcic504().equals("1") ? bi.cic504a.getId()
                                : jsonC5.getcic504().equals("2") ? bi.cic504b.getId()
                                : jsonC5.getcic504().equals("3") ? bi.cic504c.getId()
                                : bi.cic504d.getId()
                );
            }
            if (!jsonC5.getcic505().equals("0")) {
                bi.cic505.check(
                        jsonC5.getcic505().equals("1") ? bi.cic505a.getId()
                                : jsonC5.getcic505().equals("2") ? bi.cic505b.getId()
                                : jsonC5.getcic505().equals("3") ? bi.cic505c.getId()
                                : bi.cic505d.getId()
                );
            }
            if (!jsonC5.getcic506().equals("0")) {
                bi.cic506.check(
                        jsonC5.getcic506().equals("1") ? bi.cic506a.getId()
                                : jsonC5.getcic506().equals("2") ? bi.cic506b.getId()
                                : jsonC5.getcic506().equals("3") ? bi.cic506c.getId()
                                : bi.cic506d.getId()
                );
            }

        }

    }

    public void BtnContinue() {

//        Validation Boolean
        MainApp.validateFlag = true;


        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {


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


        if (!ValidatorClass.EmptyRadioButton(this, bi.cic501, bi.cic501a, getString(R.string.cic501))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, bi.cic502, bi.cic502a, getString(R.string.cic502))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, bi.cic503, bi.cic503a, getString(R.string.cic503))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, bi.cic504, bi.cic504a, getString(R.string.cic504))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, bi.cic505, bi.cic505a, getString(R.string.cic505))) {
            return false;
        }
        return ValidatorClass.EmptyRadioButton(this, bi.cic506, bi.cic506a, getString(R.string.cic506));

    }

    private void SaveDraft() throws JSONException {


        JSONObject sC5 = new JSONObject();

        if (backPressed) {
            sC5.put("updatedate_cic5", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        if (SectionC1Activity.editChildFlag) {
            sC5.put("edit_updatedate_sc2", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

//        nc301
        //sC5.put("cic501", selectedChild.getName());
//        nc302
        //sC5.put("cic502Serial", selectedChild.getSerialNo());

//        cic501
        sC5.put("cic501", bi.cic501a.isChecked() ? "1"
                : bi.cic501b.isChecked() ? "2"
                : bi.cic501c.isChecked() ? "3"
                : bi.cic501d.isChecked() ? "4"
                : "0");
//        cic502
        sC5.put("cic502", bi.cic502a.isChecked() ? "1"
                : bi.cic502b.isChecked() ? "2"
                : bi.cic502c.isChecked() ? "3"
                : bi.cic502d.isChecked() ? "4"
                : "0");
//        cic503
        sC5.put("cic503", bi.cic503a.isChecked() ? "1"
                : bi.cic503b.isChecked() ? "2"
                : bi.cic503c.isChecked() ? "3"
                : bi.cic503d.isChecked() ? "4"
                : "0");
//        cic504
        sC5.put("cic504", bi.cic504a.isChecked() ? "1"
                : bi.cic504b.isChecked() ? "2"
                : bi.cic504c.isChecked() ? "3"
                : bi.cic504d.isChecked() ? "4"
                : "0");
//        cic505
        sC5.put("cic505", bi.cic505a.isChecked() ? "1"
                : bi.cic505b.isChecked() ? "2"
                : bi.cic505c.isChecked() ? "3"
                : bi.cic505d.isChecked() ? "4"
                : "0");
//        cic506
        sC5.put("cic506", bi.cic506a.isChecked() ? "1"
                : bi.cic506b.isChecked() ? "2"
                : bi.cic506c.isChecked() ? "3"
                : bi.cic506d.isChecked() ? "4"
                : "0");

        MainApp.cc.setsC5(String.valueOf(sC5));

        //
    }

    private boolean UpdateDB() {

        //Long rowId;

        int updcount = db.updateSC5();

        if (updcount == 1) {

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
