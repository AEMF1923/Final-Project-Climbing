package climbing.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import climbing.entity.Climber;

public interface ClimberDao extends JpaRepository<Climber, Long> {

}
