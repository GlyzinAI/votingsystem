package ru.project.votingsystem.web.rest.user;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.project.votingsystem.AuthorizedUser;
import ru.project.votingsystem.model.User;
import ru.project.votingsystem.service.UserService;
import ru.project.votingsystem.to.UserTo;

import javax.validation.Valid;
import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static ru.project.votingsystem.util.UserUtil.createNewFromUser;

@RestController
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private static final Logger log = getLogger(UserController.class);

    static final String REST_URL = "/users";
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserTo get() {
        int userId = ((AuthorizedUser) getContext().getAuthentication().getPrincipal()).getId();
        log.info("get user {}", userId);
        return createNewFromUser(userService.get(userId));
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        int userId = ((AuthorizedUser) getContext().getAuthentication().getPrincipal()).getId();
        log.info("delete user {}", userId);
        userService.delete(userId);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo) {
        int id = ((AuthorizedUser) getContext().getAuthentication().getPrincipal()).getId();
        log.info("update user {}", id);
        userService.update(userTo);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        log.info("register new user");
        User created = userService.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}