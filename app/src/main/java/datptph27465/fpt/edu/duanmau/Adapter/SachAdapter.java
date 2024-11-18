package datptph27465.fpt.edu.duanmau.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import datptph27465.fpt.edu.duanmau.Models.Sach;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.SachDao;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    private final Context context;
    private final List<Sach> sachList;
    datptph27465.fpt.edu.duanmau.dao.SachDao sachDao;

    public interface OnItemClickListener {
        void onDeleteClick(Sach sach);
    }

    public SachAdapter(Context context, List<Sach> sachList) {
        this.context = context;
        this.sachList = sachList;
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        Sach sach = sachList.get(position);
        holder.tvMaSach.setText("Mã sách: " + sach.getMaSach());
        holder.tvTenSach.setText("Tên sách: " + sach.getTenSach());
        holder.tvGiaThue.setText("Giá thuê: " + sach.getGiaThue());
        holder.tvLoaiSach.setText("Loại sách: " + sach.getMaLoai());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog(sach);
            }
        });

    }
    private void showDeleteConfirmationDialog(Sach sach) {
        sachDao = new SachDao(context);
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sách \"" + sach.getTenSach() + "\"?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    sachList.remove(sach);
                    notifyDataSetChanged();
                  int kq =  sachDao.delete(sach.getMaSach());
                  if(kq>0){
                      Toast.makeText(context, "Đã xóa sách: " + sach.getTenSach(), Toast.LENGTH_SHORT).show();
                  }else{
                      Toast.makeText(context, "Xóa thất bại: " + sach.getTenSach(), Toast.LENGTH_SHORT).show();

                  }
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public int getItemCount() {
        return sachList.size();
    }

    public static class SachViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaSach, tvTenSach, tvGiaThue, tvLoaiSach;
        ImageView btnDelete;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            tvLoaiSach = itemView.findViewById(R.id.tvLoaiSach);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
