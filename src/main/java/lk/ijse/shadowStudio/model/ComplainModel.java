package lk.ijse.shadowStudio.model;

import lk.ijse.shadowStudio.db.DbConnection;
import lk.ijse.shadowStudio.dto.ComplainDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplainModel {
    public static String generateNextComplainId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT complain_id FROM complains ORDER BY complain_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitComplainId(resultSet.getString(1));
        }
        return splitComplainId(null);
    }
    private static String splitComplainId(String currentComplainId) {
        if(currentComplainId != null) {
            String[] split = currentComplainId.split("[COM]");

            int id = Integer.parseInt(split[3]); //01
            id++;
            return String.format("COM%03d",id);
        } else {
            return "COM001";
        }

    }

    public static boolean saveComplain(ComplainDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO complains VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getComplain_id());
        pstm.setString(2,dto.getCust_id());
        pstm.setString(3,dto.getCust_name());
        pstm.setString(4,dto.getAbout());
        ;

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static boolean updateCompalin(ComplainDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE complains SET cust_id = ?, cust_name = ?, about_complain = ? WHERE complain_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getCust_id());
        pstm.setString(2, dto.getCust_name());
        pstm.setString(3, dto.getAbout());
        pstm.setString(4, dto.getComplain_id());

        return pstm.executeUpdate() > 0;
    }
    public List<ComplainDto> getAllComplains() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from complains";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();



        ArrayList<ComplainDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new ComplainDto(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)
                    )
            );
        }
        return dtoList;

    }

    public boolean deleteComplain(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "delete from complains where complain_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;


    }

    public ComplainDto searchComplain(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select * from complains where complain_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        ComplainDto dto = null;

        if(resultSet.next()) {
            String complain_id = resultSet.getString(1);
            String cust_id = resultSet.getString(2);
            String cust_name = resultSet.getString(3);
            String about_complain = resultSet.getString(4);

            dto = new ComplainDto(complain_id,cust_id, cust_name, about_complain);
        }
        return dto;
    }
}
