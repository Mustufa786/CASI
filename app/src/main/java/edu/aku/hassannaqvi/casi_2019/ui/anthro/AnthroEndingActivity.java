package edu.aku.hassannaqvi.casi_2019.ui.anthro;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivityAnthroEndingBinding;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.MainActivity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class AnthroEndingActivity extends AppCompatActivity {

    private static final String TAG = AnthroEndingActivity.class.getSimpleName();

    ActivityAnthroEndingBinding binding;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_anthro_ending);
        binding.setCallback(this);

        db = new DatabaseHelper(this);

        Boolean check = getIntent().getExtras().getBoolean("complete");

        if (check) {
            binding.istatusa.setEnabled(true);
            binding.istatusb.setEnabled(false);
            binding.istatusc.setEnabled(false);
            binding.istatusd.setEnabled(false);
        } else {
            binding.istatusa.setEnabled(false);
            binding.istatusb.setEnabled(true);
            binding.istatusc.setEnabled(true);
            binding.istatusd.setEnabled(true);

        }

       /* binding.istatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (binding.istatus96.isChecked()) {
                    binding.istatus96x.setVisibility(View.VISIBLE);
                    //istatus88x.requestFocus();
                } else {
                    binding.istatus96x.setText(null);
                    binding.istatus96x.setVisibility(View.GONE);
                }
            }
        });*/
    }

    public void BtnEnd() {


        if (formValidation()) {
            SaveDraft();
            if (UpdateDB()) {

                finish();

                if (SectionAnth1Activity.counter == MainApp.all_members.size()) {

                    SectionAnth1Activity.counter = 1;
                    MainApp.all_members.clear();
                    SectionAnth1Activity.members.clear();
                    SectionAnth1Activity.membersMap.clear();
                    startActivity(new Intent(this, MainActivity.class));

                } else {


                    startActivity(new Intent(this, SectionAnth1Activity.class)
                            .putExtra("flag", true).putExtra("name", SectionAnth1Activity.name));
                }

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() {


        MainApp.emc.setIstatus(binding.istatusa.isChecked() ? "1"
                : binding.istatusb.isChecked() ? "2"
                : binding.istatusc.isChecked() ? "3"
                : binding.istatusd.isChecked() ? "4"
                : "0");

        MainApp.emc.setEnd_time(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));

        // Set summary fields
        FormsContract fc = new FormsContract();
        fc.setClusterNo(MainApp.emc.getEnm_no());
        fc.setHhNo(MainApp.emc.getHh_no());
        fc.setDevicetagID(MainApp.emc.getDevicetagID());
        fc.setFormDate(MainApp.emc.getFormDate());
        fc.setUser(MainApp.emc.getUser());
        fc.setDeviceID(MainApp.emc.getDeviceId());
        fc.setAppversion(MainApp.emc.getApp_ver());
        MainApp.sumc = MainApp.AddSummary(fc, 4);


    }

    private boolean UpdateDB() {

        int updcount = db.updateAnthroEnding();

        if (updcount == 1) {


            return MainApp.UpdateSummary(this, db, 4);

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private boolean formValidation() {


        return ValidatorClass.EmptyRadioButton(this, binding.istatus, binding.istatusa, getString(R.string.istatus));


    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
    }


}
