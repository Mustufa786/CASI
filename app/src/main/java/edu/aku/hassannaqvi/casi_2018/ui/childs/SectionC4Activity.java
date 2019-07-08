package edu.aku.hassannaqvi.casi_2018.ui.childs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;

import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionC4Binding;
import edu.aku.hassannaqvi.casi_2018.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2018.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2018.ui.wra.SectionB1Activity;
import edu.aku.hassannaqvi.casi_2018.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

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
        //setContentView(R.layout.activity_c4);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_c4);
        db = new DatabaseHelper(this);

        this.setTitle(getResources().getString(R.string.cic4heading));

        if (SectionC1Activity.editChildFlag) {
            binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                    + "\n\n" + SectionC1Activity.editMotherName + " : " + getString(R.string.cic212a));
        } else {
            if (!SectionC1Activity.isNA) {
                binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionB1Activity.wraName + " : " + getString(R.string.cic212a));
            } else {
                binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionC1Activity.careTaker + " : " + getString(R.string.cih113));
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
        binding.txtcic416.setText(binding.txtcic416.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic417.setText(binding.txtcic417.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic418.setText(binding.txtcic418.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic419.setText(binding.txtcic419.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic420.setText(binding.txtcic420.getText().toString().replace("Name", SectionC1Activity.selectedChildName));


//        //        Assigning data to UI binding
        binding.setCallback(this);


    }

    private void setupViews() {
        binding.cic401.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (!binding.cic401a.isChecked()) {
                    ClearClass.ClearAllFields(binding.fldGrcic402, false);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrcic402, true);

                }
            }
        });
        binding.cic402.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (!binding.cic402a.isChecked()) {
                    ClearClass.ClearAllFields(binding.fldGrpcic403, false);
                    ClearClass.ClearAllFields(binding.fldGrcic404, true);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpcic403, true);
                    ClearClass.ClearAllFields(binding.fldGrcic404, false);

                }
            }
        });

        binding.cic406.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (binding.cic406a.isChecked()) {
                    ClearClass.ClearAllFields(binding.fldGrpcic406, true);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpcic406, false);

                }
            }
        });
        binding.cic407.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (binding.cic407a.isChecked()) {
                    ClearClass.ClearAllFields(binding.fldGrpcic408, false);
                    ClearClass.ClearAllFields(binding.fldGrpcic409, true);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpcic408, true);
                    ClearClass.ClearAllFields(binding.fldGrpcic409, false);

                }
            }
        });

        binding.cic411.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (binding.cic411b.isChecked()) {
                    ClearClass.ClearAllFields(binding.fldGrpcic412, false);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpcic412, true);

                }
            }
        });
        binding.cic412.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (binding.cic412a.isChecked()) {
                    ClearClass.ClearAllFields(binding.fldGrpcic413, false);
                    ClearClass.ClearAllFields(binding.fldGrpnc14, true);
                } else {
                    ClearClass.ClearAllFields(binding.fldGrpcic413, true);
                    ClearClass.ClearAllFields(binding.fldGrpnc14, false);

                }
            }
        });


        //Get Intent
        selectedChild = (FamilyMembersContract) getIntent().getSerializableExtra("selectedChild");

//        binding.cic403.setOnCheckedChangeListener(this);
//        binding.cic404a.setOnCheckedChangeListener(this);
//        binding.cic407.setOnCheckedChangeListener(this);
//        binding.cic408b.setOnCheckedChangeListener(this);
//        binding.cic411.setOnCheckedChangeListener(this);
//        binding.cic412b.setOnCheckedChangeListener(this);
//        binding.cic413.setOnCheckedChangeListener(this);
//        binding.cic414.setOnCheckedChangeListener(this);
//        //binding.cic415.setOnCheckedChangeListener(this);
//        binding.cic416.addTextChangedListener(this);
//        binding.cic417.setOnCheckedChangeListener(this);
//        //binding.cic418.setOnCheckedChangeListener(this);
//        binding.cic419.setOnCheckedChangeListener(this);
//        binding.cic420m.addTextChangedListener(this);
//        binding.cic420d.addTextChangedListener(this);

//        Validation Boolean
        MainApp.validateFlag = false;

//        autoPopulateFields();

    }

//    private void autoPopulateFields() {
//        ChildContract childContract = db.getsC4();
//
//        if (!childContract.getsC4().equals("")) {
//
//            JSONC4ModelClass jsonC4 = JSONUtilClass.getModelFromJSON(childContract.getsC4(), JSONC4ModelClass.class);
//
//            if (!jsonC4.getcic401().equals("0")) {
//                binding.cic401.check(
//                        jsonC4.getcic401().equals("1") ? binding.cic401a.getId()
//                                : binding.cic401b.getId());
//            }
//            if (!jsonC4.getcic402().equals("0")) {
//                binding.cic402.check(
//                        jsonC4.getcic402().equals("1") ? binding.cic402a.getId()
//                                : binding.cic402b.getId());
//            }
//            if (!jsonC4.getcic403().equals("0")) {
//                binding.cic403.check(
//                        jsonC4.getcic403().equals("1") ? binding.cic403a.getId()
//                                : jsonC4.getcic403().equals("2") ? binding.cic403b.getId()
//                                : jsonC4.getcic403().equals("3") ? binding.cic403c.getId()
//                                : jsonC4.getcic403().equals("4") ? binding.cic403d.getId()
//                                : jsonC4.getcic403().equals("5") ? binding.cic403e.getId()
//                                : jsonC4.getcic403().equals("961") ? binding.cic40396.getId()
//                );
//            }
//            binding.cic40396x.setText(jsonC4.getcic4039601x());
//
//
//            if (!jsonC4.getcic404a().equals("0")) {
//                binding.cic404a.setChecked(true);
//            }
//            if (!jsonC4.getcic404b().equals("0")) {
//                binding.cic404b.setChecked(true);
//            }
//            if (!jsonC4.getcic404c().equals("0")) {
//                binding.cic404c.setChecked(true);
//            }
//            if (!jsonC4.getcic404d().equals("0")) {
//                binding.cic404d.setChecked(true);
//            }
//            if (!jsonC4.getcic404e().equals("0")) {
//                binding.cic404e.setChecked(true);
//            }
//            if (!jsonC4.getcic404f().equals("0")) {
//                binding.cic404f.setChecked(true);
//            }
//            if (!jsonC4.getcic404g().equals("0")) {
//                binding.cic404g.setChecked(true);
//            }
//            if (!jsonC4.getcic404h().equals("0")) {
//                binding.cic404h.setChecked(true);
//            }
//            if (!jsonC4.getcic404i().equals("0")) {
//                binding.cic404i.setChecked(true);
//            }
//            if (!jsonC4.getcic404j().equals("0")) {
//                binding.cic404j.setChecked(true);
//            }
//            if (!jsonC4.getNc404k().equals("0")) {
//                binding.cic404k.setChecked(true);
//            }
//            if (!jsonC4.getNc404l().equals("0")) {
//                binding.cic404l.setChecked(true);
//            }
//            if (!jsonC4.getNc404m().equals("0")) {
//                binding.cic404m.setChecked(true);
//            }
//            if (!jsonC4.getcic40496().equals("0")) {
//                binding.cic4049601.setChecked(true);
//            }
//            if (!jsonC4.getNc4049602().equals("0")) {
//                binding.cic4049602.setChecked(true);
//            }
//            if (!jsonC4.getNc4049603().equals("0")) {
//                binding.cic4049603.setChecked(true);
//            }
//
//            binding.cic4049601x.setText(jsonC4.getcic40496x());
//            binding.cic4049602x.setText(jsonC4.getcic40496x());
//            binding.cic4049603x.setText(jsonC4.getcic40496x());
//
//
////            C405
//
//
//            if (!jsonC4.getcic405().equals("0")) {
//                binding.cic405.check(
//                        jsonC4.getcic405().equals("1") ? binding.cic405a.getId()
//                                : binding.cic405b.getId());
//            }
//            if (!jsonC4.getcic406().equals("0")) {
//                binding.cic406.check(
//                        jsonC4.getcic406().equals("1") ? binding.cic406a.getId()
//                                : binding.cic406b.getId());
//            }
//            if (!jsonC4.getcic407().equals("0")) {
//                binding.cic407.check(
//                        jsonC4.getcic407().equals("1") ? binding.cic407a.getId()
//                                : jsonC4.getcic407().equals("2") ? binding.cic407b.getId()
//                                : jsonC4.getcic407().equals("3") ? binding.cic407c.getId()
//                                : jsonC4.getcic407().equals("4") ? binding.cic407d.getId()
//                                : jsonC4.getcic407().equals("5") ? binding.cic407e.getId()
//                                : jsonC4.getcic407().equals("961") ? binding.cic4079601.getId()
//                                : jsonC4.getcic407().equals("6") ? binding.cic407f.getId()
//                                : jsonC4.getcic407().equals("7") ? binding.cic407g.getId()
//                                : jsonC4.getcic407().equals("8") ? binding.cic407h.getId()
//                                : jsonC4.getcic407().equals("9") ? binding.cic407i.getId()
//                                : jsonC4.getcic407().equals("10") ? binding.cic407j.getId()
//                                : jsonC4.getcic407().equals("962") ? binding.cic4079602.getId()
//                                : jsonC4.getcic407().equals("11") ? binding.cic407k.getId()
//                                : jsonC4.getcic407().equals("12") ? binding.cic407l.getId()
//                                : jsonC4.getcic407().equals("13") ? binding.cic407m.getId()
//                                : binding.cic4079603.getId());
//            }
//            binding.cic4079601x.setText(jsonC4.getcic4079601x());
//            binding.cic4079602x.setText(jsonC4.getcic4079602x());
//            binding.cic4079603x.setText(jsonC4.getcic4079603x());
//
//
//            if (!jsonC4.getcic408b().equals("0")) {
//                binding.cic408b.setChecked(true);
//            }
//
//            if (!jsonC4.getcic408e().equals("0")) {
//                binding.cic408e.setChecked(true);
//            }
//            if (!jsonC4.getcic408f().equals("0")) {
//                binding.cic408f.setChecked(true);
//            }
//            if (!jsonC4.getcic408g().equals("0")) {
//                binding.cic408g.setChecked(true);
//            }
//            if (!jsonC4.getcic408h().equals("0")) {
//                binding.cic408h.setChecked(true);
//            }
//            if (!jsonC4.getcic408i().equals("0")) {
//                binding.cic408i.setChecked(true);
//            }
//            if (!jsonC4.getcic408j().equals("0")) {
//                binding.cic408j.setChecked(true);
//            }
//            if (!jsonC4.getcic4089601().equals("0")) {
//                binding.cic4089601.setChecked(true);
//            }
//
//            binding.cic4089601x.setText(jsonC4.getcic40496x());
////            C409
//            if (!jsonC4.getcic409().equals("0")) {
//                binding.cic409.check(
//                        jsonC4.getcic409().equals("1") ? binding.cic409a.getId()
//                                : binding.cic409b.getId());
//            }
//            if (!jsonC4.getcic410().equals("0")) {
//                binding.cic410.check(
//                        jsonC4.getcic410().equals("1") ? binding.cic410a.getId()
//                                : binding.cic410b.getId());
//            }
//            if (!jsonC4.getcic411().equals("0")) {
//                binding.cic411.check(
//                        jsonC4.getcic411().equals("1") ? binding.cic411a.getId()
//                                : jsonC4.getcic411().equals("2") ? binding.cic411b.getId()
//                                : jsonC4.getcic411().equals("3") ? binding.cic411c.getId()
//                                : jsonC4.getcic411().equals("4") ? binding.cic411d.getId()
//                                : jsonC4.getcic411().equals("5") ? binding.cic411e.getId()
//                                : jsonC4.getcic411().equals("961") ? binding.cic4119601.getId()
//                                : jsonC4.getcic411().equals("6") ? binding.cic411f.getId()
//                                : jsonC4.getcic411().equals("7") ? binding.cic411g.getId()
//                                : jsonC4.getcic411().equals("8") ? binding.cic411h.getId()
//                                : jsonC4.getcic411().equals("9") ? binding.cic411i.getId()
//                                : jsonC4.getcic411().equals("10") ? binding.cic411j.getId()
//                                : jsonC4.getcic411().equals("962") ? binding.cic4119602.getId()
//                                : jsonC4.getcic411().equals("11") ? binding.cic411k.getId()
//                                : jsonC4.getcic411().equals("12") ? binding.cic411l.getId()
//                                : jsonC4.getcic411().equals("13") ? binding.cic411m.getId()
//                                : binding.cic4119603.getId());
//            }
//            binding.cic4119601x.setText(jsonC4.getcic4119601x());
//            binding.cic4119602x.setText(jsonC4.getcic4119602x());
//            binding.cic4119603x.setText(jsonC4.getcic4119603x());
//
//            if (!jsonC4.getcic412a().equals("0")) {
//                binding.cic412b.setChecked(true);
//            }
//            if (!jsonC4.getcic412b().equals("0")) {
//                binding.cic412c.setChecked(true);
//            }
//            if (!jsonC4.getcic412c().equals("0")) {
//                binding.cic412e.setChecked(true);
//            }
//            if (!jsonC4.getcic412d().equals("0")) {
//                binding.cic412f.setChecked(true);
//            }
//            if (!jsonC4.getcic412e().equals("0")) {
//                binding.cic412g.setChecked(true);
//            }
//            if (!jsonC4.getcic412f().equals("0")) {
//                binding.cic412h.setChecked(true);
//            }
//            if (!jsonC4.getcic412g().equals("0")) {
//                binding.cic412i.setChecked(true);
//            }
//            if (!jsonC4.getcic412h().equals("0")) {
//                binding.cic412j.setChecked(true);
//            }
//
//            if (!jsonC4.getcic4129601().equals("0")) {
//                binding.cic4129601.setChecked(true);
//            }
//
//            binding.cic4129601x.setText(jsonC4.getcic40496x());
//
//            if (!jsonC4.getcic413().equals("0")) {
//                binding.cic413.check(
//                        jsonC4.getcic413().equals("1") ? binding.cic413a.getId()
//                                : jsonC4.getcic413().equals("2") ? binding.cic413b.getId()
//                                : binding.cic41398.getId());
//            }
//            if (!jsonC4.getcic414().equals("0")) {
//                binding.cic414.check(
//                        jsonC4.getcic414().equals("1") ? binding.cic414a.getId()
//                                : jsonC4.getcic414().equals("2") ? binding.cic414b.getId()
//                                : binding.cic41498.getId());
//            }
//            if (!jsonC4.getcic415().equals("0")) {
//                binding.cic415.check(
//                        jsonC4.getcic415().equals("1") ? binding.cic415a.getId()
//                                : jsonC4.getcic415().equals("2") ? binding.cic415b.getId()
//                                : binding.cic41598.getId());
//            }
//            binding.cic416.setText(jsonC4.getcic416());
//
//            if (!jsonC4.getcic417().equals("0")) {
//                binding.cic417.check(
//                        jsonC4.getcic417().equals("1") ? binding.cic417a.getId()
//                                : jsonC4.getcic417().equals("2") ? binding.cic417b.getId()
//                                : jsonC4.getcic417().equals("3") ? binding.cic417c.getId()
//                                : jsonC4.getcic417().equals("4") ? binding.cic417d.getId()
//                                : binding.cic417e.getId());
//            }
//            if (!jsonC4.getcic418().equals("0")) {
//                binding.cic418.check(
//                        jsonC4.getcic418().equals("1") ? binding.cic418a.getId()
//                                : binding.cic418b.getId()
//                );
//            }
//            if (!jsonC4.getcic419().equals("0")) {
//                binding.cic419.check(
//                        jsonC4.getcic419().equals("1") ? binding.cic419a.getId()
//                                : jsonC4.getcic419().equals("2") ? binding.cic419b.getId()
//                                : jsonC4.getcic419().equals("3") ? binding.cic419c.getId()
//                                : jsonC4.getcic419().equals("4") ? binding.cic419d.getId()
//                                : binding.cic419e.getId());
//            }
//
//            binding.cic420m.setText(jsonC4.getcic420m());
//
//            binding.cic420d.setText(jsonC4.getcic420d());
//
//
//        }
//    }


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

        //Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {
//            try {
//                SaveDraft();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            if (UpdateDB()) {
                backPressed = true;

                if (SectionC1Activity.ageInMontsbyDob > 23 && SectionC1Activity.ageInMontsbyDob < 60) {
                    startActivity(new Intent(this, SectionC5Activity.class)
                            .putExtra("selectedChild", selectedChild));
                } else {

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
                                //.putExtra("checkingFlag", false)
                                .putExtra("complete", true));
                    }
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
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sC4 = new JSONObject();
        if (backPressed) {
            sC4.put("updatedate_cic4", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        if (SectionC1Activity.editChildFlag) {
            sC4.put("edit_updatedate_sc2", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

//        nc301
        //sC4.put("cic4name", selectedChild.getName());
//        nc302
        //sC4.put("cic402Serial", selectedChild.getSerialNo());

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
                : binding.cic40396.isChecked() ? "961"
                : "0");

        sC4.put("cic40396x", binding.cic40396x.getText().toString());


//     cic404
        sC4.put("cic404", binding.cic404a.isChecked() ? "1"
                : binding.cic404b.isChecked() ? "2"
                : binding.cic404c.isChecked() ? "3"
                : binding.cic404d.isChecked() ? "4"
                : binding.cic404e.isChecked() ? "5"
                : binding.cic4049601.isChecked() ? "961"
                : binding.cic404g.isChecked() ? "6"
                : binding.cic404h.isChecked() ? "7"
                : binding.cic404i.isChecked() ? "8"
                : binding.cic404j.isChecked() ? "9"
                : binding.cic4049602.isChecked() ? "962"
                : binding.cic404l.isChecked() ? "10"
                : binding.cic404m.isChecked() ? "11"
                : binding.cic4049603.isChecked() ? "963" :
                "0");

        sC4.put("cic4049601x", binding.cic4049601x.getText().toString());
        sC4.put("cic4049602x", binding.cic4049602x.getText().toString());
        sC4.put("cic4049603x", binding.cic4049603x.getText().toString());


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
                : binding.cic40896.isChecked() ? "96"
                : "0");
        sC4.put("cic40896x", binding.cic40896x.getText().toString());


//        cic409
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
                : binding.cic41396.isChecked() ? "96"
                : "0");
        sC4.put("cic41396x", binding.cic41396x.getText().toString());


//      cic414
        sC4.put("cic414", binding.cic414a.isChecked() ? "1"
                : binding.cic414b.isChecked() ? "2"
                : binding.cic414c.isChecked() ? "3"
                : binding.cic414d.isChecked() ? "4"
                : binding.cic414e.isChecked() ? "5"
                : binding.cic4149601.isChecked() ? "961"
                : binding.cic414f.isChecked() ? "6"
                : binding.cic414g.isChecked() ? "7"
                : binding.cic414h.isChecked() ? "8"
                : binding.cic414i.isChecked() ? "9"
                : binding.cic414j.isChecked() ? "10"
                : binding.cic4149602.isChecked() ? "962"
                : binding.cic414k.isChecked() ? "11"
                : binding.cic414l.isChecked() ? "12"
                : binding.cic414m.isChecked() ? "13"
                : binding.cic4149603.isChecked() ? "963" :
                "0");

        sC4.put("cic4149601x", binding.cic4149601x.getText().toString());
        sC4.put("cic4149602x", binding.cic4149602x.getText().toString());
        sC4.put("cic4149603x", binding.cic4149603x.getText().toString());


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


//        cic416
        sC4.put("cic416", binding.cic416a.isChecked() ? "1"
                : binding.cic416b.isChecked() ? "2"
                : binding.cic41698.isChecked() ? "98"
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

        MainApp.cc.setsC4(String.valueOf(sC4));

        //Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSC4();

        if (updcount == 1) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}