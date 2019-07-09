package edu.aku.hassannaqvi.casi_2019.core;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import edu.aku.hassannaqvi.casi_2019.other.Summary;

public abstract class DashboardFunctions {

    static TextView headerTextView(Context mContext, String label) {

        TextView headerTextView = new TextView(mContext);
        headerTextView.setBackgroundColor(Color.BLACK);
        headerTextView.setTextColor(Color.WHITE);
        headerTextView.setText(label);
        headerTextView.setTextSize(30);
        headerTextView.setTypeface(Typeface.SANS_SERIF);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setPadding(10, 2, 10, 5);

        return headerTextView;
    }

    static TextView bodyTextView(Context mContext, String label) {

        TextView bodyTextView = new TextView(mContext);
        bodyTextView.setBackgroundColor(Color.BLACK);
        bodyTextView.setTextColor(Color.GREEN);
        bodyTextView.setText(label);
        bodyTextView.setTextSize(15);
        bodyTextView.setTypeface(Typeface.MONOSPACE);
        bodyTextView.setGravity(Gravity.CENTER);
        bodyTextView.setPadding(5, 5, 5, 5);

        return bodyTextView;
    }

    public static void componentHTableRow(Context mContext, TableLayout tbl, String[] headers) {

        TableRow componentBTableRow = new TableRow(mContext);

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 0, 0, 3);

        for (String label : headers) {
            TextView textView = headerTextView(mContext, label);
            textView.setTextSize(15);

            textView.setLayoutParams(params);
            componentBTableRow.addView(textView);
        }

        tbl.addView(componentBTableRow);
    }


    public static void componentBTableRow(Context mContext, TableLayout tbl, ArrayList<Summary> body) {

        for (int i = 0; i < body.size(); i++) {

            TableRow taleRowForTableD = new TableRow(mContext);

            String[] getSum = Summary.GetBody(body.get(i));
            for (int j = 0; j < getSum.length; j++) {
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                params.setMargins(1, 1, 0, 0);

                TextView textViewB = bodyTextView(mContext, getSum[j]);
                taleRowForTableD.addView(textViewB, params);
            }

            tbl.addView(taleRowForTableD);
        }
    }

}
