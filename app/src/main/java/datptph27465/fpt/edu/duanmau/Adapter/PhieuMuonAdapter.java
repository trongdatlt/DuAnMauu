package datptph27465.fpt.edu.duanmau.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import datptph27465.fpt.edu.duanmau.Models.PhieuMuon;
import datptph27465.fpt.edu.duanmau.Models.Sach;
import datptph27465.fpt.edu.duanmau.Models.ThanhVien;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.PhieuMuonDao;
import datptph27465.fpt.edu.duanmau.dao.SachDao;
import datptph27465.fpt.edu.duanmau.dao.ThanhVienDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {

    private List<PhieuMuon> phieuMuonList;
    private List<String> memberList;  // List for members
    private List<String> bookList;    // List for books
    Context context;
    List<ThanhVien> thanhVienList;
    List<Sach> sachList;
    public PhieuMuonAdapter(List<PhieuMuon> phieuMuonList, Context context) {
        this.phieuMuonList = phieuMuonList;
        this.context = context;

    }

    @Override
    public PhieuMuonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phieu_muon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = phieuMuonList.get(position);
        ThanhVienDao thanhVienDao = new ThanhVienDao(context);
        SachDao sachDao = new SachDao(context);

       thanhVienList = thanhVienDao.getAll();
         sachList = sachDao.getAll();



        String memberName = "";
        for (ThanhVien thanhVien : thanhVienList) {
            if (thanhVien.getMaTV() == phieuMuon.getMaTV()) {
                memberName = thanhVien.getHoTen();
                break;
            }
        }

        // Tìm tên sách tương ứng với maSach
        String bookName = "";
        for (Sach sach : sachList) {
            if (sach.getMaSach() == phieuMuon.getMaSach()) {
                bookName = sach.getTenSach();
                break;
            }
        }

        holder.tvMaPhieu.setText("Mã phiếu: " + phieuMuon.getMaPM());
        holder.tvThanhVien.setText("Thành viên: " + memberName);
        holder.tvTenSach.setText("Tên sách: " + bookName);
        holder.tvGiaThue.setText("Giá thuê: " + phieuMuon.getTienThue());
        holder.tvNgayThue.setText("Ngày thuê: " + phieuMuon.getNgayMuon());
        holder.tvTrangThai.setText(phieuMuon.getTraSach() == 1 ? "Đã trả sách" : "Chưa trả sách");

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa phiếu này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        datptph27465.fpt.edu.duanmau.dao.PhieuMuonDao phieuMuonDao = new PhieuMuonDao(context);
                        int kq =   phieuMuonDao.delete(String.valueOf(phieuMuon.getMaPM()));
                        if(kq>0){
                            phieuMuonList.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        holder.btnEdit.setOnClickListener(v -> {
            // Show the edit dialog with Spinners
            showEditDialog(phieuMuon, position);
        });
    }

    private void showEditDialog(PhieuMuon phieuMuon, int position) {
        // Create a custom AlertDialog to edit the PhieuMuon data
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_phieu_muon, null);
        Spinner spinnerThanhVien = dialogView.findViewById(R.id.spinnerThanhVien);
        Spinner spinnerTenSach = dialogView.findViewById(R.id.spinnerTenSach);
        EditText tvGiaThue = dialogView.findViewById(R.id.tvEditGiaThue);
        EditText tvNgayThue = dialogView.findViewById(R.id.tvEditNgayThue);
        RadioGroup rgTrangThai = dialogView.findViewById(R.id.rgEditTrangThai); // RadioGroup for status
        RadioButton rbDaTra = dialogView.findViewById(R.id.rbDaTra);
        RadioButton rbChuaTra = dialogView.findViewById(R.id.rbChuaTra);
        // Lấy danh sách các đối tượng từ database

        List<String> memberList = new ArrayList<>();
        for (ThanhVien thanhVien : thanhVienList) {
            memberList.add(thanhVien.getHoTen());
        }

        List<String> bookList = new ArrayList<>();
        for (Sach sach : sachList) {
            bookList.add(sach.getTenSach());
        }
// Set adapter cho Spinner Thành viên
        ArrayAdapter<String> memberAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, memberList);
        memberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThanhVien.setAdapter(memberAdapter);

// Set adapter cho Spinner Sách


        ArrayAdapter<String> bookAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, bookList);
        bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTenSach.setAdapter(bookAdapter);
        final int[] selectedMemberId = new int[1];
        final int[] selectedBookId = new int[1];


// Lắng nghe sự kiện khi chọn thành viên
        spinnerThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ThanhVien selectedThanhVien = thanhVienList.get(position);
                selectedMemberId[0] = selectedThanhVien.getMaTV();  // Lưu ID thành viên đã chọn

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Không làm gì khi không chọn gì
            }
        });

// Lắng nghe sự kiện khi chọn sách
        spinnerTenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Sach selectedSach = sachList.get(position);
                selectedBookId[0] = selectedSach.getMaSach();  // Lưu ID sách đã chọn

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Không làm gì khi không chọn gì
            }
        });

        // Đối với spinnerThanhVien
        int selectedMemberIndex = -1;
        for (int i = 0; i < thanhVienList.size(); i++) {
            if (thanhVienList.get(i).getMaTV() == phieuMuon.getMaTV()) {
                selectedMemberIndex = i;
                break;
            }
        }
        if (selectedMemberIndex != -1) {
            spinnerThanhVien.setSelection(selectedMemberIndex);
        }

// Đối với spinnerTenSach
        int selectedBookIndex = -1;
        for (int i = 0; i < sachList.size(); i++) {
            if (sachList.get(i).getMaSach() == phieuMuon.getMaSach()) {
                selectedBookIndex = i;
                break;
            }
        }
        if (selectedBookIndex != -1) {
            spinnerTenSach.setSelection(selectedBookIndex);
        }

        tvGiaThue.setText(String.valueOf(phieuMuon.getTienThue()));
        tvNgayThue.setText(phieuMuon.getNgayMuon());

        if (phieuMuon.getTraSach() == 1) {
            rbDaTra.setChecked(true);
        } else {
            rbChuaTra.setChecked(true);
        }
        tvNgayThue.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        // Cập nhật ngày khi chọn
                        calendar.set(year1, monthOfYear, dayOfMonth);
                        tvNgayThue.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
                    }, year, month, day);
            datePickerDialog.show();
        });

        new AlertDialog.Builder(context)
                .setTitle("Chỉnh sửa Phiếu Mượn")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    // Save the updated data here
                    phieuMuon.setMaSach(selectedBookId[0]);
                    phieuMuon.setTienThue(Integer.parseInt(tvGiaThue.getText().toString()));
                    phieuMuon.setNgayMuon(tvNgayThue.getText().toString());
                    phieuMuon.setMaTV(selectedMemberId[0]);
                    int trangThai = rgTrangThai.getCheckedRadioButtonId() == rbDaTra.getId() ? 1 : 0;
                    phieuMuon.setTraSach(trangThai);

                    PhieuMuonDao phieuMuonDao = new PhieuMuonDao(context);
                    int kq = phieuMuonDao.update(phieuMuon);
                    if (kq > 0) {
                        notifyItemChanged(position);
                        Toast.makeText(context, "Phiếu mượn đã được chỉnh sửa", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Phiếu mượn chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public int getItemCount() {
        return phieuMuonList.size();
    }

    public static class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPhieu, tvThanhVien, tvTenSach, tvGiaThue, tvNgayThue, tvTrangThai;
        ImageView btnEdit, btnDelete;

        public PhieuMuonViewHolder(View itemView) {
            super(itemView);
            tvMaPhieu = itemView.findViewById(R.id.tvMaPhieu);
            tvThanhVien = itemView.findViewById(R.id.tvThanhVien);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            tvNgayThue = itemView.findViewById(R.id.tvNgayThue);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
