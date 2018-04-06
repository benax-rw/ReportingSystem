package com.YellowPagesRwanda.ReportingSystem;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class HQLevelActivity extends AppCompatActivity {

    private SQLiteHandler db;
    private SessionManager session;
    private TextView welcomeMsg;
    private ArrayList<String> javaNote = new ArrayList<>();
    private ListView javaNoteList;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hq_level);
        javaNoteList = findViewById(R.id.xmlNotes);
        welcomeMsg=findViewById(R.id.welcomeMsg);
        actionBarSetup();
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        welcomeMsg.setText("Welcome "+name);

        new AsyncLogin().execute();

    }


    private void actionBarSetup() {
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Station Level Staff");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu_station_level, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.logout) {

            logoutUser();
        }
        return true;
    }


    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(HQLevelActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(HQLevelActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                url= new URL(AppConfig.URL_GET_NOTE);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return (result.toString());

                } else {

                    return ("Unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);

                    String note=json_data.getString("note");
                    String user_name=json_data.getString("user_name");
                    String user_email=json_data.getString("user_email");
                    String created_at=json_data.getString("created_at");

                    javaNote.add(note+"\n---------------------------------------\n" +
                            "Submitted by: "+user_name+", "+user_email+"\nAt: "+created_at);



                }

                AdaptNotes disadpt = new AdaptNotes(HQLevelActivity.this, javaNote);
                javaNoteList.setAdapter(disadpt);


            } catch (JSONException e) {
                Toast.makeText(HQLevelActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }


}
