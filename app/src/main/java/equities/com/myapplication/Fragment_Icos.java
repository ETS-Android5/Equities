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

import static equities.com.myapplication.Constructor_App_Variables.ico_feedItems;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Icos extends Fragment {
    private RecyclerView ico_items;
    Adapter_Icos_Feed icos_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_icos, container, false);
        ico_items= rootView.findViewById(R.id.ico_items);
        ico_items.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        icos_adapter=new Adapter_Icos_Feed(getActivity(),ico_feedItems);
        ico_items.setAdapter(icos_adapter
        );
        return rootView;

    }

}


