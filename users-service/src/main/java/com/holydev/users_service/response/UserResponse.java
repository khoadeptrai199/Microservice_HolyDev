package com.holydev.users_service.response;

import com.holydev.users_service.feign.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    List<OrderDto> orders;
}
