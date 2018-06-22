package airhawk.com.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Losers extends Fragment {
    static TextView stock_1,stock_2,stock_3,stock_4,stock_5,crypto_1,crypto_2,crypto_3,crypto_4,crypto_5,stock_,crypto_;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_losers, container, false);
        stock_ =rootView.findViewById(R.id.stock_);
        crypto_ =rootView.findViewById(R.id.crypto_);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Oregon.ttf");

        stock_.setTypeface(custom_font);
        crypto_.setTypeface(custom_font);
        //stock_.setPaintFlags(stock_.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        //crypto_.setPaintFlags(crypto_.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        stock_1 = rootView.findViewById(R.id.stock_1);
        stock_1.setTypeface(custom_font);
        stock_2 = rootView.findViewById(R.id.stock_2);
        stock_2.setTypeface(custom_font);
        stock_3 = rootView.findViewById(R.id.stock_3);
        stock_3.setTypeface(custom_font);
        stock_4 = rootView.findViewById(R.id.stock_4);
        stock_4.setTypeface(custom_font);
        stock_5 = rootView.findViewById(R.id.stock_5);
        stock_5.setTypeface(custom_font);
        crypto_1 = rootView.findViewById(R.id.crypto_1);
        crypto_1.setTypeface(custom_font);
        crypto_2 = rootView.findViewById(R.id.crypto_2);
        crypto_2.setTypeface(custom_font);
        crypto_3 = rootView.findViewById(R.id.crypto_3);
        crypto_3.setTypeface(custom_font);
        crypto_4 = rootView.findViewById(R.id.crypto_4);
        crypto_4.setTypeface(custom_font);
        crypto_5= rootView.findViewById(R.id.crypto_5);
        crypto_5.setTypeface(custom_font);
       /* crypto_1.setText(cry_1);
        crypto_2.setText(cry_2);
        crypto_3.setText(cry_3);
        crypto_4.setText(cry_4);
        crypto_5.setText(cry_5);
        stock_1.setText(sto_1);
        stock_2.setText(sto_2);
        stock_3.setText(sto_3);
        stock_4.setText(sto_4);
        stock_5.setText(sto_5);
      */
        return rootView;

    }

}


