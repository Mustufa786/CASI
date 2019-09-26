package edu.aku.hassannaqvi.casi_2019.ui.mainUI;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.casi_2019.R;
import edu.aku.hassannaqvi.casi_2019.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2019.contracts.SerialContract;
import edu.aku.hassannaqvi.casi_2019.contracts.VersionAppContract;
import edu.aku.hassannaqvi.casi_2019.core.AndroidDatabaseManager;
import edu.aku.hassannaqvi.casi_2019.core.DatabaseHelper;
import edu.aku.hassannaqvi.casi_2019.core.MainApp;
import edu.aku.hassannaqvi.casi_2019.databinding.ActivityMainBinding;
import edu.aku.hassannaqvi.casi_2019.ui.anthro.AntrhoInfoActivity;
import edu.aku.hassannaqvi.casi_2019.ui.household.SectionA1Activity;
import edu.aku.hassannaqvi.casi_2019.ui.labs.MicroResultsActivity;
import edu.aku.hassannaqvi.casi_2019.ui.labs.SpecimenInfoActivity;
import edu.aku.hassannaqvi.casi_2019.ui.viewMem.ViewMemberActivity;

public class MainActivity extends MenuActivity {

    public static String ftype = "";
    static File file;
    private final String TAG = "MainActivity";
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    @BindView(R.id.adminsec)
    LinearLayout adminsec;
    @BindView(R.id.lblheader)
    TextView lblheader;
    @BindView(R.id.recordSummary)
    TextView recordSummary;
    @BindView(R.id.syncStatus)
    TextView syncStatus;
    @BindView(R.id.syncDevice)
    Button syncDevice;
    @BindView(R.id.spAreas)
    Spinner spAreas;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    AlertDialog.Builder builder;
    String m_Text = "";
    ProgressDialog mProgressDialog;
    ArrayList<String> lablesAreas;
    Map<String, String> AreasMap;
    VersionAppContract versionAppContract;
    ActivityMainBinding mainBinding;
    int id = 1;
    DownloadManager downloadManager;
    Long refID;
    SharedPreferences sharedPrefDownload;
    SharedPreferences.Editor editorDownload;
    String preVer = "", newVer = "";
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(sharedPrefDownload.getLong("refID", 0));

                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Cursor cursor = downloadManager.query(query);
                if (cursor.moveToFirst()) {
                    int colIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(colIndex)) {

                        editorDownload.putBoolean("flag", true);
                        editorDownload.commit();

                        Toast.makeText(context, "New App downloaded!!", Toast.LENGTH_SHORT).show();
                        mainBinding.lblAppVersion.setText("CASI APP New Version " + newVer + "  Downloaded.");

                        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

                        if (taskInfo.get(0).topActivity.getClassName().equals(MainActivity.class.getName())) {
//                                InstallNewApp(newVer, preVer);
                            showDialog(newVer, preVer);
                        }
                    }
                }
            }
        }
    };
    private ProgressDialog pd;
    private Boolean exit = false;
    private String rSumText = "";

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem dbManager = menu.findItem(R.id.menu_openDB);

        if (MainApp.admin) {
            dbManager.setVisible(true);
        } else {
            dbManager.setVisible(false);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        changeLanguage();

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        lblheader.setText("Welcome! You're assigned to block ' " + MainApp.userName);

        /*TagID Start*/
        sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);
        editor = sharedPref.edit();

        /*Download File*/
        sharedPrefDownload = getSharedPreferences("appDownload", MODE_PRIVATE);
        editorDownload = sharedPrefDownload.edit();

        builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog = builder.create();

        ImageView img = new ImageView(getApplicationContext());
        img.setImageResource(R.drawable.tagimg);
        img.setPadding(0, 15, 0, 15);
        builder.setCustomTitle(img);

        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.requestFocus();
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if (!m_Text.equals("")) {
                    editor.putString("tagName", m_Text);
                    editor.commit();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        if (sharedPref.getString("tagName", null) == "" || sharedPref.getString("tagName", null) == null) {
//            builder.show();
        }
        /*TagID End*/


//        Binding setting
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setCallback(this);

        DatabaseHelper db = new DatabaseHelper(this);


        if (MainApp.admin) {
            mainBinding.adminsec.setVisibility(View.VISIBLE);
        } else {
            mainBinding.adminsec.setVisibility(View.GONE);
        }

        Collection<FormsContract> todaysForms = db.getTodayForms();
        Collection<FormsContract> unsyncedForms = db.getUnsyncedForms();

        rSumText += "TODAY'S RECORDS SUMMARY\r\n";

        rSumText += "=======================\r\n";
        rSumText += "\r\n";
        rSumText += "Total Forms Today: " + todaysForms.size() + "\r\n";
        rSumText += "\r\n";
        if (todaysForms.size() > 0) {
            rSumText += "\tFORMS' LIST: \r\n";
            String iStatus;
            rSumText += "--------------------------------------------------\r\n";
            rSumText += "[Cluster No.] \t[ House No. ] \t[Form Status] \t[Sync Status]----------\r\n";
            rSumText += "--------------------------------------------------\r\n";

            for (FormsContract fc : todaysForms) {
                if (fc.getIstatus() != null) {
                    switch (fc.getIstatus()) {
                        case "1":
                            iStatus = "\tComplete";
                            break;
                        case "2":
                            iStatus = "\tIncomplete";
                            break;
                        case "3":
                            iStatus = "\tN/A";
                            break;
                        case "4":
                            iStatus = "\tRefused";
                            break;
                        case "5":
                            iStatus = "\tN/A";
                            break;
                        case "6":
                            iStatus = "\tN/A";
                            break;
                        case "7":
                            iStatus = "\tPartial";
                            break;
                        default:
                            iStatus = "\tOther";
                    }
                } else {
                    iStatus = "\tN/A";
                }

                rSumText += fc.getClusterNo() + " \t";
                rSumText += " " + fc.getHhNo() + " \t";
                rSumText += " " + iStatus + " \t";

                rSumText += (fc.getSynced().equals("") ? "\t\tNot Synced" : "\t\tSynced");
                rSumText += "\r\n";
                rSumText += "--------------------------------------------------\r\n";
            }
        }

        SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
        rSumText += "Last Data Download: \t" + syncPref.getString("LastDownSyncServer", "Never Updated");
        rSumText += "\r\n";
        rSumText += "Last Data Upload: \t" + syncPref.getString("LastUpSyncServer", "Never Synced");
        rSumText += "\r\n";
        rSumText += "\r\n";
        rSumText += "Unsynced Forms: \t" + unsyncedForms.size();
        rSumText += "\r\n";

        Log.d(TAG, "onCreate: " + rSumText);
        mainBinding.recordSummary.setText(rSumText);

        /*Add data in Serial date wrt date*/
        MainApp.sc = db.getSerialWRTDate(new SimpleDateFormat("dd-MM-yy").format(new Date()));

        if (MainApp.sc.getDeviceid() == null) {
            db.addSerialForm(new SerialContract(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID),
                    new SimpleDateFormat("dd-MM-yy").format(new Date()),
                    "0"));

            MainApp.sc = db.getSerialWRTDate(new SimpleDateFormat("dd-MM-yy").format(new Date()));
        }

//        Testing visibility
        if (Integer.valueOf(MainApp.versionName.split("\\.")[0]) > 0) {
            mainBinding.testing.setVisibility(View.GONE);
        } else {
            mainBinding.testing.setVisibility(View.VISIBLE);
        }

//        Version Checking
        versionAppContract = db.getVersionApp();
        if (versionAppContract.getVersioncode() != null) {

            preVer = MainApp.versionName + "." + MainApp.versionCode;
            newVer = versionAppContract.getVersionname() + "." + versionAppContract.getVersioncode();

            if (MainApp.versionCode < Integer.valueOf(versionAppContract.getVersioncode())) {
                mainBinding.lblAppVersion.setVisibility(View.VISIBLE);

                String fileName = DatabaseHelper.DATABASE_NAME.replace(".db", "-New-Apps");
                file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName, versionAppContract.getPathname());

                if (file.exists()) {
                    mainBinding.lblAppVersion.setText("CASI APP New Version " + newVer + "  Downloaded.");
//                    InstallNewApp(newVer, preVer);
                    showDialog(newVer, preVer);
                } else {
                    NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        mainBinding.lblAppVersion.setText("CASI APP New Version " + newVer + " Downloading..");
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(MainApp._UPDATE_URL + versionAppContract.getPathname());
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setDestinationInExternalPublicDir(fileName, versionAppContract.getPathname())
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                .setTitle("Downloading CASI new App ver." + newVer);
                        refID = downloadManager.enqueue(request);

                        editorDownload.putLong("refID", refID);
                        editorDownload.putBoolean("flag", false);
                        editorDownload.commit();

                    } else {
                        mainBinding.lblAppVersion.setText("CASI APP New Version " + newVer + "  Available..\n(Can't download.. Internet connectivity issue!!)");
                    }
                }

            } else {
                mainBinding.lblAppVersion.setVisibility(View.GONE);
                mainBinding.lblAppVersion.setText(null);
            }
        }

        registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

//        Welcome Note
        mainBinding.lblWelcomeUser.setText("Welcome " + MainApp.userName);

    }

    void changeLanguage() {

        String countryCode = MainApp.usersContract != null ? MainApp.usersContract.getCOUNTRY_ID() : "2";
        String lang = null, country = null;

        switch (countryCode) {
            case "1":
                lang = "ps";
                country = "AF";
                break;
            case "2":
                lang = null;
                break;

        }

        if (lang == null) {
            lang = "en";
            country = "US";
        }
        Locale locale = new Locale(lang, country);

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
        this.getResources().getConfiguration().setLayoutDirection(Locale.ENGLISH);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void openForm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (LoginActivity.checkAndRequestPermissions(this, this)) {
                permissiongrantedStuff();
            } else {
                Toast.makeText(this, "Please allow permission from setting", Toast.LENGTH_SHORT).show();
            }
        } else {
            permissiongrantedStuff();
        }

    }

    private void permissiongrantedStuff() {
        if (versionAppContract.getVersioncode() != null) {
            if (MainApp.versionCode < Integer.valueOf(versionAppContract.getVersioncode())) {
                if (sharedPrefDownload.getBoolean("flag", true) && file.exists()) {
                    showDialog(newVer, preVer);
                } else {
                    OpenFormFun();
                }
            } else {
                OpenFormFun();
            }
        } else {
            Toast.makeText(this, "Please sync data to enter new form.", Toast.LENGTH_SHORT).show();
        }
        //OpenFormFun();
    }

    private void OpenFormFun() {

        HashMap<String, String> tagValues = MainApp.getTagValues(this);
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || tagValues.get("org") == null || tagValues.get("org").equals("5")) {

            Intent oF = new Intent(MainActivity.this, SectionA1Activity.class);
            startActivity(oF);

        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);
            alertDialogBuilder
                    .setMessage("GPS is disabled in your device. Enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Enable GPS",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent callGPSSettingIntent = new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(callGPSSettingIntent);
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

    public void openViewMember(Boolean flag) {
        Intent iA = new Intent(this, ViewMemberActivity.class);
        iA.putExtra("flagEdit", false);
        startActivity(iA);
    }

    public void openB(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (LoginActivity.checkAndRequestPermissions(this, this)) {
                MainActivity.ftype = "A";
                Intent iB = new Intent(this, AntrhoInfoActivity.class);
                startActivity(iB);
            } else {
                Toast.makeText(this, "Please allow permissions from setting", Toast.LENGTH_SHORT).show();
            }
        } else {
            MainActivity.ftype = "A";
            Intent iB = new Intent(this, AntrhoInfoActivity.class);
            startActivity(iB);
        }

    }

    public void openSpecimen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (LoginActivity.checkAndRequestPermissions(this, this)) {
                MainActivity.ftype = "B";
                Intent iB = new Intent(this, SpecimenInfoActivity.class);
                startActivity(iB);
            } else {
                Toast.makeText(this, "Please allow permissions from setting", Toast.LENGTH_SHORT).show();
            }
        } else {
            MainActivity.ftype = "B";
            Intent iB = new Intent(this, SpecimenInfoActivity.class);
            startActivity(iB);
        }
        //Intent iB = new Intent(this, SectionB3Activity.class);

    }

    public void openWater() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (LoginActivity.checkAndRequestPermissions(this, this)) {
                MainActivity.ftype = "W";
                //Intent iB = new Intent(this, SectionB3Activity.class);
                Intent iB = new Intent(this, SpecimenInfoActivity.class);
                startActivity(iB);
            } else {
                Toast.makeText(this, "Please allow permissions from setting", Toast.LENGTH_SHORT).show();
            }
        } else {
            MainActivity.ftype = "W";
            //Intent iB = new Intent(this, SectionB3Activity.class);
            Intent iB = new Intent(this, SpecimenInfoActivity.class);
            startActivity(iB);
        }

    }

    public void openMicroResults() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (LoginActivity.checkAndRequestPermissions(this, this)) {
                MainActivity.ftype = "W";
                //Intent iB = new Intent(this, SectionB3Activity.class);
                Intent iB = new Intent(this, MicroResultsActivity.class);
                startActivity(iB);
            } else {
                Toast.makeText(this, "Please allow permissions from setting", Toast.LENGTH_SHORT).show();
            }
        } else {
            MainActivity.ftype = "W";
            //Intent iB = new Intent(this, SectionB3Activity.class);
            Intent iB = new Intent(this, MicroResultsActivity.class);
            startActivity(iB);
        }

    }

    public void openDashboard() {
        Intent iB = new Intent(this, DashboardActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (LoginActivity.checkAndRequestPermissions(this, this)) {
                startActivity(iB);
            } else {
                Toast.makeText(this, "Please allow permissions from setting", Toast.LENGTH_SHORT).show();
            }
        } else {
            startActivity(iB);
        }

    }

    public void testGPS(View v) {

        SharedPreferences sharedPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);
        Log.d("MAP", "testGPS: " + sharedPref.getAll().toString());
        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("Map", entry.getKey() + ": " + entry.getValue().toString());
        }

    }

    public void updateApp(View v) {
        v.setBackgroundColor(Color.GREEN);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);
        alertDialogBuilder
                .setMessage("Are you sure to download new app??")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // this is how you fire the downloader
                                try {
                                    URL url = new URL(MainApp._UPDATE_URL + "app-debug.apk");
                                    HttpURLConnection c = (HttpURLConnection) url.openConnection();
                                    c.setRequestMethod("GET");
                                    c.setDoOutput(true);
                                    c.connect();

                                    String PATH = Environment.getExternalStorageDirectory() + "/download/";
                                    File file = new File(PATH);
                                    file.mkdirs();
                                    File outputFile = new File(file, "app.apk");
                                    FileOutputStream fos = new FileOutputStream(outputFile);

                                    InputStream is = c.getInputStream();

                                    byte[] buffer = new byte[1024];
                                    int len1 = 0;
                                    while ((len1 = is.read(buffer)) != -1) {
                                        fos.write(buffer, 0, len1);
                                    }
                                    fos.close();
                                    is.close();//till here, it works fine - .apk is download to my sdcard in download file

                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + "app.apk")), "application/vnd.android.package-archive");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                } catch (IOException e) {
                                    Toast.makeText(getApplicationContext(), "Update error!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void openDB(View v) {
        Intent dbmanager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }

    public void syncDevice(View view) {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            // Sync Random
            /*new GetBLRandom(this).execute();*/

            SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = syncPref.edit();

            editor.putString("LastDownSyncServer", dtToday);

            editor.apply();
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity

            Intent ii = new Intent(this, LoginActivity.class);
            ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(ii);
            finish();

//            startActivity(new Intent(this, LoginActivity.class));

        } else {
            Toast.makeText(this, "Press Back again to Exit.",
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

    void showDialog(String newVer, String preVer) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = MyDialogFragment.newInstance(newVer, preVer);
        newFragment.show(ft, "dialog");

    }

    public static class MyDialogFragment extends DialogFragment {

        String newVer, preVer;

        static MyDialogFragment newInstance(String newVer, String preVer) {
            MyDialogFragment f = new MyDialogFragment();

            Bundle args = new Bundle();
            args.putString("newVer", newVer);
            args.putString("preVer", preVer);
            f.setArguments(args);

            return f;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            newVer = getArguments().getString("newVer");
            preVer = getArguments().getString("preVer");

            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.exclamation)
                    .setTitle("CASI-2019 APP is available!")
                    .setMessage("CASI App " + newVer + " is now available. Your are currently using older version " + preVer + ".\nInstall new version to use this app.")
                    .setPositiveButton("INSTALL!!",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                    )
                    .create();
        }

    }
}