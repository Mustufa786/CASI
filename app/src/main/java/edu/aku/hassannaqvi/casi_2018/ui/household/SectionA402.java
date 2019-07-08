package edu.aku.hassannaqvi.casi_2018.ui.household;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONA4ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionA402Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionA402 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, TextWatcher {

    ActivitySectionA402Binding binding;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_a402);
        db = new DatabaseHelper(this);

        setListeners();
        autoPopulate();
    }

    private void autoPopulate() {

        FormsContract formContract = db.getsA4();
        if (!formContract.getsA4().equals("")) {

            JSONA4ModelClass jsonA4 = JSONUtilClass.getModelFromJSON(formContract.getsA4(), JSONA4ModelClass.class);
            if (!jsonA4.getnh318().equals("0")) {
                binding.nh318.check(
                        jsonA4.getnh318().equals("1") ? binding.nh318a.getId() :
                                jsonA4.getnh318().equals("2") ? binding.nh318b.getId() :
                                        jsonA4.getnh318().equals("3") ? binding.nh318c.getId() :
                                                jsonA4.getnh318().equals("4") ? binding.nh318d.getId() :
                                                        jsonA4.getnh318().equals("5") ? binding.nh318e.getId() :
                                                                jsonA4.getnh318().equals("6") ? binding.nh318f.getId() :
                                                                        jsonA4.getnh318().equals("7") ? binding.nh318g.getId() :
                                                                                jsonA4.getnh318().equals("8") ? binding.nh318h.getId() :
                                                                                        jsonA4.getnh318().equals("9") ? binding.nh318i.getId() :
                                                                                                jsonA4.getnh318().equals("10") ? binding.nh318j.getId() :
                                                                                                        jsonA4.getnh318().equals("11") ? binding.nh318k.getId() :
                                                                                                                jsonA4.getnh318().equals("12") ? binding.nh318l.getId() :
                                                                                                                        binding.nh31896.getId()
                );
            }
            binding.nh31896x.setText(jsonA4.getnh31896x());
            if (!jsonA4.getnh319().equals("0")) {
                binding.nh319.check(
                        jsonA4.getnh319().equals("1") ? binding.nh319a.getId() :
                                jsonA4.getnh319().equals("2") ? binding.nh319b.getId() :
                                        jsonA4.getnh319().equals("3") ? binding.nh319c.getId() :
                                                jsonA4.getnh319().equals("4") ? binding.nh319d.getId() :
                                                        jsonA4.getnh319().equals("5") ? binding.nh319e.getId() :
                                                                jsonA4.getnh319().equals("6") ? binding.nh319f.getId() :
                                                                        jsonA4.getnh319().equals("7") ? binding.nh319g.getId() :
                                                                                jsonA4.getnh319().equals("8") ? binding.nh319h.getId() :
                                                                                        jsonA4.getnh319().equals("9") ? binding.nh319i.getId() :
                                                                                                jsonA4.getnh319().equals("10") ? binding.nh319j.getId() :
                                                                                                        jsonA4.getnh319().equals("11") ? binding.nh319k.getId() :
                                                                                                                jsonA4.getnh319().equals("12") ? binding.nh319l.getId() :
                                                                                                                        jsonA4.getnh319().equals("13") ? binding.nh319m.getId() :
                                                                                                                                jsonA4.getnh319().equals("14") ? binding.nh319n.getId() :
                                                                                                                                        jsonA4.getnh319().equals("15") ? binding.nh319o.getId() :
                                                                                                                                                jsonA4.getnh319().equals("16") ? binding.nh319p.getId() :
                                                                                                                                                        binding.nh31996.getId()
                );
            }
            binding.nh31996x.setText(jsonA4.getnh31996x());
            binding.nh320.setText(jsonA4.getnh320());
            if (!jsonA4.getnh321().equals("0")) {
                binding.nh321.check(
                        jsonA4.getnh321().equals("1") ? binding.nh321a.getId() : binding.nh321b.getId()
                );
            }
            if (!jsonA4.getnh322().equals("0")) {
                binding.nh322.check(
                        jsonA4.getnh322().equals("1") ? binding.nh322a.getId() :
                                jsonA4.getnh322().equals("2") ? binding.nh322b.getId() :
                                        binding.nh32298.getId()
                );
            }
            binding.nh322acr.setText(jsonA4.getnh322acr());
            binding.nh322can.setText(jsonA4.getnh322can());


            if (!jsonA4.getnh323().equals("0")) {
                binding.nh323.check(
                        jsonA4.getnh323().equals("1") ? binding.nh323a.getId() :
                                binding.nh323b.getId()
                );
            }

            binding.nh324a.setText(jsonA4.getnh324a());
            binding.nh324b.setText(jsonA4.getnh324b());
            binding.nh324c.setText(jsonA4.getnh324c());
            binding.nh324d.setText(jsonA4.getnh324d());
            binding.nh324e.setText(jsonA4.getnh324e());
            binding.nh324f.setText(jsonA4.getnh324f());
            binding.nh324g.setText(jsonA4.getnh324g());

        }
    }

    private void setListeners() {
        binding.nh318.setOnCheckedChangeListener(this);
        binding.nh319.setOnCheckedChangeListener(this);
        binding.nh320.addTextChangedListener(this);

//        nh321
        binding.nh321.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                formValidation();
                if (i == R.id.nh321b) {
                    ClearClass.ClearAllFields(binding.fldGrpnh322, false);

                } else {
                    ClearClass.ClearAllFields(binding.fldGrpnh322, true);
                    binding.nh322acr.setEnabled(false);
                    binding.nh322can.setEnabled(false);
                }
            }
        });
        binding.nh322.setOnCheckedChangeListener(this);

//        nh323
        binding.nh323.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                formValidation();
                if (i == R.id.nh323b) {
                    ClearClass.ClearAllFields(binding.fldGrpnh324, false);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpnh324, true);
                }
            }
        });

        binding.nh324a.addTextChangedListener(this);
        binding.nh324b.addTextChangedListener(this);
        binding.nh324c.addTextChangedListener(this);
        binding.nh324d.addTextChangedListener(this);
        binding.nh324e.addTextChangedListener(this);
        binding.nh324f.addTextChangedListener(this);
        binding.nh324g.addTextChangedListener(this);

    }

    private boolean formValidation() {


        if (!ValidatorClass.EmptyRadioButton(this, binding.nh318, binding.nh318a, getString(R.string.nh318))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh318, binding.nh31896, binding.nh31896x, getString(R.string.nh318))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh319, binding.nh319a, getString(R.string.nh319))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh319, binding.nh31996, binding.nh31996x, getString(R.string.nh319))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, binding.nh320, getString(R.string.nh320))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, binding.nh320, 1, 15, getString(R.string.nh320), "Room")) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh321, binding.nh321b, getString(R.string.nh321))) {
            return false;
        }

        if (binding.nh321a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.nh322, binding.nh322a, getString(R.string.nh322))) {
                return false;
            }

            if (binding.nh322a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.nh322acr, getString(R.string.nh322acr))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, binding.nh322acr, 1.00, 999.0, getString(R.string.nh322acr), "acre")) {
                    return false;
                }
            } else if (binding.nh322b.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.nh322can, getString(R.string.nh322can))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, binding.nh322can, 1.00, 999.0, getString(R.string.nh322can), "kanal")) {
                    return false;
                }
            }
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh323, binding.nh323b, getString(R.string.nh323))) {
            return false;
        }


        if (binding.nh323a.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, binding.nh324a, getString(R.string.nh324a))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.nh324a, 0, 999, getString(R.string.nh324a), "Animal")) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.nh324b, getString(R.string.nh324b))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, binding.nh324b, 0, 999, getString(R.string.nh324b), "Animal")) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.nh324c, getString(R.string.nh324c))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, binding.nh324c, 0, 999, getString(R.string.nh324c), "Animal")) {
                return false;
            }
            if (!ValidatorClass.EmptyTextBox(this, binding.nh324d, getString(R.string.nh324d))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, binding.nh324d, 0, 999, getString(R.string.nh324d), "Animal")) {
                return false;
            }
            if (!ValidatorClass.EmptyTextBox(this, binding.nh324e, getString(R.string.nh324e))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, binding.nh324e, 0, 999, getString(R.string.nh324e), "Animal")) {
                return false;
            }
            if (!ValidatorClass.EmptyTextBox(this, binding.nh324f, getString(R.string.nh324f))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, binding.nh324f, 0, 999, getString(R.string.nh324f), "Animal")) {
                return false;
            }
            if (!ValidatorClass.EmptyTextBox(this, binding.nh324g, getString(R.string.nh324g))) {
                return false;
            }
            return ValidatorClass.RangeTextBox(this, binding.nh324g, 0, 999, getString(R.string.nh324g), "Animal");
        }
        return true;
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
        sA4.put("nh318", binding.nh318a.isChecked() ? "1"
                : binding.nh318b.isChecked() ? "2"
                : binding.nh318c.isChecked() ? "3"
                : binding.nh318d.isChecked() ? "4"
                : binding.nh318e.isChecked() ? "5"
                : binding.nh318f.isChecked() ? "6"
                : binding.nh318g.isChecked() ? "7"
                : binding.nh318h.isChecked() ? "8"
                : binding.nh318i.isChecked() ? "9"
                : binding.nh318j.isChecked() ? "10"
                : binding.nh318k.isChecked() ? "11"
                : binding.nh318l.isChecked() ? "12"
                : binding.nh318m.isChecked() ? "13"
                : binding.nh318n.isChecked() ? "14"
                : binding.nh31896.isChecked() ? "96"
                : "0");
        sA4.put("nh31896x", binding.nh31896x.getText().toString());

//          nh319
        sA4.put("nh319", binding.nh319a.isChecked() ? "1"
                : binding.nh319b.isChecked() ? "2"
                : binding.nh319c.isChecked() ? "3"
                : binding.nh319d.isChecked() ? "4"
                : binding.nh319e.isChecked() ? "5"
                : binding.nh319f.isChecked() ? "6"
                : binding.nh319g.isChecked() ? "7"
                : binding.nh319h.isChecked() ? "8"
                : binding.nh319i.isChecked() ? "9"
                : binding.nh319j.isChecked() ? "10"
                : binding.nh319k.isChecked() ? "11"
                : binding.nh319l.isChecked() ? "12"
                : binding.nh319m.isChecked() ? "13"
                : binding.nh319n.isChecked() ? "14"
                : binding.nh319o.isChecked() ? "15"
                : binding.nh319p.isChecked() ? "16"
                : binding.nh31996.isChecked() ? "96"
                : "0");
        sA4.put("nh31996x", binding.nh31996x.getText().toString());

//        nh320
        sA4.put("nh320", binding.nh320.getText().toString());

//        nh321
        sA4.put("nh321", binding.nh321a.isChecked() ? "1"
                : binding.nh321b.isChecked() ? "2"
                : "0");

//        nh322
        sA4.put("nh322", binding.nh322a.isChecked() ? "1"
                : binding.nh322b.isChecked() ? "2"
                : binding.nh32298.isChecked() ? "98"
                : "0");
        sA4.put("nh322acr", binding.nh322acr.getText().toString());
        sA4.put("nh322can", binding.nh322can.getText().toString());

//        nh323
        sA4.put("nh323", binding.nh323a.isChecked() ? "1"
                : binding.nh323b.isChecked() ? "2"
                : "0");
//        nh324
        sA4.put("nh324a", binding.nh324a.getText().toString());
        sA4.put("nh324b", binding.nh324b.getText().toString());
        sA4.put("nh324c", binding.nh324c.getText().toString());
        sA4.put("nh324d", binding.nh324d.getText().toString());
        sA4.put("nh324e", binding.nh324e.getText().toString());
        sA4.put("nh324f", binding.nh324f.getText().toString());
        sA4.put("nh324g", binding.nh324g.getText().toString());


    }
}
