package app.ecommerce_backend.controller;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import app.ecommerce_backend.model.User;
import app.ecommerce_backend.model.dto.UserDTO;
import app.ecommerce_backend.repository.UserRepository;
import app.ecommerce_backend.security.Jwt;
import app.ecommerce_backend.security.JwtRequest;
import app.ecommerce_backend.security.JwtResponse;
import app.ecommerce_backend.security.UserPrincipal;
import app.ecommerce_backend.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepo;
	
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	Jwt jwt;
	
	
	
	@RequestMapping(value = "/getUsers")
	public ResponseEntity<List<User>> getUsers(){
		return new ResponseEntity<List<User>>(userService.getUsers(),HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/get/{id}", method=RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
		
        Optional<User> user= userService.getUserById(id);
        if(user.isPresent()) {
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDto) {
    	
        return new ResponseEntity<User>(userService.addUser(userDto), HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/registerAdmin", method=RequestMethod.POST)
    public ResponseEntity<User> addUserAdmin(@RequestBody UserDTO userDto) {
    	
        return new ResponseEntity<User>(userService.addUserAdmin(userDto), HttpStatus.CREATED);
    }
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<User> removeUser(@PathVariable Long id) {
        try {
        	userService.removeUser(id);
        }catch (Exception e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
    	userService.updateUser(id, user);
        return new ResponseEntity<User>(HttpStatus.CREATED);
    }
	
	@RequestMapping(value = "/getByEmail/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("email")String email) {
		return new ResponseEntity<User>(userService.getUserByEmail(email), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST) 
	public JwtResponse login(@RequestBody JwtRequest jwtRequest) {
		authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
		User user = userRepo.getUser(jwtRequest.getEmail());
		UserPrincipal userPrincipal = new UserPrincipal(user);
		String token = jwt.generateToken(userPrincipal);
		return new JwtResponse(token);
		
  }
	
	
	
	
	
	private void authenticate(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
	}
	
	
}

	
	


