package lk.ijse.shadowStudio.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complain {
    private String complain_id;
    private String cust_id;
    private String cust_name;
    private String about_complain;
}
