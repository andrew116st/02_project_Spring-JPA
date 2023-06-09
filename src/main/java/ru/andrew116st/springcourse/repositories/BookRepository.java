package ru.andrew116st.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andrew116st.springcourse.models.Books;

import java.util.List;


@Repository
    public interface BookRepository extends JpaRepository<Books, Integer> {

        List<Books> findByNameContainingIgnoreCase(String name);

}
