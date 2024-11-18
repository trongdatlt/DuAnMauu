package datptph27465.fpt.edu.duanmau.Models;

public class Sach {
     Integer maSach;
     String maLoai;
     String tenSach;
     Integer giaThue;

    public Sach() {
        // Constructor mặc định
    }

    public Sach(Integer maSach, String maLoai, String tenSach, Integer giaThue) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
    }

    public Integer getMaSach() {
        return maSach;
    }

    public void setMaSach(Integer maSach) {
        this.maSach = maSach;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public Integer getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(Integer giaThue) {
        this.giaThue = giaThue;
    }
}
