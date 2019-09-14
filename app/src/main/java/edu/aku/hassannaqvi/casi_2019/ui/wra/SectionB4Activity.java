package edu.aku.hassannaqvi.casi_2019.ui.wra;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionB4Binding;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

import static edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity.userCountryTajik;

public class SectionB4Activity extends Menu2Activity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private final long DELAY = 1000;
    ActivitySectionB4Binding binding;
    DatabaseHelper db;
    Boolean backPressed = false;
    @BindViews({R.id.ciw412b, R.id.ciw412c, R.id.ciw412d, R.id.ciw412e, R.id.ciw412f, R.id.ciw41296})
    List<CheckBox> rd_ciw412;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_b4);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_b4);
        ButterKnife.bind(this);
        db = new DatabaseHelper(this);
        binding.setCallback(this);

        /*for (CheckBox ck : rd_ciw412) {
            ck.setOnCheckedChangeListener(check);
        }*/

        this.setTitle(getResources().getString(R.string.nb4heading));
        binding.textName.setText("Selected Woman : " + SectionB1Activity.wraName);


        binding.ciw40299.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                formValidation();
                if (isChecked) {
                    binding.ciw402a.setChecked(false);
                    binding.ciw402b.setChecked(false);
                    binding.ciw402c.setChecked(false);
                    binding.ciw402d.setChecked(false);
                    binding.ciw402e.setChecked(false);
                    binding.ciw402f.setChecked(false);
                    binding.ciw402g.setChecked(false);
                    binding.ciw402h.setChecked(false);
                    binding.ciw40296.setChecked(false);

                    binding.ciw402a.setEnabled(false);
                    binding.ciw402b.setEnabled(false);
                    binding.ciw402c.setEnabled(false);
                    binding.ciw402d.setEnabled(false);
                    binding.ciw402e.setEnabled(false);
                    binding.ciw402f.setEnabled(false);
                    binding.ciw402g.setEnabled(false);
                    binding.ciw402h.setEnabled(false);
                    binding.ciw40296.setEnabled(false);
                } else {
                    binding.ciw402a.setEnabled(true);
                    binding.ciw402b.setEnabled(true);
                    binding.ciw402c.setEnabled(true);
                    binding.ciw402d.setEnabled(true);
                    binding.ciw402e.setEnabled(true);
                    binding.ciw402f.setEnabled(true);
                    binding.ciw402g.setEnabled(true);
                    binding.ciw402h.setEnabled(true);
                    binding.ciw40296.setEnabled(true);
                }
            }
        });
        binding.ciw405.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                formValidation();
                if (checkedId == R.id.ciw405a) {


                    binding.fldGrpciw406.setVisibility(View.VISIBLE);
                    binding.ciw406cx.setText(null);
                    binding.ciw406rx.setText(null);

                    binding.ciw406cx.setEnabled(false);
                    binding.ciw406rx.setEnabled(false);

                } else {
//                    binding.fldGrpciw406.setVisibility(View.VISIBLE);

                    ClearClass.ClearAllFields(binding.fldGrpciw406, null);
                    binding.fldGrpciw406.setVisibility(View.GONE);
                    binding.ciw406cx.setText(null);
                    binding.ciw406rx.setText(null);

                    binding.ciw406cx.setEnabled(false);
                    binding.ciw406rx.setEnabled(false);
                }
            }
        });
        binding.ciw406.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                formValidation();
                binding.ciw406cx.setText(null);
                binding.ciw406rx.setText(null);
            }
        });
        binding.ciw411.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //formValidation();
                if (checkedId == R.id.ciw411b || checkedId == R.id.ciw41198) {
                    ClearClass.ClearAllFields(binding.fldGrpciw412, null);
                    binding.fldGrpciw412.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpciw412check, null);

                } else {
                    binding.fldGrpciw412.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.ciw41298.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                formValidation();
                if (isChecked) {
                    binding.ciw412a.setEnabled(false);
                    binding.ciw412b.setEnabled(false);
                    binding.ciw412c.setEnabled(false);
                    binding.ciw412d.setEnabled(false);
                    binding.ciw412e.setEnabled(false);
                    binding.ciw412f.setEnabled(false);
                    binding.ciw41296.setEnabled(false);
                    binding.ciw412a.setChecked(false);
                    binding.ciw412b.setChecked(false);
                    binding.ciw412c.setChecked(false);
                    binding.ciw412d.setChecked(false);
                    binding.ciw412e.setChecked(false);
                    binding.ciw412f.setChecked(false);
                    binding.ciw41296.setChecked(false);
                    binding.ciw41296x.setText(null);
                } else {
                    binding.ciw412a.setEnabled(true);
                    binding.ciw412b.setEnabled(true);
                    binding.ciw412c.setEnabled(true);
                    binding.ciw412d.setEnabled(true);
                    binding.ciw412e.setEnabled(true);
                    binding.ciw412f.setEnabled(true);
                    binding.ciw41296.setEnabled(true);
                }
            }
        });

        binding.ciw412a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.fldGrpciw413.setVisibility(View.VISIBLE);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpciw413, null);
                    binding.fldGrpciw413.setVisibility(View.GONE);
                }
            }
        });
        binding.ciw401.setOnCheckedChangeListener(this);
        binding.ciw403.setOnCheckedChangeListener(this);
        binding.ciw40301.setOnCheckedChangeListener(this);
        binding.ciw404.setOnCheckedChangeListener(this);
        binding.ciw406c.addTextChangedListener(this);
        binding.ciw406r.addTextChangedListener(this);
        binding.ciw407.setOnCheckedChangeListener(this);
        binding.ciw408.setOnCheckedChangeListener(this);
        binding.ciw409.setOnCheckedChangeListener(this);
        binding.ciw410.setOnCheckedChangeListener(this);
        binding.ciw413.setOnCheckedChangeListener(this);

//        Validation Boolean
        MainApp.validateFlag = false;

//        AutoCompleteFields();

        /*For Tajik Visibility*/
        binding.ciw401h.setVisibility(userCountryTajik ? View.GONE : View.VISIBLE);
        binding.ciw401txt01.setVisibility(userCountryTajik ? View.GONE : View.VISIBLE);
        binding.ciw401txt02.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);

        binding.ciw402h.setVisibility(userCountryTajik ? View.GONE : View.VISIBLE);
        binding.ciw402txt01.setVisibility(userCountryTajik ? View.GONE : View.VISIBLE);
        binding.ciw402txt02.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);

        binding.ciw403txt01.setVisibility(userCountryTajik ? View.GONE : View.VISIBLE);
        binding.ciw403txt02.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);
        binding.ciw403i.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);
        binding.ciw403j.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);
        binding.ciw403k.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);
        binding.ciw403l.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);
        binding.ciw403m.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);
        binding.ciw403n.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);
        binding.ciw403o.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);
        binding.ciw403p.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);
        binding.ciw403963.setVisibility(userCountryTajik ? View.GONE : View.VISIBLE);

        binding.ciw412e.setVisibility(userCountryTajik ? View.GONE : View.VISIBLE);
        binding.ciw412f.setVisibility(userCountryTajik ? View.GONE : View.VISIBLE);

        binding.ciw413.setVisibility(userCountryTajik ? View.GONE : View.VISIBLE);
        binding.ciw41301.setVisibility(userCountryTajik ? View.VISIBLE : View.GONE);

    }


    /*public void AutoCompleteFields() {

//        BackPressed
        MWRAContract mwraContract = db.getsB4();
        if (!mwraContract.getsB4().equals("")) {

            JSONB4ModelClass jsonB4 = JSONUtilClass.getModelFromJSON(mwraContract.getsB4(), JSONB4ModelClass.class);

            if (!jsonB4.getciw401().equals("0")) {
                binding.ciw401.check(
                        jsonB4.getciw401().equals("1") ? binding.ciw401a.getId() :
                                jsonB4.getciw401().equals("2") ? binding.ciw401b.getId() :
                                        jsonB4.getciw401().equals("3") ? binding.ciw401c.getId() :
                                                jsonB4.getciw401().equals("4") ? binding.ciw401d.getId() :
                                                        jsonB4.getciw401().equals("5") ? binding.ciw401e.getId() :
                                                                jsonB4.getciw401().equals("6") ? binding.ciw401f.getId() :
                                                                        jsonB4.getciw401().equals("7") ? binding.ciw401g.getId() :
                                                                                jsonB4.getciw401().equals("8") ? binding.ciw401h.getId() :
                                                                                        binding.ciw40196.getId()
                );
                binding.ciw40196x.setText(jsonB4.getciw40196x());
            }

            if (!jsonB4.getciw402a().equals("0")) {
                binding.ciw402a.setChecked(true);
            }
            if (!jsonB4.getciw402b().equals("0")) {
                binding.ciw402b.setChecked(true);
            }
            if (!jsonB4.getciw402c().equals("0")) {
                binding.ciw402c.setChecked(true);
            }
            if (!jsonB4.getciw402d().equals("0")) {
                binding.ciw402d.setChecked(true);
            }
            if (!jsonB4.getciw402e().equals("0")) {
                binding.ciw402e.setChecked(true);
            }
            if (!jsonB4.getciw402f().equals("0")) {
                binding.ciw402f.setChecked(true);
            }
            if (!jsonB4.getciw402g().equals("0")) {
                binding.ciw402g.setChecked(true);
            }
            if (!jsonB4.getciw402h().equals("0")) {
                binding.ciw402h.setChecked(true);
            }
            if (!jsonB4.getciw40299().equals("0")) {
                binding.ciw40299.setChecked(true);
            }
            if (!jsonB4.getciw40296().equals("0")) {
                binding.ciw40296.setChecked(true);
                binding.ciw40296x.setText(jsonB4.getciw40296x());
            }

            if (!jsonB4.getciw403().equals("0")) {
                binding.ciw403.check(
                        jsonB4.getciw403().equals("1") ? binding.ciw403a.getId() :
                                jsonB4.getciw403().equals("2") ? binding.ciw403b.getId() :
                                        jsonB4.getciw403().equals("3") ? binding.ciw403c.getId() :
                                                jsonB4.getciw403().equals("4") ? binding.ciw403d.getId() :
                                                        jsonB4.getciw403().equals("5") ? binding.ciw403e.getId() :
                                                                jsonB4.getciw403().equals("961") ? binding.ciw403f.getId() :
                                                                        jsonB4.getciw403().equals("7") ? binding.ciw403g.getId() :
                                                                                jsonB4.getciw403().equals("8") ? binding.ciw403h.getId() :
                                                                                        jsonB4.getciw403().equals("9") ? binding.ciw403i.getId() :
                                                                                                jsonB4.getciw403().equals("962") ? binding.ciw403j.getId() :
                                                                                                        jsonB4.getciw403().equals("11") ? binding.ciw403k.getId() :
                                                                                                                jsonB4.getciw403().equals("12") ? binding.ciw403l.getId() :
                                                                                                                        jsonB4.getciw403().equals("13") ? binding.ciw403m.getId() :
                                                                                                                                jsonB4.getciw403().equals("14") ? binding.ciw403n.getId() :
                                                                                                                                        jsonB4.getciw403().equals("15") ? binding.ciw403o.getId() :
                                                                                                                                                jsonB4.getciw403().equals("16") ? binding.ciw403p.getId() :
                                                                                                                                                        jsonB4.getciw403().equals("17") ? binding.ciw403q.getId() :
                                                                                                                                                                jsonB4.getciw403().equals("18") ? binding.ciw403r.getId() :
                                                                                                                                                                        jsonB4.getciw403().equals("19") ? binding.ciw403s.getId() :
                                                                                                                                                                                binding.ciw40396.getId()
                );
                binding.ciw403fx.setText(jsonB4.getciw403fx());
                binding.ciw403jx.setText(jsonB4.getciw403jx());
                binding.ciw40396x.setText(jsonB4.getciw40396x());
            }
            if (!jsonB4.getciw40301().equals("0")) {
                binding.ciw40301.check(
                        jsonB4.getciw40301().equals("1") ? binding.ciw40301a.getId() :
                                jsonB4.getciw40301().equals("2") ? binding.ciw40301b.getId() : binding.ciw40301c.getId()
                );
            }

            if (!jsonB4.getciw404().equals("0")) {
                binding.ciw404.check(
                        jsonB4.getciw404().equals("1") ? binding.ciw404a.getId() :
                                jsonB4.getciw404().equals("2") ? binding.ciw404b.getId() :
                                        jsonB4.getciw404().equals("3") ? binding.ciw404c.getId() :
                                                jsonB4.getciw404().equals("4") ? binding.ciw404d.getId() :
                                                        binding.ciw404e.getId()
                );
            }

            if (!jsonB4.getciw405().equals("0")) {
                binding.ciw405.check(
                        jsonB4.getciw405().equals("1") ? binding.ciw405a.getId() :
                                jsonB4.getciw405().equals("2") ? binding.ciw405b.getId() :
                                        binding.ciw40598.getId()
                );
            }

            binding.ciw406cx.setText(jsonB4.getciw406c());
            binding.ciw406rx.setText(jsonB4.getciw406r());

            if (!jsonB4.getciw40698().equals("0")) {
                binding.ciw40698.setChecked(true);
            }

            if (!jsonB4.getciw407().equals("0")) {
                binding.ciw407.check(
                        jsonB4.getciw407().equals("1") ? binding.ciw407a.getId() :
                                jsonB4.getciw407().equals("2") ? binding.ciw407b.getId() :
                                        binding.ciw40798.getId()
                );
            }

            if (!jsonB4.getciw408().equals("0")) {
                binding.ciw408.check(
                        jsonB4.getciw408().equals("1") ? binding.ciw408a.getId() :
                                jsonB4.getciw408().equals("2") ? binding.ciw408b.getId() :
                                        binding.ciw40898.getId()
                );
            }

            if (!jsonB4.getciw409().equals("0")) {
                binding.ciw409.check(
                        jsonB4.getciw409().equals("1") ? binding.ciw409a.getId() :
                                jsonB4.getciw409().equals("2") ? binding.ciw409b.getId() :
                                        binding.ciw40998.getId()
                );
            }

            binding.ciw410h.setText(jsonB4.getciw410h());
            binding.ciw410d.setText(jsonB4.getciw410d());

            if (!jsonB4.getciw410().equals("0")) {
                binding.ciw410.check(
                        jsonB4.getciw410().equals("1") ? binding.ciw410a.getId() :
                                jsonB4.getciw410().equals("2") ? binding.ciw410b.getId() :
                                        jsonB4.getciw410().equals("2") ? binding.ciw41097.getId() :
                                                binding.ciw41098.getId()
                );
            }

            if (!jsonB4.getciw411().equals("0")) {
                binding.ciw411.check(
                        jsonB4.getciw411().equals("1") ? binding.ciw411a.getId() :
                                jsonB4.getciw411().equals("2") ? binding.ciw411b.getId() :
                                        binding.ciw41198.getId()
                );
            }

            if (!jsonB4.getciw412a().equals("0")) {
                binding.ciw412a.setChecked(true);
            }
            if (!jsonB4.getciw412b().equals("0")) {
                binding.ciw412b.setChecked(true);
            }
            if (!jsonB4.getciw412c().equals("0")) {
                binding.ciw412c.setChecked(true);
            }
            if (!jsonB4.getciw412d().equals("0")) {
                binding.ciw412d.setChecked(true);
            }
            if (!jsonB4.getciw412e().equals("0")) {
                binding.ciw412e.setChecked(true);
            }
            if (!jsonB4.getciw412f().equals("0")) {
                binding.ciw412f.setChecked(true);
            }
            if (!jsonB4.getciw41298().equals("0")) {
                binding.ciw41298.setChecked(true);
            }
            if (!jsonB4.getciw41296().equals("0")) {
                binding.ciw41296.setChecked(true);
                binding.ciw41296x.setText(jsonB4.getciw41296x());
            }

            if (!jsonB4.getciw413().equals("0")) {
                binding.ciw413.check(
                        jsonB4.getciw413().equals("1") ? binding.ciw413a.getId() :
                                jsonB4.getciw413().equals("2") ? binding.ciw413b.getId() :
                                        jsonB4.getciw413().equals("3") ? binding.ciw413c.getId() :
                                                jsonB4.getciw413().equals("4") ? binding.ciw413d.getId() :
                                                        jsonB4.getciw413().equals("5") ? binding.ciw413e.getId() :
                                                                jsonB4.getciw413().equals("6") ? binding.ciw413f.getId() :
                                                                        jsonB4.getciw413().equals("7") ? binding.ciw413g.getId() :
                                                                                jsonB4.getciw413().equals("8") ? binding.ciw413h.getId() :
                                                                                        jsonB4.getciw413().equals("9") ? binding.ciw413i.getId() :
                                                                                                jsonB4.getciw413().equals("10") ? binding.ciw413j.getId() :
                                                                                                        jsonB4.getciw413().equals("11") ? binding.ciw413k.getId() :
                                                                                                                jsonB4.getciw413().equals("12") ? binding.ciw413l.getId() :
                                                                                                                        jsonB4.getciw413().equals("13") ? binding.ciw413m.getId() :
                                                                                                                                jsonB4.getciw413().equals("14") ? binding.ciw413n.getId() :
                                                                                                                                        jsonB4.getciw413().equals("15") ? binding.ciw413o.getId() :
                                                                                                                                                jsonB4.getciw413().equals("16") ? binding.ciw413p.getId() :
                                                                                                                                                        jsonB4.getciw413().equals("17") ? binding.ciw413q.getId() :
                                                                                                                                                                jsonB4.getciw413().equals("18") ? binding.ciw413r.getId() :
                                                                                                                                                                        jsonB4.getciw413().equals("19") ? binding.ciw413s.getId() :
                                                                                                                                                                                jsonB4.getciw413().equals("961") ? binding.ciw413961.getId() :
                                                                                                                                                                                        jsonB4.getciw413().equals("962") ? binding.ciw413962.getId() :
                                                                                                                                                                                                binding.ciw413963.getId()
                );
            }

        }
    }*/

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

                startActivity(new Intent(this, SectionB5Activity.class));

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


        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw401, binding.ciw401a, getString(R.string.ciw401))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw401, binding.ciw40196, binding.ciw40196x, getString(R.string.ciw401))) {
            return false;
        }


        // ciw402

        if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw402check, binding.ciw402a, getString(R.string.ciw402))) {
            return false;
        }
        if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw402check, binding.ciw40296, binding.ciw40296x, getString(R.string.ciw402))) {
            return false;
        }
        // ciw403
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw403, binding.ciw403a, getString(R.string.ciw403))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw403, binding.ciw403961, binding.ciw403961x, getString(R.string.ciw403) + " " + getString(R.string.ciw403f))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw403, binding.ciw403962, binding.ciw403962x, getString(R.string.ciw403) + " " + getString(R.string.ciw403j))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw403, binding.ciw403963, binding.ciw403963x, getString(R.string.ciw403) + " " + getString(R.string.other))) {
            return false;
        }
//        ciw40301
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw40301, binding.ciw40301a, getString(R.string.ciw40301))) {
            return false;
        }

        // ciw404
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw404, binding.ciw404a, getString(R.string.ciw404))) {
            return false;
        }
        // ciw405
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw405, binding.ciw405a, getString(R.string.ciw405))) {
            return false;
        }

        if (binding.ciw405a.isChecked()) {
            // ciw406

            if (!binding.ciw40698.isChecked()) {
                if (!ValidatorClass.EmptyRadioButton(this, binding.ciw406, binding.ciw406c, getString(R.string.ciw406))) {
                    return false;
                }
                if (binding.ciw406c.isChecked()) {
                    if (!ValidatorClass.EmptyTextBox(this, binding.ciw406cx, getString(R.string.ciw406) + " - " + getString(R.string.ciw406c))) {
                        return false;
                    }
                    if (!ValidatorClass.RangeTextBox(this, binding.ciw406cx, 0.5, 10.0, getString(R.string.ciw406) + " - " + getString(R.string.ciw406c), ""))
                        return false;
                }
                if (binding.ciw406r.isChecked()) {
                    if (!ValidatorClass.EmptyTextBox(this, binding.ciw406rx, getString(R.string.ciw406) + " - " + getString(R.string.ciw406r))) {
                        return false;
                    }
                    if (!ValidatorClass.RangeTextBox(this, binding.ciw406rx, 0.5, 10.0, getString(R.string.ciw406) + " - " + getString(R.string.ciw406c), ""))
                        return false;
                }

            }

        }

        // ciw407
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw407, binding.ciw407a, getString(R.string.ciw407))) {
            return false;
        }
        // ciw408
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw408, binding.ciw408a, getString(R.string.ciw408))) {
            return false;
        }
        // ciw409
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw409, binding.ciw409a, getString(R.string.ciw409))) {
            return false;
        }
        // ciw410
        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw410, binding.ciw410a, getString(R.string.ciw410))) {
            return false;
        }


        if (binding.ciw410b.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, binding.ciw410h, getString(R.string.ciw410) + " - " + getString(R.string.ciw410h))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.ciw410h, 1, 24, getString(R.string.ciw410), " hours")) {
                return false;
            }
        }

        if (binding.ciw410c.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, binding.ciw410d, getString(R.string.ciw410) + " - " + getString(R.string.ciw410d))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.ciw410d, 1, 30, getString(R.string.ciw410), " days")) {
                return false;
            }
        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.ciw411, binding.ciw411a, getString(R.string.ciw411))) {
            return false;
        }

        if (binding.ciw411a.isChecked()) {
            // ciw412
            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpciw412check, binding.ciw41296, binding.ciw41296x, getString(R.string.ciw412))) {
                return false;
            }
            // ciw413
            if (binding.ciw412a.isChecked()) {

                if (userCountryTajik) {
                    if (!ValidatorClass.EmptyRadioButton(this, binding.ciw41301, binding.ciw41301a, getString(R.string.cic403))) {
                        return false;
                    }
                    //        ciw413019601
                    if (!ValidatorClass.EmptyRadioButton(this, binding.ciw41301, binding.ciw41301961, binding.ciw41301961x, getString(R.string.ciw413) + " - " + getString(R.string.ciw413961))) {
                        return false;
                    }
                    //        ciw413019602
                    if (!ValidatorClass.EmptyRadioButton(this, binding.ciw41301, binding.ciw41301962, binding.ciw41301962x, getString(R.string.ciw413) + " - " + getString(R.string.ciw413962))) {
                        return false;
                    }
                    //        ciw413019603
                    return ValidatorClass.EmptyRadioButton(this, binding.ciw41301, binding.ciw41301963, binding.ciw41301963x, getString(R.string.ciw413) + " - " + getString(R.string.other));
                } else {
                    if (!ValidatorClass.EmptyRadioButton(this, binding.ciw413, binding.ciw413a, getString(R.string.cic403))) {
                        return false;
                    }
                    //        ciw4139601
                    if (!ValidatorClass.EmptyRadioButton(this, binding.ciw413, binding.ciw413961, binding.ciw413961x, getString(R.string.ciw413) + " - " + getString(R.string.ciw413961))) {
                        return false;
                    }
                    //        ciw4139602
                    if (!ValidatorClass.EmptyRadioButton(this, binding.ciw413, binding.ciw413962, binding.ciw413962x, getString(R.string.ciw413) + " - " + getString(R.string.ciw413962))) {
                        return false;
                    }
                    //        ciw4139603
                    return ValidatorClass.EmptyRadioButton(this, binding.ciw413, binding.ciw413963, binding.ciw413963x, getString(R.string.ciw413) + " - " + getString(R.string.other));
                }
            }
        }
        return true;
    }


    private void SaveDraft() throws JSONException {


        JSONObject sB4 = new JSONObject();

        if (backPressed) {
            sB4.put("updatedate_ciw4", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        //       ciw401
        sB4.put("ciw401", binding.ciw401a.isChecked() ? "1"
                : binding.ciw401b.isChecked() ? "2"
                : binding.ciw401c.isChecked() ? "3"
                : binding.ciw401d.isChecked() ? "4"
                : binding.ciw401e.isChecked() ? "5"
                : binding.ciw401f.isChecked() ? "6"
                : binding.ciw401g.isChecked() ? "7"
                : binding.ciw401h.isChecked() ? "8"
                : binding.ciw40196.isChecked() ? "96"
                : "0");
        sB4.put("ciw40196x", binding.ciw40196x.getText().toString());

//        ciw402
        sB4.put("ciw402a", binding.ciw402a.isChecked() ? "1" : "0");
        sB4.put("ciw402b", binding.ciw402b.isChecked() ? "2" : "0");
        sB4.put("ciw402c", binding.ciw402c.isChecked() ? "3" : "0");
        sB4.put("ciw402d", binding.ciw402d.isChecked() ? "4" : "0");
        sB4.put("ciw402e", binding.ciw402e.isChecked() ? "5" : "0");
        sB4.put("ciw402f", binding.ciw402f.isChecked() ? "6" : "0");
        sB4.put("ciw402g", binding.ciw402g.isChecked() ? "7" : "0");
        sB4.put("ciw402h", binding.ciw402h.isChecked() ? "8" : "0");
        sB4.put("ciw40299", binding.ciw40299.isChecked() ? "99" : "0");
        sB4.put("ciw40296", binding.ciw40296.isChecked() ? "96" : "0");
        sB4.put("ciw40296x", binding.ciw40296x.getText().toString());

//       ciw403
        sB4.put("ciw303",
                binding.ciw403a.isChecked() ? "1"
                        : binding.ciw403b.isChecked() ? "2"
                        : binding.ciw403c.isChecked() ? "3"
                        : binding.ciw403d.isChecked() ? "4"
                        : binding.ciw403e.isChecked() ? "5"
                        : binding.ciw403961.isChecked() ? "961"
                        : binding.ciw403f.isChecked() ? "6"
                        : binding.ciw403g.isChecked() ? "7"
                        : binding.ciw403h.isChecked() ? "8"
                        : binding.ciw403962.isChecked() ? "962"
                        : binding.ciw403i.isChecked() ? "9"
                        : binding.ciw403j.isChecked() ? "10"
                        : binding.ciw403k.isChecked() ? "11"
                        : binding.ciw403l.isChecked() ? "12"
                        : binding.ciw403m.isChecked() ? "13"
                        : binding.ciw403n.isChecked() ? "14"
                        : binding.ciw403o.isChecked() ? "15"
                        : binding.ciw403p.isChecked() ? "16"
                        : binding.ciw403963.isChecked() ? "963"
                        : "0");
        sB4.put("ciw303961x", binding.ciw403961x.getText().toString());
        sB4.put("ciw303962x", binding.ciw403962x.getText().toString());
        sB4.put("ciw303963x", binding.ciw403963x.getText().toString());

        sB4.put("ciw40301", binding.ciw40301a.isChecked() ? "1"
                : binding.ciw40301b.isChecked() ? "2"
                : binding.ciw40301c.isChecked() ? "3"

                : "0");
//        ciw404
        sB4.put("ciw404", binding.ciw404a.isChecked() ? "1"
                : binding.ciw404b.isChecked() ? "2"
                : binding.ciw404c.isChecked() ? "3"
                : binding.ciw404d.isChecked() ? "4"
                : binding.ciw404e.isChecked() ? "5"
                : binding.ciw40498.isChecked() ? "98"
                : "0");
//        ciw405
        sB4.put("ciw405", binding.ciw405a.isChecked() ? "1"
                : binding.ciw405b.isChecked() ? "2"
                : binding.ciw40598.isChecked() ? "98"
                : "0");
//        ciw406
        sB4.put("ciw406c", binding.ciw406cx.getText().toString());
        sB4.put("ciw406r", binding.ciw406rx.getText().toString());

        sB4.put("ciw40698", binding.ciw40698.isChecked() ? "98" : "0");

//        ciw407
        sB4.put("ciw407", binding.ciw407a.isChecked() ? "1"
                : binding.ciw407b.isChecked() ? "2"
                : binding.ciw40798.isChecked() ? "98"
                : "0");

//        ciw408
        sB4.put("ciw408", binding.ciw408a.isChecked() ? "1"
                : binding.ciw408b.isChecked() ? "2"
                : binding.ciw40898.isChecked() ? "98"
                : "0");

//        ciw409
        sB4.put("ciw409", binding.ciw409a.isChecked() ? "1"
                : binding.ciw409b.isChecked() ? "2"
                : binding.ciw40998.isChecked() ? "98"
                : "0");


//        ciw410
        sB4.put("ciw410h", binding.ciw410h.getText().toString());
        sB4.put("ciw410d", binding.ciw410d.getText().toString());
        sB4.put("ciw410", binding.ciw410a.isChecked() ? "1"
                : binding.ciw410b.isChecked() ? "2"
                : binding.ciw410c.isChecked() ? "3"
                : binding.ciw41097.isChecked() ? "97"
                : binding.ciw41098.isChecked() ? "98"
                : "0");

//        ciw411
        sB4.put("ciw411", binding.ciw411a.isChecked() ? "1"
                : binding.ciw411b.isChecked() ? "2"
                : binding.ciw41198.isChecked() ? "98"
                : "0");

//      ciw412
        sB4.put("ciw412a", binding.ciw412a.isChecked() ? "1" : "0");
        sB4.put("ciw412b", binding.ciw412b.isChecked() ? "2" : "0");
        sB4.put("ciw412c", binding.ciw412c.isChecked() ? "3" : "0");
        sB4.put("ciw412d", binding.ciw412d.isChecked() ? "4" : "0");
        sB4.put("ciw412e", binding.ciw412e.isChecked() ? "5" : "0");
        sB4.put("ciw412f", binding.ciw412f.isChecked() ? "6" : "0");
        sB4.put("ciw41298", binding.ciw41298.isChecked() ? "98" : "0");
        sB4.put("ciw41296", binding.ciw41296.isChecked() ? "96" : "0");

        sB4.put("ciw41296x", binding.ciw41296x.getText().toString());


        //        ciw413
        if (userCountryTajik) {
            sB4.put("ciw413", binding.ciw41301a.isChecked() ? "1"
                    : binding.ciw41301b.isChecked() ? "2"
                    : binding.ciw41301c.isChecked() ? "3"
                    : binding.ciw41301d.isChecked() ? "4"
                    : binding.ciw41301e.isChecked() ? "5"
                    : binding.ciw41301f.isChecked() ? "6"
                    : binding.ciw41301g.isChecked() ? "7"
                    : binding.ciw41301h.isChecked() ? "8"
                    : binding.ciw41301i.isChecked() ? "9"
                    : binding.ciw41301j.isChecked() ? "10"
                    : binding.ciw41301k.isChecked() ? "11"
                    : binding.ciw41301l.isChecked() ? "12"
                    : binding.ciw41301m.isChecked() ? "13"
                    : binding.ciw41301n.isChecked() ? "14"
                    : binding.ciw41301o.isChecked() ? "15"
                    : binding.ciw41301p.isChecked() ? "16"
                    : binding.ciw41301q.isChecked() ? "17"
                    : binding.ciw41301r.isChecked() ? "18"
                    : binding.ciw41301s.isChecked() ? "19"
                    : binding.ciw41301961.isChecked() ? "961"
                    : binding.ciw41301962.isChecked() ? "962"
                    : binding.ciw41301963.isChecked() ? "963"
                    : "0");

            sB4.put("ciw413961x", binding.ciw41301961x.getText().toString());
            sB4.put("ciw413962x", binding.ciw41301962x.getText().toString());
            sB4.put("ciw413963x", binding.ciw41301963x.getText().toString());
        } else {
            sB4.put("ciw413", binding.ciw413a.isChecked() ? "1"
                    : binding.ciw413b.isChecked() ? "2"
                    : binding.ciw413c.isChecked() ? "3"
                    : binding.ciw413d.isChecked() ? "4"
                    : binding.ciw413e.isChecked() ? "5"
                    : binding.ciw413f.isChecked() ? "6"
                    : binding.ciw413g.isChecked() ? "7"
                    : binding.ciw413h.isChecked() ? "8"
                    : binding.ciw413i.isChecked() ? "9"
                    : binding.ciw413j.isChecked() ? "10"
                    : binding.ciw413k.isChecked() ? "11"
                    : binding.ciw413l.isChecked() ? "12"
                    : binding.ciw413m.isChecked() ? "13"
                    : binding.ciw413961.isChecked() ? "961"
                    : binding.ciw413962.isChecked() ? "962"
                    : binding.ciw413963.isChecked() ? "963"
                    : "0");

            sB4.put("ciw413961x", binding.ciw413961x.getText().toString());
            sB4.put("ciw413962x", binding.ciw413962x.getText().toString());
            sB4.put("ciw413963x", binding.ciw413963x.getText().toString());
        }

        MainApp.mc.setsB4(String.valueOf(sB4));

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSB4();

        if (updcount == 1) {
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
    public void onBackPressed() {

        /*try {
            SaveDraft();
            UpdateDB();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onBackPressed();*/

        Toast.makeText(this, "You can't go back!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        formValidation();
    }


    public boolean isoneYes() {

        int i = 0;
        for (CheckBox rg : rd_ciw412) {
            if (rg.isChecked())
                return true;
        }

        return false;
    }

}
