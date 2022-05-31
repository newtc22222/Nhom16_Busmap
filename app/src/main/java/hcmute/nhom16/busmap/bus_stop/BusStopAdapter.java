package hcmute.nhom16.busmap.bus_stop;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.model.BusStop;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.BusStopHolder> {
    private Context context;
    private List<BusStop> busStops;
    private BusStopHolder preHolder = null;
    private boolean order = false;

    public BusStopAdapter(Context context, List<BusStop> busStops, boolean order) {
        this.busStops = busStops;
        this.context = context;
        this.order = order;
    }

    @NonNull
    @Override
    public BusStopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BusStopHolder(LayoutInflater.from(context).inflate(R.layout.bus_stop_item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BusStopHolder holder, int position) {
        if (!order) {
            holder.line_vertical_end.setVisibility(View.INVISIBLE);
            holder.line_vertical_first.setVisibility(View.INVISIBLE);
            holder.order_first.setVisibility(View.INVISIBLE);
        } else {
            if (position == 0) {
                holder.line_vertical_end.setVisibility(View.INVISIBLE);
                holder.order_first.setVisibility(View.VISIBLE);
            }
            if (position == busStops.size() - 1) {
                holder.line_vertical_first.setVisibility(View.INVISIBLE);
                holder.order_first.setVisibility(View.VISIBLE);
            }
        }

        holder.tv_name.setText(busStops.get(position).getName());
        holder.tv_time.setText(busStops.get(position).getTimePassString());

        holder.itemView.setOnClickListener(v -> {
            holder.order.setVisibility(View.VISIBLE);
            holder.detail.setVisibility(View.VISIBLE);
            if (preHolder != null && preHolder != holder) {
                preHolder.order.setVisibility(View.INVISIBLE);
                preHolder.detail.setVisibility(View.INVISIBLE);
            }
            preHolder = holder;
        });

        holder.detail.setOnClickListener(v -> {
            onDetailClick(position);
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void onDetailClick(int position) {
        Intent intent = new Intent(context, BusStopActivity.class);
        intent.putExtra("bus_stop", busStops.get(position));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return busStops == null ? 0 : busStops.size();
    }

    public class BusStopHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_time;
        View order, order_first, line_vertical_first, line_vertical_end;
        LinearLayout detail;

        public BusStopHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            detail = itemView.findViewById(R.id.detail);
            order = itemView.findViewById(R.id.order);
            order_first = itemView.findViewById(R.id.order_first);
            line_vertical_first = itemView.findViewById(R.id.line_vertical_first);
            line_vertical_end = itemView.findViewById(R.id.line_vertical_end);
        }
    }
}
