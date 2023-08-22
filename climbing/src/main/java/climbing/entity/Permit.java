package climbing.entity;




import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Permit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //This lets the program (Mysql) handle the ids 
	private Long permitId; 
	
	@EqualsAndHashCode.Exclude
	private String permitType; 
	
	@EqualsAndHashCode.Exclude
	private Long permitPrice; 
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "routes")//This has to match the set name below
	private Set<Route> routes = new HashSet<>();

}
