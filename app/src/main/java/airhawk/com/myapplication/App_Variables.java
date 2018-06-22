package airhawk.com.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian Dinkins on 6/13/2018.
 */

public class App_Variables {

    static ArrayList video_url = new ArrayList<>();
    static ArrayList image_video_url = new ArrayList<>();
    static ArrayList video_title = new ArrayList<>();
    protected static List<String> graph_date = new ArrayList<>();
    protected static List<String> graph_high = new ArrayList<>();
    protected static List<String> graph_low = new ArrayList<>();
    protected static List<String> graph_volume = new ArrayList<>();
    protected static List<String> graph_market_cap = new ArrayList<>();
    protected static List<String> _1_Day_Chart =new ArrayList();
    protected static List<String> _7Days =new ArrayList();
    protected static List<String> _30Days =new ArrayList();
    protected static List<String> _90Days =new ArrayList();
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
    protected static ArrayList<News_FeedItem> feedItems = new ArrayList<>();
    protected static List<String> _AllDays =new ArrayList();
    protected static ArrayList<Historical_Data_Model> dataModels;
    public static String market_symbol;
    public static String market_name;
    public static String market_type;
    protected static String newname;

    public App_Variables(){}

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


}
