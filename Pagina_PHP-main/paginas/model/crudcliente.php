<?php
class CRUDCliente extends Conexion
{
    public function ListarCliente()
    {
        $arr_clien = null;

        $cn = $this->Conectar();

        $sql = "call sp_listar_clientes();";

        $snt = $cn->prepare($sql);

        $snt->execute();

        $arr_clien = $snt->fetchAll(PDO::FETCH_OBJ);

        $cn = null;

        return $arr_clien;
    }


    public function BuscarClientePorCodigo($id_cliente)
    {
        $arr_clien = null;
        $cn = $this->Conectar();

        $sql = "CALL sp_buscar_cliente_por_id(:id_cliente);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':id_cliente', $id_cliente, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_clien = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null; 
        return $arr_clien;
    }



    public function MostrarClientePorCodigo($id_cliente)
    {
        $arr_clien = null;

        $cn = $this->Conectar();

        $sql = "call sp_mostrar_cliente_por_id(:id_cliente);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':id_cliente', $id_cliente, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_clien = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;

        return $arr_clien;
    }


    public function ConsultarClientePorCodigo($id_cliente)
{
    $arr_clien = null;

    $cn = $this->Conectar();

    $sql = "CALL sp_mostrar_cliente_por_id(:id_cliente);";

    $snt = $cn->prepare($sql);

    $snt->bindParam(':id_cliente', $id_cliente, PDO::PARAM_STR, 5);

    $snt->execute();

    $nr = $snt->rowCount();

    if ($nr > 0) {
        $arr_clien = $snt->fetch(PDO::FETCH_OBJ); // Guardar el resultado
    }

    $cn = null;

    return $arr_clien; // Retornar el producto
}



    public function FiltrarCliente($nombre)
    {
        $arr_clien = null;

        $cn = $this->Conectar();

        $sql = "call sp_filtrar_por_cliente(:nombre);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':nombre', $nombre, PDO::PARAM_STR, 40);

        $snt->execute();

        $arr_clien = $snt->fetchAll(PDO::FETCH_OBJ);

        $nr = $snt->rowCount();

        if ($nr > 0) {
            echo "<table class='table table-hover table-sm table-striped'>";
            echo "<tr class='table-primary'>";
            echo "<th>N°</th>";
            echo "<th>Código</th>";
            echo "<th>Nombre</th>";
            echo "<th>Apellido paterno</th>";
            echo "<th>Apellido materno</th>";
            echo "<th>Direccion</th>";
            echo "<th>Correo</th>";
            echo "<th>Telefono</th>";
            echo "<th>Distrito</th>";
            echo "</tr>";

            $i = 0;
            foreach ($arr_clien as $clien) {
                $i++;
                echo "<tr>";
                echo "<td>" . $i . "</td>";
                echo "<td>" . $clien->id_cliente . "</td>";
                echo "<td>" . $clien->nombre . "</td>";
                echo "<td class='text-center'>" . $clien->ap_paterno . "</td>";
                echo "<td class='text-center'>" . $clien->ap_materno . "</td>";
                echo "<td class='text-center'>" . $clien->direccion . "</td>";
                echo "<td>" . $clien->correo . "</td>";
                echo "<td>" . $clien->telefono . "</td>";
                echo "<td>" . $clien->distrito ."</td>";
                echo "</tr>";
            }
            echo "</table>";
        } else {
            echo "<div class='alert alert-danger alert-dismissible fade show' role='alert'>";
            echo "No hay registros.";
            echo "<button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>";
            echo "</div>";
        }
        $cn = null;
    }


    public function RegistrarCliente(Cliente $cliente)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_registrar_cliente(:id_cliente, :nom, :a_pat, :a_mat, :direc, :cor, :tel, :id_dist);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':id_cliente', $cliente->id_cliente);
            $snt->bindParam(':nom', $cliente->nombre);
            $snt->bindParam(':a_pat', $cliente->ap_paterno);
            $snt->bindParam(':a_mat', $cliente->ap_materno);
            $snt->bindParam(':direc', $cliente->direccion);
            $snt->bindParam(':cor', $cliente->correo);
            $snt->bindParam(':tel', $cliente->telefono);
            $snt->bindParam(':id_dist', $cliente->cliente_id_distrito);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }
public function EditarCliente(Cliente $cliente)
    {
        try {
            $cn = $this->Conectar();
            $sql = "CALL sp_editar_cliente(:id_cliente, :nom, :a_pat, :a_mat, :direc, :cor, :tel, :id_dist);";
            $snt = $cn->prepare($sql);

            $snt->bindParam(':id_cliente', $cliente->id_cliente);
            $snt->bindParam(':nom', $cliente->nombre);
            $snt->bindParam(':a_pat', $cliente->ap_paterno);
            $snt->bindParam(':a_mat', $cliente->ap_materno);
            $snt->bindParam(':direc', $cliente->direccion);
            $snt->bindParam(':cor', $cliente->correo);
            $snt->bindParam(':tel', $cliente->telefono);
            $snt->bindParam(':id_dist', $cliente->cliente_id_distrito);

            $snt->execute();
            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }
    public function BorrarCliente($id_cliente)
{
    try {
        $cn = $this->Conectar();
        $sql = "call sp_borrar_cliente(:id_cliente);";
        $snt = $cn->prepare($sql);
        $snt->bindParam(':id_cliente', $id_cliente);
        $snt->execute();
        $cn = null;

        return true; // Indica que la eliminación fue exitosa
    } catch (PDOException $ex) {
        return false; // Indica que hubo un error
    }
}
}
