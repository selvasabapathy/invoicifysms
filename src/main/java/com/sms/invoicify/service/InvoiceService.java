package com.sms.invoicify.service;

import com.sms.invoicify.exception.InvoicifyCompanyNotExistsException;
import com.sms.invoicify.exception.InvoicifyInvoiceExistsException;
import com.sms.invoicify.exception.InvoicifyInvoiceNotExistsException;
import com.sms.invoicify.models.Company;
import com.sms.invoicify.models.InvoiceEntity;
import com.sms.invoicify.repository.InvoiceRepository;
import com.sms.invoicify.utilities.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class InvoiceService {

  @Autowired private InvoiceRepository invoiceRepository;
  @Autowired private CompanyService companyService;

  public InvoiceEntity create(InvoiceEntity invoiceEntity) throws InvoicifyInvoiceExistsException, InvoicifyCompanyNotExistsException {
    if (null != invoiceRepository.findByNumber(invoiceEntity.getNumber())) {
      throw new InvoicifyInvoiceExistsException("Invoice exists, and can't be created again");
    }
    if (companyService.fetchCompanyByName(invoiceEntity.getCompanyName()) == null) {
      throw new InvoicifyCompanyNotExistsException("Company Does not exists. Invoice cannot be created");
    }

      BigDecimal invoiceTotalCost =
          invoiceEntity.getTotalCost() == null
              ? BigDecimal.valueOf(0).setScale(2)
              : invoiceEntity.getTotalCost();
      BigDecimal itemsTotalCost =
          invoiceEntity.getItems() == null
              ? new BigDecimal(0)
              : invoiceEntity.getItems().stream()
                  .map(i -> i.getTotalFees())
                  .reduce(BigDecimal.ZERO, BigDecimal::add);
      invoiceEntity.setTotalCost(invoiceTotalCost.add(itemsTotalCost));


    return invoiceRepository.save(invoiceEntity);
  }

  public List<InvoiceEntity> view() {
    return invoiceRepository.findAll();
  }

  public InvoiceEntity findByNumber(Long number) {
    return invoiceRepository.findByNumber(number);
  }

  public void update(InvoiceEntity invoiceEntity)
      throws InvoicifyInvoiceNotExistsException, ParseException {
    InvoiceEntity retrievedEntity = invoiceRepository.findByNumber(invoiceEntity.getNumber());
    if (null == retrievedEntity) {
      throw new InvoicifyInvoiceNotExistsException("Invoice does not exist, cannot be updated");
    }
    retrievedEntity.setLastModifiedDate(LocalDate.now());
    if (invoiceEntity.getCompanyName() != null) {
      retrievedEntity.setCompanyName(invoiceEntity.getCompanyName());
    }
    if (invoiceEntity.getPaymentStatus() != null) {
      retrievedEntity.setPaymentStatus(invoiceEntity.getPaymentStatus());
    }
    invoiceRepository.save(retrievedEntity);
  }

  public void delete() {
    LocalDate oneYearAgo = LocalDate.now().minusYears(1L);
    invoiceRepository.deleteYearOldAndPaid(oneYearAgo, PaymentStatus.PAID);
  }

  public List<InvoiceEntity> findByPaymentStatus(PaymentStatus paymentStatus) {
    return invoiceRepository.findByPaymentStatus(paymentStatus);
  }
}
