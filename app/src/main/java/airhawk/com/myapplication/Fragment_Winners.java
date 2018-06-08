package airhawk.com.myapplication;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static airhawk.com.myapplication.Activity_Main.cry1;
import static airhawk.com.myapplication.Activity_Main.cry2;
import static airhawk.com.myapplication.Activity_Main.cry3;
import static airhawk.com.myapplication.Activity_Main.cry4;
import static airhawk.com.myapplication.Activity_Main.cry5;
import static airhawk.com.myapplication.Activity_Main.sto1;
import static airhawk.com.myapplication.Activity_Main.sto2;
import static airhawk.com.myapplication.Activity_Main.sto3;
import static airhawk.com.myapplication.Activity_Main.sto4;
import static airhawk.com.myapplication.Activity_Main.sto5;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Winners extends Fragment {
    static TextView cl1,cl2,cl3,cl4,cl5,st1,st2,st3,st4,st5,stock,crypto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_winners, container, false);
        stock =rootView.findViewById(R.id.stock);
        crypto =rootView.findViewById(R.id.crypto);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Oregon.ttf");

        stock.setTypeface(custom_font);
        crypto.setTypeface(custom_font);
        stock.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        crypto.setPaintFlags(crypto.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        cl1 = rootView.findViewById(R.id.crypto1);
        cl2 = rootView.findViewById(R.id.crypto2);
        cl3 = rootView.findViewById(R.id.crypto3);
        cl4 = rootView.findViewById(R.id.crypto4);
        cl5 = rootView.findViewById(R.id.crypto5);
        cl1.setTypeface(custom_font);
        cl2.setTypeface(custom_font);
        cl3.setTypeface(custom_font);
        cl4.setTypeface(custom_font);
        cl5.setTypeface(custom_font);
        cl1.setText(cry1);
        cl2.setText(cry2);
        cl3.setText(cry3);
        cl4.setText(cry4);
        cl5.setText(cry5);

        st1 = rootView.findViewById(R.id.stock1);
        st2 = rootView.findViewById(R.id.stock2);
        st3 = rootView.findViewById(R.id.stock3);
        st4 = rootView.findViewById(R.id.stock4);
        st5 = rootView.findViewById(R.id.stock5);
        st1.setTypeface(custom_font);
        st2.setTypeface(custom_font);
        st3.setTypeface(custom_font);
        st4.setTypeface(custom_font);
        st5.setTypeface(custom_font);
        st1.setText(sto1);
        st2.setText(sto2);
        st3.setText(sto3);
        st4.setText(sto4);
        st5.setText(sto5);
        return rootView;

    }

}


