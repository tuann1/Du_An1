/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import customModel.SoLanMuaHang;
import hibernateConfig.HibernateConfig;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import model.ChiTietSanPham;
import model.HoaDonBan;
import model.HoaDonChiTiet;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HoaDonBanRepository {

    Session ses = hibernateConfig.HibernateConfig.getFACTORY().openSession();

    public ArrayList<HoaDonBan> getAll() {
        Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        Query q = se.createQuery("From HoaDonBan order by id desc");
        // q.setParameter("trangthai", 1);
        ArrayList<HoaDonBan> ds = (ArrayList<HoaDonBan>) q.getResultList();
        return ds;
    }

    public ArrayList<HoaDonBan> getAllByTrangThai(int trangThai) {
        Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        Query q = se.createQuery("From HoaDonBan c where c.trangThai =: trangthai  order by c.id desc");
        q.setParameter("trangthai", trangThai);
        ArrayList<HoaDonBan> ds = (ArrayList<HoaDonBan>) q.getResultList();
        return ds;
    }

    public ArrayList<HoaDonBan> getAllByMa(String ma) {
        Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        Query q = se.createQuery("From HoaDonBan  where trangThai =2 and maHDB=: ma order by id desc");
        q.setParameter("ma", ma);
        @SuppressWarnings("unchecked")
        ArrayList<HoaDonBan> ds = (ArrayList<HoaDonBan>) q.getResultList();
        return ds;
    }

    public Boolean add(HoaDonBan hdb) {
        try ( Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            Transaction tran = se.beginTransaction();
            se.save(hdb);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sua(HoaDonBan hdb, Integer id) {
        try ( Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            Transaction tran = se.beginTransaction();
            String hql = "Update HoaDonBan hd set hd.nhanVien.id= :idNv,hd.khachHang.id= :idKh,hd.maHDB =:mahdb, hd.khuyenMai.id =:idkm ,"
                    + "hd.nguoiNhan =:nguoiNhan,hd.ngayThanhToan= :ngayThanhToan,hd.sdt =:sdt, hd.diaChi =:diaChi, hd.trangThai =:trangThai"
                    + " where hd.id = :id";
            Query q = se.createQuery(hql);
            q.setParameter("id", id);
            q.setParameter("idKh", hdb.getKhachHang().getId());
            q.setParameter("idNv", hdb.getNhanVien().getId());
            q.setParameter("ngayThanhToan", hdb.getNgayThanhToan(), TemporalType.DATE);
            q.setParameter("mahdb", hdb.getMaHDB());
            q.setParameter("idkm", hdb.getKhuyenMai().getId());
            q.setParameter("nguoiNhan", hdb.getNguoiNhan());
            q.setParameter("sdt", hdb.getSdt());
            q.setParameter("diaChi", hdb.getDiaChi());
            q.setParameter("trangThai", hdb.getTrangThai());
            q.executeUpdate();
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean suaKH(HoaDonBan hdb) {
        try ( Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            Transaction tran = se.beginTransaction();
            String hql = "Update HoaDonBan hd set hd.khachHang.id= :idKh"
                    + " where hd.id = :id";
            Query q = se.createQuery(hql);
            q.setParameter("id", hdb.getId());
            q.setParameter("idKh", hdb.getKhachHang().getId());
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void dele(int id) {
        try ( Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            Transaction tran = se.beginTransaction();
            String hql = "Update HoaDonBan hd set hd.trangThai =:trangThai"
                    + " where hd.id = :id";
            Query q = se.createQuery(hql);
            q.setParameter("id", id);
            q.setParameter("trangThai", 1);
            q.executeUpdate();
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<HoaDonChiTiet> getHoaDonChiTietByHD(int idHD) {
        Session session = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        try {
            Query query = session.createQuery("From HoaDonChiTiet where IdHD =: id");
            query.setParameter("id", idHD);
            List<HoaDonChiTiet> hdct = query.getResultList();
            // NhanVien results = (NhanVien) query.list();

            return hdct;

        } catch (Exception e) {
            return null;
        }
    }

    public List<HoaDonBan> getHoaDonByKH(int idKH) {
        Session session = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        try {
            Query query = session.createQuery("From HoaDonBan where IdKH =: id");
            query.setParameter("id", idKH);
            List<HoaDonBan> hd = query.getResultList();

            return hd;

        } catch (Exception e) {
            return null;
        }
    }

    public Long countHoaDonByIdKH(int idKH) {
        Session session = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        try {
            Query query = session.createQuery("SELECT COUNT(ID) FROM HoaDonBan WHERE IdKH =: IdKH");
            query.setParameter("IdKH", idKH);
            Long so = (Long) query.getResultList().get(0);
            return so;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // ========================================================================================================================================

    public Boolean addHoaDonChiTiet(HoaDonChiTiet hdct) {
        try ( Session se = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            Transaction tran = se.beginTransaction();
            se.save(hdct);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean updateTrangThaiHoaDon(int id, int trangThai) {
        Transaction transision = null;
        Integer check = 0;
        try ( Session session = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            transision = session.beginTransaction();
            Query query = session.createQuery("UPDATE HoaDonBan SET  TrangThai = :trangthai where Id = :id");
            query.setParameter("trangthai", trangThai);
            query.setParameter("id", id);
            check = query.executeUpdate();
            transision.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean updateTrangThaiHoaDonChiTiet(int id, int trangThai) {
        Transaction transision = null;
        Integer check = 0;
        try ( Session session = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            transision = session.beginTransaction();
            Query query = session.createQuery("UPDATE HoaDonChiTiet SET  TrangThai = :trangthai where IdHD = :id");
            query.setParameter("trangthai", trangThai);
            query.setParameter("id", id);
            check = query.executeUpdate();
            transision.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean updateTrangThaiHoaDonChiTietbyIDHDCT(int id, int trangThai) {
        Transaction transision = null;
        Integer check = 0;
        try ( Session session = hibernateConfig.HibernateConfig.getFACTORY().openSession()) {
            transision = session.beginTransaction();
            Query query = session.createQuery("UPDATE HoaDonChiTiet SET  TrangThai = :trangthai where Id = :id");
            query.setParameter("trangthai", trangThai);
            query.setParameter("id", id);
            check = query.executeUpdate();
            transision.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean deleteHoaDonChitiet(int idCTSP) {
        Transaction transision = null;
        Integer check = 0;
        try ( Session session = HibernateConfig.getFACTORY().openSession()) {
            transision = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("DELETE HoaDonChiTiet WHERE IdCTSP =: id");
            query.setParameter("id", idCTSP);
            check = query.executeUpdate();
            transision.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean updateSoLuongHDCT(int idCTSP, int soLuong) {
        Transaction transision = null;
        Integer check = 0;
        try ( Session session = HibernateConfig.getFACTORY().openSession()) {
            transision = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("UPDATE HoaDonChiTiet set SoLuong =:soLuong WHERE IdCTSP =:id ");
            query.setParameter("id", idCTSP);
            query.setParameter("soLuong", soLuong);
            check = query.executeUpdate();
            transision.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean updateSoLuongHDCTbyIDHDCT(int idHDCT, int soLuong) {
        Transaction transision = null;
        Integer check = 0;
        try ( Session session = HibernateConfig.getFACTORY().openSession()) {
            transision = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("UPDATE HoaDonChiTiet set SoLuong =:soLuong WHERE ID =:id ");
            query.setParameter("id", idHDCT);
            query.setParameter("soLuong", soLuong);
            check = query.executeUpdate();
            transision.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Long getCountHoaDon() {
        Session ses = hibernateConfig.HibernateConfig.getFACTORY().openSession();
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("SELECT COUNT(ID) FROM HoaDonBan WHERE trangThai = 2");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        Long list = (Long) q.getSingleResult();
        return list;
    }

    public List<SoLanMuaHang> getSoLanMuaHang() {
        Transaction transaction = ses.beginTransaction();
        Query query = null;

        try {
            String sql = "SELECT KhachHang.TenKH,KhachHang.GioiTinh,KhachHang.SDT,KhachHang.DiaChi,COUNT(IdKH)\n"
                    + "FROM HoaDon join KhachHang on HoaDon.IdKH = KhachHang.ID\n"
                    + "GROUP BY KhachHang.TenKH,KhachHang.GioiTinh,KhachHang.SDT,KhachHang.DiaChi";
            query = ses.createSQLQuery(sql);
            List<SoLanMuaHang> list = new ArrayList<>();
            List<Object[]> rows = query.getResultList();
            for (Object[] row : rows) {
                SoLanMuaHang soLan = new SoLanMuaHang();
                soLan.setTen(row[0].toString());
                soLan.setGioiTinh(Boolean.valueOf(row[1].toString()));
                soLan.setSdt(row[2].toString());
                soLan.setEmail(row[3].toString());
                soLan.setSoLanMuaHang(Integer.valueOf(row[4].toString()));
                list.add(soLan);
            }

            transaction.commit();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("cc");
            return null;
        }
    }

    public List<HoaDonBan> pageListTraHang(int position, int pageSize, String maHD) {
        List<HoaDonBan> hdbs;
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = em.createQuery("SELECT hdb FROM HoaDonBan hdb"
                + " WHERE (hdb.maHDB LIKE :sp or :sp is null or :sp = '')"
                + "AND hdb.trangThai =2"
                + "ORDER BY hdb.id DESC");
        query.setParameter("sp", "%" + maHD + "%");
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        int pageIndex = position - 1 < 0 ? 0 : position - 1;
        int fromRecordIndex = pageIndex * pageSize;
        query.setFirstResult(fromRecordIndex);
        query.setMaxResults(pageSize);
        hdbs = query.getResultList();
        return hdbs;
    }

    public List<HoaDonBan> filterProductTraHang(String maHD) {
        List<HoaDonBan> hdbs;
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = em.createQuery("SELECT hdb FROM HoaDonBan hdb "
                + " WHERE (hdb.maHDB LIKE :sp or :sp is null or :sp = '')"
                + "AND hdb.trangThai =2"
                + "ORDER BY hdb.id DESC");
        query.setParameter("sp", "%" + maHD + "%");
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        hdbs = query.getResultList();

        return hdbs;
    }

    
        public List<HoaDonBan> pageListHoaDon(int position, int pageSize, String maHD, int tt) {
        List<HoaDonBan> hdbs;
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = em.createQuery("SELECT hdb FROM HoaDonBan hdb"
                + " WHERE (hdb.maHDB LIKE :hdb or :hdb is null or :hdb = '')"
                + "AND (hdb.trangThai =: tt )"
                + "ORDER BY hdb.id DESC");
        query.setParameter("hdb", "%" + maHD + "%");
        query.setParameter("tt",  tt);
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        int pageIndex = position - 1 < 0 ? 0 : position - 1;
        int fromRecordIndex = pageIndex * pageSize;
        query.setFirstResult(fromRecordIndex);
        query.setMaxResults(pageSize);
        hdbs = query.getResultList();
        return hdbs;
    }

    public List<HoaDonBan> filterProductHoaDon(String maHD, int tt) {
        List<HoaDonBan> hdbs;
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = em.createQuery("SELECT hdb FROM HoaDonBan hdb "
                + " WHERE (hdb.maHDB LIKE :hdb or :hdb is null or :hdb = '')"
                + "AND (hdb.trangThai =: tt)"
                + "ORDER BY hdb.id DESC");
        query.setParameter("hdb", "%" + maHD + "%");
        query.setParameter("tt", tt );
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        hdbs = query.getResultList();

        return hdbs;
    }
    public static void main(String[] args) {
        List<HoaDonBan> list = new HoaDonBanRepository().pageListHoaDon(2, 5, null, 0);
        for (HoaDonBan chiTietSanPham : list) {
            System.out.println(chiTietSanPham.getMaHDB());
        }
    }

}
