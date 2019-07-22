package edu.aku.hassannaqvi.casi_2019.ui.childs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.TimerTask;

import butterknife.BindViews;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONB1ModelClass;
import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONC1ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.ChildContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.contracts.MWRAContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionC1Binding;
import edu.aku.hassannaqvi.casi_2019.other.DateUtils;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.household.SectionA2ListActivity;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.AddMember_MenuActivity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionC1Activity extends AddMember_MenuActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    public static int counter = 1;
    public static int counterPerMom = 0;
    public static int counterPerNA = 0;
    public static String selectedChildName = "";
    public static String motherName = "";
    public static String careTaker = "";
    public static String editMotherName = "";
    public static boolean isNA;
    public static int Childsize = 0;
    public static int NAChildsize = 0;
    public static Boolean editChildFlag;
    public static List<String> childU5;
    static Map<String, FamilyMembersContract> childMap;
    static long ageInMontsbyDob = 0;
    private final long DELAY = 2000;
    Map<String, String> respMap;
    ArrayList<String> respName;
    ActivitySectionC1Binding binding;
    DatabaseHelper db;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    Boolean endflag = false;
    long agebyDob = 0;
    Calendar dob = Calendar.getInstance();
    @BindViews({R.id.cic201d, R.id.cic201m, R.id.cic201y})
    List<EditText> grpDate;
    Boolean backPressed = false;
    Boolean frontPressed = false;
    JSONC1ModelClass jsonC1;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_c1);
        ButterKnife.bind(this);

        this.setTitle(getResources().getString(R.string.cic1heading));
        db = new DatabaseHelper(this);
        respName = new ArrayList<>();
        respName.add("....");
        respMap = new HashMap<>();
        //childMap = new HashMap<>();

//        Assigning data to UI binding
        binding.setCallback(this);

//        Validation Boolean
        MainApp.validateFlag = false;

        setupSkips();

        editChildFlag = getIntent().getBooleanExtra("editForm", false);

        if (editChildFlag && getIntent().getBooleanExtra("checkflag", false)) {

            autoPopulateFields(getIntent().getStringExtra("formUid"), getIntent().getStringExtra("fmUid"));
            backPressed = true;

        } else {
            setupViews();
        }
    }

    public void setupSkips() {

        for (EditText ed : grpDate) {
            ed.addTextChangedListener(this);
        }

        //======= Checking Q201, 202 and 203
        binding.cic203.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.cic204aa.setEnabled(false);
                binding.cic204ab.setEnabled(false);
                binding.cic204ba.setEnabled(false);
                binding.cic204bb.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!binding.cic203.getText().toString().isEmpty()) {
                    if (ageInMontsbyDob == Integer.valueOf(binding.cic203.getText().toString())) {
                        binding.cic204aa.setChecked(true);
                        binding.cic204ba.setChecked(true);
                    } else {
                        binding.cic204ab.setChecked(true);
                        binding.cic204bb.setChecked(true);
                    }
                }
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
//                                        formValidation();
                                    }
                                    //}
                                });

                            }
                        },
                        DELAY
                );


            }
        });

        binding.cic202.setOnCheckedChangeListener(this);
        binding.cic205.setOnCheckedChangeListener(this);

        binding.na11801.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.na11801b) {
                    binding.cic202.clearCheck();
                    binding.cic204a.clearCheck();
                    binding.cic204b.clearCheck();
                    binding.cic205.clearCheck();
                }
            }
        });

    }

    private void setupViews() {
        if (getIntent().getBooleanExtra("reBackComing", true)) {
            if (getIntent().getBooleanExtra("childFlag", false)) {
                counter++;

            } else {

                counter = 1;
                counterPerMom = 0;
                counterPerNA = 0;

                childU5 = new ArrayList<>();
                childMap = new HashMap<>();

                childU5.add("....");

                if (isNA) {
                    for (FamilyMembersContract fmc : MainApp.childUnder5_Del) {
                        childMap.put(fmc.getName() + "-" + fmc.getSerialNo(), fmc);
                        childU5.add(fmc.getName() + "-" + fmc.getSerialNo());
                        counterPerNA++;
                    }

                    NAChildsize = MainApp.childUnder5_Del.size();
                    binding.fldGrpresp.setVisibility(View.VISIBLE);
                    binding.txtCounter.setVisibility(View.GONE);

                } else {
                    for (FamilyMembersContract fmc : MainApp.childUnder5) {
                        if (fmc.getMotherId().equals(MainApp.mc.getB1SerialNo())) {
                            childMap.put(fmc.getName() + "-" + fmc.getSerialNo(), fmc);
                            childU5.add(fmc.getName() + "-" + fmc.getSerialNo());
                            counterPerMom++;
                        }
                    }

                    Childsize = MainApp.childUnder5.size();
                    binding.fldGrpresp.setVisibility(View.GONE);
                    binding.txtCounter.setVisibility(View.VISIBLE);
                }
            }
        } else {

            if (counterPerMom == 0 && counterPerNA == 0) {
                counter = 1;
                counterPerMom = 0;
                counterPerNA = 0;

                childU5 = new ArrayList<>();
                childMap = new HashMap<>();

                childU5.add("....");
            }

            if (isNA) {
                for (int i = NAChildsize; i < MainApp.childNA.size(); i++) {
                    childMap.put(MainApp.childNA.get(i).getName() + "-" + MainApp.childNA.get(i).getSerialNo(), MainApp.childNA.get(i));
                    childU5.add(MainApp.childNA.get(i).getName() + "-" + MainApp.childNA.get(i).getSerialNo());
                    counterPerNA++;
                }


                binding.fldGrpresp.setVisibility(View.VISIBLE);
//                NAChildsize = MainApp.childNA.size();
            } else {
                for (int i = Childsize; i < MainApp.childUnder5.size(); i++) {
                    childMap.put(MainApp.childUnder5.get(Childsize).getName() + "-" + MainApp.childUnder5.get(Childsize).getSerialNo(), MainApp.childUnder5.get(Childsize));
                    childU5.add(MainApp.childUnder5.get(Childsize).getName() + "-" + MainApp.childUnder5.get(Childsize).getSerialNo());
                    counterPerMom++;
                }

                binding.fldGrpresp.setVisibility(View.GONE);


//                Childsize = MainApp.childUnder5.size();
            }
        }


        for (FamilyMembersContract fmc : MainApp.respList) {
            respName.add(fmc.getName() + "-" + fmc.getSerialNo());
            respMap.put(fmc.getName() + "-" + fmc.getSerialNo(), fmc.getSerialNo());
        }

        binding.resp.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, respName));

        binding.resp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (binding.resp.getSelectedItemPosition() != 0) {
                    careTaker = binding.resp.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // setup head
        if (!isNA) {
            binding.txtCounter.setVisibility(View.VISIBLE);
            binding.txtCounter.setText("Child " + counter + " out of " + counterPerMom +
                    "\n\n " + SectionB1Activity.wraName + " : " + getString(R.string.cih212a));
        } else {
            binding.txtCounter.setVisibility(View.GONE);
            binding.txtCounter.setText("Child " + counter + " out of " + counterPerNA
                    + "\n\n " + motherName + " : " + getString(R.string.cih212a));


        }

        // setup spinner
        binding.cic101.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, childU5));

        binding.cic101.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (binding.cic101.getSelectedItemPosition() != 0) {
                    selectedChildName = binding.cic101.getSelectedItem().toString();
                    //motherName = childMap.get(binding.cic101.getSelectedItem().toString()).getMotherName();


                    binding.txtcic202.setText(binding.txtcic202.getText().toString().replace("Name", binding.cic101.getSelectedItem().toString()));
                    binding.txtcic203.setText(binding.txtcic203.getText().toString().replace("Name", binding.cic101.getSelectedItem().toString()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void autoPopulateFields(String uuid, String uid) {

        MainApp.cc = db.getsC1(uuid, uid);
        binding.resp.setVisibility(View.GONE);
        binding.respa.setVisibility(View.VISIBLE);
        binding.cic101.setVisibility(View.GONE);
        binding.cic101a.setVisibility(View.VISIBLE);

        if (!MainApp.cc.getsC1().equals("")) {

            jsonC1 = JSONUtilClass.getModelFromJSON(MainApp.cc.getsC1(), JSONC1ModelClass.class);
            binding.cic201y.setText(jsonC1.getcic201y());
            binding.cic201m.setText(jsonC1.getcic201m());
            binding.cic201d.setText(jsonC1.getcic201d());
            binding.cic203.setText(jsonC1.getcic203());

            MainApp.cc.setClusterno(jsonC1.getCluster_no());
            MainApp.cc.setHhno(jsonC1.getHhno());

            if (!jsonC1.getcih11801().equals("0")) {
                binding.na11801.check(
                        jsonC1.getcih11801().equals("1") ? binding.na11801a.getId() :
                                binding.na11801b.getId()
                );
            }

            if (!jsonC1.getcic202().equals("0")) {
                binding.cic202.check(
                        jsonC1.getcic202().equals("1") ? binding.cic202a.getId() :
                                jsonC1.getcic202().equals("2") ? binding.cic202b.getId()
                                        : binding.cic202c.getId()
                );
            }

            if (MainApp.cc.getMUID().equals("00")) {
                binding.respa.setText(jsonC1.getRespName());
                editMotherName = "Not Available";
            } else {
                MWRAContract mwraContract = db.getWRANameByUid(MainApp.cc.getMUID(), MainApp.cc.getUUID());
                if (!mwraContract.get_UID().equals("")) {
                    JSONB1ModelClass jsonB1 = JSONUtilClass.getModelFromJSON(mwraContract.getsB1(), JSONB1ModelClass.class);
                    binding.respa.setText(jsonB1.getciw101().split("-")[0]);
                    editMotherName = jsonB1.getciw101().split("-")[0];
                }
            }

            binding.cic101a.setText(jsonC1.getcic101());

            selectedChildName = jsonC1.getcic101().split("-")[0];

            if (!jsonC1.getcic204a().equals("0")) {
                binding.cic204a.check(
                        jsonC1.getcic204a().equals("1") ? binding.cic204aa.getId() :
                                binding.cic204ab.getId()
                );
            }

            if (!jsonC1.getcic204b().equals("0")) {
                binding.cic204b.check(
                        jsonC1.getcic204a().equals("1") ? binding.cic204ba.getId() :
                                binding.cic204bb.getId()
                );
            }

            if (!jsonC1.getcic205().equals("0")) {
                binding.cic205.check(
                        jsonC1.getcic205().equals("1") ? binding.cic205a.getId() :
                                jsonC1.getcic205().equals("2") ? binding.cic205b.getId() :
                                        binding.cic20598.getId()
                );
            }
        }

    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }

    //
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

//                finish();

                frontPressed = true;

                if (!editChildFlag) {
                    if (isNA) {
                        NAChildsize = MainApp.childNA.size();
                    } else {
                        Childsize = MainApp.childUnder5.size();
                    }
                }

                if (ageInMontsbyDob < 24) {
                    startActivity(new Intent(this, SectionC2Activity.class)
                            .putExtra("selectedChild", editChildFlag ? getIntent().getSerializableExtra("childFMClass") :
                                    childMap.get(binding.cic101.getSelectedItem().toString()))
                            .putExtra("backPressed", backPressed));

                } else if (ageInMontsbyDob >= 24 && ageInMontsbyDob < 60) {
                    startActivity(new Intent(this, SectionC5Activity.class)
                            .putExtra("selectedChild", editChildFlag ? getIntent().getSerializableExtra("childFMClass") :
                                    childMap.get(binding.cic101.getSelectedItem().toString()))
                            .putExtra("backPressed", backPressed));

                } else if (ageInMontsbyDob >= 60) {

                    if (editChildFlag) {
                        finish();
                        startActivity(new Intent(this, ViewMemberActivity.class)
                                .putExtra("flagEdit", false)
                                .putExtra("comingBack", true)
                                .putExtra("cluster", MainApp.cc.getClusterno())
                                .putExtra("hhno", MainApp.cc.getHhno())
                        );
                    } else {
                        startActivity(new Intent(this, ChildEndingActivity.class)
                                .putExtra("childINEligibile", true));
                    }

                }

               /* startActivity(new Intent(this, SectionC5Activity.class)
                        .putExtra("selectedChild", childMap.get(binding.cic101.getSelectedItem().toString())));*/
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {

//        Validation Boolean
        MainApp.validateFlag = true;

        endflag = true;
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {


                //finish();
                if (editChildFlag) {
                    finish();
                    startActivity(new Intent(this, ViewMemberActivity.class)
                            .putExtra("flagEdit", false)
                            .putExtra("comingBack", true)
                            .putExtra("cluster", MainApp.cc.getClusterno())
                            .putExtra("hhno", MainApp.cc.getHhno())
                    );
                } else {
                    MainApp.endChildActivity(this, this, false);
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean formValidation() {


//        cic101

        if (endflag) {
            if (!isNA) {
                return editChildFlag || ValidatorClass.EmptySpinner(this, binding.cic101, getString(R.string.cic101));
            } else {

                if (!editChildFlag) {
                    if (!ValidatorClass.EmptySpinner(this, binding.resp, getString(R.string.resp))) {
                        return false;
                    }
                    return ValidatorClass.EmptySpinner(this, binding.cic101, getString(R.string.cic101));
                }
                return true;
            }
        } else {

            if (isNA) {
                if (!editChildFlag) {
                    if (!ValidatorClass.EmptySpinner(this, binding.resp, getString(R.string.resp))) {
                        return false;
                    }
                }
            }

            if (!editChildFlag) {
                if (!ValidatorClass.EmptySpinner(this, binding.cic101, getString(R.string.cic101))) {
                    return false;
                }
            }

            if (!ValidatorClass.EmptyRadioButton(this, binding.na11801, binding.na11801b, getString(R.string.na11801))) {
                return false;
            }

            if (binding.na11801a.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, binding.cic201y, getString(R.string.cic201))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.cic201y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), getString(R.string.cic201), " years")) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, binding.cic201m, getString(R.string.cic201))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.cic201m, 1, 12, getString(R.string.cic201), " months")) {
                    return false;
                }


                if (!ValidatorClass.EmptyTextBox(this, binding.cic201d, getString(R.string.cic201))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.cic201d, 1, 31, 98, getString(R.string.cic201), " days")) {
                    return false;
                }


                Calendar today = Calendar.getInstance();

                Calendar sixYears = Calendar.getInstance();
                sixYears.add(Calendar.DAY_OF_YEAR, -2190);

                if (dob.before(sixYears)) {
                    if (!ValidatorClass.RangeTextBoxforDate(this, binding.cic201d, 1, DateUtils.getCurrentDate(), 98, "Date can not be more than today")) {
                        return false;
                    }

                    if (!ValidatorClass.RangeTextBoxforDate(this, binding.cic201m, 1, DateUtils.getCurrentMonth(), "Month can not be more than current month")) {
                        return false;
                    }

                    if (!ValidatorClass.RangeTextBoxforDate(this, binding.cic201y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), "Year can not be more than current year")) {
                        return false;
                    }
                }


                if (dob.after(today)) {
                    if (!ValidatorClass.RangeTextBoxforDate(this, binding.cic201d, 1, DateUtils.getCurrentDate(), 98, "Date can not be more than today")) {
                        return false;
                    }

                    if (!ValidatorClass.RangeTextBoxforDate(this, binding.cic201m, 1, DateUtils.getCurrentMonth(), "Month can not be more than current month")) {
                        return false;
                    }

                    if (!ValidatorClass.RangeTextBoxforDate(this, binding.cic201y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), "Year can not be more than current year")) {
                        return false;
                    }

                }

                if (!ValidatorClass.EmptyRadioButton(this, binding.cic202, binding.cic202a, getString(R.string.cic202))) {
                    return false;
                }

                if (ageInMontsbyDob < 12 && !binding.cic202a.isChecked()) {
                    Toast.makeText(this, "ERROR(invalid): " + "Select correct option.. Age is less than 1 year" + getString(R.string.cic202), Toast.LENGTH_LONG).show();
                    binding.cic202a.setError("Select correct option.. Age is less than 1 year");

                    Log.i(SectionC1Activity.class.getSimpleName(), "cic202" + ": invalid");
                    return false;
                } else {
                    binding.cic202a.setError(null);
                }

                if ((ageInMontsbyDob > 12 && ageInMontsbyDob < 24) && !binding.cic202b.isChecked()) {
                    Toast.makeText(this, "ERROR(invalid): " + "Select correct option.. Age is greater than 1 year" + getString(R.string.cic202), Toast.LENGTH_LONG).show();
                    binding.cic202b.setError("Select correct option.. Age is greater than 1 year");

                    Log.i(SectionC1Activity.class.getSimpleName(), "cic202" + ": invalid");
                    return false;
                } else {
                    binding.cic202b.setError(null);
                }

                if ((ageInMontsbyDob > 24 &&
                        ageInMontsbyDob < 72) && !binding.cic202c.isChecked()) {
                    Toast.makeText(this, "ERROR(invalid): " + "Select correct option.. Age is greater than 2 years" + getString(R.string.cic202), Toast.LENGTH_LONG).show();
                    binding.cic202c.setError("Select correct option.. Age is greater than 2 years");

                    Log.i(SectionC1Activity.class.getSimpleName(), "cic202" + ": invalid");
                    return false;
                } else {
                    binding.cic202c.setError(null);
                }

                if (!ValidatorClass.EmptyTextBox(this, binding.cic203, getString(R.string.cic203))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, binding.cic203, 0, 72, getString(R.string.cic203), " months")) {
                    return false;
                }

                if (ageInMontsbyDob != Integer.valueOf(binding.cic203.getText().toString())) {
                    Toast.makeText(this, "ERROR(invalid): " + "Check age and dob again" + getString(R.string.cic203), Toast.LENGTH_LONG).show();
                    binding.cic203.setError("Please check age and dob again..");

                    Log.i(SectionC1Activity.class.getSimpleName(), "cic203" + ": invalid");
                    return false;
                } else {
                    binding.cic203.setError(null);
                }

            /*if (!ValidatorClass.EmptyRadioButton(this, binding.cic204a, binding.cic204aa, getString(R.string.cic204a))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, binding.cic204b, binding.cic204ba, getString(R.string.cic204b))) {
                return false;
            }*/

                if (!ValidatorClass.EmptyRadioButton(this, binding.cic205, binding.cic205a, getString(R.string.cic205))) {
                    return false;
                }

                if (ageInMontsbyDob < 24 && !binding.cic205a.isChecked()) {
                    Toast.makeText(this, "ERROR(invalid): " + "Select correct option according to age in months" + getString(R.string.cic205), Toast.LENGTH_LONG).show();
                    binding.cic205a.setError("Select correct option according to age in months");

                    Log.i(SectionC1Activity.class.getSimpleName(), "cic205" + ": invalid");
                    return false;
                } else {
                    binding.cic205a.setError(null);
                }

                if (ageInMontsbyDob >= 24 && !binding.cic205b.isChecked()) {
                    Toast.makeText(this, "ERROR(invalid): " + "Select correct option according to age in months" + getString(R.string.cic205), Toast.LENGTH_LONG).show();
                    binding.cic205b.setError("Select correct option according to age in months");

                    Log.i(SectionC1Activity.class.getSimpleName(), "cic205" + ": invalid");
                    return false;
                } else {
                    binding.cic205b.setError(null);
                }
            }

        }

        return true;

    }

    private void SaveDraft() throws JSONException {


        JSONObject sC1 = new JSONObject();

        if (!backPressed) {
            MainApp.cc = new ChildContract();
            MainApp.cc.setDevicetagID(MainApp.fc.getDevicetagID());
            MainApp.cc.setFormDate(MainApp.fc.getFormDate());
            MainApp.cc.setUser(MainApp.fc.getUser());
            MainApp.cc.setDeviceID(MainApp.fc.getDeviceID());
            MainApp.cc.setAppversion(MainApp.fc.getAppversion());
            MainApp.cc.setUUID(MainApp.fc.getUID());
            MainApp.cc.setFMUID(childMap.get(binding.cic101.getSelectedItem().toString()).get_UID());
            MainApp.cc.setC1SerialNo(childMap.get(binding.cic101.getSelectedItem().toString()).getSerialNo());
            if (childMap.get(binding.cic101.getSelectedItem().toString()).getMotherId().equals("00")) {
                MainApp.cc.setMUID("00");

            } else {

                if (MainApp.mc == null) {
                    MainApp.cc.setMUID("00");
                } else {
                    MainApp.cc.setMUID(MainApp.mc.get_UID());
                }

            }

            selectedChildName = binding.cic101.getSelectedItem().toString();

            sC1.put("cluster_no", MainApp.fc.getClusterNo());
            sC1.put("hhno", MainApp.fc.getHhNo());
            if (isNA) {
                sC1.put("respName", binding.resp.getSelectedItem().toString());
                sC1.put("resp_lno", respMap.get(binding.resp.getSelectedItem().toString()));
            } else {
                sC1.put("wra_lno", childMap.get(binding.cic101.getSelectedItem().toString()).getMotherId());
            }
            sC1.put("cic101", binding.cic101.getSelectedItem().toString());

        } else {
            sC1.put("updatedate_cic1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
//            MainApp.cc.setUID(MainApp.cc.getUID());

            if (editChildFlag && !frontPressed) {
                sC1.put("edit_updatedate_cic1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

                sC1.put("cluster_no", jsonC1.getCluster_no());
                sC1.put("hhno", jsonC1.getHhno());
                if (MainApp.cc.getMUID().equals("00")) {
                    sC1.put("respName", jsonC1.getRespName());
                    sC1.put("resp_lno", jsonC1.getRespSerial());
                } else {
                    sC1.put("wra_lno", jsonC1.getWra_lno());
                }
                sC1.put("cic101", jsonC1.getcic101());

            } else if (editChildFlag) {
                sC1.put("cluster_no", jsonC1.getCluster_no());
                sC1.put("hhno", jsonC1.getHhno());
                if (MainApp.cc.getMUID().equals("00")) {
                    sC1.put("respName", jsonC1.getRespName());
                    sC1.put("resp_lno", jsonC1.getRespSerial());
                } else {
                    sC1.put("wra_lno", jsonC1.getWra_lno());
                }
                sC1.put("cic101", jsonC1.getcic101());

            } else {

                selectedChildName = binding.cic101.getSelectedItem().toString();

                sC1.put("cluster_no", MainApp.fc.getClusterNo());
                sC1.put("hhno", MainApp.fc.getHhNo());
                if (isNA) {
                    sC1.put("respName", binding.resp.getSelectedItem().toString());
                    sC1.put("resp_lno", respMap.get(binding.resp.getSelectedItem().toString()));
                }
                sC1.put("cic101", binding.cic101.getSelectedItem().toString());

            }
        }


        sC1.put("cic11801", binding.na11801a.isChecked() ? "1" : binding.na11801b.isChecked() ? "2" : "0");
//        cic103
        sC1.put("cic201d", binding.cic201d.getText().toString());
        sC1.put("cic201m", binding.cic201m.getText().toString());
        sC1.put("cic201y", binding.cic201y.getText().toString());


        sC1.put("cic202", binding.cic202a.isChecked() ? "1"
                : binding.cic202b.isChecked() ? "2"
                : binding.cic202c.isChecked() ? "3"
                : "0");

        sC1.put("cic203", binding.cic203.getText().toString());

        sC1.put("cic204a", binding.cic204aa.isChecked() ? "1"
                : binding.cic204ab.isChecked() ? "2"
                : "0");

        sC1.put("cic204b", binding.cic204ba.isChecked() ? "1"
                : binding.cic204bb.isChecked() ? "2"
                : "0");

        sC1.put("cic205", binding.cic205a.isChecked() ? "1"
                : binding.cic205b.isChecked() ? "2"
                : binding.cic20598.isChecked() ? "98"
                : "0");


        MainApp.cc.setsC1(String.valueOf(sC1));


    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        if (!backPressed) {
            Long updcount = db.addChildForm(MainApp.cc, 0);
            MainApp.cc.set_ID(String.valueOf(updcount));

            if (updcount != 0) {


                MainApp.cc.setUID(
                        (MainApp.cc.getDeviceID() + MainApp.cc.get_ID()));
                db.updateFormChildID();

                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Long updcount = db.addChildForm(MainApp.cc, 1);

            if (updcount != 0) {
                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (frontPressed) {
            backPressed = true;
        }

        if (backPressed) {
            binding.cic101.setEnabled(false);
            binding.btnAddMember.setVisibility(View.GONE);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (!binding.cic201d.getText().toString().isEmpty() && !binding.cic201m.getText().toString().isEmpty()
                && !binding.cic201y.getText().toString().isEmpty()) {

            if (!binding.cic201d.getText().toString().equals("98")) {
                dob = DateUtils.getCalendarDate(binding.cic201d.getText().toString(),
                        binding.cic201m.getText().toString(), binding.cic201y.getText().toString());
                agebyDob = DateUtils.ageInYearByDOB(dob);
                ageInMontsbyDob = DateUtils.ageInMonthsByDOB(dob);
                binding.txtAge.setText("Current Age is : " + ageInMontsbyDob + "months");


            } else {
                dob = DateUtils.getCalendarDate(binding.cic201m.getText().toString(), binding.cic201y.getText().toString());
                agebyDob = DateUtils.ageInYearByDOB(dob);
                ageInMontsbyDob = DateUtils.ageInMonthsByDOB(dob);
                binding.txtAge.setText("Current Age is : " + ageInMontsbyDob + "months");

            }


        }

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
                            //}
                        });

                    }
                },
                DELAY
        );

    }

    public void BtnAddMember() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SectionC1Activity.this);
        alertDialogBuilder
                .setMessage("Are you sure to add missing member?")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                if (isNA) {
                                    NAChildsize = MainApp.childNA.size();
                                } else {
                                    Childsize = MainApp.childUnder5.size();
                                }

                                finish();
                                startActivity(new Intent(SectionC1Activity.this, SectionA2ListActivity.class)
                                        .putExtra("reBack", true)
                                        .putExtra("reBackChild", isNA)
                                );
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        formValidation();
    }
}
