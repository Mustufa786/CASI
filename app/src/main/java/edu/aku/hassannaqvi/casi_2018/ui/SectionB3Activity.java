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

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONB3ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.MWRAContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionB3Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionB3Activity extends Menu2Activity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private final long DELAY = 1000;
    ActivitySectionB3Binding binding;
    DatabaseHelper db;
    Boolean backPressed = false;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_b3);
        db = new DatabaseHelper(this);

//        Assigning data to UI binding
        binding.setCallback(this);

        this.setTitle(getResources().getString(R.string.nb3heading));
        binding.textName.setText("Selected Woman : " + SectionB1Activity.wraName);

//        Skip Patterns

        binding.nw327.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                formValidation();
                if (i == R.id.nw32798) {
                    ClearClass.ClearAllFields(binding.fldGrnw328, false);
                    ClearClass.ClearAllFields(binding.fldGrpnw329, false);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrnw328, true);
                    ClearClass.ClearAllFields(binding.fldGrpnw329, true);
                }
            }
        });

        binding.nw330.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                formValidation();
                if (i == R.id.nw330d) {
                    ClearClass.ClearAllFields(binding.fldGrnw331, false);

                } else {
                    ClearClass.ClearAllFields(binding.fldGrnw331, true);
                }
            }
        });

        binding.nw331.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                formValidation();
                if (i == R.id.nw331b) {
                    ClearClass.ClearAllFields(binding.fldGrnw332, false);

//                    binding.nw332.clearCheck();
                } else {
                    ClearClass.ClearAllFields(binding.fldGrnw332, true);

                }
            }
        });

//        Listener
        //binding.nw328.setOnCheckedChangeListener(this);
        binding.nw332.setOnCheckedChangeListener(this);

//        Validation Boolean
        MainApp.validateFlag = false;

        AutoCompleteFields();

    }

    public void AutoCompleteFields() {
        MWRAContract mwraContract = db.getsB3();
        if (!mwraContract.getsB3().equals("")) {

            JSONB3ModelClass jsonB3 = JSONUtilClass.getModelFromJSON(mwraContract.getsB3(), JSONB3ModelClass.class);

            if (!jsonB3.getnw327().equals("0")) {
                binding.nw327.check(
                        jsonB3.getnw327().equals("1") ? binding.nw327a.getId() :
                                jsonB3.getnw327().equals("2") ? binding.nw327b.getId() :
                                        binding.nw32798.getId());
            }
            binding.nw327m.setText(jsonB3.getnw327m());
            binding.nw327d.setText(jsonB3.getnw327d());

            if (!jsonB3.getnw328().equals("0")) {
                binding.nw328.check(
                        jsonB3.getnw328().equals("1") ? binding.nw328a.getId() :
                                jsonB3.getnw328().equals("2") ? binding.nw328b.getId() :
                                        jsonB3.getnw328().equals("3") ? binding.nw328c.getId() :
                                                jsonB3.getnw328().equals("4") ? binding.nw328d.getId() :
                                                        jsonB3.getnw328().equals("5") ? binding.nw328e.getId() :
                                                                binding.nw32896.getId()
                );
                binding.nw32896x.setText(jsonB3.getnw32896x());
            }

            if (!jsonB3.getnw329a().equals("0")) {
                binding.nw329a.setChecked(true);
            }
            if (!jsonB3.getnw329b().equals("0")) {
                binding.nw329b.setChecked(true);
            }
            if (!jsonB3.getnw329c().equals("0")) {
                binding.nw329c.setChecked(true);
            }
            if (!jsonB3.getnw329d().equals("0")) {
                binding.nw329d.setChecked(true);
            }
            if (!jsonB3.getnw329e().equals("0")) {
                binding.nw329e.setChecked(true);
            }
            if (!jsonB3.getnw329f().equals("0")) {
                binding.nw329f.setChecked(true);
            }
            if (!jsonB3.getnw329g().equals("0")) {
                binding.nw329g.setChecked(true);
            }
            if (!jsonB3.getnw329h().equals("0")) {
                binding.nw329h.setChecked(true);
            }

            if (!jsonB3.getnw330().equals("0")) {
                binding.nw330.check(
                        jsonB3.getnw330().equals("1") ? binding.nw330a.getId() :
                                jsonB3.getnw330().equals("2") ? binding.nw330b.getId() :
                                        jsonB3.getnw330().equals("3") ? binding.nw330c.getId() :
                                                binding.nw330d.getId()
                );
            }

            if (!jsonB3.getnw331().equals("0")) {
                binding.nw331.check(
                        jsonB3.getnw331().equals("1") ? binding.nw331a.getId() :
                                binding.nw331b.getId());
            }

            if (!jsonB3.getnw332().equals("0")) {
                binding.nw332.check(
                        jsonB3.getnw332().equals("1") ? binding.nw332a.getId() :
                                jsonB3.getnw332().equals("2") ? binding.nw332b.getId() :
                                        jsonB3.getnw332().equals("3") ? binding.nw332c.getId() :
                                                binding.nw332d.getId()
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

                backPressed = true;

//                finish();

                startActivity(new Intent(this, SectionB4Activity.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void BtnEnd() {

        if (SectionB1Activity.editWRAFlag) {
            finish();
            startActivity(new Intent(this, ViewMemberActivity.class)
                    .putExtra("flagEdit", false)
                    .putExtra("comingBack", true)
                    .putExtra("cluster", MainApp.mc.getCluster())
                    .putExtra("hhno", MainApp.mc.getHhno())
            );
        } else {
            MainApp.endActivityMother(this, this, false);
        }
    }


    public boolean formValidation() {

        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

//        nw327
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw327, binding.nw327a, getString(R.string.nw327))) {
            return false;
        }

        if (!binding.nw32798.isChecked()) {

            if (binding.nw327a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.nw327m, getString(R.string.nw327))) {
                    return false;
                }
            } else if (binding.nw327b.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.nw327d, getString(R.string.nw327))) {
                    return false;
                }
            }

            //if (!binding.nw32798.isChecked()) {
            // nw328
            if (!ValidatorClass.EmptyRadioButton(this, binding.nw328, binding.nw328a, getString(R.string.nw328))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, binding.nw328, binding.nw32896, binding.nw32896x, getString(R.string.nw328))) {
                return false;
            }
            // nw329
            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw329, binding.nw329a, getString(R.string.nw329))) {
                return false;
            }
            // nw330
            if (!ValidatorClass.EmptyRadioButton(this, binding.nw330, binding.nw330a, getString(R.string.nw330))) {
                return false;
            }

            if (!binding.nw330d.isChecked()) {
                // nw331
                if (!ValidatorClass.EmptyRadioButton(this, binding.nw331, binding.nw331b, getString(R.string.nw331))) {
                    return false;
                }

                if (binding.nw331a.isChecked()) {
                    // nw332
                    return ValidatorClass.EmptyRadioButton(this, binding.nw332, binding.nw332a, getString(R.string.nw332));
                }
            }
        }

        //}

        return true;
    }


    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sB3 = new JSONObject();

        if (backPressed) {
            sB3.put("updatedate_nw3b", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        sB3.put("nw327", binding.nw327a.isChecked() ? "1"
                : binding.nw327b.isChecked() ? "2"
                : binding.nw32798.isChecked() ? "98"
                : "0");
        sB3.put("nw327m", binding.nw327m.getText().toString());
        sB3.put("nw327d", binding.nw327d.getText().toString());

        sB3.put("nw328", binding.nw328a.isChecked() ? "1"
                : binding.nw328b.isChecked() ? "2"
                : binding.nw328c.isChecked() ? "3"
                : binding.nw328d.isChecked() ? "4"
                : binding.nw328e.isChecked() ? "5"
                : binding.nw32896.isChecked() ? "96"
                : "0");

        sB3.put("nw32896x", binding.nw32896x.getText().toString());

        sB3.put("nw329a", binding.nw329a.isChecked() ? "1" : "0");
        sB3.put("nw329b", binding.nw329b.isChecked() ? "2" : "0");
        sB3.put("nw329c", binding.nw329c.isChecked() ? "3" : "0");
        sB3.put("nw329d", binding.nw329d.isChecked() ? "4" : "0");
        sB3.put("nw329e", binding.nw329e.isChecked() ? "5" : "0");
        sB3.put("nw329f", binding.nw329f.isChecked() ? "6" : "0");
        sB3.put("nw329g", binding.nw329g.isChecked() ? "7" : "0");
        sB3.put("nw329h", binding.nw329h.isChecked() ? "8" : "0");


        sB3.put("nw330", binding.nw330a.isChecked() ? "1"
                : binding.nw330b.isChecked() ? "2"
                : binding.nw330c.isChecked() ? "3"
                : binding.nw330d.isChecked() ? "4"
                : "0");

        sB3.put("nw331", binding.nw331a.isChecked() ? "1"
                : binding.nw331b.isChecked() ? "2"
                : "0");

        sB3.put("nw332", binding.nw332a.isChecked() ? "1"
                : binding.nw332b.isChecked() ? "2"
                : binding.nw332c.isChecked() ? "3"
                : binding.nw332d.isChecked() ? "4"
                : "0");

        MainApp.mc.setsB3(String.valueOf(sB3));

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSB3();

        if (updcount == 1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

        //return true;

    }

    @Override
    public void onBackPressed() {

        try {
            SaveDraft();
            UpdateDB();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

