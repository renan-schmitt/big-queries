package br.com.renanschmitt.medium.big_queries.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class ReportDto {
  private final Long orderId;
  private final LocalDate date;
  private final String customerName;
  private final BigDecimal totalAmount;
  private final String currency;
  private final String status;
  private final String paymentMethod;
  private final List<Item> items;

  @Data
  public static class Item {
    private final Long productId;
    private final String productName;
    private final Integer quantity;
    private final BigDecimal price;
    private final BigDecimal totalAmount;
  }
}
