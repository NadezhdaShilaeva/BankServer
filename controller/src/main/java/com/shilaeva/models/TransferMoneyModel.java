package com.shilaeva.models;

import java.math.BigDecimal;

public record TransferMoneyModel(String to, BigDecimal amount) {
}
