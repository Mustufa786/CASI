package edu.aku.hassannaqvi.casi_2018.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import butterknife.BindViews;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONA2ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionA2EditBinding;
import edu.aku.hassannaqvi.casi_2018.other.DateUtils;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.validation.validatorClass;


public class SectionA2EditActivity extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private final long DELAY = 1000;
    private final long DELAY1 = 2000;
    ActivitySectionA2EditBinding binding;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    Map<Integer, Map<Integer, Integer>> mem;
    List<String> mothersList, fathersList;
    List<String> mothersSerials, fathersSerials;
    Map<String, String> mothersMap, fathersMap;
    int position = 0;
    int Age = 0;
    long agebyDob = 0;
    long ageinMonths = 0;
    Boolean flag = false;
    Calendar dob = Calendar.getInstance();
    public TextWatcher age = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!binding.nh2dobd.getText().toString().isEmpty() && !binding.nh2dobm.getText().toString().isEmpty() && !binding.nh2doby.getText().toString().isEmpty()) {

                if (!binding.nh2dobd.getText().toString().equals("98") && !binding.nh2dobm.getText().toString().equals("98")
                        && !binding.nh2doby.getText().toString().equals("9998")) {

                    dob = DateUtils.getCalendarDate(binding.nh2dobd.getText().toString(), binding.nh2dobm.getText().toString(),
                            binding.nh2doby.getText().toString());

                    agebyDob = DateUtils.ageInYearByDOB(dob);
                    ageinMonths = DateUtils.ageInMonthsByDOB(dob);

                    binding.nh2agey.setEnabled(false);
                    binding.nh2agey.setText(String.valueOf(agebyDob));

                } else if (!binding.nh2doby.getText().toString().equals("9998") && !binding.nh2dobm.getText().toString().equals("98")) {

                    dob = DateUtils.getCalendarDate(binding.nh2dobm.getText().toString(),
                            binding.nh2doby.getText().toString());
                    agebyDob = DateUtils.ageInYearByDOB(dob);
                    binding.nh2agey.setEnabled(false);
                    binding.nh2agey.setText(String.valueOf(agebyDob));
                } else if (!binding.nh2doby.getText().toString().equals("9998")) {
                    agebyDob = DateUtils.ageInYearByDOB(binding.nh2doby.getText().toString());
                    binding.nh2agey.setEnabled(false);
                    binding.nh2agey.setText(String.valueOf(agebyDob));
                } else if (binding.nh2doby.getText().toString().equals("9998")) {
                    binding.nh2agey.setEnabled(true);
                    binding.nh2agey.setText(null);
                }


            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };
    //Calendar calDob = Calendar.getInstance();
    @BindViews({R.id.nh2doby, R.id.nh2dobm, R.id.nh2dobd})
    List<EditText> grpdob;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_a2);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_a2_edit);
        ButterKnife.bind(this);
        binding.setCallback(this);
        setupViews();
        skipPattern();
        //setHeading();
        this.setTitle(getResources().getString(R.string.na2subHeading));

//        Validation Boolean
        MainApp.validateFlag = false;

        AutoPopulate();

    }

    public void AutoPopulate() {

        JSONA2ModelClass jsonB2 = JSONUtilClass.getModelFromJSON(MainApp.fmc.getsA2(), JSONA2ModelClass.class);

        binding.na204.check(MainApp.fmc.getna204().equals("1") ? binding.na204a.getId()
                : MainApp.fmc.getna204().equals("2") ? binding.na204b.getId() : binding.na204c.getId());
        binding.na202.setText(MainApp.fmc.getName());
        binding.na203.check(MainApp.fmc.getRealtionHH().equals("1") ? binding.na203a.getId() :
                MainApp.fmc.getRealtionHH().equals("2") ? binding.na203b.getId() :
                        MainApp.fmc.getRealtionHH().equals("3") ? binding.na203c.getId() :
                                MainApp.fmc.getRealtionHH().equals("4") ? binding.na203d.getId() :
                                        MainApp.fmc.getRealtionHH().equals("5") ? binding.na203e.getId() :
                                                MainApp.fmc.getRealtionHH().equals("6") ? binding.na203f.getId() :
                                                        MainApp.fmc.getRealtionHH().equals("7") ? binding.na203g.getId() :
                                                                MainApp.fmc.getRealtionHH().equals("8") ? binding.na203h.getId() :
                                                                        MainApp.fmc.getRealtionHH().equals("9") ? binding.na203i.getId() :
                                                                                MainApp.fmc.getRealtionHH().equals("10") ? binding.na203j.getId() :
                                                                                        MainApp.fmc.getRealtionHH().equals("11") ? binding.na203k.getId() :
                                                                                                MainApp.fmc.getRealtionHH().equals("12") ? binding.na203l.getId() :
                                                                                                        MainApp.fmc.getRealtionHH().equals("13") ? binding.na203m.getId() :
                                                                                                                MainApp.fmc.getRealtionHH().equals("98") ? binding.na20398.getId() :
                                                                                                                        binding.na20396.getId());

        binding.nh2doby.setText(jsonB2.getnh2doby());
        binding.nh2dobm.setText(jsonB2.getnh2dobm());
        binding.nh2dobd.setText(jsonB2.getnh2dobd());

        binding.nh2agey.setText(jsonB2.getnh206y());

        if (!jsonB2.getnh207().equals("0")) {
            binding.nh2ms.check(
                    jsonB2.getnh207().equals("1") ? binding.nh2msa.getId() :
                            jsonB2.getnh207().equals("2") ? binding.nh2msb.getId() :
                                    jsonB2.getnh207().equals("3") ? binding.nh2msc.getId() :
                                            jsonB2.getnh207().equals("4") ? binding.nh2msd.getId() :
                                                    binding.nh2mse.getId());
        }

        if (!jsonB2.getnh208().equals("0")) {
            binding.nh2edu.check(
                    jsonB2.getnh208().equals("1") ? binding.nh2edua.getId() :
                            jsonB2.getnh208().equals("2") ? binding.nh2edub.getId() :
                                    jsonB2.getnh208().equals("3") ? binding.nh2educ.getId() :
                                            jsonB2.getnh208().equals("4") ? binding.nh2edud.getId() :
                                                    jsonB2.getnh208().equals("5") ? binding.nh2edue.getId() :
                                                            jsonB2.getnh208().equals("6") ? binding.nh2eduf.getId() :
                                                                    jsonB2.getnh208().equals("7") ? binding.nh2edug.getId() :
                                                                            jsonB2.getnh208().equals("8") ? binding.nh2eduh.getId() :
                                                                                    jsonB2.getnh208().endsWith("9") ? binding.nh2edui.getId() :
                                                                                            binding.nh2edu98.getId());
        }

        if (!jsonB2.getnh209().equals("0")) {
            binding.nh2occ.check(
                    jsonB2.getnh209().equals("1") ? binding.nh2occa.getId() :
                            jsonB2.getnh209().equals("2") ? binding.nh2occb.getId() :
                                    jsonB2.getnh209().equals("3") ? binding.nh2occc.getId() :
                                            jsonB2.getnh209().equals("4") ? binding.nh2occd.getId() :
                                                    jsonB2.getnh209().equals("5") ? binding.nh2occe.getId() :
                                                            jsonB2.getnh209().equals("6") ? binding.nh2occf.getId() :
                                                                    jsonB2.getnh209().equals("7") ? binding.nh2occg.getId() :
                                                                            jsonB2.getnh209().equals("8") ? binding.nh2occh.getId() :
                                                                                    jsonB2.getnh209().equals("9") ? binding.nh2occi.getId() :
                                                                                            jsonB2.getnh209().equals("10") ? binding.nh2occj.getId() :
                                                                                                    binding.nh2occ96.getId());

            binding.nh2occ96x.setText(jsonB2.getnh20996x());
        }

        if (!jsonB2.getnh210().equals("0")) {
            binding.nh210.check(
                    jsonB2.getnh210().equals("1") ? binding.nh210a.getId() :
                            binding.nh210b.getId()
            );
        }

        if (!jsonB2.getResp().equals("0")) {
            binding.resp.check(
                    jsonB2.getResp().equals("1") ? binding.respa.getId() :
                            binding.respb.getId()
            );
        }


        /*Functionality Setting*/

        Age = Integer.valueOf(MainApp.fmc.getAge());


//        Calculation
        Map<Integer, Integer> memType = new HashMap<>();

        //Total
        if (MainApp.fmc.getna204().equals("1")) {
            memType.put(1, Integer.valueOf(mem.get(1).get(1).toString()) - 1);
            memType.put(2, Integer.valueOf(mem.get(1).get(2).toString()));
        } else {
            memType.put(2, Integer.valueOf(mem.get(1).get(2).toString()) - 1);
            memType.put(1, Integer.valueOf(mem.get(1).get(1).toString()));
        }

        MainApp.membersCount.setMembers(1, memType);

        //Set availability
        if (jsonB2.getnh210().equals("1")) {
            MainApp.fmc.setAv("");
        }

        //MWRA
        if ((Age >= 15 && Age < 50) && MainApp.fmc.getna204().equals("2")) {
            if (jsonB2.getnh207().equals("5")) {
                MainApp.membersCount.setWra(MainApp.membersCount.getWra() - 1);
                /*if (jsonB2.getnh210().equals("1")) {
                    MainApp.fmc.setAv("");
                }*/
            } else {
                MainApp.membersCount.setMwra(MainApp.membersCount.getMwra() - 1);
                /*if (jsonB2.getnh210().equals("1")) {
                    MainApp.fmc.setAv("");
                }*/

            }
//            MainApp.mwra.add(MainApp.fmc);
            if (jsonB2.getnh210().equals("1")) {
                for (byte i = 0; i < MainApp.mwra.size(); i++) {
                    if (MainApp.mwra.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.mwra.remove(i);
                        break;
                    }
                }
            }
        }

        //Adolescent
        if ((Age >= 10 && Age < 20) && jsonB2.getnh207().equals("5")) {
            memType = new HashMap<>();
            if (MainApp.fmc.getna204().equals("1")) {
                memType.put(1, Integer.valueOf(mem.get(2).get(1).toString()) - 1);
                memType.put(2, Integer.valueOf(mem.get(2).get(2).toString()));
            } else {
                memType.put(2, Integer.valueOf(mem.get(2).get(2).toString()) - 1);
                memType.put(1, Integer.valueOf(mem.get(2).get(1).toString()));
            }
            MainApp.membersCount.setMembers(2, memType);

            // Add data in list

//            MainApp.adolescents.add(MainApp.fmc);
            for (byte i = 0; i < MainApp.adolescents.size(); i++) {
                if (MainApp.adolescents.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                    MainApp.adolescents.remove(i);
                    break;
                }
            }

            /*if (jsonB2.getnh210().equals("1")) {
                MainApp.fmc.setAv("");
            }*/
        }

        //Children < 5
        else if (Age < 6) {
            memType = new HashMap<>();
            if (MainApp.fmc.getna204().equals("1")) {
                memType.put(1, Integer.valueOf(mem.get(3).get(1).toString()) - 1);
                memType.put(2, Integer.valueOf(mem.get(3).get(2).toString()));
            } else {
                memType.put(2, Integer.valueOf(mem.get(3).get(2).toString()) - 1);
                memType.put(1, Integer.valueOf(mem.get(3).get(1).toString()));
            }
            MainApp.membersCount.setMembers(3, memType);
            /*if (jsonB2.getnh210().equals("1")) {
                MainApp.fmc.setAv("");
            }*/

            // Add data in list
            if (Age < 2) {
//                MainApp.childUnder2.add(MainApp.fmc);
                for (byte i = 0; i < MainApp.childUnder2.size(); i++) {
                    if (MainApp.childUnder2.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.childUnder2.remove(i);
                        break;
                    }
                }

//                MainApp.childUnder5.add(MainApp.fmc);
                for (byte i = 0; i < MainApp.childUnder5.size(); i++) {
                    if (MainApp.childUnder5.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.childUnder5.remove(i);
                        break;
                    }
                }

                for (byte i = 0; i < MainApp.childUnder5_Del.size(); i++) {
                    if (MainApp.childUnder5_Del.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.childUnder5_Del.remove(i);
                        break;
                    }
                }

//                MainApp.childUnder2Check.add(MainApp.fmc);
                for (byte i = 0; i < MainApp.childUnder2Check.size(); i++) {
                    if (MainApp.childUnder2Check.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.childUnder2Check.remove(i);
                        break;
                    }
                }

            } else {

//                if (jsonB2.getnh210().equals("1")) {
//                MainApp.childUnder5.add(MainApp.fmc);
                for (byte i = 0; i < MainApp.childUnder5.size(); i++) {
                    if (MainApp.childUnder5.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.childUnder5.remove(i);
                        break;
                    }
                }
//                }

                for (byte i = 0; i < MainApp.childUnder5_Del.size(); i++) {
                    if (MainApp.childUnder5_Del.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.childUnder5_Del.remove(i);
                        break;
                    }
                }

            }

/*            if (Age < 2) {

//                MainApp.childUnder2Check.add(MainApp.fmc);
                for (byte i = 0; i < MainApp.childUnder2Check.size(); i++) {
                    if (MainApp.childUnder2Check.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.childUnder2Check.remove(i);
                        break;
                    }
                }
            }*/

//            if (Age < 6 && MainApp.fmc.getMotherId().equals("00") && jsonB2.getnh210().equals("1")) {
            if (Age < 6 && MainApp.fmc.getMotherId().equals("00")) {
//                MainApp.childNA.add(MainApp.fmc);
                for (byte i = 0; i < MainApp.childNA.size(); i++) {
                    if (MainApp.childNA.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.childNA.remove(i);
                        break;
                    }
                }
            }
        }

        if (Age >= 15) {
            // Add data in list
            if (!jsonB2.getnh207().equals("5")) {
//                MainApp.members_f_m.add(MainApp.fmc);
                for (byte i = 0; i < MainApp.members_f_m.size(); i++) {
                    if (MainApp.members_f_m.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.members_f_m.remove(i);
                        break;
                    }
                }
            }

//            MainApp.respList.add(MainApp.fmc);
            if (jsonB2.getnh210().equals("1")) {
                for (byte i = 0; i < MainApp.respList.size(); i++) {
                    if (MainApp.respList.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.respList.remove(i);
                        break;
                    }
                }
            }
        }

        // Add data in list for all members
        for (byte i = 0; i < MainApp.all_members.size(); i++) {
            if (MainApp.all_members.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                MainApp.all_members.remove(i);
                break;
            }
        }

        /*End*/


//        Visibility for isHead

        if (MainApp.fmc.getRealtionHH().equals("1")) {
            MainApp.IsHead = false;
        } else if (MainApp.IsHead) {
            binding.na203a.setEnabled(false);
        }

        if (MainApp.fmc.getResp().equals("1")) {
            MainApp.IsResp = false;
            binding.fldGrpA20101.setVisibility(View.VISIBLE);
        } else if (MainApp.IsResp) {
            binding.fldGrpA20101.setVisibility(View.GONE);
        } else {
            binding.fldGrpA20101.setVisibility(View.VISIBLE);
        }

        /*if (MainApp.IsHead) {
            binding.na203a.setEnabled(false);
        } else {
            binding.na203a.setEnabled(true);
        }*/
/*
        if (MainApp.IsResp) {
            binding.fldGrpA20101.setVisibility(View.GONE);
        }*/

    }

    public void skipPattern() {

        binding.na202.addTextChangedListener(this);
        binding.na203.setOnCheckedChangeListener(this);
        binding.na204.setOnCheckedChangeListener(this);
        binding.resp.setOnCheckedChangeListener(this);
        //binding.nh2ms.setOnCheckedChangeListener(this);
        binding.nh2edu.setOnCheckedChangeListener(this);
        binding.nh2occ.setOnCheckedChangeListener(this);
        binding.nh210.setOnCheckedChangeListener(this);


    }

    public void setupViews() {

        position = getIntent().getIntExtra("pos", -1);

        MainApp.fmc = (FamilyMembersContract) getIntent().getSerializableExtra("data");

//        Getting Intent
        MainApp.SetNameClass nameSet = new MainApp.SetNameClass(getString(R.string.nh2dob) + " " + MainApp.fmc.getName());
        binding.setName(nameSet);

        binding.txtnh2dob.setText(binding.txtnh2dob.getText().toString().replace("Name", binding.na202.getText().toString()));
        binding.txtna2age.setText(binding.txtna2age.getText().toString().replace("Name", binding.na202.getText().toString()));
        binding.txtnh210.setText(binding.txtnh210.getText().toString().replace("Name", binding.na202.getText().toString()));

        for (EditText ed : grpdob) {
            ed.addTextChangedListener(age);
        }

        binding.nh2agey.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!binding.nh2agey.getText().toString().isEmpty()) {

                    Age = Integer.valueOf(binding.nh2agey.getText().toString());
                    if (Age <= 2) {
                        binding.fldGrpnh2edu.setVisibility(View.GONE);
                        binding.fldGrpnh2ms.setVisibility(View.GONE);
                        binding.fldGrpnh2occ.setVisibility(View.GONE);

                        binding.fldGrpfid.setVisibility(View.VISIBLE);
                        binding.fldGrpmid.setVisibility(View.VISIBLE);

                        binding.nh2edu.clearCheck();
                        binding.nh2ms.clearCheck();
                        binding.nh2occ.clearCheck();

                        binding.nh2edua.setChecked(true);
                        binding.nh2occa.setChecked(true);
                        binding.nh2mse.setChecked(true);

                    } else if (Age > 2 && Age <= 5) {
                        binding.nh2occ.clearCheck();
                        binding.nh2edu.clearCheck();
                        binding.fldGrpnh2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2ms.setVisibility(View.GONE);
                        binding.fldGrpnh2occ.setVisibility(View.VISIBLE);
                        binding.nh2mse.setChecked(true);

                        binding.fldGrpfid.setVisibility(View.VISIBLE);
                        binding.fldGrpmid.setVisibility(View.VISIBLE);

                        binding.nh2edua.setEnabled(true);
                        binding.nh2edub.setEnabled(true);
                        binding.nh2edu98.setEnabled(true);
                        binding.nh2educ.setEnabled(true);
                        binding.nh2edud.setEnabled(false);
                        binding.nh2edud.setChecked(false);
                        binding.nh2edue.setEnabled(false);
                        binding.nh2edue.setChecked(false);
                        binding.nh2eduf.setEnabled(false);
                        binding.nh2eduf.setChecked(false);
                        binding.nh2edug.setEnabled(false);
                        binding.nh2edug.setChecked(false);
                        binding.nh2eduh.setEnabled(false);
                        binding.nh2eduh.setChecked(false);
                        binding.nh2edui.setEnabled(false);
                        binding.nh2edui.setChecked(false);

                        binding.nh2occa.setEnabled(true);
                        binding.nh2occb.setEnabled(true);
                        binding.nh2occc.setEnabled(true);
                        binding.nh2occd.setEnabled(true);
                        binding.nh2occe.setEnabled(true);
                        binding.nh2occg.setEnabled(true);
                        binding.nh2occh.setEnabled(true);
                        binding.nh2occi.setEnabled(true);
                        /*if(MainApp.othergender != 1) {
                            binding.nh2occi.setEnabled(true);
                        }else{
                            binding.nh2occi.setEnabled(false);
                        }*/
                        binding.nh2occ96.setEnabled(true);
                        binding.nh2occ96x.setEnabled(true);

                        binding.nh2occf.setEnabled(false);
                        binding.nh2occf.setChecked(false);
                        binding.nh2occj.setEnabled(false);
                        binding.nh2occj.setChecked(false);

                    } else if (Age > 5 && Age < 10) {
                        binding.nh2occ.clearCheck();
                        binding.nh2edu.clearCheck();
                        binding.nh2ms.clearCheck();
                        binding.fldGrpnh2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2ms.setVisibility(View.GONE);
                        binding.fldGrpnh2occ.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.nh2mse.setChecked(true);

                        binding.nh211.setSelection(1);
                        binding.nh212.setSelection(1);

                        binding.nh2edua.setEnabled(true);
                        binding.nh2edub.setEnabled(true);
                        binding.nh2edu98.setEnabled(true);
                        binding.nh2educ.setEnabled(true);
                        binding.nh2edud.setEnabled(true);
                        binding.nh2edue.setEnabled(false);
                        binding.nh2edue.setChecked(false);
                        binding.nh2eduf.setEnabled(false);
                        binding.nh2eduf.setChecked(false);
                        binding.nh2edug.setEnabled(false);
                        binding.nh2edug.setChecked(false);
                        binding.nh2eduh.setEnabled(false);
                        binding.nh2eduh.setChecked(false);
                        binding.nh2edui.setEnabled(false);
                        binding.nh2edui.setChecked(false);

                        binding.nh2occa.setEnabled(true);
                        binding.nh2occb.setEnabled(true);
                        binding.nh2occc.setEnabled(true);
                        binding.nh2occd.setEnabled(true);
                        binding.nh2occe.setEnabled(true);
                        binding.nh2occg.setEnabled(true);
                        binding.nh2occh.setEnabled(true);
                        binding.nh2occi.setEnabled(true);
                        /*if(MainApp.othergender != 1) {
                            binding.nh2occi.setEnabled(true);
                        }else{
                            binding.nh2occi.setEnabled(false);
                        }*/
                        binding.nh2occ96.setEnabled(true);
                        binding.nh2occ96x.setEnabled(true);

                        binding.nh2occf.setEnabled(false);
                        binding.nh2occf.setChecked(false);
                        binding.nh2occj.setEnabled(false);
                        binding.nh2occj.setChecked(false);

                    } else if (Age >= 10 && Age < 14) {
                        binding.nh2occ.clearCheck();
                        binding.nh2edu.clearCheck();
                        binding.nh2ms.clearCheck();
                        binding.fldGrpnh2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2ms.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2occ.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.nh211.setSelection(1);
                        binding.nh212.setSelection(1);
                        binding.nh2edua.setEnabled(true);
                        binding.nh2edub.setEnabled(true);
                        binding.nh2edu98.setEnabled(true);
                        binding.nh2educ.setEnabled(true);
                        binding.nh2edud.setEnabled(true);
                        binding.nh2edue.setEnabled(true);
                        binding.nh2eduf.setEnabled(false);
                        binding.nh2eduf.setChecked(false);
                        binding.nh2edug.setEnabled(false);
                        binding.nh2edug.setChecked(false);
                        binding.nh2eduh.setEnabled(false);
                        binding.nh2eduh.setChecked(false);
                        binding.nh2edui.setEnabled(false);
                        binding.nh2edui.setChecked(false);

                        binding.nh2occa.setEnabled(true);
                        binding.nh2occb.setEnabled(true);
                        binding.nh2occc.setEnabled(true);
                        binding.nh2occd.setEnabled(true);
                        binding.nh2occe.setEnabled(true);
                        binding.nh2occg.setEnabled(true);
                        binding.nh2occh.setEnabled(true);
                        binding.nh2occi.setEnabled(true);
                        /*if(MainApp.othergender != 1) {
                            binding.nh2occi.setEnabled(true);
                        }else{
                            binding.nh2occi.setEnabled(false);
                        }*/
                        binding.nh2occ96.setEnabled(true);
                        binding.nh2occ96x.setEnabled(true);

                        binding.nh2occf.setEnabled(false);
                        binding.nh2occf.setChecked(false);
                        binding.nh2occj.setEnabled(false);
                        binding.nh2occj.setChecked(false);
                    } else if (Age >= 14 && Age < 17) {
                        binding.nh2occ.clearCheck();
                        binding.nh2edu.clearCheck();
                        binding.nh2ms.clearCheck();
                        binding.fldGrpnh2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2ms.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2occ.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.nh211.setSelection(1);
                        binding.nh212.setSelection(1);

                        binding.nh2edua.setEnabled(true);
                        binding.nh2edub.setEnabled(true);
                        binding.nh2edu98.setEnabled(true);
                        binding.nh2educ.setEnabled(true);
                        binding.nh2edud.setEnabled(true);
                        binding.nh2edue.setEnabled(true);
                        binding.nh2eduf.setEnabled(true);
                        binding.nh2edug.setEnabled(false);
                        binding.nh2edug.setChecked(false);
                        binding.nh2eduh.setEnabled(false);
                        binding.nh2eduh.setChecked(false);
                        binding.nh2edui.setEnabled(false);
                        binding.nh2edui.setChecked(false);

                        binding.nh2occa.setEnabled(true);
                        binding.nh2occb.setEnabled(true);
                        binding.nh2occc.setEnabled(true);
                        binding.nh2occd.setEnabled(true);
                        binding.nh2occe.setEnabled(true);
                        binding.nh2occg.setEnabled(true);
                        binding.nh2occh.setEnabled(true);
                        binding.nh2occi.setEnabled(true);
                        /*if(MainApp.othergender != 1) {
                            binding.nh2occi.setEnabled(true);
                        }else{
                            binding.nh2occi.setEnabled(false);
                        }*/
                        binding.nh2occ96.setEnabled(true);
                        binding.nh2occ96x.setEnabled(true);

                        binding.nh2occf.setEnabled(false);
                        binding.nh2occf.setChecked(false);
                        binding.nh2occj.setEnabled(false);
                        binding.nh2occj.setChecked(false);
                    } else if (Age >= 17 && Age < 20) {
                        binding.nh2occ.clearCheck();
                        binding.nh2edu.clearCheck();
                        binding.nh2ms.clearCheck();
                        binding.fldGrpnh2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2ms.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2occ.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.nh211.setSelection(1);
                        binding.nh212.setSelection(1);

                        binding.nh2edua.setEnabled(true);
                        binding.nh2edub.setEnabled(true);
                        binding.nh2edu98.setEnabled(true);
                        binding.nh2educ.setEnabled(true);
                        binding.nh2edud.setEnabled(true);
                        binding.nh2edue.setEnabled(true);
                        binding.nh2eduf.setEnabled(true);
                        binding.nh2edug.setEnabled(true);
                        binding.nh2eduh.setEnabled(false);
                        binding.nh2eduh.setChecked(false);
                        binding.nh2edui.setEnabled(false);
                        binding.nh2edui.setChecked(false);

                        binding.nh2occa.setEnabled(true);
                        binding.nh2occb.setEnabled(true);
                        binding.nh2occc.setEnabled(true);
                        binding.nh2occd.setEnabled(true);
                        binding.nh2occe.setEnabled(true);
                        binding.nh2occg.setEnabled(true);
                        binding.nh2occh.setEnabled(true);
                        binding.nh2occi.setEnabled(true);
                        /*if(MainApp.othergender != 1) {
                            binding.nh2occi.setEnabled(true);
                        }else{
                            binding.nh2occi.setEnabled(false);
                        }*/
                        binding.nh2occ96.setEnabled(true);
                        binding.nh2occ96x.setEnabled(true);

                        binding.nh2occf.setEnabled(true);
                        binding.nh2occj.setEnabled(false);
                        binding.nh2occj.setChecked(false);
                    } else if (Age >= 19 && Age < 22) {
                        binding.nh2occ.clearCheck();
                        binding.nh2edu.clearCheck();
                        binding.nh2ms.clearCheck();
                        binding.fldGrpnh2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2ms.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2occ.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.nh211.setSelection(1);
                        binding.nh212.setSelection(1);

                        binding.nh2edua.setEnabled(true);
                        binding.nh2edub.setEnabled(true);
                        binding.nh2edu98.setEnabled(true);
                        binding.nh2educ.setEnabled(true);
                        binding.nh2edud.setEnabled(true);
                        binding.nh2edue.setEnabled(true);
                        binding.nh2eduf.setEnabled(true);
                        binding.nh2edug.setEnabled(true);
                        binding.nh2eduh.setEnabled(true);
                        binding.nh2edui.setEnabled(false);
                        binding.nh2edui.setChecked(false);

                        binding.nh2occa.setEnabled(true);
                        binding.nh2occb.setEnabled(true);
                        binding.nh2occc.setEnabled(true);
                        binding.nh2occd.setEnabled(true);
                        binding.nh2occe.setEnabled(true);
                        binding.nh2occg.setEnabled(true);
                        binding.nh2occh.setEnabled(true);
                        binding.nh2occi.setEnabled(true);
                        /*if(MainApp.othergender != 1) {
                            binding.nh2occi.setEnabled(true);
                        }else{
                            binding.nh2occi.setEnabled(false);
                        }*/
                        binding.nh2occ96.setEnabled(true);
                        binding.nh2occ96x.setEnabled(true);

                        binding.nh2occf.setEnabled(true);
                        binding.nh2occj.setEnabled(false);
                        binding.nh2occj.setChecked(false);
                    } else if (Age >= 22) {
                        binding.nh2occ.clearCheck();
                        binding.nh2edu.clearCheck();
                        binding.nh2ms.clearCheck();
                        binding.fldGrpnh2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2ms.setVisibility(View.VISIBLE);
                        binding.fldGrpnh2occ.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.nh211.setSelection(1);
                        binding.nh212.setSelection(1);

                        binding.nh2edua.setEnabled(true);
                        binding.nh2edub.setEnabled(true);
                        binding.nh2edu98.setEnabled(true);
                        binding.nh2educ.setEnabled(true);
                        binding.nh2edud.setEnabled(true);
                        binding.nh2edue.setEnabled(true);
                        binding.nh2eduf.setEnabled(true);
                        binding.nh2edug.setEnabled(true);
                        binding.nh2eduh.setEnabled(true);
                        binding.nh2edui.setEnabled(true);

                        binding.nh2occa.setEnabled(true);
                        binding.nh2occb.setEnabled(true);
                        binding.nh2occc.setEnabled(true);
                        binding.nh2occd.setEnabled(true);
                        binding.nh2occe.setEnabled(true);
                        binding.nh2occg.setEnabled(true);
                        binding.nh2occh.setEnabled(true);
                        binding.nh2occi.setEnabled(true);
                        /*if(MainApp.othergender != 1) {
                            binding.nh2occi.setEnabled(true);
                        }else{
                            binding.nh2occi.setEnabled(false);
                        }*/
                        binding.nh2occ96.setEnabled(true);
                        binding.nh2occ96x.setEnabled(true);

                        binding.nh2occf.setEnabled(true);
                        binding.nh2occj.setEnabled(true);

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.nh2edu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.nh2edua) {
                    binding.nh2occf.setEnabled(false);
                    binding.nh2occf.setChecked(false);
                } else {
                    binding.nh2occf.setEnabled(true);
                }
            }
        });

        binding.na203.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.na203b) {
                    binding.nh2mse.setChecked(false);
                    binding.nh2mse.setEnabled(false);
                }
            }
        });


//        Getting Members with types
        mem = MainApp.membersCount.getMembers();

//        Setting Dropdowns
        mothersList = new ArrayList<>();
        mothersSerials = new ArrayList<>();
        mothersMap = new HashMap<>();

        mothersList.add("....");
        mothersList.add("N/A");
        mothersSerials.add("0");
        mothersMap.put("N/A_0", "00");

        fathersList = new ArrayList<>();
        fathersSerials = new ArrayList<>();
        fathersMap = new HashMap<>();

        fathersList.add("....");
        fathersList.add("N/A");
        fathersSerials.add("0");
        fathersMap.put("N/A_0", "00");

        for (FamilyMembersContract mem : MainApp.members_f_m) {
            if (mem.getna204().equals("1")) {
                fathersList.add(mem.getName());
                fathersSerials.add(mem.getSerialNo());
                fathersMap.put(mem.getName() + "_" + mem.getSerialNo(), mem.getSerialNo());
            } else {
                mothersList.add(mem.getName());
                mothersSerials.add(mem.getSerialNo());
                mothersMap.put(mem.getName() + "_" + mem.getSerialNo(), mem.getSerialNo());
            }
        }

        binding.nh211.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, fathersList));
        binding.nh212.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, mothersList));
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


                finish();

                startActivity(new Intent(this, SectionA2ListActivity.class)
                        .putExtra("respChecking", binding.respa.isChecked())
                        .putExtra("respLineNo", MainApp.fmc.getSerialNo()));
                //}


            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() throws JSONException {

        MainApp.fmc.setna204(binding.na204a.isChecked() ? "1" : binding.na204b.isChecked() ? "2"
                : binding.na204c.isChecked() ? "3" : "0");
        MainApp.fmc.setName(binding.na202.getText().toString());
        MainApp.fmc.setRealtionHH(binding.na203a.isChecked() ? "1" : binding.na203b.isChecked() ? "2" : binding.na203c.isChecked() ? "3" : binding.na203d.isChecked() ? "4"
                : binding.na203e.isChecked() ? "5" : binding.na203f.isChecked() ? "6" : binding.na203g.isChecked() ? "7" : binding.na203h.isChecked() ? "8"
                : binding.na203i.isChecked() ? "9" : binding.na203j.isChecked() ? "10" : binding.na203k.isChecked() ? "11" : binding.na203l.isChecked() ? "12"
                : binding.na203m.isChecked() ? "13" : binding.na20398.isChecked() ? "98" : binding.na20396.isChecked() ? "96" : "0");

        MainApp.fmc.setResp(binding.respa.isChecked() ? "1" : binding.respb.isChecked() ? "2" : "0"); //respondent

//        Checking IsHead
        if (!MainApp.IsHead && binding.na203a.isChecked()) {
            MainApp.IsHead = true;
        }

        if (!MainApp.IsResp && binding.respa.isChecked()) {
            MainApp.IsResp = true;
            MainApp.gender = binding.na204.indexOfChild(findViewById(binding.na204.getCheckedRadioButtonId())) + 1;
        }

        MainApp.fmc.setAge(binding.nh2agey.getText().toString());
        if (Age < 6) {
            MainApp.fmc.setMotherId(mothersMap.get(binding.nh212.getSelectedItem().toString() + "_" + mothersSerials.get(mothersList.indexOf(binding.nh212.getSelectedItem().toString()) - 1)));
        }

        JSONObject sA2 = new JSONObject();

        sA2.put("cluster_no", MainApp.fc.getClusterNo());
        sA2.put("resp", MainApp.fmc.getResp().equals("0") ? "" : MainApp.fmc.getResp());
        sA2.put("nh2SerialNo", MainApp.fmc.getSerialNo());
        sA2.put("nh202", MainApp.fmc.getName());
        sA2.put("nh203", MainApp.fmc.getRealtionHH());
        sA2.put("nh204", binding.na204a.isChecked() ? "1" : binding.na204b.isChecked() ? "2" : "0");

        sA2.put("nh2doby", binding.nh2doby.getText().toString());
        sA2.put("nh2dobm", binding.nh2dobm.getText().toString());
        sA2.put("nh2dobd", binding.nh2dobd.getText().toString());

        sA2.put("nh206y", binding.nh2agey.getText().toString());

        if (binding.nh2doby.getText().toString().equals("9998")) {
            Age = Integer.valueOf(binding.nh2agey.getText().toString());
        } else {
            Age = (int) agebyDob;
        }

        sA2.put("age", String.valueOf(Age));


        sA2.put("nh207", binding.nh2msa.isChecked() ? "1" : binding.nh2msb.isChecked() ? "2" : binding.nh2msc.isChecked() ? "3" : binding.nh2msd.isChecked() ? "4"
                : binding.nh2mse.isChecked() ? "5" : "0");

        sA2.put("nh208", binding.nh2edua.isChecked() ? "1" : binding.nh2edub.isChecked() ? "2" : binding.nh2educ.isChecked() ? "3" : binding.nh2edud.isChecked() ? "4"
                : binding.nh2edue.isChecked() ? "5" : binding.nh2eduf.isChecked() ? "6" : binding.nh2edug.isChecked() ? "7" : binding.nh2eduh.isChecked() ? "8"
                : binding.nh2edui.isChecked() ? "9"
                : binding.nh2edu98.isChecked() ? "98"
                : "0");

        sA2.put("nh209", binding.nh2occa.isChecked() ? "1" : binding.nh2occb.isChecked() ? "2" : binding.nh2occc.isChecked() ? "3" : binding.nh2occd.isChecked() ? "4"
                : binding.nh2occe.isChecked() ? "5" : binding.nh2occf.isChecked() ? "6" : binding.nh2occg.isChecked() ? "7" : binding.nh2occh.isChecked() ? "8"
                : binding.nh2occi.isChecked() ? "9" : binding.nh2occj.isChecked() ? "10" : binding.nh2occ96.isChecked() ? "96" : "0");

        sA2.put("nh20996x", binding.nh2occ96x.getText().toString());

        sA2.put("nh210", binding.nh210a.isChecked() ? "1" : binding.nh210b.isChecked() ? "2" : "0");

        sA2.put("nh211", fathersMap.get(binding.nh211.getSelectedItem().toString() + "_" + fathersSerials.get(fathersList.indexOf(binding.nh211.getSelectedItem().toString()) - 1)));
        sA2.put("nh212", mothersMap.get(binding.nh212.getSelectedItem().toString() + "_" + mothersSerials.get(mothersList.indexOf(binding.nh212.getSelectedItem().toString()) - 1)));

        MainApp.fmc.setsA2(String.valueOf(sA2));

        //Setting for FamilyMembers List
        MainApp.fmc.setMaritialStatus(binding.nh2msa.isChecked() ? "1" : binding.nh2msb.isChecked() ? "2" : binding.nh2msc.isChecked() ? "3" : binding.nh2msd.isChecked() ? "4"
                : binding.nh2mse.isChecked() ? "5" : "0");
        MainApp.fmc.setFatherName(binding.nh211.getSelectedItem().toString().toUpperCase());
        MainApp.fmc.setMotherName(binding.nh212.getSelectedItem().toString().toUpperCase());

        /*Functionality Setting*/
//        Calculation
        Map<Integer, Integer> memType = new HashMap<>();

        //Total
        if (binding.na204a.isChecked()) {
            memType.put(1, Integer.valueOf(mem.get(1).get(1).toString()) + 1);
            memType.put(2, Integer.valueOf(mem.get(1).get(2).toString()));
        } else {
            memType.put(2, Integer.valueOf(mem.get(1).get(2).toString()) + 1);
            memType.put(1, Integer.valueOf(mem.get(1).get(1).toString()));
        }

        MainApp.membersCount.setMembers(1, memType);

        // Set availability
        if (binding.nh210a.isChecked()) {
            MainApp.fmc.setAv("1");
        }

        //MWRA
        if ((Age >= 15 && Age < 50) && binding.na204b.isChecked()) {
            if (binding.nh2mse.isChecked()) {
                MainApp.membersCount.setWra(MainApp.membersCount.getWra() + 1);
                /*if (binding.nh210a.isChecked()) {
                    MainApp.fmc.setAv("1");
                }*/
            } else {
                MainApp.membersCount.setMwra(MainApp.membersCount.getMwra() + 1);
                /*if (binding.nh210a.isChecked()) {
                    MainApp.fmc.setAv("1");
                }*/

            }
            if (binding.nh210a.isChecked()) {
                MainApp.mwra.add(MainApp.fmc);
            }
            //MainApp.adolescents.add(MainApp.fmc);
        }

        //Adolescent
        if ((Age >= 10 && Age < 20) && binding.nh2mse.isChecked()) {
            memType = new HashMap<>();
            if (binding.na204a.isChecked()) {
                memType.put(1, Integer.valueOf(mem.get(2).get(1).toString()) + 1);
                memType.put(2, Integer.valueOf(mem.get(2).get(2).toString()));
            } else {
                memType.put(2, Integer.valueOf(mem.get(2).get(2).toString()) + 1);
                memType.put(1, Integer.valueOf(mem.get(2).get(1).toString()));
            }
            MainApp.membersCount.setMembers(2, memType);

            // Add data in list
            MainApp.adolescents.add(MainApp.fmc);
            /*if (binding.nh210a.isChecked()) {
                MainApp.fmc.setAv("1");
            }*/
        }

        //Children < 5
        else if (Age < 6) {
            memType = new HashMap<>();
            if (binding.na204a.isChecked()) {
                memType.put(1, Integer.valueOf(mem.get(3).get(1).toString()) + 1);
                memType.put(2, Integer.valueOf(mem.get(3).get(2).toString()));
            } else {
                memType.put(2, Integer.valueOf(mem.get(3).get(2).toString()) + 1);
                memType.put(1, Integer.valueOf(mem.get(3).get(1).toString()));
            }
            MainApp.membersCount.setMembers(3, memType);
            /*if (binding.nh210a.isChecked()) {
                MainApp.fmc.setAv("1");
            }*/

            // Add data in list
            if (Age < 2) {
                MainApp.childUnder2.add(MainApp.fmc);
                MainApp.childUnder2Check.add(MainApp.fmc);
                MainApp.childUnder5.add(MainApp.fmc);
                MainApp.childUnder5_Del.add(MainApp.fmc);
            } else {
                MainApp.childUnder5.add(MainApp.fmc);
                MainApp.childUnder5_Del.add(MainApp.fmc);

                //MainApp.adolescents.add(MainApp.fmc);
            }
            /*if (Age < 2) {
                MainApp.childUnder2Check.add(MainApp.fmc);

            }*/

            if (Age < 6 && MainApp.fmc.getMotherId().equals("00")) {
                MainApp.childNA.add(MainApp.fmc);
            }
        }

        if (Age >= 15) {
            // Add data in list
            if (!binding.nh2mse.isChecked()) {
                MainApp.members_f_m.add(MainApp.fmc);
            }

            if (binding.nh210a.isChecked()) {
                MainApp.respList.add(MainApp.fmc);
            }
        }

        // Add data in list for all members
        MainApp.all_members.add(MainApp.fmc);

        /*End*/

        MainApp.fmc.setAgeInYear(String.valueOf(Age));

        MainApp.familyMembersList.set(position, MainApp.fmc);

        // Add in fm clicked
        MainApp.familyMembersClicked.put(position, MainApp.fmc);

    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateFamilyMember(MainApp.fmc);

        if (updcount != 0) {

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private boolean formValidation() {

        if (!validatorClass.EmptyTextBox(this, binding.na202, getString(R.string.na202))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, binding.na203, binding.na203a, getString(R.string.na203))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, binding.na204, binding.na204a, getString(R.string.na204))) {
            return false;
        }

        if (!MainApp.IsResp) {
            return validatorClass.EmptyRadioButton(this, binding.resp, binding.respb, getString(R.string.resp));
        }

        if (!validatorClass.EmptyTextBox(this, binding.nh2doby, getString(R.string.nh2dob))) {
            return false;
        }


        if (!validatorClass.RangeTextBox(this, binding.nh2doby, DateUtils.getCurrentYear() - 95, DateUtils.getCurrentYear(), 9998, getString(R.string.nh2dob), " year")) {
            return false;
        }


        if (!validatorClass.EmptyTextBox(this, binding.nh2dobm, getString(R.string.nh2dob))) {
            return false;
        }

        if (!validatorClass.RangeTextBox(this, binding.nh2dobm, 1, 12, 98, getString(R.string.nh2dob), " month")) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, binding.nh2dobd, getString(R.string.nh2dob))) {
            return false;
        }

        if (!validatorClass.RangeTextBox(this, binding.nh2dobd, 1, 31, 98, getString(R.string.nh2dob), " day")) {
            return false;
        }

        Calendar today = Calendar.getInstance();
        if (dob.after(today)) {
            if (!validatorClass.RangeTextBoxforDate(this, binding.nh2dobd, 1, DateUtils.getCurrentDate(), 98, "Date can not be more than today")) {
                return false;
            }

            if (!validatorClass.RangeTextBoxforDate(this, binding.nh2dobm, 1, DateUtils.getCurrentMonth(), 98, "Month can not be more than current Month")) {
                return false;
            }

            if (!validatorClass.RangeTextBoxforDate(this, binding.nh2doby, DateUtils.getCurrentYear() - 95, DateUtils.getCurrentYear(), 9998, "Year can not be more than current year")) {
                return false;
            }
        }

        if (binding.nh2doby.getText().toString().equals("9998")) {

            if (!validatorClass.EmptyTextBox(this, binding.nh2agey, getString(R.string.na2age))) {
                return false;
            }

            if (!validatorClass.RangeTextBox(this, binding.nh2agey, 0, 95, 98, getString(R.string.na2age), " years")) {
                return false;
            }
        }

        if ((MainApp.fmc.getResp().equals("1") || MainApp.fmc.getRealtionHH().equals("1")) && Age < 18) {
            String chk = MainApp.fmc.getResp().equals("1") ? "Resp" : "Head";
            binding.nh2agey.setError("Error(Invalid) Age for " + chk);
            Toast.makeText(this, chk + " Age greater then or equal 18..", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            binding.nh2agey.setError(null);
        }

        if ((MainApp.fmc.getResp().equals("1") || MainApp.fmc.getRealtionHH().equals("1")) && Age < 18) {
            String chk = MainApp.fmc.getResp().equals("1") ? "Resp" : "Head";
            binding.nh2agey.setError("Error(Invalid) Age for " + chk);
            Toast.makeText(this, chk + " Age greater then or equal 18..", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            binding.nh2agey.setError(null);
        }

        if (!validatorClass.EmptyRadioButton(this, binding.nh2ms, binding.nh2msa, getString(R.string.nh2ms))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, binding.nh2edu, binding.nh2edua, getString(R.string.nh2edu))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, binding.nh2occ, binding.nh2occa, getString(R.string.nh2occ))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, binding.nh2occ, binding.nh2occ96, binding.nh2occ96x, getString(R.string.nh2occ))) {
            return false;
        }

        if (!MainApp.IsResp) {
            if (!validatorClass.EmptyRadioButton(this, binding.nh210, binding.nh210a, getString(R.string.nh210))) {
                return false;
            }
        }

        if (Age < 5) {
            if (!validatorClass.EmptySpinner(this, binding.nh211, getString(R.string.nh211))) {
                return false;
            }
            return validatorClass.EmptySpinner(this, binding.nh212, getString(R.string.nh212));
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    }
}


