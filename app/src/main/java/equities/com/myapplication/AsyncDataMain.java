package equities.com.myapplication;

import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
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
            Activity_Main activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            activity.setMainPage();



        }

    }