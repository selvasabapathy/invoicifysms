package com.sms.invoicify.service;

import com.sms.invoicify.exception.InvoicifyCompanyExistsException;
import com.sms.invoicify.models.Address;
import com.sms.invoicify.models.Company;
import com.sms.invoicify.models.CompanyEntity;
import com.sms.invoicify.models.CompanySummary;
import com.sms.invoicify.repository.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

  @Mock CompanyRepository companyRepository;

  @InjectMocks CompanyService companyService;

  @Test
  @DisplayName("Create new Company Test")
  void createCompany() throws InvoicifyCompanyExistsException {
    Company companyDto =
        Company.builder()
            .companyName("Test1")
            .address(
                Address.builder()
                    .street("100 N State Street")
                    .city("Chicago")
                    .state("IL")
                    .zipCode("60601")
                    .build())
            .contactName("Name1")
            .title("Title1")
            .phoneNumber("312-777-7777")
            .build();

    companyService.createCompany(companyDto);

    verify(companyRepository)
        .save(
            CompanyEntity.builder()
                .companyName("Test1")
                .address(
                    Address.builder()
                        .street("100 N State Street")
                        .city("Chicago")
                        .state("IL")
                        .zipCode("60601")
                        .build())
                .contactName("Name1")
                .title("Title1")
                .phoneNumber("312-777-7777")
                .build());
    verify(companyRepository).findById("Test1");
    verifyNoMoreInteractions(companyRepository);
  }

  @Test
  @DisplayName("Get All Company")
  void getAllCompany() {
    when(companyRepository.findAll())
        .thenReturn(
            List.of(
                CompanyEntity.builder()
                    .companyName("Test1")
                    .address(
                        Address.builder()
                            .street("100 N State Street")
                            .city("Chicago")
                            .state("IL")
                            .zipCode("60601")
                            .build())
                    .contactName("Name1")
                    .title("Title1")
                    .phoneNumber("312-777-7777")
                    .build()));

    List<Company> companyFromService = companyService.fetchAllCompany();

    Company companyDtoExpected =
        Company.builder()
            .companyName("Test1")
            .address(
                Address.builder()
                    .street("100 N State Street")
                    .city("Chicago")
                    .state("IL")
                    .zipCode("60601")
                    .build())
            .contactName("Name1")
            .title("Title1")
            .phoneNumber("312-777-7777")
            .build();

    assertThat(companyFromService).isEqualTo(List.of(companyDtoExpected));

    verify(companyRepository).findAll();
    verifyNoMoreInteractions(companyRepository);
  }

  @Test
  @DisplayName("Get Companies Summary")
  void getCompaniesSummary() {
    when(companyRepository.findAll())
        .thenReturn(
            List.of(
                CompanyEntity.builder()
                    .companyName("Test1")
                    .address(
                        Address.builder()
                            .street("100 N State Street")
                            .city("Chicago")
                            .state("IL")
                            .zipCode("60601")
                            .build())
                    .contactName("Name1")
                    .title("Title1")
                    .phoneNumber("312-777-7777")
                    .build()));

    List<CompanySummary> companySummaryFromService = companyService.fetchCompanySummaryView();

    CompanySummary companySummaryDtoExpected =
        CompanySummary.builder().companyName("Test1").city("Chicago").state("IL").build();

    assertThat(companySummaryFromService).isEqualTo(List.of(companySummaryDtoExpected));

    verify(companyRepository).findAll();
    verifyNoMoreInteractions(companyRepository);
  }

  @Test
  void getCompanyByName_whenCompanyExists_returnsCompany() {
    when(companyRepository.findById(any()))
        .thenReturn(
            Optional.of(
                CompanyEntity.builder()
                    .companyName("myExistingCompany")
                    .address(
                        Address.builder()
                            .street("100 N State Street")
                            .city("Chicago")
                            .state("IL")
                            .zipCode("60601")
                            .build())
                    .contactName("Name1")
                    .title("Title1")
                    .phoneNumber("312-777-7777")
                    .build()));

    Company expected = Company.builder()
            .companyName("myExistingCompany")
            .address(
                    Address.builder()
                            .street("100 N State Street")
                            .city("Chicago")
                            .state("IL")
                            .zipCode("60601")
                            .build())
            .contactName("Name1")
            .title("Title1")
            .phoneNumber("312-777-7777")
            .build();

    Company companyFromService = companyService.fetchCompanyByName("myExistingCompany");
    assertThat(companyFromService).isEqualTo(expected);

    verify(companyRepository).findById("myExistingCompany");
    verifyNoMoreInteractions(companyRepository);
  }
}
