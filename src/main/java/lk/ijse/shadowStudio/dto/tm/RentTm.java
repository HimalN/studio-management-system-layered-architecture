package lk.ijse.shadowStudio.dto.tm;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RentTm {
    private String rent_id;
    private String cust_id;
    private String cust_name;
    private String item_id;
    private String item_name;
    private String day_count;
    private String date;
    private int qty;
    private String price;
}