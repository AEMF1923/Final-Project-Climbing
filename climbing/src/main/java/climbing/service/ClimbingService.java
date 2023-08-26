package climbing.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import climbing.controller.model.ClimbingData;
import climbing.controller.model.ClimbingData.ClimbingRoute;
import climbing.controller.model.ClimbingData.ClimbingRoute.ClimbingPermit;
import climbing.dao.ClimberDao;
import climbing.dao.RouteDao;
import climbing.entity.Climber;
import climbing.entity.Permit;
import climbing.entity.Route;
import climbing.dao.PermitDao;



/*The service layer (domain) contains your business logic. It defines which functionalities you provide, 
 * how they are accessed, and what to pass and get in return - independent on any port 
 * (of which there may be multiple: web services, message queues, scheduled events)
 *  and independent on its internal workings (it's nobody's business that the service uses the repository, 
 *  or even how data is represented in a repository). The service layer may translate 1:1 
 * from the repositiory data, or may apply filtering, transformation or aggregation of additional data.
 * 
 *
 * @Autowired so that Spring can inject the DAO object into the variable.
 */ 

@Service
public class ClimbingService {

	@Autowired
	private ClimberDao climberDao; //This helps me extend jpa/getters and setters 
	
	/*
	 * If there is no climber, then create it 
	 */
	@Transactional(readOnly = false)
	public ClimbingData saveClimber(ClimbingData climbingData) {
		
		Climber climber = null;
		if (climbingData.getClimberId() != null) {
			Long climberId = climbingData.getClimberId();
			climber = findOrCreateClimber(climberId);
			copyClimberFields(climber, climbingData);
		} else {
			climber = new Climber();
			copyClimberFields(climber, climbingData);
			climberDao.save(climber);
		}
		return new ClimbingData(climber); // returning climbing data as I save the climber	
	}

/*
 * My climber needs to have a route but it doesn't like the route get call. Why? 	
 */
	private void copyClimberFields(Climber climber, ClimbingData climbingData) {

		climber.setClimberFirstName(climbingData.getClimberFirstName());
		climber.setClimberLastName(climbingData.getClimberLastName());
		climber.setClimberId(climbingData.getClimberId());
		climber.setDateOfRouteClimbed(climbingData.getDateOfRouteClimbed());
		climber.setClimberAge(climbingData.getClimberAge());
		climber.setClimberEmail(climbingData.getClimberEmail());
		//climber.setRouteId(climbingData.getRoute());
		
	}

	/*
	 * Finding or creating a climber row
	 */
	private Climber findOrCreateClimber(Long climberId) {
		if (Objects.isNull(climberId)) {
			return new Climber();
		} else {
			return findClimberById(climberId);
		}
}

	/*
	 * finding climber by id for reading/get
	 */
	@Transactional(readOnly = true)
	private Climber findClimberById(Long climberId) {
		return climberDao.findById(climberId)
				.orElseThrow(() -> 
				new NoSuchElementException("No Climber with ID = " + climberId + " was not found."));
	
	}

	/*
	 * How to display all listed climbers in the climber table 
	 * I don't want their routes though because a climber can have 
	 * many accomplished routes
	 */
	@Transactional(readOnly = true)
	public List<ClimbingData> retrieveAllClimbers() {
		List<Climber> climbers = climberDao.findAll();
		List<ClimbingData> result = new LinkedList<>();

		for (Climber climber : climbers) {
			ClimbingData cd = new ClimbingData(climber);
//			cd.getRouteId().clear(); // we don't want a list with routes. Only the climbers
			

			result.add(cd);

		}

		return result;
		
	}

	/*
	 * Here we are calling the retrieve climber by id. 
	 */
	@Transactional(readOnly = true)
	public ClimbingData retrieveClimberById(Long climberId) {
		Climber climber = findClimberById(climberId);

		if (climber == null) {
			throw new IllegalStateException("Climber with Id = " + climberId + " does not exist");

		}

		return new ClimbingData(climber); 
											
	}

	public void deleteClimberById(Long climberId) {
		Climber climber = findClimberById(climberId); // this is you finding the climber
		climberDao.delete(climber); // this is you deleting it
		
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * Below is the route information 
	 * saving 
	 * inserting 
	 */
	@Autowired
	private RouteDao routeDao;
	
	
	
	/*
	 * Adding a check for an empty route, so what do you need to do if there is an 
	 * empty route 
	 * Do a check if null then what. This is what was done for saving a contributor in Park Service
	 */
	@Transactional(readOnly = false)
	public ClimbingRoute saveRoute(ClimbingRoute climbingRoute) {
		
		Route route = null;
		if (climbingRoute.getRouteId() != null) {
			Long routeId = climbingRoute.getRouteId();
			route = findOrCreateRoute(routeId);
			copyRouteFields(route, climbingRoute);
		} else {
			route = new Route();
			copyRouteFields(route, climbingRoute);
			routeDao.save(route);
		}
		return new ClimbingRoute(route); // returning climbing data as I save the climber
	}


	private void copyRouteFields(Route route, ClimbingRoute climbingRoute) {

		route.setRouteId(climbingRoute.getRouteId());
		route.setRouteName(climbingRoute.getRouteName());
		route.setRouteGrade(climbingRoute.getRouteGrade());
		route.setRouteGradeRange(climbingRoute.getRouteGradeRange());
		route.setRouteGradeRangeDesc(climbingRoute.getRouteGradeRangeDesc());
		route.setRouteState(climbingRoute.getRouteState());
		route.setRouteNotes(climbingRoute.getRouteNotes());
		
	}
	
	
	private Route findOrCreateRoute(Long routeId) {
		return routeDao.findById(routeId)
				.orElseThrow(() -> 
				new NoSuchElementException("No Route with ID = " + routeId + " was not found."));
	}

	public List<ClimbingRoute> retrieveAllRoutes() {
		List<Route> routes = routeDao.findAll();
		List<ClimbingRoute> response = new LinkedList<>();
		
		/* Using a enhanced for loop. Turning the List of contributor entities in to a list of contributor data */
		
		for(Route route : routes) {
			response.add(new ClimbingRoute(route));
		}
		return response;
		
	}

	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * Permit information 
	 * This is wrong 
	 * Read/Get permit information
	 */
//	@Transactional(readOnly = true)
//	public List<ClimbingPermit> retrieveAllPermits() {
//		 
//			List<Permit> permits = permitDao.findAll();
//			List<ClimbingPermit> result = new LinkedList<>();
//
//			for (Permit permit : permits) {
//				ClimbingPermit pt = new ClimbingPermit(permit);
//
//				
//
//				result.add(pt);
//
//			}
//
//			return result;
//	}
//	
	
	

	}
	
	
	
