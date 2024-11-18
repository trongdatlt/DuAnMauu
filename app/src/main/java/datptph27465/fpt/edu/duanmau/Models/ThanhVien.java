package datptph27465.fpt.edu.duanmau.Models;

public class ThanhVien {
    public Integer maTV;
    public String hoTen;
    public int namSinh;
    public Integer getMaTV() {
        return maTV;
    }

    public ThanhVien() {
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public ThanhVien(Integer maTV, String hoTen, int namSinh) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }


}