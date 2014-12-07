package view;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import model.Member;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import controller.RecommenderController;
import java.awt.Color;

public class AccountView {

	RecommenderController rec = new RecommenderController();
	public JFrame frmFilmRecommenderSystem;
	private final JLabel lbFirstName = new JLabel("First name:");
	private JLabel lblLastName;
	private JLabel lblAccountName;
	private JLabel lblPassword;
	private JButton btnRegister;
	private JLabel lblSignUpHere;
	private JTextField textField_firstName;
	private JTextField textField_lastName;
	private JTextField textField_accountName;
	private JTextField textField_password;
	private JLabel lblLogInHere;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JButton btnLogIn;
	private JTextField textField_myAccountName;
	private JTextField textField_myPassword;
	private JButton btnNewButton;
	private Member member;


	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	/**
	 * Create the application.
	 */
	public AccountView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFilmRecommenderSystem = new JFrame();
		frmFilmRecommenderSystem.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		frmFilmRecommenderSystem.setBackground(Color.WHITE);
		frmFilmRecommenderSystem.setTitle("Film Recommender System");
		frmFilmRecommenderSystem.getContentPane().setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		frmFilmRecommenderSystem.setBounds(100, 100, 570, 470);
		frmFilmRecommenderSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFilmRecommenderSystem.getContentPane().setLayout(null);


		textField_lastName = new JTextField();
		textField_lastName.setBounds(146, 166, 114, 19);
		frmFilmRecommenderSystem.getContentPane().add(textField_lastName);
		textField_lastName.setColumns(10);

		textField_accountName = new JTextField();
		textField_accountName.setBounds(146, 197, 114, 19);
		frmFilmRecommenderSystem.getContentPane().add(textField_accountName);
		textField_accountName.setColumns(10);

		textField_firstName = new JTextField();
		textField_firstName.setBounds(146, 129, 114, 19);
		frmFilmRecommenderSystem.getContentPane().add(textField_firstName);
		textField_firstName.setColumns(10);
		lbFirstName.setForeground(Color.BLUE);
		lbFirstName.setBounds(30, 122, 121, 33);
		frmFilmRecommenderSystem.getContentPane().add(lbFirstName);

		lblLastName = new JLabel("Last name:");
		lblLastName.setForeground(Color.BLUE);
		lblLastName.setBounds(30, 144, 107, 50);
		frmFilmRecommenderSystem.getContentPane().add(lblLastName);

		lblAccountName = new JLabel("Account name:");
		lblAccountName.setForeground(Color.BLUE);
		lblAccountName.setBounds(30, 190, 114, 33);
		frmFilmRecommenderSystem.getContentPane().add(lblAccountName);

		lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.BLUE);
		lblPassword.setBounds(36, 219, 133, 50);
		frmFilmRecommenderSystem.getContentPane().add(lblPassword);

		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean success =  rec.register(textField_firstName.getText(), textField_lastName.getText(),
						textField_accountName.getText(),textField_password.getText());
				if(success)
				{
					member = rec.getLoggedInMember();
					HomeView homeView = new HomeView(member, rec);
					homeView.setVisible(true);
					frmFilmRecommenderSystem.setVisible(false);
				}
				else
				{
					infoBox();
				}
			}
		});
		btnRegister.setBounds(146, 270, 117, 25);
		frmFilmRecommenderSystem.getContentPane().add(btnRegister);

		lblSignUpHere = new JLabel("Register here!");
		lblSignUpHere.setForeground(Color.BLUE);
		lblSignUpHere.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		lblSignUpHere.setBounds(80, 79, 149, 38);
		frmFilmRecommenderSystem.getContentPane().add(lblSignUpHere);

		lblLogInHere = new JLabel("Log in here!");
		lblLogInHere.setForeground(Color.BLUE);
		lblLogInHere.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		lblLogInHere.setBounds(385, 78, 141, 38);
		frmFilmRecommenderSystem.getContentPane().add(lblLogInHere);

		textField_myAccountName = new JTextField();
		textField_myAccountName.setBounds(412, 160, 114, 19);
		frmFilmRecommenderSystem.getContentPane().add(textField_myAccountName);
		textField_myAccountName.setColumns(10);

		lblNewLabel = new JLabel("Account name:");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBounds(303, 153, 114, 33);
		frmFilmRecommenderSystem.getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setBounds(303, 197, 121, 33);
		frmFilmRecommenderSystem.getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Welcome! Please sign up or log in!");
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 28));
		lblNewLabel_2.setBounds(80, 28, 446, 50);
		frmFilmRecommenderSystem.getContentPane().add(lblNewLabel_2);

		btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean success = rec.logInMember(textField_myAccountName.getText(), textField_myPassword.getText());
				if(success)
				{
					member = rec.getLoggedInMember();
					HomeView homeView = new HomeView(member,rec);
					homeView.setVisible(true);
					frmFilmRecommenderSystem.setVisible(false);
				}
				else{
					LogInInfoBox();
				}
			}
		});
		btnLogIn.setBounds(409, 242, 117, 25);
		frmFilmRecommenderSystem.getContentPane().add(btnLogIn);

		textField_password = new JTextField();
		textField_password.setBounds(146, 235, 114, 19);
		frmFilmRecommenderSystem.getContentPane().add(textField_password);
		textField_password.setColumns(10);

		textField_myPassword = new JTextField();
		textField_myPassword.setBounds(412, 204, 114, 19);
		frmFilmRecommenderSystem.getContentPane().add(textField_myPassword);
		textField_myPassword.setColumns(10);

		btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rec.saveXml();
				System.exit(0);
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		btnNewButton.setBounds(235, 362, 117, 25);
		frmFilmRecommenderSystem.getContentPane().add(btnNewButton);
	}

	private static void infoBox()
	{
		JOptionPane.showMessageDialog(null,"Sorry, Account name is taken!" + "\n" + "Please try another username", null, 0);
	}
	private static void LogInInfoBox()
	{
		JOptionPane.showMessageDialog(null,"Invalid details! "+ "\n" + "Please try again", null, 0);
	}
}
