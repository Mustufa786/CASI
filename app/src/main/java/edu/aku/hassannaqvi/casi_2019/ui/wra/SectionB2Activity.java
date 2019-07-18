package edu.aku.hassannaqvi.casi_2019.ui.wra;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONB2ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.MWRAContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionB2Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionB2Activity extends Menu2Activity implements RadioGroup.OnCheckedChangeListener, TextWatcher {

    private final long DELAY = 1000;
    ActivitySectionB2Binding bi;
    DatabaseHelper db;
    Boolean backPressed = false;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b2);
        db = new DatabaseHelper(this);
        bi.setCallback(this);

        setupViews();

        this.setTitle(getResources().getString(R.string.nb2heading));
        bi.textName.setText("Selected Woman : " + SectionB1Activity.wraName);


//        Validation Boolean
        MainApp.validateFlag = false;

        AutoCompleteFields();

    }

    public void setupViews() {
        bi.ciw301.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ValidateForm();
                if (bi.ciw301a.isChecked()) {
                    bi.fldGrpciw302.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpciw302, true);
                    ClearClass.ClearAllFields(bi.fldGrpciw302check, true);
                    ClearClass.ClearAllFields(bi.fldGrpciw306check, true);
                } else {
                    bi.fldGrpciw302.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(bi.fldGrpciw302, false);
                    ClearClass.ClearAllFields(bi.fldGrpciw302check, false);
                    ClearClass.ClearAllFields(bi.fldGrpciw306check, false);

                }
            }
        });

        bi.ciw303.setOnCheckedChangeListener(this);
        bi.ciw304w.addTextChangedListener(this);
        bi.ciw304m.addTextChangedListener(this);
        bi.ciw305.addTextChangedListener(this);
        bi.ciw307.setOnCheckedChangeListener(this);


        bi.ciw306i.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bi.ciw306i.isChecked()) {
                    bi.ciw306a.setChecked(false);
                    bi.ciw306b.setChecked(false);
                    bi.ciw306c.setChecked(false);
                    bi.ciw306d.setChecked(false);
                    bi.ciw306e.setChecked(false);
                    bi.ciw306f.setChecked(false);
                    bi.ciw306g.setChecked(false);
                    bi.ciw306h.setChecked(false);
                    bi.ciw30696.setChecked(false);

                    bi.ciw306a.setEnabled(false);
                    bi.ciw306b.setEnabled(false);
                    bi.ciw306c.setEnabled(false);
                    bi.ciw306d.setEnabled(false);
                    bi.ciw306e.setEnabled(false);
                    bi.ciw306f.setEnabled(false);
                    bi.ciw306g.setEnabled(false);
                    bi.ciw306h.setEnabled(false);
                    bi.ciw30696.setEnabled(false);
                } else {
                    bi.ciw306a.setEnabled(true);
                    bi.ciw306b.setEnabled(true);
                    bi.ciw306c.setEnabled(true);
                    bi.ciw306d.setEnabled(true);
                    bi.ciw306e.setEnabled(true);
                    bi.ciw306f.setEnabled(true);
                    bi.ciw306g.setEnabled(true);
                    bi.ciw306h.setEnabled(true);
                    bi.ciw30696.setEnabled(true);
                }
            }
        });
        bi.ciw308.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ValidateForm();
                if (bi.ciw308a.isChecked()) {

                    //bi.fldGrpciw309.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpciw309, true);
                    bi.fldGrpciw309.setVisibility(View.VISIBLE);

                } else {

                    ClearClass.ClearAllFields(bi.fldGrpciw309, false);
                    bi.fldGrpciw309.setVisibility(View.GONE);

                }
            }
        });

        bi.ciw309.addTextChangedListener(this);
        bi.ciw311.setOnCheckedChangeListener(this);
        bi.ciw313.setOnCheckedChangeListener(this);
        bi.ciw314m.addTextChangedListener(this);
        bi.ciw314d.addTextChangedListener(this);
        bi.ciw316.setOnCheckedChangeListener(this);
        bi.ciw318.setOnCheckedChangeListener(this);
        bi.ciw319m.addTextChangedListener(this);
        bi.ciw319d.addTextChangedListener(this);
        bi.ciw321.setOnCheckedChangeListener(this);
        bi.ciw323.setOnCheckedChangeListener(this);
        bi.ciw324m.addTextChangedListener(this);
        bi.ciw324d.addTextChangedListener(this);
        bi.ciw325.setOnCheckedChangeListener(this);
        bi.ciw326.setOnCheckedChangeListener(this);

        bi.ciw310.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ValidateForm();
                if (bi.ciw310a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpnb210, true);
                    bi.fldGrpnb210.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpciw312, true);
                    bi.fldGrpciw312.setVisibility(View.VISIBLE);
                    //bi.fldGrpnb210.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpnb210.setVisibility(View.GONE);
                    bi.fldGrpciw312.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(bi.fldGrpnb210, false);
                    ClearClass.ClearAllFields(bi.fldGrpciw312, false);

                }
            }
        });

        bi.ciw315.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ValidateForm();
                if (bi.ciw315a.isChecked()) {
                    //bi.fldGrpciw318.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpciw318, true);
                    bi.fldGrpciw318.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpciw317, true);
                    bi.fldGrpciw317.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpciw318.setVisibility(View.GONE);
                    bi.fldGrpciw317.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(bi.fldGrpciw318, false);
                    ClearClass.ClearAllFields(bi.fldGrpciw317, false);

                }
            }
        });


        bi.ciw320.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ValidateForm();
                if (bi.ciw320a.isChecked()) {
                    //bi.fldGrpciw323.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpciw323, true);
                    ClearClass.ClearAllFields(bi.fldGrpciw322, true);
                    bi.fldGrpciw323.setVisibility(View.VISIBLE);
                    bi.fldGrpciw322.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpciw323.setVisibility(View.GONE);
                    bi.fldGrpciw322.setVisibility(View.GONE);

                    ClearClass.ClearAllFields(bi.fldGrpciw323, false);
                    ClearClass.ClearAllFields(bi.fldGrpciw322, false);

                }
            }
        });


//        Setting name of women
        bi.ciw301Txt.setText(getString(R.string.ciw301a) + " " + SectionB1Activity.wraName + " " + getString(R.string.ciw301));
    }

    public void AutoCompleteFields() {

//        BackPressed event

        MWRAContract mwraContract = db.getsB2();
        if (!mwraContract.getsB2().equals("")) {

            JSONB2ModelClass jsonB2 = JSONUtilClass.getModelFromJSON(mwraContract.getsB2(), JSONB2ModelClass.class);

            if (!jsonB2.getciw301().equals("0")) {
                bi.ciw301.check(
                        jsonB2.getciw301().equals("1") ? bi.ciw301a.getId() :
                                bi.ciw301b.getId()
                );
            }
            if (!jsonB2.getciw302a().equals("0")) {
                bi.ciw302a.setChecked(true);
            }
            if (!jsonB2.getciw302b().equals("0")) {
                bi.ciw302b.setChecked(true);
            }
            if (!jsonB2.getciw302c().equals("0")) {
                bi.ciw302c.setChecked(true);
            }
            if (!jsonB2.getciw302d().equals("0")) {
                bi.ciw302d.setChecked(true);
            }
            if (!jsonB2.getciw302e().equals("0")) {
                bi.ciw302e.setChecked(true);
            }
            if (!jsonB2.getciw302f().equals("0")) {
                bi.ciw302f.setChecked(true);
            }
            if (!jsonB2.getciw302g().equals("0")) {
                bi.ciw302g.setChecked(true);
            }
            if (!jsonB2.getciw302h().equals("0")) {
                bi.ciw302h.setChecked(true);
            }
            if (!jsonB2.getciw30296().equals("0")) {
                bi.ciw30296.setChecked(true);
                bi.ciw30296x.setText(jsonB2.getciw30296x());
            }

            if (!jsonB2.getciw303().equals("0")) {
                bi.ciw303.check(
                        jsonB2.getciw303().equals("1") ? bi.ciw303a.getId() :
                                jsonB2.getciw303().equals("2") ? bi.ciw303b.getId() :
                                        jsonB2.getciw303().equals("3") ? bi.ciw303c.getId() :
                                                jsonB2.getciw303().equals("4") ? bi.ciw303d.getId() :
                                                        jsonB2.getciw303().equals("5") ? bi.ciw303e.getId() :
                                                                jsonB2.getciw303().equals("6") ? bi.ciw303f.getId() :
                                                                        jsonB2.getciw303().equals("7") ? bi.ciw303g.getId() :
                                                                                jsonB2.getciw303().equals("8") ? bi.ciw303h.getId() :
                                                                                        jsonB2.getciw303().equals("9") ? bi.ciw303i.getId() :
                                                                                                jsonB2.getciw303().equals("10") ? bi.ciw303j.getId() :
                                                                                                        bi.ciw30396.getId()
                );

                bi.ciw303961x.setText(jsonB2.getciw303961x());
                bi.ciw303962x.setText(jsonB2.getciw303962x());
                bi.ciw303963x.setText(jsonB2.getciw303963x());
            }

            bi.ciw304w.setText(jsonB2.getciw304());
            if (!jsonB2.getciw30498().equals("0")) {
                bi.ciw30498.setChecked(true);
            }

            bi.ciw305.setText(jsonB2.getciw304());
            if (!jsonB2.getciw305().equals("0")) {
                bi.ciw30598.setChecked(true);
            }

            if (!jsonB2.getciw306a().equals("0")) {
                bi.ciw306a.setChecked(true);
            }
            if (!jsonB2.getciw306b().equals("0")) {
                bi.ciw306b.setChecked(true);
            }
            if (!jsonB2.getciw306c().equals("0")) {
                bi.ciw306c.setChecked(true);
            }
            if (!jsonB2.getciw306d().equals("0")) {
                bi.ciw306d.setChecked(true);
            }
            if (!jsonB2.getciw306e().equals("0")) {
                bi.ciw306e.setChecked(true);
            }
            if (!jsonB2.getciw306f().equals("0")) {
                bi.ciw306f.setChecked(true);
            }
            if (!jsonB2.getciw306g().equals("0")) {
                bi.ciw306g.setChecked(true);
            }
            if (!jsonB2.getciw306h().equals("0")) {
                bi.ciw306h.setChecked(true);
            }
            if (!jsonB2.getciw306i().equals("0")) {
                bi.ciw306i.setChecked(true);
            }
            if (!jsonB2.getciw30696().equals("0")) {
                bi.ciw30696.setChecked(true);
                bi.ciw30696x.setText(jsonB2.getciw30696x());
            }

            if (!jsonB2.getciw307().equals("0")) {
                bi.ciw307.check(
                        jsonB2.getciw307().equals("1") ? bi.ciw307a.getId() :
                                jsonB2.getciw307().equals("2") ? bi.ciw307b.getId() :
                                        jsonB2.getciw307().equals("3") ? bi.ciw307c.getId() :
                                                bi.ciw30798.getId());
            }

            if (!jsonB2.getciw308().equals("0")) {
                bi.ciw308.check(
                        jsonB2.getciw308().equals("1") ? bi.ciw308a.getId() :
                                jsonB2.getciw308().equals("2") ? bi.ciw308b.getId() :
                                        bi.ciw30898.getId());
            }

            if (jsonB2.getciw309().equals("98")) {
                bi.ciw30998.setChecked(true);
            } else {
                bi.ciw309.setText(jsonB2.getciw309());
            }

            if (!jsonB2.getciw310().equals("0")) {
                bi.ciw310.check(
                        jsonB2.getciw310().equals("1") ? bi.ciw310a.getId() :
                                bi.ciw310b.getId());
            }

            if (!jsonB2.getciw311().equals("0")) {
                bi.ciw311.check(
                        jsonB2.getciw311().equals("1") ? bi.ciw311a.getId() :
                                jsonB2.getciw311().equals("2") ? bi.ciw311b.getId() :
                                        jsonB2.getciw311().equals("3") ? bi.ciw311c.getId() :
                                                jsonB2.getciw311().equals("4") ? bi.ciw311d.getId() :
                                                        jsonB2.getciw311().equals("5") ? bi.ciw311e.getId() :
                                                                jsonB2.getciw311().equals("6") ? bi.ciw311f.getId() :
                                                                        jsonB2.getciw311().equals("7") ? bi.ciw311g.getId() :
                                                                                jsonB2.getciw311().equals("8") ? bi.ciw311h.getId() :
                                                                                        bi.ciw31196.getId()
                );
                bi.ciw31196x.setText(jsonB2.getciw31196x());
            }

            if (!jsonB2.getciw312a().equals("0")) {
                bi.ciw312a.setChecked(true);
            }
            if (!jsonB2.getciw312b().equals("0")) {
                bi.ciw312b.setChecked(true);
            }
            if (!jsonB2.getciw312c().equals("0")) {
                bi.ciw312c.setChecked(true);
            }
            if (!jsonB2.getciw312d().equals("0")) {
                bi.ciw312d.setChecked(true);
            }
            if (!jsonB2.getciw312e().equals("0")) {
                bi.ciw312e.setChecked(true);
            }
            if (!jsonB2.getciw312f().equals("0")) {
                bi.ciw312f.setChecked(true);
            }
            if (!jsonB2.getciw312g().equals("0")) {
                bi.ciw312g.setChecked(true);
            }
            if (!jsonB2.getciw312h().equals("0")) {
                bi.ciw312h.setChecked(true);
            }
            if (!jsonB2.getciw312i().equals("0")) {
                bi.ciw312i.setChecked(true);
            }
            if (!jsonB2.getciw312j().equals("0")) {
                bi.ciw312j.setChecked(true);
            }
            if (!jsonB2.getciw312k().equals("0")) {
                bi.ciw312k.setChecked(true);
            }
            if (!jsonB2.getciw312l().equals("0")) {
                bi.ciw312l.setChecked(true);
            }
            if (!jsonB2.getciw312m().equals("0")) {
                bi.ciw312m.setChecked(true);
            }
            if (!jsonB2.getciw312961().equals("0")) {
                bi.ciw312961.setChecked(true);
            }
            if (!jsonB2.getciw312962().equals("0")) {
                bi.ciw312962.setChecked(true);
            }
            if (!jsonB2.getciw312963().equals("0")) {
                bi.ciw312963.setChecked(true);
            }
            bi.ciw312961x.setText(jsonB2.getciw312961x());
            bi.ciw312962x.setText(jsonB2.getciw312962x());
            bi.ciw312963x.setText(jsonB2.getciw312963x());

            if (!jsonB2.getciw313().equals("0")) {
                bi.ciw313.check(
                        jsonB2.getciw313().equals("1") ? bi.ciw313a.getId() :
                                jsonB2.getciw313().equals("2") ? bi.ciw313b.getId() :
                                        jsonB2.getciw313().equals("3") ? bi.ciw313c.getId() :
                                                jsonB2.getciw313().equals("4") ? bi.ciw313d.getId() :
                                                        bi.ciw313e.getId()
                );
            }

            bi.ciw314m.setText(jsonB2.getciw314m());
            bi.ciw314d.setText(jsonB2.getciw314d());

            if (!jsonB2.getciw315().equals("0")) {
                bi.ciw315.check(
                        jsonB2.getciw315().equals("1") ? bi.ciw315a.getId() :
                                bi.ciw315b.getId());
            }

            if (!jsonB2.getciw316().equals("0")) {
                bi.ciw316.check(
                        jsonB2.getciw316().equals("1") ? bi.ciw316a.getId() :
                                jsonB2.getciw316().equals("2") ? bi.ciw316b.getId() :
                                        jsonB2.getciw316().equals("3") ? bi.ciw316c.getId() :
                                                jsonB2.getciw316().equals("4") ? bi.ciw316d.getId() :
                                                        jsonB2.getciw316().equals("5") ? bi.ciw316e.getId() :
                                                                jsonB2.getciw316().equals("6") ? bi.ciw316f.getId() :
                                                                        jsonB2.getciw316().equals("7") ? bi.ciw316g.getId() :
                                                                                jsonB2.getciw316().equals("8") ? bi.ciw316h.getId() :
                                                                                        bi.ciw31696.getId()
                );
                bi.ciw31696x.setText(jsonB2.getciw31696x());
            }

            if (!jsonB2.getciw317a().equals("0")) {
                bi.ciw317a.setChecked(true);
            }
            if (!jsonB2.getciw317b().equals("0")) {
                bi.ciw317b.setChecked(true);
            }
            if (!jsonB2.getciw317c().equals("0")) {
                bi.ciw317c.setChecked(true);
            }
            if (!jsonB2.getciw317d().equals("0")) {
                bi.ciw317d.setChecked(true);
            }
            if (!jsonB2.getciw317e().equals("0")) {
                bi.ciw317e.setChecked(true);
            }
            if (!jsonB2.getciw317f().equals("0")) {
                bi.ciw317f.setChecked(true);
            }
            if (!jsonB2.getciw317g().equals("0")) {
                bi.ciw317g.setChecked(true);
            }
            if (!jsonB2.getciw317h().equals("0")) {
                bi.ciw317h.setChecked(true);
            }
            if (!jsonB2.getciw317i().equals("0")) {
                bi.ciw317i.setChecked(true);
            }
            if (!jsonB2.getciw317j().equals("0")) {
                bi.ciw317j.setChecked(true);
            }
            if (!jsonB2.getciw317k().equals("0")) {
                bi.ciw317k.setChecked(true);
            }
            if (!jsonB2.getciw317l().equals("0")) {
                bi.ciw317l.setChecked(true);
            }
            if (!jsonB2.getciw317m().equals("0")) {
                bi.ciw317m.setChecked(true);
            }
            if (!jsonB2.getciw317961().equals("0")) {
                bi.ciw317961.setChecked(true);
            }
            if (!jsonB2.getciw317962().equals("0")) {
                bi.ciw317962.setChecked(true);
            }
            if (!jsonB2.getciw317963().equals("0")) {
                bi.ciw317963.setChecked(true);
            }
            bi.ciw317961x.setText(jsonB2.getciw317961x());
            bi.ciw317962x.setText(jsonB2.getciw317962x());
            bi.ciw317963x.setText(jsonB2.getciw317963x());

            if (!jsonB2.getciw318().equals("0")) {
                bi.ciw318.check(
                        jsonB2.getciw318().equals("1") ? bi.ciw318a.getId() :
                                jsonB2.getciw318().equals("2") ? bi.ciw318b.getId() :
                                        jsonB2.getciw318().equals("3") ? bi.ciw318c.getId() :
                                                jsonB2.getciw318().equals("4") ? bi.ciw318d.getId() :
                                                        bi.ciw318e.getId()
                );
            }

            bi.ciw319m.setText(jsonB2.getciw319m());
            bi.ciw319d.setText(jsonB2.getciw319d());

            if (!jsonB2.getciw320().equals("0")) {
                bi.ciw320.check(
                        jsonB2.getciw320().equals("1") ? bi.ciw320a.getId() :
                                bi.ciw320b.getId());
            }


            if (!jsonB2.getciw321().equals("0")) {
                bi.ciw321.check(
                        jsonB2.getciw321().equals("1") ? bi.ciw321a.getId() :
                                jsonB2.getciw321().equals("2") ? bi.ciw321b.getId() :
                                        jsonB2.getciw321().equals("3") ? bi.ciw321c.getId() :
                                                jsonB2.getciw321().equals("4") ? bi.ciw321d.getId() :
                                                        jsonB2.getciw321().equals("5") ? bi.ciw321e.getId() :
                                                                jsonB2.getciw321().equals("6") ? bi.ciw321f.getId() :
                                                                        jsonB2.getciw321().equals("7") ? bi.ciw321g.getId() :
                                                                                jsonB2.getciw321().equals("8") ? bi.ciw321h.getId() :
                                                                                        bi.ciw32196.getId()
                );
                bi.ciw32196x.setText(jsonB2.getciw32196x());
            }

            if (!jsonB2.getciw322a().equals("0")) {
                bi.ciw322a.setChecked(true);
            }
            if (!jsonB2.getciw322b().equals("0")) {
                bi.ciw322b.setChecked(true);
            }
            if (!jsonB2.getciw322c().equals("0")) {
                bi.ciw322c.setChecked(true);
            }
            if (!jsonB2.getciw322d().equals("0")) {
                bi.ciw322d.setChecked(true);
            }
            if (!jsonB2.getciw322e().equals("0")) {
                bi.ciw322e.setChecked(true);
            }
            if (!jsonB2.getciw322f().equals("0")) {
                bi.ciw322f.setChecked(true);
            }
            if (!jsonB2.getciw322g().equals("0")) {
                bi.ciw322g.setChecked(true);
            }
            if (!jsonB2.getciw322h().equals("0")) {
                bi.ciw322h.setChecked(true);
            }
            if (!jsonB2.getciw322i().equals("0")) {
                bi.ciw322i.setChecked(true);
            }
            if (!jsonB2.getciw322j().equals("0")) {
                bi.ciw322j.setChecked(true);
            }
            if (!jsonB2.getciw322k().equals("0")) {
                bi.ciw322k.setChecked(true);
            }
            if (!jsonB2.getciw322l().equals("0")) {
                bi.ciw322l.setChecked(true);
            }
            if (!jsonB2.getciw322m().equals("0")) {
                bi.ciw322m.setChecked(true);
            }
            if (!jsonB2.getciw322961().equals("0")) {
                bi.ciw322961.setChecked(true);
            }
            if (!jsonB2.getciw322962().equals("0")) {
                bi.ciw322962.setChecked(true);
            }
            if (!jsonB2.getciw322963().equals("0")) {
                bi.ciw322963.setChecked(true);
            }
            bi.ciw322961x.setText(jsonB2.getciw322961x());
            bi.ciw322962x.setText(jsonB2.getciw322962x());
            bi.ciw322963x.setText(jsonB2.getciw322963x());

            if (!jsonB2.getciw323().equals("0")) {
                bi.ciw323.check(
                        jsonB2.getciw323().equals("1") ? bi.ciw323a.getId() :
                                jsonB2.getciw323().equals("2") ? bi.ciw323b.getId() :
                                        jsonB2.getciw323().equals("3") ? bi.ciw323c.getId() :
                                                jsonB2.getciw323().equals("4") ? bi.ciw323d.getId() :
                                                        bi.ciw323e.getId()
                );
            }

            bi.ciw324m.setText(jsonB2.getciw324m());
            bi.ciw324d.setText(jsonB2.getciw324d());

            if (!jsonB2.getciw325().equals("0")) {
                bi.ciw325.check(
                        jsonB2.getciw325().equals("1") ? bi.ciw325a.getId() :
                                bi.ciw325b.getId());
            }
            if (!jsonB2.getciw326().equals("0")) {
                bi.ciw326.check(
                        jsonB2.getciw326().equals("1") ? bi.ciw326a.getId() :
                                jsonB2.getciw326().equals("2") ? bi.ciw326b.getId() :
                                        bi.ciw32698.getId());
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


                backPressed = true;

//                finish();

                if (!MainApp.B2B6Flag) {
                    MainApp.B2B6Flag = true;
                }

                startActivity(new Intent(this, SectionB3Activity.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

        //startActivity(new Intent(this, SectionB3Activity.class));
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


        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw301, bi.ciw301a, getString(R.string.ciw301) + " " + SectionB1Activity.wraName + " " + getString(R.string.ciw301a))) {
            return false;
        }

        if (bi.ciw301a.isChecked()) {
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw302check, bi.ciw302a, getString(R.string.ciw302))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw302check, bi.ciw30296, bi.ciw30296x, getString(R.string.ciw302) + " - " + getString(R.string.other))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw303, bi.ciw303a, getString(R.string.ciw303))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw303, bi.ciw303f, bi.ciw303961x, getString(R.string.ciw303) + " - " + getString(R.string.ciw303f))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw303, bi.ciw303j, bi.ciw303962x, getString(R.string.ciw303) + " - " + getString(R.string.ciw303j))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw303, bi.ciw30396, bi.ciw303963x, getString(R.string.ciw303) + " - " + getString(R.string.other))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw304, bi.ciw304a, getString(R.string.ciw304))) {
                return false;
            }
            if (bi.ciw304a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ciw304w, getString(R.string.ciw304) + " - " + getString(R.string.week))) {
                    return false;
                }

                if (bi.ciw304m.getText().toString().equals("0")) {
                    if (!ValidatorClass.RangeTextBox(this, bi.ciw304w, 2, 44, getString(R.string.ciw304), " weeks")) {
                        return false;
                    }
                } else {
                    if (!ValidatorClass.RangeTextBox(this, bi.ciw304w, 0, 44, getString(R.string.ciw304), " weeks")) {
                        return false;
                    }
                }
            }

            if (bi.ciw304b.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, bi.ciw304m, getString(R.string.ciw304) + " - " + getString(R.string.months))) {
                    return false;
                }

                if (bi.ciw304w.getText().toString().equals("0")) {
                    if (!ValidatorClass.RangeTextBox(this, bi.ciw304m, 1, 9, getString(R.string.ciw203), " months")) {
                        return false;
                    }
                } else {
                    if (!ValidatorClass.RangeTextBox(this, bi.ciw304m, 0, 9, getString(R.string.ciw203), " months")) {
                        return false;
                    }
                }

                if (bi.ciw304w.getText().toString().equals("0") && bi.ciw304m.getText().toString().equals("0")) {
                    Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.ciw304), Toast.LENGTH_LONG).show();
                    bi.ciw304w.setError("All can not be zero");
                    bi.ciw304w.setError("All can not be zero");
                    Log.i(SectionB2Activity.class.getSimpleName(), "ciw304" + ": This data is Required!");
                    return false;
                } else {
                    bi.ciw304w.setError(null);
                    bi.ciw304m.setError(null);

                }
            }

            if (!bi.ciw30598.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ciw305, getString(R.string.ciw305) + " - " + getString(R.string.times))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, bi.ciw305, 1, 15, getString(R.string.ciw305), " times")) {
                    return false;
                }
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw306check, bi.ciw306a, getString(R.string.ciw306))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw306check, bi.ciw30696, bi.ciw30696x, getString(R.string.ciw306) + " - " + getString(R.string.other))) {
                return false;
            }

        }
        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw307, bi.ciw307a, getString(R.string.ciw307))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw308, bi.ciw308a, getString(R.string.ciw308))) {
            return false;
        }

        if (bi.ciw308a.isChecked()) {
            if (!bi.ciw30998.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ciw309, getString(R.string.ciw309) + " - " + getString(R.string.times))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.ciw309, 1, 5, getString(R.string.ciw203), " times")) {
                    return false;
                }
            }
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw310, bi.ciw310a, getString(R.string.ciw310))) {
            return false;
        }

        if (bi.ciw310a.isChecked()) {


            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw311, bi.ciw311a, getString(R.string.ciw311))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw311, bi.ciw31196, bi.ciw31196x, getString(R.string.ciw311))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw312, bi.ciw312a, getString(R.string.ciw312))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw312, bi.ciw312961, bi.ciw312961x, getString(R.string.ciw312))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw312, bi.ciw312962, bi.ciw312962x, getString(R.string.ciw312))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw312, bi.ciw312963, bi.ciw312963x, getString(R.string.ciw312))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw313, bi.ciw313a, getString(R.string.ciw313))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.ciw314m, getString(R.string.ciw314) + " - " + getString(R.string.months))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.ciw314d, getString(R.string.ciw314) + " - " + getString(R.string.day))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.ciw314m, 0, 11, getString(R.string.ciw314), " months")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.ciw314d, 0, 29, getString(R.string.ciw314), " days")) {
                return false;
            }

            if (bi.ciw314m.getText().toString().equals("0") && bi.ciw314d.getText().toString().equals("0")) {
                Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.ciw314), Toast.LENGTH_LONG).show();
                bi.ciw314m.setError("All can not be zero");
                bi.ciw314d.setError("All can not be zero");
                Log.i(SectionB2Activity.class.getSimpleName(), "ciw314" + ": This data is Required!");
                return false;
            } else {
                bi.ciw314m.setError(null);
                bi.ciw314d.setError(null);

            }

        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw315, bi.ciw315a, getString(R.string.ciw315))) {
            return false;
        }

        if (bi.ciw315a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw316, bi.ciw316a, getString(R.string.ciw316))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw316, bi.ciw31696, bi.ciw31696x, getString(R.string.ciw316))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw317, bi.ciw317a, getString(R.string.ciw317))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw317, bi.ciw317961, bi.ciw317961x, getString(R.string.ciw317))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw317, bi.ciw317962, bi.ciw317962x, getString(R.string.ciw317))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw317, bi.ciw317963, bi.ciw317963x, getString(R.string.ciw317))) {
                return false;
            }


            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw318, bi.ciw318a, getString(R.string.ciw318))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.ciw319m, getString(R.string.ciw319) + " - " + getString(R.string.month))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.ciw319d, getString(R.string.ciw319) + " - " + getString(R.string.day))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.ciw319m, 0, 11, getString(R.string.ciw319), " months")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.ciw319d, 0, 29, getString(R.string.ciw319), " days")) {
                return false;
            }

            if (bi.ciw319m.getText().toString().equals("0") && bi.ciw319d.getText().toString().equals(" 0")) {
                Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.ciw319), Toast.LENGTH_LONG).show();
                bi.ciw319m.setError("All can not be zero");
                bi.ciw319d.setError("All can not be zero");
                Log.i(SectionB2Activity.class.getSimpleName(), "ciw319" + ": This data is Required!");
                return false;
            } else {
                bi.ciw319m.setError(null);
                bi.ciw319d.setError(null);

            }

        }


        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw320, bi.ciw320a, getString(R.string.ciw320))) {
            return false;
        }

        if (bi.ciw320a.isChecked()) {


            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw321, bi.ciw321a, getString(R.string.ciw321))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw321, bi.ciw32196, bi.ciw32196x, getString(R.string.ciw321))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw322, bi.ciw322a, getString(R.string.ciw322))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw322, bi.ciw322961, bi.ciw322961x, getString(R.string.ciw322))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw322, bi.ciw322962, bi.ciw322962x, getString(R.string.ciw322))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpciw322, bi.ciw322963, bi.ciw322963x, getString(R.string.ciw322))) {
                return false;
            }


            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw323, bi.ciw323a, getString(R.string.ciw323))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.ciw324m, getString(R.string.ciw324) + " - " + getString(R.string.month))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.ciw324d, getString(R.string.ciw324) + " - " + getString(R.string.day))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.ciw324m, 0, 11, getString(R.string.ciw324), " months")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.ciw324d, 0, 29, getString(R.string.ciw324), " days")) {
                return false;
            }

            if (bi.ciw324m.getText().toString().equals("0") && bi.ciw324d.getText().toString().equals("0")) {
                Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.ciw324), Toast.LENGTH_LONG).show();
                bi.ciw324m.setError("All can not be zero");

                bi.ciw324d.setError("All can not be zero");
                Log.i(SectionB2Activity.class.getSimpleName(), "ciw324" + ": This data is Required!");
                return false;
            } else {
                bi.ciw324m.setError(null);
                bi.ciw324d.setError(null);

            }

        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw325, bi.ciw325a, getString(R.string.ciw325))) {
            return false;
        }

        return ValidatorClass.EmptyRadioButton(this, bi.ciw326, bi.ciw326a, getString(R.string.ciw326));
    }

    private void SaveDraft() throws JSONException {

//       ciw301
        JSONObject sB2 = new JSONObject();

        if (backPressed) {
            sB2.put("updatedate_ciw3", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        sB2.put("ciw301", bi.ciw301a.isChecked() ? "1"
                : bi.ciw301b.isChecked() ? "2"
                : "0");

//       ciw302
        sB2.put("ciw302a", bi.ciw302a.isChecked() ? "1" : "0");
        sB2.put("ciw302b", bi.ciw302b.isChecked() ? "2" : "0");
        sB2.put("ciw302c", bi.ciw302c.isChecked() ? "3" : "0");
        sB2.put("ciw302d", bi.ciw302d.isChecked() ? "4" : "0");
        sB2.put("ciw302e", bi.ciw302e.isChecked() ? "5" : "0");
        sB2.put("ciw302f", bi.ciw302f.isChecked() ? "6" : "0");
        sB2.put("ciw302g", bi.ciw302g.isChecked() ? "7" : "0");
        sB2.put("ciw302h", bi.ciw302h.isChecked() ? "8" : "0");
        sB2.put("ciw30296", bi.ciw30296.isChecked() ? "96" : "0");

        sB2.put("ciw30296x", bi.ciw30296x.getText().toString());

//      ciw303
        sB2.put("ciw303", bi.ciw303a.isChecked() ? "1"
                : bi.ciw303b.isChecked() ? "2"
                : bi.ciw303c.isChecked() ? "3"
                : bi.ciw303d.isChecked() ? "4"
                : bi.ciw303e.isChecked() ? "5"
                : bi.ciw303f.isChecked() ? "961"
                : bi.ciw303g.isChecked() ? "7"
                : bi.ciw303h.isChecked() ? "8"
                : bi.ciw303i.isChecked() ? "9"
                : bi.ciw303j.isChecked() ? "962"
                : bi.ciw30396.isChecked() ? "963"
                : "0");
        sB2.put("ciw303961x", bi.ciw303961x.getText().toString());
        sB2.put("ciw303962x", bi.ciw303962x.getText().toString());
        sB2.put("ciw303963x", bi.ciw303963x.getText().toString());

//        ciw304
        sB2.put("ciw304", bi.ciw304a.isChecked() ? "1" : bi.ciw304b.isChecked() ? "2" : bi.ciw30498.isChecked() ? "98" : "0");
        sB2.put("ciw304w", bi.ciw304w.getText().toString());
        sB2.put("ciw304m", bi.ciw304m.getText().toString());

//        ciw204
        sB2.put("ciw305", bi.ciw305.getText().toString());
        sB2.put("ciw30598", bi.ciw30598.isChecked() ? "98" : "0");

//      ciw306
        sB2.put("ciw306a", bi.ciw306a.isChecked() ? "1" : "0");
        sB2.put("ciw306b", bi.ciw306b.isChecked() ? "2" : "0");
        sB2.put("ciw306c", bi.ciw306c.isChecked() ? "3" : "0");
        sB2.put("ciw306d", bi.ciw306d.isChecked() ? "4" : "0");
        sB2.put("ciw306e", bi.ciw306e.isChecked() ? "5" : "0");
        sB2.put("ciw306f", bi.ciw306f.isChecked() ? "6" : "0");
        sB2.put("ciw306g", bi.ciw306g.isChecked() ? "7" : "0");
        sB2.put("ciw306h", bi.ciw306h.isChecked() ? "8" : "0");
        sB2.put("ciw306i", bi.ciw306i.isChecked() ? "9" : "0");
        sB2.put("ciw30696", bi.ciw30696.isChecked() ? "96" : "0");
        sB2.put("ciw30696x", bi.ciw30696x.getText().toString());

//        ciw307
        sB2.put("ciw307", bi.ciw307a.isChecked() ? "1"
                : bi.ciw307b.isChecked() ? "2"
                : bi.ciw307c.isChecked() ? "3"
                : bi.ciw30798.isChecked() ? "98"
                : "0");
//        ciw308
        sB2.put("ciw308", bi.ciw308a.isChecked() ? "1"
                : bi.ciw308b.isChecked() ? "2"
                : bi.ciw30898.isChecked() ? "98"
                : "0");
//        ciw309

        sB2.put("ciw309", bi.ciw30998.isChecked() ? "98" : bi.ciw309.getText().toString());
//        sB2.put("ciw30998", bi.ciw30998.isChecked() ? "98" : "0");

//        ciw310
        sB2.put("ciw310", bi.ciw310a.isChecked() ? "1"
                : bi.ciw310b.isChecked() ? "2"
                : "0");

//          ciw311
        sB2.put("ciw311", bi.ciw311a.isChecked() ? "1"
                : bi.ciw311b.isChecked() ? "2"
                : bi.ciw311c.isChecked() ? "3"
                : bi.ciw311d.isChecked() ? "4"
                : bi.ciw311e.isChecked() ? "5"
                : bi.ciw311f.isChecked() ? "6"
                : bi.ciw311g.isChecked() ? "7"
                : bi.ciw311h.isChecked() ? "8"
                : bi.ciw31196.isChecked() ? "96"
                : "0");
        sB2.put("ciw31196x", bi.ciw31196x.getText().toString());
//        ciw312
        sB2.put("ciw312a", bi.ciw312a.isChecked() ? "1" : "0");
        sB2.put("ciw312b", bi.ciw312b.isChecked() ? "2" : "0");
        sB2.put("ciw312c", bi.ciw312c.isChecked() ? "3" : "0");
        sB2.put("ciw312d", bi.ciw312d.isChecked() ? "4" : "0");
        sB2.put("ciw312e", bi.ciw312e.isChecked() ? "5" : "0");
        sB2.put("ciw312f", bi.ciw312f.isChecked() ? "6" : "0");
        sB2.put("ciw312g", bi.ciw312g.isChecked() ? "7" : "0");
        sB2.put("ciw312h", bi.ciw312h.isChecked() ? "8" : "0");
        sB2.put("ciw312i", bi.ciw312i.isChecked() ? "9" : "0");
        sB2.put("ciw312j", bi.ciw312j.isChecked() ? "10" : "0");
        sB2.put("ciw312k", bi.ciw312k.isChecked() ? "11" : "0");
        sB2.put("ciw312l", bi.ciw312l.isChecked() ? "12" : "0");
        sB2.put("ciw312m", bi.ciw312m.isChecked() ? "13" : "0");
        sB2.put("ciw312961", bi.ciw312961.isChecked() ? "961" : "0");
        sB2.put("ciw312962", bi.ciw312962.isChecked() ? "962" : "0");
        sB2.put("ciw312963", bi.ciw312963.isChecked() ? "963" : "0");

        sB2.put("ciw312961x", bi.ciw312961x.getText().toString());
        sB2.put("ciw312962x", bi.ciw312962x.getText().toString());
        sB2.put("ciw312963x", bi.ciw312963x.getText().toString());

//         ciw313
        sB2.put("ciw313", bi.ciw313a.isChecked() ? "1"
                : bi.ciw313b.isChecked() ? "2"
                : bi.ciw313c.isChecked() ? "3"
                : bi.ciw313d.isChecked() ? "4"
                : bi.ciw313e.isChecked() ? "5"
                : "0");

//        ciw314
        sB2.put("ciw314m", bi.ciw314m.getText().toString());
        sB2.put("ciw314d", bi.ciw314d.getText().toString());

        //        ciw315
        sB2.put("ciw315", bi.ciw315a.isChecked() ? "1"
                : bi.ciw315b.isChecked() ? "2"
                : "0");

//        ciw316

        sB2.put("ciw316", bi.ciw316a.isChecked() ? "1"
                : bi.ciw316b.isChecked() ? "2"
                : bi.ciw316c.isChecked() ? "3"
                : bi.ciw316d.isChecked() ? "4"
                : bi.ciw316e.isChecked() ? "5"
                : bi.ciw316f.isChecked() ? "6"
                : bi.ciw316g.isChecked() ? "7"
                : bi.ciw316h.isChecked() ? "8"
                : bi.ciw31696.isChecked() ? "96"
                : "0");
        sB2.put("ciw31696x", bi.ciw31696x.getText().toString());

//        ciw317
        sB2.put("ciw317a", bi.ciw317a.isChecked() ? "1" : "0");
        sB2.put("ciw317b", bi.ciw317b.isChecked() ? "2" : "0");
        sB2.put("ciw317c", bi.ciw317c.isChecked() ? "3" : "0");
        sB2.put("ciw317d", bi.ciw317d.isChecked() ? "4" : "0");
        sB2.put("ciw317e", bi.ciw317e.isChecked() ? "5" : "0");
        sB2.put("ciw317f", bi.ciw317f.isChecked() ? "6" : "0");
        sB2.put("ciw317g", bi.ciw317g.isChecked() ? "7" : "0");
        sB2.put("ciw317h", bi.ciw317h.isChecked() ? "8" : "0");
        sB2.put("ciw317i", bi.ciw317i.isChecked() ? "9" : "0");
        sB2.put("ciw317j", bi.ciw317j.isChecked() ? "10" : "0");
        sB2.put("ciw317k", bi.ciw317k.isChecked() ? "11" : "0");
        sB2.put("ciw317l", bi.ciw317l.isChecked() ? "12" : "0");
        sB2.put("ciw317m", bi.ciw317m.isChecked() ? "13" : "0");
        sB2.put("ciw317961", bi.ciw317961.isChecked() ? "961" : "0");
        sB2.put("ciw317962", bi.ciw317962.isChecked() ? "962" : "0");
        sB2.put("ciw317963", bi.ciw317963.isChecked() ? "963" : "0");

        sB2.put("ciw317961x", bi.ciw317961x.getText().toString());
        sB2.put("ciw317962x", bi.ciw317962x.getText().toString());
        sB2.put("ciw317963x", bi.ciw317963x.getText().toString());


//        ciw318
        sB2.put("ciw318", bi.ciw318a.isChecked() ? "1"
                : bi.ciw318b.isChecked() ? "2"
                : bi.ciw318c.isChecked() ? "3"
                : bi.ciw318d.isChecked() ? "4"
                : bi.ciw318e.isChecked() ? "5"
                : "0");

//        ciw319
        sB2.put("ciw319m", bi.ciw319m.getText().toString());
        sB2.put("ciw319d", bi.ciw319d.getText().toString());

//        ciw320
        sB2.put("ciw320", bi.ciw320a.isChecked() ? "1"
                : bi.ciw320b.isChecked() ? "2"
                : "0");


//        ciw321

        sB2.put("ciw321", bi.ciw321a.isChecked() ? "1"
                : bi.ciw321b.isChecked() ? "2"
                : bi.ciw321c.isChecked() ? "3"
                : bi.ciw321d.isChecked() ? "4"
                : bi.ciw321e.isChecked() ? "5"
                : bi.ciw321f.isChecked() ? "6"
                : bi.ciw321g.isChecked() ? "7"
                : bi.ciw321h.isChecked() ? "8"
                : bi.ciw32196.isChecked() ? "96"
                : "0");
        sB2.put("ciw32196x", bi.ciw32196x.getText().toString());

//        ciw322
        sB2.put("ciw322a", bi.ciw322a.isChecked() ? "1" : "0");
        sB2.put("ciw322b", bi.ciw322b.isChecked() ? "2" : "0");
        sB2.put("ciw322c", bi.ciw322c.isChecked() ? "3" : "0");
        sB2.put("ciw322d", bi.ciw322d.isChecked() ? "4" : "0");
        sB2.put("ciw322e", bi.ciw322e.isChecked() ? "5" : "0");
        sB2.put("ciw322f", bi.ciw322f.isChecked() ? "6" : "0");
        sB2.put("ciw322g", bi.ciw322g.isChecked() ? "7" : "0");
        sB2.put("ciw322h", bi.ciw322h.isChecked() ? "8" : "0");
        sB2.put("ciw322i", bi.ciw322i.isChecked() ? "9" : "0");
        sB2.put("ciw322j", bi.ciw322j.isChecked() ? "10" : "0");
        sB2.put("ciw322k", bi.ciw322k.isChecked() ? "11" : "0");
        sB2.put("ciw322l", bi.ciw322l.isChecked() ? "12" : "0");
        sB2.put("ciw322m", bi.ciw322m.isChecked() ? "13" : "0");
        sB2.put("ciw322961", bi.ciw322961.isChecked() ? "961" : "0");
        sB2.put("ciw322962", bi.ciw322962.isChecked() ? "962" : "0");
        sB2.put("ciw322963", bi.ciw322963.isChecked() ? "963" : "0");

        sB2.put("ciw322961x", bi.ciw322961x.getText().toString());
        sB2.put("ciw322962x", bi.ciw322962x.getText().toString());
        sB2.put("ciw322963x", bi.ciw322963x.getText().toString());


//        ciw323
        sB2.put("ciw323", bi.ciw323a.isChecked() ? "1"
                : bi.ciw323b.isChecked() ? "2"
                : bi.ciw323c.isChecked() ? "3"
                : bi.ciw323d.isChecked() ? "4"
                : bi.ciw323e.isChecked() ? "5"
                : "0");

//        ciw324
        sB2.put("ciw324m", bi.ciw324m.getText().toString());
        sB2.put("ciw324d", bi.ciw324d.getText().toString());

//        ciw325
        sB2.put("ciw325", bi.ciw325a.isChecked() ? "1"
                : bi.ciw325b.isChecked() ? "2"
                : "0");
//        ciw326
        sB2.put("ciw326", bi.ciw326a.isChecked() ? "1"
                : bi.ciw326b.isChecked() ? "2"
                : bi.ciw32698.isChecked() ? "98"
                : "0");

        MainApp.mc.setsB2(String.valueOf(sB2));


        //

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        MainApp.mc.setSb2flag("1");

        int updcount = db.updateSB2();

        if (updcount == 1) {

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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ValidateForm();
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

                        SectionB2Activity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                ValidateForm();
                            }
                            //}
                        });

                    }
                },
                DELAY
        );


    }
}
