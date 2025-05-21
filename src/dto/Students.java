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

    private String courseTitle;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;

    //생성자
    public Students(int studentPk, String accessLevel, String studentId, String studentName, LocalDate studentBirth, String studentPhone, String studentEmail) {
        this.studentPk = studentPk;
        this.accessLevel = accessLevel;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentBirth = studentBirth;
        this.studentPhone = studentPhone;
        this.studentEmail = studentEmail;
    }//
    public Students(String courseTitle, LocalDate courseStartDate, LocalDate courseEndDate) {
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
    }//



}//Students

