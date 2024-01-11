package lk.ijse.shadowStudio.BO.custom.Impl;

import lk.ijse.shadowStudio.BO.custom.ComplainBO;
import lk.ijse.shadowStudio.Entity.Complain;
import lk.ijse.shadowStudio.dao.DAOFactory;
import lk.ijse.shadowStudio.dao.custom.ComplainsDAO;
import lk.ijse.shadowStudio.dto.ComplainDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplainBOImpl implements ComplainBO {
    ComplainsDAO complainDAO = (ComplainsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COMPLAIN);

    @Override

    public String generateNextComplainID() throws SQLException, ClassNotFoundException {
        return complainDAO.generateNextId();
    }

    @Override
    public boolean saveComplain(ComplainDto dto) throws SQLException, ClassNotFoundException {
        return complainDAO.save(new Complain(dto.getComplain_id(),dto.getCust_id(),dto.getCust_name(), dto.getAbout()));
    }

    @Override
    public boolean updateComplain(ComplainDto dto) throws SQLException, ClassNotFoundException {
        return complainDAO.update(new Complain(dto.getComplain_id(),dto.getCust_id(),dto.getCust_name(), dto.getAbout()));
    }

    @Override
    public boolean deleteComplain(String id) throws SQLException, ClassNotFoundException {
        return complainDAO.delete(id);
    }
    @Override
    public ComplainDto searchComplain(String searchId) throws SQLException, ClassNotFoundException {
        Complain complain = complainDAO.search(searchId);
        if (complain != null) {
            return new ComplainDto(complain.getComplain_id(),complain.getCust_id(),complain.getCust_name(), complain.getAbout_complain());
        } else {
            return null;
        }
    }

    @Override
    public List<ComplainDto> getAllComplains() throws SQLException, ClassNotFoundException {
        ArrayList<ComplainDto> complainDto = new ArrayList<>();
        List<Complain> complains = complainDAO.getAll();

        for (Complain complain : complains) {
            complainDto.add(new ComplainDto(complain.getComplain_id(),complain.getCust_id(),complain.getCust_name(), complain.getAbout_complain()));
        }
        return complainDto;
    }
}
