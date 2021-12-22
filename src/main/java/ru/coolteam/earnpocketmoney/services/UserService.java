package ru.coolteam.earnpocketmoney.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.coolteam.earnpocketmoney.models.Parent;
import ru.coolteam.earnpocketmoney.models.Child;
import ru.coolteam.earnpocketmoney.models.UserEntity;
import ru.coolteam.earnpocketmoney.repositories.ChildRepository;
import ru.coolteam.earnpocketmoney.repositories.ParentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity saveUser(UserEntity userEntity) {

        if(userEntity.getRole().getRole().equals("ROLE_PARENT")){
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return     parentRepository.save((Parent) userEntity);
        }else{
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return     childRepository.save((Child) userEntity);
        }
    }

    public UserEntity findByLogin(String login) {
        if(parentRepository.findParentByLogin(login).isPresent()){
            return parentRepository.findParentByLogin(login).get();
        }else if (childRepository.findChildByLogin(login).isPresent()){
            return childRepository.findChildByLogin(login).get();
        } else {
            return null;
        }
    }

    public UserEntity findByLoginAndPassword(String login, String password) {
        UserEntity userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    public List<UserEntity> findAll(){
        List<UserEntity> userEntityList = null;
        if(!parentRepository.findAll().isEmpty()){
        userEntityList.addAll(parentRepository.findAll().stream().map((UserEntity.class::cast)).collect(Collectors.toList()));}
        if(!childRepository.findAll().isEmpty()){
            userEntityList.addAll(childRepository.findAll().stream().map((UserEntity.class::cast)).collect(Collectors.toList())) ;}
        return userEntityList;
    }

}