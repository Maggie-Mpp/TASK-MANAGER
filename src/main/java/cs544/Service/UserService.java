package cs544.Service;

import cs544.DAO.UserRepository;
//import cs544.Domain.Role;
import cs544.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder pwdEncoder;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(User user) {
//        String userRole = roleRepository.findByName("USER"); // Assuming "USER" is the regular user role
//        user.setRoles((Set<String>) user);
        if(user.getRoles().isEmpty()){
            user.addRole("USER");
        }
        user.setPassword(pwdEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public List<User> getUsersByRole(String roleName) {
//        String role = roleRepository.findByName(roleName);
        return userRepository.findByRolesContaining(roleName);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Optional<User>  findUserByUsername(String userName){
        return userRepository.findByUsername(userName);
    }


    public User updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(pwdEncoder.encode(user.getPassword()));
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = findUserByUsername(username);
        User user= opt.get();
        if(opt.isEmpty()){
            throw new UsernameNotFoundException(" user doesn't exist");}
        UserDetails userDetails= new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles().stream().
                            map(role -> new SimpleGrantedAuthority(role)).
                            collect(Collectors.toList()));

        CustomUserDetails customUserDetails = new CustomUserDetails(userDetails);
        customUserDetails.setId(user.getId());

        return customUserDetails;


    }
}
