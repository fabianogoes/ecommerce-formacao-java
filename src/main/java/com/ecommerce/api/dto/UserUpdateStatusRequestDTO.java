package com.ecommerce.api.dto;

import com.ecommerce.model.User;

public record UserUpdateStatusRequestDTO(
        User.UserStatus status
) {
}
