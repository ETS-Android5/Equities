package airhawk.com.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian Dinkins on 6/13/2018.
 */

public class Constructor_App_Variables {

    static ArrayList video_url = new ArrayList<>();
    static ArrayList image_video_url = new ArrayList<>();
    static ArrayList video_title = new ArrayList<>();
    public static ArrayList graph_date = new ArrayList<>();
    public static ArrayList graph_high = new ArrayList<>();
    public static ArrayList graph_low = new ArrayList<>();
    public static ArrayList graph_volume = new ArrayList<>();
    public static ArrayList graph_market_cap = new ArrayList<>();
    public static String sp_name;
    public static String sp_amount;
    public static String sp_change;
    public static String dow_name;
    public static String dow_amount;
    public static String dow_change;
    public static String nas_name;
    public static String nas_amount;
    public static String nas_change;
    public static String all_market_cap_amount;
    public static String alt_market_cap_amount;
    public static String btc_market_cap_amount;
    public static String btc_market_cap_change;
    protected static ArrayList<Constructor_News_Feed> feedItems = new ArrayList<>();
    public static List<String> _AllDays =new ArrayList();
    public static String market_symbol;
    public static String market_name;
    public static String market_change;
    public static String market_type;
    protected static String newname;


    public Constructor_App_Variables(){}

    public String getMarketName(){
        return this.market_name;
    }
    public void setMarketName(String market_name){
        this.market_name = market_name;
    }
    public String getMarketSymbol(){
        return this.market_symbol;
    }
    public void setMarketSymbol(String market_symbol){
        this.market_symbol = market_symbol;
    }
    public String getMarketType(){
        return this.market_type;
    }
    public void setMarketType(String market_type){
        this.market_type = market_type;
    }

    public String getMarketChange(){
        return this.market_change;
    }
    public void setMarketChange(String market_change){
        this.market_change = market_change;
    }

}
