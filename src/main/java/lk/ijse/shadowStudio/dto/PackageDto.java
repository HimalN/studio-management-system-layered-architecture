package lk.ijse.shadowStudio.dto;


import lombok.*;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackageDto {
    private String package_id;
    private String package_name;
    private String package_type;
    private String package_description;
    private String package_price;
}
