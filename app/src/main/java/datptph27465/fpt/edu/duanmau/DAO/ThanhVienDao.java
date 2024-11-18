package datptph27465.fpt.edu.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import datptph27465.fpt.edu.duanmau.Models.ThanhVien;
import datptph27465.fpt.edu.duanmau.database.DbHelper;

public class ThanhVienDao {
    private SQLiteDatabase db;

    public ThanhVienDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("HoTen", thanhVien.getHoTen());
        values.put("NamSinh", thanhVien.getNamSinh());
        return db.insert("thanhvien", null, values);
    }

    public int update(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("HoTen", thanhVien.getHoTen());
        values.put("NamSinh", thanhVien.getNamSinh());
        return db.update("thanhvien", values, "MaTV=?", new String[]{String.valueOf(thanhVien.getMaTV())});
    }

    public int delete(String maTV) {
        return db.delete("thanhvien", "MaTV=?", new String[]{maTV});
    }

    public List<ThanhVien> getAll() {
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.query("thanhvien", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            ThanhVien thanhVien = new ThanhVien();
            thanhVien.setMaTV(cursor.getInt(cursor.getColumnIndex("MaTV")));
            thanhVien.setHoTen(cursor.getString(cursor.getColumnIndex("HoTen")));
            thanhVien.setNamSinh(cursor.getInt(cursor.getColumnIndex("NamSinh")));
            list.add(thanhVien);
        }
        cursor.close();
        return list;
    }
}
