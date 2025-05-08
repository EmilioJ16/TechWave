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
INSERT INTO category (id, name) VALUES (3, 'Consoles');
INSERT INTO category (id, name) VALUES (4, 'Others');

-- Inserta productos asociados a la categoría "Mobile"
INSERT INTO product (id, name, description, image, category_id) VALUES 
  (1, 'Iphone 16 pro', 'Iphone-16-pro-128gb-titanio-desierto-libre', 'public/images/mobiles/apple-iphone-16-pro-128gb-titanio-desierto-libre.png', 1),
  (2, 'Iphone 16 pro-max', 'Iphone-16-pro-max-256gb-titanio-negro-libre', 'public/images/mobiles/apple-iphone-16-pro-max-256gb-titanio-negro-libre.png', 1),
  (3, 'Honor 200 Pro', 'Honor-200-pro-5g-12-512gb-blanco-libre', 'public/images/mobiles/honor-200-pro-5g-12-512gb-blanco-libre.png', 1),
  (4, 'Nothing Phone 3a Pro', 'Nothing-phone-3a-pro-smartphone-12-256gb-68-amoled-fhd-120hz-50mp-5000mah-45w-android-15-negro', 'public/images/mobiles/nothing-phone-3a-pro-smartphone-12-256gb-68-amoled-fhd-120hz-50mp-5000mah-45w-android-15-negro.png', 1),
  (5, 'Samsung Galaxy A25', 'Samsung-galaxy-a25-5g-8-256gb-azul-libre', 'public/images/mobiles/samsung-galaxy-a25-5g-8-256gb-azul-libre.png', 1),
  (6, 'Samsung Galaxy S25 Ultra', 'Samsung-galaxy-s25-ultra-smartphone-con-ia-almacenamiento-1tb-bateria-5000mah-titanio-negro', 'public/images/mobiles/samsung-galaxy-s25-ultra-smartphone-con-ia-almacenamiento-1tb-bateria-5000mah-titanio-negro-comprar.png', 1),
  (7, 'Xiaomi Redmi 14C', 'Xiaomi-redmi-14c-6-128gb-negro-libre', 'public/images/mobiles/xiaomi-redmi-14c-6-128gb-negro-libre.png', 1),
  (8, 'Xiaomi Redmi Note 13 8+256GB', 'Xiaomi-redmi-note-13-8-256gb-negro-libre', 'public/images/mobiles/xiaomi-redmi-note-13-8-256gb-negro-libre.png', 1),
  (9, 'Lenovo ideapad slim 3', 'lenovo-ideapad-slim-3-gen-8-15irh8-intel-core-i5-13420h-16gb-1tb-ssd-156', 'public/images/laptops/lenovo-ideapad-slim-3-gen-8-15irh8-intel-core-i5-13420h-16gb-1tb-ssd-156.png', 2),
  (10, 'Acer Aspire 3 15 A315-44P', 'acer-aspire-3-15-a315-44p-amd-ryzen-7-5700u-16gb-512-gb-ssd-156', 'public/images/laptops/acer-aspire-3-15-a315-44p-amd-ryzen-7-5700u-16gb-512-gb-ssd-156.png', 2),
  (11, 'Alurin Go Start 15"', 'alurin-go-start-intel-pentium-n4020-8gb-256gb-ssd-156', 'public/images/laptops/alurin-go-start-intel-pentium-n4020-8gb-256gb-ssd-156-comprar.png', 2),
  (12, 'Alurin Go Start 14"', 'alurin-go-start-n24-intel-pentium-n4020-8gb-256gb-ssd-14', 'public/images/laptops/alurin-go-start-n24-intel-pentium-n4020-8gb-256gb-ssd-14.png', 2),
  (13, 'Asus TUF Gaming A15 FA506NCR', 'asus-tuf-gaming-a15-fa506ncr-hn006-amd-ryzen-7-7435hs-16gb-512gb-ssd-rtx-3050-156', 'public/images/laptops/asus-tuf-gaming-a15-fa506ncr-hn006-amd-ryzen-7-7435hs-16gb-512gb-ssd-rtx-3050-156.png', 2),
  (14, 'Asus VivoBook Go 15 E1504FA', 'asus-vivobook-go-15-e1504fa-nj1354-amd-ryzen-5-7520u-16gb-512gb-ssd-156', 'public/images/laptops/asus-vivobook-go-15-e1504fa-nj1354-amd-ryzen-5-7520u-16gb-512gb-ssd-156.png', 2),
  (15, 'Dell Precision 5770', 'dell-precision-5770-intel-core-i7-12700h-16gb-512gb-ssd-rtx-a2000-17-tactil', 'public/images/laptops/dell-precision-5770-intel-core-i7-12700h-16gb-512gb-ssd-rtx-a2000-17-tactil-f6ed3493-25b4-48d5-860e-0e980da12600.png', 2),
  (16, 'Denver NBQ-10125ES', 'denver-nbq-10125es-intel-atom-x5-z8350-4gb-64gb-emmc-101-tactil', 'public/images/laptops/denver-nbq-10125es-intel-atom-x5-z8350-4gb-64gb-emmc-101-tactil.png', 2),
  (17, 'Prixton Flex Pro', 'prixton-flex-pro-intel-celeron-n4020-4gb-128gb-116-tactil', 'public/images/laptops/prixton-flex-pro-intel-celeron-n4020-4gb-128gb-116-tactil-foto.png', 2),
  (18, 'Xbox Series X', 'Consola - Microsoft Xbox Series X, 1 TB SSD, Negro + Mando inalámbrico', 'public/images/consoles/xbox.png', 3),
  (19, 'PlayStation 5 Digital Edition', 'sony-playstation-5-digital-edition-blanco', 'public/images/consoles/play5.png', 3),
  (20, 'Nintendo Switch', 'Consola - Nintendo Switch OLED, 7", Joy-Con, 64 GB, Blanco', 'public/images/consoles/switch.png', 3),
  (21, 'Majorana', 'Majorana X100 - auriculares inalámbricos con cancelación activa de ruido, sonido Hi-Fi de 24 bits y autonomía de 40 horas', 'public/images/others/majorana.png', 4),
  (22, 'Meta Quest 2', 'Meta Quest 2 - casco VR inalámbrico con resolución 1832x1920 por ojo, seguimiento inside-out y procesador Snapdragon XR2', 'public/images/others/meta-quest.png', 4),
  (23, 'Microondas Ultra', 'Microondas Ultra - el mejor microondas del mundo con función de teletransportación de alimentos y programación por voz con IA integrada', 'public/images/others/microondas-ultra.png', 4),
  (24, 'SimuladorF1 Pro', 'SimuladorF1 Pro - cockpit de carrera con pedales hidráulicos, volante con retroalimentación de fuerza realista y pantalla panorámica 180°', 'public/images/others/simuladorf1.png', 4),
  (25, 'Simulador Moto S', 'Simulador Moto S - plataforma giratoria 360°, vibración dinámica y manillar con resistencia ajustable para experiencia de moto realista', 'public/images/others/simulador-moto.png', 4);
  

-- Inserta precios asociados a los productos (histórico de precios)
INSERT INTO price (id, product_id, price, valid_from) VALUES 
    (1, 1, 1199.99, '2024-03-26'), 
    (2, 2, 1399.99, '2024-03-26'), 
    (3, 3, 899.99, '2024-03-26'), 
    (4, 4, 749.99, '2024-03-26'), 
    (5, 5, 399.99, '2024-03-26'), 
    (6, 6, 1599.99, '2024-03-26'), 
    (7, 7, 199.99, '2024-03-26'), 
    (8, 8, 299.99, '2024-03-26'),
    (9, 9, 699.99, '2024-03-26'),
    (10, 10, 649.99, '2024-03-26'),
    (11, 11, 399.99, '2024-03-26'),
    (12, 12, 379.99, '2024-03-26'),
    (13, 13, 1199.99, '2024-03-26'),
    (14, 14, 549.99, '2024-03-26'),
    (15, 15, 2499.99, '2024-03-26'),
    (16, 16, 229.99, '2024-03-26'),
    (17, 17, 329.99, '2024-03-26'),
    (18, 18, 499.99, '2024-03-26'),
    (19, 19, 459.99, '2024-03-26'),
    (20, 20, 299.99, '2024-03-26'),
    (21, 21, 10029.99, '2024-03-26'),
    (22, 22, 349.99, '2024-03-26'),
    (23, 23, 199999.99, '2024-03-26'),
    (24, 24, 2499.99, '2024-03-26'),
    (25, 25, 2999.99, '2024-03-26');
