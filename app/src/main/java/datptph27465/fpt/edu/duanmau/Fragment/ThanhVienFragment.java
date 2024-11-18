package datptph27465.fpt.edu.duanmau.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import datptph27465.fpt.edu.duanmau.Adapter.ThanhVienAdapter;
import datptph27465.fpt.edu.duanmau.Models.ThanhVien;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.ThanhVienDao;

public class ThanhVienFragment extends Fragment {
    RecyclerView rvThanhVien;
    datptph27465.fpt.edu.duanmau.dao.ThanhVienDao thanhVienDao;
    List<ThanhVien> listThanhVien;
    ThanhVienAdapter thanhVienAdapter;
    public ThanhVienFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvThanhVien = view.findViewById(R.id.lvThanhVien);
        thanhVienDao = new ThanhVienDao(getContext());
        listThanhVien = new ArrayList<>();
        listThanhVien.addAll(thanhVienDao.getAll());
        thanhVienAdapter = new ThanhVienAdapter(getContext(),listThanhVien);
        rvThanhVien.setLayoutManager(new LinearLayoutManager(getContext()));
        rvThanhVien.setAdapter(thanhVienAdapter);
        FloatingActionButton btnAdd = view.findViewById(R.id.fab);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showAddMemberDialog();
            }
        });

    }


    private void showAddMemberDialog() {
        // Khởi tạo AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_add_member, null);
        builder.setView(dialogView);
        // Lấy các View từ dialog
        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etYearOfBirth = dialogView.findViewById(R.id.etYearOfBirth);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        AlertDialog dialog = builder.create();

        // Xử lý nút Hủy
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Xử lý nút Lưu
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String yearOfBirthStr = etYearOfBirth.getText().toString().trim();
                if(validateInputs(etName,etYearOfBirth)){
                    if (name.isEmpty() || yearOfBirthStr.isEmpty()) {
                        Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        int yearOfBirth = Integer.parseInt(yearOfBirthStr);
                        ThanhVien newMember = new ThanhVien(0,name, yearOfBirth);
                        long kq =      thanhVienDao.insert(newMember);
                        if(kq>0){
                            listThanhVien.clear();
                            listThanhVien.addAll(thanhVienDao.getAll()); // Lấy lại tất cả thành viên
                            thanhVienAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Thêm thành công " + name, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(), "Thêm thất bại " + name, Toast.LENGTH_SHORT).show();

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

}