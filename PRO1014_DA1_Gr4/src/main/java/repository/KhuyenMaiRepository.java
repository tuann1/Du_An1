/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import hibernateConfig.HibernateConfig;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import model.KhuyenMai;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.openide.util.Exceptions;

/**
 *
 * @author T450s
 */
public class KhuyenMaiRepository {

    public ArrayList<KhuyenMai> getAll() {
        Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        Query q = se.createQuery("From KhuyenMai km order by km.id desc");
        ArrayList<KhuyenMai> ds = (ArrayList<KhuyenMai>) q.getResultList();
        return ds;
    }

    public void insert(KhuyenMai km) {
        try ( Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            Transaction tran = se.beginTransaction();
            Integer rowSucc = (Integer) se.save(km);
            tran.commit();
            System.out.println("So ban da duoc ghi" + rowSucc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dele(int id) {
        try ( Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            Transaction tran = se.beginTransaction();
            String hql = "Update KhuyenMai km set km.trangthai = :trangThai where km.id= :id";
            Query q = se.createQuery(hql);
            q.setParameter("id", id);
            q.setParameter("trangThai", 0);
            int suc = q.executeUpdate();
            tran.commit();
            System.out.println("So ban da duoc ghi thanh cong" + suc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(KhuyenMai km, int id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try ( Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            Transaction tran = se.beginTransaction();

            String hql = "Update KhuyenMai km set km.tenkm = :tenkm, km.ngayTao = :ngayTao , km.phantramgiam = :phantramgiam, km.minhoadon= :minhoadon, km.ngayhethan = :ngayHethan , km.ghichu= :ghichu, km.trangthai= :trangthai where km.id= :id";
            Query q = se.createQuery(hql);

//            q.setParameter("makm", km.getMakm());
            q.setParameter("tenkm", km.getTenkm());
            q.setParameter("ngayTao", km.getNgayTao(), TemporalType.DATE);
            q.setParameter("phantramgiam", km.getPhantramgiam());
            q.setParameter("minhoadon", km.getMinhoadon());
            q.setParameter("ngayHethan", km.getNgayhethan(), TemporalType.DATE);
            q.setParameter("ghichu", km.getGhichu());
            q.setParameter("trangthai", km.getTrangthai());
            q.setParameter("id", id);
            q.executeUpdate();
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<KhuyenMai> getAllKhuyenMaiMap(double minHoaDon) {
        Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        Query q = se.createQuery("From KhuyenMai where MinHoaDon < : min and TrangThai = 1 and Ngayhethan >= convert(varchar, getdate(), 23)");
        q.setParameter("min", (minHoaDon + 1));
        ArrayList<KhuyenMai> ds = (ArrayList<KhuyenMai>) q.getResultList();
        return ds;
    }

    public KhuyenMai getKhuyenMaiByMa(String maKM) {
        try {
            String sql = "SELECT * FROM KhuyenMai WHERE MaKM = :ma";
            SQLQuery query = HibernateConfig.getFACTORY().openSession().createSQLQuery(sql);
            query.addEntity(KhuyenMai.class);
            query.setParameter("ma", maKM);
            KhuyenMai results = (KhuyenMai) query.getSingleResult();
            // NhanVien results = (NhanVien) query.list();
            if (results == null) {
                return null;
            }
            return results;

        } catch (Exception e) {
            //            System.out.println("lỗi lấy nhân viên");
            //            e.printStackTrace();
            return null;
        }

    }

    public ArrayList<KhuyenMai> getAllByTrangT(int tt) {
        Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        Query q = se.createQuery("From KhuyenMai km where km.trangthai =:trangThai order by km.id desc");
        q.setParameter("trangThai", tt);
        ArrayList<KhuyenMai> ds = (ArrayList<KhuyenMai>) q.getResultList();
        return ds;
    }

    public ArrayList<KhuyenMai> searchByDate(String ngTao, String ngHet, int trangThai) {
        ArrayList<KhuyenMai> ds = new ArrayList<>();

        try ( Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            Transaction trann = se.beginTransaction();
            String hql = " From KhuyenMai km where (km.ngayTao>=:ngayTao or :ngayTao is null or :ngayTao='')"
                    + " and (km.ngayhethan<=:ngayhethan or :ngayhethan is null or :ngayhethan='')"
                    + "and(km.trangthai =:trangThai) order by km.id desc";
            Query q = se.createQuery(hql);
            Date a = Date.valueOf(ngTao);
            Date b = Date.valueOf(ngHet);
            q.setParameter("ngayTao", a);
            q.setParameter("ngayhethan", b);
            q.setParameter("trangThai", trangThai);
//            q.executeUpdate();
            ds = (ArrayList<KhuyenMai>) q.getResultList();
            trann.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    public List<KhuyenMai> pageListKhuyenMai(int position, int pageSize, String ngTao, String ngHet, int tt) {
        List<KhuyenMai> km;
        Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        EntityManager em = se.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();
        String hql = "Select km From KhuyenMai km where (km.ngayTao>=:ngayTao or :ngayTao is null or :ngayTao='')"
                + " and (km.ngayhethan<=:ngayhethan or :ngayhethan is null or :ngayhethan='')"
                + "and(km.trangthai =:trangThai) order by km.id desc";
        Query query = em.createQuery(hql);
        Date a = null;
        Date b = null;
        if (ngTao != null && ngHet != null) {
            a = Date.valueOf(ngTao);
            b = Date.valueOf(ngHet);
        }
        query.setParameter("ngayTao", a);
        query.setParameter("ngayhethan", b);
        query.setParameter("trangThai", tt);
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        int pageIndex = position - 1 < 0 ? 0 : position - 1;
        int fromRecordIndex = pageIndex * pageSize;
        query.setFirstResult(fromRecordIndex);
        query.setMaxResults(pageSize);
        km = query.getResultList();
        return km;
    }

    public List<KhuyenMai> filterProductKhuyenMai(String ngTao, String ngHet, int tt) {
        List<KhuyenMai> hdbs;
        Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        EntityManager em = se.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();
        String hql = "Select km From KhuyenMai km where (km.ngayTao>=:ngayTao or :ngayTao is null or :ngayTao='')"
                + " and (km.ngayhethan<=:ngayhethan or :ngayhethan is null or :ngayhethan='')"
                + "and(km.trangthai =:trangThai) order by km.id desc";
        Query query = em.createQuery(hql);
        Date a = null;
        Date b = null;
        if (ngTao != null && ngHet != null) {
            a = Date.valueOf(ngTao);
            b = Date.valueOf(ngHet);
        }
        query.setParameter("ngayTao", a);
        query.setParameter("ngayhethan", b);
        query.setParameter("trangThai", tt);
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        hdbs = query.getResultList();

        return hdbs;
    }
    
    
    public void checkEndDate(){
        try (Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession()){
            Transaction trann = se.beginTransaction();
            String hql="update KhuyenMai km set km.trangthai =0 where km.ngayhethan < convert(varchar, getdate(), 23)";
            Query q = se.createQuery(hql);
            q.executeUpdate();
            trann.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd");
//        Date n1 = null;
//        Date n2 = null;
//        try {
//            n1 = sdm.parse("2023-01-01");
//            n2 = sdm.parse("2023-03-03");
//        } catch (ParseException ex) {
//            Exceptions.printStackTrace(ex);
//        }
        List<KhuyenMai> list = new KhuyenMaiRepository().pageListKhuyenMai(2, 5, "2022-12-01", "2022-12-30", 1);
        for (KhuyenMai khuyenMai : list) {
            System.out.println(khuyenMai);
        }
    }

}
