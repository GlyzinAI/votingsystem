package ru.project.votingsystem.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.project.votingsystem.AuthorizedUser;
import ru.project.votingsystem.model.User;
import ru.project.votingsystem.repository.UserRepository;
import ru.project.votingsystem.to.UserTo;
import ru.project.votingsystem.util.UserUtil;
import ru.project.votingsystem.util.exception.NotFoundException;

import java.util.List;

import static ru.project.votingsystem.util.UserUtil.prepareToGet;
import static ru.project.votingsystem.util.UserUtil.prepareToSave;
import static ru.project.votingsystem.util.ValidationUtil.checkNotFound;
import static ru.project.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(prepareToSave(user, passwordEncoder));
    }

    @Override
    public void delete(int userId) throws NotFoundException {
        checkNotFoundWithId(userRepository.delete(userId) != 0, userId);
    }

    @Override
    public User get(int userId) throws NotFoundException {
        return checkNotFoundWithId(userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Not found user with id = " + userId)), userId);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public void update(UserTo userTo, int userId) {
        Assert.notNull(userTo, "user must not be null");
        User user = get(userId);
        userRepository.save(prepareToSave(UserUtil.updateFromTo(user, userTo), passwordEncoder));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        prepareToGet(user);
        return new AuthorizedUser(user);
    }
}