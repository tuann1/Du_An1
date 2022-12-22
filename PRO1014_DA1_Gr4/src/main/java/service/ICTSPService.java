package service;

import java.math.BigDecimal;
import model.ChiTietSanPham;
import java.util.List;
import model.ChatLieu;
import model.DanhMuc;
import model.Mau;
import model.NSX;

/**
 *
 * @author fallinluv2003
 */
public interface ICTSPService {

    List<ChiTietSanPham> getAll();

    String add(ChiTietSanPham ctsp);

    String update(ChiTietSanPham ctsp, Integer id);

    String updateTrangThai(Integer id);

    Long getSumProduct();

    int getSoLuongSpByMaCTSP(String maCTSP);

    void updateSoLuongCTSP(String maCTSP, int so);

    void updateSoLuongCTSPTraHang(String maCTSP, int so);

    public List<ChiTietSanPham> getChiTietSanPhamByComBoBox(DanhMuc isdanhMuc, ChatLieu isChatLieu, Mau isMau, NSX isNsx);

    public ChiTietSanPham getAllByID(int id);

    Long getProduct();

    Long getNonProduct();

    Long getOutProduct();

    ChiTietSanPham getByMa(String ma);

    long countAllProducts();

    List<ChiTietSanPham> pageList(int position, int pageSize, String tenSP, String tenDM, String tenCL, String tenMau, String tenNSX);

    List<ChiTietSanPham> filterProduct(String tenSP, String tenDM, String tenCL, String tenMau, String tenNSX);

    List<ChiTietSanPham> searchByName(String name);

    List<ChiTietSanPham> pageListBanHang(int position, int pageSize, String tenSP);
    List<ChiTietSanPham> filterProductBanHang(String tenSP);
}
