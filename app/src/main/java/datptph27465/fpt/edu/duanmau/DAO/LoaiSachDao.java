package datptph27465.fpt.edu.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import datptph27465.fpt.edu.duanmau.Models.LoaiSach;
import datptph27465.fpt.edu.duanmau.database.DbHelper;

public class LoaiSachDao {
    private SQLiteDatabase db;

    public LoaiSachDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("TenLoai", loaiSach.getTenSach());
        return db.insert("loaisach", null, values);
    }

    public int update(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("TenSach", loaiSach.getTenSach());
        return db.update("loaisach", values, "MaLoai=?", new String[]{loaiSach.getMaLoai()});
    }

    public int delete(String maLoai) {
        return db.delete("loaisach", "MaLoai=?", new String[]{maLoai});
    }

    public List<LoaiSach> getAll() {
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.query("loaisach", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            LoaiSach loaiSach = new LoaiSach();
            loaiSach.setMaLoai(cursor.getString(cursor.getColumnIndex("MaLoai")));
            loaiSach.setTenSach(cursor.getString(cursor.getColumnIndex("TenLoai")));
            list.add(loaiSach);
        }
        cursor.close();
        return list;
    }


}
