package service;
import dao.StudentsDAO;
import dto.Students;
import java.sql.SQLException;
import java.util.List;

public class StudentsService {

    private final StudentsDAO studentsDAO = new StudentsDAO();

    //1.학생 전체 조회
    public List<Students> getAllStudent() throws SQLException {
        return studentsDAO.getAllStudents();
    }//

    //2.학생 이름 조회
    public List<Students> searchByName(String studentName) throws SQLException {

        //입력값 유효성 검사
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new SQLException("학생 이름을 입력해주세요");
        }
        //유효성 검사 완료, studentDAO에 협업 요청
        return studentsDAO.searchByName(studentName);
    }//

    //3.학생 로그인 조회
    public Students authenticateStudent(String studentId) throws SQLException {
        //입력값 유효성 검사
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new SQLException("유효한 학번을 입력해주세요");
        }
        //유효성 검사 완료, studentDAO에 협업 요청
        return studentsDAO.authenticateStudent(studentId);
    }//

    //4.학생 수강 조회
    public Students studentCourse(String studentId) throws SQLException {
        //입력값 유효성 검사
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new SQLException("유효한 학번을 입력해주세요");
        }
        //유효성 검사 완료, studentDAO에 협업 요청
        return studentsDAO.studentCourse(studentId);
    }//

    //테스트코드
    public static void main(String[] args) {

        StudentsService service = new StudentsService();


        try {
            service.getAllStudent();
            for (int i = 0; i < service.getAllStudent().size(); i++) {
                System.out.println(service.getAllStudent().get(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        }//main
}//StudentService
