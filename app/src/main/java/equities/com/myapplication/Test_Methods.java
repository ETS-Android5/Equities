package equities.com.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static equities.com.myapplication.Constructor_App_Variables.current_percentage_change;
import static equities.com.myapplication.Constructor_App_Variables.current_updated_price;

public class Test_Methods {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        updaqte();
    }
    public static void updaqte(){
        Constructor_App_Variables app_info =new Constructor_App_Variables();
        String name="gsat";
         Document caps= null;
        try {
            caps = Jsoup.connect("https://money.cnn.com/quote/quote.html?symb="+name).userAgent("Opera").timeout(10 * 10000).get();
            Element tb= caps.select("tbody").get(1);
            String td = tb.select("td").get(7).text();
            Element tb1= caps.select("tbody").get(0);
            String td0 = String.valueOf(tb1.select("td").get(0).select("span").text());
            String td1 = String.valueOf(tb1.select("td").get(1).select("span").get(4).text());
            current_percentage_change.clear();
            current_updated_price.clear();
            current_percentage_change.add(td1);
            current_updated_price.add(td0);
            app_info.setCurrent_volume(td);
            System.out.println(td);
        }catch (IOException e){
            //NEED A BACKUP for stockupdate
        }}

}
