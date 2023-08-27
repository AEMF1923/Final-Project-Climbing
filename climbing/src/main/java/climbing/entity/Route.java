package climbing.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity //jakarta.persistence. Specifies that the class is an entity. This annotation is applied to the entity class.
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //This lets the program handle the ids 
	private Long routeId; 
	
	@EqualsAndHashCode.Exclude
	private String routeName; 
	
	@EqualsAndHashCode.Exclude
	private String routeGrade;
	
	@EqualsAndHashCode.Exclude
	private String routeGradeRange;
	
	@EqualsAndHashCode.Exclude
	private String routeGradeRangeDesc;
	
	@EqualsAndHashCode.Exclude
	private String routeState;
	
	@EqualsAndHashCode.Exclude
	private String routeNotes;
	
	
	/*
	 * Many routes can have several permits. You can have a day, overnight, month or annual 
	 */

	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "route_permit", joinColumns = @JoinColumn(name = "permit_id")) // This has to match the set name																	
	private Set<Permit> permits = new HashSet<>();
	
	
	/*
	 * one climber to many routes 
	 * one climber can climb many routes or have climbed many routes 
	 * This makes sense because multiple climbers can climb a route
	 * 
	 * It does not like the mapped by, if I take it out the application works, but I noticed that is 
	 * being used in Pet Store
	 */
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "route", cascade = CascadeType.PERSIST)  //Don't I have to put a mapped by here...it is in the PetStore
	private Set<Climber> climbers = new HashSet<>(); 
	
}

