INSERT INTO stock (id, timestamp, quantity) VALUES ('000001', CURRENT_TIMESTAMP, 1000);
INSERT INTO stock (id, timestamp, quantity) VALUES ('000002', CURRENT_TIMESTAMP, 870);
INSERT INTO stock (id, timestamp, quantity) VALUES ('000003', '2017-01-19 03:14:07', 10);
INSERT INTO stock (id, timestamp, quantity) VALUES ('000004', '2017-01-19 03:14:07', 500);

INSERT INTO product (product_id, stock_id) VALUES ('vegetable-123', '000001');
INSERT INTO product (product_id, stock_id) VALUES ('milk-002', '000002');
INSERT INTO product (product_id, stock_id) VALUES ('beer-190', '000003' );
INSERT INTO product (product_id, stock_id) VALUES ('fruit-197', '000004');








