package edu.aku.hassannaqvi.casi_2018.ui.household;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONA5ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionA5Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2018.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionA5Activity extends Menu2Activity implements TextWatcher, RadioGroup.OnCheckedChangeListener {

    //    static int deceasedCounter = 0;
    private final long DELAY = 1000;
    ActivitySectionA5Binding binding;
    public CheckBox.OnCheckedChangeListener check = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (binding.nh403a.isChecked() || binding.nh403b.isChecked() || binding.nh403c.isChecked()) {
                ClearClass.ClearAllFields(binding.fldGrnh404, false);
                ClearClass.ClearAllFields(binding.fldGrpnh405, false);
            } else {
                ClearClass.ClearAllFields(binding.fldGrnh404, true);
                ClearClass.ClearAllFields(binding.fldGrpnh405, true);
            }
        }
    };
    DatabaseHelper db;
    int recipientCounter = 0;
    int prevRecipientCounter = 0;
    Boolean backPressed = false;
    int prevDeceasedCounter = 0;
    String nh801, nh802;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_a5);
        db = new DatabaseHelper(this);

//        Assigning data to UI binding
        binding.setCallback(this);

        this.setTitle(getResources().getString(R.string.na5heading));

        binding.nh401.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //formValidation();
                if (!(checkedId == R.id.nh401a)) {

                    ClearClass.ClearAllFields(binding.fldGrpnh402, false);

                } else {
                    formValidation();
                    ClearClass.ClearAllFields(binding.fldGrpnh402, true);
                }
            }
        });

        binding.nh403a.setOnCheckedChangeListener(check);
        binding.nh403b.setOnCheckedChangeListener(check);
        binding.nh403c.setOnCheckedChangeListener(check);

        binding.nh403e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    binding.nh403a.setEnabled(false);
                    binding.nh403b.setEnabled(false);
                    binding.nh403c.setEnabled(false);
                    binding.nh403d.setEnabled(false);

                    binding.nh403a.setChecked(false);
                    binding.nh403b.setChecked(false);
                    binding.nh403c.setChecked(false);
                    binding.nh403d.setChecked(false);

                } else {


                    binding.nh403a.setEnabled(true);
                    binding.nh403b.setEnabled(true);
                    binding.nh403c.setEnabled(true);
                    binding.nh403d.setEnabled(true);
                }
            }
        });

        binding.nh404.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (checkedId == R.id.nh404b) {
                    ClearClass.ClearAllFields(binding.fldGrpnh405, false);

                } else {
                    ClearClass.ClearAllFields(binding.fldGrpnh405, true);
                }
            }
        });
        binding.nh405e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.nh405a.setEnabled(false);
                    binding.nh405b.setEnabled(false);
                    binding.nh405c.setEnabled(false);
                    binding.nh405d.setEnabled(false);

                    binding.nh405a.setChecked(false);
                    binding.nh405b.setChecked(false);
                    binding.nh405c.setChecked(false);
                    binding.nh405d.setChecked(false);

                } else {
                    binding.nh405a.setEnabled(true);
                    binding.nh405b.setEnabled(true);
                    binding.nh405c.setEnabled(true);
                    binding.nh405d.setEnabled(true);
                }
            }
        });

        binding.nh501.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                formValidation();
                if (checkedId == R.id.nh501d || checkedId == R.id.nh50196) {
                    ClearClass.ClearAllFields(binding.fldGrnh602, false);
                    ClearClass.ClearAllFields(binding.fldGrnc502, false);

                } else {
                    ClearClass.ClearAllFields(binding.fldGrnh602, true);
                    ClearClass.ClearAllFields(binding.fldGrnc502, true);

                }
            }
        });

//        Listeners
        binding.nh402.setOnCheckedChangeListener(this);
        binding.nh40601.setOnCheckedChangeListener(this);
        binding.nh40602.setOnCheckedChangeListener(this);
        binding.nh40603.setOnCheckedChangeListener(this);
        binding.nh40604.setOnCheckedChangeListener(this);
        binding.nh40605.setOnCheckedChangeListener(this);
        binding.nh40696.setOnCheckedChangeListener(this);
        //binding.nh501.setOnCheckedChangeListener(this);
        binding.nh502.setOnCheckedChangeListener(this);
        binding.nh503.setOnCheckedChangeListener(this);

        binding.nh701.setOnCheckedChangeListener(this);

        binding.nh702.addTextChangedListener(this);

//        Validation Boolean
        MainApp.validateFlag = false;

        if (SectionA1Activity.editFormFlag) {
            AutoPopulate();
        }
    }

    private void AutoPopulate() {
        FormsContract formContract = db.getsA5();
        if (!formContract.getsA5().equals("")) {

            JSONA5ModelClass jsonA5 = JSONUtilClass.getModelFromJSON(formContract.getsA5(), JSONA5ModelClass.class);

            if (!jsonA5.getnh401().equals("0")) {
                binding.nh401.check(
                        jsonA5.getnh401().equals("1") ? binding.nh401a.getId() :
                                jsonA5.getnh401().equals("2") ? binding.nh401b.getId() :
                                        jsonA5.getnh401().equals("3") ? binding.nh401c.getId() :
                                                binding.nh401d.getId()
                );
            }
            if (!jsonA5.getnh402().equals("0")) {
                binding.nh402.check(
                        jsonA5.getnh402().equals("1") ? binding.nh402a.getId() :
                                binding.nh402b.getId()
                );
            }
            if (!jsonA5.getnh403a().equals("0")) {
                binding.nh403a.setChecked(true);
            }
            if (!jsonA5.getnh403b().equals("0")) {
                binding.nh403b.setChecked(true);
            }
            if (!jsonA5.getnh403c().equals("0")) {
                binding.nh403c.setChecked(true);
            }
            if (!jsonA5.getnh403d().equals("0")) {
                binding.nh403d.setChecked(true);
            }
            if (!jsonA5.getnh403e().equals("0")) {
                binding.nh403e.setChecked(true);
            }

            if (!jsonA5.getnh404().equals("0")) {
                binding.nh404.check(
                        jsonA5.getnh404().equals("1") ? binding.nh404a.getId() :
                                binding.nh404b.getId()
                );
            }
            if (!jsonA5.getnh405a().equals("0")) {
                binding.nh405a.setChecked(true);
            }

            if (!jsonA5.getnh405b().equals("0")) {
                binding.nh405b.setChecked(true);
            }
            if (!jsonA5.getnh405c().equals("0")) {
                binding.nh405c.setChecked(true);
            }
            if (!jsonA5.getnh405d().equals("0")) {
                binding.nh405d.setChecked(true);
            }
            if (!jsonA5.getnh405e().equals("0")) {
                binding.nh405e.setChecked(true);
            }
            if (!jsonA5.getnh40601().equals("0")) {
                binding.nh40601.check(
                        jsonA5.getnh404().equals("1") ? binding.nh40601a.getId() :
                                binding.nh40601b.getId()
                );
            }
            if (!jsonA5.getnh40602().equals("0")) {
                binding.nh40602.check(
                        jsonA5.getnh40602().equals("1") ? binding.nh40602a.getId() :
                                binding.nh40602b.getId()
                );
            }
            if (!jsonA5.getnh40603().equals("0")) {
                binding.nh40603.check(
                        jsonA5.getnh404().equals("1") ? binding.nh40603a.getId() :
                                binding.nh40603b.getId()
                );
            }
            if (!jsonA5.getnh40604().equals("0")) {
                binding.nh40604.check(
                        jsonA5.getnh404().equals("1") ? binding.nh40604a.getId() :
                                binding.nh40604b.getId()
                );
            }
            if (!jsonA5.getnh40605().equals("0")) {
                binding.nh40605.check(
                        jsonA5.getnh404().equals("1") ? binding.nh40605a.getId() :
                                binding.nh40605b.getId()
                );
            }
            if (!jsonA5.getnh40696().equals("0")) {
                binding.nh40696.check(
                        jsonA5.getnh404().equals("1") ? binding.nh40696a.getId() :
                                binding.nh40696b.getId()
                );
            }
            binding.nh40696x.setText(jsonA5.getnh40696x());

            if (!jsonA5.getnh501().equals("0")) {
                binding.nh501.check(
                        jsonA5.getnh501().equals("1") ? binding.nh501a.getId() :
                                jsonA5.getnh501().equals("2") ? binding.nh501b.getId() :
                                        jsonA5.getnh501().equals("3") ? binding.nh501c.getId() :
                                                jsonA5.getnh501().equals("4") ? binding.nh501d.getId() :
                                                        binding.nh50196.getId()
                );
            }
            binding.nh50196x.setText(jsonA5.getnh50196x());
            if (!jsonA5.getnh502().equals("0")) {
                binding.nh502.check(
                        jsonA5.getnh502().equals("1") ? binding.nh502a.getId() :
                                jsonA5.getnh502().equals("2") ? binding.nh502b.getId() :
                                        binding.nh502c.getId()
                );
            }
            if (!jsonA5.getnh503().equals("0")) {
                binding.nh503.check(
                        jsonA5.getnh503().equals("1") ? binding.nh503a.getId() :
                                jsonA5.getnh503().equals("2") ? binding.nh503b.getId() :
                                        jsonA5.getnh503().equals("3") ? binding.nh503c.getId() :
                                                binding.nh503d.getId()
                );
            }

            if (!jsonA5.getnh701().equals("0")) {
                binding.nh701.check(
                        jsonA5.getnh701().equals("1") ? binding.nh701a.getId() :
                                binding.nh701b.getId()

                );
            }
            binding.nh702.setText(jsonA5.getnh702());

            if (jsonA5.getnh701().equals("2")) {
                binding.nh701a.setEnabled(false);
            }
            if (!jsonA5.getnh702().equals("")) {
                prevRecipientCounter = Integer.valueOf(jsonA5.getnh702());

            }
           /*
            if (!jsonA5.getnh801().equals("0")) {
                binding.nh801.check(
                        jsonA5.getnh801().equals("1") ? binding.nh801a.getId() :
                                binding.nh801b.getId()

                );
            }
            binding.nh802.setText(jsonA5.getnh802());

            if (jsonA5.getnh801().equals("2")) {
                binding.nh801a.setEnabled(false);
            }
            if (!jsonA5.getnh802().equals("")) {
                prevDeceasedCounter = Integer.valueOf(jsonA5.getnh802());
            }

            */

            if (!jsonA5.getnh801().equals("0")) {
                nh801 = jsonA5.getnh801().equals("1") ? "1" :
                        "2";
            }
            if (!jsonA5.getnh802().equals("")) {
                nh802 = jsonA5.getnh802();
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

                if (recipientCounter > 0) {

                    if (recipientCounter < prevRecipientCounter) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                SectionA5Activity.this);
                        alertDialogBuilder
                                .setMessage("In previous you saved " + prevRecipientCounter + " Recipient.\n" +
                                        "Do you want to continue it?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {

                                                finish();

                                                startActivity(new Intent(SectionA5Activity.this,
                                                        SectionA8AActivity.class).putExtra("recCounter", recipientCounter));
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();

                    } else {
                        finish();
                        startActivity(new Intent(this, SectionA8AActivity.class).putExtra("recCounter", recipientCounter));
                    }
                }
                /*
                else if (deceasedCounter > 0) {
//                    startActivity(new Intent(this, SectionH8Activity.class));

                    if (deceasedCounter < prevDeceasedCounter) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                SectionA5Activity.this);
                        alertDialogBuilder
                                .setMessage("In previous you saved " + prevDeceasedCounter + " Deceased.\n" +
                                        "Do you want to continue it?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {

                                                deceasedCounter = prevDeceasedCounter;
                                                finish();
//                                                startActivity(new Intent(SectionA5Activity.this, SectionH8Activity.class));
                                                startActivity(new Intent(SectionA5Activity.this, SectionH8infoActivity.class));
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();

                    } else {
                        finish();
//                        startActivity(new Intent(SectionA5Activity.this, SectionH8Activity.class));
                        startActivity(new Intent(SectionA5Activity.this, SectionH8infoActivity.class));
                    }

                }
                */
                else {
                    finish();
                    if (SectionA1Activity.editFormFlag) {
                        startActivity(new Intent(this, SectionH8infoActivity.class));

                       /* startActivity(new Intent(this, ViewMemberActivity.class)
                                .putExtra("flagEdit", false)
                                .putExtra("comingBack", true)
                                .putExtra("cluster", MainApp.fc.getClusterNo())
                                .putExtra("hhno", MainApp.fc.getHhNo())
                        );*/
                    } else {
/*
                        if (!MainApp.UpdateSummary(this, db, 1)) {
                            Toast.makeText(this, "Summary Table not update!!", Toast.LENGTH_SHORT).show();
                        }*/

//                        startActivity(new Intent(this, ViewMemberActivity.class).putExtra("activity", 1));
                        startActivity(new Intent(this, SectionH8infoActivity.class));
                    }
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }

    public boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, binding.fldGrpsectionA5);
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


    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sA5 = new JSONObject();
        if (SectionA1Activity.editFormFlag) {
            sA5.put("edit_updatedate_sa5", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

        }
        sA5.put("nh401", binding.nh401a.isChecked() ? "1"
                : binding.nh401b.isChecked() ? "2"
                : binding.nh401c.isChecked() ? "3"
                : binding.nh401d.isChecked() ? "4"
                : "0");

        sA5.put("nh402", binding.nh402a.isChecked() ? "1"
                : binding.nh402b.isChecked() ? "2"
                : "0");

        sA5.put("nh403a", binding.nh403a.isChecked() ? "1" : "0");
        sA5.put("nh403b", binding.nh403b.isChecked() ? "2" : "0");
        sA5.put("nh403c", binding.nh403c.isChecked() ? "3" : "0");
        sA5.put("nh403d", binding.nh403d.isChecked() ? "4" : "0");
        sA5.put("nh403e", binding.nh403e.isChecked() ? "5" : "0");

        sA5.put("nh404", binding.nh404a.isChecked() ? "1"
                : binding.nh404b.isChecked() ? "2"
                : "0");

        sA5.put("nh405a", binding.nh405a.isChecked() ? "1" : "0");
        sA5.put("nh405b", binding.nh405b.isChecked() ? "2" : "0");
        sA5.put("nh405c", binding.nh405c.isChecked() ? "3" : "0");
        sA5.put("nh405d", binding.nh405d.isChecked() ? "4" : "0");
        sA5.put("nh405e", binding.nh405e.isChecked() ? "5" : "0");


        sA5.put("nh40601", binding.nh40601a.isChecked() ? "1"
                : binding.nh40601b.isChecked() ? "2"
                : "0");


        sA5.put("nh40602", binding.nh40602a.isChecked() ? "1"
                : binding.nh40602b.isChecked() ? "2"
                : "0");

        sA5.put("nh40603", binding.nh40603a.isChecked() ? "1"
                : binding.nh40603b.isChecked() ? "2"
                : "0");

        sA5.put("nh40604", binding.nh40604a.isChecked() ? "1"
                : binding.nh40604b.isChecked() ? "2"
                : "0");

        sA5.put("nh40605", binding.nh40605a.isChecked() ? "1"
                : binding.nh40605b.isChecked() ? "2"
                : "0");

        sA5.put("nh40696", binding.nh40696a.isChecked() ? "1"
                : binding.nh40696b.isChecked() ? "2"
                : "0");

        sA5.put("nh40696x", binding.nh40696x.getText().toString());


        // Section A6

        sA5.put("nh501", binding.nh501a.isChecked() ? "1"
                : binding.nh501b.isChecked() ? "2"
                : binding.nh501c.isChecked() ? "3"
                : binding.nh501d.isChecked() ? "4"
                : binding.nh50196.isChecked() ? "96"
                : "0");

        sA5.put("nh50196x", binding.nh50196x.getText().toString());

        sA5.put("nh502", binding.nh502a.isChecked() ? "1"
                : binding.nh502b.isChecked() ? "2"
                : binding.nh502c.isChecked() ? "3"
                : "0");

        sA5.put("nh503", binding.nh503a.isChecked() ? "1"
                : binding.nh503b.isChecked() ? "2"
                : binding.nh503c.isChecked() ? "3"
                : binding.nh503d.isChecked() ? "4"
                : "0");

        //Section A6
        sA5.put("cih601", binding.cih601a.isChecked() ? "1"
                : binding.cih601b.isChecked() ? "2"
                : "0");
        sA5.put("cih602", binding.cih602a.isChecked() ? "1"
                : binding.cih602b.isChecked() ? "2"
                : binding.cih602c.isChecked() ? "3"
                : "0");
        sA5.put("cih603", binding.cih603a.isChecked() ? "1"
                : binding.cih603b.isChecked() ? "2"
                : "0");
        sA5.put("cih604", binding.cih604a.isChecked() ? "1"
                : binding.cih604b.isChecked() ? "2"
                : binding.cih604c.isChecked() ? "3"
                : "0");
        sA5.put("cih605", binding.cih605a.isChecked() ? "1"
                : binding.cih605b.isChecked() ? "2"
                : "0");
        sA5.put("cih606", binding.cih606a.isChecked() ? "1"
                : binding.cih606b.isChecked() ? "2"
                : binding.cih606c.isChecked() ? "3"
                : "0");

        sA5.put("cih607", binding.cih607a.isChecked() ? "1"
                : binding.cih607b.isChecked() ? "2"
                : "0");

        sA5.put("cih608", binding.cih608a.isChecked() ? "1"
                : binding.cih608b.isChecked() ? "2"
                : binding.cih608c.isChecked() ? "3"
                : "0");


        sA5.put("cih609", binding.cih609a.isChecked() ? "1"
                : binding.cih609b.isChecked() ? "2"
                : "0");

        sA5.put("cih610", binding.cih610a.isChecked() ? "1"
                : binding.cih610b.isChecked() ? "2"
                : binding.cih610c.isChecked() ? "3"
                : "0");
        sA5.put("cih611", binding.cih611a.isChecked() ? "1"
                : binding.cih611b.isChecked() ? "2"
                : "0");
        sA5.put("cih612", binding.cih612a.isChecked() ? "1"
                : binding.cih612b.isChecked() ? "2"
                : binding.cih612c.isChecked() ? "3"
                : "0");
        sA5.put("cih613", binding.cih613a.isChecked() ? "1"
                : binding.cih613b.isChecked() ? "2"
                : "0");
        sA5.put("cih614", binding.cih614a.isChecked() ? "1"
                : binding.cih614b.isChecked() ? "2"
                : binding.cih614c.isChecked() ? "3"
                : "0");
        sA5.put("cih615", binding.cih615a.isChecked() ? "1"
                : binding.cih615b.isChecked() ? "2"
                : "0");

        sA5.put("cih616", binding.cih616a.isChecked() ? "1"
                : binding.cih616b.isChecked() ? "2"
                : binding.cih616c.isChecked() ? "2"
                : "0");
        sA5.put("cih617", binding.cih617a.isChecked() ? "1"
                : binding.cih617b.isChecked() ? "2"
                : "0");
        sA5.put("cih618", binding.cih618a.isChecked() ? "1"
                : binding.cih618b.isChecked() ? "2"
                : binding.cih618c.isChecked() ? "3"
                : "0");


//        sA5.put("nh601", binding.nh601a.isChecked() ? "1"
//                : binding.nh601b.isChecked() ? "2"
//                : binding.nh60198.isChecked() ? "98"
//                : binding.nh60199.isChecked() ? "99"
//                : "0");
//
//        sA5.put("nh602", binding.nh602a.isChecked() ? "1"
//                : binding.nh602b.isChecked() ? "2"
//                : binding.nh60298.isChecked() ? "98"
//                : binding.nh60299.isChecked() ? "99"
//                : "0");
//
//        sA5.put("nh603", binding.nh603a.isChecked() ? "1"
//                : binding.nh603b.isChecked() ? "2"
//                : binding.nh60398.isChecked() ? "98"
//                : binding.nh60399.isChecked() ? "99"
//                : "0");
//
//        sA5.put("nh604", binding.nh604a.isChecked() ? "1"
//                : binding.nh604b.isChecked() ? "2"
//                : binding.nh60498.isChecked() ? "98"
//                : binding.nh60499.isChecked() ? "99"
//                : "0");
//
//        sA5.put("nh605", binding.nh605a.isChecked() ? "1"
//                : binding.nh605b.isChecked() ? "2"
//                : binding.nh60598.isChecked() ? "98"
//                : binding.nh60599.isChecked() ? "99"
//                : "0");
//
//        sA5.put("nh606", binding.nh606a.isChecked() ? "1"
//                : binding.nh606b.isChecked() ? "2"
//                : binding.nh60698.isChecked() ? "98"
//                : binding.nh60699.isChecked() ? "99"
//                : "0");
//
//        sA5.put("nh607", binding.nh607a.isChecked() ? "1"
//                : binding.nh607b.isChecked() ? "2"
//                : binding.nh60798.isChecked() ? "98"
//                : binding.nh60799.isChecked() ? "99"
//                : "0");
//
//        sA5.put("nh608", binding.nh608a.isChecked() ? "1"
//                : binding.nh608b.isChecked() ? "2"
//                : binding.nh60898.isChecked() ? "98"
//                : binding.nh60899.isChecked() ? "99"
//                : "0");
//
//        sA5.put("nh609", binding.nh609a.isChecked() ? "1"
//                : binding.nh609b.isChecked() ? "2"
//                : binding.nh60998.isChecked() ? "98"
//                : binding.nh60999.isChecked() ? "99"
//                : "0");

        // Section A8

        sA5.put("nh701", binding.nh701a.isChecked() ? "1"
                : binding.nh701b.isChecked() ? "2"
                : "0");

        sA5.put("nh702", binding.nh702.getText().toString());

        if (binding.nh701a.isChecked()) {
            recipientCounter = Integer.valueOf(binding.nh702.getText().toString());
        }

        if (SectionA1Activity.editFormFlag) {
            sA5.put("nh801", nh801);
            sA5.put("nh802", nh802);
        }
/*
        sA5.put("nh801", binding.nh801a.isChecked() ? "1" : binding.nh801b.isChecked() ? "2" : "0");
        sA5.put("nh802", binding.nh802.getText().toString());

        if (binding.nh801a.isChecked()) {
            deceasedCounter = Integer.valueOf(binding.nh802.getText().toString());
        }
*/
        MainApp.fc.setsA5(String.valueOf(sA5));

        // Set summary fields
        MainApp.sumc = MainApp.AddSummary(MainApp.fc, 1);

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSA5();

        if (updcount == 1) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            public void run() {
                                formValidation();
                            }
                        });

                    }
                },
                DELAY
        );
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        formValidation();
    }


}
