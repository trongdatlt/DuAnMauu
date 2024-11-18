package datptph27465.fpt.edu.duanmau.Models;

public class PhieuMuon {
    private int maPM;
    private String maTT;
    private int maTV;
    private int maSach;
    private String ngayMuon;
    private int traSach;
    private int tienThue;

    // Constructor
    public PhieuMuon(int maPM, String maTT, int maTV, int maSach, String ngayMuon, int traSach, int tienThue) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.ngayMuon = ngayMuon;
        this.traSach = traSach;
        this.tienThue = tienThue;
    }

    // Getter và Setter cho từng thuộc tính
    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public PhieuMuon() {
    }

    @Override
    public String toString() {
        return "PhieuMuon{" +
                "maPM=" + maPM +
                ", maTT='" + maTT + '\'' +
                ", maTV=" + maTV +
                ", maSach=" + maSach +
                ", ngayMuon='" + ngayMuon + '\'' +
                ", traSach=" + traSach +
                ", tienThue=" + tienThue +
                '}';
    }
}
