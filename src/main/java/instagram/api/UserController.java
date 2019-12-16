package instagram.api;

import instagram.model.User;
import instagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(
            UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User save(@RequestBody User employeeObj) {
        userService.save(employeeObj);
        return employeeObj;
    }

    @GetMapping("/user")
    public List<User> get(){
        return userService.get();
    }

    @GetMapping("/user/{id}")
    public User get(@PathVariable int id) {
        User userObj = userService.get(id);
        if(userObj == null) {
            throw new RuntimeException("User not found for the Id:"+id);
        }
        return userObj;
    }

    @PutMapping("/user")
    public User update(@RequestBody User userObj) {
        userService.save(userObj);
        return userObj;
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "User has been deleted with id:"+id;
    }

}
