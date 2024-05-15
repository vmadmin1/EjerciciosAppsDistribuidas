const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql');

const app = express();
const PORT = 3000;

app.use(bodyParser.json());

// Configura la conexión a la base de datos
const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'resultados'
});

connection.connect(err => {
  if (err) {
    console.error('Error de conexión a la base de datos:', err);
    return;
  }
  console.log('Conexión a la base de datos establecida');
});

// Endpoint POST para realizar una operación
app.post('/operacion', (req, res) => {
  // Verificar si se recibieron los datos esperados
  if (!req.body || !req.body.operacion || !req.body.operando1) {
    return res.status(400).json({ error: 'Se esperaban datos de operación.' });
  }

  // Extraer los datos de la solicitud
  const { operacion, operando1, operando2 } = req.body;

  // Verificar si la operación es personalizada
  if (operacion === 'custom') {
    // Evaluar la expresión personalizada
    try {
      const resultado = eval(operando1);
      const operacionString = `${operando1.replace(/\+/g, ' + ').replace(/-/g, ' - ').replace(/\*/g, ' * ').replace(/\//g, ' / ')} = ${resultado}`;
      // Imprimir la operación y el resultado en la consola del servidor
      console.log(`Operacion: '${operacionString}'`);
      // Mandar el resultado al cliente
      return res.json({ resultado });
    } catch (error) {
      return res.status(400).json({ error: 'Expresión matemática no válida.' });
    }
  }

  // Construir la cadena de texto de la operación y el resultado
  let operacionString;
  let resultado;
  switch (operacion) {
    case 'suma':
      operacionString = `${operando1} + ${operando2}`;
      resultado = operando1 + operando2;
      break;
    case 'resta':
      operacionString = `${operando1} - ${operando2}`;
      resultado = operando1 - operando2;
      break;
    case 'multiplicacion':
      operacionString = `${operando1} * ${operando2}`;
      resultado = operando1 * operando2;
      break;
    case 'division':
      if (operando2 === 0) {
        return res.status(400).json({ error: 'No se puede dividir por cero.' });
      }
      operacionString = `${operando1} / ${operando2}`;
      resultado = operando1 / operando2;
      break;
    case 'potencia':
      operacionString = `${operando1} ^ ${operando2}`;
      resultado = Math.pow(operando1, operando2);
      break;
    case 'raiz':
      if (operando1 < 0) {
        return res.status(400).json({ error: 'No se puede calcular la raíz cuadrada de un número negativo.' });
      }
      operacionString = `√${operando1}`;
      resultado = Math.sqrt(operando1);
      break;
    default:
      return res.status(400).json({ error: 'Operación no válida.' });
  }

  // Insertar el resultado en la base de datos{
  const sql = 'INSERT INTO resultados (operacion, operando1, operando2, resultado) VALUES (?, ?, ?, ?)';
  connection.query(sql, [operacion, operando1, operando2, resultado], (err, result) => {
    if (err) {
      console.error('Error al insertar el resultado en la base de datos:', err);
      return res.status(500).json({ error: 'Error interno del servidor' });
    }

    if (result.affectedRows === 1) {
      console.log('Resultado insertado en la base de datos');
      // Enviar respuesta al cliente después de la inserción exitosa
      res.json({ resultado });
    } else {
      console.error('Error al insertar el resultado en la base de datos');
      return res.status(500).json({ error: 'Error interno del servidor' });
    }
  });

});

// Iniciar el servidor
app.listen(PORT, () => {
  console.log(`Servidor escuchando en http://192.168.1.131:${PORT}`);
});
