package equities.com.myapplication;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

public class AsyncChosenData extends AsyncTask<Void, Void, String> {

        private WeakReference<Activity_Main> activityReference;
        AsyncChosenData(Activity_Main context) {
            activityReference = new WeakReference<>(context);
        }
        long startTime;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Activity_Main activity = activityReference.get();
            TableRow t = activity.findViewById(R.id.table_tabs);
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
            Activity_Main activity = activityReference.get();
            if(isCancelled())
            {

                //("Async Cancelled");
                return null;}

//System.out.print("THIS IS WHAT MARKET TYPE EQUALS "+activity.ap_info.getMarketType());

//            getSupportActionBar().setDisplayShowTitleEnabled(true);
            if (activity.async_analysis_page) {
                activity.reloadAllData();
                if (activity.ap_info.getMarketType().equals("Crypto")||(activity.ap_info.getMarketType().equals("Cryptocurrency"))) {

                    activity.getChosenCryptoInfo();
                    activity.getStockTwitsData();
                    activity.get_coinmarketcap_exchange_listing();
                    Service_Chosen_Equity shoe = new Service_Chosen_Equity(activity);
                    shoe.main();
                    activity.do_graph_change();
                }
                if (activity.ap_info.getMarketType().equals("Index")) {

                    activity.get_stock_exchange_info();
                    activity.get_current_stock_info();
                    activity.getStockTwitsData();
                    Service_Chosen_Equity shoe = new Service_Chosen_Equity(activity);
                    shoe.main();
                    activity.do_graph_change();
                }
                else {

                    activity.get_stock_exchange_info();
                    activity.get_current_stock_info();
                    activity.getStockTwitsData();
                    Service_Chosen_Equity shoe = new Service_Chosen_Equity(activity);
                    shoe.main();
                    activity.do_graph_change();
                }



            } else {
                if (activity.ap_info.getMarketType().equals("Crypto")||(activity.ap_info.getMarketType().equals("Cryptocurrency"))) {

                    activity.getChosenCryptoInfo();
                    activity.getStockTwitsData();
                    activity.get_coinmarketcap_exchange_listing();
                    Service_Chosen_Equity shoe = new Service_Chosen_Equity(activity);
                    shoe.main();
                    activity.do_graph_change();
                }
               if (activity.ap_info.getMarketType().equals("Index")) {

                    activity.get_stock_exchange_info();
                    activity.get_current_stock_info();
                    activity.getStockTwitsData();
                    Service_Chosen_Equity shoe = new Service_Chosen_Equity(activity);
                    shoe.main();
                    activity.do_graph_change();
                }

                else
                {
                    activity.getChosenCryptoInfo();
                    activity.getStockTwitsData();
                    activity.get_coinmarketcap_exchange_listing();
                    Service_Chosen_Equity shoe = new Service_Chosen_Equity(activity);
                    shoe.main();
                    activity.do_graph_change();
                }
                activity.ProcessXmlx(activity.GoogleRSFeedx());

                activity.async_analysis_page = true;


            }
            return "task finished";
        }



        @Override
        protected void onPostExecute(String result) {
            Activity_Main activity = activityReference.get();
            activity.fullScreen="go";
            // get a reference to the activity if it is still there

            TableRow t = activity.findViewById(R.id.table_tabs);
            t.setVisibility(View.VISIBLE);
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

            if (activity.ap_info.getMarketType().equals("Index")) {
                //tabs.getTabAt(0).setIcon(R.drawable.direction_info);
                //tabs.getTabAt(1).setIcon(R.drawable.direction_news);
                //tabs.getTabAt(2).setIcon(R.drawable.direction_youtube_video);
                //tabs.getTabAt(3).setIcon(R.drawable.direction_socialmedia);
            }else{
                //tabs.getTabAt(0).setIcon(R.drawable.direction_info);
                //tabs.getTabAt(1).setIcon(R.drawable.direction_news);
                //tabs.getTabAt(2).setIcon(R.drawable.direction_youtube_video);
                //tabs.getTabAt(3).setIcon(R.drawable.direction_exchange);
                //tabs.getTabAt(4).setIcon(R.drawable.direction_socialmedia);
            }


            RelativeLayout progLayout =activity.findViewById(R.id.progLayout);
            progLayout.setVisibility(View.GONE);

        }


    }