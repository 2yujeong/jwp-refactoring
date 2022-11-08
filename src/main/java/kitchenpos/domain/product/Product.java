package kitchenpos.domain.product;

import java.math.BigDecimal;
import java.util.Objects;
import kitchenpos.domain.Price;

public class Product {

    private final Long id;
    private final String name;
    private final Price price;

    public Product(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = new Price(price);
    }

    public Product(String name, BigDecimal price) {
        this(null, name, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price.getAmount();
    }
}