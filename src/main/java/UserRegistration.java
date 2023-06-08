import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * User Registration using Swing
 */
public class UserRegistration extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField idTextField;
    private JTextField firstname;
    private JTextField lastname;
    private JTextField email;
    private JTextField mob;
    private JButton btnNewButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserRegistration frame = new UserRegistration();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */

    public UserRegistration() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\User\\Desktop\\STDM.jpg"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewUserRegister = new JLabel("New Employee Registration");
        lblNewUserRegister.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewUserRegister.setBounds(362, 52, 325, 50);
        contentPane.add(lblNewUserRegister);

        JLabel lblId = new JLabel("ID");
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblId.setBounds(58, 152, 99, 43);
        contentPane.add(lblId);

        JLabel lblNewLabel = new JLabel("Last name");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(58, 243, 110, 29);
        contentPane.add(lblNewLabel);

        JLabel lblEmailAddress = new JLabel("Email\r\n address");
        lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEmailAddress.setBounds(58, 324, 124, 36);
        contentPane.add(lblEmailAddress);

        idTextField = new JTextField();
        idTextField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        idTextField.setBounds(214, 151, 228, 50);
        UUID randomId = UUID.randomUUID();
        idTextField.setEditable(false);
        contentPane.add(idTextField);
        idTextField.setColumns(10);

        lastname = new JTextField();
        lastname.setFont(new Font("Tahoma", Font.PLAIN, 32));
        lastname.setBounds(214, 235, 228, 50);
        contentPane.add(lastname);
        lastname.setColumns(10);

        email = new JTextField();

        email.setFont(new Font("Tahoma", Font.PLAIN, 32));
        email.setBounds(214, 320, 228, 50);
        contentPane.add(email);
        email.setColumns(10);

        firstname = new JTextField();
        firstname.setFont(new Font("Tahoma", Font.PLAIN, 32));
        firstname.setBounds(707, 151, 228, 50);
        contentPane.add(firstname);
        firstname.setColumns(10);

        JLabel lblFirstname= new JLabel("First Name");
        lblFirstname.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblFirstname.setBounds(542, 159, 99, 29);
        contentPane.add(lblFirstname);

        JLabel lblMobileNumber = new JLabel("Mobile number");
        lblMobileNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblMobileNumber.setBounds(542, 329, 139, 26);
        contentPane.add(lblMobileNumber);

        mob = new JTextField();
        mob.setFont(new Font("Tahoma", Font.PLAIN, 32));
        mob.setBounds(707, 320, 228, 50);
        contentPane.add(mob);
        mob.setColumns(10);

        btnNewButton = new JButton("Register");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = UUID.randomUUID().toString();
                String firstName = firstname.getText();
                String lastName = lastname.getText();
                String emailId = email.getText();
    //            String userName = username.getText();
                String mobileNumber = mob.getText();
                int len = mobileNumber.length();

                String msg = "" + firstName;
                msg += " \n";
                if (len != 10) {
                    JOptionPane.showMessageDialog(btnNewButton, "Enter a valid mobile number");
                }

                try {
                    Class.forName("org.h2.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:h2:~/swing_demo", "sa", "");
                    Statement statement = connection.createStatement();
                    // Create the database
                    statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS swing_demo");

                    // Use the created database
                    statement.execute("USE swing_demo");
                    String createTableSql = "CREATE TABLE IF NOT EXISTS employee (" +
                            "id UUID DEFAULT UUID(),"+
                            "first_name varchar(30) NOT NULL," +
                            "last_name varchar(30) NOT NULL," +
                            "department varchar(250),"+
                            "email_id varchar(250) NOT NULL,"+
                            "mobile_number varchar(250) NOT NULL" +
                            ")";
                    statement.executeUpdate(createTableSql);

                    // Close the statement and connection
                    statement.close();

                    String query = "INSERT INTO employee(first_name, last_name, department, email_id, mobile_number) values('"+ firstName + "','" + lastName + "','" + "','" + emailId + "','" + mobileNumber + "')";

                    Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);
                    if (x == 0) {
                        JOptionPane.showMessageDialog(btnNewButton, "This is alredy exist");
                    } else {
                        JOptionPane.showMessageDialog(btnNewButton,
                                "Welcome, " + msg + "Your account is sucessfully created");
                    }
                    connection.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnNewButton.setBounds(399, 447, 259, 74);
        contentPane.add(btnNewButton);
    }
}