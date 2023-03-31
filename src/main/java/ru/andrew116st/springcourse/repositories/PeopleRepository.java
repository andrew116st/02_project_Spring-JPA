package ru.andrew116st.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andrew116st.springcourse.models.Person;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {


}
