package ru.coolteam.earnpocketmoney.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.coolteam.earnpocketmoney.model.Child;

import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {

    Optional<Child> findChildByLogin (String login);
    Optional<Child> findChildByLoginAndPassword (String login, String password);


}
