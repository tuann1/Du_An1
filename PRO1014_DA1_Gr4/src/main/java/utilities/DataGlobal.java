/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.util.List;
import model.HoaDonBan;
import model.HoaDonChiTiet;
import model.KhuyenMai;

/**
 *
 * @author ADMIN
 */
public class DataGlobal {

    public static double totalHoaDon;
    public static KhuyenMai khuyenMai;
    public static List<HoaDonChiTiet> listHDCT;
    public static HoaDonBan hoaDonBan;
    public static int idHoaDon;

    public static int getIdHoaDon() {
        return idHoaDon;
    }

    public static void setIdHoaDon(int idHoaDon) {
        DataGlobal.idHoaDon = idHoaDon;
    }
    
    

    public static HoaDonBan getHoaDonBan() {
        return hoaDonBan;
    }

    public static void setHoaDonBan(HoaDonBan hoaDonBan) {
        DataGlobal.hoaDonBan = hoaDonBan;
    }
    

    public static List<HoaDonChiTiet> getListHDCT() {
        return listHDCT;
    }

    public static void setListHDCT(List<HoaDonChiTiet> listHDCT) {
        DataGlobal.listHDCT = listHDCT;
    }

    public static void clearKM() {
        khuyenMai = null;
    }

    public DataGlobal() {
        totalHoaDon = 0;
    }

    public static double getTotalHoaDon() {
        return totalHoaDon;
    }

    public static void setTotalHoaDon(double totalHoaDon) {
        DataGlobal.totalHoaDon = totalHoaDon;
    }

    public static KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public static void setKhuyenMai(KhuyenMai khuyenMai) {
        DataGlobal.khuyenMai = khuyenMai;
    }

}
