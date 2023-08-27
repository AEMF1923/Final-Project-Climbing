package climbing.controller.model;

import java.util.HashSet;
import java.util.Set;

import climbing.entity.Climber;
import climbing.entity.Permit;
import climbing.entity.Route;
import lombok.Data;
import lombok.NoArgsConstructor;




/*
 * The Climber Data contains information of what each entity is 
 * This helps with the conversion -- the conversion of a data object to an entity 
 */
@Data
@NoArgsConstructor
public class ClimberData {
	
	
	private Long climberId;
	private String climberFirstName; 
	private String climberLastName; 
	private Long climberAge; 
	private String climberEmail;
	private String dateOfRouteClimbed; 
	
	private Long routeId; 
	
	
	public  ClimberData (Climber climber) {
		/*
		 * converting climber object to climber data; this the DTO piece  
		 */
		climberId = climber.getClimberId();
		climberFirstName = climber.getClimberFirstName();
		climberLastName = climber.getClimberLastName();
		climberAge = climber.getClimberAge(); 
		climberEmail = climber.getClimberEmail();
		dateOfRouteClimbed = climber.getDateOfRouteClimbed();
		///routeId = climber.getRoute(); 
		

		}
	
	@Data
	@NoArgsConstructor
	public static class RouteData{
		
		private Long routeId;
		private String routeName; 
		private String routeGrade; 
		private String routeGradeRange;
		private String routeGradeRangeDesc;
		private String routeState;
		private String routeNotes;
		private Set<PermitData> permits = new HashSet<>(); //this accoutns for the many to many with permits 
		private Set<ClimberData> climbers = new HashSet<>(); //one route to many climbers 
		
		
		public RouteData(Route route) {
			routeId = route.getRouteId();
			routeName = route.getRouteName();
			routeGrade = route.getRouteGrade();
			routeGradeRange = route.getRouteGradeRange();
			routeGradeRangeDesc = route.getRouteGradeRangeDesc();
			routeState = route.getRouteState();
			routeNotes = route.getRouteNotes();
		
			for(Permit permit : route.getPermits()) {
				permits.add(new PermitData(permit));
			}
			
			for(Climber climber : route.getClimbers()) {
				climbers.add(new ClimberData(climber));
			}
			
		}
		
		
	}
		
		@Data
		@NoArgsConstructor
		public static class PermitData{
			
			private Long permitId;
			private Long permitPrice; 
			private String permitType; 
			
			
			public PermitData(Permit permit) {
				permitId = permit.getPermitId();
				permitPrice = permit.getPermitPrice();
				permitType = permit.getPermitType();
				
			}

		}

		
		
	
	
}
	
	

	
	 


