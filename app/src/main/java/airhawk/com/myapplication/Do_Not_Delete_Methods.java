package airhawk.com.myapplication;

import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables._AllDays;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;

public class Do_Not_Delete_Methods {

    /*
    //In Main Activity
    public static void get_backup_stock_points() {

        String symbol = ap_info.getMarketSymbol();
        String apikey = "XBA42BUC2B6U6G5C";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&outputsize=full&apikey=" + apikey;

        RequestQueue requestQueue = Volley.newRequestQueue(ApplicationContextProvider.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // your response
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    // get Time
                    JSONObject time = jsonObject.getJSONObject("Time Series (Daily)");
                    Iterator<String> iterator = time.keys();

                    while (iterator.hasNext()) {
                        String date = iterator.next().toString();
                        JSONObject dateJson = time.getJSONObject(date);
                        // get string
                        String close = dateJson.getString("4. close");
                        String volume = dateJson.getString("5. volume");
                        graph_date.add(date);
                        graph_volume.add(volume);
                        graph_high.add(close);

                    }
                    //Collections.reverse(graph_high);
                    //Collections.reverse(graph_volume);
                    //Collections.reverse(graph_date);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //("An Error occured while making the ST request");
            }
        });
        requestQueue.add(jsonObjectRequest);

        /////("GRAPH HIGH LIST "+graph_high);
        ////("GRAPH VOLUUMR LIST "+graph_volume);
        // //("GRAPH DATE LIST "+graph_date);

        List<String> numbers = graph_high;
        Collections.reverse(numbers);
        _AllDays = numbers;

    }

    //Used to update stock data from Firebase
    private void UpdateData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("ALL");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Constructor_App_Variables team = new Constructor_App_Variables();
                    team.setMarketSymbol(String.valueOf(childDataSnapshot.child("0").getValue()));
                    searchview_arraylist.add(String.valueOf(team));
                    ld = new Database_Local_Aequities(Activity_Main.this);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        userRef.addValueEventListener(postListener);
    }



    //For daily up to the minute stock quotes.. Will be used in version 2
     public void get_stock_daily_points(){
        String symbol =ap_info.getMarketSymbol();
        String apikey ="XBA42BUC2B6U6G5C";
        String url ="https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="+symbol+"&interval=5min&apikey="+apikey;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // your response
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    // get Time
                    JSONObject time = jsonObject.getJSONObject("Time Series (5min)");
                    Iterator<String> iterator = time.keys();

                    while (iterator.hasNext()) {
                        String date = iterator.next().toString();
                        JSONObject dateJson = time.getJSONObject(date);
                        // get string
                        String close = dateJson.getString("4. close");
                        String volume =dateJson.getString("5. volume");
                        DecimalFormat numberFormat = new DecimalFormat("#.00");
                        double d =Double.parseDouble(close);
                        String fclose=numberFormat.format(d);

                        graph_date_today.add(date);
                        graph_volume_today.add(volume);
                        graph_high_today.add(fclose);

                    }
                    //Collections.reverse(graph_high);
                    //Collections.reverse(graph_volume);
                    //Collections.reverse(graph_date);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //("An Error occured while making the ST request");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
   */
}
