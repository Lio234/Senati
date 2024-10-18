<?php
    // Incluimos las clases necesarias
    include "../includes/cargar_clases.php";

    // Instanciamos el objeto CRUDProducto
    $crudproducto = new CRUDProducto();

    // Verificamos si el valor ha sido enviado por el método POST
    if (isset($_POST["valor"])) {
        $valor = $_POST["valor"];

        // Llamamos al método FiltrarProducto y obtenemos los resultados
        $resultados = $crudproducto->FiltrarProducto($valor);

    } else {
        // Si no se envió el valor, mostramos un error
        echo '<p>Error: No se envió ningún valor para filtrar.</p>';
    }
?>
