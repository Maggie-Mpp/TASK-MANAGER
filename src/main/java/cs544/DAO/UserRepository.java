package cs544.DAO;

import cs544.Domain.Role;
import cs544.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByRolesContaining(Role role);
}
