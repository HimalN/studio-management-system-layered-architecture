package lk.ijse.shadowStudio.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {
    private String booking_id;
    private String cust_id;
    private String cust_name;
    private String package_id;
    private String package_name;
    private String date;
    private String time;
    private String location;
    private String description;
    private String paymemt;
}
