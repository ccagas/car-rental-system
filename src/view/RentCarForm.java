package view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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

import com.toedter.calendar.JDateChooser;
import controller.CarComparison;
import controller.Main;
import model.Car;

@SuppressWarnings("serial")
public class RentCarForm extends JFrame {
	private JPanel contentPane;
	private JTextField rentID;
	private JTextField registration;
	private JTextField license;
	private JTable listCarsTable;
	private JTextField fee;
	private JTable rentedTable;
	private double rate = 0.0;
	private JTextField search;

	/**
	 * Create the frame.
	 */
	public RentCarForm() {
		setTitle("Rent");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRentCar = new JLabel("Rent Car");
		lblRentCar.setHorizontalAlignment(SwingConstants.CENTER);
		lblRentCar.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblRentCar.setBounds(397, 28, 168, 50);
		contentPane.add(lblRentCar);

		JLabel lblRentID = new JLabel("Rent ID");
		lblRentID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRentID.setBounds(23, 123, 81, 30);
		contentPane.add(lblRentID);

		rentID = new JTextField();
		rentID.setText("AUTO INCREMENT");
		rentID.setEditable(false);
		rentID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rentID.setBounds(114, 127, 177, 27);
		contentPane.add(rentID);
		rentID.setColumns(10);

		JLabel lblRegistration = new JLabel("Registration");
		lblRegistration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRegistration.setBounds(23, 173, 81, 30);
		contentPane.add(lblRegistration);

		registration = new JTextField();
		registration.setEditable(false);
		registration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		registration.setColumns(10);
		registration.setBounds(114, 177, 177, 27);
		contentPane.add(registration);

		JLabel lblLicense = new JLabel("License");
		lblLicense.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLicense.setBounds(23, 224, 81, 30);
		contentPane.add(lblLicense);

		license = new JTextField();
		license.setEditable(false);
		license.setFont(new Font("Tahoma", Font.PLAIN, 14));
		license.setColumns(10);
		license.setBounds(114, 228, 177, 27);
		contentPane.add(license);

		JLabel lblCustomer = new JLabel("Customer");
		lblCustomer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCustomer.setBounds(23, 277, 81, 30);
		contentPane.add(lblCustomer);

		JComboBox<String> comboBox = new JComboBox<>();		
		String[][] customers = Main.database.getCustomerData();
		String[] name = new String[customers.length];
		for (int i=0; i < customers.length; i++) {
			name[i] = customers[i][0] + "," + customers[i][2];
		}
		comboBox.setModel(new DefaultComboBoxModel<>(name));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox.setBounds(114, 277, 177, 30);
		contentPane.add(comboBox);

		license.setText(customers[comboBox.getSelectedIndex()][1]);
		
		comboBox.addActionListener(a -> license.setText(customers[comboBox.getSelectedIndex()][1]));
		
		JLabel lblPickup = new JLabel("Pickup Date");
		lblPickup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPickup.setBounds(23, 328, 81, 30);
		contentPane.add(lblPickup);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.setBounds(19, 498, 113, 37);
		contentPane.add(btnAdd);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setBounds(178, 498, 113, 37);
		contentPane.add(btnEdit);

		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReset.setBounds(19, 565, 113, 37);
		contentPane.add(btnReset);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExit.setBounds(178, 565, 113, 37);
		contentPane.add(btnExit);

		JLabel lblCarsList = new JLabel("List Of Cars");
		lblCarsList.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCarsList.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarsList.setBounds(578, 100, 100, 30);
		contentPane.add(lblCarsList);

		JScrollPane sPaneListCars = new JScrollPane();
		sPaneListCars.setBounds(353, 140, 599, 219);
		contentPane.add(sPaneListCars);

		listCarsTable = new JTable();		
		listCarsTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		updateCarsList();
		sPaneListCars.setViewportView(listCarsTable);

		JDateChooser pickupDate = new JDateChooser();
		
		pickupDate.setBounds(114, 328, 157, 30);
		contentPane.add(pickupDate);

		JDateChooser returnDate = new JDateChooser();
		returnDate.setBounds(114, 378, 157, 30);
		contentPane.add(returnDate);

		JLabel lblReturn = new JLabel("Return Date");
		lblReturn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReturn.setBounds(23, 378, 81, 30);
		contentPane.add(lblReturn);

		JLabel lblFee = new JLabel("Fee");
		lblFee.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFee.setBounds(23, 418, 81, 30);
		contentPane.add(lblFee);

		fee = new JTextField();
		fee.setEditable(false);
		fee.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fee.setColumns(10);
		fee.setBounds(114, 422, 177, 27);
		contentPane.add(fee);

		JScrollPane sPaneRented = new JScrollPane();
		sPaneRented.setBounds(353, 409, 599, 219);
		contentPane.add(sPaneRented);

		rentedTable = new JTable();			
		rentedTable.setFont(new Font("Tahoma", Font.PLAIN, 14));		
		updateRented();
		sPaneRented.setViewportView(rentedTable);

		JLabel lblRented = new JLabel("Rented");
		lblRented.setHorizontalAlignment(SwingConstants.CENTER);
		lblRented.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRented.setBounds(578, 369, 100, 30);
		contentPane.add(lblRented);

		JButton btnNewButton = new JButton("Sort");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(353, 103, 119, 30);
		contentPane.add(btnNewButton);

		JButton btnSearchByMake = new JButton("Search By Make");		
		btnSearchByMake.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSearchByMake.setBounds(824, 101, 129, 30);
		contentPane.add(btnSearchByMake);

		search = new JTextField();
		search.setBounds(688, 103, 126, 30);
		contentPane.add(search);
		search.setColumns(10);

		/*
		 * Listeners
		 *
		 * Used anonymous inner class and lambda		 * 
		 */
		// list cars table
		listCarsTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {				
				registration.setText(listCarsTable.getModel().getValueAt(listCarsTable.getSelectedRow(), 0).toString());
				rate = Double.parseDouble(listCarsTable.getModel().getValueAt(listCarsTable.getSelectedRow(), 4).toString());
			}			
		});
		
		// rented table
		rentedTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {				
				rentID.setText(rentedTable.getModel().getValueAt(rentedTable.getSelectedRow(), 0).toString());
				registration.setText(rentedTable.getModel().getValueAt(rentedTable.getSelectedRow(), 1).toString());
				license.setText(rentedTable.getModel().getValueAt(rentedTable.getSelectedRow(), 2).toString());
				fee.setText(rentedTable.getModel().getValueAt(rentedTable.getSelectedRow(), 5).toString());				
				pickupDate.setDate(Date.valueOf(LocalDate.parse(rentedTable.getModel().getValueAt(rentedTable.getSelectedRow(), 3).toString())));
				returnDate.setDate(Date.valueOf(LocalDate.parse(rentedTable.getModel().getValueAt(rentedTable.getSelectedRow(), 4).toString())));				
			}			
		});
		
		// sort button
		btnNewButton.addActionListener(e -> {
			List<Car> cars = Main.database.getCarDataList();
			
			// stream
			cars = cars.stream().filter(a -> a.isAvailable()).collect(Collectors.toList());
			Collections.sort(cars, new CarComparison()); // instantiate CarComparison class to sort stream of car by make
			String[][] data = new String[cars.size()][5];
			int j = 0;
			for (int i=0; i<cars.size(); i++) {
				if (cars.get(i).isAvailable()) {
					data[j][0] = cars.get(i).getRegistration();
					data[j][1] = cars.get(i).getMake();
					data[j][2] = cars.get(i).getModel();
					data[j][3] = cars.get(i).isAvailable() ? "YES" : "NO";
					data[j][4] = String.valueOf(cars.get(i).getRate());
					j++;
				}
			}			
			listCarsTable.setModel(new DefaultTableModel(data,
					new String[] {"Registration", "Make", "Model", "Available", "Rate"}));
		});
		
		// search by make button
		btnSearchByMake.addActionListener(e -> {
			String[][] data = Main.database.getAvailableCarData();
			if (search.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Field must not be empty");
			} else {
				ArrayList<String[]> newData = new ArrayList<String[]>();
				for (int i=0; i < data.length; i++) {
					if (search.getText().equalsIgnoreCase(data[i][1])) {
						String[] s = {
								data[i][0], data[i][1], data[i][2], data[i][3], data[i][4]
						};
						newData.add(s);
					}
				}
				if (newData.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No data found");
					return;
				}
				data = new String[newData.size()][5];
				int i = 0;
				for (String[] string : newData) {
					data[i++] = string;
				}
				listCarsTable.setModel(new DefaultTableModel(data,
						new String[] {"Registration", "Make", "Model", "Available", "Rate"}));
			}
		});
		
		// add button
		btnAdd.addActionListener(e -> {
			if (registration.getText().equals("") ||
				pickupDate.getDate() == null|| returnDate.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Fields must not be empty");			
			} else {
			LocalDate date1 = pickupDate.getDate().toInstant().atZone(ZoneId.of("Canada/Central")).toLocalDate();
			LocalDate date2 = returnDate.getDate().toInstant().atZone(ZoneId.of("Canada/Central")).toLocalDate();
			fee.setText((ChronoUnit.DAYS.between(date1, date2) * rate) + "");
			
			if (date1.isBefore(LocalDate.now())) {
				JOptionPane.showMessageDialog(null, "Please enter valid pickup date");
			} else if (date2.isEqual(date1) || date2.isBefore(date1)) {
				JOptionPane.showMessageDialog(null, "Please enter valid return date");
			} else {
				if (Main.database.addRentCar
						(registration.getText(), license.getText(), date1, date2, Double.parseDouble(fee.getText()))) {
					updateRented();
					updateCarsList();
					resetFields();
					pickupDate.setDate(null);
					returnDate.setDate(null);
					JOptionPane.showMessageDialog(null, "Car Rented list udpated");
				} else {
					JOptionPane.showMessageDialog(null, "Unable to rent car");
				}	
			}						
		}
		});
		
		// edit button
		btnEdit.addActionListener(e -> {
			if (registration.getText().equals("") || rentID.getText().equals("") ||
					pickupDate.getDate() == null|| returnDate.getDate() == null) {
				JOptionPane.showMessageDialog(null, "Fields must not be empty");
			} else if (!isIDInt()) {
				JOptionPane.showMessageDialog(null, "Please enter valid ID");
			} else {
				LocalDate date1 = pickupDate.getDate().toInstant().atZone(ZoneId.of("Canada/Central")).toLocalDate();
				LocalDate date2 = returnDate.getDate().toInstant().atZone(ZoneId.of("Canada/Central")).toLocalDate();
				fee.setText((ChronoUnit.DAYS.between(date1, date2) * Main.database.getRate(registration.getText())) + "");
				
				if (date1.isBefore(LocalDate.now())) {
					JOptionPane.showMessageDialog(null, "Please enter valid pickup date");
				} else if (date2.isEqual(date1) || date2.isBefore(date1)) {
					JOptionPane.showMessageDialog(null, "Please enter valid return date");
				} else {
					if (Main.database.editRentCar(				
						Integer.parseInt(rentID.getText()), registration.getText(), license.getText(),
								date1, date2, Double.parseDouble(fee.getText()))) {
					updateRented();
					updateCarsList();
					resetFields();
					pickupDate.setDate(null);
					returnDate.setDate(null);
					JOptionPane.showMessageDialog(null, "Car Rented list updated");	
					} else {
					JOptionPane.showMessageDialog(null, "Unable to rent car");
					}
				}
			}
		});		

		// reset button
		btnReset.addActionListener(e -> {
			resetFields();
			pickupDate.setDate(null);
			returnDate.setDate(null);
		});

		// exit button
		btnExit.addActionListener(e -> {
			setVisible(false);
			LoginForm.menuForm.setVisible(true);
		});

	}

	public void updateCarsList() {
		listCarsTable.setModel(new DefaultTableModel(Main.database.getAvailableCarData(),
				new String[] {"Registration", "Make", "Model", "Available", "Rate"}));
	}

	public void updateRented() {
		rentedTable.setModel(new DefaultTableModel(Main.database.getOnRentCarData(),
				new String[] {"RentID", "Registration", "Customer", "Pickup Date", "Due Date", "Fee"}));
		rentedTable.getColumnModel().getColumn(2).setPreferredWidth(100);
	}

	public boolean isIDInt() {
		try {
			Integer.parseInt(rentID.getText());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public void resetFields() {
//		rentID.setText("");
//		license.setText("");
		registration.setText("");
		fee.setText("");		
	}

}
