package org.parking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.parking.exception.ParkingException;
import org.parking.utils.ParkingUtils;

public class ParkingService {
	
	public ParkingService() {
		
	}

	public enum Level {
		level1(1), level2(1), level3(1);

		int slots;

		private Level(int slots) {
			this.slots = slots;
		}

		public static int getSlots(Level l) {
			return l.slots;
		}
	}
	/*
	 * public static final int noOfLevels = 3; public static final int totalSlots =
	 * 100;
	 */

	public static Map<Level, List<Vehicle>> map = new LinkedHashMap<>();

	static {
		map.put(Level.level1, new ArrayList<>());
		map.put(Level.level2, new ArrayList<>());
		map.put(Level.level3, new ArrayList<>());
	}

	public boolean parkVehicle(Vehicle vehicle) {
		/*
		 * TODO add validations
		 */
		vehicle.setTicket(new Ticket(vehicle.getRegNo(), LocalDateTime.now()));
		boolean slotsAvailable = false;
		Set<Level> set = map.keySet();
		for (Level l : set) {
			int slots = Level.getSlots(l);
			if (map.get(l).size() < slots) {
				map.get(l).add(vehicle);
				slotsAvailable = true;
				break;
			}
		}

		if (!slotsAvailable) {
			//throw new ParkingException.ParkingAvaliableException("slots not available");
			return false;
		}
		return true;
	}

	public boolean unParkVehicle(Vehicle vehicle) {
		System.out.println("Inside");
		boolean result = true;
		/*
		 * TODO add validations
		 */
		for (Map.Entry<Level, List<Vehicle>> entry : map.entrySet()) {
			List<Vehicle> list = entry.getValue();
			System.out.println("Inside for loop");
			if (!list.remove(vehicle)) {
				//throw new ParkingException.VehicleNotFoundException("Vehcile cannot be unparked as it doesn't exist");
				result = false;
				break;
			}		
		}
		System.out.println("Outside for loop");
		return result;
	}

	public Vehicle getVehicleByRegNo(String regNo) {
		Vehicle reqVehicle = null;
		for (Map.Entry<Level, List<Vehicle>> entry : map.entrySet()) {
			List<Vehicle> list = entry.getValue();
			List<Vehicle> reqVehicles = list.stream().filter(vehicle -> vehicle.getRegNo().equalsIgnoreCase(regNo))
					.collect(Collectors.toList());
			if (reqVehicles.size() > 0) {
				return reqVehicles.get(0);
			}
		}
		if (reqVehicle == null) {
			//throw new ParkingException.VehicleNotFoundException("Registration Number doesn't exist");
		}
		return reqVehicle;
	}

	public List<Vehicle> getAllVehiclesOfAColor(String color) {
		List<Vehicle> allVehiclesOfAColor = new ArrayList<>();
		for (Map.Entry<Level, List<Vehicle>> entry : map.entrySet()) {
			List<Vehicle> list = entry.getValue();
			List<Vehicle> vehicles = ParkingUtils.filterVehicles(list, x -> x.getColor().equalsIgnoreCase(color));
			allVehiclesOfAColor.addAll(vehicles);
		}
		return allVehiclesOfAColor;
	}

	public List<Vehicle> getAllVehiclesByType(String type) {
		/*
		 * TODO validate the type
		 */
		List<Vehicle> allVehiclesOfAType = new ArrayList<>();
		for (Map.Entry<Level, List<Vehicle>> entry : map.entrySet()) {

			List<Vehicle> list = entry.getValue();
			List<Vehicle> vehicles = ParkingUtils.filterVehicles(list, x -> x.getType().equalsIgnoreCase(type));

			// List<Vehicle> vehicles =
			// list.stream().filter(v->v.getType().equalsIgnoreCase(type)).collect(Collectors.toList());
			allVehiclesOfAType.addAll(vehicles);
		}
		return allVehiclesOfAType;
	}

	public List<Vehicle> getAllVehiclesInAGivenDuration(LocalDateTime startTime, LocalDateTime endTime) {
		if (startTime.isAfter(endTime))
			throw new RuntimeException("startTime should be before endTime");
		List<Vehicle> allVehicles = new ArrayList<>();

		for (Map.Entry<Level, List<Vehicle>> entry : map.entrySet()) {
			List<Vehicle> list = entry.getValue();
			List<Vehicle> vehicles = list.stream().filter(v -> v.getTicket().getParkingTime().isAfter(startTime)
					&& v.getTicket().getParkingTime().isBefore(endTime)).collect(Collectors.toList());
			allVehicles.addAll(vehicles);
		}
		return allVehicles;
	}

	public static void main(String[] args) {
		Ticket t1 = new Ticket("r1", LocalDateTime.now());
		ParkingService park = new ParkingService();
		park.parkVehicle(new Vehicle("c1", "r1", "t1", t1));

		Ticket t2 = new Ticket("r2", LocalDateTime.now());
		Vehicle v2 = new Vehicle("c2", "r2", "t2", t2);
		park.parkVehicle(v2);

		Ticket t3 = new Ticket("r1", LocalDateTime.now());
		Vehicle v3 = new Vehicle("c1", "r1", "t1", t3);
		park.parkVehicle(v3);

		System.out.println(map);
		// park.unParkVehicle(v2);
		// System.out.println(map);

		Vehicle v = park.getVehicleByRegNo("r2");
		System.out.println(v);

		List<Vehicle> listOfVwithColor = park.getAllVehiclesOfAColor("c1");
		System.out.println(listOfVwithColor);

		List<Vehicle> listOfVwithType = park.getAllVehiclesByType("t1");
		System.out.println(listOfVwithType);

		System.out.println(LocalDateTime.now());
	}

}
