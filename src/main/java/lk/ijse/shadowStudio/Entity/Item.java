package lk.ijse.shadowStudio.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    private String itemId;
    private String itemName;
    private String itemType;
    private String rentalPrice;
    private String qty;
}
