package edu.aku.hassannaqvi.casi_2018.ui.wra;

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
import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONB1ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.contracts.MWRAContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionB1Binding;
import edu.aku.hassannaqvi.casi_2018.other.DateUtils;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.household.SectionA2ListActivity;
import edu.aku.hassannaqvi.casi_2018.ui.mainUI.AddMember_MenuActivity;
import edu.aku.hassannaqvi.casi_2018.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

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

            if (!bi.nw201days.getText().toString().isEmpty() && !bi.nw201months.getText().toString().isEmpty()
                    && !bi.nw201years.getText().toString().isEmpty()) {

                if (!bi.nw201days.getText().toString().equals("98") && !bi.nw201months.getText().toString().equals("98")
                        && !bi.nw201years.getText().toString().equals("9998")) {

                    dob = DateUtils.getCalendarDate(bi.nw201days.getText().toString(), bi.nw201months.getText().toString(),
                            bi.nw201years.getText().toString());

                    agebyDob = DateUtils.ageInYearByDOB(dob);

                    bi.txtAge.setVisibility(View.VISIBLE);
                    bi.txtAge.setText("Age by Date of Birth : " + agebyDob + " years");

                } else if (!bi.nw201years.getText().toString().equals("9998") &&
                        !bi.nw201months.getText().toString().equals("98")) {

                    dob = DateUtils.getCalendarDate(bi.nw201months.getText().toString(),
                            bi.nw201years.getText().toString());
                    agebyDob = DateUtils.ageInYearByDOB(dob);

                    bi.txtAge.setVisibility(View.VISIBLE);
                    bi.txtAge.setText("Age by Date of Birth : " + agebyDob + " years");

                } else if (!bi.nw201years.getText().toString().equals("9998")) {
                    agebyDob = DateUtils.ageInYearByDOB(bi.nw201years.getText().toString());

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
    @BindViews({R.id.nw21001, R.id.nw21002})
    List<RadioGroup> nw210a;
    @BindViews({R.id.nw21003, R.id.nw21098, R.id.nw21099})
    List<RadioGroup> nw210b;
    @BindViews({R.id.nw21001a, R.id.nw21002a})
    List<RadioButton> nw210aYes;
    @BindViews({R.id.nw21003a, R.id.nw21098a, R.id.nw21099a})
    List<RadioButton> nw210bYes;
    Boolean endflag = false;
    String LOG_TAG = SectionB1Activity.class.getName();
    @BindViews({R.id.nw201days, R.id.nw201months, R.id.nw201years})
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
        bi.nw203a.setEnabled(false);
        bi.nw203b.setEnabled(false);

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

        bi.nw202.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!bi.nw202.getText().toString().isEmpty()) {
                    bi.curAge.setText("Current Age is: " + bi.nw202.getText().toString() + " years");
                    bi.curAge1.setText("Current Age is: " + bi.nw202.getText().toString() + " years");
                    if (Integer.valueOf(bi.nw202.getText().toString()) >= 15 && Integer.valueOf(bi.nw202.getText().toString()) < 50) {
                        bi.nw203a.setChecked(true);
                        bi.nw203b.setChecked(false);
                    } else {
                        bi.nw203b.setChecked(true);
                        bi.nw203a.setChecked(false);
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
        bi.nw202.addTextChangedListener(this);

        bi.nw203.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nw203a.isChecked()) {
                    bi.nw204a.setEnabled(true);
                    bi.nw204b.setEnabled(true);
                    bi.nw205a.setEnabled(true);
                    bi.nw205b.setEnabled(true);
                    bi.nw206.setEnabled(true);
                    bi.nw207a.setEnabled(true);
                    bi.nw207b.setEnabled(true);
                    bi.nw208a.setEnabled(true);
                    bi.nw208b.setEnabled(true);
                    bi.nw209a.setEnabled(true);
                    bi.nw209b.setEnabled(true);
                    bi.nw21001a.setEnabled(true);
                    bi.nw21002a.setEnabled(true);
                    bi.nw21003a.setEnabled(true);
                    bi.nw21098a.setEnabled(true);
                    bi.nw21099a.setEnabled(true);
                    bi.nw21001b.setEnabled(true);
                    bi.nw21002b.setEnabled(true);
                    bi.nw21003b.setEnabled(true);
                    bi.nw21098b.setEnabled(true);
                    bi.nw21099b.setEnabled(true);
                    bi.nw211.setEnabled(true);
                    bi.nw212.setEnabled(true);
                    bi.nw214.setEnabled(true);
                    bi.nw215.setEnabled(true);
                    /*bi.nw216a.setEnabled(true);
                    bi.nw216b.setEnabled(true);
                    bi.nw216aa.setEnabled(true);*/
                    bi.nw213.setEnabled(true);

                } else {
                    bi.nw204a.setEnabled(false);
                    bi.nw204b.setEnabled(false);
                    bi.nw204.clearCheck();
                    bi.nw205a.setEnabled(false);
                    bi.nw205b.setEnabled(false);
                    bi.nw205.clearCheck();
                    bi.nw206.setEnabled(false);
                    bi.nw206.setText(null);
                    bi.nw207a.setEnabled(false);
                    bi.nw207b.setEnabled(false);
                    bi.nw207.clearCheck();
                    bi.nw208a.setEnabled(false);
                    bi.nw208b.setEnabled(false);
                    bi.nw208.clearCheck();
                    bi.nw209a.setEnabled(false);
                    bi.nw209b.setEnabled(false);
                    bi.nw209.clearCheck();
                    bi.nw21001.clearCheck();
                    bi.nw21002.clearCheck();
                    bi.nw21003.clearCheck();
                    bi.nw21098.clearCheck();
                    bi.nw21099.clearCheck();
                    bi.nw21001a.setEnabled(false);
                    bi.nw21002a.setEnabled(false);
                    bi.nw21003a.setEnabled(false);
                    bi.nw21098a.setEnabled(false);
                    bi.nw21099a.setEnabled(false);
                    bi.nw21001b.setEnabled(false);
                    bi.nw21002b.setEnabled(false);
                    bi.nw21003b.setEnabled(false);
                    bi.nw21098b.setEnabled(false);
                    bi.nw21099b.setEnabled(false);
                    bi.nw211.setEnabled(false);
                    bi.nw211.setText(null);
                    bi.nw212.setEnabled(false);
                    bi.nw212.setText(null);
                    bi.nw214.setEnabled(false);
                    bi.nw215.setEnabled(false);
                  /*  bi.nw216a.setEnabled(false);
                    bi.nw216b.setEnabled(false);
                    bi.nw216.clearCheck();
                    bi.nw216aa.setEnabled(false);
                    bi.nw216aa.setText(null);*/
                    bi.nw213.setEnabled(false);
                    bi.nw213.setText(null);

                }
            }
        });

        /*bi.nw216.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (bi.nw216a.isChecked()) {
                    bi.nw216aa.setEnabled(true);
                } else {
                    bi.nw216aa.setEnabled(false);
                    bi.nw216aa.setText(null);
                }
            }
        });
*/
        bi.nw204.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nw204a.isChecked()) {
                    bi.nw205a.setEnabled(false);
                    bi.nw205b.setEnabled(false);
                    bi.nw205.clearCheck();
                    bi.nw206.setEnabled(true);
                    bi.nw207a.setEnabled(true);
                    bi.nw207b.setEnabled(true);
                    bi.nw208a.setEnabled(true);
                    bi.nw208b.setEnabled(true);
                    bi.nw209a.setEnabled(true);
                    bi.nw209b.setEnabled(true);
                    bi.nw21001a.setEnabled(true);
                    bi.nw21002a.setEnabled(true);
                    bi.nw21003a.setEnabled(true);
                    bi.nw21098a.setEnabled(true);
                    bi.nw21099a.setEnabled(true);
                    bi.nw21001b.setEnabled(true);
                    bi.nw21002b.setEnabled(true);
                    bi.nw21003b.setEnabled(true);
                    bi.nw21098b.setEnabled(true);
                    bi.nw21099b.setEnabled(true);
                    bi.nw211.setEnabled(true);
                    bi.nw212.setEnabled(true);
                    bi.nw214.setEnabled(true);
                    bi.nw215.setEnabled(true);
                   /* bi.nw216a.setEnabled(true);
                    bi.nw216b.setEnabled(true);
                    bi.nw216aa.setEnabled(true);*/
                    bi.nw213.setEnabled(true);

                } else {

                    bi.nw205a.setEnabled(true);
                    bi.nw205b.setEnabled(true);
                    bi.nw206.setEnabled(false);
                    bi.nw206.setText(null);
                    bi.nw207a.setEnabled(false);
                    bi.nw207b.setEnabled(false);
                    bi.nw207.clearCheck();
                    bi.nw208a.setEnabled(false);
                    bi.nw208b.setEnabled(false);
                    bi.nw208.clearCheck();
                    bi.nw209a.setEnabled(false);
                    bi.nw209b.setEnabled(false);
                    bi.nw209.clearCheck();
                    bi.nw21001.clearCheck();
                    bi.nw21002.clearCheck();
                    bi.nw21003.clearCheck();
                    bi.nw21098.clearCheck();
                    bi.nw21099.clearCheck();
                    bi.nw21001.setEnabled(false);
                    bi.nw21002.setEnabled(false);
                    bi.nw21003.setEnabled(false);
                    bi.nw21098.setEnabled(false);
                    bi.nw21099.setEnabled(false);
                    bi.nw211.setEnabled(false);
                    bi.nw211.setText(null);
                    bi.nw212.setEnabled(false);
                    bi.nw212.setText(null);
                    bi.nw214.setEnabled(false);
                    bi.nw215.setEnabled(false);
                    /*bi.nw216a.setEnabled(false);
                    bi.nw216b.setEnabled(false);
                    bi.nw216.clearCheck();
                    bi.nw216aa.setEnabled(false);
                    bi.nw216aa.setText(null);*/
                    bi.nw213.setEnabled(false);
                    bi.nw213.setText(null);

                }
            }
        });

        bi.nw205.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nw205a.isChecked()) {
                    bi.nw206.setEnabled(true);
                    bi.nw207a.setEnabled(true);
                    bi.nw207b.setEnabled(true);
                    bi.nw211.setEnabled(true);
                    bi.nw212.setEnabled(true);
                    bi.nw215.setEnabled(true);
                    bi.nw214.setEnabled(true);
                    /*bi.nw216a.setEnabled(true);
                    bi.nw216b.setEnabled(true);
                    bi.nw216aa.setEnabled(true);*/
                    bi.nw215.setEnabled(true);
                    bi.nw213.setEnabled(true);
                    bi.nw208a.setEnabled(true);
                    bi.nw208b.setEnabled(true);
                    bi.nw209a.setEnabled(true);
                    bi.nw209b.setEnabled(true);
//                    w213
                    bi.nw21001a.setEnabled(true);
                    bi.nw21002a.setEnabled(true);
                    bi.nw21003a.setEnabled(true);
                    bi.nw21098a.setEnabled(true);
                    bi.nw21099a.setEnabled(true);
                    bi.nw21001b.setEnabled(true);
                    bi.nw21002b.setEnabled(true);
                    bi.nw21003b.setEnabled(true);
                    bi.nw21098b.setEnabled(true);
                    bi.nw21099b.setEnabled(true);

                } else {
                    bi.nw206.setEnabled(false);
                    bi.nw206.setText(null);
                    bi.nw207a.setEnabled(false);
                    bi.nw207b.setEnabled(false);
                    bi.nw207.clearCheck();
                    bi.nw211.setEnabled(false);
                    bi.nw211.setText(null);
                    bi.nw212.setEnabled(false);
                    bi.nw212.setText(null);
                    bi.nw215.setEnabled(false);
                    bi.nw214.setEnabled(false);
                    /*bi.nw216a.setEnabled(false);
                    bi.nw216b.setEnabled(false);
                    bi.nw216.clearCheck();
                    bi.nw216aa.setEnabled(false);
                    bi.nw216aa.setText(null);*/
                    bi.nw213.setEnabled(false);
                    bi.nw213.setText(null);
                    bi.nw208a.setEnabled(false);
                    bi.nw208b.setEnabled(false);
                    bi.nw208.clearCheck();
                    bi.nw209a.setEnabled(false);
                    bi.nw209b.setEnabled(false);
                    bi.nw209.clearCheck();
                    bi.nw21001.clearCheck();
                    bi.nw21002.clearCheck();
                    bi.nw21003.clearCheck();
                    bi.nw21098.clearCheck();
                    bi.nw21099.clearCheck();
                    bi.nw21001a.setEnabled(false);
                    bi.nw21002a.setEnabled(false);
                    bi.nw21003a.setEnabled(false);
                    bi.nw21098a.setEnabled(false);
                    bi.nw21099a.setEnabled(false);
                    bi.nw21001b.setEnabled(false);
                    bi.nw21002b.setEnabled(false);
                    bi.nw21003b.setEnabled(false);
                    bi.nw21098b.setEnabled(false);
                    bi.nw21099b.setEnabled(false);

                }
            }
        });

        bi.nw206.addTextChangedListener(this);

        bi.nw207.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (checkedId == R.id.nw207a) {

                    bi.nw211.setEnabled(true);
                    bi.nw212.setEnabled(true);
                    bi.nw215.setEnabled(true);
                    bi.nw214.setEnabled(true);
                   /* bi.nw216a.setEnabled(true);
                    bi.nw216b.setEnabled(true);
                    bi.nw216aa.setEnabled(true);*/
                    bi.nw213.setEnabled(true);
                    bi.nw208a.setEnabled(true);
                    bi.nw208b.setEnabled(true);
                    bi.nw209a.setEnabled(true);
                    bi.nw209b.setEnabled(true);
                    bi.nw21001a.setEnabled(true);
                    bi.nw21002a.setEnabled(true);
                    bi.nw21003a.setEnabled(true);
                    bi.nw21098a.setEnabled(true);
                    bi.nw21099a.setEnabled(true);
                    bi.nw21001b.setEnabled(true);
                    bi.nw21002b.setEnabled(true);
                    bi.nw21003b.setEnabled(true);
                    bi.nw21098b.setEnabled(true);
                    bi.nw21099b.setEnabled(true);
                } else {
                    bi.nw211.setEnabled(false);
                    bi.nw211.setText(null);
                    bi.nw212.setEnabled(false);
                    bi.nw215.setEnabled(false);
                    bi.nw212.setText(null);
                    bi.nw215.setText(null);
                    bi.nw214.setEnabled(false);
                    /*bi.nw216a.setEnabled(false);
                    bi.nw216b.setEnabled(false);
                    bi.nw216.clearCheck();
                    bi.nw216aa.setEnabled(false);
                    bi.nw216aa.setText(null);*/
                    bi.nw213.setEnabled(false);
                    bi.nw213.setText(null);
                    bi.nw208a.setEnabled(false);
                    bi.nw208b.setEnabled(false);
                    bi.nw208.clearCheck();
                    bi.nw209a.setEnabled(false);
                    bi.nw209b.setEnabled(false);
                    bi.nw209.clearCheck();
                    bi.nw21001.clearCheck();
                    bi.nw21002.clearCheck();
                    bi.nw21003.clearCheck();
                    bi.nw21098.clearCheck();
                    bi.nw21099.clearCheck();

                    bi.nw21001a.setEnabled(false);
                    bi.nw21002a.setEnabled(false);
                    bi.nw21003a.setEnabled(false);
                    bi.nw21098a.setEnabled(false);
                    bi.nw21099a.setEnabled(false);

                    bi.nw21001b.setEnabled(false);
                    bi.nw21002b.setEnabled(false);
                    bi.nw21003b.setEnabled(false);
                    bi.nw21098b.setEnabled(false);
                    bi.nw21099b.setEnabled(false);
                }
            }
        });

        bi.nw211.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bi.nw207a.isChecked() && bi.nw208a.isChecked()) {
                    if (bi.nw211.getText().toString().equals("1")) {
                        bi.nw212.setEnabled(false);
                        bi.nw213.setEnabled(false);
                        //bi.nw214.setEnabled(false);
                        bi.nw215.setEnabled(false);
                        /*bi.nw216a.setEnabled(false);
                        bi.nw216b.setEnabled(false);
                        bi.nw216aa.setEnabled(false);*/
                        bi.nw212.setText("0");
                        bi.nw213.setText(null);
                        //bi.nw214.setText(null);
                        bi.nw215.setText(null);
                        /*bi.nw216.clearCheck();
                        bi.nw216aa.setText(null);*/

                    } else {
                        bi.nw212.setEnabled(true);
                        bi.nw213.setEnabled(true);
                        bi.nw214.setEnabled(true);
                        bi.nw215.setEnabled(true);
                       /* bi.nw216a.setEnabled(true);
                        bi.nw216b.setEnabled(true);
                        bi.nw216aa.setEnabled(true);*/
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        bi.nw212.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (bi.nw207a.isChecked() && bi.nw208a.isChecked() && bi.nw211.getText().toString().equals("1")) {
                    if (bi.nw212.getText().toString().equals("0")) {
                        bi.nw213.setEnabled(false);
                        bi.nw213.setText(null);

                        /*bi.nw214.setEnabled(false);
                        bi.nw214.setText(null);
                        bi.nw215.setEnabled(false);
                        bi.nw215.setText(null);*/

                       /* bi.nw216a.setEnabled(false);
                        bi.nw216b.setEnabled(false);
                        bi.nw216.clearCheck();
                        bi.nw216aa.setEnabled(false);
                        bi.nw216aa.setText(null);*/

                    } else {

                        bi.nw213.setEnabled(true);

                        /*bi.nw214.setEnabled(true);
                        bi.nw215.setEnabled(true);*/

                        /*bi.nw216a.setEnabled(true);
                        bi.nw216b.setEnabled(true);
                        bi.nw216aa.setEnabled(true);*/

                    }
                } else if (bi.nw207a.isChecked() && !bi.nw208a.isChecked() && bi.nw211.getText().toString().equals("1")) {
                    if (bi.nw212.getText().toString().equals("0")) {
                        bi.nw213.setEnabled(false);
                        bi.nw213.setText(null);

                        /*bi.nw214.setEnabled(false);
                        bi.nw214.setText(null);
                        bi.nw215.setEnabled(false);
                        bi.nw215.setText(null);*/

                        /*bi.nw216a.setEnabled(false);
                        bi.nw216b.setEnabled(false);
                        bi.nw216.clearCheck();
                        bi.nw216aa.setEnabled(false);
                        bi.nw216aa.setText(null);
*/
                    } else {

                        bi.nw213.setEnabled(true);

                        /*bi.nw214.setEnabled(true);
                        bi.nw215.setEnabled(true);*/

                       /* bi.nw216a.setEnabled(true);
                        bi.nw216b.setEnabled(true);
                        bi.nw216aa.setEnabled(true);*/

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        bi.nw214.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (bi.nw214.getText().toString().equals("0")) {
                    bi.nw215.setEnabled(false);
                    bi.nw215.setText(null);

                } else if (bi.nw214.getText().toString().equals("1") && bi.nw208a.isChecked()) {
                    bi.nw215.setEnabled(false);
                    bi.nw215.setText(null);

                } else {
                    bi.nw215.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        bi.nw208.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (checkedId == R.id.nw208a) {
                    bi.nw209a.setEnabled(true);
                    bi.nw209b.setEnabled(true);

                    bi.nw21001a.setEnabled(true);
                    bi.nw21002a.setEnabled(true);
                    bi.nw21003a.setEnabled(true);
                    bi.nw21098a.setEnabled(true);
                    bi.nw21099a.setEnabled(true);

                    bi.nw21001b.setEnabled(true);
                    bi.nw21002b.setEnabled(true);
                    bi.nw21003b.setEnabled(true);
                    bi.nw21098b.setEnabled(true);
                    bi.nw21099b.setEnabled(true);
                } else {
                    bi.nw209a.setEnabled(false);
                    bi.nw209b.setEnabled(false);
                    bi.nw209.clearCheck();

                    bi.nw21001.clearCheck();
                    bi.nw21002.clearCheck();
                    bi.nw21003.clearCheck();
                    bi.nw21098.clearCheck();
                    bi.nw21099.clearCheck();
                    bi.nw21001.clearCheck();
                    bi.nw21002.clearCheck();
                    bi.nw21003.clearCheck();
                    bi.nw21098.clearCheck();
                    bi.nw21099.clearCheck();

                    bi.nw21001a.setEnabled(false);
                    bi.nw21002a.setEnabled(false);
                    bi.nw21003a.setEnabled(false);
                    bi.nw21098a.setEnabled(false);
                    bi.nw21099a.setEnabled(false);

                    bi.nw21001b.setEnabled(false);
                    bi.nw21002b.setEnabled(false);
                    bi.nw21003b.setEnabled(false);
                    bi.nw21098b.setEnabled(false);
                    bi.nw21099b.setEnabled(false);

                    bi.nw211.setText(null);
                    bi.nw212.setEnabled(true);
                    bi.nw212.setText(null);
                    bi.nw213.setEnabled(true);
                    bi.nw213.setText(null);
                    bi.nw214.setText(null);
                    bi.nw214.setEnabled(true);
                    bi.nw215.setText(null);
                    bi.nw215.setEnabled(true);
                }

            }
        });
        bi.nw209.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (checkedId == R.id.nw209a) {
                    bi.nw21001a.setEnabled(true);
                    bi.nw21002a.setEnabled(true);
                    bi.nw21003a.setEnabled(true);
                    bi.nw21098a.setEnabled(true);
                    bi.nw21099a.setEnabled(true);

                    bi.nw21001b.setEnabled(true);
                    bi.nw21002b.setEnabled(true);
                    bi.nw21003b.setEnabled(true);
                    bi.nw21098b.setEnabled(true);
                    bi.nw21099b.setEnabled(true);

                } else {
                    bi.nw21001.clearCheck();
                    bi.nw21002.clearCheck();
                    bi.nw21003.clearCheck();
                    bi.nw21098.clearCheck();
                    bi.nw21099.clearCheck();

                    bi.nw21001a.setEnabled(false);
                    bi.nw21002a.setEnabled(false);
                    bi.nw21003a.setEnabled(false);
                    bi.nw21098a.setEnabled(false);
                    bi.nw21099a.setEnabled(false);

                    bi.nw21001b.setEnabled(false);
                    bi.nw21002b.setEnabled(false);
                    bi.nw21003b.setEnabled(false);
                    bi.nw21098b.setEnabled(false);
                    bi.nw21099b.setEnabled(false);

                }
            }
        });

        RadioGroup.OnCheckedChangeListener nw210aListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (isoneYes()) {
                    bi.nw21003a.setEnabled(false);
                    bi.nw21003b.setEnabled(false);
                    bi.nw21003.clearCheck();
                    bi.nw21098a.setEnabled(false);
                    bi.nw21098b.setEnabled(false);
                    bi.nw21098.clearCheck();
                    bi.nw21099a.setEnabled(false);
                    bi.nw21099b.setEnabled(false);
                    bi.nw21099.clearCheck();
                } else {
                    bi.nw21003a.setEnabled(true);
                    bi.nw21003b.setEnabled(true);
                    bi.nw21098a.setEnabled(true);
                    bi.nw21098b.setEnabled(true);
                    bi.nw21099a.setEnabled(true);
                    bi.nw21099b.setEnabled(true);
                }

            }
        };

        RadioGroup.OnCheckedChangeListener nw210bListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ValidateForm();
                if (isoneYes2()) {
                    bi.nw21001a.setEnabled(false);
                    bi.nw21001b.setEnabled(false);
                    bi.nw21001.clearCheck();
                    bi.nw21002a.setEnabled(false);
                    bi.nw21002b.setEnabled(false);
                    bi.nw21002.clearCheck();
                } else {
                    bi.nw21001a.setEnabled(true);
                    bi.nw21001b.setEnabled(true);
                    bi.nw21002a.setEnabled(true);
                    bi.nw21002b.setEnabled(true);

                }
            }
        };

        // Nw210 Skips

        for (RadioGroup rg : nw210a) {
            rg.setOnCheckedChangeListener(nw210aListener);
        }

        for (RadioGroup rg : nw210b) {
            rg.setOnCheckedChangeListener(nw210bListener);
        }


        bi.nw213.addTextChangedListener(this);
        bi.nw214.addTextChangedListener(this);
        bi.nw215.addTextChangedListener(this);
        //bi.nw216aa.addTextChangedListener(this);

        bi.na11801.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.na11801b) {
                    bi.nw203.clearCheck();
                    bi.nw204.clearCheck();
                    bi.nw205.clearCheck();
                    bi.nw207.clearCheck();
                    bi.nw208.clearCheck();
                    bi.nw209.clearCheck();
                    bi.nw21001.clearCheck();
                    bi.nw21002.clearCheck();
                    bi.nw21003.clearCheck();
                    bi.nw21098.clearCheck();
                    bi.nw21099.clearCheck();
                    //bi.nw216.clearCheck();
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

        if (!MainApp.mc.getsB1().equals("")) {

            jsonB1 = JSONUtilClass.getModelFromJSON(MainApp.mc.getsB1(), JSONB1ModelClass.class);

            MainApp.mc.setCluster(jsonB1.getCluster_no());
            MainApp.mc.setHhno(jsonB1.getHhno());

            if (!jsonB1.getnh11801().equals("0")) {
                bi.na11801.check(
                        jsonB1.getnh11801().equals("1") ? bi.na11801a.getId() :
                                bi.na11801b.getId()
                );
            }

            if (!jsonB1.getnw216aa().equals("")) {
                prevMiscarriages = Integer.valueOf(jsonB1.getnw216aa());
            }

            bi.nb101a.setText(jsonB1.getnw101());

            bi.nw201years.setText(jsonB1.getnw201years());
            bi.nw201months.setText(jsonB1.getnw201months());
            bi.nw201days.setText(jsonB1.getnw201days());
            bi.nw202.setText(jsonB1.getnw202());

            if (!jsonB1.getnw203().equals("0")) {
                bi.nw203.check(
                        jsonB1.getnw203().equals("1") ? bi.nw203a.getId() :
                                bi.nw203b.getId()
                );
            }
            if (!jsonB1.getnw204().equals("0")) {
                bi.nw204.check(
                        jsonB1.getnw204().equals("1") ? bi.nw204a.getId() :
                                bi.nw204b.getId()
                );
            }
            if (!jsonB1.getnw205().equals("0")) {
                bi.nw205.check(
                        jsonB1.getnw205().equals("1") ? bi.nw205a.getId() :
                                bi.nw205b.getId()
                );
            }

            bi.nw206.setText(jsonB1.getnw206());

            if (!jsonB1.getnw205().equals("0")) {
                bi.nw205.check(
                        jsonB1.getnw205().equals("1") ? bi.nw205a.getId() :
                                bi.nw205b.getId()
                );
            }
            if (!jsonB1.getnw207().equals("0")) {
                bi.nw207.check(
                        jsonB1.getnw207().equals("1") ? bi.nw207a.getId() :
                                bi.nw207b.getId()
                );
            }
            if (!jsonB1.getnw208().equals("0")) {
                bi.nw208.check(
                        jsonB1.getnw208().equals("1") ? bi.nw208a.getId() :
                                bi.nw208b.getId()
                );
            }
            if (!jsonB1.getnw209().equals("0")) {
                bi.nw209.check(
                        jsonB1.getnw209().equals("1") ? bi.nw209a.getId() :
                                bi.nw209b.getId()
                );
            }

            if (jsonB1.getnw21001().equals("1")) {
                bi.nw21001a.setChecked(true);
            }
            if (jsonB1.getnw21002().equals("1")) {
                bi.nw21002a.setChecked(true);
            }
            if (jsonB1.getnw21003().equals("1")) {
                bi.nw21003a.setChecked(true);
            }
            if (jsonB1.getnw21098().equals("1")) {
                bi.nw21098a.setChecked(true);
            }
            if (jsonB1.getnw21099().equals("1")) {
                bi.nw21099a.setChecked(true);
            }

            bi.nw211.setText(jsonB1.getnw211());
            bi.nw212.setText(jsonB1.getnw212());
            bi.nw213.setText(jsonB1.getnw213());
            bi.nw214.setText(jsonB1.getnw214());

            if (!jsonB1.getnw214().equals("")) {
                prevDeliveries = Integer.valueOf(jsonB1.getnw214());
            }

            bi.nw215.setText(jsonB1.getnw215());


           /* if (!jsonB1.getnw216().equals("0")) {
                bi.nw216.check(
                        jsonB1.getnw216().equals("1") ? bi.nw216a.getId() :
                                bi.nw216b.getId()
                );
            }
            bi.nw216aa.setText(jsonB1.getnw216aa());*/

        }

    }

    public void BtnContinue() {

//        Validation Boolean
        MainApp.validateFlag = true;

        //Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                //Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();

                MainApp.nuCount = 1;
                MainApp.count = 1;

                frontPressed = true;

                if (!editWRAFlag) {
                    WRAsize = MainApp.mwra.size();
                }

                //finish();

                if (bi.nw203a.isChecked()) {
                    if (bi.nw204a.isChecked() || bi.nw205a.isChecked()) {
                        if (bi.nw207a.isChecked()) {
                            /*if (bi.nw216a.isChecked()) {
                                if (Integer.valueOf(bi.nw216aa.getText().toString()) > 0) {

                                    if (Integer.valueOf(bi.nw216aa.getText().toString()) < prevMiscarriages) {

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
                                        MainApp.totalPregnancy = Integer.valueOf(bi.nw214.getText().toString());

                                        startActivityForResult(new Intent(this, SectionB1AActivity.class)
                                                .putExtra("backPressed", classPassName.equals(SectionB1AActivity.class.getName())), 1);

                                    }
                                }

                            } else if (childCheck) {
                                startActivity(new Intent(this, SectionB2Activity.class));
                            } else {
                                redirectCondition();
                            }*/
                            MainApp.currentlyPregnant = bi.nw208a.isChecked() ? 1 : bi.nw208b.isChecked() ? 2 : 0; // when women is currently pregnant outcome should be open according to totalPregnencies-1

                            if (Integer.valueOf(bi.nw214.getText().toString()) > 0) {
                                if (Integer.valueOf(bi.nw214.getText().toString()) == 1 && MainApp.currentlyPregnant == 1) {
                                    redirectCondition();
                                } else if (Integer.valueOf(bi.nw214.getText().toString()) < prevDeliveries) {

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
                                    MainApp.totalPregnancy = Integer.valueOf(bi.nw214.getText().toString());
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
                //Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();

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
            MainApp.mc.setsB6(wraMap.get(bi.nb101.getSelectedItem().toString()).getKishSelected());

            wraName = bi.nb101.getSelectedItem().toString();

            sB1.put("cluster_no", MainApp.fc.getClusterNo());
            sB1.put("hhno", MainApp.fc.getHhNo());
            sB1.put("nw101", bi.nb101.getSelectedItem().toString());
            sB1.put("wra_lno", wraMap.get(bi.nb101.getSelectedItem().toString()).getSerialNo());
            try {
                prevDeliveries = Integer.valueOf(bi.nw214.getText().toString());
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }

        } else {

            sB1.put("updatedate_nw1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

            if (editWRAFlag && !frontPressed) {
                sB1.put("edit_updatedate_nw1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

                wraName = jsonB1.getnw101();

                sB1.put("cluster_no", jsonB1.getCluster_no());
                sB1.put("hhno", jsonB1.getHhno());
                sB1.put("nw101", jsonB1.getnw101());
                sB1.put("wra_lno", jsonB1.getnw1serialno());

            } else if (editWRAFlag) {

                wraName = jsonB1.getnw101();

                sB1.put("cluster_no", jsonB1.getCluster_no());
                sB1.put("hhno", jsonB1.getHhno());
                sB1.put("nw101", jsonB1.getnw101());
                sB1.put("wra_lno", jsonB1.getnw1serialno());

            } else {

                wraName = bi.nb101.getSelectedItem().toString();

                sB1.put("cluster_no", MainApp.fc.getClusterNo());
                sB1.put("hhno", MainApp.fc.getHhNo());
                sB1.put("nw101", bi.nb101.getSelectedItem().toString());
                sB1.put("wra_lno", wraMap.get(bi.nb101.getSelectedItem().toString()).getSerialNo());

            }
//            MainApp.mc.set_UID(MainApp.mc.get_UID());
        }

        sB1.put("nw11801", bi.na11801a.isChecked() ? "1" : bi.na11801b.isChecked() ? "2" : "0");
        //        nw201
        sB1.put("nw201days", bi.nw201days.getText().toString());
        sB1.put("nw201months", bi.nw201months.getText().toString());
        sB1.put("nw201years", bi.nw201years.getText().toString());
        //        nw202
        sB1.put("nw202", bi.nw202.getText().toString());
        //        nw203
        sB1.put("nw203", bi.nw203a.isChecked() ? "1" : bi.nw203b.isChecked() ? "2" : "0");
        //        nw204
        sB1.put("nw204", bi.nw204a.isChecked() ? "1" : bi.nw204b.isChecked() ? "2" : "0");
        //        nw205
        sB1.put("nw205", bi.nw205a.isChecked() ? "1" : bi.nw205b.isChecked() ? "2" : "0");
        //        nw206
        sB1.put("nw206", bi.nw206.getText().toString());
        //        nw207
        sB1.put("nw207", bi.nw207a.isChecked() ? "1" : bi.nw207b.isChecked() ? "2" : "0");
        sB1.put("nw208", bi.nw208a.isChecked() ? "1" : bi.nw208b.isChecked() ? "2" : "0");

        sB1.put("nw209", bi.nw209a.isChecked() ? "1" : bi.nw209b.isChecked() ? "2" : "0");

        //        nw21001
        sB1.put("nw21001", bi.nw21001a.isChecked() ? "1" : bi.nw21001b.isChecked() ? "2" : "0");
        //        nw21002
        sB1.put("nw21002", bi.nw21002a.isChecked() ? "1" : bi.nw21002b.isChecked() ? "2" : "0");
        //        nw21003
        sB1.put("nw21003", bi.nw21003a.isChecked() ? "1" : bi.nw21003b.isChecked() ? "2" : "0");
        //        nw21098
        sB1.put("nw21098", bi.nw21098a.isChecked() ? "1" : bi.nw21098b.isChecked() ? "2" : "0");
        //        nw21099
        sB1.put("nw21099", bi.nw21099a.isChecked() ? "1" : bi.nw21099b.isChecked() ? "2" : "0");

        sB1.put("nw211", bi.nw211.getText().toString());
        sB1.put("nw212", bi.nw212.getText().toString());
        sB1.put("nw213", bi.nw213.getText().toString());
        sB1.put("nw214", bi.nw214.getText().toString());
        sB1.put("nw215", bi.nw215.getText().toString());
       /* sB1.put("nw216", bi.nw216a.isChecked() ? "1" : bi.nw216b.isChecked() ? "2" : "0");
        sB1.put("nw216aa", bi.nw216aa.getText().toString());*/

        /*if (bi.nw216a.isChecked() && !bi.nw216aa.getText().toString().isEmpty()) {
            MainApp.totalPregnancy = Integer.valueOf(bi.nw216aa.getText().toString());
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

                if (!ValidatorClass.EmptyTextBox(this, bi.nw201days, getString(R.string.day))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, bi.nw201days, 1, 31, 98, "Range 1-31 or 98", getString(R.string.day))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.nw201months, getString(R.string.months))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, bi.nw201months, 1, 12, 98, "Range 1-12 or 98", getString(R.string.months))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.nw201years, getString(R.string.year2))) {
                    return false;
                }

                Date date = new Date(); // Current date
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);

                if (!ValidatorClass.RangeTextBox(this, bi.nw201years, year - 49, year - 15, 9998,
                        "Range " + (year - 49) + " - " + (year - 15), getString(R.string.year2))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.nw202, getString(R.string.nw202))) {
                    return false;
                }
                if (!ValidatorClass.RangeTextBox(this, bi.nw202, 15, 49, "Range 15-49", getString(R.string.year))) {
                    return false;
                }


                if (!ValidatorClass.EmptyRadioButton(this, bi.nw203, bi.nw203b, getString(R.string.nw203))) {
                    return false;
                }

                if (bi.nw203a.isChecked()) {

                    if (!ValidatorClass.EmptyRadioButton(this, bi.nw204, bi.nw204a, getString(R.string.nw204))) {
                        return false;
                    }

                    if (bi.nw204b.isChecked()) {
                        if (!ValidatorClass.EmptyRadioButton(this, bi.nw205, bi.nw205a, getString(R.string.nw205))) {
                            return false;
                        }
                    }

                    if (bi.nw204a.isChecked() || bi.nw205a.isChecked()) {

                        //if (bi.nw204a.isChecked() || bi.nw205a.isChecked()) {
                        if (!ValidatorClass.EmptyTextBox(this, bi.nw206, getString(R.string.nw206))) {
                            return false;
                        }

                        if (!ValidatorClass.RangeTextBox(this, bi.nw206, 10, Integer.valueOf(bi.nw202.getText().toString()), getString(R.string.nw206), " years")) {
                            return false;
                        }

                        if (!ValidatorClass.EmptyRadioButton(this, bi.nw207, bi.nw207a, getString(R.string.nw207))) {
                            return false;
                        }

                        if (bi.nw207a.isChecked()) {

                            if (!ValidatorClass.EmptyRadioButton(this, bi.nw208, bi.nw208a, getString(R.string.nw208))) {
                                return false;
                            }

                            if (bi.nw208a.isChecked()) {

                                if (!ValidatorClass.EmptyRadioButton(this, bi.nw209, bi.nw209a, getString(R.string.nw210))) {
                                    return false;
                                }

                                if (bi.nw209a.isChecked()) {

                                    if (!bi.nw21098a.isChecked() && !bi.nw21099a.isChecked() && !bi.nw21003a.isChecked()) {
                                        if (!ValidatorClass.EmptyRadioButton(this, bi.nw21001, bi.nw21001a, getString(R.string.nw21001))) {
                                            return false;
                                        }

                                        if (!ValidatorClass.EmptyRadioButton(this, bi.nw21002, bi.nw21002a, getString(R.string.nw21002))) {
                                            return false;
                                        }

                                    }
                                }

                            }

                            if (!ValidatorClass.EmptyTextBox(this, bi.nw211, getString(R.string.nw211))) {
                                return false;
                            }

                            if (!ValidatorClass.RangeTextBox(this, bi.nw211, 1, 20, getString(R.string.nw211), " pregnancies")) {
                                return false;
                            }

                            if (!ValidatorClass.EmptyTextBox(this, bi.nw212, getString(R.string.nw212))) {
                                return false;
                            }


                            if (bi.nw208a.isChecked()) {
                                if (!ValidatorClass.RangeTextBox(this, bi.nw212, 0, (Integer.valueOf(bi.nw211.getText().toString()) - 1), getString(R.string.nw212), " Deliveries")) {
                                    return false;
                                }
                            } else {
                                if (!ValidatorClass.RangeTextBox(this, bi.nw212, 0, Integer.valueOf(bi.nw211.getText().toString()), getString(R.string.nw212), " Deliveries")) {
                                    return false;
                                }
                            }

                            if (!bi.nw212.getText().toString().equals("0")) {
                                if (!ValidatorClass.EmptyTextBox(this, bi.nw213, getString(R.string.nw213))) {
                                    return false;
                                }

                                if (!ValidatorClass.EmptyTextBox(this, bi.nw213, getString(R.string.nw213))) {
                                    return false;
                                }

                                if (!ValidatorClass.RangeTextBox(this, bi.nw213, Integer.valueOf(bi.nw206.getText().toString()), Integer.valueOf(bi.nw202.getText().toString()), getString(R.string.nw213), " years")) {
                                    return false;
                                }

                            }
                            if (!ValidatorClass.EmptyTextBox(this, bi.nw214, getString(R.string.nw214))) {
                                return false;
                            }

                            if (!ValidatorClass.RangeTextBox(this, bi.nw214, 0, Integer.valueOf(bi.nw211.getText().toString()), getString(R.string.nw211), " Deliveries")) {
                                return false;
                            }

                            if (Integer.valueOf(bi.nw214.getText().toString()) > 0) {

                                if (Integer.valueOf(bi.nw214.getText().toString()) == 1 && bi.nw208a.isChecked()) {

                                } else {
                                    if (!ValidatorClass.EmptyTextBox(this, bi.nw215, getString(R.string.nw215))) {
                                        return false;
                                    }
                                    return ValidatorClass.RangeTextBox(this, bi.nw215, 0, Integer.valueOf(bi.nw212.getText().toString()), getString(R.string.nw212), " Deliveries");
                                }

                            } else {
                                if (bi.nw208a.isChecked()) {
                                    Toast.makeText(this, "The women is currently pregrnant. Previous pregnencies should be 1", Toast.LENGTH_LONG).show();
                                    bi.nw214.setError("The women is currently pregrnant. Previous pregnencies should be 1");
                                    return false;
                                } else {
                                    bi.nw214.setError("null");
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
        for (RadioButton rg : nw210aYes) {
            if (rg.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    public boolean isoneYes2() {

        int i = 0;
        for (RadioButton rg : nw210bYes) {
            if (rg.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

}