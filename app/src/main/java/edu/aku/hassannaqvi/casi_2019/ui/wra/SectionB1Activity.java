package edu.aku.hassannaqvi.casi_2019.ui.wra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
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
import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONB1ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.contracts.MWRAContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionB1Binding;
import edu.aku.hassannaqvi.casi_2019.other.DateUtils;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.household.SectionA2ListActivity;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.AddMember_MenuActivity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionB1Activity extends AddMember_MenuActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    public static String wraName = "";
    public static int WRAcounter = 1;
    public static int WRAsize = 0;
    public static Boolean editWRAFlag;
    public static Boolean childisUnder2AndAlive;
    static Map<String, FamilyMembersContract> wraMap;
    public static ArrayList<String> lstMwra;
    static Boolean childCheck = false;
    private final long DELAY = 1000;
    ArrayList<String> respName;
    Map<String, String> respMap;
    ActivitySectionB1Binding bi;
    DatabaseHelper db;
    Boolean backPressed = false;
    Boolean frontPressed = false;
    String classPassName = "";
    JSONB1ModelClass jsonB1;
    int prevMiscarriages = 0;
    int prevDeliveries = 0;
    Calendar dob = Calendar.getInstance();
    long agebyDob = 0;
    public TextWatcher age = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!bi.ciw201days.getText().toString().isEmpty() && !bi.ciw201months.getText().toString().isEmpty()
                    && !bi.ciw201years.getText().toString().isEmpty()) {

                if (!bi.ciw201days.getText().toString().equals("98") && !bi.ciw201months.getText().toString().equals("98")
                        && !bi.ciw201years.getText().toString().equals("9998")) {

                    dob = DateUtils.getCalendarDate(bi.ciw201days.getText().toString(), bi.ciw201months.getText().toString(),
                            bi.ciw201years.getText().toString());

                    agebyDob = DateUtils.ageInYearByDOB(dob);

                    bi.txtAge.setVisibility(View.VISIBLE);
                    bi.txtAge.setText("Age by Date of Birth : " + agebyDob + " years");

                } else if (!bi.ciw201years.getText().toString().equals("9998") &&
                        !bi.ciw201months.getText().toString().equals("98")) {

                    dob = DateUtils.getCalendarDate(bi.ciw201months.getText().toString(),
                            bi.ciw201years.getText().toString());
                    agebyDob = DateUtils.ageInYearByDOB(dob);

                    bi.txtAge.setVisibility(View.VISIBLE);
                    bi.txtAge.setText("Age by Date of Birth : " + agebyDob + " years");

                } else if (!bi.ciw201years.getText().toString().equals("9998")) {
                    agebyDob = DateUtils.ageInYearByDOB(bi.ciw201years.getText().toString());

                    bi.txtAge.setVisibility(View.VISIBLE);
                    bi.txtAge.setText("Age by Date of Birth : " + agebyDob + " years");

                } else {
                    bi.txtAge.setVisibility(View.GONE);
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };
    @BindViews({R.id.ciw21001, R.id.ciw21002})
    List<RadioGroup> ciw210a;
    @BindViews({R.id.ciw21003, R.id.ciw21098, R.id.ciw21099})
    List<RadioGroup> ciw210b;
    @BindViews({R.id.ciw21001a, R.id.ciw21002a})
    List<RadioButton> ciw210aYes;
    @BindViews({R.id.ciw21003a, R.id.ciw21098a, R.id.ciw21099a})
    List<RadioButton> ciw210bYes;
    Boolean endflag = false;
    String LOG_TAG = SectionB1Activity.class.getName();
    @BindViews({R.id.ciw201days, R.id.ciw201months, R.id.ciw201years})
    List<EditText> grpDob;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_b1);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b1);
        ButterKnife.bind(this);
        db = new DatabaseHelper(this);
        childisUnder2AndAlive = false;
        //Assigning data to UI binding
        bi.setCallback(this);
        bi.ciw203a.setEnabled(false);
        bi.ciw203b.setEnabled(false);

        this.setTitle(getResources().getString(R.string.nbheading));


//        setupViews();


//        Validation Boolean
        MainApp.validateFlag = false;

        setupSkips();

        editWRAFlag = getIntent().getBooleanExtra("editForm", false);

        if (editWRAFlag) {

            if (getIntent().getBooleanExtra("checkflag", true)) {
                AutoPopulate(getIntent().getStringExtra("formUid"), getIntent().getStringExtra("fmUid"));
                backPressed = true;
            } else {

            }

        } else {
            setupViews();
        }
    }

    public void setupSkips() {

        bi.ciw202.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!bi.ciw202.getText().toString().isEmpty()) {
                    bi.curAge.setText("Current Age is: " + bi.ciw202.getText().toString() + " years");
                    bi.curAge1.setText("Current Age is: " + bi.ciw202.getText().toString() + " years");
                    if (Integer.valueOf(bi.ciw202.getText().toString()) >= 15 && Integer.valueOf(bi.ciw202.getText().toString()) < 50) {
                        bi.ciw203a.setChecked(true);
                        bi.ciw203b.setChecked(false);
                    } else {
                        bi.ciw203b.setChecked(true);
                        bi.ciw203a.setChecked(false);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//============================================ Skip Patterns =======================================


        for (EditText ed : grpDob) {
            ed.addTextChangedListener(age);
        }
        bi.ciw202.addTextChangedListener(this);

        bi.ciw203.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.ciw203a.isChecked()) {
                    bi.ciw204a.setEnabled(true);
                    bi.ciw204b.setEnabled(true);
                    bi.ciw205a.setEnabled(true);
                    bi.ciw205b.setEnabled(true);
                    bi.ciw206.setEnabled(true);
                    bi.ciw207a.setEnabled(true);
                    bi.ciw207b.setEnabled(true);
                    bi.ciw208a.setEnabled(true);
                    bi.ciw208b.setEnabled(true);
                    bi.ciw209a.setEnabled(true);
                    bi.ciw209b.setEnabled(true);
                    bi.ciw21001a.setEnabled(true);
                    bi.ciw21002a.setEnabled(true);
                    bi.ciw21003a.setEnabled(true);
                    bi.ciw21098a.setEnabled(true);
                    bi.ciw21099a.setEnabled(true);
                    bi.ciw21001b.setEnabled(true);
                    bi.ciw21002b.setEnabled(true);
                    bi.ciw21003b.setEnabled(true);
                    bi.ciw21098b.setEnabled(true);
                    bi.ciw21099b.setEnabled(true);
                    bi.ciw211.setEnabled(true);
                    bi.ciw212.setEnabled(true);
                    bi.ciw214.setEnabled(true);
                    bi.ciw215.setEnabled(true);
                    /*bi.ciw216a.setEnabled(true);
                    bi.ciw216b.setEnabled(true);
                    bi.ciw216aa.setEnabled(true);*/
                    bi.ciw213.setEnabled(true);

                } else {
                    bi.ciw204a.setEnabled(false);
                    bi.ciw204b.setEnabled(false);
                    bi.ciw204.clearCheck();
                    bi.ciw205a.setEnabled(false);
                    bi.ciw205b.setEnabled(false);
                    bi.ciw205.clearCheck();
                    bi.ciw206.setEnabled(false);
                    bi.ciw206.setText(null);
                    bi.ciw207a.setEnabled(false);
                    bi.ciw207b.setEnabled(false);
                    bi.ciw207.clearCheck();
                    bi.ciw208a.setEnabled(false);
                    bi.ciw208b.setEnabled(false);
                    bi.ciw208.clearCheck();
                    bi.ciw209a.setEnabled(false);
                    bi.ciw209b.setEnabled(false);
                    bi.ciw209.clearCheck();
                    bi.ciw21001.clearCheck();
                    bi.ciw21002.clearCheck();
                    bi.ciw21003.clearCheck();
                    bi.ciw21098.clearCheck();
                    bi.ciw21099.clearCheck();
                    bi.ciw21001a.setEnabled(false);
                    bi.ciw21002a.setEnabled(false);
                    bi.ciw21003a.setEnabled(false);
                    bi.ciw21098a.setEnabled(false);
                    bi.ciw21099a.setEnabled(false);
                    bi.ciw21001b.setEnabled(false);
                    bi.ciw21002b.setEnabled(false);
                    bi.ciw21003b.setEnabled(false);
                    bi.ciw21098b.setEnabled(false);
                    bi.ciw21099b.setEnabled(false);
                    bi.ciw211.setEnabled(false);
                    bi.ciw211.setText(null);
                    bi.ciw212.setEnabled(false);
                    bi.ciw212.setText(null);
                    bi.ciw214.setEnabled(false);
                    bi.ciw215.setEnabled(false);
                  /*  bi.ciw216a.setEnabled(false);
                    bi.ciw216b.setEnabled(false);
                    bi.ciw216.clearCheck();
                    bi.ciw216aa.setEnabled(false);
                    bi.ciw216aa.setText(null);*/
                    bi.ciw213.setEnabled(false);
                    bi.ciw213.setText(null);

                }
            }
        });

        /*bi.ciw216.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (bi.ciw216a.isChecked()) {
                    bi.ciw216aa.setEnabled(true);
                } else {
                    bi.ciw216aa.setEnabled(false);
                    bi.ciw216aa.setText(null);
                }
            }
        });
*/
        bi.ciw204.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.ciw204a.isChecked()) {
                    bi.ciw205a.setEnabled(false);
                    bi.ciw205b.setEnabled(false);
                    bi.ciw205.clearCheck();
                    bi.ciw206.setEnabled(true);
                    bi.ciw207a.setEnabled(true);
                    bi.ciw207b.setEnabled(true);
                    bi.ciw208a.setEnabled(true);
                    bi.ciw208b.setEnabled(true);
                    bi.ciw209a.setEnabled(true);
                    bi.ciw209b.setEnabled(true);
                    bi.ciw21001a.setEnabled(true);
                    bi.ciw21002a.setEnabled(true);
                    bi.ciw21003a.setEnabled(true);
                    bi.ciw21098a.setEnabled(true);
                    bi.ciw21099a.setEnabled(true);
                    bi.ciw21001b.setEnabled(true);
                    bi.ciw21002b.setEnabled(true);
                    bi.ciw21003b.setEnabled(true);
                    bi.ciw21098b.setEnabled(true);
                    bi.ciw21099b.setEnabled(true);
                    bi.ciw211.setEnabled(true);
                    bi.ciw212.setEnabled(true);
                    bi.ciw214.setEnabled(true);
                    bi.ciw215.setEnabled(true);
                   /* bi.ciw216a.setEnabled(true);
                    bi.ciw216b.setEnabled(true);
                    bi.ciw216aa.setEnabled(true);*/
                    bi.ciw213.setEnabled(true);

                } else {

                    bi.ciw205a.setEnabled(true);
                    bi.ciw205b.setEnabled(true);
                    bi.ciw206.setEnabled(false);
                    bi.ciw206.setText(null);
                    bi.ciw207a.setEnabled(false);
                    bi.ciw207b.setEnabled(false);
                    bi.ciw207.clearCheck();
                    bi.ciw208a.setEnabled(false);
                    bi.ciw208b.setEnabled(false);
                    bi.ciw208.clearCheck();
                    bi.ciw209a.setEnabled(false);
                    bi.ciw209b.setEnabled(false);
                    bi.ciw209.clearCheck();
                    bi.ciw21001.clearCheck();
                    bi.ciw21002.clearCheck();
                    bi.ciw21003.clearCheck();
                    bi.ciw21098.clearCheck();
                    bi.ciw21099.clearCheck();
                    bi.ciw21001.setEnabled(false);
                    bi.ciw21002.setEnabled(false);
                    bi.ciw21003.setEnabled(false);
                    bi.ciw21098.setEnabled(false);
                    bi.ciw21099.setEnabled(false);
                    bi.ciw211.setEnabled(false);
                    bi.ciw211.setText(null);
                    bi.ciw212.setEnabled(false);
                    bi.ciw212.setText(null);
                    bi.ciw214.setEnabled(false);
                    bi.ciw215.setEnabled(false);
                    /*bi.ciw216a.setEnabled(false);
                    bi.ciw216b.setEnabled(false);
                    bi.ciw216.clearCheck();
                    bi.ciw216aa.setEnabled(false);
                    bi.ciw216aa.setText(null);*/
                    bi.ciw213.setEnabled(false);
                    bi.ciw213.setText(null);

                }
            }
        });

        bi.ciw205.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.ciw205a.isChecked()) {
                    bi.ciw206.setEnabled(true);
                    bi.ciw207a.setEnabled(true);
                    bi.ciw207b.setEnabled(true);
                    bi.ciw211.setEnabled(true);
                    bi.ciw212.setEnabled(true);
                    bi.ciw215.setEnabled(true);
                    bi.ciw214.setEnabled(true);
                    /*bi.ciw216a.setEnabled(true);
                    bi.ciw216b.setEnabled(true);
                    bi.ciw216aa.setEnabled(true);*/
                    bi.ciw215.setEnabled(true);
                    bi.ciw213.setEnabled(true);
                    bi.ciw208a.setEnabled(true);
                    bi.ciw208b.setEnabled(true);
                    bi.ciw209a.setEnabled(true);
                    bi.ciw209b.setEnabled(true);
//                    w213
                    bi.ciw21001a.setEnabled(true);
                    bi.ciw21002a.setEnabled(true);
                    bi.ciw21003a.setEnabled(true);
                    bi.ciw21098a.setEnabled(true);
                    bi.ciw21099a.setEnabled(true);
                    bi.ciw21001b.setEnabled(true);
                    bi.ciw21002b.setEnabled(true);
                    bi.ciw21003b.setEnabled(true);
                    bi.ciw21098b.setEnabled(true);
                    bi.ciw21099b.setEnabled(true);

                } else {
                    bi.ciw206.setEnabled(false);
                    bi.ciw206.setText(null);
                    bi.ciw207a.setEnabled(false);
                    bi.ciw207b.setEnabled(false);
                    bi.ciw207.clearCheck();
                    bi.ciw211.setEnabled(false);
                    bi.ciw211.setText(null);
                    bi.ciw212.setEnabled(false);
                    bi.ciw212.setText(null);
                    bi.ciw215.setEnabled(false);
                    bi.ciw214.setEnabled(false);
                    /*bi.ciw216a.setEnabled(false);
                    bi.ciw216b.setEnabled(false);
                    bi.ciw216.clearCheck();
                    bi.ciw216aa.setEnabled(false);
                    bi.ciw216aa.setText(null);*/
                    bi.ciw213.setEnabled(false);
                    bi.ciw213.setText(null);
                    bi.ciw208a.setEnabled(false);
                    bi.ciw208b.setEnabled(false);
                    bi.ciw208.clearCheck();
                    bi.ciw209a.setEnabled(false);
                    bi.ciw209b.setEnabled(false);
                    bi.ciw209.clearCheck();
                    bi.ciw21001.clearCheck();
                    bi.ciw21002.clearCheck();
                    bi.ciw21003.clearCheck();
                    bi.ciw21098.clearCheck();
                    bi.ciw21099.clearCheck();
                    bi.ciw21001a.setEnabled(false);
                    bi.ciw21002a.setEnabled(false);
                    bi.ciw21003a.setEnabled(false);
                    bi.ciw21098a.setEnabled(false);
                    bi.ciw21099a.setEnabled(false);
                    bi.ciw21001b.setEnabled(false);
                    bi.ciw21002b.setEnabled(false);
                    bi.ciw21003b.setEnabled(false);
                    bi.ciw21098b.setEnabled(false);
                    bi.ciw21099b.setEnabled(false);

                }
            }
        });

        bi.ciw206.addTextChangedListener(this);

        bi.ciw207.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (checkedId == R.id.ciw207a) {

                    bi.ciw211.setEnabled(true);
                    bi.ciw212.setEnabled(true);
                    bi.ciw215.setEnabled(true);
                    bi.ciw214.setEnabled(true);
                   /* bi.ciw216a.setEnabled(true);
                    bi.ciw216b.setEnabled(true);
                    bi.ciw216aa.setEnabled(true);*/
                    bi.ciw213.setEnabled(true);
                    bi.ciw208a.setEnabled(true);
                    bi.ciw208b.setEnabled(true);
                    bi.ciw209a.setEnabled(true);
                    bi.ciw209b.setEnabled(true);
                    bi.ciw21001a.setEnabled(true);
                    bi.ciw21002a.setEnabled(true);
                    bi.ciw21003a.setEnabled(true);
                    bi.ciw21098a.setEnabled(true);
                    bi.ciw21099a.setEnabled(true);
                    bi.ciw21001b.setEnabled(true);
                    bi.ciw21002b.setEnabled(true);
                    bi.ciw21003b.setEnabled(true);
                    bi.ciw21098b.setEnabled(true);
                    bi.ciw21099b.setEnabled(true);
                } else {
                    bi.ciw211.setEnabled(false);
                    bi.ciw211.setText(null);
                    bi.ciw212.setEnabled(false);
                    bi.ciw215.setEnabled(false);
                    bi.ciw212.setText(null);
                    bi.ciw215.setText(null);
                    bi.ciw214.setEnabled(false);
                    /*bi.ciw216a.setEnabled(false);
                    bi.ciw216b.setEnabled(false);
                    bi.ciw216.clearCheck();
                    bi.ciw216aa.setEnabled(false);
                    bi.ciw216aa.setText(null);*/
                    bi.ciw213.setEnabled(false);
                    bi.ciw213.setText(null);
                    bi.ciw208a.setEnabled(false);
                    bi.ciw208b.setEnabled(false);
                    bi.ciw208.clearCheck();
                    bi.ciw209a.setEnabled(false);
                    bi.ciw209b.setEnabled(false);
                    bi.ciw209.clearCheck();
                    bi.ciw21001.clearCheck();
                    bi.ciw21002.clearCheck();
                    bi.ciw21003.clearCheck();
                    bi.ciw21098.clearCheck();
                    bi.ciw21099.clearCheck();

                    bi.ciw21001a.setEnabled(false);
                    bi.ciw21002a.setEnabled(false);
                    bi.ciw21003a.setEnabled(false);
                    bi.ciw21098a.setEnabled(false);
                    bi.ciw21099a.setEnabled(false);

                    bi.ciw21001b.setEnabled(false);
                    bi.ciw21002b.setEnabled(false);
                    bi.ciw21003b.setEnabled(false);
                    bi.ciw21098b.setEnabled(false);
                    bi.ciw21099b.setEnabled(false);
                }
            }
        });

        bi.ciw211.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bi.ciw207a.isChecked() && bi.ciw208a.isChecked()) {
                    if (bi.ciw211.getText().toString().equals("1")) {
                        bi.ciw212.setEnabled(false);
                        bi.ciw213.setEnabled(false);
                        //bi.ciw214.setEnabled(false);
                        bi.ciw215.setEnabled(false);
                        /*bi.ciw216a.setEnabled(false);
                        bi.ciw216b.setEnabled(false);
                        bi.ciw216aa.setEnabled(false);*/
                        bi.ciw212.setText("0");
                        bi.ciw213.setText(null);
                        //bi.ciw214.setText(null);
                        bi.ciw215.setText(null);
                        /*bi.ciw216.clearCheck();
                        bi.ciw216aa.setText(null);*/

                    } else {
                        bi.ciw212.setEnabled(true);
                        bi.ciw213.setEnabled(true);
                        bi.ciw214.setEnabled(true);
                        bi.ciw215.setEnabled(true);
                       /* bi.ciw216a.setEnabled(true);
                        bi.ciw216b.setEnabled(true);
                        bi.ciw216aa.setEnabled(true);*/
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        bi.ciw212.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (bi.ciw207a.isChecked() && bi.ciw208a.isChecked() && bi.ciw211.getText().toString().equals("1")) {
                    if (bi.ciw212.getText().toString().equals("0")) {
                        bi.ciw213.setEnabled(false);
                        bi.ciw213.setText(null);

                        /*bi.ciw214.setEnabled(false);
                        bi.ciw214.setText(null);
                        bi.ciw215.setEnabled(false);
                        bi.ciw215.setText(null);*/

                       /* bi.ciw216a.setEnabled(false);
                        bi.ciw216b.setEnabled(false);
                        bi.ciw216.clearCheck();
                        bi.ciw216aa.setEnabled(false);
                        bi.ciw216aa.setText(null);*/

                    } else {

                        bi.ciw213.setEnabled(true);

                        /*bi.ciw214.setEnabled(true);
                        bi.ciw215.setEnabled(true);*/

                        /*bi.ciw216a.setEnabled(true);
                        bi.ciw216b.setEnabled(true);
                        bi.ciw216aa.setEnabled(true);*/

                    }
                } else if (bi.ciw207a.isChecked() && !bi.ciw208a.isChecked() && bi.ciw211.getText().toString().equals("1")) {
                    if (bi.ciw212.getText().toString().equals("0")) {
                        bi.ciw213.setEnabled(false);
                        bi.ciw213.setText(null);

                        /*bi.ciw214.setEnabled(false);
                        bi.ciw214.setText(null);
                        bi.ciw215.setEnabled(false);
                        bi.ciw215.setText(null);*/

                        /*bi.ciw216a.setEnabled(false);
                        bi.ciw216b.setEnabled(false);
                        bi.ciw216.clearCheck();
                        bi.ciw216aa.setEnabled(false);
                        bi.ciw216aa.setText(null);
*/
                    } else {

                        bi.ciw213.setEnabled(true);

                        /*bi.ciw214.setEnabled(true);
                        bi.ciw215.setEnabled(true);*/

                       /* bi.ciw216a.setEnabled(true);
                        bi.ciw216b.setEnabled(true);
                        bi.ciw216aa.setEnabled(true);*/

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        bi.ciw214.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (bi.ciw214.getText().toString().equals("0")) {
                    bi.ciw215.setEnabled(false);
                    bi.ciw215.setText(null);

                } else if (bi.ciw214.getText().toString().equals("1") && bi.ciw208a.isChecked()) {
                    bi.ciw215.setEnabled(false);
                    bi.ciw215.setText(null);

                } else {
                    bi.ciw215.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        bi.ciw208.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (checkedId == R.id.ciw208a) {
                    bi.ciw209a.setEnabled(true);
                    bi.ciw209b.setEnabled(true);

                    bi.ciw21001a.setEnabled(true);
                    bi.ciw21002a.setEnabled(true);
                    bi.ciw21003a.setEnabled(true);
                    bi.ciw21098a.setEnabled(true);
                    bi.ciw21099a.setEnabled(true);

                    bi.ciw21001b.setEnabled(true);
                    bi.ciw21002b.setEnabled(true);
                    bi.ciw21003b.setEnabled(true);
                    bi.ciw21098b.setEnabled(true);
                    bi.ciw21099b.setEnabled(true);
                } else {
                    bi.ciw209a.setEnabled(false);
                    bi.ciw209b.setEnabled(false);
                    bi.ciw209.clearCheck();

                    bi.ciw21001.clearCheck();
                    bi.ciw21002.clearCheck();
                    bi.ciw21003.clearCheck();
                    bi.ciw21098.clearCheck();
                    bi.ciw21099.clearCheck();
                    bi.ciw21001.clearCheck();
                    bi.ciw21002.clearCheck();
                    bi.ciw21003.clearCheck();
                    bi.ciw21098.clearCheck();
                    bi.ciw21099.clearCheck();

                    bi.ciw21001a.setEnabled(false);
                    bi.ciw21002a.setEnabled(false);
                    bi.ciw21003a.setEnabled(false);
                    bi.ciw21098a.setEnabled(false);
                    bi.ciw21099a.setEnabled(false);

                    bi.ciw21001b.setEnabled(false);
                    bi.ciw21002b.setEnabled(false);
                    bi.ciw21003b.setEnabled(false);
                    bi.ciw21098b.setEnabled(false);
                    bi.ciw21099b.setEnabled(false);

                    bi.ciw211.setText(null);
                    bi.ciw212.setEnabled(true);
                    bi.ciw212.setText(null);
                    bi.ciw213.setEnabled(true);
                    bi.ciw213.setText(null);
                    bi.ciw214.setText(null);
                    bi.ciw214.setEnabled(true);
                    bi.ciw215.setText(null);
                    bi.ciw215.setEnabled(true);
                }

            }
        });
        bi.ciw209.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (checkedId == R.id.ciw209a) {
                    bi.ciw21001a.setEnabled(true);
                    bi.ciw21002a.setEnabled(true);
                    bi.ciw21003a.setEnabled(true);
                    bi.ciw21098a.setEnabled(true);
                    bi.ciw21099a.setEnabled(true);

                    bi.ciw21001b.setEnabled(true);
                    bi.ciw21002b.setEnabled(true);
                    bi.ciw21003b.setEnabled(true);
                    bi.ciw21098b.setEnabled(true);
                    bi.ciw21099b.setEnabled(true);

                } else {
                    bi.ciw21001.clearCheck();
                    bi.ciw21002.clearCheck();
                    bi.ciw21003.clearCheck();
                    bi.ciw21098.clearCheck();
                    bi.ciw21099.clearCheck();

                    bi.ciw21001a.setEnabled(false);
                    bi.ciw21002a.setEnabled(false);
                    bi.ciw21003a.setEnabled(false);
                    bi.ciw21098a.setEnabled(false);
                    bi.ciw21099a.setEnabled(false);

                    bi.ciw21001b.setEnabled(false);
                    bi.ciw21002b.setEnabled(false);
                    bi.ciw21003b.setEnabled(false);
                    bi.ciw21098b.setEnabled(false);
                    bi.ciw21099b.setEnabled(false);

                }
            }
        });

        RadioGroup.OnCheckedChangeListener ciw210aListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (isoneYes()) {
                    bi.ciw21003a.setEnabled(false);
                    bi.ciw21003b.setEnabled(false);
                    bi.ciw21003.clearCheck();
                    bi.ciw21098a.setEnabled(false);
                    bi.ciw21098b.setEnabled(false);
                    bi.ciw21098.clearCheck();
                    bi.ciw21099a.setEnabled(false);
                    bi.ciw21099b.setEnabled(false);
                    bi.ciw21099.clearCheck();
                } else {
                    bi.ciw21003a.setEnabled(true);
                    bi.ciw21003b.setEnabled(true);
                    bi.ciw21098a.setEnabled(true);
                    bi.ciw21098b.setEnabled(true);
                    bi.ciw21099a.setEnabled(true);
                    bi.ciw21099b.setEnabled(true);
                }

            }
        };

        RadioGroup.OnCheckedChangeListener ciw210bListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ValidateForm();
                if (isoneYes2()) {
                    bi.ciw21001a.setEnabled(false);
                    bi.ciw21001b.setEnabled(false);
                    bi.ciw21001.clearCheck();
                    bi.ciw21002a.setEnabled(false);
                    bi.ciw21002b.setEnabled(false);
                    bi.ciw21002.clearCheck();
                } else {
                    bi.ciw21001a.setEnabled(true);
                    bi.ciw21001b.setEnabled(true);
                    bi.ciw21002a.setEnabled(true);
                    bi.ciw21002b.setEnabled(true);

                }
            }
        };

        // ciw210 Skips

        for (RadioGroup rg : ciw210a) {
            rg.setOnCheckedChangeListener(ciw210aListener);
        }

        for (RadioGroup rg : ciw210b) {
            rg.setOnCheckedChangeListener(ciw210bListener);
        }


        bi.ciw213.addTextChangedListener(this);
        bi.ciw214.addTextChangedListener(this);
        bi.ciw215.addTextChangedListener(this);
        //bi.ciw216aa.addTextChangedListener(this);

        bi.na11801.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.na11801b) {
                    bi.ciw203.clearCheck();
                    bi.ciw204.clearCheck();
                    bi.ciw205.clearCheck();
                    bi.ciw207.clearCheck();
                    bi.ciw208.clearCheck();
                    bi.ciw209.clearCheck();
                    bi.ciw21001.clearCheck();
                    bi.ciw21002.clearCheck();
                    bi.ciw21003.clearCheck();
                    bi.ciw21098.clearCheck();
                    bi.ciw21099.clearCheck();
                    //bi.ciw216.clearCheck();
                }
            }
        });

    }

    public void setupViews() {


//      Get intent
        if (getIntent().getBooleanExtra("reBackComing", true)) {
            if (getIntent().getBooleanExtra("mwraFlag", false)) {
                lstMwra.remove(getIntent().getStringExtra("wraName"));
                //      Increment WRA COUNTER
                WRAcounter++;

//                WRAsize = MainApp.mwra.size();

            } else {
                wraMap = new HashMap<>();
                lstMwra = new ArrayList<>();

                lstMwra.add("....");

                for (FamilyMembersContract wra : MainApp.mwra) {
                    wraMap.put(wra.getName() + "-" + wra.getSerialNo(), wra);
                    lstMwra.add(wra.getName() + "-" + wra.getSerialNo());
                }

                WRAcounter = 1;


            }
        } else {

            if (WRAcounter == 1) {
                wraMap = new HashMap<>();
                lstMwra = new ArrayList<>();

                lstMwra.add("....");

                WRAsize = 0;
            }

            for (int i = WRAsize; i < MainApp.mwra.size(); i++) {
                wraMap.put(MainApp.mwra.get(i).getName() + "-" + MainApp.mwra.get(i).getSerialNo(), MainApp.mwra.get(i));
                lstMwra.add(MainApp.mwra.get(i).getName() + "-" + MainApp.mwra.get(i).getSerialNo());
            }

//            WRAsize = MainApp.mwra.size();

        }


        bi.nb101.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, lstMwra));

        /*bi.nb101.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (bi.nb101.getSelectedItemPosition() != 0) {
                    for (FamilyMembersContract fmc : MainApp.childUnder2Check) {
                        childCheck = fmc.getMotherId().equals(wraMap.get(bi.nb101.getSelectedItem().toString()).getSerialNo());
                        if (childCheck) {
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    private void GetDataFromForm(String uuid) {
        /*if (getIntent().getIntExtra("under2Size", 0) > 0) {
            childCheck = true;
        } else {
            Collection<DeceasedContract> deceasedContracts = db.getDeceasedMembersCount(uuid);
            for (DeceasedContract deceasedContract : deceasedContracts) {
                JSONH8ModelClass jsonh8ModelClass = JSONUtilClass.getModelFromJSON(deceasedContract.getsH8(), JSONH8ModelClass.class);
                if (jsonh8ModelClass.getMwraSerial().equals(MainApp.mc.getB1SerialNo())) {
                    childCheck = true;
                    break;
                }
            }
        }*/

        bi.nb101.setVisibility(View.GONE);
        bi.nb101a.setVisibility(View.VISIBLE);

        MainApp.fc = db.getAutoPopulateFormForWRA(uuid);

        FamilyMembersContract MWR = (FamilyMembersContract) getIntent().getSerializableExtra("fmClass");

        wraMap = new HashMap<>();
        lstMwra = new ArrayList<>();

        lstMwra.add("....");

        /*for (FamilyMembersContract wra : MWR) {
            wraMap.put(wra.getName() + "-" + wra.getSerialNo(), wra);
            lstMwra.add(wra.getName() + "-" + wra.getSerialNo());
        }*/

    }

    private void AutoPopulate(String uuid, String uid) {

        MainApp.mc = db.getsB1(uuid, uid);

        bi.nb101.setVisibility(View.GONE);
        bi.nb101a.setVisibility(View.VISIBLE);

        if (!MainApp.mc.getsB1().equals("")) {

            jsonB1 = JSONUtilClass.getModelFromJSON(MainApp.mc.getsB1(), JSONB1ModelClass.class);

            MainApp.mc.setCluster(jsonB1.getCluster_no());
            MainApp.mc.setHhno(jsonB1.getHhno());

            if (!jsonB1.getciw11801().equals("0")) {
                bi.na11801.check(
                        jsonB1.getciw11801().equals("1") ? bi.na11801a.getId() :
                                bi.na11801b.getId()
                );
            }

            if (!jsonB1.getciw216aa().equals("")) {
                prevMiscarriages = Integer.valueOf(jsonB1.getciw216aa());
            }

            bi.nb101a.setText(jsonB1.getciw101());

            bi.ciw201years.setText(jsonB1.getciw201years());
            bi.ciw201months.setText(jsonB1.getciw201months());
            bi.ciw201days.setText(jsonB1.getciw201days());
            bi.ciw202.setText(jsonB1.getciw202());

            if (!jsonB1.getciw203().equals("0")) {
                bi.ciw203.check(
                        jsonB1.getciw203().equals("1") ? bi.ciw203a.getId() :
                                bi.ciw203b.getId()
                );
            }
            if (!jsonB1.getciw204().equals("0")) {
                bi.ciw204.check(
                        jsonB1.getciw204().equals("1") ? bi.ciw204a.getId() :
                                bi.ciw204b.getId()
                );
            }
            if (!jsonB1.getciw205().equals("0")) {
                bi.ciw205.check(
                        jsonB1.getciw205().equals("1") ? bi.ciw205a.getId() :
                                bi.ciw205b.getId()
                );
            }

            bi.ciw206.setText(jsonB1.getciw206());

            if (!jsonB1.getciw205().equals("0")) {
                bi.ciw205.check(
                        jsonB1.getciw205().equals("1") ? bi.ciw205a.getId() :
                                bi.ciw205b.getId()
                );
            }
            if (!jsonB1.getciw207().equals("0")) {
                bi.ciw207.check(
                        jsonB1.getciw207().equals("1") ? bi.ciw207a.getId() :
                                bi.ciw207b.getId()
                );
            }
            if (!jsonB1.getciw208().equals("0")) {
                bi.ciw208.check(
                        jsonB1.getciw208().equals("1") ? bi.ciw208a.getId() :
                                bi.ciw208b.getId()
                );
            }
            if (!jsonB1.getciw209().equals("0")) {
                bi.ciw209.check(
                        jsonB1.getciw209().equals("1") ? bi.ciw209a.getId() :
                                bi.ciw209b.getId()
                );
            }

            if (jsonB1.getciw21001().equals("1")) {
                bi.ciw21001a.setChecked(true);
            }
            if (jsonB1.getciw21002().equals("1")) {
                bi.ciw21002a.setChecked(true);
            }
            if (jsonB1.getciw21003().equals("1")) {
                bi.ciw21003a.setChecked(true);
            }
            if (jsonB1.getciw21098().equals("1")) {
                bi.ciw21098a.setChecked(true);
            }
            if (jsonB1.getciw21099().equals("1")) {
                bi.ciw21099a.setChecked(true);
            }

            bi.ciw211.setText(jsonB1.getciw211());
            bi.ciw212.setText(jsonB1.getciw212());
            bi.ciw213.setText(jsonB1.getciw213());
            bi.ciw214.setText(jsonB1.getciw214());

            if (!jsonB1.getciw214().equals("")) {
                prevDeliveries = Integer.valueOf(jsonB1.getciw214());
            }

            bi.ciw215.setText(jsonB1.getciw215());


           /* if (!jsonB1.getciw216().equals("0")) {
                bi.ciw216.check(
                        jsonB1.getciw216().equals("1") ? bi.ciw216a.getId() :
                                bi.ciw216b.getId()
                );
            }
            bi.ciw216aa.setText(jsonB1.getciw216aa());*/

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


                MainApp.nuCount = 1;
                MainApp.count = 1;

                frontPressed = true;

                if (!editWRAFlag) {
                    WRAsize = MainApp.mwra.size();
                }

                //finish();

                if (bi.ciw203a.isChecked()) {
                    if (bi.ciw204a.isChecked() || bi.ciw205a.isChecked()) {
                        if (bi.ciw207a.isChecked()) {
                            /*if (bi.ciw216a.isChecked()) {
                                if (Integer.valueOf(bi.ciw216aa.getText().toString()) > 0) {

                                    if (Integer.valueOf(bi.ciw216aa.getText().toString()) < prevMiscarriages) {

                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                SectionB1Activity.this);
                                        alertDialogBuilder
                                                .setMessage("In previous you saved " + prevMiscarriages + " Miscarriage.\n" +
                                                        "Do you want to continue it?")
                                                .setCancelable(false)
                                                .setPositiveButton("Yes",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog,
                                                                                int id) {

                                                                MainApp.totalPregnancy = prevMiscarriages;

                                                                startActivityForResult(new Intent(SectionB1Activity.this, SectionB1AActivity.class)
                                                                        .putExtra("backPressed", classPassName.equals(SectionB1AActivity.class.getName())), 1);
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

                                    } else {
                                        MainApp.totalPregnancy = Integer.valueOf(bi.ciw214.getText().toString());

                                        startActivityForResult(new Intent(this, SectionB1AActivity.class)
                                                .putExtra("backPressed", classPassName.equals(SectionB1AActivity.class.getName())), 1);

                                    }
                                }

                            } else if (childCheck) {
                                startActivity(new Intent(this, SectionB2Activity.class));
                            } else {
                                redirectCondition();
                            }*/
                            MainApp.currentlyPregnant = bi.ciw208a.isChecked() ? 1 : bi.ciw208b.isChecked() ? 2 : 0; // when women is currently pregnant outcome should be open according to totalPregnencies-1

                            if (Integer.valueOf(bi.ciw214.getText().toString()) > 0) {
                                if (Integer.valueOf(bi.ciw214.getText().toString()) == 1 && MainApp.currentlyPregnant == 1) {
                                    redirectCondition();
                                } else if (Integer.valueOf(bi.ciw214.getText().toString()) < prevDeliveries) {

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            SectionB1Activity.this);
                                    alertDialogBuilder
                                            .setMessage("In previous you saved " + prevDeliveries + " Pregnancies.\n" +
                                                    "Do you want to continue it?")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog,
                                                                            int id) {

                                                            MainApp.totalPregnancy = prevDeliveries;
                                                            startActivityForResult(new Intent(SectionB1Activity.this, SectionB1AActivity.class)
                                                                    .putExtra("backPressed", classPassName.equals(SectionB1AActivity.class.getName())), 1);
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

                                } else {
                                    MainApp.totalPregnancy = Integer.valueOf(bi.ciw214.getText().toString());
                                    startActivityForResult(new Intent(this, SectionB1AActivity.class)
                                            .putExtra("backPressed", classPassName.equals(SectionB1AActivity.class.getName())), 1);

                                }
                            } else {
                                redirectCondition();
                            }

                            /*if (MainApp.totalPregnancy > 0) {
                                startActivity(new Intent(this, SectionB1AActivity.class));
                            } else {
                                redirectCondition();
                            }*/

                        } else {
                            redirectCondition();
                        }
                    } else {
                        redirectCondition();
                    }
                } else {

                    if (editWRAFlag) {
                        finish();
                        startActivity(new Intent(this, ViewMemberActivity.class)
                                .putExtra("flagEdit", false)
                                .putExtra("comingBack", true)
                                .putExtra("cluster", MainApp.mc.getCluster())
                                .putExtra("hhno", MainApp.mc.getHhno())
                        );
                    } else {
                        startActivity(new Intent(this, MotherEndingActivity.class)
                                .putExtra("checkingFlag", true)
                                .putExtra("complete", true));
                    }
                }

                //startActivity(new Intent(this, SectionC1Activity.class));

//                finish();

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void redirectCondition() {
        if (editWRAFlag) {
            if (MainApp.mc.getsB6().equals("1")) {
                startActivityForResult(new Intent(this, SectionB6Activity.class)
                        .putExtra("backPressed", classPassName.equals(SectionB6Activity.class.getName())), 1);

            }/* else if (!db.getNutritionCount()) {
                startActivityForResult(new Intent(this, SectionB6Activity.class)
                        .putExtra("backPressed", classPassName.equals(SectionB6Activity.class.getName())), 1);
            }*/ else {
                finish();
                startActivity(new Intent(this, ViewMemberActivity.class)
                        .putExtra("flagEdit", false)
                        .putExtra("comingBack", true)
                        .putExtra("cluster", MainApp.mc.getCluster())
                        .putExtra("hhno", MainApp.mc.getHhno())
                );
            }
        } else {
//            if (SectionB1Activity.WRAcounter == MainApp.mwra.size()
//                    &&
//                    MainApp.B6Flag) {
            if (wraMap.get(bi.nb101.getSelectedItem().toString()).getKishSelected().equals("1")) {
                startActivityForResult(new Intent(this, SectionB6Activity.class)
                        .putExtra("backPressed", classPassName.equals(SectionB6Activity.class.getName())), 1);
            } else {
                startActivity(new Intent(this, MotherEndingActivity.class)
                        .putExtra("complete", true));
            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }

    public void BtnEnd() {

        endflag = true;
        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {


                //finish();

                if (editWRAFlag) {
                    finish();
                    startActivity(new Intent(this, ViewMemberActivity.class)
                            .putExtra("flagEdit", false)
                            .putExtra("comingBack", true)
                            .putExtra("cluster", MainApp.mc.getCluster())
                            .putExtra("hhno", MainApp.mc.getHhno())
                    );
                } else {
                    WRAsize = MainApp.mwra.size();
                    MainApp.endActivityMother(this, this, false);
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject sB1 = new JSONObject();

        if (!backPressed) {
            MainApp.mc = new MWRAContract();
            MainApp.mc.setDevicetagID(MainApp.fc.getDevicetagID());
            MainApp.mc.setFormDate(MainApp.fc.getFormDate());
            MainApp.mc.setUser(MainApp.fc.getUser());
            MainApp.mc.setDeviceId(MainApp.fc.getDeviceID());
            MainApp.mc.setApp_ver(MainApp.fc.getAppversion());
            MainApp.mc.setB1SerialNo(wraMap.get(bi.nb101.getSelectedItem().toString()).getSerialNo());
            MainApp.mc.set_UUID(MainApp.fc.getUID());
            MainApp.mc.setFMUID(wraMap.get(bi.nb101.getSelectedItem().toString()).get_UID());

            wraName = bi.nb101.getSelectedItem().toString();

            sB1.put("cluster_no", MainApp.fc.getClusterNo());
            sB1.put("hhno", MainApp.fc.getHhNo());
            sB1.put("ciw101", bi.nb101.getSelectedItem().toString());
            sB1.put("wra_lno", wraMap.get(bi.nb101.getSelectedItem().toString()).getSerialNo());
            try {
                prevDeliveries = Integer.valueOf(bi.ciw214.getText().toString());
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }

        } else {

            sB1.put("updatedate_ciw1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

            if (editWRAFlag && !frontPressed) {
                sB1.put("edit_updatedate_ciw1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

                wraName = jsonB1.getciw101();

                sB1.put("cluster_no", jsonB1.getCluster_no());
                sB1.put("hhno", jsonB1.getHhno());
                sB1.put("ciw101", jsonB1.getciw101());
                sB1.put("wra_lno", jsonB1.getciw1serialno());

            } else if (editWRAFlag) {

                wraName = jsonB1.getciw101();

                sB1.put("cluster_no", jsonB1.getCluster_no());
                sB1.put("hhno", jsonB1.getHhno());
                sB1.put("ciw101", jsonB1.getciw101());
                sB1.put("wra_lno", jsonB1.getciw1serialno());

            } else {

                wraName = bi.nb101.getSelectedItem().toString();

                sB1.put("cluster_no", MainApp.fc.getClusterNo());
                sB1.put("hhno", MainApp.fc.getHhNo());
                sB1.put("ciw101", bi.nb101.getSelectedItem().toString());
                sB1.put("wra_lno", wraMap.get(bi.nb101.getSelectedItem().toString()).getSerialNo());

            }
//            MainApp.mc.set_UID(MainApp.mc.get_UID());
        }

        sB1.put("ciw11801", bi.na11801a.isChecked() ? "1" : bi.na11801b.isChecked() ? "2" : "0");
        //        ciw201
        sB1.put("ciw201days", bi.ciw201days.getText().toString());
        sB1.put("ciw201months", bi.ciw201months.getText().toString());
        sB1.put("ciw201years", bi.ciw201years.getText().toString());
        //        ciw202
        sB1.put("ciw202", bi.ciw202.getText().toString());
        //        ciw203
        sB1.put("ciw203", bi.ciw203a.isChecked() ? "1" : bi.ciw203b.isChecked() ? "2" : "0");
        //        ciw204
        sB1.put("ciw204", bi.ciw204a.isChecked() ? "1" : bi.ciw204b.isChecked() ? "2" : "0");
        //        ciw205
        sB1.put("ciw205", bi.ciw205a.isChecked() ? "1" : bi.ciw205b.isChecked() ? "2" : "0");
        //        ciw206
        sB1.put("ciw206", bi.ciw206.getText().toString());
        //        ciw207
        sB1.put("ciw207", bi.ciw207a.isChecked() ? "1" : bi.ciw207b.isChecked() ? "2" : "0");
        sB1.put("ciw208", bi.ciw208a.isChecked() ? "1" : bi.ciw208b.isChecked() ? "2" : "0");

        sB1.put("ciw209", bi.ciw209a.isChecked() ? "1" : bi.ciw209b.isChecked() ? "2" : "0");

        //        ciw21001
        sB1.put("ciw21001", bi.ciw21001a.isChecked() ? "1" : bi.ciw21001b.isChecked() ? "2" : "0");
        //        ciw21002
        sB1.put("ciw21002", bi.ciw21002a.isChecked() ? "1" : bi.ciw21002b.isChecked() ? "2" : "0");
        //        ciw21003
        sB1.put("ciw21003", bi.ciw21003a.isChecked() ? "1" : bi.ciw21003b.isChecked() ? "2" : "0");
        //        ciw21098
        sB1.put("ciw21098", bi.ciw21098a.isChecked() ? "1" : bi.ciw21098b.isChecked() ? "2" : "0");
        //        ciw21099
        sB1.put("ciw21099", bi.ciw21099a.isChecked() ? "1" : bi.ciw21099b.isChecked() ? "2" : "0");

        sB1.put("ciw211", bi.ciw211.getText().toString());
        sB1.put("ciw212", bi.ciw212.getText().toString());
        sB1.put("ciw213", bi.ciw213.getText().toString());
        sB1.put("ciw214", bi.ciw214.getText().toString());
        sB1.put("ciw215", bi.ciw215.getText().toString());
       /* sB1.put("ciw216", bi.ciw216a.isChecked() ? "1" : bi.ciw216b.isChecked() ? "2" : "0");
        sB1.put("ciw216aa", bi.ciw216aa.getText().toString());*/

        /*if (bi.ciw216a.isChecked() && !bi.ciw216aa.getText().toString().isEmpty()) {
            MainApp.totalPregnancy = Integer.valueOf(bi.ciw216aa.getText().toString());
        }*/

        MainApp.mc.setsB1(String.valueOf(sB1));

    }

    private boolean UpdateDB() {

        //Long rowId;
        if (!backPressed) {
            Long updcount = db.addMWRA(MainApp.mc, 0);
            MainApp.mc.set_ID(String.valueOf(updcount));

            if (updcount != 0) {
                MainApp.mc.set_UID(
                        (MainApp.mc.getDeviceId() + MainApp.mc.get_ID()));
                db.updateMWRAID();

                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Long updcount = db.addMWRA(MainApp.mc, 1);

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
        childisUnder2AndAlive = false;
        if (frontPressed) {
            backPressed = true;
        }

        if (backPressed) {
            bi.nb101.setEnabled(false);
            bi.btnAddMember.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                classPassName = data.getStringExtra("backPressedClass");
            } else {
                classPassName = "";
            }
        }
    }

    public void BtnAddMember() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SectionB1Activity.this);
        alertDialogBuilder
                .setMessage("Are you sure to add missing member?")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                WRAsize = MainApp.mwra.size();

                                finish();
                                startActivity(new Intent(SectionB1Activity.this, SectionA2ListActivity.class)
                                        .putExtra("reBack", true)
                                        .putExtra("reBackChild", true));
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {


    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        ValidateForm();
    }

    private boolean ValidateForm() {

        if (endflag) {
            if (!editWRAFlag) {
                return ValidatorClass.EmptySpinner(this, bi.nb101, getString(R.string.nb101));
            }
        } else {

            if (!editWRAFlag) {
                if (!ValidatorClass.EmptySpinner(this, bi.nb101, getString(R.string.nb101))) {
                    return false;
                }
            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.na11801, bi.na11801b, getString(R.string.na11801))) {
                return false;
            }

            if (bi.na11801a.isChecked()) {

                if (!ValidatorClass.EmptyTextBox(this, bi.ciw201days, getString(R.string.day))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, bi.ciw201days, 1, 31, 98, "Range 1-31 or 98", getString(R.string.day))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ciw201months, getString(R.string.months))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, bi.ciw201months, 1, 12, 98, "Range 1-12 or 98", getString(R.string.months))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ciw201years, getString(R.string.year2))) {
                    return false;
                }

                Date date = new Date(); // Current date
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);

                if (!ValidatorClass.RangeTextBox(this, bi.ciw201years, year - 49, year - 15, 9998,
                        "Range " + (year - 49) + " - " + (year - 15), getString(R.string.year2))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ciw202, getString(R.string.ciw202))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, bi.ciw202, 15, 49, "Range 15-49", getString(R.string.year))) {
                    return false;
                }


                if (!ValidatorClass.EmptyRadioButton(this, bi.ciw203, bi.ciw203b, getString(R.string.ciw203))) {
                    return false;
                }

                if (bi.ciw203a.isChecked()) {

                    if (!ValidatorClass.EmptyRadioButton(this, bi.ciw204, bi.ciw204a, getString(R.string.ciw204))) {
                        return false;
                    }

                    if (bi.ciw204b.isChecked()) {
                        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw205, bi.ciw205a, getString(R.string.ciw205))) {
                            return false;
                        }
                    }

                    if (bi.ciw204a.isChecked() || bi.ciw205a.isChecked()) {

                        //if (bi.ciw204a.isChecked() || bi.ciw205a.isChecked()) {
                        if (!ValidatorClass.EmptyTextBox(this, bi.ciw206, getString(R.string.ciw206))) {
                            return false;
                        }

                        if (!ValidatorClass.RangeTextBox(this, bi.ciw206, 10, Integer.valueOf(bi.ciw202.getText().toString()), getString(R.string.ciw206), " years")) {
                            return false;
                        }

                        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw207, bi.ciw207a, getString(R.string.ciw207))) {
                            return false;
                        }

                        if (bi.ciw207a.isChecked()) {

                            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw208, bi.ciw208a, getString(R.string.ciw208))) {
                                return false;
                            }

                            if (bi.ciw208a.isChecked()) {

                                if (!ValidatorClass.EmptyRadioButton(this, bi.ciw209, bi.ciw209a, getString(R.string.ciw210))) {
                                    return false;
                                }

                                if (bi.ciw209a.isChecked()) {

                                    if (!bi.ciw21098a.isChecked() && !bi.ciw21099a.isChecked() && !bi.ciw21003a.isChecked()) {
                                        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw21001, bi.ciw21001a, getString(R.string.ciw21001))) {
                                            return false;
                                        }

                                        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw21002, bi.ciw21002a, getString(R.string.ciw21002))) {
                                            return false;
                                        }

                                    }
                                }

                            }

                            if (!ValidatorClass.EmptyTextBox(this, bi.ciw211, getString(R.string.ciw211))) {
                                return false;
                            }

                            if (!ValidatorClass.RangeTextBox(this, bi.ciw211, 1, 20, getString(R.string.ciw211), " pregnancies")) {
                                return false;
                            }

                            if (!ValidatorClass.EmptyTextBox(this, bi.ciw212, getString(R.string.ciw212))) {
                                return false;
                            }


                            if (bi.ciw208a.isChecked()) {
                                if (!ValidatorClass.RangeTextBox(this, bi.ciw212, 0, (Integer.valueOf(bi.ciw211.getText().toString()) - 1), getString(R.string.ciw212), " Deliveries")) {
                                    return false;
                                }
                            } else {
                                if (!ValidatorClass.RangeTextBox(this, bi.ciw212, 0, Integer.valueOf(bi.ciw211.getText().toString()), getString(R.string.ciw212), " Deliveries")) {
                                    return false;
                                }
                            }

                            if (!bi.ciw212.getText().toString().equals("0")) {
                                if (!ValidatorClass.EmptyTextBox(this, bi.ciw213, getString(R.string.ciw213))) {
                                    return false;
                                }

                                if (!ValidatorClass.EmptyTextBox(this, bi.ciw213, getString(R.string.ciw213))) {
                                    return false;
                                }

                                if (!ValidatorClass.RangeTextBox(this, bi.ciw213, Integer.valueOf(bi.ciw206.getText().toString()), Integer.valueOf(bi.ciw202.getText().toString()), getString(R.string.ciw213), " years")) {
                                    return false;
                                }

                            }
                            if (!ValidatorClass.EmptyTextBox(this, bi.ciw214, getString(R.string.ciw214))) {
                                return false;
                            }

                            if (!ValidatorClass.RangeTextBox(this, bi.ciw214, 0, Integer.valueOf(bi.ciw211.getText().toString()), getString(R.string.ciw211), " Deliveries")) {
                                return false;
                            }

                            if (Integer.valueOf(bi.ciw214.getText().toString()) > 0) {

                                if (Integer.valueOf(bi.ciw214.getText().toString()) == 1 && bi.ciw208a.isChecked()) {

                                } else {
                                    if (!ValidatorClass.EmptyTextBox(this, bi.ciw215, getString(R.string.ciw215))) {
                                        return false;
                                    }
                                    return ValidatorClass.RangeTextBox(this, bi.ciw215, 0, Integer.valueOf(bi.ciw212.getText().toString()), getString(R.string.ciw212), " Deliveries");
                                }

                            } else {
                                if (bi.ciw208a.isChecked()) {
                                    Toast.makeText(this, "The women is currently pregrnant. Previous pregnencies should be 1", Toast.LENGTH_LONG).show();
                                    bi.ciw214.setError("The women is currently pregrnant. Previous pregnencies should be 1");
                                    return false;
                                } else {
                                    bi.ciw214.setError("null");
                                }
                            }


                        }

                        //}
                    }

                }
            }

        }
        return true;
    }

    public boolean isoneYes() {

        int i = 0;
        for (RadioButton rg : ciw210aYes) {
            if (rg.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    public boolean isoneYes2() {

        int i = 0;
        for (RadioButton rg : ciw210bYes) {
            if (rg.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

}