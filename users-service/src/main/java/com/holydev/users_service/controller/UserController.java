package com.holydev.users_service.controller;

import com.holydev.users_service.Dto.UserDto;
import com.holydev.users_service.feign.dto.OrderDto;
import com.holydev.users_service.feign.repository.OrderFeign;
import com.holydev.users_service.model.User;
import com.holydev.users_service.repository.UserRepository;
import com.holydev.users_service.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final OrderFeign orderFeign;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    @GetMapping("/user/{userId}")
    public UserResponse getUsersByUserId(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<OrderDto> orders = orderFeign.getOrdersByUserId(userId);
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), orders);
    }
}
