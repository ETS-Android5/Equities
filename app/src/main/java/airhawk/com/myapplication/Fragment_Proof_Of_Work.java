package airhawk.com.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Proof_Of_Work extends Fragment {
    TextView stock, crypto;
    private RecyclerView stockitems;
    private RecyclerView cryptoitems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_proof_of_work, container, false);
        //stock = rootView.findViewById(R.id.stock);
        //crypto = rootView.findViewById(R.id.crypto);
        //stockitems= rootView.findViewById(R.id.stock_items);
        //stockitems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //stockitems.setAdapter(new Adapter_Main_Equities(getActivity(), "Stock_Winner", stock_win_symbollist,stock_win_namelist,stock_win_changelist));
        //cryptoitems= rootView.findViewById(R.id.crypto_items);
        //cryptoitems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //cryptoitems.setAdapter(new Adapter_Main_Equities(getActivity(), "Crypto_Winner",crypto_winners_symbollist,crypto_winners_namelist,crypto_winners_changelist));
        //Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");

        //stock.setTypeface(custom_font);
        //crypto.setTypeface(custom_font);
        //stock.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        //crypto.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        return rootView;

    }

}


