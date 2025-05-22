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
    private Students studentInfo = new Students();
    private Teacher teacherInfo = new Teacher();
    private Admin adminInfo = new Admin();


    private final AdminService adminService = new AdminService();
    private final CourseService courseService = new CourseService();
    private final StudentsService studentsService = new StudentsService();
    private final TeacherService teacherService = new TeacherService();

    private final Scanner scanner = new Scanner(System.in);

    private Integer currentStudentId = null;
    private String currentStudentName = null;

    public void start() {

        while (true) {
            System.out.println("===학원 관리 시스템===");
            if (currentStudentId == null) {
                System.out.println("로그인이 필요한 상태입니다.");
                try {
                    login();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.print("안녕하세요 : " + currentStudentName + "님");
            }

            System.out.println("1. 도서추가");
            System.out.println("2. 도서목록");
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

        if (loginType.isEmpty()) {
            System.out.println("신분을 정확히 선택 해주세요.");
            return;
        }

        System.out.println("아이디를 입력 해주세요.");
        System.out.print("입력 : ");
        String loginId = scanner.nextLine().trim();

        if (loginId.isEmpty()) {
            System.out.println("아이디를 입력해주세요.");
            return;
        }

        if (loginType.equals("1")) {
            studentInfo = studentsService.authenticateStudent(loginId);
            System.out.println("결과 : " + studentInfo);
            
        } else if (loginType.equals("2")) {
            try {
                teacherService.authenticateTeacher(loginId);
                System.out.println("결과 : " + teacherLoginInfo);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (loginType.equals("3")) {
            try {
                adminService.authenticateAdmin(loginId);
                System.out.println("결과 : " + adminLoginInfo);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            System.out.println("올바른 신분 값을 선택 및 입력 해주세요.");
        }


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
