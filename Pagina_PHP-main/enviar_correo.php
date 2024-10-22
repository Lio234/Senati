<?php
require 'vendor/autoload.php'; 

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;


$host = 'localhost';        
$dbname = 'Compuware';    
$username = 'root';        
$password = 'root';        

try {
    
    $conn = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $sql = "CALL obtener_clientes_con_distrito()";  
    $stmt = $conn->prepare($sql);
    $stmt->execute();

    
    $resultados = $stmt->fetchAll(PDO::FETCH_ASSOC);

    
    $contenidoCorreo = "<h2>Reporte de los clientes</h2>";
    $contenidoCorreo .= "<table border='1' cellpadding='5' cellspacing='0'>";
    $contenidoCorreo .= "<thead>
                            <tr>
                                <th>ID Cliente</th>
                                <th>Nombre</th>
                                <th>Apellido Paterno</th>
                                <th>Apellido Materno</th>
                                <th>Dirección</th>
                                <th>Correo</th>
                                <th>Teléfono</th>
                                <th>Distrito</th>
                            </tr>
                        </thead>";
    $contenidoCorreo .= "<tbody>";

    
    foreach ($resultados as $fila) {
        $contenidoCorreo .= "<tr>
                                <td>{$fila['id_cliente']}</td>
                                <td>{$fila['nombre']}</td>
                                <td>{$fila['ap_paterno']}</td>
                                <td>{$fila['ap_materno']}</td>
                                <td>{$fila['direccion']}</td>
                                <td>{$fila['correo']}</td>
                                <td>{$fila['telefono']}</td>
                                <td>{$fila['distrito']}</td>
                            </tr>";
    }

    $contenidoCorreo .= "</tbody></table>";

    $correoDestinatario = $_POST['email']; 

    $mail = new PHPMailer(true);

    try {
        
        $mail->isSMTP();                                            
        $mail->Host       = 'smtp.gmail.com';                       
        $mail->SMTPAuth   = true;                                   
        $mail->Username   = 'vcanchari38@gmail.com';                 
        $mail->Password   = 'cnoikrqvbprujncd';                     
        $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;         
        $mail->Port       = 587;                                    

        
        $mail->setFrom('vcanchari38@gmail.com', 'Tu Nombre');       
        $mail->addAddress($correoDestinatario);                     

        
        $mail->isHTML(true);                                        
        $mail->Subject = 'Reporte de Clientes';    
        $mail->Body    = $contenidoCorreo;                          

        // Enviar el correo
        if ($mail->send()) {
            
            header('Location: index.php'); 
            exit(); 
        }

    } catch (Exception $e) {
        echo "El correo no pudo ser enviado. Error: {$mail->ErrorInfo}";
    }

} catch (PDOException $e) {
    echo "Error de conexión a la base de datos: " . $e->getMessage();
}
?>
