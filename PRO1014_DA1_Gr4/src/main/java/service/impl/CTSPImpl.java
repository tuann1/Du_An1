package service.impl;

import java.math.BigDecimal;
import model.ChiTietSanPham;
import repository.CTSPRepository;
import java.util.List;
import model.ChatLieu;
import model.DanhMuc;
import model.Mau;
import model.NSX;
import service.ICTSPService;

/**
 *
 * @author fallinluv2003
 */
public class CTSPImpl implements ICTSPService {

    private CTSPRepository ctspRepo = new CTSPRepository();

    @Override
    public List<ChiTietSanPham> getAll() {
        return ctspRepo.getAll();
    }

    @Override
    public String add(ChiTietSanPham ctsp) {
        if (ctspRepo.add(ctsp)) {
            return "Thêm thành công";
        } else {
            return "Thêm thất bại";
        }
    }

    @Override
    public String update(ChiTietSanPham ctsp, Integer id) {
        if (ctspRepo.update(ctsp, id)) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bại";
        }
    }

    @Override
    public String updateTrangThai(Integer id) {
        if (ctspRepo.updateTrangThai(id)) {
            return "Xóa thành công";
        } else {
            return "Xóa thất bại";
        }
    }

    @Override
    public Long getSumProduct() {
        return ctspRepo.getSumProduct();
    }

    @Override
    public int getSoLuongSpByMaCTSP(String maCTSP) {
        return ctspRepo.getSoLuongSpByMaCTSP(maCTSP);
    }

    @Override
    public void updateSoLuongCTSP(String maCTSP, int so) {
        if (ctspRepo.updateSoLuongCTSP(maCTSP, so)) {
            System.out.println("Cap nhat so luong ton thanh cong");
        } else {
            System.out.println("Cap nhat that bai");
        }
    }

    @Override
    public List<ChiTietSanPham> getChiTietSanPhamByComBoBox(DanhMuc isdanhMuc, ChatLieu isChatLieu, Mau isMau, NSX isNsx) {
        return ctspRepo.getChiTietSanPhamByComBoBox(isdanhMuc, isChatLieu, isMau, isNsx);
    }

    @Override
    public ChiTietSanPham getAllByID(int id) {
        return ctspRepo.getAllByID(id);
    }

    @Override
    public Long getProduct() {
        return ctspRepo.getProduct();
    }

    @Override
    public Long getNonProduct() {
        return ctspRepo.getNonProduct();
    }

    @Override
    public Long getOutProduct() {
        return ctspRepo.getOutProduct();
    }

    @Override
    public ChiTietSanPham getByMa(String ma) {
        return ctspRepo.getByMa(ma);
    }

    @Override
    public void updateSoLuongCTSPTraHang(String maCTSP, int so) {
        if (ctspRepo.updateSoLuongCTSPTraHang(maCTSP, so)) {
            System.out.println("Cap nhat so luong ton thanh cong");
        } else {
            System.out.println("Cap nhat that bai");
        }
    }

    @Override
    public long countAllProducts() {
        return ctspRepo.totalCount();
    }

    @Override
    public List<ChiTietSanPham> pageList(int position, int pageSize, String tenSP, String tenDM, String tenCL, String tenMau, String tenNSX) {
        return ctspRepo.pageList(position, pageSize, tenSP, tenDM, tenCL, tenMau, tenNSX);
    }

    @Override
    public List<ChiTietSanPham> filterProduct(String tenSP, String tenDM, String tenCL, String tenMau, String tenNSX) {
        return ctspRepo.filterProduct(tenSP, tenDM, tenCL, tenMau, tenNSX);
    }

    @Override
    public List<ChiTietSanPham> searchByName(String name) {
        return ctspRepo.searchByName(name);
    }

    @Override
    public List<ChiTietSanPham> pageListBanHang(int position, int pageSize, String tenSP) {
        return ctspRepo.pageListBanHang(position, pageSize, tenSP);
    }

    @Override
    public List<ChiTietSanPham> filterProductBanHang(String tenSP) {
        return ctspRepo.filterProductBanHang(tenSP);
    }

}
