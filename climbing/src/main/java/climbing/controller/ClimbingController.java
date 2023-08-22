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

import climbing.controller.model.ClimbingData;
import climbing.controller.model.ClimbingData.ClimbingRoute;
import climbing.controller.model.ClimbingData.ClimbingRoute.ClimbingPermit;
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
	 * creating a route 
	 */
	
	@PostMapping("/route") //this relates to the ARC api stuff
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClimbingRoute insertRoute(@RequestBody ClimbingRoute climbingRoute) {
		log.info("Creating route {}", climbingRoute);
		return climbingService.saveRoute(climbingRoute);
	}
	
	
	/*
	 * This is create for the climber 
	 */
	@PostMapping("/climber") //this relates to the ARC api stuff. This means create = post
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClimbingData insertClimber(@RequestBody ClimbingData climbingData) {
		log.info("Creating new entry for a climber {}", climbingData);
		return climbingService.saveClimber(climbingData);
	}
	
	/*
	 * This is update for the  climber 
	 */
	@PutMapping("/climber/{climberId}") //this relates to the ARC api stuff; specifically the url 
	public ClimbingData updateClimber(@PathVariable Long climberId, 
			@RequestBody ClimbingData climbingData) {
		climbingData.setClimberId(climberId);
		log.info("Updating climber information {}", climbingData);
		return climbingService.saveClimber(climbingData);
	}
	
	/*
	 * Get = READ 
	 * Read or list all climbers no specification on identification 
	 */
	@GetMapping("/climber")
	public List<ClimbingData> retreieveAllClimbers () {
		
		log.info("Retrieving all climbers from the climber table: ");
		return climbingService.retrieveAllClimbers(); 
	}
	
	
	//GET/Read climber by Id
	// remember the brackets as a placeholder for that column of values
	@GetMapping("/climber/{climberId}") 
	public ClimbingData retrievePetStoreById(@PathVariable Long climberId) {
		log.info("Retrieving climber by their ID={}", climberId);

		return climbingService.retrieveClimberById(climberId);
	}
	
	/*
	 * Delete climber 
	 */
	
	@DeleteMapping("/climber/{climberId}")
	public Map<String, String> deleteClimberById(@PathVariable Long climberId) {
	
		climbingService.deleteClimberById(climberId); //this is what calls the service and deletes the pet store
		
	log.info("Deleting climber with id {}", climberId);
	return Map.of("delete message", "Deletion of climber with ID: " + climberId + " was succesful!!"); 
}
	
	/*
	 * Get = READ 
	 * Read or list all climbers no specification on identification 
	 */
//	@GetMapping("/climber/permit")
//	public List<ClimbingPermit> retreieveAllPermits () {
//		
//		log.info("Retrieving all climbers from the climber table: ");
//		return climbingService.retrieveAllPermits(); 
//	}
	
	
}
