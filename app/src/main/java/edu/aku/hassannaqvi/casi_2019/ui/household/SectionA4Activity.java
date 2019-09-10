package edu.aku.hassannaqvi.casi_2019.ui.household;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONA4ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionA4Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

import static edu.aku.hassannaqvi.casi_2019.ui.household.SectionA1Activity.userCountryTajik_Home;

public class SectionA4Activity extends Menu2Activity implements RadioGroup.OnCheckedChangeListener, TextWatcher {

    private final long DELAY = 1000;
    ActivitySectionA4Binding binding;
    DatabaseHelper db;
    private Timer timer = new Timer();
    boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_a4);
        db = new DatabaseHelper(this);

//        Assigning data to UI binding
        binding.setCallback(this);
        this.setTitle(getResources().getString(R.string.cih3heading));

//        Skip Pattern;

//        cih303

        binding.cih301.setOnCheckedChangeListener(this);
        binding.cih302.setOnCheckedChangeListener(this);
        binding.cih303.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.cih303b || i == R.id.cih303c) {
//                    formValidation();
                    ClearClass.ClearAllFields(binding.fldGrcih304, null);
                    binding.fldGrcih304.setVisibility(View.GONE);
                } else {

                    binding.fldGrcih304.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.cih304.addTextChangedListener(this);

        binding.cih30498.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cih30499.setChecked(false);
                    binding.cih304.setEnabled(false);
                    binding.cih304.setText(null);
                    binding.cih304.setError(null);
                } else {
                    binding.cih304.setEnabled(true);
                }
            }
        });
        binding.cih30499.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cih30498.setChecked(false);
                    binding.cih304.setEnabled(false);
                    binding.cih304.setText(null);
                    binding.cih304.setError(null);
                } else {
                    binding.cih304.setEnabled(true);
                }
            }
        });


        binding.cih305.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                formValidation();
                if (checkedId == R.id.cih305b) {
                    ClearClass.ClearAllFields(binding.fldGrpcih305, null);
                    binding.fldGrpcih305.setVisibility(View.GONE);
                } else {
                    binding.cih30696x.setEnabled(false);
                    binding.fldGrpcih305.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.cih306.setOnCheckedChangeListener(this);

//        cih307
        binding.cih307.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                formValidation();
                if (i == R.id.cih307h || i == R.id.cih307i) {
                    ClearClass.ClearAllFields(binding.fldGrpcih308, null);
                    binding.fldGrpcih308.setVisibility(View.GONE);
                } else {
                    binding.fldGrpcih308.setVisibility(View.VISIBLE);

                }
            }
        });

//        cih308
        binding.cih308.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                formValidation();
                if (checkedId == R.id.cih308b) {
                    ClearClass.ClearAllFields(binding.fldGrpcih309, null);
                    binding.fldGrpcih309.setVisibility(View.GONE);
                } else {

                    binding.fldGrpcih309.setVisibility(View.VISIBLE);

                }
            }
        });


        binding.cih309.addTextChangedListener(this);

        binding.cih310.setOnCheckedChangeListener(this);
        binding.cih31101.setOnCheckedChangeListener(this);
        binding.cih31102.setOnCheckedChangeListener(this);
        binding.cih31103.setOnCheckedChangeListener(this);
        binding.cih31104.setOnCheckedChangeListener(this);
        binding.cih31105.setOnCheckedChangeListener(this);
        binding.cih31106.setOnCheckedChangeListener(this);
        binding.cih31107.setOnCheckedChangeListener(this);
        binding.cih31108.setOnCheckedChangeListener(this);
        binding.cih31109.setOnCheckedChangeListener(this);
        binding.cih31110.setOnCheckedChangeListener(this);
        binding.cih31111.setOnCheckedChangeListener(this);
        binding.cih31112.setOnCheckedChangeListener(this);
        binding.cih31113.setOnCheckedChangeListener(this);
        binding.cih31114.setOnCheckedChangeListener(this);
        binding.cih31115.setOnCheckedChangeListener(this);
        binding.cih31116.setOnCheckedChangeListener(this);
        binding.cih31117.setOnCheckedChangeListener(this);
        binding.cih31118.setOnCheckedChangeListener(this);
        binding.cih31119.setOnCheckedChangeListener(this);
        binding.cih312a.setOnCheckedChangeListener(this);
        binding.cih312b.setOnCheckedChangeListener(this);
        binding.cih312c.setOnCheckedChangeListener(this);
        binding.cih312d.setOnCheckedChangeListener(this);
        binding.cih312e.setOnCheckedChangeListener(this);
        binding.cih312f.setOnCheckedChangeListener(this);
        binding.cih312g.setOnCheckedChangeListener(this);
        binding.cih312h.setOnCheckedChangeListener(this);
        binding.cih312i.setOnCheckedChangeListener(this);
        binding.cih314.setOnCheckedChangeListener(this);


        binding.cih315.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                formValidation();
                if (binding.cih315a.isChecked()) {
                    ClearClass.ClearAllFields(binding.fldGrpcih316, true);
                    binding.fldGrpcih316.setVisibility(View.VISIBLE);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpcih316, false);
                    binding.fldGrpcih316.setVisibility(View.GONE);
                }
            }
        });

        binding.cih316.setOnCheckedChangeListener(this);
        binding.cih317.setOnCheckedChangeListener(this);


//        Validation Boolean
        MainApp.validateFlag = false;

        if (SectionA1Activity.editFormFlag) {
            autoPopulate();
        }

        /*For Tajik*/
        if (userCountryTajik_Home) {

            binding.fldGrpA4a.setVisibility(View.GONE);
            binding.fldGrpA4b.setVisibility(View.GONE);

        }

    }

    private void autoPopulate() {
        FormsContract formContract = db.getsA4();

        if (!formContract.getsA4().equals("")) {

            JSONA4ModelClass jsonA4 = JSONUtilClass.getModelFromJSON(formContract.getsA4(), JSONA4ModelClass.class);

            if (!jsonA4.getcih301().equals("0")) {
                binding.cih301.check(
                        jsonA4.getcih301().equals("1") ? binding.cih301a.getId()
                                : jsonA4.getcih301().equals("2") ? binding.cih301b.getId()
                                : jsonA4.getcih301().equals("3") ? binding.cih301c.getId()
                                : jsonA4.getcih301().equals("4") ? binding.cih301d.getId()
                                : jsonA4.getcih301().equals("5") ? binding.cih301e.getId()
                                : jsonA4.getcih301().equals("6") ? binding.cih301f.getId()
                                : jsonA4.getcih301().equals("7") ? binding.cih301g.getId()
                                : jsonA4.getcih301().equals("7") ? binding.cih301h.getId()
                                : jsonA4.getcih301().equals("9") ? binding.cih301i.getId()
                                /*: jsonA4.getcih301().equals("10") ? binding.cih301j.getId()
                                : jsonA4.getcih301().equals("11") ? binding.cih301k.getId()
                                : jsonA4.getcih301().equals("12") ? binding.cih301l.getId()
                                : jsonA4.getcih301().equals("13") ? binding.cih301m.getId()*/
                                : binding.cih30196.getId()
                );
            }
            binding.cih30196x.setText(jsonA4.getcih30196x());
            if (!jsonA4.getcih302().equals("0")) {
                binding.cih302.check(
                        jsonA4.getcih302().equals("1") ? binding.cih302a.getId() :
                                jsonA4.getcih302().equals("2") ? binding.cih302b.getId() :
                                        binding.cih30296.getId()
                );
            }
            binding.cih30296x.setText(jsonA4.getcih30296x());
            if (!jsonA4.getcih303a().equals("0")) {
                binding.cih303a.check(
                        jsonA4.getcih303a().equals("1") ? binding.cih303a2.getId() :
                                binding.cih303a2.getId()
                );
            }
            if (!jsonA4.getcih303().equals("0")) {
                binding.cih303.check(
                        jsonA4.getcih303().equals("1") ? binding.cih303b.getId() :
                                jsonA4.getcih303().equals("2") ? binding.cih303c.getId() :
                                        jsonA4.getcih303().equals("3") ? binding.cih303d.getId() :
                                                jsonA4.getcih303().equals("4") ? binding.cih303e.getId() :
                                                        jsonA4.getcih303().equals("5") ? binding.cih303f.getId() :
                                                                jsonA4.getcih303().equals("6") ? binding.cih303g.getId() :
                                                                        jsonA4.getcih303().equals("7") ? binding.cih303h.getId() :
                                                                                jsonA4.getcih303().equals("8") ? binding.cih303i.getId() :
                                                                                        jsonA4.getcih303().equals("9") ? binding.cih303j.getId() :
                                                                                                jsonA4.getcih303().equals("10") ? binding.cih303k.getId() :
                                                                                                        jsonA4.getcih303().equals("11") ? binding.cih303l.getId() :
                                                                                                                jsonA4.getcih303().equals("12") ? binding.cih303m.getId() :
                                                                                                                        jsonA4.getcih303().equals("13") ? binding.cih303n.getId() :
                                                                                                                                jsonA4.getcih303().equals("14") ? binding.cih303o.getId() :
                                                                                                                                        jsonA4.getcih303().equals("15") ? binding.cih303p.getId() :
                                                                                                                                                jsonA4.getcih303().equals("16") ? binding.cih303q.getId() :
                                                                                                                                                        binding.cih30396.getId()
                );
                binding.cih30396x.setText(jsonA4.getcih30396x());
            }


            if (jsonA4.getcih304().equals("000")) {
                binding.cih30499.setChecked(true);
            } else if (jsonA4.getcih304().equals("998")) {
                binding.cih30498.setChecked(true);
            } else {
                binding.cih304.setText(jsonA4.getcih304());
            }


            if (!jsonA4.getcih305().equals("0")) {
                binding.cih305.check(
                        jsonA4.getcih305().equals("1") ? binding.cih305a.getId() :
                                binding.cih305b.getId()
                );
            }
            if (!jsonA4.getcih306().equals("0")) {
                binding.cih306.check(
                        jsonA4.getcih306().equals("1") ? binding.cih306a.getId() :
                                jsonA4.getcih306().equals("2") ? binding.cih306b.getId() :
                                        jsonA4.getcih306().equals("3") ? binding.cih306c.getId() :
                                                jsonA4.getcih306().equals("4") ? binding.cih306d.getId() :
                                                        jsonA4.getcih306().equals("5") ? binding.cih306e.getId() :
                                                                jsonA4.getcih306().equals("6") ? binding.cih306f.getId() :
                                                                        binding.cih30696.getId()
                );
            }
            binding.cih30696x.setText(jsonA4.getcih30696x());
            if (!jsonA4.getcih307().equals("0")) {
                binding.cih307.check(
                        jsonA4.getcih307().equals("1") ? binding.cih307a.getId() :
                                jsonA4.getcih307().equals("2") ? binding.cih307b.getId() :
                                        jsonA4.getcih307().equals("3") ? binding.cih307c.getId() :
                                                jsonA4.getcih307().equals("4") ? binding.cih307d.getId() :
                                                        jsonA4.getcih307().equals("5") ? binding.cih307e.getId() :
                                                                jsonA4.getcih307().equals("6") ? binding.cih307f.getId() :
                                                                        jsonA4.getcih307().equals("7") ? binding.cih307g.getId() :
                                                                                jsonA4.getcih307().equals("8") ? binding.cih307h.getId() :
                                                                                        jsonA4.getcih307().equals("9") ? binding.cih307i.getId() :
                                                                                                binding.cih30796.getId()
                );
            }
            binding.cih30796x.setText(jsonA4.getcih30796x());
            if (!jsonA4.getcih308().equals("0")) {
                binding.cih308.check(
                        jsonA4.getcih308().equals("1") ? binding.cih308a.getId() :
                                binding.cih308b.getId()
                );
            }
            binding.cih309.setText(jsonA4.getcih309());

            if (!jsonA4.getcih310().equals("0")) {
                binding.cih310.check(
                        jsonA4.getcih310().equals("1") ? binding.cih310a.getId() :
                                jsonA4.getcih310().equals("2") ? binding.cih310b.getId() :
                                        jsonA4.getcih310().equals("3") ? binding.cih310c.getId() :
                                                jsonA4.getcih310().equals("4") ? binding.cih310d.getId() :
                                                        jsonA4.getcih310().equals("5") ? binding.cih310e.getId() :
                                                                jsonA4.getcih310().equals("6") ? binding.cih310f.getId() :
                                                                        jsonA4.getcih310().equals("7") ? binding.cih310g.getId() :
                                                                                binding.cih31096.getId()
                );
            }
            binding.cih31096x.setText(jsonA4.getcih31096x());
            if (!jsonA4.getcih31101().equals("0")) {
                binding.cih31101.check(
                        jsonA4.getcih31101().equals("1") ? binding.cih31101a.getId() :
                                binding.cih31101b.getId()
                );
            }
            if (!jsonA4.getcih31101().equals("0")) {
                binding.cih31101.check(
                        jsonA4.getcih31101().equals("1") ? binding.cih31101a.getId() :
                                binding.cih31101b.getId()
                );
            }
            if (!jsonA4.getcih31102().equals("0")) {
                binding.cih31102.check(
                        jsonA4.getcih31102().equals("1") ? binding.cih31102a.getId() :
                                binding.cih31102b.getId()
                );
            }
            if (!jsonA4.getcih31103().equals("0")) {
                binding.cih31103.check(
                        jsonA4.getcih31103().equals("1") ? binding.cih31103a.getId() :
                                binding.cih31103b.getId()
                );
            }
            if (!jsonA4.getcih31104().equals("0")) {
                binding.cih31104.check(
                        jsonA4.getcih31104().equals("1") ? binding.cih31104a.getId() :
                                binding.cih31104b.getId()
                );
            }
            if (!jsonA4.getcih31105().equals("0")) {
                binding.cih31105.check(
                        jsonA4.getcih31105().equals("1") ? binding.cih31105a.getId() :
                                binding.cih31105b.getId()
                );
            }
            if (!jsonA4.getcih31106().equals("0")) {
                binding.cih31106.check(
                        jsonA4.getcih31106().equals("1") ? binding.cih31106a.getId() :
                                binding.cih31106b.getId()
                );
            }
            if (!jsonA4.getcih31107().equals("0")) {
                binding.cih31107.check(
                        jsonA4.getcih31107().equals("1") ? binding.cih31107a.getId() :
                                binding.cih31107b.getId()
                );
            }
            if (!jsonA4.getcih31108().equals("0")) {
                binding.cih31108.check(
                        jsonA4.getcih31108().equals("1") ? binding.cih31108a.getId() :
                                binding.cih31108b.getId()
                );
            }
            if (!jsonA4.getcih31109().equals("0")) {
                binding.cih31109.check(
                        jsonA4.getcih31109().equals("1") ? binding.cih31109a.getId() :
                                binding.cih31109b.getId()
                );
            }
            if (!jsonA4.getcih31110().equals("0")) {
                binding.cih31110.check(
                        jsonA4.getcih31110().equals("1") ? binding.cih31110a.getId() :
                                binding.cih31110b.getId()
                );
            }
            if (!jsonA4.getcih31111().equals("0")) {
                binding.cih31111.check(
                        jsonA4.getcih31111().equals("1") ? binding.cih31111a.getId() :
                                binding.cih31111b.getId()
                );
            }
            if (!jsonA4.getcih31112().equals("0")) {
                binding.cih31112.check(
                        jsonA4.getcih31112().equals("1") ? binding.cih31112a.getId() :
                                binding.cih31112b.getId()
                );
            }
            if (!jsonA4.getcih31113().equals("0")) {
                binding.cih31113.check(
                        jsonA4.getcih31113().equals("1") ? binding.cih31113a.getId() :
                                binding.cih31113b.getId()
                );
            }
            if (!jsonA4.getcih31114().equals("0")) {
                binding.cih31114.check(
                        jsonA4.getcih31114().equals("1") ? binding.cih31114a.getId() :
                                binding.cih31114b.getId()
                );
            }
            if (!jsonA4.getcih31115().equals("0")) {
                binding.cih31115.check(
                        jsonA4.getcih31115().equals("1") ? binding.cih31115a.getId() :
                                binding.cih31115b.getId()
                );
            }
            if (!jsonA4.getcih31116().equals("0")) {
                binding.cih31116.check(
                        jsonA4.getcih31116().equals("1") ? binding.cih31116a.getId() :
                                binding.cih31116b.getId()
                );
            }
            if (!jsonA4.getcih31117().equals("0")) {
                binding.cih31117.check(
                        jsonA4.getcih31117().equals("1") ? binding.cih31117a.getId() :
                                binding.cih31117b.getId()
                );
            }
            if (!jsonA4.getcih31118().equals("0")) {
                binding.cih31118.check(
                        jsonA4.getcih31118().equals("1") ? binding.cih31118a.getId() :
                                binding.cih31118b.getId()
                );
            }
            if (!jsonA4.getcih31119().equals("0")) {
                binding.cih31119.check(
                        jsonA4.getcih31119().equals("1") ? binding.cih31119a.getId() :
                                binding.cih31119b.getId()
                );
            }


            if (!jsonA4.getcih312a().equals("0")) {
                binding.cih312a.check(
                        jsonA4.getcih312a().equals("1") ? binding.cih312a1.getId() :
                                binding.cih312a2.getId()
                );
            }

            if (!jsonA4.getcih312b().equals("0")) {
                binding.cih312b.check(
                        jsonA4.getcih312b().equals("1") ? binding.cih312b1.getId() :
                                binding.cih312b2.getId()
                );
            }
            if (!jsonA4.getcih312c().equals("0")) {
                binding.cih312c.check(
                        jsonA4.getcih312c().equals("1") ? binding.cih312c1.getId() :
                                binding.cih312c2.getId()
                );
            }
            if (!jsonA4.getcih312d().equals("0")) {
                binding.cih312d.check(
                        jsonA4.getcih312d().equals("1") ? binding.cih312d1.getId() :
                                binding.cih312d2.getId()
                );
            }
            if (!jsonA4.getcih312e().equals("0")) {
                binding.cih312e.check(
                        jsonA4.getcih312e().equals("1") ? binding.cih312e1.getId() :
                                binding.cih312e2.getId()
                );
            }
            if (!jsonA4.getcih312f().equals("0")) {
                binding.cih312f.check(
                        jsonA4.getcih312f().equals("1") ? binding.cih312f1.getId() :
                                binding.cih312f2.getId()
                );
            }
            if (!jsonA4.getcih312g().equals("0")) {
                binding.cih312g.check(
                        jsonA4.getcih312g().equals("1") ? binding.cih312g1.getId() :
                                binding.cih312g2.getId()
                );
            }
            if (!jsonA4.getcih312h().equals("0")) {
                binding.cih312h.check(
                        jsonA4.getcih312h().equals("1") ? binding.cih312h1.getId() :
                                binding.cih312h2.getId()
                );
            }
            if (!jsonA4.getcih312i().equals("0")) {
                binding.cih312i.check(
                        jsonA4.getcih312i().equals("1") ? binding.cih312i1.getId() :
                                binding.cih312i2.getId()
                );
            }
            if (!jsonA4.getcih313a().equals("0")) {
                binding.cih313a.setChecked(true);
            }
            if (!jsonA4.getcih313b().equals("0")) {
                binding.cih313b.setChecked(true);
            }
            if (!jsonA4.getcih313c().equals("0")) {
                binding.cih313c.setChecked(true);
            }
            if (!jsonA4.getcih313d().equals("0")) {
                binding.cih313d.setChecked(true);
            }
            if (!jsonA4.getcih313e().equals("0")) {
                binding.cih313e.setChecked(true);
            }
            if (!jsonA4.getcih313f().equals("0")) {
                binding.cih313f.setChecked(true);
            }
            if (!jsonA4.getcih31396().equals("0")) {
                binding.cih31396.setChecked(true);
            }
            binding.cih31396x.setText(jsonA4.getcih31396x());


            if (!jsonA4.getcih314().equals("0")) {
                binding.cih314.check(
                        jsonA4.getcih314().equals("1") ? binding.cih314a.getId() :
                                jsonA4.getcih314().equals("2") ? binding.cih314b.getId() :
                                        jsonA4.getcih314().equals("3") ? binding.cih314c.getId() :
                                                jsonA4.getcih314().equals("4") ? binding.cih314d.getId() :
                                                        jsonA4.getcih314().equals("5") ? binding.cih314e.getId() :
                                                                jsonA4.getcih314().equals("6") ? binding.cih314f.getId() :
                                                                        jsonA4.getcih314().equals("7") ? binding.cih314g.getId() :
                                                                                jsonA4.getcih314().equals("8") ? binding.cih314h.getId() :
                                                                                        jsonA4.getcih314().equals("9") ? binding.cih314i.getId() :
                                                                                                jsonA4.getcih314().equals("10") ? binding.cih314j.getId() :
                                                                                                        jsonA4.getcih314().equals("11") ? binding.cih314k.getId() :

                                                                                                                binding.cih31496.getId()
                );
            }
            binding.cih31496x.setText(jsonA4.getcih31496x());
            if (!jsonA4.getcih315().equals("0")) {
                binding.cih315.check(
                        jsonA4.getcih315().equals("1") ? binding.cih315a.getId() :
                                jsonA4.getcih315().equals("2") ? binding.cih315b.getId() :
                                        jsonA4.getcih315().equals("3") ? binding.cih315c.getId() :
                                                binding.cih31496.getId()
                );
            }
            binding.cih31596x.setText(jsonA4.getcih31596x());
            if (!jsonA4.getcih316().equals("0")) {
                binding.cih316.check(
                        jsonA4.getcih316().equals("1") ? binding.cih316a.getId() : binding.cih316b.getId()
                );
            }
            if (!jsonA4.getcih317().equals("0")) {
                binding.cih317.check(
                        jsonA4.getcih317().equals("1") ? binding.cih317a.getId() :
                                jsonA4.getcih317().equals("2") ? binding.cih317b.getId() :
                                        jsonA4.getcih317().equals("3") ? binding.cih317c.getId() :
                                                jsonA4.getcih317().equals("4") ? binding.cih317c.getId() :
                                                        jsonA4.getcih317().equals("5") ? binding.cih317c.getId() :
                                                                jsonA4.getcih317().equals("6") ? binding.cih317c.getId() :
                                                                        jsonA4.getcih317().equals("7") ? binding.cih317c.getId() :
                                                                                jsonA4.getcih317().equals("8") ? binding.cih317c.getId() :
                                                                                        jsonA4.getcih317().equals("9") ? binding.cih317c.getId() :
                                                                                                jsonA4.getcih317().equals("10") ? binding.cih317c.getId() :
                                                                                                        jsonA4.getcih317().equals("11") ? binding.cih317c.getId() :
                                                                                                                binding.cih31796.getId()
                );
            }
            binding.cih31796x.setText(jsonA4.getcih31796x());


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

                finish();

                startActivity(new Intent(this, SectionA402Activity.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
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
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }

    private boolean formValidation() {


        if (!ValidatorClass.EmptyRadioButton(this, binding.cih301, binding.cih301a, getString(R.string.cih301))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih301, binding.cih30196, binding.cih30196x, getString(R.string.cih301))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih302, binding.cih302a, getString(R.string.cih302))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih302, binding.cih30296, binding.cih30296x, getString(R.string.cih302))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih303a, binding.cih303a1, getString(R.string.cih303a))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih303, binding.cih303b, getString(R.string.cih303))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih303, binding.cih30396, binding.cih30396x, getString(R.string.cih303))) {
            return false;
        }
        if (!binding.cih303b.isChecked() && !binding.cih303c.isChecked()) {

            if (!binding.cih30498.isChecked() && !binding.cih30499.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.cih304, getString(R.string.cih304))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.cih304, 1, 999, getString(R.string.cih304), "minutes")) {
                    return false;
                }
            } else {
                if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrcih304check, binding.cih30499, getString(R.string.cih304))) {
                    return false;
                }
            }
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih305, binding.cih305b, getString(R.string.cih305))) {
            return false;
        }
        if (binding.cih305a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.cih306, binding.cih306a, getString(R.string.cih306))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, binding.cih306, binding.cih30696, binding.cih30696x, getString(R.string.cih306))) {
                return false;
            }
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih307, binding.cih307a, getString(R.string.cih307))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih307, binding.cih30796, binding.cih30796x, getString(R.string.cih307))) {
            return false;
        }

        if (!binding.cih307h.isChecked() && !binding.cih307i.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.cih308, binding.cih308b, getString(R.string.cih308))) {
                return false;
            }

            if (binding.cih308a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.cih309, getString(R.string.cih309))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, binding.cih309, 1, 99, getString(R.string.cih309), "Toilet")) {
                    return false;
                }
            }
        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.cih310, binding.cih310a, getString(R.string.cih310))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih310, binding.cih31096, binding.cih31096x, getString(R.string.cih310))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31101, binding.cih31101b, getString(R.string.cih31101))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31102, binding.cih31102b, getString(R.string.cih31102))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31103, binding.cih31103b, getString(R.string.cih31103))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31104, binding.cih31104b, getString(R.string.cih31104))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31105, binding.cih31105b, getString(R.string.cih31105))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31106, binding.cih31106b, getString(R.string.cih31106))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31107, binding.cih31107b, getString(R.string.cih31107))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31108, binding.cih31108b, getString(R.string.cih31108))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31109, binding.cih31109b, getString(R.string.cih31109))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31110, binding.cih31110b, getString(R.string.cih31110))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31111, binding.cih31111b, getString(R.string.cih31111))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31112, binding.cih31112b, getString(R.string.cih31112))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31113, binding.cih31113b, getString(R.string.cih31113))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31114, binding.cih31114b, getString(R.string.cih31114))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31115, binding.cih31115b, getString(R.string.cih31115))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31116, binding.cih31116b, getString(R.string.cih31116))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31117, binding.cih31117b, getString(R.string.cih31117))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31118, binding.cih31118b, getString(R.string.cih31118))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih31119, binding.cih31119b, getString(R.string.cih31119))) {
            return false;
        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.cih312a, binding.cih312a1, getString(R.string.cih312a))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih312b, binding.cih312b1, getString(R.string.cih312b))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih312c, binding.cih312c1, getString(R.string.cih312c))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih312d, binding.cih312d1, getString(R.string.cih312d))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih312e, binding.cih312e1, getString(R.string.cih312e))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih312f, binding.cih312f1, getString(R.string.cih312f))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih312g, binding.cih312g1, getString(R.string.cih312g))) {
            return false;
        }

        if (!userCountryTajik_Home) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.cih312h, binding.cih312h1, getString(R.string.cih312h))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, binding.cih312i, binding.cih312i1, getString(R.string.cih312i))) {
                return false;
            }
        }

        if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpna0413check, binding.cih313a, getString(R.string.cih313))) {
            return false;
        }
        if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpna0413check, binding.cih31396, binding.cih31396x, getString(R.string.cih313))) {
            return false;
        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.cih314, binding.cih314a, getString(R.string.cih314))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cih314, binding.cih31496, binding.cih31496x, getString(R.string.cih314))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih315, binding.cih315a, getString(R.string.cih315))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih315, binding.cih31596, binding.cih31596x, getString(R.string.cih315))) {
            return false;
        }
        if (binding.cih315a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.cih316, binding.cih316a, getString(R.string.cih316))) {
                return false;
            }
        }
        // 315

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih317, binding.cih317a, getString(R.string.cih317))) {
            return false;
        }


        return ValidatorClass.EmptyRadioButton(this, binding.cih317, binding.cih31796, binding.cih31796x, getString(R.string.cih317));

    }

    private void SaveDraft() throws JSONException {


        JSONObject sA4 = new JSONObject();
        if (SectionA1Activity.editFormFlag) {
            sA4.put("edit_updatedate_sa4", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }
        sA4.put("cih301", binding.cih301a.isChecked() ? "1"
                : binding.cih301b.isChecked() ? "2"
                : binding.cih301c.isChecked() ? "3"
                : binding.cih301d.isChecked() ? "4"
                : binding.cih301e.isChecked() ? "5"
                : binding.cih301f.isChecked() ? "6"
                : binding.cih301g.isChecked() ? "7"
                : binding.cih301h.isChecked() ? "8"
                : binding.cih301i.isChecked() ? "9"
                /*: binding.cih301j.isChecked() ? "10"
                : binding.cih301k.isChecked() ? "11"
                : binding.cih301l.isChecked() ? "12"
                : binding.cih301m.isChecked() ? "13"*/
                : binding.cih30196.isChecked() ? "96"
                : "0");
        sA4.put("cih30196x", binding.cih30196x.getText().toString());

        sA4.put("cih302", binding.cih302a.isChecked() ? "1"
                : binding.cih302b.isChecked() ? "2"
                : binding.cih30296.isChecked() ? "96" : "0");
        sA4.put("cih30296x", binding.cih30296x.getText().toString());

        sA4.put("cih303a", binding.cih303a1.isChecked() ? "1"
                : binding.cih303a2.isChecked() ? "2"
                : "0");

        sA4.put("cih303", binding.cih303b.isChecked() ? "1"
                : binding.cih303c.isChecked() ? "2"
                : binding.cih303d.isChecked() ? "3"
                : binding.cih303e.isChecked() ? "4"
                : binding.cih303f.isChecked() ? "5"
                : binding.cih303g.isChecked() ? "6"
                : binding.cih303h.isChecked() ? "7"
                : binding.cih303i.isChecked() ? "8"
                : binding.cih303j.isChecked() ? "9"
                : binding.cih303k.isChecked() ? "10"
                : binding.cih303l.isChecked() ? "11"
                : binding.cih303m.isChecked() ? "12"
                : binding.cih303n.isChecked() ? "13"
                : binding.cih303o.isChecked() ? "14"
                : binding.cih303p.isChecked() ? "15"
                : binding.cih303q.isChecked() ? "16"
                : binding.cih30396.isChecked() ? "96"
                : "0");
        sA4.put("cih30396x", binding.cih30396x.getText().toString());

        sA4.put("cih304", binding.cih30498.isChecked() ? "998"
                : binding.cih30499.isChecked() ? "000"
                : binding.cih304.getText().toString());

        sA4.put("cih305", binding.cih305a.isChecked() ? "1"
                : binding.cih305b.isChecked() ? "2"
                : "0");

        sA4.put("cih306", binding.cih306a.isChecked() ? "1"
                : binding.cih306b.isChecked() ? "2"
                : binding.cih306c.isChecked() ? "3"
                : binding.cih306d.isChecked() ? "4"
                : binding.cih306e.isChecked() ? "5"
                : binding.cih306f.isChecked() ? "6"
                : binding.cih30696.isChecked() ? "96"
                : "0");
        sA4.put("cih30696x", binding.cih30696x.getText().toString());


        sA4.put("cih307", binding.cih307a.isChecked() ? "1"
                : binding.cih307b.isChecked() ? "2"
                : binding.cih307c.isChecked() ? "3"
                : binding.cih307d.isChecked() ? "4"
                : binding.cih307e.isChecked() ? "5"
                : binding.cih307f.isChecked() ? "6"
                : binding.cih307g.isChecked() ? "7"
                : binding.cih307h.isChecked() ? "8"
                : binding.cih307i.isChecked() ? "9"
                : binding.cih30796.isChecked() ? "96"
                : "0");

        sA4.put("cih30796x", binding.cih30796x.getText().toString());

        sA4.put("cih308", binding.cih308a.isChecked() ? "1"
                : binding.cih308b.isChecked() ? "2"
                : "0");

        sA4.put("cih309", binding.cih309.getText().toString());
//        cih310
        sA4.put("cih310", binding.cih310a.isChecked() ? "1"
                : binding.cih310b.isChecked() ? "2"
                : binding.cih310c.isChecked() ? "3"
                : binding.cih310d.isChecked() ? "4"
                : binding.cih310e.isChecked() ? "5"
                : binding.cih310f.isChecked() ? "6"
                : binding.cih310g.isChecked() ? "7"
                : binding.cih31096.isChecked() ? "96"
                : "0");
        sA4.put("cih31096x", binding.cih31096x.getText().toString());
//        cih311
        sA4.put("cih31101", binding.cih31101a.isChecked() ? "1"
                : binding.cih31101b.isChecked() ? "2"
                : "0");

        sA4.put("cih31102", binding.cih31102a.isChecked() ? "1"
                : binding.cih31102b.isChecked() ? "2"
                : "0");

        sA4.put("cih31103", binding.cih31103a.isChecked() ? "1"
                : binding.cih31103b.isChecked() ? "2"
                : "0");

        sA4.put("cih31104", binding.cih31104a.isChecked() ? "1"
                : binding.cih31104b.isChecked() ? "2"
                : "0");

        sA4.put("cih31105", binding.cih31105a.isChecked() ? "1"
                : binding.cih31105b.isChecked() ? "2"
                : "0");
        sA4.put("cih31106", binding.cih31106a.isChecked() ? "1"
                : binding.cih31106b.isChecked() ? "2"
                : "0");
        sA4.put("cih31107", binding.cih31107a.isChecked() ? "1"
                : binding.cih31107b.isChecked() ? "2"
                : "0");
        sA4.put("cih31108", binding.cih31108a.isChecked() ? "1"
                : binding.cih31108b.isChecked() ? "2"
                : "0");
        sA4.put("cih31109", binding.cih31109a.isChecked() ? "1"
                : binding.cih31109b.isChecked() ? "2"
                : "0");
        sA4.put("cih31110", binding.cih31110a.isChecked() ? "1"
                : binding.cih31110b.isChecked() ? "2"
                : "0");
        sA4.put("cih31111", binding.cih31111a.isChecked() ? "1"
                : binding.cih31111b.isChecked() ? "2"
                : "0");
        sA4.put("cih31112", binding.cih31112a.isChecked() ? "1"
                : binding.cih31112b.isChecked() ? "2"
                : "0");
        sA4.put("cih31113", binding.cih31113a.isChecked() ? "1"
                : binding.cih31113b.isChecked() ? "2"
                : "0");
        sA4.put("cih31114", binding.cih31114a.isChecked() ? "1"
                : binding.cih31114b.isChecked() ? "2"
                : "0");
        sA4.put("cih31115", binding.cih31115a.isChecked() ? "1"
                : binding.cih31115b.isChecked() ? "2"
                : "0");
        sA4.put("cih31116", binding.cih31116a.isChecked() ? "1"
                : binding.cih31116b.isChecked() ? "2"
                : "0");
        sA4.put("cih31117", binding.cih31117a.isChecked() ? "1"
                : binding.cih31117b.isChecked() ? "2"
                : "0");
        sA4.put("cih31118", binding.cih31118a.isChecked() ? "1"
                : binding.cih31118b.isChecked() ? "2"
                : "0");
        sA4.put("cih31119", binding.cih31119a.isChecked() ? "1"
                : binding.cih31119b.isChecked() ? "2"
                : "0");

//        cih312
        sA4.put("cih312a", binding.cih312a1.isChecked() ? "1" : binding.cih312a2.isChecked() ? "2" : "0");
        sA4.put("cih312b", binding.cih312b1.isChecked() ? "1" : binding.cih312b2.isChecked() ? "2" : "0");
        sA4.put("cih312c", binding.cih312c1.isChecked() ? "1" : binding.cih312c2.isChecked() ? "2" : "0");
        sA4.put("cih312d", binding.cih312d1.isChecked() ? "1" : binding.cih312d2.isChecked() ? "2" : "0");
        sA4.put("cih312e", binding.cih312e1.isChecked() ? "1" : binding.cih312e2.isChecked() ? "2" : "0");
        sA4.put("cih312f", binding.cih312f1.isChecked() ? "1" : binding.cih312f2.isChecked() ? "2" : "0");
        sA4.put("cih312g", binding.cih312g1.isChecked() ? "1" : binding.cih312g2.isChecked() ? "2" : "0");
        sA4.put("cih312h", binding.cih312h1.isChecked() ? "1" : binding.cih312h2.isChecked() ? "2" : "0");
        sA4.put("cih312i", binding.cih312i1.isChecked() ? "1" : binding.cih312i2.isChecked() ? "2" : "0");

//        cih313
        sA4.put("cih313a", binding.cih313a.isChecked() ? "1" : "0");
        sA4.put("cih313b", binding.cih313b.isChecked() ? "2" : "0");
        sA4.put("cih313c", binding.cih313c.isChecked() ? "3" : "0");
        sA4.put("cih313d", binding.cih313d.isChecked() ? "4" : "0");
        sA4.put("cih313e", binding.cih313e.isChecked() ? "5" : "0");
        sA4.put("cih313f", binding.cih313f.isChecked() ? "6" : "0");
        sA4.put("cih31396", binding.cih31396.isChecked() ? "96" : "0");
        sA4.put("cih31396x", binding.cih31396x.getText().toString());

//        cih314
        sA4.put("cih314", binding.cih314a.isChecked() ? "1"
                : binding.cih314b.isChecked() ? "2"
                : binding.cih314c.isChecked() ? "3"
                : binding.cih314d.isChecked() ? "4"
                : binding.cih314e.isChecked() ? "5"
                : binding.cih314f.isChecked() ? "6"
                : binding.cih314g.isChecked() ? "7"
                : binding.cih314h.isChecked() ? "8"
                : binding.cih314i.isChecked() ? "9"
                : binding.cih314j.isChecked() ? "10"
                : binding.cih314k.isChecked() ? "11"
                : binding.cih31496.isChecked() ? "96"
                : "0");
        sA4.put("cih31496x", binding.cih31496x.getText().toString());
//       cih315
        sA4.put("cih315", binding.cih315a.isChecked() ? "1"
                : binding.cih315b.isChecked() ? "2"
                : binding.cih315c.isChecked() ? "3"
                : binding.cih31596.isChecked() ? "96"
                : "0");
        sA4.put("cih31596x", binding.cih31596x.getText().toString());
//        cih316
        sA4.put("cih316", binding.cih316a.isChecked() ? "1"
                : binding.cih316b.isChecked() ? "2"
                : "0");

//        cih317
        sA4.put("cih317", binding.cih317a.isChecked() ? "1"
                : binding.cih317b.isChecked() ? "2"
                : binding.cih317c.isChecked() ? "3"
                : binding.cih317d.isChecked() ? "4"
                : binding.cih317e.isChecked() ? "5"
                : binding.cih317f.isChecked() ? "6"
                : binding.cih317g.isChecked() ? "7"
                : binding.cih317h.isChecked() ? "8"
                : binding.cih317i.isChecked() ? "9"
                : binding.cih317j.isChecked() ? "10"
                : binding.cih317k.isChecked() ? "11"
                : binding.cih31796.isChecked() ? "96"
                : "0");
        sA4.put("cih31796x", binding.cih31796x.getText().toString());

//        cih318


        MainApp.fc.setsA4(String.valueOf(sA4));


        //

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSA4();

        if (updcount == 1) {

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        formValidation();
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
}
