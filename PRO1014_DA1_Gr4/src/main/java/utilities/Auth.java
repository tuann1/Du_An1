package utilities;

import model.KhachHang;
import model.NhanVien;

/**
 *
 * @author fallinluv2003
 */
public class Auth {

    public static NhanVien nv = null;
    public static int isCV = 0;

    public static int getIsCV() {
        return isCV;
    }

    public static void setIsCV(int isCV) {
        Auth.isCV = isCV;
    }    
    
    public static void clear() {
        Auth.nv = null;
    }

    public static boolean isLogin() {
        return Auth.nv != null;
    }
    public static KhachHang kh = null;

    public static void clearKH() {
        Auth.nv = null;
    }

    public static KhachHang isChoose(KhachHang khachHang) {
        return Auth.kh = khachHang;
    }

    public static KhachHang getKh() {
        return kh;
    }

    public static void setKh(KhachHang kh) {
        Auth.kh = kh;
    }

    public static NhanVien getNv() {
        return nv;
    }

    public static void setNv(NhanVien nv) {
        Auth.nv = nv;
    }
    
    

}
