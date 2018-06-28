package airhawk.com.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import airhawk.com.myapplication.dummy.MarketsContent;

import static airhawk.com.myapplication.Constructor_App_Variables.feedItems;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;
import static airhawk.com.myapplication.Constructor_App_Variables.image_video_url;
import static airhawk.com.myapplication.Constructor_App_Variables.video_title;
import static airhawk.com.myapplication.Constructor_App_Variables.video_url;

public class Activity_Main extends AppCompatActivity {
    static Element price=null;
    protected ArrayAdapter<String> ad;
    private Toolbar toolbar;

    public static ArrayList<String> searchview_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_symbol_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_name_arraylist = new ArrayList<>();
    public static ArrayList<String> aequity_type_arraylist = new ArrayList<>();
    private boolean mTwoPane;
    public ViewPager pager, market_pager;
    private View recyclerView;
    public static String get_current_aequity_price;
    Database_Local_Aequities ld = new Database_Local_Aequities(Activity_Main.this);
    private static final String TAG = "ActivityMain";
    static Boolean async_analysis_page = false;
    static Constructor_App_Variables ap_info = new Constructor_App_Variables();

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
        pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.VISIBLE);
        market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.GONE);
        Adapter_Pager_Winners_Losers_Volume wlv_adapter= new Adapter_Pager_Winners_Losers_Volume(this,getSupportFragmentManager());
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
        tabs.setSelectedTabIndicatorHeight(0);
        //if (isSearchOpened) {
       //     return;
       // }

        //super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDATAMain();
    }

    private void setDATAMain() {
        ProgressBar progress;
        setContentView(R.layout.splash);
        progress =(ProgressBar)findViewById(R.id.progressbar);
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
                Executor_Winners_Losers_Kings cst =new Executor_Winners_Losers_Kings();
                cst.main();
                return null;
            }
        }.execute();

    }
    private void setMainPage(){
        setContentView(R.layout.activity_main);
        setJSON_INFO();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.VISIBLE);
        ViewPager market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.GONE);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(getString(R.string.winners)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.losers)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.volume)));
        Adapter_Pager_Winners_Losers_Volume wlv_adapter = new Adapter_Pager_Winners_Losers_Volume(this, getSupportFragmentManager());
        pager.setAdapter(wlv_adapter);
        tabs.setupWithViewPager(pager);
        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
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
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, MarketsContent.ITEMS, mTwoPane));
    }
    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
        private final List<MarketsContent.MarketsItem> mValues;
        private AdapterView.OnItemClickListener mListener;
        SimpleItemRecyclerViewAdapter(Activity_Main parent,
                                      List<MarketsContent.MarketsItem> items,
                                      boolean twoPane) {
            mValues = items;
            mTwoPane = twoPane;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_main_item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.mIdView.setText(mValues.get(position).id);
            holder.mPriceView.setText("$ " + mValues.get(position).content);
            if (mValues.get(position).change.contains("-")) {

                holder.mIdView.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mPriceView.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mChangeView.setTextColor(Color.parseColor("#FF0000"));
            } else {
                holder.mIdView.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mPriceView.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mChangeView.setTextColor(Color.parseColor("#00CC00"));
            }
            holder.mChangeView.setText(mValues.get(position).change);
            Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/scpro.ttf");
            holder.mIdView.setTypeface(custom_font);
            holder.mPriceView.setTypeface(custom_font);
            holder.mChangeView.setTypeface(custom_font);
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mValues.get(position);
                    System.out.println("This is the position "+position);
                    String temp = holder.mIdView.getText().toString();
                    System.out.println("This is the NAME "+holder.mIdView.getText().toString());
                    switch (position) {
                        case 0:
                            temp = "Dow Jones";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("DJI");
                            ap_info.setMarketType("Stock");
                            setMarketPage();
                            break;
                        case 1:
                            temp = "S%P 500";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("INX");
                            ap_info.setMarketType("Stock");
                            setMarketPage();
                            break;
                        case 2:
                            temp = "Nasdaq";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("IXIC");
                            ap_info.setMarketType("Stock");
                            setMarketPage();
                            break;
                        case 3:
                            temp = "Bitcoin";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("BTC");
                            ap_info.setMarketType(getString(R.string.crypto));
                            setMarketPage();
                            break;
                        case 4:
                            temp = "All Crypto";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("ALL");
                            ap_info.setMarketType(getString(R.string.crypto));
                            setMarketPage();
                            break;
                        case 5:
                            temp = "Alt Cap";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("ALT");
                            ap_info.setMarketType(getString(R.string.crypto));
                            setMarketPage();
                            break;

                        case 6:
                            temp = "IPO";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("IPO");
                            ap_info.setMarketType("Stock");
                            setMarketPage();
                            break;
                        case 7:
                            temp = "ICO";
                            ap_info.setMarketName(temp);
                            ap_info.setMarketSymbol("ICO");
                            ap_info.setMarketType(getString(R.string.crypto));
                            setMarketPage();
                            break;
                        default:
                            temp = temp;
                            break;

                    }



                }
            });
        }


        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mPriceView;
            final TextView mChangeView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mPriceView = view.findViewById(R.id.price);
                mChangeView = view.findViewById(R.id.change);
            }
        }
    }

    public void setMarketPage() {

        if (async_analysis_page) {
            reloadAllData();

                Executor_Chosen_Aequity shoe =new Executor_Chosen_Aequity();
            shoe.main();
            System.out.println("ASYNC HAS BEEN CALLED PREVIOUSLY");
        } else
            {

                Executor_Chosen_Aequity shoe =new Executor_Chosen_Aequity();
                shoe.main();
                System.out.println("ASYNC HAS NOT BEEN CALLED");

            async_analysis_page=true;


        }
        pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.GONE);
        market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.VISIBLE);
        Adapter_Chosen_Aequity maa_adapter = new Adapter_Chosen_Aequity(this, getSupportFragmentManager());
        market_pager.setAdapter(maa_adapter);
        Adapter_Pager_Winners_Losers_Volume wlv_adapter= new Adapter_Pager_Winners_Losers_Volume(this,getSupportFragmentManager());
        wlv_adapter.notifyDataSetChanged();
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.removeAllTabs();
        tabs.setupWithViewPager(market_pager);
        tabs.setSelectedTabIndicatorHeight(0);


    }

    private void reloadAllData(){
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
                setMarketPage();

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
                    ld = Database_Local_Aequities.getInstance(Activity_Main.this);
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

    private static void ProcessXml(org.w3c.dom.Document data) {
        if (data != null) {
            String st, sd, sp, sl;
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
                            st = Html.fromHtml(curent.getTextContent()).toString();
                            it.setTitle(st);
                        } else if (curent.getNodeName().equalsIgnoreCase("description")) {
                            sd = Html.fromHtml(curent.getTextContent()).toString();
                            String d = curent.getTextContent().toString();
                            String pattern1 = "<img src=\"";
                            String pattern2 = "\"";
                            Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
                            Matcher m = p.matcher(d);
                            while (m.find()) {
                                it.setThumbnailUrl(m.group(1));
                            }
                            it.setDescription(sd);
                        } else if (curent.getNodeName().equalsIgnoreCase("pubDate")) {
                            sp = Html.fromHtml(curent.getTextContent()).toString();
                            sp = sp.replaceAll("@20", " ");
                            it.setPubDate(sp);
                        } else if (curent.getNodeName().equalsIgnoreCase("link")) {
                            sl = Html.fromHtml(curent.getTextContent()).toString();
                            sl = sl.replaceAll("@20", " ");
                            it.setLink(sl);
                        } else if (curent.getNodeName().equalsIgnoreCase("img src")) {

                        }
                    }

                    feedItems.add(it);


                }
            }
        }
    }
    public static org.w3c.dom.Document GoogleRSFeed() {
        try {
            URL url;
            Context context;
            String repo;
            repo = ap_info.getMarketName();
            repo = repo.replace("  ", " ");
            if (repo.contains(" ")) {
                String remove = repo.substring(repo.lastIndexOf(" "));
                repo = repo.replace(remove, "");
            }
            repo = repo.replace(" ", "%20");
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


    }}

