package datptph27465.fpt.edu.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Library.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sach ("
                + "MaSach INTEGER PRIMARY KEY autoincrement, "
                + "MaLoai INTEGER, "
                + "TenSach TEXT, "
                + "GiaThue REAL, "
                + "FOREIGN KEY (MaLoai) REFERENCES loaisach(MaLoai));");

        db.execSQL("CREATE TABLE loaisach ("
                + "MaLoai INTEGER PRIMARY KEY autoincrement, "
                + "TenLoai TEXT);");

        db.execSQL("CREATE TABLE phieumuon ("
                + "MaPM INTEGER PRIMARY KEY autoincrement, "
                + "MaTT TEXT, "
                + "MaTV INTEGER, "
                + "MaSach INTEGER, "
                + "NgayMuon TEXT, "
                + "TraSach INTEGER, "
                + "TienThue INTEGER, "
                + "FOREIGN KEY (MaTT) REFERENCES thuthu(MaTT), "
                + "FOREIGN KEY (MaTV) REFERENCES thanhvien(MaTV), "
                + "FOREIGN KEY (MaSach) REFERENCES sach(MaSach));");

        db.execSQL("CREATE TABLE thuthu ("
                + "MaTT TEXT , "
                + "HoTen TEXT, "
                + "MatKhau TEXT);");

        db.execSQL("CREATE TABLE thanhvien ("
                + "MaTV INTEGER PRIMARY KEY autoincrement, "
                + "HoTen TEXT, "
                + "NamSinh INTEGER);");


        db.execSQL("INSERT INTO loaisach (TenLoai) VALUES ('Sách Khoa Học');");
        db.execSQL("INSERT INTO loaisach (TenLoai) VALUES ('Sách Văn Học');");

        db.execSQL("INSERT INTO sach (MaLoai, TenSach, GiaThue) VALUES (1, 'Lịch Sử Thế Giới', 5000);");
        db.execSQL("INSERT INTO sach (MaLoai, TenSach, GiaThue) VALUES (2, 'Truyện Kiều', 3000);");

        db.execSQL("INSERT INTO thuthu (MaTT,HoTen, MatKhau) VALUES ('Thuthu001','Nguyễn Văn A', '12345');");
        db.execSQL("INSERT INTO thuthu (MaTT,HoTen, MatKhau) VALUES ('Thuthu002','Trần Thị B', '67890');");
        db.execSQL("INSERT INTO thuthu (MaTT,HoTen, MatKhau) VALUES ('admin','Phạm Trọng Đạt', 'admin');");

        db.execSQL("INSERT INTO thanhvien (HoTen, NamSinh) VALUES ('Lê Văn C', 1990);");
        db.execSQL("INSERT INTO thanhvien (HoTen, NamSinh) VALUES ('Phạm Thị D', 1995);");

        db.execSQL("INSERT INTO phieumuon (MaTT, MaTV, MaSach, NgayMuon, TraSach, TienThue) VALUES (1, 1, 1, '2024-10-01', '2024-10-15', 5000);");
        db.execSQL("INSERT INTO phieumuon (MaTT, MaTV, MaSach, NgayMuon, TraSach, TienThue) VALUES (2, 2, 2, '2024-10-05', '2024-10-20', 3000);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sach;");
        db.execSQL("DROP TABLE IF EXISTS loaisach;");
        db.execSQL("DROP TABLE IF EXISTS phieumuon;");
        db.execSQL("DROP TABLE IF EXISTS thuthu;");
        db.execSQL("DROP TABLE IF EXISTS thanhvien;");
        onCreate(db);
    }
}

