package airhawk.com.myapplication.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static airhawk.com.myapplication.App_Variables.all_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.alt_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.btc_market_cap_amount;
import static airhawk.com.myapplication.App_Variables.btc_market_cap_change;
import static airhawk.com.myapplication.App_Variables.dow_amount;
import static airhawk.com.myapplication.App_Variables.dow_change;
import static airhawk.com.myapplication.App_Variables.dow_name;
import static airhawk.com.myapplication.App_Variables.nas_amount;
import static airhawk.com.myapplication.App_Variables.nas_change;
import static airhawk.com.myapplication.App_Variables.nas_name;
import static airhawk.com.myapplication.App_Variables.sp_amount;
import static airhawk.com.myapplication.App_Variables.sp_change;
import static airhawk.com.myapplication.App_Variables.sp_name;

public class MarketsContent {

    public static final List<MarketsItem> ITEMS = new ArrayList<MarketsItem>();
    public static final Map<String, MarketsItem> ITEM_MAP = new HashMap<String, MarketsItem>();
    static String bc = "Bitcoin";
    static String ac = "All Crypto";
    static String atc= "Alt Cap";
    static String ipo="IPO's";
    static String ico="ICO's";
    static String[] int_list = new String[]{dow_amount,sp_amount,nas_amount,btc_market_cap_amount,all_market_cap_amount,alt_market_cap_amount,"7","7"};
    static String[] market_list = new String[]{dow_name, sp_name, nas_name, bc, ac, atc,ipo,ico};
    static String[] change_list = new String[]{dow_change,sp_change, nas_change, btc_market_cap_change,"FIX","FIX","Upcoming","Upcoming"};
    static {
        for (int i = 0; i < market_list.length; i++) {
            addItem(createDummyItem(market_list[i],int_list[i],change_list[i]));
        }
      }

    private static void addItem(MarketsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static MarketsItem createDummyItem(String position, String iel, String s) {
        return new MarketsItem(position,  iel, position,s);
    }


    public static class MarketsItem {
        public final String id;
        public final String content;
        public final String change;
        public final String details;

        public MarketsItem(String id, String content, String details,String change) {
            this.id = id;
            this.content = String.valueOf(content);
            this.details = details;
            this.change =change;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
