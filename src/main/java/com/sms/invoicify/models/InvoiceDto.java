package com.sms.invoicify.models;

import com.sms.invoicify.utilities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDto {
  @NotNull private Long number;
  private LocalDate creationDate;
  private LocalDate lastModifiedDate;
  private List<Item> items;
  private String companyName;
  private PaymentStatus paymentStatus;
  private Double totalCost;
}
