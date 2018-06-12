package airhawk.com.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import airhawk.com.myapplication.dummy.MarketsContent;

import static airhawk.com.myapplication.Activity_Main._1_Day_Chart;
import static airhawk.com.myapplication.Activity_Main._30Days;
import static airhawk.com.myapplication.Activity_Main._7Days;
import static airhawk.com.myapplication.Activity_Main._90Days;
import static airhawk.com.myapplication.Activity_Main._AllDays;
import static airhawk.com.myapplication.Activity_Main.feedItems;
import static airhawk.com.myapplication.Activity_Main.graph_date;
import static airhawk.com.myapplication.Activity_Main.graph_high;
import static airhawk.com.myapplication.Activity_Main.graph_low;
import static airhawk.com.myapplication.Activity_Main.graph_market_cap;
import static airhawk.com.myapplication.Activity_Main.graph_volume;
import static airhawk.com.myapplication.Activity_Main.market_name;
import static airhawk.com.myapplication.Activity_Main.newname;
import static airhawk.com.myapplication.Activity_Main.progress;

public class Activity_Main extends AppCompatActivity {

    public static String AssetJSONFile (String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

          public void setJSON_INFO(){
//General Method for reading JSON file in Assets

        try{
            String jsonLocation = AssetJSONFile("rd.json",Activity_Main.this);
            JSONObject obj = new JSONObject(jsonLocation);

            JSONArray Arry = obj.getJSONArray("ALL");;
            for (int i=0; i < Arry.length(); i++) {
                JSONArray childJsonArray = Arry.getJSONArray(i);
                String sym = childJsonArray.getString(0);
                String nam = childJsonArray.getString(1);
                String typ = childJsonArray.getString(2);
//Now adding strings to arraylist
                name_arraylist.add(sym + "  " + nam + "  " + typ);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setDATAMain() {
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                GetMarket_Dynamic_Data gnd = new GetMarket_Dynamic_Data();
                    gnd.execute();

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                {GetCrypto_Dynamic_Data cryp = new GetCrypto_Dynamic_Data();
                cryp.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);}
                else{
                    GetCrypto_Dynamic_Data cryp = new GetCrypto_Dynamic_Data();
                    cryp.execute();}


            }

            @Override
            public void onFinish() {
                if (sp_amount != null && !sp_amount.isEmpty() && sp_change != null && !sp_change.isEmpty() && dow_amount != null && !dow_amount.isEmpty() && dow_change != null && !dow_change.isEmpty() && nas_amount != null && !nas_amount.isEmpty() && nas_change != null && !nas_change.isEmpty() && sto5 != null && !sto5.isEmpty() && cry5 != null && !cry5.isEmpty()) {
                    setMainPage();
                } else {

                    setDATAMain();
                }
            }
        }.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);
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
    public void openSearchView() {
        LayoutInflater i= (LayoutInflater)Activity_Main.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v =i.inflate(R.layout.searchbar,null);
        toolbar = findViewById(R.id.toolbar);
        toolbar.addView(v);

        ad =new ArrayAdapter(Activity_Main.this, android.R.layout.simple_dropdown_item_1line,name_arraylist);
        AutoCompleteTextView chosen_searchView_item =v.findViewById(R.id.searchtool);
        chosen_searchView_item.setAdapter(ad);

        chosen_searchView_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                feedItems.clear();
                String repo =ad.getItem(position).toString();
                market_name = repo;
                String am ="CRYPTO";
                System.out.println(market_name);



                toolbar.removeView(v);
                chosen_searchView_item.onEditorAction(EditorInfo.IME_ACTION_DONE);
                setMarketPage();

            }});
    }
    protected ArrayAdapter<String> ad;
    private Toolbar toolbar;
    private static boolean isSearchOpened = false;
    protected static ProgressBar progress;
    static ArrayList video_url = new ArrayList<>();
    static ArrayList image_video_url = new ArrayList<>();
    static ArrayList video_title = new ArrayList<>();
    private static ArrayList<String> name_arraylist =new ArrayList<>();
    protected static List<String> graph_date = new ArrayList<>();
    protected static List<String> graph_high = new ArrayList<>();
    protected static List<String> graph_low = new ArrayList<>();
    protected static List<String> graph_volume = new ArrayList<>();
    protected static List<String> graph_market_cap = new ArrayList<>();

    protected static List<String> _1_Day_Chart =new ArrayList();
    protected static List<String> _7Days =new ArrayList();
    protected static List<String> _30Days =new ArrayList();
    protected static List<String> _90Days =new ArrayList();
    private static List<String> _180Days =new ArrayList();
    private static List<String> _1_Year_Chart =new ArrayList();
    protected static List<String> _AllDays =new ArrayList();
    protected static ArrayList<Historical_Data_Model> dataModels;
    public static String market_name;
    protected static String newname;
    protected static String sp_name;
    protected static String sp_amount;
    protected static String sp_change;
    protected static String dow_name;
    protected static String dow_amount;
    protected static String dow_change;
    protected static String nas_name;
    protected static String nas_amount;
    protected static String nas_change;
    protected static String all_market_cap_amount;
    protected static String alt_market_cap_amount;
    protected static String btc_market_cap_amount;
    protected static String btc_market_cap_change;
    protected static String cry1;
    protected static String cry2;
    protected static String cry3;
    protected static String cry4;
    protected static String cry5;
    protected static String sto1;
    protected static String sto2;
    protected static String sto3;
    protected static String sto4;
    protected static String sto5;
    protected static String sto_1;
    protected static String sto_2;
    protected static String sto_3;
    protected static String sto_4;
    protected static String sto_5;
    protected static String cry_1;
    protected static String cry_2;
    protected static String cry_3;
    protected static String cry_4;
    protected static String cry_5;
    protected static String cry_vol1;
    protected static String cry_vol2;
    protected static String cry_vol3;
    protected static String cry_vol4;
    protected static String cry_vol5;
    private Winners_Losers_Volume_Pager_Adapter wlv_adapter;
    private Market_Analysis_Adapter maa_adapter;
    private boolean mTwoPane;
    private ViewPager pager,market_pager;
    private View recyclerView;
    protected static ArrayList<News_FeedItem> feedItems = new ArrayList<>();
    private static List<String> video_image_url = new ArrayList<>();
    private Get_Home_News_Data ghnd= new Get_Home_News_Data(Activity_Main.this);
    private Get_Video_News_Data gvnd= new Get_Video_News_Data(Activity_Main.this);
    private Get_Graph_Points ggp = new Get_Graph_Points(Activity_Main.this);
    private Get_Stock_Graph_Points gsgp = new Get_Stock_Graph_Points(Activity_Main.this);
    Boolean async=false;
    public void onBackPressed() {
        if(isSearchOpened) {
            return;
        }

        super.onBackPressed();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        if (sp_amount != null){
            setMainPage();
            setJSON_INFO();
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }else{
        setDATAMain();
            setJSON_INFO();
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);}

    }
    private void callPoints(){



            ghnd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        if(market_name.contains("Bitcoin")){
            ggp.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }else{
            gsgp.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
            gvnd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    private void setMarketPage() {

        if(async = false) {
            System.out.println(market_name+ " is the market name");
            callPoints();
        }
            else{
            async = true;
            System.out.println(market_name+ " is the new market name");
            Get_Alternative_Points al = new Get_Alternative_Points(Activity_Main.this);
            Get_Graph_Points nggp = new Get_Graph_Points(Activity_Main.this);
            Get_Home_News_Data nhnd = new Get_Home_News_Data(Activity_Main.this);
            Get_Video_News_Data nvnd = new Get_Video_News_Data(Activity_Main.this);
            Get_Stock_Graph_Points nsgp = new Get_Stock_Graph_Points(Activity_Main.this);
            nhnd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            nvnd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            String dj = "Dow Jones";
            String spp = "S&P 500";
            String nas = "Nasdaq";
            newname=market_name;
            if(newname !=null){
            if(newname.contains("Bitcoin")){
                nggp.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
            if(newname.contains(dj)) {
                newname = "https://finance.yahoo.com/quote/%5EDJI/history?p=%5EDJI";
                al.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
                if (newname.contains(spp)) {
                    newname = "https://finance.yahoo.com/quote/%5EGSPC/history?p=%5EGSPC";
                    al.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                if (newname.contains(nas)) {
                    newname = "https://finance.yahoo.com/quote/%5EIXIC/history?p=^IXIC";
                    al.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }} else {
                    nsgp.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }

            }


        pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.GONE);
        market_pager = findViewById(R.id.market_pager);


        market_pager.setVisibility(View.VISIBLE);
        maa_adapter = new Market_Analysis_Adapter(this, getSupportFragmentManager());
        market_pager.setAdapter(maa_adapter);
        wlv_adapter.notifyDataSetChanged();
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.removeAllTabs();
        tabs.setupWithViewPager(market_pager);
        tabs.setSelectedTabIndicatorHeight(1);

    }
    private void setMainPage() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        pager = findViewById(R.id.viewpager);
        pager.setVisibility(View.VISIBLE);
        progress = (ProgressBar)findViewById(R.id.progressbar);
        market_pager = findViewById(R.id.market_pager);
        market_pager.setVisibility(View.GONE);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(getString(R.string.winners)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.losers)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.volume)));
        wlv_adapter = new Winners_Losers_Volume_Pager_Adapter(this, getSupportFragmentManager());
        pager.setAdapter(wlv_adapter);
        tabs.setupWithViewPager(pager);
        tabs.getTabAt(0).setCustomView(R.layout.customtab);
        tabs.getTabAt(1).setCustomView(R.layout.customtab2);
        tabs.getTabAt(2).setCustomView(R.layout.customtab3);
        tabs.setSelectedTabIndicatorHeight(1);
        tabs.setTabTextColors(Color.parseColor("#0000ff"),Color.parseColor("#ffffff"));

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, MarketsContent.ITEMS, mTwoPane));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final Activity_Main mParentActivity;
        private final List<MarketsContent.MarketsItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarketsContent.MarketsItem item = (MarketsContent.MarketsItem) view.getTag();
                    market_name= String.valueOf(item.id);
                    market_name=market_name.replace(" ","%20");
                    System.out.println("THIS IS -----  "+market_name);
                setMarketPage();
            }
        };



        SimpleItemRecyclerViewAdapter(Activity_Main parent,
                                      List<MarketsContent.MarketsItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
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
            holder.mPriceView.setText("$ "+mValues.get(position).content);
            if (mValues.get(position).change.contains("-")){

                holder.mIdView.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mPriceView.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mChangeView.setTextColor(Color.parseColor("#FF0000"));}
                else{
                holder.mIdView.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mPriceView.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mChangeView.setTextColor(Color.parseColor("#00CC00"));
            }
            holder.mChangeView.setText(mValues.get(position).change);
            Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/scpro.ttf");
            holder.mIdView.setTypeface(custom_font);
            holder.mPriceView.setTypeface(custom_font);
            holder.mChangeView.setTypeface(custom_font);
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);

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




    class Get_Stock_Graph_Points extends AsyncTask<Void,Void,Void> {
        Context context;
        public Get_Stock_Graph_Points(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            get_nasdaq_points();
            return null;
        }
        public void get_nasdaq_points() {

            String marname =market_name;
            int i = marname.indexOf(" ");
            String in = marname.substring(0, i);
            marname=in;
            List<String> name = new ArrayList<>();
            List<String> number = new ArrayList<>();
            Document doc = null;
            try {
                doc = Jsoup.connect("https://charting.nasdaq.com/ext/charts.dll?2-1-14-0-0-512-03NA000000"+marname+"-&SF:1|5-BG=FFFFFF-BT=0-HT=395--XTBL-").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements divs = doc.getElementsByClass("DrillDownData");
            Elements divsdate = doc.getElementsByClass("DrillDownDate");
            ArrayList<String> elements=new ArrayList<String>();
            for (Element el : divs) {
                Elements tds = el.select("td");
                String result = tds.get(0).text();
                elements.add(result);
            }
            ArrayList<String> elements_dates=new ArrayList<String>();
            for (Element e : divsdate){
                Elements tdsdate = e.select("td");
                String resultdate =tdsdate.get(0).text();
                elements_dates.add(resultdate);
            }

            for(int j=elements.size() - 1; j >= 0; j--){
                if (j % 2 == 0) { // Even
                    number.add(elements.get(j));
                } else { // Odd
                    name.add(elements.get(j));
                }
            }
            for (int counter = 0; counter < number.size(); counter++) {
                System.out.println("VOLUME "+name.get(counter)+" NUMBER "+number.get(counter)+" DATE "+elements_dates.get(counter));
                graph_date.add(elements_dates.get(counter));
                graph_volume.add(name.get(counter));
                graph_high.add(number.get(counter));
            }
            List<String> numbers = graph_high;
            Collections.reverse(numbers);
            System.out.println(graph_high);
            _AllDays=numbers;
            _1_Day_Chart=_AllDays.subList(_AllDays.size()-1, _AllDays.size()-0);
            _7Days = _AllDays.subList(_AllDays.size()-7, _AllDays.size()-0);
            _30Days = _AllDays.subList(_AllDays.size()-30, _AllDays.size()-0);
            _90Days = _AllDays.subList(_AllDays.size()-90, _AllDays.size()-0);
            //_180Days = _AllDays.subList(_AllDays.size()-180, _AllDays.size()-0);
            //_1_Year_Chart=_AllDays.subList(_AllDays.size()-181, _AllDays.size()-0);

        }
    }

    class Get_Video_News_Data extends AsyncTask<Void,Void,Void> {
        Context context;

        public Get_Video_News_Data(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            video_image_url.clear();
            image_video_url.clear();
            video_url.clear();
            video_title.clear();

            getVideoInfo();
            return null;
        }

        public void getVideoInfo() {


            String url ="https://www.youtube.com/results";

            String begin_url;
            String mid_url;
            String end_url;
            String url_1st ="https://i.ytimg.com/vi/";
            String url_3rd ="/hqdefault.jpg";
            try {
                Document doc = Jsoup.connect(url).data("search_query",market_name).userAgent("Mozilla/5.0").get();

                for (Element a :doc.select(".yt-lockup-title > a[title]")){
                    begin_url=(a.attr("href") + " " + a.attr("title"));
                    begin_url=begin_url.replace("/watch?v=","");
                    mid_url= begin_url.substring(0, begin_url.indexOf(" "));
                    end_url= begin_url.replace(mid_url,"");
                    String f_url = url_1st+mid_url+url_3rd;
                    video_title.add(end_url);
                    video_url.add(mid_url);
                    image_video_url.add(f_url);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }




        }
    }}

    class Get_Home_News_Data extends AsyncTask<Void,Void,Void> {
        URL url;
        Context context;
        String st, sd, sp, sl;
        public Get_Home_News_Data(Context context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            progress.setIndeterminate(true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            progress.setVisibility(View.GONE);

            super.onPostExecute(aVoid);
        }



        @Override
        protected Void doInBackground(Void... voids) {
            feedItems.clear();
            ProcessXml(GoogleRSFeed());
            return null;
        }

        private void ProcessXml(org.w3c.dom.Document data) {
            if (data != null) {

                org.w3c.dom.Element root = data.getDocumentElement();
                Node channel = root.getChildNodes().item(0);
                NodeList items = channel.getChildNodes();

                for (int i = 0; i < items.getLength(); i++) {
                    News_FeedItem it = new News_FeedItem();
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



        public org.w3c.dom.Document GoogleRSFeed() {
            try {
                String repo;
                repo=market_name;
                repo =repo.replace("  "," ");
                System.out.println("BIG BOOBS1 "+repo);
                if(repo.contains(" ")){
                String remove=repo.substring(repo.lastIndexOf(" "));
                repo=repo.replace(remove,"");}
                repo=repo.replace(" ","%20");
                String address = "https://news.google.com/news/rss/search/section/q/"+repo+"?ned=us&gl=US&hl=en";

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



    }

    class Get_Graph_Points extends AsyncTask<Void,Void,Void> {
        Context context;

        public Get_Graph_Points(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            get_crypto_points();


            return null;
        }

        public void get_crypto_points() {

            DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date begindate = new Date();
            Document doc = null;
            //market_name="tron";
            try {
                doc = Jsoup.connect("https://coinmarketcap.com/currencies/" + market_name + "/historical-data/?start=20000101&end=" + sdf.format(begindate)).get();
                //
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements divs = doc.select("table");
            for (Element tz : divs) {
                Elements tds = tz.select("td");
                Elements s = tz.getElementsByClass("text-right");
                for (Element ss : s) {
                    Elements p = ss.select("td[data-format-fiat]");
                    String v = p.text();
                    String[] splited = v.split("\\s+");
                    if (v != null && !v.isEmpty()) {
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
            }
            List<String> numbers = graph_high;
            System.out.println(graph_high);
            _AllDays = numbers;
            _1_Day_Chart = _AllDays.subList(_AllDays.size() - 1, _AllDays.size() - 0);
            _7Days = _AllDays.subList(_AllDays.size() - 7, _AllDays.size() - 0);
            _30Days = _AllDays.subList(_AllDays.size() - 30, _AllDays.size() - 0);
            _90Days = _AllDays.subList(_AllDays.size() - 90, _AllDays.size() - 0);
           // _180Days = _AllDays.subList(_AllDays.size() - 180, _AllDays.size() - 0);
            // 1_Year_Chart = _AllDays.subList(_AllDays.size() - 365, _AllDays.size() - 0);


        }

    }

    class Get_Alternative_Points extends AsyncTask<Void,Void,Void> {
    Context context;

    public Get_Alternative_Points(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        getAlternativePoints();


        return null;
    }


    public void getAlternativePoints(){
        Document doc = null;
        try {
            doc = Jsoup.connect(newname).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements volumes = doc.getElementsByClass("BdT Bdc($c-fuji-grey-c) Ta(end) Fz(s) Whs(nw)");
        for (Element el : volumes) {
            Elements t = el.select("td");

            String date = t.get(0).text();
            graph_date.add(date);
            System.out.println("DATE " + date);

            String close = t.get(4).text();
            graph_high.add(close);
            System.out.println("CLOSE " + close);

            String volume = t.get(6).text();
            graph_volume.add(volume);
            System.out.println("VOLUME " + volume);
        }
        _AllDays = graph_high;
        _1_Day_Chart = _AllDays.subList(_AllDays.size() - 1, _AllDays.size() - 0);
        _7Days = _AllDays.subList(_AllDays.size() - 7, _AllDays.size() - 0);
        _30Days = _AllDays.subList(_AllDays.size() - 30, _AllDays.size() - 0);
        _90Days = _AllDays.subList(_AllDays.size() - 90, _AllDays.size() - 0);
        //_180Days = _AllDays.subList(_AllDays.size() - 180, _AllDays.size() - 0);
        //_1_Year_Chart = _AllDays.subList(_AllDays.size() - 365, _AllDays.size() - 0);
    }}



