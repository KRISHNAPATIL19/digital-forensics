
package com.forensics.digital_forensics.auth.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {}

