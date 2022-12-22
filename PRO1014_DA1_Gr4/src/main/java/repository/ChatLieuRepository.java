/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import hibernateConfig.HibernateConfig;
import model.ChatLieu;
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
public class ChatLieuRepository {
    Session ses = HibernateConfig.getFACTORY().openSession();

    public List<ChatLieu> getAll() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From ChatLieu WHERE trangThai =: trangThai ORDER BY ID DESC");
        q.setParameter("trangThai", 1);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        List<ChatLieu> list = q.getResultList();
        return list;
    }
    
    public List<ChatLieu> getAllSp() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From ChatLieu ORDER BY ID DESC");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        List<ChatLieu> list = q.getResultList();
        return list;
    }

    public boolean add(ChatLieu cl) {
        Transaction tran = null;
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            ses.saveOrUpdate(cl);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(ChatLieu cl, Integer id) {
        Transaction tran = null;
        try (Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE ChatLieu cl SET cl.maCL=:ma,cl.tenCL=:ten,cl.ngaySua=:ngaySua WHERE cl.id=:id");
            q.setParameter("ma", cl.getMaCL());
            q.setParameter("ten", cl.getTenCL());
            q.setParameter("ngaySua", cl.getNgaySua());
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
            Query q = ses.createQuery("UPDATE ChatLieu cl SET cl.trangThai = 0  WHERE cl.id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ChatLieu getById(int id) {
        ChatLieu chatLieu = null;
        try {
            Query q = ses.createQuery("SELECT cl FROM ChatLieu cl WHERE cl.id =: id");
            q.setParameter("id", id);
            chatLieu = (ChatLieu) q.getSingleResult();
        } catch (Exception e) {
        }
        return chatLieu;
    }
    
     public ChatLieu getByMa(String ma) {
        ChatLieu sp = null;
        try {
            Query q = ses.createQuery("SELECT cl FROM ChatLieu sp WHERE sp.maCL=:ma");
            q.setParameter("ma", ma);
            sp = (ChatLieu) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }
}
