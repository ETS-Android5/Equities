package equities.com.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;

import static equities.com.myapplication.Constructor_App_Variables.*;
import static equities.com.myapplication.Service_Main_Equities.*;
import static equities.com.myapplication.Service_Main_Equities.crypto_losers_changelist;

public class Test_Methods {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        get_masternodes();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void get_masternodes() {
        Document m = null;
        try {
            m = Jsoup.connect("https://masternodes.online").userAgent("Mozilla").timeout(10 * 100000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element c = m.getElementById("coins");
        Elements tb = c.select("tbody");
        Elements n = tb.get(1).select("tr");
        for (int x = 0; x < n.size(); x++) {
            Constructor_Masternodes constructor_masternodes = new Constructor_Masternodes();
            Elements k = n.get(x).select("td");
            String [] splits = k.get(2).text().split(" ");
            constructor_masternodes.setMasternode_name(splits[0]);
            constructor_masternodes.setMasternode_symbol(splits[1].replace("(","").replace(")",""));
            constructor_masternodes.setMasternode_percent_change(k.get(4).text());
            Float add;
            String mc = k.get(6).text().replace("%", "").replace("$", "").replace(",", "");
            if (mc.contains("?")) {
                constructor_masternodes.setMasternode_marketcap("Unknown");
            } else {
                add = Float.parseFloat(mc);
                if (add > 999) {
                    String a = String.format("%.0f", add);
                    String added = null;

                    if (mc.length() > 9) {
                        added = a.substring(0, 3) + " B";
                    }
                    if (mc.length() <= 9) {
                        added = a.substring(0, 3) + " M";
                    }
                    if (mc.length() == 11) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 2) + "." + added.substring(2, added.length()));
                    }
                    if (mc.length() == 10) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 1) + "." + added.substring(1, added.length()));
                    }
                    ;
                    if (mc.length() == 9) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 3) + "." + added.substring(3, added.length()));
                    }
                    if (mc.length() == 8) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 2) + "." + added.substring(2, added.length()));
                    }
                    if (mc.length() == 7) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 1) + "." + added.substring(1, added.length()));
                    }
                    if (mc.length() == 6) {
                        constructor_masternodes.setMasternode_marketcap(added.substring(0, 3) + "." + added.substring(2, added.length()));
                    }
                }
                constructor_masternodes.setMasternode_node_count(k.get(8).text());
                constructor_masternodes.setMasternode_purchase_value(k.get(10).text());}

            masternode_feedItems.add(constructor_masternodes);


        }
        for(int i=0;i<masternode_feedItems.size();i++){
            Constructor_Masternodes constructor_masternodes1 = masternode_feedItems.get(i);
            System.out.println(constructor_masternodes1.getMasternode_name());}
    }

}
