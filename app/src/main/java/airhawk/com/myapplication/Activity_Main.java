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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static airhawk.com.myapplication.Constructor_App_Variables.*;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_changelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_marketcaplist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_namelist;
import static airhawk.com.myapplication.Service_Main_Aequities.crypto_kings_symbolist;

public class Activity_Main extends AppCompatActivity {
    final int[] ICONS = new int[]{
            R.drawable.up,
            R.drawable.down,
            R.drawable.news,
            R.drawable.kings};
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
        setMainPage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDATAMain();


    }

    private void setDATAMain() {

        ProgressBar progress;
        setContentView(R.layout.splash);
        progress = (ProgressBar) findViewById(R.id.splash_bar);
        new AsyncTask<Void, Void, Void>() {
            long startTime;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                startTime = System.nanoTime();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progress.setVisibility(View.GONE);
                setMainPage();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("SERVICE MAIN TIME IS " + duration / 1000000000 + " seconds");
            }

            @Override
            protected Void doInBackground(Void... params) {
                Service_Main_Aequities cst = new Service_Main_Aequities();
                cst.main();
                Service_Saved_Aequity csa =new Service_Saved_Aequity(context);
                csa.main();
                return null;
            }
        }.execute();

    }

    private void setMainPage() {
        setContentView(R.layout.activity_main);
        setJSON_INFO();
        getCrypto_Kings();
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
        }else{
            tabs.getTabAt(2).setIcon(android.R.drawable.btn_star_big_on);
            tabs.getTabAt(3).setIcon(ICONS[2]);
            tabs.getTabAt(4).setIcon(ICONS[3]);}


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

    public void Launch_Progress(){
        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
                mainbar = findViewById(R.id.mainbar);
                mainbar.setIndeterminate(true);
                mainbar.setVisibility(View.VISIBLE);
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
        mainbar.setVisibility(View.VISIBLE);
    }

    public void setMarketPage() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (ap_info.getMarketType().equals("Cryptocurrency")) {
            getChosenCryptoInfo();
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
                Launch_Progress();
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
                            crypto_kings_marketcaplist.add(market_cap);
                            String mc =jzO.getString("percent_change_24h");
                            crypto_kings_changelist.add(mc);
                        }




                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                btc_market_cap_amount =(String) crypto_kings_marketcaplist.get(0);
                btc_market_cap_change =(String)crypto_kings_changelist.get(0);
                System.out.println("This is the information "+btc_market_cap_amount+" "+btc_market_cap_change);
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

        final String url = "https://api.coinmarketcap.com/v1/ticker/"+ap_info.getMarketName();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    JSONObject heroObject = null;
                    try {
                        heroObject = response.getJSONObject(i);
                        ap_info.setMarketSymbol(heroObject.getString("symbol"));
                        ap_info.setCurrent_Aequity_Price(heroObject.getString("price_usd"));
                        //volume_24 =heroObject.getString("24h_volume_usd");
                        ap_info.setMarketSupply(heroObject.getString("total_supply"));
                        ap_info.setCurrent_Aequity_Price_Change(heroObject.getString("percent_change_24h"));
                        ap_info.setMarketCap(heroObject.getString("market_cap_usd"));
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

    private void getStockTwitsData(){
        String market_symbol=ap_info.getMarketSymbol();
        Constructor_Stock_Twits cst = new Constructor_Stock_Twits();

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

                        cst.setMessage_time(message_time);
                        cst.setUser_name(""+user_info.get("username"));
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