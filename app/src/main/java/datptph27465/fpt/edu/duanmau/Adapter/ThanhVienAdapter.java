package datptph27465.fpt.edu.duanmau.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import datptph27465.fpt.edu.duanmau.Models.ThanhVien;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.ThanhVienDao;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    private Context context;
    private List<ThanhVien> thanhVienList;

    public ThanhVienAdapter(Context context, List<ThanhVien> thanhVienList) {
        this.context = context;
        this.thanhVienList = thanhVienList;
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.thanhvien_item, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = thanhVienList.get(position);
        datptph27465.fpt.edu.duanmau.dao.ThanhVienDao thanhvienDao = new ThanhVienDao(context);
        holder.tvMaTV.setText(String.valueOf(thanhVien.getMaTV()));
        holder.tvTenTV.setText(thanhVien.getHoTen());
        holder.tvNamSinh.setText(String.valueOf(thanhVien.getNamSinh()));
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa " + thanhVien.getHoTen() + " không?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int kq = thanhvienDao.delete(String.valueOf(thanhVien.getMaTV()));
                                if (kq > 0) {
                                    Toast.makeText(context, "Xóa thành công " + thanhVien.getHoTen(), Toast.LENGTH_SHORT).show();
                                    thanhVienList.remove(position);
                                    notifyItemRemoved(position);
                                } else {
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Hủy", null) // Dismisses the dialog when "Hủy" is clicked
                        .show();
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditMemberDialog(thanhVien);

            }
        });

    }

    @Override
    public int getItemCount() {
        return thanhVienList.size();
    }
    private void showEditMemberDialog(ThanhVien memberToEdit) {
        // Khởi tạo AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_member, null);
        builder.setView(dialogView);

        // Lấy các View từ dialog
        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etYearOfBirth = dialogView.findViewById(R.id.etYearOfBirth);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        // Hiển thị thông tin thành viên cần sửa
        etName.setText(memberToEdit.getHoTen());
        etYearOfBirth.setText(String.valueOf(memberToEdit.getNamSinh()));

        AlertDialog dialog = builder.create();

        // Xử lý nút Hủy
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Xử lý nút Lưu (Cập nhật)
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String yearOfBirthStr = etYearOfBirth.getText().toString().trim();

            if (validateInputs(etName, etYearOfBirth)) {
                if (name.isEmpty() || yearOfBirthStr.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int yearOfBirth = Integer.parseInt(yearOfBirthStr);
                        // Cập nhật thông tin thành viên
                        memberToEdit.setHoTen(name);
                        memberToEdit.setNamSinh(yearOfBirth);
            ThanhVienDao thanhVienDao = new ThanhVienDao(context);

                        int result = thanhVienDao.update(memberToEdit);
                        if (result > 0) {
                            thanhVienList.clear();
                            thanhVienList.addAll(thanhVienDao.getAll()); // Lấy lại danh sách thành viên
                            notifyDataSetChanged();
                            Toast.makeText(context, "Cập nhật thành công " + name, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, "Năm sinh phải là số!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialog.show();
    }
    private boolean validateInputs(EditText etName, EditText etYearOfBirth) {
        String name = etName.getText().toString().trim();
        String yearOfBirthStr = etYearOfBirth.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Vui lòng nhập tên");
            etName.requestFocus();
            return false;
        }

        if (yearOfBirthStr.isEmpty()) {
            etYearOfBirth.setError("Vui lòng nhập năm sinh");
            etYearOfBirth.requestFocus();
            return false;
        }

        try {
            int yearOfBirth = Integer.parseInt(yearOfBirthStr);
            if (yearOfBirth < 1900 || yearOfBirth > 2100) {  // Giới hạn năm hợp lý
                etYearOfBirth.setError("Năm sinh không hợp lệ");
                etYearOfBirth.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            etYearOfBirth.setError("Năm sinh phải là số");
            etYearOfBirth.requestFocus();
            return false;
        }

        return true;  // Nếu tất cả các kiểm tra đều hợp lệ
    }
    public static class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaTV, tvTenTV, tvNamSinh;
        ImageView imgDelete,imgEdit;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTV = itemView.findViewById(R.id.tvMaTV);
            tvTenTV = itemView.findViewById(R.id.tvTenTV);
            tvNamSinh = itemView.findViewById(R.id.tvNamSinh);
            imgDelete = itemView.findViewById(R.id.imgDeleteLS);
            imgEdit = itemView.findViewById(R.id.img_edit);
        }
    }

    public interface OnItemClickListener {
        void onDeleteClick(ThanhVien thanhVien);
    }
}
