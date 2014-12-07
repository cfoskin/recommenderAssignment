package view;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import controller.RecommenderController;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import model.Film;
import model.Member;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;


@SuppressWarnings("serial")
public class FilmView extends JFrame {

	private JPanel contentPane;
	private RecommenderController rec;
	private Member member;
	private JComboBox comboBox;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FilmView(Member member,RecommenderController rec) {
		this.rec= rec;
		this.member = member;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(330, 95, 278, 250);
		contentPane.add(scrollPane_1);

		JLabel lblNewLabel = new JLabel("      All films (Highest Rating first)");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Kinnari", Font.BOLD | Font.ITALIC, 14));
		scrollPane_1.setColumnHeaderView(lblNewLabel);

		JList list = new JList(rec.sortFilmsByRating().toArray());
		scrollPane_1.setViewportView(list);

		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(12, 0, 611, 83);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("       Welcome to the film library");
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("Kinnari", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel_2.setBounds(135, 12, 413, 50);
		panel.add(lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 98, 287, 250);
		contentPane.add(scrollPane);

		JLabel lblNewLabel_1 = new JLabel("           All films (Newest first)");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("Kinnari", Font.BOLD | Font.ITALIC, 14));
		scrollPane.setColumnHeaderView(lblNewLabel_1);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		JList list2 = new JList(rec.sortFilmsByYear().toArray());
		scrollPane.setViewportView(list2);

		JLabel lblDeleteAFilm = new JLabel("Delete a film here");
		lblDeleteAFilm.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		lblDeleteAFilm.setForeground(Color.BLUE);
		lblDeleteAFilm.setBounds(256, 360, 161, 24);
		contentPane.add(lblDeleteAFilm);
		comboBox = new JComboBox(rec.sortFilmsByYear().toArray());
		comboBox.setBounds(22, 397, 413, 49);
		contentPane.add(comboBox);

		JButton btnDeleteFilm = new JButton("Delete Film");
		btnDeleteFilm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Film film = (Film) comboBox.getSelectedItem();
				boolean success = rec.deleteFilm(film);
				if(success)
				{
					filmRemovedInfoBox();
				}
			}
		});
		btnDeleteFilm.setFont(new Font("DejaVu Sans Light", Font.BOLD | Font.ITALIC, 12));
		btnDeleteFilm.setForeground(Color.RED);
		btnDeleteFilm.setBounds(464, 409, 117, 25);
		contentPane.add(btnDeleteFilm);
		
				JButton btnNewButton = new JButton("<--- Back to Home");
				btnNewButton.setBounds(449, 360, 159, 25);
				contentPane.add(btnNewButton);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						HomeView homeView = new HomeView(member, rec);
						homeView.setVisible(true);
						setVisible(false);
					}
				});
	}
	private void filmRemovedInfoBox()
	{
		JOptionPane.showMessageDialog(contentPane, "The film was removed from the " + " \n" + 
				"library successfully!");
	}
}
