package hcmute.nhom16.busmap.result;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.nhom16.busmap.R;
import hcmute.nhom16.busmap.model.Address;
import hcmute.nhom16.busmap.model.Result;
import hcmute.nhom16.busmap.route.RouteIconAdapter;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> {
    Context context;
    List<Result> results;
    Address from, to;

    public ResultAdapter(Context context, List<Result> routes, Address from, Address to) {
        this.context = context;
        this.results = routes;
        this.from = from;
        this.to = to;
    }

    public void setResult_routes(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultHolder(LayoutInflater.from(context).inflate(R.layout.result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        holder.tv_time.setText(results.get(position).getTimePass());
        holder.tv_money.setText(results.get(position).getMoney());
        holder.tv_walk.setText(results.get(position).getWalkDistance());
        holder.tv_bus.setText(results.get(position).getBusDistance());

        RouteIconAdapter adapter = new RouteIconAdapter(context, results.get(position).getResult_routes());
        holder.rv_routes_icon.setAdapter(adapter);
        holder.rv_routes_icon.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ResultDetailActivity.class);
            intent.putExtra("result", results.get(position));
            intent.putExtra("from", from);
            intent.putExtra("to", to);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ResultHolder extends RecyclerView.ViewHolder {
        TextView tv_time, tv_money, tv_walk, tv_bus;
        RecyclerView rv_routes_icon;
        public ResultHolder(@NonNull View itemView) {
            super(itemView);
            rv_routes_icon = itemView.findViewById(R.id.rv_routes_icon);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_walk = itemView.findViewById(R.id.tv_walk);
            tv_bus = itemView.findViewById(R.id.tv_bus);
        }
    }
}
