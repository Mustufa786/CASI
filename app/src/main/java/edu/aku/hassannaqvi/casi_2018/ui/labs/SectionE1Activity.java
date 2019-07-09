package edu.aku.hassannaqvi.casi_2018.ui.labs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2018.contracts.SpecimenContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionE1Binding;
import edu.aku.hassannaqvi.casi_2018.other.DateUtils;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.mainUI.MainActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionE1Activity extends AppCompatActivity {

    private static final String TAG = SectionE1Activity.class.getSimpleName();
    static List<String> members;
    static Map<String, SelectedMem> membersMap;
    static String name;
    static String grouptype;
    static int counter = 1;
    static List<String> group;
    static List<Integer> originalPositions;
    ActivitySectionE1Binding bi;
    DatabaseHelper db;
    int slc_type;
    JSONModelClass json;
    FamilyMembersContract slecMem;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    String maxDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date().getTime());
    int position = 0;
    int indexOriginal = 0;
    int namePosition = 0;
    Boolean isBl = false;
    Boolean isUr = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_e1);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e1);

        this.setTitle(getResources().getString(R.string.ne1heading));


        bi.ne104d.setMinDate(DateUtils.getThreeDaysBack("dd/MM/yyyy", -3));
        bi.ne104d.setMaxDate(maxDate);

        bi.ne104t.setTimeFormat("HH:mm");


        bi.ne108d.setMinDate(DateUtils.getThreeDaysBack("dd/MM/yyyy", -3));
        bi.ne108d.setMaxDate(maxDate);

        bi.ne108t.setTimeFormat("HH:mm");


        bi.setCallback(this);
        setupViews();

    }

    public void setupViews() {


        //slecMem = new FamilyMembersContract();

        if (getIntent().getBooleanExtra("flag", true)) {

            group = new ArrayList<>();

            originalPositions = new ArrayList<>();

            group.add("....");
            originalPositions.add(0);


            if (MainApp.mwra.size() > 0) {
                group.add(getResources().getString(R.string.neselecteda));
                originalPositions.add(1);
            }
            if (MainApp.childUnder5.size() > 0) {
                group.add(getResources().getString(R.string.neselectedb));
                originalPositions.add(2);
            }

            if (MainApp.minors.size() > 0) {
                group.add(getResources().getString(R.string.neselectedc));
                originalPositions.add(3);
            }

            if (MainApp.adolescents.size() > 0) {
                group.add(getResources().getString(R.string.neselectedd));
                originalPositions.add(4);
            }
        }


        bi.ne103.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, group));

        bi.ne103.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (bi.ne103.getSelectedItemPosition() != 0) {

                    position = originalPositions.get(i);
                    indexOriginal = i;
                    //position = i;

                    members = new ArrayList<>();
                    membersMap = new HashMap<>();
                    members.add("....");

                    //familyMembersSetting(MainApp.all_members);
                    fetchMembersFromGroup(position);
                    bi.ne102.setAdapter(new ArrayAdapter<>(SectionE1Activity.this, R.layout.item_style, members));
                    if (position == 2) {
                        ClearClass.ClearAllFields(bi.fldGrpUnine, false);
                        ClearClass.ClearAllFields(bi.fldGrpblood, true);
                    } else if (position == 1) {
                        ClearClass.ClearAllFields(bi.fldGrpUnine, true);
                        ClearClass.ClearAllFields(bi.fldGrpblood, true);
                    } else if (position == 3) {
                        ClearClass.ClearAllFields(bi.fldGrpUnine, true);
                        ClearClass.ClearAllFields(bi.fldGrpblood, false);
                    } else if (position == 4) {
                        //ClearClass.ClearAllFields(bi.fldGrpbloodyes, false);
                        bi.ne104a.setEnabled(false);
                        bi.ne104b.setEnabled(false);
                        bi.ne104.clearCheck();
                        bi.ne105.setEnabled(false);
                        bi.ne105.setText(null);
                        bi.ne104d.setText(null);
                        bi.ne104t.setText(null);
                        bi.btnScanBL.setEnabled(false);
                        ClearClass.ClearAllFields(bi.fldGrpbloodno, false);
                        ClearClass.ClearAllFields(bi.fldGrphb, true);
                        ClearClass.ClearAllFields(bi.fldGrpUnine, false);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        bi.ne102.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (bi.ne102.getSelectedItemPosition() != 0) {
                    namePosition = position;
                    SelectedMem mem = membersMap.get(bi.ne102.getSelectedItem().toString());
                    slecMem = mem.getFmc();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bi.ne104.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (bi.ne104a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpbloodyes, true);
                    bi.ne104d.setEnabled(true);
                    bi.ne104t.setEnabled(true);

                    ClearClass.ClearAllFields(bi.fldGrpbloodno, false);

                    bi.btnScanBL.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpbloodyes, false);
                    bi.ne104d.setEnabled(false);
                    bi.ne104t.setEnabled(false);
                    bi.ne104d.setText(null);
                    bi.ne104t.setText(null);
                    bi.btnScanBL.setEnabled(false);
                    ClearClass.ClearAllFields(bi.fldGrpbloodno, true);
                }
            }
        });

        bi.ne108.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (bi.ne108a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpurineyes, true);
                    bi.ne108d.setEnabled(true);
                    bi.ne108t.setEnabled(true);

                    ClearClass.ClearAllFields(bi.fldGrpurinno, false);
                    bi.btnScanUR.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpurineyes, false);
                    bi.ne108d.setEnabled(false);
                    bi.ne108t.setEnabled(false);
                    bi.ne108d.setText(null);
                    bi.ne108t.setText(null);
                    ClearClass.ClearAllFields(bi.fldGrpurinno, true);
                    bi.btnScanUR.setEnabled(false);
                }
            }
        });


    }

    private void fetchMembersFromGroup(int position) {

        // WRA
        if (position == 1) {
            familyMembersSetting(MainApp.mwra);
        }
        // Child under 5
        else if (position == 2) {
            familyMembersSetting(MainApp.childUnder5);
        }
        // Minors
        else if (position == 3) {
            familyMembersSetting(MainApp.minors);
        }
        // Adoles
        else if (position == 4) {
            familyMembersSetting(MainApp.adolescents);
        }
    }

    public void familyMembersSetting(List<FamilyMembersContract> family) {


        for (FamilyMembersContract fmc : family) {

            //if (position == Integer.valueOf(fmc.getType()))
            {
                json = JSONUtilClass.getModelFromJSON(fmc.getsA2(), JSONModelClass.class);
                membersMap.put(json.getName() + "_" + json.getSerialNo(), new SelectedMem(position, fmc, json.getSerialNo()));
                if (!MainApp.duplicateMembers.contains(json.getName() + "_" + json.getSerialNo()))
                    members.add(json.getName() + "_" + json.getSerialNo());
            }
        }


    }

    public boolean checkmembersExists(List<FamilyMembersContract> family) {
        List<String> memberslist = new ArrayList<>();
        for (FamilyMembersContract fmc : family) {


            {
                json = JSONUtilClass.getModelFromJSON(fmc.getsA2(), JSONModelClass.class);
                if (!MainApp.duplicateMembers.contains(json.getName() + "_" + json.getSerialNo()))
                    memberslist.add(json.getName() + "_" + json.getSerialNo());
            }
        }

        return memberslist.size() > 0;
    }


    public void BtnScanBL() {
        //binding.hcCode.setText(null);

        isBl = true;
        isUr = false;
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan the QR code of Machine");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();

    }

    public void BtnScanUR() {
        //binding.hcCode.setText(null);

        isUr = true;
        isBl = false;
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan the QR code of Machine");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {


                if (position == 1) {
                    if (isBl) {
                        if (result.getContents().contains("WB")) {
                            Toast.makeText(this, "WB Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                            bi.ne105.setText("§" + result.getContents().trim());
                            bi.ne105.setEnabled(false);
                            bi.ne105.setError(null);
                        } else {
                            bi.ne105.setError("Please Scan correct QR code");
                        }
                    } else if (isUr) {
                        if (result.getContents().contains("WU")) {
                            Toast.makeText(this, "WU Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                            bi.ne109.setText("§" + result.getContents().trim());
                            bi.ne109.setEnabled(false);
                            bi.ne109.setError(null);
                        } else {
                            bi.ne109.setError("Please Scan correct QR code");
                        }
                    }
                }

                if (position == 2) {
                    if (isBl) {
                        if (result.getContents().contains("CB")) {
                            Toast.makeText(this, "CB Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                            bi.ne105.setText("§" + result.getContents().trim());
                            bi.ne105.setEnabled(false);
                            bi.ne105.setError(null);
                        } else {
                            bi.ne105.setError("Please Scan correct QR code");
                        }
                    }
                }

                if (position == 3) {
                    if (isUr) {
                        if (result.getContents().contains("CU")) {
                            Toast.makeText(this, "CU Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                            bi.ne109.setText("§" + result.getContents().trim());
                            bi.ne109.setEnabled(false);
                            bi.ne109.setError(null);
                        } else {
                            bi.ne109.setError("Please Scan correct QR code");
                        }
                    }
                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
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

                //finish();
                if (group.size() > 2) {
//                if (members.size() > 2) {

                    //String str = group.get(position);
                    //int myInt = originalPositions.get(position);

                    MainApp.duplicateMembers.add(bi.ne102.getSelectedItem().toString());

                    String selecteditem = null;
                    selecteditem = bi.ne103.getSelectedItem().toString();
                    group.remove(indexOriginal);
                    originalPositions.remove(indexOriginal);
                    boolean membersFound;
//                    wra
                    if (selecteditem.equals(getResources().getString(R.string.neselecteda))) {
                        membersFound = checkmembersExists(MainApp.adolescents);
                        if (!membersFound) {
                            for (int i = 0; i < group.size(); i++) {
                                if (group.get(i).equals(getResources().getString(R.string.neselectedd))) {
                                    group.remove(i);
                                }
                            }
                        }
                    }
//                    adolescents
                    else if (selecteditem.equals(getResources().getString(R.string.neselectedd))) {
                        membersFound = checkmembersExists(MainApp.mwra);
                        if (!membersFound) {
                            for (int i = 0; i < group.size(); i++) {
                                if (group.get(i).equals(getResources().getString(R.string.neselecteda))) {
                                    group.remove(i);
                                }
                            }
                        }
                    }

                   /* for (int i = 0; i < group.size(); i++) {
//                        mwra
                       if(group.get(i).equals(getResources().getString(R.string.neselecteda))){
                           noMembersFound = checkmembersExists(MainApp.mwra);
                           if(noMembersFound){
                               group.remove(i);
                           }
                       }
                       else if(group.get(i).equals(getResources().getString(R.string.neselectedd))){
                           noMembersFound = checkmembersExists(MainApp.adolescents);
                           if(noMembersFound){
                               group.remove(i);
                           }
                       }
                    }
                    */
                    //groupRemoved.set(position, 0);
                    //group.get(position);

                    members.clear();
                    finish();
                    if (group.size() <= 1) {
                        group.clear();
                        counter = 1;
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        counter++;
                        startActivity(new Intent(this, SectionE1Activity.class).putExtra("flag", false));
                    }
                } else {
                    group.clear();
                    members.clear();
                    counter = 1;
                    startActivity(new Intent(this, MainActivity.class));
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {


            //finish();

            MainApp.endAnthroActivity(this, this);

        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean formValidation() {


        int scanChar;

        if (!ValidatorClass.EmptySpinner(this, bi.ne103, getString(R.string.neselected))) {
            return false;
        }

        if (!ValidatorClass.EmptySpinner(this, bi.ne102, getString(R.string.ne102))) {
            return false;
        }

        if (position == 1) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ne104, bi.ne104a, getString(R.string.ne104))) {
                return false;
            }

            if (bi.ne104a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne105, getString(R.string.barcode))) {
                    return false;
                }


                if (bi.ne105.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne105.getText().length() != scanChar || !bi.ne105.getText().toString().contains("-")
                        || !bi.ne105.getText().toString().contains("WB")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.barcode), Toast.LENGTH_SHORT).show();
                    bi.ne105.setError("Invalid QR Code..");

                    Log.i(TAG, "bloodcode: Invalid or Incomplete QR Code");
                    return false;
                } else {
                    bi.ne105.setError(null);
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ne104d, getString(R.string.date))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ne104t, getString(R.string.time))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ne106, getString(R.string.hb_result))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.ne106, 0.1, 24.9, getString(R.string.hb_result), " hemoglobin")) {
                    return false;
                }

            } else {
                if (!ValidatorClass.EmptyRadioButton(this, bi.ne107, bi.ne107a, getString(R.string.ne107))) {
                    return false;
                }
            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.ne108, bi.ne108a, getString(R.string.ne104))) {
                return false;
            }

            if (bi.ne108a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne109, getString(R.string.barcode))) {
                    return false;
                }

                if (bi.ne109.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne109.getText().length() != scanChar || !bi.ne109.getText().toString().contains("-")
                        || !bi.ne109.getText().toString().contains("WU")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.barcode), Toast.LENGTH_SHORT).show();
                    bi.ne109.setError("Invalid QR Code..");

                    Log.i(TAG, "urinecode: Invalid or Incomplete QR Code");
                    return false;
                } else {
                    bi.ne109.setError(null);
                }
                if (!ValidatorClass.EmptyTextBox(this, bi.ne108d, getString(R.string.date))) {
                    return false;
                }
                if (!ValidatorClass.EmptyTextBox(this, bi.ne108t, getString(R.string.time))) {
                    return false;
                }

            } else {
                if (!ValidatorClass.EmptyRadioButton(this, bi.ne110, bi.ne110a, getString(R.string.ne107))) {
                    return false;
                }
            }

        }

        if (position == 2) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ne104, bi.ne104a, getString(R.string.ne104))) {
                return false;
            }

            if (bi.ne104a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne105, getString(R.string.barcode))) {
                    return false;
                }

                if (bi.ne105.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne105.getText().length() != scanChar || !bi.ne105.getText().toString().contains("-")
                        || !bi.ne105.getText().toString().contains("CB")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.barcode), Toast.LENGTH_SHORT).show();
                    bi.ne105.setError("Invalid QR Code..");

                    Log.i(TAG, "cbCode: Invalid or Incomplete QR code");
                    return false;
                } else {
                    bi.ne105.setError(null);
                }
                if (!ValidatorClass.EmptyTextBox(this, bi.ne104d, getString(R.string.date))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ne104t, getString(R.string.time))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ne106, getString(R.string.hb_result))) {
                    return false;
                }


            } else {
                if (!ValidatorClass.EmptyRadioButton(this, bi.ne107, bi.ne107a, getString(R.string.ne107))) {
                    return false;
                }
            }
        }

        if (position == 3) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ne108, bi.ne108a, getString(R.string.ne104))) {
                return false;
            }

            if (bi.ne108a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne109, getString(R.string.barcode))) {
                    return false;
                }

                if (bi.ne109.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne109.getText().length() != scanChar || !bi.ne109.getText().toString().contains("-")
                        || !bi.ne109.getText().toString().contains("CU")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.barcode), Toast.LENGTH_SHORT).show();
                    bi.ne109.setError("Invalid or Incomplete QR Code..");

                    Log.i(TAG, "urinecode: Invalid QR Code");
                    return false;
                } else {
                    bi.ne109.setError(null);
                }
                if (!ValidatorClass.EmptyTextBox(this, bi.ne108d, getString(R.string.date))) {
                    return false;
                }
                if (!ValidatorClass.EmptyTextBox(this, bi.ne108t, getString(R.string.time))) {
                    return false;
                }
            } else {
                if (!ValidatorClass.EmptyRadioButton(this, bi.ne110, bi.ne110a, getString(R.string.ne107))) {
                    return false;
                }
            }
        }

        if (position == 4) {
            return ValidatorClass.EmptyTextBox(this, bi.ne106, getString(R.string.hb_result));

        }


        return true;
    }

    private void SaveDraft() throws JSONException {


        MainApp.smc = new SpecimenContract();
        MainApp.smc.setDevicetagID(MainApp.getTagName(this));
        MainApp.smc.setFormDate(slecMem.getFormDate());
        MainApp.smc.setUser(MainApp.userName);
        MainApp.smc.setDeviceID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        MainApp.smc.setAppversion(MainApp.versionName + "." + MainApp.versionCode);
        MainApp.smc.setUUID(slecMem.get_UUID());
        MainApp.smc.setFMUID(slecMem.get_UID());
        MainApp.smc.setLineNo(membersMap.get(bi.ne102.getSelectedItem()).getFmc().getSerialNo());
        MainApp.smc.setClusterno(SpecimenInfoActivity.enm_no);
        MainApp.smc.setHhno(SpecimenInfoActivity.hh_no);

        JSONObject sE1 = new JSONObject();

        sE1.put("ne_selected_blood", String.valueOf(SpecimenInfoActivity.selected));
        sE1.put("ne_consent", String.valueOf(SpecimenInfoActivity.consent));
        sE1.put("start_time", SpecimenInfoActivity.datetime);

        if (MainActivity.ftype.equals("B")) {
            sE1.put("hc_code", SpecimenInfoActivity.hc_code);
        }

        sE1.put("ne102", bi.ne102.getSelectedItem().toString());
        sE1.put("ne103", originalPositions.get(bi.ne103.getSelectedItemPosition()));
        sE1.put("ne104", bi.ne104a.isChecked() ? "1" : bi.ne104b.isChecked() ? "2" : "0");
        sE1.put("ne105", bi.ne105.getText().toString());
        sE1.put("ne104d", bi.ne104d.getText().toString());
        sE1.put("ne104t", bi.ne104t.getText().toString());
        sE1.put("ne106", bi.ne106.getText().toString());
        sE1.put("ne107", bi.ne107a.isChecked() ? "1" : bi.ne107b.isChecked() ? "2" : "0");
        sE1.put("ne108", bi.ne108a.isChecked() ? "1" : bi.ne108b.isChecked() ? "2" : "0");
        sE1.put("ne109", bi.ne109.getText().toString());
        sE1.put("ne108d", bi.ne108d.getText().toString());
        sE1.put("ne108t", bi.ne108t.getText().toString());
        sE1.put("ne110", bi.ne110a.isChecked() ? "1" : bi.ne110b.isChecked() ? "2" : "0");
        sE1.put("end_time", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

        MainApp.smc.setsE1(String.valueOf(sE1));

        // Set summary fields
        FormsContract fc = new FormsContract();
        fc.setClusterNo(MainApp.smc.getClusterno());
        fc.setHhNo(MainApp.smc.getHhno());
        fc.setDevicetagID(MainApp.smc.getDevicetagID());
        fc.setFormDate(MainApp.smc.getFormDate());
        fc.setUser(MainApp.smc.getUser());
        fc.setDeviceID(MainApp.smc.getDeviceID());
        fc.setAppversion(MainApp.smc.getAppversion());
        MainApp.sumc = MainApp.AddSummary(fc, 5);



    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);


        Long updcount = db.addSpecimenMembers(MainApp.smc);
        MainApp.smc.set_ID(String.valueOf(updcount));

        if (updcount != 0) {


            MainApp.smc.setUID(
                    (MainApp.smc.getDeviceID() + MainApp.smc.get_ID()));
            db.updateSpecimenMemberID();

            return MainApp.UpdateSummary(this, db, 5);

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
        //return true;
    }

    public class SelectedMem {
        int type;
        FamilyMembersContract fmc;


        public SelectedMem(int type, FamilyMembersContract fmc, String sno) {
            this.type = type;
            this.fmc = fmc;
            this.fmc.setSerialNo(sno);
        }

        public int getType() {
            return type;
        }


        public FamilyMembersContract getFmc() {
            return fmc;
        }
    }
}

