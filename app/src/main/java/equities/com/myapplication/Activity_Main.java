package equities.com.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static equities.com.myapplication.Constructor_App_Variables.*;
import static equities.com.myapplication.Fragment_Analysis.mTimer;

public class Activity_Main extends AppCompatActivity {
    Database_Local_Aequities check_saved = new Database_Local_Aequities(Activity_Main.this);
    RequestQueue requestQueue;
    TextView txt;
    //final int[] ICONS = new int[]{R.drawable.direction_markets, R.drawable.direction_up, R.drawable.direction_down, R.drawable.direction_kings, R.drawable.direction_news, R.drawable.direction_masternode, R.drawable.direction_icos, R.drawable.direction_ipos};
    static Element price = null;
    protected ArrayAdapter<String> ad;
    private static Toolbar toolbar;
    TableRow table_tabs;
    ProgressBar progress;
    public static ArrayList<String> searchview_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_symbol_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_name_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_type_arraylist = new ArrayList<>();
    public TabLayout tabs;
    private static final String TAG = "ActivityMain";
    static String fullScreen ="no";
    static Constructor_App_Variables ap_info = new Constructor_App_Variables();
    ImageView imageView;
    LinearLayout lin_lay;
    RelativeLayout progLayout;
    Animation centerLinear;
    boolean forward;
    static String repo;
    static Boolean async_analysis_page = false;
    public static boolean db_exist =false;
    Context context =this;
    static Adapter_Main_Markets adapter;
    static RecyclerView.LayoutManager l;
    static InterstitialAd mInterstitialAd;
    private SwipeRefreshLayout swipe_container;
    static ViewPager pager;
    @Override
    protected void onStart() {
        super.onStart();
        //Starting Car
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Put car in gear

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Put car in neutral

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Turn off car

    }
    protected void onRestart(){
        super.onRestart();
        System.out.println("RESTARTING APPLICATION");
    }

    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.v(TAG, "Internet Connection Not Present");
            this.finishAffinity();
            return false;
        }
    }

    public void StartApplication(){
        MobileAds.initialize(this, "ca-app-pub-6566728316210720/4471280326");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6566728316210720/4471280326");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        checkInternetConnection();
        get_crypto_exchange_info();
        get_stock_exchange_info();
        setContentView(R.layout.splash);
        lin_lay =findViewById(R.id.lin_lay);

        centerLinear = AnimationUtils.loadAnimation(this, R.anim.center);
        imageView =findViewById(R.id.imageView);

        progress= findViewById(R.id.progressBar);
        txt = findViewById(R.id.output);
        new setAsyncCreateSavedData(this).execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check_if_first_download();
        //MobileAds.initialize(this, "ca-app-pub-6566728316210720/4471280326");

    }

    public void onBackPressed() {
        ViewPager pager = findViewById(R.id.viewpager);

        if (pager.getVisibility()==View.VISIBLE){
            finish();}else {
            reloadAllData();
                new AsyncChosenData(this).cancel(true);
                new AsyncForBackPressedSavedData(Activity_Main.this).execute();


        }

    }

    public class setAsyncCreateSavedData extends AsyncTask<Integer, Integer, String> {

        private WeakReference<Activity_Main> activityReference;
        setAsyncCreateSavedData(Activity_Main context) {
            activityReference = new WeakReference<>(context);
        }


        @Override
        protected String doInBackground(Integer... params) {
            ArrayList aa = check_saved.getType();
            if(aa.size()>0){
                getSavedEquities();}
            return "task finished";
        }
        @Override
        protected void onPostExecute(String result) {
            new AsyncDataMain(Activity_Main.this).execute();

        }

    }

    protected void setMainPage() {
        setContentView(R.layout.activity_main);
        setJSON_INFO();

        toolbar = findViewById(R.id.toolbar);
        progLayout=findViewById(R.id.progLayout);
        openSearchView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.VISIBLE);
        ViewPager market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.GONE);


        swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipe_container.setEnabled(false);
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (pager.getVisibility()==View.VISIBLE){
                    StartApplication();
                }
                if (market_pager.getVisibility()==View.VISIBLE){
                    new AsyncChosenData(Activity_Main.this).execute();
                }
                swipe_container.setRefreshing(false);
            }
        });

        table_tabs = findViewById(R.id.table_tabs);
        TabLayout pagetabs = findViewById(R.id.tabs);
        setupMainViewPager(pager);
        pagetabs.setupWithViewPager(pager);
        if(pager.getVisibility()==View.VISIBLE){
            progLayout.setVisibility(View.GONE);
        }else {
            progLayout.setVisibility(View.VISIBLE);

        }

              }
              
    public void setupMainViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_Markets(), getString(R.string.title_markets));
        adapter.addFrag(new Fragment_Winners(), getString(R.string.leaders));
        adapter.addFrag(new Fragment_Losers(), getString(R.string.losers));
        if (check_saved.getName().size()>0){
            adapter.addFrag(new Fragment_Saved(), getString(R.string.saved));
        }
        adapter.addFrag(new Fragment_Market_Kings(), getString(R.string.market_kings));
        adapter.addFrag(new Fragment_App_News(), getString(R.string.news));
        adapter.addFrag(new Fragment_Masternodes(),getString(R.string.masternodes));
        //adapter.addFrag(new Fragment_Icos(),getString(R.string.ico));
        adapter.addFrag(new Fragment_Ipos(),getString(R.string.ipo));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(7);

    }

    public void setupChosenViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (ap_info.getMarketType().equals("Index")||ap_info.getMarketType().equals("Stock")) {
            adapter.addFrag(new Fragment_Analysis(), getString(R.string.action_analysis));
            adapter.addFrag(new Fragment_News_Chosen(), getString(R.string.title_news));
            adapter.addFrag(new Fragment_Video(), getString(R.string.title_video));
            adapter.addFrag(new Fragment_StockTwits(),getString(R.string.stocktwits));
        }else {
            adapter.addFrag(new Fragment_Analysis(), getString(R.string.action_analysis));
            adapter.addFrag(new Fragment_News_Chosen(), getString(R.string.title_news));
            adapter.addFrag(new Fragment_Video(), getString(R.string.title_video));
            adapter.addFrag(new Fragment_Exchanges(), getString(R.string.title_exchanges));
            adapter.addFrag(new Fragment_StockTwits(), getString(R.string.stocktwits));

        }
        viewPager.setAdapter(adapter);
    }

    public void setJSON_INFO() {
        try {
            String jsonLocation = AssetJSONFile("rd.json", Activity_Main.this);
            JSONObject obj = new JSONObject(jsonLocation);

            JSONArray Arry = obj.getJSONArray("ALL");

            for (int i = 0; i < Arry.length(); i++) {
                JSONArray childJsonArray = Arry.getJSONArray(i);
                String sym = childJsonArray.getString(0);
                aequity_symbol_arraylist.add(sym);
                String nam = childJsonArray.getString(1);
                aequity_name_arraylist.add(nam);
                String typ = childJsonArray.getString(2);
                aequity_type_arraylist.add(typ);
                searchview_arraylist.add(sym + "  " + nam + "  " + typ);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void reloadAllData() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Fragment_Analysis fragment_analysis = new Fragment_Analysis();
        adapter.removeFrag(fragment_analysis, "Fragment_Analysis");
        mTimer.cancel();
        graph_date.clear();
        graph_high.clear();
        graph_volume.clear();
        feedItems.clear();
        stocktwits_feedItems.clear();
        image_video_url.clear();
        video_url.clear();
        video_title.clear();
    }

    public void openSearchView() {
        ad = new ArrayAdapter<String>(Activity_Main.this, R.layout.searchbar,
                R.id.searchtool, searchview_arraylist);
        AutoCompleteTextView chosen_searchView_item = findViewById(R.id.searchtool);
        chosen_searchView_item.setAdapter(ad);


        chosen_searchView_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchview_arraylist.clear();
                setJSON_INFO();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                ViewPager mp = findViewById(R.id.market_pager);
                mp.setVisibility(View.GONE);
                String[] split_marketinfo = ad.getItem(position).toString().split("  ");
                ap_info.setMarketSymbol(split_marketinfo[0]);
                ap_info.setMarketName(split_marketinfo[1]);
                ap_info.setMarketType(split_marketinfo[2]);
                chosen_searchView_item.onEditorAction(EditorInfo.IME_ACTION_DONE);
                new AsyncChosenData(Activity_Main.this).execute();
                chosen_searchView_item.setText("");
                reloadAllData();

            }
        });
    }

    public void getChosenCryptoInfo(){
        long startTime = System.nanoTime();
        String newString= null;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        if(ap_info.getMarketName().equalsIgnoreCase("XRP")){
            newString = "Ripple";
        }else {
            newString = ap_info.getMarketName();
            //("1 -"+newString);
        }if(newString.contains(" ")){
            newString =newString.replace(" ","-");
        }

        final String url = "https://api.coinmarketcap.com/v1/ticker/"+newString;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    JSONObject heroObject = null;
                    try {
                        heroObject = response.getJSONObject(i);
                        ap_info.setMarketSymbol(heroObject.getString("symbol"));
                        ap_info.setCurrent_Aequity_Price(heroObject.getString("price_usd").substring(0,6));
                        //volume_24 =heroObject.getString("24h_volume_usd");
                        DecimalFormat df = new DecimalFormat("0.00");
                        df.setMaximumFractionDigits(2);
                        String y = heroObject.getString("available_supply");
                        if(y !="null") {
                            double dd = Double.parseDouble(y);
                            y = df.format(dd);
                            int z = y.length();
                            long tt = 1000000000000L;
                            if (z < 10) {
                                y = String.valueOf(dd / 1000);
                                ap_info.setMarketSupply(y.substring(0, 5) + "TH");
                            }
                            if (z <= 12) {
                                y = String.valueOf(dd / 1000000);
                                ap_info.setMarketSupply(y.substring(0, 4) + "M");
                            }
                            if (z > 12) {
                                y = String.valueOf(dd / 1000000000);
                                try{
                                ap_info.setMarketSupply(y.substring(0, 5) + "B");}
                                catch (StringIndexOutOfBoundsException e){
                                    ap_info.setMarketSupply("UNKNOWN");
                                }
                            }
                            if (z > 15) {
                                y = String.valueOf(dd / tt);
                                ap_info.setMarketSupply(y.substring(0, 5) + "T");
                            }

                            ap_info.setCurrent_Aequity_Price_Change(heroObject.getString("percent_change_24h") + "%");
                            String p =heroObject.getString("market_cap_usd");
                            double d = Double.parseDouble(p);
                            p =df.format(d);
                            int l =p.length();
                            long t = 1000000000000L;
                            if (l<10){p= String.valueOf(d/1000);
                                ap_info.setMarketCap(p.substring(0,5)+"TH");}
                            if (l<=12){p= String.valueOf(d/1000000);
                                ap_info.setMarketCap(p.substring(0,5)+"M");}
                            if (l>12){p= String.valueOf(d/1000000000);
                                ap_info.setMarketCap(p.substring(0,5)+"B");}
                            if (l>15){p= String.valueOf(d/t);
                                ap_info.setMarketCap(p.substring(0,5)+"T");}}
                        else{ap_info.setMarketSupply("Unknown");
                            ap_info.setMarketCap("Unknown");
                            ap_info.setCurrent_Aequity_Price_Change("Unknown");}
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                //btc_market_cap_change=

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //("Something ahppened An Error occured while making the request "+error);
            }
        });
        requestQueue.add(jsonArrayRequest);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        //("CRYPTO TIME IS " + duration / 1000000000 + " seconds");

    }

    public void get_current_stock_info(){
        String symbol =ap_info.getMarketSymbol();
        String apikey ="XBA42BUC2B6U6G5C";
        String url ="https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+symbol+"&apikey="+apikey;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // your response
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    // get Time

                    JSONObject time = jsonObject.getJSONObject("Global Quote");
                    Iterator<String> iterator = time.keys();

                        String price = time.getString("05. price");
                        String change= time.getString("10. change percent");
                    DecimalFormat numberFormat = new DecimalFormat("#.00");
                    double d =Double.parseDouble(price);
                    String fclose=numberFormat.format(d);
                        ap_info.setCurrent_Aequity_Price(fclose);
                        ap_info.setCurrent_Aequity_Price_Change(change);





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //("An Error occured while making the ST request");
            }
        });
        requestQueue.add(jsonObjectRequest);




    }

    public void getStockTwitsData(){
        String market_symbol=ap_info.getMarketSymbol();
        if (market_symbol.contains("%5E")){
        market_symbol =market_symbol.replace("%5E","");}
        if (ap_info.getMarketType().equals("Crypto")||(ap_info.getMarketType().equals("Cryptocurrency"))) {
            market_symbol =market_symbol+".X";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://api.stocktwits.com/api/2/streams/symbol/"+market_symbol+".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray responseJSONArray = response.getJSONArray("messages");
                    for (int i = 0; i < responseJSONArray.length(); i++) {
                        JSONObject user_info = (JSONObject) responseJSONArray.getJSONObject(i).get("user");
                        String message_time = (String) responseJSONArray.getJSONObject(i).get("created_at");
                        String message = (String) responseJSONArray.getJSONObject(i).get("body");
                        Iterator x = user_info.keys();
                        Constructor_Stock_Twits item = new Constructor_Stock_Twits();
                        item.setMessage_time(message_time);
                        item.setUser_name("" + user_info.get("username"));
                        String url = user_info.get("avatar_url_ssl").toString();
                        item.setUser_image_url(url);
                        item.setMessage(message);

                        String[] words = message.split("\\s+");


                        Pattern pattern = Patterns.WEB_URL;
                        for(String word : words)
                        {
                            if(pattern.matcher(word).find())
                            {
                                if(!word.toLowerCase().contains("http://") && !word.toLowerCase().contains("https://"))
                                {
                                    word = "http://" + word;
                                }
                                item.setMessage_link(word);

                            }
                        }

                        JSONArray jsonArray =new JSONArray();
                        while(x.hasNext()){
                            String key = (String) x.next();
                            jsonArray.put(user_info.get(key));

                        }


                    stocktwits_feedItems.add(item);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }
//                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Constructor_Stock_Twits item = new Constructor_Stock_Twits();
                item.setMessage_time("No Social data for "+ap_info.getMarketSymbol());
                item.setUser_name("");
                item.setUser_image_url(" - ");
                item.setMessage("");
                stocktwits_feedItems.add(item);
                //("An Error occured while making the request");
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public static void get_coinmarketcap_exchange_listing() {
        Document doc = null;
        String name = ap_info.getMarketName();
        //("THIS IS THE MARKET NAME "+name);
        if (name.contains(" ")){
            name =name.replace(" ","-");
        }

        try {
            doc = Jsoup.connect("https://coinmarketcap.com/currencies/"+name+"/#markets").timeout(10 * 1000).get();
            Element tb = doc.getElementById("markets-table");
            Elements rows = null;
            if(tb!=null) {
                rows = tb.select("tr");
                for(int i =0; i<rows.size();i++){
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    for(int z =0; z<cols.size();z++) {
                        String line = cols.get(1).text();
                        if(!exchange_list.contains(line))
                            exchange_list.add(line);
                    }
                }
            }else{
                getWorldCoinIndex();
            }

            //("EXCHANGE LIST"+exchange_list);
        } catch (HttpStatusException x){
            //("There is no information about "+name +", so now we try world coinindex");
            getWorldCoinIndex();
        } catch (IOException e) {

            e.printStackTrace(); }
        HashSet<String> set = new HashSet<String>();

        for (int i = 0; i < crypto_exchange_name.size(); i++)
        {
            for (int j = 0; j < exchange_list.size(); j++)
            {
                if(crypto_exchange_name.get(i).equals(exchange_list.get(j)))
                {
                    aequity_exchanges.add(""+ crypto_exchange_name.get(i));
                }
            }
        }

        // return common elements.
        ////("Common element : "+(aequity_exchanges));




    }

    public static void getWorldCoinIndex(){
        Document doc = null;
        String name = ap_info.getMarketName();
        try {
            doc = Jsoup.connect("https://www.worldcoinindex.com/coin/"+name).timeout(10 * 1000).get();
            String s = doc.getElementsByClass("mob-exchange exchange").text();
            List<String> myList = new ArrayList<String>(Arrays.asList(s.split(" ")));
            for(int i=0; i<myList.size();i++){
                exchange_list.add(myList.get(i));
                ////(i+" "+exchange_list.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void get_crypto_exchange_info() {

        crypto_exchange_name.add("Binance");
        crypto_exchange_name.add("Hbus");
        crypto_exchange_name.add("Coinbase");
        crypto_exchange_name.add("Cryptopia");;
        crypto_exchange_name.add("CryptoBridge");


        crypto_exchange_url.add("https://www.binance.com/?ref=35795495");
        //Referral
        crypto_exchange_url.add("https://www.hbus.com/invite/inviteRank?invite_code=kgd23");
        //Referral
        crypto_exchange_url.add("https://www.coinbase.com/join/5a2cc6b6f3b80300ef643aa4");
        //Referral
        crypto_exchange_url.add("https://www.cryptopia.co.nz/Register?referrer=juliansmulian");
        //Referral
        crypto_exchange_url.add("https://crypto-bridge.org/");



        crypto_exchange_image.add("R.drawable.exchange_crypto_binance");
        crypto_exchange_image.add("R.drawable.exchange_crypto_hbus");
        crypto_exchange_image.add("R.drawable.exchange_crypto_coinbase");
        crypto_exchange_image.add("R.drawable.exchange_crypto_cryptopia");
        crypto_exchange_image.add("R.drawable.exchange_crypto_cryptobridge");




    }

    public void get_stock_exchange_info(){
        stock_exchange_name.add("E*TRADE");
        stock_exchange_name.add("TD Ameritrade");
        stock_exchange_name.add("Merrill Edge");
        stock_exchange_name.add("Charles Schwab ");
        stock_exchange_name.add("Robinhood");

        stock_exchange_url.add("https://www.etrade.com");
        stock_exchange_url.add("https://www.tdameritrade.com/home.page");
        stock_exchange_url.add("https://www.merrilledge.com/");
        stock_exchange_url.add("https://www.schwab.com/");
        stock_exchange_url.add("https://www.robinhood.com/");

        stock_exchange_image.add("exchange_stock_etrade");
        stock_exchange_image.add("exchange_stock_tdameritrade");
        stock_exchange_image.add("exchange_stock_merrill_edge");
        stock_exchange_image.add("exchange_stock_schwab");
        stock_exchange_image.add("exchange_stock_robinhood");

        HashSet sen = new HashSet();
        sen.addAll(stock_exchange_name);
        stock_exchange_name.clear();
        stock_exchange_name.addAll(sen);

        HashSet seu = new HashSet();
        seu.addAll(stock_exchange_url);
        stock_exchange_url.clear();
        stock_exchange_url.addAll(seu);

        HashSet sei = new HashSet();
        sei.addAll(stock_exchange_image);
        stock_exchange_image.clear();
        stock_exchange_image.addAll(sei);

    }

    public static void do_graph_change() {
        Double a=0.00;if(graph_high.size()>0) {
            for (int i = 0; i < graph_high.size(); i++) {
                if (graph_high.get(i) != null) {
                    if (graph_high.get(i) == "null") {
                        graph_high.get(i).toString().replace("null", "0.00");
                    }
                    a = new Double(graph_high.get(i).toString().replace(",", "").replace("<", "").replace("/", "").replace("-", ""));
                } else {
                    a = 0.00;
                }
                if (i > 0) {
                    int z = i - 1;
                    if (graph_high != null) {
                        double b = new Double(graph_high.get(z).toString().replace(",", "").replace("<", "").replace("/", ""));
                        double c = ((a - b) / a) * 100;
                        DecimalFormat numberFormat = new DecimalFormat("#0.00");
                        String add = numberFormat.format(c).replace("-", "");
                        graph_change.add(add);
                    } else {
                        graph_change.add("0");
                    }

                }
            }
        }

    }

    private void check_if_first_download() {


        int currentVersionCode = BuildConfig.VERSION_CODE;
        //SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);
       // if (currentVersionCode == savedVersionCode) {
            StartApplication();
      /*
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

         setContentView(R.layout.activity_equity_choice);
         Button start =(Button)findViewById(R.id.start_app);
         CheckBox masternodes =(CheckBox)findViewById(R.id.crypto_masternodes);
            CheckBox proofofwork =(CheckBox)findViewById(R.id.crypto_proof_of_work);
            CheckBox proofofstake =(CheckBox)findViewById(R.id.crypto_proof_of_stake);
            CheckBox icos =(CheckBox)findViewById(R.id.ico);
            CheckBox cryptowisdom=(CheckBox)findViewById(R.id.crypto_wisdom);
            CheckBox commodities =(CheckBox)findViewById(R.id.commodities);
            CheckBox bonds =(CheckBox)findViewById(R.id.bonds);
            CheckBox etfs =(CheckBox)findViewById(R.id.etf);
            CheckBox ipos =(CheckBox)findViewById(R.id.ipo);
            CheckBox stockwisdom =(CheckBox)findViewById(R.id.stock_wisdom);
         start.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 sharedpreferences = getSharedPreferences(Data_Preferences, Context.MODE_PRIVATE);
                 if (masternodes.isChecked()) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString(Masternode, "yes");
                     editor.commit();
                     //("THIS IS MASTERNODE VALUE "+Masternode);
                 }
                 if (proofofwork.isChecked()) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("proofofwork", "yes");
                     editor.commit();
                 }
                 if (proofofstake.isChecked()) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("proofofstake", "yes");
                     editor.commit();
                 }
                 if (icos.isChecked()) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("icos", "yes");
                     editor.commit();
                 }
                 if (cryptowisdom.isChecked()) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("cryptowisdom", "yes");
                     editor.commit();
                 }
                 if (commodities.isChecked()) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("commodities", "yes");
                     editor.commit();
                 }
                 if (bonds.isChecked()) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("bonds", "yes");
                     editor.commit();
                 }
                 if (etfs.isChecked()) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("etfs", "yes");
                     editor.commit();
                 }
                 if (ipos.isChecked()) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("ipos", "yes");
                     editor.commit();
                 }
                 if (stockwisdom.isChecked()) {
                     SharedPreferences.Editor editor = sharedpreferences.edit();
                     editor.putString("stockwisdom", "yes");
                     editor.commit();
                 }

                 StartApplication();
                 return;
             }});




        } else if (currentVersionCode > savedVersionCode) {
            StartApplication();
            return;
        }

        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
     */

    }

    public static void ProcessXmlx(org.w3c.dom.Document data) {
        all_feedItems.clear();
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

    public static org.w3c.dom.Document GoogleRSFeedx() {
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

    public void get_saved_stock_price_change(String taco){
        //String symbol =ap_info.getMarketSymbol();
        String apikey ="XBA42BUC2B6U6G5C";
        String url ="https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+taco+"&apikey="+apikey;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONObject time = jsonObject.getJSONObject("Global Quote");
                    Iterator<String> iterator = time.keys();
                    String change= time.getString("10. change percent");
                    change=change.replace("%","");
                    DecimalFormat numberFormat = new DecimalFormat("#.00");
                    double d =Double.parseDouble(change);
                    String fclose=numberFormat.format(d);
                    fclose=fclose+"%";
                    current_percentage_change.add(fclose);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //("An Error occured while making the ST request");
            }
        });
        requestQueue.add(jsonObjectRequest);




    }

    public void getSavedEquities(){
        ArrayList b = check_saved.getSymbol();
        ArrayList c = check_saved.getType();
        for( int x=0;x<b.size();x++) {
            Document cap = null;
            if (c.get(x).equals("Stock")||c.get(x).equals("Index")) {
                get_saved_stock_price_change(""+check_saved.getSymbol().get(x));
            } else {
                ArrayList d = check_saved.getName();
                Document caps = null;
                if (d.size()>0) {
                    String g = String.valueOf(d.get(x));
                    if (g.contains(" ")) {
                        g = g.replace(" ", "-");
                    }
                    try {
                        caps = Jsoup.connect("https://coinmarketcap.com/currencies/" + g).timeout(10 * 10000).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Element as = caps.getElementsByClass("details-panel-item--header flex-container").first();
                    Elements e = as.select("span:eq(1)");
                    String f = e.get(2).text();;
                    f = f.replaceAll("\\(", "").replaceAll("\\)", "");
                    current_percentage_change.add(f);


                }
            }

        }check_saved.close();
    }
}