package lk.ijse.shadowStudio.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rent {
    private String rentId;
    private String custId;
    private String custName;
    private String itemId;
    private String itemName;
    private String daycount;
    private String date;
    private int qty;
    private String price;
}
