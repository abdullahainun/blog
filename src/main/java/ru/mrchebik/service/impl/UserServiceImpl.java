package ru.mrchebik.service.impl;

import com.google.common.collect.Sets;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrchebik.model.User;
import ru.mrchebik.repository.RoleRepository;
import ru.mrchebik.repository.UserRepository;
import ru.mrchebik.service.UserService;

import javax.annotation.Resource;

/**
 * Created by mrchebik on 14.01.17.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User add(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Sets.newHashSet(roleRepository.findOne(1L)));
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void changeUsername(String email, String username) {
        userRepository.changeUsername(email, username);
    }

    @Override
    public void changePassword(String email, String password) {
        String bcrypt = bCryptPasswordEncoder.encode(password);
        User user = userRepository.findByEmail(email);
        userRepository.changePassword(user.getUserId(), bcrypt);
    }

    @Override
    public void changeEmail(String email, String newEmail) {
        userRepository.changeEmail(email, newEmail);
    }

    @Override
    public User findById(long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User findByUsername(final String username) {
        return userRepository.findByName(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void remove(long id) {
        userRepository.delete(id);
    }
}
