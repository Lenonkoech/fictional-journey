package com.LedgerFlow.domain;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(
    Currency currency,
    BigDecimal amount
) {
  public Money(BigDecimal amount, Currency currency) {
    this(currency, amount);
  }

  public static Validation<MoneyErrors, BigDecimal> validateAmount(BigDecimal amount) {
    return Option.of(amount)
        .toValidation((MoneyErrors) new MoneyErrors.MissingAmount())
        .flatMap(value ->
            value.signum() < 0 ? Validation.invalid(new MoneyErrors.NegativeAmount(value)) : Validation.valid(value));
  }

  public static Validation<MoneyErrors, Currency> validateCurrency(Currency currency) {
    return Option.of(currency).toValidation(new MoneyErrors.MissingCurrency());
  }

  public static Validation<Seq<MoneyErrors>, Money> validate(Currency currency, BigDecimal amount) {
    return Validation.combine(validateAmount(amount), validateCurrency(currency))
        .ap((validAmount, validCurrency) ->
            new Money(validAmount, validCurrency)
        );
  }
}
