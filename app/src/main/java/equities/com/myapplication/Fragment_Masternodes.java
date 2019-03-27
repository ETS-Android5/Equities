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

import static equities.com.myapplication.Constructor_App_Variables.masternode_feedItems;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Masternodes extends Fragment {
    TextView stock, crypto;
    private RecyclerView masternode_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_masternodes, container, false);
        masternode_items= rootView.findViewById(R.id.masternode_items);
        masternode_items.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        masternode_items.setAdapter(new Adapter_Masternodes_Feed(getActivity(),masternode_feedItems));
        TextView crypto= (TextView)rootView.findViewById(R.id.crypto);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");

        //stock.setTypeface(custom_font);
        crypto.setTypeface(custom_font);
        //stock.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        crypto.setPaintFlags(crypto.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        return rootView;

    }

}


