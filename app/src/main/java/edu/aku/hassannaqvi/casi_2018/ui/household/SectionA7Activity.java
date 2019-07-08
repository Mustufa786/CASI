package edu.aku.hassannaqvi.casi_2018.ui.household;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionA7Binding;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionA7Activity extends AppCompatActivity {


    ActivitySectionA7Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a7);
        bi.setCallback(this);
    }

    public void BtnContinue() {

//        Validation Boolean
        MainApp.validateFlag = true;

        if (formValidation()) {
//        if (true) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {


            }
        }


    }

    public void BtnEnd() {

////        Validation Boolean
//        MainApp.validateFlag = true;
//
//        flag = true;
//        //Toast.makeText(this, "Processing End Section", Toast.LENGTH_SHORT).show();
//        if (formValidation()) {
//
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                    SectionA1Activity.this);
//            alertDialogBuilder
//                    .setMessage("Do you want to Exit??")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int id) {
//                                    try {
//                                        SaveDraft();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    if (UpdateDB()) {
//
//                                        finish();
//
//                                        if (editFormFlag) {
//                                            startActivity(new Intent(SectionA1Activity.this, ViewMemberActivity.class)
//                                                    .putExtra("flagEdit", false)
//                                                    .putExtra("comingBack", true)
//                                                    .putExtra("cluster", MainApp.fc.getClusterNo())
//                                                    .putExtra("hhno", MainApp.fc.getHhNo())
//                                            );
//                                        } else {
//                                            startActivity(new Intent(SectionA1Activity.this, EndingActivity.class).putExtra("complete", false));
//                                        }
//
//                                    } else {
//                                        Toast.makeText(SectionA1Activity.this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//            alertDialogBuilder.setNegativeButton("No",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//            AlertDialog alert = alertDialogBuilder.create();
//            alert.show();
//        }
    }

    private boolean UpdateDB() {

        return true;
    }


    private boolean formValidation() {

        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpSectionH7);
    }

    private void SaveDraft() throws JSONException {


        JSONObject secH7 = new JSONObject();

        secH7.put("cih70101", bi.cih701a.isChecked() ? "1"
                : bi.cih701b.isChecked() ? "2" : "0");
        secH7.put("cih70102", bi.cih70102.getText().toString());
        secH7.put("cih70103", bi.cih70103.getSelectedItem().toString());


        secH7.put("cih70201", bi.cih702a.isChecked() ? "1"
                : bi.cih702b.isChecked() ? "2" : "0");
        secH7.put("cih70202", bi.cih70202.getText().toString());
        secH7.put("cih70203", bi.cih70203.getSelectedItem().toString());

        secH7.put("cih70301", bi.cih703a.isChecked() ? "1"
                : bi.cih703b.isChecked() ? "2" : "0");
        secH7.put("cih70302", bi.cih70302.getText().toString());
        secH7.put("cih70303", bi.cih70303.getSelectedItem().toString());


        secH7.put("cih70401", bi.cih704a.isChecked() ? "1"
                : bi.cih704b.isChecked() ? "2" : "0");
        secH7.put("cih70402", bi.cih70402.getText().toString());
        secH7.put("cih70403", bi.cih70403.getSelectedItem().toString());


        secH7.put("cih70501", bi.cih705a.isChecked() ? "1"
                : bi.cih705b.isChecked() ? "2" : "0");
        secH7.put("cih70502", bi.cih70502.getText().toString());
        secH7.put("cih70503", bi.cih70503.getSelectedItem().toString());

        secH7.put("cih70601", bi.cih706a.isChecked() ? "1"
                : bi.cih706b.isChecked() ? "2" : "0");
        secH7.put("cih70602", bi.cih70602.getText().toString());
        secH7.put("cih70603", bi.cih70603.getSelectedItem().toString());


        secH7.put("cih70701", bi.cih707a.isChecked() ? "1"
                : bi.cih707b.isChecked() ? "2" : "0");
        secH7.put("cih70702", bi.cih70702.getText().toString());
        secH7.put("cih70703", bi.cih70703.getSelectedItem().toString());


        secH7.put("cih70801", bi.cih708a.isChecked() ? "1"
                : bi.cih708b.isChecked() ? "2" : "0");
        secH7.put("cih70802", bi.cih70802.getText().toString());
        secH7.put("cih70803", bi.cih70803.getSelectedItem().toString());


        secH7.put("cih70801", bi.cih708a.isChecked() ? "1"
                : bi.cih708b.isChecked() ? "2" : "0");
        secH7.put("cih70802", bi.cih70802.getText().toString());
        secH7.put("cih70803", bi.cih70803.getSelectedItem().toString());


        secH7.put("cih70901", bi.cih709a.isChecked() ? "1"
                : bi.cih709b.isChecked() ? "2" : "0");
        secH7.put("cih70902", bi.cih70902.getText().toString());
        secH7.put("cih70903", bi.cih70903.getSelectedItem().toString());


        secH7.put("cih71001", bi.cih710a.isChecked() ? "1"
                : bi.cih710b.isChecked() ? "2" : "0");
        secH7.put("cih71002", bi.cih71002.getText().toString());
        secH7.put("cih71003", bi.cih71003.getSelectedItem().toString());


        secH7.put("cih71101", bi.cih711a.isChecked() ? "1"
                : bi.cih711b.isChecked() ? "2" : "0");
        secH7.put("cih71102", bi.cih71102.getText().toString());
        secH7.put("cih71103", bi.cih71103.getSelectedItem().toString());


        secH7.put("cih71201", bi.cih712a.isChecked() ? "1"
                : bi.cih712b.isChecked() ? "2" : "0");
        secH7.put("cih71202", bi.cih71202.getText().toString());
        secH7.put("cih71203", bi.cih71203.getSelectedItem().toString());


        secH7.put("cih71301", bi.cih713a.isChecked() ? "1"
                : bi.cih713b.isChecked() ? "2" : "0");
        secH7.put("cih71302", bi.cih71302.getText().toString());
        secH7.put("cih71303", bi.cih71303.getSelectedItem().toString());

        secH7.put("cih714", bi.cih714.getText().toString());
        secH7.put("cih715", bi.cih715.getText().toString());


    }

}