package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    private int teacherPk;
    private String accessLevel;
    private String teacherId;
    private String teacherName;
    private String teacherPhone;
    private String teacherEmail;

}
