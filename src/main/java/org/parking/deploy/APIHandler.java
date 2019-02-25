package org.parking.deploy;

import java.io.IOException;
import java.util.List;

import org.parking.ParkingService;
import org.parking.Ticket;
import org.parking.Vehicle;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
//

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class APIHandler {

	public static void park(RoutingContext context)  {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		Vehicle v = null;
		try {
			v = objectMapper.readValue(context.getBodyAsString(), Vehicle.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ParkingService park = new ParkingService();
		if(park.parkVehicle(v)) {	
			try {
				context.response().setStatusCode(200).putHeader("content-type", "text/html").end(objectMapper.writeValueAsString(v.getTicket()));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}else {
			context.response().setStatusCode(400).putHeader("content-type", "text/html").end("Slots not available");
		}
	}

	public static void unPark(RoutingContext context) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		Vehicle v = null;
		try {
			v = objectMapper.readValue(context.getBodyAsString(), Vehicle.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ParkingService park = new ParkingService();
		boolean res = park.unParkVehicle(v);
		if(res) {
			context.response().setStatusCode(200).putHeader("content-type", "text/html").end("The vehicle has been unparked");
		}else {
			context.response().setStatusCode(400);
		}
	}

	public static void getVehcilesByColor(RoutingContext context) {
		String color = context.request().getParam("color");
		ObjectMapper objectMapper = new ObjectMapper();
		ParkingService park = new ParkingService();
		List<Vehicle> getVehicle = park.getAllVehiclesOfAColor(color);
		if(getVehicle == null) {
			context.response().setStatusCode(400);
		}else{
			try {
				context.response().setStatusCode(200).putHeader("content-type", "text/html").end(objectMapper.writeValueAsString(getVehicle));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	public static void getVehiclesByType(RoutingContext context) {
		String type = context.request().getParam("type");
		ObjectMapper objectMapper = new ObjectMapper();
		ParkingService park = new ParkingService();
		List<Vehicle> getVehicle = park.getAllVehiclesByType(type);
		if(getVehicle == null) {
			context.response().setStatusCode(400);
		}else{
			try {
				context.response().setStatusCode(200).putHeader("content-type", "text/html").end(objectMapper.writeValueAsString(getVehicle));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	public static void getVehiclesByRegNo(RoutingContext context) {
		String regNo = context.request().getParam("regNo");
		ObjectMapper objectMapper = new ObjectMapper();
		ParkingService park = new ParkingService();
		Vehicle getVehicle = park.getVehicleByRegNo(regNo);
		if(getVehicle == null) {
			context.response().setStatusCode(400);
		}else{
			try {
				context.response().setStatusCode(200).putHeader("content-type", "text/html").end(objectMapper.writeValueAsString(getVehicle));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
}
