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
import javax.swing.JTextArea;
import javax.swing.UIManager;


public class MemberListView extends JFrame {

	private JPanel contentPane;
	private RecommenderController rec;
	private Member member;
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public MemberListView(Member member,RecommenderController rec) {
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

		JLabel lblNewLabel_2 = new JLabel("           View all members on the system!");
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("Kinnari", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel_2.setBounds(58, 12, 481, 50);
		panel.add(lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 133, 532, 290);
		contentPane.add(scrollPane);
		
		JList list = new JList(rec.getMembers().toArray());
		
		scrollPane.setViewportView(list);

		JButton btnNewButton = new JButton("<--- Back to Home");
		btnNewButton.setBounds(246, 425, 166, 25);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeView homeView = new HomeView(member, rec);
				homeView.setVisible(true);
				setVisible(false);
			}
		});
	}
}
