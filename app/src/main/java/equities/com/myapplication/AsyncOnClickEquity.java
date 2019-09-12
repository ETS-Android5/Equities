package equities.com.myapplication;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.Timer;

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
            LinearLayout equityView = activity.findViewById(R.id.equityView);
            equityView.setVisibility(View.VISIBLE);
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

            if (activity.ap_info.getMarketType().equalsIgnoreCase("Crypto")||(activity.ap_info.getMarketType().equalsIgnoreCase("Cryptocurrency"))) {
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
            Constructor_App_Variables ap_info =new Constructor_App_Variables();
            TextView chosen_price_change =activity.findViewById(R.id.chosenpricechange);
            String ap_pc = ap_info.getCurrent_Aequity_Price_Change();
            TextView savedd =activity.findViewById(R.id.savedd);
            TextView a_name =activity.findViewById(R.id.aequity_name);
            TextView a_type =activity.findViewById(R.id.aequity_type);
            TextView a_symbol=activity.findViewById(R.id.aequity_symbol);
            TextView a_supply=activity.findViewById(R.id.aequity_supply);
            TextView a_price_change =activity.findViewById(R.id.aequity_price_change);
            TextView a_price=activity.findViewById(R.id.aequity_price);
            TextView a_volume=activity.findViewById(R.id.aequity_current_volume);
            TextView a_website=activity.findViewById(R.id.aequity_website);
            TextView saved =activity.findViewById(R.id.saved);
            TextView a_cap =activity.findViewById(R.id.aequity_cap);
            activity.mTimer = new Timer();
            activity.mTimer.scheduleAtFixedRate(activity.createTimerTask(),0,10000);

            a_website.setText(ap_info.getChosen_website());
            a_website.setTextColor(activity.getResources().getColor(R.color.colorGold));
            a_website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                        String search = a_website.getText().toString();
                        intent.putExtra(SearchManager.QUERY, search);
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            });

            a_cap.setText(ap_info.getMarketCap());

            try {
                a_price.setText("$ " +graph_high.get(0));
                a_price_change.setText(graph_change.get(0)+" %");
                a_volume.setText(ap_info.getCurrent_volume());
            }catch(IndexOutOfBoundsException e){
                if(!a_price.getText().toString().isEmpty()){
                    a_price.setText("Updating");
                }
                ////("Had to start over ");
            }





           // mTimer = new Timer();
            //mTimer.scheduleAtFixedRate(createTimerTask(),0,15000);
            if (ap_pc !=null && ap_pc.contains("-")){
                a_price_change.setTextColor(activity.getResources().getColor(R.color.colorRed));
            }else{a_price_change.setTextColor(activity.getResources().getColor(R.color.colorGreen));}
            TextView sup=activity.findViewById(R.id.supply);
            if(ap_info.getMarketType().equals("Cryptocurrency")){
                sup.setText(activity.getString(R.string.supply));
            }else{
                sup.setText(activity.getString(R.string.shares));}

            a_supply.setText(ap_info.getMarketSupply());
            Constructor_App_Variables app_info =new Constructor_App_Variables();
            a_name.setText(app_info.getMarketName().replace(" ",""));
            String correct =app_info.getMarketSymbol();
            boolean  b = correct.startsWith("%5E");
            if(b)
            {
                String scorrect=correct.substring(0,3);
                correct =correct.replaceAll(scorrect,"");
                a_symbol.setText(correct);
            }
            else
            {
                a_symbol.setText(correct);
            }
            ImageView save =activity.findViewById(R.id.save);
            a_type.setText(app_info.getMarketType());
            Database_Local_Aequities db = new Database_Local_Aequities(activity.getApplicationContext());
            if(db.getSymbol().contains(a_symbol.getText().toString())){
                //save.setImageResource(android.R.drawable.btn_star_big_on);
            }

            //dont forget shared preferences or check database if aequity is in database show big star on


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //save.setImageResource(android.R.drawable.btn_star_big_on);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setTitle(app_info.getMarketName()+" is saved")
                            .setIcon(R.drawable.banner_ae)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {

                                            String mS=a_symbol.getText().toString();
                                            String mN=a_name.getText().toString();
                                            String mT=a_type.getText().toString();
                                            db.add_equity_info(mS,mN,mT);
                                            db.close();
                                        }
                                    }, 2000);
                                }
                            }).show();


                }
            });


        }


    }