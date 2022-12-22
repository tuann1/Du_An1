/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import customModel.SoLanMuaHang;
import java.util.ArrayList;
import java.util.List;
import model.HoaDonBan;
import model.HoaDonChiTiet;
import model.KhachHang;
import repository.KhachHangRepository;
import model.KhuyenMai;
import model.NhanVien;
import repository.HoaDonBanRepository;
import repository.KhuyenMaiRepository;

import service.IHoaDonService;

/**
 *
 * @author T450s
 */
public class HoaDonBanImpl implements IHoaDonService {

    HoaDonBanRepository hdr = new HoaDonBanRepository();
    KhachHangRepository kh = new KhachHangRepository();
    KhuyenMaiRepository kmr = new KhuyenMaiRepository();

    @Override
    public ArrayList<HoaDonBan> getListHoaDonBan() {
        return hdr.getAll();
    }

    @Override
    public List<KhachHang> getListKhach(int trangThai) {
        return kh.getAll(trangThai);
    }

//    @Override
//    public ArrayList<NhanVien> getListNv() {
//        return nvr.getAll();
//    }
    @Override
    public ArrayList<KhuyenMai> getListKhuyenMai() {
        return kmr.getAll();
    }

    @Override
    public String insert(HoaDonBan hd) {
        if (hdr.add(hd) == true) {
            return "Thêm thành công Mã :" + hd.getMaHDB();
        } else {
            return "Thất bại";
        }
    }

    @Override
    public void delete(int id) {
        this.hdr.dele(id);
    }

    @Override
    public void update(HoaDonBan hd, int id) {
        this.hdr.sua(hd, id);
    }
//=============================================================================================================================================

    @Override
    public void addHoaDonChiTiet(HoaDonChiTiet hdct) {
        if (hdr.addHoaDonChiTiet(hdct) == true) {
            System.out.println("Thêm hóa đơn chi tiết thành công");
        } else {
            System.out.println("Thêm Thất bại");
        }
    }

    @Override
    public void updateTrangThaiHoaDon(int id, int trangThai) {
        if (hdr.updateTrangThaiHoaDon(id, trangThai) == true) {
            System.out.println("Cập nhật trạng thái hóa đơn thành công : " + id);
        } else {
            System.out.println("Cập nhật Thất bại" + id);
        }
    }

    @Override
    public void updateKH(HoaDonBan hd) {
        if (hdr.suaKH(hd) == true) {
            System.out.println("Cập nhật trạng thái hóa đơn thành công : ");
        } else {
            System.out.println("Cập nhật Thất bại");
        }
    }

    @Override
    public void updateTrangThaiHoaDonChiTiet(int id, int trangThai) {
        if (hdr.updateTrangThaiHoaDonChiTiet(id, trangThai) == true) {
            System.out.println("Cập nhật trạng thái hóa đơn chi tiet thành công : " + id);
        } else {
            System.out.println("Cập nhật Thất bại" + id);
        }
    }

//    @Override
//    public List<HoaDonChiTiet> getHoaDonChiTietByIdHD(int idHD) {
//        return hdr.getHoaDonChiTietByIDHd(idHD);
//    }
    @Override
    public List<HoaDonBan> getListByTrangThai(int trangThai) {
        return hdr.getAllByTrangThai(trangThai);
    }

    @Override
    public void deleteHoaDonCT(int idCTSP) {
        if (hdr.deleteHoaDonChitiet(idCTSP) == true) {
            System.out.println("Xoa hoa don chi tiet thanh cong ");
        } else {
            System.out.println("Thất bại");
        }
    }

    @Override
    public void updateSoLuongHDCT(int idCTSP, int soLuong) {
        if (hdr.updateSoLuongHDCT(idCTSP, soLuong) == true) {
            System.out.println("Cap nhat so luong  hoa don chi tiet thanh cong ");
        } else {
            System.out.println("Thất bại");
        }
    }

    @Override
    public Long getCountHoaDon() {
        return hdr.getCountHoaDon();
    }

    @Override
    public List<SoLanMuaHang> getSoLanMuaHang() {
        return hdr.getSoLanMuaHang();
    }

    @Override
    public List<HoaDonChiTiet> getHoaDonChiTietByHD(int IdHD) {
        return hdr.getHoaDonChiTietByHD(IdHD);
    }

    @Override
    public List<HoaDonBan> getHoaDonByKH(int IdKH) {
        return hdr.getHoaDonByKH(IdKH);
    }

    @Override
    public Long countHoaDonByIdKH(int idKH) {
        return hdr.countHoaDonByIdKH(idKH);
    }

    @Override
    public List<HoaDonBan> getAllByByMa(String ma) {
        return hdr.getAllByMa(ma);
    }

    @Override
    public void updateTrangThaiHoaDonChiTietbyIDHDCT(int id, int trangThai) {
        if (hdr.updateTrangThaiHoaDonChiTietbyIDHDCT(id, trangThai) == true) {
            System.out.println("Cập nhật trạng thái hóa đơn chi tiet thành công : " + id);
        } else {
            System.out.println("Cập nhật Thất bại" + id);
        }
    }

    @Override
    public void updateSoLuongHDCTbyIDHDCT(int idHDCT, int soLuong) {
        if (hdr.updateSoLuongHDCTbyIDHDCT(idHDCT, soLuong) == true) {
            System.out.println("Cap nhat so luong  hoa don chi tiet thanh cong ");
        } else {
            System.out.println("Thất bại");
        }
    }

    @Override
    public List<HoaDonBan> pageListTraHang(int position, int pageSize, String maHD) {
        return hdr.pageListTraHang(position, pageSize, maHD);
    }

    @Override
    public List<HoaDonBan> filterProductTraHang(String maHD) {
        return hdr.filterProductTraHang(maHD);
    }

    @Override
    public List<HoaDonBan> pageListHoaDon(int position, int pageSize, String maHD, int tt) {
        return hdr.pageListHoaDon(position, pageSize, maHD, tt);
    }

    @Override
    public List<HoaDonBan> filterProductHoaDon(String maHD, int tt) {
        return hdr.filterProductHoaDon(maHD, tt);
    }

}
