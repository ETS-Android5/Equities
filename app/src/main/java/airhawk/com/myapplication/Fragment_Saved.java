package airhawk.com.myapplication;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;


import static airhawk.com.myapplication.Constructor_App_Variables.Smessage_link;
import static airhawk.com.myapplication.Constructor_App_Variables.current_percentage_change;


/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Saved extends Fragment {
    public static RecyclerView saved_feed;
    public ProgressBar progressBar2;
    public Adapter_Saved_Feed adapter_saved_feed;
    TextView fav;

    public Fragment_Saved() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved, container, false);
        fav= rootView.findViewById(R.id.fav);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");

        fav.setTypeface(custom_font);

        fav.setPaintFlags(fav.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        saved_feed=rootView.findViewById(R.id.saved_feed);
        saved_feed.setVisibility(View.VISIBLE);
        Constructor_App_Variables cav =new Constructor_App_Variables();
        Database_Local_Aequities ld =new Database_Local_Aequities(getActivity());
        //(Arrays.asList(ld.getName()));
        //The only way to fix this is to create a constructor with arraylist so you can clear arraylist when backpress is entered
        if (saved_feed!=null){
            //("SAVED FEED IS NOT NULL!!!");
            //saved_feed.setAdapter(null);
           // saved_feed.setLayoutManager(null);
            saved_feed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            saved_feed.setAdapter(new Adapter_Saved_Feed(getActivity(),Database_Local_Aequities.KEY_AEQUITY_SYMBOL,Database_Local_Aequities.KEY_AEQUITY_NAME,current_percentage_change,Database_Local_Aequities.KEY_AEQUITY_TYPE));
            //(Arrays.asList("X "+ld.getName()+" "+ld.getType()));

        }else {
            adapter_saved_feed=new Adapter_Saved_Feed(getActivity(),Database_Local_Aequities.KEY_AEQUITY_SYMBOL,Database_Local_Aequities.KEY_AEQUITY_NAME,current_percentage_change,Database_Local_Aequities.KEY_AEQUITY_TYPE);
            //("SAVED FEED IS NULL!!!");
            saved_feed.setAdapter(adapter_saved_feed);

        }
        return rootView;
    }



}

