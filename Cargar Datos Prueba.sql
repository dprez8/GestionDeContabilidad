USE gestiondecontabilidad;
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
('25', 'EEUU', 'Estados Unidos', '7');

INSERT INTO provincia (provincia_id, name, pais_id) VALUES
('411','Nueva York', '25'),
('412', 'Ciudad de México', '13');

INSERT INTO ciudad (name, provincia_id) VALUES
('16447', 'Brooklyn', '411'),
('16448', 'Ciudad de México', '412');

INSERT INTO categoria_x_sector (categoria_id, sector_id, monto_max, monto_min, personal_max, personal_min) VALUES
('3', '1', '643710000.0', '115370000.0', '200', '45'),
('4', '1', '965460000.0', '643710000.0', '590', '200'),
('2', '2', '59710000.0', '9900000.0', '30', '7');

/*ORGANIZACION, si la organizacion esta compuesta por entidades cambiar la relacion organizacion-entidad a uno a muchos, y todo lo que afecte*/
INSERT INTO organizacion (id, nombre) VALUES
('1', 'Equipo Argentino de Antropología Forense - EAAF'), 
('2', 'Equipo Argentino de Antropología Forense - EAAF'), 
('3', 'Equipo Argentino de Antropología Forense - EAAF'), 
('4', 'Colectivo de Derechos de Infancia y Adolescencia - CDIA'),
('5', 'Colectivo de Derechos de Infancia y Adolescencia - CDIA');

/*Entidad Juridica, falta agregar localidad, nombre de la entidad(la agregue pero no en el get ni en el post), hay ciudades/paises/provincias que no estan en la lista traida de la pagina de mercado libre*/
INSERT INTO entidad_juridica (nombre_ficticio, altura, calle, /*codigo_igj,*/ cuit, /*piso,*/ razon_social, /*zipcode,*/ id, ciudad_id, pais_id, provincia_id, tipo_entidad_id) VALUES 
('Oficina Central Buenos Aires', '951', 'Av. Medrano', '30-15269857-2', 'EAAF BA', '1', '172', '1', '7', '1'),
('Oficina Central Nueva York', '720', 'Liberty Ave', '30-15789655-7', 'EAAF NY', '2', '16447', '25', '411', '2'),
('Oficina Central México', '55', 'Roberto Gayol', '30-77896583-9', 'EAAF M', '3', '16448', '13', '412', '3'),
('Surcos', '2800', 'Jerónimo Salguero', '30-25888897-8', 'Surcos CS', '4', '172', '1', '7', '4');

INSERT INTO categoria_juridica (tipo_juridica, id, /*codigo_osc,*/ actividad, cantidad_personal, ventas_anuales, categoria_id, sector_id) VALUES
('empresa', '1', 'Construcción', '150', '600000000.0', '3', '1'),
('empresa', '2', 'Construcción', '580', '960000000.0', '4', '1'),
('empresa', '3', 'Construcción', '240', '643710000.0', '4', '1'),
('empresa', '4', 'Servicio de Alojamiento', '8', '8000000.0', '2', '2');

/*Hubo un malentendido del tp asi que la asociamos a la entidad juridica por como lo tenemos modelado*/
INSERT INTO entidad_base (nombre_ficticio, /*descripcion,*/ id, juridica_id) VALUES
('Andhes' , '1', '4');

/*Falta la columna REVISOR y USERNAME, y no hay mail en los datos de prueba, el usuario esta asociado a una entidad juridica*/
INSERT INTO usuario (tipo_usuario, id, contrasenia, /*mail,*/ nombre, organizacion_id) VALUES
('estandar', '1', 'd254a492d8dc4e91cacd0152269012c6b6179fda56d7d4194bf26f6016f8e27e', 'Roco, Alejandro', '1'),
('estandar', '2', 'e90e5d79e28558d4aa5e6e8b77359a9dbe257f30c250f6600c40ab846d07da8b', 'Rojas, Rocío', '1'),
('estandar', '3', 'd6ff9b6954cda745c1dcfbd06a94435023ea022633a6c4129ce0d452278aa7fe', 'Azul, Julieta', '4');