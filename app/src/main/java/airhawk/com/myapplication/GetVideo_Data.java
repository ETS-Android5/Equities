package airhawk.com.myapplication;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


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

