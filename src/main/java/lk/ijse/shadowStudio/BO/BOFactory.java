package lk.ijse.shadowStudio.BO;

import lk.ijse.shadowStudio.BO.custom.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getBoFactory () {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        LOGIN, COMPLAIN,CUSTOMER,EMPLOYEE,RENTITEMS,RENT,PACKAGES,BOOKINGS;
    }
    public SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {
            case LOGIN:
                return new LoginBOImpl();
            case COMPLAIN:
                return new ComplainBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case RENTITEMS:
                return new RentItemBOImpl();
            case RENT:
                return new RentBOImpl();
            case PACKAGES:
                return new PackageBOImpl();
            case BOOKINGS:
                return new BookingsBOImpl();
            default:
                return null;
        }
    }
}
