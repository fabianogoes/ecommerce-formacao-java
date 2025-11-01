package com.ecommerce.api.dto;

import com.ecommerce.model.User;

public record UserResponseDTO(
        Long id,
        String email,
        String fullName,
        User.UserStatus staus
) {
    public static UserResponseDTO fromUser(User user) {
        String fullName = user.getFirstName() + " " + user.getLastName();
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                fullName,
                user.getStatus()
        );
    }
}