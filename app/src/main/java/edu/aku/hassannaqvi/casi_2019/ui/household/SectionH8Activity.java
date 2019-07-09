package edu.aku.hassannaqvi.casi_2019.ui.household;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import butterknife.BindViews;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONH8ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.DeceasedContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionH8Binding;
import edu.aku.hassannaqvi.casi_2019.other.DateUtils;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionH8Activity extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    static int counter = 1;
    static int deccounter = 0;
    private final long DELAY = 1000;
    ActivitySectionH8Binding bi;
    public TextWatcher age = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!bi.cih808d.getText().toString().isEmpty() && !bi.cih808m.getText().toString().isEmpty() && !bi.cih808y.getText().toString().isEmpty()) {

                if (!bi.cih808d.getText().toString().equals("98")) {

                    dob = DateUtils.getCalendarDate(bi.cih808d.getText().toString(), bi.cih808m.getText().toString(),
                            bi.cih808y.getText().toString());

                    ageInYears = DateUtils.ageInYearByDOB(dob);


                } else if (!bi.cih808d.getText().toString().equals("98")) {
                    dob = DateUtils.getCalendarDate(bi.cih808m.getText().toString(),
                            bi.cih808y.getText().toString());
                    ageInYears = DateUtils.ageInYearByDOB(dob);
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

           /* timer.cancel();
            timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    formValidation();
                                }
                                //}
                            });

                        }
                    },
                    DELAY
            );
*/
        }
    };
    FamilyMembersContract family;
    long ageInYears = 0;
    DatabaseHelper db;
    Calendar dob = Calendar.getInstance();
    @BindViews({R.id.cih808d, R.id.cih808m, R.id.cih808y})
    List<EditText> grpdob;
    List<String> mothersList, fathersList;
    List<String> mothersSerials, fathersSerials;
    Map<String, String> mothersMap, fathersMap;
    JSONH8ModelClass jsonH8;

    Boolean dataFlag = true;
    private Timer timer = new Timer();

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


                finish();

//                if (counter == SectionA5Activity.deceasedCounter) {
                if (counter == SectionH8infoActivity.deceasedCounter) {
                    counter = 1;

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

                        startActivity(new Intent(this, ViewMemberActivity.class).putExtra("activity", 2));
                    }

                } else {
                    counter++;
                    startActivity(new Intent(this, SectionH8Activity.class));
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        if (SectionA1Activity.editFormFlag) {
            startActivity(new Intent(this, ViewMemberActivity.class)
                    .putExtra("flagEdit", false)
                    .putExtra("comingBack", true)
                    .putExtra("cluster", MainApp.fc.getClusterNo())
                    .putExtra("hhno", MainApp.fc.getHhNo())
            );
        } else {
            MainApp.endActivity(this, this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_h8);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h8);
        ButterKnife.bind(this);
        bi.setCallback(this);

        this.setTitle(getResources().getString(R.string.cih8heading));

//        Validation Boolean
        MainApp.validateFlag = false;

        for (EditText ed : grpdob) {
            ed.addTextChangedListener(age);
        }

        // Setting dropdowns

        mothersList = new ArrayList<>();
        mothersSerials = new ArrayList<>();
        mothersMap = new HashMap<>();
        family = new FamilyMembersContract();

        mothersList.add("....");
        mothersList.add("N/A");
        mothersSerials.add("0");
        mothersMap.put("N/A_0", "00");

        fathersList = new ArrayList<>();
        fathersSerials = new ArrayList<>();
        fathersMap = new HashMap<>();

        fathersList.add("....");
        fathersList.add("N/A");
        fathersSerials.add("0");
        fathersMap.put("N/A_0", "00");

        for (FamilyMembersContract mem : MainApp.members_f_m) {
            if (mem.getna204().equals("1")) {
                fathersList.add(mem.getName());
                fathersSerials.add(mem.getSerialNo());
                fathersMap.put(mem.getName() + "_" + mem.getSerialNo(), mem.getSerialNo());
            } else {
                mothersList.add(mem.getName());
                mothersSerials.add(mem.getSerialNo());
                mothersMap.put(mem.getName() + "_" + mem.getSerialNo(), mem.getSerialNo());
            }
        }

        bi.cih804.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, mothersList));
        bi.cih805.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, fathersList));

        bi.cih803.addTextChangedListener(this);
        bi.cih806.setOnCheckedChangeListener(this);
//        bi.cih809.addTextChangedListener(this);
        bi.cih809.setOnCheckedChangeListener(this);


        bi.cih807y.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!bi.cih807y.getText().toString().isEmpty()) {
                    if (Integer.valueOf(bi.cih807y.getText().toString()) < 5) {
                        bi.fldGrpfid.setVisibility(View.VISIBLE);
                        bi.fldGrpmid.setVisibility(View.VISIBLE);
                        bi.fldGrpcih8ms.setVisibility(View.GONE);
                        bi.cih8ms.clearCheck();
                    } else {
                        bi.fldGrpfid.setVisibility(View.GONE);
                        bi.fldGrpmid.setVisibility(View.GONE);
                        bi.fldGrpcih8ms.setVisibility(View.VISIBLE);
                        bi.cih804.setSelection(1);
                        bi.cih805.setSelection(1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

               /* timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        formValidation();
                                    }
                                    //}
                                });

                            }
                        },
                        DELAY
                );*/


            }
        });

//        bi.txtCounter.setText("Count " + counter + " out of " + SectionA5Activity.deceasedCounter);
        bi.txtCounter.setText("Count " + counter + " out of " + SectionH8infoActivity.deceasedCounter);


        if (!bi.cih807y.getText().toString().isEmpty()) {
            if (Integer.valueOf(bi.cih807y.getText().toString()) < 2 && ageInYears < 2) {
                MainApp.childUnder2Check.add(family);
            }
        }

        db = new DatabaseHelper(this);

        if (SectionA1Activity.editFormFlag) {
            AutoPopulateFields();
        }

    }

    private void AutoPopulateFields() {

        Collection<DeceasedContract> deceasedContracts = db.getPressedDeceasedMembers();

        for (DeceasedContract deceasedContract : deceasedContracts) {

            jsonH8 = JSONUtilClass.getModelFromJSON(deceasedContract.getsH8(), JSONH8ModelClass.class);

            if (jsonH8.getSerial().equals(String.valueOf(counter))) {

                dataFlag = false;

                MainApp.dc = deceasedContract;

                bi.cih804.setVisibility(View.GONE);
                bi.cih805.setVisibility(View.GONE);

                bi.cih804a.setVisibility(View.VISIBLE);
                bi.cih804a.setText(jsonH8.getNh804().toUpperCase());
                bi.cih805a.setVisibility(View.VISIBLE);
                bi.cih805a.setText(jsonH8.getNh805().toUpperCase());

                bi.cih803.setText(jsonH8.getNh803());
                bi.cih803.setVisibility(View.GONE);

                if (!jsonH8.getNh806().equals("0")) {
                    bi.cih806.check(
                            jsonH8.getNh806().equals("1") ? bi.cih806a.getId()
                                    : bi.cih806b.getId());
                }

                if (!jsonH8.getNh8ms().equals("0")) {
                    bi.cih8ms.check(
                            jsonH8.getNh8ms().equals("1") ? bi.cih8msa.getId()
                                    : jsonH8.getNh8ms().equals("2") ? bi.cih8msb.getId()
                                    : jsonH8.getNh8ms().equals("3") ? bi.cih8msc.getId()
                                    : jsonH8.getNh8ms().equals("4") ? bi.cih8msd.getId()
                                    : bi.cih8mse.getId()
                    );
                } else {
                    bi.cih805a.setVisibility(View.GONE);
                    bi.cih804a.setVisibility(View.GONE);
                }

                bi.cih807y.setText(jsonH8.getNh807y());
                bi.cih807m.setText(jsonH8.getNh807m());
                bi.cih807d.setText(jsonH8.getNh807d());
                bi.cih808y.setText(jsonH8.getNh808y());
                bi.cih808m.setText(jsonH8.getNh808m());
                bi.cih808d.setText(jsonH8.getNh808d());

                if (!jsonH8.getNh809().equals("0")) {
                    bi.cih809.check(
                            jsonH8.getNh809().equals("1") ? bi.cih809a.getId()
                                    : jsonH8.getNh809().equals("2") ? bi.cih809b.getId()
                                    : jsonH8.getNh809().equals("3") ? bi.cih809c.getId()
                                    : bi.cih80996.getId()
                    );
                }

                if (jsonH8.getNh8Flag().equals("1")) {
                    bi.cih8Flag.setChecked(true);
                }

                bi.cih8Flag.setVisibility(View.VISIBLE);

                break;
            }
        }

        if (dataFlag) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    SectionH8Activity.this);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder
                    .setMessage("No Deceased record found against counter no:" + counter + ".\n" +
                            "Processed to next section?")
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

//                                    if (counter == SectionA5Activity.deceasedCounter) {
                                    if (counter == SectionH8infoActivity.deceasedCounter) {

                                        counter = 1;

                                        startActivity(new Intent(getApplicationContext(), ViewMemberActivity.class)
                                                .putExtra("flagEdit", false)
                                                .putExtra("comingBack", true)
                                                .putExtra("cluster", MainApp.fc.getClusterNo())
                                                .putExtra("hhno", MainApp.fc.getHhNo())
                                        );

                                    } else {
                                        counter++;
                                        startActivity(new Intent(getApplicationContext(), SectionH8Activity.class));
                                    }

                                }
                            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }

    }

    private void SaveDraft() throws JSONException {


        JSONObject sA2 = new JSONObject();

        if (!SectionA1Activity.editFormFlag) {
            MainApp.dc = new DeceasedContract();
            MainApp.dc.setDevicetagID(MainApp.fc.getDevicetagID());
            MainApp.dc.setFormDate(MainApp.fc.getFormDate());
            MainApp.dc.setUser(MainApp.fc.getUser());
            MainApp.dc.setDeviceID(MainApp.fc.getDeviceID());
            MainApp.dc.setAppversion(MainApp.fc.getAppversion());
            MainApp.dc.setUUID(MainApp.fc.getUID());

            sA2.put("cih804", bi.cih804.getSelectedItem().toString());
            sA2.put("cih805", bi.cih805.getSelectedItem().toString());

            sA2.put("wra_lno", mothersMap.get(bi.cih804.getSelectedItemPosition()));
            sA2.put("f_lno", fathersMap.get(bi.cih805.getSelectedItemPosition()));

        } else {
            sA2.put("edit_updatedate_cih8", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

            sA2.put("cih804", jsonH8.getNh804());
            sA2.put("cih805", jsonH8.getNh805());

            sA2.put("wra_lno", jsonH8.getMwraSerial());
            sA2.put("f_lno", jsonH8.getfSerial());
        }

        sA2.put("cluster_no", MainApp.fc.getClusterNo());
        sA2.put("hhno", MainApp.fc.getHhNo());

        sA2.put("serial", String.valueOf(counter));

        sA2.put("cih8Flag", bi.cih8Flag.isChecked() ? "1" : "2");

        sA2.put("cih803", bi.cih803.getText().toString());

        sA2.put("cih806", bi.cih806a.isChecked() ? "1" : bi.cih806b.isChecked() ? "2" : "0");
        sA2.put("cih8ms", bi.cih8msa.isChecked() ? "1"
                : bi.cih8msb.isChecked() ? "2"
                : bi.cih8msc.isChecked() ? "3"
                : bi.cih8msd.isChecked() ? "4"
                : bi.cih8mse.isChecked() ? "5"
                : "0");
        sA2.put("cih807y", bi.cih807y.getText().toString());
        sA2.put("cih807m", bi.cih807m.getText().toString());
        sA2.put("cih807d", bi.cih807d.getText().toString());
        sA2.put("cih808d", bi.cih808d.getText().toString());
        sA2.put("cih808m", bi.cih808m.getText().toString());
        sA2.put("cih808y", bi.cih808y.getText().toString());
        sA2.put("cih809", bi.cih809a.isChecked() ? "1"
                : bi.cih809b.isChecked() ? "2"
                : bi.cih809c.isChecked() ? "3"
                : bi.cih80996.isChecked() ? "96"
                : "0");

        MainApp.dc.setsH8(String.valueOf(sA2));

        // Set summary fields
        MainApp.sumc = MainApp.AddSummary(MainApp.fc, 1);

        //
    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);

        if (SectionA1Activity.editFormFlag) {
            long updcount = db.addDeceasedMembers(MainApp.dc, 1);
            if (updcount != 0) {
                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            long updcount = db.addDeceasedMembers(MainApp.dc, 0);

            MainApp.dc.set_ID(String.valueOf(updcount));

            if (updcount != 0) {


                MainApp.dc.setUID(
                        (MainApp.dc.getDeviceID() + MainApp.dc.get_ID()));
                db.updateDeceasedMemberID();
                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }

    private boolean formValidation() {



        if (!ValidatorClass.EmptyTextBox(this, bi.cih803, getString(R.string.cih803))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cih806, bi.cih806a, getString(R.string.cih806))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.cih807y, getString(R.string.cih807))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.cih807y, 0, 95, getString(R.string.cih807), " years")) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.cih807m, getString(R.string.cih807))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.cih807m, 0, 11, getString(R.string.cih807), " months")) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.cih807d, getString(R.string.cih807))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.cih807d, 0, 29, getString(R.string.cih807), " days")) {
            return false;
        }


        if (bi.cih807y.getText().toString().equals("0") && bi.cih807m.getText().toString().equals("0")
                && bi.cih807d.getText().toString().equals("0")) {
            Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.na2age), Toast.LENGTH_LONG).show();
            bi.cih807y.setError("All can not be zero");
            bi.cih807m.setError("All can not be zero");
            bi.cih807d.setError("All can not be zero");
            Log.i(SectionH8Activity.class.getSimpleName(), "cih807" + ": This data is Required!");
        } else {
            bi.cih807y.setError(null);
            bi.cih807m.setError(null);
            bi.cih807d.setError(null);
        }


        if (Integer.valueOf(bi.cih807y.getText().toString()) < 5) {
            if (!SectionA1Activity.editFormFlag) {
                if (!ValidatorClass.EmptySpinner(this, bi.cih804, getString(R.string.cih804))) {
                    return false;
                }

                if (!ValidatorClass.EmptySpinner(this, bi.cih805, getString(R.string.cih805))) {
                    return false;
                }
            }
        } else {
            if (!ValidatorClass.EmptyRadioButton(this, bi.cih8ms, bi.cih8msa, getString(R.string.cih8ms))) {
                return false;
            }
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.cih808y, getString(R.string.cih808))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.cih808m, getString(R.string.cih808))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.cih808d, getString(R.string.cih808))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.cih808y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), getString(R.string.cih808), " year")) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.cih808m, 1, 12, 98, getString(R.string.cih808), " month")) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.cih808d, 1, 31, 98, getString(R.string.cih808), " day")) {
            return false;
        }


        Calendar today = Calendar.getInstance();
        if (dob.after(today)) {
            if (!ValidatorClass.RangeTextBoxforDate(this, bi.cih808d, 1, DateUtils.getCurrentDate(), 98, "Date can not be more than today")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBoxforDate(this, bi.cih808m, 1, DateUtils.getCurrentMonth(), 98, "Month can not be more than current Month")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBoxforDate(this, bi.cih808y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), 9998, "Year can not be more than current year")) {
                return false;
            }

        }

        return ValidatorClass.EmptyRadioButton(this, bi.cih809, bi.cih80996, getString(R.string.cih809));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {

        /*timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            public void run() {
                                formValidation();
                            }
                            //}
                        });

                    }
                },
                DELAY
        );
*/
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        formValidation();
    }
}
