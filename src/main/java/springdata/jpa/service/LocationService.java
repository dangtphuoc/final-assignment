package springdata.jpa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import springdata.jpa.dto.LocationDTO;
import springdata.jpa.model.Location;

@Service
public class LocationService {
	
	/**
	 * Create a location
	 * @param location
	 * @return Location created
	 */
	public Location createLocation(LocationDTO location) {
		//call repository layer to create location
		return DummyDataRepository.getLocation();
	}

	/**
	 * Get all locations
	 * @return List of Locations
	 */
	public List<Location> getLocations() {
		//call repository to get all locations
		return DummyDataRepository.getLocations();
	}

	/**
	 * Get location by id
	 * @param locationId
	 * @return
	 */
	public Location getLocation(Long locationId) {
		//call repository to get location by id
		return DummyDataRepository.getLocation();
	}

	/**
	 * Update location
	 * @param location
	 * @return Location updated
	 */
	public Location updateLocation(LocationDTO location) {
		//call repository layer to update location
		return DummyDataRepository.getLocation();
	}
}
