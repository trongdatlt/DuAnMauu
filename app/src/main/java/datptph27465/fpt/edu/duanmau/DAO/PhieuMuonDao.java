package datptph27465.fpt.edu.duanmau.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import datptph27465.fpt.edu.duanmau.Models.PhieuMuon;
import datptph27465.fpt.edu.duanmau.Models.Sach;
import datptph27465.fpt.edu.duanmau.Models.TopSach;
import datptph27465.fpt.edu.duanmau.database.DbHelper;

public class PhieuMuonDao {
    private SQLiteDatabase db;

    public PhieuMuonDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("MaTT", phieuMuon.getMaTT());
        values.put("MaTV", phieuMuon.getMaTV());
        values.put("MaSach", phieuMuon.getMaSach());
        values.put("NgayMuon", phieuMuon.getNgayMuon());
        values.put("TraSach", phieuMuon.getTraSach());
        values.put("TienThue", phieuMuon.getTienThue());
        return db.insert("phieumuon", null, values);
    }

    public int update(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("MaTT", phieuMuon.getMaTT());
        values.put("MaTV", phieuMuon.getMaTV());
        values.put("MaSach", phieuMuon.getMaSach());
        values.put("NgayMuon", phieuMuon.getNgayMuon());
        values.put("TraSach", phieuMuon.getTraSach());
        values.put("TienThue", phieuMuon.getTienThue());
        return db.update("phieumuon", values, "MaPM=?", new String[]{String.valueOf(phieuMuon.getMaPM())});
    }

    public int delete(String maPM) {
        return db.delete("phieumuon", "MaPM=?", new String[]{maPM});
    }
    @SuppressLint("Range")
    public List<PhieuMuon> getAll() {
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.query("phieumuon", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.setMaPM(cursor.getInt(cursor.getColumnIndex("MaPM")));
            phieuMuon.setMaTT(cursor.getString(cursor.getColumnIndex("MaTT")));
            phieuMuon.setMaTV(cursor.getInt(cursor.getColumnIndex("MaTV")));
            phieuMuon.setMaSach(cursor.getInt(cursor.getColumnIndex("MaSach")));
            phieuMuon.setNgayMuon(cursor.getString(cursor.getColumnIndex("NgayMuon")));
            phieuMuon.setTraSach(cursor.getInt(cursor.getColumnIndex("TraSach")));
            phieuMuon.setTienThue(cursor.getInt(cursor.getColumnIndex("TienThue")));

            list.add(phieuMuon);
        }

        cursor.close();
        return list;
    }
    public List<TopSach> getTop10Sachs() {
        List<TopSach> topSachList = new ArrayList<>();
        String query = "SELECT sach.TenSach, COUNT(phieumuon.MaSach) AS SoLanMuon " +
                "FROM phieumuon " +
                "JOIN sach ON phieumuon.MaSach = sach.MaSach " +
                "GROUP BY sach.MaSach " +
                "ORDER BY SoLanMuon DESC " +
                "LIMIT 10;";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tenSach = cursor.getString(cursor.getColumnIndex("TenSach"));
                int soLanMuon = cursor.getInt(cursor.getColumnIndex("SoLanMuon"));
                TopSach topSach = new TopSach(tenSach, soLanMuon);
                topSachList.add(topSach);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return topSachList;
    }
    public double getDoanhThuTheoKhoangNgay(String startDate, String endDate) {
        String query = "SELECT SUM(TienThue) AS DoanhThu " +
                "FROM phieumuon " +
                "WHERE TraSach = 1 " +
                "AND NgayMuon BETWEEN ? AND ?";

        // Debugging log
        System.out.println("Executing query with: Start Date = " + startDate + " End Date = " + endDate);

        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});

        double doanhThu = 0;
        if (cursor.moveToFirst()) {
            doanhThu = cursor.getDouble(cursor.getColumnIndex("DoanhThu"));
        }
        cursor.close();

        // Debugging log
        System.out.println("Calculated revenue: " + doanhThu);

        return doanhThu;
    }





}
