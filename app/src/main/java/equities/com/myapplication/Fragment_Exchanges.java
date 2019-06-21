package equities.com.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import static equities.com.myapplication.Activity_Main.ap_info;
import static equities.com.myapplication.Constructor_App_Variables.aequity_exchanges;
import static equities.com.myapplication.Constructor_App_Variables.exchanges_feedItems;
import static equities.com.myapplication.Constructor_App_Variables.stock_exchange_name;


public class Fragment_Exchanges extends Fragment {
    private RecyclerView exchange_feed;
    TextView yes,exchanges;
    TableRow yesnotable;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        aequity_exchanges = new ArrayList<String>(new LinkedHashSet<String>(aequity_exchanges));

        View rootView = inflater.inflate(R.layout.fragment_exchanges, container, false);
        TextView t = rootView.findViewById(R.id.greeting);
        yes =rootView.findViewById(R.id.yes);
        yesnotable =rootView.findViewById(R.id.yesnotable);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");
        exchanges =rootView.findViewById(R.id.exchanges);
        exchanges.setTypeface(custom_font);
        exchanges.setPaintFlags(exchanges.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        if(ap_info.getMarketType().equalsIgnoreCase("OTC")){
            stock_exchange_name.remove(4);
        }

        if (ap_info.getMarketType().equals("Cryptocurrency") || ap_info.getMarketType().equals("Crypto")) {}
        else{
            for(int i = 0; i < stock_exchange_name.size();i++){
                aequity_exchanges.add(stock_exchange_name.get(i));
            }
        }
        if (aequity_exchanges.size()<1){
            String buy =  ap_info.getMarketName()+" is not sold on any known reputable exchanges.\nWould you like to search for "+ ap_info.getMarketName()+" using Google search?";
            yesnotable.setVisibility(View.VISIBLE);
            t.setText(buy);
        }else{
            yesnotable.setVisibility(View.GONE);
        String buy = "Buy " + ap_info.getMarketName() + " at these exchanges. \nTouch an image to go their site.";
        t.setText(buy);}
        exchange_feed = rootView.findViewById(R.id.list);







            exchange_feed.setAdapter(new Adapter_Exchanges_Feed(getActivity(), exchanges_feedItems));
            exchange_feed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        yes.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    String search = "https://www.google.com/search?ei=how+to+buy+" + ap_info.getMarketName() + "+coin";
                    intent.putExtra(SearchManager.QUERY, search);
                    startActivity(intent);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

   });
        return rootView;


    }



}



