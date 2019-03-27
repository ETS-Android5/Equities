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

import static equities.com.myapplication.Constructor_App_Variables.ipo_feedItems;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Ipos extends Fragment {
    TextView  ipo;
    private RecyclerView crypto_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_ipos, container, false);
        ipo = rootView.findViewById(R.id.crypto);
        crypto_items= rootView.findViewById(R.id.crypto_items);
        crypto_items.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        crypto_items.setAdapter(new Adapter_Ipos_Feed(getActivity(), ipo_feedItems));
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");

        ipo.setTypeface(custom_font);
        ipo.setPaintFlags(ipo.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        return rootView;

    }

}


