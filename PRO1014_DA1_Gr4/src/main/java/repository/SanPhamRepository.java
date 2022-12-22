/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import hibernateConfig.HibernateConfig;
import model.SanPham;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ADMIN
 */
public class SanPhamRepository {

    Session ses = HibernateConfig.getFACTORY().openSession();

    public List<SanPham> getAll() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From SanPham WHERE trangThai =: trangThai ORDER BY ID DESC");
        q.setParameter("trangThai", 1);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        List<SanPham> list = q.getResultList();
        return list;
    }

    public List<SanPham> getAllSp() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From SanPham ORDER BY ID DESC");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        List<SanPham> list = q.getResultList();
        return list;
    }
    
    public boolean add(SanPham sp) {
        Transaction tran = null;
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            ses.save(sp);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(SanPham sp, Integer id) {
        Transaction tran = null;
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE SanPham s SET s.maSP=:ma,s.tenSP=:ten,s.ngaySua=:ngaySua WHERE s.id=:id");
            q.setParameter("ma", sp.getMaSP());
            q.setParameter("ten", sp.getTenSP());
            q.setParameter("ngaySua", sp.getNgaySua());
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
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE SanPham sp SET sp.trangThai = 0  WHERE sp.id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public SanPham getById(int id) {
        SanPham sanPham = null;
        try {
            Query q = ses.createQuery("SELECT sp FROM SanPham sp WHERE sp.id=:id");
            q.setParameter("id", id);
            sanPham = (SanPham) q.getSingleResult();
        } catch (Exception e) {
        }
        return sanPham;
    }
    
    public SanPham getByMa(String ma) {
        SanPham sanPham = null;
        try {
            Query q = ses.createQuery("SELECT sp FROM SanPham sp WHERE sp.maSP=:ma");
            q.setParameter("ma", ma);
            sanPham = (SanPham) q.getSingleResult();
        } catch (Exception e) {
        }
        return sanPham;
    }
}
