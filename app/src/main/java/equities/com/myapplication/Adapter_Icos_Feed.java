package equities.com.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static equities.com.myapplication.Constructor_App_Variables.ico_enddate;
import static equities.com.myapplication.Constructor_App_Variables.ico_message;
import static equities.com.myapplication.Constructor_App_Variables.ico_name;
import static equities.com.myapplication.Constructor_App_Variables.ico_startdate;

public class Adapter_Icos_Feed extends RecyclerView.Adapter<Adapter_Icos_Feed.MyViewHolder> {
    static String z;
    static ArrayList URLs = new ArrayList();
    static MyViewHolder.myClickWebView myClickWebView;
   List<Constructor_Icos> ico_feedItems;
   static Context context;
   public Adapter_Icos_Feed(Context context, List<Constructor_Icos> items){
     this.context=context;  
     this.ico_feedItems = items;
   }  
   
   @Override  
   public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     LayoutInflater inflater = LayoutInflater.from(parent.getContext());
     final View view = inflater.inflate(R.layout.recyclerview_icos, parent, false);

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
//     holder.name.setText(""+ico_name.get(position));
 //    holder.message.setText(""+ico_message.get(position));
//     holder.start.setText(""+ico_startdate.get(position));
 //    holder.end.setText(""+ico_enddate.get(position));
 //      holder.textlink = String.valueOf(ico_name.get(position));
   }  
   
   @Override  
   public int getItemCount() {  
     return ico_name.size();
   }  
   
   public static class MyViewHolder extends RecyclerView.ViewHolder{
   
       TextView name;
       TextView message;
       TextView start;
       TextView end;
       String textlink;

     public MyViewHolder(View itemView, myClickWebView myClickWebView) {
       super(itemView);

       name=itemView.findViewById(R.id.name);
       message=itemView.findViewById(R.id.message);
       start=itemView.findViewById(R.id.start);
       end=itemView.findViewById(R.id.end);


         myClickWebView=myClickWebView;
         name.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Snackbar sb = Snackbar.make(view, "Searching for "+name.getText().toString()+" website...", Snackbar.LENGTH_INDEFINITE);
                 //sb.setActionTextColor(context.getResources().getColor(R.color.darkTextColor2));
                 View sbView = sb.getView();
                 sbView.setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
                 TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                 textView.setTextColor(context.getResources().getColor(R.color.darkTextColor2));
                 //textView.setMaxLines(10);
                 sb.show();
                 z=textlink.replace("ICO","").replace(" ","");
                 new getURLS(view).execute();
             }
         });

   
     }  
   
     //interface to handle onclick event and send some extras to another activity  
     public interface myClickWebView{

       void gotoWebView(String textlink);

     }  
   }
    public static void find_urls(){
        final String meURL = "https://www."+z+".me";
        final String comURL = "https://www."+z+".com";
        final String ioURL = "https://www."+z+".io";
        final String techURL = "https://www."+z+".tech";

        URLs.add(meURL);
        URLs.add(ioURL);
        URLs.add(techURL);
        URLs.add(comURL);

    }


    public static class getURLS extends AsyncTask<Integer, Integer, String> {
        private View rootView;
        public getURLS(View rootView) {

            this.rootView = rootView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Integer... params) {
            find_urls();
            for(int i =0;i<URLs.size();i++) {
                try {
                    URL url = new URL((String) URLs.get(i));
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("HEAD");
                    con.connect();
                    ////("con.getResponseCode() IS : " + con.getResponseCode());
                    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        //("Success "+URLs.get(i));
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(url)));
                        context.startActivity(browserIntent);
                        URLs.clear();
                        new getURLS(rootView).cancel(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (i>=URLs.size()){
                        new getURLS(rootView).cancel(true);
                        URLs.clear();
                    }else{
                        Snackbar sb = Snackbar.make(rootView,"Still searching for "+ URLs.get(i)+" website...", Snackbar.LENGTH_LONG);
                        //sb.setActionTextColor(context.getResources().getColor(R.color.darkTextColor2));
                        View sbView = sb.getView();
                        sbView.setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(context.getResources().getColor(R.color.darkTextColor2));
                        //textView.setMaxLines(10);
                        sb.show();}//("fail "+URLs.get(i));
                }}
            return "task finished";
        }
        @Override
        protected void onPostExecute(String result) {




        }

    }

}
