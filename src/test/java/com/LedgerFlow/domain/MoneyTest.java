package com.LedgerFlow.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
class MoneyTest {

  private final Currency KES = Currency.getInstance("KES");

  @Nested
  @DisplayName("Validation")
  class ValidationsTest {

    @Test
    @DisplayName("validate correct Currency and amount")
    void shouldValidateCorrectCurrencyAndAmount(){
      var result = Money.validate(KES,BigDecimal.valueOf(1000));
      assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("reject missing amount")
    void shouldRejectMissingAmount() {
      var result = Money.validate(KES,null);
      assertThat(result.isInvalid()).isTrue();
      assertThat(result.getError()).anyMatch(error -> error instanceof MoneyErrors.MissingAmount);
    }

    @Test
    @DisplayName("reject negative amount")
    void shouldRejectNegativeAmount() {
      var result = Money.validate(KES,BigDecimal.valueOf(-100.00));
      assertThat(result.isInvalid()).isTrue();
      assertThat(result.getError()).anyMatch(error -> error instanceof MoneyErrors.NegativeAmount);
    }

    @Test
    @DisplayName("reject missing Currency")
    void shouldRejectMissingCurrency(){
      var result=Money.validate(null,BigDecimal.valueOf(1000));
      assertThat(result.isInvalid()).isTrue();
      assertThat(result.getError()).anyMatch(error -> error instanceof MoneyErrors.MissingCurrency);
    }
  }
}