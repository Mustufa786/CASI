package edu.aku.hassannaqvi.casi_2018.ui;

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
import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONC3ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.ChildContract;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionC3Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.validation.clearClass;
import edu.aku.hassannaqvi.casi_2018.validation.validatorClass;

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

        this.setTitle(getResources().getString(R.string.nc3heading));

        binding.txtnc305.setText(binding.txtnc305.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        binding.txtnc306.setText(binding.txtnc306.getText().toString().replace("Name", SectionC1Activity.selectedChildName));


        if (SectionC1Activity.editChildFlag) {
            binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                    + "\n\n" + SectionC1Activity.editMotherName + " : " + getString(R.string.nh212a));
        } else {
            if (!SectionC1Activity.isNA) {
                binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionB1Activity.wraName + " : " + getString(R.string.nh212a));
            } else {
                binding.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionC1Activity.careTaker + " : " + getString(R.string.nh113));
            }
        }
        db = new DatabaseHelper(this);
        binding.setCallback(this);
        binding.nc302.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (checkedId == R.id.nc302b) {
                    clearClass.ClearAllFields(binding.fldGrpnc303parent, false);


                } else {
                    clearClass.ClearAllFields(binding.fldGrpnc303parent, true);

                }

            }
        });

        binding.nc303.setOnCheckedChangeListener(this);
        binding.nc3bcg.setOnCheckedChangeListener(this);
        binding.nc3bcgsrc.setOnCheckedChangeListener(this);
        binding.nc3opv0.setOnCheckedChangeListener(this);
        binding.nc3opv0src.setOnCheckedChangeListener(this);

        binding.nc3opv1.setOnCheckedChangeListener(this);
        binding.nc3opv1src.setOnCheckedChangeListener(this);

        binding.nc3p1.setOnCheckedChangeListener(this);
        binding.nc3p1src.setOnCheckedChangeListener(this);

        binding.nc3pcv1.setOnCheckedChangeListener(this);
        binding.nc3pcv1src.setOnCheckedChangeListener(this);

        binding.nc3opv2.setOnCheckedChangeListener(this);
        binding.nc3opv2src.setOnCheckedChangeListener(this);

        binding.nc3p2.setOnCheckedChangeListener(this);
        binding.nc3p2src.setOnCheckedChangeListener(this);

        binding.nc3pcv2.setOnCheckedChangeListener(this);
        binding.nc3pcv2src.setOnCheckedChangeListener(this);

        binding.nc3opv3.setOnCheckedChangeListener(this);
        binding.nc3opv3src.setOnCheckedChangeListener(this);

        binding.nc3p3.setOnCheckedChangeListener(this);
        binding.nc3p3src.setOnCheckedChangeListener(this);

        binding.nc3pcv3.setOnCheckedChangeListener(this);
        binding.nc3pcv3src.setOnCheckedChangeListener(this);

        binding.nc3ipv.setOnCheckedChangeListener(this);
        binding.nc3ipvsrc.setOnCheckedChangeListener(this);

        binding.nc3m1.setOnCheckedChangeListener(this);
        binding.nc3m1src.setOnCheckedChangeListener(this);

        binding.nc3m2.setOnCheckedChangeListener(this);
        binding.nc3m2src.setOnCheckedChangeListener(this);
        binding.nc306.setOnCheckedChangeListener(this);


        binding.nc305.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                formValidation();
                if (binding.nc305b.isChecked() || binding.nc30598.isChecked()) {
//                    binding.nc306.clearCheck();
                    clearClass.ClearAllFields(binding.fldGrpnc305, false);
                } else {
                    clearClass.ClearAllFields(binding.fldGrpnc305, true);
                }
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

            if (!jsonC3.getnc302().equals("0")) {
                binding.nc302.check(
                        jsonC3.getnc302().equals("1") ? binding.nc302a.getId()
                                : binding.nc302b.getId()
                );
            }

            if (!jsonC3.getnc303().equals("0")) {
                binding.nc303.check(
                        jsonC3.getnc303().equals("1") ? binding.nc303a.getId()
                                : jsonC3.getnc303().equals("2") ? binding.nc303b.getId()
                                : jsonC3.getnc303().equals("3") ? binding.nc303c.getId()
                                : binding.nc303d.getId()
                );
            }
//            bcg

            if (!jsonC3.getnc3bcg().equals("0")) {
                binding.nc3bcg.check(
                        jsonC3.getnc3bcg().equals("1") ? binding.nc3bcga.getId()
                                : binding.nc3bcgb.getId()
                );
            }
            if (!jsonC3.getnc3bcgsrc().equals("0")) {
                binding.nc3bcgsrc.check(
                        jsonC3.getnc3bcgsrc().equals("1") ? binding.nc3bcgsrca.getId()
                                : binding.nc3bcgsrcb.getId()
                );
            }
            //            opv

            if (!jsonC3.getnc3opv0().equals("0")) {
                binding.nc3opv0.check(
                        jsonC3.getnc3opv0().equals("1") ? binding.nc3opv0a.getId()
                                : binding.nc3opv0b.getId()
                );
            }
            if (!jsonC3.getnc3opv0src().equals("0")) {
                binding.nc3opv0src.check(
                        jsonC3.getnc3opv0src().equals("1") ? binding.nc3opv0srca.getId()
                                : binding.nc3opv0srcb.getId()
                );
            }
            //            opv1

            if (!jsonC3.getnc3opv1().equals("0")) {
                binding.nc3opv1.check(
                        jsonC3.getnc3opv1().equals("1") ? binding.nc3opv1a.getId()
                                : binding.nc3opv1b.getId()
                );
            }
            if (!jsonC3.getnc3opv1src().equals("0")) {
                binding.nc3opv1src.check(
                        jsonC3.getnc3opv1src().equals("1") ? binding.nc3opv1srca.getId()
                                : binding.nc3opv1srcb.getId()
                );
            }

            //            p1

            if (!jsonC3.getnc3p1().equals("0")) {
                binding.nc3p1.check(
                        jsonC3.getnc3p1().equals("1") ? binding.nc3p1a.getId()
                                : binding.nc3p1b.getId()
                );
            }
            if (!jsonC3.getnc3p1src().equals("0")) {
                binding.nc3p1src.check(
                        jsonC3.getnc3p1src().equals("1") ? binding.nc3p1srca.getId()
                                : binding.nc3p1srcb.getId()
                );
            }

            //            pcv1

            if (!jsonC3.getnc3pcv1().equals("0")) {
                binding.nc3pcv1.check(
                        jsonC3.getnc3pcv1().equals("1") ? binding.nc3pcv1a.getId()
                                : binding.nc3pcv1b.getId()
                );
            }
            if (!jsonC3.getnc3pcv1src().equals("0")) {
                binding.nc3pcv1src.check(
                        jsonC3.getnc3pcv1src().equals("1") ? binding.nc3pcv1srca.getId()
                                : binding.nc3pcv1srcb.getId()
                );
            }
            //            opv2

            if (!jsonC3.getnc3opv2().equals("0")) {
                binding.nc3opv2.check(
                        jsonC3.getnc3opv2().equals("1") ? binding.nc3opv2a.getId()
                                : binding.nc3opv2b.getId()
                );
            }
            if (!jsonC3.getnc3opv2src().equals("0")) {
                binding.nc3opv2src.check(
                        jsonC3.getnc3opv2src().equals("1") ? binding.nc3opv2srca.getId()
                                : binding.nc3opv2srcb.getId()
                );
            }

            //            p2

            if (!jsonC3.getnc3p2().equals("0")) {
                binding.nc3p2.check(
                        jsonC3.getnc3p2().equals("1") ? binding.nc3p2a.getId()
                                : binding.nc3p2b.getId()
                );
            }
            if (!jsonC3.getnc3p2src().equals("0")) {
                binding.nc3p2src.check(
                        jsonC3.getnc3p2src().equals("1") ? binding.nc3p2srca.getId()
                                : binding.nc3p2srcb.getId()
                );
            }

            //            pcv2

            if (!jsonC3.getnc3pcv2().equals("0")) {
                binding.nc3pcv2.check(
                        jsonC3.getnc3pcv2().equals("1") ? binding.nc3pcv2a.getId()
                                : binding.nc3pcv2b.getId()
                );
            }
            if (!jsonC3.getnc3pcv2src().equals("0")) {
                binding.nc3pcv2src.check(
                        jsonC3.getnc3pcv2src().equals("1") ? binding.nc3pcv2srca.getId()
                                : binding.nc3pcv2srcb.getId()
                );
            }

            //            opv3

            if (!jsonC3.getnc3opv3().equals("0")) {
                binding.nc3opv3.check(
                        jsonC3.getnc3opv3().equals("1") ? binding.nc3opv3a.getId()
                                : binding.nc3opv3b.getId()
                );
            }
            if (!jsonC3.getnc3opv3src().equals("0")) {
                binding.nc3opv3src.check(
                        jsonC3.getnc3opv3src().equals("1") ? binding.nc3opv3srca.getId()
                                : binding.nc3opv3srcb.getId()
                );
            }

            //            p3

            if (!jsonC3.getnc3p3().equals("0")) {
                binding.nc3p3.check(
                        jsonC3.getnc3p3().equals("1") ? binding.nc3p3a.getId()
                                : binding.nc3p3b.getId()
                );
            }
            if (!jsonC3.getnc3p3src().equals("0")) {
                binding.nc3p3src.check(
                        jsonC3.getnc3p3src().equals("1") ? binding.nc3p3srca.getId()
                                : binding.nc3p3srcb.getId()
                );
            }

            //            pcv3

            if (!jsonC3.getnc3pcv3().equals("0")) {
                binding.nc3pcv3.check(
                        jsonC3.getnc3pcv3().equals("1") ? binding.nc3pcv3a.getId()
                                : binding.nc3pcv3b.getId()
                );
            }
            if (!jsonC3.getnc3pcv3src().equals("0")) {
                binding.nc3pcv3src.check(
                        jsonC3.getnc3pcv3src().equals("1") ? binding.nc3pcv3srca.getId()
                                : binding.nc3pcv3srcb.getId()
                );
            }
            //            ipv

            if (!jsonC3.getnc3ipv().equals("0")) {
                binding.nc3ipv.check(
                        jsonC3.getnc3ipv().equals("1") ? binding.nc3ipva.getId()
                                : binding.nc3ipvb.getId()
                );
            }
            if (!jsonC3.getnc3ipvsrc().equals("0")) {
                binding.nc3ipvsrc.check(
                        jsonC3.getnc3ipvsrc().equals("1") ? binding.nc3ipvsrca.getId()
                                : binding.nc3ipvsrcb.getId()
                );
            }
            //            m1

            if (!jsonC3.getnc3m1().equals("0")) {
                binding.nc3m1.check(
                        jsonC3.getnc3m1().equals("1") ? binding.nc3m1a.getId()
                                : binding.nc3m1b.getId()
                );
            }
            if (!jsonC3.getnc3m1src().equals("0")) {
                binding.nc3m1src.check(
                        jsonC3.getnc3m1src().equals("1") ? binding.nc3m1srca.getId()
                                : binding.nc3m1srcb.getId()
                );
            }
            //            m2

            if (!jsonC3.getnc3m2().equals("0")) {
                binding.nc3m2.check(
                        jsonC3.getnc3m2().equals("1") ? binding.nc3m2a.getId()
                                : binding.nc3m2b.getId()
                );
            }
            if (!jsonC3.getnc3m2src().equals("0")) {
                binding.nc3m2src.check(
                        jsonC3.getnc3m2src().equals("1") ? binding.nc3m2srca.getId()
                                : binding.nc3m2srcb.getId()
                );
            }

//           nc305

            if (!jsonC3.getnc305().equals("0")) {
                binding.nc305.check(
                        jsonC3.getnc305().equals("1") ? binding.nc305a.getId()
                                : jsonC3.getnc305().equals("2") ? binding.nc305b.getId()
                                : binding.nc30598.getId()
                );
            }
            if (!jsonC3.getnc306().equals("0")) {
                binding.nc306.check(
                        jsonC3.getnc306().equals("1") ? binding.nc306a.getId()
                                : binding.nc306b.getId()
                );
            }

        }
    }

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
        //Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

//       nc302
        if (!validatorClass.EmptyRadioButton(this, binding.nc302, binding.nc302b, getString(R.string.nc302))) {
            return false;
        }
        if (binding.nc302a.isChecked()) {

//        nc303
            if (!validatorClass.EmptyRadioButton(this, binding.nc303, binding.nc303d, getString(R.string.nc303))) {
                return false;
            }
        }

//        nc3bcg
        if (!validatorClass.EmptyRadioButton(this, binding.nc3bcg, binding.nc3bcga, getString(R.string.nc3bcg) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3bcgsrc, binding.nc3bcgsrca, getString(R.string.nc3bcg) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3opv0
        if (!validatorClass.EmptyRadioButton(this, binding.nc3opv0, binding.nc3opv0a, getString(R.string.nc3opv0) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3opv0src, binding.nc3opv0srca, getString(R.string.nc3opv0) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3opv1
        if (!validatorClass.EmptyRadioButton(this, binding.nc3opv1, binding.nc3opv1a, getString(R.string.nc3opv1) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3opv1src, binding.nc3opv1srca, getString(R.string.nc3opv1) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3p1
        if (!validatorClass.EmptyRadioButton(this, binding.nc3p1, binding.nc3p1a, getString(R.string.nc3p1) + getString(R.string.nc3response))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, binding.nc3p1src, binding.nc3p1srca, getString(R.string.nc3p1) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3pcv1
        if (!validatorClass.EmptyRadioButton(this, binding.nc3pcv1, binding.nc3pcv1a, getString(R.string.nc3pcv1) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3pcv1src, binding.nc3pcv1srca, getString(R.string.nc3pcv1) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3opv2
        if (!validatorClass.EmptyRadioButton(this, binding.nc3opv2, binding.nc3opv2a, getString(R.string.nc3opv2) + getString(R.string.nc3response))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, binding.nc3opv2src, binding.nc3opv2srca, getString(R.string.nc3opv2) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3p2
        if (!validatorClass.EmptyRadioButton(this, binding.nc3p2, binding.nc3p2a, getString(R.string.nc3p2) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3p2src, binding.nc3p2srca, getString(R.string.nc3p2) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3pcv2
        if (!validatorClass.EmptyRadioButton(this, binding.nc3pcv2, binding.nc3pcv2a, getString(R.string.nc3pcv2) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3pcv2src, binding.nc3pcv2srca, getString(R.string.nc3pcv2) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3opv3
        if (!validatorClass.EmptyRadioButton(this, binding.nc3opv3, binding.nc3opv3a, getString(R.string.nc3opv3) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3opv3src, binding.nc3opv3srca, getString(R.string.nc3opv3) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3p3
        if (!validatorClass.EmptyRadioButton(this, binding.nc3p3, binding.nc3p3a, getString(R.string.nc3p3) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3p3src, binding.nc3p3srca, getString(R.string.nc3p3) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3pcv3
        if (!validatorClass.EmptyRadioButton(this, binding.nc3pcv3, binding.nc3pcv3a, getString(R.string.nc3pcv3) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3pcv3src, binding.nc3pcv3srca, getString(R.string.nc3pcv3) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3ipv3
        if (!validatorClass.EmptyRadioButton(this, binding.nc3ipv, binding.nc3ipva, getString(R.string.nc3ipv) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3ipvsrc, binding.nc3ipvsrca, getString(R.string.nc3ipv) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3m1dt
        if (!validatorClass.EmptyRadioButton(this, binding.nc3m1, binding.nc3m1a, getString(R.string.nc3m1) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3m1src, binding.nc3m1srca, getString(R.string.nc3m1) + getString(R.string.nc3src))) {
            return false;
        }


//        nc3m2dt
        if (!validatorClass.EmptyRadioButton(this, binding.nc3m2, binding.nc3m2a, getString(R.string.nc3m2) + getString(R.string.nc3response))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, binding.nc3m2src, binding.nc3m2srca, getString(R.string.nc3m2) + getString(R.string.nc3src))) {
            return false;
        }


//        nc305
        if (!validatorClass.EmptyRadioButton(this, binding.nc305, binding.nc305a, getString(R.string.nc305))) {
            return false;
        }

        if (binding.nc305a.isChecked()) {
//        nc306
            return validatorClass.EmptyRadioButton(this, binding.nc306, binding.nc306a, getString(R.string.nc306));
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
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sC3 = new JSONObject();
        if (backPressed) {
            sC3.put("updatedate_nc3", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        if (SectionC1Activity.editChildFlag) {
            sC3.put("edit_updatedate_sc2", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

//        nc301
        //sC3.put("nc3name", selectedChild.getName());
//        nc302
        //sC3.put("nc300Serial", selectedChild.getSerialNo());

//        nc302
        sC3.put("nc302", binding.nc302a.isChecked() ? "1"
                : binding.nc302b.isChecked() ? "2"
                : "0");

//        nc303
        sC3.put("nc303", binding.nc303a.isChecked() ? "1"
                : binding.nc303b.isChecked() ? "2"
                : binding.nc303c.isChecked() ? "3"
                : binding.nc303d.isChecked() ? "4"
                : "0");
//at birth
//          nc3bcg
        sC3.put("nc3bcg", binding.nc3bcga.isChecked() ? "1"
                : binding.nc3bcgb.isChecked() ? "2"
                : "0");
        sC3.put("nc3bcgsrc", binding.nc3bcgsrca.isChecked() ? "1"
                : binding.nc3bcgsrcb.isChecked() ? "2"
                : "0");


//          nc3opv0
        sC3.put("nc3opv0", binding.nc3opv0a.isChecked() ? "1"
                : binding.nc3opv0b.isChecked() ? "2"
                : "0");
        sC3.put("nc3opv0src", binding.nc3opv0srca.isChecked() ? "1"
                : binding.nc3opv0srcb.isChecked() ? "2"
                : "0");


//       at age of 6

//          nc3opv1
        sC3.put("nc3opv1", binding.nc3opv1a.isChecked() ? "1"
                : binding.nc3opv1b.isChecked() ? "2"
                : "0");
        sC3.put("nc3opv1src", binding.nc3opv1srca.isChecked() ? "1"
                : binding.nc3opv1srcb.isChecked() ? "2"
                : "0");

//          nc3p1
        sC3.put("nc3p1", binding.nc3p1a.isChecked() ? "1"
                : binding.nc3p1b.isChecked() ? "2"
                : "0");
        sC3.put("nc3p1src", binding.nc3p1srca.isChecked() ? "1"
                : binding.nc3p1srcb.isChecked() ? "2"
                : "0");


//          nc3pcv1
        sC3.put("nc3pcv1", binding.nc3pcv1a.isChecked() ? "1"
                : binding.nc3pcv1b.isChecked() ? "2"
                : "0");
        sC3.put("nc3pcv1src", binding.nc3pcv1srca.isChecked() ? "1"
                : binding.nc3pcv1srcb.isChecked() ? "2"
                : "0");

//       at age of 10 weeks

//          nc3opv2
        sC3.put("nc3opv2", binding.nc3opv2a.isChecked() ? "1"
                : binding.nc3opv2b.isChecked() ? "2"
                : "0");
        sC3.put("nc3opv2src", binding.nc3opv2srca.isChecked() ? "1"
                : binding.nc3opv2srcb.isChecked() ? "2"
                : "0");

//          nc3p2
        sC3.put("nc3p2", binding.nc3p2a.isChecked() ? "1"
                : binding.nc3p2b.isChecked() ? "2"
                : "0");
        sC3.put("nc3p2src", binding.nc3p2srca.isChecked() ? "1"
                : binding.nc3p2srcb.isChecked() ? "2"
                : "0");


//          nc3pcv2
        sC3.put("nc3pcv2", binding.nc3pcv2a.isChecked() ? "1"
                : binding.nc3pcv2b.isChecked() ? "2"
                : "0");
        sC3.put("nc3pcv2src", binding.nc3pcv2srca.isChecked() ? "1"
                : binding.nc3pcv2srcb.isChecked() ? "2"
                : "0");


//       at age of 14 weeks

//          nc3opv3
        sC3.put("nc3opv3", binding.nc3opv3a.isChecked() ? "1"
                : binding.nc3opv3b.isChecked() ? "2"
                : "0");
        sC3.put("nc3opv3src", binding.nc3opv3srca.isChecked() ? "1"
                : binding.nc3opv3srcb.isChecked() ? "2"
                : "0");

//          nc3p3
        sC3.put("nc3p3", binding.nc3p3a.isChecked() ? "1"
                : binding.nc3p3b.isChecked() ? "2"
                : "0");
        sC3.put("nc3p3src", binding.nc3p3srca.isChecked() ? "1"
                : binding.nc3p3srcb.isChecked() ? "2"
                : "0");


//          nc3pcv3
        sC3.put("nc3pcv3", binding.nc3pcv3a.isChecked() ? "1"
                : binding.nc3pcv3b.isChecked() ? "2"
                : "0");
        sC3.put("nc3pcv3src", binding.nc3pcv3srca.isChecked() ? "1"
                : binding.nc3pcv3srcb.isChecked() ? "2"
                : "0");

//          nc3ipv
        sC3.put("nc3ipv", binding.nc3ipva.isChecked() ? "1"
                : binding.nc3ipvb.isChecked() ? "2"
                : "0");
        sC3.put("nc3ipvsrc", binding.nc3ipvsrca.isChecked() ? "1"
                : binding.nc3ipvsrcb.isChecked() ? "2"
                : "0");


//at the age of 9 months
//          nc3m1
        sC3.put("nc3m1", binding.nc3m1a.isChecked() ? "1"
                : binding.nc3m1b.isChecked() ? "2"
                : "0");
        sC3.put("nc3m1src", binding.nc3m1srca.isChecked() ? "1"
                : binding.nc3m1srcb.isChecked() ? "2"
                : "0");


//at age of 15 months
//          nc3m2
        sC3.put("nc3m2", binding.nc3m2a.isChecked() ? "1"
                : binding.nc3m2b.isChecked() ? "2"
                : "0");
        sC3.put("nc3m2src", binding.nc3m2srca.isChecked() ? "1"
                : binding.nc3m2srcb.isChecked() ? "2"
                : "0");

//        nc305
        sC3.put("nc305", binding.nc305a.isChecked() ? "1"
                : binding.nc305b.isChecked() ? "2"
                : binding.nc30598.isChecked() ? "98"
                : "0");

//        nc306
        sC3.put("nc306", binding.nc306a.isChecked() ? "1"
                : binding.nc306b.isChecked() ? "2"
                : "0");


        MainApp.cc.setsC3(String.valueOf(sC3));


        //Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSC3();

        if (updcount == 1) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        formValidation();
    }
}

