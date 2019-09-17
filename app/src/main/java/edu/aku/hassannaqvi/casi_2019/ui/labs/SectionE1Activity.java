package edu.aku.hassannaqvi.casi_2019.ui.labs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Random;

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2019.contracts.SpecimenContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionE1Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.MainActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

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
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e1);

        this.setTitle(getResources().getString(R.string.ne1heading));

        bi.setCallback(this);
        setupViews();

    }

    public void setupViews() {

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

                    members = new ArrayList<>();
                    membersMap = new HashMap<>();
                    members.add("....");

                    fetchMembersFromGroup(position);
                    bi.ne102.setAdapter(new ArrayAdapter<>(SectionE1Activity.this, R.layout.item_style, members));
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

        bi.ne107.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i != bi.ne107c.getId())
                    bi.ne106.setText(null);
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


        /*for (FamilyMembersContract fmc : family) {
            json = JSONUtilClass.getModelFromJSON(fmc.getsA2(), JSONModelClass.class);
            membersMap.put(json.getName() + "_" + json.getSerialNo(), new SelectedMem(position, fmc, json.getSerialNo()));
            if (!MainApp.duplicateMembers.contains(json.getName() + "_" + json.getSerialNo()))
                members.add(json.getName() + "_" + json.getSerialNo());
        }*/

        int rnd = new Random().nextInt(family.size());

        json = JSONUtilClass.getModelFromJSON(family.get(rnd).getsA2(), JSONModelClass.class);
        membersMap.put(json.getName() + "_" + json.getSerialNo(), new SelectedMem(position, family.get(rnd), json.getSerialNo()));
        if (!MainApp.duplicateMembers.contains(json.getName() + "_" + json.getSerialNo()))
            members.add(json.getName() + "_" + json.getSerialNo());


    }

    public boolean checkmembersExists(List<FamilyMembersContract> family) {
        List<String> memberslist = new ArrayList<>();
        for (FamilyMembersContract fmc : family) {

            json = JSONUtilClass.getModelFromJSON(fmc.getsA2(), JSONModelClass.class);
            if (!MainApp.duplicateMembers.contains(json.getName() + "_" + json.getSerialNo()))
                memberslist.add(json.getName() + "_" + json.getSerialNo());

        }

        return memberslist.size() > 0;
    }


    public void BtnScanBL() {

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

                if (group.size() > 2) {

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

            MainApp.endAnthroActivity(this, this);

        } else {
            Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean formValidation() {
        if (!ValidatorClass.EmptyCheckingContainerV2(this, bi.fldGrphb))
            return false;

        if (bi.ne107c.isChecked()) {
            if (!bi.ne106.getText().toString().matches("^(\\d{2,2}\\.\\d{1,1})$")) {
                Toast.makeText(this, "ERROR(invalid): " + "Please type the correct format" + getString(R.string.hb_result), Toast.LENGTH_LONG).show();
                bi.ne106.setError("Please type correct format (XX.X)");
                return false;
            } else {
                bi.ne106.setError(null);
            }
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

//        sE1.put("cine_selected_blood", String.valueOf(SpecimenInfoActivity.selected));
        sE1.put("cine_consent", String.valueOf(SpecimenInfoActivity.consent));
        sE1.put("start_time", SpecimenInfoActivity.datetime);

        sE1.put("cine102", bi.ne102.getSelectedItem().toString());
        sE1.put("cine103", originalPositions.get(bi.ne103.getSelectedItemPosition()));
        sE1.put("cine104", bi.ne106.getText().toString());
        sE1.put("cine105", bi.ne107a.isChecked() ? "1" : bi.ne107b.isChecked() ? "2" : bi.ne107c.isChecked() ? "3" : "0");
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

