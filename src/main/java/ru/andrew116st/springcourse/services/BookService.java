package ru.andrew116st.springcourse.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrew116st.springcourse.models.Books;
import ru.andrew116st.springcourse.repositories.BookRepository;
import ru.andrew116st.springcourse.models.Person;
import ru.andrew116st.springcourse.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {

        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Books> findAll(){

        return bookRepository.findAll();
    }

    public Books findOne(int id){
        Optional<Books> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }


    @Transactional
    public void save(Books book){
        bookRepository.save(book);

    }

    @Transactional
    public void update(int id, Books updatedBook){
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);

    }
    @Transactional
    public Optional<Person> whoGrabBook(int id) {
        Optional<Books> foundBook = bookRepository.findById(id);
        Person person = foundBook.get().getOwner();

        return Optional.ofNullable(person);
    }

    @Transactional
    public void  clearPersonBook(int id) {
        Optional<Books> foundBook = bookRepository.findById(id);
        foundBook.get().setOwner(null);


    }
    @Transactional
    public void indexPersonBook(int idBook,int idPerson) {
        Optional<Books> foundBook = bookRepository.findById(idBook);
        Optional<Person> foundPerson = peopleRepository.findById(idPerson);

        foundBook.get().setOwner(foundPerson.get());
        foundBook.get().setCreatedAt(new Date());

    }
    @Transactional
    public List<Books> findAll (int page, int size){

        return bookRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Transactional
    public List<Books> findAllSortByYear (){

        return bookRepository.findAll(Sort.by("year"));
    }

    @Transactional
    public List<Books> findComboFatality  (int page, int size){

        return bookRepository.findAll(PageRequest.of(page, size, Sort.by("year"))).getContent();
    }


    @Transactional
    public List<Books> findTheRightBook (String query){

        List<Books> foundBooks = bookRepository.findByNameContainingIgnoreCase(query);

        for(Books findBooks : foundBooks){
            Hibernate.initialize(findBooks.getOwner());
        }

        return foundBooks;
    }




}
