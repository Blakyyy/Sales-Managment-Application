package gestiondeventas;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.*;

public class View_Reg extends Model_RegAndLog implements ActionListener{
    //This class is the GUI when user press "Sign up" button. 
    public String[] emailUserPass = new String[3];
    private static JFrame registerFrame;
    private static JPanel registerPanel;
    private static JLabel emaiLabel, usernameLabel, passwordLabel, succes;
    private static JTextField emailText, usernameText;
    private static JPasswordField passwordField;
    private static JButton newRegisterButton;
    
    public View_Reg() {
        registerFrame = new JFrame();
        registerPanel = new JPanel();
        registerFrame.setSize(350, 200);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        registerFrame.add(registerPanel);
        registerPanel.setLayout(null);

        emaiLabel = new JLabel("Email");
        emaiLabel.setBounds(10, 20, 80, 25);
        registerPanel.add(emaiLabel);

        emailText = new JTextField();
        emailText.setBounds(100, 20, 165, 25);
        registerPanel.add(emailText);

        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 50, 80, 25);
        registerPanel.add(usernameLabel);

        usernameText = new JTextField();
        usernameText.setBounds(100, 50, 165, 25);
        registerPanel.add(usernameText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 80, 80, 25);
        registerPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 80, 165, 25);
        registerPanel.add(passwordField);

        succes = new JLabel();
        succes.setBounds(10, 130, 400, 25);
        succes.setText("");
        registerPanel.add(succes);

        newRegisterButton = new JButton("Sign up");
        newRegisterButton.setBounds(100, 110, 80, 25);
        registerPanel.add(newRegisterButton);
        newRegisterButton.addActionListener(this);
        registerFrame.setVisible(true);

    }

    //Backend functions when user presses "Sign up" button
    @Override
    public void actionPerformed(ActionEvent e) {
        emailUserPass[0] = emailText.getText();
        emailUserPass[1] = actionPerformedUser(e);
        emailUserPass[2] = passwordField.getText();
        Model_RegAndLog newb = new Model_RegAndLog();
            if(newb.signUp(getAdmin(), getUrl(), getPasskey(), emailUserPass[1], emailUserPass[2], emailUserPass[0]) == 1){
                succes.setText("Registration was successful");
            }
            else if(emailUserPass[0].equals("") || emailUserPass[1].equals("") || emailUserPass[2].equals("")){
                succes.setText("Email, username and password fields are obligatory");
            }
            else if(newb.signUp(getAdmin(), getUrl(), getPasskey(), emailUserPass[1], emailUserPass[2], emailUserPass[0]) == -2){
                succes.setText("User already exists");
            }
            else if(newb.signUp(getAdmin(), getUrl(), getPasskey(), emailUserPass[1], emailUserPass[2], emailUserPass[0]) == -1){
                succes.setText("Email format is incorrect");
            }
    } 

    //Collect the input of username field with  check if user entred a username with a space at end.
    public String actionPerformedUser(ActionEvent e){
        String[] userTextArray = usernameText.getText().split("");
        if(userTextArray[userTextArray.length - 1].equals(" ")){
            String userTextFinal = usernameText.getText().substring(0, usernameText.getText().length() - 1);
            return userTextFinal;
        }
        return usernameText.getText();
    }
}
