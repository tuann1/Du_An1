/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import hibernateConfig.HibernateConfig;
import model.Mau;
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
public class MauRepository {
    Session ses = HibernateConfig.getFACTORY().openSession();

    public List<Mau> getAll() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From Mau WHERE trangThai =: trangThai ORDER BY ID DESC");
        q.setParameter("trangThai", 1);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        List<Mau> list = q.getResultList();
        return list;
    }

    public List<Mau> getAllSp() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From Mau ORDER BY ID DESC");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        List<Mau> list = q.getResultList();
        return list;
    }
    
    public boolean add(Mau ms) {
        Transaction tran = null;
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            ses.saveOrUpdate(ms);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Mau ms, Integer id) {
        Transaction tran = null;
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE Mau ms SET ms.maMau=:ma,ms.tenMau=:ten,ms.ngaySua=:ngaySua WHERE ms.id=:id");
            q.setParameter("ma", ms.getMaMau());
            q.setParameter("ten", ms.getTenMau());
            q.setParameter("ngaySua", ms.getNgaySua());
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
            Query q = ses.createQuery("UPDATE Mau mau SET mau.trangThai = 0  WHERE mau.id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Mau getById(int id) {
        Mau mau = null;
        try {
            Query q = ses.createQuery("SELECT mau FROM Mau mau WHERE mau.id =: id");
            q.setParameter("id", id);
            mau = (Mau) q.getSingleResult();
        } catch (Exception e) {
        }
        return mau;
    }
    
     public Mau getByMa(String ma) {
        Mau sp = null;
        try {
            Query q = ses.createQuery("SELECT sp FROM Mau sp WHERE sp.maMau=:ma");
            q.setParameter("ma", ma);
            sp = (Mau) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }
}
