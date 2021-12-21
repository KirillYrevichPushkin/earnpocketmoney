package ru.coolteam.earnpocketmoney.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.coolteam.earnpocketmoney.model.Bonus;
import ru.coolteam.earnpocketmoney.model.Child;

import java.util.List;
import java.util.Optional;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

    Optional <Bonus> findFirstBonusByTitle (String title);
    Bonus findFirstByTitle (String title);
    List<Bonus> findAllByChild (Child child);



    @Query("SELECT i FROM Bonus i where i.title = :name ")
    Bonus findBonusByName (String name);

  /*  @Modifying
    @Query("update Bonus b set b.price = :price where b.title = :title")
    void updateBonusPrice(@Param(value = "price") Integer price, @Param(value = "title") String title);
*/


}
