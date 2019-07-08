package edu.aku.hassannaqvi.casi_2018.ui.household;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONA8AModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.contracts.RecipientsContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionA8ABinding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionA8AActivity extends AppCompatActivity {

    static int counter = 1;
    static int reccounter = 0;
    static Map<String, FamilyMembersContract> recpmap;
    static ArrayList<String> recpNames;
    static ArrayList<String> recpSerial;
    private final long DELAY = 1000;
    ActivitySectionA8ABinding bi;
    DatabaseHelper db;
    FamilyMembersContract fmcSelected;
    int position = 0;
    JSONA8AModelClass jsonA8A;
    Boolean dataFlag = true;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a8_a);
        db = new DatabaseHelper(this);
        bi.setCallback(this);

        this.setTitle(getResources().getString(R.string.na8aheading));

        if (getIntent().getBooleanExtra("flag", true)) {
            reccounter = getIntent().getIntExtra("recCounter", 0);

            recpmap = new HashMap<>();
            recpNames = new ArrayList<>();
            recpSerial = new ArrayList<>();

            recpNames.add("....");
            recpSerial.add("0");


            for (FamilyMembersContract fmc : MainApp.all_members) {
                recpmap.put(fmc.getName() + "_" + fmc.getSerialNo(), fmc);
                recpNames.add(fmc.getName());
                recpSerial.add(fmc.getSerialNo());

                fmc.getAge();

                fmc.getAgeInYear();
            }

        } else {
            counter++;

        }

        bi.cih7a02.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, recpNames));

        bi.cih7a02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                Log.d("For Debug", "Position selected is: " + position);
                try {
                    if (position != 0) {

                        fmcSelected = recpmap.get(recpNames.get(position) + "_" + recpSerial.get(position));
                    }
                } catch (Exception e) {
                    Log.e("Error", "There is an error while selecting name from spinner: " + e);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // setup head
        bi.txtCounter.setText("Count " + counter + " out of " + reccounter);

//        Validation Boolean
        MainApp.validateFlag = false;

        if (SectionA1Activity.editFormFlag) {

            AutoPopulate();
        }

    }

    private void AutoPopulate() {

        Collection<RecipientsContract> recipientsContracts = db.getPressedRecipients();

        for (RecipientsContract recipientsContract : recipientsContracts) {

            if (recipientsContract.getA8aSNo().equals(String.valueOf(counter))) {

                dataFlag = false;

                MainApp.rc = recipientsContract;

                jsonA8A = JSONUtilClass.getModelFromJSON(recipientsContract.getsA8A(), JSONA8AModelClass.class);

                bi.cih7a02.setVisibility(View.GONE);

                bi.cih7a02a.setVisibility(View.VISIBLE);
                bi.cih7a02a.setText(jsonA8A.getcih7a02().toUpperCase());

                //  bi.
                bi.cih7a03y.setText(jsonA8A.getcih7a05y());
                bi.cih7a03m.setText(jsonA8A.getcih7a06m());

                if (!jsonA8A.getcih7a07a().equals("0")) {
                    bi.cih7a04a.setChecked(true);

                }
                if (!jsonA8A.getcih7a07b().equals("0")) {
                    bi.cih7a04b.setChecked(true);

                }
                if (!jsonA8A.getcih7a07c().equals("0")) {
                    bi.cih7a04c.setChecked(true);

                }
                if (!jsonA8A.getcih7a07d().equals("0")) {
                    bi.cih7a04d.setChecked(true);

                }
                if (!jsonA8A.getcih7a07e().equals("0")) {
                    bi.cih7a04e.setChecked(true);

                }
                if (!jsonA8A.getcih7a07f().equals("0")) {
                    bi.cih7a04f.setChecked(true);

                }
                if (!jsonA8A.getcih7a07g().equals("0")) {
                    bi.cih7a04g.setChecked(true);

                }
                if (!jsonA8A.getcih7a07h().equals("0")) {
                    bi.cih7a04h.setChecked(true);

                }
                if (!jsonA8A.getcih7a07i().equals("0")) {
                    bi.cih7a04i.setChecked(true);

                }
                if (!jsonA8A.getcih7a0796().equals("0")) {
                    bi.cih7a0496.setChecked(true);
                    bi.cih7a0496x.setText(jsonA8A.getcih7a0796x());
                }
                bi.cih7a05.setText(jsonA8A.getcih7a08());

                bi.cih7a06.setText(jsonA8A.getcih7a09());

                if (jsonA8A.getcih7aFlag().equals("1")) {
                    bi.cih7aFlag.setChecked(true);
                }

                bi.cih7aFlag.setVisibility(View.VISIBLE);

                break;
            }
        }

        /*if (dataFlag) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    SectionA8AActivity.this);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder
                    .setMessage("In previous you didn't saved No" + counter + " Recipient.\n" +
                            "Do you want to continue it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    if (counter == reccounter) {

                                        counter = 1;

                                        if (SectionA5Activity.deceasedCounter > 0) {
                                            startActivity(new Intent(getApplicationContext(), SectionH8Activity.class));
                                        } else {
                                            startActivity(new Intent(getApplicationContext(), ViewMemberActivity.class)
                                                    .putExtra("flagEdit", false)
                                                    .putExtra("comingBack", true)
                                                    .putExtra("cluster", MainApp.fc.getClusterNo())
                                                    .putExtra("hhno", MainApp.fc.getHhNo())
                                            );
                                        }
                                    } else {
                                        startActivity(new Intent(getApplicationContext(), SectionA8AActivity.class).putExtra("flag", false));
                                    }
                                }
                            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }*/

    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
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

                finish();

                if (counter == reccounter) {

                    counter = 1;
/*
                    if (SectionA5Activity.deceasedCounter > 0) {
//                        startActivity(new Intent(this, SectionH8Activity.class));
                        startActivity(new Intent(this, SectionH8infoActivity.class));
                    } else {
                        if (SectionA1Activity.editFormFlag) {

                            startActivity(new Intent(this, ViewMemberActivity.class)
                                    .putExtra("flagEdit", false)
                                    .putExtra("comingBack", true)
                                    .putExtra("cluster", MainApp.fc.getClusterNo())
                                    .putExtra("hhno", MainApp.fc.getHhNo())
                            );

                        } else {

                            if (!MainApp.UpdateSummary(this, db, 1)) {
                                Toast.makeText(this, "Summary Table not update!!", Toast.LENGTH_SHORT).show();
                            }

                            startActivity(new Intent(this, ViewMemberActivity.class).putExtra("activity", 3));
                        }
                    }*/
                    startActivity(new Intent(this, SectionH8infoActivity.class));
                } else {

                    if (dataFlag) {
                        recpNames.remove(bi.cih7a02.getSelectedItem().toString());
                        recpSerial.remove(recpSerial.get(position));
                    } else {
                        recpNames.remove(jsonA8A.getcih7a02());
                        recpSerial.remove(jsonA8A.getcih7a03());
                    }

                    startActivity(new Intent(this, SectionA8AActivity.class).putExtra("flag", false));
                }


            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void BtnEnd() {

        counter = 1;

        if (SectionA1Activity.editFormFlag) {
            startActivity(new Intent(this, ViewMemberActivity.class)
                    .putExtra("flagEdit", false)
                    .putExtra("comingBack", true)
                    .putExtra("cluster", MainApp.fc.getClusterNo())
                    .putExtra("hhno", MainApp.fc.getHhNo())
            );
        } else {
            if (ValidatorClass.EmptySpinner(this, bi.cih7a02, getString(R.string.cih7a02))) {
                try {
                    SaveDraft();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (UpdateDB()) {
                    MainApp.endActivity(this, this);
                } else {
                    Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Select name first!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean ValidateForm() {

        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (!SectionA1Activity.editFormFlag) {
            if (!ValidatorClass.EmptySpinner(this, bi.cih7a02, getString(R.string.cih7a02))) {
                return false;
            }
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.cih7a03y, getString(R.string.cih7a03y))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.cih7a03m, getString(R.string.cih7a03m))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.cih7a03m, 0, 11, getString(R.string.cih7a03m), " months")) {
            return false;
        }

        if (bi.cih7a03m.getText().toString().equals("0") && bi.cih7a03m.getText().toString().equals("0")) {
            Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.na2age), Toast.LENGTH_LONG).show();
            bi.cih7a03m.setError("All can not be zero");
            bi.cih7a03y.setError("All can not be zero");
            Log.i(SectionA2Activity.class.getSimpleName(), "cih703" + ": This data is Required!");
        } else {
            bi.cih7a03y.setError(null);
            bi.cih7a03m.setError(null);
        }

        if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpna08a04check, bi.cih7a04a, getString(R.string.cih7a04))) {
            return false;
        }

        if (!ValidatorClass.EmptyCheckBox(this, bi.fldGrpna08a04check, bi.cih7a0496, bi.cih7a0496x, getString(R.string.cih7a04) + " - " + getString(R.string.other))) {
            return false;
        }


        if (!ValidatorClass.EmptyTextBox(this, bi.cih7a05, getString(R.string.cih7a05))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.cih7a05, 1000, 99999, getString(R.string.cih7a05), " Rupees")) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.cih7a06, getString(R.string.cih7a06))) {
            return false;
        }

        return ValidatorClass.RangeTextBox(this, bi.cih7a06, 0, Integer.valueOf(bi.cih7a05.getText().toString()), getString(R.string.cih7a06), " Rupees");

    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sA8a = new JSONObject();

//        if (!SectionA1Activity.editFormFlag) {
        if (dataFlag) {
            MainApp.rc = new RecipientsContract();
            MainApp.rc.setDevicetagID(MainApp.fc.getDevicetagID());
            MainApp.rc.setFormDate(MainApp.fc.getFormDate());
            MainApp.rc.setUser(MainApp.fc.getUser());
            MainApp.rc.setDeviceId(MainApp.fc.getDeviceID());
            MainApp.rc.setApp_ver(MainApp.fc.getAppversion());
            MainApp.rc.set_UUID(MainApp.fc.getUID());
            MainApp.rc.setFMUID(fmcSelected.get_UID());
            MainApp.rc.setA8aSNo(String.valueOf(counter));

            sA8a.put("cih7a04", fmcSelected.getName());
            sA8a.put("cih7a03", fmcSelected.getSerialNo());
            sA8a.put("cih7a02", bi.cih7a02.getSelectedItem().toString());
//            sA8a.put("lineno", fmcSelected.getSerialNo());

        } else {
            sA8a.put("edit_updatedate_cih7a", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

            sA8a.put("cih7a04", jsonA8A.getcih7a04());
            sA8a.put("cih7a03", jsonA8A.getcih7a03());
            sA8a.put("cih7a02", jsonA8A.getcih7a02());
//            sA8a.put("lineno", jsonA8A.getcih7a03());
        }

       /* if (backPressed) {
            sA8a.put("updatedate_na8a", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }*/

        sA8a.put("cih7aFlag", bi.cih7aFlag.isChecked() ? "1" : "2");

        sA8a.put("cluster_no", MainApp.fc.getClusterNo());
        sA8a.put("hhno", MainApp.fc.getHhNo());

        sA8a.put("cih7a05y", bi.cih7a03y.getText().toString());

        sA8a.put("cih7a06m", bi.cih7a03m.getText().toString());

        sA8a.put("cih7a07a", bi.cih7a04a.isChecked() ? "1" : "0");
        sA8a.put("cih7a07b", bi.cih7a04b.isChecked() ? "2" : "0");
        sA8a.put("cih7a07c", bi.cih7a04c.isChecked() ? "3" : "0");
        sA8a.put("cih7a07d", bi.cih7a04d.isChecked() ? "4" : "0");
        sA8a.put("cih7a07e", bi.cih7a04e.isChecked() ? "5" : "0");
        sA8a.put("cih7a07f", bi.cih7a04f.isChecked() ? "6" : "0");
        sA8a.put("cih7a07g", bi.cih7a04g.isChecked() ? "7" : "0");
        sA8a.put("cih7a07h", bi.cih7a04h.isChecked() ? "8" : "0");
        sA8a.put("cih7a07i", bi.cih7a04i.isChecked() ? "9" : "0");
        sA8a.put("cih7a07j", bi.cih7a04j.isChecked() ? "10" : "0");
        sA8a.put("cih7a0796", bi.cih7a0496.isChecked() ? "96" : "0");
        sA8a.put("cih7a0796x", bi.cih7a0496x.getText().toString());
        sA8a.put("cih7a08", bi.cih7a05.getText().toString());
        sA8a.put("cih7a09", bi.cih7a06.getText().toString());

        MainApp.rc.setsA8A(String.valueOf(sA8a));

        // Set summary fields
        MainApp.sumc = MainApp.AddSummary(MainApp.fc, 1);

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

//        if (SectionA1Activity.editFormFlag) {
        if (!dataFlag) {
            Long updcount = db.addRecipient(MainApp.rc, 1);
            if (updcount != 0) {
                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Long updcount = db.addRecipient(MainApp.rc, 0);
            MainApp.rc.set_ID(String.valueOf(updcount));

            if (updcount != 0) {
                //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

                MainApp.rc.set_UID(
                        (MainApp.rc.getDeviceId() + MainApp.rc.get_ID()));
                db.updateRecepientID();

                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        //return true;
    }


}
