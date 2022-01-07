package ru.coolteam.earnpocketmoney.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.coolteam.earnpocketmoney.models.User;
import ru.coolteam.earnpocketmoney.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;


    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByLogin(String login){
        return userRepository.findByLogin(login).orElseThrow();
    }

    public User findByLoginAndPassword (String login, String password){
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public Optional<User> findByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return Optional.empty();
    }

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepository.save(user);
    }

//    public User findByLogin(String login){
//        return userRepository.findByLogin(login);
//    }

//    public User findByLogin(String login, String password) {
//        User user = findByLogin(login);
//        if (user != null) {
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                return user;
//            }
//        }
//        return null;
//    }

//    public User findByEmail(String email) {
//        userRepository.findByEmail(email);
//        return null;
//    }
}
