package src.kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;


public class LoginFrame extends JFrame {

    private JTextField idField;
    private JPasswordField pwField;

    public LoginFrame() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));//로그인
        JLabel idLabel = new JLabel("ID: ");
        idField = new JTextField(10);
        idPanel.add(idLabel);
        idPanel.add(idField);
        setTitle("Login");

        JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel pwLabel = new JLabel("PW: ");
        pwField = new JPasswordField(10);
        pwPanel.add(pwLabel);
        pwPanel.add(pwField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String pw = String.valueOf(pwField.getPassword());

                try {
                    String validPassword = FirebaseUtil.validateUserPassword(id);
                    if (pw.equals(validPassword)) {
                        openMainFrame(id);
                    } else {
                        JOptionPane.showMessageDialog(null, "로그인 실패");
                    }
                } catch (ExecutionException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterFrame();
            }
        });

        add(idPanel, BorderLayout.NORTH);
        add(pwPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    private void openMainFrame(String id) {
        MainMenu mainFrame = new MainMenu(id);
        setVisible(false);
        mainFrame.setVisible(true);
    }

    private void openRegisterFrame() {
        RegisterFrame registerFrame = new RegisterFrame(this);
        setVisible(false);
        registerFrame.setVisible(true);
    }
}
