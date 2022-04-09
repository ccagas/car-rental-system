package view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import javax.swing.JButton;
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
import com.toedter.calendar.JTextFieldDateEditor;

import controller.Main;

@SuppressWarnings("serial")
public class ReturnCarForm extends JFrame {
	private JPanel contentPane;
	private JTextField rentID;
	private JTextField registration;
	private JTable onRentTable;
	private JTextField fine;
	private JTable returnedCarsTable;
	private JTextField days;
	private LocalDate due;
	private LocalDate today;
	private LocalDate pickup;
	private int daysOverDue;
	/**
	 * Create the frame.
	 */
	public ReturnCarForm() {
		setTitle("Rent");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRentCar = new JLabel("Return Car");
		lblRentCar.setHorizontalAlignment(SwingConstants.CENTER);
		lblRentCar.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblRentCar.setBounds(397, 28, 168, 50);
		contentPane.add(lblRentCar);

		JLabel lblRentID = new JLabel("Rent ID");
		lblRentID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRentID.setBounds(23, 123, 81, 30);
		contentPane.add(lblRentID);

		rentID = new JTextField();
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
	
		JLabel lblReturnDate = new JLabel("Return Date");
		lblReturnDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReturnDate.setBounds(23, 224, 81, 30);
		contentPane.add(lblReturnDate);
		
		JDateChooser returnDate = new JDateChooser();
//		returnDate.setCalendar(Calendar.getInstance()); // display today's date		
		JTextFieldDateEditor txtFieldDate = (JTextFieldDateEditor) returnDate.getDateEditor();
		txtFieldDate.setEditable(false);
		returnDate.setBounds(114, 228, 157, 30);
		contentPane.add(returnDate);
		
		JLabel lblDaysOverdue = new JLabel("Days Overdue");
		lblDaysOverdue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDaysOverdue.setBounds(23, 277, 96, 31);
		contentPane.add(lblDaysOverdue);

		days = new JTextField();
		days.setEditable(false);
		days.setFont(new Font("Tahoma", Font.PLAIN, 14));
		days.setColumns(10);
		days.setBounds(129, 277, 177, 27);
		contentPane.add(days);
		
		JLabel lblFine = new JLabel("Fine");
		lblFine.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFine.setBounds(23, 328, 96, 31);
		contentPane.add(lblFine);

		fine = new JTextField();
		fine.setEditable(false);
		fine.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fine.setColumns(10);
		fine.setBounds(114, 328, 177, 27);
		contentPane.add(fine);

		JButton btnReturn = new JButton("Return");
		btnReturn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReturn.setBounds(19, 498, 113, 37);
		contentPane.add(btnReturn);

		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExit.setBounds(178, 498, 113, 37);
		contentPane.add(btnExit);

		JLabel lblOnRent = new JLabel("Cars on Rent");
		lblOnRent.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblOnRent.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnRent.setBounds(578, 100, 100, 30);
		contentPane.add(lblOnRent);

		JScrollPane sPaneOnRent = new JScrollPane();
		sPaneOnRent.setBounds(353, 140, 599, 219);
		contentPane.add(sPaneOnRent);

		onRentTable = new JTable();		
		onRentTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		updateOnRentTable();
		sPaneOnRent.setViewportView(onRentTable);
		
		JLabel lblReturnedCar = new JLabel("Returned Cars");
		lblReturnedCar.setHorizontalAlignment(SwingConstants.CENTER);
		lblReturnedCar.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblReturnedCar.setBounds(578, 369, 100, 30);
		contentPane.add(lblReturnedCar);

		JScrollPane sPaneReturned = new JScrollPane();
		sPaneReturned.setBounds(353, 409, 599, 219);
		contentPane.add(sPaneReturned);
		
		returnedCarsTable = new JTable();
		returnedCarsTable.setFont(new Font("Tahoma", Font.PLAIN, 14));		
		updateReturnedTable();
		sPaneReturned.setViewportView(returnedCarsTable);
				
		/*
		 * Action listeners
		 * Used anonymous inner class and lambda
		 * 
		 */
		// on rent table
		onRentTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				
				rentID.setText(onRentTable.getModel().getValueAt(onRentTable.getSelectedRow(), 0).toString());
				registration.setText(onRentTable.getModel().getValueAt(onRentTable.getSelectedRow(), 1).toString());	
				pickup = LocalDate.parse(onRentTable.getModel().getValueAt(onRentTable.getSelectedRow(), 3).toString());
				due = LocalDate.parse(onRentTable.getModel().getValueAt(onRentTable.getSelectedRow(), 4).toString());
			}
		});
		
		// return date
		returnDate.addPropertyChangeListener(e -> {			
			if (due != null) {
				daysOverDue = (int) ChronoUnit.DAYS.between(due, returnDate.getDate().toInstant().atZone(ZoneId.of("Canada/Central")).toLocalDate());
				if (daysOverDue >= 0) {
					days.setText(daysOverDue + "");				
					fine.setText(10 * daysOverDue + "");	
				} else {
					days.setText(0 + "");				
					fine.setText(0.0 + "");												
				} 				
			}
		});

		// return rental car
		btnReturn.addActionListener(e -> {			
			today = LocalDate.now(ZoneId.of("Canada/Central"));
						
			if (registration.getText().equals("") || rentID.getText().equals("")
					|| returnDate == null || txtFieldDate.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Fields must not be empty");
			} else if (returnDate.getDate().toInstant().atZone(ZoneId.of("Canada/Central")).toLocalDate().isBefore(today)
					|| returnDate.getDate().toInstant().atZone(ZoneId.of("Canada/Central")).toLocalDate().isAfter(today)
					|| returnDate.getDate().toInstant().atZone(ZoneId.of("Canada/Central")).toLocalDate().isBefore(pickup))					
			{
				JOptionPane.showMessageDialog(null, "Unable to return car. Invalid date.");
			} else {				
				if (Main.database.editRentCar(Integer.parseInt(rentID.getText()), registration.getText(), 
						Integer.parseInt(days.getText()), Double.parseDouble(fine.getText()))) {
					updateOnRentTable();
					updateReturnedTable();
					JOptionPane.showMessageDialog(null, "Car returned.");					
				} else {
					JOptionPane.showMessageDialog(null, "Unable to return car.");
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

	public void updateOnRentTable() {
		onRentTable.setModel(new DefaultTableModel(Main.database.getOnRentCarData(),
				new String[] {"Rent ID", "Registration", "Customer ID", "Pickup", "Due Date", "Fee"}));
		onRentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
	}

	public void updateReturnedTable() {
		returnedCarsTable.setModel(new DefaultTableModel(Main.database.getReturnedCarData(),
				new String[] {"Rent ID", "Registration", "Customer ID", "Pickup", "Days Overdue", "Fine"}));
		returnedCarsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
	}
}
