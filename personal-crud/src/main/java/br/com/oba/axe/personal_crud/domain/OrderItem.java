package br.com.oba.axe.personal_crud.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "TB_ITEMS")
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal price;

    public OrderItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.price = product.getPrice();
    }

    public void incrementQuantity(Integer quantityToAdd) {
        this.quantity += quantityToAdd;
        this.price = this.product.getPrice().multiply(new BigDecimal(this.quantity));
    }
}
