<?php
include "../includes/cargar_clases.php";

$crudcliente = new CRUDCliente();

if (isset($_GET["id_cliente"])) {
    $id_cliente = $_GET["id_cliente"];
    $cliente = $crudcliente->ConsultarClientePorCodigo($id_cliente);

    if ($cliente) {
        // Muestra los detalles del producto
        echo '
             <div id="detalle_cliente">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <h6 class="card-title">Código</h6>
                            <p class="prod card-text">' . $cliente->id_cliente . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Nombre</h6>
                            <p class="prod card-text">' . $cliente->nombre . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Apellido paterno</h6>
                            <p class="stk card-text">' . $cliente->ap_paterno . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Apellido materno</h6>
                            <p class="cst card-text">' . $cliente->ap_materno . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Direccion </h6>
                            <p class="gnc card-text">' . $cliente->direccion . '</p>
                        </div>
                        <div class="col-md-4">
                            <h6 class="card-title">Correo</h6>
                            <p class="prc card-text">' . $cliente->correo . '</p>
                        </div>
                        <div class="col-md-6">
                            <h6 class="card-title">Telefono</h6>
                            <p class="mar card-text">' . $cliente->telefono . '</p>
                        </div>
                        <div class="col-md-6">
                            <h6 class="card-title">Distrito</h6>
                            <p class="cat card-text">' . $cliente->distrito . '</p>
                        </div>
                        <div class="col-md-6">
                            <h6 class="card-title">Provincia</h6>
                            <p class="cat card-text">' . $cliente->provincia . '</p>
                        </div>
                        <div class="col-md-6">
                            <h6 class="card-title">Departamento</h6>
                            <p class="cat card-text">' . $cliente->departamento . '</p>
                        </div>
                    </div>
                </div>
        ';
    } else {
        echo '<p class="text-danger">Cliente no encontrado.</p>';
    }
} else {
    echo '<p class="text-warning">Código del cliente no especificado.</p>';
}



