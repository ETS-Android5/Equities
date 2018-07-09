package airhawk.com.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import static airhawk.com.myapplication.Constructor_App_Variables.feedItems;


/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Saved extends Fragment {
    public RecyclerView saved_feed;


    public Fragment_Saved() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved, container, false);
        saved_feed=rootView.findViewById(R.id.saved_feed);
        saved_feed.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        saved_feed.setAdapter(new Adapter_Saved_Feed(getActivity(),Database_Local_Aequities.KEY_AEQUITY_SYMBOL,Database_Local_Aequities.KEY_AEQUITY_NAME,Database_Local_Aequities.KEY_AEQUITY_TYPE));

        return rootView;
    }



}

