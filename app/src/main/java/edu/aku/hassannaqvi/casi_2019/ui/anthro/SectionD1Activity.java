package edu.aku.hassannaqvi.casi_2019.ui.anthro;

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

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.AnthrosMembersContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD1Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

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
        binding.cid101.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, members));

//        Spinner setting
        binding.cid101.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {

                    SelectedMem mem = membersMap.get(binding.cid101.getSelectedItem().toString());
                    slc_type = mem.getType();
                    slecMem = mem.getFmc();

                    switch (slc_type) {
                        case 1: // MWRA

//                            binding.fldGrpbcgScar.setVisibility(View.GONE);
                            ClearClass.ClearAllFields(binding.fldGrpbcgScar, null);

//                            binding.fldGrpode.setVisibility(View.GONE);
                            ClearClass.ClearAllFields(binding.fldGrpode, null);
                            break;

                        case 2: // U5

//                            binding.fldGrpbcgScar.setVisibility(View.VISIBLE);
                            ClearClass.ClearAllFields(binding.fldGrpbcgScar, null);

//                            binding.fldGrpode.setVisibility(View.VISIBLE);
                            ClearClass.ClearAllFields(binding.fldGrpode, null);
                            break;

                        case 3: // Adolescent

//                            binding.fldGrpbcgScar.setVisibility(View.GONE);
                            ClearClass.ClearAllFields(binding.fldGrpbcgScar, null);

//                            binding.fldGrpode.setVisibility(View.GONE);
                            ClearClass.ClearAllFields(binding.fldGrpode, null);
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

        binding.cid1bcgscar.setOnCheckedChangeListener(this);


        // Text watcher for another reading of weight

        binding.cid1w.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //flag = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!flagW1) {
                    if (!binding.cid1w.getText().toString().isEmpty()) {
                        if (binding.cid1w.getText().toString().matches("^(\\d{3,3}\\.\\d{2,2})$")) {
                            weight1 = binding.cid1w.getText().toString();
                            binding.fldGrpW2.setVisibility(View.VISIBLE);

                        } else {
                            //binding.fldGrpW2.setVisibility(View.GONE);
                            binding.cid1w1.setText(null);
                            binding.cid1w.setEnabled(true);
                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        binding.cid1w1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    flagW1 = true;
                    binding.cid1w.setText("XXX.XX");
                    binding.cid1w.setEnabled(false);
                } else {
                    flagW1 = false;
                    binding.cid1w.setEnabled(true);
                    binding.cid1w.setText(weight1);
                }
            }
        });


        binding.cid1w1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!binding.cid1w1.getText().toString().isEmpty()) {
                    if (binding.cid1w1.getText().toString().matches("^(\\d{3,3}\\.\\d{2,2})$")) {
                        if (weight1.equals(binding.cid1w1.getText().toString())) {
                            binding.cid1w.setText(weight1);
                            binding.cid1w.setEnabled(true);
                            binding.cid1w.setError(null);
                            binding.cid1w1.setError(null);
                        } else {
                            binding.cid1w.setText(weight1);
                            binding.cid1w.setEnabled(true);
                            binding.cid1w.setError("Values dont match.. !!");
                            binding.cid1w1.setError("Values dont match..!!");
                        }
                    }
                }

            }
        });

        // Text Watcher for 2nd value of Height

        binding.cid1h.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //flag = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!flagH1) {
                    if (!binding.cid1h.getText().toString().isEmpty()) {
                        if (binding.cid1h.getText().toString().matches("^(\\d{3,3}\\.\\d{1,1})$")) {
                            height1 = binding.cid1h.getText().toString();
                            binding.fldGrpH2.setVisibility(View.VISIBLE);

                        } else {
                            //binding.fldGrpW2.setVisibility(View.GONE);
                            binding.cid1h1.setText(null);
                            binding.cid1h.setEnabled(true);
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.cid1h1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    flagH1 = true;
                    binding.cid1h.setText("XXX.X");
                    binding.cid1h.setEnabled(false);
                } else {
                    flagH1 = false;
                    binding.cid1h.setEnabled(true);
                    binding.cid1h.setText(height1);
                }
            }
        });


        binding.cid1h1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!binding.cid1h1.getText().toString().isEmpty()) {
                    if (binding.cid1h1.getText().toString().matches("^(\\d{3,3}\\.\\d{1,1})$")) {
                        if (height1.equals(binding.cid1h1.getText().toString())) {
                            binding.cid1h.setText(height1);
                            binding.cid1h.setEnabled(true);
                            binding.cid1h.setError(null);
                            binding.cid1h1.setError(null);
                        } else {
                            binding.cid1h.setText(height1);
                            binding.cid1h.setEnabled(true);
                            binding.cid1h.setError("Values dont match.. !!");
                            binding.cid1h1.setError("Values dont match..!!");
                        }
                    }
                }

            }
        });


        // Text watcher for another reading of MUAC

        binding.cid1muac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //flag = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!flagM1) {
                    if (!binding.cid1muac.getText().toString().isEmpty()) {
                        if (binding.cid1muac.getText().toString().matches("^(\\d{2,2}\\.\\d{1,1})$")) {
                            muac1 = binding.cid1muac.getText().toString();
                            binding.fldGrpMUAC2.setVisibility(View.VISIBLE);

                        } else {
                            //binding.fldGrpW2.setVisibility(View.GONE);
                            binding.cid1muac1.setText(null);
                            binding.cid1muac.setEnabled(true);
                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        binding.cid1muac1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    flagM1 = true;
                    binding.cid1muac.setText("XX.X");
                    binding.cid1muac.setEnabled(false);
                } else {
                    flagM1 = false;
                    binding.cid1muac.setEnabled(true);
                    binding.cid1muac.setText(muac1);
                }
            }
        });


        binding.cid1muac1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!binding.cid1muac1.getText().toString().isEmpty()) {
                    if (binding.cid1muac1.getText().toString().matches("^(\\d{2,2}\\.\\d{1,1})$")) {
                        if (muac1.equals(binding.cid1muac1.getText().toString())) {
                            binding.cid1muac.setText(muac1);
                            binding.cid1muac.setEnabled(true);
                            binding.cid1muac.setError(null);
                            binding.cid1muac1.setError(null);
                        } else {
                            binding.cid1muac.setText(muac1);
                            binding.cid1muac.setEnabled(true);
                            binding.cid1muac.setError("Values dont match.. !!");
                            binding.cid1muac1.setError("Values dont match..!!");
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


        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

/*
                if (counter == MainApp.all_members.size()) {

                    counter = 1;

                    startActivity(new Intent(this, EndingActivity.class).putExtra("complete", true));

                } else {

                    members.remove(binding.cid101.getSelectedItem().toString());

                    startActivity(new Intent(this, Sectiocid1Activity.class)
                            .putExtra("flag", true));
                }
*/

                String readings = "Weight: " + binding.cid1w.getText().toString() + "\n" +
                        "Height: " + binding.cid1h.getText().toString() + "\n" +
                        "MUAC: " + binding.cid1muac.getText().toString() + "\n";

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

                MainApp.endAnthroActivity(this, this);

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean formValidation() {


        if (endflag) {

            return ValidatorClass.EmptySpinner(this, binding.cid101, getString(R.string.cid101sno));
        } else {


            if (!ValidatorClass.EmptySpinner(this, binding.cid101, getString(R.string.cid101sno))) {
                return false;
            }

            /*Add ranges here.. 3 types of*/
            if (!ValidatorClass.EmptyTextBox(this, binding.cid1w, getString(R.string.cid1w))) {
                return false;
            }


            if (!binding.cid1w.getText().toString().matches("^(\\d{3,3}\\.\\d{2,2})$")) {
                Toast.makeText(this, "ERROR(invalid): " + "Please type the correct format" + getString(R.string.cid1w), Toast.LENGTH_LONG).show();
                binding.cid1w.setError("Please type correct format (XXX.XX)");
                return false;
            } else {
                binding.cid1w.setError(null);
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.cid1w1, getString(R.string.cid1w))) {
                return false;
            }


            if (!binding.cid1w1.getText().toString().matches("^(\\d{3,3}\\.\\d{2,2})$")) {
                Toast.makeText(this, "ERROR(invalid): " + "Please type the correct format" + getString(R.string.cid1w), Toast.LENGTH_LONG).show();
                binding.cid1w1.setError("Please type correct format (XXX.XX)");
                return false;
            } else {
                binding.cid1w1.setError(null);
            }


            if (!ValidatorClass.RangeTextBox(this, binding.cid1w, MinWeight(slc_type), MaxWeight(slc_type), getString(R.string.cid1w), " weight")) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.cid1h, getString(R.string.cid1h))) {
                return false;
            }


            if (!binding.cid1h.getText().toString().matches("^(\\d{3,3}\\.\\d{1,1})$")) {
                Toast.makeText(this, "ERROR(invalid): " + "Please type the correct format" + getString(R.string.cid1h), Toast.LENGTH_LONG).show();
                binding.cid1h.setError("Please type correct format (XXX.X)");
                return false;
            } else {
                binding.cid1h.setError(null);
            }


            if (!ValidatorClass.RangeTextBox(this, binding.cid1h, MinHeight(slc_type), MaxHeight(slc_type), getString(R.string.cid1h), " height")) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.cid1muac, getString(R.string.cid1muac))) {
                return false;
            }


            if (!binding.cid1muac.getText().toString().matches("^(\\d{2,2}\\.\\d{1,1})$")) {
                Toast.makeText(this, "ERROR(invalid): " + "Please type the correct format" + getString(R.string.cid1muac), Toast.LENGTH_LONG).show();
                binding.cid1muac.setError("Please type correct format (XX.X)");
                return false;
            } else {
                binding.cid1muac.setError(null);
            }


            if (!ValidatorClass.RangeTextBox(this, binding.cid1muac, MinMAUC(slc_type), MaxMAUC(slc_type), getString(R.string.cid1muac), " MAUC")) {
                return false;
            }
            /*end*/


            if (slc_type == 2) {
                if (!ValidatorClass.EmptyRadioButton(this, binding.cid1bcgscar, binding.cid1bcgscara, getString(R.string.cid1bcgscar))) {
                    return false;
                }

                return ValidatorClass.EmptyRadioButton(this, binding.cid1o, binding.cid1oa, getString(R.string.cid1o));
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


        MainApp.emc = new AnthrosMembersContract();

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

        name = binding.cid101.getSelectedItem().toString();
        sA3.put("ht_code", AntrhoInfoActivity.ht_code);
        sA3.put("wt_code", AntrhoInfoActivity.wt_code);
        sA3.put("cid101", binding.cid101.getSelectedItem().toString());
        sA3.put("cid101Serial", membersMap.get(binding.cid101.getSelectedItem()).getFmc().getSerialNo());

        sA3.put("cid1Serial", String.valueOf(counter));

        if (!endflag) {

            sA3.put("cid1w", binding.cid1w.getText().toString());

            sA3.put("cid1h", binding.cid1h.getText().toString());

            sA3.put("cid1muac", binding.cid1muac.getText().toString());

            sA3.put("cid1bcgscar", binding.cid1bcgscara.isChecked() ? "1"
                    : binding.cid1bcgscarb.isChecked() ? "2" : "0");

            sA3.put("cid1o", binding.cid1oa.isChecked() ? "1"
                    : binding.cid1ob.isChecked() ? "2" : "0");

        }

        sA3.put("start_time", dateTime);


        MainApp.emc.setsA3(String.valueOf(sA3));


    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        Long updcount = db.addEligibleMember(MainApp.emc);
        MainApp.emc.set_ID(String.valueOf(updcount));

        if (updcount != 0) {


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
//                                formValidation();
                            }
                        });

                    }
                },
                DELAY
        );
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        formValidation();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        formValidation();
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
