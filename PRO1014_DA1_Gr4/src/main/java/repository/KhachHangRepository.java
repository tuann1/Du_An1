/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import model.KhachHang;
import hibernateConfig.HibernateConfig;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import model.ChiTietSanPham;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Laptop
 */
public class KhachHangRepository {

    Session session = HibernateConfig.getFACTORY().openSession();

    public List<KhachHang> getAll(int trangThai) {
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = (Query) em.createQuery("From KhachHang where TrangThai =: trangthai order by ID desc");
        query.setParameter("trangthai", trangThai);
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        @SuppressWarnings("unchecked")
        List<KhachHang> list = query.getResultList();
        return list;
    }

    public List<KhachHang> getAllKhachHang() {
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = (Query) em.createQuery("From KhachHang ");

        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        @SuppressWarnings("unchecked")
        List<KhachHang> list = query.getResultList();
        return list;
    }

    public List<KhachHang> getAllByTrangThai(int trangThai) {
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From KhachHang where trangThai =: trangthai");
        q.setParameter("trangthai", trangThai);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        List<KhachHang> list = q.getResultList();
        return list;
    }

    public boolean add(KhachHang kh) {
        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            ses.save(kh);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(KhachHang kh, Integer id) {
        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE KhachHang kh SET kh.maKH=:ma,kh.tenKH=:ten,kh.gioiTinh=:gioiTinh,"
                    + "kh.diaChi=:diaChi,kh.sdt=:sdt,kh.email=:email WHERE kh.id=:id");
            q.setParameter("ma", kh.getMaKH());
            q.setParameter("ten", kh.getTenKH());
            q.setParameter("gioiTinh", kh.getGioiTinh());
            q.setParameter("diaChi", kh.getDiaChi());
            q.setParameter("sdt", kh.getSdt());
            q.setParameter("email", kh.getEmail());
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
            Query q = ses.createQuery("UPDATE KhachHang kh SET kh.trangThai = 0 WHERE kh.id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public KhachHang getKhachHangByMa(String ma) {
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From KhachHang where MaKH =: ma");
        q.setParameter("ma", ma);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        List<KhachHang> list = q.getResultList();
        KhachHang khachHang = (KhachHang) q.getSingleResult();
        return khachHang;
    }

    public Long getSumCustomer() {
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = (Query) em.createQuery("SELECT COUNT(id) FROM KhachHang kh where kh.trangThai = 1");
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        @SuppressWarnings("unchecked")
        Long list = (Long) query.getSingleResult();
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<KhachHang> pageListKhachHang(int position, int pageSize, String tenKh) {
        List<KhachHang> kh;
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        javax.persistence.Query query = em.createQuery("SELECT kh FROM KhachHang kh "
                + "WHERE ( kh.tenKH LIKE :tenKH or :tenKH is null or :tenKH = '') and (kh.trangThai = '1') "
                + "ORDER BY kh.id DESC");
        query.setParameter("tenKH", "%" + tenKh + "%");
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        int pageIndex = position - 1 < 0 ? 0 : position - 1;
        int fromRecordIndex = pageIndex * pageSize;
        query.setFirstResult(fromRecordIndex);
        query.setMaxResults(pageSize);
        kh = query.getResultList();
        return kh;
    }

    @SuppressWarnings("unchecked")
    public List<KhachHang> filterKhachHang(String tenKh) {
        List<KhachHang> kh;
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        javax.persistence.Query query = em.createQuery("SELECT kh FROM KhachHang kh "
                + "WHERE (kh.tenKH LIKE :tenKH or :tenKH is null or :tenKH = '') and (kh.trangThai = '1') "
                + "ORDER BY kh.id DESC");
        query.setParameter("tenKH", "%" + tenKh + "%");
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        kh = query.getResultList();

        return kh;
    }

    public static void main(String[] args) {
        List<KhachHang> ds = new KhachHangRepository().pageListKhachHang(0, 5, null);
        System.out.println(ds);
    }
}
