package com.ulxsth.efteconomysystem.db;

import java.util.UUID;

public record MoneysDto(UUID uniqueId, int amount) {
    public MoneysDto {
        if(amount < 0) {
            throw new IllegalArgumentException("amountは整数値である必要があります");
        }
    }

    public MoneysDto setAmount(int newAmount) {
        return new MoneysDto(
                this.uniqueId,
                newAmount
        );
    }

    public MoneysDto addAmount(int addAmount) {
        return new MoneysDto(
                this.uniqueId,
                this.amount + addAmount
        );
    }

    public MoneysDto decAmount(int decAmount) {
        return new MoneysDto(
                this.uniqueId,
                this.amount - decAmount
        );
    }
}
