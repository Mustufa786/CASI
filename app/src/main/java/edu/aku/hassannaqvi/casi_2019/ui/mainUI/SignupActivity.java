package edu.aku.hassannaqvi.casi_2019.ui.mainUI;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.SignupContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySignupBinding;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding bi;

    String[] countries = new String[]{

            "- Select Country - ", "Pakistan", "Afghanistan", "Tajikistan"
    };

    HashMap<String, String> coutryMap = new HashMap<String, String>() {{

        put("Pakistan", "1");
        put("Afghanistan", "2");
        put("Tajikistan", "3");

    }};

    String countryId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        bi.setCallback(this);

        setUIContent();
    }

    private void setUIContent() {

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, countries);
        bi.countries.setAdapter(arrayAdapter);
        bi.countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    countryId = coutryMap.get(bi.countries.getSelectedItem().toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void BtnContinue() {

        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

//                finish();

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private boolean formValidation() {

        return ValidatorClass.EmptyCheckingContainer(this, bi.signUpSection);

    }

    private void SaveDraft() throws JSONException {

        MainApp.signContract = new SignupContract();
        MainApp.signContract.setFullName(bi.fullName.getText().toString());
        MainApp.signContract.setUserName(bi.userName.getText().toString());
        MainApp.signContract.setDesignation(bi.designation.getText().toString());
        MainApp.signContract.setPassword(bi.password.getText().toString());
        MainApp.signContract.setCountryId(countryId);


    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);

        long rowID = db.addSignUpForm(MainApp.signContract);

        if (rowID != 0) {
            return true;
        } else {
            Toast.makeText(this, "Error in updating DB", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}
