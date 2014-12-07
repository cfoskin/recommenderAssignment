package view;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JList;

import controller.RecommenderController;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import model.Film;
import model.Member;
import model.Rating;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextPane;

import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.UIManager;


public class MyFilmsView extends JFrame {

	private JPanel contentPane;
	private RecommenderController rec;
	private Member member;
	private Film film;
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public MyFilmsView(Member member,RecommenderController rec) {
		this.rec= rec;
		this.member = member;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(12, 0, 611, 83);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("View your rated films and recommended films here!");
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("Kinnari", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel_2.setBounds(58, 12, 481, 50);
		panel.add(lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 153, 297, 260);
		contentPane.add(scrollPane);

		JList list = new JList(member.getMyFilms().toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent le) {
				int index = list.getSelectedIndex();
				if (index != -1)
				{  
					Film aFilm =(Film) member.getMyFilms().toArray()[index];
					film = aFilm;
				}
			}
		});

		scrollPane.setViewportView(list);
		JLabel lblNewLabel = new JLabel(" Your Rated Films - Highest Rating first");
		lblNewLabel.setForeground(Color.BLUE);
		scrollPane.setColumnHeaderView(lblNewLabel);

		JButton btnNewButton = new JButton("<--- Back to Home");
		btnNewButton.setBounds(246, 425, 166, 25);
		contentPane.add(btnNewButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(339, 153, 273, 260);
		contentPane.add(scrollPane_1);

		JLabel lblRecommended = new JLabel("       Films Recommended for you\n");
		lblRecommended.setForeground(Color.BLUE);
		scrollPane_1.setColumnHeaderView(lblRecommended);
		JList list2 = new JList(this.rec.getReccomendedFilms().toArray());
		scrollPane_1.setViewportView(list2);

		JButton btnGetMyRating = new JButton("Get my rating!");
		btnGetMyRating.setToolTipText("This is only for films that youve rated,\nnot for recommended films you haven't rated yet!!");
		btnGetMyRating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rating rating = rec.getLoggedInMember().getRatedFilms().get(film);
				if(rating != null)
				{
					JOptionPane.showMessageDialog(btnGetMyRating, "You rated : " + film.getTitle()+ " and you rated it: " + rating);
				}
				else
				{
					JOptionPane.showMessageDialog(btnGetMyRating, "You havent rated these films! " + "\n"+ " These are just reccommended for you!");
				}
			}
		});
		btnGetMyRating.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		btnGetMyRating.setForeground(Color.RED);
		btnGetMyRating.setBounds(181, 110, 133, 25);
		contentPane.add(btnGetMyRating);

		JTextPane txtpnToViewYour = new JTextPane();
		txtpnToViewYour.setFont(new Font("Dialog", Font.ITALIC, 12));
		txtpnToViewYour.setForeground(Color.BLUE);
		txtpnToViewYour.setBackground(SystemColor.controlHighlight);
		txtpnToViewYour.setText("Select a film from your Rated\nfilms below and  hit the\nget my rating button!");
		txtpnToViewYour.setBounds(22, 83, 187, 96);
		contentPane.add(txtpnToViewYour);
		
		JTextArea txtrHeresSomeFilms = new JTextArea();
		txtrHeresSomeFilms.setBackground(SystemColor.controlHighlight);
		txtrHeresSomeFilms.setFont(new Font("Dialog", Font.ITALIC, 12));
		txtrHeresSomeFilms.setForeground(Color.BLUE);
		txtrHeresSomeFilms.setText("\nHere's some films we \nrecommend for you!");
		txtrHeresSomeFilms.setBounds(384, 81, 166, 60);
		contentPane.add(txtrHeresSomeFilms);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeView homeView = new HomeView(member, rec);
				homeView.setVisible(true);
				setVisible(false);
			}
		});
	}
}
