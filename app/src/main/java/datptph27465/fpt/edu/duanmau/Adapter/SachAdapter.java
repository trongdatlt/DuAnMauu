package datptph27465.fpt.edu.duanmau.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import datptph27465.fpt.edu.duanmau.Models.LoaiSach;
import datptph27465.fpt.edu.duanmau.Models.Sach;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.SachDao;
import datptph27465.fpt.edu.duanmau.dao.LoaiSachDao;

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
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditBookDialog(sach);
            }
        });
    }
    List<LoaiSach> loaiSachList;

    private void showEditBookDialog(Sach sachToEdit) {
        // Inflate layout cho dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_book, null);

        EditText etMaSach = dialogView.findViewById(R.id.etMaSach);
        EditText etTenSach = dialogView.findViewById(R.id.etTenSach);
        EditText etGiaThue = dialogView.findViewById(R.id.etGiaThue);
        Spinner spLoaiSach = dialogView.findViewById(R.id.spLoaiSach);

        // Tạo DAO và danh sách loại sách
        LoaiSachDao loaiSachDao = new LoaiSachDao(context);
        loaiSachList = new ArrayList<>();
        List<String> loaiSachNames = new ArrayList<>();

        try {
            loaiSachList = loaiSachDao.getAll(); // Lấy tất cả loại sách từ database
            for (LoaiSach loaiSach : loaiSachList) {
                loaiSachNames.add(loaiSach.getTenSach()); // Giả sử LoaiSach có phương thức getTenLoai()
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Lỗi khi tải dữ liệu loại sách!", Toast.LENGTH_SHORT).show();
        }

        // Gắn adapter cho Spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, loaiSachNames);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaiSach.setAdapter(adapter1);

        // Đặt giá trị mặc định từ sách cần sửa
        etTenSach.setText(sachToEdit.getTenSach());
        etGiaThue.setText(String.valueOf(sachToEdit.getGiaThue()));

        // Đặt loại sách mặc định cho Spinner
        int selectedPosition = 0;
        for (int i = 0; i < loaiSachList.size(); i++) {
            if (loaiSachList.get(i).getMaLoai().equals(sachToEdit.getMaLoai())) {
                selectedPosition = i;
                break;
            }
        }
        spLoaiSach.setSelection(selectedPosition);

        // Lấy mã loại từ Spinner khi chọn
        final String[] selectedMaLoai = {sachToEdit.getMaLoai()}; // Biến lưu trữ mã loại được chọn
        spLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy mã loại dựa trên vị trí được chọn
                selectedMaLoai[0] = loaiSachList.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMaLoai[0] = null; // Không có gì được chọn
            }
        });

        // Hiển thị dialog
        new AlertDialog.Builder(context)
                .setTitle("Chỉnh sửa sách")
                .setView(dialogView)
                .setPositiveButton("Cập nhật", (dialog, which) -> {
                    // Lấy dữ liệu từ các trường
                    String tenSach = etTenSach.getText().toString().trim();
                    String giaThueStr = etGiaThue.getText().toString().trim();

                    if (tenSach.isEmpty() || giaThueStr.isEmpty() || selectedMaLoai[0] == null) {
                        Toast.makeText(context, "Vui lòng nhập đủ thông tin và chọn loại sách!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        int giaThue = Integer.parseInt(giaThueStr);

                        // Cập nhật đối tượng sách
                        sachToEdit.setTenSach(tenSach);
                        sachToEdit.setGiaThue(giaThue);
                        sachToEdit.setMaLoai(selectedMaLoai[0]);

                        // Cập nhật sách trong database
                        SachDao sachDao = new SachDao(context);
                        int kq = sachDao.update(sachToEdit);
                        if (kq > 0) {
                            Toast.makeText(context, "Đã cập nhật sách: " + tenSach, Toast.LENGTH_SHORT).show();
                            sachList.clear();
                            sachList.addAll(sachDao.getAll());
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Cập nhật sách thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, "Giá thuê phải là số!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
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
        ImageView btnDelete,btnEdit;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            tvLoaiSach = itemView.findViewById(R.id.tvLoaiSach);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);

        }
    }
}
