package service.impl;

import customModel.HoaDonDoanhThu;
import customModel.HoaDonThanhToan;
import customModel.ThongKeThang;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import model.HoaDonChiTiet;
import repository.HDChiTietRepository;
import service.IHDCTService;

/**
 *
 * @author fallinluv2003
 */
public class HDCTImpl implements IHDCTService {

    private HDChiTietRepository hdctRepo = new HDChiTietRepository();

    @Override
    public List<HoaDonChiTiet> getAll() {
        return hdctRepo.getAll();
    }

    @Override
    public List<HoaDonChiTiet> getDoanhThu() {
        return hdctRepo.getDoanhThu();
    }

    @Override
    public List<HoaDonChiTiet> getById(int id) {
        return hdctRepo.getById(id);
    }

    // HDCTImpl
    @Override
    public List<HoaDonDoanhThu> getDoanhSo(boolean isDESC) {
        return hdctRepo.getDoanhSo(isDESC);
    }

    @Override
    public List<HoaDonThanhToan> getHoaDonThanhToan() {
        return hdctRepo.getHoaDonThanhToan();
    }

    @Override
    public BigDecimal doanhThuTheoNam() {
        return hdctRepo.doanhThuTheoNam();
    }

    @Override
    public BigDecimal doanhThuTheoThang() {
        return hdctRepo.doanhThuTheoThang();
    }

    @Override
    public BigDecimal doanhThuHomNay() {
        return hdctRepo.doanhThuHomNay();
    }

    @Override
    public List<HoaDonThanhToan> filterDate(String start, String end) {
        return hdctRepo.filterDate(start, end);
    }

    @Override
    public List<ThongKeThang> getThongKeThang() {
        return hdctRepo.getThongKeThang();
    }

    @Override
    public List<HoaDonChiTiet> getByIdByTrangThai(int id, int trangThai) {
        return hdctRepo.getByIdByTrangThai(id, trangThai);
    }

    @Override
    public int getSoluongByCTSPandMaHD(int maCTSP, int maHD) {
        return hdctRepo.getSoluongByCTSPandMaHD(maCTSP, maHD);
    }

    @Override
    public List<HoaDonChiTiet> getAllByTrangThai(int TrangThai) {
        return hdctRepo.getAllByTrangThai(TrangThai);
    }

    @Override
    public List<HoaDonChiTiet> getByIdTraHang(int id) {
        return hdctRepo.getByIdTraHang(id);
    }

}
