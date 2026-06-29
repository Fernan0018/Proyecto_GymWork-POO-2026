CREATE TABLE Historial_SoporteGW (
    ID_Historial INT IDENTITY(1,1) PRIMARY KEY,
    ID_Ticket INT,
    Asunto_Ticket NVARCHAR(255),
    Remitente NVARCHAR(255),
    Categoria NVARCHAR(255),
    Id_Maquinas INT,
    Id_Sede INT,
    Prioridad NVARCHAR(50),
    Estado_Ticket NVARCHAR(50),
    Tiempo_inicio TIME,
    Tiempo_fin TIME,
    Dia_Resolucion DATE,
    Minutos_Resolucion INT,
    Fecha_Archivado DATETIME DEFAULT GETDATE()
);
