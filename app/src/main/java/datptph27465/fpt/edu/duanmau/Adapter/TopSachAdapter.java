package datptph27465.fpt.edu.duanmau.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import datptph27465.fpt.edu.duanmau.Models.TopSach;
import datptph27465.fpt.edu.duanmau.R;

public class TopSachAdapter extends RecyclerView.Adapter<TopSachAdapter.TopSachViewHolder> {
    private List<TopSach> topSachList;

    public TopSachAdapter(List<TopSach> topSachList) {
        this.topSachList = topSachList;
    }

    @NonNull
    @Override
    public TopSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_sach, parent, false);
        return new TopSachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopSachViewHolder holder, int position) {
        TopSach topSach = topSachList.get(position);
        holder.tenSach.setText(topSach.getTenSach());
        holder.soLanMuon.setText("Số lần mượn: " + topSach.getSoLanMuon());
    }

    @Override
    public int getItemCount() {
        return topSachList.size();
    }

    public static class TopSachViewHolder extends RecyclerView.ViewHolder {
        TextView tenSach, soLanMuon;

        public TopSachViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSach = itemView.findViewById(R.id.tvTenSach);
            soLanMuon = itemView.findViewById(R.id.tvSoLuong);
        }
    }
}
