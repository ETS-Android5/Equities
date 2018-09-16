package airhawk.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static airhawk.com.myapplication.Activity_Main.ap_info;
import static airhawk.com.myapplication.Constructor_App_Variables.aequity_exchanges;
import static airhawk.com.myapplication.Constructor_App_Variables.exchange_image;
import static airhawk.com.myapplication.Constructor_App_Variables.crypto_exchange_name;
import static airhawk.com.myapplication.Constructor_App_Variables.crypto_exchange_url;
import static airhawk.com.myapplication.Constructor_App_Variables.stock_exchange_name;
import static airhawk.com.myapplication.Constructor_App_Variables.stock_exchange_url;

public class Adapter_Exchanges_Feed extends RecyclerView.Adapter<Adapter_Exchanges_Feed.MyViewHolder> {
    int[] basic_crypto_array = new int[] { R.drawable.bin, R.drawable.coinb };
   private static Context context;
    private List<Constructor_Exchanges> feedItems;
    public ArrayList<String> e_text=aequity_exchanges;
    public ArrayList<String> e_image=exchange_image;
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

     holder.ex_text.setText(""+ aequity_exchanges.get(position));
       System.out.println(aequity_exchanges);
     if(exchange_image !=null){
         //System.out.println("Image is good "+exchange_image.get(position));
     //Picasso.with(context).load(basic_crypto_array[position]).memoryPolicy(MemoryPolicy.NO_CACHE).fit().centerInside().noFade().into(holder.ex_image);
     }
     else{
         //System.out.println("Image is not good");
         //Picasso.with(context).load(R.drawable.socialmedia).memoryPolicy(MemoryPolicy.NO_CACHE).noFade().into(holder.ex_image);
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
                     }
                 }}else{
                     for (int i = 0; i< stock_exchange_name.size(); i++){
                         if (stock_exchange_name.get(i).equals(ex_text.getText().toString())){
                             textlink=""+ stock_exchange_url.get(i);}

                 }}
                 myClickWebView.gotoWebView(textlink);

             }
         });



   
     }  
   
     //interface to handle onclick event and send some extras to another activity  
     public interface myClickWebView{

       void gotoWebView(String textlink);

     }  
   }  
   
 }