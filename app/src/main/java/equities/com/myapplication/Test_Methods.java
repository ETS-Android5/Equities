package equities.com.myapplication;

import android.content.Context;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static equities.com.myapplication.Constructor_App_Variables.*;
import static equities.com.myapplication.Service_Main_Equities.*;
import static equities.com.myapplication.Service_Main_Equities.crypto_losers_changelist;

public class Test_Methods {
    public static void main(String[] args) {
        updatePrice();
    }

    public static void updatePrice(){
        Constructor_App_Variables app_info =new Constructor_App_Variables();
       // if(app_info.getMarketType()=="Crypto"||app_info.getMarketType()=="Cryptocurrency"){
            //get crypto update
            Document caps = null;
            String name = "Ethereum";
            try {
                caps = Jsoup.connect("https://coinmarketcap.com/currencies/" + name).timeout(10 * 10000).get();
                Element as = caps.getElementsByClass("details-panel-item--header flex-container").first();
                Elements e = as.select("span:eq(1)");
                String change = e.get(2).text();
                String price = e.get(1).text();
                System.out.println("This is the price change info "+as);
                change = change.replaceAll("\\(", "").replaceAll("\\)", "");
                current_percentage_change.add(change);
                current_updated_price.add(price);
            } catch (IOException e) {
                e.printStackTrace();
            }
     //   }else{
            //getstockupdate
       // }
    }
}