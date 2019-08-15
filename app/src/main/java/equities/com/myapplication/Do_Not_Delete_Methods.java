package equities.com.myapplication;

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
                    ld = new Database_Local_Aequities(Activity_Markets_Main.this);
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
