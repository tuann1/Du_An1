package customModel;

import java.math.BigDecimal;

/**
 *
 * @author fallinluv2003
 */
public class ThongKeThang {

    private int thang;
    private int soLuongBanRa;
    private BigDecimal doanhThu;

    public ThongKeThang() {
    }

    public ThongKeThang(int thang, int soLuongBanRa, BigDecimal doanhThu) {
        this.thang = thang;
        this.soLuongBanRa = soLuongBanRa;
        this.doanhThu = doanhThu;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getSoLuongBanRa() {
        return soLuongBanRa;
    }

    public void setSoLuongBanRa(int soLuongBanRa) {
        this.soLuongBanRa = soLuongBanRa;
    }

    public BigDecimal getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(BigDecimal doanhThu) {
        this.doanhThu = doanhThu;
    }

    @Override
    public String toString() {
        return "ThongKeThang{" + "thang=" + thang + ", soLuongBanRa=" + soLuongBanRa + ", doanhThu=" + doanhThu + '}';
    }

}
