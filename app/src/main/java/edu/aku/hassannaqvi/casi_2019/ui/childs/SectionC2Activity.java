package edu.aku.hassannaqvi.casi_2019.ui.childs;

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
import edu.aku.hassannaqvi.casi_2019.JSONModels.JSONC2ModelClass;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.ChildContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivitySectionC2Binding;
import edu.aku.hassannaqvi.casi_2019.other.JSONUtilClass;
import edu.aku.hassannaqvi.casi_2019.ui.mainUI.Menu2Activity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.SectionB1Activity;
import edu.aku.hassannaqvi.casi_2019.validation.ClearClass;
import edu.aku.hassannaqvi.casi_2019.validation.ValidatorClass;

public class SectionC2Activity extends Menu2Activity implements RadioGroup.OnCheckedChangeListener, TextWatcher {

    private final long DELAY = 1000;
    ActivitySectionC2Binding bi;
    public CheckBox.OnCheckedChangeListener check2 = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            ValidateForm();
            if (isChecked) {
                bi.cic21199.setChecked(false);
            }
        }
    };
    FamilyMembersContract selectedChild;
    DatabaseHelper db;
    Boolean backPressed = false;
    @BindViews({R.id.cic211a, R.id.cic211b, R.id.cic211c, R.id.cic211d, R.id.cic211e, R.id.cic211f,
            R.id.cic211g, R.id.cic211h, R.id.cic211i, R.id.cic211j})
    List<CheckBox> grpcic211;
    @BindViews({R.id.cic215a, R.id.cic215b, R.id.cic215c, R.id.cic215d, R.id.cic215e, R.id.cic215f,
            R.id.cic215g, R.id.cic215h, R.id.cic215i, R.id.cic217a, R.id.cic217b, R.id.cic217c,
            R.id.cic217d, R.id.cic217e, R.id.cic217f, R.id.cic217g, R.id.cic217h, R.id.cic217i,
            R.id.cic217j, R.id.cic217k, R.id.cic217l, R.id.cic217m, R.id.cic217n, R.id.cic217o,
            R.id.cic217p, R.id.cic217q})
    List<RadioGroup> grpcic215;
    @BindViews({R.id.cic215aa, R.id.cic215ba, R.id.cic215ca, R.id.cic215da, R.id.cic215ea, R.id.cic215fa,
            R.id.cic215ga, R.id.cic215ha, R.id.cic215ia, R.id.cic217aa, R.id.cic217ba, R.id.cic217ca,
            R.id.cic217da, R.id.cic217ea, R.id.cic217fa, R.id.cic217ga, R.id.cic217ha, R.id.cic217ia,
            R.id.cic217ja, R.id.cic217ka, R.id.cic217la, R.id.cic217ma, R.id.cic217na, R.id.cic217oa,
            R.id.cic217pa, R.id.cic217qa})
    List<RadioButton> cic215yes;
    @BindViews({R.id.cic215ab, R.id.cic215bb, R.id.cic215cb, R.id.cic215db, R.id.cic215eb, R.id.cic215fb,
            R.id.cic215gb, R.id.cic215hb, R.id.cic215ib, R.id.cic217ab, R.id.cic217bb, R.id.cic217cb,
            R.id.cic217db, R.id.cic217eb, R.id.cic217fb, R.id.cic217gb, R.id.cic217hb, R.id.cic217ib,
            R.id.cic217jb, R.id.cic217kb, R.id.cic217lb, R.id.cic217mb, R.id.cic217nb, R.id.cic217ob,
            R.id.cic217pb, R.id.cic217qb})
    List<RadioButton> cic215no;
    @BindViews({R.id.cic215a98, R.id.cic215b98, R.id.cic215c98, R.id.cic215d98, R.id.cic215e98, R.id.cic215f98,
            R.id.cic215g98, R.id.cic215h98, R.id.cic215i98, R.id.cic217a98, R.id.cic217b98, R.id.cic217c98,
            R.id.cic217d98, R.id.cic217e98, R.id.cic217f98, R.id.cic217g98, R.id.cic217h98, R.id.cic217i98,
            R.id.cic217j98, R.id.cic217k98, R.id.cic217l98, R.id.cic217m98, R.id.cic217n98, R.id.cic217o98,
            R.id.cic217p98, R.id.cic217q98})
    List<RadioButton> cic215dkn;
    public RadioGroup.OnCheckedChangeListener check = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {


//            ValidateForm();

            if (isoneYes()) {
                //bi.fldGrpcic218.setVisibility(View.GONE);
                ClearClass.ClearAllFields(bi.fldGrpcic218, false);
                ClearClass.ClearAllFields(bi.fldGrpcic219, true);
                //bi.fldGrpcic219.setVisibility(View.VISIBLE);
                //bi.cic218.clearCheck();
            } else {
                ClearClass.ClearAllFields(bi.fldGrpcic218, true);
                ClearClass.ClearAllFields(bi.fldGrpcic219, false);
                //bi.fldGrpcic218.setVisibility(View.VISIBLE);
                //bi.fldGrpcic219.setVisibility(View.VISIBLE);
            }


        }
    };
    public RadioGroup.OnCheckedChangeListener cic215 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

//            ValidateForm();
            if (group == bi.cic215b) {
                if (bi.cic215ba.isChecked()) {
                    //ClearClass.ClearAllFields(bi.cic215bx, true);
                    bi.cic215bx.setVisibility(View.VISIBLE);
                    bi.cic215bx.setEnabled(true);
                } else {
                    //bi.cic215bx.setVisibility(View.GONE);
                    bi.cic215bx.setText(null);
                    bi.cic215bx.setEnabled(false);

                }
            }

            if (group == bi.cic215c) {
                if (bi.cic215ca.isChecked()) {
                    bi.cic215cx.setVisibility(View.VISIBLE);
                    bi.cic215cx.setEnabled(true);
                } else {
                    //bi.cic215cx.setVisibility(View.GONE);
                    bi.cic215cx.setText(null);
                    bi.cic215cx.setEnabled(false);
                }
            }

            if (group == bi.cic215f) {
                if (bi.cic215fa.isChecked()) {
                    bi.cic215fx.setVisibility(View.VISIBLE);
                    bi.cic215fx.setEnabled(true);
                } else {
                    //bi.cic215fx.setVisibility(View.GONE);
                    bi.cic215fx.setEnabled(false);
                    bi.cic215fx.setText(null);
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

        this.setTitle(getResources().getString(R.string.cic2heading));

        setupViews();

//        Validation Boolean
        MainApp.validateFlag = false;

        if (SectionC1Activity.editChildFlag) {
            bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                    + "\n\n" + SectionC1Activity.editMotherName + " : " + getString(R.string.cih212a));
        } else {
            if (!SectionC1Activity.isNA) {
                bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionB1Activity.wraName + " : " + getString(R.string.cih212a));
            } else {
                bi.textName.setText(SectionC1Activity.selectedChildName + " : " + getString(R.string.childname)
                        + "\n\n" + SectionC1Activity.careTaker + " : " + getString(R.string.cih113));
            }
        }

        bi.txtcic206.setText(bi.txtcic206.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic207.setText(bi.txtcic207.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic209.setText(bi.txtcic209.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic210.setText(bi.txtcic210.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic211.setText(bi.txtcic211.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic212.setText(bi.txtcic212.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic212a.setText(bi.txtcic212a.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic213.setText(bi.txtcic213.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic214.setText(bi.txtcic214.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic215.setText(bi.txtcic215.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic217.setText(bi.txtcic217.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic218.setText(bi.txtcic218.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic219.setText(bi.txtcic219.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic220.setText(bi.txtcic220.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic221.setText(bi.txtcic221.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic223.setText(bi.txtcic223.getText().toString().replace("Name", SectionC1Activity.selectedChildName));
        bi.txtcic212.setText(bi.txtcic212.getText().toString().replace("Name", SectionC1Activity.selectedChildName));


        bi.cic207a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bi.cic207h.setEnabled(false);
                    bi.cic207h.setText(null);
                    bi.cic207d.setEnabled(false);
                    bi.cic207d.setText(null);

                } /*else {
                    bi.cic207h.setEnabled(true);
                    bi.cic207d.setEnabled(true);
                }*/
            }
        });

       /* bi.cic207b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bi.cic207h.setEnabled(true);
                    bi.cic207d.setEnabled(false);
                    bi.cic207d.setText(null);
                } else {
                    bi.cic207h.setEnabled(false);
                    bi.cic207h.setText(null);
                }
            }
        });

        bi.cic207c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bi.cic207d.setEnabled(true);
                    bi.cic207h.setEnabled(false);
                    bi.cic207h.setText(null);
                } else {
                    bi.cic207d.setEnabled(false);
                    bi.cic207d.setText(null);
                }
            }
        });*/


        bi.cic220.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (bi.cic220a.isChecked()) {
                    bi.cic221a.setEnabled(true);
                    bi.cic221b.setEnabled(true);
                    bi.cic221c.setEnabled(true);
                    bi.cic22196.setEnabled(true);

                    bi.cic222a.setEnabled(true);
                    bi.cic222b.setEnabled(true);
                    bi.cic222c.setEnabled(true);
                    bi.cic22296.setEnabled(true);


                } else {
                    bi.cic221.clearCheck();
                    bi.cic222.clearCheck();

                    bi.cic221a.setEnabled(false);
                    bi.cic221b.setEnabled(false);
                    bi.cic221c.setEnabled(false);
                    bi.cic22196.setEnabled(false);

                    bi.cic222a.setEnabled(false);
                    bi.cic222b.setEnabled(false);
                    bi.cic222c.setEnabled(false);
                    bi.cic22296.setEnabled(false);
                }
            }
        });

        autoPopulateFields();

    }

    private void autoPopulateFields() {
        ChildContract childContract = db.getsC2();

        if (!childContract.getsC2().equals("")) {

            JSONC2ModelClass jsonC2 = JSONUtilClass.getModelFromJSON(childContract.getsC2(), JSONC2ModelClass.class);

            if (!jsonC2.getcic206().equals("0")) {
                bi.cic206.check(
                        jsonC2.getcic206().equals("1") ? bi.cic206a.getId()
                                : jsonC2.getcic206().equals("2") ? bi.cic206b.getId()
                                : bi.cic20698.getId()
                );
            }


            bi.cic207h.setText(jsonC2.getcic207h());
            bi.cic207d.setText(jsonC2.getcic207d());


            if (!jsonC2.getcic207().equals("0")) {
                bi.cic207.check(
                        jsonC2.getcic207().equals("1") ? bi.cic207a.getId()
                                : jsonC2.getcic207().equals("2") ? bi.cic207b.getId()
                                : bi.cic207c.getId()
                );
            }
            if (!jsonC2.getcic208().equals("0")) {
                bi.cic208.check(
                        jsonC2.getcic208().equals("1") ? bi.cic208a.getId()
                                : jsonC2.getcic208().equals("2") ? bi.cic208b.getId()
                                : bi.cic20898.getId()
                );
            }
            if (!jsonC2.getcic209().equals("0")) {
                bi.cic209.check(
                        jsonC2.getcic209().equals("1") ? bi.cic209a.getId()
                                : jsonC2.getcic209().equals("2") ? bi.cic209b.getId()
                                : jsonC2.getcic209().equals("3") ? bi.cic209c.getId()
                                : bi.cic20996.getId()
                );
            }

            bi.cic20996x.setText(jsonC2.getcic20996x());


            if (!jsonC2.getcic210().equals("0")) {
                bi.cic210.check(
                        jsonC2.getcic210().equals("1") ? bi.cic210a.getId()
                                : bi.cic210b.getId()
                );
            }


            if (!jsonC2.getNc211a().equals("0")) {
                bi.cic211a.setChecked(true);
            }
            if (!jsonC2.getNc211b().equals("0")) {
                bi.cic211b.setChecked(true);
            }
            if (!jsonC2.getNc211c().equals("0")) {
                bi.cic211c.setChecked(true);
            }
            if (!jsonC2.getNc211d().equals("0")) {
                bi.cic211d.setChecked(true);
            }

            if (!jsonC2.getNc211e().equals("0")) {
                bi.cic211e.setChecked(true);
            }
            if (!jsonC2.getNc211f().equals("0")) {
                bi.cic211f.setChecked(true);
            }

            if (!jsonC2.getNc211g().equals("0")) {
                bi.cic211g.setChecked(true);
            }
            if (!jsonC2.getNc211h().equals("0")) {
                bi.cic211h.setChecked(true);
            }
            if (!jsonC2.getNc211i().equals("0")) {
                bi.cic211i.setChecked(true);
            }
            if (!jsonC2.getNc211j().equals("0")) {
                bi.cic211j.setChecked(true);
            }
            if (!jsonC2.getNc21196().equals("0")) {
                bi.cic21196.setChecked(true);
            }
            if (!jsonC2.getNc21199().equals("0")) {
                bi.cic21199.setChecked(true);
            }

            bi.cic21196x.setText(jsonC2.getcic21196x());

            if (!jsonC2.getcic212().equals("0")) {
                bi.cic212.check(
                        jsonC2.getcic212().equals("1") ? bi.cic212a.getId()
                                : jsonC2.getcic212().equals("2") ? bi.cic212b.getId()
                                : bi.cic21298.getId()
                );
            }
//            here
            if (!jsonC2.getcic212a().equals("0")) {
                bi.cic21201.check(
                        jsonC2.getcic212a().equals("1") ? bi.cic21201a.getId()
                                : jsonC2.getcic212a().equals("2") ? bi.cic21201b.getId()
                                : bi.cic2120198.getId()
                );
            }

            if (!jsonC2.getcic213().equals("0")) {
                bi.cic213.check(
                        jsonC2.getcic213().equals("1") ? bi.cic213a.getId()
                                : jsonC2.getcic213().equals("2") ? bi.cic213b.getId()
                                : bi.cic21398.getId()
                );
            }
            if (!jsonC2.getcic214().equals("0")) {
                bi.cic214.check(
                        jsonC2.getcic214().equals("1") ? bi.cic214a.getId()
                                : jsonC2.getcic214().equals("2") ? bi.cic214b.getId()
                                : bi.cic21498.getId()
                );
            }
            if (!jsonC2.getcic215a().equals("0")) {
                bi.cic215a.check(
                        jsonC2.getcic215a().equals("1") ? bi.cic215aa.getId()
                                : jsonC2.getcic215a().equals("2") ? bi.cic215ab.getId()
                                : bi.cic215a98.getId()
                );
            }
            if (!jsonC2.getcic215b().equals("0")) {
                bi.cic215b.check(
                        jsonC2.getcic215b().equals("1") ? bi.cic215ba.getId()
                                : jsonC2.getcic215b().equals("2") ? bi.cic215bb.getId()
                                : bi.cic215b98.getId()
                );
            }
            bi.cic215bx.setText(jsonC2.getcic215bx());

            if (!jsonC2.getcic215c().equals("0")) {
                bi.cic215c.check(
                        jsonC2.getcic215c().equals("1") ? bi.cic215ca.getId()
                                : jsonC2.getcic215c().equals("2") ? bi.cic215cb.getId()
                                : bi.cic215c98.getId()
                );
            }
            bi.cic215cx.setText(jsonC2.getcic215cx());


            if (!jsonC2.getcic215d().equals("0")) {
                bi.cic215d.check(
                        jsonC2.getcic215d().equals("1") ? bi.cic215da.getId()
                                : jsonC2.getcic215d().equals("2") ? bi.cic215db.getId()
                                : bi.cic215d98.getId()
                );
            }
            if (!jsonC2.getcic215e().equals("0")) {
                bi.cic215e.check(
                        jsonC2.getcic215e().equals("1") ? bi.cic215ea.getId()
                                : jsonC2.getcic215e().equals("2") ? bi.cic215eb.getId()
                                : bi.cic215e98.getId()
                );
            }
            if (!jsonC2.getcic215f().equals("0")) {
                bi.cic215f.check(
                        jsonC2.getcic215f().equals("1") ? bi.cic215fa.getId()
                                : jsonC2.getcic215f().equals("2") ? bi.cic215fb.getId()
                                : bi.cic215f98.getId()
                );
            }
            bi.cic215fx.setText(jsonC2.getcic215fx());

            if (!jsonC2.getcic215g().equals("0")) {
                bi.cic215g.check(
                        jsonC2.getcic215g().equals("1") ? bi.cic215ga.getId()
                                : jsonC2.getcic215g().equals("2") ? bi.cic215gb.getId()
                                : bi.cic215g98.getId()
                );
            }
            if (!jsonC2.getcic215h().equals("0")) {
                bi.cic215h.check(
                        jsonC2.getcic215h().equals("1") ? bi.cic215ha.getId()
                                : jsonC2.getcic215h().equals("2") ? bi.cic215hb.getId()
                                : bi.cic215h98.getId()
                );
            }
            if (!jsonC2.getcic215i().equals("0")) {
                bi.cic215i.check(
                        jsonC2.getcic215i().equals("1") ? bi.cic215ia.getId()
                                : jsonC2.getcic215i().equals("2") ? bi.cic215ib.getId()
                                : bi.cic215i98.getId()
                );
            }


            if (!jsonC2.getcic217a().equals("0")) {
                bi.cic217a.check(
                        jsonC2.getcic217a().equals("1") ? bi.cic217aa.getId()
                                : jsonC2.getcic217a().equals("2") ? bi.cic217ab.getId()
                                : bi.cic217a98.getId()
                );
            }
            if (!jsonC2.getcic217b().equals("0")) {
                bi.cic217b.check(
                        jsonC2.getcic217b().equals("1") ? bi.cic217ba.getId()
                                : jsonC2.getcic217b().equals("2") ? bi.cic217bb.getId()
                                : bi.cic217b98.getId()
                );
            }
            if (!jsonC2.getcic217c().equals("0")) {
                bi.cic217c.check(
                        jsonC2.getcic217c().equals("1") ? bi.cic217ca.getId()
                                : jsonC2.getcic217c().equals("2") ? bi.cic217cb.getId()
                                : bi.cic217c98.getId()
                );
            }
            if (!jsonC2.getcic217d().equals("0")) {
                bi.cic217d.check(
                        jsonC2.getcic217d().equals("1") ? bi.cic217da.getId()
                                : jsonC2.getcic217d().equals("2") ? bi.cic217db.getId()
                                : bi.cic217d98.getId()
                );
            }
            if (!jsonC2.getcic217e().equals("0")) {
                bi.cic217e.check(
                        jsonC2.getcic217e().equals("1") ? bi.cic217ea.getId()
                                : jsonC2.getcic217e().equals("2") ? bi.cic217eb.getId()
                                : bi.cic217e98.getId()
                );
            }
            if (!jsonC2.getcic217f().equals("0")) {
                bi.cic217f.check(
                        jsonC2.getcic217f().equals("1") ? bi.cic217fa.getId()
                                : jsonC2.getcic217f().equals("2") ? bi.cic217fb.getId()
                                : bi.cic217f98.getId()
                );
            }
            if (!jsonC2.getcic217g().equals("0")) {
                bi.cic217g.check(
                        jsonC2.getcic217g().equals("1") ? bi.cic217ga.getId()
                                : jsonC2.getcic217a().equals("2") ? bi.cic217gb.getId()
                                : bi.cic217g98.getId()
                );
            }
            if (!jsonC2.getcic217h().equals("0")) {
                bi.cic217h.check(
                        jsonC2.getcic217h().equals("1") ? bi.cic217ha.getId()
                                : jsonC2.getcic217h().equals("2") ? bi.cic217hb.getId()
                                : bi.cic217h98.getId()
                );
            }
            if (!jsonC2.getcic217i().equals("0")) {
                bi.cic217i.check(
                        jsonC2.getcic217i().equals("1") ? bi.cic217ia.getId()
                                : jsonC2.getcic217i().equals("2") ? bi.cic217ib.getId()
                                : bi.cic217i98.getId()
                );
            }
            if (!jsonC2.getcic217j().equals("0")) {
                bi.cic217j.check(
                        jsonC2.getcic217j().equals("1") ? bi.cic217ja.getId()
                                : jsonC2.getcic217j().equals("2") ? bi.cic217jb.getId()
                                : bi.cic217j98.getId()
                );
            }
            if (!jsonC2.getcic217k().equals("0")) {
                bi.cic217k.check(
                        jsonC2.getcic217k().equals("1") ? bi.cic217ka.getId()
                                : jsonC2.getcic217k().equals("2") ? bi.cic217kb.getId()
                                : bi.cic217k98.getId()
                );
            }
            if (!jsonC2.getcic217l().equals("0")) {
                bi.cic217l.check(
                        jsonC2.getcic217l().equals("1") ? bi.cic217la.getId()
                                : jsonC2.getcic217l().equals("2") ? bi.cic217lb.getId()
                                : bi.cic217l98.getId()
                );
            }
            if (!jsonC2.getcic217m().equals("0")) {
                bi.cic217m.check(
                        jsonC2.getcic217m().equals("1") ? bi.cic217ma.getId()
                                : jsonC2.getcic217m().equals("2") ? bi.cic217mb.getId()
                                : bi.cic217m98.getId()
                );
            }
            if (!jsonC2.getcic217a().equals("0")) {
                bi.cic217a.check(
                        jsonC2.getcic217a().equals("1") ? bi.cic217aa.getId()
                                : jsonC2.getcic217a().equals("2") ? bi.cic217ab.getId()
                                : bi.cic217a98.getId()
                );
            }
            if (!jsonC2.getcic217n().equals("0")) {
                bi.cic217n.check(
                        jsonC2.getcic217n().equals("1") ? bi.cic217na.getId()
                                : jsonC2.getcic217n().equals("2") ? bi.cic217nb.getId()
                                : bi.cic217n98.getId()
                );
            }
            if (!jsonC2.getcic217o().equals("0")) {
                bi.cic217o.check(
                        jsonC2.getcic217o().equals("1") ? bi.cic217oa.getId()
                                : jsonC2.getcic217o().equals("2") ? bi.cic217ob.getId()
                                : bi.cic217o98.getId()
                );
            }
            if (!jsonC2.getcic217p().equals("0")) {
                bi.cic217p.check(
                        jsonC2.getcic217p().equals("1") ? bi.cic217pa.getId()
                                : jsonC2.getcic217p().equals("2") ? bi.cic217pb.getId()
                                : bi.cic217p98.getId()
                );
            }
            if (!jsonC2.getcic217q().equals("0")) {
                bi.cic217q.check(
                        jsonC2.getcic217q().equals("1") ? bi.cic217qa.getId()
                                : jsonC2.getcic217q().equals("2") ? bi.cic217qb.getId()
                                : bi.cic217q98.getId()
                );
            }
            if (!jsonC2.getcic218().equals("0")) {
                bi.cic218.check(
                        jsonC2.getcic218().equals("1") ? bi.cic218a.getId()
                                : jsonC2.getcic218().equals("2") ? bi.cic218b.getId()
                                : bi.cic21898.getId()
                );
            }

            if (jsonC2.getcic219().equals("98")) {
                bi.cic21998.setChecked(true);
            } else {
                bi.cic219x.setText(jsonC2.getcic219());
            }


            if (!jsonC2.getcic219().equals("0")) {
                bi.cic219.check(
                        jsonC2.getcic219().equals("1") ? bi.cic219a.getId()
                                : bi.cic21998.getId()
                );
            }

            if (!jsonC2.getcic220().equals("0")) {
                bi.cic220.check(
                        jsonC2.getcic220().equals("1") ? bi.cic220a.getId()
                                : jsonC2.getcic220().equals("2") ? bi.cic220b.getId()
                                : bi.cic22098.getId()
                );
            }
            if (!jsonC2.getcic221().equals("0")) {
                bi.cic221.check(
                        jsonC2.getcic221().equals("1") ? bi.cic221a.getId()
                                : jsonC2.getcic221().equals("2") ? bi.cic221b.getId()
                                : jsonC2.getcic221().equals("3") ? bi.cic221c.getId()
                                : bi.cic22196.getId()
                );
            }
            bi.cic22196x.setText(jsonC2.getcic22196x());
            if (!jsonC2.getcic222().equals("0")) {
                bi.cic222.check(
                        jsonC2.getcic222().equals("1") ? bi.cic222a.getId()
                                : jsonC2.getcic222().equals("2") ? bi.cic222b.getId()
                                : jsonC2.getcic222().equals("3") ? bi.cic222c.getId()
                                : bi.cic22296.getId()
                );
            }
            bi.cic22296x.setText(jsonC2.getcic22296x());

            if (!jsonC2.getcic223().equals("0")) {
                bi.cic223.check(
                        jsonC2.getcic223().equals("1") ? bi.cic223a.getId()
                                : jsonC2.getcic223().equals("2") ? bi.cic223b.getId()
                                : bi.cic22398.getId()
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


        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

//                finish();
                backPressed = true;

                startActivity(new Intent(this, SectionC5Activity.class)
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


        if (!ValidatorClass.EmptyRadioButton(this, bi.cic206, bi.cic206a, getString(R.string.cic206))) {
            return false;
        }

        if (bi.cic206a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.cic207, bi.cic207a, getString(R.string.cic207))) {
                return false;
            }

            if (bi.cic207b.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.cic207h, getString(R.string.cic207b))) {
                    return false;
                }

            }

            if (bi.cic207c.isChecked()) {
                if (!ValidatorClass.EmptyTextBox(this, bi.cic207d, getString(R.string.cic207c))) {
                    return false;
                }

            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.cic208, bi.cic208a, getString(R.string.cic208))) {
                return false;
            }

            if (bi.cic208b.isChecked()) {

                if (!ValidatorClass.EmptyRadioButton(this, bi.cic209, bi.cic209a, getString(R.string.cic209))) {
                    return false;
                }
                if (!ValidatorClass.EmptyRadioButton(this, bi.cic209, bi.cic20996, bi.cic20996x, getString(R.string.cic209))) {
                    return false;
                }
            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.cic210, bi.cic210a, getString(R.string.cic210))) {
                return false;
            }

            if (bi.cic210a.isChecked()) {
                if (!ValidatorClass.EmptyCheckBox(this, bi.cic211, bi.cic211a, getString(R.string.cic211))) {
                    return false;
                }
                if (!ValidatorClass.EmptyCheckBox(this, bi.cic211, bi.cic21196, bi.cic21196x, getString(R.string.cic211))) {
                    return false;
                }
            }


            if (!ValidatorClass.EmptyRadioButton(this, bi.cic212, bi.cic212a, getString(R.string.cic212))) {
                return false;
            }
        }

        if (!bi.cic212a.isChecked()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.cic21201, bi.cic21201a, getString(R.string.cic212a))) {
                return false;
            }
        }


        if (!ValidatorClass.EmptyRadioButton(this, bi.cic213, bi.cic213a, getString(R.string.cic213))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic214, bi.cic214a, getString(R.string.cic214))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic215a, bi.cic215aa, getString(R.string.cic215a))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic215b, bi.cic215ba, getString(R.string.cic215b))) {
            return false;
        }

        if (bi.cic215ba.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, bi.cic215bx, getString(R.string.cic215b))) {
                return false;
            }


        }
        if (!ValidatorClass.EmptyRadioButton(this, bi.cic215c, bi.cic215ca, getString(R.string.cic215c))) {
            return false;
        }

        if (bi.cic215ca.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, bi.cic215cx, getString(R.string.cic215c))) {
                return false;
            }


        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic215d, bi.cic215da, getString(R.string.cic215d))) {
            return false;
        }


        if (!ValidatorClass.EmptyRadioButton(this, bi.cic215e, bi.cic215ea, getString(R.string.cic215e))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic215f, bi.cic215fa, getString(R.string.cic215f))) {
            return false;
        }

        if (bi.cic215fa.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, bi.cic215fx, getString(R.string.cic215f))) {
                return false;
            }


        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic215g, bi.cic215ga, getString(R.string.cic215g))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic215h, bi.cic215ha, getString(R.string.cic215h))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic215i, bi.cic215ia, getString(R.string.cic215i))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217a, bi.cic217aa, getString(R.string.cic217a))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217b, bi.cic217ba, getString(R.string.cic217b))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217c, bi.cic217ca, getString(R.string.cic217c))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217d, bi.cic217da, getString(R.string.cic217d))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217e, bi.cic217ea, getString(R.string.cic217e))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217f, bi.cic217fa, getString(R.string.cic217f))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217g, bi.cic217ga, getString(R.string.cic217g))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217h, bi.cic217ha, getString(R.string.cic217h))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217i, bi.cic217ia, getString(R.string.cic217i))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217j, bi.cic217ja, getString(R.string.cic217j))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217k, bi.cic217ka, getString(R.string.cic217k))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217l, bi.cic217la, getString(R.string.cic217l))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217m, bi.cic217ma, getString(R.string.cic217m))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217n, bi.cic217na, getString(R.string.cic217n))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217o, bi.cic217oa, getString(R.string.cic217o))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217p, bi.cic217pa, getString(R.string.cic217p))) {
            return false;
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic217q, bi.cic217qa, getString(R.string.cic217q))) {
            return false;
        }

        if (isAllNo() || isAlldkn() || (!isoneYes())) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.cic218, bi.cic218a, getString(R.string.cic218))) {
                return false;
            }
        }

        if ((isAllNo() || isAlldkn()) && bi.cic218a.isChecked()) {
            Toast.makeText(this, "ERROR: " + getString(R.string.cic218) + "Atleast one should be Yes", Toast.LENGTH_SHORT).show();
            bi.cic218a.setError(getString(R.string.cic218));
            Log.i(SectionC2Activity.class.getSimpleName(), "cic218: This data is Required!");
            return false;
        } else {
            bi.cic218a.setError(null);
        }

            /*if (isAlldkn() && bi.cic218a.isChecked()) {
                Toast.makeText(this, "ERROR: " + getString(R.string.cic218) + "Atleast one should be Yes", Toast.LENGTH_SHORT).show();
                bi.cic218a.setError(getString(R.string.cic218));
                Log.i(SectionC2Activity.class.getSimpleName(), "cic218: This data is Required!");
                return false;
            } else {
                bi.cic218a.setError(null);
            }
*/
        if (isoneYes()) {
            if (!ValidatorClass.EmptyRadioButton(this, bi.cic219, bi.cic219a, bi.cic219x, getString(R.string.cic219))) {
                return false;
            }
        }

        if (bi.cic219a.isChecked()) {
            if (!ValidatorClass.EmptyTextBox(this, bi.cic219x, getString(R.string.cic219))) {
                return false;
            }
            if (!ValidatorClass.RangeTextBox(this, bi.cic219x, 1, 10, getString(R.string.cic219), " times")) {
                return false;
            }
        }

        if (!ValidatorClass.EmptyRadioButton(this, bi.cic220, bi.cic220a, getString(R.string.cic220))) {
            return false;
        }


        if (bi.cic220a.isChecked()) {

            if (!ValidatorClass.EmptyRadioButton(this, bi.cic221, bi.cic221a, getString(R.string.cic221))) {
                return false;
            }

            if (!ValidatorClass.EmptyRadioButton(this, bi.cic221, bi.cic22196, bi.cic22196x, getString(R.string.cic221))) {
                return false;
            }

            if (bi.cic221a.isChecked()) {
                if (!ValidatorClass.EmptyRadioButton(this, bi.cic222, bi.cic222a, getString(R.string.cic222))) {
                    return false;
                }

                if (!ValidatorClass.EmptyRadioButton(this, bi.cic222, bi.cic22296, bi.cic22296x, getString(R.string.cic222))) {
                    return false;
                }
            }
        }


        return ValidatorClass.EmptyRadioButton(this, bi.cic223, bi.cic223a, getString(R.string.cic223));
    }

    private void SaveDraft() throws JSONException {


        JSONObject sC2 = new JSONObject();
        if (backPressed) {
            sC2.put("updatedate_cic2", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

        if (SectionC1Activity.editChildFlag) {
            sC2.put("edit_updatedate_sc2", new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis()));
        }

//        cic2_child_name
        //sC2.put("cic2_child_name", selectedChild.getName());
//        cic2_line_no
        //sC2.put("cic2_line_noSerial", selectedChild.getSerialNo());


        sC2.put("cic206", bi.cic206a.isChecked() ? "1"
                : bi.cic206b.isChecked() ? "2"
                : bi.cic20698.isChecked() ? "98"
                : "0");

        sC2.put("cic207", bi.cic207a.isChecked() ? "1"
                : bi.cic207b.isChecked() ? "2"
                : bi.cic207c.isChecked() ? "3"
                : "0");

        sC2.put("cic207h", bi.cic207h.getText().toString());
        sC2.put("cic207d", bi.cic207d.getText().toString());

        sC2.put("cic208", bi.cic208a.isChecked() ? "1"
                : bi.cic208b.isChecked() ? "2"
                : bi.cic20898.isChecked() ? "98"
                : "0");

        sC2.put("cic209", bi.cic209a.isChecked() ? "1"
                : bi.cic209b.isChecked() ? "2"
                : bi.cic209c.isChecked() ? "3"
                : bi.cic20996.isChecked() ? "96"
                : "0");
        sC2.put("cic20996x", bi.cic20996x.getText().toString());

        sC2.put("cic210", bi.cic210a.isChecked() ? "1"
                : bi.cic210b.isChecked() ? "2"
                : "0");
        sC2.put("cic211a", bi.cic211a.isChecked() ? "1" : "0");
        sC2.put("cic211b", bi.cic211b.isChecked() ? "2" : "0");
        sC2.put("cic211c", bi.cic211c.isChecked() ? "3" : "0");
        sC2.put("cic211d", bi.cic211d.isChecked() ? "4" : "0");
        sC2.put("cic211e", bi.cic211e.isChecked() ? "5" : "0");
        sC2.put("cic211f", bi.cic211f.isChecked() ? "6" : "0");
        sC2.put("cic211g", bi.cic211g.isChecked() ? "7" : "0");
        sC2.put("cic211h", bi.cic211h.isChecked() ? "8" : "0");
        sC2.put("cic211i", bi.cic211i.isChecked() ? "9" : "0");
        sC2.put("cic211j", bi.cic211j.isChecked() ? "10" : "0");
        sC2.put("cic21199", bi.cic21199.isChecked() ? "99" : "0");
        sC2.put("cic21196", bi.cic21196.isChecked() ? "96" : "0");

        sC2.put("cic21196x", bi.cic21196x.getText().toString());

        sC2.put("cic212", bi.cic212a.isChecked() ? "1"
                : bi.cic212b.isChecked() ? "2"
                : bi.cic21298.isChecked() ? "98"
                : "0");

        sC2.put("cic212a", bi.cic21201a.isChecked() ? "1"
                : bi.cic21201b.isChecked() ? "2"
                : bi.cic2120198.isChecked() ? "98"
                : "0");

        sC2.put("cic213", bi.cic213a.isChecked() ? "1"
                : bi.cic213b.isChecked() ? "2"
                : bi.cic21398.isChecked() ? "98"
                : "0");

        sC2.put("cic214", bi.cic214a.isChecked() ? "1"
                : bi.cic214b.isChecked() ? "2"
                : bi.cic21498.isChecked() ? "98"
                : "0");

        sC2.put("cic215a", bi.cic215aa.isChecked() ? "1"
                : bi.cic215ab.isChecked() ? "2"
                : bi.cic215a98.isChecked() ? "98"
                : "0");

        sC2.put("cic215b", bi.cic215ba.isChecked() ? "1"
                : bi.cic215bb.isChecked() ? "2"
                : bi.cic215b98.isChecked() ? "98"
                : "0");

        sC2.put("cic215bx", bi.cic215bx.getText().toString());
        sC2.put("cic215c", bi.cic215ca.isChecked() ? "1"
                : bi.cic215cb.isChecked() ? "2"
                : bi.cic215c98.isChecked() ? "98"
                : "0");
        sC2.put("cic215cx", bi.cic215cx.getText().toString());

        sC2.put("cic215d", bi.cic215da.isChecked() ? "1"
                : bi.cic215db.isChecked() ? "2"
                : bi.cic215d98.isChecked() ? "98"
                : "0");

        sC2.put("cic215e", bi.cic215ea.isChecked() ? "1"
                : bi.cic215eb.isChecked() ? "2"
                : bi.cic215e98.isChecked() ? "98"
                : "0");

        sC2.put("cic215f", bi.cic215fa.isChecked() ? "1"
                : bi.cic215fb.isChecked() ? "2"
                : bi.cic215f98.isChecked() ? "98"
                : "0");
        sC2.put("cic215fx", bi.cic215fx.getText().toString());


        sC2.put("cic215g", bi.cic215ga.isChecked() ? "1"
                : bi.cic215gb.isChecked() ? "2"
                : bi.cic215g98.isChecked() ? "98"
                : "0");

        sC2.put("cic215h", bi.cic215ha.isChecked() ? "1"
                : bi.cic215hb.isChecked() ? "2"
                : bi.cic215h98.isChecked() ? "98"
                : "0");

        sC2.put("cic215i", bi.cic215ia.isChecked() ? "1"
                : bi.cic215ib.isChecked() ? "2"
                : bi.cic215i98.isChecked() ? "98"
                : "0");

        sC2.put("cic217a", bi.cic217aa.isChecked() ? "1"
                : bi.cic217ab.isChecked() ? "2"
                : bi.cic217a98.isChecked() ? "98"
                : "0");

        sC2.put("cic217b", bi.cic217ba.isChecked() ? "1"
                : bi.cic217bb.isChecked() ? "2"
                : bi.cic217b98.isChecked() ? "98"
                : "0");

        sC2.put("cic217c", bi.cic217ca.isChecked() ? "1"
                : bi.cic217cb.isChecked() ? "2"
                : bi.cic217c98.isChecked() ? "98"
                : "0");

        sC2.put("cic217d", bi.cic217da.isChecked() ? "1"
                : bi.cic217db.isChecked() ? "2"
                : bi.cic217d98.isChecked() ? "98"
                : "0");

        sC2.put("cic217e", bi.cic217ea.isChecked() ? "1"
                : bi.cic217eb.isChecked() ? "2"
                : bi.cic217e98.isChecked() ? "98"
                : "0");

        sC2.put("cic217f", bi.cic217fa.isChecked() ? "1"
                : bi.cic217fb.isChecked() ? "2"
                : bi.cic217f98.isChecked() ? "98"
                : "0");
        sC2.put("cic217g", bi.cic217ga.isChecked() ? "1"
                : bi.cic217gb.isChecked() ? "2"
                : bi.cic217g98.isChecked() ? "98"
                : "0");

        sC2.put("cic217h", bi.cic217ha.isChecked() ? "1"
                : bi.cic217hb.isChecked() ? "2"
                : bi.cic217h98.isChecked() ? "98"
                : "0");

        sC2.put("cic217i", bi.cic217ia.isChecked() ? "1"
                : bi.cic217ib.isChecked() ? "2"
                : bi.cic217i98.isChecked() ? "98"
                : "0");

        sC2.put("cic217j", bi.cic217ja.isChecked() ? "1"
                : bi.cic217jb.isChecked() ? "2"
                : bi.cic217j98.isChecked() ? "98"
                : "0");

        sC2.put("cic217k", bi.cic217ka.isChecked() ? "1"
                : bi.cic217kb.isChecked() ? "2"
                : bi.cic217k98.isChecked() ? "98"
                : "0");

        sC2.put("cic217l", bi.cic217la.isChecked() ? "1"
                : bi.cic217lb.isChecked() ? "2"
                : bi.cic217l98.isChecked() ? "98"
                : "0");

        sC2.put("cic217m", bi.cic217ma.isChecked() ? "1"
                : bi.cic217mb.isChecked() ? "2"
                : bi.cic217m98.isChecked() ? "98"
                : "0");

        sC2.put("cic217n", bi.cic217na.isChecked() ? "1"
                : bi.cic217nb.isChecked() ? "2"
                : bi.cic217n98.isChecked() ? "98"
                : "0");

        sC2.put("cic217o", bi.cic217oa.isChecked() ? "1"
                : bi.cic217ob.isChecked() ? "2"
                : bi.cic217o98.isChecked() ? "98"
                : "0");

        sC2.put("cic217p", bi.cic217pa.isChecked() ? "1"
                : bi.cic217pb.isChecked() ? "2"
                : bi.cic217p98.isChecked() ? "98"
                : "0");

        sC2.put("cic217q", bi.cic217qa.isChecked() ? "1"
                : bi.cic217qb.isChecked() ? "2"
                : bi.cic217q98.isChecked() ? "98"
                : "0");

        sC2.put("cic218", bi.cic218a.isChecked() ? "1"
                : bi.cic218b.isChecked() ? "2"
                : bi.cic21898.isChecked() ? "98"
                : "0");

        sC2.put("cic219", bi.cic21998.isChecked() ? "98" : bi.cic219x.getText().toString());

        sC2.put("cic220", bi.cic220a.isChecked() ? "1"
                : bi.cic220b.isChecked() ? "2"
                : bi.cic22098.isChecked() ? "98"
                : "0");

        sC2.put("cic221", bi.cic221a.isChecked() ? "1"
                : bi.cic221b.isChecked() ? "2"
                : bi.cic221c.isChecked() ? "3"
                : bi.cic22196.isChecked() ? "96"
                : "0");
        sC2.put("cic22196x", bi.cic22196x.getText().toString());

        sC2.put("cic222", bi.cic222a.isChecked() ? "1"
                : bi.cic222b.isChecked() ? "2"
                : bi.cic222c.isChecked() ? "3"
                : bi.cic22296.isChecked() ? "96"
                : "0");
        sC2.put("cic22296x", bi.cic22296x.getText().toString());

        sC2.put("cic223", bi.cic223a.isChecked() ? "1"
                : bi.cic223b.isChecked() ? "2"
                : bi.cic22398.isChecked() ? "98"
                : "0");


        MainApp.cc.setsC2(String.valueOf(sC2));


    }

    private boolean UpdateDB() {

        //Long rowId;
        int updcount = db.updateSC2();

        if (updcount == 1) {

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void setupViews() {
        //Get Intent
        selectedChild = (FamilyMembersContract) getIntent().getSerializableExtra("selectedChild");


        /*bi.cic205.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.cic205a.isChecked()) {
                    //bi.fldGrpcic206.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpcic206, true);

                } else {
                    ClearClass.ClearAllFields(bi.fldGrpcic206, false);

                }
            }
        });*/

        bi.cic206.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ValidateForm();
                if (bi.cic206a.isChecked()) {
                    //bi.fldGrpcic207.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpcic207, true);

                } else {
                    ClearClass.ClearAllFields(bi.fldGrpcic207, false);
                    /*bi.fldGrpcic207.setVisibility(View.GONE);
                    bi.cic207.clearCheck();
                    bi.cic207d.setText(null);
                    bi.cic207h.setText(null);
                    bi.cic208.clearCheck();
                    bi.cic209.clearCheck();
                    bi.cic20996x.setText(null);
                    bi.cic210.clearCheck();
                    bi.cic211.clearCheck();
                    bi.cic21196x.setText(null);
                    bi.cic212.clearCheck();
*/
                }
            }
        });

        bi.cic208.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ValidateForm();
                if (bi.cic208b.isChecked()) {
                    //bi.fldGrpcic209.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpcic209, true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpcic209, false);
                    /*bi.fldGrpcic209.setVisibility(View.GONE);
                    bi.cic209.clearCheck();
                    bi.cic20996x.setText(null);*/
                }
            }
        });

        bi.cic210.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ValidateForm();
                if (bi.cic210a.isChecked()) {
                    //bi.fldGrpcic211.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpcic211, true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpcic211, false);
                    /*bi.fldGrpcic211.setVisibility(View.GONE);
                    bi.cic211.clearCheck();
                    bi.cic21196x.setText(null);*/
                }
            }
        });

        bi.cic212.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ValidateForm();
                if (bi.cic212a.isChecked()) {
                    //bi.fldGrpcic212a.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(bi.fldGrpcic212a, false);
                    //bi.cic21201.clearCheck();
                } else {
                    //bi.fldGrpcic212a.setVisibility(View.VISIBLE);
                    ClearClass.ClearAllFields(bi.fldGrpcic212a, true);
                }
            }
        });
        /*bi.cic20198.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ClearClass.ClearAllFields(bi.fldGrpyear, false);
                    ClearClass.ClearAllFields(bi.fldGrpmonths, false);
                    ClearClass.ClearAllFields(bi.fldGrpdays, false);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpyear, true);
                    ClearClass.ClearAllFields(bi.fldGrpmonths, true);
                    ClearClass.ClearAllFields(bi.fldGrpdays, true);
                }
            }
        });*/
        bi.cic21199.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.cic211a.setChecked(false);
                    bi.cic211b.setChecked(false);
                    bi.cic211c.setChecked(false);
                    bi.cic211d.setChecked(false);
                    bi.cic211e.setChecked(false);
                    bi.cic211f.setChecked(false);
                    bi.cic211g.setChecked(false);
                    bi.cic211h.setChecked(false);
                    bi.cic211i.setChecked(false);
                    bi.cic211j.setChecked(false);
                    bi.cic21196.setChecked(false);
                    bi.cic21196x.setText(null);
                }
            }
        });
        bi.cic21196.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    bi.cic21196x.setText(null);
                    bi.cic21196x.setEnabled(false);
                } else {
                    bi.cic21196x.setEnabled(true);
                    if (bi.cic21199.isChecked()) {
                        bi.cic21199.setChecked(false);
                    }
                }
            }
        });



        /*bi.cic218.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(bi.cic218a.isChecked())
                {
                    bi.fldGrpcic219.setVisibility(View.VISIBLE);
                }else{
                    bi.fldGrpcic219.setVisibility(View.GONE);
                    bi.cic219.clearCheck();
                    bi.cic219x.setText(null);
                }
            }
        });*/

        for (CheckBox chk : grpcic211) {
            chk.setOnCheckedChangeListener(check2);
        }
        for (RadioGroup rg : grpcic215) {
            rg.setOnCheckedChangeListener(check);
        }

        bi.cic215b.setOnCheckedChangeListener(cic215);
        bi.cic215c.setOnCheckedChangeListener(cic215);
        bi.cic215f.setOnCheckedChangeListener(cic215);

//        Listener

        bi.cic207.setOnCheckedChangeListener(this);
        bi.cic209.setOnCheckedChangeListener(this);
        //bi.cic211.setOnCheckedChangeListener(this);
        bi.cic21201.setOnCheckedChangeListener(this);
        bi.cic213.setOnCheckedChangeListener(this);
        bi.cic214.setOnCheckedChangeListener(this);
        bi.cic218.setOnCheckedChangeListener(this);
        bi.cic219.setOnCheckedChangeListener(this);
        bi.cic220.setOnCheckedChangeListener(this);
        //bi.cic221.setOnCheckedChangeListener(this);
        bi.cic222.setOnCheckedChangeListener(this);
        bi.cic223.setOnCheckedChangeListener(this);

        bi.cic221.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (bi.cic221a.isChecked()) {
                    ClearClass.ClearAllFields(bi.fldGrpcic222, true);
                } else {
                    ClearClass.ClearAllFields(bi.fldGrpcic222, false);
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

//        ValidateForm();
    }

    /*@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ValidateForm();
    }*/

    public boolean isoneYes() {

        int i = 0;
        for (RadioButton rg : cic215yes) {
            if (rg.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    public boolean isAlldkn() {

        int i = 0;
        for (RadioButton rg : cic215dkn) {
            if (rg.isChecked())
                i++;
        }

        // Show answer here
        return i == cic215dkn.size();
    }

    public boolean isAllNo() {

        int i = 0;
        for (RadioButton rg : cic215no) {
            if (rg.isChecked())
                i++;
        }

        // Show answer here
        return i == cic215no.size();
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
