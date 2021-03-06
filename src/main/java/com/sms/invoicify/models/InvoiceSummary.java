package com.sms.invoicify.models;

import com.sms.invoicify.utilities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceSummary {
  private Long number;
  private LocalDate creationDate;
  private PaymentStatus paymentStatus;
  private BigDecimal totalCost;
}
