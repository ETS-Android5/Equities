package equities.com.myapplication;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzagz.runOnUiThread;
import static equities.com.myapplication.Service_Main_Equities.*;


/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Market_Kings_Stock extends Fragment {
    private RecyclerView stockitems;
    LinearLayout cryptoView;
    Adapter_Main_Equities stock_adapter;
    Timer mTimer;
    int t =0;
    private TimerTask createTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if(t>0) {
                                    getNewData();
                                }
                                t=t+1;
                            }
                        }, 0);
                    }

                });
            }
        };
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_kings, container, false);
        stockitems= rootView.findViewById(R.id.stock_items);
        cryptoView = rootView.findViewById(R.id.cryptoView);
        cryptoView.setVisibility(View.GONE);
        stockitems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        stock_adapter=new Adapter_Main_Equities(getActivity(), "Stock_Kings", stock_kings_symbollist,stock_kings_namelist,stock_kings_changelist);
        stockitems.setAdapter(stock_adapter);
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(createTimerTask(),0,15000);

        return rootView;

    }
    public void getNewData(){
        new ASYNCUpdateKings().execute();

    }
    public class ASYNCUpdateKings extends AsyncTask<Integer, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... integers) {
            Service_Main_Equities sme =new Service_Main_Equities();
            sme.clearStockKingsData();
            sme.getMarketKingsStock();
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            if(crypto_kings_namelist.size()>0||stock_kings_namelist.size()>0){
                setKingsUserVisibleHint(true);}else{
                mTimer = new Timer();
                mTimer.scheduleAtFixedRate(createTimerTask(),0,4000);
            }
        }}
    public void setKingsUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            stockitems.removeAllViewsInLayout();
            stock_adapter.notifyDataSetChanged();
            stock_adapter=new Adapter_Main_Equities(getActivity(), "Stock_Kings", stock_kings_symbollist,stock_kings_namelist,stock_kings_changelist);
            stockitems.setAdapter(stock_adapter);
        }}
}