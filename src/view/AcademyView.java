package view;

import dto.Admin;
import dto.Course;
import dto.Students;
import dto.Teacher;
import service.AdminService;
import service.CourseService;
import service.StudentsService;
import service.TeacherService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AcademyView {
    private Students studentInfo = new Students();
    private Teacher teacherInfo = new Teacher();
    private Admin adminInfo = new Admin();


    private final AdminService adminService = new AdminService();
    private final CourseService courseService = new CourseService();
    private final StudentsService studentsService = new StudentsService();
    private final TeacherService teacherService = new TeacherService();

    private final Scanner scanner = new Scanner(System.in);

    private String currentUserId = null;
    private String currentUserName = null;
    private String currentUserType = null;

    public void start() {

        while (true) {
            System.out.println("====학원 관리 시스템====");
            if (currentUserId == null) {
                System.out.println("로그인이 필요한 상태입니다.");
                try {
                    login();
                    continue;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("안녕하세요 : " + currentUserName + " " + currentUserType + "님");
            }

            System.out.println("이용하고 싶은 메뉴를 선택 해주세요.");

            if (currentUserId.contains("S")) {
                System.out.println("1. 강사조회");
                System.out.println("2. 강의목록 조회");
                System.out.println("3. 수강신청한 강의 정보");
                System.out.println("4. 로그아웃");
                System.out.println("5. 프로그램 종료");
            } else if (currentUserId.contains("T")) {
                System.out.println("1. 학생조회");
                System.out.println("2. 강의목록 조회");
                System.out.println("3. 담당중인 강의 정보");
                System.out.println("4. 로그아웃");
                System.out.println("5. 프로그램 종료");
            } else if (currentUserId.contains("A")) {
                System.out.println("1. 학생조회");
                System.out.println("2. 강사조회");
                System.out.println("3. 관리자조회");
                System.out.println("4. 강의목록 조회");
                System.out.println("5. 학생정보 등록/수정/삭제");
                System.out.println("6. 강사정보 등록/수정/삭제");
                System.out.println("7. 관리자정보 등록/수정/삭제");
                System.out.println("8. 강의정보 등록/수정/삭제");
                System.out.println("9. 로그아웃");
                System.out.println("10. 프로그램 종료");
            }

            int choice;
            System.out.print("선택 : ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (Exception e) {
                System.out.println("정수값을 입력해 주세요.");
                scanner.nextLine();
                continue;
            }

            if (currentUserId.contains("S") || currentUserId.contains("T")) {
                try {
                    switch (choice) {
                        case 1: // 학생/강사 조회
                            System.out.println("원하시는 조회 조건을 입력 해 주세요.");
                            System.out.println("1.전체 조회, 2.이름 검색");
                            System.out.print("입력 : ");
                            int selectTarget = scanner.nextInt();

                            scanner.nextLine();

                            if(selectTarget == 1) {
                                searchAllByType();
                            } else if (selectTarget == 2) {
                                System.out.println("검색하실 이름을 입력 해주세요.");
                                System.out.print("입력 : ");
                                String targetName = scanner.nextLine().trim();

                                if (targetName.isEmpty()) {
                                    System.out.println("빈값을 입력하였습니다.");
                                    return;
                                }

                                searchAllByTypeAndName(targetName);
                            }
                            break;
                        case 2: // 강의 목록 조회
                            System.out.println("원하시는 조회 조건을 입력 해 주세요.");
                            System.out.println("1.전체 조회, 2.이름 검색");
                            System.out.print("입력 : ");
                            int selectCourseType = scanner.nextInt();

                            scanner.nextLine();

                            if(selectCourseType == 1) {
                                searchAllCourse();
                            } else if (selectCourseType == 2) {
                                System.out.println("검색하실 이름을 입력 해주세요.");
                                System.out.print("입력 : ");
                                String targetCourseName = scanner.nextLine().trim();

                                if (targetCourseName.isEmpty()) {
                                    System.out.println("빈값을 입력하였습니다.");
                                    return;
                                }

                                searchAllByTypeAndName(targetCourseName);
                            }
                            break;
                        case 3: // 사용자가 수강 신청/담당한 강의 정보
                            searchMyCourseInfo();
                            break;
                        case 4: // 로그아웃
                            logout();
                            break;
                        case 5: // 프로그램 종료
                            System.out.println("프로그램을 종료 합니다.");
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
            } else if (currentUserId.contains("A")) {
                try {
                    switch (choice) {
                        case 1: // 학생 조회
                            searchAllByType("S");
                            break;
                        case 2: // 강사 조회

                            break;
                        case 3: // 관리자 조회

                            break;
                        case 4: // 강의 목록 조회
                            searchAllCourse();
                            break;
                        case 5: // 학생정보 등록/수정/삭제

                            break;
                        case 6: // 강사정보 등록/수정/삭제

                            break;
                        case 7: // 관리자 정보 등록/수정/삭제

                            break;
                        case 8: // 강의정보 등록/수정/삭제

                            break;
                        case 9: // 로그아웃
                            logout();
                            break;
                        case 10: // 프로그램 종료
                            System.out.println("프로그램을 종료 합니다.");
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
            }
        } // end of while
    }// end of start

    // 로그인 기능
    private void login() throws SQLException {
        if (currentUserId != null) {
            System.out.println("이미 로그인된 상태입니다.");
            return;
        }

        System.out.println("자신의 신분을 선택 해주세요.");
        System.out.println("1.학생, 2.강사, 3.관리자");
        System.out.print("입력 : ");
        String loginType = scanner.nextLine().trim();

        if (!loginType.equals("1") && !loginType.equals("2") && !loginType.equals("3")) {
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
            if (studentInfo == null) {
                System.out.println("존재하는 아이디가 없습니다.");
                return;
            }
            currentUserId = studentInfo.getStudentId();
            currentUserName = studentInfo.getStudentName();
            currentUserType = "학생";
        } else if (loginType.equals("2")) {
            teacherInfo = teacherService.authenticateTeacher(loginId);

            if (teacherInfo == null) {
                System.out.println("존재하는 아이디가 없습니다.");
                return;
            }

            currentUserId = teacherInfo.getTeacherId();
            currentUserName = teacherInfo.getTeacherName();
            currentUserType = "강사";
        } else if (loginType.equals("3")) {
            adminInfo = adminService.authenticateAdmin(loginId);
            if (adminInfo == null) {
                System.out.println("존재하는 아이디가 없습니다.");
                return;
            }

            currentUserId = adminInfo.getAdminId();
            currentUserName = adminInfo.getAdminName();
            currentUserType = "관리자";
        }
    }

    // 로그아웃 기능
    private void logout() {
        if (currentUserId == null) {
            System.out.println("이미 로그인 상태가 아닙니다.");
        } else {
            currentUserId = null;
            currentUserName = null;
            currentUserType = null;
            System.out.println("로그아웃 되었습니다.");
        }
    }

    // 학생,강사,관리자 조회 기능 (전체)
    public void searchAllByType() throws SQLException {
        // ID값(학생, 강사, 관리자)에 따라 분기 처리
        if (currentUserId.contains("S")) {
            List<Teacher> teachersList = teacherService.searchAllTeacher();
            for (int i = 0; i < teachersList.size(); i++) {
                System.out.print("강사명 : " + teachersList.get(i).getTeacherName() + "\t");
                System.out.print("강사 휴대폰 번호 : " + teachersList.get(i).getTeacherPhone() + "\t");
                System.out.println("강사 이메일 : " + teachersList.get(i).getTeacherEmail() + "\t");
            }
        } else if (currentUserId.contains("T")) {
            List<Students> studentsList = studentsService.getAllStudent();
            for (int i = 0; i < studentsList.size(); i++) {
                System.out.print("학생명 : " + studentsList.get(i).getStudentName() + "\t");
                System.out.print("학생 생년월일 :" + studentsList.get(i).getStudentBirth() + "\t");
                System.out.print("학생 휴대폰 번호" + studentsList.get(i).getStudentPhone() + "\t");
                System.out.println("학생 이메일" + studentsList.get(i).getStudentEmail() + "\t");
            }
        }
    }

    // 학생,강사,관리자 조회 기능 (관리자 전용)
    public void searchAllByType(String type) throws SQLException {
        // ID값(학생, 강사, 관리자)에 따라 분기 처리
        if (type.equals("T")) {
            List<Teacher> teachersList = teacherService.searchAllTeacher();
            for (int i = 0; i < teachersList.size(); i++) {
                System.out.print("강사명 : " + teachersList.get(i).getTeacherName() + "\t");
                System.out.print("강사 휴대폰 번호 : " + teachersList.get(i).getTeacherPhone() + "\t");
                System.out.println("강사 이메일 : " + teachersList.get(i).getTeacherEmail() + "\t");
            }
        } else if (type.equals("S")) {
            List<Students> studentsList = studentsService.getAllStudent();
            for (int i = 0; i < studentsList.size(); i++) {
                System.out.print("학생명 : " + studentsList.get(i).getStudentName() + "\t");
                System.out.print("학생 생년월일 :" + studentsList.get(i).getStudentBirth() + "\t");
                System.out.print("학생 휴대폰 번호" + studentsList.get(i).getStudentPhone() + "\t");
                System.out.println("학생 이메일" + studentsList.get(i).getStudentEmail() + "\t");
            }
        } else if (type.equals("A")) {
            List<Admin> adminsList = adminService.getAllAdmin();
            for (int i = 0; i < adminsList.size(); i++) {
                System.out.println("관리자명 : " + adminsList.get(i).getAdminName() + "\t");
            }
        }
    }

    // 조회 기능 (이름)
    public void searchAllByTypeAndName(String name) throws SQLException {
        // ID값(학생, 강사, 관리자)에 따라 분기 처리

        if (currentUserId.contains("S")) {
            List<Teacher> teachersList = teacherService.searchTeacherByTitle(name);
            for (int i = 0; i < teachersList.size(); i++) {
                System.out.print("강사명 : " + teachersList.get(i).getTeacherName() + "\t");
                System.out.print("강사 휴대폰 번호 : " + teachersList.get(i).getTeacherPhone() + "\t");
                System.out.println("강사 이메일 : " + teachersList.get(i).getTeacherEmail() + "\t");
            }
        } else if (currentUserId.contains("T")) {
            List<Students> studentsList = studentsService.searchStudentByName(name);
            for (int i = 0; i < studentsList.size(); i++) {
                System.out.print("학생명 : " + studentsList.get(i).getStudentName() + "\t");
                System.out.print("학생 생년월일 :" + studentsList.get(i).getStudentBirth() + "\t");
                System.out.print("학생 휴대폰 번호" + studentsList.get(i).getStudentPhone() + "\t");
                System.out.println("학생 이메일" + studentsList.get(i).getStudentEmail() + "\t");
            }
        }
    }

    // 강의 목록 조회 (전체)
    public void searchAllCourse() throws SQLException {
        List<Course> coursesList = courseService.getAllCourse();
        for (int i = 0; i < coursesList.size(); i++) {
            System.out.print("강의명 : " + coursesList.get(i).getCourseTitle() + "\t");
            System.out.print("강의 최대정원수 : " + coursesList.get(i).getCourseCapacity() + "\t");
            System.out.print("강의 시작일 : " + coursesList.get(i).getStartDate() + "\t");
            System.out.println("강의 종료일 : " + coursesList.get(i).getEndDate() + "\t");
        }
    }

    // 강의 목록 조회 (이름 like)
    public void searchCourseByTitle(String name) throws SQLException {
        List<Course> coursesList = courseService.searchCourseTitle(name);
        for (int i = 0; i < coursesList.size(); i++) {
            System.out.print("강의명 : " + coursesList.get(i).getCourseTitle() + "\t");
            System.out.print("강의 최대정원수 : " + coursesList.get(i).getCourseCapacity() + "\t");
            System.out.print("강의 시작일 : " + coursesList.get(i).getStartDate() + "\t");
            System.out.println("강의 종료일 : " + coursesList.get(i).getEndDate() + "\t");
        }
    }

    // 내가 신청/담당한 강의 정보
    public void searchMyCourseInfo() throws SQLException {
        // ID값(학생, 강사)에 따라 분기 처리
        if (currentUserId.contains("S")) {
            Students studentCourseInfo = studentsService.studentCourseInfo(currentUserId);

            System.out.print("강의 명 : " + studentCourseInfo.getCourseTitle() + "\t");
            System.out.print("강의 시작 날짜 : " + studentCourseInfo.getCourseStartDate() + "\t");
            System.out.println("강의 종료 날짜 : " + studentCourseInfo.getCourseEndDate());
        } else if (currentUserId.contains("T")) {
            Teacher teacherCourseInfo = teacherService.teacherCourseInfo(currentUserName);

            System.out.print("강의 명 : " + teacherCourseInfo.getCourseTitle() + "\t");
            System.out.print("강의 시작 날짜 : " + teacherCourseInfo.getStartDate() + "\t");
            System.out.println("강의 종료 날짜 : " + teacherCourseInfo.getEndDate());
        }
    }

    // 학생정보 수정/삭제

    // 강사정보 수정/삭제

    // 강의정보 등록/수정/삭제

    // 관리자 등록/수정/삭제


    public static void main(String[] args) {
        AcademyView academyView = new AcademyView();
        academyView.start();
    }
}
