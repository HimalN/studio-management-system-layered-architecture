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
        LOGIN, DASHBOARD,COMPLAIN,CUSTOMER,EMPLOYEE, RENTITEMS,RENT, RENTDETAILS,PACKAGES,BOOKINGS
    }

    public SuperDAO getDAO (DAOTypes daoTypes) {
        switch (daoTypes) {
            case LOGIN:
                return new LoginDAOImpl();
            case DASHBOARD:
                return new DashboardDAOImpl();
            case COMPLAIN:
                return new ComplainsDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case RENTITEMS:
                return new RentItemDAOImpl();
            case PACKAGES:
                return new PackagesDAOImpl();
            case RENT:
                return new RentDAOImpl();
            case RENTDETAILS:
                return new RentItemDetailDAOImpl();
            case BOOKINGS:
                return new BookingsDAOImpl();
            default:
                return null;
        }
    }
}
