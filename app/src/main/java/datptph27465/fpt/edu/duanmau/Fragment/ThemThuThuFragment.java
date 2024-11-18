package datptph27465.fpt.edu.duanmau.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import datptph27465.fpt.edu.duanmau.Models.ThuThu;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.ThuThuDao;


public class ThemThuThuFragment extends Fragment {



    public ThemThuThuFragment() {
        // Required empty public constructor
    }

    public static ThemThuThuFragment newInstance(String param1, String param2) {
        ThemThuThuFragment fragment = new ThemThuThuFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_them_thu_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etMaThuthu = view.findViewById(R.id.etMaThuthu);
        EditText etHoten =view.findViewById(R.id.etHoten);
        EditText etMatKhau =view.findViewById(R.id.etMatKhau);

        Button btnadd = view.findViewById(R.id.btnAdd);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maThuthu = etMaThuthu.getText().toString().trim();
                String hoTen = etHoten.getText().toString().trim();
                String matKhau = etMatKhau.getText().toString().trim();
                datptph27465.fpt.edu.duanmau.dao.ThuThuDao thuThuDao = new ThuThuDao(getContext());
               long kq = thuThuDao.insert(new ThuThu(maThuthu,hoTen,matKhau));
                if(kq>0){
                    Toast.makeText(getContext(), "Thêm thử thư thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Thêm thử thư thất bại", Toast.LENGTH_SHORT).show();

                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMaThuthu.setText("");
                etHoten.setText("");
                etMatKhau.setText("");
            }
        });


    }
}