package edu.aku.hassannaqvi.casi_2018.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;

import butterknife.BindViews;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.casi_2018.JSONModels.JSONC2ModelClass;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.contracts.ChildContract;
import edu.aku.hassannaqvi.casi_2018.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionC2Binding;
import edu.aku.hassannaqvi.casi_2018.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2018.validation.clearClass;
import edu.aku.hassannaqvi.casi_2018.validation.validatorClass;

public class SectionC2Activity extends Menu2Activity implements RadioGroup.OnCheckedChangeListener, TextWatcher {

    private final long DELAY = 1000;
    ActivitySectionC2Binding bi;
    public CheckBox.OnCheckedChangeListener check2 = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            ValidateForm();
            if (isChecked) {
                bi.nc21199.setChecked(false);
            }
        }
    };
    FamilyMembersContract selectedChild;
    DatabaseHelper db;
    Boolean backPressed = false;
    @BindViews({R.id.nc211a, R.id.nc211b, R.id.nc211c, R.id.nc211d, R.id.nc211e, R.id.nc211f,
            R.id.nc211g, R.id.nc211h, R.id.nc211i, R.id.nc211j})
    List<CheckBox> grpnc211;
    @BindViews({R.id.nc215a, R.id.nc215b, R.id.nc215c, R.id.nc215d, R.id.nc215e, R.id.nc215f,
            R.id.nc215g, R.id.nc215h, R.id.nc215i, R.id.nc217a, R.id.nc217b, R.id.nc217c,
            R.id.nc217d, R.id.nc217e, R.id.nc217f, R.id.nc217g, R.id.nc217h, R.id.nc217i,
            R.id.nc217j, R.id.nc217k, R.id.nc217l, R.id.nc217m, R.id.nc217n, R.id.nc217o,
            R.id.nc217p, R.id.nc217q})
    List<RadioGroup> grpnc215;
    @BindViews({R.id.nc215aa, R.id.nc215ba, R.id.nc215ca, R.id.nc215da, R.id.nc215ea, R.id.nc215fa,
            R.id.nc215ga, R.id.nc215ha, R.id.nc215ia, R.id.nc217aa, R.id.nc217ba, R.id.nc217ca,
            R.id.nc217da, R.id.nc217ea, R.id.nc217fa, R.id.nc217ga, R.id.nc217ha, R.id.nc217ia,
            R.id.nc217ja, R.id.nc217ka, R.id.nc217la, R.id.nc217ma, R.id.nc217na, R.id.nc217oa,
            R.id.nc217pa, R.id.nc217qa})
    List<RadioButton> nc215yes;
    @BindViews({R.id.nc215ab, R.id.nc215bb, R.id.nc215cb, R.id.nc215db, R.id.nc215eb, R.id.nc215fb,
            R.id.nc215gb, R.id.nc215hb, R.id.nc215ib, R.id.nc217ab, R.id.nc217bb, R.id.nc217cb,
            R.id.nc217db, R.id.nc217eb, R.id.nc217fb, R.id.nc217gb, R.id.nc217hb, R.id.nc217ib,
            R.id.nc217jb, R.id.nc217kb, R.id.nc217lb, R.id.nc217mb, R.id.nc217nb, R.id.nc217ob,
            R.id.nc217pb, R.id.nc217qb})
    List<RadioButton> nc215no;
    @BindViews({R.id.nc215a98, R.id.nc215b98, R.id.nc215c98, R.id.nc215d98, R.id.nc215e98, R.id.nc215f98,
            R.id.nc215g98, R.id.nc215h98, R.id.nc215i98, R.id.nc217a98, R.id.nc217b98, R.id.nc217c98,
            R.id.nc217d98, R.id.nc217e98, R.id.nc217f98, R.id.nc217g98, R.id.nc217h98, R.id.nc217i98,
            R.id.nc217j98, R.id.nc217k98, R.id.nc217l98, R.id.nc217m98, R.id.nc217n98, R.id.nc217o98,
            R.id.nc217p98, R.id.nc217q98})
    List<RadioButton> nc215dkn;
    public RadioGroup.OnCheckedChangeListener check = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {


            ValidateForm();

            if (isoneYes()) {
                //bi.fldGrpnc218.setVisibility(View.GONE);
                clearClass.ClearAllFields(bi.fldGrpnc218, false);
                clearClass.ClearAllFields(bi.fldGrpnc219, true);
                //bi.fldGrpnc219.setVisibility(View.VISIBLE);
                //bi.nc218.clearCheck();
            } else {
                clearClass.ClearAllFields(bi.fldGrpnc218, true);
                clearClass.ClearAllFields(bi.fldGrpnc219, false);
                //bi.fldGrpnc218.setVisibility(View.VISIBLE);
                //bi.fldGrpnc219.setVisibility(View.VISIBLE);
            }


        }
    };
    public RadioGroup.OnCheckedChangeListener nc215 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            ValidateForm();
            if (group == bi.nc215b) {
                if (bi.nc215ba.isChecked()) {
                    //clearClass.ClearAllFields(bi.nc215bx, true);
                    bi.nc215bx.setVisibility(View.VISIBLE);
                    bi.nc215bx.setEnabled(true);
                } else {
                    //bi.nc215bx.setVisibility(View.GONE);
                    bi.nc215bx.setText(null);
                    bi.nc215bx.setEnabled(false);

                }
            }

            if (group == bi.nc215c) {
                if (bi.nc215ca.isChecked()) {
                    bi.nc215cx.setVisibility(View.VISIBLE);
                    bi.nc215cx.setEnabled(true);
                } else {
                    //bi.nc215cx.setVisibility(View.GONE);
                    bi.nc215cx.setText(null);
                    bi.nc215cx.setEnabled(false);
                }
            }

            if (group == bi.nc215f) {
                if (bi.nc215fa.isChecked()) {
                    bi.nc215fx.setVisibility(View.VISIBLE);
                    bi.nc215fx.setEnabled(true);
                } else {
                    //bi.nc215fx.setVisibility(View.GONE);
                    bi.nc215fx.setEnabled(false);
                    bi.nc215fx.setText(null);
                }
            }

        }
    };
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_c2);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c2);
        ButterKnife.bind(this);
        db = new DatabaseHelper(this);
        bi.setCallback(this);

        this.setTitle(getResources().getString(R.string.nc2heading));

        setupViews();

//        Validation Boolean
        MainApp.validateFlag = false;

        if (SectionC1Activity.editChildFlag) {
            bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                    + "\n\n" + SectionC1Activity.editMotherName + " : " + getString(R.string.nh212a));
        } else {
            if (!SectionC1Activity.isNA) {
                bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionB1Activity.wraName + " : " + getString(R.string.nh212a));
            } else {
                bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionC1Activity.careTaker + " : " + getString(R.string.nh113));
            }
        }

        bi.txtnc206.setText(bi.txtnc206.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc207.setText(bi.txtnc207.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc209.setText(bi.txtnc209.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc210.setText(bi.txtnc210.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc211.setText(bi.txtnc211.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc212.setText(bi.txtnc212.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc212a.setText(bi.txtnc212a.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc213.setText(bi.txtnc213.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc214.setText(bi.txtnc214.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc215.setText(bi.txtnc215.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc217.setText(bi.txtnc217.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc218.setText(bi.txtnc218.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc219.setText(bi.txtnc219.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc220.setText(bi.txtnc220.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc221.setText(bi.txtnc221.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc223.setText(bi.txtnc223.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtnc212.setText(bi.txtnc212.getText().toString().replace("Name", SectionC1Activity.selectedChildName));


        bi.nc207a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bi.nc207h.setEnabled(false);
                    bi.nc207h.setText(null);
                    bi.nc207d.setEnabled(false);
                    bi.nc207d.setText(null);

                } /*else {
                    bi.nc207h.setEnabled(true);
                    bi.nc207d.setEnabled(true);
                }*/
            }
        });

       /* bi.nc207b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bi.nc207h.setEnabled(true);
                    bi.nc207d.setEnabled(false);
                    bi.nc207d.setText(null);
                } else {
                    bi.nc207h.setEnabled(false);
                    bi.nc207h.setText(null);
                }
            }
        });

        bi.nc207c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bi.nc207d.setEnabled(true);
                    bi.nc207h.setEnabled(false);
                    bi.nc207h.setText(null);
                } else {
                    bi.nc207d.setEnabled(false);
                    bi.nc207d.setText(null);
                }
            }
        });*/


        bi.nc220.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (bi.nc220a.isChecked()) {
                    bi.nc221a.setEnabled(true);
                    bi.nc221b.setEnabled(true);
                    bi.nc221c.setEnabled(true);
                    bi.nc22196.setEnabled(true);

                    bi.nc222a.setEnabled(true);
                    bi.nc222b.setEnabled(true);
                    bi.nc222c.setEnabled(true);
                    bi.nc22296.setEnabled(true);


                } else {
                    bi.nc221.clearCheck();
                    bi.nc222.clearCheck();

                    bi.nc221a.setEnabled(false);
                    bi.nc221b.setEnabled(false);
                    bi.nc221c.setEnabled(false);
                    bi.nc22196.setEnabled(false);

                    bi.nc222a.setEnabled(false);
                    bi.nc222b.setEnabled(false);
                    bi.nc222c.setEnabled(false);
                    bi.nc22296.setEnabled(false);
                }
            }
        });

        autoPopulateFields();

    }

    private void autoPopulateFields() {
        ChildContract childContract = db.getsC2();

        if (!childContract.getsC2().equals("")) {

            JSONC2ModelClass jsonC2 = JSONUtilClass.getModelFromJSON(childContract.getsC2(), JSONC2ModelClass.class);

            if (!jsonC2.getnc206().equals("0")) {
                bi.nc206.check(
                        jsonC2.getnc206().equals("1") ? bi.nc206a.getId()
                                : jsonC2.getnc206().equals("2") ? bi.nc206b.getId()
                                : bi.nc20698.getId()
                );
            }


            bi.nc207h.setText(jsonC2.getnc207h());
            bi.nc207d.setText(jsonC2.getnc207d());


            if (!jsonC2.getnc207().equals("0")) {
                bi.nc207.check(
                        jsonC2.getnc207().equals("1") ? bi.nc207a.getId()
                                : jsonC2.getnc207().equals("2") ? bi.nc207b.getId()
                                : bi.nc207c.getId()
                );
            }
            if (!jsonC2.getnc208().equals("0")) {
                bi.nc208.check(
                        jsonC2.getnc208().equals("1") ? bi.nc208a.getId()
                                : jsonC2.getnc208().equals("2") ? bi.nc208b.getId()
                                : bi.nc20898.getId()
                );
            }
            if (!jsonC2.getnc209().equals("0")) {
                bi.nc209.check(
                        jsonC2.getnc209().equals("1") ? bi.nc209a.getId()
                                : jsonC2.getnc209().equals("2") ? bi.nc209b.getId()
                                : jsonC2.getnc209().equals("3") ? bi.nc209c.getId()
                                : bi.nc20996.getId()
                );
            }

            bi.nc20996x.setText(jsonC2.getnc20996x());


            if (!jsonC2.getnc210().equals("0")) {
                bi.nc210.check(
                        jsonC2.getnc210().equals("1") ? bi.nc210a.getId()
                                : bi.nc210b.getId()
                );
            }


            if (!jsonC2.getNc211a().equals("0")) {
                bi.nc211a.setChecked(true);
            }
            if (!jsonC2.getNc211b().equals("0")) {
                bi.nc211b.setChecked(true);
            }
            if (!jsonC2.getNc211c().equals("0")) {
                bi.nc211c.setChecked(true);
            }
            if (!jsonC2.getNc211d().equals("0")) {
                bi.nc211d.setChecked(true);
            }

            if (!jsonC2.getNc211e().equals("0")) {
                bi.nc211e.setChecked(true);
            }
            if (!jsonC2.getNc211f().equals("0")) {
                bi.nc211f.setChecked(true);
            }

            if (!jsonC2.getNc211g().equals("0")) {
                bi.nc211g.setChecked(true);
            }
            if (!jsonC2.getNc211h().equals("0")) {
                bi.nc211h.setChecked(true);
            }
            if (!jsonC2.getNc211i().equals("0")) {
                bi.nc211i.setChecked(true);
            }
            if (!jsonC2.getNc211j().equals("0")) {
                bi.nc211j.setChecked(true);
            }
            if (!jsonC2.getNc21196().equals("0")) {
                bi.nc21196.setChecked(true);
            }
            if (!jsonC2.getNc21199().equals("0")) {
                bi.nc21199.setChecked(true);
            }

            bi.nc21196x.setText(jsonC2.getnc21196x());

            if (!jsonC2.getnc212().equals("0")) {
                bi.nc212.check(
                        jsonC2.getnc212().equals("1") ? bi.nc212a.getId()
                                : jsonC2.getnc212().equals("2") ? bi.nc212b.getId()
                                : bi.nc21298.getId()
                );
            }
//            here
            if (!jsonC2.getnc212a().equals("0")) {
                bi.nc21201.check(
                        jsonC2.getnc212a().equals("1") ? bi.nc21201a.getId()
                                : jsonC2.getnc212a().equals("2") ? bi.nc21201b.getId()
                                : bi.nc2120198.getId()
                );
            }

            if (!jsonC2.getnc213().equals("0")) {
                bi.nc213.check(
                        jsonC2.getnc213().equals("1") ? bi.nc213a.getId()
                                : jsonC2.getnc213().equals("2") ? bi.nc213b.getId()
                                : bi.nc21398.getId()
                );
            }
            if (!jsonC2.getnc214().equals("0")) {
                bi.nc214.check(
                        jsonC2.getnc214().equals("1") ? bi.nc214a.getId()
                                : jsonC2.getnc214().equals("2") ? bi.nc214b.getId()
                                : bi.nc21498.getId()
                );
            }
            if (!jsonC2.getnc215a().equals("0")) {
                bi.nc215a.check(
                        jsonC2.getnc215a().equals("1") ? bi.nc215aa.getId()
                                : jsonC2.getnc215a().equals("2") ? bi.nc215ab.getId()
                                : bi.nc215a98.getId()
                );
            }
            if (!jsonC2.getnc215b().equals("0")) {
                bi.nc215b.check(
                        jsonC2.getnc215b().equals("1") ? bi.nc215ba.getId()
                                : jsonC2.getnc215b().equals("2") ? bi.nc215bb.getId()
                                : bi.nc215b98.getId()
                );
            }
            bi.nc215bx.setText(jsonC2.getnc215bx());

            if (!jsonC2.getnc215c().equals("0")) {
                bi.nc215c.check(
                        jsonC2.getnc215c().equals("1") ? bi.nc215ca.getId()
                                : jsonC2.getnc215c().equals("2") ? bi.nc215cb.getId()
                                : bi.nc215c98.getId()
                );
            }
            bi.nc215cx.setText(jsonC2.getnc215cx());


            if (!jsonC2.getnc215d().equals("0")) {
                bi.nc215d.check(
                        jsonC2.getnc215d().equals("1") ? bi.nc215da.getId()
                                : jsonC2.getnc215d().equals("2") ? bi.nc215db.getId()
                                : bi.nc215d98.getId()
                );
            }
            if (!jsonC2.getnc215e().equals("0")) {
                bi.nc215e.check(
                        jsonC2.getnc215e().equals("1") ? bi.nc215ea.getId()
                                : jsonC2.getnc215e().equals("2") ? bi.nc215eb.getId()
                                : bi.nc215e98.getId()
                );
            }
            if (!jsonC2.getnc215f().equals("0")) {
                bi.nc215f.check(
                        jsonC2.getnc215f().equals("1") ? bi.nc215fa.getId()
                                : jsonC2.getnc215f().equals("2") ? bi.nc215fb.getId()
                                : bi.nc215f98.getId()
                );
            }
            bi.nc215fx.setText(jsonC2.getnc215fx());

            if (!jsonC2.getnc215g().equals("0")) {
                bi.nc215g.check(
                        jsonC2.getnc215g().equals("1") ? bi.nc215ga.getId()
                                : jsonC2.getnc215g().equals("2") ? bi.nc215gb.getId()
                                : bi.nc215g98.getId()
                );
            }
            if (!jsonC2.getnc215h().equals("0")) {
                bi.nc215h.check(
                        jsonC2.getnc215h().equals("1") ? bi.nc215ha.getId()
                                : jsonC2.getnc215h().equals("2") ? bi.nc215hb.getId()
                                : bi.nc215h98.getId()
                );
            }
            if (!jsonC2.getnc215i().equals("0")) {
                bi.nc215i.check(
                        jsonC2.getnc215i().equals("1") ? bi.nc215ia.getId()
                                : jsonC2.getnc215i().equals("2") ? bi.nc215ib.getId()
                                : bi.nc215i98.getId()
                );
            }


            if (!jsonC2.getnc217a().equals("0")) {
                bi.nc217a.check(
                        jsonC2.getnc217a().equals("1") ? bi.nc217aa.getId()
                                : jsonC2.getnc217a().equals("2") ? bi.nc217ab.getId()
                                : bi.nc217a98.getId()
                );
            }
            if (!jsonC2.getnc217b().equals("0")) {
                bi.nc217b.check(
                        jsonC2.getnc217b().equals("1") ? bi.nc217ba.getId()
                                : jsonC2.getnc217b().equals("2") ? bi.nc217bb.getId()
                                : bi.nc217b98.getId()
                );
            }
            if (!jsonC2.getnc217c().equals("0")) {
                bi.nc217c.check(
                        jsonC2.getnc217c().equals("1") ? bi.nc217ca.getId()
                                : jsonC2.getnc217c().equals("2") ? bi.nc217cb.getId()
                                : bi.nc217c98.getId()
                );
            }
            if (!jsonC2.getnc217d().equals("0")) {
                bi.nc217d.check(
                        jsonC2.getnc217d().equals("1") ? bi.nc217da.getId()
                                : jsonC2.getnc217d().equals("2") ? bi.nc217db.getId()
                                : bi.nc217d98.getId()
                );
            }
            if (!jsonC2.getnc217e().equals("0")) {
                bi.nc217e.check(
                        jsonC2.getnc217e().equals("1") ? bi.nc217ea.getId()
                                : jsonC2.getnc217e().equals("2") ? bi.nc217eb.getId()
                                : bi.nc217e98.getId()
                );
            }
            if (!jsonC2.getnc217f().equals("0")) {
                bi.nc217f.check(
                        jsonC2.getnc217f().equals("1") ? bi.nc217fa.getId()
                                : jsonC2.getnc217f().equals("2") ? bi.nc217fb.getId()
                                : bi.nc217f98.getId()
                );
            }
            if (!jsonC2.getnc217g().equals("0")) {
                bi.nc217g.check(
                        jsonC2.getnc217g().equals("1") ? bi.nc217ga.getId()
                                : jsonC2.getnc217a().equals("2") ? bi.nc217gb.getId()
                                : bi.nc217g98.getId()
                );
            }
            if (!jsonC2.getnc217h().equals("0")) {
                bi.nc217h.check(
                        jsonC2.getnc217h().equals("1") ? bi.nc217ha.getId()
                                : jsonC2.getnc217h().equals("2") ? bi.nc217hb.getId()
                                : bi.nc217h98.getId()
                );
            }
            if (!jsonC2.getnc217i().equals("0")) {
                bi.nc217i.check(
                        jsonC2.getnc217i().equals("1") ? bi.nc217ia.getId()
                                : jsonC2.getnc217i().equals("2") ? bi.nc217ib.getId()
                                : bi.nc217i98.getId()
                );
            }
            if (!jsonC2.getnc217j().equals("0")) {
                bi.nc217j.check(
                        jsonC2.getnc217j().equals("1") ? bi.nc217ja.getId()
                                : jsonC2.getnc217j().equals("2") ? bi.nc217jb.getId()
                                : bi.nc217j98.getId()
                );
            }
            if (!jsonC2.getnc217k().equals("0")) {
                bi.nc217k.check(
                        jsonC2.getnc217k().equals("1") ? bi.nc217ka.getId()
                                : jsonC2.getnc217k().equals("2") ? bi.nc217kb.getId()
                                : bi.nc217k98.getId()
                );
            }
            if (!jsonC2.getnc217l().equals("0")) {
                bi.nc217l.check(
                        jsonC2.getnc217l().equals("1") ? bi.nc217la.getId()
                                : jsonC2.getnc217l().equals("2") ? bi.nc217lb.getId()
                                : bi.nc217l98.getId()
                );
            }
            if (!jsonC2.getnc217m().equals("0")) {
                bi.nc217m.check(
                        jsonC2.getnc217m().equals("1") ? bi.nc217ma.getId()
                                : jsonC2.getnc217m().equals("2") ? bi.nc217mb.getId()
                                : bi.nc217m98.getId()
                );
            }
            if (!jsonC2.getnc217a().equals("0")) {
                bi.nc217a.check(
                        jsonC2.getnc217a().equals("1") ? bi.nc217aa.getId()
                                : jsonC2.getnc217a().equals("2") ? bi.nc217ab.getId()
                                : bi.nc217a98.getId()
                );
            }
            if (!jsonC2.getnc217n().equals("0")) {
                bi.nc217n.check(
                        jsonC2.getnc217n().equals("1") ? bi.nc217na.getId()
                                : jsonC2.getnc217n().equals("2") ? bi.nc217nb.getId()
                                : bi.nc217n98.getId()
                );
            }
            if (!jsonC2.getnc217o().equals("0")) {
                bi.nc217o.check(
                        jsonC2.getnc217o().equals("1") ? bi.nc217oa.getId()
                                : jsonC2.getnc217o().equals("2") ? bi.nc217ob.getId()
                                : bi.nc217o98.getId()
                );
            }
            if (!jsonC2.getnc217p().equals("0")) {
                bi.nc217p.check(
                        jsonC2.getnc217p().equals("1") ? bi.nc217pa.getId()
                                : jsonC2.getnc217p().equals("2") ? bi.nc217pb.getId()
                                : bi.nc217p98.getId()
                );
            }
            if (!jsonC2.getnc217q().equals("0")) {
                bi.nc217q.check(
                        jsonC2.getnc217q().equals("1") ? bi.nc217qa.getId()
                                : jsonC2.getnc217q().equals("2") ? bi.nc217qb.getId()
                                : bi.nc217q98.getId()
                );
            }
            if (!jsonC2.getnc218().equals("0")) {
                bi.nc218.check(
                        jsonC2.getnc218().equals("1") ? bi.nc218a.getId()
                                : jsonC2.getnc218().equals("2") ? bi.nc218b.getId()
                                : bi.nc21898.getId()
                );
            }

            if (jsonC2.getnc219().equals("98")) {
                bi.nc21998.setChecked(true);
            } else {
                bi.nc219x.setText(jsonC2.getnc219());
            }


            if (!jsonC2.getnc219().equals("0")) {
                bi.nc219.check(
                        jsonC2.getnc219().equals("1") ? bi.nc219a.getId()
                                : bi.nc21998.getId()
                );
            }

            if (!jsonC2.getnc220().equals("0")) {
                bi.nc220.check(
                        jsonC2.getnc220().equals("1") ? bi.nc220a.getId()
                                : jsonC2.getnc220().equals("2") ? bi.nc220b.getId()
                                : bi.nc22098.getId()
                );
            }
            if (!jsonC2.getnc221().equals("0")) {
                bi.nc221.check(
                        jsonC2.getnc221().equals("1") ? bi.nc221a.getId()
                                : jsonC2.getnc221().equals("2") ? bi.nc221b.getId()
                                : jsonC2.getnc221().equals("3") ? bi.nc221c.getId()
                                : bi.nc22196.getId()
                );
            }
            bi.nc22196x.setText(jsonC2.getnc22196x());
            if (!jsonC2.getnc222().equals("0")) {
                bi.nc222.check(
                        jsonC2.getnc222().equals("1") ? bi.nc222a.getId()
                                : jsonC2.getnc222().equals("2") ? bi.nc222b.getId()
                                : jsonC2.getnc222().equals("3") ? bi.nc222c.getId()
                                : bi.nc22296.getId()
                );
            }
            bi.nc22296x.setText(jsonC2.getnc22296x());

            if (!jsonC2.getnc223().equals("0")) {
                bi.nc223.check(
                        jsonC2.getnc223().equals("1") ? bi.nc223a.getId()
                                : jsonC2.getnc223().equals("2") ? bi.nc223b.getId()
                                : bi.nc22398.getId()
                );
            }

        }

    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(this, "You can't go back.", Toast.LENGTH_SHORT).show();
        try {
            SaveDraft();
            UpdateDB();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onBackPressed();

    }

    public void BtnContinue() {

//        Validation Boolean
        MainApp.validateFlag = true;

        //Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

//                finish();
                backPressed = true;

                startActivity(new Intent(this, SectionC3Activity.class)
                        .putExtra("selectedChild", selectedChild));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        if (SectionC1Activity.editChildFlag) {
            finish();
            startActivity(new Intent(this, ViewMemberActivity.class)
                    .putExtra("flagEdit", false)
                    .putExtra("comingBack", true)
                    .putExtra("cluster", MainApp.cc.getClusterno())
                    .putExtra("hhno", MainApp.cc.getHhno())
            );
        } else {
            MainApp.endChildActivity(this, this, false);
        }

    }

    private boolean ValidateForm() {


        if (!validatorClass.EmptyRadioButton(this, bi.nc206, bi.nc206a, getString(R.string.nc206))) {
            return false;
        }

        if (bi.nc206a.isChecked()) {
            if (!validatorClass.EmptyRadioButton(this, bi.nc207, bi.nc207a, getString(R.string.nc207))) {
                return false;
            }

            if (bi.nc207b.isChecked()) {
                if (!validatorClass.EmptyTextBox(this, bi.nc207h, getString(R.string.nc207b))) {
                    return false;
                }

            }

            if (bi.nc207c.isChecked()) {
                if (!validatorClass.EmptyTextBox(this, bi.nc207d, getString(R.string.nc207c))) {
                    return false;
                }

            }

            if (!validatorClass.EmptyRadioButton(this, bi.nc208, bi.nc208a, getString(R.string.nc208))) {
                return false;
            }

            if (bi.nc208b.isChecked()) {

                if (!validatorClass.EmptyRadioButton(this, bi.nc209, bi.nc209a, getString(R.string.nc209))) {
                    return false;
                }
                if (!validatorClass.EmptyRadioButton(this, bi.nc209, bi.nc20996, bi.nc20996x, getString(R.string.nc209))) {
                    return false;
                }
            }

            if (!validatorClass.EmptyRadioButton(this, bi.nc210, bi.nc210a, getString(R.string.nc210))) {
                return false;
            }

            if (bi.nc210a.isChecked()) {
                if (!validatorClass.EmptyCheckBox(this, bi.nc211, bi.nc211a, getString(R.string.nc211))) {
                    return false;
                }
                if (!validatorClass.EmptyCheckBox(this, bi.nc211, bi.nc21196, bi.nc21196x, getString(R.string.nc211))) {
                    return false;
                }
            }


            if (!validatorClass.EmptyRadioButton(this, bi.nc212, bi.nc212a, getString(R.string.nc212))) {
                return false;
            }
        }

        if (!bi.nc212a.isChecked()) {
            if (!validatorClass.EmptyRadioButton(this, bi.nc21201, bi.nc21201a, getString(R.string.nc212a))) {
                return false;
            }
        }


        if (!validatorClass.EmptyRadioButton(this, bi.nc213, bi.nc213a, getString(R.string.nc213))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc214, bi.nc214a, getString(R.string.nc214))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc215a, bi.nc215aa, getString(R.string.nc215a))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc215b, bi.nc215ba, getString(R.string.nc215b))) {
            return false;
        }

        if (bi.nc215ba.isChecked()) {
            if (!validatorClass.EmptyTextBox(this, bi.nc215bx, getString(R.string.nc215b))) {
                return false;
            }


        }
        if (!validatorClass.EmptyRadioButton(this, bi.nc215c, bi.nc215ca, getString(R.string.nc215c))) {
            return false;
        }

        if (bi.nc215ca.isChecked()) {
            if (!validatorClass.EmptyTextBox(this, bi.nc215cx, getString(R.string.nc215c))) {
                return false;
            }


        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc215d, bi.nc215da, getString(R.string.nc215d))) {
            return false;
        }


        if (!validatorClass.EmptyRadioButton(this, bi.nc215e, bi.nc215ea, getString(R.string.nc215e))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc215f, bi.nc215fa, getString(R.string.nc215f))) {
            return false;
        }

        if (bi.nc215fa.isChecked()) {
            if (!validatorClass.EmptyTextBox(this, bi.nc215fx, getString(R.string.nc215f))) {
                return false;
            }


        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc215g, bi.nc215ga, getString(R.string.nc215g))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc215h, bi.nc215ha, getString(R.string.nc215h))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc215i, bi.nc215ia, getString(R.string.nc215i))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217a, bi.nc217aa, getString(R.string.nc217a))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217b, bi.nc217ba, getString(R.string.nc217b))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217c, bi.nc217ca, getString(R.string.nc217c))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217d, bi.nc217da, getString(R.string.nc217d))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217e, bi.nc217ea, getString(R.string.nc217e))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217f, bi.nc217fa, getString(R.string.nc217f))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217g, bi.nc217ga, getString(R.string.nc217g))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217h, bi.nc217ha, getString(R.string.nc217h))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217i, bi.nc217ia, getString(R.string.nc217i))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217j, bi.nc217ja, getString(R.string.nc217j))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217k, bi.nc217ka, getString(R.string.nc217k))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217l, bi.nc217la, getString(R.string.nc217l))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217m, bi.nc217ma, getString(R.string.nc217m))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217n, bi.nc217na, getString(R.string.nc217n))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217o, bi.nc217oa, getString(R.string.nc217o))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217p, bi.nc217pa, getString(R.string.nc217p))) {
            return false;
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc217q, bi.nc217qa, getString(R.string.nc217q))) {
            return false;
        }

        if (isAllNo() || isAlldkn() || (!isoneYes())) {
            if (!validatorClass.EmptyRadioButton(this, bi.nc218, bi.nc218a, getString(R.string.nc218))) {
                return false;
            }
        }

        if ((isAllNo() || isAlldkn()) && bi.nc218a.isChecked()) {
            Toast.makeText(this, "ERROR: " + getString(R.string.nc218) + "Atleast one should be Yes", Toast.LENGTH_SHORT).show();
            bi.nc218a.setError(getString(R.string.nc218));
            Log.i(SectionC2Activity.class.getSimpleName(), "nc218: This data is Required!");
            return false;
        } else {
            bi.nc218a.setError(null);
        }

            /*if (isAlldkn() && bi.nc218a.isChecked()) {
                Toast.makeText(this, "ERROR: " + getString(R.string.nc218) + "Atleast one should be Yes", Toast.LENGTH_SHORT).show();
                bi.nc218a.setError(getString(R.string.nc218));
                Log.i(SectionC2Activity.class.getSimpleName(), "nc218: This data is Required!");
                return false;
            } else {
                bi.nc218a.setError(null);
            }
*/
        if (isoneYes()) {
            if (!validatorClass.EmptyRadioButton(this, bi.nc219, bi.nc219a, bi.nc219x, getString(R.string.nc219))) {
                return false;
            }
        }

        if (bi.nc219a.isChecked()) {
            if (!validatorClass.EmptyTextBox(this, bi.nc219x, getString(R.string.nc219))) {
                return false;
            }
            if (!validatorClass.RangeTextBox(this, bi.nc219x, 1, 10, getString(R.string.nc219), " times")) {
                return false;
            }
        }

        if (!validatorClass.EmptyRadioButton(this, bi.nc220, bi.nc220a, getString(R.string.nc220))) {
            return false;
        }


        if (bi.nc220a.isChecked()) {

            if (!validatorClass.EmptyRadioButton(this, bi.nc221, bi.nc221a, getString(R.string.nc221))) {
                return false;
            }

            if (!validatorClass.EmptyRadioButton(this, bi.nc221, bi.nc22196, bi.nc22196x, getString(R.string.nc221))) {
                return false;
            }

            if (bi.nc221a.isChecked()) {
                if (!validatorClass.EmptyRadioButton(this, bi.nc222, bi.nc222a, getString(R.string.nc222))) {
                    return false;
                }

                if (!validatorClass.EmptyRadioButton(this, bi.nc222, bi.nc22296, bi.nc22296x, getString(R.string.nc222))) {
                    return false;
                }
            }
        }


        return validatorClass.EmptyRadioButton(this, bi.nc223, bi.nc223a, getString(R.string.nc223));
    }

    private void SaveDraft() throws JSONException {
        //Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sC2 = new JSONObject();
        if (backPressed) {
            sC2.put("updatedate_nc2", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        if (SectionC1Activity.editChildFlag) {
            sC2.put("edit_updatedate_sc2", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

//        nc2_child_name
        //sC2.put("nc2_child_name", selectedChild.getName());
//        nc2_line_no
        //sC2.put("nc2_line_noSerial", selectedChild.getSerialNo());


        sC2.put("nc206", bi.nc206a.isChecked() ? "1"
                : bi.nc206b.isChecked() ? "2"
                : bi.nc20698.isChecked() ? "98"
                : "0");

        sC2.put("nc207", bi.nc207a.isChecked() ? "1"
                : bi.nc207b.isChecked() ? "2"
                : bi.nc207c.isChecked() ? "3"
                : "0");

        sC2.put("nc207h", bi.nc207h.getText().toString());
        sC2.put("nc207d", bi.nc207d.getText().toString());

        sC2.put("nc208", bi.nc208a.isChecked() ? "1"
                : bi.nc208b.isChecked() ? "2"
                : bi.nc20898.isChecked() ? "98"
                : "0");

        sC2.put("nc209", bi.nc209a.isChecked() ? "1"
                : bi.nc209b.isChecked() ? "2"
                : bi.nc209c.isChecked() ? "3"
                : bi.nc20996.isChecked() ? "96"
                : "0");
        sC2.put("nc20996x", bi.nc20996x.getText().toString());

        sC2.put("nc210", bi.nc210a.isChecked() ? "1"
                : bi.nc210b.isChecked() ? "2"
                : "0");
        sC2.put("nc211a", bi.nc211a.isChecked() ? "1" : "0");
        sC2.put("nc211b", bi.nc211b.isChecked() ? "2" : "0");
        sC2.put("nc211c", bi.nc211c.isChecked() ? "3" : "0");
        sC2.put("nc211d", bi.nc211d.isChecked() ? "4" : "0");
        sC2.put("nc211e", bi.nc211e.isChecked() ? "5" : "0");
        sC2.put("nc211f", bi.nc211f.isChecked() ? "6" : "0");
        sC2.put("nc211g", bi.nc211g.isChecked() ? "7" : "0");
        sC2.put("nc211h", bi.nc211h.isChecked() ? "8" : "0");
        sC2.put("nc211i", bi.nc211i.isChecked() ? "9" : "0");
        sC2.put("nc211j", bi.nc211j.isChecked() ? "10" : "0");
        sC2.put("nc21199", bi.nc21199.isChecked() ? "99" : "0");
        sC2.put("nc21196", bi.nc21196.isChecked() ? "96" : "0");

        sC2.put("nc21196x", bi.nc21196x.getText().toString());

        sC2.put("nc212", bi.nc212a.isChecked() ? "1"
                : bi.nc212b.isChecked() ? "2"
                : bi.nc21298.isChecked() ? "98"
                : "0");

        sC2.put("nc212a", bi.nc21201a.isChecked() ? "1"
                : bi.nc21201b.isChecked() ? "2"
                : bi.nc2120198.isChecked() ? "98"
                : "0");

        sC2.put("nc213", bi.nc213a.isChecked() ? "1"
                : bi.nc213b.isChecked() ? "2"
                : bi.nc21398.isChecked() ? "98"
                : "0");

        sC2.put("nc214", bi.nc214a.isChecked() ? "1"
                : bi.nc214b.isChecked() ? "2"
                : bi.nc21498.isChecked() ? "98"
                : "0");

        sC2.put("nc215a", bi.nc215aa.isChecked() ? "1"
                : bi.nc215ab.isChecked() ? "2"
                : bi.nc215a98.isChecked() ? "98"
                : "0");

        sC2.put("nc215b", bi.nc215ba.isChecked() ? "1"
                : bi.nc215bb.isChecked() ? "2"
                : bi.nc215b98.isChecked() ? "98"
                : "0");

        sC2.put("nc215bx", bi.nc215bx.getText().toString());
        sC2.put("nc215c", bi.nc215ca.isChecked() ? "1"
                : bi.nc215cb.isChecked() ? "2"
                : bi.nc215c98.isChecked() ? "98"
                : "0");
        sC2.put("nc215cx", bi.nc215cx.getText().toString());

        sC2.put("nc215d", bi.nc215da.isChecked() ? "1"
                : bi.nc215db.isChecked() ? "2"
                : bi.nc215d98.isChecked() ? "98"
                : "0");

        sC2.put("nc215e", bi.nc215ea.isChecked() ? "1"
                : bi.nc215eb.isChecked() ? "2"
                : bi.nc215e98.isChecked() ? "98"
                : "0");

        sC2.put("nc215f", bi.nc215fa.isChecked() ? "1"
                : bi.nc215fb.isChecked() ? "2"
                : bi.nc215f98.isChecked() ? "98"
                : "0");
        sC2.put("nc215fx", bi.nc215fx.getText().toString());


        sC2.put("nc215g", bi.nc215ga.isChecked() ? "1"
                : bi.nc215gb.isChecked() ? "2"
                : bi.nc215g98.isChecked() ? "98"
                : "0");

        sC2.put("nc215h", bi.nc215ha.isChecked() ? "1"
                : bi.nc215hb.isChecked() ? "2"
                : bi.nc215h98.isChecked() ? "98"
                : "0");

        sC2.put("nc215i", bi.nc215ia.isChecked() ? "1"
                : bi.nc215ib.isChecked() ? "2"
                : bi.nc215i98.isChecked() ? "98"
                : "0");

        sC2.put("nc217a", bi.nc217aa.isChecked() ? "1"
                : bi.nc217ab.isChecked() ? "2"
                : bi.nc217a98.isChecked() ? "98"
                : "0");

        sC2.put("nc217b", bi.nc217ba.isChecked() ? "1"
                : bi.nc217bb.isChecked() ? "2"
                : bi.nc217b98.isChecked() ? "98"
                : "0");

        sC2.put("nc217c", bi.nc217ca.isChecked() ? "1"
                : bi.nc217cb.isChecked() ? "2"
                : bi.nc217c98.isChecked() ? "98"
                : "0");

        sC2.put("nc217d", bi.nc217da.isChecked() ? "1"
                : bi.nc217db.isChecked() ? "2"
                : bi.nc217d98.isChecked() ? "98"
                : "0");

        sC2.put("nc217e", bi.nc217ea.isChecked() ? "1"
                : bi.nc217eb.isChecked() ? "2"
                : bi.nc217e98.isChecked() ? "98"
                : "0");

        sC2.put("nc217f", bi.nc217fa.isChecked() ? "1"
                : bi.nc217fb.isChecked() ? "2"
                : bi.nc217f98.isChecked() ? "98"
                : "0");
        sC2.put("nc217g", bi.nc217ga.isChecked() ? "1"
                : bi.nc217gb.isChecked() ? "2"
                : bi.nc217g98.isChecked() ? "98"
                : "0");

        sC2.put("nc217h", bi.nc217ha.isChecked() ? "1"
                : bi.nc217hb.isChecked() ? "2"
                : bi.nc217h98.isChecked() ? "98"
                : "0");

        sC2.put("nc217i", bi.nc217ia.isChecked() ? "1"
                : bi.nc217ib.isChecked() ? "2"
                : bi.nc217i98.isChecked() ? "98"
                : "0");

        sC2.put("nc217j", bi.nc217ja.isChecked() ? "1"
                : bi.nc217jb.isChecked() ? "2"
                : bi.nc217j98.isChecked() ? "98"
                : "0");

        sC2.put("nc217k", bi.nc217ka.isChecked() ? "1"
                : bi.nc217kb.isChecked() ? "2"
                : bi.nc217k98.isChecked() ? "98"
                : "0");

        sC2.put("nc217l", bi.nc217la.isChecked() ? "1"
                : bi.nc217lb.isChecked() ? "2"
                : bi.nc217l98.isChecked() ? "98"
                : "0");

        sC2.put("nc217m", bi.nc217ma.isChecked() ? "1"
                : bi.nc217mb.isChecked() ? "2"
                : bi.nc217m98.isChecked() ? "98"
                : "0");

        sC2.put("nc217n", bi.nc217na.isChecked() ? "1"
                : bi.nc217nb.isChecked() ? "2"
                : bi.nc217n98.isChecked() ? "98"
                : "0");

        sC2.put("nc217o", bi.nc217oa.isChecked() ? "1"
                : bi.nc217ob.isChecked() ? "2"
                : bi.nc217o98.isChecked() ? "98"
                : "0");

        sC2.put("nc217p", bi.nc217pa.isChecked() ? "1"
                : bi.nc217pb.isChecked() ? "2"
                : bi.nc217p98.isChecked() ? "98"
                : "0");

        sC2.put("nc217q", bi.nc217qa.isChecked() ? "1"
                : bi.nc217qb.isChecked() ? "2"
                : bi.nc217q98.isChecked() ? "98"
                : "0");

        sC2.put("nc218", bi.nc218a.isChecked() ? "1"
                : bi.nc218b.isChecked() ? "2"
                : bi.nc21898.isChecked() ? "98"
                : "0");

        sC2.put("nc219", bi.nc21998.isChecked() ? "98" : bi.nc219x.getText().toString());

        sC2.put("nc220", bi.nc220a.isChecked() ? "1"
                : bi.nc220b.isChecked() ? "2"
                : bi.nc22098.isChecked() ? "98"
                : "0");

        sC2.put("nc221", bi.nc221a.isChecked() ? "1"
                : bi.nc221b.isChecked() ? "2"
                : bi.nc221c.isChecked() ? "3"
                : bi.nc22196.isChecked() ? "96"
                : "0");
        sC2.put("nc22196x", bi.nc22196x.getText().toString());

        sC2.put("nc222", bi.nc222a.isChecked() ? "1"
                : bi.nc222b.isChecked() ? "2"
                : bi.nc222c.isChecked() ? "3"
                : bi.nc22296.isChecked() ? "96"
                : "0");
        sC2.put("nc22296x", bi.nc22296x.getText().toString());

        sC2.put("nc223", bi.nc223a.isChecked() ? "1"
                : bi.nc223b.isChecked() ? "2"
                : bi.nc22398.isChecked() ? "98"
                : "0");


        MainApp.cc.setsC2(String.valueOf(sC2));


    }

    private boolean UpdateDB() {

        //Long rowId;
        int updcount = db.updateSC2();

        if (updcount == 1) {
            //Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void setupViews() {
        //Get Intent
        selectedChild = (FamilyMembersContract) getIntent().getSerializableExtra("selectedChild");


        /*bi.nc205.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.nc205a.isChecked()) {
                    //bi.fldGrpnc206.setVisibility(View.VISIBLE);
                    clearClass.ClearAllFields(bi.fldGrpnc206, true);

                } else {
                    clearClass.ClearAllFields(bi.fldGrpnc206, false);

                }
            }
        });*/

        bi.nc206.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nc206a.isChecked()) {
                    //bi.fldGrpnc207.setVisibility(View.VISIBLE);
                    clearClass.ClearAllFields(bi.fldGrpnc207, true);

                } else {
                    clearClass.ClearAllFields(bi.fldGrpnc207, false);
                    /*bi.fldGrpnc207.setVisibility(View.GONE);
                    bi.nc207.clearCheck();
                    bi.nc207d.setText(null);
                    bi.nc207h.setText(null);
                    bi.nc208.clearCheck();
                    bi.nc209.clearCheck();
                    bi.nc20996x.setText(null);
                    bi.nc210.clearCheck();
                    bi.nc211.clearCheck();
                    bi.nc21196x.setText(null);
                    bi.nc212.clearCheck();
*/
                }
            }
        });

        bi.nc208.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nc208b.isChecked()) {
                    //bi.fldGrpnc209.setVisibility(View.VISIBLE);
                    clearClass.ClearAllFields(bi.fldGrpnc209, true);
                } else {
                    clearClass.ClearAllFields(bi.fldGrpnc209, false);
                    /*bi.fldGrpnc209.setVisibility(View.GONE);
                    bi.nc209.clearCheck();
                    bi.nc20996x.setText(null);*/
                }
            }
        });

        bi.nc210.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nc210a.isChecked()) {
                    //bi.fldGrpnc211.setVisibility(View.VISIBLE);
                    clearClass.ClearAllFields(bi.fldGrpnc211, true);
                } else {
                    clearClass.ClearAllFields(bi.fldGrpnc211, false);
                    /*bi.fldGrpnc211.setVisibility(View.GONE);
                    bi.nc211.clearCheck();
                    bi.nc21196x.setText(null);*/
                }
            }
        });

        bi.nc212.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ValidateForm();
                if (bi.nc212a.isChecked()) {
                    //bi.fldGrpnc212a.setVisibility(View.GONE);
                    clearClass.ClearAllFields(bi.fldGrpnc212a, false);
                    //bi.nc21201.clearCheck();
                } else {
                    //bi.fldGrpnc212a.setVisibility(View.VISIBLE);
                    clearClass.ClearAllFields(bi.fldGrpnc212a, true);
                }
            }
        });
        /*bi.nc20198.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clearClass.ClearAllFields(bi.fldGrpyear, false);
                    clearClass.ClearAllFields(bi.fldGrpmonths, false);
                    clearClass.ClearAllFields(bi.fldGrpdays, false);
                } else {
                    clearClass.ClearAllFields(bi.fldGrpyear, true);
                    clearClass.ClearAllFields(bi.fldGrpmonths, true);
                    clearClass.ClearAllFields(bi.fldGrpdays, true);
                }
            }
        });*/
        bi.nc21199.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.nc211a.setChecked(false);
                    bi.nc211b.setChecked(false);
                    bi.nc211c.setChecked(false);
                    bi.nc211d.setChecked(false);
                    bi.nc211e.setChecked(false);
                    bi.nc211f.setChecked(false);
                    bi.nc211g.setChecked(false);
                    bi.nc211h.setChecked(false);
                    bi.nc211i.setChecked(false);
                    bi.nc211j.setChecked(false);
                    bi.nc21196.setChecked(false);
                    bi.nc21196x.setText(null);
                }
            }
        });
        bi.nc21196.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    bi.nc21196x.setText(null);
                    bi.nc21196x.setEnabled(false);
                } else {
                    bi.nc21196x.setEnabled(true);
                    if (bi.nc21199.isChecked()) {
                        bi.nc21199.setChecked(false);
                    }
                }
            }
        });



        /*bi.nc218.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(bi.nc218a.isChecked())
                {
                    bi.fldGrpnc219.setVisibility(View.VISIBLE);
                }else{
                    bi.fldGrpnc219.setVisibility(View.GONE);
                    bi.nc219.clearCheck();
                    bi.nc219x.setText(null);
                }
            }
        });*/

        for (CheckBox chk : grpnc211) {
            chk.setOnCheckedChangeListener(check2);
        }
        for (RadioGroup rg : grpnc215) {
            rg.setOnCheckedChangeListener(check);
        }

        bi.nc215b.setOnCheckedChangeListener(nc215);
        bi.nc215c.setOnCheckedChangeListener(nc215);
        bi.nc215f.setOnCheckedChangeListener(nc215);

//        Listener

        bi.nc207.setOnCheckedChangeListener(this);
        bi.nc209.setOnCheckedChangeListener(this);
        //bi.nc211.setOnCheckedChangeListener(this);
        bi.nc21201.setOnCheckedChangeListener(this);
        bi.nc213.setOnCheckedChangeListener(this);
        bi.nc214.setOnCheckedChangeListener(this);
        bi.nc218.setOnCheckedChangeListener(this);
        bi.nc219.setOnCheckedChangeListener(this);
        bi.nc220.setOnCheckedChangeListener(this);
        //bi.nc221.setOnCheckedChangeListener(this);
        bi.nc222.setOnCheckedChangeListener(this);
        bi.nc223.setOnCheckedChangeListener(this);

        bi.nc221.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.nc221a.isChecked()) {
                    clearClass.ClearAllFields(bi.fldGrpnc222, true);
                } else {
                    clearClass.ClearAllFields(bi.fldGrpnc222, false);
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        ValidateForm();
    }

    /*@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ValidateForm();
    }*/

    public boolean isoneYes() {

        int i = 0;
        for (RadioButton rg : nc215yes) {
            if (rg.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    public boolean isAlldkn() {

        int i = 0;
        for (RadioButton rg : nc215dkn) {
            if (rg.isChecked())
                i++;
        }

        // Show answer here
        return i == nc215dkn.size();
    }

    public boolean isAllNo() {

        int i = 0;
        for (RadioButton rg : nc215no) {
            if (rg.isChecked())
                i++;
        }

        // Show answer here
        return i == nc215no.size();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
