package repository;

import hibernateConfig.HibernateConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import model.ChatLieu;
import model.ChiTietSanPham;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.DanhMuc;
import model.Mau;
import model.NSX;
import static mssql.googlecode.concurrentlinkedhashmap.Weighers.list;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

/**
 *
 * @author fallinluv2003
 */
public class CTSPRepository {

    Session ses = HibernateConfig.getFACTORY().openSession();

    public List<ChiTietSanPham> getAll() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From ChiTietSanPham WHERE trangThai =: trangThai and SoLuongTon > 0 ORDER BY ID  ");
        q.setParameter("trangThai", 1);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        List<ChiTietSanPham> list = q.getResultList();
        return list;
    }

    public boolean add(ChiTietSanPham ctsp) {
        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            ses.save(ctsp);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(ChiTietSanPham ctsp, Integer id) {
        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE ChiTietSanPham ctsp SET ctsp.ma=:ma,ctsp.sanPham.id=:idSP,"
                    + "ctsp.nhaSanXuat.id=:idNSX,ctsp.danhMuc.id=:idDM,ctsp.chatLieu.id=:idCL,"
                    + "ctsp.mauSac.id=:idMS,ctsp.moTa=:moTa,ctsp.soLuongTon=:soLuongTon,"
                    + "ctsp.giaNhap=:giaNhap,ctsp.giaBan=:giaBan,ctsp.ngaySua=:ngaySua WHERE ctsp.id=:id");
            q.setParameter("ma", ctsp.getMa());
            q.setParameter("idSP", ctsp.getSanPham().getId());
            q.setParameter("idNSX", ctsp.getNhaSanXuat().getId());
            q.setParameter("idDM", ctsp.getDanhMuc().getId());
            q.setParameter("idCL", ctsp.getChatLieu().getId());
            q.setParameter("idMS", ctsp.getMauSac().getId());
            q.setParameter("moTa", ctsp.getMoTa());
            q.setParameter("soLuongTon", ctsp.getSoLuongTon());
            q.setParameter("giaNhap", ctsp.getGiaNhap());
            q.setParameter("giaBan", ctsp.getGiaBan());
            q.setParameter("ngaySua", ctsp.getNgaySua());
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
            Query q = ses.createQuery("UPDATE ChiTietSanPham ctsp SET ctsp.trangThai = 0  WHERE ctsp.id=:id");
            q.setParameter("id", id);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long getSumProduct() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("SELECT SUM(soLuongTon) FROM ChiTietSanPham");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        Long list = (Long) q.getSingleResult();
        return list;
    }

    public int getSoLuongSpByMaCTSP(String maCTSP) {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("SELECT soLuongTon FROM ChiTietSanPham where MaCTSP =:ma");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        q.setParameter("ma", maCTSP);

        int soLuong = (int) q.getSingleResult();
        return soLuong;
    }

    public Boolean updateSoLuongCTSP(String maCTSP, int so) {

        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE ChiTietSanPham  SET soLuongTon  = soLuongTon - :so  WHERE MaCTSP =:ma");
            q.setParameter("ma", maCTSP);
            q.setParameter("so", so);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean updateSoLuongCTSPTraHang(String maCTSP, int so) {

        Transaction tran = null;
        try ( Session ses = HibernateConfig.getFACTORY().openSession()) {
            tran = ses.beginTransaction();
            Query q = ses.createQuery("UPDATE ChiTietSanPham  SET soLuongTon  = soLuongTon + :so  WHERE MaCTSP =:ma");
            q.setParameter("ma", maCTSP);
            q.setParameter("so", so);
            q.executeUpdate();
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ChiTietSanPham> getChiTietSanPhamByComBoBox(DanhMuc isdanhMuc, ChatLieu isChatLieu, Mau isMau, NSX isNsx) {
        Transaction transaction = ses.beginTransaction();
        String sqlEx = null;
        Query query = null;
        String sql = "select \n"
                + "ChiTietSP.ID,\n"
                + "ChiTietSP.MaCTSP,\n"
                + "SanPham.TenSP,\n"
                + "DanhMuc.TenDM,\n"
                + "ChatLieu.TenCL ,\n"
                + "Mau.TenMau,\n"
                + "NSX.TenNSX,\n"
                + "ChiTietSP.SoluongTon,\n"
                + "ChiTietSP.GiaNhap,\n"
                + "ChiTietSP.GiaBan,\n"
                + "ChiTietSP.MoTa,\n"
                + "ChiTietSP.NgayTao,\n"
                + "ChiTietSP.NgaySua,\n"
                + "ChiTietSP.TrangThai\n"
                + "from ChiTietSP join SanPham on SanPham.ID = ChiTietSP.IdSP\n"
                + "join DanhMuc on DanhMuc.ID =ChiTietSP.IdDM\n"
                + "join ChatLieu on ChatLieu.ID = ChiTietSP.IdCL\n"
                + "join Mau on Mau.ID = ChiTietSP.IdMau\n"
                + "join NSX on NSX.ID = ChiTietSP.IdNSX where 1=1 ";
        // Query queryx = query;
        if (isdanhMuc != null) {
            sql = sql + " and DanhMuc.ID =:iddm ";
        }
        if (isChatLieu != null) {
            sql = sql + " and ChatLieu.ID =:idcl";
        }
        if (isMau != null) {
            sql = sql + " and Mau.ID =:idmau";
        }
        if (isNsx != null) {
            sql = sql + " and NSX.ID =:idnsx";
        }

        query = ses.createSQLQuery(sql);
        if (isdanhMuc != null) {
            query = query.setParameter("iddm", isdanhMuc.getId());
        }
        if (isChatLieu != null) {
            query = query.setParameter("idcl", isChatLieu.getId());
        }
        if (isMau != null) {
            query = query.setParameter("idmau", isMau.getId());
        }
        if (isNsx != null) {
            query = query.setParameter("idnsx", isNsx.getId());
        }

        try {
            List<Object[]> rows = query.getResultList();
            List<ChiTietSanPham> list = new ArrayList<>();
            for (Object[] row : rows) {
                ChiTietSanPham ctsp = new ChiTietSanPham();
                ctsp.setId(Integer.parseInt(row[0].toString()));
                ctsp.setMa(row[1].toString());
                list.add(ctsp);
            }
            transaction.commit();
            return list;

        } catch (Exception e) {
            System.out.println("cc");
            return null;
        }
    }

    public ChiTietSanPham getAllByID(int id) {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("From ChiTietSanPham WHERE ID=:id and trangThai =: trangThai and SoLuongTon > 0 ORDER BY ID DESC");
        q.setParameter("id", id);
        q.setParameter("trangThai", 1);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        ChiTietSanPham ctsp = (ChiTietSanPham) q.getSingleResult();
        return ctsp;
    }

    public Long getProduct() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("SELECT COUNT(Id) FROM ChiTietSanPham ctsp WHERE ctsp.trangThai =: trangThai");
        q.setParameter("trangThai", 1);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        Long list = (Long) q.getSingleResult();
        return list;
    }

    public Long getNonProduct() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("SELECT COUNT(Id) FROM ChiTietSanPham ctsp WHERE ctsp.trangThai =: trangThai");
        q.setParameter("trangThai", 0);
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        Long list = (Long) q.getSingleResult();
        return list;
    }

    public Long getOutProduct() {
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query q = (Query) em.createQuery("SELECT COUNT(Id) FROM ChiTietSanPham ctsp WHERE ctsp.trangThai = 1 AND ctsp.soLuongTon < 10");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        Long list = (Long) q.getSingleResult();
        return list;
    }

    public ChiTietSanPham getByMa(String ma) {
        ChiTietSanPham ctsp = null;
        try {
            Query q = ses.createQuery("SELECT ctsp FROM ChiTietSanPham ctsp WHERE ctsp.ma=:ma");
            q.setParameter("ma", ma);
            ctsp = (ChiTietSanPham) q.getSingleResult();
        } catch (Exception e) {
        }
        return ctsp;
    }

    public long totalCount() {
        long total = 0;
        try ( Session session = HibernateConfig.getFACTORY().openSession()) {
            String statement = "SELECT COUNT(ctsp.id) FROM ChiTietSanPham ctsp";
            TypedQuery<Long> query = session.createQuery(statement, Long.class);
            total = query.getSingleResult();
        }
        return total;
    }

    public List<ChiTietSanPham> pageList(int position, int pageSize, String tenSP, String tenDM, String tenCL, String tenMau, String tenNSX) {
        List<ChiTietSanPham> ctsp;
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = em.createQuery("SELECT ctsp FROM ChiTietSanPham ctsp"
                + " WHERE (ctsp.sanPham.tenSP LIKE :sp or :sp is null or :sp = '')"
                + "AND (ctsp.danhMuc.tenDM=:dm or :dm is null or :dm = 'All')"
                + "AND (ctsp.chatLieu.tenCL=:cl or :cl is null or :cl = 'All')"
                + "AND (ctsp.mauSac.tenMau=:mau or :mau is null or :mau = 'All')"
                + "AND (ctsp.nhaSanXuat.tenNSX=:nsx or :nsx is null or :nsx = 'All')"
                + "ORDER BY ctsp.id DESC");
        query.setParameter("sp", "%" + tenSP + "%");
        query.setParameter("dm", tenDM);
        query.setParameter("cl", tenCL);
        query.setParameter("mau", tenMau);
        query.setParameter("nsx", tenNSX);
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        int pageIndex = position - 1 < 0 ? 0 : position - 1;
        int fromRecordIndex = pageIndex * pageSize;
        query.setFirstResult(fromRecordIndex);
        query.setMaxResults(pageSize);
        ctsp = query.getResultList();

        return ctsp;
    }

    public List<ChiTietSanPham> filterProduct(String tenSP, String tenDM, String tenCL, String tenMau, String tenNSX) {
        List<ChiTietSanPham> ctsp;
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = em.createQuery("SELECT ctsp FROM ChiTietSanPham ctsp"
                + " WHERE (ctsp.sanPham.tenSP LIKE :sp or :sp is null or :sp = '')"
                + "AND (ctsp.danhMuc.tenDM=:dm or :dm is null or :dm = 'All')"
                + "AND (ctsp.chatLieu.tenCL=:cl or :cl is null or :cl = 'All')"
                + "AND (ctsp.mauSac.tenMau=:mau or :mau is null or :mau = 'All')"
                + "AND (ctsp.nhaSanXuat.tenNSX=:nsx or :nsx is null or :nsx = 'All')"
                + "ORDER BY ctsp.id DESC");
        query.setParameter("sp", "%" + tenSP + "%");
        query.setParameter("dm", tenDM);
        query.setParameter("cl", tenCL);
        query.setParameter("mau", tenMau);
        query.setParameter("nsx", tenNSX);
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        ctsp = query.getResultList();

        return ctsp;
    }

    public List<ChiTietSanPham> searchByName(String name) {
        Query q = ses.createQuery("SELECT e From ChiTietSanPham e join SanPham a on a.id = e.sanPham.id \n"
                + "where a.tenSP like  :ten and e.soLuongTon > 0 and e.trangThai =1 and a.trangThai =1  ");
        q.setParameter("ten", "%" + name + "%");
        q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        List<ChiTietSanPham> list = q.getResultList();
        return list;
    }

    public List<ChiTietSanPham> pageListBanHang(int position, int pageSize, String tenSP) {
        List<ChiTietSanPham> ctsp;
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = em.createQuery("SELECT ctsp FROM ChiTietSanPham ctsp "
                + "WHERE ctsp.sanPham.tenSP LIKE :sp or :sp is null or :sp = '' "
                + "ORDER BY ctsp.id DESC");
        query.setParameter("sp", "%" + tenSP + "%");
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        int pageIndex = position - 1 < 0 ? 0 : position - 1;
        int fromRecordIndex = pageIndex * pageSize;
        query.setFirstResult(fromRecordIndex);
        query.setMaxResults(pageSize);
        ctsp = query.getResultList();
        return ctsp;
    }

    public static void main(String[] args) {
        List<ChiTietSanPham> list = new CTSPRepository().pageListBanHang(2, 5, null);
        for (ChiTietSanPham chiTietSanPham : list) {
            System.out.println(chiTietSanPham.getMa());
        }
    }

    public List<ChiTietSanPham> filterProductBanHang(String tenSP) {
        List<ChiTietSanPham> ctsp;
        EntityManager em = ses.getEntityManagerFactory().createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        EntityTransaction entityTransaction = em.getTransaction();

        Query query = em.createQuery("SELECT ctsp FROM ChiTietSanPham ctsp "
                + " WHERE (ctsp.sanPham.tenSP LIKE :sp or :sp is null or :sp = '')"
                + "ORDER BY ctsp.id DESC");
        query.setParameter("sp", "%" + tenSP + "%");
        query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");

        ctsp = query.getResultList();

        return ctsp;
    }

}
