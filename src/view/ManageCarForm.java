package view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Main;
import model.Car;

@SuppressWarnings("serial")
public class ManageCarForm extends JFrame {
	private JPanel contentPane;
	private JTextField reg_no;
	private JTextField make;
	private JTextField model;
	private JTextField rate;
	private JTable table;
	private JComboBox<String> comboBox;

	/**
	 * Create the frame.
	 */
	public ManageCarForm() {
		setTitle("Manage Car");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 602);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		updateTable();

		JLabel lblCar = new JLabel("Car");
		lblCar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCar.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCar.setBounds(397, 28, 168, 50);
		contentPane.add(lblCar);

		JLabel lblRegistration = new JLabel("Registration");
		lblRegistration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRegistration.setBounds(23, 123, 81, 30);
		contentPane.add(lblRegistration);

		reg_no = new JTextField();
		reg_no.setFont(new Font("Tahoma", Font.PLAIN, 14));
		reg_no.setBounds(114, 127, 177, 27);
		contentPane.add(reg_no);
		reg_no.setColumns(10);

		JLabel lblMake = new JLabel("Make");
		lblMake.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMake.setBounds(23, 173, 81, 30);
		contentPane.add(lblMake);
		
		make = new JTextField();
		make.setFont(new Font("Tahoma", Font.PLAIN, 14));
		make.setColumns(10);
		make.setBounds(114, 177, 177, 27);
		contentPane.add(make);

		JLabel lblModel = new JLabel("Model");
		lblModel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblModel.setBounds(23, 224, 81, 30);
		contentPane.add(lblModel);

		model = new JTextField();
		model.setFont(new Font("Tahoma", Font.PLAIN, 14));
		model.setColumns(10);
		model.setBounds(114, 228, 177, 27);
		contentPane.add(model);
		
		JLabel lblAvailable = new JLabel("Available");
		lblAvailable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAvailable.setBounds(23, 277, 81, 30);
		contentPane.add(lblAvailable);
		
		comboBox = new JComboBox<>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] { "YES", "NO" }));
		comboBox.setBounds(114, 277, 132, 30);
		contentPane.add(comboBox);
		
		JLabel lblRate = new JLabel("Rate");
		lblRate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRate.setBounds(23, 328, 81, 30);
		contentPane.add(lblRate);

		rate = new JTextField();
		rate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rate.setColumns(10);
		rate.setBounds(114, 332, 132, 26);
		contentPane.add(rate);

		JButton btnAdd = new JButton("Add");		
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.setBounds(19, 387, 113, 37);
		contentPane.add(btnAdd);

		JButton btnEdit = new JButton("Edit");		
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setBounds(178, 387, 113, 37);
		contentPane.add(btnEdit);

		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReset.setBounds(178, 447, 113, 37);
		contentPane.add(btnReset);

		JButton btnDelete = new JButton("Delete");		
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(19, 447, 113, 37);
		contentPane.add(btnDelete);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExit.setBounds(101, 501, 113, 37);
		contentPane.add(btnExit);

		JLabel lblListCars = new JLabel("List Of Cars");
		lblListCars.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblListCars.setHorizontalAlignment(SwingConstants.CENTER);
		lblListCars.setBounds(578, 100, 100, 30);
		contentPane.add(lblListCars);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(353, 140, 599, 386);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(table);

		/*
		 * Action listeners
		 * 
		 * Used anonymous inner class and lambda
		 */
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {				
				reg_no.setText(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
				make.setText(table.getModel().getValueAt(table.getSelectedRow(), 1).toString());
				model.setText(table.getModel().getValueAt(table.getSelectedRow(), 2).toString());
				comboBox.setEnabled(false);
				rate.setText(table.getModel().getValueAt(table.getSelectedRow(), 4).toString());							
			}			
		});
		
		// add button
		btnAdd.addActionListener(e -> {
			// assert to check if any field is empty
			assert make.getText().equals("") || model.getText().equals("") || rate.getText().equals("")
			|| reg_no.getText().equals("");
			
			if (make.getText().equals("") || model.getText().equals("") || rate.getText().equals("")
					|| reg_no.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Fields must not be empty");
			} else if (!isRateInDouble()) {
				JOptionPane.showMessageDialog(null, "Invalid rate.");
			} else {
				boolean isAvailable = Objects.equals(Objects.requireNonNull(comboBox.getSelectedItem()).toString(),
						"YES");
				Car car = new Car(reg_no.getText(), make.getText(), model.getText(), isAvailable,
						Double.parseDouble(rate.getText()));
				if (Main.database.addCarData(car)) {
					updateTable();
					resetFields();
					JOptionPane.showMessageDialog(null, "Car successfully added");					
				} else {
					JOptionPane.showMessageDialog(null, "Unable to add car");
				}
			}
		});				
		
		// edit button
		btnEdit.addActionListener(e -> {
			if (make.getText().equals("") || model.getText().equals("") || rate.getText().equals("")
					|| reg_no.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please fill up all fields");				
			} else if (!isRateInDouble()) {
				JOptionPane.showMessageDialog(null, "Invalid rate");
			} else {
				boolean isAvailable = Objects.equals(Objects.requireNonNull(comboBox.getSelectedItem()).toString(), "YES");
				Car car = new Car(reg_no.getText(), make.getText(), model.getText(), isAvailable,
						Double.parseDouble(rate.getText()));
				
				if (Main.database.modifyCarData(car)) {
					updateTable();
					resetFields();
					JOptionPane.showMessageDialog(null, "Car list updated successfully.");
				} else {
					JOptionPane.showConfirmDialog(null, "Unable to update car list.");
				}
			}
		});		
		
		// delete button
		btnDelete.addActionListener(e -> {			
			if (reg_no.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter registration number.");
			} else if (!carAvailable()) { // added boolean
				JOptionPane.showMessageDialog(null, "Car unavailable. Unable to delete.");
			}  else {				
				if (Main.database.deleteCarData(reg_no.getText())) { 
					updateTable();
					resetFields();
					JOptionPane.showMessageDialog(null, "Car deleted.");
				} else {
					JOptionPane.showMessageDialog(null, "Unable to delete car.");
				}				
			}			
		});

		// reset button
		btnReset.addActionListener(e -> {
			resetFields();
		});
		
		// exit button
		btnExit.addActionListener(e -> {
			setVisible(false);
			LoginForm.menuForm.setLocationRelativeTo(null);
			LoginForm.menuForm.setVisible(true);
		});
	}

	private boolean isRateInDouble() {
		try {
			Double.parseDouble(rate.getText());
			return true;
		} catch (NumberFormatException e) {			
			return false;
		}
	}
	
	private boolean carAvailable() {
		boolean isAvailable = Objects.equals(table.getModel().getValueAt(table.getSelectedRow(), 3).toString(), "YES");
		if (!isAvailable) {
			return false;
		} else {
			return isAvailable;
		}
	}

	public void updateTable() {
		table.setModel(new DefaultTableModel(Main.database.getCarData(),
				new String[] {"Registration", "Make", "Model", "Available", "Rate"}));
	}
	
	public void resetFields() {
		reg_no.setText("");
		make.setText("");
		model.setText("");
		comboBox.setEnabled(true);
		rate.setText("");
	}
}
