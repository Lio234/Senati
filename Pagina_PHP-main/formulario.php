<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enviar Correo</title>
    <!-- Integrar Bootstrap CSS -->
    <link rel="stylesheet" href="./css/bootstrap/css/bootstrap.css">
</head>
<body class="bg-light">

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow-lg">
                    <div class="card-header bg-primary text-white text-center">
                        <h2>Enviar Información de Clientes</h2>
                    </div>
                    <div class="card-body">
                        <form action="enviar_correo.php" method="POST">
                            <div class="mb-3">
                                <label for="email" class="form-label">Correo del destinatario</label>
                                <input type="email" id="email" name="email" class="form-control" placeholder="Introduce el correo" required>
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-success">Enviar Correo</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
