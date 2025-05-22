package service;

import dao.StudentsDAO;
import dto.Students;

import java.sql.SQLException;
import java.util.List;

public class StudentsService {

    private final StudentsDAO studentsDAO = new StudentsDAO();

    //1.학생 전체 조회
    public List<Students> getAllStudent() {
        try {
            return studentsDAO.getAllStudents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//

    //2.학생 이름 조회
    public List<Students> searchStudentByName(String studentName) {

        try {

            //입력값 유효성 검사
            if (studentName == null || studentName.trim().isEmpty()) {
                throw new SQLException("성함을 다시 입력해주세요");
            }

            //유효성 검사 완료, studentDAO에 협업 요청
            return studentsDAO.searchStudentByName(studentName);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//

    //3.학생 로그인 조회
    public Students authenticateStudent(String studentId) {

        try {

            //입력값 유효성 검사
            if (studentId == null || studentId.trim().isEmpty()) {
                throw new SQLException("유효한 id 값을 다시 입력해주세요");
            }

            //유효성 검사 완료, studentDAO에 협업 요청
            return studentsDAO.authenticateStudent(studentId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//

    //4.학생 수강 조회
    public Students studentCourseInfo(String studentId) {

        try {

            //입력값 유효성 검사
            if (studentId == null || studentId.trim().isEmpty()) {
                throw new SQLException("유효한 id 값을 다시 입력해주세요");
            }
            //유효성 검사 완료, studentDAO에 협업 요청
            return studentsDAO.studentCourseInfo(studentId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//

    //테스트코드
    public static void main(String[] args) {

        StudentsService service = new StudentsService();

        //전체조회
//        try {
//            service.getAllStudent();
//            for (int i = 0; i < service.getAllStudent().size(); i++) {
//                System.out.println(service.getAllStudent().get(i));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        //이름조회

//        System.out.println(service.searchStudentByName("1"));


    }//main
}//StudentService
