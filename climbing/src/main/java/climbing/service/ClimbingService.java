package climbing.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import climbing.controller.model.ClimberData;
import climbing.controller.model.ClimberData.PermitData;
import climbing.controller.model.ClimberData.RouteData;
import climbing.dao.ClimberDao;
import climbing.dao.RouteDao;
import climbing.dao.PermitDao;
import climbing.entity.Climber;
import climbing.entity.Permit;
import climbing.entity.Route;








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
	public ClimberData saveClimber(Long routeId, ClimberData climberData) {
		Route route = findRouteById(routeId); 
		Long climberId = climberData.getClimberId(); 
		Climber climber = findOrCreateClimber(routeId, climberId);
		
		copyClimberFields(climber, climberData);
		
		climber.setRoute(route);
		route.getClimbers().add(climber);
		
		return new ClimberData(climberDao.save(climber));
	}

	/*
	 * Finding or creating a climber row
	 */
	
	private Climber findOrCreateClimber(Long routeId, Long climberId) {
		if (Objects.isNull(climberId)) {
			return new Climber();
		} else {
			return findClimberById(routeId, climberId);
		}
}

	/*
 * My climber needs to have a route but it doesn't like the route get call. Why? 	
 * Notice what you are calling in the fields, the climber entity and the climber 
 * data or DTO conversion 
 */
	private void copyClimberFields(Climber climber, ClimberData climbingData) {

		climber.setClimberFirstName(climbingData.getClimberFirstName());
		climber.setClimberLastName(climbingData.getClimberLastName());
		climber.setClimberId(climbingData.getClimberId());
		climber.setDateOfRouteClimbed(climbingData.getDateOfRouteClimbed());
		climber.setClimberAge(climbingData.getClimberAge());
		climber.setClimberEmail(climbingData.getClimberEmail());
		//climber.setRouteId(climbingData.getRoute());
		
	}



	/*
	 * finding climber by id for reading/get
	 * Remember that the DAO extends the JPA-CRUD operations. remember how this
	 * travels: URL -> controller -> service -> Dao (takes the data from the DTO) 
	 * The no such element exception is
	 * 404 error but it makes more sense and is readable
	 */
	@Transactional(readOnly = true)
	private Climber findClimberById(Long climberId, Long routeId) {
		Climber climber = climberDao.findById(climberId).orElseThrow(() -> 
				new NoSuchElementException("No Climber with ID = " + climberId + " was not found."));
		
		if(climber.getRoute().getRouteId() != routeId) {
			throw new NoSuchElementException("The climber with ID " + climberId + " did not climb route" + routeId);
		}
		return climber;
	
	}

	/*
	 * How to display all listed climbers in the climber table 
	 * I don't want their routes though because a climber can have 
	 * many accomplished routes
	 */
	@Transactional(readOnly = true)
	public List<ClimberData> retrieveAllClimbers() {
		List<Climber> climbers = climberDao.findAll();
		List<ClimberData> result = new LinkedList<>();

		for (Climber climber : climbers) {
			ClimberData cd = new ClimberData(climber);
//			cd.getRouteId().clear(); // we don't want a list with routes. Only the climbers

			result.add(cd);

		}

		return result;

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
	public RouteData saveRoute(RouteData climbingRoute) {
		
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
		return new RouteData(route); // returning climbing data as I save the climber
	}


	private void copyRouteFields(Route route, RouteData climbingRoute) {

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

	/*
	 * Get/reading all route information 
	 * 
	 */
	public List<RouteData> retrieveAllRoutes() {
		List<Route> routes = routeDao.findAll();
		List<RouteData> response = new LinkedList<>();
		
		/* Using a enhanced for loop. Turning the List of contributor entities in to a list of contributor data */
		
		for(Route route : routes) {
			response.add(new RouteData(route));
		}
		return response;
		
	}

	/* 
	 * Find route by id 
	 */
	private Route findRouteById(Long routeId) {
		
	return routeDao.findById(routeId).orElseThrow(() -> new NoSuchElementException("Route with ID " + routeId + " not found."));

	}		
		
	/*
	 * Retrieve route by id 
	 */
	
	@Transactional(readOnly = true)
	public RouteData retrieveRouteById(Long routeId) {
		Route route = findRouteById(routeId);

		if (route == null) {
			throw new IllegalStateException("Route with Id = " + routeId + " does not exist");

		}

		return new RouteData(route); 
											
	}
	
	public void deleteRouteById(Long routeId) {
		Route route = findRouteById(routeId); // this is you finding the pet store first
		routeDao.delete(route);
		
		
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private PermitDao permitDao;
	
	public PermitData savePermit(Long routeId, PermitData permitData) {
		
		Route route = findRouteById(routeId);
		Long permitId = permitData.getPermitId();
		Permit permit = findOrCreatePermit(routeId, permitId);
		
		copyPermitFields(permit, permitData);
		
		permit.getRoutes().add(route);
		
		route.getPermits().add(permit);
		
		//Remember what you are doing above this is the many to many relantionship 
		//several routes can have several permits 
		
		return new PermitData(permitDao.save(permit));
	}
	
	

/*
 * Think of this of setting and defining what the permit object is 
 * what they expected values are 
 */
	private void copyPermitFields(Permit permit, PermitData permitData) {
		permit.setPermitId(permitData.getPermitId());
		permit.setPermitPrice(permitData.getPermitPrice());
		permit.setPermitType(permitData.getPermitType());
		
	}
	
	/*
	 * if you can't find a new route please create the new route 
	 * If the object permit is null create a new permit object return by id 
	 * 
	 */
	
	private Permit findOrCreatePermit(Long routeId, Long permitId) {
		
		if(Objects.isNull(permitId)) {
			return new Permit(); 
		} else {
			return findPermitById(permitId, routeId);
		}
		
		
	}
	
	
	private Permit findPermitById(Long permitId, Long routeId) {

		Permit permit = permitDao.findById(permitId)
				.orElseThrow(() -> new NoSuchElementException("Permit with ID = " + permitId + " was not found."));
		boolean found = false; // think of this of a new variable that helps you find a match

		for (Route route : permit.getRoutes()) { // the permit is linked to route. remember the entity
													// relantioship
			if (route.getRouteId() == routeId) {
				found = true;
				break; // this stops the loop when the route id is found
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"Permit with ID = " + permitId + " doesn't belong to the route with ID = " + routeId);
		}
		return permit;

	}

	/*
	 * 
	 * Read/Get All permit information
	 */
	@Transactional(readOnly = true)
	public List<PermitData> retrieveAllPermits() {
		List<Permit> permits = permitDao.findAll();
		List<PermitData> result = new LinkedList<>();

		for (Permit permit : permits) {
			PermitData pt = new PermitData(permit);
			result.add(pt);
		}
		return result;
	}
	
	

	}
	
	
	
	
