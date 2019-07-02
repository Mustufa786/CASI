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

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONB5ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.MWRAContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionB5Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionB5Activity extends Menu2Activity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private final long DELAY = 1000;
    ActivitySectionB5Binding binding;
    DatabaseHelper db;
    Boolean backPressed = false;
    Boolean frontPressed = false;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_b5);
        db = new DatabaseHelper(this);

//        Assigning data to UI binding
        binding.setCallback(this);

        this.setTitle(getResources().getString(R.string.nb4heading));
        binding.textName.setText("Selected Woman : " + SectionB1Activity.wraName);

//        Skip patterns

        binding.nw414.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ValidateForm();
                if (i == R.id.nw414b) {

                    ClearClass.ClearAllFields(binding.fldGrpnw415, false);

                } else {
                    ClearClass.ClearAllFields(binding.fldGrpnw415, true);
                }
            }
        });

        binding.nw419.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ValidateForm();
                if (i == R.id.nw419b) {

                    ClearClass.ClearAllFields(binding.fldGrpnw420, false);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpnw420, true);
                }
            }
        });

        binding.nw416.setOnCheckedChangeListener(this);
        binding.nw417.addTextChangedListener(this);
        binding.nw421.setOnCheckedChangeListener(this);
        binding.nw422.addTextChangedListener(this);


//        Validation Boolean
        MainApp.validateFlag = false;

        AutoCompleteFields();

    }

    public void AutoCompleteFields() {

//         Back Pressed
        MWRAContract mwraContract = db.getsB5();

        if (!mwraContract.getsB5().equals("")) {

            JSONB5ModelClass jsonB5 = JSONUtilClass.getModelFromJSON(mwraContract.getsB5(), JSONB5ModelClass.class);

            if (!jsonB5.getnw414().equals("0")) {
                binding.nw414.check(
                        jsonB5.getnw414().equals("1") ? binding.nw414a.getId() :
                                binding.nw414b.getId());
            }

            if (!jsonB5.getnw415a().equals("0")) {
                binding.nw415a.setChecked(true);
            }
            if (!jsonB5.getnw415b().equals("0")) {
                binding.nw415b.setChecked(true);
            }
            if (!jsonB5.getnw415c().equals("0")) {
                binding.nw415c.setChecked(true);
            }
            if (!jsonB5.getnw415d().equals("0")) {
                binding.nw415d.setChecked(true);
            }
            if (!jsonB5.getnw415e().equals("0")) {
                binding.nw415e.setChecked(true);
            }
            if (!jsonB5.getnw415f().equals("0")) {
                binding.nw415f.setChecked(true);
            }
            if (!jsonB5.getnw415g().equals("0")) {
                binding.nw415g.setChecked(true);
            }
            if (!jsonB5.getnw41596().equals("0")) {
                binding.nw41596.setChecked(true);
                binding.nw41596x.setText(jsonB5.getnw41596x());
            }

            if (!jsonB5.getnw416().equals("0")) {
                binding.nw416.check(
                        jsonB5.getnw416().equals("1") ? binding.nw416a.getId()
                                : jsonB5.getnw416().equals("2") ? binding.nw416b.getId()
                                : jsonB5.getnw416().equals("3") ? binding.nw416c.getId()
                                : binding.nw41698.getId());
            }

            binding.nw416hr.setText(jsonB5.getnw416hr());
            binding.nw416d.setText(jsonB5.getnw416d());
            binding.nw416w.setText(jsonB5.getnw416w());

            binding.nw417.setText(jsonB5.getnw417());

            if (!jsonB5.getnw418a().equals("0")) {
                binding.nw418a.setChecked(true);
            }
            if (!jsonB5.getnw418b().equals("0")) {
                binding.nw418b.setChecked(true);
            }
            if (!jsonB5.getnw418c().equals("0")) {
                binding.nw418c.setChecked(true);
            }
            if (!jsonB5.getnw418d().equals("0")) {
                binding.nw418d.setChecked(true);
            }
            if (!jsonB5.getnw418e().equals("0")) {
                binding.nw418e.setChecked(true);
            }
            if (!jsonB5.getnw418f().equals("0")) {
                binding.nw418f.setChecked(true);
            }
            if (!jsonB5.getnw418g().equals("0")) {
                binding.nw418g.setChecked(true);
            }
            if (!jsonB5.getnw41896().equals("0")) {
                binding.nw41896.setChecked(true);
                binding.nw41896x.setText(jsonB5.getnw41896x());
            }

            if (!jsonB5.getnw419().equals("0")) {
                binding.nw419.check(
                        jsonB5.getnw419().equals("1") ? binding.nw419a.getId() :
                                binding.nw419b.getId());
            }

            if (!jsonB5.getnw420a().equals("0")) {
                binding.nw420a.setChecked(true);
            }
            if (!jsonB5.getnw420b().equals("0")) {
                binding.nw420b.setChecked(true);
            }
            if (!jsonB5.getnw420c().equals("0")) {
                binding.nw420c.setChecked(true);
            }
            if (!jsonB5.getnw420d().equals("0")) {
                binding.nw420d.setChecked(true);
            }
            if (!jsonB5.getnw420e().equals("0")) {
                binding.nw420e.setChecked(true);
            }
            if (!jsonB5.getnw420f().equals("0")) {
                binding.nw420f.setChecked(true);
            }
            if (!jsonB5.getnw420g().equals("0")) {
                binding.nw420g.setChecked(true);
            }
            if (!jsonB5.getnw42096().equals("0")) {
                binding.nw42096.setChecked(true);
                binding.nw42096x.setText(jsonB5.getnw42096x());
            }

            if (!jsonB5.getnw421().equals("0")) {
                binding.nw421.check(
                        jsonB5.getnw421().equals("1") ? binding.nw421a.getId()
                                : jsonB5.getnw421().equals("2") ? binding.nw421b.getId()
                                : jsonB5.getnw421().equals("3") ? binding.nw421c.getId()
                                : binding.nw42198.getId());
            }

            binding.nw421hr.setText(jsonB5.getnw421hr());
            binding.nw421d.setText(jsonB5.getnw421d());
            binding.nw421w.setText(jsonB5.getnw421w());

            binding.nw422.setText(jsonB5.getnw422());

            if (!jsonB5.getnw423a().equals("0")) {
                binding.nw423a.setChecked(true);
            }
            if (!jsonB5.getnw423b().equals("0")) {
                binding.nw423b.setChecked(true);
            }
            if (!jsonB5.getnw423c().equals("0")) {
                binding.nw423c.setChecked(true);
            }
            if (!jsonB5.getnw423d().equals("0")) {
                binding.nw423d.setChecked(true);
            }
            if (!jsonB5.getnw423e().equals("0")) {
                binding.nw423e.setChecked(true);
            }
            if (!jsonB5.getnw42396().equals("0")) {
                binding.nw42396.setChecked(true);
                binding.nw42396x.setText(jsonB5.getnw42396x());
            }

        }
    }

    public void BtnContinue() {

//        Validation Boolean
        MainApp.validateFlag = true;

        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

                MainApp.nuCount = 1;

                frontPressed = true;

//                finish();

                if (SectionB1Activity.editWRAFlag) {
                    /*if (!db.getNutritionCount()) {
                        startActivity(new Intent(this, SectionB6Activity.class)
                                .putExtra("backPressed", backPressed));
                    } else*/
                    if (MainApp.mc.getsB6().equals("1")) {
                        startActivity(new Intent(this, SectionB6Activity.class)
                                .putExtra("backPressed", backPressed));
                    } else {
                        finish();
                        startActivity(new Intent(this, ViewMemberActivity.class)
                                .putExtra("flagEdit", false)
                                .putExtra("comingBack", true)
                                .putExtra("cluster", MainApp.mc.getCluster())
                                .putExtra("hhno", MainApp.mc.getHhno())
                        );
                    }
                } else {
                    /*if (SectionB1Activity.WRAcounter == MainApp.mwra.size()
                            &&
                            MainApp.B6Flag) {*/
                    if (MainApp.mc.getsB6().equals("1")) {
                        startActivity(new Intent(this, SectionB6Activity.class)
                                .putExtra("backPressed", backPressed));
                    }/* else if (MainApp.B2B6Flag) {
                        startActivity(new Intent(this, SectionB6Activity.class)
                                .putExtra("backPressed", backPressed));
                    }*/ else {
                        startActivity(new Intent(this, MotherEndingActivity.class)
                                .putExtra("complete", true));
                    }
                }

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

    private boolean ValidateForm() {

        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (!ValidatorClass.EmptyRadioButton(this, binding.nw414, binding.nw414a, getString(R.string.nw414))) {
            return false;
        }

        if (binding.nw414a.isChecked()) {
            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw415check, binding.nw415a, getString(R.string.nw415))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw415check, binding.nw41596, binding.nw41596x, getString(R.string.nw415) + " - " + getString(R.string.other))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, binding.nw416, binding.nw416a, getString(R.string.nw416))) {
                return false;
            }

            if (binding.nw416a.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.nw416hr, getString(R.string.nw416a))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.nw416hr, 1, 23, getString(R.string.nw416a), " hours")) {
                    return false;
                }

            }

            if (binding.nw416b.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.nw416d, getString(R.string.nw416b))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.nw416d, 1, 29, getString(R.string.nw416b), " days")) {
                    return false;
                }

            }

            if (binding.nw416c.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.nw416w, getString(R.string.nw416c))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.nw416w, 1, 29, getString(R.string.nw416c), " weeks")) {
                    return false;
                }

            }


            if (!ValidatorClass.EmptyTextBox(this, binding.nw417, getString(R.string.nw417))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.nw417, 1, 12, getString(R.string.nw417), " times")) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw418check, binding.nw418a, getString(R.string.nw418))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw418check, binding.nw41896, binding.nw41896x, getString(R.string.nw418) + " - " + getString(R.string.other))) {
                return false;
            }

        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.nw419, binding.nw419a, getString(R.string.nw419))) {
            return false;
        }


        if (binding.nw419a.isChecked()) {

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw420check, binding.nw420a, getString(R.string.nw420))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw420check, binding.nw42096, binding.nw42096x, getString(R.string.nw418) + " - " + getString(R.string.other))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, binding.nw421, binding.nw421a, getString(R.string.nw421))) {
                return false;
            }

            if (binding.nw421a.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.nw421hr, getString(R.string.nw421a))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.nw421hr, 1, 23, getString(R.string.nw421a), " hours")) {
                    return false;
                }


            }


            if (binding.nw421b.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.nw421d, getString(R.string.nw421b))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.nw421d, 1, 29, getString(R.string.nw421b), " days")) {
                    return false;
                }

            }

            if (binding.nw421c.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.nw421w, getString(R.string.nw421c))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.nw421w, 1, 29, getString(R.string.nw421c), " weeks")) {
                    return false;
                }

            }


            if (!ValidatorClass.EmptyTextBox(this, binding.nw422, getString(R.string.nw422))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.nw422, 1, 12, getString(R.string.nw422), " times")) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw423check, binding.nw423a, getString(R.string.nw423))) {
                return false;
            }

            return ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw423check, binding.nw42396, binding.nw42396x, getString(R.string.nw423) + " - " + getString(R.string.other));


        }

        return true;
    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sB5 = new JSONObject();

        if (backPressed) {
            sB5.put("updatedate_nw4b", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

//        nw414
        sB5.put("nw414", binding.nw414a.isChecked() ? "1"
                : binding.nw414b.isChecked() ? "2"
                : "0");
//      nw415
        sB5.put("nw415a", binding.nw415a.isChecked() ? "1" : "0");
        sB5.put("nw415b", binding.nw415b.isChecked() ? "2" : "0");
        sB5.put("nw415c", binding.nw415c.isChecked() ? "3" : "0");
        sB5.put("nw415d", binding.nw415d.isChecked() ? "4" : "0");
        sB5.put("nw415e", binding.nw415e.isChecked() ? "5" : "0");
        sB5.put("nw415f", binding.nw415f.isChecked() ? "6" : "0");
        sB5.put("nw415g", binding.nw415g.isChecked() ? "7" : "0");
        sB5.put("nw41596", binding.nw41596.isChecked() ? "96" : "0");
        sB5.put("nw41596x", binding.nw41596x.getText().toString());

//      nw416
        sB5.put("nw416", binding.nw416a.isChecked() ? "1"
                : binding.nw416b.isChecked() ? "2"
                : binding.nw416c.isChecked() ? "3"
                : binding.nw41698.isChecked() ? "98"
                : "0");
        sB5.put("nw416hr", binding.nw416hr.getText().toString());
        sB5.put("nw416d", binding.nw416d.getText().toString());
        sB5.put("nw416w", binding.nw416w.getText().toString());

//        nw417
        sB5.put("nw417", binding.nw417.getText().toString());

//        nw418
        sB5.put("nw418a", binding.nw418a.isChecked() ? "1" : "0");
        sB5.put("nw418b", binding.nw418b.isChecked() ? "2" : "0");
        sB5.put("nw418c", binding.nw418c.isChecked() ? "3" : "0");
        sB5.put("nw418d", binding.nw418d.isChecked() ? "4" : "0");
        sB5.put("nw418e", binding.nw418e.isChecked() ? "5" : "0");
        sB5.put("nw418f", binding.nw418f.isChecked() ? "6" : "0");
        sB5.put("nw418g", binding.nw418g.isChecked() ? "7" : "0");
        sB5.put("nw418h", binding.nw418h.isChecked() ? "8" : "0");
        sB5.put("nw41896", binding.nw41896.isChecked() ? "96" : "0");
        sB5.put("nw41896x", binding.nw41896x.getText().toString());

//        nw419
        sB5.put("nw419", binding.nw419a.isChecked() ? "1"
                : binding.nw419b.isChecked() ? "2"
                : "0");
//        nw420
        sB5.put("nw420a", binding.nw420a.isChecked() ? "1" : "0");
        sB5.put("nw420b", binding.nw420b.isChecked() ? "2" : "0");
        sB5.put("nw420c", binding.nw420c.isChecked() ? "3" : "0");
        sB5.put("nw420d", binding.nw420d.isChecked() ? "4" : "0");
        sB5.put("nw420e", binding.nw420e.isChecked() ? "5" : "0");
        sB5.put("nw420f", binding.nw420f.isChecked() ? "6" : "0");
        sB5.put("nw420g", binding.nw420g.isChecked() ? "7" : "0");
        sB5.put("nw42096", binding.nw42096.isChecked() ? "96" : "0");
        sB5.put("nw42096x", binding.nw42096x.getText().toString());


//        nw421
        sB5.put("nw421", binding.nw421a.isChecked() ? "1"
                : binding.nw421b.isChecked() ? "2"
                : binding.nw421c.isChecked() ? "3"
                : binding.nw42198.isChecked() ? "98"
                : "0");
        sB5.put("nw421hr", binding.nw421hr.getText().toString());
        sB5.put("nw421d", binding.nw421d.getText().toString());
        sB5.put("nw421w", binding.nw421w.getText().toString());

//        nw422
        sB5.put("nw422", binding.nw422.getText().toString());

//        nw423
        sB5.put("nw423a", binding.nw423a.isChecked() ? "1" : "0");
        sB5.put("nw423b", binding.nw423b.isChecked() ? "2" : "0");
        sB5.put("nw423c", binding.nw423c.isChecked() ? "3" : "0");
        sB5.put("nw423d", binding.nw423d.isChecked() ? "4" : "0");
        sB5.put("nw423e", binding.nw423e.isChecked() ? "5" : "0");
        sB5.put("nw42396", binding.nw42396.isChecked() ? "96" : "0");
        sB5.put("nw42396x", binding.nw42396x.getText().toString());

        MainApp.mc.setsB5(String.valueOf(sB5));

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSB5();

        if (updcount == 1) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

        //return true;

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (frontPressed) {
            backPressed = true;
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
                                ValidateForm();
                            }
                        });

                    }
                },
                DELAY
        );
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ValidateForm();
    }


}
