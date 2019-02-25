package org.parking;

import java.time.LocalDateTime;

public class Ticket {

	private String regNo;
	private LocalDateTime parkingTime;
	
	public Ticket() {
		
	}

	public Ticket(String regNo, LocalDateTime time) {
		this.regNo = regNo;
		this.parkingTime = time;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public LocalDateTime getParkingTime() {
		return parkingTime;
	}

	public void setParkingTime(LocalDateTime parkingTime) {
		this.parkingTime = parkingTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parkingTime == null) ? 0 : parkingTime.hashCode());
		result = prime * result + ((regNo == null) ? 0 : regNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (parkingTime == null) {
			if (other.parkingTime != null)
				return false;
		} else if (!parkingTime.equals(other.parkingTime))
			return false;
		if (regNo == null) {
			if (other.regNo != null)
				return false;
		} else if (!regNo.equals(other.regNo))
			return false;
		return true;
	}

}
