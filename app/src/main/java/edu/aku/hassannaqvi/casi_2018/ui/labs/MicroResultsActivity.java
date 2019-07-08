package edu.aku.hassannaqvi.casi_2018.ui.labs;

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
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONE2ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.casi_2018.contracts.MicroContract;
import edu.aku.hassannaqvi.casi_2018.contracts.WaterSpecimenContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivityMicroResultsBinding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.mainUI.MainActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class MicroResultsActivity extends AppCompatActivity {

    private static final String TAG = MicroResultsActivity.class.getSimpleName();
    ActivityMicroResultsBinding binding;
    DatabaseHelper db;

    int length = 0;

    Collection<WaterSpecimenContract> specimen;
    JSONE2ModelClass json;
    String formdate = "";
    String uuid = "";
    String wuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_micro_results);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_micro_results);

        db = new DatabaseHelper(this);
        binding.setCallback(this);


        binding.ne20301b.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()));
        binding.ne20301f.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTimeInMillis() + ((MainApp.MILLISECONDS_IN_DAY))));
        //  binding.ne20301f.setMinDate(new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()));
        //binding.ne20301c.setCu


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


    }


    public void BtnCheckHH() {

        if (!binding.nh102.getText().toString().trim().isEmpty() && !binding.nh108.getText().toString().trim().isEmpty()) {

            specimen = db.getMicroforresults(binding.nh102.getText().toString(), binding.nh108.getText().toString().toUpperCase());

            if (specimen.size() != 0) {
                for (WaterSpecimenContract fm : specimen) {

                    if (fm.getsE2() != null) {
                        json = JSONUtilClass.getModelFromJSON(fm.getsE2(), JSONE2ModelClass.class);
                        if ((Integer.valueOf(json.getNe201a()) == 1) && Integer.valueOf(json.getNe20201()) == 1) {

                            formdate = fm.getFormDate();
                            uuid = fm.getUUID();
                            wuid = fm.getUID();
                            Toast.makeText(this, "Household Found..", Toast.LENGTH_SHORT).show();
                            binding.fldGrpne20301a.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(this, "Not Found..", Toast.LENGTH_SHORT).show();
                            binding.fldGrpne20301a.setVisibility(View.GONE);
                        }
                    }
                }


            } else {
                Toast.makeText(this, "Not found.", Toast.LENGTH_SHORT).show();
                binding.fldGrpne20301a.setVisibility(View.GONE);
            }
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

    public void BtnScanPetri() {
        //binding.hcCode.setText(null);

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
                if (result.getContents().contains("HM") && result.getContents().contains("PET")) {
                    Toast.makeText(this, "PET Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    binding.ne20301a.setText("§" + result.getContents().trim());
                    binding.ne20301a.setEnabled(false);
                    binding.ne20301a.setError(null);
                } else {
                    binding.ne20301a.setError("Please Scan correct QR code");
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
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
                //Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();

                finish();

                startActivity(new Intent(this, MainActivity.class));

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
            //Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();

            //finish();

            MainApp.endAnthroActivity(this, this);

        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean formValidation() {

        int scanChar;

        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();
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

        if (!ValidatorClass.EmptyTextBox(this, binding.ne20301a, getString(R.string.ne20301a))) {
            return false;
        }

        if (binding.ne20301a.getText().toString().contains("§")) {
            scanChar = 19;
        } else {
            scanChar = 18;
        }

        if (binding.ne20301a.getText().length() != scanChar || !binding.ne20301a.getText().toString().contains("-")
                || !binding.ne20301a.getText().toString().contains("HM") || !binding.ne20301a.getText().toString().contains("PET")) {
            Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne20301a), Toast.LENGTH_SHORT).show();
            binding.ne20301a.setError("Invalid or Incomplete QR code..");

            Log.i(TAG, "ne20301: Invalid Bar code");
            return false;
        } else {
            binding.ne20301a.setError(null);
        }


        if (!ValidatorClass.EmptyTextBox(this, binding.ne20301b, getString(R.string.ne20301b))) {
            return false;
        }


        // Collection Time
        if (!ValidatorClass.EmptyTextBox(this, binding.ne20301c, getString(R.string.ne20301c))) {
            return false;
        }


        // Inoculation Time
        if (!ValidatorClass.EmptyTextBox(this, binding.ne20301d, getString(R.string.ne20301d))) {
            return false;
        }


        if (binding.ne20301c.getText().toString().equals(binding.ne20301d.getText().toString())) {
            Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne20301d), Toast.LENGTH_SHORT).show();
            binding.ne20301d.setError("Should not be equal to Collection Time.. Check Again");
            Log.i(TAG, "ne20301d: Invalid Time");
            return false;

        } else {
            binding.ne20301d.setError(null);
        }

        // Reading Time
        if (!ValidatorClass.EmptyTextBox(this, binding.ne20301e, getString(R.string.ne20301e))) {
            return false;
        }

        if (binding.ne20301d.getText().toString().equals(binding.ne20301e.getText().toString())) {
            Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne20301e), Toast.LENGTH_SHORT).show();
            binding.ne20301e.setError("Should not be equal to Inoculation Time.. Check Again");
            Log.i(TAG, "ne20301d: Invalid Time");
            return false;

        } else {
            binding.ne20301e.setError(null);
        }

        if (!ValidatorClass.EmptyTextBox(this, binding.ne20301f, getString(R.string.ne20301f))) {
            return false;
        }


        if (!ValidatorClass.EmptyTextBox(this, binding.ne20301g, getString(R.string.ne20301g))) {
            return false;
        }


        if (!ValidatorClass.EmptyTextBox(this, binding.ne20301h, getString(R.string.ne20301h))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, binding.ne20301h, 0, 999, getString(R.string.ne20301h), "")) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, binding.ne20301i, getString(R.string.ne20301i))) {
            return false;
        }

        return ValidatorClass.RangeTextBox(this, binding.ne20301i, 0, 999, getString(R.string.ne20301i), "");
    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        MainApp.msc = new MicroContract();
        MainApp.msc.setDevicetagID(MainApp.getTagName(this));
        MainApp.msc.setFormDate(formdate);
        MainApp.msc.setUser(MainApp.userName);
        MainApp.msc.setDeviceID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        MainApp.msc.setAppversion(MainApp.versionName + "." + MainApp.versionCode);
        MainApp.msc.setUUID(uuid);
        MainApp.msc.setWUID(wuid);

        MainApp.msc.setClusterno(binding.nh102.getText().toString());
        MainApp.msc.setHhno(binding.nh108.getText().toString());

        JSONObject sE1 = new JSONObject();


        sE1.put("ne20301a", binding.ne20301a.getText().toString());
        sE1.put("ne20301b", binding.ne20301b.getText().toString());
        sE1.put("ne20301c", binding.ne20301c.getText().toString());
        sE1.put("ne20301d", binding.ne20301d.getText().toString());
        sE1.put("ne20301e", binding.ne20301e.getText().toString());
        sE1.put("ne20301f", binding.ne20301f.getText().toString());
        sE1.put("ne20301g", binding.ne20301g.getText().toString());
        sE1.put("ne20301h", binding.ne20301h.getText().toString());
        sE1.put("ne20301i", binding.ne20301i.getText().toString());


        MainApp.msc.setsM(String.valueOf(sE1));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();


    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);


        Long updcount = db.addMicroForm(MainApp.msc);
        MainApp.msc.set_ID(String.valueOf(updcount));

        if (updcount != 0) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            MainApp.msc.setUID(
                    (MainApp.msc.getDeviceID() + MainApp.msc.get_ID()));
            db.updateMicroID();

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }


        // return true;
    }

}
