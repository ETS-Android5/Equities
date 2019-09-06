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

import java.sql.Array;
import java.sql.Date;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzagz.runOnUiThread;
import static equities.com.myapplication.Constructor_App_Variables.btc_market_cap_amount;
import static equities.com.myapplication.Constructor_App_Variables.btc_market_cap_change;
import static equities.com.myapplication.Constructor_App_Variables.cac_amount;
import static equities.com.myapplication.Constructor_App_Variables.cac_change;
import static equities.com.myapplication.Constructor_App_Variables.cac_name;
import static equities.com.myapplication.Constructor_App_Variables.dax_amount;
import static equities.com.myapplication.Constructor_App_Variables.dax_change;
import static equities.com.myapplication.Constructor_App_Variables.dax_name;
import static equities.com.myapplication.Constructor_App_Variables.dow_amount;
import static equities.com.myapplication.Constructor_App_Variables.dow_change;
import static equities.com.myapplication.Constructor_App_Variables.dow_name;
import static equities.com.myapplication.Constructor_App_Variables.ftse_amount;
import static equities.com.myapplication.Constructor_App_Variables.ftse_change;
import static equities.com.myapplication.Constructor_App_Variables.ftse_name;
import static equities.com.myapplication.Constructor_App_Variables.hang_amount;
import static equities.com.myapplication.Constructor_App_Variables.hang_change;
import static equities.com.myapplication.Constructor_App_Variables.hang_name;
import static equities.com.myapplication.Constructor_App_Variables.bov_amount;
import static equities.com.myapplication.Constructor_App_Variables.bov_change;
import static equities.com.myapplication.Constructor_App_Variables.bov_name;
import static equities.com.myapplication.Constructor_App_Variables.nikk_amount;
import static equities.com.myapplication.Constructor_App_Variables.nikk_change;
import static equities.com.myapplication.Constructor_App_Variables.nikk_name;
import static equities.com.myapplication.Constructor_App_Variables.shse_amount;
import static equities.com.myapplication.Constructor_App_Variables.shse_change;
import static equities.com.myapplication.Constructor_App_Variables.shse_name;
import static equities.com.myapplication.Constructor_App_Variables.sp_amount;
import static equities.com.myapplication.Constructor_App_Variables.sp_change;
import static equities.com.myapplication.Constructor_App_Variables.sp_name;
import static equities.com.myapplication.Service_Main_Equities.*;
import static equities.com.myapplication.Service_Main_Equities.stock_kings_changelist;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Markets extends Fragment {
    private RecyclerView stockitems;
    Adapter_Main_Markets adapter;
    String[] market_list = new String[]{dow_name, sp_name, bov_name, "Bitcoin",ftse_name,cac_name,dax_name,shse_name,hang_name,nikk_name};
    String[] int_list = new String[]{dow_amount,sp_amount, bov_amount,btc_market_cap_amount,ftse_amount,cac_amount,dax_amount,shse_amount,hang_amount,nikk_amount};
    String[] change_list = new String[]{dow_change,sp_change, bov_change, btc_market_cap_change,ftse_change,cac_change,dax_change,shse_change,hang_change,nikk_change};
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_markets, container, false);

        stockitems= rootView.findViewById(R.id.stock_items);
        stockitems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter =new Adapter_Main_Markets(getActivity(),  market_list,int_list,change_list);
        stockitems.setAdapter(adapter);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");


        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(createTimerTask(),0,2000);
        return rootView;

    }

    public void getNewData(){
        new ASYNCUpdateMarkets().execute();

    }
    public class ASYNCUpdateMarkets extends AsyncTask<Integer, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            }
        @Override
        protected String doInBackground(Integer... integers) {
            clearMarkets();
            Service_Main_Equities sme =new Service_Main_Equities();
            sme.getWorldMarkets();
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            setMarketsUserVisibleHint(true);

        }
    }

    public void setMarketsUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            stockitems.removeAllViewsInLayout();
            //stockitems.setAdapter(null);
            adapter =new Adapter_Main_Markets(getActivity(),  market_list,int_list,change_list);
            stockitems.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
}
    public void clearMarkets(){
        Arrays.fill(market_list, null);
        Arrays.fill(int_list, null);
        Arrays.fill(change_list, null);
        market_list = new String[]{dow_name, sp_name, bov_name, "Bitcoin",ftse_name,cac_name,dax_name,shse_name,hang_name,nikk_name};
        int_list = new String[]{dow_amount,sp_amount, bov_amount,btc_market_cap_amount,ftse_amount,cac_amount,dax_amount,shse_amount,hang_amount,nikk_amount};
        change_list = new String[]{dow_change,sp_change, bov_change, btc_market_cap_change,ftse_change,cac_change,dax_change,shse_change,hang_change,nikk_change};

    }

    }


