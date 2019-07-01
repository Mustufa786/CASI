package edu.aku.hassannaqvi.casi_2018.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.casi_2018.R;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2018.core.MainApp;
import edu.aku.hassannaqvi.casi_2018.databinding.ActivitySectionB6NewBinding;

//import com.tokenautocomplete.TokenCompleteTextView;
//import edu.aku.hassannaqvi.nns_2018.other.CompletionTextView;

public class SectionB6NewActivity extends AppCompatActivity {
    //implements TokenCompleteTextView.TokenListener<String>

    ActivitySectionB6NewBinding bi;
    DatabaseHelper db;
    @BindViews({R.id.nw601j1, R.id.nw601j2, R.id.nw601j3, R.id.nw601j4, R.id.nw601j5, R.id.nw601j6, R.id.nw601j96})
    List<CheckBox> greenLeafy;
    public CheckBox.OnCheckedChangeListener greenLeafyCheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isGreenLeafy()) {
                bi.txtGrLeaf.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_checked_checkbox, 0);
            } else {
                bi.txtGrLeaf.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_unchecked, 0);
            }
        }
    };
    @BindViews({R.id.nw601m1, R.id.nw601m2, R.id.nw601m3, R.id.nw601j7, R.id.nw601m4, R.id.nw601m5, R.id.nw601m6, R.id.nw601m7, R.id.nw601m96})
    List<CheckBox> green;
    public CheckBox.OnCheckedChangeListener greenCheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isGreen()) {
                bi.txtGreen.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_checked_checkbox, 0);
            } else {
                bi.txtGreen.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_unchecked, 0);
            }
        }
    };
    @BindViews({R.id.nw601k1, R.id.nw601k2, R.id.nw601k3, R.id.nw601k4, R.id.nw601m8, R.id.nw601m9, R.id.nw601k96})
    List<CheckBox> redVegi;
    public CheckBox.OnCheckedChangeListener redVegiCheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isRed()) {
                bi.txtRedVegi.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_checked_checkbox, 0);
            } else {
                bi.txtRedVegi.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_unchecked, 0);
            }
        }
    };
    @BindViews({R.id.nw601m10, R.id.nw601m11, R.id.nw601m12, R.id.nw601b1, R.id.nw601m13, R.id.nw601m14, R.id.nw601m15,
            R.id.nw601b2, R.id.nw601b3, R.id.nw601m16, R.id.nw601b4, R.id.nw601b96})
    List<CheckBox> otherVegi;
    public CheckBox.OnCheckedChangeListener othVegiCheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isOtherVegi()) {
                bi.txtOthVeg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_checked_checkbox, 0);
            } else {
                bi.txtOthVeg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_unchecked, 0);
            }
        }
    };
    @BindViews({R.id.nw601l1, R.id.nw601n1, R.id.nw601n2, R.id.nw601n3, R.id.nw601l2, R.id.nw601n4, R.id.nw601n5,
            R.id.nw601n6, R.id.nw601l96})
    List<CheckBox> singSeed;
    public CheckBox.OnCheckedChangeListener sinSeedCheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isSingleSeed()) {
                bi.txtSinSeed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_checked_checkbox, 0);
            } else {
                bi.txtSinSeed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_unchecked, 0);
            }
        }
    };
    @BindViews({R.id.nw601n7, R.id.nw601n8, R.id.nw601l3, R.id.nw601n9, R.id.nw601n10, R.id.nw601n11, R.id.nw601n12,
            R.id.nw601l4, R.id.nw601n13, R.id.nw601n14, R.id.nw601n15, R.id.nw601n16, R.id.nw601n96})
    List<CheckBox> mulSeed;
    public CheckBox.OnCheckedChangeListener mulSeedCheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isMulSeed()) {
                bi.txtMulSeed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_checked_checkbox, 0);
            } else {
                bi.txtMulSeed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_unchecked, 0);
            }
        }
    };
    @BindViews({R.id.nw601n17, R.id.nw601n18, R.id.nw601n19, R.id.nw601n20, R.id.nw601n21, R.id.nw601n15, R.id.nw601n196})
    List<CheckBox> othFru;
    public CheckBox.OnCheckedChangeListener othFruCheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isOthFru()) {
                bi.txtothFru.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_checked_checkbox, 0);
            } else {
                bi.txtothFru.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_shortcut_unchecked, 0);
            }
        }
    };

    /*@BindViews({R.id.nw60196x, R.id.nw601b96x, R.id.nw601j96x, R.id.nw601k96x, R.id.nw601l96x, R.id.nw601m96x, R.id.nw601n96x, R.id.nw601n196x})
    List<CompletionTextView> groupOthers;
*/
    @BindViews({R.id.nw601a1, R.id.nw601a2, R.id.nw601a3, R.id.nw601a4, R.id.nw601f,
            R.id.nw601i, R.id.vegi, R.id.fruit, R.id.nw601v, R.id.nw601c,
            R.id.nw601e, R.id.nw601q, R.id.nw601d})
    List<CheckBox> listFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_section_b6);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_b6_new);
        ButterKnife.bind(this);
        db = new DatabaseHelper(this);
        bi.setCallback(this);

        /*for (CompletionTextView ct : groupOthers) {
            ct.setTokenLimit(10);
            ct.setTokenListener(this);
        }*/


        setUpViews();
    }

    public void setUpViews() {

        bi.vegi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.fldGrpVegi.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpVegi.setVisibility(View.GONE);
                    for (CheckBox ck : greenLeafy) {
                        ck.setChecked(false);
                    }

                    for (CheckBox ck : green) {
                        ck.setChecked(false);
                    }

                    for (CheckBox ck : redVegi) {
                        ck.setChecked(false);
                    }

                    for (CheckBox ck : otherVegi) {
                        ck.setChecked(false);
                    }

                }
            }
        });

        bi.fruit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bi.fldGrpFruit.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpFruit.setVisibility(View.GONE);
                    for (CheckBox ck : singSeed) {
                        ck.setChecked(false);
                    }
                    for (CheckBox ck : mulSeed) {
                        ck.setChecked(false);
                    }

                    for (CheckBox ck : othFru) {
                        ck.setChecked(false);
                    }

                }
            }
        });


        bi.cardGrLeafy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bi.fldGrpgrLeafy.getVisibility() == View.GONE) {
                    bi.fldGrpgrLeafy.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpgrLeafy.setVisibility(View.GONE);

                }
            }
        });

        bi.cardGrVegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bi.fldGrpgrVegi.getVisibility() == View.GONE) {
                    bi.fldGrpgrVegi.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpgrVegi.setVisibility(View.GONE);
                }
            }
        });

        bi.cardRedVegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bi.fldGrpregVegi.getVisibility() == View.GONE) {
                    bi.fldGrpregVegi.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpregVegi.setVisibility(View.GONE);
                }
            }
        });

        bi.cardOthVegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bi.fldGrpothVegi.getVisibility() == View.GONE) {
                    bi.fldGrpothVegi.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpothVegi.setVisibility(View.GONE);
                }
            }
        });

        bi.cardSingSeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bi.fldGrpsingSeed.getVisibility() == View.GONE) {
                    bi.fldGrpsingSeed.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpsingSeed.setVisibility(View.GONE);
                }
            }
        });

        bi.cardMulSeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bi.fldGrpmulSeed.getVisibility() == View.GONE) {
                    bi.fldGrpmulSeed.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpmulSeed.setVisibility(View.GONE);
                }
            }
        });

        bi.cardOtherFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bi.fldGrpothFruits.getVisibility() == View.GONE) {
                    bi.fldGrpothFruits.setVisibility(View.VISIBLE);
                } else {
                    bi.fldGrpothFruits.setVisibility(View.GONE);
                }
            }
        });

        for (CheckBox ck : greenLeafy) {
            ck.setOnCheckedChangeListener(greenLeafyCheck);
        }

        for (CheckBox ck : green) {
            ck.setOnCheckedChangeListener(greenCheck);
        }

        for (CheckBox ck : redVegi) {
            ck.setOnCheckedChangeListener(redVegiCheck);
        }

        for (CheckBox ck : otherVegi) {
            ck.setOnCheckedChangeListener(othVegiCheck);
        }

        for (CheckBox ck : singSeed) {
            ck.setOnCheckedChangeListener(sinSeedCheck);
        }

        for (CheckBox ck : mulSeed) {
            ck.setOnCheckedChangeListener(mulSeedCheck);
        }

        for (CheckBox ck : othFru) {
            ck.setOnCheckedChangeListener(othFruCheck);
        }


        // Other Foods


    }

    public void BtnContinue() {

        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                Toast.makeText(this, "Starting Ending Section", Toast.LENGTH_SHORT).show();

                finish();


                startActivity(new Intent(this, MainActivity.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void BtnEnd() {

        MainApp.endActivity(this, this);

    }

    private boolean ValidateForm() {

        /*Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (!isFood()) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.nw601), Toast.LENGTH_LONG).show();
            bi.nw601a1.setError("This data is required");
            return false;
        } else {
            bi.nw601a1.setError(null);
        }

        if (bi.nw60196.isChecked()) {
            if (!validatorClass.EmptyTextBox(this, bi.nw601b96x, getString(R.string.nw601) + " - " + getString(R.string.other))) {
                return false;
            }
        }


        if (bi.vegi.isChecked()) {
            if (!isGreen() && !isGreenLeafy() && !isRed() && !isOtherVegi()) {
                Toast.makeText(this, "ERROR(empty): " + getString(R.string.vegi), Toast.LENGTH_LONG).show();
                bi.txtGrLeaf.setError("This data is required");
                bi.txtGreen.setError("This data is required");
                bi.txtRedVegi.setError("This data is required");
                bi.txtOthVeg.setError("This data is required");
                return false;
            } else {
                bi.txtGrLeaf.setError(null);
                bi.txtGreen.setError(null);
                bi.txtRedVegi.setError(null);
                bi.txtOthVeg.setError(null);
            }

            if (bi.fldGrpgrLeafy.getVisibility() == View.VISIBLE) {
                if (!validatorClass.EmptyCheckBox(this, bi.fldGrpgrLeafy, bi.nw601j96, bi.nw601j96x, getString(R.string.grLeafy))) {
                    return false;
                }
            }

            if (bi.fldGrpgrVegi.getVisibility() == View.VISIBLE) {
                if (!validatorClass.EmptyCheckBox(this, bi.fldGrpgrVegi, bi.nw601m96, bi.nw601m96x, getString(R.string.grLeafy))) {
                    return false;
                }
            }

            if (bi.fldGrpregVegi.getVisibility() == View.VISIBLE) {
                if (!validatorClass.EmptyCheckBox(this, bi.fldGrpregVegi, bi.nw601k96, bi.nw601k96x, getString(R.string.grLeafy))) {
                    return false;
                }
            }

            if (bi.fldGrpothVegi.getVisibility() == View.VISIBLE) {
                if (!validatorClass.EmptyCheckBox(this, bi.fldGrpothVegi, bi.nw601b96, bi.nw601b96x, getString(R.string.grLeafy))) {
                    return false;
                }
            }

        }

        if (bi.fruit.isChecked()) {
            if (!isSingleSeed() && !isMulSeed() && !isOthFru()) {
                Toast.makeText(this, "ERROR(empty): " + getString(R.string.fruit), Toast.LENGTH_LONG).show();
                bi.txtSinSeed.setError("This data is required");
                bi.txtMulSeed.setError("This data is required");
                bi.txtothFru.setError("This data is required");
                return false;
            } else {
                bi.txtSinSeed.setError(null);
                bi.txtMulSeed.setError(null);
                bi.txtothFru.setError(null);

            }

            if (bi.fldGrpsingSeed.getVisibility() == View.VISIBLE) {
                if (!validatorClass.EmptyCheckBox(this, bi.fldGrpsingSeed, bi.nw601l96, bi.nw601l96x, getString(R.string.sinSeed))) {
                    return false;
                }
            }

            if (bi.fldGrpmulSeed.getVisibility() == View.VISIBLE) {
                if (!validatorClass.EmptyCheckBox(this, bi.fldGrpmulSeed, bi.nw601n96, bi.nw601n96x, getString(R.string.mulSeed))) {
                    return false;
                }
            }

            if (bi.fldGrpothFruits.getVisibility() == View.VISIBLE) {
                if (!validatorClass.EmptyCheckBox(this, bi.fldGrpothFruits, bi.nw601n196, bi.nw601n196x, getString(R.string.grLeafy))) {
                    return false;
                }
            }

        }
*/

        return true;
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();


        JSONObject sB6 = new JSONObject();

        sB6.put("nw601a1", bi.nw601a1.isChecked() ? "1" : "2");
        sB6.put("nw601a2", bi.nw601a2.isChecked() ? "1" : "2");
        sB6.put("nw601a3", bi.nw601a3.isChecked() ? "1" : "2");
        sB6.put("nw601a4", bi.nw601a4.isChecked() ? "1" : "2");
        sB6.put("nw601f", bi.nw601f.isChecked() ? "1" : "2");
        sB6.put("nw601i", bi.nw601i.isChecked() ? "1" : "2");
        sB6.put("nw601v", bi.nw601v.isChecked() ? "1" : "2");
        sB6.put("nw601c", bi.nw601c.isChecked() ? "1" : "2");
        sB6.put("nw601e", bi.nw601e.isChecked() ? "1" : "2");
        sB6.put("nw601q", bi.nw601q.isChecked() ? "1" : "2");
        sB6.put("nw601d", bi.nw601d.isChecked() ? "1" : "2");
        sB6.put("nw601oth", bi.nw60196.isChecked() ? "1" : "2");
        //sB6.put("nw60196x", bi.nw60196x.getText().toString());

        sB6.put("nw601j1", bi.nw601j1.isChecked() ? "1" : "2");
        sB6.put("nw601j2", bi.nw601j2.isChecked() ? "1" : "2");
        sB6.put("nw601j3", bi.nw601j3.isChecked() ? "1" : "2");
        sB6.put("nw601j4", bi.nw601j4.isChecked() ? "1" : "2");
        sB6.put("nw601j5", bi.nw601j5.isChecked() ? "1" : "2");
        sB6.put("nw601j6", bi.nw601j6.isChecked() ? "1" : "2");
        sB6.put("nw601joth", bi.nw601j96.isChecked() ? "1" : "2");
        //sB6.put("nw601j96x", bi.nw601j96x.getText().toString());

        sB6.put("nw601m1", bi.nw601m1.isChecked() ? "1" : "2");
        sB6.put("nw601m2", bi.nw601m2.isChecked() ? "1" : "2");
        sB6.put("nw601m3", bi.nw601m3.isChecked() ? "1" : "2");
        sB6.put("nw601j7", bi.nw601j7.isChecked() ? "1" : "2");
        sB6.put("nw601m4", bi.nw601m4.isChecked() ? "1" : "2");
        sB6.put("nw601m5", bi.nw601m5.isChecked() ? "1" : "2");
        sB6.put("nw601m6", bi.nw601m6.isChecked() ? "1" : "2");
        sB6.put("nw601m7", bi.nw601m7.isChecked() ? "1" : "2");
        sB6.put("nw601moth", bi.nw601m96.isChecked() ? "1" : "2");
        //sB6.put("nw601m96x", bi.nw601m96x.getText().toString());
        sB6.put("nw601k1", bi.nw601k1.isChecked() ? "1" : "2");
        sB6.put("nw601k2", bi.nw601k2.isChecked() ? "1" : "2");
        sB6.put("nw601k3", bi.nw601k3.isChecked() ? "1" : "2");
        sB6.put("nw601m8", bi.nw601m8.isChecked() ? "1" : "2");
        sB6.put("nw601k4", bi.nw601k4.isChecked() ? "1" : "2");
        sB6.put("nw601m9", bi.nw601m9.isChecked() ? "1" : "2");

        sB6.put("nw601koth", bi.nw601k96.isChecked() ? "1" : "2");
        //sB6.put("nw601k96x", bi.nw601k96x.getText().toString());

        sB6.put("nw601m10", bi.nw601m10.isChecked() ? "1" : "2");
        sB6.put("nw601m11", bi.nw601m11.isChecked() ? "1" : "2");
        sB6.put("nw601m12", bi.nw601m12.isChecked() ? "1" : "2");
        sB6.put("nw601b1", bi.nw601b1.isChecked() ? "1" : "2");
        sB6.put("nw601m13", bi.nw601m13.isChecked() ? "1" : "2");
        sB6.put("nw601m14", bi.nw601m14.isChecked() ? "1" : "2");
        sB6.put("nw601m15", bi.nw601m15.isChecked() ? "1" : "2");
        sB6.put("nw601b2", bi.nw601b2.isChecked() ? "1" : "2");
        sB6.put("nw601b3", bi.nw601b3.isChecked() ? "1" : "2");
        sB6.put("nw601m16", bi.nw601m16.isChecked() ? "1" : "2");
        sB6.put("nw601b4", bi.nw601b4.isChecked() ? "1" : "2");
        sB6.put("nw601b96", bi.nw601b96.isChecked() ? "1" : "2");
        //sB6.put("nw601b96x", bi.nw601b96x.getText().toString());
        sB6.put("nw601l1", bi.nw601l1.isChecked() ? "1" : "2");
        sB6.put("nw601n1", bi.nw601n1.isChecked() ? "1" : "2");
        sB6.put("nw601n2", bi.nw601n2.isChecked() ? "1" : "2");
        sB6.put("nw601n3", bi.nw601n3.isChecked() ? "1" : "2");
        sB6.put("nw601l2", bi.nw601l2.isChecked() ? "1" : "2");
        sB6.put("nw601n4", bi.nw601n4.isChecked() ? "1" : "2");
        sB6.put("nw601n5", bi.nw601n5.isChecked() ? "1" : "2");
        sB6.put("nw601n6", bi.nw601n6.isChecked() ? "1" : "2");
        sB6.put("nw601l96", bi.nw601l96.isChecked() ? "1" : "2");
        //sB6.put("nw601l96x", bi.nw601l96x.getText().toString());

        sB6.put("nw601n7", bi.nw601n7.isChecked() ? "1" : "2");
        sB6.put("nw601n8", bi.nw601n8.isChecked() ? "1" : "2");
        sB6.put("nw601l3", bi.nw601l3.isChecked() ? "1" : "2");
        sB6.put("nw601n9", bi.nw601n9.isChecked() ? "1" : "2");
        sB6.put("nw601n10", bi.nw601n10.isChecked() ? "1" : "2");
        sB6.put("nw601n11", bi.nw601n11.isChecked() ? "1" : "2");
        sB6.put("nw601n12", bi.nw601n12.isChecked() ? "1" : "2");
        sB6.put("nw601l4", bi.nw601l4.isChecked() ? "1" : "2");
        sB6.put("nw601n13", bi.nw601n13.isChecked() ? "1" : "2");
        sB6.put("nw601n14", bi.nw601n14.isChecked() ? "1" : "2");
        sB6.put("nw601n15", bi.nw601n15.isChecked() ? "1" : "2");
        sB6.put("nw601n16", bi.nw601n16.isChecked() ? "1" : "2");
        sB6.put("nw601n96", bi.nw601n96.isChecked() ? "1" : "2");
        //sB6.put("nw601n96x", bi.nw601n96x.getText().toString());
        sB6.put("nw601n17", bi.nw601n17.isChecked() ? "1" : "2");
        sB6.put("nw601n18", bi.nw601n18.isChecked() ? "1" : "2");
        sB6.put("nw601n19", bi.nw601n19.isChecked() ? "1" : "2");
        sB6.put("nw601n20", bi.nw601n20.isChecked() ? "1" : "2");
        sB6.put("nw601n21", bi.nw601n21.isChecked() ? "1" : "2");
        sB6.put("nw601n16", bi.nw601n16.isChecked() ? "1" : "2");
        sB6.put("nw601l5", bi.nw601l5.isChecked() ? "1" : "2");

        sB6.put("nw601n196", bi.nw601n96.isChecked() ? "1" : "2");
        //sB6.put("nw601n196x", bi.nw601n96x.getText().toString());


        //MainApp.mc.setsB6(String.valueOf(sB6));


        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {

        //Long rowId;
        /*DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSB6();

        if (updcount == 1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
*/
        return true;

    }

    public boolean isGreenLeafy() {

        for (CheckBox ck : greenLeafy) {
            if (ck.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    public boolean isGreen() {

        int i = 0;
        for (CheckBox ck : green) {
            if (ck.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    public boolean isRed() {

        int i = 0;
        for (CheckBox ck : redVegi) {
            if (ck.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }


    public boolean isOtherVegi() {

        int i = 0;
        for (CheckBox ck : otherVegi) {
            if (ck.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    public boolean isSingleSeed() {

        int i = 0;
        for (CheckBox ck : singSeed) {
            if (ck.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    public boolean isMulSeed() {

        int i = 0;
        for (CheckBox ck : mulSeed) {
            if (ck.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    public boolean isOthFru() {

        int i = 0;
        for (CheckBox ck : othFru) {
            if (ck.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    public boolean isFood() {

        int i = 0;
        for (CheckBox ck : listFoods) {
            if (ck.isChecked())
                return true;
        }

        // Show answer here
        // return i == rg;
        return false;
    }

    /*@Override
    public void onTokenAdded(String token) {

    }

    @Override
    public void onTokenRemoved(String token) {

    }*/
}


