package airhawk.com.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;


import com.android.volley.AuthFailureError;
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
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables.*;

public class Test_Methods {



    public static void main(String[] args) {
        Document doc =null;
        try {
            doc = Jsoup.connect("https://info.binance.com/en/currencies").timeout(10 * 1000).get();
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements a =doc.getElementsByClass("name");
        Elements b =a.select("span.abbr");
        for (Element symbol:b){
            String s = symbol.text();
        System.out.println(s);}
    }








   }