package airhawk.com.myapplication;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static airhawk.com.myapplication.GetCrypto_Dynamic_Data.cryptochangelist;
import static airhawk.com.myapplication.GetCrypto_Dynamic_Data.cryptonamelist;
import static airhawk.com.myapplication.GetCrypto_Dynamic_Data.cryptosymbollist;
import static airhawk.com.myapplication.GetMarket_Dynamic_Data.stock_win_change;
import static airhawk.com.myapplication.GetMarket_Dynamic_Data.stock_win_name;
import static airhawk.com.myapplication.GetMarket_Dynamic_Data.stock_win_symbol;

/**
 * Created by Julian Dinkins on 4/25/2018.
 */

public class Fragment_Winners extends Fragment {
    static TextView st_symbol_1, st_symbol_2, st_symbol_3, st_symbol_4, st_symbol_5,
            st_name_1,st_name_2,st_name_3,st_name_4,st_name_5,
            st_type_1,st_type_2,st_type_3,st_type_4,st_type_5,
            cr_symbol_1, cr_symbol_2, cr_symbol_3, cr_symbol_4, cr_symbol_5,
            cr_name_1,cr_name_2,cr_name_3,cr_name_4,cr_name_5,
            cr_type_1,cr_type_2,cr_type_3,cr_type_4,cr_type_5,
            stock, crypto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_winners, container, false);
        stock = rootView.findViewById(R.id.stock);
        crypto = rootView.findViewById(R.id.crypto);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oregon.ttf");

        stock.setTypeface(custom_font);
        crypto.setTypeface(custom_font);
        stock.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        crypto.setPaintFlags(stock.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        cr_symbol_1 = rootView.findViewById(R.id.cryptosymbol1);
        cr_symbol_2 = rootView.findViewById(R.id.cryptosymbol2);
        cr_symbol_3 = rootView.findViewById(R.id.cryptosymbol3);
        cr_symbol_4 = rootView.findViewById(R.id.cryptosymbol4);
        cr_symbol_5 = rootView.findViewById(R.id.cryptosymbol5);
        cr_name_1 = rootView.findViewById(R.id.cryptoname1);
        cr_name_2 = rootView.findViewById(R.id.cryptoname2);
        cr_name_3 = rootView.findViewById(R.id.cryptoname3);
        cr_name_4 = rootView.findViewById(R.id.cryptoname4);
        cr_name_5 = rootView.findViewById(R.id.cryptoname5);
        cr_type_1 = rootView.findViewById(R.id.cryptotype1);
        cr_type_2 = rootView.findViewById(R.id.cryptotype2);
        cr_type_3 = rootView.findViewById(R.id.cryptotype3);
        cr_type_4 = rootView.findViewById(R.id.cryptotype4);
        cr_type_5 = rootView.findViewById(R.id.cryptotype5);
        cr_symbol_1.setTypeface(custom_font);
        cr_symbol_2.setTypeface(custom_font);
        cr_symbol_3.setTypeface(custom_font);
        cr_symbol_4.setTypeface(custom_font);
        cr_symbol_5.setTypeface(custom_font);

        cr_symbol_1.setText(""+cryptosymbollist.get(0));
        cr_symbol_2.setText(""+cryptosymbollist.get(1));
        cr_symbol_3.setText(""+cryptosymbollist.get(2));
        cr_symbol_4.setText(""+cryptosymbollist.get(3));
        cr_symbol_5.setText(""+cryptosymbollist.get(4));
        cr_name_1.setText(""+cryptonamelist.get(0));
        cr_name_2.setText(""+cryptonamelist.get(1));
        cr_name_3.setText(""+cryptonamelist.get(2));
        cr_name_4.setText(""+cryptonamelist.get(3));
        cr_name_5.setText(""+cryptonamelist.get(4));
        cr_type_1.setText("+"+cryptochangelist.get(0));
        cr_type_2.setText("+"+cryptochangelist.get(1));
        cr_type_3.setText("+"+cryptochangelist.get(2));
        cr_type_4.setText("+"+cryptochangelist.get(3));
        cr_type_5.setText("+"+cryptochangelist.get(4));
        cr_type_1.setTextColor(Color.parseColor("#00ff00"));
        cr_type_2.setTextColor(Color.parseColor("#00ff00"));
        cr_type_3.setTextColor(Color.parseColor("#00ff00"));
        cr_type_4.setTextColor(Color.parseColor("#00ff00"));
        cr_type_5.setTextColor(Color.parseColor("#00ff00"));



        st_symbol_1 = rootView.findViewById(R.id.stocksymbol1);
        st_symbol_2 = rootView.findViewById(R.id.stocksymbol2);
        st_symbol_3 = rootView.findViewById(R.id.stocksymbol3);
        st_symbol_4 = rootView.findViewById(R.id.stocksymbol4);
        st_symbol_5 = rootView.findViewById(R.id.stocksymbol5);
        st_name_1 = rootView.findViewById(R.id.stockname1);
        st_name_2 = rootView.findViewById(R.id.stockname2);
        st_name_3 = rootView.findViewById(R.id.stockname3);
        st_name_4 = rootView.findViewById(R.id.stockname4);
        st_name_5 = rootView.findViewById(R.id.stockname5);
        st_type_1 = rootView.findViewById(R.id.stocktype1);
        st_type_2 = rootView.findViewById(R.id.stocktype2);
        st_type_3 = rootView.findViewById(R.id.stocktype3);
        st_type_4 = rootView.findViewById(R.id.stocktype4);
        st_type_5 = rootView.findViewById(R.id.stocktype5);
        st_symbol_1.setTypeface(custom_font);
        st_symbol_2.setTypeface(custom_font);
        st_symbol_3.setTypeface(custom_font);
        st_symbol_4.setTypeface(custom_font);
        st_symbol_5.setTypeface(custom_font);


        st_type_1.setText(""+stock_win_change.get(0));
        st_type_2.setText(""+stock_win_change.get(1));
        st_type_3.setText(""+stock_win_change.get(2));
        st_type_4.setText(""+stock_win_change.get(3));
        st_type_5.setText(""+stock_win_change.get(4));
        st_type_1.setTextColor(Color.parseColor("#00ff00"));
        st_type_2.setTextColor(Color.parseColor("#00ff00"));
        st_type_3.setTextColor(Color.parseColor("#00ff00"));
        st_type_4.setTextColor(Color.parseColor("#00ff00"));
        st_type_5.setTextColor(Color.parseColor("#00ff00"));




        st_name_1.setText(""+stock_win_name.get(0));
        st_name_2.setText(""+stock_win_name.get(1));
        st_name_3.setText(""+stock_win_name.get(2));
        st_name_4.setText(""+stock_win_name.get(3));
        st_name_5.setText(""+stock_win_name.get(4));
        st_symbol_1.setText(""+stock_win_symbol.get(0));
        st_symbol_2.setText(""+stock_win_symbol.get(1));
        st_symbol_3.setText(""+stock_win_symbol.get(2));
        st_symbol_4.setText(""+stock_win_symbol.get(3));
        st_symbol_5.setText(""+stock_win_symbol.get(4));






        return rootView;

    }

}


