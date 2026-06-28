-- Cambiar columnas de TIME a DATETIME2 para que guarden fecha y hora
ALTER TABLE Tiempos_TicketGW ALTER COLUMN Tiempo_inicio DATETIME2;
ALTER TABLE Tiempos_TicketGW ALTER COLUMN Tiempo_fin DATETIME2;

-- Eliminar registros existentes (tienen fechas incorrectas 1900-01-01)
DELETE FROM Tiempos_TicketGW;
