package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private int coursePk;
    private String courseTitle;
    private int courseCapacity;
    private LocalDate startDate;
    private LocalDate endDate;
}
