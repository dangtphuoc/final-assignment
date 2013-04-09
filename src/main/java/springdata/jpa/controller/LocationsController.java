package springdata.jpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springdata.jpa.common.ErrorType;
import springdata.jpa.dto.LocationDTO;
import springdata.jpa.dto.ResponseBean;
import springdata.jpa.model.Location;
import springdata.jpa.service.LocationService;



@RequestMapping("/locations")
@Controller
public class LocationsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationsController.class);
	
	@Autowired
	private LocationService locationService;
	
	@RequestMapping(method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<LocationDTO> getLocations() {
		LOGGER.debug("getting locations...");
		
		List<Location> locations = locationService.getLocations();
		List<LocationDTO> locationDTOs = new ArrayList<LocationDTO>();
		for(Location location : locations) {
			locationDTOs.add(new LocationDTO(location));
		}
		return locationDTOs;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public LocationDTO getLocation(@PathVariable("id") Long id) {
		LOGGER.debug("getting location: " + id);
		
		Location location =  locationService.getLocation(id);
		LocationDTO locationDTO = new LocationDTO(location);
		return locationDTO;
	}
	
	@RequestMapping(method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseBean createLocation(@RequestBody LocationDTO locationDTO) {
		LOGGER.debug("creating location...");
		
		Location location = locationService.createLocation(locationDTO);
		ErrorType error = ErrorType.SUCCESS;
		if(location.getId() == null) {
    		error = ErrorType.FAIL;
    	}
        return new ResponseBean(error);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseBean updateLocation(@RequestBody LocationDTO locationDTO) {
		LOGGER.debug("updating location...");
		
		Location location = locationService.updateLocation(locationDTO);
		ErrorType error = ErrorType.SUCCESS;
		if(location == null) {
    		error = ErrorType.FAIL;
    	}
        return new ResponseBean(error);
	}
}
