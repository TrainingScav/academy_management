package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Students {

    private int studentPk;
    private String accessLevel;
    private String studentId;
    private String studentName;
    private LocalDate studentBirth;
    private String studentPhone;
    private String studentEmail;
}