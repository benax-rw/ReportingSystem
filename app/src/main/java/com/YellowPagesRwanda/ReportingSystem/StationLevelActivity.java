package com.YellowPagesRwanda.ReportingSystem;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StationLevelActivity extends AppCompatActivity {

    private SQLiteHandler db;
    private SessionManager session;
    private EditText noteJava;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_level);

        TextView welcomeMsg = findViewById(R.id.welcomeMsg);
        noteJava=findViewById(R.id.noteXML);
        Button saveButton = findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String note = noteJava.getText().toString().trim();
                db = new SQLiteHandler(getApplicationContext());
                HashMap<String, String> user = db.getUserDetails();

                if (!note.isEmpty()) {
                    addNote(note,user.get("name"), user.get("email"));
                    noteJava.setText("");
                    Toast.makeText(getApplicationContext(), "Saving", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Nothing to save!", Toast.LENGTH_SHORT).show();
                }
            }

        });

        actionBarSetup();

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        welcomeMsg.setText("Welcome "+name);



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
        Intent intent = new Intent(StationLevelActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



    private void addNote(final String note, final String user_name,
                         final String user_email) {
        // Tag used to cancel the request
        String tag_string_req = "req_addnote";



        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_NOTE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(getApplicationContext(), "Note Saved", Toast.LENGTH_LONG).show();


                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("note", note);
                params.put("name", user_name);
                params.put("email", user_email);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



}
