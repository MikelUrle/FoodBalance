<?php
// db/database.php
$host = '127.0.0.1';  // Asegúrate de que sea correcto
$db = 'foodbalancedb'; // Nombre de tu base de datos
$user = 'root';        // Nombre de usuario
$pass = '';            // Cambia aquí si tienes una contraseña, en tu caso parece estar vacío
$port = '3307';        // Asegúrate de que este sea el puerto correcto
$charset = 'utf8mb4';

$dsn = "mysql:host=$host;dbname=$db;charset=$charset;port=$port";
$options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES   => false,
];

try {
    $pdo = new PDO($dsn, $user, $pass, $options);
} catch (\PDOException $e) {
    throw new \PDOException($e->getMessage(), (int)$e->getCode());
}
