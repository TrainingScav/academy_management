package view;

import dao.AdminDAO;
import dao.StudentsDAO;
import dao.TeacherDAO;
import dto.Admin;
import dto.Students;
import dto.Teacher;
import service.AdminService;
import service.CourseService;
import service.StudentsService;
import service.TeacherService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginView {

    private final AdminService adminService = new AdminService();
    private final CourseService courseService = new CourseService();
    private final StudentsService studentsService = new StudentsService();
    private final TeacherService teacherService = new TeacherService();

    private final Scanner scanner = new Scanner(System.in);

    private Integer currentStudentId = null;
    private String currentStudentName = null;

    public void start() {

        while (true) {
            System.out.println("===도서관리 시스템===");
            if (currentStudentId == null) {
                System.out.println("로그인이 필요한 상태입니다.");
                try {
                    login();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("현재 로그인 유저 : " + currentStudentName);
            }

            System.out.println("1. 도서추가");
            System.out.println("2. 도서목록");
            System.out.println("3. 도서검색");
            System.out.println("4. 학생 등록");
            System.out.println("5. 학생 목록");
            System.out.println("6. 도서 대출");
            System.out.println("7. 대출중인 도서 목록");
            System.out.println("8. 도서반납");
            System.out.println("9. 로그인");
            System.out.println("10. 로그아웃");
            System.out.println("11. 종료");

            System.out.print("선택 : ");
            int choice;

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (Exception e) {
                System.out.println("정수값을 입력해 주세요.");
                scanner.nextLine();
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.println("도서 추가");
                        break;
                    case 2:
                        System.out.println("2");
                        break;
                    case 3:
                        System.out.println("3");
                        break;
                    case 4:
                        System.out.println("4");
                        break;
                    case 5:
                        System.out.println("5");
                        break;
                    case 6:
                        borrowBook();
                        break;
                    case 7:
                        System.out.println("7");
                        break;
                    case 8:
                        System.out.println("8");
                        break;
                    case 9:
                        login();
                        break;
                    case 10:
                        logout();
                        break;
                    case 11:
                        System.out.println("시스템 종료");
                        scanner.close(); //자원 해제
                        return;
                    default:
                        System.out.println("잘못된 입력 입니다.");
                }
            } catch (SQLException e) {
                System.out.println("오류 발생 " + e.getMessage());
            } catch (Exception e) {
                System.out.println("전체 오류 : " + e.getMessage());
            }
        } // end of while
    }// end of start

    private void login() throws SQLException {
        if (currentStudentId != null) {
            System.out.println("이미 로그인된 상태입니다.");
            return;
        }
        System.out.println("자신의 신분을 선택 해주세요.");
        System.out.println("1.학생, 2.강사, 3.관리자");
        System.out.print("입력 : ");
        String loginType = scanner.nextLine().trim();

        System.out.println("아이디를 입력 해주세요.");
        System.out.print("입력 : ");
        String loginId = scanner.nextLine().trim();

        if (loginId.trim().isEmpty()) {
            System.out.println("아이디를 입력해주세요.");
            return;
        }

        // 1. 학번을 입력 받았다면 -> 실제 학번이 맞는지 확인
        // 1.1 (데이터베이스에 접근해서 해당하는 학번(비밀번호) 맞는지 조회
        //Student studentDTO = service.authenticateStudent(studentId);

        //if (studentDTO == null) {
        //    System.out.println("존재하지 않는 학번입니다.");
        //} else {
        //    currentStudentId = studentDTO.getId();
        //    currentStudentName = studentDTO.getName();
        //    System.out.println("로그인 성공 : " + currentStudentName);
        //}
    }

    // 로그아웃 기능 만들어 보기
    private void logout() {
        if (currentStudentId == null) {
            System.out.println("이미 로그인 상태가 아닙니다.");
        } else {
            currentStudentId = null;
            currentStudentName = null;
            System.out.println("로그아웃 완료");
        }
    }

    private void borrowBook() throws SQLException {

    }

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.start();
    }
}
