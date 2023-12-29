package lk.ijse.shadowStudio.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentDto {
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
