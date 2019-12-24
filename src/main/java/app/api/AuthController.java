package app.api;

import app.dto.rq.LoginRq;
import app.dto.rs.LoginRs;
import app.dto.rs.RegisterRs;
import app.model.User;
import app.service.AuthService;
import app.service.UserService;
import app.util.CurrentUser;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @PostMapping("/login")
    public LoginRs handle_login(@RequestBody LoginRq rq){
        return authService.login(rq.getUsername(), rq.getPassword(), rq.isRemember())
                .map(t -> new LoginRs("OK", t, CurrentUser.get()))
                .orElse(new LoginRs("ERR", null, null));
    }

    @PostMapping("/register")
    public RegisterRs handle_register(@RequestBody User user){
        boolean result = authService.register_new(user);
        return result ? RegisterRs.Ok() : RegisterRs.AlreadyExists();
    }

    @PutMapping("/user")
    public LoginRs handle_put(@RequestBody User user) {
        return authService.update_user(user)
                .map(t -> new LoginRs("OK", t, CurrentUser.get()))
                .orElse(new LoginRs("ERR", null, null));
    }
}
