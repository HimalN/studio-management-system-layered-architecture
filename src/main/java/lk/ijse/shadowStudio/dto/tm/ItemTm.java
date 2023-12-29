package lk.ijse.shadowStudio.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemTm {
    private String itemId;
    private String itemName;
    private String itemType;
    private String rentalPrice;
    private String qty;
}