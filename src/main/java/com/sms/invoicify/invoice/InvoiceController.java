package com.sms.invoicify.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InvoiceController {

  @Autowired
  private InvoiceService invoiceService;

  @PostMapping("/invoices")
  public ResponseEntity<InvoiceDto> create(@RequestBody InvoiceDto invoiceDto) {

    InvoiceEntity invoiceEntity = InvoiceEntity.builder()
            .number(invoiceDto.getNumber())
            .creationDate(invoiceDto.getCreationDate())
            .companyName(invoiceDto.getCompanyName())
            .paymentStatus(invoiceDto.getPaymentStatus())
            .totalCost(invoiceDto.getTotalCost())
            .build();
    InvoiceEntity createdInvoiceEntity = invoiceService.create(invoiceEntity);

    InvoiceDto createdInvoiceDto = InvoiceDto.builder()
            .number(createdInvoiceEntity.getNumber())
            .creationDate(createdInvoiceEntity.getCreationDate())
            .companyName(createdInvoiceEntity.getCompanyName())
            .paymentStatus(createdInvoiceEntity.getPaymentStatus())
            .totalCost(createdInvoiceEntity.getTotalCost())
            .build();

    return new ResponseEntity<InvoiceDto>(createdInvoiceDto, HttpStatus.CREATED);
  }

  @GetMapping("/invoices/{type}")
  public ResponseEntity<List<InvoiceSummaryDto>> getInvoices(@PathParam("type") String type) {
    List<InvoiceSummaryDto> summaryDtoList =
        invoiceService.view().stream()
            .map(
                e ->
                    new InvoiceSummaryDto(
                        e.getNumber(), e.getCreationDate(), e.getPaymentStatus(), e.getTotalCost()))
            .collect(Collectors.toList());
    return new ResponseEntity<List<InvoiceSummaryDto>>(summaryDtoList,HttpStatus.OK);
  }

}
