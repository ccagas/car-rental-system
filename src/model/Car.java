package model;

public class Car {
	private String registration;
	private String make;
	private String model;
	private boolean isAvailable;
	private double rate;

	public Car(String registration, String make, String model, boolean isAvailable, double rate) {
		this.registration = registration;
		this.make = make;
		this.model = model;
		this.isAvailable = isAvailable;
		this.rate = rate;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean available) {
		isAvailable = available;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}
