package airhawk.com.myapplication;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Julian Dinkins on 6/13/2018.
 */

public class Constructor_App_Variables {
    static Set<String> kracken_list;

    static ArrayList binance_list=new ArrayList<>();
    static ArrayList exchange_list=new ArrayList<>();
    //static ArrayList binance_list=new ArrayList<>();
    static ArrayList crypto_exchange_url =new ArrayList<>();
    static ArrayList crypto_exchange_name =new ArrayList<>();
    static ArrayList stock_exchange_url =new ArrayList<>();
    static ArrayList stock_exchange_name =new ArrayList<>();
    static ArrayList aequity_exchanges =new ArrayList();
    static ArrayList exchange_text=new ArrayList<>();
    static ArrayList crypto_exchange_image=new ArrayList<>();
    static ArrayList stock_exchange_image=new ArrayList<>();
    static ArrayList exchange_message=new ArrayList<>();
    protected static ArrayList<Constructor_Exchanges> exchanges_feedItems = new ArrayList<>();
    static ArrayList video_url = new ArrayList<>();
    static ArrayList image_video_url = new ArrayList<>();
    static ArrayList video_title = new ArrayList<>();
    static ArrayList Smessage_link=new ArrayList<>();
    static ArrayList Smessage_time=new ArrayList<>();
    static ArrayList Suser_name=new ArrayList<>();
    static ArrayList Smessage=new ArrayList<>();
    static ArrayList Simg_url=new ArrayList<>();
    public static ArrayList current_percentage_change = new ArrayList<>();
    public static ArrayList graph_date_today = new ArrayList<>();
    public static ArrayList graph_high_today = new ArrayList<>();
    public static ArrayList graph_volume_today = new ArrayList<>();
    public static ArrayList graph_date = new ArrayList<>();
    public static ArrayList graph_high = new ArrayList<>();
    public static ArrayList graph_change = new ArrayList<>();
    public static ArrayList graph_low = new ArrayList<>();
    public static ArrayList graph_volume = new ArrayList<>();
    public static ArrayList graph_market_cap = new ArrayList<>();
    public static boolean dow_flow;
    public static boolean sp_flow;
    public static boolean nd_flow;
    public static boolean btc_flow;
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
    protected static ArrayList<Constructor_Stock_Twits> stocktwits_feedItems = new ArrayList<>();
    protected static ArrayList<Constructor_News_Feed> all_feedItems = new ArrayList<>();
    public static List<String> _AllDays =new ArrayList();
    public static String market_symbol;
    public static String market_name;
    public static String market_change;
    public static String market_type;
    public static String market_supply;
    public static String current_aequity_price;
    public static String current_aequity_price_change;
    public static String market_cap;
    public static String binan="https://www.binance.com/?ref=13398675";
    public static String cobe="https://www.coinbase.com/join/5a2cc6b6f3b80300ef643aa4";
    public static String rbh="http://share.robinhood.com/juliand141";
    public static ArrayList cpr;


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
    public String getCurrent_Aequity_Price(){ return this.current_aequity_price; }
    public void setCurrent_Aequity_Price(String current_aequity_price){
        this.current_aequity_price=current_aequity_price;
    }

    public String getCurrent_Aequity_Price_Change(){ return this.current_aequity_price_change; }
    public void setCurrent_Aequity_Price_Change(String current_aequity_price_change){
        this.current_aequity_price_change=current_aequity_price_change;
    }

    public String getMarketChange(){
        return this.market_change;
    }
    public void setMarketChange(String market_change){
        this.market_change = market_change;
    }


    public String getMarketCap(){
        return this.market_cap;
    }
    public void setMarketCap(String market_cap){
        this.market_cap = market_cap;
    }


    public String getMarketSupply(){
        return this.market_supply;
    }
    public void setMarketSupply(String market_supply){
        this.market_supply = market_supply;
    }


}
