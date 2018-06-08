package airhawk.com.myapplication;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static airhawk.com.myapplication.Activity_Main.all_market_cap_amount;
import static airhawk.com.myapplication.Activity_Main.alt_market_cap_amount;
import static airhawk.com.myapplication.Activity_Main.btc_market_cap_amount;
import static airhawk.com.myapplication.Activity_Main.btc_market_cap_change;
import static airhawk.com.myapplication.Activity_Main.cry1;
import static airhawk.com.myapplication.Activity_Main.cry2;
import static airhawk.com.myapplication.Activity_Main.cry3;
import static airhawk.com.myapplication.Activity_Main.cry4;
import static airhawk.com.myapplication.Activity_Main.cry5;
import static airhawk.com.myapplication.Activity_Main.cry_1;
import static airhawk.com.myapplication.Activity_Main.cry_2;
import static airhawk.com.myapplication.Activity_Main.cry_3;
import static airhawk.com.myapplication.Activity_Main.cry_4;
import static airhawk.com.myapplication.Activity_Main.cry_5;
import static airhawk.com.myapplication.Activity_Main.cry_vol1;
import static airhawk.com.myapplication.Activity_Main.cry_vol2;
import static airhawk.com.myapplication.Activity_Main.cry_vol3;
import static airhawk.com.myapplication.Activity_Main.cry_vol4;
import static airhawk.com.myapplication.Activity_Main.cry_vol5;


public class GetVideo_Data extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            crypto();
            return null;
        }
        public void crypto() {


            try {
                Document top5st_data = Jsoup.connect("https://www.youtube.com/results?search_query=tron+trx").get();
                //Elements link = top5st_data.getAllElements();
                //System.out.println(link);

                Element c = top5st_data.getElementById("img-preload");
                String cc = String.valueOf(c);

                cc = cc.replace("<div id=\"img-preload\" style=\"display: none;\">","");
                cc =cc.replace("img src=\"","");
                cc =cc.replace("\"","");
                cc =cc.replace("<","");
                cc =cc.replace("/div","");
                cc =cc.replace("_","");
                cc =cc.replace(" ","");
                String[] split = cc.split(">");
                int numberOfItems = split.length;

                for (int i=0; i<numberOfItems; i++){
                    String name = split[i];
                    if(name != null && name.trim().length() > 0)
                    {
                        System.out.println("TACO: " + name);

                        //it.setThumbnailUrl(name);}
                    }}

            } catch (IOException e) {
                e.printStackTrace();
            }

        }}

