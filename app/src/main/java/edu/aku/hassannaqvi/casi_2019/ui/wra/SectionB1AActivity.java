package edu.aku.hassannaqvi.casi_2019.ui.wra;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONB1AModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.OutcomeContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionB1ABinding;
import edu.aku.hassannaqvi.casi_2019.other.DateUtils;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.household.SectionA2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

import static edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity.childisUnder2AndAlive;

public class SectionB1AActivity extends AppCompatActivity implements TextWatcher {

    static int childSerial = 1;
    private final long DELAY = 1000;
    ActivitySectionB1ABinding bi;
    DatabaseHelper db;
    @BindViews({R.id.nw217y, R.id.nw217m, R.id.nw217d})
    List<EditText> grpDate;
    Calendar date = Calendar.getInstance();
    //    long yearsBydob;
    double yearsBydob;

    String classPassName = "";
    String uid = "";
    OutcomeContract outcomeCC;
    Boolean backPressed = false;
    Boolean frontPressed = false;
    Boolean firstTimePressed = false;
    JSONB1AModelClass jsonB1A;

    Boolean twinFlag = false;

    //static int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_b1_a);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b1_a);
        ButterKnife.bind(this);
        db = new DatabaseHelper(this);
        bi.setCallback(this);
        setupViews();

        bi.textName.setText("Selected Woman : " + SectionB1Activity.wraName);
        int totalPregnancy = MainApp.totalPregnancy;
        if (MainApp.currentlyPregnant == 1) {
            totalPregnancy = MainApp.totalPregnancy - 1;
        }
        bi.count.setText("Pregnancy No " + MainApp.count + " out of " + totalPregnancy);

        for (EditText ed : grpDate) {
            ed.addTextChangedListener(this);
        }

        twinFlag = getIntent().getBooleanExtra("flag", false);

        if (twinFlag) {
//            Boolean type = getIntent().getExtras().getBoolean("type");
            String datey = getIntent().getExtras().getString("datey");
            String datem = getIntent().getExtras().getString("datem");
            String dated = getIntent().getExtras().getString("dated");

//            if (type) {
            bi.nw217y.setText(datey);
            bi.nw217m.setText(datem);
            bi.nw217d.setText(dated);
            bi.nw218d.setChecked(true);
            bi.nw217y.setEnabled(false);
            bi.nw217d.setEnabled(false);
            bi.nw217m.setEnabled(false);
            bi.nw218a.setEnabled(false);
            bi.nw218b.setEnabled(false);
            bi.nw218c.setEnabled(false);
            bi.nw218e.setEnabled(false);
            bi.nw218f.setEnabled(false);
            /*} else {
                bi.nw217y.setText(null);
                bi.nw217d.setText(null);
                bi.nw217m.setText(null);
                bi.nw218d.setChecked(false);
                bi.nw217y.setEnabled(true);
                bi.nw217d.setText(null);
                bi.nw217m.setText(null);
                bi.nw218a.setEnabled(true);
                bi.nw218b.setEnabled(true);
                bi.nw218c.setEnabled(true);
                bi.nw218e.setEnabled(true);
                bi.nw218f.setEnabled(true);

            }*/

//            childSerial++;
        } else {
//            childSerial = 1;
        }

        AutoPopulate();

    }

    public void AutoPopulate() {

        Collection<OutcomeContract> outcomeContracts = db.getPressedOutcome();

        for (OutcomeContract outcomeContract : outcomeContracts) {
            if (outcomeContract.getB1aPregSNo().equals(String.valueOf(MainApp.count))) {

                jsonB1A = JSONUtilClass.getModelFromJSON(outcomeContract.getsB1A(), JSONB1AModelClass.class);

                outcomeCC = outcomeContract;

                bi.nw217y.setText(jsonB1A.getnw217y());
                bi.nw217m.setText(jsonB1A.getnw217m());
                bi.nw217d.setText(jsonB1A.getnw217d());

                if (!jsonB1A.getnw218().equals("0")) {
                    bi.nw218.check(
                            jsonB1A.getnw218().equals("1") ? bi.nw218a.getId() :
                                    jsonB1A.getnw218().equals("2") ? bi.nw218b.getId() :
                                            jsonB1A.getnw218().equals("3") ? bi.nw218c.getId() :
                                                    jsonB1A.getnw218().equals("4") ? bi.nw218d.getId() :
                                                            jsonB1A.getnw218().equals("5") ? bi.nw218e.getId() :
                                                                    bi.nw218f.getId()
                    );
                }

//                childSerial = Integer.valueOf(jsonB1A.getnw219());

                if (jsonB1A.getnw219().equals("2")) {
                    bi.nw218d.setChecked(true);
                    bi.nw217y.setEnabled(false);
                    bi.nw217d.setEnabled(false);
                    bi.nw217m.setEnabled(false);
                    bi.nw218a.setEnabled(false);
                    bi.nw218b.setEnabled(false);
                    bi.nw218c.setEnabled(false);
                    bi.nw218e.setEnabled(false);
                    bi.nw218f.setEnabled(false);

                    frontPressed = true;

                    if (!jsonB1A.getnw220().equals("0")) {
                        bi.nw220.check(
                                jsonB1A.getnw220().equals("1") ? bi.nw220a.getId() : jsonB1A.getnw220().equals("2") ? bi.nw220b.getId() : bi.nw220c.getId()
                        );
                    }

                    bi.nw221y.setText(jsonB1A.getnw221y());
                    bi.nw221m.setText(jsonB1A.getnw221m());
                    bi.nw221d.setText(jsonB1A.getnw221d());

                    bi.nw222y.setText(jsonB1A.getnw222y());
                    bi.nw222m.setText(jsonB1A.getnw222m());
                    bi.nw222d.setText(jsonB1A.getnw222d());

                    if (jsonB1A.getnw217Flag().equals("1")) {
                        bi.nw217Flag.setChecked(true);
                    }

                    bi.nw217Flag.setVisibility(View.VISIBLE);

                    break;

                }

                if (!twinFlag) {

                    frontPressed = true;

                    if (!jsonB1A.getnw220().equals("0")) {
                        bi.nw220.check(
                                jsonB1A.getnw220().equals("1") ? bi.nw220a.getId() : jsonB1A.getnw220().equals("2") ? bi.nw220b.getId() : bi.nw220c.getId()
                        );
                    }

                    bi.nw221y.setText(jsonB1A.getnw221y());
                    bi.nw221m.setText(jsonB1A.getnw221m());
                    bi.nw221d.setText(jsonB1A.getnw221d());

                    bi.nw222y.setText(jsonB1A.getnw222y());
                    bi.nw222m.setText(jsonB1A.getnw222m());
                    bi.nw222d.setText(jsonB1A.getnw222d());

                    if (jsonB1A.getnw217Flag().equals("1")) {
                        bi.nw217Flag.setChecked(true);
                    }

                    bi.nw217Flag.setVisibility(View.VISIBLE);

                    break;
                }

            }

        }

    }

    public void setupViews() {

        //bi.nw217.setManager(getSupportFragmentManager());
        //bi.nw217.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()));

        bi.nw220.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.nw220a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpnw221, true);
                    ClearClass.ClearAllFields(bi.fldGrpnw222, false);

                    ClearClass.ClearAllFields(bi.fldGrnw221alive, true);
                    ClearClass.ClearAllFields(bi.fldGrnw221death, false);
                    //bi.fldGrpnw221.setVisibility(View.VISIBLE);
                    /*bi.fldGrpnw222.setVisibility(View.GONE);
                    bi.nw222d.setText(null);
                    bi.nw222m.setText(null);
                    bi.nw222y.setText(null);*/

                } else {
                    ClearClass.ClearAllFields(bi.fldGrpnw221, false);
                    ClearClass.ClearAllFields(bi.fldGrpnw222, true);

                    ClearClass.ClearAllFields(bi.fldGrnw221alive, false);
                    ClearClass.ClearAllFields(bi.fldGrnw221death, true);
                    /*bi.fldGrpnw221.setVisibility(View.GONE);
                    bi.fldGrpnw222.setVisibility(View.VISIBLE);
                    bi.nw221d.setText(null);
                    bi.nw221m.setText(null);
                    bi.nw221y.setText(null);*/
                }
            }
        });

        bi.nw218.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.nw218a.isChecked() || bi.nw218b.isChecked() || bi.nw218e.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpnb1a03, false);
                    ClearClass.ClearAllFields(bi.fldGrnw221alive, false);
                    ClearClass.ClearAllFields(bi.fldGrnw221death, false);

                } else {
                    ClearClass.ClearAllFields(bi.fldGrpnb1a03, true);
                    ClearClass.ClearAllFields(bi.fldGrnw221alive, true);
                    ClearClass.ClearAllFields(bi.fldGrnw221death, true);
                }

            }
        });


    }

    public void BtnContinue() {


        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                MainApp.nuCount = 1;

                if (bi.nw218d.isChecked() && !twinFlag) {

                    Intent i = new Intent(this, SectionB1AActivity.class);
                    i.putExtra("datey", bi.nw217y.getText().toString());
                    i.putExtra("datem", bi.nw217m.getText().toString());
                    i.putExtra("dated", bi.nw217d.getText().toString());
                    i.putExtra("flag", true);
                    startActivity(i);

                } else {
                    MainApp.count++;
                    int totalPregnancy = MainApp.totalPregnancy;
                    if (MainApp.currentlyPregnant == 1) {
                        totalPregnancy = MainApp.totalPregnancy - 1;
                    }

                    if (MainApp.count > totalPregnancy) {

                        MainApp.count = 1;

//                        if (yearsBydob <= 2 && MainApp.status > 0) {
                        if (childisUnder2AndAlive) {
                            startActivity(new Intent(this, SectionB2Activity.class));
                        } else {
                            if (SectionB1Activity.editWRAFlag) {
                                /*if (!db.getNutritionCount()) {
                                    startActivityForResult(new Intent(this, SectionB6Activity.class)
                                            .putExtra("backPressed", classPassName.equals(SectionB6Activity.class.getName())), 1);
                                } else*/
                                if (MainApp.mc.getsB6().equals("1")) {
                                    startActivityForResult(new Intent(this, SectionB6Activity.class)
                                            .putExtra("backPressed", classPassName.equals(SectionB6Activity.class.getName())), 1);

                                } else {
                                    finish();
                                    startActivity(new Intent(this, ViewMemberActivity.class)
                                            .putExtra("flagEdit", false)
                                            .putExtra("comingBack", true)
                                            .putExtra("cluster", MainApp.mc.getCluster())
                                            .putExtra("hhno", MainApp.mc.getHhno())
                                    );
                                }
                            } else {
                                /*if (SectionB1Activity.WRAcounter == MainApp.mwra.size()
                                        &&
                                        MainApp.B6Flag) {*/
                                if (MainApp.mc.getsB6().equals("1")) {
                                    startActivityForResult(new Intent(this, SectionB6Activity.class)
                                            .putExtra("backPressed", classPassName.equals(SectionB6Activity.class.getName())), 1);
                                } else {
                                    startActivity(new Intent(this, MotherEndingActivity.class)
                                            .putExtra("complete", true));
                                }
                            }
                        }

                    } else {
                        startActivityForResult(new Intent(this, SectionB1AActivity.class)
                                .putExtra("backPressed", classPassName.equals(SectionB1AActivity.class.getName())), 1);
                    }

                }
            }
        }

        //startActivity(new Intent(this, SectionB2Activity.class));
    }

    public void BtnEnd() {
        if (SectionB1Activity.editWRAFlag) {
            finish();
            startActivity(new Intent(this, ViewMemberActivity.class)
                    .putExtra("flagEdit", false)
                    .putExtra("comingBack", true)
                    .putExtra("cluster", MainApp.mc.getCluster())
                    .putExtra("hhno", MainApp.mc.getHhno())
            );
        } else {
            MainApp.endActivityMother(this, this, false);
        }
    }

    private boolean ValidateForm() {



        if (!ValidatorClass.EmptyTextBox(this, bi.nw217d, getString(R.string.nw217))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.nw217m, getString(R.string.nw217))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.nw217y, getString(R.string.nw217))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.nw217d, 1, 31, 98, getString(R.string.nw217), " day")) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.nw217m, 1, 12, 98, getString(R.string.nw217), " month")) {
            return false;
        }


        if (!ValidatorClass.RangeTextBox(this, bi.nw217y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), getString(R.string.nw217), " years")) {
            return false;
        }

        Calendar today = Calendar.getInstance();
        if (date.after(today)) {
            if (!ValidatorClass.RangeTextBoxforDate(this, bi.nw217d, 1, DateUtils.getCurrentDate(), 98, "Date can not be more than today")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBoxforDate(this, bi.nw217m, 1, DateUtils.getCurrentMonth(), 98, "Month can not be more than current month")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBoxforDate(this, bi.nw217y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), "Year can not be more than current year")) {
                return false;
            }

        }


        if (!ValidatorClass.EmptyRadioButton(this, bi.nw218, bi.nw218a, getString(R.string.nw218))) {
            return false;
        }

        if (bi.nw218c.isChecked() || bi.nw218f.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.nw220, bi.nw220a, getString(R.string.nw220))) {
                return false;
            }

            if (bi.nw220a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.nw221y, getString(R.string.nw221) + " - " + getString(R.string.year))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.nw221m, getString(R.string.nw221) + " - " + getString(R.string.month))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.nw221d, getString(R.string.nw221) + " - " + getString(R.string.day))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.nw221y, 0, 4, getString(R.string.nw221), " years")) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.nw221m, 0, 11, getString(R.string.nw221), " months")) {
                    return false;
                }


                if (!ValidatorClass.RangeTextBox(this, bi.nw221d, 0, 29, getString(R.string.nw221), " days")) {
                    return false;
                }

                if (bi.nw221y.getText().toString().equals("0") && bi.nw221m.getText().toString().equals("0") && bi.nw221d.getText().toString().equals("0")) {
                    Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.nw221), Toast.LENGTH_LONG).show();
                    bi.nw221y.setError("All can not be zero");
                    bi.nw221m.setError("All can not be zero");
                    bi.nw221d.setError("All can not be zero");
                    Log.i(SectionA2Activity.class.getSimpleName(), "nw221" + ": This data is Required!");
                    return false;
                } else {
                    bi.nw221y.setError(null);
                    bi.nw221m.setError(null);
                    bi.nw221d.setError(null);
                }
               /* String myDate =bi.nw217y+"/"+bi.nw217m+"/"+bi.nw217d;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millis = date.getTime();
                AgeModel ageModel  =  DateUtils.calculateAge(millis);
                bi.nw221d.setText(ageModel.getdays());
                bi.nw221m.setText(ageModel.getmonths());
                bi.nw221y.setText(ageModel.getyears());
*/
            } else if (bi.nw220b.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.nw222y, getString(R.string.nw222) + " - " + getString(R.string.year))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.nw222m, getString(R.string.nw222) + " - " + getString(R.string.month))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.nw222d, getString(R.string.nw222) + " - " + getString(R.string.day))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.nw222y, 0, 4, getString(R.string.nw222), " years")) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.nw222m, 0, 11, getString(R.string.nw222), " months")) {
                    return false;
                }


                if (!ValidatorClass.RangeTextBox(this, bi.nw222d, 0, 29, getString(R.string.nw222), " days")) {
                    return false;
                }

                if (bi.nw222y.getText().toString().equals("0") && bi.nw222m.getText().toString().equals("0") && bi.nw222d.getText().toString().equals("0")) {
                    Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.nw222), Toast.LENGTH_LONG).show();
                    bi.nw222y.setError("All can not be zero");
                    bi.nw222m.setError("All can not be zero");
                    bi.nw222d.setError("All can not be zero");
                    Log.i(SectionA2Activity.class.getSimpleName(), "nw222" + ": This data is Required!");
                    return false;
                } else {
                    bi.nw222y.setError(null);
                    bi.nw222m.setError(null);
                    bi.nw222d.setError(null);
                }
            }
        }


        return true;
    }

    private void SaveDraft() throws JSONException {

      /*  if (!bi.nw217y.getText().toString().isEmpty() && !bi.nw217m.getText().toString().isEmpty() && !bi.nw217d.getText().toString().isEmpty()) {
            if (!bi.nw217d.getText().toString().equals("98") && !bi.nw217m.getText().toString().equals("98")) {
                date = DateUtils.getCalendarDate(bi.nw217d.getText().toString(), bi.nw217m.getText().toString(), bi.nw217y.getText().toString());

                yearsBydob = DateUtils.ageInYearByDOBdouble(date);

            } else {
                //date = bi.nw217d.getText().toString() + "-" bi.nw21
                yearsBydob = DateUtils.ageInYearByDOB(bi.nw217y.getText().toString());
            }


        }*/
        MainApp.oc = new OutcomeContract();
        JSONObject sB1a = new JSONObject();

        if (!backPressed && !frontPressed) {
            if (SectionB1Activity.editWRAFlag) {
                MainApp.oc.setDevicetagID(MainApp.mc.getDevicetagID());
                MainApp.oc.setFormDate(MainApp.mc.getFormDate());
                MainApp.oc.setUser(MainApp.mc.getUser());
                MainApp.oc.setDeviceId(MainApp.mc.getDeviceId());
                MainApp.oc.setApp_ver(MainApp.mc.getApp_ver());
                MainApp.oc.set_UUID(MainApp.mc.get_UUID());
                MainApp.oc.setMUID(MainApp.mc.get_UID());
                MainApp.oc.setFMUID(MainApp.mc.getFMUID());

                MainApp.oc.setB1aPregSNo(String.valueOf(MainApp.count));

                sB1a.put("cluster_no", MainApp.mc.getCluster());
                sB1a.put("hhno", MainApp.mc.getHhno());


            } else {
                MainApp.oc.setDevicetagID(MainApp.fc.getDevicetagID());
                MainApp.oc.setFormDate(MainApp.fc.getFormDate());
                MainApp.oc.setUser(MainApp.fc.getUser());
                MainApp.oc.setDeviceId(MainApp.fc.getDeviceID());
                MainApp.oc.setApp_ver(MainApp.fc.getAppversion());
                MainApp.oc.set_UUID(MainApp.fc.getUID());
                MainApp.oc.setMUID(MainApp.mc.get_UID());
                MainApp.oc.setFMUID(MainApp.mc.getFMUID());

                MainApp.oc.setB1aPregSNo(String.valueOf(MainApp.count));

                sB1a.put("cluster_no", MainApp.fc.getClusterNo());
                sB1a.put("hhno", MainApp.fc.getHhNo());


            }

        } else {
            MainApp.oc.setUpdatedate(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

            if (frontPressed) {
                MainApp.oc.set_UID(outcomeCC.get_UID());
            } else if (backPressed) {
                MainApp.oc.set_UID(uid);
            }

            if (SectionB1Activity.editWRAFlag && !frontPressed) {
                sB1a.put("edit_updatedate_ciw1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
                sB1a.put("cluster_no", jsonB1A.getCluster_no());
                sB1a.put("hhno", jsonB1A.getHhno());

            } else if (SectionB1Activity.editWRAFlag) {
                sB1a.put("cluster_no", jsonB1A.getCluster_no());
                sB1a.put("hhno", jsonB1A.getHhno());

            } else {
                sB1a.put("cluster_no", MainApp.fc.getClusterNo());
                sB1a.put("hhno", MainApp.fc.getHhNo());
            }

        }

        if ((bi.nw218c.isChecked() || bi.nw218d.isChecked() || bi.nw218f.isChecked()) && yearsBydob <= 2) {
            childisUnder2AndAlive = true;
        }

        sB1a.put("nw217Flag", bi.nw217Flag.isChecked() ? "1" : "2");
        sB1a.put("wra_lno", MainApp.mc.getB1SerialNo());

        sB1a.put("nw219", twinFlag ? "2" : "1");

        sB1a.put("nw217y", bi.nw217y.getText().toString());
        sB1a.put("nw217m", bi.nw217m.getText().toString());
        sB1a.put("nw217d", bi.nw217d.getText().toString());

        sB1a.put("nw218", bi.nw218a.isChecked() ? "1"
                : bi.nw218b.isChecked() ? "2"
                : bi.nw218c.isChecked() ? "3"
                : bi.nw218d.isChecked() ? "4"
                : bi.nw218e.isChecked() ? "5"
                : bi.nw218f.isChecked() ? "6"
                : "0");

        if (!getIntent().getBooleanExtra("flag", false)) {
            MainApp.outcome = bi.nw218.indexOfChild(findViewById(bi.nw218.getCheckedRadioButtonId())) + 1;
        }

        sB1a.put("nw220", bi.nw220a.isChecked() ? "1"
                : bi.nw220b.isChecked() ? "2"
                : bi.nw220c.isChecked() ? "3"
                : "0");
        if (bi.nw218c.isChecked() || bi.nw218d.isChecked() || bi.nw218f.isChecked()) {
            MainApp.status++;
        }

        sB1a.put("nw221y", bi.nw221y.getText().toString());
        sB1a.put("nw221m", bi.nw221m.getText().toString());
        sB1a.put("nw221d", bi.nw221d.getText().toString());

        sB1a.put("nw222y", bi.nw222y.getText().toString());
        sB1a.put("nw222m", bi.nw222m.getText().toString());
        sB1a.put("nw222d", bi.nw222d.getText().toString());

        if (backPressed) {
            sB1a.put("backPressed", backPressed);
        } else if (frontPressed) {
            sB1a.put("frontPressed", frontPressed);
        }

        MainApp.oc.setsB1A(String.valueOf(sB1a));

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        /*Long updcount = db.addOutcome(MainApp.oc, 0);
        MainApp.oc.set_ID(String.valueOf(updcount));

        if (updcount != 0) {


            MainApp.oc.set_UID(
                    (MainApp.oc.getDeviceId() + MainApp.oc.get_ID()));
            db.updateOutcomeID();

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if (!backPressed && !frontPressed) {
            Long updcount = db.addOutcome(MainApp.oc, 0);
            MainApp.oc.set_ID(String.valueOf(updcount));

            if (updcount != 0) {
                MainApp.oc.set_UID(
                        (MainApp.oc.getDeviceId() + MainApp.oc.get_ID()));
                db.updateOutcomeID();

                uid = MainApp.oc.getDeviceId() + MainApp.oc.get_ID();

                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Long updcount = db.addOutcome(MainApp.oc, 1);
            if (updcount != 0) {
                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (!bi.nw217y.getText().toString().isEmpty() && !bi.nw217m.getText().toString().isEmpty()
                && !bi.nw217d.getText().toString().isEmpty()) {
            if (!bi.nw217d.getText().toString().equals("98") && !bi.nw217m.getText().toString().equals("98")) {
                date = DateUtils.getCalendarDate(bi.nw217d.getText().toString(), bi.nw217m.getText().toString(), bi.nw217y.getText().toString());

                yearsBydob = DateUtils.ageInYearByDOBdouble(date);

            } else {
                //date = bi.nw217d.getText().toString() + "-" bi.nw21
                yearsBydob = DateUtils.ageInYearByDOB(bi.nw217y.getText().toString());
            }


        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                classPassName = data.getStringExtra("backPressedClass");
            } else {
                classPassName = "";
            }
        }
    }

    @Override
    public void onBackPressed() {

        try {
            SaveDraft();
            UpdateDB();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!twinFlag) {
            MainApp.count--;
        }

        Intent intent = new Intent();
        intent.putExtra("backPressedClass", SectionB1AActivity.class.getName());
        setResult(RESULT_OK, intent);

        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (firstTimePressed) {
            backPressed = true;
        }

        firstTimePressed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (firstTimePressed && !frontPressed) {
            backPressed = false;
            if (!SectionB1Activity.editWRAFlag) {
                firstTimePressed = false;
            }
        }
    }

}

