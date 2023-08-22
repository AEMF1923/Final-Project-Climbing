package climbing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * Things to remember. This must match the values created in DBeaver in the tables created 
 */
@Data
@Entity
public class Climber {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //This lets the program handle the ids 
	private Long climberId; //this needs to be in lower camelcase
	
	@EqualsAndHashCode.Exclude
	private String climberFirstName; 
	
	@EqualsAndHashCode.Exclude
	private String climberLastName;
	
	@EqualsAndHashCode.Exclude
	private Long climberAge; 
	
	@EqualsAndHashCode.Exclude
	private String climberEmail; 
	
	@EqualsAndHashCode.Exclude
	private String dateOfRouteClimbed;
	
	
	/*
	 * you need to point back to the route class 
	 * Need to understand what the equals and hash code means 
	 * Many climbers to one route. Many to one requirements. 
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "route_id", nullable = false)
	//private Long routeId;
	
	private Route route; 
	
	
}
