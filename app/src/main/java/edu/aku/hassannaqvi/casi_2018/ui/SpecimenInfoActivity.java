package edu.aku.hassannaqvi.casi_2018.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.contracts.SpecimenContract;
import edu.aku.hassannaqvi.casi_2018.contracts.WaterSpecimenContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySpecimenInfoBinding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.validation.clearClass;
import edu.aku.hassannaqvi.casi_2018.validation.validatorClass;


public class SpecimenInfoActivity extends AppCompatActivity {

    private static final String TAG = SpecimenInfoActivity.class.getName();
    static String enm_no;
    static String hh_no;
    static String hc_code;
    static String uuid;
    static String wt_code;
    static int consent = 0;
    static int selected = 0;
    static String datetime = "";
    JSONModelClass json;
    ActivitySpecimenInfoBinding binding;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    DatabaseHelper db;
    Collection<FamilyMembersContract> members;
    ArrayList<FamilyMembersContract> hh;
    Map<String, String> hhMap;
    ArrayList<String> hhList;
    Boolean isHC = false, isHT = false, isWT = false;
    int length = 0;
    FamilyMembersContract slecMem;
    String date = "";
    String dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_specimen_info);
        db = new DatabaseHelper(this);
        binding.setCallback(this);

        SetupViewFunctionality();

        dateTime = new SimpleDateFormat("dd-MM-yyy HH:mm").format(System.currentTimeMillis());

        slecMem = new FamilyMembersContract();
        MainApp.duplicateMembers = new ArrayList<>();

        /*SectionE1Activity.members.clear();
        SectionE1Activity.membersMap.clear();*/


    }

    public void SetupViewFunctionality() {

        MainApp.all_members = new ArrayList<>();
        MainApp.childUnder2 = new ArrayList<>();
        MainApp.childUnder5 = new ArrayList<>();
        MainApp.childNA = new ArrayList<>();
        MainApp.mwra = new ArrayList<>();
        MainApp.adolescents = new ArrayList<>();
        MainApp.minors = new ArrayList<>();
        MainApp.childUnder2Check = new ArrayList<>();
        members = new ArrayList<>();
        hhMap = new HashMap<>();
        hhList = new ArrayList<>();
        hh = new ArrayList<>();
        json = new JSONModelClass();

        if (MainActivity.ftype.equals("B")) {
            binding.fldGrpnhconsent.setVisibility(View.VISIBLE);
            binding.fldGrpQR.setVisibility(View.VISIBLE);
            binding.fldGrpHC.setVisibility(View.VISIBLE);
            binding.txtSelected.setText(getResources().getString(R.string.selected1));


        } else if (MainActivity.ftype.equals("W")) {
            binding.fldGrpnhconsent.setVisibility(View.GONE);
            binding.fldGrpQR.setVisibility(View.GONE);
            binding.fldGrpHC.setVisibility(View.GONE);
            binding.hcCode.setText(null);
            binding.txtSelected.setText(getResources().getString(R.string.selected2));

        }

        binding.na11802.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (binding.na11802a.isChecked()) {
                    binding.fldGrpQR.setVisibility(View.VISIBLE);
                    binding.fldGrpHC.setVisibility(View.VISIBLE);
                } else {
                    binding.fldGrpQR.setVisibility(View.GONE);
                    binding.fldGrpHC.setVisibility(View.GONE);
                    binding.hcCode.setText(null);
                }
            }
        });

        binding.neselected.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (MainActivity.ftype.equals("B")) {
                    if (binding.neselecteda.isChecked()) {

                        clearClass.ClearAllFields(binding.fldGrpnhconsent, true);
                        clearClass.ClearAllFields(binding.fldGrpHC, true);
                        binding.btnScanHC.setEnabled(true);
                    } else {
                        clearClass.ClearAllFields(binding.fldGrpnhconsent, false);
                        clearClass.ClearAllFields(binding.fldGrpHC, false);
                        binding.btnScanHC.setEnabled(false);
                    }
                } else if (MainActivity.ftype.equals("W")) {
                    if (binding.neselecteda.isChecked()) {

                        clearClass.ClearAllFields(binding.fldGrpnhconsent, true);

                    } else {
                        clearClass.ClearAllFields(binding.fldGrpnhconsent, false);

                    }
                }
            }
        });


        //slcMem = new ArrayList<>();
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


//        HH listener
        binding.nh108.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                binding.nh108.setInputType(InputType.TYPE_CLASS_NUMBER);

                length = charSequence.toString().length();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                binding.fldGrpListHH.setVisibility(View.GONE);

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


//        HH Spinner
/*        binding.listHH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String hhUID = hhMap.get(binding.listHH.getSelectedItem().toString());
                    populateMembers(hhUID, binding.listHH.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }

    public void BtnContinue() {

        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (UpdateDB()) {
                //Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();
                finish();

                if (MainActivity.ftype.equals("B")) {

                    if (binding.neselecteda.isChecked() && binding.na11802a.isChecked()) {
                        startActivity(new Intent(this, SectionE1Activity.class));
                    } else {
                        startActivity(new Intent(this, MainActivity.class));
                    }
                } else if (MainActivity.ftype.equals("W")) {
                    if (binding.neselecteda.isChecked()) {
                        startActivity(new Intent(this, SectionE2Activity.class));
                    } else {
                        startActivity(new Intent(this, MainActivity.class));
                    }
                }


            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {

        Toast.makeText(this, "Processing End Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {

            try {

                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (UpdateDB()) {
                Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();

                finish();

                startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean formValidation() {

        Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

//        nh102
        if (!validatorClass.EmptyTextBox(this, binding.nh102, getString(R.string.nh102))) {
            return false;
        }

//        nh108

        if (binding.nh108.getText().toString().length() == 8) {
            String[] str = binding.nh108.getText().toString().split("-");
            if (str.length > 2 || binding.nh108.getText().toString().charAt(4) != '-' || !str[0].matches("[0-9]+") || !str[1].matches("[0-9]+")) {
                binding.nh108.setError("Wrong presentation!!");
                return false;
            }
        } else {
            Toast.makeText(this, "Invalid length: " + getString(R.string.nh108), Toast.LENGTH_SHORT).show();
            binding.nh108.setError("Invalid length");
            return false;
        }


        int scanChar;

        if (MainActivity.ftype.equals("B")) {

            if (!validatorClass.EmptyRadioButton(this, binding.neselected, binding.neselecteda, getString(R.string.selected1))) {
                return false;
            }

            if (binding.neselecteda.isChecked()) {

                if (!validatorClass.EmptyRadioButton(this, binding.na11802, binding.na11802b, getString(R.string.na11802))) {
                    return false;
                }

                if (binding.na11802a.isChecked()) {

                    if (!validatorClass.EmptyTextBox(this, binding.hcCode, getString(R.string.hc))) {
                        return false;
                    }

                    if (binding.hcCode.getText().toString().contains("§")) {
                        scanChar = 7;
                    } else {
                        scanChar = 6;
                    }

                    if (binding.hcCode.getText().length() != scanChar || !binding.hcCode.getText().toString().contains("-")
                            || !binding.hcCode.getText().toString().contains("HC")) {
                        Toast.makeText(this, "ERROR(invalid)" + getString(R.string.hc), Toast.LENGTH_SHORT).show();
                        binding.hcCode.setError("Invalid Number..");

                        Log.i(TAG, "hcCode: Invalid number");
                        return false;
                    } else {
                        binding.hcCode.setError(null);
                    }
                }
            }
        } else {
            return validatorClass.EmptyRadioButton(this, binding.neselected, binding.neselecteda, getString(R.string.selected2));
        }


        return true;
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();


        if (MainActivity.ftype.equals("B")) {
            if (binding.neselecteda.isChecked()) {

                enm_no = binding.nh102.getText().toString();
                hh_no = binding.nh108.getText().toString().toUpperCase();
                hc_code = binding.hcCode.getText().toString();
                consent = binding.na11802.indexOfChild(findViewById(binding.na11802.getCheckedRadioButtonId())) + 1;
                selected = binding.neselected.indexOfChild(findViewById(binding.neselected.getCheckedRadioButtonId())) + 1;
                datetime = dateTime;

            } else {
                MainApp.smc = new SpecimenContract();

                MainApp.smc.setDevicetagID(MainApp.getTagName(this));
                MainApp.smc.setFormDate(date);
                MainApp.smc.setUser(MainApp.userName);
                MainApp.smc.setDeviceID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID));
                MainApp.smc.setAppversion(MainApp.versionName + "." + MainApp.versionCode);
                MainApp.smc.setClusterno(binding.nh102.getText().toString());
                MainApp.smc.setHhno(binding.nh108.getText().toString().toUpperCase());

                JSONObject sE1 = new JSONObject();
                sE1.put("ne_selected_blood", binding.neselecteda.isChecked() ? "1" : binding.neselectedb.isChecked() ? "2" : "0");
                sE1.put("ne_consent", binding.na11802a.isChecked() ? "1" : binding.na11802b.isChecked() ? "2" : "0");
                sE1.put("start_time", dateTime);
                sE1.put("end_time", new SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis()));

                MainApp.smc.setsE1(String.valueOf(sE1));

            }
        } else if (MainActivity.ftype.equals("W")) {

            if (binding.neselecteda.isChecked()) {

                enm_no = binding.nh102.getText().toString();
                hh_no = binding.nh108.getText().toString().toUpperCase();
                consent = binding.na11802.indexOfChild(findViewById(binding.na11802.getCheckedRadioButtonId())) + 1;
                selected = binding.neselected.indexOfChild(findViewById(binding.neselected.getCheckedRadioButtonId())) + 1;
                datetime = dateTime;

            } else {
                MainApp.wsc = new WaterSpecimenContract();

                MainApp.wsc.setDevicetagID(MainApp.getTagName(this));
                MainApp.wsc.setFormDate(date);
                MainApp.wsc.setUser(MainApp.userName);
                MainApp.wsc.setDeviceID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID));
                MainApp.wsc.setAppversion(MainApp.versionName + "." + MainApp.versionCode);
                MainApp.wsc.setClusterno(binding.nh102.getText().toString());
                MainApp.wsc.setHhno(binding.nh108.getText().toString().toUpperCase());


                JSONObject sE1 = new JSONObject();
                sE1.put("ne_selected_water", binding.neselecteda.isChecked() ? "1" : binding.neselectedb.isChecked() ? "2" : "0");
                sE1.put("start_time", dateTime);
                sE1.put("end_time", new SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis()));


                MainApp.wsc.setsE2(String.valueOf(sE1));

            }

        }

    }

    private boolean UpdateDB() {

        if (MainActivity.ftype.equals("B")) {
            if (binding.neselectedb.isChecked()) {
                DatabaseHelper db = new DatabaseHelper(this);


                Long updcount = db.addSpecimenMembers(MainApp.smc);
                MainApp.smc.set_ID(String.valueOf(updcount));

                if (updcount != 0) {
                    //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

                    MainApp.smc.setUID(
                            (MainApp.smc.getDeviceID() + MainApp.smc.get_ID()));
                    db.updateSpecimenMemberID();

                    return true;
                } else {
                    Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                return true;
            }
        } else if (MainActivity.ftype.equals("W")) {
            if (binding.neselectedb.isChecked()) {
                DatabaseHelper db = new DatabaseHelper(this);


                Long updcount = db.addWaterSpecimenForm(MainApp.wsc);
                MainApp.wsc.set_ID(String.valueOf(updcount));

                if (updcount != 0) {
                    //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

                    MainApp.wsc.setUID(
                            (MainApp.wsc.getDeviceID() + MainApp.wsc.get_ID()));
                    db.updateWaterSpecimenMemberID();

                    return true;
                } else {
                    Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    public void BtnCheckHH() {

        if (!binding.nh102.getText().toString().trim().isEmpty() && !binding.nh108.getText().toString().trim().isEmpty()) {

//            populateHH();
            hh = db.getAllHHforAnthro(binding.nh102.getText().toString(), binding.nh108.getText().toString().toUpperCase());
            if (hh.size() > 0) {
                populateMembers(hh.get(hh.size() - 1).get_UUID(), hh.get(hh.size() - 1).getFormDate());
            }

        } else {
            Toast.makeText(this, "Not found.", Toast.LENGTH_SHORT).show();
        }

    }

/*    public void populateHH() {
        hh = db.getAllHHforAnthro(binding.nh102.getText().toString(), binding.nh108.getText().toString().toUpperCase());
        hhList.add("....");

        if (hh.size() > 0) {
            for (FamilyMembersContract fm : hh) {
                hhMap.put(fm.getFormDate(), fm.get_UUID());
                hhList.add(fm.getFormDate());
            }

            binding.fldGrpListHH.setVisibility(View.VISIBLE);

            binding.listHH.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, hhList));
        }
    }*/

    public void populateMembers(String uuid, String formDate) {
        members = db.getAllMembersByHHforAnthro(binding.nh102.getText().toString(), binding.nh108.getText().toString().toUpperCase(), uuid, formDate, false);

        if (members.size() != 0) {
            for (FamilyMembersContract fm : members) {

                if (fm.getsA2() != null) {
                    json = JSONUtilClass.getModelFromJSON(fm.getsA2(), JSONModelClass.class);
                    date = fm.getFormDate();
                    if ((Integer.valueOf(json.getAge()) > 14 && Integer.valueOf(json.getAge()) < 50)
                            && json.getGender().equals("2") && json.getNh210().equals("1")) {
                        fm.setType("1");
                        MainApp.mwra.add(fm);
                        MainApp.all_members.add(fm);
                        addIfNotExists(MainApp.all_members, fm);
                    }
                    if ((Integer.valueOf(json.getAge()) >= 10 && (Integer.valueOf(json.getAge()) < 20))
                            && json.getGender().equals("2") && json.getNh210().equals("1") && json.getMaritalStatus().equals("5")) {
                        fm.setType("4");
                        MainApp.adolescents.add(fm);
                        MainApp.all_members.add(fm);
                        addIfNotExists(MainApp.all_members, fm);
                    }
                    if ((Integer.valueOf(json.getAge()) >= 6 && (Integer.valueOf(json.getAge()) < 13))
                            && json.getNh210().equals("1")) {
                        fm.setType("3");
                        MainApp.minors.add(fm);
                        MainApp.all_members.add(fm);
                        addIfNotExists(MainApp.all_members, fm);
                    }
                    if (Integer.valueOf(json.getAge()) < 6 && json.getNh210().equals("1")) {
                        fm.setType("2");
                        MainApp.childUnder5.add(fm);
                        MainApp.all_members.add(fm);
                        addIfNotExists(MainApp.all_members, fm);
                    }


                }

            }

            if (MainApp.all_members.size() > 0) {

                Toast.makeText(this, "Members Found..", Toast.LENGTH_SHORT).show();
                binding.fldGrpQR.setVisibility(View.VISIBLE);
                binding.btnContinue.setVisibility(View.VISIBLE);
                binding.btnEnd.setVisibility(View.GONE);
                binding.fldGrpHH.setVisibility(View.VISIBLE);

            } else {
                binding.fldGrpQR.setVisibility(View.GONE);
                binding.hcCode.setText(null);
                binding.fldGrpHH.setVisibility(View.GONE);
                binding.btnContinue.setVisibility(View.GONE);
                binding.btnEnd.setVisibility(View.GONE);
                Toast.makeText(this, "No Eligible member found for anthropometry, Check another HH.", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "No members found for the HH.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addIfNotExists(List<FamilyMembersContract> all_members, FamilyMembersContract fm) {

    }

    public void BtnCheckEnm() {

        if (validatorClass.EmptyTextBox(this, binding.nh102, getString(R.string.nh102))) {

            EnumBlockContract enumBlockContract = db.getEnumBlock(binding.nh102.getText().toString());
            if (enumBlockContract != null) {
                String selected = enumBlockContract.getGeoarea();
                if (!selected.equals("")) {

                    String[] selSplit = selected.split("\\|");

                    binding.nh103.setText(selSplit[0]);
                    binding.nh104.setText(selSplit[1].equals("") ? "----" : selSplit[1]);
                    binding.nh105.setText(selSplit[2].equals("") ? "----" : selSplit[2]);
                    binding.nh106.setText(selSplit[3]);

                    binding.fldGrpnh101.setVisibility(View.VISIBLE);
                }
            } else {
                binding.nh108.setText(null);
                Toast.makeText(this, "Sorry not found any block", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void BtnScanHC() {
        //binding.hcCode.setText(null);
        isHC = true;
        isWT = false;
        isHT = false;

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


                if (isHC) {
                    if (result.getContents().contains("HC")) {
                        Toast.makeText(this, "HC Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        binding.hcCode.setText("§" + result.getContents().trim());
                        binding.hcCode.setEnabled(false);
                        binding.hcCode.setError(null);
                    } else {
                        binding.hcCode.setError("Please Scan QR code of Hemocue Machine");
                    }
                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
