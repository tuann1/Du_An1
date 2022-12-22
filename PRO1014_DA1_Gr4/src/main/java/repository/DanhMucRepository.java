/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import hibernateConfig.HibernateConfig;
import model.DanhMuc;
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
public class DanhMucRepository {
    Session ses = HibernateConfig.getFACTORY().openSession();

    public List<DanhMuc> getAll() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From DanhMuc WHERE trangThai =: trangThai ORDER BY ID DESC");
        q.setParameter("trangThai", 1);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        List<DanhMuc> list = q.getResultList();
        return list;
    }

    public List<DanhMuc> getAllSp() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From DanhMuc ORDER BY ID DESC");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        List<DanhMuc> list = q.getResultList();
        return list;
    }
    
    public boolean add(DanhMuc dm) {
        Transaction tran = null;
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            ses.saveOrUpdate(dm);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(DanhMuc dm, Integer id) {
        Transaction tran = null;
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE DanhMuc dm SET dm.maDM=:ma,dm.tenDM=:ten,dm.ngaySua=:ngaySua WHERE dm.id=:id");
            q.setParameter("ma", dm.getMaDM());
            q.setParameter("ten", dm.getTenDM());
            q.setParameter("ngaySua", dm.getNgaySua());
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
            Query q = ses.createQuery("UPDATE DanhMuc dm SET dm.trangThai = 0  WHERE dm.id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public DanhMuc getById(int id) {
        DanhMuc danhMuc = null;
        try {
            Query q = ses.createQuery("SELECT dm FROM DanhMuc dm WHERE dm.id=:id");
            q.setParameter("id", id);
            danhMuc = (DanhMuc) q.getSingleResult();
        } catch (Exception e) {
        }
        return danhMuc;
    }
    
     public DanhMuc getByMa(String ma) {
        DanhMuc sp = null;
        try {
            Query q = ses.createQuery("SELECT sp FROM DanhMuc sp WHERE sp.maDM=:ma");
            q.setParameter("ma", ma);
            sp = (DanhMuc) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return sp;
    }
}
