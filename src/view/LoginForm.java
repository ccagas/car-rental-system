package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.Main;

@SuppressWarnings("serial")
public class LoginForm extends JFrame {
	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	public static MenuForm menuForm;

	/*
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public LoginForm() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Car Rental Management System");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(69, 44, 433, 48);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(69, 95, 433, 288);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(100, 42, 107, 37);
		panel.add(lblNewLabel_1);

		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 12));
		username.setBounds(100, 83, 226, 28);
		panel.add(username);
		username.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(100, 121, 107, 37);
		panel.add(lblNewLabel_1_1);

		password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 12));
		password.setColumns(10);
		password.setBounds(100, 157, 226, 28);
		panel.add(password);

		JButton btnOk = new JButton("OK");
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOk.setBounds(44, 207, 107, 28);
		panel.add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(269, 207, 107, 28);
		panel.add(btnCancel);

		/*
		 * Action listeners for buttons
		 * 
		 * Used anonymous inner class and lambda
		 */

		// login
		btnOk.addActionListener(e -> {
			//assert to check if username or password is empty or not
			assert username.getText().equals("") || password.getText().equals("");
			
			if (username.getText().equals("") || password.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Any field is empty");
				return;
			}
			if (!Main.ADMIN_USERNAME.equalsIgnoreCase(username.getText())) {
				JOptionPane.showMessageDialog(null, "Username not found");
				return;
			}
			if (!Main.ADMIN_PASSWORD.equals(password.getText())) {
				JOptionPane.showMessageDialog(null, "Password is wrong");
				return;
			}
			setVisible(false);
			username.setText("");
			password.setText("");
			menuForm = new MenuForm();
			menuForm.setLocationRelativeTo(null);
			menuForm.setVisible(true);
		});

		// exit app
		btnCancel.addActionListener(e -> {
			System.exit(0);
		});
	}

}
