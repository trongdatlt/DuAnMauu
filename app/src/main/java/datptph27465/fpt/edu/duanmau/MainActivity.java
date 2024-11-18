package datptph27465.fpt.edu.duanmau;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import datptph27465.fpt.edu.duanmau.Fragment.LoaiSachFragment;
import datptph27465.fpt.edu.duanmau.Fragment.ChangePassFragment;
import datptph27465.fpt.edu.duanmau.Fragment.PhieuMuonFragment;
import datptph27465.fpt.edu.duanmau.Fragment.SachFragment;
import datptph27465.fpt.edu.duanmau.Fragment.ThanhVienFragment;
import datptph27465.fpt.edu.duanmau.Fragment.ThemThuThuFragment;
import datptph27465.fpt.edu.duanmau.Fragment.ThongKeDoanhTHuFragment;
import datptph27465.fpt.edu.duanmau.Fragment.Top10DanhSachFragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderView;
    TextView edUser;
    FragmentManager mangager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FragmentManager mangager    =getSupportFragmentManager();
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.menu_open_24px);
        ab.setDisplayHomeAsUpEnabled(true);
        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
        mangager.beginTransaction().replace(R.id.flContent,phieuMuonFragment).commit();
        NavigationView nv = findViewById(R.id.nvView);
        mHeaderView = nv.getHeaderView(0);
        edUser = mHeaderView.findViewById(R.id.txtUser);
        Intent i  = getIntent();
        edUser.setText("Welcome !");

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_PhieuMuon) {
                    setTitle("Quản lý phiếu mượn");
                    PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
                    mangager.beginTransaction().replace(R.id.flContent,phieuMuonFragment).commit();
                }
                else if (item.getItemId() == R.id.nav_LoaiSach) {
                    setTitle("Quản lý Loại sách");
                    LoaiSachFragment categoryFragment = new LoaiSachFragment();
                    mangager.beginTransaction().replace(R.id.flContent,categoryFragment).commit();
                }
                else if (item.getItemId() == R.id.nav_Sach) {
                    setTitle("Quản lý Sách");
                    SachFragment sachFragment = new SachFragment();
                    mangager.beginTransaction().replace(R.id.flContent,sachFragment).commit();
                }
                else if (item.getItemId() == R.id.nav_ThanhVien) {
                    setTitle("Quản lý thành viên");
                    ThanhVienFragment thanhVienFragment = new ThanhVienFragment();
                    mangager.beginTransaction().replace(R.id.flContent,thanhVienFragment).commit();
                }

                else if (item.getItemId() == R.id.sub_Top) {
                    setTitle("Top 10 sách cho thuê nhiều nhất");
                    Top10DanhSachFragment top10DanhSachFragment = new Top10DanhSachFragment();
                    mangager.beginTransaction().replace(R.id.flContent,top10DanhSachFragment).commit();
                } else if (item.getItemId() == R.id.sub_DoanhThu) {
                    setTitle("Thống kê doanh thu");
                    ThongKeDoanhTHuFragment thongKeDoanhTHuFragment = new ThongKeDoanhTHuFragment();
                    mangager.beginTransaction().replace(R.id.flContent,thongKeDoanhTHuFragment).commit();
                } else if (item.getItemId() == R.id.sub_AddUser) {
                    setTitle("Thêm người dùng");
                    ThemThuThuFragment themThuThuFragment = new ThemThuThuFragment();
                    mangager.beginTransaction().replace(R.id.flContent,themThuThuFragment).commit();
                } else if (item.getItemId() == R.id.sub_Pass) {
                    setTitle("Thay đổi mật khẩu");
                    ChangePassFragment changePassFragment = new ChangePassFragment();
                    mangager.beginTransaction().replace(R.id.flContent,changePassFragment).commit();

                } else if (item.getItemId() == R.id.sub_Logout) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

                drawer.closeDrawers();
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home)
            drawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }
}