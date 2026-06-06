package com.sibusiso.taskmanager.taskmanager.service;

import com.sibusiso.taskmanager.taskmanager.dto.RegisterRequest;
import com.sibusiso.taskmanager.taskmanager.model.Role;
import com.sibusiso.taskmanager.taskmanager.model.User;
import com.sibusiso.taskmanager.taskmanager.repository.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


import java.util.Optional;
/**
 *
 * @author ramph
 */
@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

   public void registerUser(RegisterRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
        throw new RuntimeException("Username already taken");
    }

    User user = new User();
    user.setFullName(request.getFullName());
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    // Roles
    if (request.getRoles() == null || request.getRoles().isEmpty()) {
        user.setRoles(Collections.singletonList(Role.USER));
    } else {
        user.setRoles(request.getRoles());
    }

    //Set verification token + expiry
    user.setVerificationToken(UUID.randomUUID().toString());
    user.setVerificationTokenExpiry(Instant.now().plus(24, ChronoUnit.HOURS));
    user.setEnabled(true);

    userRepository.save(user);

    // TODO: Send verification email with token link
}

public boolean verifyUser(String token) {
    User user = userRepository.findByVerificationToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid token"));

    if (user.getVerificationTokenExpiry().isBefore(Instant.now())) {
        throw new RuntimeException("Verification token expired");
    }

    user.setEnabled(true);
    user.setVerificationToken(null);
    user.setVerificationTokenExpiry(null);

    userRepository.save(user);
    return true;
}

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username));
    }
}
