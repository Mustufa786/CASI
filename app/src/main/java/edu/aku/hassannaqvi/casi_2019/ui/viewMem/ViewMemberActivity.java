package edu.aku.hassannaqvi.casi_2019.ui.viewMem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.WifiDirect.WiFiDirectActivity;
import edu.aku.hassannaqvi.casi_2019.adapters.AdolescentsAdapter;
import edu.aku.hassannaqvi.casi_2019.adapters.ChildAdapter;
import edu.aku.hassannaqvi.casi_2019.adapters.OthersAdapter;
import edu.aku.hassannaqvi.casi_2019.adapters.WraAdapter;
import edu.aku.hassannaqvi.casi_2019.contracts.BLRandomContract;
import edu.aku.hassannaqvi.casi_2019.contracts.EnumBlockContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivityViewMemberBinding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.childs.SectionC1Activity;
import edu.aku.hassannaqvi.casi_2019.ui.household.EndingActivity;
import edu.aku.hassannaqvi.casi_2019.ui.household.SectionA1Activity;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.MainActivity;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.MenuActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;


public class ViewMemberActivity extends MenuActivity {

    WraAdapter wraAdapter;
    ChildAdapter childAdapter;
    AdolescentsAdapter adolescentsAdapter;
    OthersAdapter othersAdapter;


    ActivityViewMemberBinding binding;
    Collection<BLRandomContract> selected;
    DatabaseHelper db;
    JSONModelClass json;
    Collection<FamilyMembersContract> members;

    ProgressDialog progressDialog;

    Boolean flag = true;
    boolean checkflag;

    String formUid;
    int length = 0;
    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_view_member);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_member);
        db = new DatabaseHelper(this);
        binding.setCallback(this);
        this.setTitle("Members List");

//        Setting ProgressDialog
        progressDialog = new ProgressDialog(ViewMemberActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Populating...");

        if (getIntent().getBooleanExtra("flagEdit", true)) {

            binding.fldGrpEditHH.setVisibility(View.GONE);
            binding.fldGrpVisA.setVisibility(View.GONE);
            binding.fldGrpVisB.setVisibility(View.GONE);

            binding.chckenumblock.setText(MainApp.fc.getClusterNo());
            binding.chckhouse.setText(MainApp.fc.getHhNo());

            binding.chckhouse.setEnabled(false);

            BtnCheckEnm();
            BtnCheckHH();

            flag = true;

            binding.btnContinue.setVisibility(View.VISIBLE);

        } else {
            binding.fldGrpEditHH.setVisibility(View.VISIBLE);
            binding.fldGrpVisA.setVisibility(View.VISIBLE);
            binding.fldGrpVisB.setVisibility(View.VISIBLE);

            binding.btnContinue.setVisibility(View.GONE);

            flag = false;

            initializingLists();

            if (getIntent().getBooleanExtra("comingBack", false)) {

                binding.fldGrpVisA.setVisibility(View.GONE);
                binding.fldGrpVisB.setVisibility(View.GONE);
                binding.fldGrpEditHH.setVisibility(View.VISIBLE);

                binding.chckenumblock.setText(getIntent().getStringExtra("cluster"));
                binding.chckhouse.setText(getIntent().getStringExtra("hhno"));

                BtnCheckEnm();
                BtnCheckHH();

                binding.btnEnd.setVisibility(View.VISIBLE);
            }
        }

        binding.chckenumblock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.fldGrpVisB.setVisibility(View.GONE);
                binding.fldGrpviewlist.setVisibility(View.GONE);
                binding.chckhouse.setText(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        HH listener
        binding.chckhouse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                binding.chckhouse.setInputType(InputType.TYPE_CLASS_NUMBER);

                String scanned = charSequence.toString();

                length = charSequence.toString().length();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                binding.fldGrpviewlist.setVisibility(View.GONE);

                if (!binding.chckhouse.getText().toString().isEmpty() && binding.chckhouse.getText().toString().length() == 4) {
                    if (binding.chckhouse.getText().toString().substring(0, 3).matches("[0-9]+")) {
                        if (length < 5) {
                            binding.chckhouse.setText(binding.chckhouse.getText().toString() + "-");
                            binding.chckhouse.setSelection(binding.chckhouse.getText().length());
//                            binding.chckhouse.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }

    private void initializingLists() {

        MainApp.all_members_1 = new ArrayList<>();
        MainApp.otherMembers_1 = new ArrayList<>();
        MainApp.childUnder2_1 = new ArrayList<>();
        MainApp.childUnder5_1 = new ArrayList<>();
        MainApp.adolescents_1 = new ArrayList<>();
        MainApp.mwra_1 = new ArrayList<>();
        json = new JSONModelClass();

    }

    public void BtnCheckHH() {

        if (!binding.chckenumblock.getText().toString().trim().isEmpty() && !binding.chckhouse.getText().toString().trim().isEmpty()) {

            formUid = flag ? MainApp.fc.getUID() :
                    db.getUIDByHH(binding.chckenumblock.getText().toString(), binding.chckhouse.getText().toString().toUpperCase());

            if (formUid != null) {

                members = db.getAllMembersByHH(formUid);

                initializingLists();

                if (members.size() != 0) {

                    progressDialog.show();

                    new PopulatingData(this).execute();

                } else {
                    Toast.makeText(this, "Sorry no members found!!\nYou can't edit this form!", Toast.LENGTH_SHORT).show();
                }
            } else {
                binding.fldGrpviewlist.setVisibility(View.GONE);
                Toast.makeText(this, "No members found for the HH.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Not found.", Toast.LENGTH_SHORT).show();
        }

    }

    private void viewChildList() {
        new populateChildRecyclerView(this).execute();

        binding.recyclerChild.addOnItemTouchListener(
                new ViewMemRecyclerItemClickListener(ViewMemberActivity.this, new ViewMemRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        // TODO Handle item click

                        if (position != -1) {

                            if (!flag) {

                                checkflag = false;
                                for (int item : ChildAdapter.childExistList) {
                                    if (item == position) {
                                        checkflag = true;
                                        break;
                                    }
                                }

                                if (checkflag) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            ViewMemberActivity.this);
                                    alertDialogBuilder
                                            .setMessage("Are you sure to update this member?")
                                            .setCancelable(false)
                                            .setPositiveButton("Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {

                                                            startActivity(new Intent(ViewMemberActivity.this, SectionC1Activity.class)
                                                                    .putExtra("editForm", true)
                                                                    .putExtra("checkflag", true)
                                                                    .putExtra("childFMClass", MainApp.childUnder5_1.get(position))
                                                                    .putExtra("formUid", MainApp.childUnder5_1.get(position).get_UUID())
                                                                    .putExtra("fmUid", MainApp.childUnder5_1.get(position).get_UID())
                                                            );

                                                        }
                                                    });
                                    alertDialogBuilder.setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alert = alertDialogBuilder.create();
                                    alert.show();
                                }
                            }
                        }
                    }
                })
        );

    }

    private void viewWraList() {
        new populateWraRecyclerView(this).execute();

        binding.recyclerMwra.addOnItemTouchListener(
                new ViewMemRecyclerItemClickListener(ViewMemberActivity.this, new ViewMemRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        // TODO Handle item click

                        if (position != -1) {

                            if (!flag) {

                                checkflag = false;
                                for (int item : WraAdapter.wraExistList) {
                                    if (item == position) {
                                        checkflag = true;
                                        break;
                                    }
                                }

                                if (checkflag) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            ViewMemberActivity.this);
                                    alertDialogBuilder
                                            .setMessage("Are you sure to update this member?")
                                            .setCancelable(false)
                                            .setPositiveButton("Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {

                                                            startActivity(new Intent(ViewMemberActivity.this, SectionB1Activity.class)
                                                                    .putExtra("editForm", true)
                                                                    .putExtra("checkflag", checkflag)
                                                                    .putExtra("under2Size", MainApp.childUnder2_1.size())
                                                                    .putExtra("formUid", MainApp.mwra_1.get(position).get_UUID())
                                                                    .putExtra("fmUid", MainApp.mwra_1.get(position).get_UID())
                                                                    .putExtra("fmClass", MainApp.mwra_1.get(position))

                                                            );

                                                        }
                                                    });
                                    alertDialogBuilder.setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alert = alertDialogBuilder.create();
                                    alert.show();
                                }
                            }
                        }
                    }
                })
        );

    }

    private void viewAdolList() {
        new populateAdolRecyclerView(this).execute();
    }

    private void viewOthList() {
        new populateOtherRecyclerView(this).execute();
    }

    public void BtnCheckEnm() {

        if (ValidatorClass.EmptyTextBox(this, binding.chckenumblock, getString(R.string.cih102))) {

            EnumBlockContract enumBlockContract = db.getEnumBlock(binding.chckenumblock.getText().toString());
            if (enumBlockContract != null) {
                String selected = enumBlockContract.getGeoarea();
                if (!selected.equals("")) {

                    String[] selSplit = selected.split("\\|");
                    binding.nsuba.setText(selSplit[0]);
                    binding.nzilla.setText(selSplit[1].equals("") ? "----" : selSplit[1]);
                    binding.ntehsil.setText(selSplit[2].equals("") ? "----" : selSplit[2]);
                    binding.ncity.setText(selSplit[3]);

                    binding.fldGrphidden.setVisibility(View.VISIBLE);

                }

                binding.fldGrpVisB.setVisibility(View.VISIBLE);

            } else {
                binding.chckhouse.setText(null);
                Toast.makeText(this, "Sorry not found any block", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public Boolean updateEndingFromHH() {
        MainApp.fc.setIstatusHH("1");

        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateHHEnding();

        if (updcount == 1) {

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void BtnContinue() {

        Intent GetIntent = null;
        switch (getIntent().getIntExtra("activity", 0)) {
            case 1:
            case 2:
            case 3:

                // Update istatus of ending activity
                if (!updateEndingFromHH()) {
                    Toast.makeText(this, "Istatus for HH not update!!", Toast.LENGTH_SHORT).show();
                }

                if (MainApp.mwra.size() > 0) {
                    GetIntent = new Intent(this, SectionB1Activity.class);
                } else if (MainApp.childUnder5.size() > 0) {
                    if (MainApp.childNA.size() > 0) {
                        SectionC1Activity.isNA = true;
                        GetIntent = new Intent(this, SectionC1Activity.class);
                    } else if (MainApp.childUnder5_Del.size() > 0) {
                        for (FamilyMembersContract familyMembersContract : MainApp.childUnder5_Del) {
                            if (!familyMembersContract.getMotherId().equals("00")) {
                                for (FamilyMembersContract familyMembersContract1 : MainApp.members_f_m) {
                                    if (familyMembersContract1.getna204().equals("2") && familyMembersContract1.getSerialNo().equals(familyMembersContract.getMotherId())) {
                                        if (Integer.valueOf(familyMembersContract1.getAgeInYear()) >= 50 && familyMembersContract1.getAv().equals("1")) {
                                            SectionC1Activity.isNA = true;
                                            GetIntent = new Intent(this, SectionC1Activity.class);

                                            break;
                                        } else {
                                            GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);

                                            break;
                                        }
                                    }
                                }
                            }
                            if (GetIntent != null) {
                                break;
                            }
                        }


                    } else {
                        GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);
                    }
                } else {
                    GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);
                }
                break;

            case 4:
                if (!(SectionC1Activity.counterPerMom <= 0)) {

                    if (SectionC1Activity.counter == SectionC1Activity.counterPerMom) {

                        if (SectionB1Activity.WRAcounter == MainApp.mwra.size()
                                ||
                                SectionB1Activity.lstMwra.size() == 1) {

                            if (MainApp.mc != null) {
                                SectionB1Activity.WRAcounter++;
                                SectionB1Activity.lstMwra.remove(SectionB1Activity.wraName);
                            }

                            SectionC1Activity.isNA = false;
//                            SectionC1Activity.childU5.remove(SectionC1Activity.selectedChildName);
                            SectionC1Activity.counter = 1;
                            SectionC1Activity.counterPerMom = 0;
                            SectionC1Activity.counterPerNA = 0;

                            if (MainApp.childNA.size() > 0) {
                                SectionC1Activity.isNA = true;
                                GetIntent = new Intent(this, SectionC1Activity.class);
                            } else if (MainApp.childUnder5_Del.size() > 0) {
                                for (FamilyMembersContract familyMembersContract : MainApp.childUnder5_Del) {
                                    if (!familyMembersContract.getMotherId().equals("00")) {
                                        for (FamilyMembersContract familyMembersContract1 : MainApp.members_f_m) {
                                            if (familyMembersContract1.getna204().equals("2") && familyMembersContract1.getSerialNo().equals(familyMembersContract.getMotherId())) {
                                                if (Integer.valueOf(familyMembersContract1.getAgeInYear()) >= 50 && familyMembersContract1.getAv().equals("1")) {
                                                    SectionC1Activity.isNA = true;
                                                    GetIntent = new Intent(this, SectionC1Activity.class);

                                                    break;
                                                } else {
                                                    GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);

                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (GetIntent != null) {
                                        break;
                                    }
                                }

                            } else {
                                GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);
                            }
                        } else {

                            SectionC1Activity.isNA = false;

                            GetIntent = new Intent(this, SectionB1Activity.class)
                                    .putExtra("mwraFlag", true)
                                    .putExtra("wraName", SectionB1Activity.wraName);
                        }

                    } else {
                        SectionC1Activity.isNA = false;
                        GetIntent = new Intent(this, SectionC1Activity.class)
                                .putExtra("childFlag", true)
                                .putExtra("name", SectionC1Activity.selectedChildName);
                    }
                } else {

                    if (SectionC1Activity.counter == SectionC1Activity.counterPerNA) {

                        SectionC1Activity.isNA = false;
                        SectionC1Activity.counter = 1;
                        SectionC1Activity.counterPerMom = 0;
                        SectionC1Activity.counterPerNA = 0;

                        GetIntent = new Intent(this, EndingActivity.class).
                                putExtra("complete", true);

                    } else {

                        GetIntent = new Intent(this, SectionC1Activity.class)
                                .putExtra("childFlag", true);
                    }
                }
                break;

            case 5:
                if (MainApp.childUnder5.size() > 0) {
                    Boolean childcount = false;
                    for (FamilyMembersContract fmc : MainApp.childUnder5) {
                        if (fmc.getMotherId().equals(MainApp.mc.getB1SerialNo())) {
                            childcount = true;
                            break;
                        }
                    }
                    if (childcount) {
                        SectionC1Activity.isNA = false;
                        GetIntent = new Intent(this, SectionC1Activity.class);
                    } else if (SectionB1Activity.WRAcounter == MainApp.mwra.size()) {
                        SectionB1Activity.WRAcounter++;
                        SectionB1Activity.lstMwra.remove(SectionB1Activity.wraName);

                        SectionC1Activity.counter = 1;
                        SectionC1Activity.counterPerMom = 0;
                        SectionC1Activity.counterPerNA = 0;

                        if (MainApp.childNA.size() > 0) {
                            SectionC1Activity.isNA = true;
                            GetIntent = new Intent(this, SectionC1Activity.class);
                        } else if (MainApp.childUnder5_Del.size() > 0) {
                            for (FamilyMembersContract familyMembersContract : MainApp.childUnder5_Del) {
                                if (!familyMembersContract.getMotherId().equals("00")) {
                                    for (FamilyMembersContract familyMembersContract1 : MainApp.members_f_m) {
                                        if (familyMembersContract1.getna204().equals("2") && familyMembersContract1.getSerialNo().equals(familyMembersContract.getMotherId())) {
                                            if (Integer.valueOf(familyMembersContract1.getAgeInYear()) >= 50 && familyMembersContract1.getAv().equals("1")) {
                                                SectionC1Activity.isNA = true;
                                                GetIntent = new Intent(this, SectionC1Activity.class);
                                                break;
                                            } else {
                                                GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (GetIntent != null) {
                                    break;
                                }
                            }


                        } else {
                            GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);
                        }
                    } else {
                        GetIntent = new Intent(this, SectionB1Activity.class)
                                .putExtra("mwraFlag", true)
                                .putExtra("wraName", SectionB1Activity.wraName);
                    }
                } else if (SectionB1Activity.WRAcounter == MainApp.mwra.size()) {
                    SectionB1Activity.WRAcounter++;
                    SectionB1Activity.lstMwra.remove(SectionB1Activity.wraName);

                    SectionC1Activity.counter = 1;
                    SectionC1Activity.counterPerMom = 0;
                    SectionC1Activity.counterPerNA = 0;

                    GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);
                } else {
                    GetIntent = new Intent(this, SectionB1Activity.class)
                            .putExtra("mwraFlag", true)
                            .putExtra("wraName", SectionB1Activity.wraName);
                }
                break;
            case 6:
                if (SectionA1Activity.reBackChildFlag) {
                    if (MainApp.mwra.size() > 0 && SectionB1Activity.WRAsize != MainApp.mwra.size()
                            || (SectionB1Activity.WRAcounter - 1) != MainApp.mwra.size()) {
                        GetIntent = new Intent(this, SectionB1Activity.class)
                                .putExtra("reBackComing", false);
                    } else if (MainApp.childUnder5.size() > 0 &&
                            (SectionC1Activity.NAChildsize != MainApp.childNA.size() ||
                                    SectionC1Activity.Childsize != (MainApp.childUnder5.size() - MainApp.childNA.size()))) {
                        if (MainApp.childNA.size() > SectionC1Activity.NAChildsize) {
                            SectionC1Activity.isNA = true;
                            GetIntent = new Intent(this, SectionC1Activity.class)
                                    .putExtra("reBackComing", false);
                        } else if ((MainApp.childUnder5.size() - MainApp.childNA.size()) > SectionC1Activity.Childsize && SectionC1Activity.counterPerMom != (SectionC1Activity.counter - 1)) {
                            SectionC1Activity.isNA = false;
                            GetIntent = new Intent(this, SectionC1Activity.class)
                                    .putExtra("reBackComing", false);
                        } else {
                            GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);
                        }
                    } else {
                        GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);
                    }
                } else {
                    SectionC1Activity.isNA = false;
                    GetIntent = new Intent(this, SectionC1Activity.class)
                            .putExtra("reBackComing", false);
                }
                break;
            default:
                GetIntent = new Intent(this, EndingActivity.class).putExtra("complete", true);
                break;
        }

        finish();
        startActivity(GetIntent);

    }

    public void BtnEnd() {
        if (exit) {
            finish(); // finish activity

            startActivity(new Intent(this, MainActivity.class));

        } else {
            Toast.makeText(this, "Press Button End Again..",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

    public void intentWifi(View view) {
        Intent wifidirect = new Intent(ViewMemberActivity.this, WiFiDirectActivity.class);
        startActivity(wifidirect);
    }

    public void BtnEditHH() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ViewMemberActivity.this);
        alertDialogBuilder
                .setMessage("Are you sure to edit this form?")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                finish();
                                startActivity(new Intent(ViewMemberActivity.this, SectionA1Activity.class)
                                        .putExtra("editForm", true)
                                        .putExtra("clusterNo", binding.chckenumblock.getText().toString())
                                        .putExtra("hhNo", binding.chckhouse.getText().toString())
                                );
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        if (MainApp.all_members_1.size() > 0) {
            binding.btnEnd.setVisibility(View.VISIBLE);
            Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    public static class ViewMemRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        GestureDetector mGestureDetector;
        private OnItemClickListener mListener;

        public ViewMemRecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;

            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }
    }

    public class populateAdolRecyclerView extends AsyncTask<String, String, String> {
        private Context mContext;

        public populateAdolRecyclerView(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

//              Set Recycler View
                    adolescentsAdapter = new AdolescentsAdapter(MainApp.adolescents_1);
                    if (adolescentsAdapter.getItemCount() > 0) {
                        binding.noadolfound.setVisibility(View.GONE);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        binding.recyclerAdol.setLayoutManager(mLayoutManager);
                        binding.recyclerAdol.setItemAnimator(new DefaultItemAnimator());
                        binding.recyclerAdol.setAdapter(adolescentsAdapter);
                        adolescentsAdapter.notifyDataSetChanged();
                    } else {
                        binding.noadolfound.setVisibility(View.VISIBLE);
                    }
                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // notifychildchange(childAdapter);
//                   Background black for those that's data filled
                  /*  for (int item : MainApp.hhClicked) {
                        binding.recyclerAdol.getChildAt(item).setBackgroundColor(Color.BLACK);
                    }*/
                }
            }, 800);
        }
    }

    public class populateWraRecyclerView extends AsyncTask<String, String, String> {
        private Context mContext;

        public populateWraRecyclerView(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

//              Set Recycler View
                    wraAdapter = new WraAdapter(mContext, MainApp.mwra_1);
                    if (wraAdapter.getItemCount() > 0) {
                        binding.nowrafound.setVisibility(View.GONE);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        binding.recyclerMwra.setLayoutManager(mLayoutManager);
                        binding.recyclerMwra.setItemAnimator(new DefaultItemAnimator());
                        binding.recyclerMwra.setAdapter(wraAdapter);
                        wraAdapter.notifyDataSetChanged();
                    } else {
                        binding.nowrafound.setVisibility(View.VISIBLE);
                    }
                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    wraAdapter.notifyDataSetChanged();
                }
            }, 800);
        }
    }

    public class populateOtherRecyclerView extends AsyncTask<String, String, String> {

        Context mContext;

        public populateOtherRecyclerView(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

//              Set Recycler View
                    othersAdapter = new OthersAdapter(MainApp.otherMembers_1);

                    if (othersAdapter.getItemCount() > 0) {
                        binding.othersfound.setVisibility(View.GONE);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        binding.recyclerOthers.setLayoutManager(mLayoutManager);
                        binding.recyclerOthers.setItemAnimator(new DefaultItemAnimator());
                        binding.recyclerOthers.setAdapter(othersAdapter);
                        othersAdapter.notifyDataSetChanged();

                    } else {
                        binding.othersfound.setVisibility(View.VISIBLE);
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
        }
    }

    public class populateChildRecyclerView extends AsyncTask<String, String, String> {
        private Context mContext;

        public populateChildRecyclerView(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

//              Set Recycler View
                    childAdapter = new ChildAdapter(mContext, MainApp.childUnder5_1);


                    if (childAdapter.getItemCount() > 0) {
                        binding.nochildfound.setVisibility(View.GONE);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        binding.recyclerChild.setLayoutManager(mLayoutManager);
                        binding.recyclerChild.setItemAnimator(new DefaultItemAnimator());
                        binding.recyclerChild.setAdapter(childAdapter);
                        childAdapter.notifyDataSetChanged();

                    } else {
                        binding.nochildfound.setVisibility(View.VISIBLE);
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

//                   Change background color for those whose data already filled
                   /* for (int item : ChildAdapter.childExistList) {
//                        binding.recyclerChild.getChildAt(item).setBackgroundColor(getResources().getColor(R.color.softgreen));
                        binding.recyclerChild.getChildAt(item).setBackgroundColor(getResources().getColor(R.color.lime));
                    }*/

                }
            }, 800);
        }
    }

    public class PopulatingData extends AsyncTask<Void, Void, Void> {

        private Context mContext;

        public PopulatingData(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            for (FamilyMembersContract fm : members) {

                if (fm.getsA2() != null) {
                    json = JSONUtilClass.getModelFromJSON(fm.getsA2(), JSONModelClass.class);
                    if ((Integer.valueOf(json.getAge()) >= 15 && Integer.valueOf(json.getAge()) <= 49) && json.getGender().equals("2")) {
                        MainApp.mwra_1.add(fm);
                        MainApp.all_members_1.add(fm);
                    }
                    if ((Integer.valueOf(json.getAge()) >= 10 && (Integer.valueOf(json.getAge()) <= 19)) && json.getMaritalStatus().equals("5")) {
                        MainApp.adolescents_1.add(fm);
                        MainApp.all_members_1.add(fm);
                    } else if (Integer.valueOf(json.getAge()) < 5) {
                        if (Integer.valueOf(json.getAge()) < 2) {
                            MainApp.childUnder2_1.add(fm);
                        }
                        MainApp.childUnder5_1.add(fm);
                        MainApp.all_members_1.add(fm);
                    } else if (!((Integer.valueOf(json.getAge()) >= 15 && Integer.valueOf(json.getAge()) <= 49) && json.getGender().equals("2"))) {
                        MainApp.otherMembers_1.add(fm);
                        MainApp.all_members_1.add(fm);
                    }

                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {

                            if (MainApp.all_members_1.size() > 0) {
                                Toast.makeText(mContext, "Members Found..", Toast.LENGTH_SHORT).show();
                                binding.fldGrpviewlist.setVisibility(View.VISIBLE);
                                viewWraList();
                                viewChildList();
                                viewAdolList();
                                viewOthList();

                            } else {
                                binding.fldGrpviewlist.setVisibility(View.GONE);
                                binding.btnContinue.setVisibility(View.GONE);
                                Toast.makeText(mContext, "No members found, Check another HH.", Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();
                        }
                    }, 3000);
        }
    }

}
