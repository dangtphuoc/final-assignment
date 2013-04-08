package springdata.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springdata.jpa.model.Role;
import springdata.jpa.repository.RoleRepository;

import com.google.common.collect.Lists;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	public List<Role> getRoles() {
		return Lists.newArrayList(roleRepository.findAll());
	}
}
