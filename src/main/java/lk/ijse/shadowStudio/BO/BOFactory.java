package lk.ijse.shadowStudio.BO;

import lk.ijse.shadowStudio.BO.custom.Impl.ComplainBOImpl;
import lk.ijse.shadowStudio.BO.custom.Impl.CustomerBOImpl;
import lk.ijse.shadowStudio.BO.custom.Impl.EmployeeBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getBoFactory () {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        COMPLAIN,CUSTOMER,EMPLOYEE,RENTITEMS,RENT,PACKAGES,BOOKINGS;
    }
    public SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {
            case COMPLAIN:
                return new ComplainBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            /*case ITEMS:
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case PRODUCTS:
                return new ProductsBOImpl();
            case SUPPLIERS:
                return new SuppliersBOImpl();*/
            default:
                return null;
        }
    }
}
