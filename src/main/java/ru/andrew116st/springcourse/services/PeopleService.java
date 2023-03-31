package ru.andrew116st.springcourse.services;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrew116st.springcourse.models.Books;
import ru.andrew116st.springcourse.models.Person;
import ru.andrew116st.springcourse.repositories.BookRepository;
import ru.andrew116st.springcourse.repositories.PeopleRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    private final BookRepository bookRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BookRepository bookRepository) {

        this.peopleRepository = peopleRepository;
        this.bookRepository = bookRepository;
    }

    public List<Person> findAll(){

        return peopleRepository.findAll();
    }

    public Person findOne(int id){
       Optional<Person> foundPerson = peopleRepository.findById(id);
       return foundPerson.orElse(null);
    }


    @Transactional
    public void save(Person person){
        peopleRepository.save(person);

    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);

    }

    @Transactional
    public List<Books> showPerson(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);

        Hibernate.initialize(foundPerson.get().getBooks());

        return foundPerson.get().getBooks();


    }


}
