package lk.ijse.shadowStudio.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Package {
    private String package_id;
    private String package_name;
    private String package_type;
    private String package_description;
    private String package_price;
}
