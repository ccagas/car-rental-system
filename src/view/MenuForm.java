package view;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MenuForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MenuForm() {
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 515, 508);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCarMgmt = new JLabel("Car Rental Management System");
		lblCarMgmt.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCarMgmt.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarMgmt.setBounds(41, 35, 425, 43);
		contentPane.add(lblCarMgmt);

		JButton btnManageCar = new JButton("Manage Car");
		btnManageCar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnManageCar.setBounds(164, 101, 151, 54);
		contentPane.add(btnManageCar);

		JButton btnManageCustomer = new JButton("Manage Customer");
		btnManageCustomer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnManageCustomer.setBounds(164, 175, 151, 54);
		contentPane.add(btnManageCustomer);

		JButton btnRent = new JButton("Rent");
		btnRent.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRent.setBounds(164, 249, 151, 54);
		contentPane.add(btnRent);

		JButton btnReturn = new JButton("Return");
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnReturn.setBounds(164, 320, 151, 54);
		contentPane.add(btnReturn);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogout.setBounds(164, 395, 151, 54);
		contentPane.add(btnLogout);

		/*
		 * Action listeners
		 * 
		 * Used anonymous inner class and lambda
		 */

		// open Manage Car Form
		btnManageCar.addActionListener(e -> {
			setVisible(false);
			ManageCarForm carForm = new ManageCarForm();
			carForm.setLocationRelativeTo(null);
			carForm.setVisible(true);
		});

		// open Manage Customer Form
		btnManageCustomer.addActionListener(e -> {
			setVisible(false);
			ManageCustomerForm custForm = new ManageCustomerForm();
			custForm.setLocationRelativeTo(null);
			custForm.setVisible(true);
		});

		// open Rent Car Form
		btnRent.addActionListener(e -> {
			setVisible(false);
			RentCarForm rentForm = new RentCarForm();
			rentForm.setLocationRelativeTo(null);
			rentForm.setVisible(true);
		});

		// return rental car
		btnReturn.addActionListener(e -> {
			setVisible(false);
			// open Return Form
			ReturnCarForm returnForm = new ReturnCarForm();
			returnForm.setLocationRelativeTo(null);
			returnForm.setVisible(true);
		});

		// exit app
		btnLogout.addActionListener(e -> {
			System.exit(0);
		});
	}
}
