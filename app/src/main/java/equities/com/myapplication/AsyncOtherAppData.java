package equities.com.myapplication;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

import static equities.com.myapplication.Service_Chosen_Equity.GoogleRSFeed;
import static equities.com.myapplication.Service_Main_Equities.*;

public class AsyncOtherAppData extends AsyncTask<Integer, Integer, String> {

        private WeakReference<Activity_Markets_Main> activityReference;
        AsyncOtherAppData(Activity_Markets_Main context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            Service_Main_Equities cst = new Service_Main_Equities();
            cst.getCrypto_Data();
            cst.getStock_Data();
            cst.get_masternodes();
            cst.get_ipos();
            return "task finished";
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println("ALL DATA IS AVALIABLE");
        }

    }