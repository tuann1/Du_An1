package service.impl;

import java.util.List;
import model.ChucVu;
import repository.ChucVuRepository;
import service.IChucVuService;

/**
 *
 * @author fallinluv2003
 */
public class ChucVuImpl implements IChucVuService{
    
    private ChucVuRepository cvRepo = new ChucVuRepository();

    @Override
    public List<ChucVu> getAll() {
        return cvRepo.getAll();
    }
    
}
