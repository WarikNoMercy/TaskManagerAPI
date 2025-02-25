package kirill.task.api.repository;

import kirill.task.api.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection =  EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void userRepository_FindEmployees_ReturnAllEmployees(){
        User user1 = new User();
        user1.setUsername("employee1");
        user1.setRole("ROLE_EMPLOYEE");

        User user2 = new User();
        user2.setUsername("employee2");
        user2.setRole("ROLE_EMPLOYEE_SENIOR");

        User user3 = new User();
        user3.setUsername("manager");
        user3.setRole("ROLE_MANAGER");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        List<User> employees = userRepository.findEmployees();
        Assertions.assertThat(employees.size()).isEqualTo(2);

    }

    @Test
    public void userRepository_findByUsername(){
        User user = new User();
        user.setUsername("user");
        userRepository.save(user);

        Optional<User> user1 = userRepository.findByUsername("user");
        Optional<User> user2 = userRepository.findByUsername("user2");

        Assertions.assertThat(user1.get()).isNotNull();
        Assertions.assertThat(user2.isPresent()).isFalse();
    }
}
