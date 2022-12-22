/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import hibernateConfig.HibernateConfig;
import model.NSX;
import java.util.ArrayList;
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
public class NSXRepository {
    Session ses = HibernateConfig.getFACTORY().openSession();

    public List<NSX> getAll() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From NSX WHERE trangThai =: trangThai ORDER BY ID DESC");
        q.setParameter("trangThai", 1);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        List<NSX> list = q.getResultList();
        return list;
    }

    public List<NSX> getAllSp() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From NSX ORDER BY ID DESC");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        List<NSX> list = q.getResultList();
        return list;
    }
    
    public boolean add(NSX nsx) {
        Transaction tran = null;
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            ses.saveOrUpdate(nsx);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NSX nsx, Integer id) {
        Transaction tran = null;
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE NSX nsx SET nsx.maNSX=:ma,nsx.tenNSX=:ten,nsx.ngaySua=:ngaySua WHERE nsx.id=:id");
            q.setParameter("ma", nsx.getMaNSX());
            q.setParameter("ten", nsx.getTenNSX());
            q.setParameter("ngaySua", nsx.getNgaySua());
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
            Query q = ses.createQuery("UPDATE NSX nsx SET nsx.trangThai = 0  WHERE nsx.id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public NSX getById(int id) {
        NSX nsx = null;
        try {
            Query q = ses.createQuery("SELECT nsx FROM NSX nsx WHERE nsx.id =: id");
            q.setParameter("id", id);
            nsx = (NSX) q.getSingleResult();
        } catch (Exception e) {
        }
        return nsx;
    }
    
     public NSX getByMa(String ma) {
        NSX nsx = null;
        try {
            Query q = ses.createQuery("SELECT sp FROM NSX sp WHERE sp.maNSX=:ma");
            q.setParameter("ma", ma);
            nsx = (NSX) q.setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nsx;
    }
}
