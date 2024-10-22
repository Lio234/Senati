<?php
    // Incluimos las clases necesarias
    include "../includes/cargar_clases.php";

    // Instanciamos el objeto 
    $crudcliente = new CRUDCliente();

    // Verificamos si el valor ha sido enviado por el método POST
    if (isset($_POST["valor"])) {
        $nombre = $_POST["valor"];

        // Llamamos al método FiltrarProducto y obtenemos los resultados
        $resultados = $crudcliente->FiltrarCliente($nombre);

    } else {
        // Si no se envió el valor, mostramos un error
        echo '<p>Error: No se envió ningún valor para filtrar.</p>';
    }
?>
