package springdata.jpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import springdata.jpa.model.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {
}
