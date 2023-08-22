package climbing.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import climbing.entity.Permit;

public interface PermitDao extends JpaRepository<Permit, Long> {

}
