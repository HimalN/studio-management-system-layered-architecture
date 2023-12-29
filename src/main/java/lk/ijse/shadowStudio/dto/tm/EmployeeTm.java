package lk.ijse.shadowStudio.dto.tm;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTm {
    private String employeeId;
    private String employeeName;
    private String employeeAddress;
    private String employeeNic;
    private String employeeTp;
}
