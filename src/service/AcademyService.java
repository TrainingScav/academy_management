package service;

import dao.AdminDAO;
import dao.CourseDAO;
import dao.StudentsDAO;
import dao.TeacherDAO;
import dto.Teacher;

public class AcademyService {

    private final AdminDAO adminDAO = new AdminDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentsDAO studentsDAO = new StudentsDAO();
    private final TeacherDAO teacherDAO =new TeacherDAO();


//    학생

// 1.학생 전체 조회
//2. 학생 이름 조회
//3.학생 로그인 조회
//4.학생 수강 조회



//강사

//1. 강사 전체 조회
//2.강사 이름으로 조회
//3.강사 로그인
//4.강사가 담당하고 있는 수강과목



//관리자

//1. 관리자 회원정보 등록(INSERT) 트랜잭션
//2.관리자 회원정보 조회(SELECT)
//3.관리자 회원정보 수정(UPDATE) 트랜잭션
//4.관리자 회원정보 삭제(DELETE) 트랜잭션
//5.관리자 admin_id 로 관리자 인증(로그인 용) 기능
//6.강의 개설(INSERT)
//7.강의 수정(UPDATE)
//8.강의 정보 삭제(DELETE)
//9.강사 회원정보 등록(INSERT)
//10.강사 회원정보 수정(UPDATE)
//11.강사 회원정보 삭제(DELETE)
//12.학생 회원정보 등록(INSERT)
//13.학생 회원정보 수정(UPDATE)
//14.학생 회원정보 삭제(DELETE)


    
// 수강

//1.수강 신청
//2.수강조회
//3.수강 전체조회
//4.강의명으로 수강 정보 조회

}
