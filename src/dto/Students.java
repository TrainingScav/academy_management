package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Students {

    private int studentsPk;
    private String accessLevel;
    private String studentsId;
    private String studentsName;
    private LocalDate studentsBirth;
    private String studentsPhone;
    private String studentsEmail;
}