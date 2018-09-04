package airhawk.com.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_date;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_high;
import static airhawk.com.myapplication.Constructor_App_Variables.graph_volume;
import static airhawk.com.myapplication.Database_Local_Aequities.TAG;

import java.util.ArrayList;
public class Adapter_Graph_Points extends RecyclerView.Adapter<Adapter_Graph_Points.MyViewHolder> {

    ArrayList graph_h;
    ArrayList graph_v;
    ArrayList graph_d;
    Integer integer;
    Context context;
    double saved_volume =0;
    double saved_high=0;

    public Adapter_Graph_Points(Context context, Integer integer, ArrayList graph_h, ArrayList graph_v, ArrayList graph_d) {
        this.graph_h = graph_h;
        this.graph_v = graph_v;
        this.graph_d = graph_d;
        this.integer =integer;
        this.context=context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView aequity_amount, aequity_volume, aequity_date;
        public MyViewHolder(View itemView) {
            super(itemView);
            aequity_amount = itemView.findViewById(R.id.aequity_amount);
            aequity_volume = itemView.findViewById(R.id.aequity_volume);
            aequity_date = itemView.findViewById(R.id.aequity_date);

            /*No Listener needed for now
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
*/
        }}
            @Override
    public Adapter_Graph_Points.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historical_row, parent, false);
        return new Adapter_Graph_Points.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Graph_Points.MyViewHolder holder, int position) {
        if(position<graph_date.size()){
         holder.aequity_amount.setText("$ "+graph_high.get(position));
         Double as = Double.parseDouble(String.valueOf(graph_high.get(position)));

         if(as>saved_high)
         {
         holder.aequity_amount.setTextColor(Color.parseColor("#00ff00"));
         }
         else
         {
         holder.aequity_amount.setTextColor(Color.parseColor("#ff0000"));
         }
         saved_high =as;


         String s = (String) graph_volume.get(position);
         s=s.replace(",","");
         s=s.replace("-","");
         if (s!=null) {
             Double a = Double.parseDouble(s);
             holder.aequity_volume.setText("" + graph_volume.get(position));
             if (a > saved_volume) {
                 holder.aequity_volume.setTextColor(Color.parseColor("#00ff00"));
             } else {
                 holder.aequity_volume.setTextColor(Color.parseColor("#ff0000"));
             }
             saved_volume = Double.parseDouble(s);
         }

         holder.aequity_date.setText(""+graph_date.get(position));
    }}

    @Override
    public int getItemCount() {

    return integer;
    }






         }

