package cs544.Service;

import cs544.DAO.RoleRepository;
import cs544.DAO.UserRepository;
import cs544.Domain.Role;
import cs544.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public User createUser(User user) {
        Role userRole = roleRepository.findByName("USER"); // Assuming "USER" is the regular user role
        user.addRole(userRole);
        return userRepository.save(user);
    }
    public List<User> getUsersByRole(String roleName) {
        Role role = roleRepository.findByName(roleName);
        return userRepository.findByRolesContaining(role);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }



    public User updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
