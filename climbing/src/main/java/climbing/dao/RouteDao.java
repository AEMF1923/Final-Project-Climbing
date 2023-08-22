package climbing.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import climbing.entity.Route;

public interface RouteDao extends JpaRepository<Route, Long> {

}
