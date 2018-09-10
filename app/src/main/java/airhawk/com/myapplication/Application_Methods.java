package airhawk.com.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static airhawk.com.myapplication.Constructor_App_Variables._AllDays;
import static airhawk.com.myapplication.Constructor_App_Variables.all_feedItems;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_flow;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_name;
import static airhawk.com.myapplication.Constructor_App_Variables.feedItems;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_low;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_market_cap;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;
import static airhawk.com.myapplication.Constructor_App_Variables.image_video_url;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_change;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_name;
import static airhawk.com.myapplication.Constructor_App_Variables.nd_flow;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_change;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_flow;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_name;
import static airhawk.com.myapplication.Constructor_App_Variables.stocktwits_feedItems;
import static airhawk.com.myapplication.Constructor_App_Variables.video_title;
import static airhawk.com.myapplication.Constructor_App_Variables.video_url;

public class Application_Methods {
    static ArrayList stock_kings_symbollist = new ArrayList();
    static ArrayList stock_kings_namelist = new ArrayList();
    static ArrayList stock_kings_changelist = new ArrayList();
    static ArrayList stock_win_symbollist = new ArrayList();
    static ArrayList stock_win_namelist = new ArrayList();
    static ArrayList stock_win_changelist = new ArrayList();
    static ArrayList stock_losers_symbollist = new ArrayList();
    static ArrayList stock_losers_namelist = new ArrayList();
    static ArrayList stock_losers_changelist = new ArrayList();
    public static ArrayList crypto_volume_namelist = new ArrayList();
    public static ArrayList crypto_volume_volumelist = new ArrayList();
    static ArrayList crypto_winners_changelist = new ArrayList();
    static ArrayList crypto_winners_namelist = new ArrayList();
    static ArrayList crypto_winners_symbollist = new ArrayList();
    static ArrayList crypto_losers_changelist = new ArrayList();
    static ArrayList crypto_losers_namelist = new ArrayList();
    static ArrayList crypto_losers_symbollist = new ArrayList();
    static ArrayList crypto_kings_symbolist = new ArrayList();
    static ArrayList crypto_kings_namelist = new ArrayList();
    static ArrayList crypto_kings_marketcaplist = new ArrayList();
    static ArrayList crypto_kings_changelist =new ArrayList();
    public static ArrayList<String> searchview_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_symbol_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_name_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_type_arraylist = new ArrayList<>();

    static Constructor_App_Variables ap_info = new Constructor_App_Variables();
    private static Context context;
    private Application_Methods(Context context)
    {
        this.context = context;
    }
    private static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

    private static void setJSON_INFO() {
//General Method for reading JSON file in Assets

        try {
            String jsonLocation = AssetJSONFile("rd.json", context);
            JSONObject obj = new JSONObject(jsonLocation);

            JSONArray Arry = obj.getJSONArray("ALL");

            for (int i = 0; i < Arry.length(); i++) {
                JSONArray childJsonArray = Arry.getJSONArray(i);
                String sym = childJsonArray.getString(0);
                aequity_symbol_arraylist.add(sym);
                String nam = childJsonArray.getString(1);
                aequity_name_arraylist.add(nam);
                String typ = childJsonArray.getString(2);
                aequity_type_arraylist.add(typ);

                searchview_arraylist.add(sym + "  " + nam + "  " + typ);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public static void reloadAllData() {
        graph_date.clear();
        graph_high.clear();
        graph_volume.clear();
        feedItems.clear();
        stocktwits_feedItems.clear();
        image_video_url.clear();
        video_url.clear();
        video_title.clear();
    }


    private static void UpdateData() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("ALL");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Database_Local_Aequities ld = new Database_Local_Aequities(context);
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Constructor_App_Variables team = new Constructor_App_Variables();
                    team.setMarketSymbol(String.valueOf(childDataSnapshot.child("0").getValue()));
                    searchview_arraylist.add(String.valueOf(team));
                    ld = new Database_Local_Aequities(context);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        userRef.addValueEventListener(postListener);
    }

    public static void getCrypto_Kings() {

        long startTime = System.nanoTime();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final String url = "https://api.coinmarketcap.com/v2/ticker/?sort=rank";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = response.getJSONObject("data");
                    JSONArray keys = obj.names ();
                    String market_cap = null;
                    for (int i = 0; i < keys.length (); ++i) {
                        String key = keys.getString (i); // Here's your key
                        String value = obj.getString (key);// Here's your value
                        JSONObject jsonObject = new JSONObject(value);
                        String name = jsonObject.getString("name");
                        crypto_kings_namelist.add(name);
                        String symbol = jsonObject.getString("symbol");
                        crypto_kings_symbolist.add(symbol);
                        JSONObject quotes =jsonObject.getJSONObject("quotes");
                        JSONArray ke = quotes.names ();
                        for(int a =0; a < ke.length(); ++a){
                            String keyz = ke.getString (a); // Here's your key
                            String valuez = quotes.getString (keyz);
                            JSONObject jzO = new JSONObject(valuez);
                            market_cap= jzO.getString("market_cap");
                            DecimalFormat df = new DecimalFormat("0.00");
                            df.setMaximumFractionDigits(2);
                            String p =market_cap;
                            double d = Double.parseDouble(p);
                            p =df.format(d);
                            int l =p.length();
                            long t = 1000000000000L;
                            if (l<=12){p= String.valueOf(d/1000000);
                                crypto_kings_marketcaplist.add(p.substring(0,5)+" M");}
                            if (l>12){p= String.valueOf(d/1000000000);
                                crypto_kings_marketcaplist.add(p.substring(0,5)+" B");}
                            if (l>15){p= String.valueOf(d/t);
                                crypto_kings_marketcaplist.add(p.substring(0,5)+" T");}
                            String mc =jzO.getString("percent_change_24h");
                            crypto_kings_changelist.add(mc);
                        }




                    }
                    btc_market_cap_amount =(String) crypto_kings_marketcaplist.get(0);
                    btc_market_cap_change =crypto_kings_changelist.get(0)+"%";
                    System.out.println("This is the information "+btc_market_cap_amount+" "+btc_market_cap_change);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("An Error occured while making the request");
            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    public static void getChosenCryptoInfo(){

        long startTime = System.nanoTime();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String newString =ap_info.getMarketName();
        System.out.println(newString);
        if(newString.contains(" ")){
            newString =newString.replace(" ","-");
        }
        final String url = "https://api.coinmarketcap.com/v1/ticker/"+newString;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    JSONObject heroObject = null;
                    try {
                        heroObject = response.getJSONObject(i);
                        ap_info.setMarketSymbol(heroObject.getString("symbol"));
                        ap_info.setCurrent_Aequity_Price(heroObject.getString("price_usd").substring(0,6));
                        //volume_24 =heroObject.getString("24h_volume_usd");
                        DecimalFormat df = new DecimalFormat("0.00");
                        df.setMaximumFractionDigits(2);
                        String y = heroObject.getString("available_supply");
                        if(y !="null") {
                            double dd = Double.parseDouble(y);
                            y = df.format(dd);
                            int z = y.length();
                            long tt = 1000000000000L;
                            if (z < 10) {
                                y = String.valueOf(dd / 1000);
                                ap_info.setMarketSupply(y.substring(0, 5) + "TH");
                            }
                            if (z <= 12) {
                                y = String.valueOf(dd / 1000000);
                                ap_info.setMarketSupply(y.substring(0, 5) + "M");
                            }
                            if (z > 12) {
                                y = String.valueOf(dd / 1000000000);
                                ap_info.setMarketSupply(y.substring(0, 5) + "B");
                            }
                            if (z > 15) {
                                y = String.valueOf(dd / tt);
                                ap_info.setMarketSupply(y.substring(0, 5) + "T");
                            }

                            ap_info.setCurrent_Aequity_Price_Change(heroObject.getString("percent_change_24h") + "%");
                            String p =heroObject.getString("market_cap_usd");
                            double d = Double.parseDouble(p);
                            p =df.format(d);
                            int l =p.length();
                            long t = 1000000000000L;
                            if (l<10){p= String.valueOf(d/1000);
                                ap_info.setMarketCap(p.substring(0,5)+"TH");}
                            if (l<=12){p= String.valueOf(d/1000000);
                                ap_info.setMarketCap(p.substring(0,5)+"M");}
                            if (l>12){p= String.valueOf(d/1000000000);
                                ap_info.setMarketCap(p.substring(0,5)+"B");}
                            if (l>15){p= String.valueOf(d/t);
                                ap_info.setMarketCap(p.substring(0,5)+"T");}}
                        else{ap_info.setMarketSupply("Unknown");
                            ap_info.setMarketCap("Unknown");
                            ap_info.setCurrent_Aequity_Price_Change("Unknown");}
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                //btc_market_cap_change=

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Something ahppened An Error occured while making the request "+error);
            }
        });
        requestQueue.add(jsonArrayRequest);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("CRYPTO TIME IS " + duration / 1000000000 + " seconds");

    }

    public static void get_crypto_points() {
        long startTime = System.nanoTime();
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();
        String f = ap_info.getMarketName();
        System.out.println("SYMBO NAME "+f);
        if (f.contains(" ")){
            f= f.replaceAll(" ","-");}
        String url = "https://coinmarketcap.com/currencies/" + f + "/historical-data/?start=20000101&end=" + sdf.format(begindate);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String response_utf8 = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8");
                            Document doc = Jsoup.parse(response_utf8);
                            Element price = doc.getElementById("quote_price");
                            String v = price.text();
                            Elements divs = doc.select("table");
                            for (Element tz : divs) {
                                Elements tds = tz.select("td");
                                Elements s = tz.getElementsByClass("text-right");
                                for (Element ss : s) {
                                    Elements p = ss.select("td[data-format-fiat]");
                                    String g = p.text();
                                    String[] splited = g.split("\\s+");
                                    if (v != null && !g.isEmpty()) {
                                        graph_high.add(splited[3]);
                                        graph_low.add(splited[2]);
                                    }
                                    Elements pn = ss.select("td[data-format-market-cap]");
                                    String vp = pn.text();
                                    String[] split = vp.split("\\s+");
                                    if (vp != null && !vp.isEmpty()) {
                                        graph_volume.add(split[0]);
                                        graph_market_cap.add(split[1]);
                                    }
                                }
                                for (Element bb : tds) {
                                    Elements gdate = bb.getElementsByClass("text-left");
                                    String result0 = gdate.text();
                                    if (result0 != null && !result0.isEmpty()) {
                                        graph_date.add(String.valueOf(result0));
                                    }
                                }
                            }_AllDays = graph_high;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("log2=", error.toString());
                        //requestQueue.stop();
                    }
                });
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
        requestQueue.start();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("CRYPTO POINTS TIME IS " + duration / 1000000000 + " seconds");






        //for (int counter = 0; counter < numbers.size(); counter++) {
        //     System.out.println("VOLUME " + graph_volume.get(counter) + " NUMBER " + graph_high.get(counter) + " DATE " + graph_date.get(counter));

        //}


    }

    private static void get_current_stock_info(){
        String symbol =ap_info.getMarketSymbol();
        String apikey ="XBA42BUC2B6U6G5C";
        String url ="https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+symbol+"&apikey="+apikey;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // your response
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    // get Time
                    JSONObject time = jsonObject.getJSONObject("Global Quote");
                    Iterator<String> iterator = time.keys();

                    String price = time.getString("05. price");
                    String change= time.getString("10. change percent");
                    ap_info.setCurrent_Aequity_Price(price);
                    ap_info.setCurrent_Aequity_Price_Change(change);





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("An Error occured while making the ST request");
            }
        });
        requestQueue.add(jsonObjectRequest);




    }

    private static void getStockTwitsData(){
        String market_symbol=ap_info.getMarketSymbol();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final String url = "https://api.stocktwits.com/api/2/streams/symbol/"+market_symbol+".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    StringBuilder formattedResult = new StringBuilder();
                    JSONArray responseJSONArray = response.getJSONArray("messages");
                    for (int i = 0; i < responseJSONArray.length(); i++) {
                        JSONObject user_info = (JSONObject) responseJSONArray.getJSONObject(i).get("user");
                        String message_time = (String) responseJSONArray.getJSONObject(i).get("created_at");
                        String message = (String) responseJSONArray.getJSONObject(i).get("body");
                        //System.out.println(key);
                        System.out.println("THIS IS THE TIME "+message_time);
                        System.out.println("THIS IS THE USERNAME "+user_info.get("username"));
                        System.out.println("THIS IS THE IMAGE URL "+user_info.get("avatar_url"));
                        System.out.println("THIS IS THE MESSAGE "+message);
                        Iterator x = user_info.keys();
                        Constructor_App_Variables.Smessage_time.add(message_time);
                        Constructor_App_Variables.Suser_name.add("" + user_info.get("username"));
                        String url = user_info.get("avatar_url_ssl").toString();
                        Constructor_App_Variables.Simg_url.add(url);
                        Constructor_App_Variables.Smessage.add(message);

                        JSONArray jsonArray =new JSONArray();
                        while(x.hasNext()){
                            String key = (String) x.next();
                            jsonArray.put(user_info.get(key));

                        }




                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("An Error occured while making the request");
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public static void getSaved_stock_points(){
        Database_Local_Aequities co =new Database_Local_Aequities(context);
        Constructor_App_Variables cav =new Constructor_App_Variables();
        ArrayList temp = new ArrayList();
        ArrayList a = co.getSymbol();
        ArrayList aa = co.getType();
        ArrayList b = co.getstockSymbol();
        String apikey ="XBA42BUC2B6U6G5C";

        for(int i=0;i<a.size();i++){
            if(aa.get(i).equals("Stock")) {
                String url ="https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+b.get(i)+"&apikey="+apikey;

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            // get Time
                            JSONObject time = jsonObject.getJSONObject("Global Quote");
                            //Iterator<String> iterator = time.keys();

                            //String price = time.getString("05. price");
                            String change= time.getString("10. change percent");
                            temp.add(change);
                            ap_info.setCurrent_Aequity_Price_Change(change);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("An Error occured while making the ST request");
                    }
                });
                requestQueue.add(jsonObjectRequest);

            }
            cav.setCpr(temp);

        }

        ArrayList d = co.getcryptoName();
        for(int i=0;i<d.size();i++) {
            Document cap = null;
            String g = String.valueOf(d.get(i));
            if (g.contains(" ")) {
                g = g.replace(" ", "-");
            }
            try {
                cap = Jsoup.connect("https://coinmarketcap.com/currencies/" + g).timeout(10 * 10000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements as = cap.select("div>span");
            String f = as.get(4).text();
            f = f.replaceAll("\\(", "").replaceAll("\\)", "");
            System.out.println("It's a crypto " + f);
            temp.add(f);
        }
    }

    public static void get_stock_points() {


        String symbol =ap_info.getMarketSymbol();
        System.out.println("THIS IS THE SYMBOL ITS SEARCHING FOR "+symbol);
        String apikey ="XBA42BUC2B6U6G5C";
        String url ="https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+symbol+"&outputsize=full&apikey="+apikey;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                        String volume =dateJson.getString("5. volume");
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
                System.out.println("An Error occured while making the ST request");
            }
        });
        requestQueue.add(jsonObjectRequest);

        ///System.out.println("GRAPH HIGH LIST "+graph_high);
        //System.out.println("GRAPH VOLUUMR LIST "+graph_volume);
        // System.out.println("GRAPH DATE LIST "+graph_date);

        List<String> numbers = graph_high;
        Collections.reverse(numbers);
        _AllDays = numbers;

    }

    public static void getStocks_Market_Caps() {
        Document z=null;
        try {
            z = Jsoup.connect("https://finance.yahoo.com").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element table = z.getElementById("Lead-2-FinanceHeader-Proxy");
        Elements as = table.select("a[title]");
        sp_name = as.get(0).text().replace("&", "");
        dow_name = as.get(2).text();
        nas_name = as.get(4).text();
        Elements as1 = z.select("h3>span");
        sp_amount =as1.get(0).text();
        dow_amount=as1.get(1).text();
        nas_amount=as1.get(2).text();
        Elements as2 =z.select("span>span");
        sp_change =as2.get(1).text();
        dow_change=as2.get(2).text();
        nas_change=as2.get(3).text();
        if (sp_change.contains("-")){
            sp_flow=true;
        }
        if (dow_change.contains("-")){
            dow_flow=true;
        }
        if (nas_change.contains("-")){
            nd_flow=true;
        }

    }

    public static void getCrypto_Winners_Losers() {
        Document stock_data=null;
        try {


            stock_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements ffsd = stock_data.getElementsByClass("table-responsive");
        Elements tr = stock_data.select("div#losers-24h");
        Elements lose1 = tr.select("td.text-left");
        Elements lose2 = tr.select("td[data-sort]");
        for (Element s : lose1) {
            crypto_losers_symbollist.add(s.text());
        }
        for (int i = 0; i < lose2.size(); i++) {
            if (i % 2 == 0) {
                crypto_losers_namelist.add(lose2.get(i).text());
            } else {
                crypto_losers_changelist.add(lose2.get(i).text());
            }
        }
//CRYPTO WINNERS ARRAYS
        Element l = ffsd.get(2);
        Elements xx = l.select("tbody");
        Elements xxxd = xx.select("td[data-usd]");//Get's percentage change
        Elements ax = xx.select("tr");
        Elements name_change = xx.select("img[src]");
        for (Element crypto_symbol : ax) {
            String symbol = crypto_symbol.select("td.text-left").text();
            crypto_winners_symbollist.add(symbol);
        }
        for (Element crypto_name : name_change) {
            String name = crypto_name.attr("alt");
            crypto_winners_namelist.add(name);
        }
        for (Element crypto_change : xxxd) {
            crypto_winners_changelist.add(crypto_change.text());
        }

    }

    private static void getStock_Winners_Losers() {
        Document sss = null;
        try {
            sss = Jsoup.connect("http://money.cnn.com/data/hotstocks/").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements ddd = sss.getElementsByClass("wsod_dataTable wsod_dataTableBigAlt");
        Element ff = ddd.get(1);
        Elements a = ff.select("a");
        Elements b = ff.select("span[title]");
        Elements aa = ff.select("span.posData");
        for (Element stock_symbol : a) {
            String symbol = stock_symbol.select("a.wsod_symbol").text();
            stock_win_symbollist.add(symbol);
        }
        for (Element stock_name : b) {
            String name = stock_name.text();
            if (name.isEmpty()) {
            } else {
                stock_win_namelist.add(name);
            }
        }
        for (Element stock_change : aa) {
            String change = stock_change.text();
            if (change.isEmpty()) {
            } else {
                if (change.contains("%")) {
                    stock_win_changelist.add(change);
                }

            }
        }
//STOCK LOSERS ARRAYS
        Element fl = ddd.get(2);
        Elements al = fl.select("a");
        Elements bl = fl.select("span[title]");
        Elements aal = fl.select("span.negData");
        for (Element x : al) {
            stock_losers_symbollist.add(x.text());
        }
        for (Element x : bl) {
            stock_losers_namelist.add(x.text());
        }
        for (int i = 0; i < aal.size(); i++) {
            if (i % 2 == 0) {
                // This is point amount
            } else {
                stock_losers_changelist.add(aal.get(i).text());
            }
        }

    }

    private static void getStock_Kings() {
        Document sv = null;
        try {
            sv = Jsoup.connect("http://www.iweblists.com/us/commerce/MarketCapitalization.html").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements table_body = sv.select("tr td:eq(1)");
        Elements table_body2 = sv.select("tr td:eq(2)");
        Elements table_body3 = sv.select("tr td:eq(3)");
        for(int i=1; i<=10;i++)
        {
            String s =table_body2.get(i).text();
            if (s.contains(".")){
                s =s.replace(".","-");
            }
            stock_kings_namelist.add(table_body.get(i).text());
            stock_kings_symbollist.add(s);
            Double add = Double.parseDouble(table_body3.get(i).text());
            String added =null;
            if (add >1000){
                added =table_body3.get(i).text()+" T";
            }else{
                added =table_body3.get(i).text()+" B";
            }
            stock_kings_changelist.add(added);
        }

    }

    private static void ProcessXml(org.w3c.dom.Document data) {
        if (data != null) {
            String st, sd, sp, sl;
            org.w3c.dom.Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(0);
            NodeList items = channel.getChildNodes();

            for (int i = 0; i < items.getLength(); i++) {
                Constructor_News_Feed it = new Constructor_News_Feed();
                Node curentchild = items.item(i);
                if (curentchild.getNodeName().equalsIgnoreCase("item")) {
                    NodeList itemchilds = curentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node curent = itemchilds.item(j);
                        if (curent.getNodeName().equalsIgnoreCase("title")) {
                            st = Html.fromHtml(curent.getTextContent()).toString();
                            it.setTitle(st);
                        } else if (curent.getNodeName().equalsIgnoreCase("description")) {
                            sd = Html.fromHtml(curent.getTextContent()).toString();
                            String d = curent.getTextContent().toString();
                            String pattern1 = "<img src=\"";
                            String pattern2 = "\"";
                            Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
                            Matcher m = p.matcher(d);
                            while (m.find()) {
                                it.setThumbnailUrl(m.group(1));
                            }
                            it.setDescription(sd);

                        } else if (curent.getNodeName().equalsIgnoreCase("pubDate")) {
                            sp = Html.fromHtml(curent.getTextContent()).toString();
                            sp = sp.replaceAll("@20", " ");
                            it.setPubDate(sp);
                        } else if (curent.getNodeName().equalsIgnoreCase("link")) {
                            sl = Html.fromHtml(curent.getTextContent()).toString();
                            sl = sl.replaceAll("@20", " ");
                            it.setLink(sl);
                        } else if (curent.getNodeName().equalsIgnoreCase("img src")) {

                        }
                    }

                    all_feedItems.add(it);


                }
            }
        }
    }

    private static org.w3c.dom.Document GoogleRSFeed() {
        try {
            URL url;
            Context context;
            String repo = "Stock%20Cryptocurrency";
            String address = "https://news.google.com/news/rss/search/section/q/" + repo + "?ned=us&gl=US&hl=en";


            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.w3c.dom.Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
