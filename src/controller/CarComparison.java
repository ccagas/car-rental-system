package controller;

import java.util.Comparator;

import model.Car;

/*
 * class implementing Comparator interface to 
 * compare car make for sorting
 */
public class CarComparison implements Comparator<Car>{

	@Override
	public int compare(Car o1, Car o2) {
		return o1.getMake().compareTo(o2.getMake());
	}	
}
