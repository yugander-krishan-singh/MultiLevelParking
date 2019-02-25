package org.parking.utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.parking.Vehicle;

public class ParkingUtils {
	public static List<Vehicle> filterVehicles(List<Vehicle> list, Predicate<Vehicle> predicate) {
		return list.stream().filter(predicate).collect(Collectors.toList());
	}	
}
