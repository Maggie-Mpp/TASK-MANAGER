package cs544.Controller;

import cs544.Domain.User;
import cs544.Service.UserService;
import cs544.utility.JWTUtil;
import cs544.utility.UserRequest;
import cs544.utility.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request) {

        //: validate usrname/pwd with DB

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        System.out.println("isAuthenticated >>>>> " + auth.isAuthenticated());

        String token = "Bearer " + jwtUtil.generateToken(request.getUsername());


//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", token);

        return ResponseEntity.ok()
                .body(new UserResponse(token, "success"));

    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("incoming req body to add user>>>>: " + user);
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/roles/{roleName}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleName) {
        List<User> users = userService.getUsersByRole(roleName);
        return ResponseEntity.ok(users);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }


    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/Welcome")
    public ResponseEntity<String> welcome(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return ResponseEntity.ok("Hello User: " + username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
