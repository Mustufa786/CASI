package edu.aku.hassannaqvi.casi_2019.ui.household;

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
import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONA2ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionA2EditBinding;
import edu.aku.hassannaqvi.casi_2019.other.DateUtils;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

import static edu.aku.hassannaqvi.casi_2019.ui.household.SectionA1Activity.userCountryTajik_Home;


public class SectionA2EditActivity extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private final long DELAY = 1000;
    private final long DELAY1 = 2000;
    ActivitySectionA2EditBinding binding;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    Map<Integer, Map<Integer, Integer>> mem;
    List<String> mothersList, fathersList;
    List<String> mothersSerials, fathersSerials;
    Map<String, String> mothersMap, fathersMap;
    Map<String, FamilyMembersContract> mothersChildMap;
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

            if (!binding.cih2dobd.getText().toString().isEmpty() && !binding.cih2dobm.getText().toString().isEmpty() && !binding.cih2doby.getText().toString().isEmpty()) {

                if (!binding.cih2dobd.getText().toString().equals("98") && !binding.cih2dobm.getText().toString().equals("98")
                        && !binding.cih2doby.getText().toString().equals("9998")) {

                    dob = DateUtils.getCalendarDate(binding.cih2dobd.getText().toString(), binding.cih2dobm.getText().toString(),
                            binding.cih2doby.getText().toString());

                    agebyDob = DateUtils.ageInYearByDOB(dob);
                    ageinMonths = DateUtils.ageInMonthsByDOB(dob);

                    binding.cih2agey.setEnabled(false);
                    binding.cih2agey.setText(String.valueOf(agebyDob));

                } else if (!binding.cih2doby.getText().toString().equals("9998") && !binding.cih2dobm.getText().toString().equals("98")) {

                    dob = DateUtils.getCalendarDate(binding.cih2dobm.getText().toString(),
                            binding.cih2doby.getText().toString());
                    agebyDob = DateUtils.ageInYearByDOB(dob);
                    binding.cih2agey.setEnabled(false);
                    binding.cih2agey.setText(String.valueOf(agebyDob));
                } else if (!binding.cih2doby.getText().toString().equals("9998")) {
                    agebyDob = DateUtils.ageInYearByDOB(binding.cih2doby.getText().toString());
                    binding.cih2agey.setEnabled(false);
                    binding.cih2agey.setText(String.valueOf(agebyDob));
                } else if (binding.cih2doby.getText().toString().equals("9998")) {
                    binding.cih2agey.setEnabled(true);
                    binding.cih2agey.setText(null);
                }


            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };
    //Calendar calDob = Calendar.getInstance();
    @BindViews({R.id.cih2doby, R.id.cih2dobm, R.id.cih2dobd})
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

        /*For Tajik*/
        binding.fldGrpcih214check.setVisibility(userCountryTajik_Home ? View.VISIBLE : View.GONE);
        binding.cih2occ98.setVisibility(userCountryTajik_Home ? View.VISIBLE : View.GONE);
        binding.cih2occ96.setVisibility(userCountryTajik_Home ? View.GONE : View.VISIBLE);

        binding.cih2occa.setVisibility(userCountryTajik_Home ? View.GONE : View.VISIBLE);
        binding.cih2occh.setVisibility(userCountryTajik_Home ? View.GONE : View.VISIBLE);

        binding.cih2occl.setVisibility(userCountryTajik_Home ? View.VISIBLE : View.GONE);
        binding.cih2occm.setVisibility(userCountryTajik_Home ? View.VISIBLE : View.GONE);
        binding.cih2occn.setVisibility(userCountryTajik_Home ? View.VISIBLE : View.GONE);

        binding.na203o.setVisibility(userCountryTajik_Home ? View.GONE : View.VISIBLE);
    }

    public void AutoPopulate() {

        JSONA2ModelClass jsonB2 = JSONUtilClass.getModelFromJSON(MainApp.fmc.getsA2(), JSONA2ModelClass.class);

        binding.na204.check(MainApp.fmc.getna204().equals("1") ? binding.na204a.getId()
                : binding.na204b.getId());
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

        binding.cih2doby.setText(jsonB2.getcih2doby());
        binding.cih2dobm.setText(jsonB2.getcih2dobm());
        binding.cih2dobd.setText(jsonB2.getcih2dobd());

        binding.cih2agey.setText(jsonB2.getcih206y());

        if (!jsonB2.getcih207().equals("0")) {
            binding.cih2ms.check(
                    jsonB2.getcih207().equals("1") ? binding.cih2msa.getId() :
                            jsonB2.getcih207().equals("2") ? binding.cih2msb.getId() :
                                    jsonB2.getcih207().equals("3") ? binding.cih2msc.getId() :
                                            jsonB2.getcih207().equals("4") ? binding.cih2msd.getId() :
                                                    binding.cih2mse.getId());
        }

        if (!jsonB2.getcih208().equals("0")) {
            binding.cih2edu.check(
                    jsonB2.getcih208().equals("1") ? binding.cih2edua.getId() :
                            jsonB2.getcih208().equals("2") ? binding.cih2edub.getId() :
                                    jsonB2.getcih208().equals("3") ? binding.cih2educ.getId() :
                                            jsonB2.getcih208().equals("4") ? binding.cih2edud.getId() :
                                                    jsonB2.getcih208().equals("5") ? binding.cih2edue.getId() :
                                                            jsonB2.getcih208().equals("6") ? binding.cih2eduf.getId() :
                                                                    jsonB2.getcih208().equals("7") ? binding.cih2edug.getId() :
                                                                            jsonB2.getcih208().equals("8") ? binding.cih2eduh.getId() :
                                                                                    jsonB2.getcih208().endsWith("9") ? binding.cih2edui.getId() :
                                                                                            jsonB2.getcih208().endsWith("10") ? binding.cih2eduj.getId() :
                                                                                                    binding.cih2edu98.getId());
        }

        if (!jsonB2.getcih209().equals("0")) {
            binding.cih2occ.check(
                    jsonB2.getcih209().equals("1") ? binding.cih2occa.getId() :
                            jsonB2.getcih209().equals("2") ? binding.cih2occb.getId() :
                                    jsonB2.getcih209().equals("3") ? binding.cih2occc.getId() :
                                            jsonB2.getcih209().equals("4") ? binding.cih2occd.getId() :
                                                    jsonB2.getcih209().equals("5") ? binding.cih2occe.getId() :
                                                            jsonB2.getcih209().equals("6") ? binding.cih2occf.getId() :
                                                                    jsonB2.getcih209().equals("7") ? binding.cih2occg.getId() :
                                                                            jsonB2.getcih209().equals("8") ? binding.cih2occh.getId() :
                                                                                    jsonB2.getcih209().equals("9") ? binding.cih2occi.getId() :
                                                                                            jsonB2.getcih209().equals("10") ? binding.cih2occj.getId() :
                                                                                                    jsonB2.getcih209().equals("11") ? binding.cih2occk.getId() :
                                                                                                            jsonB2.getcih209().equals("12") ? binding.cih2occl.getId() :
                                                                                                                    jsonB2.getcih209().equals("13") ? binding.cih2occm.getId() :
                                                                                                                            jsonB2.getcih209().equals("14") ? binding.cih2occn.getId() :
                                                                                                                                    jsonB2.getcih209().equals("98") ? binding.cih2occ98.getId() :
                                                                                                                                            binding.cih2occ96.getId());

            binding.cih2occ96x.setText(jsonB2.getcih20996x());
        }

        if (!jsonB2.getCih209aa().equals("0")) {
            binding.cih214a.setChecked(true);
        }
        if (!jsonB2.getCih209ab().equals("0")) {
            binding.cih214b.setChecked(true);
        }
        if (!jsonB2.getCih209ac().equals("0")) {
            binding.cih214c.setChecked(true);
        }
        if (!jsonB2.getCih209ad().equals("0")) {
            binding.cih214d.setChecked(true);
        }
        if (!jsonB2.getCih209ae().equals("0")) {
            binding.cih214e.setChecked(true);
        }
        if (!jsonB2.getCih209af().equals("0")) {
            binding.cih214f.setChecked(true);
        }
        if (!jsonB2.getCih209ag().equals("0")) {
            binding.cih214g.setChecked(true);
        }
        if (!jsonB2.getCih209ah().equals("0")) {
            binding.cih214h.setChecked(true);
        }


        if (!jsonB2.getcih210().equals("0")) {
            binding.cih210.check(
                    jsonB2.getcih210().equals("1") ? binding.cih210a.getId() :
                            binding.cih210b.getId()
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
        if (jsonB2.getcih210().equals("1")) {
            MainApp.fmc.setAv("");
        }

        //MWRA
        if ((Age >= 15 && Age < 50) && MainApp.fmc.getna204().equals("2")) {
            if (jsonB2.getcih207().equals("5")) {
                MainApp.membersCount.setWra(MainApp.membersCount.getWra() - 1);
                /*if (jsonB2.getcih210().equals("1")) {
                    MainApp.fmc.setAv("");
                }*/
            } else {
                MainApp.membersCount.setMwra(MainApp.membersCount.getMwra() - 1);
                /*if (jsonB2.getcih210().equals("1")) {
                    MainApp.fmc.setAv("");
                }*/

            }
//            MainApp.mwra.add(MainApp.fmc);
            if (jsonB2.getcih210().equals("1")) {
                for (byte i = 0; i < MainApp.mwra.size(); i++) {
                    if (MainApp.mwra.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.mwra.remove(i);
                        break;
                    }
                }
            }
        }

        //Adolescent
        if ((Age >= 10 && Age < 20) && jsonB2.getcih207().equals("5")) {
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

            /*if (jsonB2.getcih210().equals("1")) {
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
            /*if (jsonB2.getcih210().equals("1")) {
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

//                if (jsonB2.getcih210().equals("1")) {
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

//            if (Age < 6 && MainApp.fmc.getMotherId().equals("00") && jsonB2.getcih210().equals("1")) {
            if (MainApp.fmc.getMotherId().equals("00")) {
//                MainApp.childNA.add(MainApp.fmc);
                for (byte i = 0; i < MainApp.childNA.size(); i++) {
                    if (MainApp.childNA.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.childNA.remove(i);
                        break;
                    }
                }
            } else {
                if (Age < 5) {
                    for (byte i = 0; i < MainApp.mwraChild.size(); i++) {
                        if (MainApp.mwraChild.get(i).getSerialNo().equals(mothersSerials.get(mothersList.indexOf(binding.cih212.getSelectedItem().toString()) - 1))) {
                            MainApp.mwraChild.remove(i);
                            break;
                        }
                    }
                }
            }
        }

        if (Age >= 15) {
            // Add data in list
            if (!jsonB2.getcih207().equals("5")) {
//                MainApp.members_f_m.add(MainApp.fmc);
                for (byte i = 0; i < MainApp.members_f_m.size(); i++) {
                    if (MainApp.members_f_m.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                        MainApp.members_f_m.remove(i);
                        break;
                    }
                }
            }

//            MainApp.respList.add(MainApp.fmc);
            if (jsonB2.getcih210().equals("1")) {
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

        // Add data in list for Adolescent
        for (byte i = 0; i < MainApp.adolesUnderAge.size(); i++) {
            if (MainApp.adolesUnderAge.get(i).getSerialNo().equals(MainApp.fmc.getSerialNo())) {
                MainApp.adolesUnderAge.remove(i);
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
        //binding.cih2ms.setOnCheckedChangeListener(this);
        binding.cih2edu.setOnCheckedChangeListener(this);
        binding.cih2occ.setOnCheckedChangeListener(this);
        binding.cih210.setOnCheckedChangeListener(this);


    }

    public void setupViews() {

        position = getIntent().getIntExtra("pos", -1);

        MainApp.fmc = (FamilyMembersContract) getIntent().getSerializableExtra("data");

//        Getting Intent
        MainApp.SetNameClass nameSet = new MainApp.SetNameClass(getString(R.string.cih2dob) + " " + MainApp.fmc.getName());
        binding.setName(nameSet);

        binding.txtcih2dob.setText(binding.txtcih2dob.getText().toString().replace("Name", binding.na202.getText().toString()));
        binding.txtna2age.setText(binding.txtna2age.getText().toString().replace("Name", binding.na202.getText().toString()));
        binding.txtcih210.setText(binding.txtcih210.getText().toString().replace("Name", binding.na202.getText().toString()));

        for (EditText ed : grpdob) {
            ed.addTextChangedListener(age);
        }

        binding.cih2agey.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!binding.cih2agey.getText().toString().isEmpty()) {


                    binding.cih2occ.clearCheck();

                    Age = Integer.valueOf(binding.cih2agey.getText().toString());
                    if (Age <= 2) {
                        binding.fldGrpcih2edu.setVisibility(View.GONE);
                        binding.fldGrpcih2ms.setVisibility(View.GONE);

                        binding.fldGrpfid.setVisibility(View.VISIBLE);
                        binding.fldGrpmid.setVisibility(View.VISIBLE);

                        binding.cih2edu.clearCheck();
                        binding.cih2ms.clearCheck();
                        binding.cih2occ.clearCheck();

                        binding.cih2edua.setChecked(true);
                        binding.cih2mse.setChecked(true);

                    } else if (Age > 2 && Age <= 5) {
                        binding.cih2edu.clearCheck();
                        binding.fldGrpcih2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpcih2ms.setVisibility(View.GONE);
                        binding.cih2mse.setChecked(true);

                        binding.fldGrpfid.setVisibility(View.VISIBLE);
                        binding.fldGrpmid.setVisibility(View.VISIBLE);

                        binding.cih2edua.setEnabled(true);
                        binding.cih2edub.setEnabled(true);
                        binding.cih2edu98.setEnabled(true);
                        binding.cih2educ.setEnabled(true);
                        binding.cih2edud.setEnabled(false);
                        binding.cih2edud.setChecked(false);
                        binding.cih2edue.setEnabled(false);
                        binding.cih2edue.setChecked(false);
                        binding.cih2eduf.setEnabled(false);
                        binding.cih2eduf.setChecked(false);
                        binding.cih2edug.setEnabled(false);
                        binding.cih2edug.setChecked(false);
                        binding.cih2eduh.setEnabled(false);
                        binding.cih2eduh.setChecked(false);
                        binding.cih2edui.setEnabled(false);
                        binding.cih2eduj.setEnabled(false);
                        binding.cih2edui.setChecked(false);
                        binding.cih2eduj.setChecked(false);

                    } else if (Age > 5 && Age < 10) {
                        binding.cih2edu.clearCheck();
                        binding.cih2ms.clearCheck();
                        binding.fldGrpcih2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpcih2ms.setVisibility(View.GONE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.cih2mse.setChecked(true);

                        binding.cih211.setSelection(1);
                        binding.cih212.setSelection(1);

                        binding.cih2edua.setEnabled(true);
                        binding.cih2edub.setEnabled(true);
                        binding.cih2edu98.setEnabled(true);
                        binding.cih2educ.setEnabled(true);
                        binding.cih2edud.setEnabled(true);
                        binding.cih2edue.setEnabled(false);
                        binding.cih2edue.setChecked(false);
                        binding.cih2eduf.setEnabled(false);
                        binding.cih2eduf.setChecked(false);
                        binding.cih2edug.setEnabled(false);
                        binding.cih2edug.setChecked(false);
                        binding.cih2eduh.setEnabled(false);
                        binding.cih2eduh.setChecked(false);
                        binding.cih2edui.setEnabled(false);
                        binding.cih2edui.setChecked(false);
                        binding.cih2eduj.setEnabled(false);
                        binding.cih2eduj.setChecked(false);

                    } else if (Age >= 10 && Age < 14) {
                        binding.cih2edu.clearCheck();
                        binding.cih2ms.clearCheck();
                        binding.fldGrpcih2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpcih2ms.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.cih211.setSelection(1);
                        binding.cih212.setSelection(1);
                        binding.cih2edua.setEnabled(true);
                        binding.cih2edub.setEnabled(true);
                        binding.cih2edu98.setEnabled(true);
                        binding.cih2educ.setEnabled(true);
                        binding.cih2edud.setEnabled(true);
                        binding.cih2edue.setEnabled(true);
                        binding.cih2eduf.setEnabled(false);
                        binding.cih2eduf.setChecked(false);
                        binding.cih2edug.setEnabled(false);
                        binding.cih2edug.setChecked(false);
                        binding.cih2eduh.setEnabled(false);
                        binding.cih2eduh.setChecked(false);
                        binding.cih2edui.setEnabled(false);
                        binding.cih2edui.setChecked(false);
                        binding.cih2eduj.setEnabled(false);
                        binding.cih2eduj.setChecked(false);

                    } else if (Age >= 14 && Age < 17) {
                        binding.cih2edu.clearCheck();
                        binding.cih2ms.clearCheck();
                        binding.fldGrpcih2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpcih2ms.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.cih211.setSelection(1);
                        binding.cih212.setSelection(1);

                        binding.cih2edua.setEnabled(true);
                        binding.cih2edub.setEnabled(true);
                        binding.cih2edu98.setEnabled(true);
                        binding.cih2educ.setEnabled(true);
                        binding.cih2edud.setEnabled(true);
                        binding.cih2edue.setEnabled(true);
                        binding.cih2eduf.setEnabled(true);
                        binding.cih2edug.setEnabled(false);
                        binding.cih2edug.setChecked(false);
                        binding.cih2eduh.setEnabled(false);
                        binding.cih2eduh.setChecked(false);
                        binding.cih2edui.setEnabled(false);
                        binding.cih2edui.setChecked(false);
                        binding.cih2eduj.setEnabled(false);
                        binding.cih2eduj.setChecked(false);

                    } else if (Age >= 17 && Age < 20) {
                        binding.cih2edu.clearCheck();
                        binding.cih2ms.clearCheck();
                        binding.fldGrpcih2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpcih2ms.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.cih211.setSelection(1);
                        binding.cih212.setSelection(1);

                        binding.cih2edua.setEnabled(true);
                        binding.cih2edub.setEnabled(true);
                        binding.cih2edu98.setEnabled(true);
                        binding.cih2educ.setEnabled(true);
                        binding.cih2edud.setEnabled(true);
                        binding.cih2edue.setEnabled(true);
                        binding.cih2eduf.setEnabled(true);
                        binding.cih2edug.setEnabled(true);
                        binding.cih2eduh.setEnabled(false);
                        binding.cih2eduh.setChecked(false);
                        binding.cih2edui.setEnabled(false);
                        binding.cih2edui.setChecked(false);
                        binding.cih2eduj.setEnabled(false);
                        binding.cih2eduj.setChecked(false);

                    } else if (Age >= 19 && Age < 22) {
                        binding.cih2edu.clearCheck();
                        binding.cih2ms.clearCheck();
                        binding.fldGrpcih2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpcih2ms.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.cih211.setSelection(1);
                        binding.cih212.setSelection(1);

                        binding.cih2edua.setEnabled(true);
                        binding.cih2edub.setEnabled(true);
                        binding.cih2edu98.setEnabled(true);
                        binding.cih2educ.setEnabled(true);
                        binding.cih2edud.setEnabled(true);
                        binding.cih2edue.setEnabled(true);
                        binding.cih2eduf.setEnabled(true);
                        binding.cih2edug.setEnabled(true);
                        binding.cih2eduh.setEnabled(true);
                        binding.cih2edui.setEnabled(false);
                        binding.cih2edui.setChecked(false);
                        binding.cih2eduj.setEnabled(false);
                        binding.cih2eduj.setChecked(false);

                    } else if (Age >= 22) {
                        binding.cih2edu.clearCheck();
                        binding.cih2ms.clearCheck();
                        binding.fldGrpcih2edu.setVisibility(View.VISIBLE);
                        binding.fldGrpcih2ms.setVisibility(View.VISIBLE);

                        binding.fldGrpfid.setVisibility(View.GONE);
                        binding.fldGrpmid.setVisibility(View.GONE);
                        binding.cih211.setSelection(1);
                        binding.cih212.setSelection(1);

                        binding.cih2edua.setEnabled(true);
                        binding.cih2edub.setEnabled(true);
                        binding.cih2edu98.setEnabled(true);
                        binding.cih2educ.setEnabled(true);
                        binding.cih2edud.setEnabled(true);
                        binding.cih2edue.setEnabled(true);
                        binding.cih2eduf.setEnabled(true);
                        binding.cih2edug.setEnabled(true);
                        binding.cih2eduh.setEnabled(true);
                        binding.cih2edui.setEnabled(true);
                        binding.cih2eduj.setEnabled(true);
                    }

                    binding.cih2occa.setEnabled(true);
                    binding.cih2occb.setEnabled(false);
                    binding.cih2occc.setEnabled(false);
                    binding.cih2occd.setEnabled(false);
                    binding.cih2occe.setEnabled(false);
                    binding.cih2occf.setEnabled(false);
                    binding.cih2occg.setEnabled(false);
                    binding.cih2occh.setEnabled(false);
                    binding.cih2occi.setEnabled(false);
                    binding.cih2occj.setEnabled(false);
                    binding.cih2occk.setEnabled(false);
                    binding.cih2occl.setEnabled(false);
                    binding.cih2occm.setEnabled(false);
                    binding.cih2occn.setEnabled(false);
                    binding.cih2occ98.setEnabled(false);
                    binding.cih2occ96.setEnabled(false);

                    if (Age > 10) {
                        binding.cih2occb.setEnabled(true);
                        binding.cih2occc.setEnabled(true);
                        binding.cih2occd.setEnabled(true);
                        binding.cih2occe.setEnabled(true);
                        binding.cih2occf.setEnabled(true);
                        binding.cih2occg.setEnabled(true);
                        binding.cih2occh.setEnabled(true);
                        binding.cih2occk.setEnabled(true);
                        binding.cih2occ98.setEnabled(true);
                        binding.cih2occ96.setEnabled(true);
                    }

                    if (Age < 7)
                        binding.cih2occl.setEnabled(true);
                    else if (Age >= 7 && Age < 18)
                        binding.cih2occm.setEnabled(true);
                    else if (Age >= 18 && Age < 50)
                        binding.cih2occn.setEnabled(true);
                    else {
                        binding.cih2occn.setEnabled(true);
                        binding.cih2occj.setEnabled(true);
                    }

                    if (Age >= 14) {
                        if (binding.na204b.isChecked()) {
                            binding.cih2occi.setEnabled(true);
                        } else {
                            binding.cih2occi.setEnabled(false);
                            binding.cih2occi.setChecked(false);
                        }
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.cih2edu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cih2edua) {
                    binding.cih2occf.setEnabled(false);
                    binding.cih2occf.setChecked(false);
                } else {
                    binding.cih2occf.setEnabled(true);
                }
            }
        });

        binding.na203.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.na203b) {
                    binding.cih2mse.setChecked(false);
                    binding.cih2mse.setEnabled(false);
                }
            }
        });


//        Getting Members with types
        mem = MainApp.membersCount.getMembers();

//        Setting Dropdowns
        mothersList = new ArrayList<>();
        mothersSerials = new ArrayList<>();
        mothersMap = new HashMap<>();
        mothersChildMap = new HashMap<>();

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

                mothersChildMap.put(mem.getName() + "_" + mem.getSerialNo(), mem);
            }
        }

        binding.cih211.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, fathersList));
        binding.cih212.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, mothersList));
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
                : "0");
        MainApp.fmc.setName(binding.na202.getText().toString());
        MainApp.fmc.setRealtionHH(binding.na203a.isChecked() ? "1" : binding.na203b.isChecked() ? "2" : binding.na203c.isChecked() ? "3" : binding.na203d.isChecked() ? "4"
                : binding.na203e.isChecked() ? "5" : binding.na203f.isChecked() ? "6" : binding.na203g.isChecked() ? "7" : binding.na203h.isChecked() ? "8"
                : binding.na203i.isChecked() ? "9" : binding.na203j.isChecked() ? "10" : binding.na203k.isChecked() ? "11" : binding.na203l.isChecked() ? "12"
                : binding.na203m.isChecked() ? "13" : binding.na203n.isChecked() ? "14" : binding.na203o.isChecked() ? "15" : binding.na20398.isChecked() ? "98" : binding.na20396.isChecked() ? "96" : "0");

        MainApp.fmc.setResp(binding.respa.isChecked() ? "1" : binding.respb.isChecked() ? "2" : "0"); //respondent

//        Checking IsHead
        if (!MainApp.IsHead && binding.na203a.isChecked()) {
            MainApp.IsHead = true;
        }

        if (!MainApp.IsResp && binding.respa.isChecked()) {
            MainApp.IsResp = true;
            MainApp.gender = binding.na204.indexOfChild(findViewById(binding.na204.getCheckedRadioButtonId())) + 1;
        }

        MainApp.fmc.setAge(binding.cih2agey.getText().toString());
        if (Age < 6) {
            MainApp.fmc.setMotherId(mothersMap.get(binding.cih212.getSelectedItem().toString() + "_" + mothersSerials.get(mothersList.indexOf(binding.cih212.getSelectedItem().toString()) - 1)));
        }

        JSONObject sA2 = new JSONObject();

        sA2.put("cluster_no", MainApp.fc.getClusterNo());
        sA2.put("resp", MainApp.fmc.getResp().equals("0") ? "" : MainApp.fmc.getResp());
        sA2.put("cih2SerialNo", MainApp.fmc.getSerialNo());
        sA2.put("cih202", MainApp.fmc.getName());
        sA2.put("cih203", MainApp.fmc.getRealtionHH());
        sA2.put("cih204", binding.na204a.isChecked() ? "1" : binding.na204b.isChecked() ? "2" : "0");

        sA2.put("cih2doby", binding.cih2doby.getText().toString());
        sA2.put("cih2dobm", binding.cih2dobm.getText().toString());
        sA2.put("cih2dobd", binding.cih2dobd.getText().toString());

        sA2.put("cih206y", binding.cih2agey.getText().toString());

        if (binding.cih2doby.getText().toString().equals("9998")) {
            Age = Integer.valueOf(binding.cih2agey.getText().toString());
        } else {
            Age = (int) agebyDob;
        }

        sA2.put("age", String.valueOf(Age));


        sA2.put("cih207", binding.cih2msa.isChecked() ? "1" : binding.cih2msb.isChecked() ? "2" : binding.cih2msc.isChecked() ? "3" : binding.cih2msd.isChecked() ? "4"
                : binding.cih2mse.isChecked() ? "5" : "0");

        sA2.put("cih208", binding.cih2edua.isChecked() ? "1" : binding.cih2edub.isChecked() ? "2" : binding.cih2educ.isChecked() ? "3" : binding.cih2edud.isChecked() ? "4"
                : binding.cih2edue.isChecked() ? "5" : binding.cih2eduf.isChecked() ? "6" : binding.cih2edug.isChecked() ? "7" : binding.cih2eduh.isChecked() ? "8"
                : binding.cih2edui.isChecked() ? "9"
                : binding.cih2eduj.isChecked() ? "9"
                : binding.cih2edu98.isChecked() ? "98"
                : "0");

        sA2.put("cih209", binding.cih2occa.isChecked() ? "1" : binding.cih2occb.isChecked() ? "2" : binding.cih2occc.isChecked() ? "3" : binding.cih2occd.isChecked() ? "4"
                : binding.cih2occe.isChecked() ? "5" : binding.cih2occf.isChecked() ? "6" : binding.cih2occg.isChecked() ? "7" : binding.cih2occh.isChecked() ? "8"
                : binding.cih2occi.isChecked() ? "9" : binding.cih2occj.isChecked() ? "10" : binding.cih2occk.isChecked() ? "11" : binding.cih2occl.isChecked() ? "12"
                : binding.cih2occm.isChecked() ? "13" : binding.cih2occn.isChecked() ? "14" : binding.cih2occ98.isChecked() ? "98" : binding.cih2occ96.isChecked() ? "96" : "0");

        sA2.put("cih20996x", binding.cih2occ96x.getText().toString());

        sA2.put("cih209aa", binding.cih214a.isChecked() ? "1" : "0");
        sA2.put("cih209ab", binding.cih214b.isChecked() ? "2" : "0");
        sA2.put("cih209ac", binding.cih214c.isChecked() ? "3" : "0");
        sA2.put("cih209ad", binding.cih214d.isChecked() ? "4" : "0");
        sA2.put("cih209ae", binding.cih214e.isChecked() ? "5" : "0");
        sA2.put("cih209af", binding.cih214f.isChecked() ? "6" : "0");
        sA2.put("cih209ag", binding.cih214g.isChecked() ? "7" : "0");
        sA2.put("cih209ah", binding.cih214h.isChecked() ? "8" : "0");

        sA2.put("cih210", binding.cih210a.isChecked() ? "1" : binding.cih210b.isChecked() ? "2" : "0");

        sA2.put("cih211", fathersMap.get(binding.cih211.getSelectedItem().toString() + "_" + fathersSerials.get(fathersList.indexOf(binding.cih211.getSelectedItem().toString()) - 1)));
        sA2.put("cih212", mothersMap.get(binding.cih212.getSelectedItem().toString() + "_" + mothersSerials.get(mothersList.indexOf(binding.cih212.getSelectedItem().toString()) - 1)));

        MainApp.fmc.setsA2(String.valueOf(sA2));

        //Setting for FamilyMembers List
        MainApp.fmc.setMaritialStatus(binding.cih2msa.isChecked() ? "1" : binding.cih2msb.isChecked() ? "2" : binding.cih2msc.isChecked() ? "3" : binding.cih2msd.isChecked() ? "4"
                : binding.cih2mse.isChecked() ? "5" : "0");
        MainApp.fmc.setFatherName(binding.cih211.getSelectedItem().toString().toUpperCase());
        MainApp.fmc.setMotherName(binding.cih212.getSelectedItem().toString().toUpperCase());

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
        if (binding.cih210a.isChecked()) {
            MainApp.fmc.setAv("1");
        }

        //MWRA
        if ((Age >= 15 && Age < 50) && binding.na204b.isChecked()) {
            if (binding.cih2mse.isChecked()) {
                MainApp.membersCount.setWra(MainApp.membersCount.getWra() + 1);
                /*if (binding.cih210a.isChecked()) {
                    MainApp.fmc.setAv("1");
                }*/
            } else {
                MainApp.membersCount.setMwra(MainApp.membersCount.getMwra() + 1);
                /*if (binding.cih210a.isChecked()) {
                    MainApp.fmc.setAv("1");
                }*/

            }
            if (binding.cih210a.isChecked()) {
                MainApp.mwra.add(MainApp.fmc);
            }
            //MainApp.adolescents.add(MainApp.fmc);
        }

        //Adolescent
        if ((Age >= 10 && Age < 20) && binding.cih2mse.isChecked()) {
            memType = new HashMap<>();
            if (binding.na204a.isChecked()) {
                memType.put(1, Integer.valueOf(mem.get(2).get(1).toString()) + 1);
                memType.put(2, Integer.valueOf(mem.get(2).get(2).toString()));
            } else {
                memType.put(2, Integer.valueOf(mem.get(2).get(2).toString()) + 1);
                memType.put(1, Integer.valueOf(mem.get(2).get(1).toString()));
            }
            MainApp.membersCount.setMembers(2, memType);

            if (binding.cih210a.isChecked()) {
                // Add data in list
                MainApp.adolescents.add(MainApp.fmc);
            }

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
            /*if (binding.cih210a.isChecked()) {
                MainApp.fmc.setAv("1");
            }*/

            // Add data in list
            if (binding.cih210a.isChecked()) {
                if (Age < 2) {
                    MainApp.childUnder2.add(MainApp.fmc);
                    MainApp.childUnder2Check.add(MainApp.fmc);
                    MainApp.childUnder5.add(MainApp.fmc);
                    MainApp.childUnder5_Del.add(MainApp.fmc);
                } else {
                    MainApp.childUnder5.add(MainApp.fmc);
                    MainApp.childUnder5_Del.add(MainApp.fmc);
                }
            }

            if (MainApp.fmc.getMotherId().equals("00")) {
                if (binding.cih210a.isChecked())
                    MainApp.childNA.add(MainApp.fmc);

            } else {
                if (Age < 5)
                    MainApp.mwraChild.add(mothersChildMap.get(binding.cih212.getSelectedItem().toString() + "_" + mothersSerials.get(mothersList.indexOf(binding.cih212.getSelectedItem().toString()) - 1)));
            }

        }

        if (Age >= 15) {
            // Add data in list
            if (!binding.cih2mse.isChecked()) {
                MainApp.members_f_m.add(MainApp.fmc);
            }

            if (binding.cih210a.isChecked()) {
                MainApp.respList.add(MainApp.fmc);
            }
        }

        if (binding.cih210a.isChecked()) {
            if (Age >= 5 && Age <= 19) {
                MainApp.adolesUnderAge.add(MainApp.fmc);
            }
        }

        /*End*/

        MainApp.fmc.setAgeInYear(String.valueOf(Age));

        // Add data in list for all members
        MainApp.all_members.add(MainApp.fmc);

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

        if (!ValidatorClass.EmptyTextBox(this, binding.na202, getString(R.string.na202))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.na203, binding.na203a, getString(R.string.na203))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.na204, binding.na204a, getString(R.string.na204))) {
            return false;
        }

        if (!MainApp.IsResp) {
            return ValidatorClass.EmptyRadioButton(this, binding.resp, binding.respb, getString(R.string.resp));
        }

        if (!ValidatorClass.EmptyTextBox(this, binding.cih2doby, getString(R.string.cih2dob))) {
            return false;
        }


        if (!ValidatorClass.RangeTextBox(this, binding.cih2doby, DateUtils.getCurrentYear() - 95, DateUtils.getCurrentYear(), 9998, getString(R.string.cih2dob), " year")) {
            return false;
        }


        if (!ValidatorClass.EmptyTextBox(this, binding.cih2dobm, getString(R.string.cih2dob))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, binding.cih2dobm, 1, 12, 98, getString(R.string.cih2dob), " month")) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, binding.cih2dobd, getString(R.string.cih2dob))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, binding.cih2dobd, 1, 31, 98, getString(R.string.cih2dob), " day")) {
            return false;
        }

        Calendar today = Calendar.getInstance();
        if (dob.after(today)) {
            if (!ValidatorClass.RangeTextBoxforDate(this, binding.cih2dobd, 1, DateUtils.getCurrentDate(), 98, "Date can not be more than today")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBoxforDate(this, binding.cih2dobm, 1, DateUtils.getCurrentMonth(), 98, "Month can not be more than current Month")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBoxforDate(this, binding.cih2doby, DateUtils.getCurrentYear() - 95, DateUtils.getCurrentYear(), 9998, "Year can not be more than current year")) {
                return false;
            }
        }

        if (binding.cih2doby.getText().toString().equals("9998")) {

            if (!ValidatorClass.EmptyTextBox(this, binding.cih2agey, getString(R.string.na2age))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.cih2agey, 0, 95, 98, getString(R.string.na2age), " years")) {
                return false;
            }
        }

        if ((MainApp.fmc.getResp().equals("1") || MainApp.fmc.getRealtionHH().equals("1")) && Age < 18) {
            String chk = MainApp.fmc.getResp().equals("1") ? "Resp" : "Head";
            binding.cih2agey.setError("Error(Invalid) Age for " + chk);
            Toast.makeText(this, chk + " Age greater then or equal 18..", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            binding.cih2agey.setError(null);
        }

        if ((MainApp.fmc.getResp().equals("1") || MainApp.fmc.getRealtionHH().equals("1")) && Age < 18) {
            String chk = MainApp.fmc.getResp().equals("1") ? "Resp" : "Head";
            binding.cih2agey.setError("Error(Invalid) Age for " + chk);
            Toast.makeText(this, chk + " Age greater then or equal 18..", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            binding.cih2agey.setError(null);
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih2ms, binding.cih2msa, getString(R.string.cih2ms))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cih2edu, binding.cih2edua, getString(R.string.cih2edu))) {
            return false;
        }

        if (Age >= 10) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.cih2occ, binding.cih2occa, getString(R.string.cih2occ))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, binding.cih2occ, binding.cih2occ96, binding.cih2occ96x, getString(R.string.cih2occ))) {
                return false;
            }
        }

        if (userCountryTajik_Home) {
            if (!ValidatorClass.EmptyCheckBox(this, binding.fldGrpcih214check, binding.cih214a, getString(R.string.cih214))) {
                return false;
            }
        }

        if (!MainApp.IsResp) {
            if (!ValidatorClass.EmptyRadioButton(this, binding.cih210, binding.cih210a, getString(R.string.cih210))) {
                return false;
            }
        }

        if (Age < 5) {
            if (!ValidatorClass.EmptySpinner(this, binding.cih211, getString(R.string.cih211))) {
                return false;
            }
            return ValidatorClass.EmptySpinner(this, binding.cih212, getString(R.string.cih212));
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


