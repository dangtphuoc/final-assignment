package springdata.jpa.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import springdata.jpa.model.AbstractEntity;
import springdata.jpa.model.ClassOffering;
import springdata.jpa.model.Course;
import springdata.jpa.model.Curriculum;
import springdata.jpa.model.Location;
import springdata.jpa.model.Student;

public class DummyDataRepository {

	public static List<Course> getCourses() {
		
		return Arrays.asList(getCourse());
	}

	public static Course getCourse() {
		Course course = new Course(1L, "Dummy Course Title", "Dummy Course Description");
		return course;
	}

	public static Curriculum getCurriculum() {
		Curriculum curriculum = new Curriculum(1L, "Dummy Curriculum Title", "Dummy Curriculum Description");
		return curriculum;
	}
	
	public static List<Curriculum> getCurricula() {
		return Arrays.asList(getCurriculum());
	}

	public static List<ClassOffering> getClassOfferings() {
		return Arrays.asList(getClassOffering());
	}
	
	public static ClassOffering getClassOffering() {
		ClassOffering classOffering = new ClassOffering(1L, "Dummy Class Offering Title", "Dummy Class Offering Description");
		classOffering.setCourse(getCourse());
		return classOffering;
	}

	public static Location getLocation() {
		Location location = new Location(1L, "Dummy Location Title", "Dummy Location Description");
		return location;
	}
	
	public static List<Location> getLocations() {
		return Arrays.asList(getLocation());
	}

	public static Student getStudent() {
		Student student = new Student(2L, "First Name", "Last Name");
		return student;
	}
	public static List<Student> getStudents() {
		return Arrays.asList(getStudent());
	}

	public static List<AbstractEntity> getContents() {
		List<AbstractEntity> content = new ArrayList<AbstractEntity>();
		content.add(getClassOffering());
		content.add(getCurriculum());
		return content;
	}

}
