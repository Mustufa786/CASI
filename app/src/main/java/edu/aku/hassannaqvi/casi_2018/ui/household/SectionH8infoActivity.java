package edu.aku.hassannaqvi.casi_2018.ui.household;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONA5ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionH8infoBinding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2018.validation.ValidatorClass;

public class SectionH8infoActivity extends AppCompatActivity {
    public static int deceasedCounter = 0;
    ActivitySectionH8infoBinding bi;
    int prevDeceasedCounter = 0;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h8info);
        bi.setCallback(this);
        db = new DatabaseHelper(this);
        this.setTitle(getResources().getString(R.string.cih8heading));

        if (SectionA1Activity.editFormFlag) {
            AutoPopulate();
        }
    }

    public boolean formValidation() {


        if (!ValidatorClass.EmptyRadioButton(this, bi.cih801, bi.cih801a, getString(R.string.cih801))) {
            return false;
        }

        if (bi.cih801a.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, bi.cih802, getString(R.string.cih802))) {
                return false;
            }

            if (SectionA1Activity.editFormFlag) {
                if (Integer.valueOf(bi.cih802.getText().toString()) > prevDeceasedCounter) {
                    Toast.makeText(this, "Can't increase Deceased!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                return ValidatorClass.RangeTextBox(this, bi.cih802, 1, 99, getString(R.string.cih802), " Deceased");
            }
        }

        return true;
    }

    private void SaveDraft() throws JSONException {



        JSONA5ModelClass jsonA5 = JSONUtilClass.getModelFromJSON(MainApp.fc.getsA5(), JSONA5ModelClass.class);


        jsonA5.setcih801(bi.cih801a.isChecked() ? "1" : bi.cih801b.isChecked() ? "2" : "0");
        jsonA5.setcih802(bi.cih802.getText().toString());

        if (bi.cih801a.isChecked()) {
            deceasedCounter = Integer.valueOf(bi.cih802.getText().toString());
        }
//        JsonObject json = new JsonObject(jsonA5);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        MainApp.fc.setsA5(String.valueOf(gson.toJson(jsonA5)));

        // Set summary fields
        MainApp.sumc = MainApp.AddSummary(MainApp.fc, 1);



    }

    private boolean UpdateDB() {

        //Long rowId;
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSA5();

        if (updcount == 1) {

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void AutoPopulate() {
        FormsContract formContract = db.getsA5();
        if (!formContract.getsA5().equals("")) {

            JSONA5ModelClass jsonA5 = JSONUtilClass.getModelFromJSON(formContract.getsA5(), JSONA5ModelClass.class);

            if (!jsonA5.getcih801().equals("0")) {
                bi.cih801.check(
                        jsonA5.getcih801().equals("1") ? bi.cih801a.getId() :
                                bi.cih801b.getId()

                );
            }
            bi.cih802.setText(jsonA5.getcih802());

            if (jsonA5.getcih801().equals("2")) {
                bi.cih801a.setEnabled(false);
            }
            if (!jsonA5.getcih802().equals("")) {
                prevDeceasedCounter = Integer.valueOf(jsonA5.getcih802());
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
                if (bi.cih801a.isChecked()) {
                    startActivity(new Intent(SectionH8infoActivity.this, SectionH8Activity.class));
                } else {
                    if (SectionA1Activity.editFormFlag) {
                        startActivity(new Intent(this, ViewMemberActivity.class)
                                .putExtra("flagEdit", false)
                                .putExtra("comingBack", true)
                                .putExtra("cluster", MainApp.fc.getClusterNo())
                                .putExtra("hhno", MainApp.fc.getHhNo())
                        );

                    } else {

                        if (!MainApp.UpdateSummary(this, db, 1)) {
                            Toast.makeText(this, "Summary Table not update!!", Toast.LENGTH_SHORT).show();
                        }

                        startActivity(new Intent(this, ViewMemberActivity.class).putExtra("activity", 3));
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
}
