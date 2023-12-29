package lk.ijse.shadowStudio.dto;

import lombok.*;

import java.lang.invoke.StringConcatException;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private String cust_id;
    private String cust_Name;
    private String cust_address;
    private String cust_nic;
    private String cust_tp;
    private String cust_email;
}
