package datptph27465.fpt.edu.duanmau.Adapter;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import datptph27465.fpt.edu.duanmau.Models.LoaiSach;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.LoaiSachDao;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder>   {
    Context context;
    List<LoaiSach> listLoaiSach;
    datptph27465.fpt.edu.duanmau.dao.LoaiSachDao loaiSachDao;

    public LoaiSachAdapter(Context context, List<LoaiSach> listLoaiSach) {
        this.context = context;
        this.listLoaiSach = listLoaiSach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LoaiSach loaiSach = listLoaiSach.get(position);
            holder.txtMaLoai.setText("Mã Loại Sách:"+loaiSach.getMaLoai());
            holder.txtTenLoai.setText("Tên Loại Sách:"+loaiSach.getTenSach());
//        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDeleteDialog(loaiSach,position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listLoaiSach.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
            TextView txtMaLoai,txtTenLoai;
            ImageView imgEdit,imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtId);
            txtTenLoai = itemView.findViewById(R.id.txt_tenloai);
            imgEdit = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);

        }
    }
    private void showDeleteDialog(LoaiSach loaiSach,int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa loại sách: " + loaiSach.getTenSach() + "?");
        loaiSachDao = new LoaiSachDao(context);
      int isDeleted=  loaiSachDao.delete(loaiSach.getMaLoai());
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            if (isDeleted>0) {
                Toast.makeText(context, "Xóa thành công loại sách: " + loaiSach.getTenSach() , Toast.LENGTH_SHORT).show();
                listLoaiSach.remove(i);
                notifyItemRemoved(i);
            } else {
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        // Nút Hủy
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
