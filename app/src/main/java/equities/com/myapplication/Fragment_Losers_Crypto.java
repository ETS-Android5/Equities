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
public class Fragment_Losers_Crypto extends Fragment {
    LinearLayout stockView;
    private RecyclerView cryptoitems;
    Adapter_Main_Equities crypto_adapter;
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
                                    getNewData();}
                                t=t+1;
                            }
                        }, 0);
                    }

                });
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_losers, container, false);
        stockView= rootView.findViewById(R.id.stockView);
        stockView.setVisibility(View.GONE);
        cryptoitems= rootView.findViewById(R.id.crypto_items);
        cryptoitems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        crypto_adapter=new Adapter_Main_Equities(getActivity(), "Crypto_Loser",crypto_losers_symbollist,crypto_losers_namelist,crypto_losers_changelist);
        cryptoitems.setAdapter(crypto_adapter);
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(createTimerTask(),0,15000);
        return rootView;

    }
    public void getNewData(){
        new ASYNCUpdateLosers().execute();

    }

    public class ASYNCUpdateLosers extends AsyncTask<Integer, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            Service_Main_Equities sme = new Service_Main_Equities();
            sme.clearCryptoLosersData();
            sme.getCryptoLosers();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(stock_losers_namelist.size()>0||crypto_losers_namelist.size()>0){
                setLosersUserVisibleHint(true);}else{
                mTimer = new Timer();
                mTimer.scheduleAtFixedRate(createTimerTask(),0,2000);
            }
        }

    }
    public void setLosersUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            cryptoitems.removeAllViewsInLayout();
            crypto_adapter.notifyDataSetChanged();
            cryptoitems.setAdapter(crypto_adapter);
            cryptoitems.setAdapter(new Adapter_Main_Equities(getActivity(), "Crypto_Loser",crypto_losers_symbollist,crypto_losers_namelist,crypto_losers_changelist));
        }
    }


}