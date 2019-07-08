package edu.aku.hassannaqvi.casi_2018.ui.wra;

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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindViews;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONB4ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.MWRAContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionB4Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2018.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionB4Activity extends Menu2Activity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private final long DELAY = 1000;
    ActivitySectionB4Binding binding;
    DatabaseHelper db;
    Boolean backPressed = false;
    @BindViews({R.id.nb412b, R.id.nb412c, R.id.nb412d, R.id.nb412e, R.id.nb412f, R.id.nb41296})
    List<CheckBox> rd_nb412;
    CheckBox.OnCheckedChangeListener check = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isoneYes()) {
                if (!binding.nb412a.isChecked()) {
                    ClearClass.ClearAllFields(binding.fldGrpnw413, false);
                }
            } else {
                ClearClass.ClearAllFields(binding.fldGrpnw413, true);
            }
        }
    };
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_b4);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_b4);
        ButterKnife.bind(this);
        db = new DatabaseHelper(this);
        binding.setCallback(this);

        /*for (CheckBox ck : rd_nb412) {
            ck.setOnCheckedChangeListener(check);
        }*/

        this.setTitle(getResources().getString(R.string.nb4heading));
        binding.textName.setText("Selected Woman : " + SectionB1Activity.wraName);


        binding.nw40299.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                formValidation();
                if (isChecked) {
                    binding.nw402a.setChecked(false);
                    binding.nw402b.setChecked(false);
                    binding.nw402c.setChecked(false);
                    binding.nw402d.setChecked(false);
                    binding.nw402e.setChecked(false);
                    binding.nw402f.setChecked(false);
                    binding.nw402g.setChecked(false);
                    binding.nw402h.setChecked(false);
                    binding.nw40296.setChecked(false);

                    binding.nw402a.setEnabled(false);
                    binding.nw402b.setEnabled(false);
                    binding.nw402c.setEnabled(false);
                    binding.nw402d.setEnabled(false);
                    binding.nw402e.setEnabled(false);
                    binding.nw402f.setEnabled(false);
                    binding.nw402g.setEnabled(false);
                    binding.nw402h.setEnabled(false);
                    binding.nw40296.setEnabled(false);
                } else {
                    binding.nw402a.setEnabled(true);
                    binding.nw402b.setEnabled(true);
                    binding.nw402c.setEnabled(true);
                    binding.nw402d.setEnabled(true);
                    binding.nw402e.setEnabled(true);
                    binding.nw402f.setEnabled(true);
                    binding.nw402g.setEnabled(true);
                    binding.nw402h.setEnabled(true);
                    binding.nw40296.setEnabled(true);
                }
            }
        });
        binding.nw405.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (checkedId == R.id.nw405a) {


                    ClearClass.ClearAllFields(binding.fldGrpnw406, true);
                    binding.nw406cx.setText(null);
                    binding.nw406rx.setText(null);

                    binding.nw406cx.setEnabled(false);
                    binding.nw406rx.setEnabled(false);

                } else {
//                    binding.fldGrpnw406.setVisibility(View.VISIBLE);

                    ClearClass.ClearAllFields(binding.fldGrpnw406, false);
                    binding.nw406cx.setText(null);
                    binding.nw406rx.setText(null);

                    binding.nw406cx.setEnabled(false);
                    binding.nw406rx.setEnabled(false);
                }
            }
        });
        binding.nw406.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                binding.nw406cx.setText(null);
                binding.nw406rx.setText(null);
            }
        });
        binding.nb411.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //formValidation();
                if (checkedId == R.id.nb411b || checkedId == R.id.nb41198) {

                    ClearClass.ClearAllFields(binding.fldGrpnb412, false);
                    ClearClass.ClearAllFields(binding.fldGrpnb412check, false);

                } else {

                    ClearClass.ClearAllFields(binding.fldGrpnb412, true);
                    ClearClass.ClearAllFields(binding.fldGrpnb412check, true);
                }
            }
        });
        binding.nb41298.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                formValidation();
                if (isChecked) {
                    binding.nb412a.setEnabled(false);
                    binding.nb412b.setEnabled(false);
                    binding.nb412c.setEnabled(false);
                    binding.nb412d.setEnabled(false);
                    binding.nb412e.setEnabled(false);
                    binding.nb412f.setEnabled(false);
                    binding.nb41296.setEnabled(false)
                    ;

                    binding.nb412a.setChecked(false);
                    binding.nb412b.setChecked(false);
                    binding.nb412c.setChecked(false);
                    binding.nb412d.setChecked(false);
                    binding.nb412e.setChecked(false);
                    binding.nb412f.setChecked(false);
                    binding.nb41296.setChecked(false);

                    binding.nb41296x.setText(null);
                    if (!binding.nb412a.isChecked()) {
                        ClearClass.ClearAllFields(binding.fldGrpnw413, false);
                    }

                } else {
                    binding.nb412a.setEnabled(true);
                    binding.nb412b.setEnabled(true);
                    binding.nb412c.setEnabled(true);
                    binding.nb412d.setEnabled(true);
                    binding.nb412e.setEnabled(true);
                    binding.nb412f.setEnabled(true);
                    binding.nb41296.setEnabled(true);
                }
            }
        });

        binding.nb412a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ClearClass.ClearAllFields(binding.fldGrpnw413, true);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpnw413, false);
                }
            }
        });
        binding.nw401.setOnCheckedChangeListener(this);
        binding.nw403.setOnCheckedChangeListener(this);
        binding.nw40301.setOnCheckedChangeListener(this);
        binding.nw404.setOnCheckedChangeListener(this);
        binding.nw406c.addTextChangedListener(this);
        binding.nw406r.addTextChangedListener(this);
        binding.nw407.setOnCheckedChangeListener(this);
        binding.nw408.setOnCheckedChangeListener(this);
        binding.nw409.setOnCheckedChangeListener(this);
        binding.nb410.setOnCheckedChangeListener(this);
        binding.nw413.setOnCheckedChangeListener(this);

//        Validation Boolean
        MainApp.validateFlag = false;

        AutoCompleteFields();

    }


    public void AutoCompleteFields() {

//        BackPressed
        MWRAContract mwraContract = db.getsB4();
        if (!mwraContract.getsB4().equals("")) {

            JSONB4ModelClass jsonB4 = JSONUtilClass.getModelFromJSON(mwraContract.getsB4(), JSONB4ModelClass.class);

            if (!jsonB4.getnw401().equals("0")) {
                binding.nw401.check(
                        jsonB4.getnw401().equals("1") ? binding.nw401a.getId() :
                                jsonB4.getnw401().equals("2") ? binding.nw401b.getId() :
                                        jsonB4.getnw401().equals("3") ? binding.nw401c.getId() :
                                                jsonB4.getnw401().equals("4") ? binding.nw401d.getId() :
                                                        jsonB4.getnw401().equals("5") ? binding.nw401e.getId() :
                                                                jsonB4.getnw401().equals("6") ? binding.nw401f.getId() :
                                                                        jsonB4.getnw401().equals("7") ? binding.nw401g.getId() :
                                                                                jsonB4.getnw401().equals("8") ? binding.nw401h.getId() :
                                                                                        binding.nw40196.getId()
                );
                binding.nw40196x.setText(jsonB4.getnw40196x());
            }

            if (!jsonB4.getnw402a().equals("0")) {
                binding.nw402a.setChecked(true);
            }
            if (!jsonB4.getnw402b().equals("0")) {
                binding.nw402b.setChecked(true);
            }
            if (!jsonB4.getnw402c().equals("0")) {
                binding.nw402c.setChecked(true);
            }
            if (!jsonB4.getnw402d().equals("0")) {
                binding.nw402d.setChecked(true);
            }
            if (!jsonB4.getnw402e().equals("0")) {
                binding.nw402e.setChecked(true);
            }
            if (!jsonB4.getnw402f().equals("0")) {
                binding.nw402f.setChecked(true);
            }
            if (!jsonB4.getnw402g().equals("0")) {
                binding.nw402g.setChecked(true);
            }
            if (!jsonB4.getnw402h().equals("0")) {
                binding.nw402h.setChecked(true);
            }
            if (!jsonB4.getnw40299().equals("0")) {
                binding.nw40299.setChecked(true);
            }
            if (!jsonB4.getnw40296().equals("0")) {
                binding.nw40296.setChecked(true);
                binding.nw40296x.setText(jsonB4.getnw40296x());
            }

            if (!jsonB4.getnw403().equals("0")) {
                binding.nw403.check(
                        jsonB4.getnw403().equals("1") ? binding.nw403a.getId() :
                                jsonB4.getnw403().equals("2") ? binding.nw403b.getId() :
                                        jsonB4.getnw403().equals("3") ? binding.nw403c.getId() :
                                                jsonB4.getnw403().equals("4") ? binding.nw403d.getId() :
                                                        jsonB4.getnw403().equals("5") ? binding.nw403e.getId() :
                                                                jsonB4.getnw403().equals("6") ? binding.nw403f.getId() :
                                                                        jsonB4.getnw403().equals("7") ? binding.nw403g.getId() :
                                                                                jsonB4.getnw403().equals("8") ? binding.nw403h.getId() :
                                                                                        jsonB4.getnw403().equals("9") ? binding.nw403i.getId() :
                                                                                                jsonB4.getnw403().equals("10") ? binding.nw403j.getId() :
                                                                                                        binding.nw40396.getId()
                );
                binding.nw403fx.setText(jsonB4.getnw403fx());
                binding.nw403jx.setText(jsonB4.getnw403jx());
                binding.nw40396x.setText(jsonB4.getnw40396x());
            }
            if (!jsonB4.getnw40301().equals("0")) {
                binding.nw40301.check(
                        jsonB4.getnw40301().equals("1") ? binding.nw40301a.getId() :
                                jsonB4.getnw40301().equals("2") ? binding.nw40301b.getId() : binding.nw40301c.getId()
                );
            }

            if (!jsonB4.getnw404().equals("0")) {
                binding.nw404.check(
                        jsonB4.getnw404().equals("1") ? binding.nw404a.getId() :
                                jsonB4.getnw404().equals("2") ? binding.nw404b.getId() :
                                        jsonB4.getnw404().equals("3") ? binding.nw404c.getId() :
                                                jsonB4.getnw404().equals("4") ? binding.nw404d.getId() :
                                                        binding.nw404e.getId()
                );
            }

            if (!jsonB4.getnw405().equals("0")) {
                binding.nw405.check(
                        jsonB4.getnw405().equals("1") ? binding.nw405a.getId() :
                                jsonB4.getnw405().equals("2") ? binding.nw405b.getId() :
                                        binding.nw40598.getId()
                );
            }

            binding.nw406cx.setText(jsonB4.getnw406c());
            binding.nw406rx.setText(jsonB4.getnw406r());

            if (!jsonB4.getnw40698().equals("0")) {
                binding.nw40698.setChecked(true);
            }

            if (!jsonB4.getnw407().equals("0")) {
                binding.nw407.check(
                        jsonB4.getnw407().equals("1") ? binding.nw407a.getId() :
                                jsonB4.getnw407().equals("2") ? binding.nw407b.getId() :
                                        binding.nw40798.getId()
                );
            }

            if (!jsonB4.getnw408().equals("0")) {
                binding.nw408.check(
                        jsonB4.getnw408().equals("1") ? binding.nw408a.getId() :
                                jsonB4.getnw408().equals("2") ? binding.nw408b.getId() :
                                        binding.nw40898.getId()
                );
            }

            if (!jsonB4.getnw409().equals("0")) {
                binding.nw409.check(
                        jsonB4.getnw409().equals("1") ? binding.nw409a.getId() :
                                jsonB4.getnw409().equals("2") ? binding.nw409b.getId() :
                                        binding.nw40998.getId()
                );
            }

            binding.nb410h.setText(jsonB4.getnw410h());
            binding.nb410d.setText(jsonB4.getnw410d());

            if (!jsonB4.getnw410().equals("0")) {
                binding.nb410.check(
                        jsonB4.getnw410().equals("1") ? binding.nb410a.getId() :
                                jsonB4.getnw410().equals("2") ? binding.nb410b.getId() :
                                        jsonB4.getnw410().equals("2") ? binding.nb41097.getId() :
                                                binding.nb41098.getId()
                );
            }

            if (!jsonB4.getnw411().equals("0")) {
                binding.nb411.check(
                        jsonB4.getnw411().equals("1") ? binding.nb411a.getId() :
                                jsonB4.getnw411().equals("2") ? binding.nb411b.getId() :
                                        binding.nb41198.getId()
                );
            }

            if (!jsonB4.getnw412a().equals("0")) {
                binding.nb412a.setChecked(true);
            }
            if (!jsonB4.getnw412b().equals("0")) {
                binding.nb412b.setChecked(true);
            }
            if (!jsonB4.getnw412c().equals("0")) {
                binding.nb412c.setChecked(true);
            }
            if (!jsonB4.getnw412d().equals("0")) {
                binding.nb412d.setChecked(true);
            }
            if (!jsonB4.getnw412e().equals("0")) {
                binding.nb412e.setChecked(true);
            }
            if (!jsonB4.getnw412f().equals("0")) {
                binding.nb412f.setChecked(true);
            }
            if (!jsonB4.getnw41298().equals("0")) {
                binding.nb41298.setChecked(true);
            }
            if (!jsonB4.getnw41296().equals("0")) {
                binding.nb41296.setChecked(true);
                binding.nb41296x.setText(jsonB4.getnw41296x());
            }

            if (!jsonB4.getnw413().equals("0")) {
                binding.nw413.check(
                        jsonB4.getnw413().equals("1") ? binding.nw413a.getId() :
                                jsonB4.getnw413().equals("2") ? binding.nw413b.getId() :
                                        jsonB4.getnw413().equals("3") ? binding.nw413c.getId() :
                                                jsonB4.getnw413().equals("4") ? binding.nw413d.getId() :
                                                        jsonB4.getnw413().equals("5") ? binding.nw413e.getId() :
                                                                jsonB4.getnw413().equals("6") ? binding.nw413f.getId() :
                                                                        jsonB4.getnw413().equals("7") ? binding.nw413g.getId() :
                                                                                jsonB4.getnw413().equals("8") ? binding.nw413h.getId() :
                                                                                        jsonB4.getnw413().equals("9") ? binding.nw413i.getId() :
                                                                                                jsonB4.getnw413().equals("10") ? binding.nw413j.getId() :
                                                                                                        jsonB4.getnw413().equals("11") ? binding.nw413k.getId() :
                                                                                                                jsonB4.getnw413().equals("12") ? binding.nw413l.getId() :
                                                                                                                        jsonB4.getnw413().equals("13") ? binding.nw413m.getId() :
                                                                                                                                jsonB4.getnw413().equals("961") ? binding.nw413961.getId() :
                                                                                                                                        jsonB4.getnw413().equals("962") ? binding.nw413962.getId() :
                                                                                                                                                binding.nw413963.getId()
                );
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

                backPressed = true;

//                finish();

                startActivity(new Intent(this, SectionB5Activity.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

        //startActivity(new Intent(this, SectionB5Activity.class));
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

        if (!ValidatorClass.EmptyRadioButton(this, binding.nw401, binding.nw401a, getString(R.string.nw401))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw401, binding.nw40196, binding.nw40196x, getString(R.string.nw401))) {
            return false;
        }


        // nw402

        if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw402check, binding.nw402a, getString(R.string.nw402))) {
            return false;
        }
        if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnw402check, binding.nw40296, binding.nw40296x, getString(R.string.nw402))) {
            return false;
        }
        // nw403
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw403, binding.nw403a, getString(R.string.nw403))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw403, binding.nw403f, binding.nw403fx, getString(R.string.nw403) + " " + getString(R.string.nw403f))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw403, binding.nw403j, binding.nw403jx, getString(R.string.nw403) + " " + getString(R.string.nw403j))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw403, binding.nw40396, binding.nw40396x, getString(R.string.nw403) + " " + getString(R.string.other))) {
            return false;
        }
//        nw40301
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw40301, binding.nw40301a, getString(R.string.nw40301))) {
            return false;
        }

        // nw404
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw404, binding.nw404a, getString(R.string.nw404))) {
            return false;
        }
        // nw405
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw405, binding.nw405a, getString(R.string.nw405))) {
            return false;
        }

        if (binding.nw405a.isChecked()) {
            // nw406

            if (!binding.nw40698.isChecked()) {
                if (!ValidatorClass.EmptyRadioButton(this, binding.nw406, binding.nw406c, getString(R.string.nw406))) {
                    return false;
                }
                if (binding.nw406c.isChecked()) {
                    if (!ValidatorClass.EmptyTextBox(this, binding.nw406cx, getString(R.string.nw406) + " - " + getString(R.string.nw406c))) {
                        return false;
                    }
                }
                if (binding.nw406r.isChecked()) {
                    if (!ValidatorClass.EmptyTextBox(this, binding.nw406rx, getString(R.string.nw406) + " - " + getString(R.string.nw406r))) {
                        return false;
                    }
                }

            }

        }

        // nw407
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw407, binding.nw407a, getString(R.string.nw407))) {
            return false;
        }
        // nw408
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw408, binding.nw408a, getString(R.string.nw408))) {
            return false;
        }
        // nw409
        if (!ValidatorClass.EmptyRadioButton(this, binding.nw409, binding.nw409a, getString(R.string.nw409))) {
            return false;
        }
        // nb410
        if (!ValidatorClass.EmptyRadioButton(this, binding.nb410, binding.nb410a, getString(R.string.nb410))) {
            return false;
        }


        if (binding.nb410b.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, binding.nb410h, getString(R.string.nb410) + " - " + getString(R.string.nb410h))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.nb410h, 1, 24, getString(R.string.nb410), " hours")) {
                return false;
            }
        }

        if (binding.nb410c.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, binding.nb410d, getString(R.string.nb410) + " - " + getString(R.string.nb410d))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.nb410d, 1, 30, getString(R.string.nb410), " days")) {
                return false;
            }
        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.nb411, binding.nb411a, getString(R.string.nb411))) {
            return false;
        }

        if (binding.nb411a.isChecked()) {
            // nb412
            /*if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnb412check, binding.nb412a, getString(R.string.nb412))) {
                return false;
            }*/
            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpnb412check, binding.nb41296, binding.nb41296x, getString(R.string.nb412))) {
                return false;
            }
            // nw413

            if (binding.nb412a.isChecked()) {
                if (!ValidatorClass.EmptyRadioButton(this, binding.nw413, binding.nw413a, getString(R.string.cic403))) {
                    return false;
                }
                //        nw4139601
                if (!ValidatorClass.EmptyRadioButton(this, binding.nw413, binding.nw413961, binding.nw413961x, getString(R.string.nw413) + " - " + getString(R.string.nw413961))) {
                    return false;
                }
                //        nw4139602
                if (!ValidatorClass.EmptyRadioButton(this, binding.nw413, binding.nw413962, binding.nw413962x, getString(R.string.nw413) + " - " + getString(R.string.nw413962))) {
                    return false;
                }
                //        nw4139603
                return ValidatorClass.EmptyRadioButton(this, binding.nw413, binding.nw413963, binding.nw413963x, getString(R.string.nw413) + " - " + getString(R.string.other));
            }
        }
        return true;
    }


    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sB4 = new JSONObject();

        if (backPressed) {
            sB4.put("updatedate_nw4", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        //       nw401
        sB4.put("nw401", binding.nw401a.isChecked() ? "1"
                : binding.nw401b.isChecked() ? "2"
                : binding.nw401c.isChecked() ? "3"
                : binding.nw401d.isChecked() ? "4"
                : binding.nw401e.isChecked() ? "5"
                : binding.nw401f.isChecked() ? "6"
                : binding.nw401g.isChecked() ? "7"
                : binding.nw401h.isChecked() ? "8"
                : binding.nw40196.isChecked() ? "96"
                : "0");
        sB4.put("nw40196x", binding.nw40196x.getText().toString());

//        nw402
        sB4.put("nw402a", binding.nw402a.isChecked() ? "1" : "0");
        sB4.put("nw402b", binding.nw402b.isChecked() ? "2" : "0");
        sB4.put("nw402c", binding.nw402c.isChecked() ? "3" : "0");
        sB4.put("nw402d", binding.nw402d.isChecked() ? "4" : "0");
        sB4.put("nw402e", binding.nw402e.isChecked() ? "5" : "0");
        sB4.put("nw402f", binding.nw402f.isChecked() ? "6" : "0");
        sB4.put("nw402g", binding.nw402g.isChecked() ? "7" : "0");
        sB4.put("nw402h", binding.nw402h.isChecked() ? "8" : "0");
        sB4.put("nw40299", binding.nw40299.isChecked() ? "99" : "0");
        sB4.put("nw40296", binding.nw40296.isChecked() ? "96" : "0");
        sB4.put("nw40296x", binding.nw40296x.getText().toString());

//       nw403
        sB4.put("nw403", binding.nw403a.isChecked() ? "1"
                : binding.nw403b.isChecked() ? "2"
                : binding.nw403c.isChecked() ? "3"
                : binding.nw403d.isChecked() ? "4"
                : binding.nw403e.isChecked() ? "5"
                : binding.nw403f.isChecked() ? "961"
                : binding.nw403g.isChecked() ? "7"
                : binding.nw403h.isChecked() ? "8"
                : binding.nw403i.isChecked() ? "9"
                : binding.nw403j.isChecked() ? "962"
                : binding.nw40396.isChecked() ? "963"
                : "0");
//       nw403

        sB4.put("nw403961x", binding.nw403fx.getText().toString());
        sB4.put("nw403962x", binding.nw403jx.getText().toString());
        sB4.put("nw403963x", binding.nw40396x.getText().toString());

        sB4.put("nw40301", binding.nw40301a.isChecked() ? "1"
                : binding.nw40301b.isChecked() ? "2"
                : binding.nw40301c.isChecked() ? "3"

                : "0");
//        nw404
        sB4.put("nw404", binding.nw404a.isChecked() ? "1"
                : binding.nw404b.isChecked() ? "2"
                : binding.nw404c.isChecked() ? "3"
                : binding.nw404d.isChecked() ? "4"
                : binding.nw404e.isChecked() ? "5"
                : binding.nw40498.isChecked() ? "98"
                : "0");
//        nw405
        sB4.put("nw405", binding.nw405a.isChecked() ? "1"
                : binding.nw405b.isChecked() ? "2"
                : binding.nw40598.isChecked() ? "98"
                : "0");
//        nw406
        sB4.put("nw406c", binding.nw406cx.getText().toString());
        sB4.put("nw406r", binding.nw406rx.getText().toString());

        sB4.put("nw40698", binding.nw40698.isChecked() ? "98" : "0");

//        nw407
        sB4.put("nw407", binding.nw407a.isChecked() ? "1"
                : binding.nw407b.isChecked() ? "2"
                : binding.nw40798.isChecked() ? "98"
                : "0");

//        nw408
        sB4.put("nw408", binding.nw408a.isChecked() ? "1"
                : binding.nw408b.isChecked() ? "2"
                : binding.nw40898.isChecked() ? "98"
                : "0");

//        nw409
        sB4.put("nw409", binding.nw409a.isChecked() ? "1"
                : binding.nw409b.isChecked() ? "2"
                : binding.nw40998.isChecked() ? "98"
                : "0");


//        nb410
        sB4.put("nw410h", binding.nb410h.getText().toString());
        sB4.put("nw410d", binding.nb410d.getText().toString());
        sB4.put("nw410", binding.nb410a.isChecked() ? "1"
                : binding.nb410b.isChecked() ? "2"
                : binding.nb410c.isChecked() ? "3"
                : binding.nb41097.isChecked() ? "97"
                : binding.nb41098.isChecked() ? "98"
                : "0");

//        nb411
        sB4.put("nw411", binding.nb411a.isChecked() ? "1"
                : binding.nb411b.isChecked() ? "2"
                : binding.nb41198.isChecked() ? "98"
                : "0");

//      nb412
        sB4.put("nw412a", binding.nb412a.isChecked() ? "1" : "0");
        sB4.put("nw412b", binding.nb412b.isChecked() ? "2" : "0");
        sB4.put("nw412c", binding.nb412c.isChecked() ? "3" : "0");
        sB4.put("nw412d", binding.nb412d.isChecked() ? "4" : "0");
        sB4.put("nw412e", binding.nb412e.isChecked() ? "5" : "0");
        sB4.put("nw412f", binding.nb412f.isChecked() ? "6" : "0");
        sB4.put("nw41298", binding.nb41298.isChecked() ? "98" : "0");
        sB4.put("nw41296", binding.nb41296.isChecked() ? "96" : "0");

        sB4.put("nw41296x", binding.nb41296x.getText().toString());


        //        nb413
        sB4.put("nw413", binding.nw413a.isChecked() ? "1"
                : binding.nw413b.isChecked() ? "2"
                : binding.nw413c.isChecked() ? "3"
                : binding.nw413d.isChecked() ? "4"
                : binding.nw413e.isChecked() ? "5"
                : binding.nw413f.isChecked() ? "6"
                : binding.nw413g.isChecked() ? "7"
                : binding.nw413h.isChecked() ? "8"
                : binding.nw413i.isChecked() ? "9"
                : binding.nw413j.isChecked() ? "10"
                : binding.nw413k.isChecked() ? "11"
                : binding.nw413l.isChecked() ? "12"
                : binding.nw413m.isChecked() ? "13"
                : binding.nw413961.isChecked() ? "961"
                : binding.nw413962.isChecked() ? "962"
                : binding.nw413963.isChecked() ? "963"
                : "0");

        sB4.put("nw413961x", binding.nw413961x.getText().toString());
        sB4.put("nw413962x", binding.nw413962x.getText().toString());
        sB4.put("nw413963x", binding.nw413963x.getText().toString());


        MainApp.mc.setsB4(String.valueOf(sB4));


        //Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSB4();

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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        formValidation();
    }


    public boolean isoneYes() {

        int i = 0;
        for (CheckBox rg : rd_nb412) {
            if (rg.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

}
