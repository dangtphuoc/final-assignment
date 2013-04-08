package springdata.jpa.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import springdata.jpa.dto.ContactDTO;
import springdata.jpa.model.Contact;
import springdata.jpa.model.ContactTestUtil;
import springdata.jpa.repository.ContactRepository;
import springdata.jpa.service.NotFoundException;
import springdata.jpa.service.RepositoryContactService;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Petri Kainulainen
 */
public class RepositoryContactServiceTest {

    private static final Long ID = Long.valueOf(3);

    private RepositoryContactService service;

    private ContactRepository repositoryMock;

    @Before
    public void setUp() {
        service = new RepositoryContactService();

        repositoryMock = mock(ContactRepository.class);
        ReflectionTestUtils.setField(service, "repository", repositoryMock);
    }

    @Test
    public void add() {
        ContactDTO added = ContactTestUtil.createDTO();

        service.add(added);

        ArgumentCaptor<Contact> contactArgument = ArgumentCaptor.forClass(Contact.class);
        verify(repositoryMock, times(1)).save(contactArgument.capture());
        verifyNoMoreInteractions(repositoryMock);

        Contact actual = contactArgument.getValue();
        ContactTestUtil.assertContact(added, actual);
    }

    @Test
    public void deleteById() throws NotFoundException {
        Contact deleted = new Contact();
        when(repositoryMock.findOne(ID)).thenReturn(deleted);

        Contact actual = service.deleteById(ID);

        verify(repositoryMock, times(1)).findOne(ID);
        verify(repositoryMock, times(1)).delete(deleted);
        verifyNoMoreInteractions(repositoryMock);

        assertEquals(deleted, actual);
    }

    @Test(expected = NotFoundException.class)
    public void deleteByIdWhenContactIsNotFound() throws NotFoundException {
        when(repositoryMock.findOne(ID)).thenReturn(null);

        service.deleteById(ID);

        verify(repositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void findAll() {
        List<Contact> found = new ArrayList<Contact>();
        when(repositoryMock.findAll()).thenReturn(found);

        List<Contact> actual = service.findAll();

        verify(repositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(repositoryMock);

        assertEquals(found, actual);
    }

    @Test
    public void findById() throws NotFoundException {
        Contact found = new Contact();
        when(repositoryMock.findOne(ID)).thenReturn(found);

        Contact actual = service.findById(ID);

        verify(repositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(repositoryMock);

        assertEquals(actual, found);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdWhenContactIsNotFound() throws NotFoundException {
        when(repositoryMock.findOne(ID)).thenReturn(null);

        service.findById(ID);

        verify(repositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void update() throws NotFoundException {
        Contact found = ContactTestUtil.createModel(ID);
        when(repositoryMock.findOne(ID)).thenReturn(found);

        ContactDTO updated = ContactTestUtil.createDTO(ID);
        Contact actual = service.update(updated);

        verify(repositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(repositoryMock);

        ContactTestUtil.assertContact(updated, found);
        assertEquals(found, actual);
    }

    @Test(expected = NotFoundException.class)
    public void updateWhenContactIsNotFound() throws NotFoundException {
        when(repositoryMock.findOne(ID)).thenReturn(null);

        ContactDTO updated = ContactTestUtil.createDTO(ID);
        service.update(updated);

        verify(repositoryMock, times(1)).findOne(ID);
        verifyNoMoreInteractions(repositoryMock);
    }
}
