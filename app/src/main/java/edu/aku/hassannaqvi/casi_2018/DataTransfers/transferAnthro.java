package edu.aku.hassannaqvi.casi_2018.DataTransfers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import edu.aku.hassannaqvi.casi_2018.WifiDirect.WiFiDirectActivity;
import edu.aku.hassannaqvi.casi_2018.core.DatabaseHelper;

/**
 * A simple server socket that accepts connection and writes some data on
 * the stream.
 */
public class transferAnthro extends AsyncTask<Void, Void, String> {

    private Context context;
    private TextView statusText;

    /**
     * @param context
     * @param statusText
     */
    public transferAnthro(Context context, View statusText) {
        this.context = context;
        this.statusText = (TextView) statusText;
        this.statusText.setText(null);

    }

    @Override
    protected String doInBackground(Void... params) {
        String line = "No Response";

        try {
            ServerSocket serverSocket = new ServerSocket(8988);
            Log.d(WiFiDirectActivity.TAG, "Server: Socket opened");
            Socket client = serverSocket.accept();
            Log.d(WiFiDirectActivity.TAG, "Server: connection done");
                /*final File f = new File(Environment.getExternalStorageDirectory() + "/"
                        + context.getPackageName() + "/wifip2pshared-" + System.currentTimeMillis()
                        + ".jpg");

                File dirs = new File(f.getParent());
                if (!dirs.exists())
                    dirs.mkdirs();
                f.createNewFile();
*/
            //Log.d(WiFiDirectActivity.TAG, "server: copying files " + f.toString());
            InputStream inputstream = client.getInputStream();
            //copyFile(inputstream, new FileOutputStream(f));
            serverSocket.close();
            //return f.getAbsolutePath();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    inputstream, StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            System.out.println("" + sb.toString());
            return sb.toString();
        } catch (IOException e) {
            Log.e(WiFiDirectActivity.TAG, e.getMessage());
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            String json = result;
            if (json.length() > 0) {
                DatabaseHelper db = new DatabaseHelper(context);
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    db.syncAnthroFromDevice(jsonArray);
                    statusText.setText("Message - " + jsonArray.length() + " records of Data Received..");
                    Toast.makeText(context, jsonArray.length() + " records of Data Received..", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        statusText.setText("Opening a server socket");
    }

}
