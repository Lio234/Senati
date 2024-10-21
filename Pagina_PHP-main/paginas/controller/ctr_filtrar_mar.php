<?php
    // Incluimos las clases necesarias
    include "../includes/cargar_clases.php";

    // Instanciamos el objeto CRUDmarca
    $crudmarca = new CRUDmarca();

    // Verificamos si el valor ha sido enviado por el método POST
    if (isset($_POST["valor"])) {
        $valor = $_POST["valor"];

        // Llamamos al método Filtrarmarca y obtenemos los resultados
        $resultados = $crudmarca->Filtrarmarca($valor);

    } else {
        // Si no se envió el valor, mostramos un error
        echo '<p>Error: No se envió ningún valor para filtrar.</p>';
    }
?>
