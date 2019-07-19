package edu.aku.hassannaqvi.casi_2019.ui.household;

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

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONA1ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.BLRandomContract;
import edu.aku.hassannaqvi.casi_2019.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionA1Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.other.MembersCount;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionA1Activity extends Menu2Activity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = SectionA1Activity.class.getName();
    public static Boolean editFormFlag;
    static int progress = 0;
    public static Boolean reBackChildFlag = true;
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

            binding.cih102.setText(getIntent().getStringExtra("clusterNo"));
            binding.cih102.setEnabled(false);
            binding.checkClusterBtn.setEnabled(false);
            binding.checkClusterBtn.setBackgroundColor(getResources().getColor(R.color.red));
            BtnCheckEnm();
            binding.cih108.setText(getIntent().getStringExtra("hhNo"));
            binding.cih108.setEnabled(false);
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

        MainApp.fc = db.getPressedForms(binding.cih102.getText().toString()
                , binding.cih108.getText().toString());

        if (MainApp.fc != null) {

            JSONA1ModelClass jsonA1 = JSONUtilClass.getModelFromJSON(MainApp.fc.getsA1(), JSONA1ModelClass.class);

            if (jsonA1.getHhheadpresent().equals("1")) {
                binding.checkHHHeadpresent.setChecked(true);
            } else {
                binding.newHHheadname.setText(jsonA1.getHhheadpresentnew());
            }

            binding.cih101.setText(jsonA1.getcih101());
            binding.cih103.setText(jsonA1.getcih103());
            binding.cih104.setText(jsonA1.getcih104());
            binding.cih105.setText(jsonA1.getcih105());
            binding.cih106.setText(jsonA1.getcih106());

            binding.cih111.setText(jsonA1.getcih111());
            binding.cih113.setText(jsonA1.getcih113());
            binding.cih115.setText(jsonA1.getcih115());
            binding.cih213.setText(jsonA1.getcih213());

            if (!jsonA1.getcih11801().equals("0")) {
                binding.na11801.check(
                        jsonA1.getcih11801().equals("1") ? binding.na11801a.getId() :
                                binding.na11801b.getId()
                );

                binding.na11801b.setEnabled(false);
            }

           /* if (!jsonA1.getcih11802().equals("0")) {
                binding.na11802.check(
                        jsonA1.getcih11802().equals("1") ? binding.na11802a.getId() :
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

        binding.cih102.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.cih108.setText(null);
                binding.fldGrpcih101.setVisibility(View.GONE);
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
        binding.cih108.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                binding.cih108.setInputType(InputType.TYPE_CLASS_NUMBER);
                length = charSequence.toString().length();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                clearFields();


                if (!binding.cih108.getText().toString().isEmpty() && binding.cih108.getText().toString().length() == 4) {
                    if (binding.cih108.getText().toString().substring(0, 3).matches("[0-9]+")) {
                        if (length < 5) {
                            binding.cih108.setText(binding.cih108.getText().toString() + "-");
                            binding.cih108.setSelection(binding.cih108.getText().length());
                            //binding.cih108.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                        }

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        if (MainApp.cluster_no.equals("")) {
            binding.cih102.setText(null);
        } else {
            binding.cih102.setText(MainApp.cluster_no);

            EnumBlockContract enumBlockContract = db.getEnumBlock(binding.cih102.getText().toString());
            if (enumBlockContract != null) {
                String selected = enumBlockContract.getGeoarea();
                if (!selected.equals("")) {

                    String[] selSplit = selected.split("\\|");

                    binding.cih103.setText(selSplit[0]);
                    binding.cih104.setText(selSplit[1].equals("") ? "----" : selSplit[1]);
                    binding.cih105.setText(selSplit[2].equals("") ? "----" : selSplit[2]);
                    binding.cih106.setText(selSplit[3]);

                    binding.fldGrpcih101.setVisibility(View.VISIBLE);
                    MainApp.cluster_no = binding.cih102.getText().toString();

                }
            }
        }

// Initializing Re-Back functionality
        reBackFlag = true;
        reBackChildFlag = true;

    }

    public void clearFields() {
        binding.fldGrpcih110.setVisibility(View.GONE);

        binding.hhName.setText(null);
        binding.newHHheadname.setText(null);
        binding.checkHHHeadpresent.setChecked(false);
        // binding.cih110.setText(null);
        binding.cih113.setText(null);
        binding.cih115.setText(null);
        binding.cih213.setText(null);
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
        //
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


//        cih101
        if (!ValidatorClass.EmptyTextBox(this, binding.cih101, getString(R.string.cih101))) {
            return false;
        }

//        cih102
        if (!ValidatorClass.EmptyTextBox(this, binding.cih102, getString(R.string.cih102))) {
            return false;
        }

//        cih108

        if (!binding.cih102.getText().toString().isEmpty()) {

            if (binding.cih108.getText().toString().length() == 8) {
                String[] str = binding.cih108.getText().toString().split("-");
                if (str.length > 2 || binding.cih108.getText().toString().charAt(4) != '-' || !str[0].matches("[0-9]+")
                        || !str[1].matches("[0-9]+")) {
                    binding.cih108.setError("Wrong presentation!!");
                    return false;
                }
            } else {
                //Toast.makeText(this, "Invalid length: " + getString(R.string.cih108), Toast.LENGTH_SHORT).show();
                binding.cih108.setError("Invalid length");
                return false;
            }
        }

//        New HHHead

        if (!binding.checkHHHeadpresent.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, binding.newHHheadname, "New head name.")) {
                return false;
            }
        } else {
            binding.newHHheadname.setError(null);
        }

//        cih113
        if (!flag) {
            if (!ValidatorClass.EmptyTextBox(this, binding.cih113, getString(R.string.cih113))) {
                return false;
            }
            if (!ValidatorClass.EmptyTextBox(this, binding.cih111, getString(R.string.cih111))) {
                return false;
            }
//        cih115
            if (!ValidatorClass.EmptyTextBox(this, binding.cih115, getString(R.string.cih115))) {
                return false;
            }

            if (!ValidatorClass.RangeTextBox(this, binding.cih115, 18, 99, getString(R.string.cih115), "age")) {
                return false;
            }

//        cih213
            if (!ValidatorClass.EmptyTextBox(this, binding.cih213, getString(R.string.cih213))) {
                return false;
            }

//        na11801
            return ValidatorClass.EmptyRadioButton(this, binding.na11801, binding.na11801b, getString(R.string.na11801));
//        na11802

           /* if (MainApp.selectedHead.getSelStructure().equals("1")) {
                return ValidatorClass.EmptyRadioButton(this, binding.na11802, binding.na11802b, getString(R.string.na11802));
            }
*/

        }

        return true;
    }

    private void SaveDraft() throws JSONException {


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
            MainApp.fc.setClusterNo(binding.cih102.getText().toString());
            MainApp.fc.setHhNo(binding.cih108.getText().toString().toUpperCase());

            setGPS(); // Set GPS
        } else {
            sA1.put("edit_updatedate_sa1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        if (MainApp.usersContract != null) {
            sA1.put("userid", MainApp.usersContract.getUserID());
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

        sA1.put("cih101", binding.cih101.getText().toString());

        sA1.put("cih103", binding.cih103.getText().toString());
        sA1.put("cih104", binding.cih104.getText().toString());
        sA1.put("cih105", binding.cih105.getText().toString());
        sA1.put("cih106", binding.cih106.getText().toString());


        sA1.put("cih113", binding.cih113.getText().toString());
        sA1.put("cih111", binding.cih111.getText().toString());
        sA1.put("cih115", binding.cih115.getText().toString());

        sA1.put("cih213", binding.cih213.getText().toString());

        /*sA1.put("cih11701blood", MainApp.selectedHead.getSelStructure());
        sA1.put("cih11702urine", MainApp.selectedHead.getSelStructure());
        sA1.put("cih11703water", MainApp.selectedHead.getSelStructure());
*/
        sA1.put("cih11801", binding.na11801a.isChecked() ? "1"
                : binding.na11801b.isChecked() ? "2" : "0");

        /*sA1.put("cih11802", binding.na11802a.isChecked() ? "1"
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

        if (!binding.cih102.getText().toString().trim().isEmpty() && !binding.cih108.getText().toString().trim().isEmpty()) {

            if (editFormFlag) {
                setupViews();
            } else {
                FormsContract partialMem = db.getPartialForms(binding.cih102.getText().toString(), binding.cih108.getText().toString(), "1");

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

//                                            finish();
//                                            startActivity(new Intent(SectionA1Activity.this, ViewMemberActivity.class)
//                                                    .putExtra("comingBack", true)
//                                                    .putExtra("flagEdit", false)
//                                                    .putExtra("cluster", binding.cih102.getText().toString())
//                                                    .putExtra("hhno", binding.cih108.getText().toString()));
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
        selected = db.getAllBLRandom(binding.cih102.getText().toString(), binding.cih108.getText().toString().toUpperCase());

        if (selected.size() != 0) {

            Toast.makeText(this, "Household found!", Toast.LENGTH_SHORT).show();

            for (BLRandomContract rnd : selected) {
                MainApp.selectedHead = new BLRandomContract(rnd);
            }

            binding.hhName.setText(MainApp.selectedHead.getHhhead().toUpperCase());

            binding.fldGrpcih110.setVisibility(View.VISIBLE);

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

        if (editFormFlag || ValidatorClass.EmptyTextBox(this, binding.cih101, getString(R.string.cih101)) && ValidatorClass.EmptyTextBox(this, binding.cih102, getString(R.string.cih102))) {

            Boolean loginFlag = false;
            int clus = Integer.valueOf(binding.cih102.getText().toString());
            if (clus < 6000) {
                loginFlag = !(MainApp.userName.equals("test1234") || MainApp.userName.equals("dmu@aku") || MainApp.userName.substring(0, 4).equals("user"));
            } else {
                loginFlag = MainApp.userName.equals("test1234") || MainApp.userName.equals("dmu@aku") || MainApp.userName.substring(0, 4).equals("user");
            }

            if (loginFlag) {
                EnumBlockContract enumBlockContract = db.getEnumBlock(binding.cih102.getText().toString());
                if (enumBlockContract != null) {
                    String selected = enumBlockContract.getGeoarea();
                    if (!selected.equals("")) {

                        String[] selSplit = selected.split("\\|");

                        binding.cih103.setText(selSplit[0]);
                        binding.cih104.setText(selSplit[1].equals("") ? "----" : selSplit[1]);
                        binding.cih105.setText(selSplit[2].equals("") ? "----" : selSplit[2]);
                        binding.cih106.setText(selSplit[3]);

                        binding.fldGrpcih101.setVisibility(View.VISIBLE);
                        MainApp.cluster_no = binding.cih102.getText().toString();

                    }
                } else {
                    binding.cih108.setText(null);
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
