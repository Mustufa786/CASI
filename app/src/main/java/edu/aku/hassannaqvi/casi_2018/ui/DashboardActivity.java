package edu.aku.hassannaqvi.casi_2018.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.core.DashboardFunctions;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivityDashboardBinding;
import edu.aku.hassannaqvi.casi_2018.other.Summary;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding bi;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        bi.setCallback(this);

        db = new DatabaseHelper(this);

        this.setTitle("DASHBOARD -- SUMMARY");

        // Summary
        new PopulatingData(this).execute();

    }

    public class PopulatingData extends AsyncTask<Void, Void, Void> {

        Context mContext;
        ArrayList<Summary> dashBoardSummary;

        public PopulatingData(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            dashBoardSummary = db.getSummary();

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            DashboardFunctions.componentHTableRow(mContext, bi.dashboardTable, Summary.GetHeaders());

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            DashboardFunctions.componentBTableRow(mContext, bi.dashboardTable, dashBoardSummary);
        }
    }

}
