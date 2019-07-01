package edu.aku.hassannaqvi.casi_2018.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONA1ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.BLRandomContract;
import edu.aku.hassannaqvi.casi_2018.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.casi_2018.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionA1Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.other.MembersCount;
import edu.aku.hassannaqvi.casi_2018.validation.validatorClass;

public class SectionA1Activity extends Menu2Activity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = SectionA1Activity.class.getName();
    public static Boolean editFormFlag;
    static int progress = 0;
    static Boolean reBackChildFlag = true;
    static Boolean reBackFlag = true;
    private final long DELAY = 1000;
    ActivitySectionA1Binding binding;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    DatabaseHelper db;
    Collection<BLRandomContract> selected;
    int progressStatus = 0;
    Handler handler = new Handler();
    Boolean flag = false;
    int length = 0;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_a1);
        db = new DatabaseHelper(this);
        binding.setCallback(this);

        SetupViewFunctionality();

        SkipPatterns();

//        Edit form intent
        editFormFlag = getIntent().getBooleanExtra("editForm", false);

        if (editFormFlag) {

            binding.nh102.setText(getIntent().getStringExtra("clusterNo"));
            binding.nh102.setEnabled(false);
            binding.checkClusterBtn.setEnabled(false);
            binding.checkClusterBtn.setBackgroundColor(getResources().getColor(R.color.red));
            BtnCheckEnm();
            binding.nh108.setText(getIntent().getStringExtra("hhNo"));
            binding.nh108.setEnabled(false);
            BtnCheckHH();
            binding.checkHHBtn.setEnabled(false);
            binding.checkHHBtn.setBackgroundColor(getResources().getColor(R.color.red));

            AutoCompleteFields();
        }

        this.setTitle(getResources().getString(R.string.na1heading));

//        Validation Boolean
        MainApp.validateFlag = false;

    }

    public void AutoCompleteFields() {

        MainApp.fc = db.getPressedForms(binding.nh102.getText().toString()
                , binding.nh108.getText().toString());

        if (MainApp.fc != null) {

            JSONA1ModelClass jsonA1 = JSONUtilClass.getModelFromJSON(MainApp.fc.getsA1(), JSONA1ModelClass.class);

            if (jsonA1.getHhheadpresent().equals("1")) {
                binding.checkHHHeadpresent.setChecked(true);
            } else {
                binding.newHHheadname.setText(jsonA1.getHhheadpresentnew());
            }

            binding.nh101.setText(jsonA1.getnh101());
            binding.nh103.setText(jsonA1.getnh103());
            binding.nh104.setText(jsonA1.getnh104());
            binding.nh105.setText(jsonA1.getnh105());
            binding.nh106.setText(jsonA1.getnh106());

            binding.nh113.setText(jsonA1.getnh113());
            binding.nh115.setText(jsonA1.getnh115());
            binding.nh213.setText(jsonA1.getnh213());

            if (!jsonA1.getnh11801().equals("0")) {
                binding.na11801.check(
                        jsonA1.getnh11801().equals("1") ? binding.na11801a.getId() :
                                binding.na11801b.getId()
                );

                binding.na11801b.setEnabled(false);
            }

           /* if (!jsonA1.getnh11802().equals("0")) {
                binding.na11802.check(
                        jsonA1.getnh11802().equals("1") ? binding.na11802a.getId() :
                                binding.na11802b.getId()
                );

                binding.na11802b.setEnabled(false);
            }
*/

        }

    }

    private void SkipPatterns() {

        // binding.na11802.setOnCheckedChangeListener(this);


    }

    public void SetupViewFunctionality() {

        //  Members Initialization
        MainApp.membersCount = new MembersCount();

        //  Setting members in map for Section A2
        Map<Integer, Map<Integer, Integer>> mem = new HashMap<>();
        Map<Integer, Integer> memType = new HashMap<>();
        memType.put(1, 0);
        memType.put(2, 0);

        mem.put(1, memType);
        mem.put(2, memType);
        mem.put(3, memType);

        MainApp.membersCount.setMembers(mem);
        MainApp.membersCount.setMwra(0);
        MainApp.membersCount.setWra(0);

        MainApp.members_f_m = new ArrayList<>();
        MainApp.respList = new ArrayList<>();
        MainApp.all_members = new ArrayList<>();
        MainApp.childUnder2 = new ArrayList<>();
        MainApp.childUnder5 = new ArrayList<>();
        MainApp.childUnder5_Del = new ArrayList<>();
        MainApp.childUnder2Check = new ArrayList<>();
        MainApp.childNA = new ArrayList<>();
        MainApp.mwra = new ArrayList<>();
        MainApp.adolescents = new ArrayList<>();
        MainApp.serial_no = 0;

//        Checking IsHead
        MainApp.IsHead = false;
        MainApp.IsResp = false;

//        Checking B6 Section
        MainApp.B6Flag = true;
        MainApp.B2B6Flag = false;

//        Listener

        binding.nh102.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.nh108.setText(null);
                binding.fldGrpnh101.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


//        FamilyMembersList initialization
        MainApp.familyMembersList = new ArrayList<>();
        MainApp.hhClicked = new ArrayList<>();
        MainApp.flagClicked = new ArrayList<>();
        MainApp.familyMembersClicked = new HashMap<>();


//        HH listener
        binding.nh108.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                binding.nh108.setInputType(InputType.TYPE_CLASS_NUMBER);
                length = charSequence.toString().length();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                clearFields();


                if (!binding.nh108.getText().toString().isEmpty() && binding.nh108.getText().toString().length() == 4) {
                    if (binding.nh108.getText().toString().substring(0, 3).matches("[0-9]+")) {
                        if (length < 5) {
                            binding.nh108.setText(binding.nh108.getText().toString() + "-");
                            binding.nh108.setSelection(binding.nh108.getText().length());
                            //binding.nh108.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                        }

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        if (MainApp.cluster_no.equals("")) {
            binding.nh102.setText(null);
        } else {
            binding.nh102.setText(MainApp.cluster_no);

            EnumBlockContract enumBlockContract = db.getEnumBlock(binding.nh102.getText().toString());
            if (enumBlockContract != null) {
                String selected = enumBlockContract.getGeoarea();
                if (!selected.equals("")) {

                    String[] selSplit = selected.split("\\|");

                    binding.nh103.setText(selSplit[0]);
                    binding.nh104.setText(selSplit[1].equals("") ? "----" : selSplit[1]);
                    binding.nh105.setText(selSplit[2].equals("") ? "----" : selSplit[2]);
                    binding.nh106.setText(selSplit[3]);
                    binding.nh107.setText(enumBlockContract.getEbcode());

                    binding.fldGrpnh101.setVisibility(View.VISIBLE);
                    MainApp.cluster_no = binding.nh102.getText().toString();

                }
            }
        }

// Initializing Re-Back functionality
        reBackFlag = true;
        reBackChildFlag = true;

    }

    public void clearFields() {
        binding.fldGrpnh110.setVisibility(View.GONE);

        binding.hhName.setText(null);
        binding.newHHheadname.setText(null);
        binding.checkHHHeadpresent.setChecked(false);
        // binding.nh110.setText(null);
        binding.nh113.setText(null);
        binding.nh115.setText(null);
        binding.nh213.setText(null);
        binding.na11801.clearCheck();
        //binding.na11802.clearCheck();
    }

    public void BtnContinue() {

//        Validation Boolean
        MainApp.validateFlag = true;

        if (formValidation()) {
//        if (true) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

                new Thread(new Runnable() {
                    public void run() {
                        while (progressStatus < 100) {
                            progressStatus = doSomeWork();
                            handler.post(new Runnable() {
                                public void run() {
                                    binding.progress.setProgress(progressStatus);
                                }
                            });
                        }
                        handler.post(new Runnable() {
                            public void run() {

                                progress = 0;
                                finish();

                                startActivity(new Intent(SectionA1Activity.this, SectionA2ListActivity.class));
                            }
                        });
                    }

                    private int doSomeWork() {
                        try {
                            // ---simulate doing some work---
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return ++progress;
                    }
                }).start();


            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {

//        Validation Boolean
        MainApp.validateFlag = true;

        flag = true;
        //Toast.makeText(this, "Processing End Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    SectionA1Activity.this);
            alertDialogBuilder
                    .setMessage("Do you want to Exit??")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    try {
                                        SaveDraft();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (UpdateDB()) {

                                        finish();

                                        if (editFormFlag) {
                                            startActivity(new Intent(SectionA1Activity.this, ViewMemberActivity.class)
                                                    .putExtra("flagEdit", false)
                                                    .putExtra("comingBack", true)
                                                    .putExtra("cluster", MainApp.fc.getClusterNo())
                                                    .putExtra("hhno", MainApp.fc.getHhNo())
                                            );
                                        } else {
                                            startActivity(new Intent(SectionA1Activity.this, EndingActivity.class).putExtra("complete", false));
                                        }

                                    } else {
                                        Toast.makeText(SectionA1Activity.this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
                                    }
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

    public boolean formValidation() {

        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

//        nh101
        if (!validatorClass.EmptyTextBox(this, binding.nh101, getString(R.string.nh101))) {
            return false;
        }

//        nh102
        if (!validatorClass.EmptyTextBox(this, binding.nh102, getString(R.string.nh102))) {
            return false;
        }

//        nh108

        if (!binding.nh102.getText().toString().isEmpty()) {

            if (binding.nh108.getText().toString().length() == 8) {
                String[] str = binding.nh108.getText().toString().split("-");
                if (str.length > 2 || binding.nh108.getText().toString().charAt(4) != '-' || !str[0].matches("[0-9]+")
                        || !str[1].matches("[0-9]+")) {
                    binding.nh108.setError("Wrong presentation!!");
                    return false;
                }
            } else {
                //Toast.makeText(this, "Invalid length: " + getString(R.string.nh108), Toast.LENGTH_SHORT).show();
                binding.nh108.setError("Invalid length");
                return false;
            }
        }

//        New HHHead

        if (!binding.checkHHHeadpresent.isChecked()) {
            if (!validatorClass.EmptyTextBox(this, binding.newHHheadname, "New head name.")) {
                return false;
            }
        } else {
            binding.newHHheadname.setError(null);
        }

//        nh113
        if (!flag) {
            if (!validatorClass.EmptyTextBox(this, binding.nh113, getString(R.string.nh113))) {
                return false;
            }
//        nh115
            if (!validatorClass.EmptyTextBox(this, binding.nh115, getString(R.string.nh115))) {
                return false;
            }

            if (!validatorClass.RangeTextBox(this, binding.nh115, 18, 99, getString(R.string.nh115), "age")) {
                return false;
            }

//        nh213
            if (!validatorClass.EmptyTextBox(this, binding.nh213, getString(R.string.nh213))) {
                return false;
            }

//        na11801
            return validatorClass.EmptyRadioButton(this, binding.na11801, binding.na11801b, getString(R.string.na11801));
//        na11802

           /* if (MainApp.selectedHead.getSelStructure().equals("1")) {
                return validatorClass.EmptyRadioButton(this, binding.na11802, binding.na11802b, getString(R.string.na11802));
            }
*/

        }

        return true;
    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sA1 = new JSONObject();

        if (!editFormFlag) {
            MainApp.fc = new FormsContract();
            MainApp.fc.setDevicetagID(MainApp.getTagName(this));
            MainApp.fc.setFormDate(dtToday);
            MainApp.fc.setUser(MainApp.userName);
            MainApp.fc.setDeviceID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID));
            MainApp.fc.setAppversion(MainApp.versionName + "." + MainApp.versionCode);
            MainApp.fc.setRespLineNo(MainApp.lineNo);
            MainApp.fc.setClusterNo(binding.nh102.getText().toString());
            MainApp.fc.setHhNo(binding.nh108.getText().toString().toUpperCase());

            setGPS(); // Set GPS
        } else {
            sA1.put("edit_updatedate_sa1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        if (MainApp.usersContract != null) {
            sA1.put("userid", MainApp.usersContract.getUserID());
            sA1.put("fullname", MainApp.usersContract.getFULL_NAME());
            sA1.put("teamNo", MainApp.usersContract.getTEAM_NO());
        }
        String imei = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                imei = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            }
        } else {
            imei = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        }
        sA1.put("imei", imei);
        sA1.put("rndid", MainApp.selectedHead.get_ID());
        sA1.put("luid", MainApp.selectedHead.getLUID());
        sA1.put("randDT", MainApp.selectedHead.getRandomDT());
        sA1.put("hh03", MainApp.selectedHead.getStructure());
        sA1.put("hh07", MainApp.selectedHead.getExtension());
        sA1.put("hhhead", MainApp.selectedHead.getHhhead());
        sA1.put("hh09", MainApp.selectedHead.getContact());
        sA1.put("hhss", MainApp.selectedHead.getSelStructure());
        sA1.put("hhheadpresent", binding.checkHHHeadpresent.isChecked() ? "1" : "2");
        sA1.put("hhheadpresentnew", binding.newHHheadname.getText().toString());
        sA1.put("enumNo", binding.nh107.getText().toString());

        sA1.put("nh101", binding.nh101.getText().toString());

        sA1.put("nh103", binding.nh103.getText().toString());
        sA1.put("nh104", binding.nh104.getText().toString());
        sA1.put("nh105", binding.nh105.getText().toString());
        sA1.put("nh106", binding.nh106.getText().toString());


        sA1.put("nh113", binding.nh113.getText().toString());
        sA1.put("nh115", binding.nh115.getText().toString());

        sA1.put("nh213", binding.nh213.getText().toString());

        /*sA1.put("nh11701blood", MainApp.selectedHead.getSelStructure());
        sA1.put("nh11702urine", MainApp.selectedHead.getSelStructure());
        sA1.put("nh11703water", MainApp.selectedHead.getSelStructure());
*/
        sA1.put("nh11801", binding.na11801a.isChecked() ? "1"
                : binding.na11801b.isChecked() ? "2" : "0");

        /*sA1.put("nh11802", binding.na11802a.isChecked() ? "1"
                : binding.na11802b.isChecked() ? "2" : "0");
*/
        MainApp.fc.setsA1(String.valueOf(sA1));

    }

    public void setGPS() {
        SharedPreferences GPSPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);
        try {
            String lat = GPSPref.getString("Latitude", "0");
            String lang = GPSPref.getString("Longitude", "0");
            String acc = GPSPref.getString("Accuracy", "0");
            String elevation = GPSPref.getString("Elevation", "0");

            if (lat == "0" && lang == "0") {
                Toast.makeText(this, "Could not obtained GPS points", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();
            }

            String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

            MainApp.fc.setGpsLat(lat);
            MainApp.fc.setGpsLng(lang);
            MainApp.fc.setGpsAcc(acc);
            MainApp.fc.setGpsDT(date); // Timestamp is converted to date above
            MainApp.fc.setGpsElev(elevation);

            Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "setGPS: " + e.getMessage());
        }

    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);

        if (!editFormFlag) {
            long updcount = db.addForm(MainApp.fc, 0);

            MainApp.fc.set_ID(String.valueOf(updcount));

            if (updcount != 0) {
                //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

                MainApp.fc.setUID(
                        (MainApp.fc.getDeviceID() + MainApp.fc.get_ID()));
                db.updateFormID();
                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            long updcount = db.addForm(MainApp.fc, 1);
            if (updcount != 0) {
                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public void BtnCheckHH() {

        if (!binding.nh102.getText().toString().trim().isEmpty() && !binding.nh108.getText().toString().trim().isEmpty()) {

            if (editFormFlag) {
                setupViews();
            } else {
                FormsContract partialMem = db.getPartialForms(binding.nh102.getText().toString(), binding.nh108.getText().toString(), "1");

                if (partialMem != null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            SectionA1Activity.this);
                    alertDialogBuilder
                            .setMessage("یہ House Hold پہلے سے 'مکمل' موجود ہے۔")
//                            .setCancelable(false)
                            .setPositiveButton("۔Edit فارم شروح کرنا ہے",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();

                                            finish();
                                            startActivity(new Intent(SectionA1Activity.this, ViewMemberActivity.class)
                                                    .putExtra("comingBack", true)
                                                    .putExtra("flagEdit", false)
                                                    .putExtra("cluster", binding.nh102.getText().toString())
                                                    .putExtra("hhno", binding.nh108.getText().toString()));
                                        }
                                    })
                            .setNegativeButton("۔دوبارہ فارم شروح کرنا ہے",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            setupViews();
                                        }
                                    });
                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();
                } else {
                    setupViews();
                }
            }

        } else {
            Toast.makeText(this, "Not found.", Toast.LENGTH_SHORT).show();
        }

    }

    public void setupViews() {
        selected = db.getAllBLRandom(binding.nh102.getText().toString(), binding.nh108.getText().toString().toUpperCase());

        if (selected.size() != 0) {

            Toast.makeText(this, "Household found!", Toast.LENGTH_SHORT).show();

            for (BLRandomContract rnd : selected) {
                MainApp.selectedHead = new BLRandomContract(rnd);
            }

            binding.hhName.setText(MainApp.selectedHead.getHhhead().toUpperCase());

            binding.fldGrpnh110.setVisibility(View.VISIBLE);

                /*if (MainApp.selectedHead.getSelStructure().equals("1")) {
                    binding.na11802a.setEnabled(true);
                    binding.na11802b.setEnabled(true);
                } else {
                    binding.na11802a.setEnabled(false);
                    binding.na11802b.setEnabled(false);
                    binding.na11802.clearCheck();

                }
*/

        } else {

            clearFields();

            Toast.makeText(this, "No Household found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void BtnCheckEnm() {

        if (editFormFlag || validatorClass.EmptyTextBox(this, binding.nh101, getString(R.string.nh101)) && validatorClass.EmptyTextBox(this, binding.nh102, getString(R.string.nh102))) {

            Boolean loginFlag = false;
            int clus = Integer.valueOf(binding.nh102.getText().toString());
            if (clus < 6000) {
                loginFlag = !(MainApp.userName.equals("test1234") || MainApp.userName.equals("dmu@aku") || MainApp.userName.substring(0, 4).equals("user"));
            } else {
                loginFlag = MainApp.userName.equals("test1234") || MainApp.userName.equals("dmu@aku") || MainApp.userName.substring(0, 4).equals("user");
            }

            if (loginFlag) {
                EnumBlockContract enumBlockContract = db.getEnumBlock(binding.nh102.getText().toString());
                if (enumBlockContract != null) {
                    String selected = enumBlockContract.getGeoarea();
                    if (!selected.equals("")) {

                        String[] selSplit = selected.split("\\|");

                        binding.nh103.setText(selSplit[0]);
                        binding.nh104.setText(selSplit[1].equals("") ? "----" : selSplit[1]);
                        binding.nh105.setText(selSplit[2].equals("") ? "----" : selSplit[2]);
                        binding.nh106.setText(selSplit[3]);
                        binding.nh107.setText(enumBlockContract.getEbcode());

                        binding.fldGrpnh101.setVisibility(View.VISIBLE);
                        MainApp.cluster_no = binding.nh102.getText().toString();

                    }
                } else {
                    binding.nh108.setText(null);
                    Toast.makeText(this, "Sorry cluster not found!!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Can't proceed test cluster for current user!!", Toast.LENGTH_SHORT).show();
            }
        }
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
        //formValidation();
    }
}
