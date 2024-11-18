package datptph27465.fpt.edu.duanmau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import datptph27465.fpt.edu.duanmau.dao.ThuThuDao;
import datptph27465.fpt.edu.duanmau.database.DbHelper;

public class LoginActivity extends AppCompatActivity {
    datptph27465.fpt.edu.duanmau.dao.ThuThuDao thuThuDao;
    EditText edUserName ;
    EditText edPassword ;
    CheckBox chkRememberPass ;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        thuThuDao = new ThuThuDao(this);
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassWord);
        chkRememberPass = findViewById(R.id.remember_me);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCancel = findViewById(R.id.cancel_button);
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String user = preferences.getString("USERNAME","");
        String pass = preferences.getString("PASSWORD","");
        boolean rem = preferences.getBoolean("REMEMBER",false);
        edUserName.setText(user);
        edPassword.setText(pass);
        chkRememberPass.setChecked(rem);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edUserName.setText("");
                edPassword.setText("");
            }
        });
        EditText edPassWord = findViewById(R.id.edPassWord);
        boolean[] isPasswordVisible = {false}; // Biến để lưu trạng thái hiện/ẩn của mật khẩu

        edPassWord.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Vị trí của icon (drawableEnd) trên EditText
                if (event.getRawX() >= (edPassWord.getRight() - edPassWord.getCompoundDrawables()[2].getBounds().width())) {
                    if (isPasswordVisible[0]) {
                        // Đang hiển thị mật khẩu, chuyển sang ẩn
                        edPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        edPassWord.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_off_24px, 0);
                    } else {
                        // Đang ẩn mật khẩu, chuyển sang hiển thị
                        edPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        edPassWord.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_24px, 0);
                    }
                    isPasswordVisible[0] = !isPasswordVisible[0];
                    edPassWord.setSelection(edPassWord.getText().length()); // Giữ con trỏ ở cuối văn bản
                    return true;
                }
            }
            return false;
        });

    }
   public void rememberUser(String u,String p,boolean status){
       SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        if(!status){
            editor.clear();
        }else{
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);

        }
        editor.commit();
   }

   public  void  checkLogin(){
       String user = edUserName.getText().toString();
       String password = edPassword.getText().toString();
       if(user.isEmpty()||password.isEmpty()){
           Toast.makeText(this, "Tên đăng nhập và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
       }else{
           Log.d("zzzzzz", "checkLogin: "+thuThuDao.getAll());
           thuThuDao.getAll();
           Boolean kt =    thuThuDao.KiemTraDangNhap(user,password);
           if(kt){
               Toast.makeText(LoginActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
               rememberUser(user,password,chkRememberPass.isChecked());
               // Trong LoginActivity.java
               Intent intent = new Intent(LoginActivity.this, MainActivity.class);
               intent.putExtra("user",user);
               startActivity(intent);
               finish();
           }else{
               Toast.makeText(LoginActivity.this, "Sai Mật Khẩu Hoặc Tên Đăng Nhập", Toast.LENGTH_SHORT).show();
           }
       }

   }
}