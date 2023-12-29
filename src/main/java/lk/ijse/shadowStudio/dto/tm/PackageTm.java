package lk.ijse.shadowStudio.dto.tm;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PackageTm {
    private String package_id;
    private String package_name;
    private String package_type;
    private String package_description;
    private String package_price;
}
