package equities.com.myapplication;

import android.content.Context;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static equities.com.myapplication.Constructor_App_Variables.*;

//The original version of the core code was found on StackOverflow.com from user flup
//https://stackoverflow.com/users/1973271/flup
//https://stackoverflow.com/questions/21670451/how-to-send-multiple-asynchronous-requests-to-different-web-services


public class Service_Main_Equities {
    public static Context myContext;

    public Service_Main_Equities(Context context) {
        myContext = context;
    }

    public Service_Main_Equities() {
    }
    static Database_Local_Aequities co =new Database_Local_Aequities(ApplicationContextProvider.getContext());

    static Document sss = null;
    static Document sv = null;
    static Document z = null;
    static Document bb;
    static Document stock_data;
    static Document crypto_data;
    static ArrayList stock_kings_symbollist = new ArrayList();
    static ArrayList stock_kings_namelist = new ArrayList();
    static ArrayList stock_kings_ipdown = new ArrayList();
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
    static ArrayList crypto_kings_changelist = new ArrayList();

    static String repo;
    public static void main() {
        ExecutorService service = Executors.newCachedThreadPool();
        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                    getWorldMarkets();}
                return null;
            }
        });
        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                    getCrypto_Kings();}
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                getStocks_Market_Caps();}
                return null;
            }
        });


        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                getCrypto_Winners_Losers();}
                return null;
            }
        });


        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                getStock_Winners_Losers();}
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                getStock_Kings();
           }
                return null;
            }
        });



        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                ProcessXml(GoogleRSFeed());}
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {

                if(saved_helper == 1){}else{
                get_masternodes();}

                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                get_icos();}
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if(saved_helper == 1){}else{
                get_ipos();}
                return null;
            }
        });

        try {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures) {
//                //(future.get());
                //Where to check all variables
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getCrypto_Kings() {
        RequestQueue requestQueue = Volley.newRequestQueue(ApplicationContextProvider.getContext());
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
                        if (name.equalsIgnoreCase("XRP")){
                            crypto_kings_namelist.add("Ripple");}else{
                            crypto_kings_namelist.add(name);
                        }
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
                            if (l<=12){
                                p= String.valueOf(d/1000000);
                                crypto_kings_marketcaplist.add(p.substring(0,3)+" M");}
                            if (l>12){p= String.valueOf(d/1000000000);
                                crypto_kings_marketcaplist.add(p.substring(0,3)+" B");}
                            if (l>15){p= String.valueOf(d/t);
                                crypto_kings_marketcaplist.add(p.substring(0,3)+" T");}
                            String mc =jzO.getString("percent_change_24h");
                            crypto_kings_changelist.add(mc);
                        }




                    }
                    btc_market_cap_amount =(String) crypto_kings_marketcaplist.get(0);
                    btc_market_cap_amount =btc_market_cap_amount.replace(" ","");
                    btc_market_cap_change =crypto_kings_changelist.get(0)+"%";
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //("An Error occured while making the request");
            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    public static void getStock_Winners_Losers() {
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

    private static void ProcessXml(org.w3c.dom.Document data) {
        if (data != null) {
            String st, sd, sp, sl,si;
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
                            st = curent.getTextContent().toString();
                            st= st.substring(0,st.indexOf(" - ")+" - ".length());
                            st= st.replace("-","");
                            it.setTitle(st);
                            //(st);
                        } else if (curent.getNodeName().equalsIgnoreCase("media:content")) {
                            sd = curent.getTextContent().toString();
                            //(sd);

                            String d = curent.getTextContent().toString();
                            String pattern1 = "<img src=\"";
                            String pattern2 = "\"";
                            Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
                            Matcher m = p.matcher(d);
                            while (m.find()) {
                                it.setThumbnailUrl(m.group(1));
                                ////("HERE IS YOUR IMAGE DUDE! "+m.group(1));
                            }
                            it.setDescription(sd);
                        } else if (curent.getNodeName().equalsIgnoreCase("pubDate")) {
                            sp = curent.getTextContent().toString();
                            sp = sp.replaceAll("@20", " ");
                            it.setPubDate(sp);
                            //(sp);
                        } else if (curent.getNodeName().equalsIgnoreCase("link")) {
                            sl = curent.getTextContent().toString();
                            sl = sl.replaceAll("@20", " ");
                            it.setLink(sl);
                            //(sl);
                        } else if (curent.getNodeName().equalsIgnoreCase("source")) {
                            si = curent.getTextContent().toString();
                            it.setSource(si);
                        }
                    }

                    all_feedItems.add(it);


                }
            }
        }
    }

    public static org.w3c.dom.Document GoogleRSFeed() {
        try {
            URL url;
            Context context;
            repo = "Stock%20Cryptocurrency";
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

    public static void getStocks_Market_Caps() {
        try {
            z = Jsoup.connect("https://finance.yahoo.com").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element table = z.getElementById("Lead-2-FinanceHeader-Proxy");
        Elements as = table.select("a[title]");
        sp_name = as.get(0).text().replace("&", "");
        dow_name = as.get(2).text();
        bov_name = as.get(4).text();
        Elements as1 = z.select("h3>span");
        sp_amount = as1.get(0).text();
        dow_amount = as1.get(1).text();
        bov_amount = as1.get(2).text();
        Elements as2 = z.select("span>span");
        sp_change = as2.get(2).text();
        dow_change = as2.get(3).text();
        bov_change = as2.get(4).text();
        if (sp_change.contains("-")) {
            sp_flow = true;
        }
        if (dow_change.contains("-")) {
            dow_flow = true;
        }
        if (bov_change.contains("-")) {
            nd_flow = true;
        }

    }

    //7
    public static void getCrypto_Winners_Losers() {
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


    public void getSaved_Crypto_Price() {

        Database_Local_Aequities db = new Database_Local_Aequities(myContext);
        db.getName();
        for (int i = 0; i <= db.getName().size(); i++) {
            try {
                bb = Jsoup.connect("https://coinmarketcap.com/currencies/" + db.getName().get(i)).timeout(10 * 10000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void getStock_Kings() {
        Document sv = null;
        try {
            sv = Jsoup.connect("http://www.dogsofthedow.com/largest-companies-by-market-cap.htm").userAgent("Opera").timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements tbody =sv.select("tbody");
        Elements tbody2 =tbody.select("tbody");
        Elements tr =tbody2.select("tr");
        for (int i = 15; i <= 24; i++) {
            Element td0 = tr.get(i).select("td").get(0);
            Element td1 = tr.get(i).select("td").get(1);
            Element td2 = tr.get(i).select("td").get(3);
            Element td3 = tr.get(i).select("td").get(4);
            String remove =td2.text().replace(",","");
            double add = Double.parseDouble(remove);
            int value = (int)Math.round(add);
            String added = String.valueOf(value);
            if (add > 1000) {
                added = added + " T";
            } else
            {
                added = added + " B";
            }
            stock_kings_namelist.add(td1.text());
            stock_kings_symbollist.add(td0.text());
            stock_kings_changelist.add(added);
            stock_kings_ipdown.add(td3.text());

            //System.out.println(i-14+" "+td0.text()+" "+td1.text()+" "+added);
        }

    }



    public static void get_masternodes(){
        Document m =null;
        try {
            m = Jsoup.connect("https://masternodes.online").userAgent("Mozilla").timeout(10 * 100000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element c = m.getElementById("coins");
        Elements tb = c.select("tbody");
        Elements n = tb.get(1).select("tr");
        for(int x =0; x<20;x++){
            Elements k = n.get(x).select("td");
            String z = k.text().replace("%","").replace("$","").replace(",","");
            String[] split = z.split(" ");
            //
            // split[6] = split[6].substring(0, 1) + "." + split[6].substring(4, split[6].length());
            //System.out.println("GET ! "+ split.length);
            if(split.length<12){
                masternode_name.add(split[0]);
                masternode_symbol.add(split[1].replace("(","").replace(")",""));
                masternode_percent_change.add(split[3]);
                Float add = Float.parseFloat(split[6]);
                String a = String.format("%.0f", add);
                //replaceFirst(",", ".").replace(",","")
                String added = null;
                if (split[6].length() >9 ) {
                    added = a.substring(0,3) + " B";
                }
                if (split[6].length() <= 9) {
                    added = a.substring(0, 3) + " M";
                }
                if(split[6].length()==11){masternode_marketcap.add(added.substring(0, 2) + "." + added.substring(2, added.length()));}
                if(split[6].length()==10){masternode_marketcap.add(added.substring(0, 1) + "." + added.substring(1, added.length()));};
                if(split[6].length()==9){masternode_marketcap.add(added.substring(0, 3) + "." + added.substring(3, added.length()));}
                if(split[6].length()==8){masternode_marketcap.add(added.substring(0, 2) + "." + added.substring(2, added.length()));}
                if(split[6].length()==7){masternode_marketcap.add(added.substring(0, 1) + "." + added.substring(1, added.length()));}
                if(split[6].length()==6){masternode_marketcap.add(added.substring(0, 3) + "." + added.substring(2, added.length()));}
                masternode_node_count.add(split[8]);
                masternode_purchase_value.add(split[10]);}
            else{
                masternode_name.add(split[0]);
                masternode_symbol.add(split[2].replace("(","").replace(")",""));
                masternode_percent_change.add(split[4].replace("$",""));
                Double add = Double.parseDouble(split[6]);
                String a = String.format("%.0f", add);
                String added = null;
                if (split[7].length() >9 ) {
                    added = a.substring(0, 3) + " B";
                }
                if (split[7].length() <= 9) {
                    added = a.substring(0, 3) + " M";
                }

                if(split[7].length()==11){masternode_marketcap.add(added.substring(0, 2) + "." + added.substring(2, added.length()));}
                if(split[7].length()==10){masternode_marketcap.add(added.substring(0, 1) + "." + added.substring(1, added.length()));}
                if(split[7].length()==9){masternode_marketcap.add(added.substring(0, 3) + "." + added.substring(3, added.length()));}
                if(split[7].length()==8){masternode_marketcap.add(added.substring(0, 2) + "." + added.substring(2, added.length()));}
                if(split[7].length()==7){masternode_marketcap.add(added.substring(0, 1) + "." + added.substring(1, added.length()));}
                if(split[7].length()==6){masternode_marketcap.add(added.substring(0, 3) + "." + added.substring(2, added.length()));}
                masternode_node_count.add(split[9]);
                masternode_purchase_value.add(split[11]);}
            //System.out.println("THIS IS "+x+" "+split[6].length()+" "+masternode_name.get(x)+" "+masternode_symbol.get(x)+" "+masternode_percent_change.get(x)+" "+masternode_marketcap.get(x)+" "+masternode_node_count.get(x)+" "+masternode_purchase_value.get(x));

        }
    }

    public static void get_icos(){
        Document e =null;
        Constructor_Icos it =new Constructor_Icos();
        try {
            e = Jsoup.connect("https://topicolist.com/ongoing-icos/").userAgent("Mozilla").timeout(10 * 100000).get();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Elements tb =e.select("tbody");
        Elements ref =tb.select("tr");
        for(int i =0;i<ref.size();i++) {
            String w = ref.get(i).select("a").attr("title");
            String x = ref.get(i).select("td").get(2).text();
            String y = ref.get(i).select("td").get(3).text();
            String z = ref.get(i).select("td").get(4).text();
            DateFormat f = new SimpleDateFormat("MMMM dd, yyyy");
            Date formatted = null;
            Date formatted2 = null;
            try {
                if(y.isEmpty()){formatted = f.parse("December 31, 2099");}else{
                    formatted = f.parse(y);}
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            try {
                if(z.isEmpty()){formatted2 = f.parse("December 31, 2099");}else{
                    formatted2 = f.parse(z);}
            } catch (ParseException e1) {
                formatted2 = formatted;
                e1.printStackTrace();
            }

            ico_name.add(w);
            ico_message.add(x);
            String out = formatted.toString().substring(4, 10);
            String put = formatted.toString().substring(24,28);
            String out2 = formatted2.toString().substring(4, 10);
            String put2 = formatted2.toString().substring(24,28);
            String output=out+" "+put;
            String output2=out2+" "+put2;
            ico_startdate.add(output);
            ico_enddate.add(output2);
        }
        for (int i=0;i<ico_name.size();i++) {
            //("SIZE "+ico_name.get(i)+" "+ico_message.get(i)+" "+ico_startdate.get(i)+" "+ico_enddate.get(i));
        }
    }


    public static void get_ipos(){
        Document d =null;
        try {
            d= Jsoup.connect("https://www.marketbeat.com/IPOs").userAgent("Mozilla").timeout(10 * 100000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements z=d.select("tbody");
        Elements tr = z.select("tr");
        for (int i = 0; i < tr.size(); i++) {
            Element row = tr.get(i);
            Elements cols = row.select("td");
            ipo_date.add(cols.get(0).text());
            ipo_name.add(cols.get(1).text());
            ipo_range.add(cols.get(2).text());
            ipo_volume.add(cols.get(3).text());
        }

    }

    public static void getWorldMarkets(){
        Document us=null;
        try{
            us =Jsoup.connect("https://money.cnn.com/data/world_markets/americas/").timeout(10 *10000).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        Element etable1 = us.getElementById("wsod_indexDataTableGrid");
        Elements et1 =etable1.select("tr");
        Elements amer = et1.select("td");
        dow_name=amer.select("td").get(1).text();
        dow_change=amer.select("td").get(4).text();
        dow_amount=amer.select("td").get(5).text();

        sp_name=amer.select("td").get(8).text();
        sp_change=amer.select("td").get(11).text();
        sp_amount=amer.select("td").get(12).text();

        bov_name =amer.select("td").get(15).text();
        bov_change =amer.select("td").get(18).text();
        bov_amount =amer.select("td").get(19).text();


        Document euro=null;
        try{
            euro =Jsoup.connect("https://money.cnn.com/data/world_markets/europe/").timeout(10 *10000).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        Element etable = euro.getElementById("wsod_indexDataTableGrid");
        Elements et =etable.select("tr");
        Elements edt = et.select("td");
        ftse_name=edt.select("td").get(1).text();
        ftse_change=edt.select("td").get(4).text();
        ftse_amount=edt.select("td").get(5).text();

        cac_name=edt.select("td").get(15).text();
        cac_change=edt.select("td").get(18).text();
        cac_amount=edt.select("td").get(19).text();

        dax_name=edt.select("td").get(22).text();
        dax_change=edt.select("td").get(25).text();
        dax_amount=edt.select("td").get(26).text();


        Document asia=null;
        try{
            asia =Jsoup.connect("https://money.cnn.com/data/world_markets/asia/").timeout(10 *10000).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        Element table = asia.getElementById("wsod_indexDataTableGrid");
        Elements t =table.select("tr");
        Elements dt = t.select("td");
        shse_name=dt.select("td").get(8).text();
        shse_change=dt.select("td").get(11).text();
        shse_amount=dt.select("td").get(12).text();

        hang_name=dt.select("td").get(15).text();
        hang_change=dt.select("td").get(18).text();
        hang_amount=dt.select("td").get(19).text();

        nikk_name=dt.select("td").get(29).text();
        nikk_change=dt.select("td").get(32).text();
        nikk_amount=dt.select("td").get(33).text();

    }
    }


