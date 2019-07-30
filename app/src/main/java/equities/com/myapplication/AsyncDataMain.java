package equities.com.myapplication;

import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

import static equities.com.myapplication.Service_Main_Equities.*;

public class AsyncDataMain extends AsyncTask<Integer, Integer, String> {

        private WeakReference<Activity_Main> activityReference;
        AsyncDataMain(Activity_Main context) {
            activityReference = new WeakReference<>(context);
        }
        long startTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Activity_Main activity = activityReference.get();
            ViewPager pager = activity.findViewById(R.id.viewpager);
            if (pager!=null&&pager.getVisibility()== View.VISIBLE){
                pager.setVisibility(View.GONE);
                Random rand = new Random();
                int value = rand.nextInt(31);
                String [] qu =activity.getResources().getStringArray(R.array.all_quotes);
                String q = qu[value];
                TextView txt2 =activity.findViewById(R.id.output2);
                txt2.setText(q);
                ProgressBar mainbar = activity.findViewById(R.id.mainbar);
                mainbar.setIndeterminate(true);
                //Snackbar sb = Snackbar.make(progLayout,"Loading Equities...", Snackbar.LENGTH_LONG);
                //View sbView = sb.getView();
                //sbView.setBackgroundColor(activity.getResources().getColor(R.color.colorTrans));
                //TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                //textView.setTextColor(activity.getResources().getColor(R.color.darkTextColor2));
                //textView.setTextSize(30);
                //textView.setGravity(Gravity.CENTER_HORIZONTAL);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                  //  textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                //textView.setAnimation(AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(), R.anim.flash));
                //sb.show();
                pager = activity.findViewById(R.id.viewpager);
                pager.setVisibility(View.GONE);
            }else{
                RelativeLayout progressLayout =activity.findViewById(R.id.progressLayout);
                startTime = System.nanoTime();
                Random rand = new Random();
                int value = rand.nextInt(31);
                String [] qu =activity.getResources().getStringArray(R.array.all_quotes);
                String q = qu[value];
                TextView txt2 =activity.findViewById(R.id.output);
                txt2.setText(q);
                Snackbar sb = Snackbar.make(progressLayout,"Loading Equities...", Snackbar.LENGTH_LONG);
                View sbView = sb.getView();
                sbView.setBackgroundColor(activity.getResources().getColor(R.color.colorTrans));
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(activity.getResources().getColor(R.color.darkTextColor2));
                textView.setTextSize(30);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                textView.setAnimation(AnimationUtils.loadAnimation(activity.context, R.anim.flash));
                sb.show();
            }

        }

        @Override
        protected String doInBackground(Integer... params) {
            Service_Main_Equities cst = new Service_Main_Equities();
            cst.main();
            return "task finished";
        }
        @Override
        protected void onPostExecute(String result) {
            int a = stock_win_symbollist.size();
            int b = stock_losers_symbollist.size();
            int c = stock_kings_symbollist.size();
            int d = crypto_winners_symbollist.size();
            int e = crypto_losers_symbollist.size();
            int f = crypto_kings_symbolist.size();
            lowest_integer = new Integer[]{a, b, c, d, e, f};
            System.out.println("THIS IS LOWEST INTEGER "+lowest_integer[0]
                    +" THIS IS ARRAYS sizes"+ stock_win_symbollist.size()+" "+stock_losers_symbollist.size()+" "+stock_kings_symbollist.size()+" "+crypto_winners_symbollist.size()+"  "+crypto_losers_symbollist.size()+" "+crypto_kings_symbolist.size());

            Activity_Main activity = activityReference.get();
//          TabLayout t = activity.findViewById(R.id.tabs);
//          t.setVisibility(View.VISIBLE);
            if (activity == null || activity.isFinishing()) return;
            activity.setMainPage();



        }

    }