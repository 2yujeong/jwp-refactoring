package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Collections;
import java.util.List;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TableServiceTest extends ServiceTest {

    @Test
    void 테이블_생성_메소드는_입력받은_테이블을_저장한다() {
        // given
        OrderTable orderTable = new OrderTable(4, false);

        // when
        OrderTable savedOrderTable = tableService.create(orderTable);

        // then
        List<OrderTable> tables = tableService.list();
        assertThat(tables).extracting(OrderTable::getId, OrderTable::getNumberOfGuests, OrderTable::isEmpty)
                .contains(tuple(savedOrderTable.getId(), 4, false));
    }

    @Test
    void 테이블_목록_조회_메소드는_모든_테이블을_조회한다() {
        // given
        OrderTable orderTable1 = 테이블을_저장한다(4);
        OrderTable orderTable2 = 빈_테이블을_저장한다();

        // when
        List<OrderTable> tables = tableService.list();

        // then
        assertThat(tables).extracting(OrderTable::getId, OrderTable::getNumberOfGuests, OrderTable::isEmpty)
                .contains(
                        tuple(orderTable1.getId(), orderTable1.getNumberOfGuests(), orderTable1.isEmpty()),
                        tuple(orderTable2.getId(), orderTable2.getNumberOfGuests(), orderTable2.isEmpty()));
    }

    @Nested
    class changeEmpty_메소드는 extends ServiceTest {

        @ParameterizedTest
        @ValueSource(booleans = {true, false})
        void 테이블이_비어_있는지_여부를_변경한다(boolean isEmpty) {
            // given
            OrderTable savedOrderTable = 테이블을_저장한다(4);

            // TODO: 0
            OrderTable emptyOrderTable = new OrderTable(0, isEmpty);

            // when
            tableService.changeEmpty(savedOrderTable.getId(), emptyOrderTable);

            // then
            OrderTable findTable = tableService.list()
                    .stream()
                    .filter(table -> table.getId().equals(savedOrderTable.getId()))
                    .findFirst()
                    .orElseThrow();

            assertThat(findTable.isEmpty()).isEqualTo(isEmpty);
        }

        @Test
        void 주문_상태가_계산_완료_상태가_아니라면_예외가_발생한다() {
            // given
            OrderTable savedOrderTable = 테이블을_저장한다(4);

            Order savedOrder = 주문을_저장한다(savedOrderTable);
            Order order = new Order(null, OrderStatus.COOKING.name(), null, Collections.emptyList());
            orderService.changeOrderStatus(savedOrder.getId(), order);

            OrderTable emptyOrderTable = new OrderTable(0, true);

            // when & then
            assertThatThrownBy(() -> tableService.changeEmpty(savedOrderTable.getId(), emptyOrderTable));
        }

        @Test
        void 테이블_그룹_id를_가지고_있다면_예외가_발생한다() {
            // given
            OrderTable savedOrderTable1 = 빈_테이블을_저장한다();
            OrderTable savedOrderTable2 = 빈_테이블을_저장한다();
            TableGroup tableGroup = 테이블_그룹을_저장한다(savedOrderTable1, savedOrderTable2);

            OrderTable emptyOrderTable = new OrderTable(tableGroup.getId(), 0, true);

            // when & then
            assertThatThrownBy(() -> tableService.changeEmpty(savedOrderTable1.getId(), emptyOrderTable))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 존재하지_않는_테이블이라면_예외가_발생한다() {
            // given
            OrderTable emptyOrderTable = new OrderTable(0, true);

            // when & then
            assertThatThrownBy(() -> tableService.changeEmpty(0L, emptyOrderTable))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class changeNumberOfGuests_메소드는 extends ServiceTest {

        @Test
        void 테이블_손님_수를_변경한다() {
            // given
            OrderTable savedOrderTable = 테이블을_저장한다(4);

            OrderTable orderTable = new OrderTable(2, false);

            // when
            tableService.changeNumberOfGuests(savedOrderTable.getId(), orderTable);

            // then
            OrderTable findTable = tableService.list()
                    .stream()
                    .filter(table -> table.getId().equals(savedOrderTable.getId()))
                    .findFirst()
                    .orElseThrow();

            assertThat(findTable.getNumberOfGuests()).isEqualTo(2);
        }

        @Test
        void 존재하지_않는_테이블이라면_예외가_발생한다() {
            // given
            OrderTable orderTable = new OrderTable(5, false);

            // when & then
            assertThatThrownBy(() -> tableService.changeNumberOfGuests(0L, orderTable))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 비어있는_테이블이라면_예외가_발생한다() {
            // given
            OrderTable savedOrderTable = 빈_테이블을_저장한다();

            OrderTable orderTable = new OrderTable(5, true);

            // when & then
            assertThatThrownBy(() -> tableService.changeNumberOfGuests(savedOrderTable.getId(), orderTable))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 변경하려는_손님_수가_음수라면_예외가_발생한다() {
            // given
            OrderTable savedOrderTable = 테이블을_저장한다(4);

            OrderTable orderTable = new OrderTable(-1, false);

            // when & then
            assertThatThrownBy(() -> tableService.changeNumberOfGuests(savedOrderTable.getId(), orderTable))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
