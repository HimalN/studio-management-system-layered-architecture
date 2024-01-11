package lk.ijse.shadowStudio.dao;

import lk.ijse.shadowStudio.dao.custom.Impl.*;

import static com.lowagie.text.pdf.PdfName.ORDER;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory == null)?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes {
        COMPLAIN,CUSTOMER,EMPLOYEE, RENTITEMS,RENT
    }

    public SuperDAO getDAO (DAOTypes daoTypes) {
        switch (daoTypes) {
            case COMPLAIN:
                return new ComplainsDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            /*case RENTITEMS:
                return new RentItemDAOImpl();
*//*            case QUERY:
                return new JoinQueryDAOImpl();*//*
            case RENT:
                return new RentDAOImpl();*/
            default:
                return null;
        }
    }
}
