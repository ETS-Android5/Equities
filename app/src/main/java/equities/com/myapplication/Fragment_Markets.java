package equities.com.myapplication;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import static equities.com.myapplication.Constructor_App_Variables.nas_amount;
import static equities.com.myapplication.Constructor_App_Variables.nas_change;
import static equities.com.myapplication.Constructor_App_Variables.nas_name;
import static equities.com.myapplication.Constructor_App_Variables.nikk_amount;
import static equities.com.myapplication.Constructor_App_Variables.nikk_change;
import static equities.com.myapplication.Constructor_App_Variables.nikk_name;
import static equities.com.myapplication.Constructor_App_Variables.shse_amount;
import static equities.com.myapplication.Constructor_App_Variables.shse_change;
import static equities.com.myapplication.Constructor_App_Variables.shse_name;
import static equities.com.myapplication.Constructor_App_Variables.sp_amount;
import static equities.com.myapplication.Constructor_App_Variables.sp_change;
import static equities.com.myapplication.Constructor_App_Variables.sp_name;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Markets extends Fragment {
    TextView stock;
    private RecyclerView stockitems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] market_list = new String[]{dow_name, sp_name, nas_name, "Bitcoin",ftse_name,cac_name,dax_name,shse_name,hang_name,nikk_name};
        String[] int_list = new String[]{dow_amount,sp_amount,nas_amount,btc_market_cap_amount,ftse_amount,cac_amount,dax_amount,shse_amount,hang_amount,nikk_amount};
        String[] change_list = new String[]{dow_change,sp_change, nas_change, btc_market_cap_change,ftse_change,cac_change,dax_change,shse_change,hang_change,nikk_change};

        View rootView = inflater.inflate(R.layout.fragment_markets, container, false);
        stock = rootView.findViewById(R.id.stock);

        stockitems= rootView.findViewById(R.id.stock_items);
        stockitems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        stockitems.setAdapter(new Adapter_Main_Markets(getActivity(),  market_list,int_list,change_list));
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");

        stock.setTypeface(custom_font);
        stock.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        return rootView;

    }

}


