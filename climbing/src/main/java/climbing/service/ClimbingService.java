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
	
	
	@Transactional(readOnly = false)
	public ClimbingData saveClimber(ClimbingData climbingData) {
		Long climberId = climbingData.getClimberId();
		Climber climber = findOrCreateClimber(climberId);

		copyClimberFields(climber, climbingData); // this is a method call. it calls upon a method
		return new ClimbingData(climberDao.save(climber)); //returning climbing data as I save the climber
	}

	private void copyClimberFields(Climber climber, ClimbingData climbingData) {

		climber.setClimberFirstName(climbingData.getClimberFirstName());
		climber.setClimberLastName(climbingData.getClimberLastName());
		climber.setClimberId(climbingData.getClimberId());
		climber.setDateOfRouteClimbed(climbingData.getDateOfRouteClimbed());
		climber.setClimberAge(climbingData.getClimberAge());
		climber.setClimberEmail(climbingData.getClimberEmail());
		//climber.setRouteId(climbingData.getRouteId());
		
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
		Climber climber = findClimberById(climberId); // this is you finding the pet store first
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
	
	
	@Transactional(readOnly = false)
	public ClimbingRoute saveRoute(ClimbingRoute climbingRoute) {
		
		Long routeId = climbingRoute.getRouteId();
		Route route = findOrCreateRoute(routeId);

		copyRouteFields(route, climbingRoute); // this is a method call. it calls upon a method
		return new ClimbingRoute(routeDao.save(route)); //returning climbing data as I save the climber
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
	
	
	
