package app.service;

import app.beans.NullAwareBeanUtilsBean;
import app.dao.UserRepository;
import app.dto.rs.LoginRs;
import app.model.User;
import app.security.jwt.Const;
import app.security.jwt.JwtTokenService;
import app.security.ud.MyUserDetails;
import app.util.CurrentUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@Service
public class AuthService {
    private final AuthenticationManager am;
    private final JwtTokenService tp;
    private final UserRepository repo;
    private final PasswordEncoder enc;


    public AuthService(AuthenticationManager am, JwtTokenService tp, UserRepository repo, PasswordEncoder enc) {
        this.am = am;
        this.tp = tp;
        this.repo = repo;
        this.enc = enc;
    }

    public boolean register_new(User user) {
        Optional<User> found = repo.findByUsername(user.getUsername());
        if (!found.isPresent()) {
            String[] roles = {"USER"};
            user.setRoles(roles);
            user.setPassword(enc.encode(user.getPassword()));
            repo.save(user);
        }
        return !found.isPresent();
    }

    public Optional<String> login(String username, String password, boolean remember) {
        return Optional.of(am.authenticate(new UsernamePasswordAuthenticationToken(username, password)))
                .filter(Authentication::isAuthenticated)
                .map(a -> {
                    SecurityContextHolder.getContext().setAuthentication(a); return a; })
                .map(a -> (MyUserDetails) a.getPrincipal())
                .map(ud-> tp.generateToken(ud.getId(), remember))
                .map(t-> Const.AUTH_BEARER + t);
    }

    public Optional<String> update_user(User user) {
        Optional<User> old_user = repo.findById(CurrentUser.get().getId());
        return old_user.map(u-> {
            NullAwareBeanUtilsBean bean = new NullAwareBeanUtilsBean();
            try {
                bean.copyProperties(u,user);
                if(user.getPassword() != null) u.setPassword(enc.encode(user.getPassword()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            repo.save(u);
            return u; })
            .flatMap(u->login(u.getUsername(), u.getPassword(), false));

    }
}
