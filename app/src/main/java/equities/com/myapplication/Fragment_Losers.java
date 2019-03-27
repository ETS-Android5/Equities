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

import static equities.com.myapplication.Service_Main_Equities.crypto_losers_changelist;
import static equities.com.myapplication.Service_Main_Equities.crypto_losers_namelist;
import static equities.com.myapplication.Service_Main_Equities.crypto_losers_symbollist;
import static equities.com.myapplication.Service_Main_Equities.stock_losers_changelist;
import static equities.com.myapplication.Service_Main_Equities.stock_losers_namelist;
import static equities.com.myapplication.Service_Main_Equities.stock_losers_symbollist;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */
public class Fragment_Losers extends Fragment {
    TextView stock, crypto;
    private RecyclerView stockitems;
    private RecyclerView cryptoitems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_losers, container, false);
        stock = rootView.findViewById(R.id.stock);
        crypto = rootView.findViewById(R.id.crypto);
        stockitems= rootView.findViewById(R.id.stock_items);
        stockitems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        stockitems.setAdapter(new Adapter_Main_Equities(getActivity(), "Stock_Loser", stock_losers_symbollist,stock_losers_namelist,stock_losers_changelist));
        cryptoitems= rootView.findViewById(R.id.crypto_items);
        cryptoitems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cryptoitems.setAdapter(new Adapter_Main_Equities(getActivity(), "Crypto_Loser",crypto_losers_symbollist,crypto_losers_namelist,crypto_losers_changelist));
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");

        stock.setTypeface(custom_font);
        crypto.setTypeface(custom_font);
        stock.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        crypto.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        return rootView;

    }




}


