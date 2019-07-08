package edu.aku.hassannaqvi.casi_2018.ui.anthro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.EligibleMembersContract;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionD1Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2018.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionD1Activity extends Menu2Activity implements TextWatcher, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    static List<String> members;
    static Map<String, SelectedMem> membersMap;
    static String name;
    static int counter = 1;
    private final long DELAY = 500;
    ActivitySectionD1Binding binding;
    DatabaseHelper db;
    int slc_type;
    JSONModelClass json;
    FamilyMembersContract slecMem;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    Boolean endflag = false;
    String dateTime = "";
    String weight1, height1, muac1;
    Boolean flagW1 = false, flagH1 = false, flagM1 = false;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_d1);
        db = new DatabaseHelper(this);

        this.setTitle(getResources().getString(R.string.nd1heading));

//        Assigning data to UI binding
        binding.setCallback(this);
        json = new JSONModelClass();

        dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis());


        setupViews();

        Log.d("Mem", String.valueOf(MainApp.members_f_m));
        Log.d("Mem", String.valueOf(MainApp.childUnder2));
        Log.d("Mem", String.valueOf(MainApp.childUnder5));
        Log.d("Mem", String.valueOf(MainApp.mwra));

//        Validation Boolean
        MainApp.validateFlag = false;

    }

    public void setupViews() {

//        Setup spinner

        //  Getting Extra
        if (getIntent().getBooleanExtra("flag", false)) {
            members.remove(getIntent().getStringExtra("name"));
            counter++;
        } else {
            members = new ArrayList<>();
            membersMap = new HashMap<>();

            members.add("....");

            familyMembersSetting(MainApp.mwra, 1);  // 1 for Mwra
            familyMembersSetting(MainApp.childUnder5, 2);  // 2 for Under 5
            familyMembersSetting(MainApp.adolescents, 3);  // 3 for Adolescents
        }
        slecMem = new FamilyMembersContract();
        binding.nd101.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, members));

//        Spinner setting
        binding.nd101.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    SelectedMem mem = membersMap.get(binding.nd101.getSelectedItem().toString());
                    slc_type = mem.getType();
                    slecMem = mem.getFmc();

                    switch (slc_type) {
                        case 1: // MWRA

//                            binding.fldGrpbcgScar.setVisibility(View.GONE);
                            ClearClass.ClearAllFields(binding.fldGrpbcgScar, false);

//                            binding.fldGrpgoiter.setVisibility(View.VISIBLE);
                            ClearClass.ClearAllFields(binding.fldGrpgoiter, true);

//                            binding.fldGrpca.setVisibility(View.VISIBLE);
                            ClearClass.ClearAllFields(binding.fldGrpca, true);

//                            binding.fldGrpode.setVisibility(View.GONE);
                            ClearClass.ClearAllFields(binding.fldGrpode, false);
                            break;

                        case 2: // U5

//                            binding.fldGrpbcgScar.setVisibility(View.VISIBLE);
                            ClearClass.ClearAllFields(binding.fldGrpbcgScar, true);

//                            binding.fldGrpgoiter.setVisibility(View.VISIBLE);
                            ClearClass.ClearAllFields(binding.fldGrpgoiter, true);

//                            binding.fldGrpca.setVisibility(View.VISIBLE);
                            ClearClass.ClearAllFields(binding.fldGrpca, true);

//                            binding.fldGrpode.setVisibility(View.VISIBLE);
                            ClearClass.ClearAllFields(binding.fldGrpode, true);
                            break;

                        case 3: // Adolescent

//                            binding.fldGrpbcgScar.setVisibility(View.GONE);
                            ClearClass.ClearAllFields(binding.fldGrpbcgScar, false);

//                            binding.fldGrpgoiter.setVisibility(View.VISIBLE);
                            ClearClass.ClearAllFields(binding.fldGrpgoiter, true);

//                            binding.fldGrpca.setVisibility(View.VISIBLE);
                            ClearClass.ClearAllFields(binding.fldGrpca, true);

//                            binding.fldGrpode.setVisibility(View.GONE);
                            ClearClass.ClearAllFields(binding.fldGrpode, false);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // setup head
        binding.txtCounter.setText("Count " + counter + " out of " + MainApp.all_members.size());

        binding.nd1bcgscar.setOnCheckedChangeListener(this);


        // Text watcher for another reading of weight

        binding.nd1w.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //flag = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!flagW1) {
                    if (!binding.nd1w.getText().toString().isEmpty()) {
                        if (binding.nd1w.getText().toString().matches("^(\\d{3,3}\\.\\d{2,2})$")) {
                            weight1 = binding.nd1w.getText().toString();
                            binding.fldGrpW2.setVisibility(View.VISIBLE);

                        } else {
                            //binding.fldGrpW2.setVisibility(View.GONE);
                            binding.nd1w1.setText(null);
                            binding.nd1w.setEnabled(true);
                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        binding.nd1w1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    flagW1 = true;
                    binding.nd1w.setText("XXX.XX");
                    binding.nd1w.setEnabled(false);
                } else {
                    flagW1 = false;
                    binding.nd1w.setEnabled(true);
                    binding.nd1w.setText(weight1);
                }
            }
        });


        binding.nd1w1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!binding.nd1w1.getText().toString().isEmpty()) {
                    if (binding.nd1w1.getText().toString().matches("^(\\d{3,3}\\.\\d{2,2})$")) {
                        if (weight1.equals(binding.nd1w1.getText().toString())) {
                            binding.nd1w.setText(weight1);
                            binding.nd1w.setEnabled(true);
                            binding.nd1w.setError(null);
                            binding.nd1w1.setError(null);
                        } else {
                            binding.nd1w.setText(weight1);
                            binding.nd1w.setEnabled(true);
                            binding.nd1w.setError("Values dont match.. !!");
                            binding.nd1w1.setError("Values dont match..!!");
                        }
                    }
                }

            }
        });

        // Text Watcher for 2nd value of Height

        binding.nd1h.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //flag = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!flagH1) {
                    if (!binding.nd1h.getText().toString().isEmpty()) {
                        if (binding.nd1h.getText().toString().matches("^(\\d{3,3}\\.\\d{1,1})$")) {
                            height1 = binding.nd1h.getText().toString();
                            binding.fldGrpH2.setVisibility(View.VISIBLE);

                        } else {
                            //binding.fldGrpW2.setVisibility(View.GONE);
                            binding.nd1h1.setText(null);
                            binding.nd1h.setEnabled(true);
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.nd1h1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    flagH1 = true;
                    binding.nd1h.setText("XXX.X");
                    binding.nd1h.setEnabled(false);
                } else {
                    flagH1 = false;
                    binding.nd1h.setEnabled(true);
                    binding.nd1h.setText(height1);
                }
            }
        });


        binding.nd1h1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!binding.nd1h1.getText().toString().isEmpty()) {
                    if (binding.nd1h1.getText().toString().matches("^(\\d{3,3}\\.\\d{1,1})$")) {
                        if (height1.equals(binding.nd1h1.getText().toString())) {
                            binding.nd1h.setText(height1);
                            binding.nd1h.setEnabled(true);
                            binding.nd1h.setError(null);
                            binding.nd1h1.setError(null);
                        } else {
                            binding.nd1h.setText(height1);
                            binding.nd1h.setEnabled(true);
                            binding.nd1h.setError("Values dont match.. !!");
                            binding.nd1h1.setError("Values dont match..!!");
                        }
                    }
                }

            }
        });


        // Text watcher for another reading of MUAC

        binding.nd1muac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //flag = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!flagM1) {
                    if (!binding.nd1muac.getText().toString().isEmpty()) {
                        if (binding.nd1muac.getText().toString().matches("^(\\d{2,2}\\.\\d{1,1})$")) {
                            muac1 = binding.nd1muac.getText().toString();
                            binding.fldGrpMUAC2.setVisibility(View.VISIBLE);

                        } else {
                            //binding.fldGrpW2.setVisibility(View.GONE);
                            binding.nd1muac1.setText(null);
                            binding.nd1muac.setEnabled(true);
                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        binding.nd1muac1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    flagM1 = true;
                    binding.nd1muac.setText("XX.X");
                    binding.nd1muac.setEnabled(false);
                } else {
                    flagM1 = false;
                    binding.nd1muac.setEnabled(true);
                    binding.nd1muac.setText(muac1);
                }
            }
        });


        binding.nd1muac1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!binding.nd1muac1.getText().toString().isEmpty()) {
                    if (binding.nd1muac1.getText().toString().matches("^(\\d{2,2}\\.\\d{1,1})$")) {
                        if (muac1.equals(binding.nd1muac1.getText().toString())) {
                            binding.nd1muac.setText(muac1);
                            binding.nd1muac.setEnabled(true);
                            binding.nd1muac.setError(null);
                            binding.nd1muac1.setError(null);
                        } else {
                            binding.nd1muac.setText(muac1);
                            binding.nd1muac.setEnabled(true);
                            binding.nd1muac.setError("Values dont match.. !!");
                            binding.nd1muac1.setError("Values dont match..!!");
                        }
                    }
                }

            }
        });


    }

    public void familyMembersSetting(List<FamilyMembersContract> family, int type) {

        for (FamilyMembersContract fmc : family) {
            json = JSONUtilClass.getModelFromJSON(fmc.getsA2(), JSONModelClass.class);
            membersMap.put(json.getName() + "_" + json.getSerialNo(), new SelectedMem(type, fmc, json.getSerialNo()));
            members.add(json.getName() + "_" + json.getSerialNo());
        }


    }

    @Override
    public void onBackPressed() {
        if (counter == 1) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
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
/*
                if (counter == MainApp.all_members.size()) {

                    counter = 1;

                    startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));

                } else {

                    members.remove(binding.nd101.getSelectedItem().toString());

                    startActivity(new Intent(this, SectionD1Activity.class)
                            .putExtra("flag", true));
                }
*/

                String readings = "Weight: " + binding.nd1w.getText().toString() + "\n" +
                        "Height: " + binding.nd1h.getText().toString() + "\n" +
                        "MUAC: " + binding.nd1muac.getText().toString() + "\n";

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        SectionD1Activity.this);
                alertDialogBuilder
                        .setMessage("Are you sure to confirm these reading's?\n\n" + readings)
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        finish();
                                        startActivity(new Intent(SectionD1Activity.this, AnthroEndingActivity.class).putExtra("complete", true));
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
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        endflag = true;
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                //Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();

                //finish();

                MainApp.endAnthroActivity(this, this);

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean formValidation() {

        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (endflag) {

            return ValidatorClass.EmptySpinner(this, binding.nd101, getString(R.string.nd101sno));
        } else {


            if (!ValidatorClass.EmptySpinner(this, binding.nd101, getString(R.string.nd101sno))) {
                return false;
            }

            /*Add ranges here.. 3 types of*/
            if (!ValidatorClass.EmptyTextBox(this, binding.nd1w, getString(R.string.nd1w))) {
                return false;
            }


            if (!binding.nd1w.getText().toString().matches("^(\\d{3,3}\\.\\d{2,2})$")) {
                Toast.makeText(this, "ERROR(invalid): " + "Please type the correct format" + getString(R.string.nd1w), Toast.LENGTH_LONG).show();
                binding.nd1w.setError("Please type correct format (XXX.XX)");
                return false;
            } else {
                binding.nd1w.setError(null);
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.nd1w1, getString(R.string.nd1w))) {
                return false;
            }


            if (!binding.nd1w1.getText().toString().matches("^(\\d{3,3}\\.\\d{2,2})$")) {
                Toast.makeText(this, "ERROR(invalid): " + "Please type the correct format" + getString(R.string.nd1w), Toast.LENGTH_LONG).show();
                binding.nd1w1.setError("Please type correct format (XXX.XX)");
                return false;
            } else {
                binding.nd1w1.setError(null);
            }


            if (!ValidatorClass.RangeTextBox(this, binding.nd1w, MinWeight(slc_type), MaxWeight(slc_type), getString(R.string.nd1w), " weight")) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.nd1h, getString(R.string.nd1h))) {
                return false;
            }


            if (!binding.nd1h.getText().toString().matches("^(\\d{3,3}\\.\\d{1,1})$")) {
                Toast.makeText(this, "ERROR(invalid): " + "Please type the correct format" + getString(R.string.nd1h), Toast.LENGTH_LONG).show();
                binding.nd1h.setError("Please type correct format (XXX.X)");
                return false;
            } else {
                binding.nd1h.setError(null);
            }


            if (!ValidatorClass.RangeTextBox(this, binding.nd1h, MinHeight(slc_type), MaxHeight(slc_type), getString(R.string.nd1h), " height")) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.nd1muac, getString(R.string.nd1muac))) {
                return false;
            }


            if (!binding.nd1muac.getText().toString().matches("^(\\d{2,2}\\.\\d{1,1})$")) {
                Toast.makeText(this, "ERROR(invalid): " + "Please type the correct format" + getString(R.string.nd1muac), Toast.LENGTH_LONG).show();
                binding.nd1muac.setError("Please type correct format (XX.X)");
                return false;
            } else {
                binding.nd1muac.setError(null);
            }


            if (!ValidatorClass.RangeTextBox(this, binding.nd1muac, MinMAUC(slc_type), MaxMAUC(slc_type), getString(R.string.nd1muac), " MAUC")) {
                return false;
            }
            /*end*/


            if (slc_type == 2) {
                if (!ValidatorClass.EmptyRadioButton(this, binding.nd1bcgscar, binding.nd1bcgscara, getString(R.string.nd1bcgscar))) {
                    return false;
                }
            }
            if (!ValidatorClass.EmptyRadioButton(this, binding.nd1g, binding.nd1ga, getString(R.string.nd1g))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, binding.nd1ca, binding.nd1caa, getString(R.string.nd1ca))) {
                return false;
            }

            if (slc_type == 2) {
                return ValidatorClass.EmptyRadioButton(this, binding.nd1o, binding.nd1oa, getString(R.string.nd1o));
            }
        }

        return true;
    }

    public double MinWeight(int type) {

        switch (type) {
            case 1:
            case 3:
                return 15d;
            case 2:
                return 0.5d;
        }
        return 0;
    }

    public double MaxWeight(int type) {

        switch (type) {
            case 1:
            case 3:
                return 250d;
            case 2:
                return 40d;
        }
        return 0;
    }

    public double MinHeight(int type) {

        switch (type) {
            case 1:
            case 3:
                return 100d;
            case 2:
                return 10d;
        }
        return 0;
    }

    public double MaxHeight(int type) {

        switch (type) {
            case 1:
            case 3:
                return 200d;
            case 2:
                return 140d;
        }
        return 0;
    }

    public double MinMAUC(int type) {

        switch (type) {
            case 1:
            case 3:
                return 15d;
            case 2:
                return 5d;
        }
        return 0;
    }

    public double MaxMAUC(int type) {

        switch (type) {
            case 1:
            case 3:
                return 60d;
            case 2:
                return 25d;
        }
        return 0;
    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        MainApp.emc = new EligibleMembersContract();

        MainApp.emc.setDevicetagID(MainApp.getTagName(this));
        MainApp.emc.setFormDate(slecMem.getFormDate());
        MainApp.emc.setUser(MainApp.userName);
        MainApp.emc.setDeviceId(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        MainApp.emc.setApp_ver(MainApp.versionName + "." + MainApp.versionCode);
        MainApp.emc.set_UUID(slecMem.get_UUID());
        MainApp.emc.setFmuid(slecMem.get_UID());
        MainApp.emc.setEnm_no(AntrhoInfoActivity.enm_no);
        MainApp.emc.setHh_no(AntrhoInfoActivity.hh_no);

        JSONObject sA3 = new JSONObject();

        name = binding.nd101.getSelectedItem().toString();
        sA3.put("ht_code", AntrhoInfoActivity.ht_code);
        sA3.put("wt_code", AntrhoInfoActivity.wt_code);
        sA3.put("nd101", binding.nd101.getSelectedItem().toString());
        sA3.put("nd101Serial", membersMap.get(binding.nd101.getSelectedItem()).getFmc().getSerialNo());

        sA3.put("nd1Serial", String.valueOf(counter));

        if (!endflag) {

            sA3.put("nd1w", binding.nd1w.getText().toString());

            sA3.put("nd1h", binding.nd1h.getText().toString());

            sA3.put("nd1muac", binding.nd1muac.getText().toString());

            sA3.put("nd1bcgscar", binding.nd1bcgscara.isChecked() ? "1"
                    : binding.nd1bcgscarb.isChecked() ? "2" : "0");

            sA3.put("nd1g", binding.nd1ga.isChecked() ? "1"
                    : binding.nd1gb.isChecked() ? "2" : "0");

            sA3.put("nd1ca", binding.nd1caa.isChecked() ? "1"
                    : binding.nd1cab.isChecked() ? "2" : "0");

            sA3.put("nd1o", binding.nd1oa.isChecked() ? "1"
                    : binding.nd1ob.isChecked() ? "2" : "0");

        }

        sA3.put("start_time", dateTime);


        MainApp.emc.setsA3(String.valueOf(sA3));


        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        Long updcount = db.addEligibleMember(MainApp.emc);
        MainApp.emc.set_ID(String.valueOf(updcount));

        if (updcount != 0) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            MainApp.emc.set_UID(
                    (MainApp.emc.getDeviceId() + MainApp.emc.get_ID()));
            db.updateEligibleMemberID();

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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        formValidation();
    }

    public class SelectedMem {
        int type;
        FamilyMembersContract fmc;


        public SelectedMem(int type, FamilyMembersContract fmc, String SerialNo) {
            this.type = type;
            this.fmc = fmc;
            this.fmc.setSerialNo(SerialNo);
        }

        public int getType() {
            return type;
        }


        public FamilyMembersContract getFmc() {
            return fmc;
        }
    }


}
