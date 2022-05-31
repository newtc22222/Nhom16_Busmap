package hcmute.nhom16.busmap.result;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.model.BusStopGuide;
import hcmute.nhom16.busmap.model.ResultRoute;

public class ResultBusStopGuideAdapter extends RecyclerView.Adapter<ResultBusStopGuideAdapter.BusStopGuideHolder> {
    Context context;
    List<BusStopGuide> bus_stop_guides;
    BusStopGuideHolder preHolder = null;

    public ResultBusStopGuideAdapter(Context context, List<BusStopGuide> bus_stop_guides) {
        this.context = context;
        this.bus_stop_guides = bus_stop_guides;
    }
    @NonNull
    @Override
    public BusStopGuideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BusStopGuideHolder(LayoutInflater.from(context).inflate(R.layout.bus_stop_result, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull BusStopGuideHolder holder, int position) {
        if (bus_stop_guides.get(position).getType() == MoveType.BUS) {
            if (position > 0 && bus_stop_guides.get(position - 1).getType() == MoveType.WALK) {
                holder.line_vertical_end.setVisibility(View.INVISIBLE);
                holder.order_first.setVisibility(View.VISIBLE);
            }
            if (position < bus_stop_guides.size() && bus_stop_guides.get(position + 1).getType() == MoveType.WALK) {
                holder.line_vertical_first.setVisibility(View.INVISIBLE);
                holder.order_first.setVisibility(View.VISIBLE);
            }
        } else {
            holder.order_black.setVisibility(View.VISIBLE);
            holder.line_vertical_first.setVisibility(View.INVISIBLE);
            holder.line_vertical_end.setVisibility(View.INVISIBLE);
        }

        holder.tv_route.setText(bus_stop_guides.get(position).getRoute());
        holder.tv_name.setText(bus_stop_guides.get(position).getName());

        holder.itemView.setOnClickListener(v -> {
            holder.order.setVisibility(View.VISIBLE);
            if (preHolder != null) {
                preHolder.order.setVisibility(View.INVISIBLE);
            }
            preHolder = holder;
        });
    }
    
    @Override
    public int getItemCount() {
        return bus_stop_guides == null ? 0 : bus_stop_guides.size();
    }

    public class BusStopGuideHolder extends RecyclerView.ViewHolder {
        View line_vertical_first, line_vertical_end, order_first, order_black, order;
        TextView tv_route, tv_name;
        public BusStopGuideHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_route = itemView.findViewById(R.id.tv_route);
            order = itemView.findViewById(R.id.order);
            order_black = itemView.findViewById(R.id.order_black);
            order_first = itemView.findViewById(R.id.order_first);
            line_vertical_end = itemView.findViewById(R.id.line_vertical_end);
            line_vertical_first = itemView.findViewById(R.id.line_vertical_first);
        }
    }
}
