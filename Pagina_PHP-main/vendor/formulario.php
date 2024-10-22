<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enviar Correo</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl7/4pc5JO2d4G5lFy3sz8iZ9fY0CAY/qeVjLL/+Xc" crossorigin="anonymous">
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

    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-2CM2vH8Mt3srHwpooxMkxVmCEJcnxvMzhUyZdYs1dSvvttVrG9xk8aIHLYKSjMVm" crossorigin="anonymous"></script>
</body>
</html>
