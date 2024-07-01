CREATE OR REPLACE FUNCTION order_service.get_order_with_details_by_id(order_id_function UUID)
    RETURNS TABLE
            (
                id              UUID,
                order_number    VARCHAR(5),
                order_sum       DOUBLE PRECISION,
                order_date      DATE,
                order_recipient VARCHAR(255),
                order_address   VARCHAR(255),
                order_payment   VARCHAR(255),
                order_delivery  VARCHAR(255),
                product_article INTEGER,
                product_name    VARCHAR(255),
                product_amount  INTEGER,
                product_cost    DOUBLE PRECISION
            )
AS
$$
BEGIN
    RETURN QUERY SELECT os.id,
                        os.order_number,
                        os.order_sum,
                        os.order_date,
                        os.order_recipient,
                        os.order_address,
                        os.order_payment,
                        os.order_delivery,
                        od.product_article,
                        od.product_name,
                        od.product_amount,
                        od.product_cost
                 FROM order_service.orders AS os
                          INNER JOIN  order_service.order_details od on os.id = od.order_id
                 WHERE os.id = order_id_function;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION order_service.get_order_by_date_and_sum(order_date_function DATE, order_sum_function DOUBLE PRECISION)
    RETURNS TABLE
            (
                id              UUID,
                order_number    VARCHAR(5),
                order_sum       DOUBLE PRECISION,
                order_date      DATE,
                order_recipient VARCHAR(255),
                order_address   VARCHAR(255),
                order_payment   VARCHAR(255),
                order_delivery  VARCHAR(255),
                product_article INTEGER,
                product_name    VARCHAR(255),
                product_amount  INTEGER,
                product_cost    DOUBLE PRECISION
            )
AS
$$
BEGIN
    RETURN QUERY SELECT os.id,
                        os.order_number,
                        os.order_sum,
                        os.order_date,
                        os.order_recipient,
                        os.order_address,
                        os.order_payment,
                        os.order_delivery,
                        od.product_article,
                        od.product_name,
                        od.product_amount,
                        od.product_cost
                 FROM (SELECT *
                       FROM order_service.orders os2
                       WHERE os2.order_date = order_date_function
                         AND os2.order_sum > order_sum_function) AS os
                          INNER JOIN order_service.order_details od on os.id = od.order_id;

END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION order_service.filter_order_by_article_and_date(product_article_function INTEGER,
                                                                          product_date_start DATE,
                                                                          product_date_end DATE)
    RETURNS TABLE
            (
                id              UUID,
                order_number    VARCHAR(5),
                order_sum       DOUBLE PRECISION,
                order_date      DATE,
                order_recipient VARCHAR(255),
                order_address   VARCHAR(255),
                order_payment   VARCHAR(255),
                order_delivery  VARCHAR(255),
                product_article INTEGER,
                product_name    VARCHAR(255),
                product_amount  INTEGER,
                product_cost    DOUBLE PRECISION
            )
AS
$$
BEGIN
    RETURN QUERY SELECT os.id,
                        os.order_number,
                        os.order_sum,
                        os.order_date,
                        os.order_recipient,
                        os.order_address,
                        os.order_payment,
                        os.order_delivery,
                        od.product_article,
                        od.product_name,
                        od.product_amount,
                        od.product_cost
                 FROM order_service.orders AS os
                          INNER JOIN order_service.order_details od on os.id = od.order_id
                 WHERE os.id NOT IN (SELECT DISTINCT od.order_id
                                     FROM order_service.order_details od
                                     WHERE od.product_article = product_article_function)
                   AND os.order_date BETWEEN product_date_start AND product_date_end;

END;
$$ LANGUAGE plpgsql;