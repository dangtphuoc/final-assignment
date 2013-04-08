package springdata.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springdata.jpa.model.Contact;

/**
 * Provides the repository methods for contacts.
 * @author Petri Kainulainen
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
