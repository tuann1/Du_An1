package customModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author fallinluv2003
 */
public class HoaDonThanhToan {
    
    private Date ngayThanhToan;
    private BigDecimal doanhThu;
    private int hoaDonThanhToan;

    public HoaDonThanhToan() {
    }

    public HoaDonThanhToan(Date ngayThanhToan, BigDecimal doanhThu, int hoaDonThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
        this.doanhThu = doanhThu;
        this.hoaDonThanhToan = hoaDonThanhToan;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public BigDecimal getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(BigDecimal doanhThu) {
        this.doanhThu = doanhThu;
    }

    public int getHoaDonThanhToan() {
        return hoaDonThanhToan;
    }

    public void setHoaDonThanhToan(int hoaDonThanhToan) {
        this.hoaDonThanhToan = hoaDonThanhToan;
    }

    @Override
    public String toString() {
        return "HoaDonThanhToan{" + "ngayThanhToan=" + ngayThanhToan + ", doanhThu=" + doanhThu + ", hoaDonThanhToan=" + hoaDonThanhToan + '}';
    }

}
