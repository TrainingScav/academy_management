package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private int coursePk;
    private String courseTitle;
    private int courseCapacity;
    private int teacherId;
    private LocalDate startDate;
    private LocalDate endDate;
}
