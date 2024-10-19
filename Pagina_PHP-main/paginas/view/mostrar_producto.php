<?php
include "../includes/cargar_clases.php";

$crudproducto = new CRUDProducto();

if (isset($_GET["cod_prod"])) {
    $cod_prod = $_GET["cod_prod"];
    $producto = $crudproducto->ConsultarProductoPorCodigo($cod_prod);

    if ($producto) {
        // Muestra los detalles del producto
        echo '
             <div id="detalleProducto">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <h6 class="card-title">Código</h6>
                            <p class="prod card-text">' . $producto->id_producto . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Producto</h6>
                            <p class="prod card-text">' . $producto->producto . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Stock disponible</h6>
                            <p class="stk card-text">' . $producto->stock_disponible . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Costo</h6>
                            <p class="cst card-text">S/' . $producto->costo . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">% Ganancia</h6>
                            <p class="gnc card-text">' . $producto->ganancia . '%</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Precio</h6>
                            <p class="prc card-text">S/' . $producto->precio . '</p>
                        </div>
                        <div class="col-md-6">
                            <h6 class="card-title">Marca</h6>
                            <p class="mar card-text">' . $producto->marca . '</p>
                        </div>
                        <div class="col-md-6">
                            <h6 class="card-title">Categoría</h6>
                            <p class="cat card-text">' . $producto->categoria . '</p>
                        </div>
                    </div>
                </div>
        ';
    } else {
        echo '<p class="text-danger">Producto no encontrado.</p>';
    }
} else {
    echo '<p class="text-warning">Código del producto no especificado.</p>';
}



