package airhawk.com.myapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.http.Url;

import static airhawk.com.myapplication.Activity_Main.image_video_url;
import static airhawk.com.myapplication.Activity_Main.video_title;
import static airhawk.com.myapplication.Activity_Main.video_url;

public class Test_Videos {
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) {

        String url ="https://www.youtube.com/results";
        String query ="Tron trx";
        String begin_url;
        String mid_url;
        String end_url;
        String url_1st ="https://i.ytimg.com/vi/";
        String url_3rd ="/hqdefault.jpg";
        try {
            Document doc = Jsoup.connect(url).data("search_query",query).userAgent("Mozilla/5.0").get();

            for (Element a :doc.select(".yt-lockup-title > a[title]")){
                begin_url=(a.attr("href") + " " + a.attr("title"));
                begin_url=begin_url.replace("/watch?v=","");
                mid_url= begin_url.substring(0, begin_url.indexOf(" "));
                end_url= begin_url.replace(mid_url,"");
                String f_url = url_1st+mid_url+url_3rd;
                video_title.add(end_url);
                video_url.add(mid_url);
                image_video_url.add(f_url);

                System.out.println("URL "+mid_url);
                System.out.println("IMAGE URL "+f_url);
                System.out.println("DESCRIPTION "+end_url);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }









}









