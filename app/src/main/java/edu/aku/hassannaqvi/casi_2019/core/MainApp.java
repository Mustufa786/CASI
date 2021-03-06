package edu.aku.hassannaqvi.casi_2019.core;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.aku.hassannaqvi.casi_2019.contracts.AnthrosMembersContract;
import edu.aku.hassannaqvi.casi_2019.contracts.BLRandomContract;
import edu.aku.hassannaqvi.casi_2019.contracts.ChildContract;
import edu.aku.hassannaqvi.casi_2019.contracts.D4WRAContract;
import edu.aku.hassannaqvi.casi_2019.contracts.D6AdolesContract;
import edu.aku.hassannaqvi.casi_2019.contracts.DeceasedContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FamilyMembersContract;
import edu.aku.hassannaqvi.casi_2019.contracts.FormsContract;
import edu.aku.hassannaqvi.casi_2019.contracts.MWRAContract;
import edu.aku.hassannaqvi.casi_2019.contracts.MicroContract;
import edu.aku.hassannaqvi.casi_2019.contracts.NutritionContract;
import edu.aku.hassannaqvi.casi_2019.contracts.OutcomeContract;
import edu.aku.hassannaqvi.casi_2019.contracts.RecipientsContract;
import edu.aku.hassannaqvi.casi_2019.contracts.SerialContract;
import edu.aku.hassannaqvi.casi_2019.contracts.SignupContract;
import edu.aku.hassannaqvi.casi_2019.contracts.SpecimenContract;
import edu.aku.hassannaqvi.casi_2019.contracts.SummaryContract;
import edu.aku.hassannaqvi.casi_2019.contracts.UsersContract;
import edu.aku.hassannaqvi.casi_2019.contracts.VersionAppContract;
import edu.aku.hassannaqvi.casi_2019.contracts.WaterSpecimenContract;
import edu.aku.hassannaqvi.casi_2019.other.MembersCount;
import edu.aku.hassannaqvi.casi_2019.ui.anthro.AnthroEndingActivity;
import edu.aku.hassannaqvi.casi_2019.ui.childs.ChildEndingActivity;
import edu.aku.hassannaqvi.casi_2019.ui.household.EndingActivity;
import edu.aku.hassannaqvi.casi_2019.ui.wra.MotherEndingActivity;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class MainApp extends Application {

    public static String appMode = "Field"; // Field or Test


    public static final Integer _PORT = 443; // Port - with colon (:)
    public static final Integer MONTHS_LIMIT = 11;
    public static final Integer DAYS_LIMIT = 29;
    //public static final long MILLISECONDS_IN_5YEAR = (MILLISECONDS_IN_YEAR + MILLISECONDS_IN_YEAR + MILLISECONDS_IN_YEAR + MILLISECONDS_IN_YEAR + MILLISECONDS_IN_YEAR);
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    private static final int TWENTY_MINUTES = 1000 * 60 * 20;
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static final long MILLIS_IN_SECOND = 1000;
    private static final long SECONDS_IN_MINUTE = 60;
    private static final long MINUTES_IN_HOUR = 60;
    private static final long HOURS_IN_DAY = 24;
    public static final long MILLISECONDS_IN_DAY = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY;
    private static final long DAYS_IN_YEAR = 365;
    public static final long MILLISECONDS_IN_YEAR = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_YEAR;
    private static final long DAYS_IN_5_YEAR = 365 * 5;
    public static final long MILLISECONDS_IN_5Years = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_5_YEAR;
    private static final long DAYS_IN_MONTH = 30;
    public static final long MILLISECONDS_IN_MONTH = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_MONTH;
    private static final long DAYS_IN_9MONTH = 274;
    public static final long MILLISECONDS_IN_9MONTH = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_9MONTH;
    private static final long DAYS_IN_2_YEAR = 365 * 2;
    public static final long MILLISECONDS_IN_2Years = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_2_YEAR;
    //    public static String _IP2 = "58.65.211.13"; // Test PHP server
    //    public static String _IP = "43.245.131.159"; // Test PHP server
//    public static String _IP = "f38158"; // Test PHP server
    public static String _IP = "vcoe1.aku.edu"; // Live PHP server

    public static boolean isAttitudeCheck = false;
    public static String _HOST_URL = "https://" + MainApp._IP + "/casi/api/";
    public static String _UPDATE_URL;
    public static String deviceId;

    public static Boolean admin = false;
    public static FormsContract fc;
    public static String IMEI;
    public static MWRAContract mc;
    public static NutritionContract nc;
    public static OutcomeContract oc;
    public static ChildContract cc;
    public static SerialContract sc;
    public static RecipientsContract rc;
    public static SummaryContract sumc;
    public static MembersCount membersCount;
    public static String userName = "0000";
    public static UsersContract usersContract = null;
    public static int versionCode;
    public static String versionName;
    public static Boolean IsHead;
    public static int gender;
    public static int othergender;
    public static Boolean IsResp;
    public static String cluster_no = "";
    public static Boolean flag_head = false;

    public static SignupContract signContract;
    //    Ali
    public static FamilyMembersContract fmc;
    public static SpecimenContract smc;
    public static WaterSpecimenContract wsc;
    public static MicroContract msc;

    public static DeceasedContract dc;
    public static AnthrosMembersContract emc;
    public static List<FamilyMembersContract> members_f_m;
    public static List<FamilyMembersContract> respList;
    public static List<FamilyMembersContract> all_members;
    public static List<FamilyMembersContract> all_members_1;
    public static List<FamilyMembersContract> otherMembers_1;
    public static List<FamilyMembersContract> childUnder2;
    public static List<FamilyMembersContract> childUnder2_1;
    public static List<FamilyMembersContract> childUnder5;
    public static List<FamilyMembersContract> childUnder5_Del;
    public static List<FamilyMembersContract> childUnder5_1;
    public static List<FamilyMembersContract> childNA;
    public static List<FamilyMembersContract> childUnder2Check;
    public static List<FamilyMembersContract> mwra;
    public static List<FamilyMembersContract> mwra_1;
    public static List<FamilyMembersContract> adolescents;
    public static List<FamilyMembersContract> minors;
    public static List<FamilyMembersContract> mwraChild;
    public static List<FamilyMembersContract> adolesUnderAge;
    public static List<FamilyMembersContract> adolescents_1;
    public static List<String> editmothersList, editfathersList;
    public static List<String> editmothersSerials, editfathersSerials;
    public static ArrayList<String> duplicateMembers;

    public static BLRandomContract selectedHead;
    public static int serial_no;
    public static int dwraSerial_no;

    // fro section A2
    public static List<FamilyMembersContract> familyMembersList;

    public static List<Integer> hhClicked;
    public static List<Integer> flagClicked;
    public static Map<Integer, FamilyMembersContract> familyMembersClicked;

    public static D4WRAContract d4WRAc;
    public static D6AdolesContract d6Adolesc;
    public static String dWraType;
    // Gul Sanober
    public static int totalPregnancy = 0;
    public static int count = 1;
    public static boolean flag = false;
    public static int outcome = 0;
    public static String lineNo = "";
    public static int status = 0;
    public static String DeviceURL = "devices.php";

    public static Boolean validateFlag = false;

    protected static LocationManager locationManager;

    public static final String WRAD2B = "d2b";
    public static final String WRAD3B = "d3b";
    public static final String WRAD4A = "d4a";
    public static final String WRAD4B = "d4b";
    public static final String WRAD4C = "d4c";
    public static final String WRAD4D = "d4d";
    public static final String WRAD4E = "d4e";
    public static final String WRAD6 = "d6";
    public static final String[] D4WRATypes = {WRAD2B, WRAD3B, WRAD4A, WRAD4B, WRAD4C, WRAD4D};
    public static final String[] D4WRAURLS = D4WRAContract.D4WRATable.urls;
    public static ArrayList<DRWRAQues> D4WRAQUESTIONS;

    public static final class DRWRAQues {
        String quesNo, response;

        public DRWRAQues(String quesNo, String response) {
            this.quesNo = quesNo;
            this.response = response;
        }

        public String getQuesNo() {
            return quesNo;
        }

        public String getResponse() {
            return response;
        }
    }

    public static int monthsBetweenDates(Date startDate, Date endDate) {

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        int monthsBetween = 0;
        int dateDiff = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH);

        if (dateDiff < 0) {
            int borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH);
            dateDiff = (end.get(Calendar.DAY_OF_MONTH) + borrrow) - start.get(Calendar.DAY_OF_MONTH);
            monthsBetween--;

            if (dateDiff > 0) {
                monthsBetween++;
            }
        }

        monthsBetween += end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        monthsBetween += (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        return monthsBetween;
    }

    public static long ageInMonths(String year, String month) {
        long ageInMonths = (Integer.valueOf(year) * 12) + Integer.valueOf(month);
        return ageInMonths;
    }

    public static SummaryContract AddSummary(FormsContract fc, int type) {

        SummaryContract summaryContract = new SummaryContract();
        summaryContract.setClusterno(fc.getClusterNo());
        summaryContract.setHhno(fc.getHhNo());
        summaryContract.setDevicetagID(fc.getDevicetagID());
        summaryContract.setFormdate(fc.getFormDate());
        summaryContract.setUser(fc.getUser());
        summaryContract.setDeviceid(fc.getDeviceID());
        summaryContract.setAppversion(fc.getAppversion());

        switch (type) {
            case 1:
                summaryContract.setHh("1");
                break;
            case 2:
                summaryContract.setWomen("1");
                break;
            case 3:
                summaryContract.setChild("1");
                break;
            case 4:
                summaryContract.setAnthro("1");
                break;
            case 5:
                summaryContract.setSpecimen("1");
                break;
            case 6:
                summaryContract.setWater("1");
                break;
        }

        return summaryContract;
    }

    public static boolean UpdateSummary(Context context, DatabaseHelper db, int type) {
        Long updcount = db.addSummaryForms(MainApp.sumc, type);
        MainApp.sumc.setROW_ID(String.valueOf(updcount));

        if (updcount != 0) {
            MainApp.sumc.set_uid(
                    (MainApp.sumc.getDeviceid() + MainApp.sumc.getROW_ID()));
            db.updateSummaryID();

            return true;
        } else {
            Toast.makeText(context, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static void errorCheck(final Context context, String error) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void errorCountDialog(final Context context, final Activity activity, String error) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton("Discard",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
//                                MainApp.memFlag--;
                                activity.finish();
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

    public static String getTagName(Context mContext) {
        SharedPreferences sharedPref = mContext.getSharedPreferences("tagName", MODE_PRIVATE);
        return sharedPref.getString("tagName", null);
    }

    public static HashMap<String, String> getTagValues(Context mContext) {
        SharedPreferences sharedPref = mContext.getSharedPreferences("tagName", MODE_PRIVATE);

        HashMap<String, String> map = new HashMap<>();
        map.put("tag", sharedPref.getString("tagName", null));
        map.put("org", sharedPref.getString("countryID", null));

        return map;
    }

    public static void finishActivity(final Context context, final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setMessage("Do you want to Exit??")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                activity.finish();
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

    public static void endActivityMother(final Context context, final Activity activity, final Boolean completeFlag) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setMessage("Do you want to Exit??")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                activity.finish();
                                Intent end_intent = new Intent(context, MotherEndingActivity.class);
                                end_intent.putExtra("complete", completeFlag);
                                context.startActivity(end_intent);
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

    public static void endChildActivity(final Context context, final Activity activity, final Boolean completeFlag) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setMessage("Do you want to Exit??")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                activity.finish();
                                Intent end_intent = new Intent(context, ChildEndingActivity.class);
                                end_intent.putExtra("complete", completeFlag);
                                context.startActivity(end_intent);
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

    public static void endActivityAll(final Context context, final Activity activity, final Class<?> destinationClass, final Boolean completeFlag) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setMessage("Do you want to Exit??")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                activity.finish();
                                Intent end_intent = new Intent(context, destinationClass);
                                end_intent.putExtra("complete", completeFlag);
                                context.startActivity(end_intent);
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

    public static void endActivity(final Context context, final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setMessage("Do you want to Exit??")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                activity.finish();
                                Intent end_intent = new Intent(context, EndingActivity.class);
                                end_intent.putExtra("complete", false);
                                context.startActivity(end_intent);
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


    public static void endAnthroActivity(final Context context, final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder
                .setMessage("Do you want to Exit??")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                activity.finish();
                                Intent end_intent = new Intent(context, AnthroEndingActivity.class);
                                end_intent.putExtra("complete", false);
                                context.startActivity(end_intent);
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

    public static String convertDateFormat(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = sdf.parse(dateStr);
            return new SimpleDateFormat("dd/MM/yyyy").format(d);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return "";
    }

    public static Calendar getCalendarDate(String value) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(value);
            calendar.setTime(date);
            return calendar;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static long ageInYearByDOB(String dateStr) {
        Calendar cal = getCalendarDate(dateStr);
        Date dob = cal.getTime();
        Date today = new Date();
        Long diff = today.getTime() - dob.getTime();
        long ageInYears = (diff / (24 * 60 * 60 * 1000)) / 365;
        return ageInYears;
    }

    public static void updateApp(Context mContext) {
        //Update URL
        HashMap<String, String> tagVal = getTagValues(mContext);
        String country = tagVal.get("org") != null ? tagVal.get("org").equals("null") ? "0" : tagVal.get("org").equals("") ? "0" : tagVal.get("org") : "0";

        _UPDATE_URL = "https://" + _IP + "/casi/app/";

        String URI_VERSION;
        if (country.equals("1"))
            URI_VERSION = VersionAppContract.VersionAppTable.DARI_FOLDER;
        else if (country.equals("2"))
            URI_VERSION = VersionAppContract.VersionAppTable.URDU_FOLDER;
        else if (country.equals("3"))
            URI_VERSION = VersionAppContract.VersionAppTable.TAJIK_FOLDER;
        else
            URI_VERSION = VersionAppContract.VersionAppTable.DEFAULT_FOLDER;

        Log.i("Update URL", _UPDATE_URL + URI_VERSION);

        _UPDATE_URL = _UPDATE_URL + URI_VERSION;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/JameelNooriNastaleeq.ttf");

        // font from assets: font for Farsi
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/farsi.ttf");

        //font for Tajik
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Arimo-Regular.ttf");


        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);


        // Requires Permission for GPS -- android.permission.ACCESS_FINE_LOCATION
        // Requires Additional permission for 5.0 -- android.hardware.location.gps
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // requestPermission();
            } else {
                requestLocationUpdate();
            }
        } else {
            requestLocationUpdate();
        }

//        Initialize Dead Member List
//        deadMembers = new ArrayList<String>();
        updateApp(this);
    }

    private void requestPermission() {

        // The ACCESS_COARSE_LOCATION is denied, then I request it and manage the result in
        // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
      /*  if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }
        // The ACCESS_FINE_LOCATION is denied, then I request it and manage the result in
        // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    MY_PERMISSION_ACCESS_FINE_LOCATION);
        }*/
    }


    protected void showCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            //Toast.makeText(getApplicationContext(), message,
            //Toast.LENGTH_SHORT).show();
        }

    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;

            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else return isNewer && !isSignificantlyLessAccurate && isFromSameProvider;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }


    public void requestLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new GPSLocationListener() // Implement this class from code

        );
    }

    public static class deadMemberClass {

        int position;
        String DSSId;

        public deadMemberClass(int i, String s) {
            position = i;
            DSSId = s;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int i) {
            position = i;
        }

        public void setDSSId(String id) {
            DSSId = id;
        }

    }

    public static class SetNameClass {
        String check;

        public SetNameClass(String check) {
            this.check = check;
        }

        public String getName() {
            return check;
        }
    }

    public class GPSLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {

            SharedPreferences sharedPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            String dt = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(sharedPref.getString("Time", "0"))).toString();

            Location bestLocation = new Location("storedProvider");
            bestLocation.setAccuracy(Float.parseFloat(sharedPref.getString("Accuracy", "0")));
            bestLocation.setTime(Long.parseLong(sharedPref.getString(dt, "0")));
            bestLocation.setLatitude(Float.parseFloat(sharedPref.getString("Latitude", "0")));
            bestLocation.setLongitude(Float.parseFloat(sharedPref.getString("Longitude", "0")));

            if (isBetterLocation(location, bestLocation)) {
                editor.putString("Longitude", String.valueOf(location.getLongitude()));
                editor.putString("Latitude", String.valueOf(location.getLatitude()));
                editor.putString("Accuracy", String.valueOf(location.getAccuracy()));
                editor.putString("Time", String.valueOf(location.getTime()));
                editor.putString("Elevation", String.valueOf(location.getAltitude()));
                String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(String.valueOf(location.getTime()))).toString();
//                Toast.makeText(getApplicationContext(),
//                        "GPS Commit! LAT: " + String.valueOf(location.getLongitude()) +
//                                " LNG: " + String.valueOf(location.getLatitude()) +
//                                " Accuracy: " + String.valueOf(location.getAccuracy()) +
//                                " Time: " + date,
//                        Toast.LENGTH_SHORT).show();

                editor.apply();
            }
        }


        public void onStatusChanged(String s, int i, Bundle b) {
            showCurrentLocation();
        }

        public void onProviderDisabled(String s) {

        }

        public void onProviderEnabled(String s) {

        }
    }

}