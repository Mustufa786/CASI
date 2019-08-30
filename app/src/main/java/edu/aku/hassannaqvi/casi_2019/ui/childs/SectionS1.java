package edu.aku.hassannaqvi.casi_2019.ui.childs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import butterknife.BindViews;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionC1Binding;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionS1Binding;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.ChildContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;

public class SectionS1 extends AppCompatActivity {

    ActivitySectionS1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_s1);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }

    //
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

                frontPressed = true;

                if (!editChildFlag) {
                    if (isNA) {
                        NAChildsize = MainApp.childNA.size();
                    } else {
                        Childsize = MainApp.childUnder5.size();
                    }
                }

                if (ageInMontsbyDob < 24) {
                    startActivity(new Intent(this, SectionC2Activity.class)
                            .putExtra("selectedChild", editChildFlag ? getIntent().getSerializableExtra("childFMClass") :
                                    childMap.get(binding.cic101.getSelectedItem().toString()))
                            .putExtra("backPressed", backPressed));

                } else if (ageInMontsbyDob >= 24 && ageInMontsbyDob < 60) {
                    startActivity(new Intent(this, SectionC5Activity.class)
                            .putExtra("selectedChild", editChildFlag ? getIntent().getSerializableExtra("childFMClass") :
                                    childMap.get(binding.cic101.getSelectedItem().toString()))
                            .putExtra("backPressed", backPressed));

                } else if (ageInMontsbyDob >= 60) {

                    if (editChildFlag) {
                        finish();
                        startActivity(new Intent(this, ViewMemberActivity.class)
                                .putExtra("flagEdit", false)
                                .putExtra("comingBack", true)
                                .putExtra("cluster", MainApp.cc.getClusterno())
                                .putExtra("hhno", MainApp.cc.getHhno())
                        );
                    } else {
                        startActivity(new Intent(this, ChildEndingActivity.class)
                                .putExtra("childINEligibile", true));
                    }

                }

               /* startActivity(new Intent(this, SectionC5Activity.class)
                        .putExtra("selectedChild", childMap.get(binding.cic101.getSelectedItem().toString())));*/
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {

//        Validation Boolean
        MainApp.validateFlag = true;

        endflag = true;
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {


                //finish();
                if (editChildFlag) {
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

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() throws JSONException {


        JSONObject sS1 = new JSONObject();

        if (!backPressed) {
            MainApp.cc = new ChildContract();
            MainApp.cc.setDevicetagID(MainApp.fc.getDevicetagID());
            MainApp.cc.setFormDate(MainApp.fc.getFormDate());
            MainApp.cc.setUser(MainApp.fc.getUser());
            MainApp.cc.setDeviceID(MainApp.fc.getDeviceID());
            MainApp.cc.setAppversion(MainApp.fc.getAppversion());
            MainApp.cc.setUUID(MainApp.fc.getUID());
            MainApp.cc.setFMUID(childMap.get(binding.cic101.getSelectedItem().toString()).get_UID());
            MainApp.cc.setC1SerialNo(childMap.get(binding.cic101.getSelectedItem().toString()).getSerialNo());
            if (childMap.get(binding.cic101.getSelectedItem().toString()).getMotherId().equals("00")) {
                MainApp.cc.setMUID("00");

            } else {

                if (MainApp.mc == null) {
                    MainApp.cc.setMUID("00");
                } else {
                    MainApp.cc.setMUID(MainApp.mc.get_UID());
                }

            }

            selectedChildName = binding.cic101.getSelectedItem().toString();

            sC1.put("cluster_no", MainApp.fc.getClusterNo());
            sC1.put("hhno", MainApp.fc.getHhNo());
            if (isNA) {
                sC1.put("respName", binding.resp.getSelectedItem().toString());
                sC1.put("resp_lno", respMap.get(binding.resp.getSelectedItem().toString()));
            } else {
                sC1.put("wra_lno", childMap.get(binding.cic101.getSelectedItem().toString()).getMotherId());
            }
            sC1.put("cic101", binding.cic101.getSelectedItem().toString());

        } else {
            sC1.put("updatedate_cic1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
//            MainApp.cc.setUID(MainApp.cc.getUID());

            if (editChildFlag && !frontPressed) {
                sC1.put("edit_updatedate_cic1", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

                sC1.put("cluster_no", jsonC1.getCluster_no());
                sC1.put("hhno", jsonC1.getHhno());
                if (MainApp.cc.getMUID().equals("00")) {
                    sC1.put("respName", jsonC1.getRespName());
                    sC1.put("resp_lno", jsonC1.getRespSerial());
                } else {
                    sC1.put("wra_lno", jsonC1.getWra_lno());
                }
                sC1.put("cic101", jsonC1.getcic101());

            } else if (editChildFlag) {
                sC1.put("cluster_no", jsonC1.getCluster_no());
                sC1.put("hhno", jsonC1.getHhno());
                if (MainApp.cc.getMUID().equals("00")) {
                    sC1.put("respName", jsonC1.getRespName());
                    sC1.put("resp_lno", jsonC1.getRespSerial());
                } else {
                    sC1.put("wra_lno", jsonC1.getWra_lno());
                }
                sC1.put("cic101", jsonC1.getcic101());

            } else {

                selectedChildName = binding.cic101.getSelectedItem().toString();

                sC1.put("cluster_no", MainApp.fc.getClusterNo());
                sC1.put("hhno", MainApp.fc.getHhNo());
                if (isNA) {
                    sC1.put("respName", binding.resp.getSelectedItem().toString());
                    sC1.put("resp_lno", respMap.get(binding.resp.getSelectedItem().toString()));
                }
                sC1.put("cic101", binding.cic101.getSelectedItem().toString());

            }
        }


        sS1.put("cid601", binding.cid601a.isChecked() ? "1" : binding.cid601b.isChecked() ? "2" : binding.cid601c.isChecked() ? "3" : "0");

        sS1.put("cid602", binding.cid602a.isChecked() ? "1"
                : binding.cid602b.isChecked() ? "2"
                : binding.cid602c.isChecked() ? "3"
                : binding.cid602d.isChecked() ? "4"
                : "0");

        sS1.put("cid603", binding.cid603a.isChecked() ? "1"
                : binding.cid603b.isChecked() ? "2"
                : binding.cid603c.isChecked() ? "3"
                : binding.cid603d.isChecked() ? "4"
                : "0");

        sS1.put("cid604", binding.cid604a.isChecked() ? "1" : binding.cid604b.isChecked() ? "2" : binding.cid604c.isChecked() ? "3" : "0");

        sS1.put("cid605", binding.cid605a.isChecked() ? "1"
                : binding.cid605b.isChecked() ? "2"
                : binding.cid605c.isChecked() ? "3"
                : binding.cid605d.isChecked() ? "4"
                : "0");

        sS1.put("cid606", binding.cid606a.isChecked() ? "1"
                : binding.cid606b.isChecked() ? "2"
                : binding.cid606c.isChecked() ? "3"
                : binding.cid606d.isChecked() ? "4"
                : binding.cid606e.isChecked() ? "5"
                : "0");

        sS1.put("cid607", binding.cid607a.isChecked() ? "1" : binding.cid607b.isChecked() ? "2" : binding.cid607c.isChecked() ? "3" : "0");

        sS1.put("cid608", binding.cid608a.isChecked() ? "1"
                : binding.cid608b.isChecked() ? "2"
                : binding.cid608c.isChecked() ? "3"
                : binding.cid608d.isChecked() ? "4"
                : "0");

        sS1.put("cid609", binding.cid609a.isChecked() ? "1" : binding.cid609b.isChecked() ? "2" : binding.cid609c.isChecked() ? "3" : "0");

        sS1.put("cid610", binding.cid610a.isChecked() ? "1" : binding.cid610b.isChecked() ? "2" : binding.cid610c.isChecked() ? "3" : "0");
//
        sS1.put("cid611", binding.cid611.getText().toString());

        sS1.put("cid612", binding.cid612a.isChecked() ? "1" : binding.cid612b.isChecked() ? "2" : binding.cid612c.isChecked() ? "3" : "0");

        sS1.put("cid613", binding.cid613.getText().toString());

        sS1.put("cid614", binding.cid614a.isChecked() ? "1" : binding.cid614b.isChecked() ? "2" : binding.cid614c.isChecked() ? "3" : "0");

        sS1.put("cid615", binding.cid615a.isChecked() ? "1" : binding.cid615b.isChecked() ? "2" : binding.cid615c.isChecked() ? "3" : "0");

        sS1.put("cid616", binding.cid616a.isChecked() ? "1" : binding.cid616b.isChecked() ? "2" : binding.cid616c.isChecked() ? "3" : "0");

        sS1.put("cid617", binding.cid617a.isChecked() ? "1" : binding.cid617b.isChecked() ? "2" : binding.cid617c.isChecked() ? "3" : "0");

        sS1.put("cid618", binding.cid618a.isChecked() ? "1" : binding.cid618b.isChecked() ? "2" : binding.cid618c.isChecked() ? "3" : "0");


        MainApp.cc.setsC1(String.valueOf(sS1));


    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        if (!backPressed) {
            Long updcount = db.addChildForm(MainApp.cc, 0);
            MainApp.cc.set_ID(String.valueOf(updcount));

            if (updcount != 0) {


                MainApp.cc.setUID(
                        (MainApp.cc.getDeviceID() + MainApp.cc.get_ID()));
                db.updateFormChildID();

                return true;
            } else {
                Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Long updcount = db.addChildForm(MainApp.cc, 1);

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

        if (frontPressed) {
            backPressed = true;
        }

        if (backPressed) {
            binding.cic101.setEnabled(false);
            binding.btnAddMember.setVisibility(View.GONE);
        }

    }



}
