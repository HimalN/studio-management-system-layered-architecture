package lk.ijse.shadowStudio.dto.tm;

import lombok.*;

import java.util.PrimitiveIterator;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTm {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerNic;
    private String customerTp;
    private String customerEmail;

}
