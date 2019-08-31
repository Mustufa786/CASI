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
import edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD.SectionD2AActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.secWRAD.SectionD3AActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

import static edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity.childisUnder2AndAlive;

public class SectionB1AActivity extends AppCompatActivity implements TextWatcher {

    static int childSerial = 1;
    private final long DELAY = 1000;
    ActivitySectionB1ABinding bi;
    DatabaseHelper db;
    @BindViews({R.id.ciw217y, R.id.ciw217m, R.id.ciw217d})
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
            bi.ciw217y.setText(datey);
            bi.ciw217m.setText(datem);
            bi.ciw217d.setText(dated);
            bi.ciw218d.setChecked(true);
            bi.ciw217y.setEnabled(false);
            bi.ciw217d.setEnabled(false);
            bi.ciw217m.setEnabled(false);
            bi.ciw218a.setEnabled(false);
            bi.ciw218b.setEnabled(false);
            bi.ciw218c.setEnabled(false);
            bi.ciw218e.setEnabled(false);
            bi.ciw218f.setEnabled(false);
            /*} else {
                bi.ciw217y.setText(null);
                bi.ciw217d.setText(null);
                bi.ciw217m.setText(null);
                bi.ciw218d.setChecked(false);
                bi.ciw217y.setEnabled(true);
                bi.ciw217d.setText(null);
                bi.ciw217m.setText(null);
                bi.ciw218a.setEnabled(true);
                bi.ciw218b.setEnabled(true);
                bi.ciw218c.setEnabled(true);
                bi.ciw218e.setEnabled(true);
                bi.ciw218f.setEnabled(true);

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

                bi.ciw217y.setText(jsonB1A.getciw217y());
                bi.ciw217m.setText(jsonB1A.getciw217m());
                bi.ciw217d.setText(jsonB1A.getciw217d());

                if (!jsonB1A.getciw218().equals("0")) {
                    bi.ciw218.check(
                            jsonB1A.getciw218().equals("1") ? bi.ciw218a.getId() :
                                    jsonB1A.getciw218().equals("2") ? bi.ciw218b.getId() :
                                            jsonB1A.getciw218().equals("3") ? bi.ciw218c.getId() :
                                                    jsonB1A.getciw218().equals("4") ? bi.ciw218d.getId() :
                                                            jsonB1A.getciw218().equals("5") ? bi.ciw218e.getId() :
                                                                    bi.ciw218f.getId()
                    );
                }

//                childSerial = Integer.valueOf(jsonB1A.getciw219());

                if (jsonB1A.getciw219().equals("2")) {
                    bi.ciw218d.setChecked(true);
                    bi.ciw217y.setEnabled(false);
                    bi.ciw217d.setEnabled(false);
                    bi.ciw217m.setEnabled(false);
                    bi.ciw218a.setEnabled(false);
                    bi.ciw218b.setEnabled(false);
                    bi.ciw218c.setEnabled(false);
                    bi.ciw218e.setEnabled(false);
                    bi.ciw218f.setEnabled(false);

                    frontPressed = true;

                    if (!jsonB1A.getciw220().equals("0")) {
                        bi.ciw220.check(
                                jsonB1A.getciw220().equals("1") ? bi.ciw220a.getId() : jsonB1A.getciw220().equals("2") ? bi.ciw220b.getId() : bi.ciw220c.getId()
                        );
                    }

                    bi.ciw221y.setText(jsonB1A.getciw221y());
                    bi.ciw221m.setText(jsonB1A.getciw221m());
                    bi.ciw221d.setText(jsonB1A.getciw221d());

                    bi.ciw222y.setText(jsonB1A.getciw222y());
                    bi.ciw222m.setText(jsonB1A.getciw222m());
                    bi.ciw222d.setText(jsonB1A.getciw222d());

                    if (jsonB1A.getciw217Flag().equals("1")) {
                        bi.ciw217Flag.setChecked(true);
                    }

                    bi.ciw217Flag.setVisibility(View.VISIBLE);

                    break;

                }

                if (!twinFlag) {

                    frontPressed = true;

                    if (!jsonB1A.getciw220().equals("0")) {
                        bi.ciw220.check(
                                jsonB1A.getciw220().equals("1") ? bi.ciw220a.getId() : jsonB1A.getciw220().equals("2") ? bi.ciw220b.getId() : bi.ciw220c.getId()
                        );
                    }

                    bi.ciw221y.setText(jsonB1A.getciw221y());
                    bi.ciw221m.setText(jsonB1A.getciw221m());
                    bi.ciw221d.setText(jsonB1A.getciw221d());

                    bi.ciw222y.setText(jsonB1A.getciw222y());
                    bi.ciw222m.setText(jsonB1A.getciw222m());
                    bi.ciw222d.setText(jsonB1A.getciw222d());

                    if (jsonB1A.getciw217Flag().equals("1")) {
                        bi.ciw217Flag.setChecked(true);
                    }

                    bi.ciw217Flag.setVisibility(View.VISIBLE);

                    break;
                }

            }

        }

    }

    public void setupViews() {

        //bi.ciw217.setManager(getSupportFragmentManager());
        //bi.ciw217.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()));

        bi.ciw220.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ciw220a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpciw221, true);
                    ClearClass.ClearAllFields(bi.fldGrpciw222, false);

                    ClearClass.ClearAllFields(bi.fldGrciw221alive, true);
                    ClearClass.ClearAllFields(bi.fldGrciw221death, false);
                    //bi.fldGrpciw221.setVisibility(View.VISIBLE);
                    /*bi.fldGrpciw222.setVisibility(View.GONE);
                    bi.ciw222d.setText(null);
                    bi.ciw222m.setText(null);
                    bi.ciw222y.setText(null);*/

                } else {
                    ClearClass.ClearAllFields(bi.fldGrpciw221, false);
                    ClearClass.ClearAllFields(bi.fldGrpciw222, true);

                    ClearClass.ClearAllFields(bi.fldGrciw221alive, false);
                    ClearClass.ClearAllFields(bi.fldGrciw221death, true);
                    /*bi.fldGrpciw221.setVisibility(View.GONE);
                    bi.fldGrpciw222.setVisibility(View.VISIBLE);
                    bi.ciw221d.setText(null);
                    bi.ciw221m.setText(null);
                    bi.ciw221y.setText(null);*/
                }
            }
        });

        bi.ciw218.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.ciw218a.isChecked() || bi.ciw218b.isChecked() || bi.ciw218e.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpnb1a03, false);
                    ClearClass.ClearAllFields(bi.fldGrciw221alive, false);
                    ClearClass.ClearAllFields(bi.fldGrciw221death, false);

                } else {
                    ClearClass.ClearAllFields(bi.fldGrpnb1a03, true);
                    ClearClass.ClearAllFields(bi.fldGrciw221alive, true);
                    ClearClass.ClearAllFields(bi.fldGrciw221death, true);
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

                if (bi.ciw218d.isChecked() && !twinFlag) {

                    Intent i = new Intent(this, SectionB1AActivity.class);
                    i.putExtra("datey", bi.ciw217y.getText().toString());
                    i.putExtra("datem", bi.ciw217m.getText().toString());
                    i.putExtra("dated", bi.ciw217d.getText().toString());
                    i.putExtra("flag", true);
                    startActivity(i);

                } else {

                    if (MainApp.count >= MainApp.totalPregnancy) {

                        MainApp.count = 1;

                        if (childisUnder2AndAlive) {
                            startActivity(new Intent(this, SectionB2Activity.class));
                        } else {
                            if (SectionB1Activity.editWRAFlag) {

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

                                if (MainApp.mc.getKishSelectWRA()) {
                                    startActivityForResult(new Intent(this, SectionB6Activity.class)
                                            .putExtra("backPressed", classPassName.equals(SectionB6Activity.class.getName())), 1);
                                }
                                if (MainApp.mc.getKishSelectMWRA()) {
                                    finish();
                                    if (SectionB1Activity.isCurrentlyPreg)
                                        startActivity(new Intent(this, SectionD2AActivity.class));
                                    else
                                        startActivity(new Intent(this, SectionD3AActivity.class));
                                } else {
                                    startActivity(new Intent(this, MotherEndingActivity.class)
                                            .putExtra("complete", true));
                                }
                            }
                        }

                    } else {

                        MainApp.count++;

                        startActivityForResult(new Intent(this, SectionB1AActivity.class)
                                .putExtra("backPressed", classPassName.equals(SectionB1AActivity.class.getName())), 1);
                    }

                }
            }
        }

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


        if (!ValidatorClass.EmptyTextBox(this, bi.ciw217d, getString(R.string.ciw217))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.ciw217m, getString(R.string.ciw217))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, bi.ciw217y, getString(R.string.ciw217))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.ciw217d, 1, 31, 98, getString(R.string.ciw217), " day")) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, bi.ciw217m, 1, 12, 98, getString(R.string.ciw217), " month")) {
            return false;
        }


        if (!ValidatorClass.RangeTextBox(this, bi.ciw217y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), getString(R.string.ciw217), " years")) {
            return false;
        }

        Calendar today = Calendar.getInstance();
        if (date.after(today)) {
            if (!ValidatorClass.RangeTextBoxforDate(this, bi.ciw217d, 1, DateUtils.getCurrentDate(), 98, "Date can not be more than today")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBoxforDate(this, bi.ciw217m, 1, DateUtils.getCurrentMonth(), 98, "Month can not be more than current month")) {
                return false;
            }

            if (!ValidatorClass.RangeTextBoxforDate(this, bi.ciw217y, DateUtils.getCurrentYear() - 5, DateUtils.getCurrentYear(), "Year can not be more than current year")) {
                return false;
            }

        }


        if (!ValidatorClass.EmptyRadioButton(this, bi.ciw218, bi.ciw218a, getString(R.string.ciw218))) {
            return false;
        }

        if (bi.ciw218c.isChecked() || bi.ciw218f.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.ciw220, bi.ciw220a, getString(R.string.ciw220))) {
                return false;
            }

            if (bi.ciw220a.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ciw221y, getString(R.string.ciw221) + " - " + getString(R.string.year))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ciw221m, getString(R.string.ciw221) + " - " + getString(R.string.month))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ciw221d, getString(R.string.ciw221) + " - " + getString(R.string.day))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.ciw221y, 0, 4, getString(R.string.ciw221), " years")) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.ciw221m, 0, 11, getString(R.string.ciw221), " months")) {
                    return false;
                }


                if (!ValidatorClass.RangeTextBox(this, bi.ciw221d, 0, 29, getString(R.string.ciw221), " days")) {
                    return false;
                }

                if (bi.ciw221y.getText().toString().equals("0") && bi.ciw221m.getText().toString().equals("0") && bi.ciw221d.getText().toString().equals("0")) {
                    Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.ciw221), Toast.LENGTH_LONG).show();
                    bi.ciw221y.setError("All can not be zero");
                    bi.ciw221m.setError("All can not be zero");
                    bi.ciw221d.setError("All can not be zero");
                    Log.i(SectionA2Activity.class.getSimpleName(), "ciw221" + ": This data is Required!");
                    return false;
                } else {
                    bi.ciw221y.setError(null);
                    bi.ciw221m.setError(null);
                    bi.ciw221d.setError(null);
                }
               /* String myDate =bi.ciw217y+"/"+bi.ciw217m+"/"+bi.ciw217d;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millis = date.getTime();
                AgeModel ageModel  =  DateUtils.calculateAge(millis);
                bi.ciw221d.setText(ageModel.getdays());
                bi.ciw221m.setText(ageModel.getmonths());
                bi.ciw221y.setText(ageModel.getyears());
*/
            } else if (bi.ciw220b.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.ciw222y, getString(R.string.ciw222) + " - " + getString(R.string.year))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ciw222m, getString(R.string.ciw222) + " - " + getString(R.string.month))) {
                    return false;
                }

                if (!ValidatorClass.EmptyTextBox(this, bi.ciw222d, getString(R.string.ciw222) + " - " + getString(R.string.day))) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.ciw222y, 0, 4, getString(R.string.ciw222), " years")) {
                    return false;
                }

                if (!ValidatorClass.RangeTextBox(this, bi.ciw222m, 0, 11, getString(R.string.ciw222), " months")) {
                    return false;
                }


                if (!ValidatorClass.RangeTextBox(this, bi.ciw222d, 0, 29, getString(R.string.ciw222), " days")) {
                    return false;
                }

                if (bi.ciw222y.getText().toString().equals("0") && bi.ciw222m.getText().toString().equals("0") && bi.ciw222d.getText().toString().equals("0")) {
                    Toast.makeText(this, "ERROR(invalid): " + "All can not be zero" + getString(R.string.ciw222), Toast.LENGTH_LONG).show();
                    bi.ciw222y.setError("All can not be zero");
                    bi.ciw222m.setError("All can not be zero");
                    bi.ciw222d.setError("All can not be zero");
                    Log.i(SectionA2Activity.class.getSimpleName(), "ciw222" + ": This data is Required!");
                    return false;
                } else {
                    bi.ciw222y.setError(null);
                    bi.ciw222m.setError(null);
                    bi.ciw222d.setError(null);
                }
            }
        }


        return true;
    }

    private void SaveDraft() throws JSONException {

      /*  if (!bi.ciw217y.getText().toString().isEmpty() && !bi.ciw217m.getText().toString().isEmpty() && !bi.ciw217d.getText().toString().isEmpty()) {
            if (!bi.ciw217d.getText().toString().equals("98") && !bi.ciw217m.getText().toString().equals("98")) {
                date = DateUtils.getCalendarDate(bi.ciw217d.getText().toString(), bi.ciw217m.getText().toString(), bi.ciw217y.getText().toString());

                yearsBydob = DateUtils.ageInYearByDOBdouble(date);

            } else {
                //date = bi.ciw217d.getText().toString() + "-" bi.ciw21
                yearsBydob = DateUtils.ageInYearByDOB(bi.ciw217y.getText().toString());
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

        if ((bi.ciw218c.isChecked() || bi.ciw218d.isChecked() || bi.ciw218f.isChecked()) && yearsBydob <= 2) {
            childisUnder2AndAlive = true;
        }

        sB1a.put("ciw217Flag", bi.ciw217Flag.isChecked() ? "1" : "2");
        sB1a.put("wra_lno", MainApp.mc.getB1SerialNo());

        sB1a.put("ciw219", twinFlag ? "2" : "1");

        sB1a.put("ciw217y", bi.ciw217y.getText().toString());
        sB1a.put("ciw217m", bi.ciw217m.getText().toString());
        sB1a.put("ciw217d", bi.ciw217d.getText().toString());

        sB1a.put("ciw218", bi.ciw218a.isChecked() ? "1"
                : bi.ciw218b.isChecked() ? "2"
                : bi.ciw218c.isChecked() ? "3"
                : bi.ciw218d.isChecked() ? "4"
                : bi.ciw218e.isChecked() ? "5"
                : bi.ciw218f.isChecked() ? "6"
                : "0");

        if (!getIntent().getBooleanExtra("flag", false)) {
            MainApp.outcome = bi.ciw218.indexOfChild(findViewById(bi.ciw218.getCheckedRadioButtonId())) + 1;
        }

        sB1a.put("ciw220", bi.ciw220a.isChecked() ? "1"
                : bi.ciw220b.isChecked() ? "2"
                : bi.ciw220c.isChecked() ? "3"
                : "0");
        if (bi.ciw218c.isChecked() || bi.ciw218d.isChecked() || bi.ciw218f.isChecked()) {
            MainApp.status++;
        }

        sB1a.put("ciw221y", bi.ciw221y.getText().toString());
        sB1a.put("ciw221m", bi.ciw221m.getText().toString());
        sB1a.put("ciw221d", bi.ciw221d.getText().toString());

        sB1a.put("ciw222y", bi.ciw222y.getText().toString());
        sB1a.put("ciw222m", bi.ciw222m.getText().toString());
        sB1a.put("ciw222d", bi.ciw222d.getText().toString());

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

        if (!bi.ciw217y.getText().toString().isEmpty() && !bi.ciw217m.getText().toString().isEmpty()
                && !bi.ciw217d.getText().toString().isEmpty()) {
            if (!bi.ciw217d.getText().toString().equals("98") && !bi.ciw217m.getText().toString().equals("98")) {
                date = DateUtils.getCalendarDate(bi.ciw217d.getText().toString(), bi.ciw217m.getText().toString(), bi.ciw217y.getText().toString());

                yearsBydob = DateUtils.ageInYearByDOBdouble(date);

            } else {
                //date = bi.ciw217d.getText().toString() + "-" bi.ciw21
                yearsBydob = DateUtils.ageInYearByDOB(bi.ciw217y.getText().toString());
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

