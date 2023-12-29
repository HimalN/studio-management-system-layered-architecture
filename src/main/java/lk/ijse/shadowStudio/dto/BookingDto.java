package lk.ijse.shadowStudio.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDto {
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
