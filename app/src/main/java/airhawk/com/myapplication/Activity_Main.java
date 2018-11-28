package airhawk.com.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import com.twitter.sdk.android.core.TwitterSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


import static airhawk.com.myapplication.Constructor_App_Variables.*;
import static airhawk.com.myapplication.Service_Main_Equities.crypto_kings_changelist;
import static airhawk.com.myapplication.Service_Main_Equities.crypto_kings_marketcaplist;
import static airhawk.com.myapplication.Service_Main_Equities.crypto_kings_namelist;
import static airhawk.com.myapplication.Service_Main_Equities.crypto_kings_symbolist;

public class Activity_Main extends AppCompatActivity {
    final String PREFS_NAME = "TheSteelersalwayscheat";
    final String PREF_VERSION_CODE_KEY = "version_code";
    final int DOESNT_EXIST = -1;
    int savedVersionCode;
    public static final String Data_Preferences = "MyPrefs" ;
    public static final String Masternode = "masternodes";
    public static final String ProofofWork = "proofofwork";
    public static final String ProofofStake = "proofofstake";
    public static final String Icos ="icos";
    public static final String CryptoWisdom="cryptowisdom";
    public static final String Commodities="commodities";
    public static final String Bonds="bonds";
    public static final String Etfs="etfs";
    public static final String Ipos="ipos";
    public static final String StockWisdom ="stockwisdom";

    SharedPreferences sharedpreferences;
    Database_Local_Aequities check_saved = new Database_Local_Aequities(Activity_Main.this);
    RequestQueue requestQueue;
    Database_Local_Aequities co =new Database_Local_Aequities(this);
    TextView txt;
    final int[] ICONS = new int[]{
            R.drawable.direction_up,
            R.drawable.direction_down,
            R.drawable.direction_kings,
            R.drawable.direction_news,
            R.drawable.direction_masternode
            };
    static Element price = null;
    protected ArrayAdapter<String> ad;
    private Toolbar toolbar;
    ProgressBar mainbar,progress;
    public static ArrayList<String> searchview_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_symbol_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_name_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_type_arraylist = new ArrayList<>();
    public ViewPager pager, market_pager;
    public TabLayout tabs;
    private static final String TAG = "ActivityMain";
    static String fullScreen ="no";
    static Constructor_App_Variables ap_info = new Constructor_App_Variables();
    ImageView search_button,imageView;
    LinearLayout lin_lay;
    RelativeLayout progresslayout;
    FrameLayout fu,frameLayout;
    static RecyclerView recyclerView;
    Animation centerLinear;
    boolean forward;
    static Boolean async_analysis_page = false;
    public static boolean db_exist =false;
    Context context =this;
    static Adapter_Main_Markets adapter;
    static RecyclerView.LayoutManager l;
    private static InterstitialAd mInterstitialAd;


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
        // test for connection
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

    public void starterup(){
        MobileAds.initialize(this, "ca-app-pub-6566728316210720/4471280326");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6566728316210720/4471280326");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        checkInternetConnection();
        get_crypto_exchange_info();
        get_stock_exchange_info();
        setContentView(R.layout.splash);
        lin_lay =(LinearLayout)findViewById(R.id.lin_lay);

        centerLinear = AnimationUtils.loadAnimation(this, R.anim.center);
        imageView =findViewById(R.id.imageView);
        //imageView.startAnimation(centerLinear);

        progress= (ProgressBar) findViewById(R.id.progressBar);
        txt = (TextView) findViewById(R.id.output);

        new setAsyncDataMain(this).execute();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check_if_first_download();

    }

    public void onBackPressed() {
        System.out.println("CODE "+savedVersionCode+" "+DOESNT_EXIST);
        //if (savedVersionCode == DOESNT_EXIST) {
          //  check_if_first_download();
        //}else {
            check_to_show_ad();
            graph_change.clear();
            exchange_list.clear();
            aequity_exchanges.clear();
            stocktwits_feedItems.clear();
            graph_date.clear();
            graph_high.clear();
            graph_volume.clear();
            new setAsyncChosenData(this).cancel(true);
            new setSavedAsyncDataMain(this).execute();
        //}

    }

    public class setSavedAsyncDataMain extends AsyncTask<Integer, Integer, String> {

        private WeakReference<Activity_Main> activityReference;
        setSavedAsyncDataMain(Activity_Main context) {
            activityReference = new WeakReference<>(context);
        }
        long startTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Activity_Main activity = activityReference.get();
            startTime = System.nanoTime();
            Random rand = new Random();
            int value = rand.nextInt(50);
            String [] qu =getResources().getStringArray(R.array.all_quotes);
            String q = qu[value];
            TextView txt2 =activity.findViewById(R.id.output2);
            txt2.setText(q);
            RelativeLayout progLayout =activity.findViewById(R.id.progLayout);
            progLayout.setVisibility(View.GONE);
            ProgressBar mainbar = activity.findViewById(R.id.mainbar);
            mainbar.setIndeterminate(true);
            ViewPager mp = activity.findViewById(R.id.market_pager);
            mp.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(Integer... params) {
            getAdded_stock_point();
            return "task finished";
        }
        @Override
        protected void onPostExecute(String result) {
            Activity_Main activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            ViewPager pager = findViewById(R.id.viewpager);
            if (pager.getVisibility() == View.VISIBLE) {
                activity.finishAffinity();
            }
            pager.setVisibility(View.VISIBLE);
            ViewPager market_pager = findViewById(R.id.market_pager);
            market_pager.setVisibility(View.GONE);
            TabLayout tabs = findViewById(R.id.tabs);

            setupMainViewPager(pager);
            tabs.setupWithViewPager(pager);
            tabs.getTabAt(0).setIcon(ICONS[0]);
            tabs.getTabAt(1).setIcon(ICONS[1]);
            if (check_saved.getName().isEmpty()){
                tabs.getTabAt(2).setIcon(ICONS[2]);
                tabs.getTabAt(3).setIcon(ICONS[3]);;
            }else{
                tabs.getTabAt(2).setIcon(android.R.drawable.btn_star_big_on);
                tabs.getTabAt(3).setIcon(ICONS[2]);
                tabs.getTabAt(4).setIcon(ICONS[3]);}
            recyclerView = findViewById(R.id.item_list);
            l =new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(l);
            adapter = new Adapter_Main_Markets(Activity_Main.this);
            recyclerView.setAdapter(adapter);
            autoScroll();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("SERVICE MAIN TIME IS " + duration / 1000000000 + " seconds");

        }

    }

    public class setAsyncDataMain extends AsyncTask<Integer, Integer, String> {

        private WeakReference<Activity_Main> activityReference;
        setAsyncDataMain(Activity_Main context) {
            activityReference = new WeakReference<>(context);
        }
        long startTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            startTime = System.nanoTime();
            Random rand = new Random();
            int value = rand.nextInt(31);
            String [] qu =getResources().getStringArray(R.array.all_quotes);
            String q = qu[value];
            txt.setText(q);
        }

        @Override
        protected String doInBackground(Integer... params) {


            getCrypto_Kings();
            getSaved_stock_points();
            //get_main_graph();

            Service_Main_Equities cst = new Service_Main_Equities();
            cst.main();

            //Service_Saved_Equity csa =new Service_Saved_Equity(context);
            //csa.main();
            //SAVED METHOD SLOWS DOWN APP BY 6 SECONDS. DONT FORGET TO UNHIDE CODE OM FRAGMENT_SAVED WHEN THIS IS REPAIRED
            return "task finished";
        }
        @Override
        protected void onPostExecute(String result) {
            // get a reference to the activity if it is still there
            Activity_Main activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            setMainPage();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("SERVICE MAIN TIME IS " + duration / 1000000000 + " seconds");

        }

    }

    public class setAsyncChosenData extends AsyncTask<Void, Void, String> {

        private WeakReference<Activity_Main> activityReference;
        setAsyncChosenData(Activity_Main context) {
            activityReference = new WeakReference<>(context);
        }
        long startTime;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Random rand = new Random();
            int svalue = rand.nextInt(25);
            int cvalue = rand.nextInt(25);

            Activity_Main activity = activityReference.get();
            String [] sq =activity.getResources().getStringArray(R.array.stock_quotes);
            String [] cq =activity.getResources().getStringArray(R.array.crypto_quotes);
            String q;
            if (ap_info.getMarketType().equals("Stock")){
                q = sq[svalue];}
                else{
                q = cq[cvalue];
            }
            TextView txt2 =activity.findViewById(R.id.output2);
            txt2.setText(q);
            startTime = System.nanoTime();
            RelativeLayout progLayout =activity.findViewById(R.id.progLayout);
            progLayout.setVisibility(View.VISIBLE);
            ProgressBar mainbar = activity.findViewById(R.id.mainbar);
            mainbar.setIndeterminate(true);
            pager = activity.findViewById(R.id.viewpager);
            pager.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(Void... params) {
            Activity_Main activity = activityReference.get();
            if(isCancelled())
            {

                System.out.println("Async Cancelled");return null;}



//            getSupportActionBar().setDisplayShowTitleEnabled(true);
            if (async_analysis_page) {
                reloadAllData();
                if (ap_info.getMarketType().equals("Crypto")||(ap_info.getMarketType().equals("Cryptocurrency"))) {
                    activity.getChosenCryptoInfo();
                    activity.getStockTwitsData();
                    activity.get_coinmarketcap_exchange_listing();
                    Service_Chosen_Equity shoe = new Service_Chosen_Equity(Activity_Main.this);

                    shoe.main();
                    do_graph_change();
                    System.out.println("ASYNC C HAS BEEN CALLED PREVIOUSLY");
                }else{

                    activity.get_current_stock_info();
                    activity.getStockTwitsData();
                    Service_Chosen_Equity shoe = new Service_Chosen_Equity(Activity_Main.this);

                    System.out.println("ASYNC S HAS BEEN CALLED PREVIOUSLY");
                    shoe.main();
                    do_graph_change();
                }



            } else {
                if (ap_info.getMarketType().equals("Crypto")||(ap_info.getMarketType().equals("Cryptocurrency"))) {
                    activity.getChosenCryptoInfo();
                    activity.getStockTwitsData();
                    activity.get_coinmarketcap_exchange_listing();
                    Service_Chosen_Equity shoe = new Service_Chosen_Equity(Activity_Main.this);

                    shoe.main();
                    System.out.println("ASYNC* HAS NOT BEEN CALLED PREVIOUSLY");
                    do_graph_change();
                }else{

                    activity.get_current_stock_info();
                    activity.getStockTwitsData();
                    Service_Chosen_Equity shoe = new Service_Chosen_Equity(Activity_Main.this);
                    System.out.println("ASYNC* HAS NOT BEEN CALLED PREVIOUSLY");
                    shoe.main();
                    do_graph_change();
                }

                async_analysis_page = true;


            }
            return "task finished";
        }



        @Override
        protected void onPostExecute(String result) {
            fullScreen="go";
            // get a reference to the activity if it is still there
            Activity_Main activity = activityReference.get();


            String a=ap_info.getCurrent_Aequity_Price();
            if (activity == null || activity.isFinishing()&&graph_high.size()>0&&requestQueue !=null&& forward) return;
            ProgressBar mainbar = activity.findViewById(R.id.mainbar);
            mainbar.setIndeterminate(false);
            pager = activity.findViewById(R.id.viewpager);
            pager.setVisibility(View.GONE);
            market_pager = activity.findViewById(R.id.market_pager);
            market_pager.setVisibility(View.VISIBLE);

            tabs = activity.findViewById(R.id.tabs);
            activity.setupChosenViewPager(market_pager);
            tabs.setupWithViewPager(market_pager);
            tabs.getTabAt(0).setIcon(R.drawable.direction_info);
            tabs.getTabAt(1).setIcon(R.drawable.direction_news);
            tabs.getTabAt(2).setIcon(R.drawable.direction_youtube_video);
            tabs.getTabAt(3).setIcon(R.drawable.direction_exchange);
            tabs.getTabAt(4).setIcon(R.drawable.direction_socialmedia);
            RelativeLayout progLayout =activity.findViewById(R.id.progLayout);
            progLayout.setVisibility(View.GONE);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("SERVICE Chosen TIME IS " + duration / 1000000000 + " seconds");

        }


    }

    private void setMainPage() {
        setContentView(R.layout.activity_main);
        setJSON_INFO();
        toolbar = findViewById(R.id.toolbar);
        fu=findViewById(R.id.frameLayout);
        fu.setVisibility(View.VISIBLE);
        search_button=findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSearchView();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ViewPager pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.VISIBLE);
        ViewPager market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.GONE);
        TabLayout tabs = findViewById(R.id.tabs);
        setupMainViewPager(pager);
        tabs.setupWithViewPager(pager);
        tabs.getTabAt(0).setIcon(ICONS[0]);
        tabs.getTabAt(1).setIcon(ICONS[1]);
        if (check_saved.getName().isEmpty()){
            tabs.getTabAt(2).setIcon(ICONS[2]);
            tabs.getTabAt(3).setIcon(ICONS[3]);
            //tabs.getTabAt(4).setIcon(ICONS[4]);
        }else{
            tabs.getTabAt(2).setIcon(android.R.drawable.btn_star_big_on);
            tabs.getTabAt(3).setIcon(ICONS[2]);
            tabs.getTabAt(4).setIcon(ICONS[3]);
           //tabs.getTabAt(5).setIcon(ICONS[4]);

            }



        recyclerView = findViewById(R.id.item_list);
        l =new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(l);
        adapter = new Adapter_Main_Markets(Activity_Main.this);
        recyclerView.setAdapter(adapter);
        autoScroll();
              }

    public void autoScroll(){
        final int speedScroll = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = -2;
            @Override
            public void run() {
                if(count == adapter.getItemCount())
                    count =0;
                recyclerView.smoothScrollToPosition(count-count);
                if(count < adapter.getItemCount()){
                    recyclerView.scrollToPosition(++count);
                    handler.postDelayed(this,3000);
                }
            }
        };
        handler.postDelayed(runnable,speedScroll);
    }

    private void setupMainViewPager(ViewPager viewPager) {
        SharedPreferences sp = this.getSharedPreferences(Data_Preferences, Context.MODE_PRIVATE);
        String masternodes = sp.getString(Masternode, "");
        String proofofwork = sp.getString(ProofofWork, "");
        String proofofstake = sp.getString(ProofofStake, "");
        String icos = sp.getString(Icos, "");
        String cryptowisdom = sp.getString(CryptoWisdom, "");
        String commodities = sp.getString(Commodities, "");
        String bonds = sp.getString(Bonds, "");
        String etfs = sp.getString(Etfs, "");
        String ipos = sp.getString(Ipos, "");
        String stockwisdom = sp.getString(StockWisdom, "");



        System.out.println("There are "+check_saved.getName().size()+" saved items at setupviewpager "+ masternodes);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_Winners(), getString(R.string.leaders));
        adapter.addFrag(new Fragment_Losers(), getString(R.string.losers));
        if (check_saved.getName().size()>0){
            adapter.addFrag(new Fragment_Saved(), getString(R.string.saved));
        }
        adapter.addFrag(new Fragment_App_News(), getString(R.string.news));
        adapter.addFrag(new Fragment_Market_Kings(), getString(R.string.market_kings));
        if(masternodes.equals("yes")){
            adapter.addFrag(new Fragment_Masternodes(),getString(R.string.masternodes));}
        if(proofofwork.equals("yes")){
            adapter.addFrag(new Fragment_Proof_Of_Work(),getString(R.string.crypto_proof_of_work));}
        if(proofofstake.equals("yes")){
            adapter.addFrag(new Fragment_Proof_Of_Stake(),getString(R.string.crypto_proof_of_stake));}
        if(icos.equals("yes")){
            adapter.addFrag(new Fragment_Icos(),getString(R.string.ico));}
        if(cryptowisdom.equals("yes")){
            adapter.addFrag(new Fragment_Crypto_Wisdom(),getString(R.string.crypto_wisdom));}
        if(commodities.equals("yes")){
            adapter.addFrag(new Fragment_Commodities(),getString(R.string.commodities));}
        if(bonds.equals("yes")){
            adapter.addFrag(new Fragment_Bonds(),getString(R.string.bonds));}
        if(etfs.equals("yes")){
            adapter.addFrag(new Fragment_Etfs(),getString(R.string.etf));}
        if(ipos.equals("yes")){
            adapter.addFrag(new Fragment_Ipos(),getString(R.string.ipo));}
        if(stockwisdom.equals("yes")){
            adapter.addFrag(new Fragment_Stock_Wisdom(),getString(R.string.stock_wisdom));}

        //adapter.addFrag(new Fragment_stockVScrypto(),getString(R.string.compare));
        viewPager.setAdapter(adapter);
    }

    private void setupChosenViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_Analysis(), getString(R.string.action_analysis));
        adapter.addFrag(new Fragment_News(), getString(R.string.title_news));
        adapter.addFrag(new Fragment_Video(), getString(R.string.title_video));
        adapter.addFrag(new Fragment_Exchanges(), getString(R.string.title_exchanges));
        adapter.addFrag(new Fragment_StockTwits(),getString(R.string.stocktwits));
        viewPager.setAdapter(adapter);
    }

    public void setJSON_INFO() {
//General Method for reading JSON file in Assets

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

    private void reloadAllData() {
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
        LayoutInflater i = (LayoutInflater) Activity_Main.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = i.inflate(R.layout.searchbar, null);
        toolbar = findViewById(R.id.toolbar);
        if(toolbar.getVisibility()==View.VISIBLE){
            toolbar.removeAllViews();
            toolbar.setVisibility(View.GONE);
            return;
        }else {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.addView(v);
        }
        ad = new ArrayAdapter(Activity_Main.this, android.R.layout.simple_dropdown_item_1line, searchview_arraylist);
        AutoCompleteTextView chosen_searchView_item = v.findViewById(R.id.searchtool);
        chosen_searchView_item.setAdapter(ad);


        chosen_searchView_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                graph_change.clear();
                exchange_list.clear();
                aequity_exchanges.clear();
                stocktwits_feedItems.clear();
                graph_date.clear();
                graph_high.clear();
                graph_volume.clear();
                feedItems.clear();
                String[] split_marketinfo = ad.getItem(position).toString().split("  ");
                ap_info.setMarketSymbol(split_marketinfo[0]);
                ap_info.setMarketName(split_marketinfo[1]);
                ap_info.setMarketType(split_marketinfo[2]);
                toolbar.removeView(v);
                chosen_searchView_item.onEditorAction(EditorInfo.IME_ACTION_DONE);
                new setAsyncChosenData(Activity_Main.this).execute();
                toolbar.setVisibility(View.GONE);

            }
        });
    }

    public void getCrypto_Kings() {
        long startTime = System.nanoTime();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                    btc_market_cap_change =crypto_kings_changelist.get(0)+"%";
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("An Error occured while making the request");
            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    public void getChosenCryptoInfo(){

        long startTime = System.nanoTime();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String newString =ap_info.getMarketName();
        System.out.println("1 -"+newString);
        if(newString.contains(" ")){
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
                                ap_info.setMarketSupply(y.substring(0, 5) + "M");
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
                System.out.println("Something ahppened An Error occured while making the request "+error);
            }
        });
        requestQueue.add(jsonArrayRequest);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("CRYPTO TIME IS " + duration / 1000000000 + " seconds");

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
                System.out.println("An Error occured while making the ST request");
            }
        });
        requestQueue.add(jsonObjectRequest);




    }

    public void getStockTwitsData(){
        String market_symbol=ap_info.getMarketSymbol();
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
                System.out.println("An Error occured while making the request");
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void getSaved_stock_points(){
        ArrayList aaa = co.getName();
        ArrayList a = co.getSymbol();
        ArrayList aa = co.getType();
        ArrayList b = co.getstockSymbol();
        //String apikey ="XBA42BUC2B6U6G5C";
        for(int c=0;c<b.size();c++) {
            Document cap = null;
            if (aa.get(c).equals("Stock")) {
                try {
                    cap = Jsoup.connect("https://finance.yahoo.com/quote/"+b.get(c)).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Element test = cap.select("div[data-reactid='34']").first().select("span").get(1);
                String foofoo =test.text().toString().replace("(","").replace(")","");
                String[] foo = foofoo.split(" ");
                String f =foo[1];
                current_percentage_change.add(f);
            }

                else{ ArrayList d = co.getcryptoName();
                for(int i=0;i<d.size();i++) {
                    Document caps = null;
                    String g = String.valueOf(d.get(i));
                    if (g.contains(" ")) {
                        g = g.replace(" ", "-");
                    }
                    try {
                        caps = Jsoup.connect("https://coinmarketcap.com/currencies/" + g).timeout(10 * 10000).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Elements as = caps.select("div>span");
                    String f = as.get(4).text();
                    f = f.replaceAll("\\(", "").replaceAll("\\)", "");
                    current_percentage_change.add(f);

                }}

        }


co.close();
    }

    public void getAdded_stock_point(){
        ArrayList aaa = co.getName();
        ArrayList a = co.getSymbol();
        ArrayList aa = co.getType();
        ArrayList b = co.getstockSymbol();
        //String apikey ="XBA42BUC2B6U6G5C";
if (aa.size()>0) {
    Document cap = null;
    if (aa.get(aa.size() - 1).equals("Stock")) {
        try {
            cap = Jsoup.connect("https://finance.yahoo.com/quote/" + b.get(b.size() - 1)).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element test = cap.select("div[data-reactid='34']").first().select("span").get(1);
        String foofoo = test.text().toString().replace("(", "").replace(")", "");
        String[] foo = foofoo.split(" ");
        String f = foo[1];
        current_percentage_change.add(f);
    } else {
        ArrayList d = co.getcryptoName();

        Document caps = null;
        String g = String.valueOf(d.get(d.size() - 1));
        if (g.contains(" ")) {
            g = g.replace(" ", "-");
        }
        try {
            caps = Jsoup.connect("https://coinmarketcap.com/currencies/" + g).timeout(10 * 10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements as = caps.select("div>span");
        String f = as.get(4).text();
        f = f.replaceAll("\\(", "").replaceAll("\\)", "");
        current_percentage_change.add(f);

    }


}

        co.close();
    }

    public static void get_coinmarketcap_exchange_listing() {
        Document doc = null;
        String name = ap_info.getMarketName();
        System.out.println("THIS IS THE MARKET NAME "+name);
        if (name.contains(" ")){
            name =name.replace(" ","-");
        }

        try {
            doc = Jsoup.connect("https://coinmarketcap.com/currencies/"+name+"/#markets").timeout(10 * 1000).get();
            Element tb = doc.getElementById("markets-table");
            Elements rows = tb.select("tr");

            for(int i =0; i<rows.size();i++){
                Element row = rows.get(i);
                Elements cols = row.select("td");
                for(int z =0; z<cols.size();z++) {
                    String line = cols.get(1).text();
                    if(!exchange_list.contains(line))
                        exchange_list.add(line);
                }
            }
            System.out.println("EXCHANGE LIST"+exchange_list);
        } catch (HttpStatusException x){
            System.out.println("There is no information about "+name +", so now we try world coinindex");
            try {
                doc = Jsoup.connect("https://www.worldcoinindex.com/coin/"+name).timeout(10 * 1000).get();
                String s = doc.getElementsByClass("mob-exchange exchange").text();
                List<String> myList = new ArrayList<String>(Arrays.asList(s.split(" ")));
                for(int i=0; i<myList.size();i++){
                    exchange_list.add(myList.get(i));
                    //System.out.println(i+" "+exchange_list.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        //System.out.println("Common element : "+(aequity_exchanges));




    }

    public static void get_crypto_exchange_info() {

        crypto_exchange_name.add("Binance");
        crypto_exchange_name.add("Hbus");
        crypto_exchange_name.add("Coinbase");
        crypto_exchange_name.add("Cryptopia");;
        crypto_exchange_name.add("CryptoBridge");
        crypto_exchange_name.add("HitBTC");

        crypto_exchange_url.add("https://www.binance.com/?ref=35795495");
        //Referral
        crypto_exchange_url.add("https://www.hbus.com/invite/inviteRank?invite_code=kgd23");
        //Referral
        crypto_exchange_url.add("https://www.coinbase.com/join/5a2cc6b6f3b80300ef643aa4");
        //Referral
        crypto_exchange_url.add("https://www.cryptopia.co.nz/Register?referrer=juliansmulian");
        //Referral
        crypto_exchange_url.add("https://crypto-bridge.org/");

        crypto_exchange_url.add("https://hitbtc.com/");


        crypto_exchange_image.add("R.drawable.exchange_crypto_binance");
        crypto_exchange_image.add("R.drawable.exchange_crypto_hbus");
        crypto_exchange_image.add("R.drawable.exchange_crypto_coinbase");
        crypto_exchange_image.add("R.drawable.exchange_crypto_cryptopia");
        crypto_exchange_image.add("R.drawable.exchange_crypto_cryptobridge");
        crypto_exchange_image.add("R.drawable.exchange_crypto_hitbtc");



    }

    public static void get_stock_exchange_info(){
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



    }

    public static void do_graph_change() {
        System.out.println("BANANAS"+graph_high);
        for (int i = 0; i < graph_high.size(); i++) {
                   Double a= new Double(graph_high.get(i).toString().replace(",",""));

            if (i > 0) {
                int z = i - 1;
                double b = new Double(graph_high.get(z).toString().replace(",",""));
                double c = ((a - b) / a) * 100;
                DecimalFormat numberFormat = new DecimalFormat("#0.00");
                String add =numberFormat.format(c).replace("-","");
                graph_change.add(add);

            }
        }


    }

    private void check_if_first_download() {


        int currentVersionCode = BuildConfig.VERSION_CODE;
        //SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);
       // if (currentVersionCode == savedVersionCode) {
            starterup();
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
                     System.out.println("THIS IS MASTERNODE VALUE "+Masternode);
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

                 starterup();
                 return;
             }});




        } else if (currentVersionCode > savedVersionCode) {
            starterup();
            return;
        }

        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
     */

    }



    private void check_to_show_ad(){
        if (fullScreen.equalsIgnoreCase("go")) {
            // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713


            MobileAds.initialize(this, "ca-app-pub-6566728316210720/4471280326");

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();

            }
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {

                }
            });
        }

    }
}