package edu.aku.hassannaqvi.casi_2019.ui.wra;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionD4BBinding;

public class SectionD4BActivity extends AppCompatActivity {


    ActivitySectionD4BBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_d4_b);
        bi.setCallback(this);
    }
}