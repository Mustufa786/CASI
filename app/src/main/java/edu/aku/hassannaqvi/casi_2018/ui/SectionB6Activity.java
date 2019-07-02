package edu.aku.hassannaqvi.casi_2018.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Timer;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONB6ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.NutritionContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionB6Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionB6Activity extends Menu2Activity {

    private final long DELAY = 1000;
    ActivitySectionB6Binding bi;
    DatabaseHelper db;
    Boolean firstTimePressed = false;
    Boolean backPressed = false;
    Boolean frontPressed = false;
    NutritionContract nutritionCC;
    String uid = "";
    String classPassName = "";
    JSONB6ModelClass jsonB6;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_b6);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b6);
        db = new DatabaseHelper(this);
        bi.setCallback(this);

        this.setTitle(getResources().getString(R.string.nw5heading));
        bi.textName.setText("Selected Woman : " + SectionB1Activity.wraName);

        settingTimeToEat();
        setListners();

//        if (getIntent().getBooleanExtra("backPressed", false)) {
//            frontPressed = true;

        Collection<NutritionContract> nutritionContracts = db.getPressedNutrition();

        for (NutritionContract nutritionContract : nutritionContracts) {
            jsonB6 = JSONUtilClass.getModelFromJSON(nutritionContract.getsB6(), JSONB6ModelClass.class);

            if (jsonB6.getSerial().equals(String.valueOf(MainApp.nuCount))) {

                frontPressed = true;

                nutritionCC = nutritionContract;

                if (jsonB6.getnw501a().equals("1")) {
                    bi.nw501a.setChecked(true);
                }
                if (jsonB6.getnw501b().equals("1")) {
                    bi.nw501b.setChecked(true);
                }
                if (jsonB6.getnw501c().equals("1")) {
                    bi.nw501c.setChecked(true);
                }
                if (jsonB6.getnw501d().equals("1")) {
                    bi.nw501d.setChecked(true);
                }
                if (jsonB6.getnw501e().equals("1")) {
                    bi.nw501e.setChecked(true);
                }
                if (jsonB6.getnw501f().equals("1")) {
                    bi.nw501f.setChecked(true);
                }
                if (jsonB6.getnw501g().equals("1")) {
                    bi.nw501g.setChecked(true);
                }
                if (jsonB6.getnw501h().equals("1")) {
                    bi.nw501h.setChecked(true);
                }
                if (jsonB6.getnw501i().equals("1")) {
                    bi.nw501i.setChecked(true);
                }
                if (jsonB6.getnw501j().equals("1")) {
                    bi.nw501j.setChecked(true);
                }
                if (jsonB6.getnw501none().equals("1")) {
                    bi.nw501none.setChecked(true);
                }

            }
        }


//        }


//        Validation Boolean
        MainApp.validateFlag = false;

    }

    private void setListners() {
        bi.nw501none.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501a.setChecked(false);
                    bi.nw501b.setChecked(false);
                    bi.nw501c.setChecked(false);
                    bi.nw501d.setChecked(false);
                    bi.nw501e.setChecked(false);
                    bi.nw501f.setChecked(false);
                    bi.nw501g.setChecked(false);
                    bi.nw501h.setChecked(false);
                    bi.nw501i.setChecked(false);
                    bi.nw501j.setChecked(false);

                    bi.nw501a.setEnabled(false);
                    bi.nw501b.setEnabled(false);
                    bi.nw501c.setEnabled(false);
                    bi.nw501d.setEnabled(false);
                    bi.nw501e.setEnabled(false);
                    bi.nw501f.setEnabled(false);
                    bi.nw501g.setEnabled(false);
                    bi.nw501h.setEnabled(false);
                    bi.nw501i.setEnabled(false);
                    bi.nw501j.setEnabled(false);
                } else {
                    bi.nw501a.setEnabled(true);
                    bi.nw501b.setEnabled(true);
                    bi.nw501c.setEnabled(true);
                    bi.nw501d.setEnabled(true);
                    bi.nw501e.setEnabled(true);
                    bi.nw501f.setEnabled(true);
                    bi.nw501g.setEnabled(true);
                    bi.nw501h.setEnabled(true);
                    bi.nw501i.setEnabled(true);
                    bi.nw501j.setEnabled(true);

                }

            }
        });


        bi.nw501a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501none.setChecked(false);
                    bi.nw501none.setEnabled(false);
                } else {
                    bi.nw501none.setEnabled(true);
                }
            }
        });

        bi.nw501b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501none.setChecked(false);
                    bi.nw501none.setEnabled(false);
                } else {
                    bi.nw501none.setEnabled(true);
                }
            }
        });

        bi.nw501c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501none.setChecked(false);
                    bi.nw501none.setEnabled(false);
                } else {
                    bi.nw501none.setEnabled(true);
                }
            }
        });

        bi.nw501d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501none.setChecked(false);
                    bi.nw501none.setEnabled(false);
                } else {
                    bi.nw501none.setEnabled(true);
                }
            }
        });

        bi.nw501e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501none.setChecked(false);
                    bi.nw501none.setEnabled(false);
                } else {
                    bi.nw501none.setEnabled(true);
                }
            }
        });

        bi.nw501f.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501none.setChecked(false);
                    bi.nw501none.setEnabled(false);
                } else {
                    bi.nw501none.setEnabled(true);
                }
            }
        });

        bi.nw501g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501none.setChecked(false);
                    bi.nw501none.setEnabled(false);
                } else {
                    bi.nw501none.setEnabled(true);
                }
            }
        });

        bi.nw501h.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501none.setChecked(false);
                    bi.nw501none.setEnabled(false);
                } else {
                    bi.nw501none.setEnabled(true);
                }
            }
        });

        bi.nw501i.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501none.setChecked(false);
                    bi.nw501none.setEnabled(false);
                } else {
                    bi.nw501none.setEnabled(true);
                }
            }
        });

        bi.nw501j.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nw501none.setChecked(false);
                    bi.nw501none.setEnabled(false);
                } else {
                    bi.nw501none.setEnabled(true);
                }
            }
        });

    }

    private void settingTimeToEat() {
        if (MainApp.nuCount == 1) {
            bi.nw501.setText(R.string._1);
        } else if (MainApp.nuCount == 2) {
            bi.nw501.setText(R.string._2);
        } else if (MainApp.nuCount == 3) {
            bi.nw501.setText(R.string._3);
        } else if (MainApp.nuCount == 4) {
            bi.nw501.setText(R.string._4);
        } else if (MainApp.nuCount == 5) {
            bi.nw501.setText(R.string._5);
        } else if (MainApp.nuCount == 6) {
            bi.nw501.setText(R.string._6);
        } else if (MainApp.nuCount == 7) {
            bi.nw501.setText(R.string._7);
        }
    }

    public void BtnContinue() {

//        Validation Boolean
        MainApp.validateFlag = true;

        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

//                finish();

                MainApp.B6Flag = false;

                if (MainApp.nuCount == 7) {

                    /*int childcount = 0;
                    if (MainApp.childUnder5.size() > 0) {
                        for (FamilyMembersContract fmc : MainApp.childUnder5) {
                            if (fmc.getMotherId().equals(MainApp.mc.getB1SerialNo())) {
                                childcount++;
                            }
                        }
                        if (childcount < 1) {
                            startActivity(new Intent(this, MotherEndingActivity.class)
                                    .putExtra("checkingFlag", true)
                                    .putExtra("complete", true));

                        } else {
                            startActivity(new Intent(this, SectionC1Activity.class));
                        }

                    } else*/
                    if (SectionB1Activity.editWRAFlag) {
                        finish();
                        startActivity(new Intent(this, ViewMemberActivity.class)
                                .putExtra("flagEdit", false)
                                .putExtra("comingBack", true)
                                .putExtra("cluster", MainApp.mc.getCluster())
                                .putExtra("hhno", MainApp.mc.getHhno())
                        );
                    } else {
                        startActivity(new Intent(this, MotherEndingActivity.class)
                                .putExtra("checkingFlag", true)
                                .putExtra("complete", true));
                    }
                } else {

                    MainApp.nuCount++;

//                    finish();

                    startActivityForResult(new Intent(this, SectionB6Activity.class)
                            .putExtra("backPressed", classPassName.equals(SectionB1AActivity.class.getName())), 1);

                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void BtnEnd() {

        if (MainApp.nuCount == 1) {
            MainApp.B6Flag = true;
            MainApp.B2B6Flag = false;
        }

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

        return ValidatorClass.EmptyCheckBox(this, bi.fldGrpnw501check, bi.nw501a, getString(R.string.nw501a));

    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();


        MainApp.nc = new NutritionContract();

        JSONObject sB6 = new JSONObject();

        if (!backPressed && !frontPressed) {
            if (SectionB1Activity.editWRAFlag) {
                MainApp.nc.setDevicetagID(MainApp.mc.getDevicetagID());
                MainApp.nc.setFormDate(MainApp.mc.getFormDate());
                MainApp.nc.setUser(MainApp.mc.getUser());
                MainApp.nc.setDeviceId(MainApp.mc.getDeviceId());
                MainApp.nc.setApp_ver(MainApp.mc.getApp_ver());
                MainApp.nc.set_UUID(MainApp.mc.get_UUID());
                MainApp.nc.setMUID(MainApp.mc.get_UID());
                MainApp.nc.setFMUID(MainApp.mc.getFMUID());

                sB6.put("cluster_no", MainApp.mc.getCluster());
                sB6.put("hhno", MainApp.mc.getHhno());

            } else {
                MainApp.nc.setDevicetagID(MainApp.fc.getDevicetagID());
                MainApp.nc.setFormDate(MainApp.fc.getFormDate());
                MainApp.nc.setUser(MainApp.fc.getUser());
                MainApp.nc.setDeviceId(MainApp.fc.getDeviceID());
                MainApp.nc.setApp_ver(MainApp.fc.getAppversion());
                MainApp.nc.set_UUID(MainApp.fc.getUID());
                MainApp.nc.setMUID(MainApp.mc.get_UID());
                MainApp.nc.setFMUID(MainApp.mc.getFMUID());

                sB6.put("cluster_no", MainApp.fc.getClusterNo());
                sB6.put("hhno", MainApp.fc.getHhNo());
            }
        } else {
            MainApp.nc.setUpdatedate(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

            if (frontPressed) {
                MainApp.nc.set_UID(nutritionCC.get_UID());
            } else if (backPressed) {
                MainApp.nc.set_UID(uid);
            }

            if (SectionB1Activity.editWRAFlag && !frontPressed) {
                sB6.put("edit_updatedate_nw1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

                sB6.put("cluster_no", jsonB6.getCluster_no());
                sB6.put("hhno", jsonB6.getHhno());

            } else if (SectionB1Activity.editWRAFlag) {

                sB6.put("cluster_no", jsonB6.getCluster_no());
                sB6.put("hhno", jsonB6.getHhno());

            } else {

                sB6.put("cluster_no", MainApp.fc.getClusterNo());
                sB6.put("hhno", MainApp.fc.getHhNo());

            }

        }

        //sB6.put("MUID", MainApp.mc.get_UID());

        sB6.put("tpsno", String.valueOf(MainApp.nuCount));
        sB6.put("wra_lno", MainApp.mc.getB1SerialNo());
        //       nw501
        sB6.put("nw501a", bi.nw501a.isChecked() ? "1"
                : "2");
        sB6.put("nw501b", bi.nw501b.isChecked() ? "1"
                : "2");
        sB6.put("nw501c", bi.nw501c.isChecked() ? "1"
                : "2");
        sB6.put("nw501d", bi.nw501d.isChecked() ? "1"
                : "2");
        sB6.put("nw501e", bi.nw501e.isChecked() ? "1"
                : "2");
        sB6.put("nw501f", bi.nw501f.isChecked() ? "1"
                : "2");
        sB6.put("nw501g", bi.nw501g.isChecked() ? "1"
                : "2");
        sB6.put("nw501h", bi.nw501h.isChecked() ? "1"
                : "2");
        sB6.put("nw501i", bi.nw501i.isChecked() ? "1"
                : "2");
        sB6.put("nw501j", bi.nw501j.isChecked() ? "1"
                : "2");
        sB6.put("nw501none", bi.nw501none.isChecked() ? "1"
                : "2");

        if (backPressed) {
            sB6.put("backPressed", backPressed);
        } else if (frontPressed) {
            sB6.put("frontPressed", frontPressed);
        }

        MainApp.nc.setsB6(String.valueOf(sB6));

    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        if (!backPressed && !frontPressed) {

            Long updcount = db.addNutrition(MainApp.nc, 0);
            MainApp.nc.set_ID(String.valueOf(updcount));

            if (updcount != 0) {
                //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

                MainApp.nc.set_UID(
                        (MainApp.nc.getDeviceId() + MainApp.nc.get_ID()));
                db.updateNutritionID();

                /*MainApp.mc.setsB6("1");
                db.updateWRAB6();*/

                uid = MainApp.nc.getDeviceId() + MainApp.nc.get_ID();

                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {

            Long updcount = db.addNutrition(MainApp.nc, 1);
            if (updcount != 0) {
                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
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

    @Override
    public void onBackPressed() {

        try {
            SaveDraft();
            UpdateDB();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (MainApp.nuCount != 7) {
            MainApp.nuCount--;
        }

        if (MainApp.nuCount == 1) {
            MainApp.B6Flag = true;
            MainApp.B2B6Flag = false;
        }

        Intent intent = new Intent();
        intent.putExtra("backPressedClass", SectionB6Activity.class.getName());
        setResult(RESULT_OK, intent);

        super.onBackPressed();
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


}
