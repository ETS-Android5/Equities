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
    protected static String cry1;
    protected static String cry2;
    protected static String cry3;
    protected static String cry4;
    protected static String cry5;
    protected static String sto1;
    protected static String sto2;
    protected static String sto3;
    protected static String sto4;
    protected static String sto5;
    protected static String sto_1;
    protected static String sto_2;
    protected static String sto_3;
    protected static String sto_4;
    protected static String sto_5;
    protected static String cry_1;
    protected static String cry_2;
    protected static String cry_3;
    protected static String cry_4;
    protected static String cry_5;
    protected static String cry_vol1;
    protected static String cry_vol2;
    protected static String cry_vol3;
    protected static String cry_vol4;
    protected static String cry_vol5;
    protected static ArrayList<News_FeedItem> feedItems = new ArrayList<>();
    protected static List<String> _AllDays =new ArrayList();
    protected static ArrayList<Historical_Data_Model> dataModels;
    public static String market_name;
    protected static String newname;
    private String KEY_AEQUITY_SYMBOL;
    private String KEY_AEQUITY_NAME;
    private String KEY_AEQUITY_TYPE;
    private String KEY_AEQUITY_CURRENT_PRICE;
    private String KEY_AEQUITY_PERCENT_CHANGE;

    public App_Variables(){}
    public App_Variables(String KEY_EQUITY_SYMBOL, String KEY_EQUITY_NAME, String KEY_EQUITY_TYPE){

        this.KEY_AEQUITY_SYMBOL=KEY_EQUITY_SYMBOL;
        this.KEY_AEQUITY_NAME=KEY_EQUITY_NAME;
        this.KEY_AEQUITY_TYPE=KEY_EQUITY_TYPE;

    }

    public String getAequity_Name(){

        return this.KEY_AEQUITY_NAME;
    }
    public void setAequity_Name(String KEY_EQUITY_NAME){

        this.KEY_AEQUITY_NAME = KEY_EQUITY_NAME;
    }
    public String getAequity_Symbol(){

        return this.KEY_AEQUITY_SYMBOL;
    }
    public void setEquity_Symbol(String KEY_EQUITY_SYMBOL){

        this.KEY_AEQUITY_SYMBOL = KEY_EQUITY_SYMBOL;
    }
    public String getAequity_Type(){

        return this.KEY_AEQUITY_TYPE;
    }
    public void setAequity_Type(String KEY_EQUITY_TYPE){

        this.KEY_AEQUITY_TYPE = KEY_EQUITY_TYPE;
    }
    public String getAequity_Current_Price(){

        return this.KEY_AEQUITY_CURRENT_PRICE;
    }
    public void setAequity_Current_Price(String KEY_EQUITY_CURRENT_PRICE){

        this.KEY_AEQUITY_CURRENT_PRICE = KEY_EQUITY_CURRENT_PRICE;
    }

}
