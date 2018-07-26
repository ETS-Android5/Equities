package airhawk.com.myapplication;

import android.content.Context;
import android.view.View;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

public class Test_Methods {
    private static Context context;

    public static void setContext(Context cntxt) {
        context = cntxt;
    }

    public static Context getContext() {
        return context;
    }


    public static void main(String[] args) {
        Test_Methods tm =new Test_Methods();


        long startTime = System.nanoTime();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final String url = "https://api.coinmarketcap.com/v2/ticker/?limit=10&sort=rank";
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
                        // crypto_kings_namelist.add(name);
                        String symbol = jsonObject.getString("symbol");
                        // crypto_kings_symbolist.add(symbol);
                        JSONObject quotes =jsonObject.getJSONObject("quotes");
                        JSONArray ke = quotes.names ();
                        for(int a =0; a < ke.length(); ++a){
                            String keyz = ke.getString (a); // Here's your key
                            String valuez = quotes.getString (keyz);
                            JSONObject jzO = new JSONObject(valuez);
                            market_cap= jzO.getString("market_cap");
                            //    crypto_kings_marketcaplist.add(market_cap);
                        }
                        // JSONArray keyz =quotes.names();
                        //String market_cap =jsonObject.getString("")
                        System.out.println("This is the information "+name+" "+symbol+" "+market_cap);
                    }
                    //btc_market_cap_change=

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
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("CRYPTO TIME IS " + duration / 1000000000 + " seconds");

    }





    public void BackUpCryptoMethod(){
        /*
        long startTime = System.nanoTime();

        Document bb = null;
        try {
            bb = Jsoup.connect("https://www.cryptocurrencychart.com/").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements tbody = bb.select("tbody");
        Elements td_name = tbody.select("tr.row>td.name");
        Elements td_price = tbody.select("tr.row>td.price");

        for(int i=0; i<td_name.size();i++) {
            String name_value = td_name.get(i).attr("data-value");
            String price_value = td_price.get(i).attr("data-value");
            System.out.println(name_value+" "+price_value);
        }


        //btc_market_cap_change;
        //crypto_kings_namelist;
        //crypto_kings_marketcaplist;
        //crypto_kings_marketcaplist;
        //btc_market_cap_amount=""+crypto_kings_marketcaplist.get(0);
        //crypto_kings_symbolist;

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("main METHOD TIME IS " + duration / 1000000000 + " seconds");
        */
    }
    public static void go(){
    Document cap =null;
    try{
        cap =Jsoup.connect("https://finance.yahoo.com/quote/AMZN").get();
    } catch (IOException e){
        e.printStackTrace();
    }
    Element table = cap.getElementById("Lead-2-QuoteHeader-Proxy");
    Elements as = table.select("div>span");
    String f =as.get(3).text();
    String[] ff=f.split(" ");
    f= ff[1];
    f= f.replaceAll("\\(","").replaceAll("\\)","");
    System.out.println(f);


}
    public static void no(){

        //Get crypto name not symbol !
        Document cap =null;
        try{
            cap =Jsoup.connect("https://coinmarketcap.com/currencies/bitcoin/").get();
        } catch (IOException e){
            e.printStackTrace();
        }
        //Element table = cap.getElementById("div");
        Elements as = cap.select("div>span");
        String f =as.get(4).text();
        f= f.replaceAll("\\(","").replaceAll("\\)","");
        System.out.println(f);


    }
}


