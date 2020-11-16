USE gestiondecontabilidad;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE ingreso;
TRUNCATE TABLE item_presupuesto;
TRUNCATE TABLE presupuesto;
TRUNCATE TABLE item_egreso;
TRUNCATE TABLE tipo_item;
TRUNCATE TABLE item;
TRUNCATE TABLE egreso_categoria_operacion;
TRUNCATE TABLE categoria_operacion;
TRUNCATE TABLE criterio_operacion;
TRUNCATE TABLE egreso;
TRUNCATE TABLE proveedor;
TRUNCATE TABLE pago;
TRUNCATE TABLE medio_de_pago;
TRUNCATE TABLE usuario;
TRUNCATE TABLE entidad_base;
TRUNCATE TABLE categoria_juridica;
TRUNCATE TABLE entidad_juridica;
TRUNCATE TABLE organizacion;
TRUNCATE TABLE categoria_x_sector;
TRUNCATE TABLE categoria_empresa;
TRUNCATE TABLE sector;
SET FOREIGN_KEY_CHECKS = 1;

START TRANSACTION;

INSERT INTO sector (sector_id, nombre) VALUES
('1', 'Construcción'),
('2', 'Servicios'),
('3', 'Comercio'),
('4', 'Industria y Minería'),
('5', 'Agropecuario');

INSERT INTO categoria_empresa (categoria_id, nombre) VALUES
('1', 'Micro'),
('2', 'Pequeña'),
('3', 'Mediana - Tramo 1'),
('4', 'Mediana - Tramo 2');

INSERT INTO pais (pais_id, pais_code, name, moneda_id) VALUES
('25', 'EEUU', 'Estados Unidos', '7')
ON DUPLICATE KEY UPDATE
pais_code = VALUES(pais_code),
name = VALUES(name),
moneda_id = VALUES(moneda_id);

INSERT INTO provincia (provincia_id, name, pais_id) VALUES
('411','Nueva York', '25'),
('412', 'Ciudad de México', '13')
ON DUPLICATE KEY UPDATE
name = VALUES(name),
pais_id = VALUES(pais_id);

INSERT INTO ciudad (ciudad_id, name, provincia_id) VALUES
('16447', 'Brooklyn', '411'),
('16448', 'Ciudad de México', '412')
ON DUPLICATE KEY UPDATE
name = VALUES(name),
provincia_id = VALUES(provincia_id);

INSERT INTO categoria_x_sector (categoria_id, sector_id, monto_max, monto_min, personal_max, personal_min) VALUES
('3', '1', '643710000.0', '115370000.0', '200', '45'),
('4', '1', '965460000.0', '643710000.0', '590', '200'),
('2', '2', '59710000.0', '9900000.0', '30', '7');

/*ORGANIZACION, si la organizacion esta compuesta por entidades cambiar la relacion organizacion-entidad a uno a muchos, y todo lo que afecte*/
INSERT INTO organizacion (id, nombre) VALUES
('1', 'Equipo Argentino de Antropología Forense - EAAF'), 
('2', 'Equipo Argentino de Antropología Forense - EAAF'), 
('3', 'Equipo Argentino de Antropología Forense - EAAF'), 
('4', 'Colectivo de Derechos de Infancia y Adolescencia - CDIA');

INSERT INTO categoria_juridica (tipo_juridica, id, /*codigo_osc,*/ actividad, cantidad_personal, ventas_anuales, categoria_id, sector_id) VALUES
('empresa', '1', 'Construcción', '150', '600000000.0', '3', '1'),
('empresa', '2', 'Construcción', '580', '960000000.0', '4', '1'),
('empresa', '3', 'Construcción', '240', '643710000.0', '4', '1'),
('empresa', '4', 'Servicio de Alojamiento', '8', '8000000.0', '2', '2');

/*Entidad Juridica, falta agregar localidad, nombre de la entidad(la agregue pero no en el get ni en el post), hay ciudades/paises/provincias que no estan en la lista traida de la pagina de mercado libre*/
INSERT INTO entidad_juridica (nombre_ficticio, altura, calle, /*codigo_igj,*/ cuit, /*piso,*/ razon_social, /*zipcode,*/ id, ciudad_id, pais_id, provincia_id, tipo_entidad_id) VALUES 
('Oficina Central Buenos Aires', '951', 'Av. Medrano', '30-15269857-2', 'EAAF BA', '1', '172', '1', '7', '1'),
('Oficina Central Nueva York', '720', 'Liberty Ave', '30-15789655-7', 'EAAF NY', '2', '16447', '25', '411', '2'),
('Oficina Central México', '55', 'Roberto Gayol', '30-77896583-9', 'EAAF M', '3', '16448', '13', '412', '3'),
('Surcos', '2800', 'Jerónimo Salguero', '30-25888897-8', 'Surcos CS', '4', '172', '1', '7', '4');

/*Hubo un malentendido del tp asi que la asociamos a la entidad juridica por como lo tenemos modelado*/
INSERT INTO entidad_base (nombre_ficticio, /*descripcion,*/ id, juridica_id) VALUES
('Andhes' , '1', '4');

/*Falta la columna REVISOR y USERNAME(temporalmente lo cargo en mail), y no hay mail en los datos de prueba, el usuario esta asociado a una entidad juridica*/
INSERT INTO usuario (tipo_usuario, id, contrasenia, username, apellido, nombre, /*mail,*/ organizacion_id) VALUES
('estandar', '1', 'd254a492d8dc4e91cacd0152269012c6b6179fda56d7d4194bf26f6016f8e27e', 'aroco', 'Roco', 'Alejandro', '1'),
('estandar', '2', 'e90e5d79e28558d4aa5e6e8b77359a9dbe257f30c250f6600c40ab846d07da8b', 'rrojas', 'Rojas', 'Rocío', '1'),
('estandar', '3', 'd6ff9b6954cda745c1dcfbd06a94435023ea022633a6c4129ce0d452278aa7fe', 'jazul', 'Azul', 'Julieta', '4');

INSERT INTO medio_de_pago (id, medio_de_pago) VALUES
('1', 'Efectivo'),
('2', 'Tarjeta de Crédito - 3 pagos s/i'),
('3', 'Tarjeta de Débito');

INSERT INTO pago (id, codigo_asociado, medio_id) VALUES
('1', '4509 9535 6623 3704', '2'),
('2', null, '1'),
('3', null, '1'),
('4', '5031 7557 3453 0604', '3'),
('5', null, '1'),
('6', null, '1'),
('7', null, '1'),
('8', null, '1'),
('9', null, '1'),
('10', null, '1');

INSERT INTO proveedor (proveedor_id, /*altura, calle, documento_proveedor,*/ nombre/*, piso, zipcode, ciudad_id, pais_id, provincia_id*/) VALUES
('1', 'Pinturerías Serrentino'),
('2', 'Edesur'),
('3', 'Metrogas'),
('4', 'Mitoas SA'),
('5', 'Ingeniería Comercial SRL'),
('6', 'Corralón Laprida SRL'),
('7', 'Telas ZN'),
('8', 'Pinturerías REX'),
('9', 'Pinturerías San Jorge'),
('10', 'La casa del Audio'),
('11', 'Garbarino'),
('12', 'Corralón San Juan SRL');

/*FALTAN DATOS DE DE PRUEBA*/
INSERT INTO egreso (id, fecha_carga, fecha_operacion, organizacion_id, cantidadPresupuestos, /*validado,*/ valorTotal, /*documento_comercial_id, ingreso_asociado,*/ pago_id, proveedor_id) VALUES
('1', '2020-03-10 00:00:00', '2020-03-10', '1', '3', '19952.69', '1', '1'),
('2', '2020-07-08 00:00:00', '2020-07-08', '1', null, '2100.00', '2', '2'),
('3', '2020-07-09 00:00:00', '2020-07-09', '1', null, '3500.00', '3', '3'),
('4', '2020-03-08 00:00:00', '2020-03-08', '1', null, '10800.00', '4', '4'),
('5', '2020-09-27 00:00:00', '2020-09-27', '1', '6', '8500.00', '5', '5'),
('6', '2020-10-01 00:00:00', '2020-10-01', '1', '4', '4922.40', '6', '6'),
('7', '2020-10-05 00:00:00', '2020-10-05', '1', null, '250.00', '7', '6'),
('8', '2020-07-10 00:00:00', '2020-07-10', '4', null, '1100.00', '8', '2'),
('9', '2020-07-10 00:00:00', '2020-07-10', '4', null, '800.00', '9', '3'),
('10', '2020-09-25 00:00:00', '2020-09-25', '4', null, '4200.00', '10', '7');

INSERT INTO criterio_operacion (id, descripcion, criterio_padre_id) VALUES
('1', 'Gastos de mantenimiento', null),
('2', 'Lugar de aplicación', '1'),
('3', 'Causante', null),
('4', 'Gastos generales', null),
('5', 'Elementos de oficina', null),
('6', 'Momento de utilización', null),
('7', 'Tipo de producto', null),
('8', 'Lugar de aplicación', '1'),
('9', 'Tamaño del gasto', null),
('10', 'Servicios', null),
('11', 'Elementos de uso interno', null);

INSERT INTO categoria_operacion (id, descripcion, criterio_id) VALUES
('1', 'Fachada', '1'),
('2', 'Interior', '2'),
('3', 'Humedad', '3'),
('4', 'Servicios generales', '4'),
('5', 'Muebles y últiles', '5'),
('6', 'Coffe Break', '6'),
('7', 'Electrónicos ', '7'),
('8', 'Exterior', '8'),
('9', 'Grande', '9'),
('10', 'Servicios de Luz', '10'),
('11', 'Servicios de Gas', '10'),
('12', 'Necesarios', '11');

INSERT INTO egreso_categoria_operacion (Egreso_id, categorias_id) VALUES 
('1', '1'),
('1', '2'),
('1', '3'),
('2', '4'),
('3', '4'),
('4', '5'),
('4', '6'),
('5', '5'),
('5', '7'),
('6', '1'),
('6', '8'),
('6', '9'),
('7', '1'),
('8', '10'),
('9', '11'),
('10', '12');

INSERT INTO tipo_item (id, nombre) VALUES
('1', 'Producto'),
('2', 'Servicio');

INSERT INTO item (id, descripcion, tipo_item_id) VALUES
('1', 'PINTURA Z10 LATEX SUPERCUBRITIVO 20L', '1'),
('2', 'PINTURA LOXON FTES IMPERMEABILIZANTE 10L', '1'),
('3', 'PINTURA BRIKOL PISOS NEGRO 4L', '1'),
('4', 'FACTURA SERVICIO DE LUZ PERIODO JUNIO 2020', '2'),
('5', 'FACTURA SERVICIO DE GAS PERIODO JUNIO 2020', '2'),
('6', 'PAVA ELECTRICA SMARTLIFE 1,5 LTS 1850W', '1'),
('7', 'CAFETERA SMARTLIFE 1095 ACERO INOX.', '1'),
('8', 'TELEFONOS INALAMBRICOS MOTOROLA DUO BLANCO', '1'),
('9', 'CEMENTO LOMA NEGRA', '1'),
('10', 'ARENA FINA EN BOLSÓN X M30', '1'),
('11', 'HIERRO ACINDAR', '1'),
('12', 'BLOQUE LADRILLO CEMENTO', '1'),
('13', 'CORTINAS BLACKOUT VINILICO 2 PAÑOS', '1');

INSERT INTO item_egreso (id, cantidad, precio, egreso_id, item_id) VALUES
('1', '1', '9625.00', '1', '1'),
('2', '1', '6589.40', '1', '2'),
('3', '1', '3738.29', '1', '3'),
('4', '1', '2100.00', '2', '4'),
('5', '1', '3500.00', '3', '5'),
('6', '3', '4500.00', '4', '6'),
('7', '2', '6300.00', '4', '7'),
('8', '2', '8500.00', '5', '8'),
('9', '10', '704.40', '6', '9'),
('10', '5', '3100.00', '6', '10'),
('11', '4', '891.00', '6', '11'),
('12', '800', '227.00', '6', '12'),
('13', '800', '250.00', '7', '12'),
('14', '1', '1100.00', '8', '4'),
('15', '1', '800.00', '9', '5'),
('16', '5', '4200.00', '10', '13');

INSERT INTO presupuesto (id, fecha_vigente, /*operacion_numero,*/ valor_total, /*documento_comercial_id,*/ egreso_asociado, proveedor_id) VALUES
('1', '2020-02-25', '21451.60', '1', '8'),
('2', '2020-02-26', '20300.80', '1', '9'),
('3', '2020-02-27', '19952.69', '1', '1'),
('4', '2020-09-10', '8950.00', '1', '10'),
('5', '2020-09-11', '8830.00', '1', '11'),
('6', '2020-09-12', '8500.00', '1', '5'),
('7', '2020-09-15', '4980.00', '1', '12'),
('8', '2020-09-15', '4922.40', '1', '6');

INSERT INTO item_presupuesto (id, cantidad, precio, item_id, item_egreso_id, presupuesto_id) VALUES
('1', '1', '9900.80', '1', '1', '1'),
('2', '1', '7200.00', '2', '2', '1'),
('3', '1', '4350.80', '3', '3', '1'),
('4', '1', '9610.50', '1', '1', '2'),
('5', '1', '6590.30', '2', '2', '2'),
('6', '1', '4100.00', '3', '3', '2'),
('7', '1', '9625.00', '1', '1', '3'),
('8', '1', '6589.40', '2', '2', '3'),
('9', '1', '3738.29', '3', '3', '3'),
('10', '2', '8950.00', '8', '8', '4'),
('11', '2', '8830.00', '8', '8', '5'),
('12', '2', '8500.00', '8', '8', '6'),
('13', '10', '715.00', '9', '9', '7'),
('14', '5', '3150.00', '10', '10', '7'),
('15', '4', '880.00', '11', '11', '7'),
('16', '800', '235.00', '12', '12', '7'),
('17', '10', '704.40', '9', '9', '8'),
('18', '5', '3100.00', '10', '10', '8'),
('19', '4', '891.00', '11', '11', '8'),
('20', '800', '227.00', '12', '12', '8');

INSERT INTO ingreso (id, fecha_carga, fecha_operacion, organizacion_id, descripcion, fecha_acep_egreso, montoTotal, moneda_id) VALUES
('1', '2020-02-25 00:00:00', '2020-02-25', '1', 'Donación de terceros', '2020-03-20', '20000.00', '1'),
('2', '2020-05-02 00:00:00', '2020-05-02', '1', 'Donación de Rimoli SA', '2020-08-03', '10000.00', '1'),
('3', '2020-08-03 00:00:00', '2020-08-03', '1', 'Donación de Gran Imperio', '2020-10-01', '980000.00', '1'),
('4', '2020-05-01 00:00:00', '2020-05-01', '4', 'Donación Gabino SRL', '2020-10-01', '10000.00', '4');

COMMIT;