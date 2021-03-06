package edu.aku.hassannaqvi.casi_2019.ui.childs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionC4Binding;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

import static edu.aku.hassannaqvi.casi_2019.ui.childs.SectionC1Activity.userCountryDari_Child;
import static edu.aku.hassannaqvi.casi_2019.ui.childs.SectionC1Activity.userCountryTajik_Child;

public class SectionC4Activity extends Menu2Activity {

    private final long DELAY = 1000;
    ActivitySectionC4Binding binding;
    DatabaseHelper db;
    FamilyMembersContract selectedChild;
    Boolean backPressed = false;
    private Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_c4);
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

        binding.txtcic401.setText(binding.txtcic401.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic402.setText(binding.txtcic402.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic403.setText(binding.txtcic403.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic404.setText(binding.txtcic404.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic405.setText(binding.txtcic405.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic406.setText(binding.txtcic406.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic407.setText(binding.txtcic407.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic408.setText(binding.txtcic408.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic409.setText(binding.txtcic409.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic410.setText(binding.txtcic410.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic411.setText(binding.txtcic411.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic412.setText(binding.txtcic412.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic413.setText(binding.txtcic413.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic414.setText(binding.txtcic414.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic415.setText(binding.txtcic415.getText().toString().replace("Name", SectionC1Activity.selectedChildName));


//      Assigning data to UI binding
        binding.setCallback(this);

        setupViews();

    }

    private void setupViews() {
        binding.cic401.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (binding.cic401a.isChecked()) {
                    binding.fldGrpcic402.setVisibility(View.VISIBLE);
                    binding.fldGrpcic403.setVisibility(View.VISIBLE);
                    binding.fldGrcic404.setVisibility(View.VISIBLE);
                    binding.fldGrpcic405.setVisibility(View.VISIBLE);


                } else {
                    binding.fldGrpcic402.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpcic402, null);
                    binding.fldGrpcic403.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpcic403, null);
                    binding.fldGrcic404.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrcic404, null);
                    binding.fldGrpcic405.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpcic405, null);
                }
            }
        });
        binding.cic402.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (!binding.cic402a.isChecked()) {
                    binding.fldGrpcic403.setVisibility(View.VISIBLE);
                    binding.fldGrcic404.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrcic404, null);
                    binding.fldGrpcic405.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpcic405, null);

                } else {
                    binding.fldGrpcic403.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpcic403, null);
                    binding.fldGrcic404.setVisibility(View.VISIBLE);
                    binding.fldGrpcic405.setVisibility(View.VISIBLE);

                }
            }
        });

        binding.cic406.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (binding.cic406a.isChecked()) {
                    binding.fldGrpcic407.setVisibility(View.VISIBLE);
                    binding.fldGrpcic408.setVisibility(View.VISIBLE);
                    binding.fldGrpcic409.setVisibility(View.VISIBLE);
                    binding.fldGrpcic410.setVisibility(View.VISIBLE);

                } else {
                    binding.fldGrpcic407.setVisibility(View.GONE);
                    binding.cic407.clearCheck();
                    binding.fldGrpcic408.setVisibility(View.GONE);
                    binding.cic408.clearCheck();
                    binding.fldGrpcic409.setVisibility(View.GONE);
                    binding.cic409.clearCheck();
                    binding.fldGrpcic410.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpcic410, null);

                }
            }
        });
        binding.cic407.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (binding.cic407a.isChecked()) {
                    binding.fldGrpcic408.setVisibility(View.GONE);
                    binding.cic408.clearCheck();
                    binding.fldGrpcic409.setVisibility(View.VISIBLE);
                    binding.fldGrpcic410.setVisibility(View.VISIBLE);

                } else {
                    binding.fldGrpcic408.setVisibility(View.VISIBLE);
                    binding.fldGrpcic409.setVisibility(View.GONE);
                    binding.cic409.clearCheck();
                    binding.fldGrpcic410.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpcic410, null);
                }
            }
        });

        binding.cic411.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (binding.cic411b.isChecked()) {
                    binding.fldGrpcic412.setVisibility(View.GONE);
                    binding.cic412.clearCheck();
                    binding.fldGrpcic413.setVisibility(View.GONE);
                    binding.cic413.clearCheck();
                    binding.fldGrpnc14.setVisibility(View.GONE);
                    binding.cic414.clearCheck();
                    binding.fldGrpcic415.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(binding.fldGrpcic415, null);
                } else {
                    binding.fldGrpcic412.setVisibility(View.VISIBLE);
                    binding.fldGrpcic413.setVisibility(View.VISIBLE);
                    binding.fldGrpnc14.setVisibility(View.VISIBLE);
                    binding.fldGrpcic415.setVisibility(View.VISIBLE);

                }
            }
        });
        binding.cic412.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (binding.cic412a.isChecked()) {
                    binding.fldGrpcic413.setVisibility(View.GONE);
                    binding.cic413.clearCheck();
                    binding.fldGrpnc14.setVisibility(View.VISIBLE);
                    binding.fldGrpcic415.setVisibility(View.VISIBLE);
                } else {
                    binding.fldGrpcic413.setVisibility(View.VISIBLE);
                    binding.fldGrpnc14.setVisibility(View.GONE);
                    binding.fldGrpcic415.setVisibility(View.GONE);
                    binding.cic414.clearCheck();
                    ClearClass.ClearAllFields(binding.fldGrpcic415, null);

                }
            }
        });


        //Get Intent
        selectedChild = (FamilyMembersContract) getIntent().getSerializableExtra("selectedChild");

//        Validation Boolean
        MainApp.validateFlag = false;

//        autoPopulateFields();


        /*Only For Tajik*/
        binding.cic403f.setVisibility(userCountryTajik_Child ? View.VISIBLE : View.GONE);
        binding.cic403g.setVisibility(userCountryTajik_Child ? View.VISIBLE : View.GONE);
        binding.cic403h.setVisibility(userCountryTajik_Child ? View.VISIBLE : View.GONE);

        binding.cic408f.setVisibility(userCountryTajik_Child ? View.VISIBLE : View.GONE);
        binding.cic408g.setVisibility(userCountryTajik_Child ? View.VISIBLE : View.GONE);
        binding.cic408h.setVisibility(userCountryTajik_Child ? View.VISIBLE : View.GONE);

        binding.cic413f.setVisibility(userCountryTajik_Child ? View.VISIBLE : View.GONE);
        binding.cic413g.setVisibility(userCountryTajik_Child ? View.VISIBLE : View.GONE);
        binding.cic413h.setVisibility(userCountryTajik_Child ? View.VISIBLE : View.GONE);


        if (userCountryTajik_Child) {

            binding.cic404.setVisibility(View.GONE);
            binding.cic40401.setVisibility(View.VISIBLE);

            binding.cic405txt02.setVisibility(View.VISIBLE);
            binding.cic405txt01.setVisibility(View.GONE);

            binding.cic409.setVisibility(View.GONE);
            binding.cic40901.setVisibility(View.VISIBLE);

            binding.cic414.setVisibility(View.GONE);
            binding.cic41401.setVisibility(View.VISIBLE);

            binding.cic415txt02.setVisibility(View.VISIBLE);
            binding.cic415txt01.setVisibility(View.GONE);

        } else {

            binding.cic404.setVisibility(View.VISIBLE);
            binding.cic40401.setVisibility(View.GONE);

            binding.cic405txt02.setVisibility(View.GONE);
            binding.cic405txt01.setVisibility(View.VISIBLE);

            binding.cic409.setVisibility(View.VISIBLE);
            binding.cic40901.setVisibility(View.GONE);

            binding.cic414.setVisibility(View.VISIBLE);
            binding.cic41401.setVisibility(View.GONE);

            binding.cic415txt02.setVisibility(View.GONE);
            binding.cic415txt01.setVisibility(View.VISIBLE);

        }

        // Dari
        binding.cic415b.setVisibility(userCountryDari_Child ? View.GONE : View.VISIBLE);


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
                    startActivity(new Intent(this, SectionC402Activity.class)
                            .putExtra("selectedChild", selectedChild));
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
        if (backPressed) {
            sC4.put("updatedate_cic4", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        if (SectionC1Activity.editChildFlag) {
            sC4.put("edit_updatedate_sc2", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

//        cic401
        sC4.put("cic401", binding.cic401a.isChecked() ? "1"
                : binding.cic401b.isChecked() ? "2"
                : "0");

//      cic402
        sC4.put("cic402", binding.cic402a.isChecked() ? "1"
                : binding.cic402b.isChecked() ? "2"
                : "0");


//        cic403
        sC4.put("cic403", binding.cic403a.isChecked() ? "1"
                : binding.cic403b.isChecked() ? "2"
                : binding.cic403c.isChecked() ? "3"
                : binding.cic403d.isChecked() ? "4"
                : binding.cic403e.isChecked() ? "5"
                : binding.cic403f.isChecked() ? "6"
                : binding.cic403g.isChecked() ? "7"
                : binding.cic403h.isChecked() ? "8"
                : binding.cic40396.isChecked() ? "961"
                : "0");

        sC4.put("cic40396x", binding.cic40396x.getText().toString());


//     cic404
        if (!userCountryTajik_Child) {
            sC4.put("cic404",
                    binding.cic404a.isChecked() ? "1"
                            : binding.cic404b.isChecked() ? "2"
                            : binding.cic404c.isChecked() ? "3"
                            : binding.cic404d.isChecked() ? "4"
                            : binding.cic404e.isChecked() ? "5"
                            : binding.cic4049601.isChecked() ? "961"
                            : binding.cic404g.isChecked() ? "7"
                            : binding.cic404h.isChecked() ? "8"
                            : binding.cic404i.isChecked() ? "9"
                            : binding.cic404j.isChecked() ? "10"
                            : binding.cic4049602.isChecked() ? "962"
                            : binding.cic404l.isChecked() ? "12"
                            : binding.cic404m.isChecked() ? "13"
                            : binding.cic4049603.isChecked() ? "963" :
                            "0");

            sC4.put("cic4049601x", binding.cic4049601x.getText().toString());
            sC4.put("cic4049602x", binding.cic4049602x.getText().toString());
            sC4.put("cic4049603x", binding.cic4049603x.getText().toString());
        } else {
            sC4.put("cic404",
                    binding.cic40401a.isChecked() ? "1"
                            : binding.cic40401b.isChecked() ? "2"
                            : binding.cic40401c.isChecked() ? "3"
                            : binding.cic40401d.isChecked() ? "4"
                            : binding.cic40401e.isChecked() ? "5"
                            : binding.cic404019601.isChecked() ? "961"
                            : binding.cic40401g.isChecked() ? "7"
                            : binding.cic40401h.isChecked() ? "8"
                            : binding.cic40401i.isChecked() ? "9"
                            : binding.cic40401j.isChecked() ? "10"
                            : binding.cic404019602.isChecked() ? "962"
                            : binding.cic40401l.isChecked() ? "12"
                            : binding.cic40401m.isChecked() ? "13"
                            : binding.cic40401n.isChecked() ? "14"
                            : binding.cic40401o.isChecked() ? "15"
                            : binding.cic40401p.isChecked() ? "16"
                            : binding.cic40401q.isChecked() ? "17"
                            : binding.cic404019603.isChecked() ? "963" :
                            "0");

            sC4.put("cic4049601x", binding.cic404019601x.getText().toString());
            sC4.put("cic4049602x", binding.cic404019602x.getText().toString());
            sC4.put("cic4049603x", binding.cic404019603x.getText().toString());
        }


//        cic405
        sC4.put("cic405a", binding.cic405a.isChecked() ? "1" : "0");
        sC4.put("cic405b", binding.cic405b.isChecked() ? "2" : "0");
        sC4.put("cic405c", binding.cic405c.isChecked() ? "3" : "0");
        sC4.put("cic405d", binding.cic405d.isChecked() ? "4" : "0");
        sC4.put("cic405e", binding.cic405e.isChecked() ? "5" : "0");
        sC4.put("cic405f", binding.cic405f.isChecked() ? "6" : "0");
        sC4.put("cic405g", binding.cic405g.isChecked() ? "7" : "0");
        sC4.put("cic405h", binding.cic405h.isChecked() ? "8" : "0");
        sC4.put("cic405i", binding.cic405i.isChecked() ? "9" : "0");
        sC4.put("cic405j", binding.cic405j.isChecked() ? "10" : "0");
        sC4.put("cic40596", binding.cic40596.isChecked() ? "96" : "0");
        sC4.put("cic40596x", binding.cic40596x.getText().toString());


//        cic406
        sC4.put("cic406", binding.cic406a.isChecked() ? "1"
                : binding.cic406b.isChecked() ? "2"
                : "0");


//        cic407
        sC4.put("cic407", binding.cic407a.isChecked() ? "1"
                : binding.cic407b.isChecked() ? "2"
                : "0");


//        cic408
        sC4.put("cic408", binding.cic408a.isChecked() ? "1"
                : binding.cic408b.isChecked() ? "2"
                : binding.cic408c.isChecked() ? "3"
                : binding.cic408d.isChecked() ? "4"
                : binding.cic408e.isChecked() ? "5"
                : binding.cic408f.isChecked() ? "6"
                : binding.cic408g.isChecked() ? "7"
                : binding.cic408h.isChecked() ? "8"
                : binding.cic40896.isChecked() ? "96"
                : "0");
        sC4.put("cic40896x", binding.cic40896x.getText().toString());


//        cic409
        if (!userCountryTajik_Child) {
            sC4.put("cic409", binding.cic409a.isChecked() ? "1"
                    : binding.cic409b.isChecked() ? "2"
                    : binding.cic409c.isChecked() ? "3"
                    : binding.cic409d.isChecked() ? "4"
                    : binding.cic409e.isChecked() ? "5"
                    : binding.cic4099601.isChecked() ? "961"
                    : binding.cic409f.isChecked() ? "6"
                    : binding.cic409g.isChecked() ? "7"
                    : binding.cic409h.isChecked() ? "8"
                    : binding.cic409i.isChecked() ? "9"
                    : binding.cic409j.isChecked() ? "10"
                    : binding.cic4099602.isChecked() ? "962"
                    : binding.cic409k.isChecked() ? "11"
                    : binding.cic409l.isChecked() ? "12"
                    : binding.cic409m.isChecked() ? "13"
                    : binding.cic4099603.isChecked() ? "963" :
                    "0");

            sC4.put("cic4099601x", binding.cic4099601x.getText().toString());
            sC4.put("cic4099602x", binding.cic4099602x.getText().toString());
            sC4.put("cic4099603x", binding.cic4099603x.getText().toString());
        } else {
            sC4.put("cic409", binding.cic40901a.isChecked() ? "1"
                    : binding.cic40901b.isChecked() ? "2"
                    : binding.cic40901c.isChecked() ? "3"
                    : binding.cic40901d.isChecked() ? "4"
                    : binding.cic40901e.isChecked() ? "5"
                    : binding.cic409019601.isChecked() ? "961"
                    : binding.cic40901f.isChecked() ? "6"
                    : binding.cic40901g.isChecked() ? "7"
                    : binding.cic40901h.isChecked() ? "8"
                    : binding.cic40901i.isChecked() ? "9"
                    : binding.cic40901j.isChecked() ? "10"
                    : binding.cic409019602.isChecked() ? "962"
                    : binding.cic40901k.isChecked() ? "11"
                    : binding.cic40901l.isChecked() ? "12"
                    : binding.cic40901m.isChecked() ? "13"
                    : binding.cic40901n.isChecked() ? "14"
                    : binding.cic40901o.isChecked() ? "15"
                    : binding.cic40901p.isChecked() ? "16"
                    : binding.cic40901q.isChecked() ? "17"
                    : binding.cic409019603.isChecked() ? "963" :
                    "0");

            sC4.put("cic4099601x", binding.cic409019601x.getText().toString());
            sC4.put("cic4099602x", binding.cic409019602x.getText().toString());
            sC4.put("cic4099603x", binding.cic409019603x.getText().toString());
        }


//          cic410
        sC4.put("cic410a", binding.cic410a.isChecked() ? "1" : "0");
        sC4.put("cic410b", binding.cic410b.isChecked() ? "2" : "0");
        sC4.put("cic410c", binding.cic410c.isChecked() ? "3" : "0");
        sC4.put("cic410d", binding.cic410d.isChecked() ? "4" : "0");
        sC4.put("cic410e", binding.cic410e.isChecked() ? "5" : "0");
        sC4.put("cic410f", binding.cic410f.isChecked() ? "6" : "0");
        sC4.put("cic410g", binding.cic410g.isChecked() ? "7" : "0");
        sC4.put("cic41096", binding.cic41096.isChecked() ? "96" : "0");
        sC4.put("cic41096x", binding.cic41096x.getText().toString());


//        cic411
        sC4.put("cic411", binding.cic411a.isChecked() ? "1"
                : binding.cic411b.isChecked() ? "2"
                : "0");


//       cic412
        sC4.put("cic412", binding.cic412a.isChecked() ? "1"
                : binding.cic412b.isChecked() ? "2"
                : "0");


//        cic413
        sC4.put("cic413", binding.cic413a.isChecked() ? "1"
                : binding.cic413b.isChecked() ? "2"
                : binding.cic413c.isChecked() ? "3"
                : binding.cic413d.isChecked() ? "4"
                : binding.cic413e.isChecked() ? "5"
                : binding.cic413f.isChecked() ? "6"
                : binding.cic413g.isChecked() ? "7"
                : binding.cic413h.isChecked() ? "8"
                : binding.cic41396.isChecked() ? "96"
                : "0");
        sC4.put("cic41396x", binding.cic41396x.getText().toString());


//      cic414
        if (!userCountryTajik_Child) {
            sC4.put("cic414",
                    binding.cic414a.isChecked() ? "1"
                            : binding.cic414b.isChecked() ? "2"
                            : binding.cic414c.isChecked() ? "3"
                            : binding.cic414d.isChecked() ? "4"
                            : binding.cic414e.isChecked() ? "5"
                            : binding.cic4149601.isChecked() ? "961"
                            : binding.cic414g.isChecked() ? "7"
                            : binding.cic414h.isChecked() ? "8"
                            : binding.cic414i.isChecked() ? "9"
                            : binding.cic414j.isChecked() ? "10"
                            : binding.cic4149602.isChecked() ? "962"
                            : binding.cic414l.isChecked() ? "12"
                            : binding.cic414m.isChecked() ? "13"
                            : binding.cic4149603.isChecked() ? "963" :
                            "0");

            sC4.put("cic4149601x", binding.cic4149601x.getText().toString());
            sC4.put("cic4149602x", binding.cic4149602x.getText().toString());
            sC4.put("cic4149603x", binding.cic4149603x.getText().toString());
        } else {
            sC4.put("cic414",
                    binding.cic41401a.isChecked() ? "1"
                            : binding.cic41401b.isChecked() ? "2"
                            : binding.cic41401c.isChecked() ? "3"
                            : binding.cic41401d.isChecked() ? "4"
                            : binding.cic41401e.isChecked() ? "5"
                            : binding.cic414019601.isChecked() ? "961"
                            : binding.cic41401g.isChecked() ? "7"
                            : binding.cic41401h.isChecked() ? "8"
                            : binding.cic41401i.isChecked() ? "9"
                            : binding.cic41401j.isChecked() ? "10"
                            : binding.cic414019602.isChecked() ? "962"
                            : binding.cic41401l.isChecked() ? "12"
                            : binding.cic41401m.isChecked() ? "13"
                            : binding.cic41401n.isChecked() ? "14"
                            : binding.cic41401o.isChecked() ? "15"
                            : binding.cic41401p.isChecked() ? "16"
                            : binding.cic41401q.isChecked() ? "17"
                            : binding.cic414019603.isChecked() ? "963" :
                            "0");

            sC4.put("cic4149601x", binding.cic414019601x.getText().toString());
            sC4.put("cic4149602x", binding.cic414019602x.getText().toString());
            sC4.put("cic4149603x", binding.cic414019603x.getText().toString());
        }


//        cic415
        sC4.put("cic415a", binding.cic415a.isChecked() ? "1" : "0");
        sC4.put("cic415b", binding.cic415b.isChecked() ? "2" : "0");
        sC4.put("cic415c", binding.cic415c.isChecked() ? "3" : "0");
        sC4.put("cic415d", binding.cic415d.isChecked() ? "4" : "0");
        sC4.put("cic415e", binding.cic415e.isChecked() ? "5" : "0");
        sC4.put("cic415f", binding.cic415f.isChecked() ? "6" : "0");
        sC4.put("cic415g", binding.cic415g.isChecked() ? "7" : "0");
        sC4.put("cic415h", binding.cic415g.isChecked() ? "7" : "0");
        sC4.put("cic41596", binding.cic41596.isChecked() ? "96" : "0");
        sC4.put("cic41596x", binding.cic41596x.getText().toString());

        MainApp.cc.setsC4(String.valueOf(sC4));

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