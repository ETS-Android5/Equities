package equities.com.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Julian Dinkins on 6/13/2018.
 */

public class Constructor_App_Variables {
    static ArrayList masternode_name=new ArrayList<>();
    static ArrayList masternode_symbol=new ArrayList<>();
    static ArrayList masternode_percent_change=new ArrayList<>();
    static ArrayList masternode_marketcap=new ArrayList<>();
    static ArrayList masternode_node_count=new ArrayList<>();
    static ArrayList masternode_purchase_value=new ArrayList<>();
    static Set<String> kracken_list;

    static ArrayList binance_list=new ArrayList<>();
    static ArrayList exchange_list=new ArrayList<>();
    static ArrayList ico_name=new ArrayList<>();
    static ArrayList ico_message=new ArrayList<>();
    static ArrayList ico_startdate=new ArrayList<>();
    static ArrayList ico_enddate=new ArrayList<>();

    static ArrayList ipo_name=new ArrayList();
    static ArrayList ipo_range=new ArrayList();
    static ArrayList ipo_date =new ArrayList();
    static ArrayList ipo_volume =new ArrayList();
    static int saved_helper;

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
    public static ArrayList current_updated_price = new ArrayList<>();
    public static ArrayList graph_date_today = new ArrayList<>();
    public static ArrayList graph_high_today = new ArrayList<>();
    public static ArrayList graph_volume_today = new ArrayList<>();
    public static ArrayList graph_date = new ArrayList<>();
    public static ArrayList graph_high = new ArrayList<>();
    public static ArrayList graph_24 = new ArrayList<>();
    public static ArrayList reversed_graph_high = new ArrayList<>();
    public static ArrayList graph_open = new ArrayList<>();
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
    public static String bov_name;
    public static String bov_amount;
    public static String bov_change;

    public static String shse_name;
    public static String shse_amount;
    public static String shse_change;

    public static String hang_name;
    public static String hang_amount;
    public static String hang_change;

    public static String nikk_name;
    public static String nikk_amount;
    public static String nikk_change;

    public static String ftse_name;
    public static String ftse_amount;
    public static String ftse_change;

    public static String cac_name;
    public static String cac_amount;
    public static String cac_change;

    public static String dax_name;
    public static String dax_amount;
    public static String dax_change;




    public static String btc_market_cap_amount;
    public static String btc_market_cap_change;
    protected static ArrayList<Constructor_Ipos> ipo_feedItems =new ArrayList<>();

    protected static ArrayList<Constructor_Icos> ico_feedItems =new ArrayList<>();
    public static ArrayList<Constructor_News_Feed> feedItems = new ArrayList<>();
    protected static ArrayList<Constructor_Stock_Twits> stocktwits_feedItems = new ArrayList<>();
    protected static ArrayList<Constructor_Stock_Equities> stock_losers_feedItems = new ArrayList<>();
    protected static ArrayList<Constructor_Stock_Equities> crypto_losers_feedItems = new ArrayList<>();

    protected static ArrayList<Constructor_Masternodes> masternode_feedItems = new ArrayList<>();
    protected static ArrayList<Constructor_News_Feed> all_feedItems = new ArrayList<>();
    protected static ArrayList<Constructor_Crypto_Equities> crypto_loser_feedItems = new ArrayList<>();
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
