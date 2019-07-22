package edu.aku.hassannaqvi.casi_2019.ui.childs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;

import butterknife.ButterKnife;
import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONC3ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.ChildContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionC3Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionC3Activity extends Menu2Activity implements RadioGroup.OnCheckedChangeListener {

    private final long DELAY = 1000;
    ActivitySectionC3Binding binding;
    FamilyMembersContract selectedChild;
    DatabaseHelper db;
    Boolean backPressed = false;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_c3);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_c3);
        ButterKnife.bind(this);

        this.setTitle(getResources().getString(R.string.cic3heading));

        binding.txtcic305.setText(binding.txtcic305.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtcic306.setText(binding.txtcic306.getText().toString().replace("Name", SectionC1Activity.selectedChildName));


        if (SectionC1Activity.editChildFlag) {
            binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                    + "\n\n" + SectionC1Activity.editMotherName + " : " + getString(R.string.cih212a));
        } else {
            if (!SectionC1Activity.isNA) {
                binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionB1Activity.wraName + " : " + getString(R.string.cih212a));
            } else {
                binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionC1Activity.careTaker + " : " + getString(R.string.cih113));
            }
        }
        db = new DatabaseHelper(this);
        binding.setCallback(this);
        binding.cic302.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                formValidation();
                if (checkedId == R.id.cic302b) {
                    ClearClass.ClearAllFields(binding.fldGrpcic303parent, false);


                } else {
                    ClearClass.ClearAllFields(binding.fldGrpcic303parent, true);

                }

            }
        });

        binding.cic303.setOnCheckedChangeListener(this);
        binding.cic3bcg.setOnCheckedChangeListener(this);
        binding.cic3bcgsrc.setOnCheckedChangeListener(this);
        binding.cic3opv0.setOnCheckedChangeListener(this);
        binding.cic3opv0src.setOnCheckedChangeListener(this);

        binding.cic3opv1.setOnCheckedChangeListener(this);
        binding.cic3opv1src.setOnCheckedChangeListener(this);

        binding.cic3p1.setOnCheckedChangeListener(this);
        binding.cic3p1src.setOnCheckedChangeListener(this);

        binding.cic3pcv1.setOnCheckedChangeListener(this);
        binding.cic3pcv1src.setOnCheckedChangeListener(this);

        binding.cic3opv2.setOnCheckedChangeListener(this);
        binding.cic3opv2src.setOnCheckedChangeListener(this);

        binding.cic3p2.setOnCheckedChangeListener(this);
        binding.cic3p2src.setOnCheckedChangeListener(this);

        binding.cic3pcv2.setOnCheckedChangeListener(this);
        binding.cic3pcv2src.setOnCheckedChangeListener(this);

        binding.cic3opv3.setOnCheckedChangeListener(this);
        binding.cic3opv3src.setOnCheckedChangeListener(this);

        binding.cic3p3.setOnCheckedChangeListener(this);
        binding.cic3p3src.setOnCheckedChangeListener(this);

        binding.cic3pcv3.setOnCheckedChangeListener(this);
        binding.cic3pcv3src.setOnCheckedChangeListener(this);

        binding.cic3ipv.setOnCheckedChangeListener(this);
        binding.cic3ipvsrc.setOnCheckedChangeListener(this);

        binding.cic3m1.setOnCheckedChangeListener(this);
        binding.cic3m1src.setOnCheckedChangeListener(this);

        binding.cic3m2.setOnCheckedChangeListener(this);
        binding.cic3m2src.setOnCheckedChangeListener(this);
        binding.cic306.setOnCheckedChangeListener(this);


        binding.cic305.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == binding.cic305a.getId())
                    binding.cic306.clearCheck();
            }
        });


        //Get Intent
        selectedChild = (FamilyMembersContract) getIntent().getSerializableExtra("selectedChild");

//        Validation Boolean
        MainApp.validateFlag = false;

        autoPopulateFields();

    }

    private void autoPopulateFields() {
        ChildContract childContract = db.getsC3();

        if (!childContract.getsC3().equals("")) {

            JSONC3ModelClass jsonC3 = JSONUtilClass.getModelFromJSON(childContract.getsC3(), JSONC3ModelClass.class);

            if (!jsonC3.getcic302().equals("0")) {
                binding.cic302.check(
                        jsonC3.getcic302().equals("1") ? binding.cic302a.getId()
                                : binding.cic302b.getId()
                );
            }

            if (!jsonC3.getcic303().equals("0")) {
                binding.cic303.check(
                        jsonC3.getcic303().equals("1") ? binding.cic303a.getId()
                                : jsonC3.getcic303().equals("2") ? binding.cic303b.getId()
                                : jsonC3.getcic303().equals("3") ? binding.cic303c.getId()
                                : binding.cic303d.getId()
                );
            }
//            bcg

            if (!jsonC3.getcic3bcg().equals("0")) {
                binding.cic3bcg.check(
                        jsonC3.getcic3bcg().equals("1") ? binding.cic3bcga.getId()
                                : binding.cic3bcgb.getId()
                );
            }
            if (!jsonC3.getcic3bcgsrc().equals("0")) {
                binding.cic3bcgsrc.check(
                        jsonC3.getcic3bcgsrc().equals("1") ? binding.cic3bcgsrca.getId()
                                : binding.cic3bcgsrcb.getId()
                );
            }
            //            opv

            if (!jsonC3.getcic3opv0().equals("0")) {
                binding.cic3opv0.check(
                        jsonC3.getcic3opv0().equals("1") ? binding.cic3opv0a.getId()
                                : binding.cic3opv0b.getId()
                );
            }
            if (!jsonC3.getcic3opv0src().equals("0")) {
                binding.cic3opv0src.check(
                        jsonC3.getcic3opv0src().equals("1") ? binding.cic3opv0srca.getId()
                                : binding.cic3opv0srcb.getId()
                );
            }
            //            opv1

            if (!jsonC3.getcic3opv1().equals("0")) {
                binding.cic3opv1.check(
                        jsonC3.getcic3opv1().equals("1") ? binding.cic3opv1a.getId()
                                : binding.cic3opv1b.getId()
                );
            }
            if (!jsonC3.getcic3opv1src().equals("0")) {
                binding.cic3opv1src.check(
                        jsonC3.getcic3opv1src().equals("1") ? binding.cic3opv1srca.getId()
                                : binding.cic3opv1srcb.getId()
                );
            }

            //            p1

            if (!jsonC3.getcic3p1().equals("0")) {
                binding.cic3p1.check(
                        jsonC3.getcic3p1().equals("1") ? binding.cic3p1a.getId()
                                : binding.cic3p1b.getId()
                );
            }
            if (!jsonC3.getcic3p1src().equals("0")) {
                binding.cic3p1src.check(
                        jsonC3.getcic3p1src().equals("1") ? binding.cic3p1srca.getId()
                                : binding.cic3p1srcb.getId()
                );
            }

            //            pcv1

            if (!jsonC3.getcic3pcv1().equals("0")) {
                binding.cic3pcv1.check(
                        jsonC3.getcic3pcv1().equals("1") ? binding.cic3pcv1a.getId()
                                : binding.cic3pcv1b.getId()
                );
            }
            if (!jsonC3.getcic3pcv1src().equals("0")) {
                binding.cic3pcv1src.check(
                        jsonC3.getcic3pcv1src().equals("1") ? binding.cic3pcv1srca.getId()
                                : binding.cic3pcv1srcb.getId()
                );
            }
            //            opv2

            if (!jsonC3.getcic3opv2().equals("0")) {
                binding.cic3opv2.check(
                        jsonC3.getcic3opv2().equals("1") ? binding.cic3opv2a.getId()
                                : binding.cic3opv2b.getId()
                );
            }
            if (!jsonC3.getcic3opv2src().equals("0")) {
                binding.cic3opv2src.check(
                        jsonC3.getcic3opv2src().equals("1") ? binding.cic3opv2srca.getId()
                                : binding.cic3opv2srcb.getId()
                );
            }

            //            p2

            if (!jsonC3.getcic3p2().equals("0")) {
                binding.cic3p2.check(
                        jsonC3.getcic3p2().equals("1") ? binding.cic3p2a.getId()
                                : binding.cic3p2b.getId()
                );
            }
            if (!jsonC3.getcic3p2src().equals("0")) {
                binding.cic3p2src.check(
                        jsonC3.getcic3p2src().equals("1") ? binding.cic3p2srca.getId()
                                : binding.cic3p2srcb.getId()
                );
            }

            //            pcv2

            if (!jsonC3.getcic3pcv2().equals("0")) {
                binding.cic3pcv2.check(
                        jsonC3.getcic3pcv2().equals("1") ? binding.cic3pcv2a.getId()
                                : binding.cic3pcv2b.getId()
                );
            }
            if (!jsonC3.getcic3pcv2src().equals("0")) {
                binding.cic3pcv2src.check(
                        jsonC3.getcic3pcv2src().equals("1") ? binding.cic3pcv2srca.getId()
                                : binding.cic3pcv2srcb.getId()
                );
            }

            //            opv3

            if (!jsonC3.getcic3opv3().equals("0")) {
                binding.cic3opv3.check(
                        jsonC3.getcic3opv3().equals("1") ? binding.cic3opv3a.getId()
                                : binding.cic3opv3b.getId()
                );
            }
            if (!jsonC3.getcic3opv3src().equals("0")) {
                binding.cic3opv3src.check(
                        jsonC3.getcic3opv3src().equals("1") ? binding.cic3opv3srca.getId()
                                : binding.cic3opv3srcb.getId()
                );
            }

            //            p3

            if (!jsonC3.getcic3p3().equals("0")) {
                binding.cic3p3.check(
                        jsonC3.getcic3p3().equals("1") ? binding.cic3p3a.getId()
                                : binding.cic3p3b.getId()
                );
            }
            if (!jsonC3.getcic3p3src().equals("0")) {
                binding.cic3p3src.check(
                        jsonC3.getcic3p3src().equals("1") ? binding.cic3p3srca.getId()
                                : binding.cic3p3srcb.getId()
                );
            }

            //            pcv3

            if (!jsonC3.getcic3pcv3().equals("0")) {
                binding.cic3pcv3.check(
                        jsonC3.getcic3pcv3().equals("1") ? binding.cic3pcv3a.getId()
                                : binding.cic3pcv3b.getId()
                );
            }
            if (!jsonC3.getcic3pcv3src().equals("0")) {
                binding.cic3pcv3src.check(
                        jsonC3.getcic3pcv3src().equals("1") ? binding.cic3pcv3srca.getId()
                                : binding.cic3pcv3srcb.getId()
                );
            }
            //            ipv

            if (!jsonC3.getcic3ipv().equals("0")) {
                binding.cic3ipv.check(
                        jsonC3.getcic3ipv().equals("1") ? binding.cic3ipva.getId()
                                : binding.cic3ipvb.getId()
                );
            }
            if (!jsonC3.getcic3ipvsrc().equals("0")) {
                binding.cic3ipvsrc.check(
                        jsonC3.getcic3ipvsrc().equals("1") ? binding.cic3ipvsrca.getId()
                                : binding.cic3ipvsrcb.getId()
                );
            }
            //            m1

            if (!jsonC3.getcic3m1().equals("0")) {
                binding.cic3m1.check(
                        jsonC3.getcic3m1().equals("1") ? binding.cic3m1a.getId()
                                : binding.cic3m1b.getId()
                );
            }
            if (!jsonC3.getcic3m1src().equals("0")) {
                binding.cic3m1src.check(
                        jsonC3.getcic3m1src().equals("1") ? binding.cic3m1srca.getId()
                                : binding.cic3m1srcb.getId()
                );
            }
            //            m2

            if (!jsonC3.getcic3m2().equals("0")) {
                binding.cic3m2.check(
                        jsonC3.getcic3m2().equals("1") ? binding.cic3m2a.getId()
                                : binding.cic3m2b.getId()
                );
            }
            if (!jsonC3.getcic3m2src().equals("0")) {
                binding.cic3m2src.check(
                        jsonC3.getcic3m2src().equals("1") ? binding.cic3m2srca.getId()
                                : binding.cic3m2srcb.getId()
                );
            }

//           cic305

            if (!jsonC3.getcic305().equals("0")) {
                binding.cic305.check(
                        jsonC3.getcic305().equals("1") ? binding.cic305a.getId()
                                : jsonC3.getcic305().equals("2") ? binding.cic305b.getId()
                                : binding.cic30598.getId()
                );
            }
            if (!jsonC3.getcic306().equals("0")) {
                binding.cic306.check(
                        jsonC3.getcic306().equals("1") ? binding.cic306a.getId()
                                : binding.cic306b.getId()
                );
            }

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


//                finish();
                backPressed = true;
                startActivity(new Intent(this, SectionC4Activity.class)
                        .putExtra("selectedChild", selectedChild));

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


//       cic302
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic302, binding.cic302b, getString(R.string.cic302))) {
            return false;
        }
        if (binding.cic302a.isChecked()) {

//        cic303
            if (!ValidatorClass.EmptyRadioButton(this, binding.cic303, binding.cic303d, getString(R.string.cic303))) {
                return false;
            }
        }

//        cic3bcg
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3bcg, binding.cic3bcga, getString(R.string.cic3bcg) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3bcgsrc, binding.cic3bcgsrca, getString(R.string.cic3bcg) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3opv0
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3opv0, binding.cic3opv0a, getString(R.string.cic3opv0) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3opv0src, binding.cic3opv0srca, getString(R.string.cic3opv0) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3opv1
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3opv1, binding.cic3opv1a, getString(R.string.cic3opv1) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3opv1src, binding.cic3opv1srca, getString(R.string.cic3opv1) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3p1
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3p1, binding.cic3p1a, getString(R.string.cic3p1) + getString(R.string.cic3response))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3p1src, binding.cic3p1srca, getString(R.string.cic3p1) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3pcv1
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3pcv1, binding.cic3pcv1a, getString(R.string.cic3pcv1) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3pcv1src, binding.cic3pcv1srca, getString(R.string.cic3pcv1) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3opv2
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3opv2, binding.cic3opv2a, getString(R.string.cic3opv2) + getString(R.string.cic3response))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3opv2src, binding.cic3opv2srca, getString(R.string.cic3opv2) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3p2
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3p2, binding.cic3p2a, getString(R.string.cic3p2) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3p2src, binding.cic3p2srca, getString(R.string.cic3p2) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3pcv2
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3pcv2, binding.cic3pcv2a, getString(R.string.cic3pcv2) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3pcv2src, binding.cic3pcv2srca, getString(R.string.cic3pcv2) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3opv3
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3opv3, binding.cic3opv3a, getString(R.string.cic3opv3) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3opv3src, binding.cic3opv3srca, getString(R.string.cic3opv3) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3p3
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3p3, binding.cic3p3a, getString(R.string.cic3p3) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3p3src, binding.cic3p3srca, getString(R.string.cic3p3) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3pcv3
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3pcv3, binding.cic3pcv3a, getString(R.string.cic3pcv3) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3pcv3src, binding.cic3pcv3srca, getString(R.string.cic3pcv3) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3ipv3
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3ipv, binding.cic3ipva, getString(R.string.cic3ipv) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3ipvsrc, binding.cic3ipvsrca, getString(R.string.cic3ipv) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3m1dt
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3m1, binding.cic3m1a, getString(R.string.cic3m1) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3m1src, binding.cic3m1srca, getString(R.string.cic3m1) + getString(R.string.cic3src))) {
            return false;
        }


//        cic3m2dt
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3m2, binding.cic3m2a, getString(R.string.cic3m2) + getString(R.string.cic3response))) {
            return false;
        }
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic3m2src, binding.cic3m2srca, getString(R.string.cic3m2) + getString(R.string.cic3src))) {
            return false;
        }


//        cic305
        if (!ValidatorClass.EmptyRadioButton(this, binding.cic305, binding.cic305a, getString(R.string.cic305))) {
            return false;
        }

        if (binding.cic305a.isChecked()) {
//        cic306
            return ValidatorClass.EmptyRadioButton(this, binding.cic306, binding.cic306a, getString(R.string.cic306));
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        //  Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();

        try {
            SaveDraft();
            UpdateDB();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onBackPressed();
    }

    private void SaveDraft() throws JSONException {


        JSONObject sC3 = new JSONObject();
        if (backPressed) {
            sC3.put("updatedate_cic3", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        if (SectionC1Activity.editChildFlag) {
            sC3.put("edit_updatedate_sc2", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

//        cic301
        //sC3.put("cic3name", selectedChild.getName());
//        cic302
        //sC3.put("cic300Serial", selectedChild.getSerialNo());

//        cic302
        sC3.put("cic302", binding.cic302a.isChecked() ? "1"
                : binding.cic302b.isChecked() ? "2"
                : "0");

//        cic303
        sC3.put("cic303", binding.cic303a.isChecked() ? "1"
                : binding.cic303b.isChecked() ? "2"
                : binding.cic303c.isChecked() ? "3"
                : binding.cic303d.isChecked() ? "4"
                : "0");
//at birth
//          cic3bcg
        sC3.put("cic3bcg", binding.cic3bcga.isChecked() ? "1"
                : binding.cic3bcgb.isChecked() ? "2"
                : "0");
        sC3.put("cic3bcgsrc", binding.cic3bcgsrca.isChecked() ? "1"
                : binding.cic3bcgsrcb.isChecked() ? "2"
                : "0");


//          cic3opv0
        sC3.put("cic3opv0", binding.cic3opv0a.isChecked() ? "1"
                : binding.cic3opv0b.isChecked() ? "2"
                : "0");
        sC3.put("cic3opv0src", binding.cic3opv0srca.isChecked() ? "1"
                : binding.cic3opv0srcb.isChecked() ? "2"
                : "0");


//       at age of 6

//          cic3opv1
        sC3.put("cic3opv1", binding.cic3opv1a.isChecked() ? "1"
                : binding.cic3opv1b.isChecked() ? "2"
                : "0");
        sC3.put("cic3opv1src", binding.cic3opv1srca.isChecked() ? "1"
                : binding.cic3opv1srcb.isChecked() ? "2"
                : "0");

//          cic3p1
        sC3.put("cic3p1", binding.cic3p1a.isChecked() ? "1"
                : binding.cic3p1b.isChecked() ? "2"
                : "0");
        sC3.put("cic3p1src", binding.cic3p1srca.isChecked() ? "1"
                : binding.cic3p1srcb.isChecked() ? "2"
                : "0");


//          cic3pcv1
        sC3.put("cic3pcv1", binding.cic3pcv1a.isChecked() ? "1"
                : binding.cic3pcv1b.isChecked() ? "2"
                : "0");
        sC3.put("cic3pcv1src", binding.cic3pcv1srca.isChecked() ? "1"
                : binding.cic3pcv1srcb.isChecked() ? "2"
                : "0");

//       at age of 10 weeks

//          cic3opv2
        sC3.put("cic3opv2", binding.cic3opv2a.isChecked() ? "1"
                : binding.cic3opv2b.isChecked() ? "2"
                : "0");
        sC3.put("cic3opv2src", binding.cic3opv2srca.isChecked() ? "1"
                : binding.cic3opv2srcb.isChecked() ? "2"
                : "0");

//          cic3p2
        sC3.put("cic3p2", binding.cic3p2a.isChecked() ? "1"
                : binding.cic3p2b.isChecked() ? "2"
                : "0");
        sC3.put("cic3p2src", binding.cic3p2srca.isChecked() ? "1"
                : binding.cic3p2srcb.isChecked() ? "2"
                : "0");


//          cic3pcv2
        sC3.put("cic3pcv2", binding.cic3pcv2a.isChecked() ? "1"
                : binding.cic3pcv2b.isChecked() ? "2"
                : "0");
        sC3.put("cic3pcv2src", binding.cic3pcv2srca.isChecked() ? "1"
                : binding.cic3pcv2srcb.isChecked() ? "2"
                : "0");


//       at age of 14 weeks

//          cic3opv3
        sC3.put("cic3opv3", binding.cic3opv3a.isChecked() ? "1"
                : binding.cic3opv3b.isChecked() ? "2"
                : "0");
        sC3.put("cic3opv3src", binding.cic3opv3srca.isChecked() ? "1"
                : binding.cic3opv3srcb.isChecked() ? "2"
                : "0");

//          cic3p3
        sC3.put("cic3p3", binding.cic3p3a.isChecked() ? "1"
                : binding.cic3p3b.isChecked() ? "2"
                : "0");
        sC3.put("cic3p3src", binding.cic3p3srca.isChecked() ? "1"
                : binding.cic3p3srcb.isChecked() ? "2"
                : "0");


//          cic3pcv3
        sC3.put("cic3pcv3", binding.cic3pcv3a.isChecked() ? "1"
                : binding.cic3pcv3b.isChecked() ? "2"
                : "0");
        sC3.put("cic3pcv3src", binding.cic3pcv3srca.isChecked() ? "1"
                : binding.cic3pcv3srcb.isChecked() ? "2"
                : "0");

//          cic3ipv
        sC3.put("cic3ipv", binding.cic3ipva.isChecked() ? "1"
                : binding.cic3ipvb.isChecked() ? "2"
                : "0");
        sC3.put("cic3ipvsrc", binding.cic3ipvsrca.isChecked() ? "1"
                : binding.cic3ipvsrcb.isChecked() ? "2"
                : "0");


//at the age of 9 months
//          cic3m1
        sC3.put("cic3m1", binding.cic3m1a.isChecked() ? "1"
                : binding.cic3m1b.isChecked() ? "2"
                : "0");
        sC3.put("cic3m1src", binding.cic3m1srca.isChecked() ? "1"
                : binding.cic3m1srcb.isChecked() ? "2"
                : "0");


//at age of 15 months
//          cic3m2
        sC3.put("cic3m2", binding.cic3m2a.isChecked() ? "1"
                : binding.cic3m2b.isChecked() ? "2"
                : "0");
        sC3.put("cic3m2src", binding.cic3m2srca.isChecked() ? "1"
                : binding.cic3m2srcb.isChecked() ? "2"
                : "0");

//        cic305
        sC3.put("cic305", binding.cic305a.isChecked() ? "1"
                : binding.cic305b.isChecked() ? "2"
                : binding.cic30598.isChecked() ? "98"
                : "0");

//        cic306
        sC3.put("cic306", binding.cic306a.isChecked() ? "1"
                : binding.cic306b.isChecked() ? "2"
                : "0");


        MainApp.cc.setsC3(String.valueOf(sC3));


        //
    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSC3();

        if (updcount == 1) {

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        formValidation();
    }
}

