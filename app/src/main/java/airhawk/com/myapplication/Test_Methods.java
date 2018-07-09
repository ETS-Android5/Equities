package airhawk.com.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables._AllDays;
import static airhawk.com.myapplication.Constructor_App_Variables.all_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.alt_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_flow;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_name;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_low;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_market_cap;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_change;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_name;
import static airhawk.com.myapplication.Constructor_App_Variables.nd_flow;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_change;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_flow;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_name;
import static airhawk.com.myapplication.Database_Local_Aequities.TAG;
import static airhawk.com.myapplication.Executor_Winners_Losers_Kings.crypto_kings_marketcaplist;
import static airhawk.com.myapplication.Executor_Winners_Losers_Kings.crypto_kings_namelist;
import static airhawk.com.myapplication.Executor_Winners_Losers_Kings.crypto_kings_symbolist;

public class Test_Methods {
    public static Context MYcontext;

    public Test_Methods(Context context)
    {
        MYcontext = context;
    }
    // Do not set text variables through adapter when speed is a factor. Instead set variables in fragment. App crashes because of false NPex/ slow speed when user not using wifi.


    public static void main(String[] args) {

            Document bb=null;


            ArrayList fufu =new ArrayList();
            fufu.add("BITCOIN");
            fufu.add("LITECOIN");
            fufu.add("TRON");
            for (int i = 0; i <= fufu.size(); i++) {
                try {
                    bb = Jsoup.connect("https://coinmarketcap.com/currencies/" +fufu.get(i)).timeout(10 * 10000).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
             Elements dd =bb.select("table");
                System.out.println("HERE IS IT, AINT LIFE GRAND! "+dd.text());
            }



    }}