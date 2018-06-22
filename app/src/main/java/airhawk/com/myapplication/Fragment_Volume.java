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

public class Fragment_Volume extends Fragment {
    private TextView stock,crypto;
    private TextView volumestock1,volumestock2,volumestock3,volumestock4,volumestock5,volumecrypto1,volumecrypto2,volumecrypto3,volumecrypto4,volumecrypto5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_volume, container, false);
        stock =rootView.findViewById(R.id.stock);
        //stock.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        crypto =rootView.findViewById(R.id.crypto);
        //crypto.setPaintFlags(crypto.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Oregon.ttf");

        stock.setTypeface(custom_font);
        crypto.setTypeface(custom_font);
        volumecrypto1 = rootView.findViewById(R.id.volumecrypto1);
        volumecrypto2 = rootView.findViewById(R.id.volumecrypto2);
        volumecrypto3 = rootView.findViewById(R.id.volumecrypto3);
        volumecrypto4 = rootView.findViewById(R.id.volumecrypto4);
        volumecrypto5 = rootView.findViewById(R.id.volumecrypto5);
        volumestock1 = rootView.findViewById(R.id.volumestock1);
        volumecrypto1.setTypeface(custom_font);
        volumestock2 = rootView.findViewById(R.id.volumestock2);
        volumecrypto2.setTypeface(custom_font);
        volumestock3 = rootView.findViewById(R.id.volumestock3);
        volumecrypto3.setTypeface(custom_font);
        volumestock4 = rootView.findViewById(R.id.volumestock4);
        volumecrypto4.setTypeface(custom_font);
        volumestock5 = rootView.findViewById(R.id.volumestock5);
        volumecrypto5.setTypeface(custom_font);
        /*
        volumestock1.setText(sto1);
        volumestock1.setTypeface(custom_font);
        volumecrypto2.setTypeface(custom_font);
        volumestock2.setText(sto2);
        volumestock2.setTypeface(custom_font);
                volumestock3.setTypeface(custom_font);
        volumestock4.setTypeface(custom_font);
                volumestock5.setTypeface(custom_font);

        volumecrypto3.setTypeface(custom_font);
        volumestock3.setText(sto3);
        volumecrypto4.setTypeface(custom_font);
        volumestock4.setText(sto4);
        volumecrypto5.setTypeface(custom_font);
        volumestock5.setText(sto5);


        volumecrypto1.setText(cry_vol1);
        volumecrypto2.setText(cry_vol2);
        volumecrypto3.setText(cry_vol3);
        volumecrypto4.setText(cry_vol4);
        volumecrypto5.setText(cry_vol5);
*/
        return rootView;

    }

}


