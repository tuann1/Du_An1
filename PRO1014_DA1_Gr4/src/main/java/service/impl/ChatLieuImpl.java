/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import model.ChatLieu;
import repository.ChatLieuRepository;
import java.util.List;
import service.IChatLieuService;


/**
 *
 * @author HP
 */
public class ChatLieuImpl implements IChatLieuService{
    
    private ChatLieuRepository clRepo = new ChatLieuRepository();

    @Override
    public List<ChatLieu> getAll() {
        return clRepo.getAll();
    }

    @Override
    public String add(ChatLieu cl) {
        if(clRepo.add(cl)) {
            return "Thêm thành công";
        } else {
            return "Thêm thất bại";
        }
    }

    @Override
    public String update(ChatLieu cl, Integer id) {
        if(clRepo.update(cl, id)) {
            return "Sửa thành công";
        } else {
            return "Sửa thất bại";
        }
    }

    @Override
    public String updateTrangThai(Integer id) {
        if(clRepo.updateTrangThai(id)) {
            return "Xóa thành công";
        } else {
            return "Xóa thất bại";
        }
    }

    @Override
    public List<ChatLieu> getAllSp() {
        return clRepo.getAllSp();
    }

    @Override
    public ChatLieu getByMa(String ma) {
        return clRepo.getByMa(ma);
    }
    
}
