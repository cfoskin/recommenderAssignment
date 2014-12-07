package app;

import java.awt.EventQueue;

import javax.swing.JFrame;

import view.AccountView;

/**
 * @author colum foskin
 * this class runs the application
 *
 */
public class Main {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountView window = new AccountView();
					window.frmFilmRecommenderSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
