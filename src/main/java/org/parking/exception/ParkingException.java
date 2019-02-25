package org.parking.exception;

public abstract class ParkingException extends RuntimeException {

	String msg;
	
	public ParkingException(String msg) {
		super(msg);
	}
	
	public static class ParkingAvaliableException extends ParkingException{
		
		public ParkingAvaliableException(String msg) {
			super(msg);
		}		
	}
	
	public static class VehicleNotFoundException extends ParkingException{
		
		public VehicleNotFoundException(String msg) {
			super(msg);
		}
		
	}
}
