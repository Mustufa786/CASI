package edu.aku.hassannaqvi.casi_2018.ui.household;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONACountModelClass;
import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionListA2Binding;
import edu.aku.hassannaqvi.casi_2018.databinding.FamilymemberslistBinding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.other.KishGrid;
import edu.aku.hassannaqvi.casi_2018.ui.viewMem.ViewMemberActivity;

public class SectionA2ListActivity extends AppCompatActivity {

    static String respLineNo = "";
    ActivitySectionListA2Binding binding;
    Map<Integer, Map<Integer, Integer>> mem;
    /*Variables*/
    DatabaseHelper db;
    FamilyMembersAdapter mAdapter;
    JSONModelClass json;
    JSONACountModelClass countJSON;
    Boolean flagMember = false;
    //static Boolean flag = false;

    Boolean head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_section_list_a2);
        binding.setCallback(this);

        db = new DatabaseHelper(this);

        setupViews();
        this.setTitle(getResources().getString(R.string.na2heading));


        if (SectionA1Activity.editFormFlag) {
//        binding.btn_AddMore.setVisibility(View.GONE);
            binding.btnAddMore.setVisibility(View.GONE);
            binding.btnContinue.setVisibility(View.VISIBLE);

        } else {
//        binding.btn_AddMore.setVisibility(View.VISIBLE);
            binding.btnAddMore.setVisibility(View.VISIBLE);
        }
    }

    public void setupViews() {

//        Setting Counts
        mem = MainApp.membersCount.getMembers();

        // Total
        binding.nh2tm.setText(mem.get(1).get(1).toString());
        binding.nh2tf.setText(mem.get(1).get(2).toString());
        // Adolescents
        binding.nh2adm.setText(mem.get(2).get(1).toString());
        binding.nh2adf.setText(mem.get(2).get(2).toString());
        // Children < 5
        binding.nh2u5b.setText(mem.get(3).get(1).toString());
        binding.nh2u5g.setText(mem.get(3).get(2).toString());
        //  Mwra
        binding.nh2mw.setText(String.valueOf(MainApp.membersCount.getMwra()));

        autoPopulateFields();

//        Populate RecyclerView
        new populateRecyclerView(this).execute();

        //        Recycler click listener
        binding.recyclerNoMembers.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    Boolean delFlag = true;

                    @Override
                    public void onItemClick(View view, final int position) {
                        // TODO Handle item click
                        if (!SectionA1Activity.editFormFlag) {

                            if (position != -1) {
                                boolean flag = true;
                                for (int hh : MainApp.hhClicked) {
                                    if (hh == position) {
                                        flag = false;
                                        break;
                                    }
                                }

                                if (flag) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            SectionA2ListActivity.this);
                                    alertDialogBuilder
                                            .setMessage("Are you sure to update this member?")
                                            .setCancelable(false)
                                            .setPositiveButton("Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog,
                                                                            int id) {

                                                            MainApp.hhClicked.add(position);
                                                          /*  for (int item : MainApp.hhClicked) {
                                                                binding.recyclerNoMembers.getChildAt(item).setBackgroundColor(Color.BLACK);
                                                            }*/

                                                            finish();
                                                            startActivity(new Intent(getApplicationContext(), SectionA2Activity.class)
                                                                    .putExtra("data", MainApp.familyMembersList.get(position))
                                                                    .putExtra("pos", position));

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

                    @Override
                    public void onItemLongClick(final View view, final int position) {

                        if (position != -1 && SectionA1Activity.reBackFlag) {
                            boolean flag = false;
                            delFlag = true;
                            for (int hh : MainApp.hhClicked) {
                                if (hh == position) {
                                    flag = true;

                                    for (int check : MainApp.flagClicked) {
                                        if (check == position) {
                                            delFlag = false;
                                            break;
                                        }
                                    }

                                    break;
                                }
                            }
                            if (flag) {
                                AlertDialog.Builder editAlert = new AlertDialog.Builder(
                                        SectionA2ListActivity.this);
                                editAlert.setMessage("Do you want to edit this member?");
                                if (!SectionA1Activity.editFormFlag) {
                                    editAlert
                                            .setCancelable(false)
                                            .setPositiveButton("Edit",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog,
                                                                            int id) {
                                                            finish();
                                                            startActivity(new Intent(getApplicationContext(), SectionA2EditActivity.class)
                                                                    .putExtra("data", MainApp.familyMembersList.get(position))
                                                                    .putExtra("pos", position));
                                                        }
                                                    });
                                }
                                editAlert.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        MainApp.familyMembersList.get(position).setDelflag(delFlag ? "1" : "2");

                                        int updcount = db.updateFamilyMemberFLAG(delFlag ? "1" : "2", MainApp.familyMembersList.get(position).get_UID());
                                        if (updcount == 1) {
                                            Toast.makeText(SectionA2ListActivity.this, delFlag ? "Record Flag to delete!" : "Delete undo!!", Toast.LENGTH_SHORT).show();

                                            if (delFlag) {
                                                MainApp.flagClicked.add(position);
//                                                binding.recyclerNoMembers.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.brown));

                                            } else {
                                                for (byte i = 0; i < MainApp.flagClicked.size(); i++) {
                                                    if (position == MainApp.flagClicked.get(i)) {
                                                        MainApp.flagClicked.remove(i);
                                                    }
                                                    break;
                                                }
//                                                binding.recyclerNoMembers.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.black));
                                            }
                                        }
                                    }
                                });
                                editAlert.setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = editAlert.create();
                                alert.show();
                            }
                        }
                    }
                })
        );

//        Checking Button continue
        if (MainApp.hhClicked.size() == 0 || MainApp.hhClicked.size() != MainApp.familyMembersList.size()) {
            binding.btnContinue.setVisibility(View.GONE);
        } else {
            binding.btnContinue.setVisibility(View.VISIBLE);
        }

        //        Re-Back
        if (getIntent().getBooleanExtra("reBack", false)) {
            SectionA1Activity.reBackFlag = false;

            SectionA1Activity.reBackChildFlag = getIntent().getBooleanExtra("reBackChild", true);

        } else {
            // Getting resp Line no
            if (getIntent().getBooleanExtra("respChecking", false)) {
                respLineNo = getIntent().getStringExtra("respLineNo");
            }
        }

    }

    private void autoPopulateFields() {

        if (SectionA1Activity.editFormFlag) {

            binding.btnEnd.setVisibility(View.VISIBLE);

            MainApp.editfathersList = new ArrayList<>();
            MainApp.editfathersSerials = new ArrayList<>();
            MainApp.editmothersList = new ArrayList<>();
            MainApp.editmothersSerials = new ArrayList<>();

            int position = 0;

            for (FamilyMembersContract fm : MainApp.all_members_1) {

                Boolean serialFlag = true;
                json = JSONUtilClass.getModelFromJSON(fm.getsA2(), JSONModelClass.class);

                // Done this thing to not add duplicate in list
                for (FamilyMembersContract ser : MainApp.all_members) {
                    if (ser.getSerialNo().equals(fm.getSerialNo())) {
                        serialFlag = false;
                    }
                }
                if (serialFlag) {
                    int Age = Integer.valueOf(json.getAge());
                    int gender = Integer.valueOf(json.getGender());
                    fm.setAgeInYear(json.getAge());
                    fm.setna204(json.getGender());
                    fm.setSerialNo(json.getSerialNo());
                    fm.setName(json.getName());
                    Log.d("Names", "Name: " + json.getName());

                    boolean flag = true;

                    if ((Age >= 15 && Age <= 49) && json.getGender().equals("2")) {
                        MainApp.mwra.add(fm);
                        MainApp.all_members.add(fm);

                        flag = false;
                    }
                    if ((Age >= 10 && (Age <= 19)) && MStatusChecking(json.getMaritalStatus()).equals("5")) {
                        MainApp.adolescents.add(fm);
                        MainApp.all_members.add(fm);

                        flag = false;

                    }
                    if (Integer.valueOf(json.getAge()) < 5) {
                        MainApp.childUnder5.add(fm);
                        MainApp.all_members.add(fm);

                        flag = false;
                    }

                    if (flag) {
                        MainApp.all_members.add(fm);
                    }

                    MainApp.familyMembersList.add(fm);
                    if (Age >= 15 && json.getGender().equals("2") && !MStatusChecking(json.getMaritalStatus()).equals("5")) {
                        MainApp.editmothersList.add(json.getName());
                        MainApp.editmothersSerials.add(json.getSerialNo());
                    } else if (Age >= 15 && json.getGender().equals("1") && !MStatusChecking(json.getMaritalStatus()).equals("5")) {
                        MainApp.editfathersList.add(json.getName());
                        MainApp.editfathersSerials.add(json.getSerialNo());
                    }

                    MainApp.hhClicked.add(position);
                    MainApp.familyMembersClicked.put(position, fm);

                    if (fm.getDelflag().equals("1")) {
                        MainApp.flagClicked.add(position);
                    }

                    position++;
                }
            }
            setCount();

        }

    }

    private void setCount() {
//        editFormContract.getCount();
        countJSON = JSONUtilClass.getModelFromJSON(MainApp.fc.getCount(), JSONACountModelClass.class);
        // Total
        binding.nh2tm.setText(countJSON.getnh2tm());
        binding.nh2tf.setText(countJSON.getnh2tf());
        // Adolescents
        binding.nh2adm.setText(countJSON.getnh2adm());
        binding.nh2adf.setText(countJSON.getnh2adf());
        // Children < 5
        binding.nh2u5b.setText(countJSON.getnh2u5b());
        binding.nh2u5g.setText(countJSON.getnh2u5g());
        //  Mwra
        binding.nh2mw.setText(countJSON.getnh2mw());
    }

    public String MStatusChecking(String ms) {
        String result = "";
        switch (ms) {
            case "1":
                result = "Married";
                break;
            case "2":
                result = "Widowed";
                break;
            case "3":
                result = "Divorced";
                break;
            case "4":
                result = "Seperated";
                break;
            case "5":
                result = "Never Married";
                break;
        }
        return result;
    }

    public void BtnContinue() {

        //Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                //Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        SectionA2ListActivity.this);
                alertDialogBuilder
                        .setMessage("Are you sure to continue for next section?")
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        if (SectionA1Activity.reBackFlag) {
                                            if (!MainApp.IsHead && !SectionA1Activity.editFormFlag) {
                                                Toast.makeText(SectionA2ListActivity.this, "Assign Head to this HH!!", Toast.LENGTH_SHORT).show();
                                            } else if (!MainApp.IsResp && !SectionA1Activity.editFormFlag) {
                                                Toast.makeText(SectionA2ListActivity.this, "Assign Respondent to this HH!!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                finish();
                                                respLineNo = "";
                                                startActivity(new Intent(getApplicationContext(), SectionA4Activity.class));
                                                //                                           startActivity(new Intent(getApplicationContext(), ViewMemberActivity.class).putExtra("activity", 1));
                                            }
                                        } else {
                                            startActivity(new Intent(getApplicationContext(), ViewMemberActivity.class).putExtra("activity", 6));
                                        }

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

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnAddMore() {

        MainApp.flag_head = true;
        //Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SectionA2ListActivity.this);
        alertDialogBuilder
                .setMessage("Are you sure to add new member?")
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                finish();
                                startActivity(new Intent(SectionA2ListActivity.this, SectionA2Activity.class)
                                        .putExtra("flag", true));
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

    private Boolean formValidation() {
        return true;
    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject count = new JSONObject();

        count.put("nh2tm", binding.nh2tm.getText().toString());
        count.put("nh2tf", binding.nh2tf.getText().toString());
        count.put("nh2adm", binding.nh2adm.getText().toString());
        count.put("nh2adf", binding.nh2adf.getText().toString());
        count.put("nh2u5b", binding.nh2u5b.getText().toString());
        count.put("nh2u5g", binding.nh2u5g.getText().toString());
        count.put("nh2mw", binding.nh2mw.getText().toString());

        MainApp.fc.setRespLineNo(respLineNo);

        MainApp.fc.setCount(String.valueOf(count));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {

        //Long rowId;
        int updcount = db.updateSACount();

        if (updcount == 1) {

            if (!SectionA1Activity.editFormFlag && SectionA1Activity.reBackFlag && MainApp.mwra.size() > 0) {
//                int counter = KishGrid.KishGridProcess(Integer.valueOf(MainApp.fc.getHhNo().split("-")[1]), MainApp.mwra.size());
                int counter = KishGrid.KishGridProcess(Integer.valueOf(MainApp.selectedHead.getSno()), MainApp.mwra.size());
                updcount = db.updateFamilyMemberKishFlag(MainApp.mwra.get(counter - 1).get_UUID(), MainApp.mwra.get(counter - 1).get_UID());

                if (updcount == 1) {
                    MainApp.mwra.get(counter - 1).setKishSelected("1");
                    return true;
                } else {
                    Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            return true;

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        GestureDetector mGestureDetector;
        private OnItemClickListener mListener;
        private RecyclerView viewRecycle;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;

            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = viewRecycle.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onItemLongClick(child, viewRecycle.getChildAdapterPosition(child));
                    }

                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            viewRecycle = view;
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

            void onItemLongClick(View view, int position);
        }


    }

    //    Recycler classes
    public class FamilyMembersAdapter extends RecyclerView.Adapter<FamilyMembersAdapter.MyViewHolder> {

        MyViewHolder holder;
        private List<FamilyMembersContract> membersList;

        public FamilyMembersAdapter(List<FamilyMembersContract> membersList) {
            this.membersList = membersList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.familymemberslist, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            this.holder = holder;
            this.holder.bindUser(this.membersList.get(position));
//            this.holder.itemView.setBackgroundColor(getResources().getColor(R.color.brown));
            for (int item : MainApp.hhClicked) {
                if (position == item) {
                    JSONModelClass json = JSONUtilClass.getModelFromJSON(MainApp.familyMembersClicked.get(item).getsA2(), JSONModelClass.class);
                    int age = Integer.valueOf(json.getAge());

                    if (age >= 10) {
                        if (json.getGender().equals("1")) {
                            this.holder.itemView.setBackgroundColor(getResources().getColor(R.color.darkBlue));
                        } else if (json.getGender().equals("2")) {
                            this.holder.itemView.setBackgroundColor(getResources().getColor(R.color.darkPink));
                        } else {
                            this.holder.itemView.setBackgroundColor(Color.BLACK);
                        }
                    } else {
                        if (json.getGender().equals("1")) {
                            this.holder.itemView.setBackgroundColor(getResources().getColor(R.color.lightBlue));
                        } else if (json.getGender().equals("2")) {
                            this.holder.itemView.setBackgroundColor(getResources().getColor(R.color.lightPink));
                        } else {
                            this.holder.itemView.setBackgroundColor(Color.BLACK);
                        }
                    }
                }
            }
//            if (SectionA1Activity.editFormFlag) {
            for (int item : MainApp.flagClicked) {
                if (position == item) {
                    this.holder.itemView.setBackgroundColor(getResources().getColor(R.color.brown));
                }
            }
//            }


        }

        @Override
        public int getItemCount() {
            return membersList.size();
        }

        public String MStatusChecking(String ms) {
            String result = "";
            switch (ms) {
                case "1":
                    result = "Married";
                    break;
                case "2":
                    result = "Widowed";
                    break;
                case "3":
                    result = "Divorced";
                    break;
                case "4":
                    result = "Seperated";
                    break;
                case "5":
                    result = "Never Married";
                    break;
            }
            return result;
        }

        public int SetImage(String na204, String age) {
            int result = 0;
            switch (na204) {
                case "1":
                    if (age != "") {
                        if (Integer.valueOf(age) < 15) {
                            result = R.drawable.ctr_childboy;
                        } else {
                            result = R.drawable.ctr_male;
                        }
                    } else {
                        result = R.drawable.ic_person;
                    }
                    break;
                case "2":
                    if (age != "") {
                        if (Integer.valueOf(age) < 15) {
                            result = R.drawable.ctr_childgirl;
                        } else {
                            result = R.drawable.ctr_female;
                        }
                    } else {
                        result = R.drawable.ic_person;
                    }
                    break;

                case "3":
                    if (age != "") {
                        result = R.drawable.ic_person;
                    } else {
                        result = R.drawable.ic_person;
                    }
                    break;
            }
            return result;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            FamilymemberslistBinding familyBinding;

            public MyViewHolder(View itemView) {
                super(itemView);
                familyBinding = DataBindingUtil.bind(itemView);

            }

            public void bindUser(FamilyMembersContract mem) {

                familyBinding.imgUser.setImageDrawable(getDrawable(SetImage(mem.getna204(), mem.getAgeInYear())));
                String memName = mem.getName().length() > 15 ? mem.getName().substring(0, 15).toUpperCase() + ".." : mem.getName().toUpperCase();
                familyBinding.memberName.setText(memName + (mem.getAgeInYear().equals("") ? "" : " \r-- " + mem.getAgeInYear() + "Years"));
                familyBinding.na204.setText(mem.getna204().equals("1") ? "Male" : mem.getna204().equals("2") ? "Female" : "Transgender");
                familyBinding.lineNo.setText("Line No:" + mem.getSerialNo());
                familyBinding.ffName.setText(mem.getFatherName().equals("") ? "..." : mem.getFatherName());
                familyBinding.mmName.setText(mem.getMotherName().equals("") ? "..." : mem.getMotherName());
                familyBinding.maritalStatus.setText(MStatusChecking(mem.getMaritialStatus()));
                if (mem.getResp().equals("1")) {
                    familyBinding.imgResp.setVisibility(View.VISIBLE);
                } else {
                    familyBinding.imgResp.setVisibility(View.GONE);
                }

                if (mem.getRealtionHH().equals("1")) {
                    familyBinding.imgHead.setVisibility(View.VISIBLE);
                } else {
                    familyBinding.imgHead.setVisibility(View.GONE);
                }

            }
        }
    }

    public class populateRecyclerView extends AsyncTask<String, String, String> {
        private Context mContext;

        public populateRecyclerView(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

//              Set Recycler View
                    mAdapter = new FamilyMembersAdapter(MainApp.familyMembersList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.recyclerNoMembers.setLayoutManager(mLayoutManager);
                    binding.recyclerNoMembers.setItemAnimator(new DefaultItemAnimator());
                    binding.recyclerNoMembers.setAdapter(mAdapter);

                    mAdapter.notifyDataSetChanged();
                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                   Background black for those that's data filled
                /*    for (int item : MainApp.hhClicked) {

                        JSONModelClass json = JSONUtilClass.getModelFromJSON(MainApp.familyMembersClicked.get(item).getsA2(), JSONModelClass.class);
                        int age = Integer.valueOf(json.getAge());

                        if (age >= 10) {
                            if (json.getGender().equals("1")) {
                                binding.recyclerNoMembers.getChildAt(item).setBackgroundColor(getResources().getColor(R.color.darkBlue));
                            } else if (json.getGender().equals("2")) {
                                binding.recyclerNoMembers.getChildAt(item).setBackgroundColor(getResources().getColor(R.color.darkPink));
                            } else {
                                binding.recyclerNoMembers.getChildAt(item).setBackgroundColor(Color.BLACK);
                            }
                        } else {
                            if (json.getGender().equals("1")) {
                                binding.recyclerNoMembers.getChildAt(item).setBackgroundColor(getResources().getColor(R.color.lightBlue));
                            } else if (json.getGender().equals("2")) {
                                binding.recyclerNoMembers.getChildAt(item).setBackgroundColor(getResources().getColor(R.color.lightPink));
                            } else {
                                binding.recyclerNoMembers.getChildAt(item).setBackgroundColor(Color.BLACK);
                            }
                        }

                    }

                    for (int item : MainApp.flagClicked) {
                        binding.recyclerNoMembers.getChildAt(item).setBackgroundColor(getResources().getColor(R.color.brown));
                    }
*/
                }
            }, 800);
        }
    }


}