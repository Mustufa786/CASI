package edu.aku.hassannaqvi.casi_2019.ui.childs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionC402Binding;
import edu.aku.hassannaqvi.casi_2019.other.JsonUtils;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionC402Activity extends Menu2Activity {

    private final long DELAY = 1000;
    ActivitySectionC402Binding binding;
    DatabaseHelper db;
    FamilyMembersContract selectedChild;
    Boolean backPressed = false;
    private Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_c4);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_c402);
        db = new DatabaseHelper(this);

        this.setTitle(getResources().getString(R.string.cic4heading));

        if (SectionC1Activity.editChildFlag) {
            binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                    + "\n\n" + SectionC1Activity.editMotherName);
        } else {
            if (!SectionC1Activity.isNA) {
                binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionB1Activity.wraName);
            } else {
                binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionC1Activity.careTaker);
            }
        }

        binding.txtcic416.setText(binding.txtcic416.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic417.setText(binding.txtcic417.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic418.setText(binding.txtcic418.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic419.setText(binding.txtcic419.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic420.setText(binding.txtcic420.getText().toString().replace("Name", SectionC1Activity.selectedChildName));


//      Assigning data to UI binding
        binding.setCallback(this);

        setupViews();

    }

    private void setupViews() {

        binding.cic416.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == binding.cic416a.getId()) {
                    binding.fldGrpcic41601.setVisibility(View.VISIBLE);
                } else {
                    binding.fldGrpcic41601.setVisibility(View.GONE);
                    binding.cic41601.clearCheck();
                }

            }
        });


        binding.cic418.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == binding.cic418a.getId()) {
                    binding.fldGrpcic419.setVisibility(View.VISIBLE);
                    binding.fldGrpcic420.setVisibility(View.VISIBLE);
                } else {
                    binding.fldGrpcic419.setVisibility(View.GONE);
                    binding.fldGrpcic420.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpcic420, null);
                    ClearClass.ClearAllFields(binding.fldGrpcic419, null);

                }
            }
        });

        binding.cic421.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == binding.cic421b.getId()) {
                    binding.fldGrpcic422.setVisibility(View.GONE);
                    binding.fldGrpcic423.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpcic422, null);
                    ClearClass.ClearAllFields(binding.fldGrpcic423, null);
                } else {
                    binding.fldGrpcic422.setVisibility(View.VISIBLE);
                    binding.fldGrpcic423.setVisibility(View.VISIBLE);


                }
            }
        });


        //Get Intent
        selectedChild = (FamilyMembersContract) getIntent().getSerializableExtra("selectedChild");

//        Validation Boolean
        MainApp.validateFlag = false;

//        autoPopulateFields();


    }

    /*private void autoPopulateFields() {
        ChildContract childContract = db.getsC4();

        if (!childContract.getsC4().equals("")) {

            JSONC4ModelClass jsonC4 = JSONUtilClass.getModelFromJSON(childContract.getsC4(), JSONC4ModelClass.class);

            if (!jsonC4.getcic401().equals("0")) {
                binding.cic401.check(
                        jsonC4.getcic401().equals("1") ? binding.cic401a.getId()
                                : binding.cic401b.getId());
            }
            if (!jsonC4.getcic402().equals("0")) {
                binding.cic402.check(
                        jsonC4.getcic402().equals("1") ? binding.cic402a.getId()
                                : binding.cic402b.getId());
            }
            if (!jsonC4.getcic403().equals("0")) {
                binding.cic403.check(
                        jsonC4.getcic403().equals("1") ? binding.cic403a.getId()
                                : jsonC4.getcic403().equals("2") ? binding.cic403b.getId()
                                : jsonC4.getcic403().equals("3") ? binding.cic403c.getId()
                                : jsonC4.getcic403().equals("4") ? binding.cic403d.getId()
                                : jsonC4.getcic403().equals("5") ? binding.cic403e.getId()
                                : jsonC4.getcic403().equals("6") ? binding.cic403f.getId()
                                : jsonC4.getcic403().equals("7") ? binding.cic403g.getId()
                                : jsonC4.getcic403().equals("8") ? binding.cic403h.getId()
                                : binding.cic40396.getId()
                );
            }
            binding.cic40396x.setText(jsonC4.getcic4039601x());


            *//*if (!jsonC4.getcic404a().equals("0")) {
                binding.cic404a.setChecked(true);
            }
            if (!jsonC4.getcic404b().equals("0")) {
                binding.cic404b.setChecked(true);
            }
            if (!jsonC4.getcic404c().equals("0")) {
                binding.cic404c.setChecked(true);
            }
            if (!jsonC4.getcic404d().equals("0")) {
                binding.cic404d.setChecked(true);
            }
            if (!jsonC4.getcic404e().equals("0")) {
                binding.cic404e.setChecked(true);
            }
            if (!jsonC4.getcic404f().equals("0")) {
                binding.cic404f.setChecked(true);
            }
            if (!jsonC4.getcic404g().equals("0")) {
                binding.cic404g.setChecked(true);
            }
            if (!jsonC4.getcic404h().equals("0")) {
                binding.cic404h.setChecked(true);
            }
            if (!jsonC4.getcic404i().equals("0")) {
                binding.cic404i.setChecked(true);
            }
            if (!jsonC4.getcic404j().equals("0")) {
                binding.cic404j.setChecked(true);
            }
            if (!jsonC4.getcic404k().equals("0")) {
                binding.cic404k.setChecked(true);
            }
            if (!jsonC4.getcic404l().equals("0")) {
                binding.cic404l.setChecked(true);
            }
            if (!jsonC4.getcic404m().equals("0")) {
                binding.cic404m.setChecked(true);
            }
            if (!jsonC4.getcic404n().equals("0")) {
                binding.cic404n.setChecked(true);
            }
            if (!jsonC4.getcic404o().equals("0")) {
                binding.cic404o.setChecked(true);
            }
            if (!jsonC4.getcic404p().equals("0")) {
                binding.cic404p.setChecked(true);
            }
            if (!jsonC4.getcic404q().equals("0")) {
                binding.cic404q.setChecked(true);
            }
            if (!jsonC4.getcic40496().equals("0")) {
                binding.cic4049601.setChecked(true);
            }
            if (!jsonC4.getcic4049602().equals("0")) {
                binding.cic4049602.setChecked(true);
            }
            if (!jsonC4.getcic4049603().equals("0")) {
                binding.cic4049603.setChecked(true);
            }*//*

            binding.cic4049601x.setText(jsonC4.getcic40496x());
            binding.cic4049602x.setText(jsonC4.getcic40496x());
            binding.cic4049603x.setText(jsonC4.getcic40496x());


//            C405


            if (!jsonC4.getCic405a().equals("0")) {
                binding.cic405a.setChecked(true);
            }
            if (!jsonC4.getCic405b().equals("0")) {
                binding.cic405b.setChecked(true);
            }
            if (!jsonC4.getCic405c().equals("0")) {
                binding.cic405c.setChecked(true);
            }
            if (!jsonC4.getCic405d().equals("0")) {
                binding.cic405d.setChecked(true);
            }
            if (!jsonC4.getCic405e().equals("0")) {
                binding.cic405e.setChecked(true);
            }
            if (!jsonC4.getCic405e().equals("0")) {
                binding.cic405e.setChecked(true);
            }
            if (!jsonC4.getCic405f().equals("0")) {
                binding.cic405f.setChecked(true);
            }
            if (!jsonC4.getCic405g().equals("0")) {
                binding.cic405g.setChecked(true);
            }
            if (!jsonC4.getCic405h().equals("0")) {
                binding.cic405h.setChecked(true);
            }
            if (!jsonC4.getCic405i().equals("0")) {
                binding.cic405i.setChecked(true);
            }
            if (!jsonC4.getCic405j().equals("0")) {
                binding.cic405j.setChecked(true);
            }
            if (!jsonC4.getCic40596().equals("0")) {
                binding.cic40596.setChecked(true);
            }

            binding.cic40596x.setText(jsonC4.getCic40596x());

            if (!jsonC4.getcic406().equals("0")) {
                binding.cic406.check(
                        jsonC4.getcic406().equals("1") ? binding.cic406a.getId()
                                : binding.cic406b.getId());
            }
            if (!jsonC4.getcic407().equals("0")) {
                binding.cic407.check(
                        jsonC4.getcic407().equals("1") ? binding.cic407a.getId()
                                : binding.cic407b.getId());
            }
            if (!jsonC4.getCic408().equals("0")) {
                binding.cic408.check(
                        jsonC4.getCic408().equals("1") ? binding.cic408a.getId()
                                : jsonC4.getCic408().equals("2") ? binding.cic408b.getId()
                                : jsonC4.getCic408().equals("3") ? binding.cic408c.getId()
                                : jsonC4.getCic408().equals("4") ? binding.cic408d.getId()
                                : jsonC4.getCic408().equals("5") ? binding.cic408e.getId()
                                : jsonC4.getCic405d().equals("6") ? binding.cic408f.getId()
                                : jsonC4.getCic405d().equals("7") ? binding.cic408g.getId()
                                : jsonC4.getCic405d().equals("8") ? binding.cic408h.getId()
                                : binding.cic40896.getId());
            }

            binding.cic40896x.setText(jsonC4.getCic40896x());


//            C409
            if (!jsonC4.getcic409().equals("0")) {
                binding.cic409.check(
                        jsonC4.getcic409().equals("1") ? binding.cic409a.getId()
                                : jsonC4.getcic409().equals("2") ? binding.cic409b.getId()
                                : jsonC4.getcic409().equals("3") ? binding.cic409c.getId()
                                : jsonC4.getcic409().equals("4") ? binding.cic409d.getId()
                                : jsonC4.getcic409().equals("5") ? binding.cic409e.getId()
                                : jsonC4.getcic409().equals("961") ? binding.cic4099601.getId()
                                : jsonC4.getcic409().equals("6") ? binding.cic409f.getId()
                                : jsonC4.getcic409().equals("7") ? binding.cic409g.getId()
                                : jsonC4.getcic409().equals("8") ? binding.cic409h.getId()
                                : jsonC4.getcic409().equals("9") ? binding.cic409i.getId()
                                : jsonC4.getcic409().equals("10") ? binding.cic409j.getId()
                                : jsonC4.getcic409().equals("962") ? binding.cic4099602.getId()
                                : jsonC4.getcic409().equals("11") ? binding.cic409k.getId()
                                : jsonC4.getcic409().equals("12") ? binding.cic409l.getId()
                                : jsonC4.getcic409().equals("13") ? binding.cic409m.getId()
                                : jsonC4.getcic409().equals("14") ? binding.cic409n.getId()
                                : jsonC4.getcic409().equals("15") ? binding.cic409o.getId()
                                : jsonC4.getcic409().equals("16") ? binding.cic409p.getId()
                                : jsonC4.getcic409().equals("17") ? binding.cic409q.getId()
                                : binding.cic4099603.getId());

                binding.cic4099601x.setText(jsonC4.getCic4049601x());
                binding.cic4099602x.setText(jsonC4.getCic4049602x());
                binding.cic4099603x.setText(jsonC4.getCic4099603x());
            }
            if (!jsonC4.getCic410a().equals("0")) {
                binding.cic410a.setChecked(true);
            }
            if (!jsonC4.getCic410b().equals("0")) {
                binding.cic410b.setChecked(true);
            }
            if (!jsonC4.getCic410b().equals("0")) {
                binding.cic410b.setChecked(true);
            }
            if (!jsonC4.getCic410c().equals("0")) {
                binding.cic410c.setChecked(true);
            }
            if (!jsonC4.getCic410d().equals("0")) {
                binding.cic410d.setChecked(true);
            }
            if (!jsonC4.getCic410e().equals("0")) {
                binding.cic410e.setChecked(true);
            }
            if (!jsonC4.getCic410a().equals("0")) {
                binding.cic410f.setChecked(true);
            }
            if (!jsonC4.getCic410g().equals("0")) {
                binding.cic410g.setChecked(true);
            }
            if (!jsonC4.getCic41096().equals("0")) {
                binding.cic41096.setChecked(true);
            }
            binding.cic41096x.setText(jsonC4.getCic41096x());

            if (!jsonC4.getcic411().equals("0")) {
                binding.cic411.check(
                        jsonC4.getcic411().equals("1") ? binding.cic411a.getId()
                                : binding.cic411b.getId());
            }
            if (!jsonC4.getcic411().equals("0")) {
                binding.cic411.check(
                        jsonC4.getcic411().equals("1") ? binding.cic411a.getId()
                                : binding.cic411b.getId());
            }
            if (!jsonC4.getCic412().equals("0")) {
                binding.cic412.check(
                        jsonC4.getCic412().equals("1") ? binding.cic412a.getId()
                                : binding.cic412b.getId());
            }
            if (!jsonC4.getcic413().equals("0")) {
                binding.cic413.check(
                        jsonC4.getcic413().equals("1") ? binding.cic413a.getId()
                                : jsonC4.getcic413().equals("2") ? binding.cic413b.getId()
                                : jsonC4.getcic413().equals("3") ? binding.cic413c.getId()
                                : jsonC4.getcic413().equals("4") ? binding.cic413d.getId()
                                : jsonC4.getcic413().equals("5") ? binding.cic413e.getId()
                                : jsonC4.getcic413().equals("6") ? binding.cic413f.getId()
                                : jsonC4.getcic413().equals("7") ? binding.cic413g.getId()
                                : jsonC4.getcic413().equals("8") ? binding.cic413h.getId()
                                : binding.cic41396.getId());
            }
            if (!jsonC4.getcic414().equals("0")) {
                binding.cic414.check(
                        jsonC4.getcic414().equals("1") ? binding.cic414a.getId()
                                : jsonC4.getcic414().equals("2") ? binding.cic414b.getId()
                                : jsonC4.getcic414().equals("3") ? binding.cic414c.getId()
                                : jsonC4.getcic414().equals("4") ? binding.cic414d.getId()
                                : jsonC4.getcic414().equals("5") ? binding.cic414e.getId()
                                : jsonC4.getcic414().equals("6") ? binding.cic414f.getId()
                                : jsonC4.getcic414().equals("7") ? binding.cic414g.getId()
                                : jsonC4.getcic414().equals("8") ? binding.cic414h.getId()
                                : jsonC4.getcic414().equals("9") ? binding.cic414i.getId()
                                : jsonC4.getcic414().equals("10") ? binding.cic414j.getId()
                                : jsonC4.getcic414().equals("11") ? binding.cic414k.getId()
                                : jsonC4.getcic414().equals("12") ? binding.cic414l.getId()
                                : jsonC4.getcic414().equals("13") ? binding.cic414m.getId()
                                : jsonC4.getcic409().equals("14") ? binding.cic414n.getId()
                                : jsonC4.getcic409().equals("15") ? binding.cic414o.getId()
                                : jsonC4.getcic409().equals("16") ? binding.cic414p.getId()
                                : jsonC4.getcic409().equals("17") ? binding.cic414q.getId()
                                : jsonC4.getcic414().equals("961") ? binding.cic4149601.getId()
                                : jsonC4.getcic414().equals("962") ? binding.cic4149602.getId()
                                : binding.cic4149603.getId());
                binding.cic4149601x.setText(jsonC4.getCic4149601x());
                binding.cic4149602x.setText(jsonC4.getCic4149602x());
                binding.cic4149603x.setText(jsonC4.getCic4149603x());


            }

            if (!jsonC4.getCic415a().equals("0")) {
                binding.cic415a.setChecked(true);
            }
            if (!jsonC4.getCic415b().equals("0")) {
                binding.cic415b.setChecked(true);
            }
            if (!jsonC4.getCic415c().equals("0")) {
                binding.cic415c.setChecked(true);
            }
            if (!jsonC4.getCic415d().equals("0")) {
                binding.cic415d.setChecked(true);
            }
            if (!jsonC4.getCic415e().equals("0")) {
                binding.cic415e.setChecked(true);
            }
            if (!jsonC4.getCic415f().equals("0")) {
                binding.cic415f.setChecked(true);
            }
            if (!jsonC4.getCic415g().equals("0")) {
                binding.cic415g.setChecked(true);
            }
            if (!jsonC4.getCic415h().equals("0")) {
                binding.cic415h.setChecked(true);
            }
            if (!jsonC4.getCic41596().equals("0")) {
                binding.cic41596.setChecked(true);
            }
            binding.cic41596x.setText(jsonC4.getCic41596x());


            if (!jsonC4.getcic416().equals("0")) {
                binding.cic416.check(
                        jsonC4.getcic416().equals("1") ? binding.cic416a.getId()
                                : jsonC4.getcic416().equals("2") ? binding.cic416b.getId()
                                : binding.cic41698.getId());
            }
            if (!jsonC4.getcic417().equals("0")) {
                binding.cic417.check(
                        jsonC4.getcic417().equals("1") ? binding.cic417a.getId()
                                : jsonC4.getcic417().equals("2") ? binding.cic417b.getId()
                                : binding.cic41798.getId());
            }
            if (!jsonC4.getcic418().equals("0")) {
                binding.cic418.check(
                        jsonC4.getcic418().equals("1") ? binding.cic418a.getId()
                                : jsonC4.getcic418().equals("2") ? binding.cic418b.getId()
                                : binding.cic41898.getId());
            }
            if (!jsonC4.getCic420().equals("0")) {
                binding.cic420.check(
                        jsonC4.getCic420().equals("1") ? binding.cic420a.getId()
                                : jsonC4.getCic420().equals("2") ? binding.cic420b.getId()
                                : jsonC4.getCic420().equals("3") ? binding.cic420c.getId()
                                : jsonC4.getCic420().equals("4") ? binding.cic420d.getId()
                                : binding.cic420e.getId());
            }

            if (!jsonC4.getCic421().equals("0")) {
                binding.cic418.check(
                        jsonC4.getCic421().equals("1") ? binding.cic421a.getId()
                                : binding.cic421b.getId());
            }
            if (!jsonC4.getCic422().equals("0")) {
                binding.cic422.check(
                        jsonC4.getCic422().equals("1") ? binding.cic422a.getId()
                                : jsonC4.getCic422().equals("2") ? binding.cic422b.getId()
                                : jsonC4.getCic422().equals("2") ? binding.cic422c.getId()
                                : jsonC4.getCic422().equals("2") ? binding.cic422d.getId()
                                : binding.cic422e.getId());
            }

            binding.cic423d.setText(jsonC4.getCic423d());
            binding.cic423m.setText(jsonC4.getCic423m());
        }

    }*/


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
//        try {
//            SaveDraft();
//            UpdateDB();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        super.onBackPressed();


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
                backPressed = true;

                if (SectionC1Activity.editChildFlag) {
                    finish();
                    startActivity(new Intent(this, ViewMemberActivity.class)
                            .putExtra("flagEdit", false)
                            .putExtra("comingBack", true)
                            .putExtra("cluster", MainApp.cc.getClusterno())
                            .putExtra("hhno", MainApp.cc.getHhno())
                    );
                } else {
                    startActivity(new Intent(this, ChildEndingActivity.class)
                            .putExtra("complete", true));
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        if (SectionC1Activity.editChildFlag) {
            finish();
            startActivity(new Intent(this, ViewMemberActivity.class)
                    .putExtra("flagEdit", false)
                    .putExtra("comingBack", true)
                    .putExtra("cluster", MainApp.cc.getClusterno())
                    .putExtra("hhno", MainApp.cc.getHhno())
            );
        } else {
            MainApp.endChildActivity(this, this, false);
        }
    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, binding.fldGrpSectionC4);
    }


    private void SaveDraft() throws JSONException {

        JSONObject sC4 = new JSONObject();

//        cic416
        sC4.put("cic416", binding.cic416a.isChecked() ? "1"
                : binding.cic416b.isChecked() ? "2"
                : binding.cic41698.isChecked() ? "98"
                : "0");

//        cic41601
        sC4.put("cic41601", binding.cic41601a.isChecked() ? "1"
                : binding.cic41601b.isChecked() ? "2"
                : "0");


//        cic417
        sC4.put("cic417", binding.cic417a.isChecked() ? "1"
                : binding.cic417b.isChecked() ? "2"
                : binding.cic41798.isChecked() ? "98"
                : "0");

//        cic418
        sC4.put("cic418", binding.cic418a.isChecked() ? "1"
                : binding.cic418b.isChecked() ? "2"
                : binding.cic41898.isChecked() ? "98"
                : "0");


//        cic419
        sC4.put("cic419", binding.cic419.getText().toString());


//        cic420
        sC4.put("cic420", binding.cic420a.isChecked() ? "1"
                : binding.cic420b.isChecked() ? "2"
                : binding.cic420c.isChecked() ? "3"
                : binding.cic420d.isChecked() ? "4"
                : binding.cic420e.isChecked() ? "5"
                : "0");

//        cic421
        sC4.put("cic421", binding.cic421a.isChecked() ? "1"
                : binding.cic421b.isChecked() ? "2"
                : "0");

//        cic422
        sC4.put("cic422", binding.cic422a.isChecked() ? "1"
                : binding.cic422b.isChecked() ? "2"
                : binding.cic422c.isChecked() ? "3"
                : binding.cic422d.isChecked() ? "4"
                : binding.cic422e.isChecked() ? "5"
                : "0");

//        cic423m
        sC4.put("cic423m", binding.cic423m.getText().toString());

//       cic423a
        sC4.put("cic423d", binding.cic423d.getText().toString());


        JSONObject localJsonObj = JsonUtils.mergeJSONObjects(new JSONObject(MainApp.cc.getsC4()), sC4);

        MainApp.cc.setsC4(String.valueOf(localJsonObj));

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSC4();

        if (updcount == 1) {

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}