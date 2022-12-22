/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customModel;

/**
 *
 * @author ADMIN
 */
public class HoaDonDoanhThu {

    private String maCTSP;
    private String tenSP;
    private String tenDm;
    private String tenCL;
    private String tenMau;
    private String tenNSX;
    private int soLuongBanRa;

    public HoaDonDoanhThu() {
    }

    public HoaDonDoanhThu(String maCTSP, String tenSP, String tenDm, String tenCL, String tenMau, String tenNSX, int soLuongBanRa) {
        this.maCTSP = maCTSP;
        this.tenSP = tenSP;
        this.tenDm = tenDm;
        this.tenCL = tenCL;
        this.tenMau = tenMau;
        this.tenNSX = tenNSX;
        this.soLuongBanRa = soLuongBanRa;
    }

    public String getMaCTSP() {
        return maCTSP;
    }

    public void setMaCTSP(String maCTSP) {
        this.maCTSP = maCTSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuongBanRa() {
        return soLuongBanRa;
    }

    public void setSoLuongBanRa(int soLuongBanRa) {
        this.soLuongBanRa = soLuongBanRa;
    }

    public String getTenDm() {
        return tenDm;
    }

    public void setTenDm(String tenDm) {
        this.tenDm = tenDm;
    }

    public String getTenCL() {
        return tenCL;
    }

    public void setTenCL(String tenCL) {
        this.tenCL = tenCL;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    @Override
    public String toString() {
        return "HoaDonDoanhThu{" + "maCTSP=" + maCTSP + ", tenSP=" + tenSP + ", tenDm=" + tenDm + ", tenCL=" + tenCL + ", tenMau=" + tenMau + ", tenNSX=" + tenNSX + ", soLuongBanRa=" + soLuongBanRa + '\n';
    }

}
