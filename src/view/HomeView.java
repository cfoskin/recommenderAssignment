package view;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controller.RecommenderController;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import model.Film;
import model.Member;
import java.awt.Color;

@SuppressWarnings("serial")
public class HomeView extends JDialog {

	private RecommenderController rec;
	private Member member;
	private JTextField textField_filmTitle;
	private JTextField textField_filmYear;
	private JTextField textField_filmGenre;
	private JLabel lblEnterFilmYear;
	private JLabel lblEnterFilmGenre;
	private JButton btnAddNewFilm;
	private JLabel lblCloseAccountHere;
	private JButton btnCloseMyAccount;
	private JButton btnNewButton;
	private JLabel lblLogOutOf;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JButton btnViewFilms;
	private JLabel lblViewYouFilms;
	private JButton btnMyRatedFilms;
	private JLabel lblSeeWhosRegistered;
	/**
	 * Launch the application.
	 */
	public HomeView(Member member,RecommenderController rec) {
		getContentPane().setForeground(Color.BLUE);
		this.rec = rec;
		this.member = member;
		setBounds(100, 100, 702, 500);
		getContentPane().setLayout(null);
		getContentPane().setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		setTitle("Welcome to your home menu");
		getContentPane().setLayout(null);

		JLabel lblAddAFilm = new JLabel("Add a film here!");
		lblAddAFilm.setForeground(Color.BLUE);
		lblAddAFilm.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblAddAFilm.setBounds(107, 164, 152, 43);
		getContentPane().add(lblAddAFilm);

		textField_filmTitle = new JTextField();
		textField_filmTitle.setBounds(212, 229, 114, 19);
		getContentPane().add(textField_filmTitle);
		textField_filmTitle.setColumns(10);

		textField_filmYear = new JTextField();
		textField_filmYear.setToolTipText("numbers only!!!");
		textField_filmYear.setBounds(212, 260, 114, 19);
		getContentPane().add(textField_filmYear);
		textField_filmYear.setColumns(10);

		textField_filmGenre = new JTextField();
		textField_filmGenre.setBounds(212, 291, 114, 19);
		getContentPane().add(textField_filmGenre);
		textField_filmGenre.setColumns(10);

		JLabel lblEnterFilmTitle = new JLabel("Enter film title:");
		lblEnterFilmTitle.setForeground(Color.BLUE);
		lblEnterFilmTitle.setBounds(59, 219, 105, 29);
		getContentPane().add(lblEnterFilmTitle);

		lblEnterFilmYear = new JLabel("Enter film year:");
		lblEnterFilmYear.setForeground(Color.BLUE);
		lblEnterFilmYear.setBounds(59, 243, 114, 43);
		getContentPane().add(lblEnterFilmYear);

		lblEnterFilmGenre = new JLabel("Enter film genre:");
		lblEnterFilmGenre.setForeground(Color.BLUE);
		lblEnterFilmGenre.setBounds(59, 281, 118, 29);
		getContentPane().add(lblEnterFilmGenre);

		btnAddNewFilm = new JButton("Add new film");
		btnAddNewFilm.setToolTipText("Be sure you are using the correct format or the film will not add!");
		btnAddNewFilm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int filmsNewId = rec.getFilms().size()+1;
					Film film = rec.searchForFilm(filmsNewId);
					if(film == null)
					{
						int filmYear = new Integer(textField_filmYear.getText());
						boolean success = rec.createFilm(filmsNewId, textField_filmTitle.getText(),
								filmYear, textField_filmGenre.getText());
						if(success)
						{
							infoBox();
						}
						else
						{ 
							badInfoBox();
						}
					}
					else
					{
						badIdInfoBox();
					}
				} catch (NumberFormatException e1) {
					badInfoBox();				}
			}
		});
		btnAddNewFilm.setBounds(78, 331, 145, 25);
		getContentPane().add(btnAddNewFilm);

		lblCloseAccountHere = new JLabel("Close account here!");
		lblCloseAccountHere.setForeground(Color.RED);
		lblCloseAccountHere.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblCloseAccountHere.setBounds(250, 382, 200, 36);
		getContentPane().add(lblCloseAccountHere);

		btnCloseMyAccount = new JButton("Delete my account");
		btnCloseMyAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rec.deleteMember(rec.getLoggedInMember());
					closedAccInfoBox();	
					AccountView accView = new AccountView();
					accView.frmFilmRecommenderSystem.setVisible(true);
					setVisible(false);			
				} catch (Exception e1) {
				}
			}
		});
		btnCloseMyAccount.setToolTipText("Are you sure? This cannot be undone!");
		btnCloseMyAccount.setBounds(250, 422, 173, 25);
		getContentPane().add(btnCloseMyAccount);

		btnNewButton = new JButton("Log out");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rec.saveXml();
				AccountView accView = new AccountView();
				accView.frmFilmRecommenderSystem.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton.setBounds(47, 422, 117, 25);
		getContentPane().add(btnNewButton);

		lblLogOutOf = new JLabel("Log out of your account here!");
		lblLogOutOf.setForeground(Color.BLUE);
		lblLogOutOf.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		lblLogOutOf.setBounds(12, 386, 220, 30);
		getContentPane().add(lblLogOutOf);

		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(75, 12, 509, 122);
		getContentPane().add(panel);
		panel.setLayout(null);

		lblNewLabel = new JLabel("Welcome to your Home page!");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 21));
		lblNewLabel.setBounds(114, 25, 352, 50);
		panel.add(lblNewLabel);

		btnViewFilms = new JButton("View all films");
		btnViewFilms.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		btnViewFilms.setForeground(Color.BLUE);
		btnViewFilms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilmView filmView = new FilmView(member,rec);
				filmView.setVisible(true);
				setVisible(false);
			}
		});
		btnViewFilms.setBounds(414, 215, 134, 25);
		getContentPane().add(btnViewFilms);

		lblViewYouFilms = new JLabel("View films here!");
		lblViewYouFilms.setForeground(Color.BLUE);
		lblViewYouFilms.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblViewYouFilms.setBounds(414, 168, 157, 36);
		getContentPane().add(lblViewYouFilms);

		btnMyRatedFilms = new JButton("My rated films");
		btnMyRatedFilms.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		btnMyRatedFilms.setForeground(Color.BLUE);
		btnMyRatedFilms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyFilmsView myfilms = new MyFilmsView(member,rec);
				myfilms.setVisible(true);
				setVisible(false);
			}
		});
		btnMyRatedFilms.setBounds(414, 252, 134, 25);
		getContentPane().add(btnMyRatedFilms);

		JButton btnNewButton_1 = new JButton("Rate a film");
		btnNewButton_1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		btnNewButton_1.setForeground(Color.BLUE);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RateFilmView rafv = new RateFilmView(member,rec);
				rafv.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_1.setBounds(414, 288, 134, 25);
		getContentPane().add(btnNewButton_1);

		JButton btnViewAllMembers = new JButton("View  members");
		btnViewAllMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberListView mlv = new MemberListView(member,rec);
				mlv.setVisible(true);
				setVisible(false);	
			}
		});
		btnViewAllMembers.setForeground(Color.BLUE);
		btnViewAllMembers.setBounds(414, 356, 145, 25);
		getContentPane().add(btnViewAllMembers);

		lblSeeWhosRegistered = new JLabel("See who's registered!");
		lblSeeWhosRegistered.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblSeeWhosRegistered.setForeground(Color.BLUE);
		lblSeeWhosRegistered.setBounds(386, 331, 198, 19);
		getContentPane().add(lblSeeWhosRegistered);
	}

	public static void infoBox()
	{
		String infoMessage = "";
		String titleBar ="";
		JOptionPane.showMessageDialog(null, infoMessage, "Film was Added!" + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	private static void badInfoBox()
	{
		JOptionPane.showMessageDialog(null,"Invalid details! "+ "\n" + "Please try again", null, 0);
	}

	private static void badIdInfoBox()
	{
		JOptionPane.showMessageDialog(null,"Invalid Id entered!- this Id is in use!"+ "\n" + "Please try again", null, 0);
	}
	private void closedAccInfoBox()
	{
		JOptionPane.showMessageDialog(btnCloseMyAccount,"Your Account is now closed, "+ "\n" + "Please Sign up again. GoodBye" + "", null, 0);
	}
}

