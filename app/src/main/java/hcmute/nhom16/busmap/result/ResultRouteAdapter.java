package hcmute.nhom16.busmap.result;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.ResultRoute;

public class ResultRouteAdapter extends RecyclerView.Adapter<ResultRouteAdapter.ResultRouteHolder> {
    Context context;
    List<ResultRoute> result_routes;
    Address from, to;

    public ResultRouteAdapter(Context context, List<ResultRoute> routes, Address from, Address to) {
        this.context = context;
        this.result_routes = routes;
        this.from = from;
        this.to = to;
    }

    public void setResult_routes(List<ResultRoute> resultRoutes) {
        this.result_routes = resultRoutes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultRouteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultRouteHolder(LayoutInflater.from(context).inflate(R.layout.result_route, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultRouteHolder holder, int position) {
        holder.tv_id.setText(result_routes.get(position).getRoute().getRouteName());
        holder.tv_name.setText(result_routes.get(position).getRoute().getName());
        holder.tv_time.setText(result_routes.get(position).getRoute().getOperatingTime());
        holder.tv_money.setText(result_routes.get(position).getRoute().getMoney());
        holder.tv_walk.setText(result_routes.get(position).getWalkDistance());
        holder.tv_bus.setText(result_routes.get(position).getBusDistance());
        holder.tv_repeat.setText(result_routes.get(position).getRoute().getRepeat());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ResultRouteDetailActivity.class);
            intent.putExtra("result_route", result_routes.get(position));
            intent.putExtra("from", from);
            intent.putExtra("to", to);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return result_routes.size();
    }

    public class ResultRouteHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_name, tv_time, tv_money, tv_walk, tv_bus, tv_repeat;
        public ResultRouteHolder(@NonNull View itemView) {
            super(itemView);
            tv_repeat = itemView.findViewById(R.id.tv_repeat);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_walk = itemView.findViewById(R.id.tv_walk);
            tv_bus = itemView.findViewById(R.id.tv_bus);
        }
    }
}
