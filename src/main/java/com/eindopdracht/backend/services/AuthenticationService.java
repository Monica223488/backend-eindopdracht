package com.eindopdracht.backend.services;

import com.eindopdracht.backend.dtos.AuthResponseDto;
import com.eindopdracht.backend.dtos.LoginRequestDto;
import com.eindopdracht.backend.dtos.RegisterRequestDto;
import com.eindopdracht.backend.exceptions.BadRequestException;
import com.eindopdracht.backend.models.Role;
import com.eindopdracht.backend.models.User;
import com.eindopdracht.backend.repositories.RoleRepository;
import com.eindopdracht.backend.repositories.UserRepository;
import com.eindopdracht.backend.security.CustomUserDetailsService;
import com.eindopdracht.backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 CustomUserDetailsService userDetailsService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDto register(RegisterRequestDto dto) {
        if (userRepository.findByEmail(dto.email).isPresent()){
            throw new BadRequestException("Email already exists");
        }
        User user = new User();
        user.setName(dto.name);
        user.setEmail(dto.email);
        user.setPassword(passwordEncoder.encode(dto.password));
        user.setRoles(Set.of(Role.valueOf(dto.role.toUpperCase())));

        Role role = roleRepository.findByName(dto.role.toUpperCase())
                .orElseThrow(() -> new BadRequestException("Role not found"));

        user.setRoles(Set.of(role));

        UserRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDto(token);
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email, dto.password)
        );

        userDetails userDetails = userDetailsService.loadUserByUsername(dto.email);
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDto(token);
    }
}
