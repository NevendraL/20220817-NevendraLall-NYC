package org.invescent.a20220817_nevendralall_nyc.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.invescent.a20220817_nevendralall_nyc.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AboutHighSchoolActivity extends AppCompatActivity {
    private JSONObject highSchoolJsonData;
    private ProgressBar loadingBar;
    private RequestQueue requestQueue;
    private RecyclerView.LayoutManager layoutManager;
    private String tappedOnData = "";
    private TextView satReadingDataTv;
    private TextView satMathDataTv;
    private TextView satWritingDataTv;
    private TextView highSchoolNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_high_school);
        tappedOnData = getIntent().getStringExtra("TAPPEDONSCHOOLDATA");
        requestQueue = Volley.newRequestQueue(AboutHighSchoolActivity.this);
        downloadHighSchoolData("https://data.cityofnewyork.us/resource/f9bf-2cp4.json?school_name=" + tappedOnData.toUpperCase());
        findViewsById();
        highSchoolNameTv.setText(tappedOnData);


    }

    public void findViewsById() {
        satReadingDataTv = findViewById(R.id.readingSatData);
        satWritingDataTv = findViewById(R.id.writingSatDataTv);
        satMathDataTv = findViewById(R.id.mathSatData);
        highSchoolNameTv = findViewById(R.id.highSchoolNameTv);
        loadingBar = findViewById(R.id.loadingBar);
    }


    private void downloadHighSchoolData(String api) {
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                api,
                null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            // Get current json object
                            try {
                                //Populate SAT data into text views...
                                highSchoolJsonData = response.getJSONObject(i);
                                satMathDataTv.setText(highSchoolJsonData.getString("sat_math_avg_score"));
                                satReadingDataTv.setText(highSchoolJsonData.getString("sat_critical_reading_avg_score"));
                                satWritingDataTv.setText(highSchoolJsonData.getString("sat_writing_avg_score"));
                                loadingBar.setVisibility(View.INVISIBLE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(AboutHighSchoolActivity.this, "Sorry, no  data available at this current time...", Toast.LENGTH_SHORT).show();
                                loadingBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                },
                //Had I more time, i would fix the bug that causes some data not to load, my guess is because some school names start with &
                //And this is throwing the code off, because & is also a keyword..
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AboutHighSchoolActivity.this, "Sorry, no  data available at this current time...", Toast.LENGTH_SHORT).show();
                        Log.i("error", error.toString());
                        loadingBar.setVisibility(View.INVISIBLE);


                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }


}