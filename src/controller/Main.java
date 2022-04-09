package controller;

import view.LoginForm;
import java.awt.EventQueue;

public class Main {
	public static Database database;
	public static LoginForm frame;
	public static final String ADMIN_USERNAME = "admin";
	public static final String ADMIN_PASSWORD = "admin123";

	/**
	 * Launch the application.
	 * 
	 */
	public static void main(String[] args) {
		database = new Database();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LoginForm();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
