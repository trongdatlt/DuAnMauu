package datptph27465.fpt.edu.duanmau.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import datptph27465.fpt.edu.duanmau.Adapter.SachAdapter;
import datptph27465.fpt.edu.duanmau.Models.LoaiSach;
import datptph27465.fpt.edu.duanmau.Models.Sach;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.LoaiSachDao;
import datptph27465.fpt.edu.duanmau.dao.SachDao;

public class SachFragment extends Fragment {
    private RecyclerView rvSach;
    private ExtendedFloatingActionButton btnAdd;
    private List<Sach> sachList;
    private SachAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach, container, false);

        rvSach = view.findViewById(R.id.rvSach);
        btnAdd = view.findViewById(R.id.btnAdd);
        sachList = new ArrayList<>();
        // Sample data
        SachDao sachDao = new SachDao(getContext());
        adapter = new SachAdapter(getContext(),sachList);
        sachList.addAll(
                sachDao.getAll());
        rvSach.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSach.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddBookDialog();
            }
        });
        return view;
    }
    List<LoaiSach> loaiSachList;
    private void showAddBookDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_book, null);

        EditText etMaSach = dialogView.findViewById(R.id.etMaSach);
        EditText etTenSach = dialogView.findViewById(R.id.etTenSach);
        EditText etGiaThue = dialogView.findViewById(R.id.etGiaThue);
        Spinner spLoaiSach = dialogView.findViewById(R.id.spLoaiSach);

        // Tạo DAO và danh sách loại sách
        LoaiSachDao loaiSachDao = new LoaiSachDao(getContext());
        loaiSachList = new ArrayList<>();
        List<String> loaiSachNames = new ArrayList<>();

        try {
            loaiSachList = loaiSachDao.getAll(); // Lấy tất cả loại sách từ database
            for (LoaiSach loaiSach : loaiSachList) {
                loaiSachNames.add(loaiSach.getTenSach()); // Giả sử LoaiSach có phương thức getTenLoai()
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Lỗi khi tải dữ liệu loại sách!", Toast.LENGTH_SHORT).show();
        }

        // Gắn adapter cho Spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, loaiSachNames);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaiSach.setAdapter(adapter1);

        // Lấy mã loại từ Spinner
        final String[] selectedMaLoai = {null}; // Biến lưu trữ mã loại được chọn
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
        new AlertDialog.Builder(getContext())
                .setTitle("Thêm sách mới")
                .setView(dialogView)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    // Lấy dữ liệu từ các trường
                    String tenSach = etTenSach.getText().toString().trim();
                    String giaThueStr = etGiaThue.getText().toString().trim();

                    if (tenSach.isEmpty() || giaThueStr.isEmpty() || selectedMaLoai[0] == null) {
                        Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin và chọn loại sách!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        int giaThue = Integer.parseInt(giaThueStr);

                        // Tạo đối tượng sách mới
                        Sach sach = new Sach(1, selectedMaLoai[0], tenSach, giaThue);

                        // Thêm sách vào database
                        SachDao sachDao = new SachDao(getContext());
                        long kq = sachDao.insert(sach);
                        if (kq > 0) {
                            Toast.makeText(getContext(), "Đã thêm sách: " + tenSach, Toast.LENGTH_SHORT).show();
                            sachList.clear();
                            sachList.addAll(sachDao.getAll());
                            adapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getContext(), "Thêm sách thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Giá thuê phải là số!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }


}