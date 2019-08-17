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
        getStock_Data();
    }
    public static void getStock_Data() {
        //Getting winners and losers
        sv = null;
        try {
//WINNERS
            sv = Jsoup.connect("https://finance.yahoo.com/gainers").userAgent("Opera").timeout(10 * 10000).get();
            Elements tbody =sv.select("tbody");
            Elements tr =tbody.select("tr");
            for(int i=0;i<20;i++){
                String symbol = tr.get(i).select("td").get(0).text();
                String name = tr.get(i).select("td").get(1).text();
                String price = tr.get(i).select("td").get(2).text().replace("+","");
                String change = tr.get(i).select("td").get(4).text().replace("+","");
                stock_winners_symbollist.add(symbol);
                stock_winners_namelist.add(name);
                stock_winners_pricelist.add(price);
                stock_winners_changelist.add(change);
                typelist.add("Stock");
                //System.out.println("FIRST METHOD WINNERS"+symbol+" "+name+" "+price+" "+change);

            }
            Document sv1=null;
            sv1 = Jsoup.connect("https://finance.yahoo.com/losers").userAgent("Opera").timeout(10 * 10000).get();
            Elements tbodyl = sv1.select("tbody");
            Elements trl = tbodyl.select("tr");
            for (int il = 0; il < 20; il++) {
                Constructor_Stock_Equities stock_equities = new Constructor_Stock_Equities();
                String lsymbol = trl.get(il).select("td").get(0).text();
                String lname = trl.get(il).select("td").get(1).text();
                String lprice = trl.get(il).select("td").get(2).text().replace("+", "");
                String lchange = trl.get(il).select("td").get(4).text().replace("+", "");
                stock_equities.setStock_losers_symbol(lsymbol);
                stock_equities.setStock_losers_name(lname);
                stock_equities.setStock_losers_price(lprice);
                stock_equities.setStock_losers_percent_change(lchange);
                typelist.add("Stock");
            stock_losers_feedItems.add(stock_equities);
            }

            System.out.println(stock_losers_feedItems.size());
            for(int i=0;i<20;i++){
                Constructor_Stock_Equities stock_equities = new Constructor_Stock_Equities();
                System.out.println(stock_equities.getStock_losers_name());}

        } catch (IOException e) {
//ALTERNATIVE METHOD
            getStock_DataBACKUP();
            e.printStackTrace();
        }

        try {
//GET STOCK KINGS
            sv = Jsoup.connect("https://www.tradingview.com/markets/stocks-usa/market-movers-large-cap/").userAgent("Opera").timeout(10 * 10000).get();
            Elements tbody =sv.select("tbody");

            Elements tr =tbody.select("tr");
            for (Element z : tr) {
                if (stock_kings_symbollist.size() < 20) {


                    String td0 = z.select("td").get(0).text();
                    String[] split = td0.split(" ");
                    String symbol = split[0];
                    String name = td0.replace(split[0],"");
                    String td1 = z.select("td").get(1).text();
                    String price = td1;
                    String td2 = z.select("td").get(2).text();
                    String change = td2;
                    stock_kings_symbollist.add(symbol);
                    stock_kings_namelist.add(name);
                    stock_kings_ipdown.add(change);
                    stock_kings_changelist.add(price);
                    //System.out.println("MAIN KINGS METHOD "+name+" "+symbol+" "+price+" "+change);
                }}
        } catch (IOException z) {
            //USE ALTERNATIVE SITE FOR STOCK KINGS
            try {
                sv = Jsoup.connect("http://www.dogsofthedow.com/largest-companies-by-market-cap.htm").userAgent("Opera").timeout(10 * 10000).get();
                Elements tbody =sv.select("tbody");
                Elements tbody2 =tbody.select("tbody");
                Elements tr =tbody2.select("tr");
                for (int i = 15; i <= 24; i++) {
                    if (stock_kings_namelist.size() < 20) {
                        Element td0 = tr.get(i).select("td").get(0);
                        Element td1 = tr.get(i).select("td").get(1);
                        Element td2 = tr.get(i).select("td").get(3);
                        Element td3 = tr.get(i).select("td").get(4);
                        String remove = td2.text().replace(",", "");
                        double add = Double.parseDouble(remove);
                        int value = (int) Math.round(add);
                        String added = String.valueOf(value);
                        if (add > 1000) {
                            added = added + " T";
                        } else {
                            added = added + " B";
                        }
                        try {
                            sv = Jsoup.connect("https://money.cnn.com/quote/quote.html?symb=" + td0.text()).timeout(10 * 10000).get();
                            Elements t = sv.select("tbody");
                            Elements tx = t.select("tr");
                            String td = tx.select("td").get(0).text();
                            String[] split = td.split(" ");
                            stock_kings_ipdown.add(split[0]);
                            //System.out.println("SPECIFIC QUOTE "+split[0]);

                        } catch (IOException d) {
                        }
                        stock_kings_namelist.add(td1.text());
                        stock_kings_symbollist.add(td0.text());
                        stock_kings_changelist.add(added);
                        //stock_kings_ipdown.add(td3.text());

//                    System.out.println(i-14+" "+td0.text()+" "+td1.text()+" "+added);
                    }
                }

            } catch (IOException e) {
                //Neither method works for Stock Kings..send an email to the developer
                e.printStackTrace();
            }    z.printStackTrace();
        }


    }


}
