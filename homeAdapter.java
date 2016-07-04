package com.example.ben.itrans;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by helen_000 on 7/2/2016.
 */
public class homeAdapter extends RecyclerView.Adapter<homeAdapter.MyViewHolder> {

    private List<Bus> busServices;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView ETA, busNum;
        public ImageView BusFeature;

        public MyViewHolder(View view){
            super(view);
            busNum = (TextView) view.findViewById(R.id.busNumber);
            ETA = (TextView) view.findViewById(R.id.busTiming);
            BusFeature = (ImageView) view.findViewById(R.id.wheelCA);
        }
    }

    public homeAdapter(List<Bus> busServices){
        this.busServices = busServices;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_home, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Bus bus = busServices.get(position);
        holder.busNum.setText(bus.getBusNo());
        holder.ETA.setText(bus.getBusT());
        if(bus.getBF().isEmpty()){
            holder.BusFeature.setVisibility(View.INVISIBLE);
        }else{
            holder.BusFeature.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount(){
        return busServices.size();
    }
}
