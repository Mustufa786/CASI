package edu.aku.hassannaqvi.casi_2019.ui.labs;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
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

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.contracts.SpecimenContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySpecimenInfoBinding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.household.EndingActivity;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.MainActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;


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
    StringBuilder mwra, adolscent, child, other;

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

        this.setTitle(getString(R.string.na1heading));

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

        mwra = new StringBuilder();
        other = new StringBuilder();
        adolscent = new StringBuilder();
        child = new StringBuilder();

        if (MainActivity.ftype.equals("B")) {
            binding.fldGrpcihconsent.setVisibility(View.VISIBLE);
            binding.fldGrpQR.setVisibility(View.VISIBLE);
            binding.fldGrpHC.setVisibility(View.VISIBLE);
            binding.txtSelected.setText(getResources().getString(R.string.selected1));


        } else if (MainActivity.ftype.equals("W")) {
            binding.fldGrpcihconsent.setVisibility(View.GONE);
            binding.fldGrpQR.setVisibility(View.GONE);
            binding.fldGrpHC.setVisibility(View.GONE);
            binding.hcCode.setText(null);
            binding.txtSelected.setText(getResources().getString(R.string.selected2));

        }

        /*binding.na11802.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        });*/

        //slcMem = new ArrayList<>();
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


//        HH listener
        binding.cih108.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                binding.cih108.setInputType(InputType.TYPE_CLASS_NUMBER);

                length = charSequence.toString().length();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!binding.cih108.getText().toString().isEmpty() && binding.cih108.getText().toString().length() == 4) {
                    if (binding.cih108.getText().toString().substring(0, 3).matches("[0-9]+")) {
                        if (length < 5) {
                            binding.cih108.setText(binding.cih108.getText().toString() + "-");
                            binding.cih108.setSelection(binding.cih108.getText().length());
                        }

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }

    public void BtnContinue() {


        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (UpdateDB()) {

                finish();

                if (MainActivity.ftype.equals("B")) {

                    if (binding.na11802a.isChecked()) {
                        startActivity(new Intent(this, SectionE1Activity.class));
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


        if (formValidation()) {

            try {

                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (UpdateDB()) {


                finish();

                startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean formValidation() {


//        cih102
        if (!ValidatorClass.EmptyTextBox(this, binding.cih102, getString(R.string.cih102))) {
            return false;
        }

//        cih108

        if (binding.cih108.getText().toString().length() == 8) {
            String[] str = binding.cih108.getText().toString().split("-");
            if (str.length > 2 || binding.cih108.getText().toString().charAt(4) != '-' || !str[0].matches("[0-9]+") || !str[1].matches("[0-9]+")) {
                binding.cih108.setError("Wrong presentation!!");
                return false;
            }
        } else {
            Toast.makeText(this, "Invalid length: " + getString(R.string.cih108), Toast.LENGTH_SHORT).show();
            binding.cih108.setError("Invalid length");
            return false;
        }


        int scanChar;

        if (MainActivity.ftype.equals("B")) {


            return ValidatorClass.EmptyRadioButton(this, binding.na11802, binding.na11802b, getString(R.string.na11802));

            /*if (binding.na11802a.isChecked()) {

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
            }*/
        }


        return true;
    }

    private void SaveDraft() throws JSONException {


        if (MainActivity.ftype.equals("B")) {
            if (binding.na11802a.isChecked()) {

                enm_no = binding.cih102.getText().toString();
                hh_no = binding.cih108.getText().toString().toUpperCase();
                hc_code = binding.hcCode.getText().toString();
                consent = binding.na11802.indexOfChild(findViewById(binding.na11802.getCheckedRadioButtonId())) + 1;
                datetime = dateTime;

            } else {
                MainApp.smc = new SpecimenContract();

                MainApp.smc.setDevicetagID(MainApp.getTagName(this));
                MainApp.smc.setFormDate(date);
                MainApp.smc.setUser(MainApp.userName);
                MainApp.smc.setDeviceID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID));
                MainApp.smc.setAppversion(MainApp.versionName + "." + MainApp.versionCode);
                MainApp.smc.setClusterno(binding.cih102.getText().toString());
                MainApp.smc.setHhno(binding.cih108.getText().toString().toUpperCase());

                JSONObject sE1 = new JSONObject();
                sE1.put("cine_consent", binding.na11802a.isChecked() ? "1" : binding.na11802b.isChecked() ? "2" : "0");
                sE1.put("start_time", dateTime);
                sE1.put("end_time", new SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis()));

                MainApp.smc.setsE1(String.valueOf(sE1));

            }

        }

    }

    private boolean UpdateDB() {

        if (MainActivity.ftype.equals("B")) {
            if (binding.na11802b.isChecked()) {
                DatabaseHelper db = new DatabaseHelper(this);


                Long updcount = db.addSpecimenMembers(MainApp.smc);
                MainApp.smc.set_ID(String.valueOf(updcount));

                if (updcount != 0) {

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
        }
        return true;
    }

    public void BtnCheckHH() {

        if (!binding.cih102.getText().toString().trim().isEmpty() && !binding.cih108.getText().toString().trim().isEmpty()) {

//            populateHH();
            hh = db.getAllHHforAnthro(binding.cih102.getText().toString(), binding.cih108.getText().toString().toUpperCase());
            if (hh.size() > 0) {
                populateMembers(hh.get(hh.size() - 1).get_UUID(), hh.get(hh.size() - 1).getFormDate());
            }

        } else {
            Toast.makeText(this, "Not found.", Toast.LENGTH_SHORT).show();
        }

    }

    public void populateMembers(String uuid, String formDate) {
        members = db.getAllMembersByHHforAnthro(binding.cih102.getText().toString(), binding.cih108.getText().toString().toUpperCase(), uuid, formDate, false);

        if (members.size() != 0) {
            for (FamilyMembersContract fm : members) {

                if (fm.getsA2() != null) {
                    json = JSONUtilClass.getModelFromJSON(fm.getsA2(), JSONModelClass.class);
                    date = fm.getFormDate();
                    if ((Integer.valueOf(json.getAge()) > 14 && Integer.valueOf(json.getAge()) < 50)
                            && json.getGender().equals("2") && json.getcih210().equals("1")) {
                        fm.setType("1");
                        MainApp.mwra.add(fm);
                        MainApp.all_members.add(fm);
                        addIfNotExists(MainApp.all_members, fm);

                        if (mwra.length() == 0)
                            mwra.append("<h3>&ensp;&ensp;MWRA's</h3>");

                        mwra.append("<i>" + json.getName().substring(0, 1).toUpperCase() + json.getName().substring(1) + "</i><br>");
                    }
                    if ((Integer.valueOf(json.getAge()) >= 10 && (Integer.valueOf(json.getAge()) < 20))
                            && json.getGender().equals("2") && json.getcih210().equals("1") && json.getMaritalStatus().equals("5")) {
                        fm.setType("4");
                        MainApp.adolescents.add(fm);
                        MainApp.all_members.add(fm);
                        addIfNotExists(MainApp.all_members, fm);

                        if (adolscent.length() == 0)
                            adolscent.append("<h3>&ensp;&ensp;Adolescent's</h3>");

                        adolscent.append("<i>" + json.getName().substring(0, 1).toUpperCase() + json.getName().substring(1) + "</i><br>");
                    }
                    if ((Integer.valueOf(json.getAge()) >= 6 && (Integer.valueOf(json.getAge()) < 13))
                            && json.getcih210().equals("1")) {
                        fm.setType("3");
                        MainApp.minors.add(fm);
                        MainApp.all_members.add(fm);
                        addIfNotExists(MainApp.all_members, fm);

                        if (other.length() == 0)
                            other.append("<h3>&ensp;&ensp;Other's</h3>");

                        other.append("<i>" + json.getName().substring(0, 1).toUpperCase() + json.getName().substring(1) + "</i><br>");
                    }
                    if (Integer.valueOf(json.getAge()) < 6 && json.getcih210().equals("1")) {
                        fm.setType("2");
                        MainApp.childUnder5.add(fm);
                        MainApp.all_members.add(fm);
                        addIfNotExists(MainApp.all_members, fm);

                        if (child.length() == 0)
                            child.append("<h3>&ensp;&ensp;Children's < 5</h3>");

                        child.append("<i>" + json.getName().substring(0, 1).toUpperCase() + json.getName().substring(1) + "</i><br>");
                    }


                }

            }

            if (MainApp.all_members.size() > 0) {

                Toast.makeText(this, "Members Found..", Toast.LENGTH_SHORT).show();
                binding.fldGrpQR.setVisibility(View.GONE);
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

        if (ValidatorClass.EmptyTextBox(this, binding.cih102, getString(R.string.cih102))) {

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
                }
            } else {
                binding.cih108.setText(null);
                Toast.makeText(this, "Sorry not found any block", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void BtnScanHC() {
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

    public void BtnShowMembers() {

        StringBuilder allSelMem = new StringBuilder();
        allSelMem.append(mwra).append(child).append(adolscent).append(other);

        new AlertDialog.Builder(this)
                .setTitle(Html.fromHtml("<h1>&ensp;&ensp;MEMBERS INFORMATION</h1>"))
                .setMessage(Html.fromHtml(allSelMem.toString()))
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.dismiss();
                            }
                        }
                ).create().show();
    }

}
