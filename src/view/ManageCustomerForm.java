package view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Main;
import model.Customer;

@SuppressWarnings("serial")
public class ManageCustomerForm extends JFrame {
	private JPanel contentPane;
	private JTextField id;
	private JTextField license;
	private JTextField name;
	private JTextArea address;
	private JTextField phone;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ManageCustomerForm() {
		setTitle("Manage Car");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 602);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();
//		table.setEnabled(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		updateTable();

		JLabel lblNewLabel = new JLabel("Customer");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(397, 28, 168, 50);
		contentPane.add(lblNewLabel);

		JLabel lblCustID = new JLabel("Customer ID");
		lblCustID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCustID.setBounds(23, 123, 81, 30);
		contentPane.add(lblCustID);

		id = new JTextField();
		id.setFont(new Font("Tahoma", Font.PLAIN, 14));
		id.setBounds(114, 127, 177, 27);
		contentPane.add(id);
		id.setColumns(10);

		JLabel lblLicense = new JLabel("License");
		lblLicense.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLicense.setBounds(23, 173, 81, 30);
		contentPane.add(lblLicense);
		
		license = new JTextField();
		license.setFont(new Font("Tahoma", Font.PLAIN, 14));
		license.setColumns(10);
		license.setBounds(114, 177, 177, 27);
		contentPane.add(license);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(23, 224, 81, 30);
		contentPane.add(lblName);
		
		name = new JTextField();
		name.setFont(new Font("Tahoma", Font.PLAIN, 14));
		name.setColumns(10);
		name.setBounds(114, 228, 177, 27);
		contentPane.add(name);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAddress.setBounds(23, 277, 81, 30);
		contentPane.add(lblAddress);

		address = new JTextArea();
		address.setBounds(114, 277, 177, 62);
		contentPane.add(address);

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPhone.setBounds(23, 345, 81, 30);
		contentPane.add(lblPhone);

		phone = new JTextField();
		phone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		phone.setColumns(10);
		phone.setBounds(114, 349, 177, 26);
		contentPane.add(phone);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.setBounds(19, 404, 113, 37);
		contentPane.add(btnAdd);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setBounds(178, 404, 113, 37);
		contentPane.add(btnEdit);

		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReset.setBounds(178, 464, 113, 37);
		contentPane.add(btnReset);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(19, 464, 113, 37);
		contentPane.add(btnDelete);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExit.setBounds(101, 518, 113, 37);
		contentPane.add(btnExit);

		JLabel lblCustList = new JLabel("List Of Customers");
		lblCustList.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustList.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustList.setBounds(578, 100, 130, 30);
		contentPane.add(lblCustList);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(353, 140, 599, 386);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(table);

		/*
		 * Action listeners
		 * 
		 * Used anonymous inner class and lambda
		 * 
		 */
		// table
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {				
				id.setText(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
				license.setText(table.getModel().getValueAt(table.getSelectedRow(), 1).toString());
				name.setText(table.getModel().getValueAt(table.getSelectedRow(), 2).toString());
				address.setText(table.getModel().getValueAt(table.getSelectedRow(), 3).toString());				
				phone.setText(table.getModel().getValueAt(table.getSelectedRow(), 4).toString());							
			}			
		});
		// add button
		btnAdd.addActionListener(e -> {
			if (id.getText().equals("") || name.getText().equals("") || license.getText().equals("")
					|| address.getText().equals("") || phone.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields must not be empty");
			} else if (!isIDInInt()) {
				JOptionPane.showConfirmDialog(null, "Please enter valid ID");
			} else {
				Customer customer = new Customer(Integer.parseInt(id.getText()),
						license.getText(), name.getText(), address.getText(), phone.getText());
				if (Main.database.addCustomerData(customer)) {
					updateTable();
					resetFields();
					JOptionPane.showMessageDialog(null, "Customer successfully added");
				} else {
					JOptionPane.showMessageDialog(null, "Customer not added");
				}
			}
		});
		
		// edit button
		btnEdit.addActionListener(e -> {
			if (id.getText().equals("") || name.getText().equals("") || license.getText().equals("")
					|| address.getText().equals("") || phone.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields must not be empty");				
			} else if (!isIDInInt()) {
				JOptionPane.showMessageDialog(null, "Please enter valid ID");
			} else {
				Customer customer = new Customer(Integer.parseInt(id.getText()),
						license.getText(), name.getText(), address.getText(), phone.getText());
				if (Main.database.modifyCustomerData(customer)) {
					updateTable();
					resetFields();
					JOptionPane.showMessageDialog(null, "Customer successfully updated");
				} else {
					JOptionPane.showMessageDialog(null, "Customer not updated");
				}
			}
		});
				
		// reset form
		btnReset.addActionListener(e -> {
			resetFields();
		});
		
		// delete button
		btnDelete.addActionListener(e -> {
			//assert to check id is entered or not
			assert id.getText().equals("");
			
			if (id.getText().equals("") || (!isIDInInt())) {
				JOptionPane.showMessageDialog(null, "Please enter valid ID");
			} else {
				if (Main.database.deleteCustomerData(Integer.parseInt(id.getText()))) {
					updateTable();
					resetFields();
					JOptionPane.showMessageDialog(null, "Customer successfully deleted");
				} else {
					JOptionPane.showMessageDialog(null, "Customer not deleted");
				}
			}
		});		

		// exit form
		btnExit.addActionListener(e -> {
			setVisible(false);
			LoginForm.menuForm.setLocationRelativeTo(null);
			LoginForm.menuForm.setVisible(true);
		});
	}
	
	public void resetFields() {
		id.setText("");
		name.setText("");
		address.setText("");
		license.setText("");
		phone.setText("");
	}

	public boolean isIDInInt() {
		try {
			Integer.parseInt(id.getText());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void updateTable() {
		table.setModel(new DefaultTableModel(Main.database.getCustomerData(),
				new String[] {"Customer ID", "License", "Name", "Address", "Phone"}));
	}
}
