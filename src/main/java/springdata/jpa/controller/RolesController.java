package springdata.jpa.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springdata.jpa.model.Role;
import springdata.jpa.service.RoleService;


@RequestMapping("/roles")
@Controller
public class RolesController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RolesController.class);
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<Role> getRoles() {
		return roleService.getRoles();
	}
}
