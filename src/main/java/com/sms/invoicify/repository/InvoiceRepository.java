package com.sms.invoicify.repository;

import com.sms.invoicify.models.InvoiceEntity;
import com.sms.invoicify.utilities.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
  InvoiceEntity findByNumber(long number);

  List<InvoiceEntity> findByCompanyNameAndPaymentStatus(
      String companyName, PaymentStatus paymentStatus, Pageable pageable);

  Page<InvoiceEntity> findAll(Pageable pageable);

  @Query(
      value =
          "SELECT entity FROM InvoiceEntity entity WHERE entity.creationDate < :oneYearAgo AND entity.paymentStatus = :paymentStatus")
  List<InvoiceEntity> findYearOldandPaid(
      @Param("oneYearAgo") LocalDate oneYearAgo,
      @Param("paymentStatus") PaymentStatus paymentStatus);
}
