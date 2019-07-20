package edu.aku.hassannaqvi.casi_2019.ui.household;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONA4ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionA402Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionA402Activity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, TextWatcher {

    ActivitySectionA402Binding binding;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_a402);
        binding.setCallback(this);
        db = new DatabaseHelper(this);

        this.setTitle(getResources().getString(R.string.cih3heading));

        setListeners();

        if (SectionA1Activity.editFormFlag) {
            autoPopulate();
        }
    }

    private void autoPopulate() {

        FormsContract formContract = db.getsA402();
        if (!formContract.getsA4().equals("")) {

            JSONA4ModelClass jsonA4 = JSONUtilClass.getModelFromJSON(formContract.getsA402(), JSONA4ModelClass.class);
            if (!jsonA4.getcih318().equals("0")) {
                binding.cih318.check(
                        jsonA4.getcih318().equals("1") ? binding.cih318a.getId() :
                                jsonA4.getcih318().equals("2") ? binding.cih318b.getId() :
                                        jsonA4.getcih318().equals("3") ? binding.cih318c.getId() :
                                                jsonA4.getcih318().equals("4") ? binding.cih318d.getId() :
                                                        jsonA4.getcih318().equals("5") ? binding.cih318e.getId() :
                                                                jsonA4.getcih318().equals("6") ? binding.cih318f.getId() :
                                                                        jsonA4.getcih318().equals("7") ? binding.cih318g.getId() :
                                                                                jsonA4.getcih318().equals("8") ? binding.cih318h.getId() :
                                                                                        jsonA4.getcih318().equals("9") ? binding.cih318i.getId() :
                                                                                                jsonA4.getcih318().equals("10") ? binding.cih318j.getId() :
                                                                                                        jsonA4.getcih318().equals("11") ? binding.cih318k.getId() :
                                                                                                                jsonA4.getcih318().equals("12") ? binding.cih318l.getId() :
                                                                                                                        binding.cih31896.getId()
                );
            }
            binding.cih31896x.setText(jsonA4.getcih31896x());
            if (!jsonA4.getcih319().equals("0")) {
                binding.cih319.check(
                        jsonA4.getcih319().equals("1") ? binding.cih319a.getId() :
                                jsonA4.getcih319().equals("2") ? binding.cih319b.getId() :
                                        jsonA4.getcih319().equals("3") ? binding.cih319c.getId() :
                                                jsonA4.getcih319().equals("4") ? binding.cih319d.getId() :
                                                        jsonA4.getcih319().equals("5") ? binding.cih319e.getId() :
                                                                jsonA4.getcih319().equals("6") ? binding.cih319f.getId() :
                                                                        jsonA4.getcih319().equals("7") ? binding.cih319g.getId() :
                                                                                jsonA4.getcih319().equals("8") ? binding.cih319h.getId() :
                                                                                        jsonA4.getcih319().equals("9") ? binding.cih319i.getId() :
                                                                                                jsonA4.getcih319().equals("10") ? binding.cih319j.getId() :
                                                                                                        jsonA4.getcih319().equals("11") ? binding.cih319k.getId() :
                                                                                                                jsonA4.getcih319().equals("12") ? binding.cih319l.getId() :
                                                                                                                        jsonA4.getcih319().equals("13") ? binding.cih319m.getId() :
                                                                                                                                jsonA4.getcih319().equals("14") ? binding.cih319n.getId() :
                                                                                                                                        jsonA4.getcih319().equals("15") ? binding.cih319o.getId() :
                                                                                                                                                jsonA4.getcih319().equals("16") ? binding.cih319p.getId() :
                                                                                                                                                        binding.cih31996.getId()
                );
            }
            binding.cih31996x.setText(jsonA4.getcih31996x());
            binding.cih320.setText(jsonA4.getcih320());
            if (!jsonA4.getcih321().equals("0")) {
                binding.cih321.check(
                        jsonA4.getcih321().equals("1") ? binding.cih321a.getId() : binding.cih321b.getId()
                );
            }
            if (!jsonA4.getcih322().equals("0")) {
                binding.cih322.check(
                        jsonA4.getcih322().equals("1") ? binding.cih322a.getId() :
                                jsonA4.getcih322().equals("2") ? binding.cih322b.getId() :
                                        binding.cih32298.getId()
                );
            }
            binding.cih322acr.setText(jsonA4.getcih322acr());
            binding.cih322can.setText(jsonA4.getcih322can());


            if (!jsonA4.getcih323().equals("0")) {
                binding.cih323.check(
                        jsonA4.getcih323().equals("1") ? binding.cih323a.getId() :
                                binding.cih323b.getId()
                );
            }

            binding.cih324a.setText(jsonA4.getcih324a());
            binding.cih324b.setText(jsonA4.getcih324b());
            binding.cih324c.setText(jsonA4.getcih324c());
            binding.cih324d.setText(jsonA4.getcih324d());
            binding.cih324e.setText(jsonA4.getcih324e());
            binding.cih324f.setText(jsonA4.getcih324f());
            binding.cih324g.setText(jsonA4.getcih324g());

        }
    }

    private void setListeners() {
        binding.cih318.setOnCheckedChangeListener(this);
        binding.cih319.setOnCheckedChangeListener(this);
        binding.cih320.addTextChangedListener(this);

//        cih321
        binding.cih321.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                formValidation();
                if (i == R.id.cih321b) {
                    ClearClass.ClearAllFields(binding.fldGrpcih322, false);
                    binding.fldGrpcih322.setVisibility(View.GONE);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpcih322, true);
                    binding.cih322acr.setEnabled(false);
                    binding.cih322can.setEnabled(false);
                    binding.fldGrpcih322.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.cih322.setOnCheckedChangeListener(this);

//        cih323
        binding.cih323.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                formValidation();
                if (i == R.id.cih323b) {
                    ClearClass.ClearAllFields(binding.fldGrpcih324, false);
                    binding.fldGrpcih324.setVisibility(View.GONE);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpcih324, true);
                    binding.fldGrpcih324.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.cih324a.addTextChangedListener(this);
        binding.cih324b.addTextChangedListener(this);
        binding.cih324c.addTextChangedListener(this);
        binding.cih324d.addTextChangedListener(this);
        binding.cih324e.addTextChangedListener(this);
        binding.cih324f.addTextChangedListener(this);
        binding.cih324g.addTextChangedListener(this);

    }

    private boolean formValidation() {


        if (!ValidatorClass.EmptyRadioButton(this, binding.cih318, binding.cih318a, getString(R.string.cih318))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih318, binding.cih31896, binding.cih31896x, getString(R.string.cih318))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih319, binding.cih319a, getString(R.string.cih319))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih319, binding.cih31996, binding.cih31996x, getString(R.string.cih319))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, binding.cih320, getString(R.string.cih320))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, binding.cih320, 1, 15, getString(R.string.cih320), "Room")) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih321, binding.cih321b, getString(R.string.cih321))) {
            return false;
        }

        if (binding.cih321a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.cih322, binding.cih322a, getString(R.string.cih322))) {
                return false;
            }

            if (binding.cih322a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.cih322acr, getString(R.string.cih322acr))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, binding.cih322acr, 1.00, 999.0, getString(R.string.cih322acr), "acre")) {
                    return false;
                }
            } else if (binding.cih322b.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.cih322can, getString(R.string.cih322can))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, binding.cih322can, 1.00, 999.0, getString(R.string.cih322can), "kanal")) {
                    return false;
                }
            }
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih323, binding.cih323b, getString(R.string.cih323))) {
            return false;
        }


        if (binding.cih323a.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, binding.cih324a, getString(R.string.cih324a))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.cih324a, 0, 999, getString(R.string.cih324a), "Animal")) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.cih324b, getString(R.string.cih324b))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, binding.cih324b, 0, 999, getString(R.string.cih324b), "Animal")) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.cih324c, getString(R.string.cih324c))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, binding.cih324c, 0, 999, getString(R.string.cih324c), "Animal")) {
                return false;
            }
            if (!ValidatorClass.EmptyTextBox(this, binding.cih324d, getString(R.string.cih324d))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, binding.cih324d, 0, 999, getString(R.string.cih324d), "Animal")) {
                return false;
            }
            if (!ValidatorClass.EmptyTextBox(this, binding.cih324e, getString(R.string.cih324e))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, binding.cih324e, 0, 999, getString(R.string.cih324e), "Animal")) {
                return false;
            }
            if (!ValidatorClass.EmptyTextBox(this, binding.cih324f, getString(R.string.cih324f))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, binding.cih324f, 0, 999, getString(R.string.cih324f), "Animal")) {
                return false;
            }
            if (!ValidatorClass.EmptyTextBox(this, binding.cih324g, getString(R.string.cih324g))) {
                return false;
            }
            return ValidatorClass.RangeTextBox(this, binding.cih324g, 0, 999, getString(R.string.cih324g), "Animal");
        }
        return true;
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


                finish();

                startActivity(new Intent(this, SectionA5Activity.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSA402();

        if (updcount == 1) {

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void SaveDraft() throws JSONException {
        JSONObject sA4 = new JSONObject();
        sA4.put("cih318", binding.cih318a.isChecked() ? "1"
                : binding.cih318b.isChecked() ? "2"
                : binding.cih318c.isChecked() ? "3"
                : binding.cih318d.isChecked() ? "4"
                : binding.cih318e.isChecked() ? "5"
                : binding.cih318f.isChecked() ? "6"
                : binding.cih318g.isChecked() ? "7"
                : binding.cih318h.isChecked() ? "8"
                : binding.cih318i.isChecked() ? "9"
                : binding.cih318j.isChecked() ? "10"
                : binding.cih318k.isChecked() ? "11"
                : binding.cih318l.isChecked() ? "12"
                : binding.cih318m.isChecked() ? "13"
                : binding.cih318n.isChecked() ? "14"
                : binding.cih31896.isChecked() ? "96"
                : "0");
        sA4.put("cih31896x", binding.cih31896x.getText().toString());

//          cih319
        sA4.put("cih319", binding.cih319a.isChecked() ? "1"
                : binding.cih319b.isChecked() ? "2"
                : binding.cih319c.isChecked() ? "3"
                : binding.cih319d.isChecked() ? "4"
                : binding.cih319e.isChecked() ? "5"
                : binding.cih319f.isChecked() ? "6"
                : binding.cih319g.isChecked() ? "7"
                : binding.cih319h.isChecked() ? "8"
                : binding.cih319i.isChecked() ? "9"
                : binding.cih319j.isChecked() ? "10"
                : binding.cih319k.isChecked() ? "11"
                : binding.cih319l.isChecked() ? "12"
                : binding.cih319m.isChecked() ? "13"
                : binding.cih319n.isChecked() ? "14"
                : binding.cih319o.isChecked() ? "15"
                : binding.cih319p.isChecked() ? "16"
                : binding.cih31996.isChecked() ? "96"
                : "0");
        sA4.put("cih31996x", binding.cih31996x.getText().toString());

//        cih320
        sA4.put("cih320", binding.cih320.getText().toString());

//        cih321
        sA4.put("cih321", binding.cih321a.isChecked() ? "1"
                : binding.cih321b.isChecked() ? "2"
                : "0");

//        cih322
        sA4.put("cih322", binding.cih322a.isChecked() ? "1"
                : binding.cih322b.isChecked() ? "2"
                : binding.cih32298.isChecked() ? "98"
                : "0");
        sA4.put("cih322acr", binding.cih322acr.getText().toString());
        sA4.put("cih322can", binding.cih322can.getText().toString());

//        cih323
        sA4.put("cih323", binding.cih323a.isChecked() ? "1"
                : binding.cih323b.isChecked() ? "2"
                : "0");
//        cih324
        sA4.put("cih324a", binding.cih324a.getText().toString());
        sA4.put("cih324b", binding.cih324b.getText().toString());
        sA4.put("cih324c", binding.cih324c.getText().toString());
        sA4.put("cih324d", binding.cih324d.getText().toString());
        sA4.put("cih324e", binding.cih324e.getText().toString());
        sA4.put("cih324f", binding.cih324f.getText().toString());
        sA4.put("cih324g", binding.cih324g.getText().toString());


        MainApp.fc.setsA402(sA4.toString());


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }

}
