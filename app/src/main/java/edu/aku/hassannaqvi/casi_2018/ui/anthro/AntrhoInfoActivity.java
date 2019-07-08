package edu.aku.hassannaqvi.casi_2018.ui.anthro;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivityAntrhoInfoBinding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.household.EndingActivity;
import edu.aku.hassannaqvi.casi_2018.ui.mainUI.MainActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;


public class AntrhoInfoActivity extends Activity {

    private static final String TAG = AntrhoInfoActivity.class.getName();
    static String enm_no;
    static String hh_no;
    static String hc_code;
    static String ht_code;
    static String wt_code;
    JSONModelClass json;
    ActivityAntrhoInfoBinding binding;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    DatabaseHelper db;
    Collection<FamilyMembersContract> members;
    ArrayList<FamilyMembersContract> hh;
    Map<String, String> hhMap;
    ArrayList<String> hhList;
    Boolean isHC = false, isHT = false, isWT = false;
    int length = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_antrho_info);
        db = new DatabaseHelper(this);
        binding.setCallback(this);

        SetupViewFunctionality();

    }

    public void SetupViewFunctionality() {

        MainApp.all_members = new ArrayList<>();
        MainApp.childUnder2 = new ArrayList<>();
        MainApp.childUnder5 = new ArrayList<>();
        MainApp.childNA = new ArrayList<>();
        MainApp.mwra = new ArrayList<>();
        MainApp.adolescents = new ArrayList<>();
        MainApp.childUnder2Check = new ArrayList<>();
        members = new ArrayList<>();
        hhMap = new HashMap<>();
        hhList = new ArrayList<>();
        hh = new ArrayList<>();
        json = new JSONModelClass();

        /*if(MainActivity.ftype.equals("A"))
        {
            binding.fldGrpQR.setVisibility(View.VISIBLE);
            binding.fldGrpHC.setVisibility(View.GONE);
            binding.fldGrpHT.setVisibility(View.VISIBLE);
            binding.hcCode.setText(null);
            binding.fldGrpWT.setVisibility(View.VISIBLE);
        }else if(MainActivity.ftype.equals("B")){
            binding.fldGrpQR.setVisibility(View.VISIBLE);
            binding.fldGrpHC.setVisibility(View.VISIBLE);
            binding.fldGrpHT.setVisibility(View.GONE);
            binding.htCode.setText(null);
            binding.fldGrpWT.setVisibility(View.GONE);
            binding.wtCode.setText(null);
        }else if(MainActivity.ftype.equals("W"))
        {
            binding.fldGrpQR.setVisibility(View.GONE);
            binding.hcCode.setText(null);
            binding.htCode.setText(null);
            binding.wtCode.setText(null);
        }

*/
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

                binding.fldGrpQR.setVisibility(View.GONE);

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

                    binding.fldGrpHT.setVisibility(View.VISIBLE);
                    binding.fldGrpWT.setVisibility(View.VISIBLE);
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

            SaveDraft();

            if (UpdateDB()) {
                //Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();
                finish();

                startActivity(new Intent(this, SectionD1Activity.class));


            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {

        Toast.makeText(this, "Processing End Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {

            SaveDraft();

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
        if (!ValidatorClass.EmptyTextBox(this, binding.nh102, getString(R.string.nh102))) {
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

        /*if(MainActivity.ftype.equals("B")) {
            if (!ValidatorClass.EmptyTextBox(this, binding.hcCode, getString(R.string.hc))) {
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

        */
        if (MainActivity.ftype.equals("A")) {
            if (!ValidatorClass.EmptyTextBox(this, binding.htCode, getString(R.string.ht))) {
                return false;
            }

            if (binding.htCode.getText().toString().contains("§")) {
                scanChar = 7;
            } else {
                scanChar = 6;
            }

            if (binding.htCode.getText().length() != scanChar || !binding.htCode.getText().toString().contains("-")
                    || !binding.htCode.getText().toString().contains("HT")) {
                Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ht), Toast.LENGTH_SHORT).show();
                binding.htCode.setError("Invalid Number..");

                Log.i(TAG, "htCode: Invalid number");
                return false;
            } else {
                binding.htCode.setError(null);
            }

            if (!ValidatorClass.EmptyTextBox(this, binding.wtCode, getString(R.string.wt))) {
                return false;
            }

            if (binding.wtCode.getText().toString().contains("§")) {
                scanChar = 7;
            } else {
                scanChar = 6;
            }

            if (binding.wtCode.getText().length() != scanChar || !binding.wtCode.getText().toString().contains("-")
                    || !binding.wtCode.getText().toString().contains("WT")) {
                Toast.makeText(this, "ERROR(invalid)" + getString(R.string.wt), Toast.LENGTH_SHORT).show();
                binding.wtCode.setError("Invalid Number..");

                Log.i(TAG, "wtCode: Invalid number");
                return false;
            } else {
                binding.wtCode.setError(null);
            }
        }


        return true;
    }

    private void SaveDraft() {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        enm_no = binding.nh102.getText().toString();
        hh_no = binding.nh108.getText().toString().toUpperCase();
        //hc_code = binding.hcCode.getText().toString();
        ht_code = binding.htCode.getText().toString();
        wt_code = binding.wtCode.getText().toString();


    }


    private boolean UpdateDB() {

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
            *//*for (FamilyMembersContract fm : hh) {
                hhMap.put(fm.getFormDate(), fm.get_UUID());
                hhList.add(fm.getFormDate());
            }*//*
            for (FamilyMembersContract fm : hh) {
                hhMap.put(fm.getFormDate(), fm.get_UUID());
                hhList.add(fm.getFormDate());
            }

            binding.fldGrpQR.setVisibility(View.VISIBLE);
            binding.fldGrpHT.setVisibility(View.GONE);
            binding.fldGrpWT.setVisibility(View.GONE);

            binding.listHH.setAdapter(new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, hhList));
        }
    }*/

    public void populateMembers(final String uuid, final String formDate) {

        Toast.makeText(getApplicationContext(), "Searching Members!!", Toast.LENGTH_SHORT).show();

        members = db.getAllMembersByHHforAnthro(binding.nh102.getText().toString(), binding.nh108.getText().toString().toUpperCase(), uuid, formDate, true);

        if (members.size() != 0) {
            for (FamilyMembersContract fm : members) {

                if (fm.getsA2() != null) {
                    json = JSONUtilClass.getModelFromJSON(fm.getsA2(), JSONModelClass.class);
                    if ((Integer.valueOf(json.getAge()) >= 15 && Integer.valueOf(json.getAge()) < 50) && json.getGender().equals("2")) {
                        MainApp.mwra.add(fm);
                        MainApp.all_members.add(fm);
                    } else if ((Integer.valueOf(json.getAge()) >= 10 && (Integer.valueOf(json.getAge()) < 20)) && json.getMaritalStatus().equals("5")) {
                        MainApp.adolescents.add(fm);
                        MainApp.all_members.add(fm);
                    } else if (Integer.valueOf(json.getAge()) < 6) {
                        MainApp.childUnder5.add(fm);
                        MainApp.all_members.add(fm);
                    }
                }

            }
            if (MainApp.all_members.size() > 0) {
                Toast.makeText(getApplicationContext(), "Members Found..", Toast.LENGTH_SHORT).show();
                binding.fldGrpQR.setVisibility(View.VISIBLE);
                binding.btnContinue.setVisibility(View.VISIBLE);
                binding.btnEnd.setVisibility(View.GONE);

            } else {
                binding.fldGrpQR.setVisibility(View.GONE);
                //binding.hcCode.setText(null);
                binding.htCode.setText(null);
                binding.wtCode.setText(null);
                binding.btnContinue.setVisibility(View.GONE);
                binding.btnEnd.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "No Eligible member found for anthropometry, Check another HH.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "No members found for the HH.", Toast.LENGTH_SHORT).show();
        }
    }

    public void BtnCheckEnm() {

        if (ValidatorClass.EmptyTextBox(this, binding.nh102, getString(R.string.nh102))) {

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

    /*public void BtnScanHC() {
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
*/
    public void BtnScanHT() {
        //binding.hcCode.setText(null);
        isHT = true;
        isWT = false;
        isHC = false;
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan the QR code of Machine");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();

    }

    public void BtnScanWT() {
        //binding.hcCode.setText(null);
        isWT = true;
        isHT = false;
        isHC = false;
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
                 /*   if (result.getContents().contains("HC")) {
                        Toast.makeText(this, "HC Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        binding.hcCode.setText("§" + result.getContents().trim());
                        binding.hcCode.setEnabled(false);
                        binding.hcCode.setError(null);
                    } else {
                        binding.hcCode.setError("Please Scan correct QR code");
                    }*/
                } else if (isHT) {
                    if (result.getContents().contains("HT")) {
                        Toast.makeText(this, "HT Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        binding.htCode.setText("§" + result.getContents().trim());
                        binding.htCode.setEnabled(false);
                        binding.htCode.setError(null);
                    } else {
                        binding.htCode.setError("Please Scan correct QR code");
                    }
                } else if (isWT) {
                    if (result.getContents().contains("WT")) {
                        Toast.makeText(this, "WT Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        binding.wtCode.setText("§" + result.getContents().trim());
                        binding.wtCode.setEnabled(false);
                        binding.wtCode.setError(null);
                    } else {
                        binding.wtCode.setError("Please Scan correct QR code");
                    }
                }


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
