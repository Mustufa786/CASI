package edu.aku.hassannaqvi.casi_2019.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONA2ModelClass;
import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.databinding.ChildAdapterBinding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;

/**
 * Created by ramsha.ahmed on 3/28/2018.
 */

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {

    public static ArrayList<Integer> childExistList;
    ChildAdapter.ChildViewHolder holder;
    JSONModelClass json;
    DatabaseHelper db;
    private List<FamilyMembersContract> childList;
    private Context mContext;

    public ChildAdapter(Context mContext, List<FamilyMembersContract> childList) {
        json = new JSONModelClass();
        this.childList = childList;

        childExistList = new ArrayList<>();
        db = new DatabaseHelper(mContext);

        this.mContext = mContext;
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_adapter, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return new ChildViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        this.holder = holder;
        this.holder.bindUser(childList.get(position));

        if (db.getChildExistanceByUid(childList.get(position).get_UUID(), childList.get(position).get_UID())) {
            childExistList.add(position);
        }
        for (int item : childExistList) {
            if (position == item)
                this.holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorRedsub));
        }
    }

    @Override
    public int getItemCount() {
        return childList.size() > 0 ? childList.size() : 0;
    }

    private String setupMotherName(FamilyMembersContract child) {
        ArrayList<FamilyMembersContract> childContract = db.getMotherForChild(child.get_UUID(), child.getHhNo(), child.getEnmNo());
        for (FamilyMembersContract mother : childContract) {
            JSONA2ModelClass jsonA2 = JSONUtilClass.getModelFromJSON(mother.getsA2(), JSONA2ModelClass.class);
            if (jsonA2.getcih2SerialNo().equals(json.getMothername()))
                return jsonA2.getcih212();
        }

        return "";
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {

        ChildAdapterBinding childBinding;

        public ChildViewHolder(View itemView) {
            super(itemView);
            childBinding = DataBindingUtil.bind(itemView);
        }

        public void bindUser(FamilyMembersContract child) {
            json = JSONUtilClass.getModelFromJSON(child.getsA2(), JSONModelClass.class);
            childBinding.childName.setText(json.getName().toUpperCase());
            childBinding.Age.setText("Age: " + json.getAge());
            childBinding.na204.setText(json.getGender().equals("1") ? "Male" : "Female");
            childBinding.childmName.setText(json.getMothername().equals("") ? "..." : setupMotherName(child));
        }
    }

}
