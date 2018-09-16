package airhawk.com.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import static airhawk.com.myapplication.Constructor_App_Variables.*;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_changelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_marketcaplist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_namelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_symbolist;

public class Activity_Main extends AppCompatActivity {
    Integer count =1;
    boolean forward;    RequestQueue requestQueue;
    ArrayList temp =new ArrayList();
    Database_Local_Aequities co =new Database_Local_Aequities(this);
    Constructor_App_Variables cav =new Constructor_App_Variables();
    RelativeLayout progresslayout;
    TextView txt;
    final int[] ICONS = new int[]{
            R.drawable.up,
            R.drawable.down,
            R.drawable.news,
            R.drawable.kings};
    int[] names = new int[]{R.string.leaders, R.string.losers, R.string.news, R.string.market_kings};
    ImageView search_button;
    ImageView imageView;
    static Element price = null;
    protected ArrayAdapter<String> ad;
    private Toolbar toolbar;
    ProgressBar mainbar;
    public static ArrayList<String> searchview_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_symbol_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_name_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_type_arraylist = new ArrayList<>();
    public ViewPager pager, market_pager;
    public TabLayout tabs;
    ProgressBar progress;
    static RecyclerView recyclerView;
    Database_Local_Aequities ld = new Database_Local_Aequities(Activity_Main.this);
    private static final String TAG = "ActivityMain";
    static Boolean async_analysis_page = false;
    static Constructor_App_Variables ap_info = new Constructor_App_Variables();
    Database_Local_Aequities check_saved = new Database_Local_Aequities(Activity_Main.this);
    Animation animLinear,centerLinear;
    FrameLayout fu,frameLayout;
    Context context =this;



    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

    public void onBackPressed() {
       exchange_list.clear();
        aequity_exchanges.clear();
        stocktwits_feedItems.clear();
        graph_date.clear();
        graph_high.clear();
        graph_volume.clear();
        new setAsyncDataMain(this).cancel(true);
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
            tabs.getTabAt(3).setIcon(ICONS[3]);;
        }else{
            tabs.getTabAt(2).setIcon(android.R.drawable.btn_star_big_on);
            tabs.getTabAt(3).setIcon(ICONS[2]);
            tabs.getTabAt(4).setIcon(ICONS[3]);}

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_crypto_exchange_info();
        get_stock_exchange_info();
        setContentView(R.layout.splash);
        centerLinear = AnimationUtils.loadAnimation(this, R.anim.center);
        imageView =findViewById(R.id.imageView);
        //imageView.startAnimation(centerLinear);
        count =1;
        progress= (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(10);
        progress.setProgress(0);
        txt = (TextView) findViewById(R.id.output);

        new setAsyncDataMain(this).execute(10);

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
        }

        @Override
        protected String doInBackground(Integer... params) {
            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(100);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            getCrypto_Kings();
            getSaved_stock_points();
            //get_main_graph();

            Service_Main_Aequities cst = new Service_Main_Aequities();
            cst.main();

            //Service_Saved_Aequity csa =new Service_Saved_Aequity(context);
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
        @Override
        protected void onProgressUpdate(Integer... values) {
            txt.setText(values[0]+"0 %");
            progress.setProgress(values[0]);
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

            startTime = System.nanoTime();
            Activity_Main activity = activityReference.get();
            ProgressBar mainbar = activity.findViewById(R.id.mainbar);
            mainbar.setIndeterminate(true);
            mainbar.setVisibility(View.VISIBLE);
            pager = activity.findViewById(R.id.viewpager);
            pager.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(Void... params) {
            if(isCancelled())
            {
                System.out.println("Async Cancelled");return null;}
            Activity_Main activity = activityReference.get();


//            getSupportActionBar().setDisplayShowTitleEnabled(true);
            if (async_analysis_page) {
                reloadAllData();
                if (ap_info.getMarketType().equals("Crypto")||(ap_info.getMarketType().equals("Cryptocurrency"))) {
                    activity.get_crypto_points();
                    activity.getChosenCryptoInfo();
                    activity.getStockTwitsData();
                    activity.get_coinmarketcap_exchange_listing();
                    Service_Chosen_Aequity shoe = new Service_Chosen_Aequity();
                    shoe.main();
                    System.out.println("ASYNC C HAS BEEN CALLED PREVIOUSLY");
                }else{
                    activity.get_stock_points();
                    activity.get_current_stock_info();
                    activity.getStockTwitsData();
                    Service_Chosen_Aequity shoe = new Service_Chosen_Aequity();
                    System.out.println("ASYNC S HAS BEEN CALLED PREVIOUSLY");
                    shoe.main();
                }



            } else {
                if (ap_info.getMarketType().equals("Crypto")||(ap_info.getMarketType().equals("Cryptocurrency"))) {
                    activity.get_crypto_points();
                    activity.getChosenCryptoInfo();
                    activity.getStockTwitsData();
                    activity.get_coinmarketcap_exchange_listing();
                    Service_Chosen_Aequity shoe = new Service_Chosen_Aequity();
                    shoe.main();
                    System.out.println("ASYNC* HAS NOT BEEN CALLED PREVIOUSLY");
                }else{
                    activity.get_stock_points();
                    activity.get_current_stock_info();
                    activity.getStockTwitsData();
                    Service_Chosen_Aequity shoe = new Service_Chosen_Aequity();
                    System.out.println("ASYNC* HAS NOT BEEN CALLED PREVIOUSLY");
                    shoe.main();
                }

                async_analysis_page = true;


            }
            return "task finished";
        }



        @Override
        protected void onPostExecute(String result) {


            // get a reference to the activity if it is still there
            Activity_Main activity = activityReference.get();
            String a=ap_info.getCurrent_Aequity_Price();
            if (activity == null || activity.isFinishing()&&graph_high.size()>0&&requestQueue !=null&& forward) return;

            pager = activity.findViewById(R.id.viewpager);
            pager.setVisibility(View.GONE);
            market_pager = activity.findViewById(R.id.market_pager);
            market_pager.setVisibility(View.VISIBLE);

            tabs = activity.findViewById(R.id.tabs);
            activity.setupChosenViewPager(market_pager);
            tabs.setupWithViewPager(market_pager);
            tabs.getTabAt(0).setIcon(R.drawable.info);
            tabs.getTabAt(1).setIcon(R.drawable.news);
            tabs.getTabAt(2).setIcon(R.drawable.yt);
            tabs.getTabAt(3).setIcon(R.drawable.exchange);
            tabs.getTabAt(4).setIcon(R.drawable.socialmedia);
            mainbar = activity.findViewById(R.id.mainbar);
            mainbar.setIndeterminate(false);
            mainbar.setVisibility(View.GONE);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("SERVICE MAIN TIME IS " + duration / 1000000000 + " seconds");

        }


    }

    private void setMainPage() {
        setContentView(R.layout.activity_main);
        setJSON_INFO();
        frameLayout=findViewById(R.id.frameLayout);
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
            tabs.getTabAt(3).setIcon(ICONS[3]);;
        }else{
            tabs.getTabAt(2).setIcon(android.R.drawable.btn_star_big_on);
            tabs.getTabAt(3).setIcon(ICONS[2]);
            tabs.getTabAt(4).setIcon(ICONS[3]);}


        recyclerView = findViewById(R.id.item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new Adapter_Main_Markets());
        //recyclerView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.linear));
    }

    private void setupMainViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_Winners(), getString(R.string.leaders));
        adapter.addFrag(new Fragment_Losers(), getString(R.string.losers));
        if (check_saved.getName().isEmpty()){}else{
            adapter.addFrag(new Fragment_Saved(), getString(R.string.saved));}
        adapter.addFrag(new Fragment_App_News(), getString(R.string.news));
        adapter.addFrag(new Fragment_Market_Kings(), getString(R.string.market_kings));
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

    public void Update_Saved_Data(){

        toolbar = findViewById(R.id.toolbar);
        fu=findViewById(R.id.frameLayout);
        fu.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.VISIBLE);
        market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.GONE);
        TabLayout tabs = findViewById(R.id.tabs);
        setupMainViewPager(pager);
        tabs.setupWithViewPager(pager);
        tabs.getTabAt(0).setIcon(ICONS[0]);
        tabs.getTabAt(1).setIcon(ICONS[1]);
        Database_Local_Aequities check_saved = new Database_Local_Aequities(Activity_Main.this);
        if (check_saved.getName().isEmpty()){}else{
            tabs.getTabAt(2).setIcon(ICONS[2]);}
        tabs.getTabAt(3).setIcon(ICONS[3]);
        tabs.getTabAt(4).setIcon(ICONS[4]);
        tabs.getTabAt(5).setIcon(ICONS[5]);
        mainbar.setVisibility(View.VISIBLE);
    }

    public void setSavedMarketPage() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (ap_info.getMarketType().equals("Cryptocurrency")) {
            getChosenCryptoInfo();
            get_crypto_points();
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if (async_analysis_page) {
            reloadAllData();
            getStockTwitsData();

            get_stock_points();
            Service_Chosen_Aequity shoe = new Service_Chosen_Aequity();
            shoe.main();
            System.out.println("ASYNC! HAS BEEN CALLED PREVIOUSLY");
        } else {
            getStockTwitsData();
            get_stock_points();
            Service_Chosen_Aequity shoe = new Service_Chosen_Aequity();
            shoe.main();
            System.out.println("ASYNC! HAS NOT BEEN CALLED");

            async_analysis_page = true;


        }
        pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.GONE);
        market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.VISIBLE);

        TabLayout tabs = findViewById(R.id.tabs);
        setupChosenViewPager(market_pager);
        tabs.setupWithViewPager(market_pager);
        tabs.getTabAt(0).setIcon(R.drawable.info);
        tabs.getTabAt(1).setIcon(R.drawable.news);
        tabs.getTabAt(2).setIcon(R.drawable.yt);
        tabs.getTabAt(3).setIcon(R.drawable.exchange);
        tabs.getTabAt(4).setIcon(R.drawable.socialmedia);
        mainbar = findViewById(R.id.mainbar);
        mainbar.setIndeterminate(false);
        mainbar.setVisibility(View.GONE);
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
    }//Get's updated data from Firebase about new aequities

    private void UpdateData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("ALL");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Constructor_App_Variables team = new Constructor_App_Variables();
                    team.setMarketSymbol(String.valueOf(childDataSnapshot.child("0").getValue()));
                    searchview_arraylist.add(String.valueOf(team));
                    ld = new Database_Local_Aequities(Activity_Main.this);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        userRef.addValueEventListener(postListener);
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
                        crypto_kings_namelist.add(name);
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
                            if (l<=12){p= String.valueOf(d/1000000);
                                crypto_kings_marketcaplist.add(p.substring(0,5)+" M");}
                            if (l>12){p= String.valueOf(d/1000000000);
                                crypto_kings_marketcaplist.add(p.substring(0,5)+" B");}
                            if (l>15){p= String.valueOf(d/t);
                                crypto_kings_marketcaplist.add(p.substring(0,5)+" T");}
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

    public void get_crypto_points() {
        long startTime = System.nanoTime();
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date begindate = new Date();
        String f = ap_info.getMarketName();
        if (f.contains(" ")){
            f= f.replaceAll(" ","-");}
        String url = "https://coinmarketcap.com/currencies/" + f + "/historical-data/?start=20000101&end=" + sdf.format(begindate);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String response_utf8 = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8");
                            Document doc = Jsoup.parse(response_utf8);
                            Element price = doc.getElementById("quote_price");
                            String v = price.text();
                            Elements divs = doc.select("table");
                            for (Element tz : divs) {
                                Elements tds = tz.select("td");
                                Elements s = tz.getElementsByClass("text-right");
                                for (Element ss : s) {
                                    Elements p = ss.select("td[data-format-fiat]");
                                    String g = p.text();
                                    String[] splited = g.split("\\s+");
                                    if (v != null && !g.isEmpty()) {
                                        graph_high.add(splited[3]);
                                        graph_low.add(splited[2]);
                                    }
                                    Elements pn = ss.select("td[data-format-market-cap]");
                                    String vp = pn.text();
                                    String[] split = vp.split("\\s+");
                                    if (vp != null && !vp.isEmpty()) {
                                        graph_volume.add(split[0]);
                                        graph_market_cap.add(split[1]);
                                    }
                                }
                                for (Element bb : tds) {
                                    Elements gdate = bb.getElementsByClass("text-left");
                                    String result0 = gdate.text();
                                    if (result0 != null && !result0.isEmpty()) {
                                        graph_date.add(String.valueOf(result0));
                                    }
                                }
                            }_AllDays = graph_high;

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("log2=", error.toString());
                        //requestQueue.stop();
                    }
                });
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
        requestQueue.start();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("CRYPTO POINTS TIME IS " + duration / 1000000000 + " seconds");






        //for (int counter = 0; counter < numbers.size(); counter++) {
        //     println("VOLUME " + graph_volume.get(counter) + " NUMBER " + graph_high.get(counter) + " DATE " + graph_date.get(counter));

        //}


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
                    StringBuilder formattedResult = new StringBuilder();
                    JSONArray responseJSONArray = response.getJSONArray("messages");
                    for (int i = 0; i < responseJSONArray.length(); i++) {
                        JSONObject user_info = (JSONObject) responseJSONArray.getJSONObject(i).get("user");
                        String message_time = (String) responseJSONArray.getJSONObject(i).get("created_at");
                        String message = (String) responseJSONArray.getJSONObject(i).get("body");
                        Iterator x = user_info.keys();
                        Constructor_App_Variables.Smessage_time.add(message_time);
                        Constructor_App_Variables.Suser_name.add("" + user_info.get("username"));
                        String url = user_info.get("avatar_url_ssl").toString();
                        Constructor_App_Variables.Simg_url.add(url);
                        Constructor_App_Variables.Smessage.add(message);

                        JSONArray jsonArray =new JSONArray();
                        while(x.hasNext()){
                            String key = (String) x.next();
                            jsonArray.put(user_info.get(key));

                        }




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

        ArrayList a = co.getSymbol();
        ArrayList aa = co.getType();
        ArrayList b = co.getstockSymbol();
        String apikey ="XBA42BUC2B6U6G5C";

        for(int i=0;i<a.size();i++){
            if(aa.get(i).equals("Stock")) {
                String url ="https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+b.get(i)+"&apikey="+apikey;

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            // get Time
                            JSONObject time = jsonObject.getJSONObject("Global Quote");
                            //Iterator<String> iterator = time.keys();

                            //String price = time.getString("05. price");
                            String change= time.getString("10. change percent");
                            temp.add(change);
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
            cav.setCpr(temp);

    }

        ArrayList d = co.getcryptoName();
        for(int i=0;i<d.size();i++) {
            Document cap = null;
            String g = String.valueOf(d.get(i));
            if (g.contains(" ")) {
                g = g.replace(" ", "-");
            }
            try {
                cap = Jsoup.connect("https://coinmarketcap.com/currencies/" + g).timeout(10 * 10000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements as = cap.select("div>span");
            String f = as.get(4).text();
            f = f.replaceAll("\\(", "").replaceAll("\\)", "");
            temp.add(f);
        }
        }

    public void get_stock_points() {
        forward=false;
        String symbol =ap_info.getMarketSymbol();
        String apikey ="XBA42BUC2B6U6G5C";
        String url ="https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+symbol+"&outputsize=full&apikey="+apikey;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // your response
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    // get Time

                    JSONObject time = jsonObject.getJSONObject("Time Series (Daily)");
                    System.out.println("Response : "+time);
                    Iterator<String> iterator = time.keys();

                    while (iterator.hasNext()) {
                        String date = iterator.next().toString();
                        JSONObject dateJson = time.getJSONObject(date);
                        // get string
                        String close = dateJson.getString("4. close");
                        String volume =dateJson.getString("5. volume");
                        DecimalFormat numberFormat = new DecimalFormat("#.00");
                        double d =Double.parseDouble(close);
                        String fclose=numberFormat.format(d);

                        graph_date.add(date);
                        graph_volume.add(volume);
                        graph_high.add(fclose);

                    }
                    String a =ap_info.getCurrent_Aequity_Price();
                    if (graph_high.size()>0&&a!=null)
                    {
                        forward=true;
                        System.out.println("Graph high size is "+graph_high.size());
                    }
                    else{
                        forward=false;
                        System.out.println("Had to start over "+graph_high.size());


                    }
                    //Collections.reverse(graph_high);
                    //Collections.reverse(graph_volume);
                    //Collections.reverse(graph_date);
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
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);



        List<String> numbers = graph_high;
        Collections.reverse(numbers);
        _AllDays = numbers;

    }

    public void get_stock_daily_points(){
        String symbol =ap_info.getMarketSymbol();
        String apikey ="XBA42BUC2B6U6G5C";
        String url ="https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="+symbol+"&interval=5min&apikey="+apikey;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // your response
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    // get Time
                    JSONObject time = jsonObject.getJSONObject("Time Series (5min)");
                    Iterator<String> iterator = time.keys();

                    while (iterator.hasNext()) {
                        String date = iterator.next().toString();
                        JSONObject dateJson = time.getJSONObject(date);
                        // get string
                        String close = dateJson.getString("4. close");
                        String volume =dateJson.getString("5. volume");
                        DecimalFormat numberFormat = new DecimalFormat("#.00");
                        double d =Double.parseDouble(close);
                        String fclose=numberFormat.format(d);

                        graph_date_today.add(date);
                        graph_volume_today.add(volume);
                        graph_high_today.add(fclose);

                    }
                    //Collections.reverse(graph_high);
                    //Collections.reverse(graph_volume);
                    //Collections.reverse(graph_date);
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

    public void after_launch_crypto_listings(){
        exchange_text.add("BTC");
        exchange_text.add("ETH");
        exchange_text.add("BCH");
        exchange_text.add("LTC");

        Document doc = null;
        try {
            doc = Jsoup.connect("https://info.binance.com/en/all").timeout(10 * 1000).get();
        } catch (IOException e) {
            e.printStackTrace(); }
        Elements a = doc.getElementsByClass("name");
        Elements b = a.select("span.abbr");
        for (int i =0;i<b.size();i++) {
            binance_list.add(b.get(i).text());
            System.out.println(i+" "+b.get(i).text());
        }


    }

    public static void get_coinmarketcap_exchange_listing() {
        Document doc = null;
        String symbol = ap_info.getMarketName();

        try {
            doc = Jsoup.connect("https://coinmarketcap.com/currencies/"+symbol+"/#markets").timeout(10 * 1000).get();
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
            System.out.println("There is no information about "+symbol +", so now we try world coinindex");
            try {
                doc = Jsoup.connect("https://www.worldcoinindex.com/coin/"+symbol).timeout(10 * 1000).get();
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

        crypto_exchange_url.add("https://www.Binance.com/m-HomePage.html ");
        crypto_exchange_url.add("https://www.OKEx.com");
        crypto_exchange_url.add("https://www.Huobi.com");
        crypto_exchange_url.add("https://www.Bithumb.com");
        crypto_exchange_url.add("https://www.ZB.com");
        crypto_exchange_url.add("https://www.Bitfinex.com");
        crypto_exchange_url.add("https://www.HitBTC.com");
        crypto_exchange_url.add("https://www.Bit-Z.com");
        crypto_exchange_url.add("https://www.Bibox.com");
        crypto_exchange_url.add("https://www.Coinsuper.com");
        crypto_exchange_url.add("https://www.BCEX.com");
        crypto_exchange_url.add("https://www.LBank.com");
        crypto_exchange_url.add("https://www.DigiFinex.com");
        crypto_exchange_url.add("https://www.Upbit.com");
        crypto_exchange_url.add("https://pro.Coinbase.com");
        crypto_exchange_url.add("https://www.Simex.com");
        crypto_exchange_url.add("https://www.OEX.com");
        crypto_exchange_url.add("https://www.Kraken.com");
        crypto_exchange_url.add("https://www.Cryptonex.com");
        crypto_exchange_url.add("https://www.BitMart.com");
        crypto_exchange_url.add("https://www.Allcoin.com");
        crypto_exchange_url.add("https://www.IDAX.com");
        crypto_exchange_url.add("https://www.RightBTC.com");
        crypto_exchange_url.add("https://www.IDCM.com");
        crypto_exchange_url.add("https://www.YoBit.com");
        crypto_exchange_url.add("https://www.DragonEX.io");
        crypto_exchange_url.add("https://www.Gate.io");
        crypto_exchange_url.add("https://www.Fatbtc.com");
        crypto_exchange_url.add("https://www.Exrates.com");
        crypto_exchange_url.add("https://www.CoinsBank.com");
        crypto_exchange_url.add("https://www.BTCBOX.com");
        crypto_exchange_url.add("https://www.Kryptono.exchange");
        crypto_exchange_url.add("https://www.UEX.com");
        crypto_exchange_url.add("https://www.Bitlish.com");
        crypto_exchange_url.add("https://www.Sistemkoin.com");
        crypto_exchange_url.add("https://www.Bitstamp.com");
        crypto_exchange_url.add("https://www.Bitbank.com");
        crypto_exchange_url.add("https://www.CoinTiger.com");
        crypto_exchange_url.add("https://www.CoinBene.com");
        crypto_exchange_url.add("https://www.LATOKEN.com");
        crypto_exchange_url.add("https://www.Bitstamp.com");
        crypto_exchange_url.add("https://www.Bittrex.com");
        crypto_exchange_url.add("https://www.Poloniex.com");
        crypto_exchange_url.add("https://www.CoinEgg.com");
        crypto_exchange_url.add("https://www.Exmo.com");
        crypto_exchange_url.add("https://www.Hotbit.com");
        crypto_exchange_url.add("https://www.bitFlyer.com");
        crypto_exchange_url.add("https://www.B2BX.com");
        crypto_exchange_url.add("https://www.Rfinex.com");
        crypto_exchange_url.add("https://www.CPDAX.com");
        crypto_exchange_url.add("https://www.C2CX.com");
        crypto_exchange_url.add("https://www.Livecoin.com");
        crypto_exchange_url.add("https://www.BtcTrade.im");
        crypto_exchange_url.add("https://www.xBTCe.com");
        crypto_exchange_url.add("https://www.Coinone.com");
        crypto_exchange_url.add("https://www.Kucoin.com");
        crypto_exchange_url.add("https://www.BitBay.com");
        crypto_exchange_url.add("https://www.Gemini.com");
        crypto_exchange_url.add("https://www.Coinbe.net");
        crypto_exchange_url.add("https://www.InfinityCoin.exchange");
        crypto_exchange_url.add("https://www.Tradebytrade.com");
        crypto_exchange_url.add("https://www.BiteBTC.com");
        crypto_exchange_url.add("https://www.Coinsquare.com");
        crypto_exchange_url.add("https://www.HADAX.com");
        crypto_exchange_url.add("https://www.Coinroom.com");
        crypto_exchange_url.add("https://www.Mercatox.com");
        crypto_exchange_url.add("https://www.Coinhub.com");
        crypto_exchange_url.add("https://www.BigONE.com");
        crypto_exchange_url.add("https://www.BitShares.org");
        crypto_exchange_url.add("https://www.CEX.IO");
        crypto_exchange_url.add("https://www.ChaoEX.com");
        crypto_exchange_url.add("https://www.Bitsane.com");
        crypto_exchange_url.add("https://www.BitForex.com");
        crypto_exchange_url.add("https://www.LocalTrade.com");
        crypto_exchange_url.add("https://www.Bitinka.com");
        crypto_exchange_url.add("https://www.Liqui.io");
        crypto_exchange_url.add("https://www.Liquid.com");
        crypto_exchange_url.add("https://www.BTC-Alpha.com");
        crypto_exchange_url.add("https://www.Instantbitex.com");
        crypto_exchange_url.add("https://www.Cryptopia.com");
        crypto_exchange_url.add("https://www.GOPAX.com");
        crypto_exchange_url.add("https://www.Korbit.com");
        crypto_exchange_url.add("https://www.itBit.com");
        crypto_exchange_url.add("https://www.Indodax.com");
        crypto_exchange_url.add("https://www.Vebitcoin.com");
        crypto_exchange_url.add("https://www.Allbit.com");
        crypto_exchange_url.add("https://www.Indodax.com");
        crypto_exchange_url.add("https://www.BtcTurk.com");
        crypto_exchange_url.add("https://www.Ovis.com");
        crypto_exchange_url.add("https://www.BTCC.com");
        crypto_exchange_url.add("https://www.IDEX.market");
        crypto_exchange_url.add("https://www.Ethfinex.com");
        crypto_exchange_url.add("https://www.QuadrigaCX.com");
        crypto_exchange_url.add("https://www.CoinExchange.io");
        crypto_exchange_url.add("https://www.Tidex.com");
        crypto_exchange_url.add("https://www.https://www.negociecoins.com/br");
        crypto_exchange_url.add("https://www.CryptoBridge.com");
        crypto_exchange_url.add("https://www.LakeBTC.com");
        crypto_exchange_url.add("https://www.Bancor.network");
        crypto_exchange_url.add("https://www.QBTC.com");
        crypto_exchange_url.add("https://www.WEX.com");
        crypto_exchange_name.add("Binance");
        crypto_exchange_name.add("OKEx");
        crypto_exchange_name.add("Huobi");
        crypto_exchange_name.add("Bithumb");
        crypto_exchange_name.add("ZB.COM");
        crypto_exchange_name.add("Bitfinex");
        crypto_exchange_name.add("HitBTC");
        crypto_exchange_name.add("Bit-Z");
        crypto_exchange_name.add("Bibox");
        crypto_exchange_name.add("Coinsuper");
        crypto_exchange_name.add("BCEX");
        crypto_exchange_name.add("LBank");
        crypto_exchange_name.add("DigiFinex");
        crypto_exchange_name.add("Upbit");
        crypto_exchange_name.add("Coinbase Pro");
        crypto_exchange_name.add("Simex");
        crypto_exchange_name.add("OEX");
        crypto_exchange_name.add("Kraken");
        crypto_exchange_name.add("Cryptonex");
        crypto_exchange_name.add("BitMart");
        crypto_exchange_name.add("Allcoin");
        crypto_exchange_name.add("IDAX");
        crypto_exchange_name.add("RightBTC");
        crypto_exchange_name.add("IDCM");
        crypto_exchange_name.add("YoBit");
        crypto_exchange_name.add("DragonEX");
        crypto_exchange_name.add("Gate.io");
        crypto_exchange_name.add("Fatbtc");
        crypto_exchange_name.add("Exrates");
        crypto_exchange_name.add("CoinsBank");
        crypto_exchange_name.add("BTCBOX");
        crypto_exchange_name.add("Kryptono");
        crypto_exchange_name.add("UEX");
        crypto_exchange_name.add("Bitlish");
        crypto_exchange_name.add("Sistemkoin");
        crypto_exchange_name.add("Bitstamp");
        crypto_exchange_name.add("Bitbank");
        crypto_exchange_name.add("CoinTiger");
        crypto_exchange_name.add("CoinBene");
        crypto_exchange_name.add("LATOKEN");
        crypto_exchange_name.add("Bitstamp");
        crypto_exchange_name.add("Bittrex");
        crypto_exchange_name.add("Poloniex");
        crypto_exchange_name.add("CoinEgg");
        crypto_exchange_name.add("Exmo");
        crypto_exchange_name.add("Hotbit");
        crypto_exchange_name.add("B2BX");
        crypto_exchange_name.add("bitFlyer");
        crypto_exchange_name.add("Rfinex");
        crypto_exchange_name.add("CPDAX");
        crypto_exchange_name.add("C2CX");
        crypto_exchange_name.add("Livecoin");
        crypto_exchange_name.add("BtcTrade.im");
        crypto_exchange_name.add("xBTCe");
        crypto_exchange_name.add("Coinone");
        crypto_exchange_name.add("Kucoin");
        crypto_exchange_name.add("BitBay");
        crypto_exchange_name.add("Gemini");
        crypto_exchange_name.add("Coinbe");
        crypto_exchange_name.add("InfinityCoin Exchange");
        crypto_exchange_name.add("Trade By Trade");
        crypto_exchange_name.add("BiteBTC");
        crypto_exchange_name.add("Coinsquare");
        crypto_exchange_name.add("HADAX");
        crypto_exchange_name.add("Coinroom");
        crypto_exchange_name.add("Mercatox");
        crypto_exchange_name.add("Coinhub");
        crypto_exchange_name.add("BigONE");
        crypto_exchange_name.add("BitShares Exchange");
        crypto_exchange_name.add("CEX.IO");
        crypto_exchange_name.add("ChaoEX");
        crypto_exchange_name.add("Bitsane");
        crypto_exchange_name.add("BitForex");
        crypto_exchange_name.add("LocalTrade");
        crypto_exchange_name.add("Bitinka");
        crypto_exchange_name.add("Liqui");
        crypto_exchange_name.add("Liquid");
        crypto_exchange_name.add("BTC-Alpha");
        crypto_exchange_name.add("Instant Bitex");
        crypto_exchange_name.add("Cryptopia");
        crypto_exchange_name.add("GOPAX");
        crypto_exchange_name.add("Korbit");
        crypto_exchange_name.add("itBit");
        crypto_exchange_name.add("Indodax");
        crypto_exchange_name.add("Vebitcoin");
        crypto_exchange_name.add("Allbit");
        crypto_exchange_name.add("Coindeal");
        crypto_exchange_name.add("BtcTurk");
        crypto_exchange_name.add("Ovis");
        crypto_exchange_name.add("BTCC");
        crypto_exchange_name.add("IDEX");
        crypto_exchange_name.add("Ethfinex");
        crypto_exchange_name.add("QuadrigaCX");
        crypto_exchange_name.add("CoinExchange");
        crypto_exchange_name.add("Tidex");
        crypto_exchange_name.add("Negocie Coins");
        crypto_exchange_name.add("CryptoBridge");
        crypto_exchange_name.add("LakeBTC");
        crypto_exchange_name.add("Bancor Network");
        crypto_exchange_name.add("QBTC");
        crypto_exchange_name.add("WEX");



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

    }
}