<?php
// api/index.php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: Content-Type, Authorization");
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE");
header('Content-Type: application/json');

// Verificar si es una solicitud OPTIONS (preflight)
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200); // Responde con 200 OK
    exit; // Finaliza la ejecución para solicitudes OPTIONS
}

require '../db/database.php'; // Asegúrate de que la ruta sea correcta

$request_method = $_SERVER['REQUEST_METHOD'];

switch ($request_method) {
    case 'GET':
        if (isset($_GET['id'])) {
            getComida($_GET['id']);
        } else {
            getComidas();
        }
        break;
    case 'POST':
        insertComida();
        break;
    case 'PUT':
        updateComida($_GET['id']);
        break;
    case 'DELETE':
        deleteComida($_GET['id']);
        break;
    default:
        header("HTTP/1.1 405 Method Not Allowed");
        break;
}

function getComidas() {
    global $pdo;
    $stmt = $pdo->query("SELECT * FROM comida");
    $comidas = $stmt->fetchAll();
    echo json_encode($comidas);
}

function getComida($id) {
    global $pdo;
    $stmt = $pdo->prepare("SELECT * FROM comida WHERE id = ?");
    $stmt->execute([$id]);
    $comida = $stmt->fetch();
    
    if ($comida) {
        echo json_encode($comida);
    } else {
        header("HTTP/1.1 404 Not Found");
        echo json_encode(['message' => 'Comida no encontrada.']);
    }
}

function insertComida() {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    
    $stmt = $pdo->prepare("INSERT INTO comida (Nombre, Calorias, Proteina, Grasa, CH, Fibra, Extra, Categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    if ($stmt->execute([$data['Nombre'], $data['Calorias'], $data['Proteina'], $data['Grasa'], $data['CH'], $data['Fibra'], $data['Extra'], $data['Categoria']])) {
        echo json_encode(['message' => 'Comida agregada exitosamente.']);
    } else {
        header("HTTP/1.1 400 Bad Request");
        echo json_encode(['message' => 'Error al agregar la comida.']);
    }
}

function updateComida($id) {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    
    $stmt = $pdo->prepare("UPDATE comida SET Nombre = ?, Calorias = ?, Proteina = ?, Grasa = ?, CH = ?, Fibra = ?, Extra = ?, Categoria = ? WHERE id = ?");
    if ($stmt->execute([$data['Nombre'], $data['Calorias'], $data['Proteina'], $data['Grasa'], $data['CH'], $data['Fibra'], $data['Extra'], $data['Categoria'], $id])) {
        echo json_encode(['message' => 'Comida actualizada exitosamente.']);
    } else {
        header("HTTP/1.1 400 Bad Request");
        echo json_encode(['message' => 'Error al actualizar la comida.']);
    }
}

function deleteComida($id) {
    global $pdo;
    $stmt = $pdo->prepare("DELETE FROM comida WHERE id = ?");
    if ($stmt->execute([$id])) {
        echo json_encode(['message' => 'Comida eliminada exitosamente.']);
    } else {
        header("HTTP/1.1 404 Not Found");
        echo json_encode(['message' => 'Comida no encontrada.']);
    }
}
