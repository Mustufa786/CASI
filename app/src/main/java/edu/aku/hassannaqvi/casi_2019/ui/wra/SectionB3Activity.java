package edu.aku.hassannaqvi.casi_2019.ui.wra;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONB3ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.MWRAContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionB3Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

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
        binding.ciw327.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                formValidation();
                if (i == R.id.ciw32798) {
                    ClearClass.ClearAllFields(binding.fldGrciw328, null);
                    binding.fldGrciw328.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpciw329, null);
                    binding.fldGrpciw329.setVisibility(View.GONE);
                } else {
                    binding.fldGrciw328.setVisibility(View.VISIBLE);
                    binding.fldGrpciw329.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.ciw330.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                formValidation();
                if (i == R.id.ciw330d) {
                    binding.fldGrciw331.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrciw331, null);

                } else {
                    binding.fldGrciw331.setVisibility(View.VISIBLE);

                }
            }
        });

        binding.ciw331.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                formValidation();
                if (i == R.id.ciw331b) {
                    ClearClass.ClearAllFields(binding.fldGrciw332, null);
                    binding.fldGrciw332.setVisibility(View.GONE);

//                    binding.ciw332.clearCheck();
                } else {
                    binding.fldGrciw332.setVisibility(View.VISIBLE);


                }
            }
        });

//        Listener
        //binding.ciw328.setOnCheckedChangeListener(this);
        binding.ciw332.setOnCheckedChangeListener(this);

//        Validation Boolean
        MainApp.validateFlag = false;

        AutoCompleteFields();

    }

    public void AutoCompleteFields() {
        MWRAContract mwraContract = db.getsB3();
        if (!mwraContract.getsB3().equals("")) {

            JSONB3ModelClass jsonB3 = JSONUtilClass.getModelFromJSON(mwraContract.getsB3(), JSONB3ModelClass.class);

            if (!jsonB3.getciw327().equals("0")) {
                binding.ciw327.check(
                        jsonB3.getciw327().equals("1") ? binding.ciw327a.getId() :
                                jsonB3.getciw327().equals("2") ? binding.ciw327b.getId() :
                                        binding.ciw32798.getId());
            }
            binding.ciw327m.setText(jsonB3.getciw327m());
            binding.ciw327d.setText(jsonB3.getciw327d());

            if (!jsonB3.getciw328().equals("0")) {
                binding.ciw328.check(
                        jsonB3.getciw328().equals("1") ? binding.ciw328a.getId() :
                                jsonB3.getciw328().equals("2") ? binding.ciw328b.getId() :
                                        jsonB3.getciw328().equals("3") ? binding.ciw328c.getId() :
                                                jsonB3.getciw328().equals("4") ? binding.ciw328d.getId() :
                                                        jsonB3.getciw328().equals("5") ? binding.ciw328e.getId() :
                                                                binding.ciw32896.getId()
                );
                binding.ciw32896x.setText(jsonB3.getciw32896x());
            }

            if (!jsonB3.getciw329a().equals("0")) {
                binding.ciw329a.setChecked(true);
            }
            if (!jsonB3.getciw329b().equals("0")) {
                binding.ciw329b.setChecked(true);
            }
            if (!jsonB3.getciw329c().equals("0")) {
                binding.ciw329c.setChecked(true);
            }
            if (!jsonB3.getciw329d().equals("0")) {
                binding.ciw329d.setChecked(true);
            }
            if (!jsonB3.getciw329e().equals("0")) {
                binding.ciw329e.setChecked(true);
            }
            if (!jsonB3.getciw329f().equals("0")) {
                binding.ciw329f.setChecked(true);
            }
            if (!jsonB3.getciw329g().equals("0")) {
                binding.ciw329g.setChecked(true);
            }
            if (!jsonB3.getciw329h().equals("0")) {
                binding.ciw329h.setChecked(true);
            }

            if (!jsonB3.getciw330().equals("0")) {
                binding.ciw330.check(
                        jsonB3.getciw330().equals("1") ? binding.ciw330a.getId() :
                                jsonB3.getciw330().equals("2") ? binding.ciw330b.getId() :
                                        jsonB3.getciw330().equals("3") ? binding.ciw330c.getId() :
                                                binding.ciw330d.getId()
                );
            }

            if (!jsonB3.getciw331().equals("0")) {
                binding.ciw331.check(
                        jsonB3.getciw331().equals("1") ? binding.ciw331a.getId() :
                                binding.ciw331b.getId());
            }

            if (!jsonB3.getciw332().equals("0")) {
                binding.ciw332.check(
                        jsonB3.getciw332().equals("1") ? binding.ciw332a.getId() :
                                jsonB3.getciw332().equals("2") ? binding.ciw332b.getId() :
                                        jsonB3.getciw332().equals("3") ? binding.ciw332c.getId() :
                                                binding.ciw332d.getId()
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


//        ciw327
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw327, binding.ciw327a, getString(R.string.ciw327))) {
            return false;
        }

        if (!binding.ciw32798.isChecked()) {

            if (binding.ciw327a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.ciw327m, getString(R.string.ciw327))) {
                    return false;
                }
            } else if (binding.ciw327b.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.ciw327d, getString(R.string.ciw327))) {
                    return false;
                }
            }

            //if (!binding.ciw32798.isChecked()) {
            // ciw328
            if (!ValidatorClass.EmptyRadioButton(this, binding.ciw328, binding.ciw328a, getString(R.string.ciw328))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, binding.ciw328, binding.ciw32896, binding.ciw32896x, getString(R.string.ciw328))) {
                return false;
            }
            // ciw329
            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw329, binding.ciw329a, getString(R.string.ciw329))) {
                return false;
            }
            // ciw330
            if (!ValidatorClass.EmptyRadioButton(this, binding.ciw330, binding.ciw330a, getString(R.string.ciw330))) {
                return false;
            }

            if (!binding.ciw330d.isChecked()) {
                // ciw331
                if (!ValidatorClass.EmptyRadioButton(this, binding.ciw331, binding.ciw331b, getString(R.string.ciw331))) {
                    return false;
                }

                if (binding.ciw331a.isChecked()) {
                    // ciw332
                    return ValidatorClass.EmptyRadioButton(this, binding.ciw332, binding.ciw332a, getString(R.string.ciw332));
                }
            }
        }

        //}

        return true;
    }


    private void SaveDraft() throws JSONException {


        JSONObject sB3 = new JSONObject();

        if (backPressed) {
            sB3.put("updatedate_ciw3b", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        sB3.put("ciw327", binding.ciw327a.isChecked() ? "1"
                : binding.ciw327b.isChecked() ? "2"
                : binding.ciw32798.isChecked() ? "98"
                : "0");
        sB3.put("ciw327m", binding.ciw327m.getText().toString());
        sB3.put("ciw327d", binding.ciw327d.getText().toString());

        sB3.put("ciw328", binding.ciw328a.isChecked() ? "1"
                : binding.ciw328b.isChecked() ? "2"
                : binding.ciw328c.isChecked() ? "3"
                : binding.ciw328d.isChecked() ? "4"
                : binding.ciw328e.isChecked() ? "5"
                : binding.ciw32896.isChecked() ? "96"
                : "0");

        sB3.put("ciw32896x", binding.ciw32896x.getText().toString());

        sB3.put("ciw329a", binding.ciw329a.isChecked() ? "1" : "0");
        sB3.put("ciw329b", binding.ciw329b.isChecked() ? "2" : "0");
        sB3.put("ciw329c", binding.ciw329c.isChecked() ? "3" : "0");
        sB3.put("ciw329d", binding.ciw329d.isChecked() ? "4" : "0");
        sB3.put("ciw329e", binding.ciw329e.isChecked() ? "5" : "0");
        sB3.put("ciw329f", binding.ciw329f.isChecked() ? "6" : "0");
        sB3.put("ciw329g", binding.ciw329g.isChecked() ? "7" : "0");
        sB3.put("ciw329h", binding.ciw329h.isChecked() ? "8" : "0");


        sB3.put("ciw330", binding.ciw330a.isChecked() ? "1"
                : binding.ciw330b.isChecked() ? "2"
                : binding.ciw330c.isChecked() ? "3"
                : binding.ciw330d.isChecked() ? "4"
                : "0");

        sB3.put("ciw331", binding.ciw331a.isChecked() ? "1"
                : binding.ciw331b.isChecked() ? "2"
                : "0");

        sB3.put("ciw332", binding.ciw332a.isChecked() ? "1"
                : binding.ciw332b.isChecked() ? "2"
                : binding.ciw332c.isChecked() ? "3"
                : binding.ciw332d.isChecked() ? "4"
                : "0");

        MainApp.mc.setsB3(String.valueOf(sB3));

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSB3();

        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

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

}

