package edu.aku.hassannaqvi.casi_2018.ui;

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
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionA2Binding;
import edu.aku.hassannaqvi.casi_2018.other.DateUtils;
import edu.aku.hassannaqvi.casi_2018.validation.clearClass;
import edu.aku.hassannaqvi.casi_2018.validation.validatorClass;


public class SectionA2Activity extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private final long DELAY = 1000;
    private final long DELAY1 = 2000;
    ActivitySectionA2Binding binding;
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
    FamilyMembersContract family;
    Calendar dob = Calendar.getInstance();
    public TextWatcher age = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (binding.nh2dobd.getText().toString().length() == 2 && binding.nh2dobm.getText().toString().isEmpty()) {
                binding.nh2dobm.requestFocus();
            } else if (binding.nh2dobm.getText().toString().length() == 2 && binding.nh2doby.getText().toString().isEmpty()) {
                binding.nh2doby.requestFocus();
            } else if (binding.nh2doby.getText().toString().length() == 4 && binding.nh2dobm.getText().toString().isEmpty()) {
                binding.nh2dobm.requestFocus();
            }
            binding.nh2agey.setText(null);

            if (!binding.nh2dobd.getText().toString().isEmpty()
                    && !binding.nh2dobm.getText().toString().isEmpty()
                    && !binding.nh2doby.getText().toString().isEmpty()
                    && (Integer.valueOf(binding.nh2dobd.getText().toString()) <= 31 || Integer.valueOf(binding.nh2dobd.getText().toString()) == 98)
                    && (Integer.valueOf(binding.nh2dobm.getText().toString()) <= 12 || Integer.valueOf(binding.nh2dobm.getText().toString()) == 98)
                    && ((Integer.valueOf(binding.nh2doby.getText().toString()) > 1900
                    && Integer.valueOf(binding.nh2doby.getText().toString()) <= Calendar.getInstance().get(Calendar.YEAR))
                    || Integer.valueOf(binding.nh2doby.getText().toString()) == 9998)) {

                if (!binding.nh2dobd.getText().toString().equals("98") && !binding.nh2dobm.getText().toString().equals("98")
                        && !binding.nh2doby.getText().toString().equals("9998")) {

                    dob = DateUtils.getCalendarDate(binding.nh2dobd.getText().toString(), binding.nh2dobm.getText().toString(),
                            binding.nh2doby.getText().toString());

                    agebyDob = DateUtils.ageInYearByDOB(dob);
                    ageinMonths = DateUtils.ageInMonthsByDOB(dob);

                    binding.nh2agey.setEnabled(false);
                    binding.nh2agey.setText(agebyDob < 95 ? String.valueOf(agebyDob) : "95");

                } else if (!binding.nh2doby.getText().toString().equals("9998") && !binding.nh2dobm.getText().toString().equals("98")) {

                    dob = DateUtils.getCalendarDate(binding.nh2dobm.getText().toString(),
                            binding.nh2doby.getText().toString());
                    agebyDob = DateUtils.ageInYearByDOB(dob);
                    binding.nh2agey.setEnabled(false);
                    binding.nh2agey.setText(agebyDob < 95 ? String.valueOf(agebyDob) : "95");
                } else if (!binding.nh2doby.getText().toString().equals("9998")) {
                    agebyDob = DateUtils.ageInYearByDOB(binding.nh2doby.getText().toString());
                    binding.nh2agey.setEnabled(false);
                    binding.nh2agey.setText(agebyDob < 95 ? String.valueOf(agebyDob) : "95");
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
    Boolean head;
    //Calendar calDob = Calendar.getInstance();
    @BindViews({R.id.nh2doby, R.id.nh2dobm, R.id.nh2dobd})
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
    }

    public void skipPattern() {

        binding.na202.addTextChangedListener(this);
//        binding.na203.setOnCheckedChangeListener(this);
//        binding.na204.setOnCheckedChangeListener(this);
        binding.resp.setOnCheckedChangeListener(this);
        //binding.nh2ms.setOnCheckedChangeListener(this);
        binding.nh2edu.setOnCheckedChangeListener(this);
        binding.nh2occ.setOnCheckedChangeListener(this);
        binding.nh210.setOnCheckedChangeListener(this);


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

            MainApp.SetNameClass nameSet = new MainApp.SetNameClass(getString(R.string.nh2dob) + " " + family.getName());
            binding.setName(nameSet);

            binding.txtnh2dob.setText(binding.txtnh2dob.getText().toString().replace("Name", binding.selectedName.getText().toString()));
            binding.txtna2age.setText(binding.txtna2age.getText().toString().replace("Name", binding.selectedName.getText().toString()));
            binding.txtnh210.setText(binding.txtnh210.getText().toString().replace("Name", binding.selectedName.getText().toString()));


            binding.fldGrpA201.setVisibility(View.GONE);
            binding.fldGrpA202.setVisibility(View.VISIBLE);

        }

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

                       /* if (!flag) {
                            if (!family.getna204().equals("1")) {
                                binding.nh2occi.setEnabled(true);
                            } else {
                                binding.nh2occi.setEnabled(false);
                                binding.nh2occi.setChecked(false);
                            }
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
                        //binding.nh2occi.setEnabled(true);

                        /*if (!flag) {
                            if (!family.getna204().equals("1")) {
                                binding.nh2occi.setEnabled(true);
                            } else {
                                binding.nh2occi.setEnabled(false);
                                binding.nh2occi.setChecked(false);
                            }
                        }
*/
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
                        //binding.nh2occi.setEnabled(true);

                       /* if (!flag) {
                            if (!family.getna204().equals("1")) {
                                binding.nh2occi.setEnabled(true);
                            } else {
                                binding.nh2occi.setEnabled(false);
                                binding.nh2occi.setChecked(false);
                            }
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

                       /* if (!flag) {
                            if (!family.getna204().equals("1")) {
                                binding.nh2occi.setEnabled(true);
                            } else {
                                binding.nh2occi.setEnabled(false);
                                binding.nh2occi.setChecked(false);
                            }
                        }*/

                        //binding.nh2occi.setEnabled(true);
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

                        /*if (!flag) {
                            if (!family.getna204().equals("1")) {
                                binding.nh2occi.setEnabled(true);
                            } else {
                                binding.nh2occi.setEnabled(false);
                                binding.nh2occi.setChecked(false);
                            }
                        }*/

                        //binding.nh2occi.setEnabled(true);
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
                        //binding.nh2occi.setEnabled(true);

                       /* if (!flag) {
                            if (!family.getna204().equals("1")) {
                                binding.nh2occi.setEnabled(true);
                            } else {
                                binding.nh2occi.setEnabled(false);
                                binding.nh2occi.setChecked(false);
                            }
                        }
*/
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
                        //binding.nh2occi.setEnabled(true);

                        /*if (!flag) {
                            if (!family.getna204().equals("1")) {
                                binding.nh2occi.setEnabled(true);
                            } else {
                                binding.nh2occi.setEnabled(false);
                                binding.nh2occi.setChecked(false);
                            }
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


        if (!flag) {
            if (!family.getna204().equals("1")) {
                binding.nh2occi.setEnabled(true);
            } else {
                binding.nh2occi.setEnabled(false);
                binding.nh2occi.setChecked(false);
            }
        }
        binding.nh2edu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.nh2edua || checkedId == R.id.nh2edub || checkedId == R.id.nh2educ
                        || checkedId == R.id.nh2edud || checkedId == R.id.nh2edue || checkedId == R.id.nh2eduf) {
                    binding.nh2occf.setEnabled(false);
                    binding.nh2occf.setChecked(false);
                } else {
                    binding.nh2occf.setEnabled(true);
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
                        binding.na204c.setEnabled(false);
                        binding.na204c.setChecked(false);
                        binding.na204b.setEnabled(true);
                    } else {
                        binding.na204a.setEnabled(true);
                        binding.na204b.setEnabled(false);
                        binding.na204c.setEnabled(false);
                        binding.na204c.setChecked(false);
                        binding.na204b.setChecked(false);
                    }

                    binding.nh2mse.setChecked(false);
                    binding.nh2mse.setEnabled(false);

                } else {
                    binding.na204.clearCheck();
                    binding.na204a.setEnabled(true);
                    binding.na204b.setEnabled(true);
                    binding.na204c.setEnabled(true);
                }
            }
        });

        if (MainApp.othergender == 3) {
            clearClass.ClearAllFields(binding.fldGrpnh2ms, false);
        } else {
            clearClass.ClearAllFields(binding.fldGrpnh2ms, true);
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
                        .putExtra("respLineNo", MainApp.fmc.getSerialNo())
                        .putExtra("count", head));
                //}


            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

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
                    : binding.na204c.isChecked() ? "3" : "0");
            MainApp.fmc.setName(binding.na202.getText().toString());
            MainApp.fmc.setRealtionHH(binding.na203a.isChecked() ? "1" : binding.na203b.isChecked() ? "2" : binding.na203c.isChecked() ? "3" : binding.na203d.isChecked() ? "4"
                    : binding.na203e.isChecked() ? "5" : binding.na203f.isChecked() ? "6" : binding.na203g.isChecked() ? "7" : binding.na203h.isChecked() ? "8"
                    : binding.na203i.isChecked() ? "9" : binding.na203j.isChecked() ? "10" : binding.na203k.isChecked() ? "11" : binding.na203l.isChecked() ? "12"
                    : binding.na203m.isChecked() ? "13" : binding.na20398.isChecked() ? "98" : binding.na20396.isChecked() ? "96" : "0");

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

            //family.setDob(binding.nh2dob.getText().toString());
            family.setAge(binding.nh2agey.getText().toString()); //+ "/" + binding.nh2agem.getText().toString() + "/" + binding.nh2aged.getText().toString());
            if (Age < 6) {
                family.setMotherId(mothersMap.get(binding.nh212.getSelectedItem().toString() + "_" + mothersSerials.get(mothersList.indexOf(binding.nh212.getSelectedItem().toString()) - 1)));
            }


            JSONObject sA2 = new JSONObject();


            sA2.put("resp", family.getResp().equals("0") ? "" : family.getResp());
            sA2.put("nh2SerialNo", family.getSerialNo());
            sA2.put("nh202", family.getName());
            sA2.put("nh203", family.getRealtionHH());
            sA2.put("nh204", family.getna204());

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
                    : binding.nh2edu98.isChecked() ? "98" :
                    "0");

            sA2.put("nh209", binding.nh2occa.isChecked() ? "1" : binding.nh2occb.isChecked() ? "2" : binding.nh2occc.isChecked() ? "3" : binding.nh2occd.isChecked() ? "4"
                    : binding.nh2occe.isChecked() ? "5" : binding.nh2occf.isChecked() ? "6" : binding.nh2occg.isChecked() ? "7" : binding.nh2occh.isChecked() ? "8"
                    : binding.nh2occi.isChecked() ? "9" : binding.nh2occj.isChecked() ? "10" : binding.nh2occ96.isChecked() ? "96" : "0");

            sA2.put("nh20996x", binding.nh2occ96x.getText().toString());

            sA2.put("nh210", binding.nh210a.isChecked() ? "1" : binding.nh210b.isChecked() ? "2" : "0");

            sA2.put("nh211", fathersMap.get(binding.nh211.getSelectedItem().toString() + "_" + fathersSerials.get(fathersList.indexOf(binding.nh211.getSelectedItem().toString()) - 1)));
            sA2.put("nh212", mothersMap.get(binding.nh212.getSelectedItem().toString() + "_" + mothersSerials.get(mothersList.indexOf(binding.nh212.getSelectedItem().toString()) - 1)));


            family.setsA2(String.valueOf(sA2));

            //Setting for FamilyMembers List
            family.setMaritialStatus(binding.nh2msa.isChecked() ? "1" : binding.nh2msb.isChecked() ? "2" : binding.nh2msc.isChecked() ? "3" : binding.nh2msd.isChecked() ? "4"
                    : binding.nh2mse.isChecked() ? "5" : "0");
            family.setFatherName(binding.nh211.getSelectedItem().toString().toUpperCase());
            family.setMotherName(binding.nh212.getSelectedItem().toString().toUpperCase());

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
            if (binding.nh210a.isChecked()) {
                family.setAv("1");
            }


            //MWRA
            if ((Age >= 15 && Age < 50) && family.getna204().equals("2")) {
                if (binding.nh2mse.isChecked()) {
                    MainApp.membersCount.setWra(MainApp.membersCount.getWra() + 1);
                    /*if (binding.nh210a.isChecked()) {
                        family.setAv("1");
                    }*/
                } else {
                    MainApp.membersCount.setMwra(MainApp.membersCount.getMwra() + 1);
                    /*if (binding.nh210a.isChecked()) {
                        family.setAv("1");
                    }*/

                }
                if (binding.nh210a.isChecked()) {
                    MainApp.mwra.add(family);
                }
                //MainApp.adolescents.add(family);
            }
            //Adolescent
            if ((Age >= 10 && Age < 20) && binding.nh2mse.isChecked()) {
                memType = new HashMap<>();
                if (family.getna204().equals("1")) {
                    memType.put(1, Integer.valueOf(mem.get(2).get(1).toString()) + 1);
                    memType.put(2, Integer.valueOf(mem.get(2).get(2).toString()));
                } else {
                    memType.put(2, Integer.valueOf(mem.get(2).get(2).toString()) + 1);
                    memType.put(1, Integer.valueOf(mem.get(2).get(1).toString()));
                }
                MainApp.membersCount.setMembers(2, memType);

                // Add data in list
                MainApp.adolescents.add(family);
                /*if (binding.nh210a.isChecked()) {
                    family.setAv("1");
                }*/
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
                /*if (binding.nh210a.isChecked()) {
                    family.setAv("1");
                }*/

                // Add data in list
                if (Age < 2) {
                    MainApp.childUnder2.add(family);
                    MainApp.childUnder2Check.add(family);
                    MainApp.childUnder5.add(family);
                    MainApp.childUnder5_Del.add(family);
                } else {
                    MainApp.childUnder5.add(family);
                    MainApp.childUnder5_Del.add(family);

                    //MainApp.adolescents.add(family);
                }
                /*if (Age < 2) {
                    MainApp.childUnder2Check.add(family);
                }*/

                if (Age < 6 && family.getMotherId().equals("00")) {
                    MainApp.childNA.add(family);
                }
            }

            if (Age >= 15) {
                // Add data in list
                if (!binding.nh2mse.isChecked()) {
                    MainApp.members_f_m.add(family);
                }

                if (binding.nh210a.isChecked()) {
                    MainApp.respList.add(family);
                }
            }

            // Add data in list for all members
            MainApp.all_members.add(family);

            /*End*/

            family.setAgeInYear(String.valueOf(Age));

            MainApp.familyMembersList.set(position, family);

            // Add in fm clicked
            MainApp.familyMembersClicked.put(position, family);

        }

        //Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);

        if (flag) {

            long updcount = db.addFamilyMembers(MainApp.fmc);

            MainApp.fmc.set_ID(String.valueOf(updcount));

            if (updcount != 0) {
                //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

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
                //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean formValidation() {

        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (flag) {

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

        } else {

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
            if ((family.getResp().equals("1") || family.getRealtionHH().equals("1")) && Age < 18) {
                String chk = family.getResp().equals("1") ? "Resp" : "Head";
                binding.nh2agey.setError("Error(Invalid) Age for " + chk);
                Toast.makeText(this, chk + " Age greater then or equal 18..", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                binding.nh2agey.setError(null);
            }


            if ((family.getResp().equals("1") || family.getRealtionHH().equals("1")) && Age < 18) {
                String chk = family.getResp().equals("1") ? "Resp" : "Head";
                binding.nh2agey.setError("Error(Invalid) Age for " + chk);
                Toast.makeText(this, chk + " Age greater then or equal 18..", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                binding.nh2agey.setError(null);
            }
            //}

            if (MainApp.othergender != 3) {
                if (!validatorClass.EmptyRadioButton(this, binding.nh2ms, binding.nh2msa, getString(R.string.nh2ms))) {
                    return false;
                }
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
            if (!validatorClass.EmptyRadioButton(this, binding.nh210, binding.nh210a, getString(R.string.nh210))) {
                return false;
            }
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
        //formValidation();
    }
}


