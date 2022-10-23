# 키친포스

## 요구 사항
### Product
- 상품 등록
  - 상품 이름과 가격을 요청으로 받는다.
  - 상품 가격은 0 이상이어야 한다.
- 상품 목록 조회
  - 등록된 모든 상품의 id, 이름, 가격을 조회한다.

### MenuGroup
- 메뉴 그룹 등록
  - 메뉴 그룹의 이름을 요청으로 받는다.
- 메뉴 그룹 목록 조회
  - 등록된 모든 메뉴 그룹의 id, 이름을 조회한다.

### Menu
- 메뉴 등록
  - 메뉴 이름, 가격, 메뉴가 속하는 메뉴 그룹의 id, 메뉴를 구성하는 상품들의 id와 수량을 요청으로 받는다.
  - 메뉴 가격은 0 이상이어야 한다.
  - 이미 존재하는 메뉴 그룹에 속해야 한다.
  - 메뉴를 구성하는 상품들은 모두 이미 존재하는 상품이어야 한다.
  - 메뉴를 구성하는 상품들의 각 가격의 합은 메뉴의 가격과 동일해야 한다.
- 메뉴 목록 조회
  - 등록된 모든 메뉴의 id, 이름, 가격, 속한 메뉴 그룹의 id, 각 메뉴를 구성하는 상품들의 순서, 상품 id, 수량을 조회한다.

### OrderTable
- 테이블 등록
  - 현재 테이블에 착석한 손님의 수와 비어 있는 테이블인지 여부를 입력받는다.
- 테이블 목록 조회
  - 등록된 모든 테이블의 id, 테이블 그룹 id, 손님 수, 비어 있는 테이블인지 여부를 조회한다.
- 테이블 상태 변경
  - 빈 테이블 여부를 요청으로 받는다.
    - 테이블이 비어 있다면 true, 비어 있지 않다면 false
    - 변경하려는 테이블의 주문 상태는 계산 완료 상태여야 한다.
    - 요청 대상 테이블은 이미 존재하는 테이블이어야 한다.
  - 테이블에 착석한 손님 수를 요청으로 받는다.
    - 손님 수는 0 이상이어야 한다.
    - 요청 대상 테이블은 이미 존재하는 테이블이어야 한다.
    - 요청 대상 테이블은 비어 있지 않아야 한다.

### TableGroup
- 테이블 그룹 등록
  - 그룹화하려는 테이블들의 id를 요청으로 받는다.
  - 그룹화하려는 테이블의 수는 2 이상이어야 한다.
  - 그룹화하려는 모든 테이블들은 이미 존재하는 테이블이어야 한다.
  - 비어 있는 테이블은 그룹화할 수 없다.
  - 이미 다른 테이블 그룹에 속해 있는 테이블은 새로 그룹화할 수 없다.
- 테이블 그룹 삭제
  - 요청 대상 테이블 그룹은 이미 존재하는 테이블 그룹이어야 한다.
  - 삭제하려는 그룹에 속해 있는 테이블들의 주문 상태는 모두 계산 완료 상태여야 한다.

### Order
- 주문 등록
  - 주문하는 테이블의 id, 주문하려는 각 메뉴들의 id와 수량을 요청으로 받는다.
  - 하나 이상의 메뉴를 주문해야 한다.
  - 주문하려는 모든 메뉴들은 이미 존재하는 메뉴여야 한다.
  - 주문을 하는 테이블은 이미 존재하는 테이블이어야 한다.
  - 주문을 하는 테이블의 상태는 비어 있지 않아야 한다.
- 주문 목록 조회
  - 등록된 모든 주문의 id, 테이블 id, 주문 상태, 주문 시간, 주문한 메뉴들의 목록을 조회한다.
- 주문 상태 변경
  - 상태를 변경하려는 주문이 이미 존재하는 주문이어야 한다.
  - 계산 완료된 주문의 상태는 변경할 수 없다.

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |
