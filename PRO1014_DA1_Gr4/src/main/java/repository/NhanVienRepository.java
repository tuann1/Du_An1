/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import hibernateConfig.HibernateConfig;
import model.NhanVien;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import model.KhachHang;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.EmailSender;
import utilities.RandomPassword;

/**
 *
 * @author ADMIN
 */
public class NhanVienRepository {

    Session session = HibernateConfig.getFACTORY().openSession();
    List<NhanVien> list = new ArrayList<>();

    public List<NhanVien> getAll() {
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From NhanVien WHERE trangThai =: trangThai ORDER BY ID DESC");
        q.setParameter("trangThai", 1);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        List<NhanVien> list = q.getResultList();
        return list;
    }

    public List<NhanVien> getAllNhanVien() {
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From NhanVien");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        List<NhanVien> ds = q.getResultList();
        return ds;
    }

    public NhanVien getNhanVien(String maNv) {
        try {
            String sql = "SELECT * FROM NhanVien WHERE maNV = :ma";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(NhanVien.class);
            query.setParameter("ma", maNv);
            NhanVien results = (NhanVien) query.getSingleResult();
            // NhanVien results = (NhanVien) query.list();

            return results;

        } catch (Exception e) {
            //            System.out.println("lỗi lấy nhân viên");
            //            e.printStackTrace();
            return null;
        }

    }

    public boolean add(NhanVien nv) {
        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            ses.save(nv);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhanVien nv, Integer id) {
        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE NhanVien nv SET nv.maNV=:ma,nv.tenNV=:ten,"
                    + "nv.chucVu.id=:idCV,nv.ngaySinh=:ngaySinh,nv.gioiTinh=:gioiTinh,"
                    + "nv.diaChi=:diaChi,nv.sdt=:sdt,nv.email=:email,nv.pass=:pass WHERE nv.id=:id");
            q.setParameter("ma", nv.getMaNV());
            q.setParameter("ten", nv.getTenNV());
            q.setParameter("idCV", nv.getChucVu().getId());
            q.setParameter("ngaySinh", nv.getNgaySinh());
            q.setParameter("gioiTinh", nv.getGioiTinh());
            q.setParameter("diaChi", nv.getDiaChi());
            q.setParameter("sdt", nv.getSdt());
            q.setParameter("email", nv.getEmail());
            q.setParameter("pass", nv.getPass());
            q.setParameter("id", id);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTrangThai(Integer id) {
        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE NhanVien nv SET nv.trangThai = 0  WHERE nv.id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePass(NhanVien nv) {
        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE NhanVien nv SET nv.pass =: pass WHERE nv.maNV=:ma");
            q.setParameter("pass", nv.getPass());
            q.setParameter("ma", nv.getMaNV());
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean forgotPass(String ma, String email) {
        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE NhanVien nv SET nv.pass =: pass WHERE nv.maNV=:ma AND nv.email=:email");
            String pass = new RandomPassword().randomString(6);
            q.setParameter("pass", pass);
            q.setParameter("ma", ma);
            q.setParameter("email", email);
            if (q.executeUpdate() > 0) {
                EmailSender emailSender = new EmailSender();
                emailSender.guiMail(email, pass);
            }
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NhanVien> pageListNhanVien(int position, int pageSize, String maNv) {
        List<NhanVien> nv;
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        javax.persistence.Query query = em.createQuery("SELECT nv FROM NhanVien nv "
                + "WHERE ( nv.maNV LIKE :maNV or :maNV is null or :maNV = '') and (nv.trangThai = 1) "
                + "ORDER BY nv.id DESC");
        query.setParameter("maNV", "%" + maNv + "%");
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        int pageIndex = position - 1 < 0 ? 0 : position - 1;
        int fromRecordIndex = pageIndex * pageSize;
        query.setFirstResult(fromRecordIndex);
        query.setMaxResults(pageSize);
        nv = query.getResultList();
        return nv;
    }

    @SuppressWarnings("unchecked")
    public List<NhanVien> filterNhanVien(String maNv) {
        List<NhanVien> nv;
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        javax.persistence.Query query = em.createQuery("SELECT nv FROM NhanVien nv "
                + "WHERE (nv.maNV LIKE :maNV or :maNV is null or :maNV = '') and (nv.trangThai = 1) "
                + "ORDER BY nv.id DESC");
        query.setParameter("maNV", "%" + maNv + "%");
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        nv = query.getResultList();

        return nv;
    }

    public static void main(String[] args) {
        List<NhanVien> list = new NhanVienRepository().filterNhanVien("NV02");
        System.out.println(list);
    }
}
