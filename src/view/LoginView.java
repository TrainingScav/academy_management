package view;

import dao.AdminDAO;
import dao.StudentsDAO;
import dao.TeacherDAO;
import dto.Admin;
import dto.Students;
import dto.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginView extends JFrame implements ActionListener {

    private String currentUserName;
    private String currentUserId;

    ///  TODO DAO 호출 대신 service 호출 로직으로 변경 필요
    private final StudentsDAO studentsDAO = new StudentsDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();
    private final AdminDAO adminDAO = new AdminDAO();


    private JPanel panel1;
    private JLabel idLabel; // 아이디
    private JTextField idTextField; // 아이디 입력창
    private JButton loginButton; // 로그인 버튼

    private JPanel panel2;

    private JPanel radioPanel;

    JRadioButton[] radio = new JRadioButton[3];
    String[] radio_name = {"학생", "강사", "관리자"};


    public LoginView() {
        initData();
        setInitLayout();
        addEventListener();
    }

    private void initData() {
        setTitle("로그인");
        setSize(400, 300);

        radioPanel = new JPanel();

        panel1 = new JPanel();
        idLabel = new JLabel("아이디");
        idTextField = new JTextField(10);
        loginButton = new JButton("로그인");

        panel2 = new JPanel();
    }

    private void setInitLayout() {
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        Color backgroundColor = new Color(200, 235, 226);
        getContentPane().setBackground(backgroundColor);

        // radioPanel
        ButtonGroup group = new ButtonGroup();
        radioPanel.setBounds(100, 30, 200, 40);
        radioPanel.setBackground(backgroundColor);
        // for 문으로 각 라디오 버튼 생성 및 추가
        for(int i=0; i<radio.length; i++){
            radio[i] = new JRadioButton(radio_name[i]);
            group.add(radio[i]);
            radioPanel.add(radio[i]);
        }
        // 학생 라디오 버튼 default 설정
        radio[0].setSelected(true);
        add(radioPanel);

        // panel1
        panel1.setBounds(40, 80, 200, 55);
        panel1.setBackground(backgroundColor);
        add(panel1);

        panel1.add(idLabel);
        panel1.add(idTextField);

        loginButton.setBounds(240, 77, 80, 40);
        add(loginButton);

        // panel2
        panel2.setBounds(0, 180, 400, 100);
        panel2.setBackground(backgroundColor);
        add(panel2);

        setVisible(true);
    }

    private void addEventListener() {
        loginButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton targetButton = (JButton) e.getSource();

        if (targetButton == loginButton) {
            String id = idTextField.getText();
            String selectedRadioName = "";

            for (int i = 0; i < radio.length; i++) {
                if(radio[i].isSelected()){
                    selectedRadioName = radio[i].getText();
                }
            }

            /// TODO SERVICE 구현 시 서비스를 호출해서 구현
            /// 입력한 id와 선택한 라디오 버튼에 따라 인증 확인 메서드 구현
            login(id,selectedRadioName);

            // null이면 로그인에 실패한 것
            /*
            if (loginMemberDTO == null) {
                JOptionPane.showMessageDialog(null, "아이디나 비밀번호를 확인해주세요.", "실패", JOptionPane.PLAIN_MESSAGE);
                idTextField.grabFocus();
                // 로그인 성공
            } else {
                // 확인을 누르면 로그인 프레임 닫기 + 쇼핑몰 홈페이지 프레임
                JOptionPane.showMessageDialog(null, id + "님, 환영합니다!", "로그인 성공", JOptionPane.PLAIN_MESSAGE);
                new ShopFrame(id);
                this.dispose();
            }
             */
        }
    }

    public void login(String id, String selectedRadioName) {
        Students studentLoginInfo = new Students();
        Teacher teacherLoginInfo = new Teacher();
        Admin adminLoginInfo = new Admin();

        System.out.println("id 값 : " + id );
        System.out.println("selectedRadioName 값 : " + selectedRadioName );

        if (selectedRadioName.equals("학생")) {
            try {
                studentLoginInfo = studentsDAO.authenticateStudents(id);
                System.out.println("결과 : " + studentLoginInfo);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (selectedRadioName.equals("강사")) {
            try {
                teacherLoginInfo = teacherDAO.authenticateTeacher(id);
                System.out.println("결과 : " + teacherLoginInfo);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (selectedRadioName.equals("관리자")) {
            try {
                adminLoginInfo = adminDAO.authenticateAdmin(id);
                System.out.println("결과 : " + adminLoginInfo);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String[] args) {
        LoginView login = new LoginView();
    }
}
