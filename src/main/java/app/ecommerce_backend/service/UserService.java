package app.ecommerce_backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import app.ecommerce_backend.repository.UserRepository;
import app.ecommerce_backend.security.UserPrincipal;
import app.ecommerce_backend.model.User;
import app.ecommerce_backend.model.dto.UserDTO;
@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}

	

	
	
	public List<User> getUsers(){
		return userRepo.findAll().stream()
				.filter(u -> !u.isDeleted())
				.collect(Collectors.toList());
	}
	
	public Optional<User> getUserById(Long id){
		return userRepo.findById(id);
	}
	
	public User getUserByEmail(String email) {
		Optional <User> user = userRepo.findByEmail(email);
        if(user.isPresent()){
        	return user.get();
        }
        return null;
    }
	
	public User addUser(UserDTO userDto) {
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getEmail());
		user.setPassword(encoder().encode(userDto.getPassword()));
		user.setDeleted(false);
		user.setLastName(userDto.getLastName());
		user.setRole("USER");
		return userRepo.save(user);
	}
	
	public void removeUser(Long id) {
		User us = userRepo.findById(id).get();
		if(us != null) {
			us.setDeleted(true);
			userRepo.save(us);
		}
	}
	
	public void updateUser(Long id,User user) {
		Optional<User> us = userRepo.findById(id);
		if(us.isPresent()) {
			us.get().setEmail(user.getEmail());
			us.get().setPassword(user.getPassword());
			us.get().setFirstName(user.getFirstName());
			us.get().setLastName(user.getLastName());
			userRepo.save(us.get());
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.getUser(username);
		if(user == null) {
			throw new UsernameNotFoundException("Could not find user with email " + username);
		}
		return new UserPrincipal(user);
	}





	public User addUserAdmin(UserDTO userDto) {
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setPassword(encoder().encode(userDto.getPassword()));
		user.setDeleted(false);
		user.setLastName(userDto.getLastName());
		user.setRole(userDto.getRole().toUpperCase());
		return userRepo.save(user);
	}
	
	
	
	

}
