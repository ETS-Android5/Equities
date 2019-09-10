package equities.com.myapplication;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.lang.ref.WeakReference;
import java.util.Random;

import static equities.com.myapplication.Constructor_App_Variables.aequity_exchanges;
import static equities.com.myapplication.Constructor_App_Variables.exchange_list;
import static equities.com.myapplication.Constructor_App_Variables.graph_change;
import static equities.com.myapplication.Constructor_App_Variables.graph_date;
import static equities.com.myapplication.Constructor_App_Variables.graph_high;
import static equities.com.myapplication.Constructor_App_Variables.graph_volume;
import static equities.com.myapplication.Constructor_App_Variables.feedItems;
import static equities.com.myapplication.Constructor_App_Variables.stocktwits_feedItems;

public class AsyncOnClickEquity extends AsyncTask<Void, Void, String> {

        private WeakReference<Activity_Markets_Main> activityReference;
        AsyncOnClickEquity(Activity_Markets_Main context) {
            activityReference = new WeakReference<>(context);
        }
        long startTime;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Activity_Markets_Main activity = activityReference.get();
            BottomNavigationView t = activity.findViewById(R.id.activity_tabs);
            t.setVisibility(View.GONE);
            graph_change.clear();
            exchange_list.clear();
            aequity_exchanges.clear();
            stocktwits_feedItems.clear();
            graph_date.clear();
            graph_high.clear();
            graph_volume.clear();
            feedItems.clear();
            Random rand = new Random();
            int svalue = rand.nextInt(25);
            int cvalue = rand.nextInt(25);
            ViewPager market_pager = activity.findViewById(R.id.market_pager);
            if (market_pager.getVisibility()== View.VISIBLE){
                market_pager.setVisibility(View.GONE);
            }
            String [] sq =activity.getResources().getStringArray(R.array.stock_quotes);
            String [] cq =activity.getResources().getStringArray(R.array.crypto_quotes);
            String q;
            if (activity.ap_info.getMarketType().equals("Stock")){
                q = sq[svalue];}
                else{
                q = cq[cvalue];
            }
            TextView txt2 =activity.findViewById(R.id.output2);
            txt2.setText(q);
            LinearLayout mainlayout= activity.findViewById(R.id.main_layout);
            mainlayout.setBackgroundResource(R.drawable.background_gradient);
            AppBarLayout abl = activity.findViewById(R.id.app_bar);
            abl.setVisibility(View.GONE);
            startTime = System.nanoTime();
            RelativeLayout progLayout =activity.findViewById(R.id.progLayout);
            progLayout.setVisibility(View.VISIBLE);
            ProgressBar mainbar = activity.findViewById(R.id.mainbar);
            mainbar.setIndeterminate(true);
            Snackbar sb = Snackbar.make(progLayout,"Loading data for "+activity.ap_info.getMarketName(), Snackbar.LENGTH_LONG);
            View sbView = sb.getView();
            sbView.setBackgroundColor(activity.getResources().getColor(R.color.colorTrans));
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(activity.getResources().getColor(R.color.darkTextColor2));
            textView.setMaxLines(10);
            textView.setTextSize(30);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            textView.setAnimation(AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(), R.anim.flash));
            sb.show();
            ViewPager pager = activity.findViewById(R.id.viewpager);
            pager.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(Void... params) {
            Activity_Markets_Main activity = activityReference.get();
            if(isCancelled())
            {
                //("Async Cancelled");
                return null;}

            if (activity.ap_info.getMarketType().equals("Crypto")||(activity.ap_info.getMarketType().equals("Cryptocurrency"))) {
                activity.primaryGetCrypto_Method();
                activity.primaryGetSocialMedia_Method();
                activity.primaryGetCryptoExchange_Method();
                Service_Chosen_Equity shoe = new Service_Chosen_Equity(activity);
                shoe.main();
                activity.historic_daily_percentage_change();
            }else{
                activity.get_exchange_info();
                activity.primaryGetStock_Method();
                activity.primaryGetSocialMedia_Method();
                Service_Chosen_Equity shoe = new Service_Chosen_Equity(activity);
                shoe.main();
                activity.historic_daily_percentage_change();
                //activity.async_analysis_page = true;
            }
            return "task finished";
        }



        @Override
        protected void onPostExecute(String result) {
            Activity_Markets_Main activity = activityReference.get();
            activity.fullScreen="go";
            LinearLayout mainlayout= activity.findViewById(R.id.main_layout);
            mainlayout.setBackgroundColor(Color.parseColor("#ffffff"));
            AppBarLayout abl = activity.findViewById(R.id.app_bar);
            abl.setVisibility(View.VISIBLE);
            String a=activity.ap_info.getCurrent_Aequity_Price();
            if (activity == null || activity.isFinishing()&&graph_high.size()>0&&activity.requestQueue !=null&& activity.forward) return;
            ProgressBar mainbar = activity.findViewById(R.id.mainbar);
            mainbar.setIndeterminate(false);
            ViewPager pager = activity.findViewById(R.id.viewpager);
            pager.setVisibility(View.GONE);
            ViewPager market_pager = activity.findViewById(R.id.market_pager);
            market_pager.setVisibility(View.VISIBLE);
            TabLayout tabs = activity.findViewById(R.id.tabs);
            tabs.setupWithViewPager(market_pager);
            activity.setupChosenViewPager(market_pager);
            RelativeLayout progLayout =activity.findViewById(R.id.progLayout);
            progLayout.setVisibility(View.GONE);

        }


    }