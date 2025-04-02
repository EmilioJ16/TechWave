-- Desactivar restricciones de clave foránea temporalmente
SET FOREIGN_KEY_CHECKS = 0;

-- Vaciar y reiniciar las tablas antes de insertar nuevos datos
DELETE FROM cart_item; -- Asegura eliminar primero los elementos del carrito
DELETE FROM price;
DELETE FROM product;
DELETE FROM category;

-- Reiniciar los IDs autoincrementales
ALTER TABLE cart_item AUTO_INCREMENT = 1;
ALTER TABLE price AUTO_INCREMENT = 1;
ALTER TABLE product AUTO_INCREMENT = 1;
ALTER TABLE category AUTO_INCREMENT = 1;

-- Volver a activar restricciones de clave foránea
SET FOREIGN_KEY_CHECKS = 1;

-- Inserta las categorías
INSERT INTO category (id, name) VALUES (1, 'Mobile');
INSERT INTO category (id, name) VALUES (2, 'Laptop');

-- Inserta productos asociados a la categoría "Mobile"
INSERT INTO product (id, name, description, image, category_id) VALUES 
  (1, 'Iphone 16 pro', 'Iphone-16-pro-128gb-titanio-desierto-libre', 'public/images/mobiles/apple-iphone-16-pro-128gb-titanio-desierto-libre.png', 1),
  (2, 'Iphone 16 pro-max', 'Iphone-16-pro-max-256gb-titanio-negro-libre', 'public/images/mobiles/apple-iphone-16-pro-max-256gb-titanio-negro-libre.png', 1),
  (3, 'Honor 200 Pro', 'Honor-200-pro-5g-12-512gb-blanco-libre', 'public/images/mobiles/honor-200-pro-5g-12-512gb-blanco-libre.png', 1),
  (4, 'Nothing Phone 3a Pro', 'Nothing-phone-3a-pro-smartphone-12-256gb-68-amoled-fhd-120hz-50mp-5000mah-45w-android-15-negro', 'public/images/mobiles/nothing-phone-3a-pro-smartphone-12-256gb-68-amoled-fhd-120hz-50mp-5000mah-45w-android-15-negro.png', 1),
  (5, 'Samsung Galaxy A25', 'Samsung-galaxy-a25-5g-8-256gb-azul-libre', 'public/images/mobiles/samsung-galaxy-a25-5g-8-256gb-azul-libre.png', 1),
  (6, 'Samsung Galaxy S25 Ultra', 'Samsung-galaxy-s25-ultra-smartphone-con-ia-almacenamiento-1tb-bateria-5000mah-titanio-negro', 'public/images/mobiles/samsung-galaxy-s25-ultra-smartphone-con-ia-almacenamiento-1tb-bateria-5000mah-titanio-negro-comprar.png', 1),
  (7, 'Xiaomi Redmi 14C', 'Xiaomi-redmi-14c-6-128gb-negro-libre', 'public/images/mobiles/xiaomi-redmi-14c-6-128gb-negro-libre.png', 1),
  (8, 'Xiaomi Redmi Note 13 8+256GB', 'Xiaomi-redmi-note-13-8-256gb-negro-libre', 'public/images/mobiles/xiaomi-redmi-note-13-8-256gb-negro-libre.png', 1);

-- Inserta precios asociados a los productos (histórico de precios)
INSERT INTO price (id, product_id, price, valid_from) VALUES 
    (1, 1, 1199.99, '2024-03-26'), 
    (2, 2, 1399.99, '2024-03-26'), 
    (3, 3, 899.99, '2024-03-26'), 
    (4, 4, 749.99, '2024-03-26'), 
    (5, 5, 399.99, '2024-03-26'), 
    (6, 6, 1599.99, '2024-03-26'), 
    (7, 7, 199.99, '2024-03-26'), 
    (8, 8, 299.99, '2024-03-26');
