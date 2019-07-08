package edu.aku.hassannaqvi.casi_2018.ui.household;

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
import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONH8ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.DeceasedContract;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionH8Binding;
import edu.aku.hassannaqvi.casi_2018.other.DateUtils;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionH8Activity extends AppCompatActivity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    static int counter = 1;
    static int deccounter = 0;
    private final long DELAY = 1000;
    ActivitySectionH8Binding bi;
    @BindViews({R.id.nh808d, R.id.nh808m, R.id.nh808y})
    List<EditText> grpdob;
    FamilyMembersContract family;
    long ageInYears = 0;
    DatabaseHelper db;
    Calendar dob = Calendar.getInstance();
    public TextWatcher age = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!bi.nh808d.getText().toString().isEmpty() && !bi.nh808m.getText().toString().isEmpty() && !bi.nh808y.getText().toString().isEmpty()) {

                if (!bi.nh808d.getText().toString().equals("98")) {

                    dob = DateUtils.getCalendarDate(bi.nh808d.getText().toString(), bi.nh808m.getText().toString(),
                            bi.nh808y.getText().toString());

                    ageInYears = DateUtils.ageInYearByDOB(dob);


                } else if (!bi.nh808d.getText().toString().equals("98")) {
                    dob = DateUtils.getCalendarDate(bi.nh808m.getText().toString(),
                            bi.nh808y.getText().toString());
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
    List<String> mothersList, fathersList;
    List<String> mothersSerials, fathersSerials;
    Map<String, String> mothersMap, fathersMap;
    JSONH8ModelClass jsonH8;

    Boolean dataFlag = true;
    private Timer timer = new Timer();

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

        this.setTitle(getResources().getString(R.string.nh8heading));

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

        bi.nh804.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, mothersList));
        bi.nh805.setAdapter(new ArrayAdapter<>(this, R.layout.item_style, fathersList));

        bi.nh803.addTextChangedListener(this);
        bi.nh806.setOnCheckedChangeListener(this);
//        bi.nh809.addTextChangedListener(this);
        bi.nh809.setOnCheckedChangeListener(this);


        bi.nh807y.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!bi.nh807y.getText().toString().isEmpty()) {
                    if (Integer.valueOf(bi.nh807y.getText().toString()) < 5) {
                        bi.fldGrpfid.setVisibility(View.VISIBLE);
                        bi.fldGrpmid.setVisibility(View.VISIBLE);
                        bi.fldGrpnh8ms.setVisibility(View.GONE);
                        bi.nh8ms.clearCheck();
                    } else {
                        bi.fldGrpfid.setVisibility(View.GONE);
                        bi.fldGrpmid.setVisibility(View.GONE);
                        bi.fldGrpnh8ms.setVisibility(View.VISIBLE);
                        bi.nh804.setSelection(1);
                        bi.nh805.setSelection(1);
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


        if (!bi.nh807y.getText().toString().isEmpty()) {
            if (Integer.valueOf(bi.nh807y.getText().toString()) < 2 && ageInYears < 2) {
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

                bi.nh804.setVisibility(View.GONE);
                bi.nh805.setVisibility(View.GONE);

                bi.nh804a.setVisibility(View.VISIBLE);
                bi.nh804a.setText(jsonH8.getNh804().toUpperCase());
                bi.nh805a.setVisibility(View.VISIBLE);
                bi.nh805a.setText(jsonH8.getNh805().toUpperCase());

                bi.nh803.setText(jsonH8.getNh803());
                bi.nh803.setVisibility(View.GONE);

                if (!jsonH8.getNh806().equals("0")) {
                    bi.nh806.check(
                            jsonH8.getNh806().equals("1") ? bi.nh806a.getId()
                                    : bi.nh806b.getId());
                }

                if (!jsonH8.getNh8ms().equals("0")) {
                    bi.nh8ms.check(
                            jsonH8.getNh8ms().equals("1") ? bi.nh8msa.getId()
                                    : jsonH8.getNh8ms().equals("2") ? bi.nh8msb.getId()
                                    : jsonH8.getNh8ms().equals("3") ? bi.nh8msc.getId()
                                    : jsonH8.getNh8ms().equals("4") ? bi.nh8msd.getId()
                                    : bi.nh8mse.getId()
                    );
                } else {
                    bi.nh805a.setVisibility(View.GONE);
                    bi.nh804a.setVisibility(View.GONE);
                }

                bi.nh807y.setText(jsonH8.getNh807y());
                bi.nh807m.setText(jsonH8.getNh807m());
                bi.nh807d.setText(jsonH8.getNh807d());
                bi.nh808y.setText(jsonH8.getNh808y());
                bi.nh808m.setText(jsonH8.getNh808m());
                bi.nh808d.setText(jsonH8.getNh808d());

                if (!jsonH8.getNh809().equals("0")) {
                    bi.nh809.check(
                            jsonH8.getNh809().equals("1") ? bi.nh809a.getId()
                                    : jsonH8.getNh809().equals("2") ? bi.nh809b.getId()
                                    : jsonH8.getNh809().equals("3") ? bi.nh809c.getId()
                                    : bi.nh80996.getId()
                    );
                }

                if (jsonH8.getNh8Flag().equals("1")) {
                    bi.nh8Flag.setChecked(true);
                }

                bi.nh8Flag.setVisibility(View.VISIBLE);

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
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sA2 = new JSONObject();

        if (!SectionA1Activity.editFormFlag) {
            MainApp.dc = new DeceasedContract();
            MainApp.dc.setDevicetagID(MainApp.fc.getDevicetagID());
            MainApp.dc.setFormDate(MainApp.fc.getFormDate());
            MainApp.dc.setUser(MainApp.fc.getUser());
            MainApp.dc.setDeviceID(MainApp.fc.getDeviceID());
            MainApp.dc.setAppversion(MainApp.fc.getAppversion());
            MainApp.dc.setUUID(MainApp.fc.getUID());

            sA2.put("nh804", bi.nh804.getSelectedItem().toString());
            sA2.put("nh805", bi.nh805.getSelectedItem().toString());

            sA2.put("wra_lno", mothersMap.get(bi.nh804.getSelectedItemPosition()));
            sA2.put("f_lno", fathersMap.get(bi.nh805.getSelectedItemPosition()));

        } else {
            sA2.put("edit_updatedate_nh8", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

            sA2.put("nh804", jsonH8.getNh804());
            sA2.put("nh805", jsonH8.getNh805());

            sA2.put("wra_lno", jsonH8.getMwraSerial());
            sA2.put("f_lno", jsonH8.getfSerial());
        }

        sA2.put("cluster_no", MainApp.fc.getClusterNo());
        sA2.put("hhno", MainApp.fc.getHhNo());

        sA2.put("serial", String.valueOf(counter));

        sA2.put("nh8Flag", bi.nh8Flag.isChecked() ? "1" : "2");

        sA2.put("nh803", bi.nh803.getText().toString());

        sA2.put("nh806", bi.nh806a.isChecked() ? "1" : bi.nh806b.isChecked() ? "2" : "0");
        sA2.put("nh8ms", bi.nh8msa.isChecked() ? "1"
                : bi.nh8msb.isChecked() ? "2"
                : bi.nh8msc.isChecked() ? "3"
                : bi.nh8msd.isChecked() ? "4"
                : bi.nh8mse.isChecked() ? "5"
                : "0");
        sA2.put("nh807y", bi.nh807y.getText().toString());
        sA2.put("nh807m", bi.nh807m.getText().toString());
        sA2.put("nh807d", bi.nh807d.getText().toString());
        sA2.put("nh808d", bi.nh808d.getText().toString());
        sA2.put("nh808m", bi.nh808m.getText().toString());
        sA2.put("nh808y", bi.nh808y.getText().toString());
        sA2.put("nh809", bi.nh809a.isChecked() ? "1"
                : bi.nh809b.isChecked() ? "2"
                : bi.nh809c.isChecked() ? "3"
                : bi.nh80996.isChecked() ? "96"
                : "0");

        MainApp.dc.setsH8(String.valueOf(sA2));

        // Set summary fields
        MainApp.sumc = MainApp.AddSummary(MainApp.fc, 1);

        //Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

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

        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (!ValidatorClass.EmptyTextBox(this, bi.nh803, getString(R.string.nh803))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.nh806, bi.nh806a, getString(R.string.nh806))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.nh807y, getString(R.string.nh807))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.nh807y, 0, 95, getString(R.string.nh807), " years")) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.nh807m, getString(R.string.nh807))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.nh807m, 0, 11, getString(R.string.nh807), " months")) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.nh807d, getString(R.string.nh807))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.nh807d, 0, 29, getString(R.string.nh807), " days")) {
            return false;
        }


        if (bi.nh807y.getText().toString().equals("0") && bi.nh807m.getText().toString().equals("0")
                && bi.nh807d.getText().toString().equals("0")) {
            Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.na2age), Toast.LENGTH_LONG).show();
            bi.nh807y.setError("All can not be zero");
            bi.nh807m.setError("All can not be zero");
            bi.nh807d.setError("All can not be zero");
            Log.i(SectionH8Activity.class.getSimpleName(), "nh807" + ": This data is Required!");
        } else {
            bi.nh807y.setError(null);
            bi.nh807m.setError(null);
            bi.nh807d.setError(null);
        }


        if (Integer.valueOf(bi.nh807y.getText().toString()) < 5) {
            if (!SectionA1Activity.editFormFlag) {
                if (!ValidatorClass.EmptySpinner(this, bi.nh804, getString(R.string.nh804))) {
                    return false;
                }

                if (!ValidatorClass.EmptySpinner(this, bi.nh805, getString(R.string.nh805))) {
                    return false;
                }
            }
        } else {
            if (!ValidatorClass.EmptyRadioButton(this, bi.nh8ms, bi.nh8msa, getString(R.string.nh8ms))) {
                return false;
            }
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.nh808y, getString(R.string.nh808))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.nh808m, getString(R.string.nh808))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.nh808d, getString(R.string.nh808))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.nh808y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), getString(R.string.nh808), " year")) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.nh808m, 1, 12, 98, getString(R.string.nh808), " month")) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.nh808d, 1, 31, 98, getString(R.string.nh808), " day")) {
            return false;
        }


        Calendar today = Calendar.getInstance();
        if (dob.after(today)) {
            if (!ValidatorClass.RangeTextBoxforDate(this, bi.nh808d, 1, DateUtils.getCurrentDate(), 98, "Date can not be more than today")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBoxforDate(this, bi.nh808m, 1, DateUtils.getCurrentMonth(), 98, "Month can not be more than current Month")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBoxforDate(this, bi.nh808y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), 9998, "Year can not be more than current year")) {
                return false;
            }

        }

        return ValidatorClass.EmptyRadioButton(this, bi.nh809, bi.nh80996, getString(R.string.nh809));
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
