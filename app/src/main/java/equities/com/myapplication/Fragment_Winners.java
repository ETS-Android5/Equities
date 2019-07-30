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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static equities.com.myapplication.Service_Main_Equities.stock_win_changelist;
import static equities.com.myapplication.Service_Main_Equities.stock_win_namelist;
import static equities.com.myapplication.Service_Main_Equities.stock_win_symbollist;
import static equities.com.myapplication.Service_Main_Equities.crypto_winners_changelist;
import static equities.com.myapplication.Service_Main_Equities.crypto_winners_namelist;
import static equities.com.myapplication.Service_Main_Equities.crypto_winners_symbollist;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Winners extends Fragment {
    TextView stock, crypto;
    private RecyclerView stockitems;
    private RecyclerView cryptoitems;
    Adapter_Main_Equities crypto_adapter;
    Adapter_Main_Equities stock_adapter;

    Timer mTimer;
    View mView;
    private TimerTask createTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                getNewData();
                            }
                        }, 10000);
                    }

                });
            }
        };
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_leaders, container, false);
        stock = rootView.findViewById(R.id.stock);
        crypto = rootView.findViewById(R.id.crypto);

        stockitems= rootView.findViewById(R.id.stock_items);
        stockitems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        stock_adapter=new Adapter_Main_Equities(getActivity(), "Stock_Winner", stock_win_symbollist,stock_win_namelist,stock_win_changelist);
        stockitems.setAdapter(stock_adapter);

        cryptoitems= rootView.findViewById(R.id.crypto_items);
        cryptoitems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        crypto_adapter =new Adapter_Main_Equities(getActivity(), "Crypto_Winner",crypto_winners_symbollist,crypto_winners_namelist,crypto_winners_changelist);
        cryptoitems.setAdapter(crypto_adapter);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");

        stock.setTypeface(custom_font);
        crypto.setTypeface(custom_font);
        stock.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        crypto.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(createTimerTask(),0,10000);
        return rootView;

    }
    public void getNewData(){
        new ASYNCUpdate().execute();

    }
    public class ASYNCUpdate extends AsyncTask<Integer, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           }
        @Override
        protected String doInBackground(Integer... integers) {
            Service_Main_Equities sme =new Service_Main_Equities();
            sme.clearWinnersData();
            sme.getCrypto_Data();
            sme.getStock_Data();
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            stockitems.removeAllViewsInLayout();
            cryptoitems.removeAllViewsInLayout();
            crypto_adapter.notifyDataSetChanged();
            stock_adapter.notifyDataSetChanged();
            crypto_adapter =new Adapter_Main_Equities(getActivity(), "Crypto_Winner",crypto_winners_symbollist,crypto_winners_namelist,crypto_winners_changelist);
            cryptoitems.setAdapter(crypto_adapter);
            stock_adapter=new Adapter_Main_Equities(getActivity(), "Stock_Winner", stock_win_symbollist,stock_win_namelist,stock_win_changelist);
            stockitems.setAdapter(stock_adapter);
        }
    }
}


