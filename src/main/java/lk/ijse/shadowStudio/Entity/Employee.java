package lk.ijse.shadowStudio.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    private String emp_id;
    private String emp_name;
    private String emp_address;
    private String emp_nic;
    private String emp_tp;
}
