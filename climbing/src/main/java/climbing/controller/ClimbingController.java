package climbing.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import climbing.controller.model.ClimberData;
import climbing.controller.model.ClimberData.PermitData;
import climbing.controller.model.ClimberData.RouteData;
import climbing.service.ClimbingService;
import lombok.extern.slf4j.Slf4j;








/*
 * The controller (presentation layer, or port) is a protocol 
 * interface which exposes application functionality as RESTful 
 * web services. It should do that and nothing more.
 * @RestController indicates that this class is a controller for handling RESTful API requests.
 * @RequestMapping("/climbing") specifies that the base URL for this controller is "/climbing".
 * @Slf4j is used for logging.
 */
@RestController
@RequestMapping("/climbing")
@Slf4j
public class ClimbingController {
	
	@Autowired
	private ClimbingService climbingService; 

	
	/*
	 * CREATE: creating a route 
	 * This passed on 08/27/2023
	 */
	
	@PostMapping("/route") //this relates to the ARC api stuff
	@ResponseStatus(code = HttpStatus.CREATED)
	public RouteData insertRoute(@RequestBody RouteData climbingRoute) {
		log.info("Creating new route {}", climbingRoute);
		return climbingService.saveRoute(climbingRoute);
	}
	
	/*
	 * GET: Reading/Listing all the routes available to us 
	 * This passed on 08/28/2023
	 */
	    @GetMapping("/route")
        public List<RouteData> retreieveAllRoutes () {
		
		log.info("Retrieving all climbing routes from the route table: ");
		return climbingService.retrieveAllRoutes(); 
	}
	    
	    
	    //Read route by Id
		// remember the brackets as a placeholder for that column of values
		@GetMapping("/{routeId}") 
		public RouteData retrieveRouteById(@PathVariable Long routeId) {
			log.info("Retrieving climbing route by ID={}", routeId);

			return climbingService.retrieveRouteById(routeId); //there is no retriece methods in pet store
		}
		
		
		/*
		 * Delete route 
		 */
		
		@DeleteMapping("/route/{routeId}")
		public Map<String, String> deleteRouteById(@PathVariable Long routeId) {
		
			climbingService.deleteRouteById(routeId); //this is what calls the service and deletes the pet store
			
		log.info("Deleting route with id {}", routeId);
		return Map.of("delete message", "Deletion of route with ID: " + routeId + " was succesful!!"); 
	}
	
		
		/*
		 * UPDATE: This is update for the  climber 
		 */
		@PutMapping("/route/{routeId}") //this relates to the ARC api stuff; specifically the url 
		public RouteData updateRoute(@PathVariable Long routeId, 
				@RequestBody RouteData routeData) {
			routeData.setRouteId(routeId);
			log.info("Updating climber information {}", routeData);
			return climbingService.saveRoute(routeData);
		}	
		
		
		
		
		
	/*
	 * POST/CREATE: This is create for the climber 
	 */
	@PostMapping("/{routeId}/climber") //this relates to the ARC api stuff. This means create = post
	@ResponseStatus(code = HttpStatus.CREATED)// This is 201 Success!
	public ClimberData addClimber(@PathVariable Long routeId,
			@RequestBody ClimberData climberData) {
		
		log.info("Creating new entry for a climber {}", climberData);
		return climbingService.saveClimber(routeId, climberData);
	}
	
	
	/*
	 * GET= READ 
	 * Read or list all climbers no specification on identification 
	 */
	@GetMapping("/climber")
	public List<ClimberData> retreieveAllClimbers () {
		
		log.info("Retrieving all climbers from the climber table: ");
		return climbingService.retrieveAllClimbers(); 
	}
	
	
	/*
	 * Get = READ 
	 * Read or list all climbers no specification on identification 
	 * Tested and passed 08/27/2023
	 * 
	 */
	@GetMapping("/permit")
	public List<PermitData> retreieveAllPermits () {
		
		log.info("Retrieving all permits from the permit table: ");
		return climbingService.retrieveAllPermits(); 
	}
	
	
	/*
	 * Tested and passed on 08/27/2023
	 */
	@PostMapping("/{routeId}/permit")
	@ResponseStatus(code = HttpStatus.CREATED) //this is 201 created
	public PermitData addPermit(@PathVariable Long routeId, 
			@RequestBody PermitData permitData) {
		
		log.info("Adding a new pet store customer {}", permitData);
		return climbingService.savePermit(routeId, permitData);
	}
	
}
