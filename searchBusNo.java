package com.example.ben.itrans;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by helen_000 on 6/27/2016.
 */
public class searchBusNo extends AppCompatActivity{
    Button homeBtn;
    JsonObjectRequest jsonObjectRequest;
    int count;
    RequestQueue requestQueue;
    boolean dontCall;
    boolean end;
    boolean done;
    MyApplication ma;
    List<String> busList;
    private List<String> allBusStops = new ArrayList<>();
    GoogleMap nMap;
    LatLng nLocation;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_busno);
        end = false;
        done = false;
        Toolbar homeToolBar = (Toolbar) findViewById(R.id.homeToolBar);
        setSupportActionBar(homeToolBar);
        requestQueue = VolleySingleton.getInstance().getRequestQueue();
        ma = MyApplication.getInstance();
        busList = ma.retrieveAll(getApplicationContext());
        for(int i=0;i<busList.size();i++) {
            if(busList.get(i).equals("151")) {
                count = (int) Math.round(i*1.1)*50;
                Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_LONG).show();

            }
        }
        nMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.busNoMap)).getMap();
        nMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            /*if(bus.equals("67")){
                count = busList.indexOf(bus)*100-50;
                Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_LONG).show();
            }*/


        Toolbar busNToolBar = (Toolbar) findViewById(R.id.busNoToolBar);
        setSupportActionBar(busNToolBar);
        Intent intent = getIntent();
        /*if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchBus(query);
        }*/

        ActionBar bnab = getSupportActionBar();
        bnab.setDisplayHomeAsUpEnabled(true);
        busStops();
    }
    /*Toast.makeText(getApplicationContext(), "List created", Toast.LENGTH_SHORT).show();
                                for(allBuses bus:allBuses){
                                    if(bus.getBusNo().equals("171")){
                                        count = allBuses.indexOf(bus)*50-50;
                                        Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_LONG).show();
                                    }
                                }
                                count = 0;
                                if(count<0){
                                    Toast.makeText(getApplicationContext(), "This bus does not exist", Toast.LENGTH_LONG).show();
                                }*/

    public void busStops(){
        dontCall = false;
        count += 50;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://datamall2.mytransport.sg/ltaodataservice/BusRoutes?$skip="+String.valueOf(count), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("value");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject services = jsonArray.getJSONObject(i);
                                String busNo = services.getString("ServiceNo");
                                if(busNo.equals("151")){
                                    String busStop = services.getString("BusStopCode");
                                    allBusStops.add(busStop);
                                    end = true;
                                }else if(end){
                                    dontCall = true;

                                }

                            }
                            if(!dontCall){
                                busStops();
                            }else{
                                Toast.makeText(getApplicationContext(), "Finished at " + String.valueOf(count), Toast.LENGTH_LONG).show();
                            }
                                /*
                                    end = true;
                                }else if(end){
                                    callAgain = true;
                                }*/
                            //JSONObject services = jsonArray.getJSONObject(i);
                            //String busNo = services.getString("ServiceNo");
                            // JSONObject nextBus = services.getJSONObject("NextBus");
                            //String eta = nextBus.getString("EstimatedArrival");
                            // String wheelC = nextBus.getString("Feature");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", "ERROR");
                        Toast.makeText(getApplicationContext(), "That did not work:(", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("AccountKey", "3SnRYzr/X0eKp2HvwTYtmg==");
                headers.put("UniqueUserID", "0bf7760d-15ec-4a1b-9c82-93562fcc9798");
                headers.put("accept", "application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
