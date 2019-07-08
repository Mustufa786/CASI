package edu.aku.hassannaqvi.casi_2018.ui.household;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONA4ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionA4Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2018.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionA4Activity extends Menu2Activity implements RadioGroup.OnCheckedChangeListener, TextWatcher {

    private final long DELAY = 1000;
    ActivitySectionA4Binding binding;
    DatabaseHelper db;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_a4);
        db = new DatabaseHelper(this);

//        Assigning data to UI binding
        binding.setCallback(this);
        this.setTitle(getResources().getString(R.string.nh3heading));

//        Skip Pattern;

//        nh303

        binding.nh301.setOnCheckedChangeListener(this);
        binding.nh302.setOnCheckedChangeListener(this);
        binding.nh303.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.nh303b || i == R.id.nh303c) {
                    formValidation();
                    ClearClass.ClearAllFields(binding.fldGrnh304, false);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrnh304, true);
                }
            }
        });

        binding.nh304.addTextChangedListener(this);

        binding.nh30498.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.nh30499.setChecked(false);
                    binding.nh304.setEnabled(false);
                    binding.nh304.setText(null);
                    binding.nh304.setError(null);
                } else {
                    binding.nh304.setEnabled(true);
                }
            }
        });
        binding.nh30499.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.nh30498.setChecked(false);
                    binding.nh304.setEnabled(false);
                    binding.nh304.setText(null);
                    binding.nh304.setError(null);
                } else {
                    binding.nh304.setEnabled(true);
                }
            }
        });


        binding.nh305.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (checkedId == R.id.nh305b) {
                    ClearClass.ClearAllFields(binding.fldGrpnh305, false);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpnh305, true);
                    binding.nh30696x.setEnabled(false);
                }
            }
        });

        binding.nh306.setOnCheckedChangeListener(this);

//        nh307
        binding.nh307.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                formValidation();
                if (i == R.id.nh307h || i == R.id.nh307i) {
                    ClearClass.ClearAllFields(binding.fldGrpnh308, false);
                    //  ClearClass.ClearAllFields(binding.fldGrpnh309,true);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpnh308, true);
                }
            }
        });

//        nh308
        binding.nh308.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (checkedId == R.id.nh308b) {
                    ClearClass.ClearAllFields(binding.fldGrpnh309, false);
                } else {
//                    binding.fldGrpnh309.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(binding.fldGrpnh309, true);

                }
            }
        });


        binding.nh309.addTextChangedListener(this);

        binding.nh310.setOnCheckedChangeListener(this);
        binding.nh31101.setOnCheckedChangeListener(this);
        binding.nh31102.setOnCheckedChangeListener(this);
        binding.nh31103.setOnCheckedChangeListener(this);
        binding.nh31104.setOnCheckedChangeListener(this);
        binding.nh31105.setOnCheckedChangeListener(this);
        binding.nh31106.setOnCheckedChangeListener(this);
        binding.nh31107.setOnCheckedChangeListener(this);
        binding.nh31108.setOnCheckedChangeListener(this);
        binding.nh31109.setOnCheckedChangeListener(this);
        binding.nh31110.setOnCheckedChangeListener(this);
        binding.nh31111.setOnCheckedChangeListener(this);
        binding.nh31112.setOnCheckedChangeListener(this);
        binding.nh31113.setOnCheckedChangeListener(this);
        binding.nh31114.setOnCheckedChangeListener(this);
        binding.nh31115.setOnCheckedChangeListener(this);
        binding.nh31116.setOnCheckedChangeListener(this);
        binding.nh31117.setOnCheckedChangeListener(this);
        binding.nh31118.setOnCheckedChangeListener(this);
        binding.nh31119.setOnCheckedChangeListener(this);
        binding.nh312a.setOnCheckedChangeListener(this);
        binding.nh312b.setOnCheckedChangeListener(this);
        binding.nh312c.setOnCheckedChangeListener(this);
        binding.nh312d.setOnCheckedChangeListener(this);
        binding.nh312e.setOnCheckedChangeListener(this);
        binding.nh312f.setOnCheckedChangeListener(this);
        binding.nh312g.setOnCheckedChangeListener(this);
        binding.nh312h.setOnCheckedChangeListener(this);
        binding.nh312i.setOnCheckedChangeListener(this);
        binding.nh314.setOnCheckedChangeListener(this);


        binding.nh315.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (binding.nh315a.isChecked()) {
                    ClearClass.ClearAllFields(binding.fldGrpnh316, true);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpnh316, false);
                }
            }
        });

        binding.nh316.setOnCheckedChangeListener(this);
        binding.nh317.setOnCheckedChangeListener(this);


//        Validation Boolean
        MainApp.validateFlag = false;

        if (SectionA1Activity.editFormFlag) {
            autoPopulate();
        }

    }

    private void autoPopulate() {
        FormsContract formContract = db.getsA4();

        if (!formContract.getsA4().equals("")) {

            JSONA4ModelClass jsonA4 = JSONUtilClass.getModelFromJSON(formContract.getsA4(), JSONA4ModelClass.class);

            if (!jsonA4.getnh301().equals("0")) {
                binding.nh301.check(
                        jsonA4.getnh301().equals("1") ? binding.nh301a.getId() :
                                jsonA4.getnh301().equals("2") ? binding.nh301b.getId() :
                                        jsonA4.getnh301().equals("3") ? binding.nh301c.getId() :
                                                jsonA4.getnh301().equals("4") ? binding.nh301d.getId() :
                                                        jsonA4.getnh301().equals("5") ? binding.nh301e.getId() :
                                                                jsonA4.getnh301().equals("6") ? binding.nh301f.getId() :
                                                                        jsonA4.getnh301().equals("7") ? binding.nh301g.getId() :
                                                                                binding.nh30196.getId()
                );
            }
            binding.nh30196x.setText(jsonA4.getnh30196x());
            if (!jsonA4.getnh302().equals("0")) {
                binding.nh302.check(
                        jsonA4.getnh302().equals("1") ? binding.nh302a.getId() :
                                jsonA4.getnh302().equals("2") ? binding.nh302b.getId() :
                                        binding.nh30296.getId()
                );
            }
            binding.nh30296x.setText(jsonA4.getnh30296x());
            if (!jsonA4.getnh303a().equals("0")) {
                binding.nh303a.check(
                        jsonA4.getnh303a().equals("1") ? binding.nh303a2.getId() :
                                binding.nh303a2.getId()
                );
            }
            if (!jsonA4.getnh303().equals("0")) {
                binding.nh303.check(
                        jsonA4.getnh303().equals("1") ? binding.nh303b.getId() :
                                jsonA4.getnh303().equals("2") ? binding.nh303c.getId() :
                                        jsonA4.getnh303().equals("3") ? binding.nh303d.getId() :
                                                jsonA4.getnh303().equals("4") ? binding.nh303e.getId() :
                                                        jsonA4.getnh303().equals("5") ? binding.nh303f.getId() :
                                                                jsonA4.getnh303().equals("6") ? binding.nh303g.getId() :
                                                                        jsonA4.getnh303().equals("7") ? binding.nh303h.getId() :
                                                                                jsonA4.getnh303().equals("8") ? binding.nh303i.getId() :
                                                                                        jsonA4.getnh303().equals("9") ? binding.nh303j.getId() :
                                                                                                jsonA4.getnh303().equals("10") ? binding.nh303k.getId() :
                                                                                                        jsonA4.getnh303().equals("11") ? binding.nh303l.getId() :
                                                                                                                jsonA4.getnh303().equals("12") ? binding.nh303m.getId() :
                                                                                                                        jsonA4.getnh303().equals("13") ? binding.nh303n.getId() :
                                                                                                                                jsonA4.getnh303().equals("14") ? binding.nh303o.getId() :
                                                                                                                                        jsonA4.getnh303().equals("15") ? binding.nh303p.getId() :
                                                                                                                                                jsonA4.getnh303().equals("16") ? binding.nh303q.getId() :
                                                                                                                                                        binding.nh30396.getId()
                );
                binding.nh30396x.setText(jsonA4.getnh30396x());
            }


            if (jsonA4.getnh304().equals("000")) {
                binding.nh30499.setChecked(true);
            } else if (jsonA4.getnh304().equals("998")) {
                binding.nh30498.setChecked(true);
            } else {
                binding.nh304.setText(jsonA4.getnh304());
            }


            if (!jsonA4.getnh305().equals("0")) {
                binding.nh305.check(
                        jsonA4.getnh305().equals("1") ? binding.nh305a.getId() :
                                binding.nh305b.getId()
                );
            }
            if (!jsonA4.getnh306().equals("0")) {
                binding.nh306.check(
                        jsonA4.getnh306().equals("1") ? binding.nh306a.getId() :
                                jsonA4.getnh306().equals("2") ? binding.nh306b.getId() :
                                        jsonA4.getnh306().equals("3") ? binding.nh306c.getId() :
                                                jsonA4.getnh306().equals("4") ? binding.nh306d.getId() :
                                                        jsonA4.getnh306().equals("5") ? binding.nh306e.getId() :
                                                                jsonA4.getnh306().equals("6") ? binding.nh306f.getId() :
                                                                        binding.nh30696.getId()
                );
            }
            binding.nh30696x.setText(jsonA4.getnh30696x());
            if (!jsonA4.getnh307().equals("0")) {
                binding.nh307.check(
                        jsonA4.getnh307().equals("1") ? binding.nh307a.getId() :
                                jsonA4.getnh307().equals("2") ? binding.nh307b.getId() :
                                        jsonA4.getnh307().equals("3") ? binding.nh307c.getId() :
                                                jsonA4.getnh307().equals("4") ? binding.nh307d.getId() :
                                                        jsonA4.getnh307().equals("5") ? binding.nh307e.getId() :
                                                                jsonA4.getnh307().equals("6") ? binding.nh307f.getId() :
                                                                        jsonA4.getnh307().equals("7") ? binding.nh307g.getId() :
                                                                                jsonA4.getnh307().equals("8") ? binding.nh307h.getId() :
                                                                                        jsonA4.getnh307().equals("9") ? binding.nh307i.getId() :
                                                                                                binding.nh30796.getId()
                );
            }
            binding.nh30796x.setText(jsonA4.getnh30796x());
            if (!jsonA4.getnh308().equals("0")) {
                binding.nh308.check(
                        jsonA4.getnh308().equals("1") ? binding.nh308a.getId() :
                                binding.nh308b.getId()
                );
            }
            binding.nh309.setText(jsonA4.getnh309());

            if (!jsonA4.getnh310().equals("0")) {
                binding.nh310.check(
                        jsonA4.getnh310().equals("1") ? binding.nh310a.getId() :
                                jsonA4.getnh310().equals("2") ? binding.nh310b.getId() :
                                        jsonA4.getnh310().equals("3") ? binding.nh310c.getId() :
                                                jsonA4.getnh310().equals("4") ? binding.nh310d.getId() :
                                                        jsonA4.getnh310().equals("5") ? binding.nh310e.getId() :
                                                                jsonA4.getnh310().equals("6") ? binding.nh310f.getId() :
                                                                        jsonA4.getnh310().equals("7") ? binding.nh310g.getId() :
                                                                                binding.nh31096.getId()
                );
            }
            binding.nh31096x.setText(jsonA4.getnh31096x());
            if (!jsonA4.getnh31101().equals("0")) {
                binding.nh31101.check(
                        jsonA4.getnh31101().equals("1") ? binding.nh31101a.getId() :
                                binding.nh31101b.getId()
                );
            }
            if (!jsonA4.getnh31101().equals("0")) {
                binding.nh31101.check(
                        jsonA4.getnh31101().equals("1") ? binding.nh31101a.getId() :
                                binding.nh31101b.getId()
                );
            }
            if (!jsonA4.getnh31102().equals("0")) {
                binding.nh31102.check(
                        jsonA4.getnh31102().equals("1") ? binding.nh31102a.getId() :
                                binding.nh31102b.getId()
                );
            }
            if (!jsonA4.getnh31103().equals("0")) {
                binding.nh31103.check(
                        jsonA4.getnh31103().equals("1") ? binding.nh31103a.getId() :
                                binding.nh31103b.getId()
                );
            }
            if (!jsonA4.getnh31104().equals("0")) {
                binding.nh31104.check(
                        jsonA4.getnh31104().equals("1") ? binding.nh31104a.getId() :
                                binding.nh31104b.getId()
                );
            }
            if (!jsonA4.getnh31105().equals("0")) {
                binding.nh31105.check(
                        jsonA4.getnh31105().equals("1") ? binding.nh31105a.getId() :
                                binding.nh31105b.getId()
                );
            }
            if (!jsonA4.getnh31106().equals("0")) {
                binding.nh31106.check(
                        jsonA4.getnh31106().equals("1") ? binding.nh31106a.getId() :
                                binding.nh31106b.getId()
                );
            }
            if (!jsonA4.getnh31107().equals("0")) {
                binding.nh31107.check(
                        jsonA4.getnh31107().equals("1") ? binding.nh31107a.getId() :
                                binding.nh31107b.getId()
                );
            }
            if (!jsonA4.getnh31108().equals("0")) {
                binding.nh31108.check(
                        jsonA4.getnh31108().equals("1") ? binding.nh31108a.getId() :
                                binding.nh31108b.getId()
                );
            }
            if (!jsonA4.getnh31109().equals("0")) {
                binding.nh31109.check(
                        jsonA4.getnh31109().equals("1") ? binding.nh31109a.getId() :
                                binding.nh31109b.getId()
                );
            }
            if (!jsonA4.getnh31110().equals("0")) {
                binding.nh31110.check(
                        jsonA4.getnh31110().equals("1") ? binding.nh31110a.getId() :
                                binding.nh31110b.getId()
                );
            }
            if (!jsonA4.getnh31111().equals("0")) {
                binding.nh31111.check(
                        jsonA4.getnh31111().equals("1") ? binding.nh31111a.getId() :
                                binding.nh31111b.getId()
                );
            }
            if (!jsonA4.getnh31112().equals("0")) {
                binding.nh31112.check(
                        jsonA4.getnh31112().equals("1") ? binding.nh31112a.getId() :
                                binding.nh31112b.getId()
                );
            }
            if (!jsonA4.getnh31113().equals("0")) {
                binding.nh31113.check(
                        jsonA4.getnh31113().equals("1") ? binding.nh31113a.getId() :
                                binding.nh31113b.getId()
                );
            }
            if (!jsonA4.getnh31114().equals("0")) {
                binding.nh31114.check(
                        jsonA4.getnh31114().equals("1") ? binding.nh31114a.getId() :
                                binding.nh31114b.getId()
                );
            }
            if (!jsonA4.getnh31115().equals("0")) {
                binding.nh31115.check(
                        jsonA4.getnh31115().equals("1") ? binding.nh31115a.getId() :
                                binding.nh31115b.getId()
                );
            }
            if (!jsonA4.getnh31116().equals("0")) {
                binding.nh31116.check(
                        jsonA4.getnh31116().equals("1") ? binding.nh31116a.getId() :
                                binding.nh31116b.getId()
                );
            }
            if (!jsonA4.getnh31117().equals("0")) {
                binding.nh31117.check(
                        jsonA4.getnh31117().equals("1") ? binding.nh31117a.getId() :
                                binding.nh31117b.getId()
                );
            }
            if (!jsonA4.getnh31118().equals("0")) {
                binding.nh31118.check(
                        jsonA4.getnh31118().equals("1") ? binding.nh31118a.getId() :
                                binding.nh31118b.getId()
                );
            }
            if (!jsonA4.getnh31119().equals("0")) {
                binding.nh31119.check(
                        jsonA4.getnh31119().equals("1") ? binding.nh31119a.getId() :
                                binding.nh31119b.getId()
                );
            }


            if (!jsonA4.getnh312a().equals("0")) {
                binding.nh312a.check(
                        jsonA4.getnh312a().equals("1") ? binding.nh312a1.getId() :
                                binding.nh312a2.getId()
                );
            }

            if (!jsonA4.getnh312b().equals("0")) {
                binding.nh312b.check(
                        jsonA4.getnh312b().equals("1") ? binding.nh312b1.getId() :
                                binding.nh312b2.getId()
                );
            }
            if (!jsonA4.getnh312c().equals("0")) {
                binding.nh312c.check(
                        jsonA4.getnh312c().equals("1") ? binding.nh312c1.getId() :
                                binding.nh312c2.getId()
                );
            }
            if (!jsonA4.getnh312d().equals("0")) {
                binding.nh312d.check(
                        jsonA4.getnh312d().equals("1") ? binding.nh312d1.getId() :
                                binding.nh312d2.getId()
                );
            }
            if (!jsonA4.getnh312e().equals("0")) {
                binding.nh312e.check(
                        jsonA4.getnh312e().equals("1") ? binding.nh312e1.getId() :
                                binding.nh312e2.getId()
                );
            }
            if (!jsonA4.getnh312f().equals("0")) {
                binding.nh312f.check(
                        jsonA4.getnh312f().equals("1") ? binding.nh312f1.getId() :
                                binding.nh312f2.getId()
                );
            }
            if (!jsonA4.getnh312g().equals("0")) {
                binding.nh312g.check(
                        jsonA4.getnh312g().equals("1") ? binding.nh312g1.getId() :
                                binding.nh312g2.getId()
                );
            }
            if (!jsonA4.getnh312h().equals("0")) {
                binding.nh312h.check(
                        jsonA4.getnh312h().equals("1") ? binding.nh312h1.getId() :
                                binding.nh312h2.getId()
                );
            }
            if (!jsonA4.getnh312i().equals("0")) {
                binding.nh312i.check(
                        jsonA4.getnh312i().equals("1") ? binding.nh312i1.getId() :
                                binding.nh312i2.getId()
                );
            }
            if (!jsonA4.getnh313a().equals("0")) {
                binding.nh313a.setChecked(true);
            }
            if (!jsonA4.getnh313b().equals("0")) {
                binding.nh313b.setChecked(true);
            }
            if (!jsonA4.getnh313c().equals("0")) {
                binding.nh313c.setChecked(true);
            }
            if (!jsonA4.getnh313d().equals("0")) {
                binding.nh313d.setChecked(true);
            }
            if (!jsonA4.getnh313e().equals("0")) {
                binding.nh313e.setChecked(true);
            }
            if (!jsonA4.getnh313f().equals("0")) {
                binding.nh313f.setChecked(true);
            }
            if (!jsonA4.getnh31396().equals("0")) {
                binding.nh31396.setChecked(true);
            }
            binding.nh31396x.setText(jsonA4.getnh31396x());


            if (!jsonA4.getnh314().equals("0")) {
                binding.nh314.check(
                        jsonA4.getnh314().equals("1") ? binding.nh314a.getId() :
                                jsonA4.getnh314().equals("2") ? binding.nh314b.getId() :
                                        jsonA4.getnh314().equals("3") ? binding.nh314c.getId() :
                                                jsonA4.getnh314().equals("4") ? binding.nh314d.getId() :
                                                        jsonA4.getnh314().equals("5") ? binding.nh314e.getId() :
                                                                jsonA4.getnh314().equals("6") ? binding.nh314f.getId() :
                                                                        jsonA4.getnh314().equals("7") ? binding.nh314g.getId() :
                                                                                jsonA4.getnh314().equals("8") ? binding.nh314h.getId() :
                                                                                        jsonA4.getnh314().equals("9") ? binding.nh314i.getId() :
                                                                                                jsonA4.getnh314().equals("10") ? binding.nh314j.getId() :
                                                                                                        jsonA4.getnh314().equals("11") ? binding.nh314k.getId() :

                                                                                                                binding.nh31496.getId()
                );
            }
            binding.nh31496x.setText(jsonA4.getnh31496x());
            if (!jsonA4.getnh315().equals("0")) {
                binding.nh315.check(
                        jsonA4.getnh315().equals("1") ? binding.nh315a.getId() :
                                jsonA4.getnh315().equals("2") ? binding.nh315b.getId() :
                                        jsonA4.getnh315().equals("3") ? binding.nh315c.getId() :
                                                binding.nh31496.getId()
                );
            }
            binding.nh31596x.setText(jsonA4.getnh31596x());
            if (!jsonA4.getnh316().equals("0")) {
                binding.nh316.check(
                        jsonA4.getnh316().equals("1") ? binding.nh316a.getId() : binding.nh316b.getId()
                );
            }
            if (!jsonA4.getnh317().equals("0")) {
                binding.nh317.check(
                        jsonA4.getnh317().equals("1") ? binding.nh317a.getId() :
                                jsonA4.getnh317().equals("2") ? binding.nh317b.getId() :
                                        jsonA4.getnh317().equals("3") ? binding.nh317c.getId() :
                                                jsonA4.getnh317().equals("4") ? binding.nh317c.getId() :
                                                        jsonA4.getnh317().equals("5") ? binding.nh317c.getId() :
                                                                jsonA4.getnh317().equals("6") ? binding.nh317c.getId() :
                                                                        jsonA4.getnh317().equals("7") ? binding.nh317c.getId() :
                                                                                jsonA4.getnh317().equals("8") ? binding.nh317c.getId() :
                                                                                        jsonA4.getnh317().equals("9") ? binding.nh317c.getId() :
                                                                                                jsonA4.getnh317().equals("10") ? binding.nh317c.getId() :
                                                                                                        jsonA4.getnh317().equals("11") ? binding.nh317c.getId() :
                                                                                                                binding.nh31796.getId()
                );
            }
            binding.nh31796x.setText(jsonA4.getnh31796x());


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
                //Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();

                finish();

                startActivity(new Intent(this, SectionA5Activity.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

        //startActivity(new Intent(this, SectionA5Activity.class));
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
        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh301, binding.nh301a, getString(R.string.nh301))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh301, binding.nh30196, binding.nh30196x, getString(R.string.nh301))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh302, binding.nh302a, getString(R.string.nh302))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh302, binding.nh30296, binding.nh30296x, getString(R.string.nh302))) {
            return false;
        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.nh303a, binding.nh303a1, getString(R.string.nh303a))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh303, binding.nh303b, getString(R.string.nh303))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh303, binding.nh30396, binding.nh30396x, getString(R.string.nh303))) {
            return false;
        }
        if (!binding.nh303b.isChecked() && !binding.nh303c.isChecked()) {

            if (!binding.nh30498.isChecked() && !binding.nh30499.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.nh304, getString(R.string.nh304))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.nh304, 1, 999, getString(R.string.nh304), "minutes")) {
                    return false;
                }
            } else {
                if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrnh304check, binding.nh30499, getString(R.string.nh304))) {
                    return false;
                }
            }
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh305, binding.nh305b, getString(R.string.nh305))) {
            return false;
        }
        if (binding.nh305a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.nh306, binding.nh306a, getString(R.string.nh306))) {
                return false;
            }
            if (!ValidatorClass.EmptyRadioButton(this, binding.nh306, binding.nh30696, binding.nh30696x, getString(R.string.nh306))) {
                return false;
            }
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh307, binding.nh307a, getString(R.string.nh307))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh307, binding.nh30796, binding.nh30796x, getString(R.string.nh307))) {
            return false;
        }

        if (!binding.nh307h.isChecked() && !binding.nh307i.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.nh308, binding.nh308b, getString(R.string.nh308))) {
                return false;
            }

            if (binding.nh308a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, binding.nh309, getString(R.string.nh309))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, binding.nh309, 1, 99, getString(R.string.nh309), "Toilet")) {
                    return false;
                }
            }
        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.nh310, binding.nh310a, getString(R.string.nh310))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh310, binding.nh31096, binding.nh31096x, getString(R.string.nh310))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31101, binding.nh31101b, getString(R.string.nh31101))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31102, binding.nh31102b, getString(R.string.nh31102))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31103, binding.nh31103b, getString(R.string.nh31103))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31104, binding.nh31104b, getString(R.string.nh31104))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31105, binding.nh31105b, getString(R.string.nh31105))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31106, binding.nh31106b, getString(R.string.nh31106))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31107, binding.nh31107b, getString(R.string.nh31107))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31108, binding.nh31108b, getString(R.string.nh31108))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31109, binding.nh31109b, getString(R.string.nh31109))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31110, binding.nh31110b, getString(R.string.nh31110))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31111, binding.nh31111b, getString(R.string.nh31111))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31112, binding.nh31112b, getString(R.string.nh31112))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31113, binding.nh31113b, getString(R.string.nh31113))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31114, binding.nh31114b, getString(R.string.nh31114))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31115, binding.nh31115b, getString(R.string.nh31115))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31116, binding.nh31116b, getString(R.string.nh31116))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31117, binding.nh31117b, getString(R.string.nh31117))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31118, binding.nh31118b, getString(R.string.nh31118))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh31119, binding.nh31119b, getString(R.string.nh31119))) {
            return false;
        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.nh312a, binding.nh312a1, getString(R.string.nh312a))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh312b, binding.nh312b1, getString(R.string.nh312b))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh312c, binding.nh312c1, getString(R.string.nh312c))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh312d, binding.nh312d1, getString(R.string.nh312d))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh312e, binding.nh312e1, getString(R.string.nh312e))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh312f, binding.nh312f1, getString(R.string.nh312f))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh312g, binding.nh312g1, getString(R.string.nh312g))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh312h, binding.nh312h1, getString(R.string.nh312h))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh312i, binding.nh312i1, getString(R.string.nh312i))) {
            return false;
        }

        if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpna0413check, binding.nh313a, getString(R.string.nh313))) {
            return false;
        }
        if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpna0413check, binding.nh31396, binding.nh31396x, getString(R.string.nh313))) {
            return false;
        }


        if (!ValidatorClass.EmptyRadioButton(this, binding.nh314, binding.nh314a, getString(R.string.nh314))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.nh314, binding.nh31496, binding.nh31496x, getString(R.string.nh314))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh315, binding.nh315a, getString(R.string.nh315))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh315, binding.nh31596, binding.nh31596x, getString(R.string.nh315))) {
            return false;
        }
        if (binding.nh315a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.nh316, binding.nh316a, getString(R.string.nh316))) {
                return false;
            }
        }
        // 315

        if (!ValidatorClass.EmptyRadioButton(this, binding.nh317, binding.nh317a, getString(R.string.nh317))) {
            return false;
        }


        return ValidatorClass.EmptyRadioButton(this, binding.nh317, binding.nh31796, binding.nh31796x, getString(R.string.nh317));

    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();


        JSONObject sA4 = new JSONObject();
        if (SectionA1Activity.editFormFlag) {
            sA4.put("edit_updatedate_sa4", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }
        sA4.put("nh301", binding.nh301a.isChecked() ? "1"
                : binding.nh301b.isChecked() ? "2"
                : binding.nh301c.isChecked() ? "3"
                : binding.nh301d.isChecked() ? "4"
                : binding.nh301e.isChecked() ? "5"
                : binding.nh301f.isChecked() ? "6"
                : binding.nh301g.isChecked() ? "7"
                : binding.nh30196.isChecked() ? "96"
                : "0");
        sA4.put("nh30196x", binding.nh30196x.getText().toString());

        sA4.put("nh302", binding.nh302a.isChecked() ? "1"
                : binding.nh302b.isChecked() ? "2"
                : binding.nh30296.isChecked() ? "96" : "0");
        sA4.put("nh30296x", binding.nh30296x.getText().toString());

        sA4.put("nh303a", binding.nh303a1.isChecked() ? "1"
                : binding.nh303a2.isChecked() ? "2"
                : "0");

        sA4.put("nh303", binding.nh303b.isChecked() ? "1"
                : binding.nh303c.isChecked() ? "2"
                : binding.nh303d.isChecked() ? "3"
                : binding.nh303e.isChecked() ? "4"
                : binding.nh303f.isChecked() ? "5"
                : binding.nh303g.isChecked() ? "6"
                : binding.nh303h.isChecked() ? "7"
                : binding.nh303i.isChecked() ? "8"
                : binding.nh303j.isChecked() ? "9"
                : binding.nh303k.isChecked() ? "10"
                : binding.nh303l.isChecked() ? "11"
                : binding.nh303m.isChecked() ? "12"
                : binding.nh303n.isChecked() ? "13"
                : binding.nh303o.isChecked() ? "14"
                : binding.nh303p.isChecked() ? "15"
                : binding.nh303q.isChecked() ? "16"
                : binding.nh30396.isChecked() ? "96"
                : "0");
        sA4.put("nh30396x", binding.nh30396x.getText().toString());

        sA4.put("nh304", binding.nh30498.isChecked() ? "998" : binding.nh30499.isChecked() ? "000" : binding.nh304.getText().toString());

        sA4.put("nh305", binding.nh305a.isChecked() ? "1"
                : binding.nh305b.isChecked() ? "2"
                : "0");

        sA4.put("nh306", binding.nh306a.isChecked() ? "1"
                : binding.nh306b.isChecked() ? "2"
                : binding.nh306c.isChecked() ? "3"
                : binding.nh306d.isChecked() ? "4"
                : binding.nh306e.isChecked() ? "5"
                : binding.nh306f.isChecked() ? "6"
                : binding.nh30696.isChecked() ? "96"
                : "0");
        sA4.put("nh30696x", binding.nh30696x.getText().toString());


        sA4.put("nh307", binding.nh307a.isChecked() ? "1"
                : binding.nh307b.isChecked() ? "2"
                : binding.nh307c.isChecked() ? "3"
                : binding.nh307d.isChecked() ? "4"
                : binding.nh307e.isChecked() ? "5"
                : binding.nh307f.isChecked() ? "6"
                : binding.nh307g.isChecked() ? "7"
                : binding.nh307h.isChecked() ? "8"
                : binding.nh307i.isChecked() ? "9"
                : binding.nh30796.isChecked() ? "96"
                : "0");

        sA4.put("nh30796x", binding.nh30796x.getText().toString());

        sA4.put("nh308", binding.nh308a.isChecked() ? "1"
                : binding.nh308b.isChecked() ? "2"
                : "0");

        sA4.put("nh309", binding.nh309.getText().toString());
//        nh310
        sA4.put("nh310", binding.nh310a.isChecked() ? "1"
                : binding.nh310b.isChecked() ? "2"
                : binding.nh310c.isChecked() ? "3"
                : binding.nh310d.isChecked() ? "4"
                : binding.nh310e.isChecked() ? "5"
                : binding.nh310f.isChecked() ? "6"
                : binding.nh310g.isChecked() ? "7"
                : binding.nh31096.isChecked() ? "96"
                : "0");
        sA4.put("nh31096x", binding.nh31096x.getText().toString());
//        nh311
        sA4.put("nh31101", binding.nh31101a.isChecked() ? "1"
                : binding.nh31101b.isChecked() ? "2"
                : "0");

        sA4.put("nh31102", binding.nh31102a.isChecked() ? "1"
                : binding.nh31102b.isChecked() ? "2"
                : "0");

        sA4.put("nh31103", binding.nh31103a.isChecked() ? "1"
                : binding.nh31103b.isChecked() ? "2"
                : "0");

        sA4.put("nh31104", binding.nh31104a.isChecked() ? "1"
                : binding.nh31104b.isChecked() ? "2"
                : "0");

        sA4.put("nh31105", binding.nh31105a.isChecked() ? "1"
                : binding.nh31105b.isChecked() ? "2"
                : "0");
        sA4.put("nh31106", binding.nh31106a.isChecked() ? "1"
                : binding.nh31106b.isChecked() ? "2"
                : "0");
        sA4.put("nh31107", binding.nh31107a.isChecked() ? "1"
                : binding.nh31107b.isChecked() ? "2"
                : "0");
        sA4.put("nh31108", binding.nh31108a.isChecked() ? "1"
                : binding.nh31108b.isChecked() ? "2"
                : "0");
        sA4.put("nh31109", binding.nh31109a.isChecked() ? "1"
                : binding.nh31109b.isChecked() ? "2"
                : "0");
        sA4.put("nh31110", binding.nh31110a.isChecked() ? "1"
                : binding.nh31110b.isChecked() ? "2"
                : "0");
        sA4.put("nh31111", binding.nh31111a.isChecked() ? "1"
                : binding.nh31111b.isChecked() ? "2"
                : "0");
        sA4.put("nh31112", binding.nh31112a.isChecked() ? "1"
                : binding.nh31112b.isChecked() ? "2"
                : "0");
        sA4.put("nh31113", binding.nh31113a.isChecked() ? "1"
                : binding.nh31113b.isChecked() ? "2"
                : "0");
        sA4.put("nh31114", binding.nh31114a.isChecked() ? "1"
                : binding.nh31114b.isChecked() ? "2"
                : "0");
        sA4.put("nh31115", binding.nh31115a.isChecked() ? "1"
                : binding.nh31115b.isChecked() ? "2"
                : "0");
        sA4.put("nh31116", binding.nh31116a.isChecked() ? "1"
                : binding.nh31116b.isChecked() ? "2"
                : "0");
        sA4.put("nh31117", binding.nh31117a.isChecked() ? "1"
                : binding.nh31117b.isChecked() ? "2"
                : "0");
        sA4.put("nh31118", binding.nh31118a.isChecked() ? "1"
                : binding.nh31118b.isChecked() ? "2"
                : "0");
        sA4.put("nh31119", binding.nh31119a.isChecked() ? "1"
                : binding.nh31119b.isChecked() ? "2"
                : "0");

//        nh312
        sA4.put("nh312a", binding.nh312a1.isChecked() ? "1" : binding.nh312a2.isChecked() ? "2" : "0");
        sA4.put("nh312b", binding.nh312b1.isChecked() ? "1" : binding.nh312b2.isChecked() ? "2" : "0");
        sA4.put("nh312c", binding.nh312c1.isChecked() ? "1" : binding.nh312c2.isChecked() ? "2" : "0");
        sA4.put("nh312d", binding.nh312d1.isChecked() ? "1" : binding.nh312d2.isChecked() ? "2" : "0");
        sA4.put("nh312e", binding.nh312e1.isChecked() ? "1" : binding.nh312e2.isChecked() ? "2" : "0");
        sA4.put("nh312f", binding.nh312f1.isChecked() ? "1" : binding.nh312f2.isChecked() ? "2" : "0");
        sA4.put("nh312g", binding.nh312g1.isChecked() ? "1" : binding.nh312g2.isChecked() ? "2" : "0");
        sA4.put("nh312h", binding.nh312h1.isChecked() ? "1" : binding.nh312h2.isChecked() ? "2" : "0");
        sA4.put("nh312i", binding.nh312i1.isChecked() ? "1" : binding.nh312i2.isChecked() ? "2" : "0");

//        nh313
        sA4.put("nh313a", binding.nh313a.isChecked() ? "1" : "0");
        sA4.put("nh313b", binding.nh313b.isChecked() ? "2" : "0");
        sA4.put("nh313c", binding.nh313c.isChecked() ? "3" : "0");
        sA4.put("nh313d", binding.nh313d.isChecked() ? "4" : "0");
        sA4.put("nh313e", binding.nh313e.isChecked() ? "5" : "0");
        sA4.put("nh313f", binding.nh313f.isChecked() ? "6" : "0");
        sA4.put("nh31396", binding.nh31396.isChecked() ? "96" : "0");
        sA4.put("nh31396x", binding.nh31396x.getText().toString());

//        nh314
        sA4.put("nh314", binding.nh314a.isChecked() ? "1"
                : binding.nh314b.isChecked() ? "2"
                : binding.nh314c.isChecked() ? "3"
                : binding.nh314d.isChecked() ? "4"
                : binding.nh314e.isChecked() ? "5"
                : binding.nh314f.isChecked() ? "6"
                : binding.nh314g.isChecked() ? "7"
                : binding.nh314h.isChecked() ? "8"
                : binding.nh314i.isChecked() ? "9"
                : binding.nh314j.isChecked() ? "10"
                : binding.nh314k.isChecked() ? "11"
                : binding.nh31496.isChecked() ? "96"
                : "0");
        sA4.put("nh31496x", binding.nh31496x.getText().toString());
//       nh315
        sA4.put("nh315", binding.nh315a.isChecked() ? "1"
                : binding.nh315b.isChecked() ? "2"
                : binding.nh315c.isChecked() ? "3"
                : binding.nh31596.isChecked() ? "96"
                : "0");
        sA4.put("nh31596x", binding.nh31596x.getText().toString());
//        nh316
        sA4.put("nh316", binding.nh316a.isChecked() ? "1"
                : binding.nh316b.isChecked() ? "2"
                : "0");

//        nh317
        sA4.put("nh317", binding.nh317a.isChecked() ? "1"
                : binding.nh317b.isChecked() ? "2"
                : binding.nh317c.isChecked() ? "3"
                : binding.nh317d.isChecked() ? "4"
                : binding.nh317e.isChecked() ? "5"
                : binding.nh317f.isChecked() ? "6"
                : binding.nh317g.isChecked() ? "7"
                : binding.nh317h.isChecked() ? "8"
                : binding.nh317i.isChecked() ? "9"
                : binding.nh317j.isChecked() ? "10"
                : binding.nh317k.isChecked() ? "11"
                : binding.nh31796.isChecked() ? "96"
                : "0");
        sA4.put("nh31796x", binding.nh31796x.getText().toString());

//        nh318


        MainApp.fc.setsA4(String.valueOf(sA4));


        //Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSA4();

        if (updcount == 1) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        formValidation();
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

   /* @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        formValidation();
    }*/
}
