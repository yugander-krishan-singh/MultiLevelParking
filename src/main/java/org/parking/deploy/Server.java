package org.parking.deploy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Server extends AbstractVerticle {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		DeploymentOptions options = new DeploymentOptions()
				.setConfig(new JsonObject().put("http.port", 8096).put("infinispan.host", "localhost"));
		vertx.deployVerticle(Server.class.getName(), options);
	}

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.post("/park").handler(APIHandler::park);
		router.post("/unpark").handler(APIHandler::unPark);
		router.get("/vehicles/colors/:color").handler(APIHandler::getVehcilesByColor);
		router.get("/vehicles/regNos/:regNo").handler(APIHandler::getVehiclesByRegNo);
		router.get("/vehicles/types/:type").handler(APIHandler::getVehiclesByType);
		vertx.createHttpServer() // creates a HttpServer
				.requestHandler(router::accept) // router::accept will handle the requests
				.listen(config().getInteger("http.port", 8096)); // Get "http.port" from the config, default value 8080
	}

}
