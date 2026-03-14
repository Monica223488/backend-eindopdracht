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

        Role role = roleRepository.findByName(dto.role.toUpperCase())
                .orElseThrow(() -> new BadRequestException("Role not found"));

        User user = User.create(
                dto.name,
                dto.email,
                passwordEncoder.encode(dto.password),
                Set.of(role)
        );


        user.setRoles(Set.of(role));

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponseDto(token);
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email, dto.password)
        );

        String token = jwtService.generateToken(dto.email);

        return new AuthResponseDto(token);
    }
}
