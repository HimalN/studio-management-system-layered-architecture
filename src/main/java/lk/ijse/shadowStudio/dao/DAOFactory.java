package lk.ijse.shadowStudio.dao;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory == null)?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes {
        COMPLAIN,CUSTOMER,EMPLOYEE,ITEMS,QUERY,ITEM,ITEM_DETAILS,RENT
    }
}
