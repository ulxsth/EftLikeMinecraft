package com.ulxsth.eftmainsystem.db.dto;

import java.util.Date;
import java.util.UUID;

public record PlayerDto(UUID uniqueId, String name, Date firstLoggedIn, Date recentlyLoggedIn) {
    public PlayerDto {
        if(name.length() == 0 || name.length() > 16) {
            throw new IllegalArgumentException("nameの長さは1文字以上16文字以下である必要があります");
        }
    }

    public PlayerDto setName(String name) {
        return new PlayerDto(
                uniqueId(),
                name,
                firstLoggedIn(),
                recentlyLoggedIn()
        );
    }
}