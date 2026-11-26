package com.LedgerFlow.domain;

import java.math.BigDecimal;

public interface MoneyErrors {
  record MissingAmount() implements MoneyErrors {
  }

  record NegativeAmount(BigDecimal amount) implements MoneyErrors {
  }

  record MissingCurrency() implements MoneyErrors {
  }

  record CurrencyMismatch(String Expected, String Actual) implements MoneyErrors {
  }

  record ArithmeticError() implements MoneyErrors {
  }
}
