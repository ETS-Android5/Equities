package airhawk.com.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static airhawk.com.myapplication.Activity_Main._AllDays;
import static airhawk.com.myapplication.Activity_Main.dataModels;
import static airhawk.com.myapplication.Activity_Main.graph_date;
import static airhawk.com.myapplication.Activity_Main.graph_high;
import static airhawk.com.myapplication.Activity_Main.graph_market_cap;
import static airhawk.com.myapplication.Activity_Main.graph_volume;
import static airhawk.com.myapplication.Activity_Main.market_name;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Buy extends Fragment {

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buy, container, false);











        return rootView;

    }

}


