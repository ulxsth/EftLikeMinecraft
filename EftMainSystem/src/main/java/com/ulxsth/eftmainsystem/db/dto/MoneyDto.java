package com.ulxsth.eftmainsystem.db.dto;

import java.util.UUID;

public record MoneyDto(UUID uniqueId, int amount) {
    public MoneyDto {
        if(amount < 0) {
            throw new IllegalArgumentException("amountは整数値である必要があります");
        }
    }

    public MoneyDto setAmount(int newAmount) {
        return new MoneyDto(
                this.uniqueId,
                newAmount
        );
    }

    public MoneyDto addAmount(int addAmount) {
        return new MoneyDto(
                this.uniqueId,
                this.amount + addAmount
        );
    }

    public MoneyDto decAmount(int decAmount) {
        return new MoneyDto(
                this.uniqueId,
                this.amount - decAmount
        );
    }
}
