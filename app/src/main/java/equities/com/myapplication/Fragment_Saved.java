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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import static equities.com.myapplication.Constructor_App_Variables.current_percentage_change;
import static equities.com.myapplication.Constructor_App_Variables.current_saved_price;
import static equities.com.myapplication.Constructor_App_Variables.top_story_image_url;
import static equities.com.myapplication.Constructor_App_Variables.top_story_title;
import static equities.com.myapplication.Constructor_App_Variables.top_story_url;


/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Saved extends Fragment {
    public static RecyclerView saved_items;
    public ProgressBar progressBar2;
    public Adapter_Saved_Feed adapter_saved_feed;
    TextView news_title, newslink;
    ImageView newsimage;
    public Fragment_Saved() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved, container, false);
        saved_items=rootView.findViewById(R.id.saved_items);
        saved_items.setVisibility(View.VISIBLE);
        news_title=rootView.findViewById(R.id.news_title);
        newsimage=rootView.findViewById(R.id.news_image);
        newslink=rootView.findViewById(R.id.news_link);
        news_title.setText(top_story_title);
        Picasso.with(getActivity()).load(top_story_image_url).memoryPolicy(MemoryPolicy.NO_CACHE).noFade().into(newsimage);
        newslink.setText(top_story_url);
        Constructor_App_Variables cav =new Constructor_App_Variables();
        Database_Local_Aequities ld =new Database_Local_Aequities(getActivity());
        //(Arrays.asList(ld.getName()));
        //The only way to fix this is to create a constructor with arraylist so you can clear arraylist when backpress is entered
        if (saved_items!=null){
            //("SAVED FEED IS NOT NULL!!!");
            //saved_feed.setAdapter(null);
           // saved_feed.setLayoutManager(null);
            saved_items.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            saved_items.setAdapter(new Adapter_Saved_Feed(getActivity(),Database_Local_Aequities.KEY_AEQUITY_SYMBOL,Database_Local_Aequities.KEY_AEQUITY_NAME,current_percentage_change,current_saved_price,Database_Local_Aequities.KEY_AEQUITY_TYPE));
            //(Arrays.asList("X "+ld.getName()+" "+ld.getType()));

        }else {
            adapter_saved_feed=new Adapter_Saved_Feed(getActivity(),Database_Local_Aequities.KEY_AEQUITY_SYMBOL,Database_Local_Aequities.KEY_AEQUITY_NAME,current_percentage_change,current_saved_price,Database_Local_Aequities.KEY_AEQUITY_TYPE);
            //("SAVED FEED IS NULL!!!");
            saved_items.setAdapter(adapter_saved_feed);

        }
        return rootView;
    }



}

