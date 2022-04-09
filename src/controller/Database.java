package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import model.Car;
import model.Customer;

public class Database {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet result = null;
	private static final String URL = "jdbc:mysql://localhost/";
	private static final String DATABASE = "crm";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";
	private static final String PATH = "rent_cars.txt";

	public Database()  {	
		try {
			conn = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);				
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}

	/*
	 * Handle Car Data
	 */
	public List<Car> getCarDataList() {
		List<Car> cars = new ArrayList<Car>();
		try {
			ps = conn.prepareStatement("SELECT * FROM car");
			result = ps.executeQuery();
			while (result.next()) {
				cars.add(new Car(result.getString(1), result.getString(2), result.getString(3),
						result.getString(4).equals("YES"), result.getDouble(5)));
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
		} 
		return cars;		
	}

	public String[][] getCarData() {
		List<Car> cars = getCarDataList();
		String[][] data = new String[cars.size()][5];
		for (int i = 0; i < cars.size(); i++) {
			data[i][0] = cars.get(i).getRegistration();
			data[i][1] = cars.get(i).getMake();
			data[i][2] = cars.get(i).getModel();
			data[i][3] = cars.get(i).isAvailable() ? "YES" : "NO";
			data[i][4] = String.valueOf(cars.get(i).getRate());
		}
		return data;
	}

	public String[][] getAvailableCarData() {
		List<Car> cars = getCarDataList();

		// stream to get only available cars
		cars = cars.stream().filter(a -> a.isAvailable()).collect(Collectors.toList());

		String[][] data = new String[cars.size()][5];
		 int j = 0;
		for (int i = 0; i < cars.size(); i++) {
			if (cars.get(i).isAvailable()) {
				data[j][0] = cars.get(i).getRegistration();
				data[j][1] = cars.get(i).getMake();
				data[j][2] = cars.get(i).getModel();
				data[j][3] = cars.get(i).isAvailable() ? "YES" : "NO";
				data[j][4] = String.valueOf(cars.get(i).getRate());
				j++;
			}
		}
		return data;
	}

	public boolean addCarData(Car car) {
		try {
			ps = conn.prepareStatement("INSERT IGNORE INTO car VALUES (?,?,?,?,?)");
			ps.setString(1, car.getRegistration());
			ps.setString(2, car.getMake());
			ps.setString(3, car.getModel());
			ps.setString(4, car.isAvailable() ? "YES" : "NO");
			ps.setDouble(5, car.getRate());

			int a = ps.executeUpdate();
			if (a > 0) {
				return true;
			}

		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return false;
	}

	public boolean modifyCarData(Car car) {
		return deleteCarData(car.getRegistration()) ? addCarData(car) : false;
	}

	public boolean deleteCarData(String reg_no) {
		try {
			ps = conn.prepareStatement("DELETE FROM car WHERE reg_no = '" + reg_no + "'" );
			int a = ps.executeUpdate();
			if (a > 0) {
				return true;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Handle Customer Data
	 */
	public ArrayList<Customer> getCustomerDataList() {
		ArrayList<Customer> customers = new ArrayList<>();
		try {
			ps = conn.prepareStatement("SELECT * FROM customer");
			result = ps.executeQuery();
		
			while(result.next()) {
				customers.add(new Customer(result.getInt(1), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return customers;		
	}
	
	public String[][] getCustomerData() {
		ArrayList<Customer> customers = getCustomerDataList();
		String[][] data = new String[customers.size()][5];
		
		for (int i=0; i < customers.size(); i++) {
			data[i][0] = String.valueOf(customers.get(i).getCustomerID());
			data[i][1] = customers.get(i).getLicense();
			data[i][2] = customers.get(i).getName();
			data[i][3] = customers.get(i).getAddress();
			data[i][4] = customers.get(i).getPhoneNumber();
		}
		return data;
	}
	
	public boolean addCustomerData(Customer customer ) {
		try {
			ps = conn.prepareStatement("INSERT IGNORE INTO customer VALUES (?,?,?,?,?)");
			ps.setInt(1, customer.getCustomerID());
			ps.setString(2, customer.getLicense());
			ps.setString(3, customer.getName());
			ps.setString(4, customer.getAddress());
			ps.setString(5, customer.getPhoneNumber());
			
			int a = ps.executeUpdate();
			
			if (a > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	public boolean modifyCustomerData(Customer customer ) {
		return deleteCustomerData(customer.getCustomerID()) && addCustomerData(customer);
	}
	
	public boolean deleteCustomerData(int id) {
		try {
			ps = conn.prepareStatement("DELETE FROM customer WHERE id = '" + id + "'");
			int a = ps.executeUpdate();
			if (a > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Handle Rent and Return
	 */
	public int getCustomerID(String license) {
		try {
			ps = conn.prepareStatement("SELECT id FROM customer WHERE license = '" + license + "'");
			result = ps.executeQuery();
			
			if (result.next()) {
				return result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean addRentCar(String registration, String license, LocalDate pickup, LocalDate returnD, double fee) {
		synchronized (this) { // concurrency
			int customerID = getCustomerID(license);		
			try {
				ps = conn.prepareStatement(
						"INSERT IGNORE INTO car_customer (customer_id, reg_id, pickup_date, return_date, fee, fine,"
						+ "days_overdue, returned) VALUES (?,?,?,?,?,?,?,?)");
				ps.setInt(1, customerID);
				ps.setString(2, registration);
				ps.setDate(3, Date.valueOf(pickup));
				ps.setDate(4, Date.valueOf(returnD));
				ps.setDouble(5, fee);
				ps.setDouble(6, 0);
				ps.setInt(7, 0);
				ps.setInt(8, 0);
				
				int a = ps.executeUpdate();
				if (a > 0) {
					ps = conn.prepareStatement("UPDATE car SET available = 'NO' WHERE reg_no = '" + registration + "'");
					ps.executeUpdate();
					
					File file = new File(PATH);
					// write to file
					FileWriter writer = new FileWriter(PATH, false);
					
					if (!file.exists()) {
						file.createNewFile();
					}
									
					String[][] string = getOnRentCarData();
					for (String[] strings : string) {
						for (String string2 : strings) {
							writer.write(string2 + " ");
						}					
						writer.write("\n");				
					}						
					writer.flush();
					writer.close();
					
					return true;
				}
				
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}		
			return false;	
		}
		
	}
	
	public boolean editRentCar(int rentID, String registration, String license, LocalDate pickup, LocalDate returnD, double fee) {
		int id = getCustomerID(license);		
		try {
			ps = conn.prepareStatement
					("UPDATE car_customer SET reg_id = '" + registration + "'" 
					+ ", customer_id = '" + id + "', pickup_date = '" + pickup + "', "
					+ "return_date = '" + returnD + "', fee = '" + fee + "' WHERE rent_id = '" + rentID + "'");
//			return ps.executeUpdate() > 0;
			int a = ps.executeUpdate();
			if(a > 0) {
				File file = new File(PATH);
				// write to file
				FileWriter writer = new FileWriter(PATH, false);
				
				if (!file.exists()) {
					file.createNewFile();
				}
								
				String[][] string = getOnRentCarData();
				for (String[] strings : string) {
					for (String string2 : strings) {
						writer.write(string2 + " ");
					}					
					writer.write("\n");				
				}						
				writer.flush();
				writer.close();
				
				return true;
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}			
		return false;
	}
	
	public boolean editRentCar(int rentID, String registration, int dueDays, double fine) {
		try {
			ps = conn.prepareStatement("UPDATE car_customer SET fine = '" 
					+ fine + "', days_overdue = '" + dueDays + "', returned = TRUE WHERE rent_id = '" + rentID + "'");
			int a = ps.executeUpdate();			
		
			if (a > 0) {
				ps = conn.prepareStatement("UPDATE car SET available = 'YES' WHERE reg_no = '" + registration + "'" );
				return ps.executeUpdate() > 0;					
			}
			
			try {
				// update text file
				File file = new File(PATH);
				FileWriter writer = new FileWriter(file, false);

				if (file.exists() && file.isFile()) {
					file.delete();
				}
				file.createNewFile();
				
				String[][] string = getOnRentCarData();
				for (String[] strings : string) {
					for (String string2 : strings) {
						writer.write(string2 + " ");
					}					
					writer.write("\n");				
				}						
				writer.flush();
				writer.close();
				
				return true;	
			} catch (IOException e) {
				e.printStackTrace();
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	public String[][] getOnRentCarData() {
		int size = 0;		
		try {
			ps = conn.prepareStatement("SELECT * FROM car_customer WHERE returned = FALSE");
			result = ps.executeQuery();			
			while (result.next()) {
				size++;
			}
			if (size == 0) {
				return null;
			}
			
			String[][] data = new String[size][6];
			int j = 0;
			
			ps = conn.prepareStatement("SELECT car_customer.*, license FROM car_customer JOIN customer "
					+ "ON (car_customer.customer_id = customer.id) WHERE returned = FALSE");
			result = ps.executeQuery();
			
			while (result.next()) {
				data[j][0] = String.valueOf(result.getInt(1));				
				data[j][2] = result.getString(10);
				data[j][1] = result.getString(3);				
				data[j][3] = result.getDate(4).toLocalDate().toString();
				data[j][4] = result.getDate(5).toLocalDate().toString();
				data[j][5] = String.valueOf(result.getDouble(6));
				j++;
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	public String[][] getReturnedCarData() {
		int size = 0;		
		try {
			ps = conn.prepareStatement("SELECT * FROM car_customer WHERE returned = TRUE");
			result = ps.executeQuery();
			
			while (result.next()) {
				size++;
			}
			if (size == 0) {
				return null;
			}
			
			String[][] data = new String[size][6];
			int j = 0;
			
			ps = conn.prepareStatement("SELECT * FROM car_customer WHERE returned = TRUE");
			result = ps.executeQuery();
			
			while (result.next()) {
				data[j][0] = String.valueOf(result.getInt(1));
				data[j][1] = result.getString(3);
				data[j][2] = String.valueOf(result.getInt(2));				
				data[j][3] = result.getDate(4).toLocalDate().toString();
				data[j][4] = String.valueOf(result.getInt(8));
				data[j][5] = String.valueOf(result.getDouble(7));
				j++;
			}			
			return data;			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public double getRate(String registration) {
		try {
			ps = conn.prepareStatement("SELECT rate FROM car WHERE reg_no = '" + registration + "'");
			result = ps.executeQuery();
			
			if (result.next()) {
				return result.getDouble(1);
			}
		} catch (SQLException e) {
			 e.printStackTrace();
		}
		return 0;
	}

}
