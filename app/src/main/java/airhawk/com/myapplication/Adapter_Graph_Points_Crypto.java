package airhawk.com.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Graph_Points_Crypto extends RecyclerView.Adapter<Adapter_Graph_Points_Crypto.MyViewHolder> {

    ArrayList graph_high;
    ArrayList graph_volume;
    ArrayList graph_date;
    Context context;


    public Adapter_Graph_Points_Crypto(Context context, ArrayList graph_high, ArrayList graph_volume, ArrayList graph_date) {
        this.graph_high = graph_high;
        this.graph_volume = graph_volume;
        this.graph_date = graph_date;
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
    public Adapter_Graph_Points_Crypto.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historical_row, parent, false);
        return new Adapter_Graph_Points_Crypto.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Graph_Points_Crypto.MyViewHolder holder, int position) {
         holder.aequity_amount.setText("x"+graph_high.get(position));
         holder.aequity_volume.setText("x"+graph_volume.get(position));
         holder.aequity_date.setText("x"+graph_date.get(position));

    }

    @Override
    public int getItemCount() {
        return 10;
    }






         }

