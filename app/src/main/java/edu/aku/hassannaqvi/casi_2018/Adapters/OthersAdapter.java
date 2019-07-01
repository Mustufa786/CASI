package edu.aku.hassannaqvi.casi_2018.Adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.databinding.OtherAdapterBinding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;

/**
 * Created by ramsha.ahmed on 3/30/2018.
 */

public class OthersAdapter extends RecyclerView.Adapter<OthersAdapter.OtherViewHolder> {

    OtherViewHolder holder;
    JSONModelClass json;
    private List<FamilyMembersContract> othList;

    public OthersAdapter(List<FamilyMembersContract> othList) {
        json = new JSONModelClass();
        this.othList = othList;
    }

    @Override
    public OtherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.other_adapter, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return new OtherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherViewHolder holder, int position) {
        this.holder = holder;
        this.holder.bindUser(this.othList.get(position));
    }

    @Override
    public int getItemCount() {
        return othList.size() > 0 ? othList.size() : 0;
    }

    private String MStatusChecking(String ms) {
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

    public class OtherViewHolder extends RecyclerView.ViewHolder {

        OtherAdapterBinding OtherBinding;

        public OtherViewHolder(View itemView) {
            super(itemView);
            OtherBinding = DataBindingUtil.bind(itemView);
        }

        public void bindUser(FamilyMembersContract Other) {
            json = JSONUtilClass.getModelFromJSON(Other.getsA2(), JSONModelClass.class);
            OtherBinding.otherName.setText(json.getName().toUpperCase());
            OtherBinding.otherAge.setText("Age: " + json.getAge());
            OtherBinding.otherms.setText("Marital Status: " + MStatusChecking(json.getMaritalStatus()));
        }
    }

}
