const mysql = require('mysql');

// Configuración de la conexión a la base de datos
const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root', // El nombre de usuario de tu base de datos
  password: '', // La contraseña de tu base de datos
  database: 'resultados' // El nombre de tu base de datos en XAMPP
});

// Establecer la conexión a la base de datos
connection.connect(err => {
  if (err) {
    console.error('Error al conectar a la base de datos:', err);
    return;
  }
  console.log('Conexión a la base de datos establecida');
});


// Consulta SQL para insertar datos
const sql = 'INSERT INTO pruebas VALUES (2,3,4)';

// Ejecutar la consulta SQL para insertar datos
connection.query(sql, (error, results, fields) => {
  if (error) {
    console.error('Error al insertar datos:', error);
    return;
  }
  console.log('Datos insertados correctamente');
});

// Cerrar la conexión a la base de datos después de realizar la inserción
connection.end();
