package datptph27465.fpt.edu.duanmau.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import datptph27465.fpt.edu.duanmau.Models.ThuThu;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.ThuThuDao;

public class ChangePassFragment extends Fragment {
    Button btnSave,btnCancel;
    EditText etPassOld,etPass,etRePass;
    datptph27465.fpt.edu.duanmau.dao.ThuThuDao thuThuDao;


    public ChangePassFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_change_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etPassOld = view.findViewById(R.id.etOldPassword);
        etPass = view.findViewById(R.id.etNewPassword);
        etRePass = view.findViewById(R.id.etConfirmNewPassword);
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE",MODE_PRIVATE);
                String user = preferences.getString("USERNAME","");
                if(validate()>0){
                    thuThuDao = new ThuThuDao(getContext());
            int kq =        thuThuDao.setMatKhau(user,etPass.getText().toString());
            if(kq>0){
                Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();

            }
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPass.setText("");
                etRePass.setText("");
                etPassOld.setText("");
            }
        });
        super.onViewCreated(view, savedInstanceState);


    }
    public int validate(){
       int check =1;
       if(etPassOld.getText().length()==0||etRePass.getText().length()==0||etPass.getText().length()==0){
           Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
           check=-1;
       }else {
           SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE",MODE_PRIVATE);
           String passOld = preferences.getString("PASSWORD","");
           String pass = etPass.getText().toString();
           String rePass = etRePass.getText().toString();
           if(!passOld.equals(etPassOld.getText().toString())){
               Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
               check=-1;
           }
           if(!pass.equals(rePass)){
               Toast.makeText(getContext(), "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
               check=-1;
           }
       }

       return  check;
    }

}