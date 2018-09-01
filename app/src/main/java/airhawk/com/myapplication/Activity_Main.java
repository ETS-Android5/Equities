package airhawk.com.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import java.util.Date;
import java.util.Iterator;

import static airhawk.com.myapplication.Constructor_App_Variables.*;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_changelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_marketcaplist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_namelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_symbolist;

public class Activity_Main extends AppCompatActivity {
    Integer count =1;
    RelativeLayout progresslayout;
    TextView txt;
    final int[] ICONS = new int[]{
            R.drawable.up,
            R.drawable.down,
            R.drawable.news,
            R.drawable.kings,
            R.drawable.chart};
    int[] names = new int[]{R.string.leaders, R.string.losers, R.string.news, R.string.market_kings};
    ImageView search_button;
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
    RecyclerView recyclerView;
    Database_Local_Aequities ld = new Database_Local_Aequities(Activity_Main.this);
    private static final String TAG = "ActivityMain";
    static Boolean async_analysis_page = false;
    static Constructor_App_Variables ap_info = new Constructor_App_Variables();
    Database_Local_Aequities check_saved = new Database_Local_Aequities(Activity_Main.this);

    FrameLayout fu;
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
            tabs.getTabAt(3).setIcon(ICONS[3]);
            tabs.getTabAt(4).setIcon(ICONS[4]);
        }else{
            tabs.getTabAt(2).setIcon(android.R.drawable.btn_star_big_on);
            tabs.getTabAt(3).setIcon(ICONS[2]);
            tabs.getTabAt(4).setIcon(ICONS[3]);
            tabs.getTabAt(5).setIcon(ICONS[4]);}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
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
            mainbar = activity.findViewById(R.id.mainbar);
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
            System.out.println("CHSOEN TYPE EQUALS "+ap_info.getMarketType());
            if (ap_info.getMarketType().equals("Crypto")||(ap_info.getMarketType().equals("Cryptocurrency"))) {
                activity.getChosenCryptoInfo();
                activity.get_crypto_points();
            }
//            getSupportActionBar().setDisplayShowTitleEnabled(true);
            if (async_analysis_page) {
                reloadAllData();
                activity.getStockTwitsData();
                Service_Chosen_Aequity shoe = new Service_Chosen_Aequity();
                shoe.main();
                System.out.println("ASYNC HAS BEEN CALLED PREVIOUSLY");
            } else {
                activity.getStockTwitsData();
                Service_Chosen_Aequity shoe = new Service_Chosen_Aequity();
                shoe.main();
                System.out.println("ASYNC HAS NOT BEEN CALLED");

                async_analysis_page = true;


            }
            return "task finished";
        }

        @Override
        protected void onPostExecute(String result) {

            // get a reference to the activity if it is still there
            Activity_Main activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

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
            tabs.getTabAt(4).setIcon(ICONS[4]);
        }else{
            tabs.getTabAt(2).setIcon(android.R.drawable.btn_star_big_on);
            tabs.getTabAt(3).setIcon(ICONS[2]);
            tabs.getTabAt(4).setIcon(ICONS[3]);
            tabs.getTabAt(5).setIcon(ICONS[4]);}


        recyclerView = findViewById(R.id.item_list);

        //assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new Adapter_Main_Markets());


    }

    private void setupMainViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_Winners(), getString(R.string.leaders));
        adapter.addFrag(new Fragment_Losers(), getString(R.string.losers));
        if (check_saved.getName().isEmpty()){}else{
        adapter.addFrag(new Fragment_Saved(), getString(R.string.saved));}
        adapter.addFrag(new Fragment_App_News(), getString(R.string.news));
        adapter.addFrag(new Fragment_Market_Kings(), getString(R.string.market_kings));
        adapter.addFrag(new Fragment_stockVScrypto(),getString(R.string.compare));
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

    public void Launch_Chosen_Progress(){
        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
                mainbar = findViewById(R.id.mainbar);
                mainbar.setIndeterminate(true);
                mainbar.setVisibility(View.VISIBLE);
                pager = findViewById(R.id.viewpager);
                pager.setVisibility(View.GONE);
            }

            public void onFinish() {
                setMarketPage();
            }
        }.start();
    }

    public void Launch_Main_Progress(){
        ProgressBar progress;
        setContentView(R.layout.splash);
        progress = (ProgressBar) findViewById(R.id.splash_bar);
        progress.setVisibility(View.VISIBLE);
        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
                Service_Main_Aequities cst = new Service_Main_Aequities();
                cst.main();
                //Service_Saved_Aequity csa =new Service_Saved_Aequity(context);
                //csa.main();
            }

            public void onFinish() {
                setMarketPage();
            }
        }.start();
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

    public void setMarketPage() {
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
            Service_Chosen_Aequity shoe = new Service_Chosen_Aequity();
            shoe.main();
            System.out.println("ASYNC HAS BEEN CALLED PREVIOUSLY");
        } else {
            getStockTwitsData();
            Service_Chosen_Aequity shoe = new Service_Chosen_Aequity();
            shoe.main();
            System.out.println("ASYNC HAS NOT BEEN CALLED");

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
    //Get's updated data from Firebase about new aequities
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
                    btc_market_cap_change =(String)crypto_kings_changelist.get(0);
                    System.out.println("This is the information "+btc_market_cap_amount+" "+btc_market_cap_change);
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
        System.out.println(newString);
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
                                ap_info.setMarketSupply(y.substring(0, 5) + "B");
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
        System.out.println("SYMBO NAME "+f);
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
        //     System.out.println("VOLUME " + graph_volume.get(counter) + " NUMBER " + graph_high.get(counter) + " DATE " + graph_date.get(counter));

        //}


    }

    public void getStockTwitsData(){
        String market_symbol=ap_info.getMarketSymbol();
        Constructor_Stock_Twits cst = new Constructor_Stock_Twits();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://api.stocktwits.com/api/2/streams/symbol/"+market_symbol+".json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject JObject = response.getJSONObject("messages");
                    System.out.println("Messages "+JObject.length());
                    JSONObject user_info = null;
                    String message_time=null;
                    String message=null;
                    for (int i = 0; i < JObject.length(); i++) {
                        user_info = (JSONObject) JObject.get("user");

                        message_time = (String) JObject.get("created_at");
                        message = (String) JObject.get("body");

                        Iterator x = user_info.keys();

                        cst.setMessage_time(message_time);
                        cst.setUser_name("" + user_info.get("username"));
                        String url = user_info.get("avatar_url_ssl").toString();
                        cst.setUser_image_url(url);
                        cst.setMessage(message);
                    }
                    stocktwits_feedItems.add(cst);





                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println();
                System.out.println("THIS IS THE BIG MESSAGE "+cst.getMessage()+" "+ cst.getMessage_time()+" "+cst.getUser_name()+" "+cst.getUser_image_url());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("An Error occured while making the ST request");
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}