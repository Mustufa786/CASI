package edu.aku.hassannaqvi.casi_2018.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionA7Binding;

public class SectionA7Activity extends AppCompatActivity {


    ActivitySectionA7Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a7);
    }
}
