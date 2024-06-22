package br.com.renanschmitt.medium.big_queries.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@ToString
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp private Instant createdOn;

  @UpdateTimestamp private Instant lastUpdatedOn;

  private Integer quantity;

  private BigDecimal price;
  private BigDecimal totalAmount;
  private BigDecimal discount;
  private BigDecimal taxAmount;
  private BigDecimal taxPercent;

  @ManyToOne Product product;

  @ManyToOne @ToString.Exclude Order order;
}
