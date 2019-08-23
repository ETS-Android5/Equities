package equities.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static equities.com.myapplication.Constructor_App_Variables.crypto_market_news_feedItems;

public class Adapter_Crypto_News_Feed extends RecyclerView.Adapter<Adapter_Crypto_News_Feed.MyViewHolder> {

   private List<Constructor_News_Feed> crypto_market_news_feedItems;
   private static Context context;
   public Adapter_Crypto_News_Feed(Context context, List<Constructor_News_Feed> items){
     this.context=context;  
     this.crypto_market_news_feedItems = items;
   }  
   
   @Override  
   public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     LayoutInflater inflater = LayoutInflater.from(parent.getContext());
     final View view = inflater.inflate(R.layout.recyclerview_app_news, parent, false);

     MyViewHolder mvh = new MyViewHolder(view, new MyViewHolder.myClickWebView() {  
       @Override  
       public void gotoWebView(String textlink) {  
         Intent intent=new Intent(view.getContext(),Activity_Web_View.class);
         intent.putExtra("url",textlink);
           //("NEWS "+textlink);
           view.getContext().startActivity(intent);
       }  
     });  
     return mvh;  
   }  
   
   @Override  
   public void onBindViewHolder(MyViewHolder holder, int position) {  
     Constructor_News_Feed item = crypto_market_news_feedItems.get(position);
     holder.tv_pubdate.setText(item.getPubDate());  
     holder.tv_title.setText(item.getTitle());
     holder.tv_link.setPaintFlags(holder.tv_link.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
     holder.tv_link.setText(item.getLink());
     holder.tv_source.setText(item.getSource());


     if(item.getThumbnailUrl() !=null){
     Picasso.with(context).load(item.getThumbnailUrl()).memoryPolicy(MemoryPolicy.NO_CACHE).noFade().into(holder.tv_image);}
     else{Picasso.with(context).load(R.drawable.direction_news).memoryPolicy(MemoryPolicy.NO_CACHE).noFade().into(holder.tv_image);}
     holder.textlink=item.getLink();
   }  
   
   @Override  
   public int getItemCount() {  
     return crypto_market_news_feedItems==null?0:crypto_market_news_feedItems.size();
   }  
   
   public static class MyViewHolder extends RecyclerView.ViewHolder{
   
       TextView tv_title;
       TextView tv_pubdate;
       ImageView tv_image;
       TextView tv_link;
       TextView tv_source;
       String textlink;
       myClickWebView myClickWebView;
   
     public MyViewHolder(View itemView, final myClickWebView myClickWebView) {  
       super(itemView);

       tv_title=itemView.findViewById(R.id.news_title);
       tv_pubdate=itemView.findViewById(R.id.news_pubdate);
       tv_image=itemView.findViewById(R.id.news_image);
       tv_link=itemView.findViewById(R.id.news_link);
         tv_source=itemView.findViewById(R.id.news_source);
       this.myClickWebView=myClickWebView;  
   
       itemView.setOnClickListener(new View.OnClickListener() {  
         @Override  
         public void onClick(View view) {
             Intent intent=new Intent(view.getContext(),Activity_Web_View.class);
             intent.putExtra("url",textlink);


             view.getContext().startActivity(intent);
         }  
       });



   
     }  
   
     //interface to handle onclick event and send some extras to another activity  
     public interface myClickWebView{

       void gotoWebView(String textlink);

     }  
   }

 }
