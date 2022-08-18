package org.invescent.a20220817_nevendralall_nyc.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.invescent.a20220817_nevendralall_nyc.R;
import org.invescent.a20220817_nevendralall_nyc.adapter.HighSchoolDataAdapter;
import org.invescent.a20220817_nevendralall_nyc.recycleViewListener.RecyclerItemClickListener;
import org.invescent.a20220817_nevendralall_nyc.model.HighSchoolModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<HighSchoolModel> highSchoolModelArrayList;
    private HighSchoolDataAdapter highSchoolDataAdapter;
    private JSONObject highSchoolJsonData;
    private HighSchoolModel highSchoolModel;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;
    private RecyclerView highSchoolDataRv;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        initObjects();
        downloadHighSchoolData("https://data.cityofnewyork.us/resource/s3k6-pzi2.json");
        initRecycleView();

    }


    public void findViewsById() {
        highSchoolDataRv = findViewById(R.id.highSchoolDataRv);
        progressBar = findViewById(R.id.progressBar);
    }

    public void initObjects() {
        highSchoolModelArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(MainActivity.this);
    }


    public void initRecycleView() {
        layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        highSchoolDataRv.setHasFixedSize(true);
        highSchoolDataRv.setLayoutManager(layoutManager);
        highSchoolDataAdapter = new HighSchoolDataAdapter(MainActivity.this, highSchoolModelArrayList);
        highSchoolDataRv.setAdapter(highSchoolDataAdapter);

        //Set on  click listener on recycle view
        highSchoolDataRv.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, highSchoolDataRv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String tapedOnHighSchool = highSchoolModelArrayList.get(position).getSchool_name();
                        Intent intent = new Intent(MainActivity.this, AboutHighSchoolActivity.class);
                        intent.putExtra("TAPPEDONSCHOOLDATA", tapedOnHighSchool);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
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
                                highSchoolJsonData = response.getJSONObject(i);
                                Log.i("HIGH SCHOOL DATA", highSchoolJsonData.getString("school_name") + "");
                                highSchoolModel = new HighSchoolModel(highSchoolJsonData.getString("school_name"), highSchoolJsonData.getString("phone_number"), highSchoolJsonData.getString("borough"));
                                highSchoolModelArrayList.add(highSchoolModel);
                                highSchoolDataAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.INVISIBLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Sorry no data available please try again...", Toast.LENGTH_SHORT).show();
                        Log.i("error", error.toString());
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }


}