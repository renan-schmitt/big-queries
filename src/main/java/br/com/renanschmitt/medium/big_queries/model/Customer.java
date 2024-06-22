package br.com.renanschmitt.medium.big_queries.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "customers")
@Getter
@Setter
@ToString
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp private Instant createdOn;

  @UpdateTimestamp private Instant lastUpdatedOn;

  private String firstName;
  private String lastName;
  private String phone;
  private String title;

  private Boolean gender;

  private LocalDate birthDate;

  private String streetAddress;
  private String cityAddress;
  private String state;
  private String country;
  private String zipCode;
}
