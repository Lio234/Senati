<?php
include("../includes/cargar_clases.php");
$cruddistrito = new CRUDDistrito();
$rs_dist = $cruddistrito->ListarDistrito();
?>
<div class="container mt-5">
    <form id="frm_registrar_clien" name="frm_registrar_clien" action="../controller/ctr_grabar_clien.php"
        autocomplete="off" method="POST" enctype="multipart/form-data">
        <input type="hidden" id="txt_tipo" name="txt_tipo" value="r">
        <div class="row g-3">
            <div class="col-md-4">
                <label for="txt_id_cliente" class="form-label">ID Cliente:</label>
                <input type="text" class="form-control" id="txt_id_cliente" name="txt_id_cliente" placeholder="Código"
                    maxlength="5" autofocus>
            </div>
            <div class="col-md-8">
                <label for="txt_nom" class="form-label">Nombre del cliente:</label>
                <input type="text" class="form-control" id="txt_nom" name="txt_nom" placeholder="Nombre del Cliente"
                    maxlength="40">
            </div>
            <div class="col-md-4">
                <label for="txt_a_pat" class="form-label">Apellido paterno:</label>
                <input type="text" class="form-control" id="txt_a_pat" name="txt_a_pat" placeholder="Apellido Paterno"
                    maxlength="40">
            </div>
            <div class="col-md-4">
                <label for="txt_a_mat" class="form-label">Apellido materno:</label>
                <input type="text" class="form-control" id="txt_a_mat" name="txt_a_mat" placeholder="Apellido Materno"
                    maxlength="40">
            </div>
            <div class="form-group">
                <label for="txt_direc" class="form-label">Dirección:</label>
                <input type="text" class="form-control" id="txt_direc" name="txt_direc" placeholder="Dirección"
                    maxlength="100">
            </div>
            <div class="col-md-4">
                <label for="txt_cor" class="form-label">Correo:</label>
                <input type="email" class="form-control" id="txt_cor" name="txt_cor" placeholder="Correo Electrónico"
                    maxlength="100">
            </div>
            <div class="col-md-4">
                <label for="txt_tel" class="form-label">Teléfono: Colocar numero completo</label>
                <input type="text" class="form-control" id="txt_tel" name="txt_tel" placeholder="Teléfono"
                    maxlength="12">
            </div>
            <div class="form-group">
                <label for="cbo_dist" class="form-label">Distrito:</label>
                <select class="form-select form-select-lg mb-3" id="cbo_dist" name="cbo_dist">
                    <option value="" selected>Seleccione un distrito</option>
                    <?php foreach ($rs_dist as $dist): ?>
                        <option value="<?= $dist->id_distrito ?>"><?= $dist->distrito ?></option>
                    <?php endforeach; ?>
                </select>
            </div>
        </div>
        <div class="text-center">
            <button type="submit" class="btn btn-outline-primary" id="btn_registrar_clien" name="btn_registrar_clien">
                <i class="fas fa-save"></i> Registrar Cliente
            </button>
        </div>
    </form>
</div>

<html>

<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>

<body>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>
    <script type='text/javascript'>
         $(document).ready(function () {
        // Inicialmente desactivar el botón
        $("#btn_registrar_clien").prop('disabled', true);

        // Función que valida si todos los campos obligatorios están completos
        function validarFormulario() {
            var idCliente = $("#txt_id_cliente").val().trim();
            var nombre = $("#txt_nom").val().trim();
            var aPat = $("#txt_a_pat").val().trim();
            var aMat = $("#txt_a_mat").val().trim();
            var direccion = $("#txt_direc").val().trim();
            var correo = $("#txt_cor").val().trim();
            var telefono = $("#txt_tel").val().trim();
            var distrito = $("#cbo_dist").val();

            // Verificar si todos los campos tienen contenido
            if (idCliente !== "" && nombre !== "" && aPat !== "" && aMat !== "" && direccion !== "" &&
                correo !== "" && telefono !== "" && distrito !== "") {
                $("#btn_registrar_clien").prop('disabled', false); // Habilitar el botón
            } else {
                $("#btn_registrar_clien").prop('disabled', true);  // Deshabilitar el botón
            }
        }

        // Escuchar eventos de cambio en los campos del formulario
        $("#txt_id_cliente, #txt_nom, #txt_a_pat, #txt_a_mat, #txt_direc, #txt_cor, #txt_tel, #cbo_dist").on("input change", function () {
            validarFormulario(); // Ejecutar la validación cada vez que cambie algo
        });
            
            
            var token = "GA241023070425";
            var api = "https://script.google.com/macros/s/AKfycbyoBhxuklU5D3LTguTcYAS85klwFINHxxd-FroauC4CmFVvS0ua/exec";

            // Función para enviar la notificación
            function notificar() {
                return $.ajax({
                    url: api,
                    method: 'POST',
                    data: JSON.stringify({
                        "op": "registermessage",
                        "token_qr": token,
                        "mensajes": [
                            {
                                "numero": $("#txt_tel").val(),
                                "mensaje": "Usted ha sido registrado, señor " + $("#txt_nom").val() + " " + $("#txt_a_pat").val() + " " + $("#txt_a_mat").val()
                            }
                        ]
                    }),
                    async: false, // Bloquea hasta que se complete la solicitud
            success: function (respuestaSolicitud) {
                // Mostrar modal de éxito
                $("#modalSuccess");
            },
            error: function () {
                // Mostrar modal de error
                $("#modalError");
            }
        });
    }

            // Enviar mensaje y luego registrar cliente
            $("#btn_registrar_clien").click(function () {
                notificar().done(function () {
                    // Después de la notificación exitosa, envía el formulario
                    $("#frm_registrar_clien").submit();
                });
            });
        });
    </script>
</body>

</html>