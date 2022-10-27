package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.domain.Product;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProductServiceTest extends ServiceTest {

    @Nested
    class 상품_등록_메소드는 extends ServiceTest {

        @Test
        void 입력받은_상품을_저장한다() {
            // given
            Product newProduct = new Product("상품 1", BigDecimal.valueOf(10000));

            // when
            final Product savedProduct = productService.create(newProduct);

            // then
            List<Product> products = productService.list();
            assertThat(products)
                    .extracting(Product::getId, Product::getName, (product) -> product.getPrice().intValue())
                    .contains(tuple(savedProduct.getId(), newProduct.getName(), newProduct.getPrice().intValue()));
        }

        @Test
        void 상품_가격이_음수면_예외가_발생한다() {
            // given
            Product product = new Product("상품 1", BigDecimal.valueOf(-1));

            // when & then
            assertThatThrownBy(() -> productService.create(product))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 상품_목록_조회_메소드는_모든_상품의_id_이름_가격을_조회한다() {
        // given
        Product product1 = 상품을_저장한다("상품1", 10000);
        Product product2 = 상품을_저장한다("상품2", 20000);

        // when
        final List<Product> products = productService.list();

        // then
        assertThat(products).hasSize(2)
                .extracting(Product::getId, Product::getName, Product::getPrice)
                .contains(tuple(product1.getId(), product1.getName(), product1.getPrice()),
                        tuple(product2.getId(), product2.getName(), product2.getPrice()));
    }
}
