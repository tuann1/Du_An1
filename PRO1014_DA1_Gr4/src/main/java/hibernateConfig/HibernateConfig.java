/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hibernateConfig;

import model.ChatLieu;
import model.ChiTietSanPham;
import model.ChucVu;
import model.DanhMuc;
//import model.GioHang;
//import model.GioHangChiTiet;
import model.HoaDonBan;
import model.HoaDonChiTiet;
import model.KhachHang;
import model.KhuyenMai;
import model.Mau;
import model.NSX;
import model.NhanVien;
import model.SanPham;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author ADMIN
 */
public class HibernateConfig {

    private static final SessionFactory FACTORY;

    static {
        Configuration conf = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.SQLServer2008Dialect");
        properties.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        properties.put(Environment.URL, "jdbc:sqlserver://localhost:1433;databaseName=QLBANTUIXACH");
        properties.put(Environment.USER, "sa");
        properties.put(Environment.PASS, "123456");

        conf.setProperties(properties);

        conf.addAnnotatedClass(NhanVien.class);
        conf.addAnnotatedClass(ChucVu.class);
        conf.addAnnotatedClass(Mau.class);
        conf.addAnnotatedClass(SanPham.class);
        conf.addAnnotatedClass(ChatLieu.class);
        conf.addAnnotatedClass(DanhMuc.class);
        conf.addAnnotatedClass(NSX.class);
        conf.addAnnotatedClass(ChiTietSanPham.class);
        conf.addAnnotatedClass(KhachHang.class);
      //  conf.addAnnotatedClass(GioHang.class);
      //  conf.addAnnotatedClass(GioHangChiTiet.class);
        conf.addAnnotatedClass(KhuyenMai.class);
        conf.addAnnotatedClass(HoaDonBan.class);
        conf.addAnnotatedClass(HoaDonChiTiet.class);
        // 12893467239
        // oihljkasdhf
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory(registry);

    }

    public static SessionFactory getFACTORY() {
        return FACTORY;
    }

    public static void main(String[] args) {
        getFACTORY();
    }
}
