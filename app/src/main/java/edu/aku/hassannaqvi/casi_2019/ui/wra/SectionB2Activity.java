package edu.aku.hassannaqvi.casi_2019.ui.wra;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
        bi.nw301.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nw301a.isChecked()) {
                    //bi.fldGrpnw302.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpnw302, true);
                    ClearClass.ClearAllFields(bi.fldGrpnw302check, true);
                    ClearClass.ClearAllFields(bi.fldGrpnw306check, true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpnw302, false);
                    ClearClass.ClearAllFields(bi.fldGrpnw302check, false);
                    ClearClass.ClearAllFields(bi.fldGrpnw306check, false);

                }
            }
        });

        bi.nw303.setOnCheckedChangeListener(this);
        bi.nw304w.addTextChangedListener(this);
        bi.nw304m.addTextChangedListener(this);
        bi.nw305.addTextChangedListener(this);
        bi.nw307.setOnCheckedChangeListener(this);


        bi.nw306i.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bi.nw306i.isChecked()) {

                    bi.nw306a.setChecked(false);
                    bi.nw306b.setChecked(false);
                    bi.nw306c.setChecked(false);
                    bi.nw306d.setChecked(false);
                    bi.nw306e.setChecked(false);
                    bi.nw306f.setChecked(false);
                    bi.nw306g.setChecked(false);
                    bi.nw306h.setChecked(false);
                    bi.nw30696.setChecked(false);

                    bi.nw306a.setEnabled(false);
                    bi.nw306b.setEnabled(false);
                    bi.nw306c.setEnabled(false);
                    bi.nw306d.setEnabled(false);
                    bi.nw306e.setEnabled(false);
                    bi.nw306f.setEnabled(false);
                    bi.nw306g.setEnabled(false);
                    bi.nw306h.setEnabled(false);
                    bi.nw30696.setEnabled(false);
                } else {
                    bi.nw306a.setEnabled(true);
                    bi.nw306b.setEnabled(true);
                    bi.nw306c.setEnabled(true);
                    bi.nw306d.setEnabled(true);
                    bi.nw306e.setEnabled(true);
                    bi.nw306f.setEnabled(true);
                    bi.nw306g.setEnabled(true);
                    bi.nw306h.setEnabled(true);
                    bi.nw30696.setEnabled(true);
                }
            }
        });
        bi.nw308.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nw308a.isChecked()) {

                    //bi.fldGrpnw309.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpnw309, true);

                } else {

                    ClearClass.ClearAllFields(bi.fldGrpnw309, false);

                }
            }
        });

        bi.nw309.addTextChangedListener(this);
        bi.nw311.setOnCheckedChangeListener(this);
        bi.nw313.setOnCheckedChangeListener(this);
        bi.nw314m.addTextChangedListener(this);
        bi.nw314d.addTextChangedListener(this);
        bi.nw316.setOnCheckedChangeListener(this);
        bi.nw318.setOnCheckedChangeListener(this);
        bi.nw319m.addTextChangedListener(this);
        bi.nw319d.addTextChangedListener(this);
        bi.nw321.setOnCheckedChangeListener(this);
        bi.nw323.setOnCheckedChangeListener(this);
        bi.nw324m.addTextChangedListener(this);
        bi.nw324d.addTextChangedListener(this);
        bi.nw325.setOnCheckedChangeListener(this);
        bi.nw326.setOnCheckedChangeListener(this);

        bi.nw310.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nw310a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpnb210, true);
                    ClearClass.ClearAllFields(bi.fldGrpnw312, true);
                    //bi.fldGrpnb210.setVisibility(View.VISIBLE);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpnb210, false);
                    ClearClass.ClearAllFields(bi.fldGrpnw312, false);

                }
            }
        });

        bi.nw315.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nw315a.isChecked()) {
                    //bi.fldGrpnw318.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpnw318, true);
                    ClearClass.ClearAllFields(bi.fldGrpnw317, true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpnw318, false);
                    ClearClass.ClearAllFields(bi.fldGrpnw317, false);

                }
            }
        });


        bi.nw320.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nw320a.isChecked()) {
                    //bi.fldGrpnw323.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpnw323, true);
                    ClearClass.ClearAllFields(bi.fldGrpnw322, true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpnw323, false);
                    ClearClass.ClearAllFields(bi.fldGrpnw322, false);

                }
            }
        });


//        Setting name of women
        bi.nw301Txt.setText(getString(R.string.nw301a) + " " + SectionB1Activity.wraName + " " + getString(R.string.nw301b));
    }

    public void AutoCompleteFields() {

//        BackPressed event

        MWRAContract mwraContract = db.getsB2();
        if (!mwraContract.getsB2().equals("")) {

            JSONB2ModelClass jsonB2 = JSONUtilClass.getModelFromJSON(mwraContract.getsB2(), JSONB2ModelClass.class);

            if (!jsonB2.getnw301().equals("0")) {
                bi.nw301.check(
                        jsonB2.getnw301().equals("1") ? bi.nw301a.getId() :
                                bi.nw301b.getId()
                );
            }
            if (!jsonB2.getnw302a().equals("0")) {
                bi.nw302a.setChecked(true);
            }
            if (!jsonB2.getnw302b().equals("0")) {
                bi.nw302b.setChecked(true);
            }
            if (!jsonB2.getnw302c().equals("0")) {
                bi.nw302c.setChecked(true);
            }
            if (!jsonB2.getnw302d().equals("0")) {
                bi.nw302d.setChecked(true);
            }
            if (!jsonB2.getnw302e().equals("0")) {
                bi.nw302e.setChecked(true);
            }
            if (!jsonB2.getnw302f().equals("0")) {
                bi.nw302f.setChecked(true);
            }
            if (!jsonB2.getnw302g().equals("0")) {
                bi.nw302g.setChecked(true);
            }
            if (!jsonB2.getnw302h().equals("0")) {
                bi.nw302h.setChecked(true);
            }
            if (!jsonB2.getnw30296().equals("0")) {
                bi.nw30296.setChecked(true);
                bi.nw30296x.setText(jsonB2.getnw30296x());
            }

            if (!jsonB2.getnw303().equals("0")) {
                bi.nw303.check(
                        jsonB2.getnw303().equals("1") ? bi.nw303a.getId() :
                                jsonB2.getnw303().equals("2") ? bi.nw303b.getId() :
                                        jsonB2.getnw303().equals("3") ? bi.nw303c.getId() :
                                                jsonB2.getnw303().equals("4") ? bi.nw303d.getId() :
                                                        jsonB2.getnw303().equals("5") ? bi.nw303e.getId() :
                                                                jsonB2.getnw303().equals("6") ? bi.nw303f.getId() :
                                                                        jsonB2.getnw303().equals("7") ? bi.nw303g.getId() :
                                                                                jsonB2.getnw303().equals("8") ? bi.nw303h.getId() :
                                                                                        jsonB2.getnw303().equals("9") ? bi.nw303i.getId() :
                                                                                                jsonB2.getnw303().equals("10") ? bi.nw303j.getId() :
                                                                                                        bi.nw30396.getId()
                );

                bi.nw303961x.setText(jsonB2.getnw303961x());
                bi.nw303962x.setText(jsonB2.getnw303962x());
                bi.nw303963x.setText(jsonB2.getnw303963x());
            }

            bi.nw304w.setText(jsonB2.getnw304());
            if (!jsonB2.getnw30498().equals("0")) {
                bi.nw30498.setChecked(true);
            }

            bi.nw305.setText(jsonB2.getnw304());
            if (!jsonB2.getnw305().equals("0")) {
                bi.nw30598.setChecked(true);
            }

            if (!jsonB2.getnw306a().equals("0")) {
                bi.nw306a.setChecked(true);
            }
            if (!jsonB2.getnw306b().equals("0")) {
                bi.nw306b.setChecked(true);
            }
            if (!jsonB2.getnw306c().equals("0")) {
                bi.nw306c.setChecked(true);
            }
            if (!jsonB2.getnw306d().equals("0")) {
                bi.nw306d.setChecked(true);
            }
            if (!jsonB2.getnw306e().equals("0")) {
                bi.nw306e.setChecked(true);
            }
            if (!jsonB2.getnw306f().equals("0")) {
                bi.nw306f.setChecked(true);
            }
            if (!jsonB2.getnw306g().equals("0")) {
                bi.nw306g.setChecked(true);
            }
            if (!jsonB2.getnw306h().equals("0")) {
                bi.nw306h.setChecked(true);
            }
            if (!jsonB2.getnw306i().equals("0")) {
                bi.nw306i.setChecked(true);
            }
            if (!jsonB2.getnw30696().equals("0")) {
                bi.nw30696.setChecked(true);
                bi.nw30696x.setText(jsonB2.getnw30696x());
            }

            if (!jsonB2.getnw307().equals("0")) {
                bi.nw307.check(
                        jsonB2.getnw307().equals("1") ? bi.nw307a.getId() :
                                jsonB2.getnw307().equals("2") ? bi.nw307b.getId() :
                                        jsonB2.getnw307().equals("3") ? bi.nw307c.getId() :
                                                bi.nw30798.getId());
            }

            if (!jsonB2.getnw308().equals("0")) {
                bi.nw308.check(
                        jsonB2.getnw308().equals("1") ? bi.nw308a.getId() :
                                jsonB2.getnw308().equals("2") ? bi.nw308b.getId() :
                                        bi.nw30898.getId());
            }

            if (jsonB2.getnw309().equals("98")) {
                bi.nw30998.setChecked(true);
            } else {
                bi.nw309.setText(jsonB2.getnw309());
            }

            if (!jsonB2.getnw310().equals("0")) {
                bi.nw310.check(
                        jsonB2.getnw310().equals("1") ? bi.nw310a.getId() :
                                bi.nw310b.getId());
            }

            if (!jsonB2.getnw311().equals("0")) {
                bi.nw311.check(
                        jsonB2.getnw311().equals("1") ? bi.nw311a.getId() :
                                jsonB2.getnw311().equals("2") ? bi.nw311b.getId() :
                                        jsonB2.getnw311().equals("3") ? bi.nw311c.getId() :
                                                jsonB2.getnw311().equals("4") ? bi.nw311d.getId() :
                                                        jsonB2.getnw311().equals("5") ? bi.nw311e.getId() :
                                                                jsonB2.getnw311().equals("6") ? bi.nw311f.getId() :
                                                                        jsonB2.getnw311().equals("7") ? bi.nw311g.getId() :
                                                                                jsonB2.getnw311().equals("8") ? bi.nw311h.getId() :
                                                                                        bi.nw31196.getId()
                );
                bi.nw31196x.setText(jsonB2.getnw31196x());
            }

            if (!jsonB2.getnw312a().equals("0")) {
                bi.nw312a.setChecked(true);
            }
            if (!jsonB2.getnw312b().equals("0")) {
                bi.nw312b.setChecked(true);
            }
            if (!jsonB2.getnw312c().equals("0")) {
                bi.nw312c.setChecked(true);
            }
            if (!jsonB2.getnw312d().equals("0")) {
                bi.nw312d.setChecked(true);
            }
            if (!jsonB2.getnw312e().equals("0")) {
                bi.nw312e.setChecked(true);
            }
            if (!jsonB2.getnw312f().equals("0")) {
                bi.nw312f.setChecked(true);
            }
            if (!jsonB2.getnw312g().equals("0")) {
                bi.nw312g.setChecked(true);
            }
            if (!jsonB2.getnw312h().equals("0")) {
                bi.nw312h.setChecked(true);
            }
            if (!jsonB2.getnw312i().equals("0")) {
                bi.nw312i.setChecked(true);
            }
            if (!jsonB2.getnw312j().equals("0")) {
                bi.nw312j.setChecked(true);
            }
            if (!jsonB2.getnw312k().equals("0")) {
                bi.nw312k.setChecked(true);
            }
            if (!jsonB2.getnw312l().equals("0")) {
                bi.nw312l.setChecked(true);
            }
            if (!jsonB2.getnw312m().equals("0")) {
                bi.nw312m.setChecked(true);
            }
            if (!jsonB2.getnw312961().equals("0")) {
                bi.nw312961.setChecked(true);
            }
            if (!jsonB2.getnw312962().equals("0")) {
                bi.nw312962.setChecked(true);
            }
            if (!jsonB2.getnw312963().equals("0")) {
                bi.nw312963.setChecked(true);
            }
            bi.nw312961x.setText(jsonB2.getnw312961x());
            bi.nw312962x.setText(jsonB2.getnw312962x());
            bi.nw312963x.setText(jsonB2.getnw312963x());

            if (!jsonB2.getnw313().equals("0")) {
                bi.nw313.check(
                        jsonB2.getnw313().equals("1") ? bi.nw313a.getId() :
                                jsonB2.getnw313().equals("2") ? bi.nw313b.getId() :
                                        jsonB2.getnw313().equals("3") ? bi.nw313c.getId() :
                                                jsonB2.getnw313().equals("4") ? bi.nw313d.getId() :
                                                        bi.nw313e.getId()
                );
            }

            bi.nw314m.setText(jsonB2.getnw314m());
            bi.nw314d.setText(jsonB2.getnw314d());

            if (!jsonB2.getnw315().equals("0")) {
                bi.nw315.check(
                        jsonB2.getnw315().equals("1") ? bi.nw315a.getId() :
                                bi.nw315b.getId());
            }

            if (!jsonB2.getnw316().equals("0")) {
                bi.nw316.check(
                        jsonB2.getnw316().equals("1") ? bi.nw316a.getId() :
                                jsonB2.getnw316().equals("2") ? bi.nw316b.getId() :
                                        jsonB2.getnw316().equals("3") ? bi.nw316c.getId() :
                                                jsonB2.getnw316().equals("4") ? bi.nw316d.getId() :
                                                        jsonB2.getnw316().equals("5") ? bi.nw316e.getId() :
                                                                jsonB2.getnw316().equals("6") ? bi.nw316f.getId() :
                                                                        jsonB2.getnw316().equals("7") ? bi.nw316g.getId() :
                                                                                jsonB2.getnw316().equals("8") ? bi.nw316h.getId() :
                                                                                        bi.nw31696.getId()
                );
                bi.nw31696x.setText(jsonB2.getnw31696x());
            }

            if (!jsonB2.getnw317a().equals("0")) {
                bi.nw317a.setChecked(true);
            }
            if (!jsonB2.getnw317b().equals("0")) {
                bi.nw317b.setChecked(true);
            }
            if (!jsonB2.getnw317c().equals("0")) {
                bi.nw317c.setChecked(true);
            }
            if (!jsonB2.getnw317d().equals("0")) {
                bi.nw317d.setChecked(true);
            }
            if (!jsonB2.getnw317e().equals("0")) {
                bi.nw317e.setChecked(true);
            }
            if (!jsonB2.getnw317f().equals("0")) {
                bi.nw317f.setChecked(true);
            }
            if (!jsonB2.getnw317g().equals("0")) {
                bi.nw317g.setChecked(true);
            }
            if (!jsonB2.getnw317h().equals("0")) {
                bi.nw317h.setChecked(true);
            }
            if (!jsonB2.getnw317i().equals("0")) {
                bi.nw317i.setChecked(true);
            }
            if (!jsonB2.getnw317j().equals("0")) {
                bi.nw317j.setChecked(true);
            }
            if (!jsonB2.getnw317k().equals("0")) {
                bi.nw317k.setChecked(true);
            }
            if (!jsonB2.getnw317l().equals("0")) {
                bi.nw317l.setChecked(true);
            }
            if (!jsonB2.getnw317m().equals("0")) {
                bi.nw317m.setChecked(true);
            }
            if (!jsonB2.getnw317961().equals("0")) {
                bi.nw317961.setChecked(true);
            }
            if (!jsonB2.getnw317962().equals("0")) {
                bi.nw317962.setChecked(true);
            }
            if (!jsonB2.getnw317963().equals("0")) {
                bi.nw317963.setChecked(true);
            }
            bi.nw317961x.setText(jsonB2.getnw317961x());
            bi.nw317962x.setText(jsonB2.getnw317962x());
            bi.nw317963x.setText(jsonB2.getnw317963x());

            if (!jsonB2.getnw318().equals("0")) {
                bi.nw318.check(
                        jsonB2.getnw318().equals("1") ? bi.nw318a.getId() :
                                jsonB2.getnw318().equals("2") ? bi.nw318b.getId() :
                                        jsonB2.getnw318().equals("3") ? bi.nw318c.getId() :
                                                jsonB2.getnw318().equals("4") ? bi.nw318d.getId() :
                                                        bi.nw318e.getId()
                );
            }

            bi.nw319m.setText(jsonB2.getnw319m());
            bi.nw319d.setText(jsonB2.getnw319d());

            if (!jsonB2.getnw320().equals("0")) {
                bi.nw320.check(
                        jsonB2.getnw320().equals("1") ? bi.nw320a.getId() :
                                bi.nw320b.getId());
            }


            if (!jsonB2.getnw321().equals("0")) {
                bi.nw321.check(
                        jsonB2.getnw321().equals("1") ? bi.nw321a.getId() :
                                jsonB2.getnw321().equals("2") ? bi.nw321b.getId() :
                                        jsonB2.getnw321().equals("3") ? bi.nw321c.getId() :
                                                jsonB2.getnw321().equals("4") ? bi.nw321d.getId() :
                                                        jsonB2.getnw321().equals("5") ? bi.nw321e.getId() :
                                                                jsonB2.getnw321().equals("6") ? bi.nw321f.getId() :
                                                                        jsonB2.getnw321().equals("7") ? bi.nw321g.getId() :
                                                                                jsonB2.getnw321().equals("8") ? bi.nw321h.getId() :
                                                                                        bi.nw32196.getId()
                );
                bi.nw32196x.setText(jsonB2.getnw32196x());
            }

            if (!jsonB2.getnw322a().equals("0")) {
                bi.nw322a.setChecked(true);
            }
            if (!jsonB2.getnw322b().equals("0")) {
                bi.nw322b.setChecked(true);
            }
            if (!jsonB2.getnw322c().equals("0")) {
                bi.nw322c.setChecked(true);
            }
            if (!jsonB2.getnw322d().equals("0")) {
                bi.nw322d.setChecked(true);
            }
            if (!jsonB2.getnw322e().equals("0")) {
                bi.nw322e.setChecked(true);
            }
            if (!jsonB2.getnw322f().equals("0")) {
                bi.nw322f.setChecked(true);
            }
            if (!jsonB2.getnw322g().equals("0")) {
                bi.nw322g.setChecked(true);
            }
            if (!jsonB2.getnw322h().equals("0")) {
                bi.nw322h.setChecked(true);
            }
            if (!jsonB2.getnw322i().equals("0")) {
                bi.nw322i.setChecked(true);
            }
            if (!jsonB2.getnw322j().equals("0")) {
                bi.nw322j.setChecked(true);
            }
            if (!jsonB2.getnw322k().equals("0")) {
                bi.nw322k.setChecked(true);
            }
            if (!jsonB2.getnw322l().equals("0")) {
                bi.nw322l.setChecked(true);
            }
            if (!jsonB2.getnw322m().equals("0")) {
                bi.nw322m.setChecked(true);
            }
            if (!jsonB2.getnw322961().equals("0")) {
                bi.nw322961.setChecked(true);
            }
            if (!jsonB2.getnw322962().equals("0")) {
                bi.nw322962.setChecked(true);
            }
            if (!jsonB2.getnw322963().equals("0")) {
                bi.nw322963.setChecked(true);
            }
            bi.nw322961x.setText(jsonB2.getnw322961x());
            bi.nw322962x.setText(jsonB2.getnw322962x());
            bi.nw322963x.setText(jsonB2.getnw322963x());

            if (!jsonB2.getnw323().equals("0")) {
                bi.nw323.check(
                        jsonB2.getnw323().equals("1") ? bi.nw323a.getId() :
                                jsonB2.getnw323().equals("2") ? bi.nw323b.getId() :
                                        jsonB2.getnw323().equals("3") ? bi.nw323c.getId() :
                                                jsonB2.getnw323().equals("4") ? bi.nw323d.getId() :
                                                        bi.nw323e.getId()
                );
            }

            bi.nw324m.setText(jsonB2.getnw324m());
            bi.nw324d.setText(jsonB2.getnw324d());

            if (!jsonB2.getnw325().equals("0")) {
                bi.nw325.check(
                        jsonB2.getnw325().equals("1") ? bi.nw325a.getId() :
                                bi.nw325b.getId());
            }
            if (!jsonB2.getnw326().equals("0")) {
                bi.nw326.check(
                        jsonB2.getnw326().equals("1") ? bi.nw326a.getId() :
                                jsonB2.getnw326().equals("2") ? bi.nw326b.getId() :
                                        bi.nw32698.getId());
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


        if (!ValidatorClass.EmptyRadioButton(this, bi.nw301, bi.nw301a, getString(R.string.nw301b) + " " + SectionB1Activity.wraName + " " + getString(R.string.nw301a))) {
            return false;
        }

        if (bi.nw301a.isChecked()) {
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw302check, bi.nw302a, getString(R.string.nw302))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw302check, bi.nw30296, bi.nw30296x, getString(R.string.nw302) + " - " + getString(R.string.other))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.nw303, bi.nw303a, getString(R.string.nw303))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.nw303, bi.nw303f, bi.nw303961x, getString(R.string.nw303) + " - " + getString(R.string.nw303f))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.nw303, bi.nw303j, bi.nw303962x, getString(R.string.nw303) + " - " + getString(R.string.nw303j))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.nw303, bi.nw30396, bi.nw303963x, getString(R.string.nw303) + " - " + getString(R.string.other))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.nw304, bi.nw304a, getString(R.string.nw304))) {
                return false;
            }
            if (bi.nw304a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.nw304w, getString(R.string.nw304) + " - " + getString(R.string.week))) {
                    return false;
                }

                if (bi.nw304m.getText().toString().equals("0")) {
                    if (!ValidatorClass.RangeTextBox(this, bi.nw304w, 2, 44, getString(R.string.nw304), " weeks")) {
                        return false;
                    }
                } else {
                    if (!ValidatorClass.RangeTextBox(this, bi.nw304w, 0, 44, getString(R.string.nw304), " weeks")) {
                        return false;
                    }
                }
            }

            if (bi.nw304b.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, bi.nw304m, getString(R.string.nw304) + " - " + getString(R.string.months))) {
                    return false;
                }

                if (bi.nw304w.getText().toString().equals("0")) {
                    if (!ValidatorClass.RangeTextBox(this, bi.nw304m, 1, 9, getString(R.string.nw203), " months")) {
                        return false;
                    }
                } else {
                    if (!ValidatorClass.RangeTextBox(this, bi.nw304m, 0, 9, getString(R.string.nw203), " months")) {
                        return false;
                    }
                }

                if (bi.nw304w.getText().toString().equals("0") && bi.nw304m.getText().toString().equals("0")) {
                    Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.nw304), Toast.LENGTH_LONG).show();
                    bi.nw304w.setError("All can not be zero");
                    bi.nw304w.setError("All can not be zero");
                    Log.i(SectionB2Activity.class.getSimpleName(), "nw304" + ": This data is Required!");
                    return false;
                } else {
                    bi.nw304w.setError(null);
                    bi.nw304m.setError(null);

                }
            }

            if (!bi.nw30598.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.nw305, getString(R.string.nw305) + " - " + getString(R.string.times))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, bi.nw305, 1, 15, getString(R.string.nw305), " times")) {
                    return false;
                }
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw306check, bi.nw306a, getString(R.string.nw306))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw306check, bi.nw30696, bi.nw30696x, getString(R.string.nw306) + " - " + getString(R.string.other))) {
                return false;
            }

        }
        if (!ValidatorClass.EmptyRadioButton(this, bi.nw307, bi.nw307a, getString(R.string.nw307))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.nw308, bi.nw308a, getString(R.string.nw308))) {
            return false;
        }

        if (bi.nw308a.isChecked()) {
            if (!bi.nw30998.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.nw309, getString(R.string.nw309) + " - " + getString(R.string.times))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.nw309, 1, 5, getString(R.string.nw203), " times")) {
                    return false;
                }
            }
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.nw310, bi.nw310a, getString(R.string.nw310))) {
            return false;
        }

        if (bi.nw310a.isChecked()) {


            if (!ValidatorClass.EmptyRadioButton(this, bi.nw311, bi.nw311a, getString(R.string.nw311))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.nw311, bi.nw31196, bi.nw31196x, getString(R.string.nw311))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw312, bi.nw312a, getString(R.string.nw312))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw312, bi.nw312961, bi.nw312961x, getString(R.string.nw312))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw312, bi.nw312962, bi.nw312962x, getString(R.string.nw312))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw312, bi.nw312963, bi.nw312963x, getString(R.string.nw312))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.nw313, bi.nw313a, getString(R.string.nw313))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.nw314m, getString(R.string.nw314) + " - " + getString(R.string.months))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.nw314d, getString(R.string.nw314) + " - " + getString(R.string.day))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.nw314m, 0, 11, getString(R.string.nw314), " months")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.nw314d, 0, 29, getString(R.string.nw314), " days")) {
                return false;
            }

            if (bi.nw314m.getText().toString().equals("0") && bi.nw314d.getText().toString().equals("0")) {
                Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.nw314), Toast.LENGTH_LONG).show();
                bi.nw314m.setError("All can not be zero");
                bi.nw314d.setError("All can not be zero");
                Log.i(SectionB2Activity.class.getSimpleName(), "nw314" + ": This data is Required!");
                return false;
            } else {
                bi.nw314m.setError(null);
                bi.nw314d.setError(null);

            }

        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.nw315, bi.nw315a, getString(R.string.nw315))) {
            return false;
        }

        if (bi.nw315a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.nw316, bi.nw316a, getString(R.string.nw316))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.nw316, bi.nw31696, bi.nw31696x, getString(R.string.nw316))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw317, bi.nw317a, getString(R.string.nw317))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw317, bi.nw317961, bi.nw317961x, getString(R.string.nw317))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw317, bi.nw317962, bi.nw317962x, getString(R.string.nw317))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw317, bi.nw317963, bi.nw317963x, getString(R.string.nw317))) {
                return false;
            }


            if (!ValidatorClass.EmptyRadioButton(this, bi.nw318, bi.nw318a, getString(R.string.nw318))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.nw319m, getString(R.string.nw319) + " - " + getString(R.string.month))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.nw319d, getString(R.string.nw319) + " - " + getString(R.string.day))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.nw319m, 0, 11, getString(R.string.nw319), " months")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.nw319d, 0, 29, getString(R.string.nw319), " days")) {
                return false;
            }

            if (bi.nw319m.getText().toString().equals("0") && bi.nw319d.getText().toString().equals(" 0")) {
                Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.nw319), Toast.LENGTH_LONG).show();
                bi.nw319m.setError("All can not be zero");
                bi.nw319d.setError("All can not be zero");
                Log.i(SectionB2Activity.class.getSimpleName(), "nw319" + ": This data is Required!");
                return false;
            } else {
                bi.nw319m.setError(null);
                bi.nw319d.setError(null);

            }

        }


        if (!ValidatorClass.EmptyRadioButton(this, bi.nw320, bi.nw320a, getString(R.string.nw320))) {
            return false;
        }

        if (bi.nw320a.isChecked()) {


            if (!ValidatorClass.EmptyRadioButton(this, bi.nw321, bi.nw321a, getString(R.string.nw321))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, bi.nw321, bi.nw32196, bi.nw32196x, getString(R.string.nw321))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw322, bi.nw322a, getString(R.string.nw322))) {
                return false;
            }
            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw322, bi.nw322961, bi.nw322961x, getString(R.string.nw322))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw322, bi.nw322962, bi.nw322962x, getString(R.string.nw322))) {
                return false;
            }

            if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw322, bi.nw322963, bi.nw322963x, getString(R.string.nw322))) {
                return false;
            }


            if (!ValidatorClass.EmptyRadioButton(this, bi.nw323, bi.nw323a, getString(R.string.nw323))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.nw324m, getString(R.string.nw324) + " - " + getString(R.string.month))) {
                return false;
            }

            if (!ValidatorClass.EmptyTextBox(this, bi.nw324d, getString(R.string.nw324) + " - " + getString(R.string.day))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.nw324m, 0, 11, getString(R.string.nw324), " months")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, bi.nw324d, 0, 29, getString(R.string.nw324), " days")) {
                return false;
            }

            if (bi.nw324m.getText().toString().equals("0") && bi.nw324d.getText().toString().equals("0")) {
                Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.nw324), Toast.LENGTH_LONG).show();
                bi.nw324m.setError("All can not be zero");

                bi.nw324d.setError("All can not be zero");
                Log.i(SectionB2Activity.class.getSimpleName(), "nw324" + ": This data is Required!");
                return false;
            } else {
                bi.nw324m.setError(null);
                bi.nw324d.setError(null);

            }

        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.nw325, bi.nw325a, getString(R.string.nw325))) {
            return false;
        }

        return ValidatorClass.EmptyRadioButton(this, bi.nw326, bi.nw326a, getString(R.string.nw326));
    }

    private void SaveDraft() throws JSONException {

//       nw301
        JSONObject sB2 = new JSONObject();

        if (backPressed) {
            sB2.put("updatedate_ciw3", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        sB2.put("nw301", bi.nw301a.isChecked() ? "1"
                : bi.nw301b.isChecked() ? "2"
                : "0");

//       nw302
        sB2.put("nw302a", bi.nw302a.isChecked() ? "1" : "0");
        sB2.put("nw302b", bi.nw302b.isChecked() ? "2" : "0");
        sB2.put("nw302c", bi.nw302c.isChecked() ? "3" : "0");
        sB2.put("nw302d", bi.nw302d.isChecked() ? "4" : "0");
        sB2.put("nw302e", bi.nw302e.isChecked() ? "5" : "0");
        sB2.put("nw302f", bi.nw302f.isChecked() ? "6" : "0");
        sB2.put("nw302g", bi.nw302g.isChecked() ? "7" : "0");
        sB2.put("nw302h", bi.nw302h.isChecked() ? "8" : "0");
        sB2.put("nw30296", bi.nw30296.isChecked() ? "96" : "0");

        sB2.put("nw30296x", bi.nw30296x.getText().toString());

//      nw303
        sB2.put("nw303", bi.nw303a.isChecked() ? "1"
                : bi.nw303b.isChecked() ? "2"
                : bi.nw303c.isChecked() ? "3"
                : bi.nw303d.isChecked() ? "4"
                : bi.nw303e.isChecked() ? "5"
                : bi.nw303f.isChecked() ? "961"
                : bi.nw303g.isChecked() ? "7"
                : bi.nw303h.isChecked() ? "8"
                : bi.nw303i.isChecked() ? "9"
                : bi.nw303j.isChecked() ? "962"
                : bi.nw30396.isChecked() ? "963"
                : "0");
        sB2.put("nw303961x", bi.nw303961x.getText().toString());
        sB2.put("nw303962x", bi.nw303962x.getText().toString());
        sB2.put("nw303963x", bi.nw303963x.getText().toString());

//        nw304
        sB2.put("nw304", bi.nw304a.isChecked() ? "1" : bi.nw304b.isChecked() ? "2" : bi.nw30498.isChecked() ? "98" : "0");
        sB2.put("nw304w", bi.nw304w.getText().toString());
        sB2.put("nw304m", bi.nw304m.getText().toString());

//        nw204
        sB2.put("nw305", bi.nw305.getText().toString());
        sB2.put("nw30598", bi.nw30598.isChecked() ? "98" : "0");

//      nw306
        sB2.put("nw306a", bi.nw306a.isChecked() ? "1" : "0");
        sB2.put("nw306b", bi.nw306b.isChecked() ? "2" : "0");
        sB2.put("nw306c", bi.nw306c.isChecked() ? "3" : "0");
        sB2.put("nw306d", bi.nw306d.isChecked() ? "4" : "0");
        sB2.put("nw306e", bi.nw306e.isChecked() ? "5" : "0");
        sB2.put("nw306f", bi.nw306f.isChecked() ? "6" : "0");
        sB2.put("nw306g", bi.nw306g.isChecked() ? "7" : "0");
        sB2.put("nw306h", bi.nw306h.isChecked() ? "8" : "0");
        sB2.put("nw306i", bi.nw306i.isChecked() ? "9" : "0");
        sB2.put("nw30696", bi.nw30696.isChecked() ? "96" : "0");
        sB2.put("nw30696x", bi.nw30696x.getText().toString());

//        nw307
        sB2.put("nw307", bi.nw307a.isChecked() ? "1"
                : bi.nw307b.isChecked() ? "2"
                : bi.nw307c.isChecked() ? "3"
                : bi.nw30798.isChecked() ? "98"
                : "0");
//        nw308
        sB2.put("nw308", bi.nw308a.isChecked() ? "1"
                : bi.nw308b.isChecked() ? "2"
                : bi.nw30898.isChecked() ? "98"
                : "0");
//        nw309

        sB2.put("nw309", bi.nw30998.isChecked() ? "98" : bi.nw309.getText().toString());
//        sB2.put("nw30998", bi.nw30998.isChecked() ? "98" : "0");

//        nw310
        sB2.put("nw310", bi.nw310a.isChecked() ? "1"
                : bi.nw310b.isChecked() ? "2"
                : "0");

//          nw311
        sB2.put("nw311", bi.nw311a.isChecked() ? "1"
                : bi.nw311b.isChecked() ? "2"
                : bi.nw311c.isChecked() ? "3"
                : bi.nw311d.isChecked() ? "4"
                : bi.nw311e.isChecked() ? "5"
                : bi.nw311f.isChecked() ? "6"
                : bi.nw311g.isChecked() ? "7"
                : bi.nw311h.isChecked() ? "8"
                : bi.nw31196.isChecked() ? "96"
                : "0");
        sB2.put("nw31196x", bi.nw31196x.getText().toString());
//        nw312
        sB2.put("nw312a", bi.nw312a.isChecked() ? "1" : "0");
        sB2.put("nw312b", bi.nw312b.isChecked() ? "2" : "0");
        sB2.put("nw312c", bi.nw312c.isChecked() ? "3" : "0");
        sB2.put("nw312d", bi.nw312d.isChecked() ? "4" : "0");
        sB2.put("nw312e", bi.nw312e.isChecked() ? "5" : "0");
        sB2.put("nw312f", bi.nw312f.isChecked() ? "6" : "0");
        sB2.put("nw312g", bi.nw312g.isChecked() ? "7" : "0");
        sB2.put("nw312h", bi.nw312h.isChecked() ? "8" : "0");
        sB2.put("nw312i", bi.nw312i.isChecked() ? "9" : "0");
        sB2.put("nw312j", bi.nw312j.isChecked() ? "10" : "0");
        sB2.put("nw312k", bi.nw312k.isChecked() ? "11" : "0");
        sB2.put("nw312l", bi.nw312l.isChecked() ? "12" : "0");
        sB2.put("nw312m", bi.nw312m.isChecked() ? "13" : "0");
        sB2.put("nw312961", bi.nw312961.isChecked() ? "961" : "0");
        sB2.put("nw312962", bi.nw312962.isChecked() ? "962" : "0");
        sB2.put("nw312963", bi.nw312963.isChecked() ? "963" : "0");

        sB2.put("nw312961x", bi.nw312961x.getText().toString());
        sB2.put("nw312962x", bi.nw312962x.getText().toString());
        sB2.put("nw312963x", bi.nw312963x.getText().toString());

//         nw313
        sB2.put("nw313", bi.nw313a.isChecked() ? "1"
                : bi.nw313b.isChecked() ? "2"
                : bi.nw313c.isChecked() ? "3"
                : bi.nw313d.isChecked() ? "4"
                : bi.nw313e.isChecked() ? "5"
                : "0");

//        nw314
        sB2.put("nw314m", bi.nw314m.getText().toString());
        sB2.put("nw314d", bi.nw314d.getText().toString());

        //        nw315
        sB2.put("nw315", bi.nw315a.isChecked() ? "1"
                : bi.nw315b.isChecked() ? "2"
                : "0");

//        nw316

        sB2.put("nw316", bi.nw316a.isChecked() ? "1"
                : bi.nw316b.isChecked() ? "2"
                : bi.nw316c.isChecked() ? "3"
                : bi.nw316d.isChecked() ? "4"
                : bi.nw316e.isChecked() ? "5"
                : bi.nw316f.isChecked() ? "6"
                : bi.nw316g.isChecked() ? "7"
                : bi.nw316h.isChecked() ? "8"
                : bi.nw31696.isChecked() ? "96"
                : "0");
        sB2.put("nw31696x", bi.nw31696x.getText().toString());

//        nw317
        sB2.put("nw317a", bi.nw317a.isChecked() ? "1" : "0");
        sB2.put("nw317b", bi.nw317b.isChecked() ? "2" : "0");
        sB2.put("nw317c", bi.nw317c.isChecked() ? "3" : "0");
        sB2.put("nw317d", bi.nw317d.isChecked() ? "4" : "0");
        sB2.put("nw317e", bi.nw317e.isChecked() ? "5" : "0");
        sB2.put("nw317f", bi.nw317f.isChecked() ? "6" : "0");
        sB2.put("nw317g", bi.nw317g.isChecked() ? "7" : "0");
        sB2.put("nw317h", bi.nw317h.isChecked() ? "8" : "0");
        sB2.put("nw317i", bi.nw317i.isChecked() ? "9" : "0");
        sB2.put("nw317j", bi.nw317j.isChecked() ? "10" : "0");
        sB2.put("nw317k", bi.nw317k.isChecked() ? "11" : "0");
        sB2.put("nw317l", bi.nw317l.isChecked() ? "12" : "0");
        sB2.put("nw317m", bi.nw317m.isChecked() ? "13" : "0");
        sB2.put("nw317961", bi.nw317961.isChecked() ? "961" : "0");
        sB2.put("nw317962", bi.nw317962.isChecked() ? "962" : "0");
        sB2.put("nw317963", bi.nw317963.isChecked() ? "963" : "0");

        sB2.put("nw317961x", bi.nw317961x.getText().toString());
        sB2.put("nw317962x", bi.nw317962x.getText().toString());
        sB2.put("nw317963x", bi.nw317963x.getText().toString());


//        nw318
        sB2.put("nw318", bi.nw318a.isChecked() ? "1"
                : bi.nw318b.isChecked() ? "2"
                : bi.nw318c.isChecked() ? "3"
                : bi.nw318d.isChecked() ? "4"
                : bi.nw318e.isChecked() ? "5"
                : "0");

//        nw319
        sB2.put("nw319m", bi.nw319m.getText().toString());
        sB2.put("nw319d", bi.nw319d.getText().toString());

//        nw320
        sB2.put("nw320", bi.nw320a.isChecked() ? "1"
                : bi.nw320b.isChecked() ? "2"
                : "0");


//        nw321

        sB2.put("nw321", bi.nw321a.isChecked() ? "1"
                : bi.nw321b.isChecked() ? "2"
                : bi.nw321c.isChecked() ? "3"
                : bi.nw321d.isChecked() ? "4"
                : bi.nw321e.isChecked() ? "5"
                : bi.nw321f.isChecked() ? "6"
                : bi.nw321g.isChecked() ? "7"
                : bi.nw321h.isChecked() ? "8"
                : bi.nw32196.isChecked() ? "96"
                : "0");
        sB2.put("nw32196x", bi.nw32196x.getText().toString());

//        nw322
        sB2.put("nw322a", bi.nw322a.isChecked() ? "1" : "0");
        sB2.put("nw322b", bi.nw322b.isChecked() ? "2" : "0");
        sB2.put("nw322c", bi.nw322c.isChecked() ? "3" : "0");
        sB2.put("nw322d", bi.nw322d.isChecked() ? "4" : "0");
        sB2.put("nw322e", bi.nw322e.isChecked() ? "5" : "0");
        sB2.put("nw322f", bi.nw322f.isChecked() ? "6" : "0");
        sB2.put("nw322g", bi.nw322g.isChecked() ? "7" : "0");
        sB2.put("nw322h", bi.nw322h.isChecked() ? "8" : "0");
        sB2.put("nw322i", bi.nw322i.isChecked() ? "9" : "0");
        sB2.put("nw322j", bi.nw322j.isChecked() ? "10" : "0");
        sB2.put("nw322k", bi.nw322k.isChecked() ? "11" : "0");
        sB2.put("nw322l", bi.nw322l.isChecked() ? "12" : "0");
        sB2.put("nw322m", bi.nw322m.isChecked() ? "13" : "0");
        sB2.put("nw322961", bi.nw322961.isChecked() ? "961" : "0");
        sB2.put("nw322962", bi.nw322962.isChecked() ? "962" : "0");
        sB2.put("nw322963", bi.nw322963.isChecked() ? "963" : "0");

        sB2.put("nw322961x", bi.nw322961x.getText().toString());
        sB2.put("nw322962x", bi.nw322962x.getText().toString());
        sB2.put("nw322963x", bi.nw322963x.getText().toString());


//        nw323
        sB2.put("nw323", bi.nw323a.isChecked() ? "1"
                : bi.nw323b.isChecked() ? "2"
                : bi.nw323c.isChecked() ? "3"
                : bi.nw323d.isChecked() ? "4"
                : bi.nw323e.isChecked() ? "5"
                : "0");

//        nw324
        sB2.put("nw324m", bi.nw324m.getText().toString());
        sB2.put("nw324d", bi.nw324d.getText().toString());

//        nw325
        sB2.put("nw325", bi.nw325a.isChecked() ? "1"
                : bi.nw325b.isChecked() ? "2"
                : "0");
//        nw326
        sB2.put("nw326", bi.nw326a.isChecked() ? "1"
                : bi.nw326b.isChecked() ? "2"
                : bi.nw32698.isChecked() ? "98"
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
