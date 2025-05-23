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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AcademyView {

    // 로그인 메서드 호출 후 프로그램 종료 선택 시 while문 종료 제어 할 수 있도록 하는 변수
    private boolean isLoginEnd = false;
    // 생년월일, 시작날짜, 종료날짜 포맷팅을 맞추기 위한 변수
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

            // 로그인 메서드 호출 후 프로그램 종료 시 while문 제어처리
            if (isLoginEnd) {
                break;
            }
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
                System.out.println("3. 수강신청");
                System.out.println("4. 수강취소");
                System.out.println("5. 수강신청한 강의 정보");
                System.out.println("6. 로그아웃");
                System.out.println("7. 프로그램 종료");
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

            if (currentUserId.contains("S")) {
                try {
                    switch (choice) {
                        case 1: // 학생/강사 조회
                            System.out.println("원하시는 조회 조건을 입력 해 주세요.");
                            System.out.println("1.전체 조회, 2.이름 검색");
                            System.out.print("입력 : ");
                            int selectTarget = scanner.nextInt();

                            scanner.nextLine();

                            if (selectTarget == 1) {
                                searchAllByType();
                            } else if (selectTarget == 2) {
                                System.out.println("검색하실 이름을 입력 해주세요.");
                                System.out.print("입력 : ");
                                String targetName = scanner.nextLine().trim();

                                if (targetName.isEmpty()) {
                                    System.out.println("빈값을 입력하였습니다.");
                                    continue;
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

                            if (selectCourseType == 1) {
                                searchAllCourse();
                            } else if (selectCourseType == 2) {
                                System.out.println("검색하실 이름을 입력 해주세요.");
                                System.out.print("입력 : ");
                                String targetCourseName = scanner.nextLine().trim();

                                if (targetCourseName.isEmpty()) {
                                    System.out.println("빈값을 입력하였습니다.");
                                    continue;
                                }

                                searchCourseByTitle(targetCourseName);
                            }
                            break;
                        case 3: // 학생 수강 신청
                            System.out.println("수강 신청하실 강의 번호를 입력 해 주세요");
                            System.out.print("입력 : ");
                            int insertCourseId;
                            try {
                                insertCourseId = scanner.nextInt();
                                scanner.nextLine(); // 버퍼 비우기
                            } catch (Exception e) {
                                System.out.println("정수값을 입력해 주세요.");
                                scanner.nextLine();
                                continue;
                            }

                            if (insertCourseId <= 0) {
                                System.out.println("0보다 같거나 작은 수를 입력할 수 없습니다.");
                                continue;
                            }

                            insertStudentCourse(insertCourseId);
                            break;
                        case 4: // 학생 수강 취소
                            cancelStudentCourse();
                            break;
                        case 5: // 사용자가 수강 신청/담당한 강의 정보
                            searchMyCourseInfo();
                            break;
                        case 6: // 로그아웃
                            logout();
                            break;
                        case 7: // 프로그램 종료
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
            } else if (currentUserId.contains("T")) {
                try {
                    switch (choice) {
                        case 1: // 학생/강사 조회
                            System.out.println("원하시는 조회 조건을 입력 해 주세요.");
                            System.out.println("1.전체 조회, 2.이름 검색");
                            System.out.print("입력 : ");
                            int selectTarget = scanner.nextInt();

                            scanner.nextLine();

                            if (selectTarget == 1) {
                                searchAllByType();
                            } else if (selectTarget == 2) {
                                System.out.println("검색하실 이름을 입력 해주세요.");
                                System.out.print("입력 : ");
                                String targetName = scanner.nextLine().trim();

                                if (targetName.isEmpty()) {
                                    System.out.println("빈값을 입력하였습니다.");
                                    continue;
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

                            if (selectCourseType == 1) {
                                searchAllCourse();
                            } else if (selectCourseType == 2) {
                                System.out.println("검색하실 이름을 입력 해주세요.");
                                System.out.print("입력 : ");
                                String targetCourseName = scanner.nextLine().trim();

                                if (targetCourseName.isEmpty()) {
                                    System.out.println("빈값을 입력하였습니다.");
                                    continue;
                                }

                                searchCourseByTitle(targetCourseName);
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
            } else if (currentUserId.contains("A")) { // 관리자용 검색창
                try {
                    switch (choice) {
                        case 1: // 학생 조회
                            System.out.println("원하시는 조회 조건을 입력 해 주세요.");
                            System.out.println("1.전체 조회, 2.이름 검색");
                            System.out.print("입력 : ");
                            int selectTargetS = scanner.nextInt();

                            scanner.nextLine();

                            if (selectTargetS == 1) {
                                searchAllByType("S");
                            } else if (selectTargetS == 2) {
                                System.out.println("검색하실 이름을 입력 해주세요.");
                                System.out.print("입력 : ");
                                String targetName = scanner.nextLine().trim();

                                if (targetName.isEmpty()) {
                                    System.out.println("빈값을 입력하였습니다.");
                                    continue;
                                }

                                searchAllByTypeAndName(targetName, "S");
                            }
                            break;
                        case 2: // 강사 조회
                            System.out.println("원하시는 조회 조건을 입력 해 주세요.");
                            System.out.println("1.전체 조회, 2.이름 검색");
                            System.out.print("입력 : ");
                            int selectTargetT = scanner.nextInt();

                            scanner.nextLine();

                            if (selectTargetT == 1) {
                                searchAllByType("T");
                            } else if (selectTargetT == 2) {
                                System.out.println("검색하실 이름을 입력 해주세요.");
                                System.out.print("입력 : ");
                                String targetName = scanner.nextLine().trim();

                                if (targetName.isEmpty()) {
                                    System.out.println("빈값을 입력하였습니다.");
                                    continue;
                                }
                                searchAllByTypeAndName(targetName, "T");
                            }
                            break;
                        case 3: // 관리자 조회
                            searchAllByType("A");
                            break;
                        case 4: // 강의 목록 조회
                            System.out.println("원하시는 조회 조건을 입력 해 주세요.");
                            System.out.println("1.전체 조회, 2.이름 검색");
                            System.out.print("입력 : ");
                            int selectCourseType = scanner.nextInt();

                            scanner.nextLine();

                            if (selectCourseType == 1) {
                                searchAllCourse();
                            } else if (selectCourseType == 2) {
                                System.out.println("검색하실 이름을 입력 해주세요.");
                                System.out.print("입력 : ");
                                String targetCourseName = scanner.nextLine().trim();

                                if (targetCourseName.isEmpty()) {
                                    System.out.println("빈값을 입력하였습니다.");
                                    continue;
                                }

                                searchCourseByTitle(targetCourseName);
                            }
                            break;
                        case 5: // 학생정보 등록/수정/삭제
                            System.out.println("진행하실 내용을 입력 해주세요.");
                            System.out.println("1. 등록, 2.수정, 3.삭제");
                            String selManageTargetS = scanner.nextLine().trim();

                            if (selManageTargetS.isEmpty()) {
                                System.out.println("빈값을 입력하였습니다.");
                                continue;
                            }

                            if (selManageTargetS.equals("1")) {
                                registUserInfo("S");
                            } else if (selManageTargetS.equals("2")) {
                                updateUserInfo("S");
                            } else if (selManageTargetS.equals("3")) {
                                deleteUserInfo("S");
                            }

                            break;
                        case 6: // 강사정보 등록/수정/삭제
                            System.out.println("진행하실 내용을 입력 해주세요.");
                            System.out.println("1. 등록, 2.수정, 3.삭제");
                            String selManageTargetT = scanner.nextLine().trim();

                            if (selManageTargetT.isEmpty()) {
                                System.out.println("빈값을 입력하였습니다.");
                                continue;
                            }

                            if (selManageTargetT.equals("1")) {
                                registUserInfo("T");
                            } else if (selManageTargetT.equals("2")) {
                                updateUserInfo("T");
                            } else if (selManageTargetT.equals("3")) {
                                deleteUserInfo("T");
                            }

                            break;
                        case 7: // 관리자 정보 등록/수정/삭제
                            System.out.println("진행하실 내용을 입력 해주세요.");
                            System.out.println("1. 등록, 2.수정, 3.삭제");
                            String selManageTargetA = scanner.nextLine().trim();

                            if (selManageTargetA.isEmpty()) {
                                System.out.println("빈값을 입력하였습니다.");
                                continue;
                            }

                            if (selManageTargetA.equals("1")) {
                                registUserInfo("A");
                            } else if (selManageTargetA.equals("2")) {
                                updateUserInfo("A");
                            } else if (selManageTargetA.equals("3")) {
                                deleteUserInfo("A");
                            }

                            break;
                        case 8: // 강의정보 등록/수정/삭제
                            System.out.println("진행하고 싶은 번호를 입력 해주세요");
                            System.out.println("1.등록, 2.수정, 3.삭제");

                            int targetManageCourse;
                            System.out.print("입력 : ");
                            try {
                                targetManageCourse = scanner.nextInt();
                                scanner.nextLine(); // 버퍼 비우기
                            } catch (Exception e) {
                                System.out.println("정수값을 입력해 주세요.");
                                scanner.nextLine();
                                continue;
                            }

                            if (targetManageCourse == 1) {
                                addCourseInfo();
                            } else if (targetManageCourse == 2) {
                                updateCourseInfo();
                            } else if (targetManageCourse == 3) {
                                deleteCourseInfo();
                            } else {
                                System.out.println("정확한 값을 입력 해주세요.");
                                continue;
                            }
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
        System.out.println("1.학생, 2.강사, 3.관리자, 4.프로그램 종료");
        System.out.print("입력 : ");
        String loginType = scanner.nextLine().trim();

        if (!loginType.equals("1") && !loginType.equals("2") && !loginType.equals("3") && !loginType.equals("4")) {
            System.out.println("정확한 값을 입력 해주세요.");
            return;
        }

        if (loginType.equals("4")) {
            System.out.println("프로그램을 종료합니다.");
            isLoginEnd = true;
            scanner.close();
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
            if (teachersList.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
            for (int i = 0; i < teachersList.size(); i++) {
                System.out.print("강사명 : " + teachersList.get(i).getTeacherName() + "\t");
                System.out.print("강사 휴대폰 번호 : " + teachersList.get(i).getTeacherPhone() + "\t");
                System.out.println("강사 이메일 : " + teachersList.get(i).getTeacherEmail() + "\t");
            }
        } else if (currentUserId.contains("T")) {
            List<Students> studentsList = studentsService.getAllStudent();
            if (studentsList.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
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
            if (teachersList.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
            for (int i = 0; i < teachersList.size(); i++) {
                System.out.print("강사명 : " + teachersList.get(i).getTeacherName() + "\t");
                System.out.print("강사 휴대폰 번호 : " + teachersList.get(i).getTeacherPhone() + "\t");
                System.out.println("강사 이메일 : " + teachersList.get(i).getTeacherEmail() + "\t");
            }
        } else if (type.equals("S")) {
            List<Students> studentsList = studentsService.getAllStudent();
            if (studentsList.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
            for (int i = 0; i < studentsList.size(); i++) {
                System.out.print("학생명 : " + studentsList.get(i).getStudentName() + "\t");
                System.out.print("학생 생년월일 :" + studentsList.get(i).getStudentBirth() + "\t");
                System.out.print("학생 휴대폰 번호" + studentsList.get(i).getStudentPhone() + "\t");
                System.out.println("학생 이메일" + studentsList.get(i).getStudentEmail() + "\t");
            }
        } else if (type.equals("A")) {
            List<Admin> adminsList = adminService.getAllAdmin();
            if (adminsList.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
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
            if (teachersList.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
            for (int i = 0; i < teachersList.size(); i++) {
                System.out.print("강사명 : " + teachersList.get(i).getTeacherName() + "\t");
                System.out.print("강사 휴대폰 번호 : " + teachersList.get(i).getTeacherPhone() + "\t");
                System.out.println("강사 이메일 : " + teachersList.get(i).getTeacherEmail() + "\t");
            }
        } else if (currentUserId.contains("T")) {
            List<Students> studentsList = studentsService.searchStudentByName(name);
            if (studentsList.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
            for (int i = 0; i < studentsList.size(); i++) {
                System.out.print("학생명 : " + studentsList.get(i).getStudentName() + "\t");
                System.out.print("학생 생년월일 :" + studentsList.get(i).getStudentBirth() + "\t");
                System.out.print("학생 휴대폰 번호" + studentsList.get(i).getStudentPhone() + "\t");
                System.out.println("학생 이메일" + studentsList.get(i).getStudentEmail() + "\t");
            }
        }
    }

    // 조회 기능 (이름) (관리자전용)
    public void searchAllByTypeAndName(String name, String type) throws SQLException {
        // ID값(학생, 강사, 관리자)에 따라 분기 처리

        if (type.equals("T")) {
            List<Teacher> teachersList = teacherService.searchTeacherByTitle(name);
            if (teachersList.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
            for (int i = 0; i < teachersList.size(); i++) {
                System.out.print("강사명 : " + teachersList.get(i).getTeacherName() + "\t");
                System.out.print("강사 휴대폰 번호 : " + teachersList.get(i).getTeacherPhone() + "\t");
                System.out.println("강사 이메일 : " + teachersList.get(i).getTeacherEmail() + "\t");
            }
        } else if (type.equals("S")) {
            List<Students> studentsList = studentsService.searchStudentByName(name);
            if (studentsList.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
            for (int i = 0; i < studentsList.size(); i++) {
                System.out.print("학생명 : " + studentsList.get(i).getStudentName() + "\t");
                System.out.print("학생 생년월일 :" + studentsList.get(i).getStudentBirth() + "\t");
                System.out.print("학생 휴대폰 번호" + studentsList.get(i).getStudentPhone() + "\t");
                System.out.println("학생 이메일" + studentsList.get(i).getStudentEmail() + "\t");
            }
        }
    }

    // 학생 수강신청
    public void insertStudentCourse(int coursePk) throws SQLException {
        courseService.insert(coursePk, currentUserId);
    }

    // 학생 수강취소
    public void cancelStudentCourse() throws SQLException {
        courseService.delete(currentUserId);
    }

    // 강의 목록 조회 (전체)
    public void searchAllCourse() throws SQLException {
        List<Course> coursesList = courseService.getAllCourse();
        if (coursesList.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }
        for (int i = 0; i < coursesList.size(); i++) {
            System.out.print("강의 번호 : " + coursesList.get(i).getCoursePk() + "\t");
            System.out.print("강의명 : " + coursesList.get(i).getCourseTitle() + "\t");
            System.out.print("강의 최대정원수 : " + coursesList.get(i).getCourseCapacity() + "\t");
            System.out.print("강의 시작일 : " + coursesList.get(i).getStartDate() + "\t");
            System.out.println("강의 종료일 : " + coursesList.get(i).getEndDate() + "\t");
        }
    }

    // 강의 목록 조회 (이름 like)
    public void searchCourseByTitle(String name) throws SQLException {
        List<Course> coursesList = courseService.searchCourseTitle(name);
        if (coursesList.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }
        for (int i = 0; i < coursesList.size(); i++) {
            System.out.print("강의 번호 : " + coursesList.get(i).getCoursePk() + "\t");
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
            if (studentCourseInfo == null) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
            System.out.print("강의 명 : " + studentCourseInfo.getCourseTitle() + "\t");
            System.out.print("강의 시작 날짜 : " + studentCourseInfo.getCourseStartDate() + "\t");
            System.out.println("강의 종료 날짜 : " + studentCourseInfo.getCourseEndDate());
        } else if (currentUserId.contains("T")) {
            Teacher teacherCourseInfo = teacherService.teacherCourseInfo(currentUserName);
            if (teacherCourseInfo == null) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
            System.out.print("강의 명 : " + teacherCourseInfo.getCourseTitle() + "\t");
            System.out.print("강의 시작 날짜 : " + teacherCourseInfo.getStartDate() + "\t");
            System.out.println("강의 종료 날짜 : " + teacherCourseInfo.getEndDate());
        }
    }

    // 학생/강사/관리자 정보 등록
    public void registUserInfo(String type) throws SQLException {
        String newId = null;
        String newName = null;
        String newBirthDate = null;
        // String birthDate 값을 LocalDate 값 변환을 위한 변수
        LocalDate newCastBirthDate = null;
        String newPhoneNumber = null;
        String newEmail = null;

        while (true) {
            System.out.println("아이디를 입력 해 주세요.");
            System.out.print("입력 : ");
            newId = scanner.nextLine().trim();
            if (newId.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }

            System.out.println("이름을 입력 해 주세요.");
            System.out.print("입력 : ");
            newName = scanner.nextLine().trim();
            if (newName.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }

            // 학생이나 강사에 대해서만 입력 받도록 함
            // 관리자는 아이디랑 이름만 필요함
            if (type.equals("S") || type.equals("T")) {
                if (type.equals("S")) {
                    System.out.println("생년월일을 입력 해 주세요. (- 포함해서 작성)");
                    System.out.print("입력 : ");
                    newBirthDate = scanner.nextLine().trim();
                    if (newBirthDate.isEmpty()) {
                        System.out.println("빈값으로 입력 하실 수 없습니다.");
                        continue;
                    }

                    newCastBirthDate = LocalDate.from(LocalDate.parse(newBirthDate, formatter));
                }

                System.out.println("휴대폰 번호를 입력 해 주세요. (- 포함해서 작성)");
                System.out.print("입력 : ");
                newPhoneNumber = scanner.nextLine().trim();
                if (newPhoneNumber.isEmpty()) {
                    System.out.println("빈값으로 입력 하실 수 없습니다.");
                    continue;
                }
                if (!newPhoneNumber.contains("-")) {
                    System.out.println("하이픈(-)을 포함해서 작성 해주세요.");
                    continue;
                }

                System.out.println("이메일을 입력 해 주세요.");
                System.out.print("입력 : ");
                newEmail = scanner.nextLine().trim();
                if (newEmail.isEmpty()) {
                    System.out.println("빈값으로 입력 하실 수 없습니다.");
                    continue;
                }
            }

            if (type.equals("S")) {
                Students newStudentInfo = new Students(0, newId, newName, newCastBirthDate, newPhoneNumber, newEmail);
                adminService.addStudent(newStudentInfo);
            } else if (type.equals("T")) {
                Teacher newTeacherInfo = new Teacher(0, newId, newName, newPhoneNumber, newEmail);
                adminService.addTeacher(newTeacherInfo);
            } else if (type.equals("A")) {
                Admin newAdminInfo = new Admin(0, newId, newName);
                adminService.addAdmin(newAdminInfo);
            }

            System.out.println("처리 완료 되었습니다.");
            break;
        }
    }

    // 학생/강사/관리자 정보 수정
    public void updateUserInfo(String type) throws SQLException {
        String updateId = null;
        String updateName = null;
        String updateBirthDate = null;
        // String birthDate 값을 LocalDate 값 변환을 위한 변수
        LocalDate updateCastBirthDate = null;
        String updatePhoneNumber = null;
        String updateEmail = null;

        while (true) {
            System.out.println("수정할 유저의 ID를 입력 해 주세요.");
            String checkUpdateUserInfo = scanner.nextLine().trim();

            if (type.equals("S")) {
                studentInfo = studentsService.authenticateStudent(checkUpdateUserInfo);
                if (studentInfo == null) {
                    System.out.println("존재하지 않는 아이디 입니다.");
                    continue;
                }
                updateId = studentInfo.getStudentId();
            } else if (type.equals("T")) {
                teacherInfo = teacherService.authenticateTeacher(checkUpdateUserInfo);
                if (teacherInfo == null) {
                    System.out.println("존재하지 않는 아이디 입니다.");
                    continue;
                }
                updateId = teacherInfo.getTeacherId();
            } else if (type.equals("A")) {
                adminInfo = adminService.authenticateAdmin(checkUpdateUserInfo);
                if (adminInfo == null) {
                    System.out.println("존재하지 않는 아이디 입니다.");
                    continue;
                }
                updateId = adminInfo.getAdminId();
            }

            System.out.println("이름을 입력 해 주세요.");
            System.out.print("입력 : ");
            updateName = scanner.nextLine().trim();
            if (updateName.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }

            // 학생이나 강사에 대해서만 입력 받도록 함
            // 관리자는 아이디랑 이름만 필요함
            if (type.equals("S") || type.equals("T")) {
                if (type.equals("S")) {
                    System.out.println("생년월일을 입력 해 주세요. (- 포함해서 작성)");
                    System.out.print("입력 : ");
                    updateBirthDate = scanner.nextLine().trim();
                    if (updateBirthDate.isEmpty()) {
                        System.out.println("빈값으로 입력 하실 수 없습니다.");
                        continue;
                    }
                    updateCastBirthDate = LocalDate.from(LocalDate.parse(updateBirthDate, formatter));
                }

                System.out.println("휴대폰 번호를 입력 해 주세요. - 포함해서 작성");
                System.out.print("입력 : ");
                updatePhoneNumber = scanner.nextLine().trim();
                if (updatePhoneNumber.isEmpty()) {
                    System.out.println("빈값으로 입력 하실 수 없습니다.");
                    continue;
                }
                if (!updatePhoneNumber.contains("-")) {
                    System.out.println("하이픈(-)을 포함해서 작성 해주세요.");
                    continue;
                }

                System.out.println("이메일을 입력 해 주세요.");
                System.out.print("입력 : ");
                updateEmail = scanner.nextLine().trim();
                if (updateEmail.isEmpty()) {
                    System.out.println("빈값으로 입력 하실 수 없습니다.");
                    continue;
                }
            }

            if (type.equals("S")) {
                Students newStudentInfo = new Students(0, updateId, updateName, updateCastBirthDate, updatePhoneNumber, updateEmail);
                System.out.println("newStudentInfo : " + newStudentInfo);
                adminService.updateStudents(newStudentInfo);
            } else if (type.equals("T")) {
                Teacher newTeacherInfo = new Teacher(0, updateId, updateName, updatePhoneNumber, updateEmail);
                adminService.updateTeacher(newTeacherInfo);
            } else if (type.equals("A")) {
                Admin newAdminInfo = new Admin(0, updateId, updateName);
                adminService.updateAdmin(newAdminInfo);
            }

            System.out.println("처리 완료 되었습니다.");
            break;
        }
    }

    // 학생/강사/관리자 정보 삭제
    public void deleteUserInfo(String type) throws SQLException {

        while (true) {
            System.out.println("삭제할 유저의 ID를 입력 해 주세요.");
            String deleteUserInfo = scanner.nextLine().trim();

            if (deleteUserInfo.isEmpty()) {
                System.out.println("빈값을 입력할 수 없습니다.");
                continue;
            }

            if (currentUserId.equals(deleteUserInfo)) {
                System.out.println("현재 로그인 되어 있는 계정을 삭제 할 수 없습니다.");
                continue;
            }

            if (type.equals("S")) {
                adminService.deleteStudent(deleteUserInfo);
            } else if (type.equals("T")) {
                adminService.deleteTeacher(deleteUserInfo);
            } else if (type.equals("A")) {
                adminService.deleteAdmin(deleteUserInfo);
            }

            System.out.println("처리 완료 되었습니다.");
            break;
        }
    }

    // 강의정보 등록
    public void addCourseInfo() throws SQLException {
        String newTeacherId = null;
        String newTitle = null;
        int newCapacity = 0;
        String newStartDate = null;
        String newEndDate = null;
        // startDate, endDate 값을 LocalDate 값 변환을 위한 변수
        LocalDate newCastStartDate = null;
        LocalDate newCastEndDate = null;

        while (true) {
            System.out.println("담당 강사의 아이디를 입력 해 주세요.");
            System.out.print("입력 : ");
            newTeacherId = scanner.nextLine().trim();
            if (newTeacherId.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }

            System.out.println("강의명을 입력 해 주세요.");
            System.out.print("입력 : ");
            newTitle = scanner.nextLine().trim();
            if (newTitle.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }

            System.out.println("최대 정원 수를 입력 해 주세요. (숫자)");
            System.out.print("입력 : ");

            // 정수값이 아닌 값 예외처리
            try {
                newCapacity = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (Exception e) {
                System.out.println("정수값을 입력해 주세요.");
                scanner.nextLine();
                continue;
            }

            if (newCapacity <= 0) {
                System.out.println("0 보다 작거나 같은 수를 입력 하실 수 없습니다.");
                continue;
            }

            System.out.println("시작날짜를 입력 해 주세요.");
            System.out.print("입력 : ");
            newStartDate = scanner.nextLine().trim();
            if (newStartDate.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }
            newCastStartDate = LocalDate.from(LocalDate.parse(newStartDate, formatter));

            System.out.println("종료날짜를 입력 해 주세요.");
            System.out.print("입력 : ");
            newEndDate = scanner.nextLine().trim();
            if (newEndDate.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }
            newCastEndDate = LocalDate.from(LocalDate.parse(newEndDate, formatter));

            Course newCourseInfo = new Course(0, newTeacherId, newTitle, newCapacity, newCastStartDate, newCastEndDate);
            adminService.addCourse(newCourseInfo);

            System.out.println("처리 완료 되었습니다.");
            break;
        }
    }

    public void updateCourseInfo() throws SQLException {
        String updateTeacherId = null;
        String updateTitle = null;
        int updateCapacity = 0;
        String updateStartDate = null;
        String updateEndDate = null;
        // startDate, endDate 값을 LocalDate 값 변환을 위한 변수
        LocalDate updateCastStartDate = null;
        LocalDate updateCastEndDate = null;

        while (true) {
            System.out.println("수정할 강의의 ID를 입력 해 주세요.");
            int checkUpdateCourseInfo;

            try {
                checkUpdateCourseInfo = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (Exception e) {
                System.out.println("정수값을 입력해 주세요.");
                scanner.nextLine();
                continue;
            }

            if (checkUpdateCourseInfo <= 0) {
                System.out.println("0 보다 작거나 같은 수를 입력 하실 수 없습니다.");
                continue;
            }

            // todo pk값 검색 서비스로직 필요
            Course checkCourseInfo = courseService.authenticateCourse(checkUpdateCourseInfo);

            if (checkCourseInfo == null) {
                System.out.println("존재하는 않는 강의 ID 입니다.");
                continue;
            }

            System.out.println("변경할 담당 강사의 아이디를 입력 해 주세요.");
            System.out.println("기존 강사 아이디를 유지하고 싶을 시 다시 입력 부탁 드립니다. (개선예정)");
            System.out.print("입력 : ");
            updateTeacherId = scanner.nextLine().trim();
            if (updateTeacherId.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }

            System.out.println("변경할 강의명을 입력 해 주세요.");
            System.out.print("입력 : ");
            updateTitle = scanner.nextLine().trim();
            if (updateTitle.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }

            System.out.println("변경할 최대 정원 수를 입력 해 주세요. (숫자)");
            System.out.print("입력 : ");

            // 정수값이 아닌 값 예외처리
            try {
                updateCapacity = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (Exception e) {
                System.out.println("정수값을 입력해 주세요.");
                scanner.nextLine();
                continue;
            }

            if (updateCapacity <= 0) {
                System.out.println("0 보다 작거나 같은 수를 입력 하실 수 없습니다.");
                continue;
            }

            System.out.println("변경할 시작날짜를 입력 해 주세요.");
            System.out.print("입력 : ");
            updateStartDate = scanner.nextLine().trim();
            if (updateStartDate.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }
            updateCastStartDate = LocalDate.from(LocalDate.parse(updateStartDate, formatter));

            System.out.println("변경할 종료날짜를 입력 해 주세요.");
            System.out.print("입력 : ");
            updateEndDate = scanner.nextLine().trim();
            if (updateEndDate.isEmpty()) {
                System.out.println("빈값으로 입력 하실 수 없습니다.");
                continue;
            }
            updateCastEndDate = LocalDate.from(LocalDate.parse(updateEndDate, formatter));

            Course newCourseInfo = new Course(0, updateTeacherId, updateTitle, updateCapacity, updateCastStartDate, updateCastEndDate);
            adminService.updateCourse(newCourseInfo);

            break;
        }
    }

    public void deleteCourseInfo() throws SQLException {

        while (true) {
            System.out.println("삭제할 강의의 pk값을 입력 해 주세요.");
            int deleteCourseId = scanner.nextInt();

            try {
                deleteCourseId = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기
            } catch (Exception e) {
                System.out.println("정수값을 입력해 주세요.");
                scanner.nextLine();
                continue;
            }

            if (deleteCourseId <= 0) {
                System.out.println("0 보다 작거나 같은 수를 입력 하실 수 없습니다.");
                continue;
            }

            adminService.deleteCourse(deleteCourseId);

            break;
        }
    }


    public static void main(String[] args) {
        AcademyView academyView = new AcademyView();
        academyView.start();
    }
}
