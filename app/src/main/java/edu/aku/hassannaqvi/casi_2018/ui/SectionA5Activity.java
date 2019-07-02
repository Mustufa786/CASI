package edu.aku.hassannaqvi.casi_2018.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONA5ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionA5Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.validation.clearClass;
import edu.aku.hassannaqvi.casi_2018.validation.validatorClass;

public class SectionA5Activity extends Menu2Activity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    //    static int deceasedCounter = 0;
    private final long DELAY = 1000;
    ActivitySectionA5Binding binding;
    public CheckBox.OnCheckedChangeListener check = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (binding.nh403a.isChecked() || binding.nh403b.isChecked() || binding.nh403c.isChecked()) {
                clearClass.ClearAllFields(binding.fldGrnh404, false);
                clearClass.ClearAllFields(binding.fldGrpnh405, false);
            } else {
                clearClass.ClearAllFields(binding.fldGrnh404, true);
                clearClass.ClearAllFields(binding.fldGrpnh405, true);
            }
        }
    };
    DatabaseHelper db;
    int recipientCounter = 0;
    int prevRecipientCounter = 0;
    Boolean backPressed = false;
    int prevDeceasedCounter = 0;
    String nh801, nh802;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_a5);
        db = new DatabaseHelper(this);

//        Assigning data to UI binding
        binding.setCallback(this);

        this.setTitle(getResources().getString(R.string.na5heading));

        binding.nh401.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //formValidation();
                if (!(checkedId == R.id.nh401a)) {

                    clearClass.ClearAllFields(binding.fldGrpnh402, false);

                } else {
                    formValidation();
                    clearClass.ClearAllFields(binding.fldGrpnh402, true);
                }
            }
        });

        binding.nh403a.setOnCheckedChangeListener(check);
        binding.nh403b.setOnCheckedChangeListener(check);
        binding.nh403c.setOnCheckedChangeListener(check);

        binding.nh403e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    binding.nh403a.setEnabled(false);
                    binding.nh403b.setEnabled(false);
                    binding.nh403c.setEnabled(false);
                    binding.nh403d.setEnabled(false);

                    binding.nh403a.setChecked(false);
                    binding.nh403b.setChecked(false);
                    binding.nh403c.setChecked(false);
                    binding.nh403d.setChecked(false);

                } else {


                    binding.nh403a.setEnabled(true);
                    binding.nh403b.setEnabled(true);
                    binding.nh403c.setEnabled(true);
                    binding.nh403d.setEnabled(true);
                }
            }
        });

        binding.nh404.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (checkedId == R.id.nh404b) {
                    clearClass.ClearAllFields(binding.fldGrpnh405, false);

                } else {
                    clearClass.ClearAllFields(binding.fldGrpnh405, true);
                }
            }
        });
        binding.nh405e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.nh405a.setEnabled(false);
                    binding.nh405b.setEnabled(false);
                    binding.nh405c.setEnabled(false);
                    binding.nh405d.setEnabled(false);

                    binding.nh405a.setChecked(false);
                    binding.nh405b.setChecked(false);
                    binding.nh405c.setChecked(false);
                    binding.nh405d.setChecked(false);

                } else {
                    binding.nh405a.setEnabled(true);
                    binding.nh405b.setEnabled(true);
                    binding.nh405c.setEnabled(true);
                    binding.nh405d.setEnabled(true);
                }
            }
        });

        binding.nh501.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (checkedId == R.id.nh501d || checkedId == R.id.nh50196) {
                    clearClass.ClearAllFields(binding.fldGrnh602, false);
                    clearClass.ClearAllFields(binding.fldGrnc502, false);

                } else {
                    clearClass.ClearAllFields(binding.fldGrnh602, true);
                    clearClass.ClearAllFields(binding.fldGrnc502, true);

                }
            }
        });

//        Listeners
        binding.nh402.setOnCheckedChangeListener(this);
        binding.nh40601.setOnCheckedChangeListener(this);
        binding.nh40602.setOnCheckedChangeListener(this);
        binding.nh40603.setOnCheckedChangeListener(this);
        binding.nh40604.setOnCheckedChangeListener(this);
        binding.nh40605.setOnCheckedChangeListener(this);
        binding.nh40696.setOnCheckedChangeListener(this);
        //binding.nh501.setOnCheckedChangeListener(this);
        binding.nh502.setOnCheckedChangeListener(this);
        binding.nh503.setOnCheckedChangeListener(this);
        binding.nh601.setOnCheckedChangeListener(this);
        binding.nh602.setOnCheckedChangeListener(this);
        binding.nh603.setOnCheckedChangeListener(this);
        binding.nh604.setOnCheckedChangeListener(this);
        binding.nh605.setOnCheckedChangeListener(this);
        binding.nh606.setOnCheckedChangeListener(this);
        binding.nh607.setOnCheckedChangeListener(this);
        binding.nh608.setOnCheckedChangeListener(this);
        binding.nh609.setOnCheckedChangeListener(this);
        binding.nh701.setOnCheckedChangeListener(this);

        binding.nh702.addTextChangedListener(this);

//        Validation Boolean
        MainApp.validateFlag = false;

        if (SectionA1Activity.editFormFlag) {
            AutoPopulate();
        }
    }

    private void AutoPopulate() {
        FormsContract formContract = db.getsA5();
        if (!formContract.getsA5().equals("")) {

            JSONA5ModelClass jsonA5 = JSONUtilClass.getModelFromJSON(formContract.getsA5(), JSONA5ModelClass.class);

            if (!jsonA5.getnh401().equals("0")) {
                binding.nh401.check(
                        jsonA5.getnh401().equals("1") ? binding.nh401a.getId() :
                                jsonA5.getnh401().equals("2") ? binding.nh401b.getId() :
                                        jsonA5.getnh401().equals("3") ? binding.nh401c.getId() :
                                                binding.nh401d.getId()
                );
            }
            if (!jsonA5.getnh402().equals("0")) {
                binding.nh402.check(
                        jsonA5.getnh402().equals("1") ? binding.nh402a.getId() :
                                binding.nh402b.getId()
                );
            }
            if (!jsonA5.getnh403a().equals("0")) {
                binding.nh403a.setChecked(true);
            }
            if (!jsonA5.getnh403b().equals("0")) {
                binding.nh403b.setChecked(true);
            }
            if (!jsonA5.getnh403c().equals("0")) {
                binding.nh403c.setChecked(true);
            }
            if (!jsonA5.getnh403d().equals("0")) {
                binding.nh403d.setChecked(true);
            }
            if (!jsonA5.getnh403e().equals("0")) {
                binding.nh403e.setChecked(true);
            }

            if (!jsonA5.getnh404().equals("0")) {
                binding.nh404.check(
                        jsonA5.getnh404().equals("1") ? binding.nh404a.getId() :
                                binding.nh404b.getId()
                );
            }
            if (!jsonA5.getnh405a().equals("0")) {
                binding.nh405a.setChecked(true);
            }

            if (!jsonA5.getnh405b().equals("0")) {
                binding.nh405b.setChecked(true);
            }
            if (!jsonA5.getnh405c().equals("0")) {
                binding.nh405c.setChecked(true);
            }
            if (!jsonA5.getnh405d().equals("0")) {
                binding.nh405d.setChecked(true);
            }
            if (!jsonA5.getnh405e().equals("0")) {
                binding.nh405e.setChecked(true);
            }
            if (!jsonA5.getnh40601().equals("0")) {
                binding.nh40601.check(
                        jsonA5.getnh404().equals("1") ? binding.nh40601a.getId() :
                                binding.nh40601b.getId()
                );
            }
            if (!jsonA5.getnh40602().equals("0")) {
                binding.nh40602.check(
                        jsonA5.getnh40602().equals("1") ? binding.nh40602a.getId() :
                                binding.nh40602b.getId()
                );
            }
            if (!jsonA5.getnh40603().equals("0")) {
                binding.nh40603.check(
                        jsonA5.getnh404().equals("1") ? binding.nh40603a.getId() :
                                binding.nh40603b.getId()
                );
            }
            if (!jsonA5.getnh40604().equals("0")) {
                binding.nh40604.check(
                        jsonA5.getnh404().equals("1") ? binding.nh40604a.getId() :
                                binding.nh40604b.getId()
                );
            }
            if (!jsonA5.getnh40605().equals("0")) {
                binding.nh40605.check(
                        jsonA5.getnh404().equals("1") ? binding.nh40605a.getId() :
                                binding.nh40605b.getId()
                );
            }
            if (!jsonA5.getnh40696().equals("0")) {
                binding.nh40696.check(
                        jsonA5.getnh404().equals("1") ? binding.nh40696a.getId() :
                                binding.nh40696b.getId()
                );
            }
            binding.nh40696x.setText(jsonA5.getnh40696x());

            if (!jsonA5.getnh501().equals("0")) {
                binding.nh501.check(
                        jsonA5.getnh501().equals("1") ? binding.nh501a.getId() :
                                jsonA5.getnh501().equals("2") ? binding.nh501b.getId() :
                                        jsonA5.getnh501().equals("3") ? binding.nh501c.getId() :
                                                jsonA5.getnh501().equals("4") ? binding.nh501d.getId() :
                                                        binding.nh50196.getId()
                );
            }
            binding.nh50196x.setText(jsonA5.getnh50196x());
            if (!jsonA5.getnh502().equals("0")) {
                binding.nh502.check(
                        jsonA5.getnh502().equals("1") ? binding.nh502a.getId() :
                                jsonA5.getnh502().equals("2") ? binding.nh502b.getId() :
                                        binding.nh502c.getId()
                );
            }
            if (!jsonA5.getnh503().equals("0")) {
                binding.nh503.check(
                        jsonA5.getnh503().equals("1") ? binding.nh503a.getId() :
                                jsonA5.getnh503().equals("2") ? binding.nh503b.getId() :
                                        jsonA5.getnh503().equals("3") ? binding.nh503c.getId() :
                                                binding.nh503d.getId()
                );
            }
            if (!jsonA5.getnh601().equals("0")) {
                binding.nh601.check(
                        jsonA5.getnh601().equals("1") ? binding.nh601a.getId() :
                                jsonA5.getnh601().equals("2") ? binding.nh601b.getId() :
                                        jsonA5.getnh601().equals("98") ? binding.nh60198.getId() :
                                                binding.nh60199.getId()
                );
            }
            if (!jsonA5.getnh602().equals("0")) {
                binding.nh602.check(
                        jsonA5.getnh602().equals("1") ? binding.nh602a.getId() :
                                jsonA5.getnh602().equals("2") ? binding.nh602b.getId() :
                                        jsonA5.getnh602().equals("98") ? binding.nh60298.getId() :
                                                binding.nh60299.getId()
                );
            }
            if (!jsonA5.getnh603().equals("0")) {
                binding.nh603.check(
                        jsonA5.getnh603().equals("1") ? binding.nh603a.getId() :
                                jsonA5.getnh603().equals("2") ? binding.nh603b.getId() :
                                        jsonA5.getnh603().equals("98") ? binding.nh60398.getId() :
                                                binding.nh60399.getId()
                );
            }
            if (!jsonA5.getnh604().equals("0")) {
                binding.nh604.check(
                        jsonA5.getnh604().equals("1") ? binding.nh604a.getId() :
                                jsonA5.getnh604().equals("2") ? binding.nh604b.getId() :
                                        jsonA5.getnh604().equals("98") ? binding.nh60498.getId() :
                                                binding.nh60499.getId()
                );
            }
            if (!jsonA5.getnh605().equals("0")) {
                binding.nh605.check(
                        jsonA5.getnh605().equals("1") ? binding.nh605a.getId() :
                                jsonA5.getnh605().equals("2") ? binding.nh605b.getId() :
                                        jsonA5.getnh605().equals("98") ? binding.nh60598.getId() :
                                                binding.nh60599.getId()
                );
            }
            if (!jsonA5.getnh606().equals("0")) {
                binding.nh606.check(
                        jsonA5.getnh606().equals("1") ? binding.nh606a.getId() :
                                jsonA5.getnh606().equals("2") ? binding.nh606b.getId() :
                                        jsonA5.getnh606().equals("98") ? binding.nh60698.getId() :
                                                binding.nh60699.getId()
                );
            }
            if (!jsonA5.getnh607().equals("0")) {
                binding.nh607.check(
                        jsonA5.getnh607().equals("1") ? binding.nh607a.getId() :
                                jsonA5.getnh607().equals("2") ? binding.nh607b.getId() :
                                        jsonA5.getnh607().equals("98") ? binding.nh60798.getId() :
                                                binding.nh60799.getId()
                );
            }
            if (!jsonA5.getnh608().equals("0")) {
                binding.nh608.check(
                        jsonA5.getnh608().equals("1") ? binding.nh608a.getId() :
                                jsonA5.getnh608().equals("2") ? binding.nh608b.getId() :
                                        jsonA5.getnh608().equals("98") ? binding.nh60898.getId() :
                                                binding.nh60899.getId()
                );
            }
            if (!jsonA5.getnh609().equals("0")) {
                binding.nh609.check(
                        jsonA5.getnh609().equals("1") ? binding.nh609a.getId() :
                                jsonA5.getnh609().equals("2") ? binding.nh609b.getId() :
                                        jsonA5.getnh609().equals("98") ? binding.nh60998.getId() :
                                                binding.nh60999.getId()
                );
            }
            if (!jsonA5.getnh701().equals("0")) {
                binding.nh701.check(
                        jsonA5.getnh701().equals("1") ? binding.nh701a.getId() :
                                binding.nh701b.getId()

                );
            }
            binding.nh702.setText(jsonA5.getnh702());

            if (jsonA5.getnh701().equals("2")) {
                binding.nh701a.setEnabled(false);
            }
            if (!jsonA5.getnh702().equals("")) {
                prevRecipientCounter = Integer.valueOf(jsonA5.getnh702());

            }
           /*
            if (!jsonA5.getnh801().equals("0")) {
                binding.nh801.check(
                        jsonA5.getnh801().equals("1") ? binding.nh801a.getId() :
                                binding.nh801b.getId()

                );
            }
            binding.nh802.setText(jsonA5.getnh802());

            if (jsonA5.getnh801().equals("2")) {
                binding.nh801a.setEnabled(false);
            }
            if (!jsonA5.getnh802().equals("")) {
                prevDeceasedCounter = Integer.valueOf(jsonA5.getnh802());
            }

            */

            if (!jsonA5.getnh801().equals("0")) {
                nh801 = jsonA5.getnh801().equals("1") ? "1" :
                        "2";
            }
            if (!jsonA5.getnh802().equals("")) {
                nh802 = jsonA5.getnh802();
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

                if (recipientCounter > 0) {

                    if (recipientCounter < prevRecipientCounter) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                SectionA5Activity.this);
                        alertDialogBuilder
                                .setMessage("In previous you saved " + prevRecipientCounter + " Recipient.\n" +
                                        "Do you want to continue it?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {

                                                finish();

                                                startActivity(new Intent(SectionA5Activity.this,
                                                        SectionA8AActivity.class).putExtra("recCounter", recipientCounter));
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

                    } else {
                        finish();
                        startActivity(new Intent(this, SectionA8AActivity.class).putExtra("recCounter", recipientCounter));
                    }
                }
                /*
                else if (deceasedCounter > 0) {
//                    startActivity(new Intent(this, SectionH8Activity.class));

                    if (deceasedCounter < prevDeceasedCounter) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                SectionA5Activity.this);
                        alertDialogBuilder
                                .setMessage("In previous you saved " + prevDeceasedCounter + " Deceased.\n" +
                                        "Do you want to continue it?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {

                                                deceasedCounter = prevDeceasedCounter;
                                                finish();
//                                                startActivity(new Intent(SectionA5Activity.this, SectionH8Activity.class));
                                                startActivity(new Intent(SectionA5Activity.this, SectionH8infoActivity.class));
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

                    } else {
                        finish();
//                        startActivity(new Intent(SectionA5Activity.this, SectionH8Activity.class));
                        startActivity(new Intent(SectionA5Activity.this, SectionH8infoActivity.class));
                    }

                }
                */
                else {
                    finish();
                    if (SectionA1Activity.editFormFlag) {
                        startActivity(new Intent(this, SectionH8infoActivity.class));

                       /* startActivity(new Intent(this, ViewMemberActivity.class)
                                .putExtra("flagEdit", false)
                                .putExtra("comingBack", true)
                                .putExtra("cluster", MainApp.fc.getClusterNo())
                                .putExtra("hhno", MainApp.fc.getHhNo())
                        );*/
                    } else {
/*
                        if (!MainApp.UpdateSummary(this, db, 1)) {
                            Toast.makeText(this, "Summary Table not update!!", Toast.LENGTH_SHORT).show();
                        }*/

//                        startActivity(new Intent(this, ViewMemberActivity.class).putExtra("activity", 1));
                        startActivity(new Intent(this, SectionH8infoActivity.class));
                    }
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }

    public boolean formValidation() {

        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

//        nh401

        if (!validatorClass.EmptyRadioButton(this, binding.nh401, binding.nh401a, getString(R.string.nh401))) {
            return false;
        }

//        nh402
        if (binding.nh401a.isChecked()) {
            if (!validatorClass.EmptyRadioButton(this, binding.nh402, binding.nh402a, getString(R.string.nh402b))) {
                return false;
            }
            // nh403
            if (!validatorClass.EmptyCheckBox(this, binding.fldGrnh403check, binding.nh403a, getString(R.string.nh403))) {
                return false;
            }
        }
        if (!binding.nh403a.isChecked() && !binding.nh403b.isChecked() && !binding.nh403c.isChecked()) {
//        nh404
            if (!validatorClass.EmptyRadioButton(this, binding.nh404, binding.nh404a, getString(R.string.nh404))) {
                return false;
            }

//        nh405
            if (binding.nh404a.isChecked()) {
                if (!validatorClass.EmptyCheckBox(this, binding.fldGrpnh405check, binding.nh405a, getString(R.string.nh405))) {
                    return false;
                }
            }
        }
//        nh40601
        if (!validatorClass.EmptyRadioButton(this, binding.nh40601, binding.nh40601b, getString(R.string.nh40601))) {
            return false;
        }
//        nh40602
        if (!validatorClass.EmptyRadioButton(this, binding.nh40602, binding.nh40602b, getString(R.string.nh40602))) {
            return false;
        }
//        nh40603
        if (!validatorClass.EmptyRadioButton(this, binding.nh40603, binding.nh40603b, getString(R.string.nh40603))) {
            return false;
        }
//        nh40604
        if (!validatorClass.EmptyRadioButton(this, binding.nh40604, binding.nh40604b, getString(R.string.nh40604))) {
            return false;
        }
//        nh40605
        if (!validatorClass.EmptyRadioButton(this, binding.nh40605, binding.nh40605b, getString(R.string.nh40605))) {
            return false;
        }
//        nh40696
        if (!validatorClass.EmptyRadioButton(this, binding.nh40696, binding.nh40696a, binding.nh40696x, getString(R.string.nh406) + " - " + getString(R.string.other))) {
            return false;
        }
//        nh501
        if (!validatorClass.EmptyRadioButton(this, binding.nh501, binding.nh501a, getString(R.string.nh501))) {
            return false;
        }
//        nh501
        if (!validatorClass.EmptyRadioButton(this, binding.nh501, binding.nh50196, binding.nh50196x, getString(R.string.nh501) + " - " + getString(R.string.other))) {
            return false;
        }
        if (!binding.nh501d.isChecked()) {


//        nh502
            if (!validatorClass.EmptyRadioButton(this, binding.nh502, binding.nh502a, getString(R.string.nh502))) {
                return false;
            }
//        nh503
            if (!validatorClass.EmptyRadioButton(this, binding.nh503, binding.nh503a, getString(R.string.nh503))) {
                return false;
            }
//        nh504

        }

//        nh601
        if (!validatorClass.EmptyRadioButton(this, binding.nh601, binding.nh601a, getString(R.string.nh601))) {
            return false;
        }
//        nh602
        if (!validatorClass.EmptyRadioButton(this, binding.nh602, binding.nh602a, getString(R.string.nh602))) {
            return false;
        }
//        nh603
        if (!validatorClass.EmptyRadioButton(this, binding.nh603, binding.nh603a, getString(R.string.nh603))) {
            return false;
        }
//        nh604
        if (!validatorClass.EmptyRadioButton(this, binding.nh604, binding.nh604a, getString(R.string.nh604))) {
            return false;
        }
//        nh605
        if (!validatorClass.EmptyRadioButton(this, binding.nh605, binding.nh605a, getString(R.string.nh605))) {
            return false;
        }
//        nh606
        if (!validatorClass.EmptyRadioButton(this, binding.nh606, binding.nh606a, getString(R.string.nh606))) {
            return false;
        }
//        nh607
        if (!validatorClass.EmptyRadioButton(this, binding.nh607, binding.nh607a, getString(R.string.nh607))) {
            return false;
        }
//        nh608
        if (!validatorClass.EmptyRadioButton(this, binding.nh608, binding.nh608a, getString(R.string.nh608))) {
            return false;
        }
//        nh609
        if (!validatorClass.EmptyRadioButton(this, binding.nh609, binding.nh609a, getString(R.string.nh609))) {
            return false;
        }
//        nh701
        if (!validatorClass.EmptyRadioButton(this, binding.nh701, binding.nh701b, getString(R.string.nh701))) {
            return false;
        }
//        nh702
        if (binding.nh701a.isChecked()) {
            if (!validatorClass.EmptyTextBox(this, binding.nh702, getString(R.string.nh702))) {
                return false;
            }

            if (SectionA1Activity.editFormFlag) {
                /*if (Integer.valueOf(binding.nh702.getText().toString()) > prevRecipientCounter) {
                    Toast.makeText(this, "Can't increase Recipient!", Toast.LENGTH_SHORT).show();
                    return false;
                }*/

                return validatorClass.RangeTextBox(this, binding.nh702, 1, MainApp.all_members.size(), getString(R.string.nh702), " Recipient no");

            } else {
                return validatorClass.RangeTextBox(this, binding.nh702, 1, MainApp.membersCount.getCount(), getString(R.string.nh702), " Recipient no");
            }

        }
/*
        if (!validatorClass.EmptyRadioButton(this, binding.nh801, binding.nh801a, getString(R.string.nh801))) {
            return false;
        }

        if (binding.nh801a.isChecked()) {
            if (!validatorClass.EmptyTextBox(this, binding.nh802, getString(R.string.nh802))) {
                return false;
            }

            if (SectionA1Activity.editFormFlag) {
                if (Integer.valueOf(binding.nh802.getText().toString()) > prevDeceasedCounter) {
                    Toast.makeText(this, "Can't increase Deceased!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                return validatorClass.RangeTextBox(this, binding.nh802, 1, 99, getString(R.string.nh802), " Deceased");
            }
        }
*/
        return true;
    }

    public void BtnEnd() {
        if (SectionA1Activity.editFormFlag) {
            startActivity(new Intent(this, ViewMemberActivity.class)
                    .putExtra("flagEdit", false)
                    .putExtra("comingBack", true)
                    .putExtra("cluster", MainApp.fc.getClusterNo())
                    .putExtra("hhno", MainApp.fc.getHhNo())
            );
        } else {
            MainApp.endActivity(this, this);
        }

    }


    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sA5 = new JSONObject();
        if (SectionA1Activity.editFormFlag) {
            sA5.put("edit_updatedate_sa5", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

        }
        sA5.put("nh401", binding.nh401a.isChecked() ? "1"
                : binding.nh401b.isChecked() ? "2"
                : binding.nh401c.isChecked() ? "3"
                : binding.nh401d.isChecked() ? "4"
                : "0");

        sA5.put("nh402", binding.nh402a.isChecked() ? "1"
                : binding.nh402b.isChecked() ? "2"
                : "0");

        sA5.put("nh403a", binding.nh403a.isChecked() ? "1" : "0");
        sA5.put("nh403b", binding.nh403b.isChecked() ? "2" : "0");
        sA5.put("nh403c", binding.nh403c.isChecked() ? "3" : "0");
        sA5.put("nh403d", binding.nh403d.isChecked() ? "4" : "0");
        sA5.put("nh403e", binding.nh403e.isChecked() ? "5" : "0");

        sA5.put("nh404", binding.nh404a.isChecked() ? "1"
                : binding.nh404b.isChecked() ? "2"
                : "0");

        sA5.put("nh405a", binding.nh405a.isChecked() ? "1" : "0");
        sA5.put("nh405b", binding.nh405b.isChecked() ? "2" : "0");
        sA5.put("nh405c", binding.nh405c.isChecked() ? "3" : "0");
        sA5.put("nh405d", binding.nh405d.isChecked() ? "4" : "0");
        sA5.put("nh405e", binding.nh405e.isChecked() ? "5" : "0");


        sA5.put("nh40601", binding.nh40601a.isChecked() ? "1"
                : binding.nh40601b.isChecked() ? "2"
                : "0");


        sA5.put("nh40602", binding.nh40602a.isChecked() ? "1"
                : binding.nh40602b.isChecked() ? "2"
                : "0");

        sA5.put("nh40603", binding.nh40603a.isChecked() ? "1"
                : binding.nh40603b.isChecked() ? "2"
                : "0");

        sA5.put("nh40604", binding.nh40604a.isChecked() ? "1"
                : binding.nh40604b.isChecked() ? "2"
                : "0");

        sA5.put("nh40605", binding.nh40605a.isChecked() ? "1"
                : binding.nh40605b.isChecked() ? "2"
                : "0");

        sA5.put("nh40696", binding.nh40696a.isChecked() ? "1"
                : binding.nh40696b.isChecked() ? "2"
                : "0");

        sA5.put("nh40696x", binding.nh40696x.getText().toString());


        // Section A6

        sA5.put("nh501", binding.nh501a.isChecked() ? "1"
                : binding.nh501b.isChecked() ? "2"
                : binding.nh501c.isChecked() ? "3"
                : binding.nh501d.isChecked() ? "4"
                : binding.nh50196.isChecked() ? "96"
                : "0");

        sA5.put("nh50196x", binding.nh50196x.getText().toString());

        sA5.put("nh502", binding.nh502a.isChecked() ? "1"
                : binding.nh502b.isChecked() ? "2"
                : binding.nh502c.isChecked() ? "3"
                : "0");

        sA5.put("nh503", binding.nh503a.isChecked() ? "1"
                : binding.nh503b.isChecked() ? "2"
                : binding.nh503c.isChecked() ? "3"
                : binding.nh503d.isChecked() ? "4"
                : "0");

        //Section A7

        sA5.put("nh601", binding.nh601a.isChecked() ? "1"
                : binding.nh601b.isChecked() ? "2"
                : binding.nh60198.isChecked() ? "98"
                : binding.nh60199.isChecked() ? "99"
                : "0");

        sA5.put("nh602", binding.nh602a.isChecked() ? "1"
                : binding.nh602b.isChecked() ? "2"
                : binding.nh60298.isChecked() ? "98"
                : binding.nh60299.isChecked() ? "99"
                : "0");

        sA5.put("nh603", binding.nh603a.isChecked() ? "1"
                : binding.nh603b.isChecked() ? "2"
                : binding.nh60398.isChecked() ? "98"
                : binding.nh60399.isChecked() ? "99"
                : "0");

        sA5.put("nh604", binding.nh604a.isChecked() ? "1"
                : binding.nh604b.isChecked() ? "2"
                : binding.nh60498.isChecked() ? "98"
                : binding.nh60499.isChecked() ? "99"
                : "0");

        sA5.put("nh605", binding.nh605a.isChecked() ? "1"
                : binding.nh605b.isChecked() ? "2"
                : binding.nh60598.isChecked() ? "98"
                : binding.nh60599.isChecked() ? "99"
                : "0");

        sA5.put("nh606", binding.nh606a.isChecked() ? "1"
                : binding.nh606b.isChecked() ? "2"
                : binding.nh60698.isChecked() ? "98"
                : binding.nh60699.isChecked() ? "99"
                : "0");

        sA5.put("nh607", binding.nh607a.isChecked() ? "1"
                : binding.nh607b.isChecked() ? "2"
                : binding.nh60798.isChecked() ? "98"
                : binding.nh60799.isChecked() ? "99"
                : "0");

        sA5.put("nh608", binding.nh608a.isChecked() ? "1"
                : binding.nh608b.isChecked() ? "2"
                : binding.nh60898.isChecked() ? "98"
                : binding.nh60899.isChecked() ? "99"
                : "0");

        sA5.put("nh609", binding.nh609a.isChecked() ? "1"
                : binding.nh609b.isChecked() ? "2"
                : binding.nh60998.isChecked() ? "98"
                : binding.nh60999.isChecked() ? "99"
                : "0");

        // Section A8

        sA5.put("nh701", binding.nh701a.isChecked() ? "1"
                : binding.nh701b.isChecked() ? "2"
                : "0");

        sA5.put("nh702", binding.nh702.getText().toString());

        if (binding.nh701a.isChecked()) {
            recipientCounter = Integer.valueOf(binding.nh702.getText().toString());
        }

        if (SectionA1Activity.editFormFlag) {
            sA5.put("nh801", nh801);
            sA5.put("nh802", nh802);
        }
/*
        sA5.put("nh801", binding.nh801a.isChecked() ? "1" : binding.nh801b.isChecked() ? "2" : "0");
        sA5.put("nh802", binding.nh802.getText().toString());

        if (binding.nh801a.isChecked()) {
            deceasedCounter = Integer.valueOf(binding.nh802.getText().toString());
        }
*/
        MainApp.fc.setsA5(String.valueOf(sA5));

        // Set summary fields
        MainApp.sumc = MainApp.AddSummary(MainApp.fc, 1);

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSA5();

        if (updcount == 1) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
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
