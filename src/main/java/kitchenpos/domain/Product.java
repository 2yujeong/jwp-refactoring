package kitchenpos.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final Long id;
    private final String name;
    private final BigDecimal price;

    public Product(Long id, String name, BigDecimal price) {
        validatePrice(price);

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(String name, BigDecimal price) {
        this(null, name, price);
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal calculatePrice(long quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
