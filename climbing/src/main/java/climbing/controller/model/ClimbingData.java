package climbing.controller.model;

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
public class ClimbingData {
	
	
	private Long climberId;
	private String climberFirstName; 
	private String climberLastName; 
	private Long climberAge; 
	private String climberEmail;
	private String dateOfRouteClimbed; 
	
//	private Long routeId; 
	
	
	public  ClimbingData (Climber climber) {
		
		climberId = climber.getClimberId();
		climberFirstName = climber.getClimberFirstName();
		climberLastName = climber.getClimberLastName();
		climberAge = climber.getClimberAge(); 
		climberEmail = climber.getClimberEmail();
		dateOfRouteClimbed = climber.getDateOfRouteClimbed();
		

		}
	
	@Data
	@NoArgsConstructor
	public static class ClimbingRoute{
		
		private Long routeId;
		private String routeName; 
		private String routeGrade; 
		private String routeGradeRange;
		private String routeGradeRangeDesc;
		private String routeState;
		private String routeNotes;
		
		
		public ClimbingRoute(Route route) {
			routeId = route.getRouteId();
			routeName = route.getRouteName();
			routeGrade = route.getRouteGrade();
			routeGradeRange = route.getRouteGradeRange();
			routeGradeRangeDesc = route.getRouteGradeRangeDesc();
			routeState = route.getRouteState();
			routeNotes = route.getRouteNotes();
		
		}
		
		
		@Data
		@NoArgsConstructor
		public static class ClimbingPermit{
			
			private Long permitId;
			private Long permitPrice; 
			private String permitType; 
			
			
			public ClimbingPermit(Permit permit) {
				permitId = permit.getPermitId();
				permitPrice = permit.getPermitPrice();
				permitType = permit.getPermitType();
				
			}
			
		
		
	}
	}
}
	
	

	
	 


