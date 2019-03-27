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
    TextView ico;
    private RecyclerView ico_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_icos, container, false);
        ico = rootView.findViewById(R.id.ico);
        ico_items= rootView.findViewById(R.id.ico_items);
        ico_items.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ico_items.setAdapter(new Adapter_Icos_Feed(getActivity(),ico_feedItems));
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");

        ico.setTypeface(custom_font);

        ico.setPaintFlags(ico.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        return rootView;

    }

}


