/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import customModel.SoLanMuaHang;
import java.util.ArrayList;
import java.util.List;
import model.HoaDonBan;
import model.HoaDonChiTiet;
import model.KhachHang;
import model.KhuyenMai;

/**
 *
 * @author T450s
 */
public interface IHoaDonService {

    ArrayList<HoaDonBan> getListHoaDonBan();

    List<HoaDonBan> getListByTrangThai(int trangThai);

    List<HoaDonBan> getAllByByMa(String ma);

    List<KhachHang> getListKhach(int trangThai);

    // ArrayList<NhanVien> getListNv();
    ArrayList<KhuyenMai> getListKhuyenMai();

    String insert(HoaDonBan hd);

    void delete(int id);

    void update(HoaDonBan hd, int id);

    void updateKH(HoaDonBan hd);

    void addHoaDonChiTiet(HoaDonChiTiet hdct);

    void updateTrangThaiHoaDon(int id, int trangThai);

    void updateTrangThaiHoaDonChiTiet(int id, int trangThai);

    void updateTrangThaiHoaDonChiTietbyIDHDCT(int id, int trangThai);

    void deleteHoaDonCT(int idCTSP);

    public void updateSoLuongHDCT(int idCTSP, int soLuong);

    void updateSoLuongHDCTbyIDHDCT(int idHDCT, int soLuong);

    Long getCountHoaDon();

    public List<SoLanMuaHang> getSoLanMuaHang();

    Long countHoaDonByIdKH(int idKH);

    List<HoaDonChiTiet> getHoaDonChiTietByHD(int IdHD);

    List<HoaDonBan> getHoaDonByKH(int IdKH);

    List<HoaDonBan> pageListTraHang(int position, int pageSize, String maHD);

    List<HoaDonBan> filterProductTraHang(String maHD);
    
    List<HoaDonBan> pageListHoaDon(int position, int pageSize, String maHD, int tt);
    
    List<HoaDonBan> filterProductHoaDon(String maHD, int tt);

}
