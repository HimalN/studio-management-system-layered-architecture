package lk.ijse.shadowStudio.Entity;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String cust_id;
    private String cust_Name;
    private String cust_address;
    private String cust_nic;
    private String cust_tp;
    private String cust_email;
}
