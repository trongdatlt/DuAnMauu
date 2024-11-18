package datptph27465.fpt.edu.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import datptph27465.fpt.edu.duanmau.Models.Sach;
import datptph27465.fpt.edu.duanmau.database.DbHelper;

public class SachDao {
    private SQLiteDatabase db;

    public SachDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("MaLoai", sach.getMaLoai());
        values.put("TenSach", sach.getTenSach());
        values.put("GiaThue", sach.getGiaThue());
        return db.insert("sach", null, values);
    }

    public int update(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("MaLoai", sach.getMaLoai());
        values.put("TenSach", sach.getTenSach());
        values.put("GiaThue", sach.getGiaThue());
        return db.update("sach", values, "MaSach=?", new String[]{String.valueOf(sach.getMaSach())});
    }

    public int delete(int maSach) {
        return db.delete("sach", "MaSach=?", new String[]{String.valueOf(maSach)});
    }

    public List<Sach> getAll() {
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.query("sach", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Sach sach = new Sach();
            sach.setMaSach(cursor.getInt(cursor.getColumnIndex("MaSach")));
            sach.setMaLoai(cursor.getString(cursor.getColumnIndex("MaLoai")));
            sach.setTenSach(cursor.getString(cursor.getColumnIndex("TenSach")));
            sach.setGiaThue(cursor.getInt(cursor.getColumnIndex("GiaThue")));
            list.add(sach);
        }
        cursor.close();
        return list;
    }
}
