package lk.ijse.shadowStudio.dao.custom.Impl;

import lk.ijse.shadowStudio.Entity.Complain;
import lk.ijse.shadowStudio.dao.SQLUtil;
import lk.ijse.shadowStudio.dao.custom.ComplainsDAO;
import lk.ijse.shadowStudio.dto.ComplainDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplainsDAOImpl implements ComplainsDAO {
    @Override
    public boolean save(Complain dto) throws SQLException {
        //        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "INSERT INTO complains VALUES(?,?,?,?)";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//
//        pstm.setString(1, dto.getComplain_id());
//        pstm.setString(2,dto.getCust_id());
//        pstm.setString(3,dto.getCust_name());
//        pstm.setString(4,dto.getAbout());
//
        return SQLUtil.execute("INSERT INTO complains VALUES(?,?,?,?)",dto.getComplain_id(),dto.getCust_id(),
                dto.getCust_name(),dto.getAbout_complain());
    }

    @Override
    public String generateNextId() throws SQLException {
        //        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "SELECT complain_id FROM complains ORDER BY complain_id DESC LIMIT 1";
//        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = SQLUtil.execute("SELECT complain_id FROM complains ORDER BY complain_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    @Override
    public String splitId(String currentId) throws SQLException {
        if(currentId != null) {
            String[] split = currentId.split("[COM]");

            int id = Integer.parseInt(split[3]); //01
            id++;
            return String.format("COM%03d",id);
        } else {
            return "COM001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        //        Connection connection = DbConnection.getInstance().getConnection();
//        String sql = "delete from complains where complain_id = ?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//
//        pstm.setString(1, id);

        return SQLUtil.execute("delete from complains where complain_id = ?",id);
    }

    @Override
    public boolean update(Complain dto) throws SQLException {
        //        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "UPDATE complains SET cust_id = ?, cust_name = ?, about_complain = ? WHERE complain_id = ?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setString(1, dto.getCust_id());
//        pstm.setString(2, dto.getCust_name());
//        pstm.setString(3, dto.getAbout());
//        pstm.setString(4, dto.getComplain_id());

        return SQLUtil.execute("UPDATE complains SET cust_id = ?, cust_name = ?, about_complain = ? WHERE complain_id = ?",
                dto.getCust_id(),dto.getCust_name(),dto.getAbout_complain(),dto.getComplain_id());
    }

    @Override
    public List<Complain> getAll() throws SQLException {
        //        Connection connection = DbConnection.getInstance().getConnection();
//        String sql = "select * from complains";
//        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet rs = SQLUtil.execute("SELECT * from complains");

        ArrayList<Complain> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new Complain(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)
                    )
            );
        }
        return dtoList;
    }

    @Override
    public Complain search(String id) throws SQLException {
        //        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "select * from complains where complain_id=?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//
//        pstm.setString(1, id);

        ResultSet resultSet = SQLUtil.execute("SELECT * from complains where complain_id=?",id);

        Complain dto = null;

        if(resultSet.next()) {
            String complain_id = resultSet.getString(1);
            String cust_id = resultSet.getString(2);
            String cust_name = resultSet.getString(3);
            String about_complain = resultSet.getString(4);

            dto = new Complain(complain_id,cust_id, cust_name, about_complain);
        }
        return dto;
    }
}
