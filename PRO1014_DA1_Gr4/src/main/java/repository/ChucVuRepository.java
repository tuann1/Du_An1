package repository;

import hibernateConfig.HibernateConfig;
import java.util.List;
import javax.persistence.Query;
import model.ChucVu;
import org.hibernate.Session;

/**
 *
 * @author fallinluv2003
 */
public class ChucVuRepository {
    Session ses = HibernateConfig.getFACTORY().openSession();

    public List<ChucVu> getAll() {
        Query q = ses.createQuery("FROM ChucVu");
        List<ChucVu> list = q.getResultList();
        return list;
    }
}
