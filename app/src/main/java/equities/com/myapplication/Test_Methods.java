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

import static equities.com.myapplication.Activity_Markets_Main.ap_info;
import static equities.com.myapplication.Constructor_App_Variables.*;
import static equities.com.myapplication.Service_Main_Equities.*;
import static equities.com.myapplication.Service_Main_Equities.crypto_losers_changelist;

public class Test_Methods {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        updatePrice();
    }
    public static void updatePrice(){
        Constructor_App_Variables app_info =new Constructor_App_Variables();
        if(app_info.getMarketType()=="Crypto"||app_info.getMarketType()=="Cryptocurrency"){
            Document caps = null;
            String name=null;
            if(ap_info.getMarketName().equalsIgnoreCase("XRP")){
                name = "Ripple";
            }else{
                name = app_info.getMarketName();}
            try {
                caps = Jsoup.connect("https://coinmarketcap.com/currencies/" + "bitcoin").timeout(10 * 10000).get();
                Element as = caps.getElementsByClass("details-panel-item--header flex-container").first();
                Elements e = as.select("span:eq(1)");
                Elements p = as.select("span:eq(0)");
                Element av = caps.getElementsByClass("details-panel-item--marketcap-stats flex-container").first();
                Elements v = av.select("span.eq(1)");

                String change = e.get(2).text();
                String price = p.get(1).text();
                change = change.replaceAll("\\(", "").replaceAll("\\)", "");
                current_percentage_change.clear();
                current_updated_price.clear();
                current_percentage_change.add(change);
                current_updated_price.add(price);
                System.out.println("hello "+as);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //getstockupdate
        }
    }


}
