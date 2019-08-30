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

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONB5ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.MWRAContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionB5Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD.SectionD2AActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD.SectionD3AActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

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

        binding.ciw414.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                formValidation();
                if (i == R.id.ciw414b) {

                    ClearClass.ClearAllFields(binding.fldGrpciw415, null);
                    binding.fldGrpciw415.setVisibility(View.GONE);


                } else {
                    binding.fldGrpciw415.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.ciw419.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                formValidation();
                if (i == R.id.ciw419b) {

                    ClearClass.ClearAllFields(binding.fldGrpciw420, null);
                    binding.fldGrpciw420.setVisibility(View.GONE);
                } else {

                    binding.fldGrpciw420.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.ciw416.setOnCheckedChangeListener(this);
        binding.ciw417.addTextChangedListener(this);
        binding.ciw421.setOnCheckedChangeListener(this);
        binding.ciw422.addTextChangedListener(this);


//        Validation Boolean
        MainApp.validateFlag = false;

        AutoCompleteFields();

    }

    public void AutoCompleteFields() {

//         Back Pressed
        MWRAContract mwraContract = db.getsB5();

        if (!mwraContract.getsB5().equals("")) {

            JSONB5ModelClass jsonB5 = JSONUtilClass.getModelFromJSON(mwraContract.getsB5(), JSONB5ModelClass.class);

            if (!jsonB5.getciw414().equals("0")) {
                binding.ciw414.check(
                        jsonB5.getciw414().equals("1") ? binding.ciw414a.getId() :
                                binding.ciw414b.getId());
            }

            if (!jsonB5.getciw415a().equals("0")) {
                binding.ciw415a.setChecked(true);
            }
            if (!jsonB5.getciw415b().equals("0")) {
                binding.ciw415b.setChecked(true);
            }
            if (!jsonB5.getciw415c().equals("0")) {
                binding.ciw415c.setChecked(true);
            }
            if (!jsonB5.getciw415d().equals("0")) {
                binding.ciw415d.setChecked(true);
            }
            if (!jsonB5.getciw415e().equals("0")) {
                binding.ciw415e.setChecked(true);
            }
            if (!jsonB5.getciw415f().equals("0")) {
                binding.ciw415f.setChecked(true);
            }
            if (!jsonB5.getciw415g().equals("0")) {
                binding.ciw415g.setChecked(true);
            }
            if (!jsonB5.getciw41596().equals("0")) {
                binding.ciw41596.setChecked(true);
                binding.ciw41596x.setText(jsonB5.getciw41596x());
            }

            if (!jsonB5.getciw416().equals("0")) {
                binding.ciw416.check(
                        jsonB5.getciw416().equals("1") ? binding.ciw416a.getId()
                                : jsonB5.getciw416().equals("2") ? binding.ciw416b.getId()
                                : jsonB5.getciw416().equals("3") ? binding.ciw416c.getId()
                                : binding.ciw41698.getId());
            }

            binding.ciw416hr.setText(jsonB5.getciw416hr());
            binding.ciw416d.setText(jsonB5.getciw416d());
            binding.ciw416w.setText(jsonB5.getciw416w());

            binding.ciw417.setText(jsonB5.getciw417());

            if (!jsonB5.getciw418a().equals("0")) {
                binding.ciw418a.setChecked(true);
            }
            if (!jsonB5.getciw418b().equals("0")) {
                binding.ciw418b.setChecked(true);
            }
            if (!jsonB5.getciw418c().equals("0")) {
                binding.ciw418c.setChecked(true);
            }
            if (!jsonB5.getciw418d().equals("0")) {
                binding.ciw418d.setChecked(true);
            }
            if (!jsonB5.getciw418e().equals("0")) {
                binding.ciw418e.setChecked(true);
            }
            if (!jsonB5.getciw418f().equals("0")) {
                binding.ciw418f.setChecked(true);
            }
            if (!jsonB5.getciw418g().equals("0")) {
                binding.ciw418g.setChecked(true);
            }
            if (!jsonB5.getciw41896().equals("0")) {
                binding.ciw41896.setChecked(true);
                binding.ciw41896x.setText(jsonB5.getciw41896x());
            }

            if (!jsonB5.getciw419().equals("0")) {
                binding.ciw419.check(
                        jsonB5.getciw419().equals("1") ? binding.ciw419a.getId() :
                                binding.ciw419b.getId());
            }

            if (!jsonB5.getciw420a().equals("0")) {
                binding.ciw420a.setChecked(true);
            }
            if (!jsonB5.getciw420b().equals("0")) {
                binding.ciw420b.setChecked(true);
            }
            if (!jsonB5.getciw420c().equals("0")) {
                binding.ciw420c.setChecked(true);
            }
            if (!jsonB5.getciw420d().equals("0")) {
                binding.ciw420d.setChecked(true);
            }
            if (!jsonB5.getciw420e().equals("0")) {
                binding.ciw420e.setChecked(true);
            }
            if (!jsonB5.getciw420f().equals("0")) {
                binding.ciw420f.setChecked(true);
            }
            if (!jsonB5.getciw420g().equals("0")) {
                binding.ciw420g.setChecked(true);
            }
            if (!jsonB5.getciw42096().equals("0")) {
                binding.ciw42096.setChecked(true);
                binding.ciw42096x.setText(jsonB5.getciw42096x());
            }

            if (!jsonB5.getciw421().equals("0")) {
                binding.ciw421.check(
                        jsonB5.getciw421().equals("1") ? binding.ciw421a.getId()
                                : jsonB5.getciw421().equals("2") ? binding.ciw421b.getId()
                                : jsonB5.getciw421().equals("3") ? binding.ciw421c.getId()
                                : binding.ciw42198.getId());
            }

            binding.ciw421hr.setText(jsonB5.getciw421hr());
            binding.ciw421d.setText(jsonB5.getciw421d());
            binding.ciw421w.setText(jsonB5.getciw421w());

            binding.ciw422.setText(jsonB5.getciw422());

            if (!jsonB5.getciw423a().equals("0")) {
                binding.ciw423a.setChecked(true);
            }
            if (!jsonB5.getciw423b().equals("0")) {
                binding.ciw423b.setChecked(true);
            }
            if (!jsonB5.getciw423c().equals("0")) {
                binding.ciw423c.setChecked(true);
            }
            if (!jsonB5.getciw423d().equals("0")) {
                binding.ciw423d.setChecked(true);
            }
            if (!jsonB5.getciw423e().equals("0")) {
                binding.ciw423e.setChecked(true);
            }
            if (!jsonB5.getciw42396().equals("0")) {
                binding.ciw42396.setChecked(true);
                binding.ciw42396x.setText(jsonB5.getciw42396x());
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

                if (MainApp.mc.getKishSelectWRA()) {
                    startActivity(new Intent(this, SectionB6Activity.class));
                } else if (MainApp.mc.getKishSelectMWRA()) {
                    finish();
                    if (SectionB1Activity.isCurrentlyPreg)
                        startActivity(new Intent(this, SectionD2AActivity.class));
                    else
                        startActivity(new Intent(this, SectionD3AActivity.class));
                } else {
                    startActivity(new Intent(this, MotherEndingActivity.class)
                            .putExtra("complete", true));
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

    private boolean formValidation() {


        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw414, binding.ciw414a, getString(R.string.ciw414))) {
            return false;
        }

        if (binding.ciw414a.isChecked()) {
            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw415check, binding.ciw415a, getString(R.string.ciw415))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw415check, binding.ciw41596, binding.ciw41596x, getString(R.string.ciw415) + " - " + getString(R.string.other))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, binding.ciw416, binding.ciw416a, getString(R.string.ciw416))) {
                return false;
            }

            if (binding.ciw416a.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.ciw416hr, getString(R.string.ciw416a))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.ciw416hr, 1, 23, getString(R.string.ciw416a), " hours")) {
                    return false;
                }

            }

            if (binding.ciw416b.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.ciw416d, getString(R.string.ciw416b))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.ciw416d, 1, 29, getString(R.string.ciw416b), " days")) {
                    return false;
                }

            }

            if (binding.ciw416c.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.ciw416w, getString(R.string.ciw416c))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.ciw416w, 1, 29, getString(R.string.ciw416c), " weeks")) {
                    return false;
                }

            }


            if (!ValidatorClass.EmptyTextBox(this, binding.ciw417, getString(R.string.ciw417))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.ciw417, 1, 12, getString(R.string.ciw417), " times")) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw418check, binding.ciw418a, getString(R.string.ciw418))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw418check, binding.ciw41896, binding.ciw41896x, getString(R.string.ciw418) + " - " + getString(R.string.other))) {
                return false;
            }

        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw419, binding.ciw419a, getString(R.string.ciw419))) {
            return false;
        }


        if (binding.ciw419a.isChecked()) {

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw420check, binding.ciw420a, getString(R.string.ciw420))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw420check, binding.ciw42096, binding.ciw42096x, getString(R.string.ciw418) + " - " + getString(R.string.other))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, binding.ciw421, binding.ciw421a, getString(R.string.ciw421))) {
                return false;
            }

            if (binding.ciw421a.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.ciw421hr, getString(R.string.ciw421a))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.ciw421hr, 1, 23, getString(R.string.ciw421a), " hours")) {
                    return false;
                }


            }


            if (binding.ciw421b.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.ciw421d, getString(R.string.ciw421b))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.ciw421d, 1, 29, getString(R.string.ciw421b), " days")) {
                    return false;
                }

            }

            if (binding.ciw421c.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.ciw421w, getString(R.string.ciw421c))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.ciw421w, 1, 29, getString(R.string.ciw421c), " weeks")) {
                    return false;
                }

            }


            if (!ValidatorClass.EmptyTextBox(this, binding.ciw422, getString(R.string.ciw422))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.ciw422, 1, 12, getString(R.string.ciw422), " times")) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw423check, binding.ciw423a, getString(R.string.ciw423))) {
                return false;
            }

            return ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw423check, binding.ciw42396, binding.ciw42396x, getString(R.string.ciw423) + " - " + getString(R.string.other));


        }

        return true;
    }

    private void SaveDraft() throws JSONException {


        JSONObject sB5 = new JSONObject();

        if (backPressed) {
            sB5.put("updatedate_ciw4b", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

//        ciw414
        sB5.put("ciw414", binding.ciw414a.isChecked() ? "1"
                : binding.ciw414b.isChecked() ? "2"
                : "0");
//      ciw415
        sB5.put("ciw415a", binding.ciw415a.isChecked() ? "1" : "0");
        sB5.put("ciw415b", binding.ciw415b.isChecked() ? "2" : "0");
        sB5.put("ciw415c", binding.ciw415c.isChecked() ? "3" : "0");
        sB5.put("ciw415d", binding.ciw415d.isChecked() ? "4" : "0");
        sB5.put("ciw415e", binding.ciw415e.isChecked() ? "5" : "0");
        sB5.put("ciw415f", binding.ciw415f.isChecked() ? "6" : "0");
        sB5.put("ciw415g", binding.ciw415g.isChecked() ? "7" : "0");
        sB5.put("ciw41596", binding.ciw41596.isChecked() ? "96" : "0");
        sB5.put("ciw41596x", binding.ciw41596x.getText().toString());

//      ciw416
        sB5.put("ciw416", binding.ciw416a.isChecked() ? "1"
                : binding.ciw416b.isChecked() ? "2"
                : binding.ciw416c.isChecked() ? "3"
                : binding.ciw41698.isChecked() ? "98"
                : "0");
        sB5.put("ciw416hr", binding.ciw416hr.getText().toString());
        sB5.put("ciw416d", binding.ciw416d.getText().toString());
        sB5.put("ciw416w", binding.ciw416w.getText().toString());

//        ciw417
        sB5.put("ciw417", binding.ciw417.getText().toString());

//        ciw418
        sB5.put("ciw418a", binding.ciw418a.isChecked() ? "1" : "0");
        sB5.put("ciw418b", binding.ciw418b.isChecked() ? "2" : "0");
        sB5.put("ciw418c", binding.ciw418c.isChecked() ? "3" : "0");
        sB5.put("ciw418d", binding.ciw418d.isChecked() ? "4" : "0");
        sB5.put("ciw418e", binding.ciw418e.isChecked() ? "5" : "0");
        sB5.put("ciw418f", binding.ciw418f.isChecked() ? "6" : "0");
        sB5.put("ciw418g", binding.ciw418g.isChecked() ? "7" : "0");
        sB5.put("ciw418h", binding.ciw418h.isChecked() ? "8" : "0");
        sB5.put("ciw41896", binding.ciw41896.isChecked() ? "96" : "0");
        sB5.put("ciw41896x", binding.ciw41896x.getText().toString());

//        ciw419
        sB5.put("ciw419", binding.ciw419a.isChecked() ? "1"
                : binding.ciw419b.isChecked() ? "2"
                : "0");
//        ciw420
        sB5.put("ciw420a", binding.ciw420a.isChecked() ? "1" : "0");
        sB5.put("ciw420b", binding.ciw420b.isChecked() ? "2" : "0");
        sB5.put("ciw420c", binding.ciw420c.isChecked() ? "3" : "0");
        sB5.put("ciw420d", binding.ciw420d.isChecked() ? "4" : "0");
        sB5.put("ciw420e", binding.ciw420e.isChecked() ? "5" : "0");
        sB5.put("ciw420f", binding.ciw420f.isChecked() ? "6" : "0");
        sB5.put("ciw420g", binding.ciw420g.isChecked() ? "7" : "0");
        sB5.put("ciw42096", binding.ciw42096.isChecked() ? "96" : "0");
        sB5.put("ciw42096x", binding.ciw42096x.getText().toString());


//        ciw421
        sB5.put("ciw421", binding.ciw421a.isChecked() ? "1"
                : binding.ciw421b.isChecked() ? "2"
                : binding.ciw421c.isChecked() ? "3"
                : binding.ciw42198.isChecked() ? "98"
                : "0");
        sB5.put("ciw421hr", binding.ciw421hr.getText().toString());
        sB5.put("ciw421d", binding.ciw421d.getText().toString());
        sB5.put("ciw421w", binding.ciw421w.getText().toString());

//        ciw422
        sB5.put("ciw422", binding.ciw422.getText().toString());

//        ciw423
        sB5.put("ciw423a", binding.ciw423a.isChecked() ? "1" : "0");
        sB5.put("ciw423b", binding.ciw423b.isChecked() ? "2" : "0");
        sB5.put("ciw423c", binding.ciw423c.isChecked() ? "3" : "0");
        sB5.put("ciw423d", binding.ciw423d.isChecked() ? "4" : "0");
        sB5.put("ciw423e", binding.ciw423e.isChecked() ? "5" : "0");
        sB5.put("ciw42396", binding.ciw42396.isChecked() ? "96" : "0");
        sB5.put("ciw42396x", binding.ciw42396x.getText().toString());

        MainApp.mc.setsB5(String.valueOf(sB5));

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSB5();

        if (updcount == 1) {

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

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
