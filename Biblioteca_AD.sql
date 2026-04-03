CREATE TABLE IF NOT EXISTS libro (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    anio INT,
    disponible BOOLEAN NOT NULL DEFAULT true
);
TRUNCATE TABLE libro RESTART IDENTITY;
INSERT INTO libro (titulo, isbn, anio, disponible) VALUES
('Harry Potter y la Piedra filosofal', '9780132350884', 1999, true),
('Los Pilares de la Tierra', '9780134685991', 1998, true),
('El Hobbit', '9780201633610', 1964, false);
SELECT * FROM libro ORDER BY id;
-- Tabla usuario
CREATE TABLE IF NOT EXISTS usuario (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    email VARCHAR(120) UNIQUE
);
-- Tabla prestamo
CREATE TABLE IF NOT EXISTS prestamo (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuario(id),
    libro_id BIGINT NOT NULL REFERENCES libro(id),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    fecha_devolucion DATE,
    CONSTRAINT ck_fechas CHECK (fecha_fin >= fecha_inicio)
);
-- Evitar dos préstamos ACTIVOS del mismo libro
CREATE UNIQUE INDEX IF NOT EXISTS uq_prestamo_libro_activo
ON prestamo(libro_id)
WHERE fecha_devolucion IS NULL;
-- Datos de prueba: usuarios (alumnado)
INSERT INTO usuario(nombre,email) VALUES
('Oumnia', 'oumnia@correo.local'),
('Maria Fernanda', 'maria.fernanda@correo.local'),
('Saad', 'saad@correo.local'),
('Adrian Fernandez', 'adrian.fernandez@correo.local'),
('David Gallardo', 'david.gallardo@correo.local'),
('Silvia', 'silvia@correo.local'),
('Victor', 'victor@correo.local'),
('Javier Faustino', 'javier.faustino@correo.local'),
('Javier Herranz', 'javier.herranz@correo.local'),
('Daniel Jimenez', 'daniel.jimenez@correo.local'),
('Raul', 'raul@correo.local'),
('Juanma', 'juanma@correo.local'),
('Mario Losada', 'mario.losada@correo.local'),
('Javier Machuca', 'javier.machuca@correo.local'),
('Javier Montilla', 'javier.montilla@correo.local'),
('Jorge Moreno', 'jorge.moreno@correo.local'),
('Alvaro Moreno', 'alvaro.moreno@correo.local'),
('Salma', 'salma@correo.local'),
('Mateo', 'mateo@correo.local'),
('Daniel Muñoz', 'daniel.munoz@correo.local'),
('Victor Pavon', 'victor.pavon@correo.local'),
('Jhonny', 'jhonatan@correo.local'),
('Victor Reina', 'victor.reina@correo.local'),
('Mario Rodriguez', 'mario.rodriguez@correo.local'),
('Alvaro Sanchez', 'alvaro.sanchez@correo.local'),
('Juan Torres', 'juan.torres@correo.local'),
('Adrian Torres', 'adrian.torres@correo.local')
ON CONFLICT DO NOTHING;
-- Datos de prueba: libros (fantasía y ciencia ficción)
-- Nota: se asume tabla libro(titulo, isbn, anio, disponible). Ajusta si tu tabla
difiere.
INSERT INTO libro(titulo, isbn, anio, disponible) VALUES
('Dune', 'SCI-0001', 1965, true),
('Fundación', 'SCI-0002', 1951, true),
('Neuromante', 'SCI-0003', 1984, true),
('Hyperion', 'SCI-0004', 1989, true),
('El juego de Ender', 'SCI-0005', 1985, true),
('La mano izquierda de la oscuridad', 'SCI-0006', 1969, true),
('Snow Crash', 'SCI-0007', 1992, true),
('Ready Player One', 'SCI-0008', 2011, true),
('El problema de los tres cuerpos', 'SCI-0009', 2006, true),
('Blade Runner (¿Sueñan los androides...? )', 'SCI-0010', 1968, true),
('El nombre del viento', 'FAN-0001', 2007, true),
('El temor de un hombre sabio', 'FAN-0002', 2011, true),
('Mistborn: El imperio final', 'FAN-0003', 2006, true),
('El pozo de la ascensión', 'FAN-0004', 2007, true),
('El héroe de las eras', 'FAN-0005', 2008, true),
('Elantris', 'FAN-0006', 2005, true),
('La comunidad del anillo', 'FAN-0007', 1954, true),
('Las dos torres', 'FAN-0008', 1954, true),
('El retorno del rey', 'FAN-0009', 1955, true),
('Juego de tronos', 'FAN-0010', 1996, true),
('Choque de reyes', 'FAN-0011', 1998, true),
('Tormenta de espadas', 'FAN-0012', 2000, true),
('La rueda del tiempo: El ojo del mundo', 'FAN-0014', 1990, true),
('Harry Potter y la cámara secreta', 'FAN-0016', 1998, true),
('La princesa prometida', 'FAN-0017', 1973, true),
('El archivo de las tormentas: El camino de los reyes', 'FAN-0018', 2010, true),
('La historia interminable', 'FAN-0019', 1979, true),
('La quinta estación', 'SCI-0011', 2015, true)
ON CONFLICT DO NOTHING;