package airhawk.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables.aequity_exchanges;
import static airhawk.com.myapplication.Constructor_App_Variables.crypto_exchange_image;
import static airhawk.com.myapplication.Constructor_App_Variables.crypto_exchange_name;
import static airhawk.com.myapplication.Constructor_App_Variables.crypto_exchange_url;
import static airhawk.com.myapplication.Constructor_App_Variables.stock_exchange_image;
import static airhawk.com.myapplication.Constructor_App_Variables.stock_exchange_name;
import static airhawk.com.myapplication.Constructor_App_Variables.stock_exchange_url;

public class Adapter_Exchanges_Feed extends RecyclerView.Adapter<Adapter_Exchanges_Feed.MyViewHolder> {
    int[] basic_crypto_array = new int[] { R.drawable.exchange_crypto_binance, R.drawable.exchange_crypto_coinbase};
    private static Context context;
    private List<Constructor_Exchanges> feedItems;
    public ArrayList<String> e_text=aequity_exchanges;
    public ArrayList<String> s_image=stock_exchange_image;
    public ArrayList<String> c_image=crypto_exchange_image;
    public Adapter_Exchanges_Feed(Context context, List<Constructor_Exchanges> items){
        this.context=context;
        this.feedItems = items;
    }


    @Override
   public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     LayoutInflater inflater = LayoutInflater.from(parent.getContext());
     View view = inflater.inflate(R.layout.view_exchanges, parent, false);

     MyViewHolder mvh = new MyViewHolder(view, new MyViewHolder.myClickWebView() {  
       @Override  
       public void gotoWebView(String textlink) {  
         Intent intent=new Intent(view.getContext(),Activity_Web_View.class);
         intent.putExtra("url",textlink);


           view.getContext().startActivity(intent);
       }  
     });  
     return mvh;  
   }  
   
   @Override  
   public void onBindViewHolder(MyViewHolder holder, int position) {


       Constructor_App_Variables ax = new Constructor_App_Variables();
       String a = ax.getMarketType();
       aequity_exchanges = new ArrayList<String>(new LinkedHashSet<String>(aequity_exchanges));
       if (a.equals("Cryptocurrency") || a.equals("Crypto"))
       {

           holder.ex_text.setText(""+ aequity_exchanges.get(position));
                   String g = (String) "exchange_crypto_"+aequity_exchanges.get(position).toString().toLowerCase();
           int productImageId = context.getResources().getIdentifier(
                   g, "drawable", context.getPackageName());

                   Picasso.with(context).load(productImageId).memoryPolicy(MemoryPolicy.NO_CACHE).fit().centerInside().noFade().into(holder.ex_image);

       }
       if (a.equalsIgnoreCase("Stock")
               ||a.equalsIgnoreCase("Nasdaq")
               ||a.equalsIgnoreCase("Nyse")
               ||a.equalsIgnoreCase("ETF")
               ||a.equalsIgnoreCase("OTC")
               ||a.equalsIgnoreCase("Amex")
               ||a.equalsIgnoreCase("SPDR S&P 500")){

           holder.ex_text.setText(""+ aequity_exchanges.get(position));
             //("Image is good "+s_image.get(position));
               int productImageId = context.getResources().getIdentifier(
                      s_image.get(position), "drawable", context.getPackageName());
               Picasso.with(context).load(productImageId).memoryPolicy(MemoryPolicy.NO_CACHE).fit().centerInside().noFade().into(holder.ex_image);
          }







   }
   
   @Override  
   public int getItemCount() {
     return aequity_exchanges.size();
   }  
   
   public static class MyViewHolder extends RecyclerView.ViewHolder{

       TextView ex_text;
       ImageView ex_image;
       myClickWebView myClickWebView;
       String textlink;
     public MyViewHolder(View itemView, final myClickWebView myClickWebView) {
       super(itemView);
       ex_image =itemView.findViewById(R.id.exchange_image);
       ex_text=itemView.findViewById(R.id.exchange_text);


       this.myClickWebView=myClickWebView;

         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (ap_info.getMarketType().equals("Cryptocurrency") || ap_info.getMarketType().equals("Crypto")) {
                 for (int i = 0; i< crypto_exchange_name.size(); i++){
                     if (crypto_exchange_name.get(i).equals(ex_text.getText().toString())){
                         textlink=""+ crypto_exchange_url.get(i);
                         //(textlink);
                     }
                 }}else{
                     for (int i = 0; i< stock_exchange_name.size(); i++){
                         if (stock_exchange_name.get(i).equals(ex_text.getText().toString())){
                             textlink=""+ stock_exchange_url.get(i);}
                         //(textlink);
                 }}
                 goToWebPage(textlink);

             }
         });



   
     }
   
     //interface to handle onclick event and send some extras to another activity  
     public interface myClickWebView{

       void gotoWebView(String textlink);

     }
   }
    public static void goToWebPage(String yourUrl)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(yourUrl));
        context.startActivity(intent);
    }
 }