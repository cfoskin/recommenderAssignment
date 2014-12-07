package view;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
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
import model.Rating;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextPane;
import javax.swing.JComboBox;


public class RateFilmView extends JFrame {

	private JPanel contentPane;
	private RecommenderController rec;
	private Member member;
	private Film film;
	private JComboBox comboBox;
	/**
	 * Create the frame.
	 */
	public RateFilmView(Member member,RecommenderController rec) {
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

		JLabel lblNewLabel_2 = new JLabel("          Rate a film here!");
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("Kinnari", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel_2.setBounds(135, 12, 413, 50);
		panel.add(lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 98, 287, 359);
		contentPane.add(scrollPane);

		JList list = new JList(rec.displayUnseenFilmsForRating().toArray());

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent le) {
				int index = list.getSelectedIndex();
				try {
					if (index != -1)
					{  
						if(index <rec.displayUnseenFilmsForRating().size())
						{
							Film aFilm =(Film) rec.displayUnseenFilmsForRating().toArray()[index];
							film = aFilm;
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(contentPane, "incorrect value selected");
				}
			}
		});

		scrollPane.setViewportView(list);

		JLabel lblNewLabel_1 = new JLabel(" Here is all your  Unseen films (Newest first)");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("Kinnari", Font.BOLD | Font.ITALIC, 14));
		scrollPane.setColumnHeaderView(lblNewLabel_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(321, 96, 285, 257);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblEnterYourRating = new JLabel("Choose your rating");
		lblEnterYourRating.setForeground(Color.BLUE);
		lblEnterYourRating.setBounds(71, 118, 139, 15);
		panel_1.add(lblEnterYourRating);

		JButton btnNewButton_1 = new JButton("Rate!");
		btnNewButton_1.setForeground(Color.RED);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rating rating = (Rating) comboBox.getSelectedItem();
				boolean success = rec.rateAFilm(film, rating);
				if(success)
				{
					ratedFilmInfoBox();
				}
			} 
		});
		btnNewButton_1.setBounds(81, 207, 117, 25);
		panel_1.add(btnNewButton_1);

		JTextPane txtpnPleaseClickOn = new JTextPane();
		txtpnPleaseClickOn.setForeground(Color.BLUE);
		txtpnPleaseClickOn.setText("How to rate:\n1. Click on chosen film once.\n2. Select rating from the drop-down menu.\n3. Click the rate button!\n4. Return to your films to see the film added to your ratings!");
		txtpnPleaseClickOn.setBounds(0, 12, 285, 94);
		panel_1.add(txtpnPleaseClickOn);


		comboBox = new JComboBox(Rating.values());
		comboBox.setBounds(43, 145, 196, 35);
		panel_1.add(comboBox);
		comboBox.setToolTipText("Click here to view available rating values!");
		comboBox.setForeground(Color.BLUE);
		comboBox.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));

		JButton btnNewButton = new JButton("<--- Back to Home");
		btnNewButton.setBounds(369, 385, 190, 25);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeView homeView = new HomeView(member, rec);
				homeView.setVisible(true);
				setVisible(false);
			}
		});
	}
	private void ratedFilmInfoBox()
	{
		JOptionPane.showMessageDialog(contentPane, "The film : " + film.getTitle()+ " was rated successfully!");
	}
}
