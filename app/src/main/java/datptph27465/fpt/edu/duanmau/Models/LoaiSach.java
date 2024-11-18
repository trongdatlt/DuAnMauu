package datptph27465.fpt.edu.duanmau.Models;

public class LoaiSach {
    public String maLoai;
    public String tenLoai;

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public LoaiSach() {
    }

    public String getTenSach() {
        return tenLoai;
    }

    public void setTenSach(String tenSach) {
        this.tenLoai = tenSach;
    }

    public LoaiSach(String maLoai, String tenSach) {
        this.maLoai = maLoai;
        this.tenLoai = tenSach;
    }
}