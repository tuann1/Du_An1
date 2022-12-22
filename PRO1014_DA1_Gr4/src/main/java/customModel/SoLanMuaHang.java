package customModel;

/**
 *
 * @author fallinluv2003
 */
public class SoLanMuaHang {
    private String ten;
    private boolean gioiTinh;
    private String sdt;
    private String email;
    private int soLanMuaHang;

    public SoLanMuaHang() {
    }

    public SoLanMuaHang(String ten, boolean gioiTinh, String sdt, String email, int soLanMuaHang) {
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.email = email;
        this.soLanMuaHang = soLanMuaHang;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSoLanMuaHang() {
        return soLanMuaHang;
    }

    public void setSoLanMuaHang(int soLanMuaHang) {
        this.soLanMuaHang = soLanMuaHang;
    }
    
}
