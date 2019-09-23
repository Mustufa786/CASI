package edu.aku.hassannaqvi.casi_2019.ui.household;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionA2Binding;
import edu.aku.hassannaqvi.casi_2019.other.DateUtils;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

import static edu.aku.hassannaqvi.casi_2019.ui.household.SectionA1Activity.userCountryTajik_Home;


public class SectionA2Activity extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private final long DELAY = 1000;
    private final long DELAY1 = 2000;
    ActivitySectionA2Binding binding;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    Map<Integer, Map<Integer, Integer>> mem;
    List<String> mothersList, fathersList;

    List<String> mothersSerials, fathersSerials;
    Map<String, String> mothersMap, fathersMap;
    Map<String, FamilyMembersContract> motherChildMap;
    int position = 0;
    int Age = 0;
    long agebyDob = 0;
    long ageinMonths = 0;
    Boolean flag = false;
    FamilyMembersContract family;
    Calendar dob = Calendar.getInstance();
    public TextWatcher age = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (binding.cih2dobd.getText().toString().length() == 2 && binding.cih2dobm.getText().toString().isEmpty()) {
                binding.cih2dobm.requestFocus();
            } else if (binding.cih2dobm.getText().toString().length() == 2 && binding.cih2doby.getText().toString().isEmpty()) {
                binding.cih2doby.requestFocus();
            } else if (binding.cih2doby.getText().toString().length() == 4 && binding.cih2dobm.getText().toString().isEmpty()) {
                binding.cih2dobm.requestFocus();
            }
            binding.cih2agey.setText(null);

            if (!binding.cih2dobd.getText().toString().isEmpty()
                    && !binding.cih2dobm.getText().toString().isEmpty()
                    && !binding.cih2doby.getText().toString().isEmpty()
                    && (Integer.valueOf(binding.cih2dobd.getText().toString()) <= 31 || Integer.valueOf(binding.cih2dobd.getText().toString()) == 98)
                    && (Integer.valueOf(binding.cih2dobm.getText().toString()) <= 12 || Integer.valueOf(binding.cih2dobm.getText().toString()) == 98)
                    && ((Integer.valueOf(binding.cih2doby.getText().toString()) > 1900
                    && Integer.valueOf(binding.cih2doby.getText().toString()) <= Calendar.getInstance().get(Calendar.YEAR))
                    || Integer.valueOf(binding.cih2doby.getText().toString()) == 9998)) {

                if (!binding.cih2dobd.getText().toString().equals("98") && !binding.cih2dobm.getText().toString().equals("98")
                        && !binding.cih2doby.getText().toString().equals("9998")) {

                    dob = DateUtils.getCalendarDate(binding.cih2dobd.getText().toString(), binding.cih2dobm.getText().toString(),
                            binding.cih2doby.getText().toString());

                    agebyDob = DateUtils.ageInYearByDOB(dob);
                    ageinMonths = DateUtils.ageInMonthsByDOB(dob);

                    binding.cih2agey.setEnabled(false);
                    binding.cih2agey.setText(agebyDob < 95 ? String.valueOf(agebyDob) : "95");

                } else if (!binding.cih2doby.getText().toString().equals("9998") && !binding.cih2dobm.getText().toString().equals("98")) {

                    dob = DateUtils.getCalendarDate(binding.cih2dobm.getText().toString(),
                            binding.cih2doby.getText().toString());
                    agebyDob = DateUtils.ageInYearByDOB(dob);
                    binding.cih2agey.setEnabled(false);
                    binding.cih2agey.setText(agebyDob < 95 ? String.valueOf(agebyDob) : "95");
                } else if (!binding.cih2doby.getText().toString().equals("9998")) {
                    agebyDob = DateUtils.ageInYearByDOB(binding.cih2doby.getText().toString());
                    binding.cih2agey.setEnabled(false);
                    binding.cih2agey.setText(agebyDob < 95 ? String.valueOf(agebyDob) : "95");
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
    Boolean head;
    //Calendar calDob = Calendar.getInstance();
    @BindViews({R.id.cih2doby, R.id.cih2dobm, R.id.cih2dobd})
    List<EditText> grpdob;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_a2);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_a2);
        ButterKnife.bind(this);
        binding.setCallback(this);
        setupViews();
        skipPattern();
        //setHeading();
        this.setTitle(getResources().getString(R.string.na2subHeading));

        head = MainApp.familyMembersList.size() == 0;

//        Validation Boolean
        MainApp.validateFlag = false;

        if (head) {
            binding.na203a.setEnabled(true);
            binding.na203b.setEnabled(false);
            binding.na203c.setEnabled(false);
            binding.na203d.setEnabled(false);
            binding.na203e.setEnabled(false);
            binding.na203f.setEnabled(false);
            binding.na203g.setEnabled(false);
            binding.na203h.setEnabled(false);
            binding.na203i.setEnabled(false);
            binding.na203j.setEnabled(false);
            binding.na203k.setEnabled(false);
            binding.na203l.setEnabled(false);
            binding.na203m.setEnabled(false);
            binding.na203n.setEnabled(false);
            binding.na203o.setEnabled(false);
            binding.na20396.setEnabled(false);
            binding.na20398.setEnabled(false);
        }

        if (!flag) {
            binding.cih2occi.setEnabled(family.getna204().equals("2"));
            if (family.getRealtionHH().equals("2")) {
                binding.cih2msb.setEnabled(false);
                binding.cih2msc.setEnabled(false);
                binding.cih2msd.setEnabled(false);
                binding.cih2mse.setEnabled(false);
            }
        }

        /*For Tajik*/
        binding.fldGrpcih214.setVisibility(userCountryTajik_Home ? View.VISIBLE : View.GONE);
        binding.cih2occ98.setVisibility(userCountryTajik_Home ? View.VISIBLE : View.GONE);
        binding.cih2occ96.setVisibility(userCountryTajik_Home ? View.GONE : View.VISIBLE);
        binding.cih2occa.setVisibility(userCountryTajik_Home ? View.GONE : View.VISIBLE);
        binding.cih2occh.setVisibility(userCountryTajik_Home ? View.GONE : View.VISIBLE);

        binding.cih2occl.setVisibility(userCountryTajik_Home ? View.VISIBLE : View.GONE);
        binding.cih2occm.setVisibility(userCountryTajik_Home ? View.VISIBLE : View.GONE);
        binding.cih2occn.setVisibility(userCountryTajik_Home ? View.VISIBLE : View.GONE);

        binding.na203o.setVisibility(userCountryTajik_Home ? View.GONE : View.VISIBLE);

    }

    public void skipPattern() {

        binding.na202.addTextChangedListener(this);
//        binding.na203.setOnCheckedChangeListener(this);
//        binding.na204.setOnCheckedChangeListener(this);
        binding.resp.setOnCheckedChangeListener(this);
        //binding.cih2ms.setOnCheckedChangeListener(this);
        binding.cih2edu.setOnCheckedChangeListener(this);
        binding.cih2occ.setOnCheckedChangeListener(this);
        binding.cih210.setOnCheckedChangeListener(this);


    }

    public void setupViews() {

//        Getting Intent
        flag = getIntent().getBooleanExtra("flag", false);

        if (flag) {
            binding.fldGrpA201.setVisibility(View.VISIBLE);
            binding.fldGrpA202.setVisibility(View.GONE);
        } else {

            family = (FamilyMembersContract) getIntent().getSerializableExtra("data");
            position = getIntent().getIntExtra("pos", 0);

            binding.selectedName.setText(family.getName().toUpperCase());

            MainApp.SetNameClass nameSet = new MainApp.SetNameClass(getString(R.string.cih2dob) + " " + family.getName());
            binding.setName(nameSet);

            binding.txtcih2dob.setText(binding.txtcih2dob.getText().toString().replace("Name", binding.selectedName.getText().toString()));
            binding.txtna2age.setText(binding.txtna2age.getText().toString().replace("Name", binding.selectedName.getText().toString()));
            binding.txtcih210.setText(binding.txtcih210.getText().toString().replace("Name", binding.selectedName.getText().toString()));


            binding.fldGrpA201.setVisibility(View.GONE);
            binding.fldGrpA202.setVisibility(View.VISIBLE);

        }

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

                    if (userCountryTajik_Home) {
                        if (Age < 7) {
                            binding.cih2occl.setEnabled(true);
                            binding.cih2educ.setEnabled(false);
                            binding.cih2edud.setEnabled(false);
                        } else if (Age >= 7 && Age < 18)
                            binding.cih2occm.setEnabled(true);
                        else if (Age >= 18 && Age < 50)
                            binding.cih2occn.setEnabled(true);
                        else {
                            binding.cih2occn.setEnabled(true);
                            binding.cih2occj.setEnabled(true);
                        }
                    }

                    if (Age >= 50) {
                        binding.cih2occj.setEnabled(true);
                    }

                    if (Age >= 14) {
                        if (!family.getna204().equals("1")) {
                            binding.cih2occi.setEnabled(true);
                        } else {
                            binding.cih2occi.setEnabled(false);
                            binding.cih2occi.setChecked(false);
                        }
                    }

                    // Setting Dropdowns
                    /*mothersList = new ArrayList<>();
                    mothersSerials = new ArrayList<>();
                    mothersMap = new HashMap<>();
                    motherChildMap = new HashMap<>();

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
                        if (Integer.valueOf(family.getAge()) < 15) continue;
                        if (mem.getna204().equals("1")) {
                            fathersList.add(mem.getName());
                            fathersSerials.add(mem.getSerialNo());
                            fathersMap.put(mem.getName() + "_" + mem.getSerialNo(), mem.getSerialNo());
                        } else {
                            mothersList.add(mem.getName());
                            mothersSerials.add(mem.getSerialNo());
                            mothersMap.put(mem.getName() + "_" + mem.getSerialNo(), mem.getSerialNo());

                            motherChildMap.put(mem.getName() + "_" + mem.getSerialNo(), mem);
                        }
                    }

                    binding.cih211.setAdapter(new ArrayAdapter<>(SectionA2Activity.this, R.layout.item_style, fathersList));
                    binding.cih212.setAdapter(new ArrayAdapter<>(SectionA2Activity.this, R.layout.item_style, mothersList));
*/
                    if (Age > 5) {

                        binding.cih211.setSelection(1);
                        binding.cih212.setSelection(1);

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        /*if (!flag) {
            if (!family.getna204().equals("1")) {
                binding.cih2occi.setEnabled(true);
            } else {
                binding.cih2occi.setEnabled(false);
                binding.cih2occi.setChecked(false);
            }
        }*/
        binding.cih2edu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cih2edua || checkedId == R.id.cih2edub || checkedId == R.id.cih2educ
                        || checkedId == R.id.cih2edud || checkedId == R.id.cih2edue || checkedId == R.id.cih2eduf) {
                    binding.cih2occf.setEnabled(false);
                    binding.cih2occf.setChecked(false);
                } else {
                    binding.cih2occf.setEnabled(true);
                }
            }
        });


//        Getting Members with types
        mem = MainApp.membersCount.getMembers();

//        Setting Dropdowns
        mothersList = new ArrayList<>();
        mothersSerials = new ArrayList<>();
        mothersMap = new HashMap<>();
        motherChildMap = new HashMap<>();

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

                motherChildMap.put(mem.getName() + "_" + mem.getSerialNo(), mem);
            }
        }

        binding.cih211.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, fathersList));
        binding.cih212.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, mothersList));

//        Visibility for isHead
        if (MainApp.IsHead) {
            binding.na203a.setEnabled(false);
        } else {
            binding.na203a.setEnabled(true);
        }

        if (MainApp.IsResp) {
            binding.fldGrpA20101.setVisibility(View.GONE);
        }

        if (MainApp.gender == 3) {
            binding.na203b.setEnabled(false);
        } else {
            binding.na203b.setEnabled(true);
        }

        binding.na203.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.na203b) {
                    if (MainApp.gender == 1) {
                        binding.na204a.setEnabled(false);
                        binding.na204a.setChecked(false);
//                        binding.na204c.setEnabled(false);
//                        binding.na204c.setChecked(false);
                        binding.na204b.setEnabled(true);
                    } else {
                        binding.na204a.setEnabled(true);
                        binding.na204b.setEnabled(false);
//                        binding.na204c.setEnabled(false);
//                        binding.na204c.setChecked(false);
                        binding.na204b.setChecked(false);
                    }

                    binding.cih2mse.setChecked(false);
                    binding.cih2mse.setEnabled(false);

                } else {
                    binding.na204.clearCheck();
                    binding.na204a.setEnabled(true);
                    binding.na204b.setEnabled(true);
//                    binding.na204c.setEnabled(true);
                }
            }
        });

        if (MainApp.othergender == 3) {
            ClearClass.ClearAllFields(binding.fldGrpcih2ms, null);
        } else {
            ClearClass.ClearAllFields(binding.fldGrpcih2ms, null);
        }


    }

    public void BtnEnd() {
        if (flag) {
            MainApp.endActivityAll(this, this, SectionA2ListActivity.class, true);
        } else {
//            MainApp.endActivityAll(this, SectionA2Activity.this, SectionA2ListActivity.class, true);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage("Do you want to Exit??")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    finish();
                                    for (int pos = 0; pos < MainApp.hhClicked.size(); pos++) {
                                        if (MainApp.hhClicked.get(pos) == position) {
                                            MainApp.hhClicked.remove(pos);
                                        }
                                    }

                                    Intent end_intent = new Intent(getApplicationContext(), SectionA2ListActivity.class);
                                    end_intent.putExtra("complete", true);
                                    startActivity(end_intent);
                                }
                            });
            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();

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


                finish();

                startActivity(new Intent(this, SectionA2ListActivity.class)
                        .putExtra("respChecking", binding.respa.isChecked())
                        .putExtra("respLineNo", MainApp.fmc.getSerialNo())
                        .putExtra("count", head));
                //}


            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() throws JSONException {


        if (flag) {
            MainApp.serial_no++;

            MainApp.fmc = new FamilyMembersContract();

            MainApp.fmc.setDevicetagID(MainApp.fc.getDevicetagID());
            MainApp.fmc.setFormDate(MainApp.fc.getFormDate());
            MainApp.fmc.setUser(MainApp.fc.getUser());
            MainApp.fmc.setDeviceId(MainApp.fc.getDeviceID());
            MainApp.fmc.setApp_ver(MainApp.fc.getAppversion());
            MainApp.fmc.set_UUID(MainApp.fc.getUID());
            MainApp.fmc.setEnmNo(MainApp.fc.getClusterNo());
            MainApp.fmc.setHhNo(MainApp.fc.getHhNo());

            MainApp.fmc.setSerialNo(String.valueOf(MainApp.serial_no));
            MainApp.fmc.setna204(binding.na204a.isChecked() ? "1" : binding.na204b.isChecked() ? "2"
                    : "0");
            MainApp.fmc.setName(binding.na202.getText().toString());
            MainApp.fmc.setRealtionHH(binding.na203a.isChecked() ? "1" : binding.na203b.isChecked() ? "2" : binding.na203c.isChecked() ? "3" : binding.na203d.isChecked() ? "4"
                    : binding.na203e.isChecked() ? "5" : binding.na203f.isChecked() ? "6" : binding.na203g.isChecked() ? "7" : binding.na203h.isChecked() ? "8"
                    : binding.na203i.isChecked() ? "9" : binding.na203j.isChecked() ? "10" : binding.na203k.isChecked() ? "11" : binding.na203l.isChecked() ? "12"
                    : binding.na203m.isChecked() ? "13" : binding.na203n.isChecked() ? "14" : binding.na203o.isChecked() ? "15" : binding.na20398.isChecked() ? "98" : binding.na20396.isChecked() ? "96" : "0");

            MainApp.fmc.setResp(binding.respa.isChecked() ? "1" : binding.respb.isChecked() ? "2" : "0"); //respondent

            MainApp.familyMembersList.add(MainApp.fmc);

//        Checking IsHead
            if (!MainApp.IsHead && binding.na203a.isChecked()) {
                MainApp.IsHead = true;
                MainApp.gender = binding.na204.indexOfChild(findViewById(binding.na204.getCheckedRadioButtonId())) + 1;

            }

            /*if (!MainApp.IsResp && binding.respa.isChecked()) {
                MainApp.IsResp = true;
            }*/

            if (binding.respa.isChecked()) {
                MainApp.IsResp = true;
            }

            MainApp.othergender = binding.na204.indexOfChild(findViewById(binding.na204.getCheckedRadioButtonId())) + 1;

        } else {

            //family.setDob(binding.cih2dob.getText().toString());
            family.setAge(binding.cih2agey.getText().toString()); //+ "/" + binding.cih2agem.getText().toString() + "/" + binding.cih2aged.getText().toString());
            if (Age < 6) {
                family.setMotherId(mothersMap.get(binding.cih212.getSelectedItem().toString() + "_" + mothersSerials.get(mothersList.indexOf(binding.cih212.getSelectedItem().toString()) - 1)));
            }


            JSONObject sA2 = new JSONObject();


            sA2.put("resp", family.getResp().equals("0") ? "" : family.getResp());
            sA2.put("cih2SerialNo", family.getSerialNo());
            sA2.put("cih202", family.getName());
            sA2.put("cih203", family.getRealtionHH());
            sA2.put("cih204", family.getna204());

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
                    : binding.cih2eduj.isChecked() ? "10"
                    : binding.cih2edu98.isChecked() ? "98" :
                    "0");

            sA2.put("cih209", binding.cih2occa.isChecked() ? "1" : binding.cih2occb.isChecked() ? "2" : binding.cih2occc.isChecked() ? "3" : binding.cih2occd.isChecked() ? "4"
                    : binding.cih2occe.isChecked() ? "5" : binding.cih2occf.isChecked() ? "6" : binding.cih2occg.isChecked() ? "7" : binding.cih2occh.isChecked() ? "8"
                    : binding.cih2occi.isChecked() ? "9" : binding.cih2occj.isChecked() ? "10" : binding.cih2occk.isChecked() ? "11" : binding.cih2occl.isChecked() ? "12"
                    : binding.cih2occm.isChecked() ? "13" : binding.cih2occn.isChecked() ? "14" : binding.cih2occ98.isChecked() ? "98" : binding.cih2occ96.isChecked() ? "96" : "0");

            sA2.put("cih20996x", binding.cih2occ96x.getText().toString());

            sA2.put("cih214", binding.cih214a.isChecked() ? "1" : binding.cih214b.isChecked() ? "2" : binding.cih214c.isChecked() ? "3" : binding.cih214d.isChecked() ? "4"
                    : binding.cih214e.isChecked() ? "5" : binding.cih214f.isChecked() ? "6" : binding.cih214g.isChecked() ? "7" : binding.cih214h.isChecked() ? "8" : "0");

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


            family.setsA2(String.valueOf(sA2));

            //Setting for FamilyMembers List
            family.setMaritialStatus(binding.cih2msa.isChecked() ? "1" : binding.cih2msb.isChecked() ? "2" : binding.cih2msc.isChecked() ? "3" : binding.cih2msd.isChecked() ? "4"
                    : binding.cih2mse.isChecked() ? "5" : "0");
            family.setFatherName(binding.cih211.getSelectedItem().toString().toUpperCase());
            family.setMotherName(binding.cih212.getSelectedItem().toString().toUpperCase());

            /*Functionality Setting*/

//        Calculation
            Map<Integer, Integer> memType = new HashMap<>();

            //Total
            if (family.getna204().equals("1")) {
                memType.put(1, Integer.valueOf(mem.get(1).get(1).toString()) + 1);
                memType.put(2, Integer.valueOf(mem.get(1).get(2).toString()));
            } else {
                memType.put(2, Integer.valueOf(mem.get(1).get(2).toString()) + 1);
                memType.put(1, Integer.valueOf(mem.get(1).get(1).toString()));
            }

            MainApp.membersCount.setMembers(1, memType);

            // Set availability
            if (binding.cih210a.isChecked()) {
                family.setAv("1");
            }

            //MWRA
            if ((Age >= 15 && Age < 50) && family.getna204().equals("2")) {
                if (binding.cih2mse.isChecked()) {
                    MainApp.membersCount.setWra(MainApp.membersCount.getWra() + 1);
                } else {
                    MainApp.membersCount.setMwra(MainApp.membersCount.getMwra() + 1);
                }
                if (binding.cih210a.isChecked()) {
                    MainApp.mwra.add(family);
                }
            }
            //Adolescent
            if ((Age >= 10 && Age < 20) && binding.cih2mse.isChecked()) {
                memType = new HashMap<>();
                if (family.getna204().equals("1")) {
                    memType.put(1, Integer.valueOf(mem.get(2).get(1).toString()) + 1);
                    memType.put(2, Integer.valueOf(mem.get(2).get(2).toString()));
                } else {
                    memType.put(2, Integer.valueOf(mem.get(2).get(2).toString()) + 1);
                    memType.put(1, Integer.valueOf(mem.get(2).get(1).toString()));
                }
                MainApp.membersCount.setMembers(2, memType);

                if (binding.cih210a.isChecked()) {
                    // Add data in list
                    MainApp.adolescents.add(family);
                }
            }
            //Children < 5
            else if (Age < 6) {
                memType = new HashMap<>();
                if (family.getna204().equals("1")) {
                    memType.put(1, Integer.valueOf(mem.get(3).get(1).toString()) + 1);
                    memType.put(2, Integer.valueOf(mem.get(3).get(2).toString()));
                } else {
                    memType.put(2, Integer.valueOf(mem.get(3).get(2).toString()) + 1);
                    memType.put(1, Integer.valueOf(mem.get(3).get(1).toString()));
                }
                MainApp.membersCount.setMembers(3, memType);

                // Add data in list

                if (binding.cih210a.isChecked()) {
                    if (Age < 2) {
                        MainApp.childUnder2.add(family);
                        MainApp.childUnder2Check.add(family);
                        MainApp.childUnder5.add(family);
                        MainApp.childUnder5_Del.add(family);
                    } else {
                        MainApp.childUnder5.add(family);
                        MainApp.childUnder5_Del.add(family);
                    }
                }

                if (family.getMotherId().equals("00")) {
                    if (binding.cih210a.isChecked()) {
                        MainApp.childNA.add(family);
                    }
                } else {
                    if (Age < 5)
                        MainApp.mwraChild.add(motherChildMap.get(binding.cih212.getSelectedItem().toString() + "_" + mothersSerials.get(mothersList.indexOf(binding.cih212.getSelectedItem().toString()) - 1)));
                }


            }

            if (Age >= 15) {
                // Add data in list
                if (!binding.cih2mse.isChecked()) {
                    MainApp.members_f_m.add(family);
                }

                if (binding.cih210a.isChecked()) {
                    MainApp.respList.add(family);
                }
            }

            if (binding.cih210a.isChecked()) {
                if (Age >= 5 && Age <= 19) {
                    MainApp.adolesUnderAge.add(family);
                }
            }

            /*End*/

            family.setAgeInYear(String.valueOf(Age));

            // Add data in list for all members
            MainApp.all_members.add(family);

            MainApp.familyMembersList.set(position, family);

            // Add in fm clicked
            MainApp.familyMembersClicked.put(position, family);

        }

    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);

        if (flag) {

            long updcount = db.addFamilyMembers(MainApp.fmc);

            MainApp.fmc.set_ID(String.valueOf(updcount));

            if (updcount != 0) {


                MainApp.fmc.set_UID(
                        (MainApp.fmc.getDeviceId() + MainApp.fmc.get_ID()));
                db.updateFamilyMemberID();
                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            int updcount = db.updateFamilyMember(family);

            if (updcount != 0) {

                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean formValidation() {


        if (flag) {

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

        } else {

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
            if ((family.getResp().equals("1") || family.getRealtionHH().equals("1")) && Age < 18) {
                String chk = family.getResp().equals("1") ? "Resp" : "Head";
                binding.cih2agey.setError("Error(Invalid) Age for " + chk);
                Toast.makeText(this, chk + " Age greater then or equal 18..", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                binding.cih2agey.setError(null);
            }


            if ((family.getResp().equals("1") || family.getRealtionHH().equals("1")) && Age < 18) {
                String chk = family.getResp().equals("1") ? "Resp" : "Head";
                binding.cih2agey.setError("Error(Invalid) Age for " + chk);
                Toast.makeText(this, chk + " Age greater then or equal 18..", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                binding.cih2agey.setError(null);
            }
            //}

            if (MainApp.othergender != 3) {
                if (!ValidatorClass.EmptyRadioButton(this, binding.cih2ms, binding.cih2msa, getString(R.string.cih2ms))) {
                    return false;
                }
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

            if (!ValidatorClass.EmptyRadioButton(this, binding.cih210, binding.cih210a, getString(R.string.cih210))) {
                return false;
            }
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
        //formValidation();
    }
}


