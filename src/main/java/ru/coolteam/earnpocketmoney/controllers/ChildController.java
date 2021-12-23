package ru.coolteam.earnpocketmoney.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.coolteam.earnpocketmoney.dtos.ChildDto;
import ru.coolteam.earnpocketmoney.model.Bonus;
import ru.coolteam.earnpocketmoney.model.Child;
import ru.coolteam.earnpocketmoney.model.Task;
import ru.coolteam.earnpocketmoney.services.BonusService;
import ru.coolteam.earnpocketmoney.services.ChildService;
import ru.coolteam.earnpocketmoney.services.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v2/children")
public class ChildController {
    private final ChildService childService;
    private final TaskService taskService;
    private final BonusService bonusService;

    // Получить список детей
    @GetMapping()
    public List<ChildDto> getAllChildren() {
        return childService.findAll().stream().map(ChildDto::new).collect(Collectors.toList());
    }

    // Поиск ребенка по логину
    @GetMapping("/getLogin")
    public Optional<ChildDto> getChildDtoByLogin(@RequestParam String login){
        return childService.findByLogin(login).map(ChildDto::new);
    }

    // Создание ребенка
    @GetMapping("/create")
    public Optional<ChildDto> create (@RequestParam String login,
                                      @RequestParam String password
                                      ){
        Child child = childService.createChild(login, password);
        return Optional.of(new ChildDto(child));
    }

    // TODO сделать такое же удаление всех сущностей
    // Удаление ребенка
    @DeleteMapping("/delete")
    public boolean delete (@RequestParam String login,
                           @RequestParam String password){
        Child child = childService.findByLogin(login).get();
       List<Task> tasks = taskService.findAllByChild(child);
       if(tasks.size()!=0){
           for (Task t: tasks ) {
               taskService.updateChild(t.getTitle(),null);
           }
       }
       List<Bonus> bonuses = bonusService.findAllByChild(child);
        if(bonuses.size()!=0){
            for (Bonus b: bonuses ) {
                bonusService.updateChild(b.getTitle(),null);
            }
        }
        childService.delete(login,password);
        return true;
    }

    //обновление кошелька
    //Кидаем новый остаток
    @GetMapping("/updateWallet")
    public Optional<ChildDto> updateWallet (@RequestParam String login,
                                            @RequestParam Integer wallet){
        return Optional.of(new ChildDto(childService.updateWalletChild(login, wallet)));
    }

    //Изменить пароль
    @GetMapping("/updatePassword")
    public Optional<ChildDto> updatePassword (@RequestParam String login,
                                              @RequestParam String lastPass,
                                              @RequestParam String futurePass){
        return Optional.of(new ChildDto(childService.updatePasswordChild(login, lastPass, futurePass)));
    }
}
