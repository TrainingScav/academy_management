package dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Admin {

    private int admin_pk;
    private String access_level;
    private String admin_id;
    private String admin_name;

}
