package airhawk.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables.exchanges_feedItems;
import static airhawk.com.myapplication.Constructor_App_Variables.stocktwits_feedItems;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Exchanges extends Fragment {
    private RecyclerView exchange_feed;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exchanges, container, false);
        TextView t = rootView.findViewById(R.id.greeting);
        String buy = "Buy " + ap_info.getMarketName() + " at these exchanges";
        t.setText(buy);
        exchange_feed = rootView.findViewById(R.id.list);
        if (ap_info.getMarketType().equals("Cryptocurrency") || ap_info.getMarketType().equals("Crypto")) {

            Constructor_App_Variables.exchange_text.add("Binance");
            Constructor_App_Variables.exchange_text.add("Coinbase");
            Constructor_App_Variables.exchange_image.add("R.drawable.bin");
            Constructor_App_Variables.exchange_image.add("R.drawable.coinb");


            exchange_feed.setAdapter(new Adapter_Exchanges_Feed(getActivity(), exchanges_feedItems));
            exchange_feed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }


        return rootView;


    }



}



