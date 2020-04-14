package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.model.User;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.contains;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository users;

    private User norbertSiegmund = new User("Norbert", "Siegmund");
    private User jonasHecht = new User("Jonas", "Hecht");

    @Before
    public void fillSomeDataIntoOurDb() {
        // Add new Users to Database
        entityManager.persist(norbertSiegmund);
        entityManager.persist(jonasHecht);
    }




}