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
	
	private String permitType; 
	private Long permitPrice; 
	
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "permits")//fixed this on 08/27/2023 -- it needs to point to permits 
	private Set<Route> routes = new HashSet<>();

}
