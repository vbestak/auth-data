package hr.vbestak.authclient.service;

import hr.vbestak.authclient.entity.User;
import hr.vbestak.authclient.dto.UserUpdateCommand;
import hr.vbestak.authclient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void update(Long id, UserUpdateCommand userUpdateCommand){
        User user = userRepository.findById(id).get();
        User userUpdate = mapUserUpdateCommand(user, userUpdateCommand);
        userRepository.save(userUpdate);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("User not found for email: " + email));
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found for id: " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException("User not found for: " + s));
    }

    public User mapUserUpdateCommand(User user, UserUpdateCommand userUpdateCommand){
        user.setUsername(userUpdateCommand.getUserName());
        user.setFirstName(userUpdateCommand.getFirstName());
        user.setLastName(userUpdateCommand.getLastName());
        user.setEmail(userUpdateCommand.getEmail());
        return user;
    }
}
