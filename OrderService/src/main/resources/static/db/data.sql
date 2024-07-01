INSERT INTO order_service.orders
VALUES ('fcf72071-ca20-449f-b2a6-42de0e9406d2', 'abf45', 2500.0, '2024-06-23', 'Ivan Ivanov',
        '458049, Астраханская область, город Подольск, пр. Косиора, 07', 'CARD', 'PICKUP');

INSERT INTO order_service.orders
VALUES ('05873a14-4ab5-41f5-8292-d0656e3ed90b', 'fde13', 1200.0, '2024-06-23', 'Petr Petrov',
        '211298, Калининградская область, город Пушкино, пр. Гагарина, 42', 'CARD', 'COURIER');

INSERT INTO order_service.orders
VALUES ('85d205bc-8148-4ab1-8d17-db0eaf423847', 'aefwe', 1800.0, '2024-06-24', 'Anton Antonov',
        '924066, Ульяновская область, город Озёры, шоссе Сталина, 50', 'CASH', 'PICKUP');
INSERT INTO order_service.orders
VALUES ('1310194f-fd14-486f-8ec5-84627bd148de', 'ghfe3', 950.0, '2024-06-24', 'Maksim Maksimov',
        '463086, Волгоградская область, город Ступино, пр. Чехова, 07', 'CARD', 'PICKUP');
INSERT INTO order_service.orders
    VALUES ('ecdd959c-b717-4ffb-926a-e7414248be19', '12cdf', 1300.0, '2024-06-22', 'Ivan Ivanov',
        '644063, Пензенская область, город Егорьевск, пр. Гоголя, 65', 'CASH', 'COURIER');

INSERT INTO order_service.order_details(product_article, product_name, product_amount, product_cost, order_id)
    VALUES (737171, 'Футболка Oodji', 1, 950.0, '1310194f-fd14-486f-8ec5-84627bd148de');
INSERT INTO order_service.order_details(product_article, product_name, product_amount, product_cost, order_id)
    VALUES (133463, 'Брюки Oodji', 1, 2500.0, 'fcf72071-ca20-449f-b2a6-42de0e9406d2');
INSERT INTO order_service.order_details(product_article, product_name, product_amount, product_cost, order_id)
VALUES (143139, 'Майка Demix', 2, 600.0, '05873a14-4ab5-41f5-8292-d0656e3ed90b');
INSERT INTO order_service.order_details(product_article, product_name, product_amount, product_cost, order_id)
VALUES (656284, 'Кроссовки Nike', 1, 1500.0, '85d205bc-8148-4ab1-8d17-db0eaf423847');
INSERT INTO order_service.order_details(product_article, product_name, product_amount, product_cost, order_id)
VALUES (551524, 'Носки Nike', 2, 150.0, '85d205bc-8148-4ab1-8d17-db0eaf423847');
INSERT INTO order_service.order_details(product_article, product_name, product_amount, product_cost, order_id)
VALUES (663585, 'Кепка Vans', 1, 1000.0, 'ecdd959c-b717-4ffb-926a-e7414248be19');
INSERT INTO order_service.order_details(product_article, product_name, product_amount, product_cost, order_id)
VALUES (307148, 'Шнурки', 1, 100.0, 'ecdd959c-b717-4ffb-926a-e7414248be19');
INSERT INTO order_service.order_details(product_article, product_name, product_amount, product_cost, order_id)
VALUES (402282, 'Стельки', 1, 200.0, 'ecdd959c-b717-4ffb-926a-e7414248be19');
