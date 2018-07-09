package airhawk.com.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.List;

import static airhawk.com.myapplication.Constructor_App_Variables.feedItems;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;
import static airhawk.com.myapplication.Constructor_App_Variables.image_video_url;
import static airhawk.com.myapplication.Constructor_App_Variables.video_title;
import static airhawk.com.myapplication.Constructor_App_Variables.video_url;

public class Activity_Main extends AppCompatActivity {
    static Element price = null;
    protected ArrayAdapter<String> ad;
    private Toolbar toolbar;
    ProgressBar mainbar;
    public static ArrayList<String> searchview_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_symbol_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_name_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_type_arraylist = new ArrayList<>();
    public ViewPager pager, market_pager;
    private View recyclerView;
    Database_Local_Aequities ld = new Database_Local_Aequities(Activity_Main.this);
    private static final String TAG = "ActivityMain";
    static Boolean async_analysis_page = false;
    static Constructor_App_Variables ap_info = new Constructor_App_Variables();
    FrameLayout fu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_top, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                openSearchView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

    public void onBackPressed() {
        toolbar = findViewById(R.id.toolbar);
        fu=findViewById(R.id.frameLayout);
        fu.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.VISIBLE);
        market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.GONE);
        Adapter_Pager_Winners_Losers_Kings wlv_adapter = new Adapter_Pager_Winners_Losers_Kings(this, getSupportFragmentManager());
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
        tabs.setSelectedTabIndicatorHeight(0);
        //if (isSearchOpened) {
        //     return;
        // }

        //super.onBackPressed();
        mainbar.setVisibility(View.VISIBLE);
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

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progress.setVisibility(View.GONE);
                setMainPage();
            }

            @Override
            protected Void doInBackground(Void... params) {
                Executor_Winners_Losers_Kings cst = new Executor_Winners_Losers_Kings();
                cst.main();
                return null;
            }
        }.execute();

    }

    private void setMainPage() {
        setContentView(R.layout.activity_main);
        setJSON_INFO();
        toolbar = findViewById(R.id.toolbar);
        fu=findViewById(R.id.frameLayout);
        fu.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ViewPager pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.VISIBLE);
        ViewPager market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.GONE);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(getString(R.string.leaders)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.losers)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.volume)));
        Adapter_Pager_Winners_Losers_Kings wlv_adapter = new Adapter_Pager_Winners_Losers_Kings(this, getSupportFragmentManager());
        pager.setAdapter(wlv_adapter);
        tabs.setupWithViewPager(pager);
        recyclerView = findViewById(R.id.item_list);
        //assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
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

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new Adapter_Main_Markets());
    }

    public void Launch_Progress(){
        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {
                mainbar = findViewById(R.id.mainbar);
                mainbar.setIndeterminate(true);
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
        Adapter_Pager_Winners_Losers_Kings wlv_adapter = new Adapter_Pager_Winners_Losers_Kings(this, getSupportFragmentManager());
        TabLayout tabs = findViewById(R.id.tabs);
        pager.setAdapter(wlv_adapter);
        tabs.setupWithViewPager(pager);
        tabs.setSelectedTabIndicatorHeight(0);
        mainbar.setVisibility(View.VISIBLE);
    }

    public void setMarketPage() {

        toolbar = findViewById(R.id.toolbar);
        fu=findViewById(R.id.frameLayout);
        fu.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if (async_analysis_page) {
            reloadAllData();

            Executor_Chosen_Aequity shoe = new Executor_Chosen_Aequity();
            shoe.main();
            System.out.println("ASYNC HAS BEEN CALLED PREVIOUSLY");
        } else {

            Executor_Chosen_Aequity shoe = new Executor_Chosen_Aequity();
            shoe.main();
            System.out.println("ASYNC HAS NOT BEEN CALLED");

            async_analysis_page = true;


        }
        pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.GONE);
        market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.VISIBLE);
        Adapter_Chosen_Aequity maa_adapter = new Adapter_Chosen_Aequity(this, getSupportFragmentManager());
        market_pager.setAdapter(maa_adapter);
        Adapter_Pager_Winners_Losers_Kings wlv_adapter = new Adapter_Pager_Winners_Losers_Kings(this, getSupportFragmentManager());
        wlv_adapter.notifyDataSetChanged();
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.removeAllTabs();
        tabs.setupWithViewPager(market_pager);
        tabs.setSelectedTabIndicatorHeight(0);
        mainbar = findViewById(R.id.mainbar);
        mainbar.setIndeterminate(false);
        mainbar.setVisibility(View.GONE);
    }

    private void reloadAllData() {
        graph_date.clear();
        graph_high.clear();
        graph_volume.clear();
        feedItems.clear();
        image_video_url.clear();
        video_url.clear();
        video_title.clear();
    }

    public void openSearchView() {
        LayoutInflater i = (LayoutInflater) Activity_Main.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = i.inflate(R.layout.searchbar, null);
        toolbar = findViewById(R.id.toolbar);
        toolbar.addView(v);

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

            }
        });
    }

    public void setSpecialView() {


        pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.GONE);
        market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.VISIBLE);
        Adapter_Chosen_Aequity maa_adapter = new Adapter_Chosen_Aequity(this, getSupportFragmentManager());
        market_pager.setAdapter(maa_adapter);
        Adapter_Pager_Winners_Losers_Kings wlv_adapter = new Adapter_Pager_Winners_Losers_Kings(this, getSupportFragmentManager());
        wlv_adapter.notifyDataSetChanged();
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.removeAllTabs();
        tabs.setupWithViewPager(market_pager);
        tabs.setSelectedTabIndicatorHeight(0);


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
}