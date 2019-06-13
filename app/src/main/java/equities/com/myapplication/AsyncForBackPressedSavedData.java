package equities.com.myapplication;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static equities.com.myapplication.Constructor_App_Variables.aequity_exchanges;
import static equities.com.myapplication.Constructor_App_Variables.exchange_list;
import static equities.com.myapplication.Constructor_App_Variables.graph_change;
import static equities.com.myapplication.Constructor_App_Variables.graph_date;
import static equities.com.myapplication.Constructor_App_Variables.graph_high;
import static equities.com.myapplication.Constructor_App_Variables.graph_volume;
import static equities.com.myapplication.Constructor_App_Variables.stocktwits_feedItems;
import static equities.com.myapplication.Test_Methods.getSavedEquities;

public class AsyncForBackPressedSavedData extends AsyncTask<Integer, Integer, String> {

        private WeakReference<Activity_Main> activityReference;
        AsyncForBackPressedSavedData(Activity_Main context) {
            activityReference = new WeakReference<>(context);
        }
    Activity_Main activity = activityReference.get();

    long startTime;
        ViewPager pager = activity.findViewById(R.id.viewpager);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (pager.getVisibility()== View.VISIBLE){
                activity.finishAffinity();}else{


                if (activity.mInterstitialAd.isLoaded()) {
                    activity.mInterstitialAd.show();

                }else{
                    //mInterstitialAd.setAdUnitId("ca-app-pub-6566728316210720/4471280326");
                    AdRequest adRequest = new AdRequest.Builder().build();
                    activity.mInterstitialAd.loadAd(adRequest);
                    activity.mInterstitialAd.show();
                }
                activity.mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        //("Ad is showing");
                        activity.mInterstitialAd.show();
                    }
                    @Override
                    public void onAdClosed() {
                        System.err.println("Ad loaded");
                    }
                });


            }
        }

        @Override
        protected String doInBackground(Integer... params) {
            ArrayList aa = activity.check_saved.getType();
            if(aa.size()>0){
                getSavedEquities();}
            return "task finished";
        }
        @Override
        protected void onPostExecute(String result) {
            Activity_Main activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            graph_change.clear();
            exchange_list.clear();
            aequity_exchanges.clear();
            stocktwits_feedItems.clear();
            graph_date.clear();
            graph_high.clear();
            graph_volume.clear();
            new AsyncChosenData(activity).cancel(true);
            pager.setVisibility(View.VISIBLE);
            ViewPager market_pager = activity.findViewById(R.id.market_pager);
            market_pager.setVisibility(View.GONE);
            TabLayout tabs = activity.findViewById(R.id.tabs);
            activity.setupMainViewPager(pager);
            tabs.setupWithViewPager(pager);


            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            //("SERVICE MAIN TIME IS " + duration / 1000000000 + " seconds");

        }

    }