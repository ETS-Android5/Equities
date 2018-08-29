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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables.*;

public class Test_Methods {
    private static Context context;

    public static void setContext(Context cntxt) {
        context = cntxt;
    }

    public static Context getContext() {
        return context;
    }


    public static void main(String[] args) {
        get_stock_points();


    }

    public static void get_stock_points() {

        String marname = "AMZN";
        Document d = null;
        try {
            d = Jsoup.connect("https://finance.yahoo.com/quote/" + marname + "/history?p=" + marname).timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element u = d.getElementById("Lead-2-QuoteHeader-Proxy");
        Elements x = u.select("div>span");
        String c = x.get(2).text();
        String cuap = c.replace(",","");
        ap_info.setCurrent_Aequity_Price(cuap);
        String c3 = x.get(3).text();
        String[] spit = c3.split(" ");
        spit[1]=spit[1].replaceAll("\\(","");
        spit[1]=spit[1].replaceAll("\\)","");
        ap_info.setCurrent_Aequity_Price_Change(spit[1]);

        ArrayList<String> temp = new ArrayList();
        Elements tables = d.select("table");
        Elements trs = tables.select("tr");
        for (Element g : trs) {
            Elements p = g.select("td");
            temp.add(p.text());

        }
        temp.remove(0);
        temp.remove(0);
        for (int counter = 0; counter < temp.size(); counter++) {
            String sev = temp.get(counter);
            String [] split = sev.split(" ");
            graph_date.add(split[0]+" "+split[1]+" "+split[2]);
            if(split[7].equals("adjusted"))
            {
                graph_high.add(ap_info.getCurrent_Aequity_Price());
            }
            else
            {
                String split7 =split[7];
                if (split7.contains(",")){
                    split7 =split7.replace(",","");
                }
                double dudu = Double.parseDouble(split7);
                graph_high.add(dudu);}
            System.out.println("GRAPH HIGH LIST "+graph_high);
            if(split[8].equals("for")||split[8].contains("-"))
            {graph_volume.add("0");}else{
                graph_volume.add(split[8]);}

        }


        List<String> numbers = graph_high;
        Collections.reverse(numbers);
        _AllDays = numbers;

    }

    public void BackUpCryptoMethod() {
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
}



