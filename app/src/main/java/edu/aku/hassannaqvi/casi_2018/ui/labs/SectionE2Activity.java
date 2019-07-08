package edu.aku.hassannaqvi.casi_2018.ui.labs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2018.contracts.WaterSpecimenContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionE2Binding;
import edu.aku.hassannaqvi.casi_2018.ui.mainUI.MainActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionE2Activity extends AppCompatActivity {


    private static final String TAG = SectionE2Activity.class.getSimpleName();
    ActivitySectionE2Binding bi;
    Boolean isMicro = false, isNitric = false, isBoric = false, isPlain = false, isQC = false, isField = false;
    int isFieldValue = 0;

    //FamilyMembersContract fmc;
    Map<Integer, FamilyMembersContract> membersMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_e2);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e2);
        this.setTitle(getResources().getString(R.string.ne2heading));
        bi.setCallback(this);


        membersMap = new HashMap<>();

        for (FamilyMembersContract fmc : MainApp.all_members) {
            membersMap.put(0, fmc);
        }

//        checkingFieldBlank();


        bi.ne201a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ClearClass.ClearAllFields(bi.fldGrpMicro, true);
//                    bi.btnScanMicro.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpMicro, false);
                    bi.btnScanMicro.setEnabled(false);
                }

                checkingFieldBlank();
            }
        });

        bi.ne201b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ClearClass.ClearAllFields(bi.fldGrpNirtric, true);
//                    bi.btnScanNitric.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpNirtric, false);
                    bi.btnScanNitric.setEnabled(false);
                }

                checkingFieldBlank();
            }
        });

        bi.ne201c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ClearClass.ClearAllFields(bi.fldGrpBoric, true);
//                    bi.btnScanBoric.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpBoric, false);
                    bi.btnScanBoric.setEnabled(false);
                }

                checkingFieldBlank();
            }
        });

        bi.ne201d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ClearClass.ClearAllFields(bi.fldGrpPlain, true);
//                    bi.btnScanPlain.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpPlain, false);
                    bi.btnScanPlain.setEnabled(false);
                }

                checkingFieldBlank();
            }
        });


        bi.ne201e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ClearClass.ClearAllFields(bi.fldGrpQC, true);
//                    bi.btnScanQC.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpQC, false);
                    bi.btnScanQC.setEnabled(false);
                }

                checkingFieldBlank();
            }
        });

        bi.ne201f.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ClearClass.ClearAllFields(bi.fldGrpField, true);
                    /*bi.btnScanField01.setEnabled(true);
                    bi.btnScanField02.setEnabled(true);
                    bi.btnScanField03.setEnabled(true);
                    bi.btnScanField04.setEnabled(true);*/
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpField, false);
                    bi.btnScanField01.setEnabled(false);
                    bi.btnScanField02.setEnabled(false);
                    bi.btnScanField03.setEnabled(false);
                    bi.btnScanField04.setEnabled(false);
                }
            }
        });


        bi.ne20201.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ne20201a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpne20301, true);
                    bi.btnScanMicro.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpne20301, false);
                    bi.btnScanMicro.setEnabled(false);
                }
            }
        });

        bi.ne20202.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ne20202a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpneNitric, true);
                    bi.btnScanNitric.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpneNitric, false);
                    bi.btnScanNitric.setEnabled(false);
                }
            }
        });


        bi.ne20203.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ne20203a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpneBoric, true);
                    bi.btnScanBoric.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpneBoric, false);
                    bi.btnScanBoric.setEnabled(false);
                }
            }
        });

        bi.ne20204.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ne20204a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpnePlain, true);
                    bi.btnScanPlain.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpnePlain, false);
                    bi.btnScanPlain.setEnabled(false);
                }
            }
        });

        bi.ne20205.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ne20205a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpneQC, true);
                    bi.btnScanQC.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpneQC, false);
                    bi.btnScanQC.setEnabled(false);
                }
            }
        });

        bi.ne2020601.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ne2020601a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpneField01, true);
                    bi.btnScanField01.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpneField01, false);
                    bi.btnScanField01.setEnabled(false);
                }
            }
        });

        bi.ne2020602.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ne2020602a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpneField02, true);
                    bi.btnScanField02.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpneField02, false);
                    bi.btnScanField02.setEnabled(false);
                }
            }
        });

        bi.ne2020603.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ne2020603a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpneField03, true);
                    bi.btnScanField03.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpneField03, false);
                    bi.btnScanField03.setEnabled(false);
                }
            }
        });

        bi.ne2020604.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ne2020604a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpneField04, true);
                    bi.btnScanField04.setEnabled(true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpneField04, false);
                    bi.btnScanField04.setEnabled(false);
                }
            }
        });

    }

    public void checkingFieldBlank() {
        if (bi.ne201a.isChecked() && bi.ne201b.isChecked() && bi.ne201c.isChecked() && bi.ne201d.isChecked() && bi.ne201e.isChecked()) {
            bi.fldGrpMainField.setVisibility(View.VISIBLE);
            ClearClass.ClearAllFields(bi.fldGrpMainField, true);
        } else {
            bi.fldGrpMainField.setVisibility(View.GONE);
            ClearClass.ClearAllFields(bi.fldGrpMainField, false);
        }
    }


    public void BtnScanMirco() {
        //binding.hcCode.setText(null);
        isMicro = true;
        isPlain = false;
        isNitric = false;
        isBoric = false;
        isField = false;
        isQC = false;

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan the QR code of Machine");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();

    }

    public void BtnScanNitric() {
        //binding.hcCode.setText(null);
        isNitric = true;
        isPlain = false;
        isMicro = false;
        isBoric = false;
        isField = false;
        isQC = false;

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan the QR code of Machine");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();

    }

    public void BtnScanBoric() {
        //binding.hcCode.setText(null);

        isBoric = true;
        isPlain = false;
        isMicro = false;
        isNitric = false;
        isField = false;
        isQC = false;

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan the QR code of Machine");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();

    }

    public void BtnScanPlain() {
        //binding.hcCode.setText(null);

        isPlain = true;
        isMicro = false;
        isNitric = false;
        isBoric = false;
        isField = false;
        isQC = false;

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan the QR code of Machine");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();

    }

    public void BtnScanQC() {
        //binding.hcCode.setText(null);
        isQC = true;
        isMicro = false;
        isNitric = false;
        isBoric = false;
        isField = false;
        isPlain = false;

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan the QR code of Machine");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);

        integrator.initiateScan();

    }

    public void BtnScanField(int value) {
        //binding.hcCode.setText(null);

        isField = true;
        isMicro = false;
        isNitric = false;
        isBoric = false;
        isQC = false;
        isPlain = false;

        isFieldValue = value;

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
                if (isMicro) {
                    if (result.getContents().contains("HM")) {
                        Toast.makeText(this, "HM Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        bi.ne20301.setText("§" + result.getContents().trim());
                        bi.ne20301.setEnabled(false);
                        bi.ne20301.setError(null);
                    } else {
                        bi.ne20301.setError("Please Scan correct QR code");
                    }

                    bi.ne20301.setFocusable(true);

                } else if (isNitric) {
                    if (result.getContents().contains("HN")) {
                        Toast.makeText(this, "HN Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        bi.ne20302.setText("§" + result.getContents().trim());
                        bi.ne20302.setEnabled(false);
                        bi.ne20302.setError(null);
                    } else {
                        bi.ne20302.setError("Please Scan correct QR code");
                    }

                    bi.ne20302.setFocusable(true);

                } else if (isBoric) {
                    if (result.getContents().contains("HB")) {
                        Toast.makeText(this, "HB Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        bi.ne20303.setText("§" + result.getContents().trim());
                        bi.ne20303.setEnabled(false);
                        bi.ne20303.setError(null);
                    } else {
                        bi.ne20303.setError("Please Scan correct QR code");
                    }

                    bi.ne20303.setFocusable(true);

                } else if (isPlain) {
                    if (result.getContents().contains("HP")) {
                        Toast.makeText(this, "HP Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        bi.ne20304.setText("§" + result.getContents().trim());
                        bi.ne20304.setEnabled(false);
                        bi.ne20304.setError(null);
                    } else {
                        bi.ne20304.setError("Please Scan correct QR code");
                    }

                    bi.ne20304.setFocusable(true);

                } else if (isQC) {
                    if (result.getContents().contains("HQ")) {
                        Toast.makeText(this, "HQ Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                        bi.ne20305.setText("§" + result.getContents().trim());
                        bi.ne20305.setEnabled(false);
                        bi.ne20305.setError(null);
                    } else {
                        bi.ne20305.setError("Please Scan correct QR code");
                    }

                    bi.ne20305.setFocusable(true);

                } else if (isField) {

                    String selectedField = "";
                    EditText txt = bi.ne2030601;
                    switch (isFieldValue) {
                        case 1:
                            selectedField = "FM";
                            txt = bi.ne2030601;
                            break;
                        case 2:
                            selectedField = "FN";
                            txt = bi.ne2030602;
                            break;
                        case 3:
                            selectedField = "FB";
                            txt = bi.ne2030603;
                            break;
                        case 4:
                            selectedField = "FP";
                            txt = bi.ne2030604;
                            break;

                    }

                    if (result.getContents().contains(selectedField)) {
                        Toast.makeText(this, selectedField + " Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                        txt.setText("§" + result.getContents().trim());
                        txt.setEnabled(false);
                        txt.setError(null);
                    } else {
                        txt.setError("Please Scan correct QR code");
                    }

                    txt.setFocusable(true);

                    focusOnView();

                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private final void focusOnView() {
        bi.scrollE2.post(new Runnable() {
            @Override
            public void run() {
                bi.scrollE2.fullScroll(View.FOCUS_DOWN);
            }
        });
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
        if (!(bi.ne201a.isChecked() || bi.ne201b.isChecked() || bi.ne201c.isChecked() || bi.ne201d.isChecked()
                || bi.ne201e.isChecked() || bi.ne201f.isChecked())) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.ne201), Toast.LENGTH_SHORT).show();
            bi.ne201a.setError("This data is Required!");    // Set Error on last radio button

            Log.i(TAG, "ne201: This data is Required!");
            return false;
        } else {
            bi.ne201a.setError(null);
        }

        if (!bi.ne201a.isChecked() && (bi.ne201b.isChecked() || bi.ne201c.isChecked() || bi.ne201d.isChecked()
                || bi.ne201e.isChecked() || bi.ne201f.isChecked())) {
            Toast.makeText(this, "ERROR(invalid): " + getString(R.string.ne201a), Toast.LENGTH_SHORT).show();
            bi.ne201a.setError("Please Select Micro Testing first...");    // Set Error on last radio button

            Log.i(TAG, "ne201a: This data is Required!");
            return false;
        } else {
            bi.ne201a.setError(null);
        }

        if (bi.ne201a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ne20201, bi.ne20201a, getString(R.string.ne202a))) {
                return false;
            }

            if (bi.ne20201a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne20301, getString(R.string.ne203))) {
                    return false;
                }

                if (bi.ne20301.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne20301.getText().length() != scanChar || !bi.ne20301.getText().toString().contains("-")
                        || !bi.ne20301.getText().toString().contains("HM")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne203), Toast.LENGTH_SHORT).show();
                    bi.ne20301.setError("Invalid or Incomplete QR code..");

                    Log.i(TAG, "ne20301: Invalid Bar code");
                    return false;
                } else {
                    bi.ne20301.setError(null);
                }

            }
        }

        if (bi.ne201b.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ne20202, bi.ne20202a, getString(R.string.ne202b))) {
                return false;
            }

            if (bi.ne20202a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne20302, getString(R.string.ne203))) {
                    return false;
                }

                if (bi.ne20302.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne20302.getText().length() != scanChar || !bi.ne20302.getText().toString().contains("-")
                        || !bi.ne20302.getText().toString().contains("HN")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne203), Toast.LENGTH_SHORT).show();
                    bi.ne20302.setError("Invalid or Incomplete QR code..");

                    Log.i(TAG, "ne20302: Invalid Bar code");
                    return false;
                } else {
                    bi.ne20302.setError(null);
                }

            }
        }


        if (bi.ne201c.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ne20203, bi.ne20203a, getString(R.string.ne202c))) {
                return false;
            }

            if (bi.ne20203a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne20303, getString(R.string.ne203))) {
                    return false;
                }

                if (bi.ne20303.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne20303.getText().length() != scanChar || !bi.ne20303.getText().toString().contains("-")
                        || !bi.ne20303.getText().toString().contains("HB")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne203), Toast.LENGTH_SHORT).show();
                    bi.ne20303.setError("Invalid or Incomplete QR code..");

                    Log.i(TAG, "ne20303: Invalid Bar code");
                    return false;
                } else {
                    bi.ne20303.setError(null);
                }

            }
        }

        if (bi.ne201d.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ne20204, bi.ne20204a, getString(R.string.ne202d))) {
                return false;
            }

            if (bi.ne20204a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne20304, getString(R.string.ne203))) {
                    return false;
                }

                if (bi.ne20304.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne20304.getText().length() != scanChar || !bi.ne20304.getText().toString().contains("-")
                        || !bi.ne20304.getText().toString().contains("HP")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne203), Toast.LENGTH_SHORT).show();
                    bi.ne20304.setError("Invalid or Incomplete QR code..");

                    Log.i(TAG, "ne20304: Invalid Bar code");
                    return false;
                } else {
                    bi.ne20304.setError(null);
                }

            }
        }

        if (bi.ne201e.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ne20205, bi.ne20205a, getString(R.string.ne202e))) {
                return false;
            }

            if (bi.ne20205a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne20305, getString(R.string.ne203))) {
                    return false;
                }

                if (bi.ne20305.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne20305.getText().length() != scanChar || !bi.ne20305.getText().toString().contains("-")
                        || !bi.ne20305.getText().toString().contains("HQ")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne203), Toast.LENGTH_SHORT).show();
                    bi.ne20305.setError("Invalid or Incomplete QR code..");

                    Log.i(TAG, "ne20305: Invalid Bar code");
                    return false;
                } else {
                    bi.ne20305.setError(null);
                }

            }
        }

        if (bi.ne201f.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ne2020601, bi.ne2020601a, getString(R.string.ne202f01))) {
                return false;
            }

            if (bi.ne2020601a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne2030601, getString(R.string.ne203))) {
                    return false;
                }

                if (bi.ne2030601.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne2030601.getText().length() != scanChar || !bi.ne2030601.getText().toString().contains("-")
                        || !bi.ne2030601.getText().toString().contains("FM")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne203), Toast.LENGTH_SHORT).show();
                    bi.ne2030601.setError("Invalid or Incomplete QR code..");

                    Log.i(TAG, "ne2030601: Invalid Bar code");
                    return false;
                } else {
                    bi.ne2030601.setError(null);
                }

            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.ne2020602, bi.ne2020602a, getString(R.string.ne202f02))) {
                return false;
            }

            if (bi.ne2020602a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne2030602, getString(R.string.ne203))) {
                    return false;
                }

                if (bi.ne2030602.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne2030602.getText().length() != scanChar || !bi.ne2030602.getText().toString().contains("-")
                        || !bi.ne2030602.getText().toString().contains("FN")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne203), Toast.LENGTH_SHORT).show();
                    bi.ne2030602.setError("Invalid or Incomplete QR code..");

                    Log.i(TAG, "ne2030602: Invalid Bar code");
                    return false;
                } else {
                    bi.ne2030602.setError(null);
                }

            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.ne2020603, bi.ne2020603a, getString(R.string.ne202f03))) {
                return false;
            }

            if (bi.ne2020603a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne2030603, getString(R.string.ne203))) {
                    return false;
                }

                if (bi.ne2030603.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne2030603.getText().length() != scanChar || !bi.ne2030603.getText().toString().contains("-")
                        || !bi.ne2030603.getText().toString().contains("FB")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne203), Toast.LENGTH_SHORT).show();
                    bi.ne2030603.setError("Invalid or Incomplete QR code..");

                    Log.i(TAG, "ne2030603: Invalid Bar code");
                    return false;
                } else {
                    bi.ne2030603.setError(null);
                }

            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.ne2020604, bi.ne2020604a, getString(R.string.ne202f04))) {
                return false;
            }

            if (bi.ne2020604a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ne2030604, getString(R.string.ne203))) {
                    return false;
                }

                if (bi.ne2030604.getText().toString().contains("§")) {
                    scanChar = 19;
                } else {
                    scanChar = 18;
                }

                if (bi.ne2030604.getText().length() != scanChar || !bi.ne2030604.getText().toString().contains("-")
                        || !bi.ne2030604.getText().toString().contains("FP")) {
                    Toast.makeText(this, "ERROR(invalid)" + getString(R.string.ne203), Toast.LENGTH_SHORT).show();
                    bi.ne2030604.setError("Invalid or Incomplete QR code..");

                    Log.i(TAG, "ne2030604: Invalid Bar code");
                    return false;
                } else {
                    bi.ne2030604.setError(null);
                }

            }
        }
       /* if(bi.ne20202a.isChecked() && bi.ne20203a.isChecked() && bi.ne20204a.isChecked()){
            String[] typeb = bi.ne20302.getText().toString().split("-");
            String[] typec = bi.ne20303.getText().toString().split("-");
            String[] typed = bi.ne20304.getText().toString().split("-");
            if(!typeb[2].equals(typec[2]) && !typec[2].equals(typed[2])){
                Toast.makeText(this, "ERROR(Wrong QR code)" + getString(R.string.ne201b), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "ERROR(Wrong QR code)" + getString(R.string.ne201c), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "ERROR(Wrong QR code)" + getString(R.string.ne201d), Toast.LENGTH_SHORT).show();
                bi.ne20302.setError("Invalid or Incomplete QR code. Must be of same characters!");
                bi.ne20303.setError("Invalid or Incomplete QR code. Must be of same characters!");
                bi.ne20304.setError("Invalid or Incomplete QR code. Must be of same characters!");
                return false;
            }else{
                bi.ne20302.setError(null);
                bi.ne20303.setError(null);
                bi.ne20304.setError(null);
            }
        }*/
        if (bi.ne20202a.isChecked() && bi.ne20203a.isChecked()) {
            String[] typeb = bi.ne20302.getText().toString().split("-");
            String[] typec = bi.ne20303.getText().toString().split("-");
            if (!typeb[2].equals(typec[2])) {
                Toast.makeText(this, "ERROR(Wrong QR code)" + getString(R.string.ne201b), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "ERROR(Wrong QR code)" + getString(R.string.ne201c), Toast.LENGTH_SHORT).show();
                bi.ne20302.setError("Invalid or Incomplete QR code. Must be of same characters!");
                bi.ne20303.setError("Invalid or Incomplete QR code. Must be of same characters!");
                return false;
            } else {
                bi.ne20302.setError(null);
                bi.ne20303.setError(null);

            }
        }
        if (bi.ne20202a.isChecked() && bi.ne20204a.isChecked()) {
            String[] typeb = bi.ne20302.getText().toString().split("-");
            String[] typed = bi.ne20304.getText().toString().split("-");
            if (!typeb[2].equals(typed[2])) {
                Toast.makeText(this, "ERROR(Wrong QR code)" + getString(R.string.ne201b), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "ERROR(Wrong QR code)" + getString(R.string.ne201d), Toast.LENGTH_SHORT).show();
                bi.ne20302.setError("Invalid or Incomplete QR code. Must be of same characters!");
                bi.ne20304.setError("Invalid or Incomplete QR code. Must be of same characters!");
                return false;
            } else {
                bi.ne20302.setError(null);
                bi.ne20304.setError(null);
            }
        }
        if (bi.ne20203a.isChecked() && bi.ne20204a.isChecked()) {
            String[] typec = bi.ne20303.getText().toString().split("-");
            String[] typed = bi.ne20304.getText().toString().split("-");
            if (!typec[2].equals(typed[2])) {
                Toast.makeText(this, "ERROR(Wrong QR code)" + getString(R.string.ne201c), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "ERROR(Wrong QR code)" + getString(R.string.ne201d), Toast.LENGTH_SHORT).show();
                bi.ne20303.setError("Invalid or Incomplete QR code. Must be of same characters!");
                bi.ne20304.setError("Invalid or Incomplete QR code. Must be of same characters!");
                return false;
            } else {
                bi.ne20303.setError(null);
                bi.ne20304.setError(null);
            }
        }


        return true;
    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        MainApp.wsc = new WaterSpecimenContract();
        MainApp.wsc.setDevicetagID(MainApp.getTagName(this));
        MainApp.wsc.setFormDate(membersMap.get(0).getFormDate());
        MainApp.wsc.setUser(MainApp.userName);
        MainApp.wsc.setDeviceID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        MainApp.wsc.setAppversion(MainApp.versionName + "." + MainApp.versionCode);
        MainApp.wsc.setUUID(membersMap.get(0).get_UUID());

        MainApp.wsc.setClusterno(SpecimenInfoActivity.enm_no);
        MainApp.wsc.setHhno(SpecimenInfoActivity.hh_no);

        JSONObject sE1 = new JSONObject();

        sE1.put("ne_selected_water", String.valueOf(SpecimenInfoActivity.selected));
        sE1.put("start_time", SpecimenInfoActivity.datetime);
        sE1.put("ne201a", bi.ne201a.isChecked() ? "1" : "2");
        sE1.put("ne20201", bi.ne20201a.isChecked() ? "1" : bi.ne20201b.isChecked() ? "2" : "0");
        sE1.put("ne20301", bi.ne20301.getText().toString());

        sE1.put("ne201b", bi.ne201b.isChecked() ? "1" : "2");
        sE1.put("ne20202", bi.ne20202a.isChecked() ? "1" : bi.ne20202b.isChecked() ? "2" : "0");
        sE1.put("ne20302", bi.ne20302.getText().toString());

        sE1.put("ne201c", bi.ne201c.isChecked() ? "1" : "2");
        sE1.put("ne20203", bi.ne20203a.isChecked() ? "1" : bi.ne20203b.isChecked() ? "2" : "0");
        sE1.put("ne20303", bi.ne20303.getText().toString());

        sE1.put("ne201d", bi.ne201d.isChecked() ? "1" : "2");
        sE1.put("ne20204", bi.ne20204a.isChecked() ? "1" : bi.ne20204b.isChecked() ? "2" : "0");
        sE1.put("ne20304", bi.ne20304.getText().toString());

        sE1.put("ne201e", bi.ne201e.isChecked() ? "1" : "2");
        sE1.put("ne20205", bi.ne20205a.isChecked() ? "1" : bi.ne20205b.isChecked() ? "2" : "0");
        sE1.put("ne20305", bi.ne20305.getText().toString());

        sE1.put("ne201f", bi.ne201f.isChecked() ? "1" : "2");

        sE1.put("ne2020601", bi.ne2020601a.isChecked() ? "1" : bi.ne2020601b.isChecked() ? "2" : "0");
        sE1.put("ne2030601", bi.ne2030601.getText().toString());

        sE1.put("ne2020602", bi.ne2020602a.isChecked() ? "1" : bi.ne2020602b.isChecked() ? "2" : "0");
        sE1.put("ne2030602", bi.ne2030602.getText().toString());

        sE1.put("ne2020603", bi.ne2020603a.isChecked() ? "1" : bi.ne2020603b.isChecked() ? "2" : "0");
        sE1.put("ne2030603", bi.ne2030603.getText().toString());

        sE1.put("ne2020604", bi.ne2020604a.isChecked() ? "1" : bi.ne2020604b.isChecked() ? "2" : "0");
        sE1.put("ne2030604", bi.ne2030604.getText().toString());

        sE1.put("end_time", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

        MainApp.wsc.setsE2(String.valueOf(sE1));


        // Set summary fields
        FormsContract fc = new FormsContract();
        fc.setClusterNo(MainApp.wsc.getClusterno());
        fc.setHhNo(MainApp.wsc.getHhno());
        fc.setDevicetagID(MainApp.wsc.getDevicetagID());
        fc.setFormDate(MainApp.wsc.getFormDate());
        fc.setUser(MainApp.wsc.getUser());
        fc.setDeviceID(MainApp.wsc.getDeviceID());
        fc.setAppversion(MainApp.wsc.getAppversion());
        MainApp.sumc = MainApp.AddSummary(fc, 6);

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();


    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);

        Long updcount = db.addWaterSpecimenForm(MainApp.wsc);
        MainApp.wsc.set_ID(String.valueOf(updcount));

        if (updcount != 0) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            MainApp.wsc.setUID(
                    (MainApp.wsc.getDeviceID() + MainApp.wsc.get_ID()));
            db.updateWaterSpecimenMemberID();

            return MainApp.UpdateSummary(this, db, 6);

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }


        //return true;
    }
}
