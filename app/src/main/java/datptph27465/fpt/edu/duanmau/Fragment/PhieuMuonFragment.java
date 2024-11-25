package datptph27465.fpt.edu.duanmau.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import datptph27465.fpt.edu.duanmau.Adapter.PhieuMuonAdapter;
import datptph27465.fpt.edu.duanmau.Models.PhieuMuon;
import datptph27465.fpt.edu.duanmau.Models.Sach;
import datptph27465.fpt.edu.duanmau.Models.ThanhVien;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.PhieuMuonDao;
import datptph27465.fpt.edu.duanmau.dao.SachDao;
import datptph27465.fpt.edu.duanmau.dao.ThanhVienDao;

public class PhieuMuonFragment extends Fragment {
    private RecyclerView recyclerView;
    private PhieuMuonAdapter adapter;
    private ArrayList<PhieuMuon> phieuMuonList;
    private List<ThanhVien> thanhVienList; // List for Members
    private List<Sach> sachList; // List for Books

    public PhieuMuonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load data for members and books
        ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
        sachList = new SachDao(getContext()).getAll();
        thanhVienList = thanhVienDao.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        ExtendedFloatingActionButton btnadd;
        recyclerView = view.findViewById(R.id.recyclerViewPhieuMuon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnadd = view.findViewById(R.id.btn_add);

        // Initialize the data
        phieuMuonList = new ArrayList<>();
        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(getContext());
        phieuMuonList.addAll(phieuMuonDao.getAll());

        // Set the adapter
        adapter = new PhieuMuonAdapter(phieuMuonList, getContext());
        recyclerView.setAdapter(adapter);

        btnadd.setOnClickListener(v -> showAddDialog());
        return view;
    }

    private void showAddDialog() {
        // Tạo một AlertDialog tùy chỉnh để thêm PhieuMuon
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_phieu_muon, null);
        Spinner spinnerThanhVien = dialogView.findViewById(R.id.spinnerThanhVien);
        Spinner spinnerTenSach = dialogView.findViewById(R.id.spinnerTenSach);
        EditText tvGiaThue = dialogView.findViewById(R.id.tvGiaThue);
        EditText tvNgayThue = dialogView.findViewById(R.id.tvNgayThue);
        RadioGroup rgTrangThai = dialogView.findViewById(R.id.rgTrangThai);
        RadioButton rbDaTra = dialogView.findViewById(R.id.rbDaTra);
        RadioButton rbChuaTra = dialogView.findViewById(R.id.rbChuaTra);

        // Tạo danh sách thành viên và sách
        List<String> memberList = new ArrayList<>();
        for (ThanhVien thanhVien : thanhVienList) {
            memberList.add(thanhVien.getHoTen());
        }

        List<String> bookList = new ArrayList<>();
        for (Sach sach : sachList) {
            bookList.add(sach.getTenSach());
        }

        // Set adapter cho Spinner Thành viên
        ArrayAdapter<String> memberAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, memberList);
        memberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThanhVien.setAdapter(memberAdapter);

        // Set adapter cho Spinner Sách
        ArrayAdapter<String> bookAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, bookList);
        bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTenSach.setAdapter(bookAdapter);

        // Biến lưu trữ ID đã chọn của thành viên và sách
        final int[] selectedMemberId = new int[1];
        final int[] selectedBookId = new int[1];
        final Sach[] Sachhh = {new Sach()};

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
                tvGiaThue.setText(String.valueOf(selectedSach.getGiaThue()));
                Sachhh[0] = selectedSach;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Không làm gì khi không chọn gì
            }
        });

        // Hiển thị hộp thoại để thêm dữ liệu
        new AlertDialog.Builder(getContext())
                .setTitle("Thêm Phiếu Mượn Mới")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    int trangThai = rgTrangThai.getCheckedRadioButtonId() == rbDaTra.getId() ? 1 : 0;
                    Date today = new Date();

                    // Định dạng ngày thành dd/MM/yyyy
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = dateFormat.format(today);
                    PhieuMuon phieuMuon = new PhieuMuon();
                    phieuMuon.setMaTT("");
                    phieuMuon.setMaTV(selectedMemberId[0]);
                    phieuMuon.setMaSach(Sachhh[0].getMaSach());
                    phieuMuon.setTienThue(Sachhh[0].getGiaThue());
                    phieuMuon.setNgayMuon(formattedDate);
                    phieuMuon.setTraSach(trangThai);
                    // Thêm dữ liệu vào cơ sở dữ liệu
                    PhieuMuonDao phieuMuonDao = new PhieuMuonDao(getContext());
                    long result = phieuMuonDao.insert(phieuMuon);  // Gọi phương thức thêm dữ liệu

                    if (result > 0) {
                        phieuMuonList.clear();
                        phieuMuonList.addAll(phieuMuonDao.getAll());
                        adapter.notifyDataSetChanged();  // Thông báo RecyclerView cập nhật
                        Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
