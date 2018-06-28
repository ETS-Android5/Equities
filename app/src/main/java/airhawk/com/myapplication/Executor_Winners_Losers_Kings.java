package airhawk.com.myapplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static airhawk.com.myapplication.Constructor_App_Variables.all_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.alt_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.btc_market_cap_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_change;
import static airhawk.com.myapplication.Constructor_App_Variables.dow_name;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_change;
import static airhawk.com.myapplication.Constructor_App_Variables.nas_name;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_amount;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_change;
import static airhawk.com.myapplication.Constructor_App_Variables.sp_name;

//The original version of the core code was found on StackOverflow.com from user flup
//https://stackoverflow.com/users/1973271/flup
//https://stackoverflow.com/questions/21670451/how-to-send-multiple-asynchronous-requests-to-different-web-services


public class Executor_Winners_Losers_Kings {
    static Document sss = null;
    static Document sv = null;
    static Document z = null;
    static Document bb;
    static Document stock_data;
    static Document crypto_data;
    static ArrayList stock_volume_symbollist = new ArrayList();
    static ArrayList stock_volume_namelist = new ArrayList();
    static ArrayList stock_volume_volumelist = new ArrayList();
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

    public static void main() {
        ExecutorService service = Executors.newCachedThreadPool();
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                getCrypto_Market_Caps();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("getCrypto_Market_Caps TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                getCrypto_Winners_Losers();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("getCrypto_Winners_Losers TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });
/*
        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                getCrypto_Volume_Leaders();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("getCrypto_Volume_Leaders TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });
*/
        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                getStocks_Market_Caps();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("getStocks_Market_Caps TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });

        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                getStock_Winners_Losers();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("getStock_Winners_Losers TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });
/*
        callables.add(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.nanoTime();
                getStock_Volume_Leaders();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("getStock_Volume_Leaders TIME IS " + duration / 1000000000 + " seconds");
                return null;
            }
        });
*/


        try {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures) {
                System.out.println(future.get());
                //Where to check all variables
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void getCrypto_Market_Caps() {
        try {
            crypto_data = Jsoup.connect("https://www.worldcoinindex.com/").timeout(10 * 1000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements crypto_content = crypto_data.getElementsByClass("top-marketcap");
        String cryptext = crypto_content.text();
        String[] cryptarray = cryptext.split(" ");
        all_market_cap_amount = cryptarray[4];

        Elements btc_mc = crypto_data.getElementsByClass("row row-body zoomer");
        Elements xxdd = btc_mc.select("tbody");
        Elements cc = xxdd.select("span");
        String c = cc.text();
        String ce[] = c.split(" ");
        btc_market_cap_change = ce[4];
        String are = xxdd.text();
        String aren[] = are.split(" ");
        String ca = cryptarray[4].replace("B", "");
        String ba = aren[14].replace("B", "");
        Double dca = Double.parseDouble(ca);
        Double dba = Double.parseDouble(ba);
        Double dcadba = dca - dba;
        Float t = dcadba.floatValue();
        String altmc = t.toString();
        btc_market_cap_amount = aren[14];
        alt_market_cap_amount = altmc + "B";
    }

    public static void getCrypto_Winners_Losers() {
        //CRYPTO LOSERS ARRAYS
        try {
            stock_data = Jsoup.connect("https://coinmarketcap.com/gainers-losers/").timeout(10 * 1000).get();
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
        long startTime3 = System.nanoTime();
        Element l = ffsd.get(1);
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

    public static void getCrypto_Volume_Leaders() {
        try {
            bb = Jsoup.connect("https://www.barchart.com/crypto/market-capitalizations?orderBy=volume&orderDir=desc").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements crypto_table_body = bb.select("a[data-ng-href]");
        String s =crypto_table_body.text();
        System.out.println("THIS IS IT "+s+crypto_table_body);




        crypto_volume_volumelist.add(1);
        crypto_volume_namelist.add("Taco");
/*
        //THIS METHOD IS TAKING TOO LONG
        Elements crypto_table_body = bb.select("tbody");
        Elements crypto_table_row = crypto_table_body.select("tr");
        Elements crypto_table_divider = crypto_table_row.select("td");
        for (int i = 0; i < 300; i += 66) {
            String split[] = crypto_table_divider.get(i).text().split(" ");
            crypto_volume_namelist.add(split[1]);
        }
        for (int i = 63; i < 363; i += 66) {
            crypto_volume_volumelist.add(crypto_table_divider.get(i).text());
        }
*/
    }

    public static void getStocks_Market_Caps() {
        try {
            z = Jsoup.connect("https://finance.yahoo.com").timeout(10 * 1000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element table = z.getElementById("Lead-2-FinanceHeader-Proxy");
//        System.out.println("THIS SEEMS TO BE OCCASIONALLY NULL "+table.select("h3").text());
        //CREATE METHOD to rerun
        Elements as = table.select("a");
        Elements as2 = table.select("span");
        String temp = as.get(0).text();
        temp = temp.replace("&", "");
        sp_name = temp;
        dow_name = as.get(2).text();
        nas_name = as.get(4).text();
        sp_amount = as2.get(2).text();
        sp_change = as2.get(5).text();
        dow_amount = as2.get(6).text();
        dow_change = as2.get(9).text();
        nas_amount = as2.get(10).text();
        nas_change = as2.get(13).text();

    }

    public static void getStock_Winners_Losers() {
        try {
            sss = Jsoup.connect("http://money.cnn.com/data/hotstocks/sp1500/").timeout(10 * 1000).get();
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

    public static void getStock_Volume_Leaders() {
        try {
            sv = Jsoup.connect("https://finance.yahoo.com/most-active/").timeout(10 * 1000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements table_body = sv.select("tbody");
        Elements table_row = table_body.select("tr");
        Elements table_divider = table_row.select("td");
        for (int i = 4; i < table_divider.size(); i += 11) {
            stock_volume_namelist.add(table_divider.get(i).text());
        }
        for (int i = 3; i < table_divider.size(); i += 11) {
            stock_volume_symbollist.add(table_divider.get(i).text());
        }
        for (int i = 8; i < table_divider.size(); i += 11) {
            stock_volume_volumelist.add(table_divider.get(i).text());
        }


    }

}
