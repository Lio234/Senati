<?php
class CRUDDepartamento extends Conexion
{
    public function ListarDepartamento()
    {
        $arr_dep = null;

        $cn = $this->Conectar();

        $sql = "call sp_listar_departamentos()";

        $snt = $cn->prepare($sql);

        $snt->execute();

        $arr_dep = $snt->fetchAll(PDO::FETCH_OBJ);

        $cn = null;

        return $arr_dep;
    }
    public function BuscarDepartamentoPorCodigo($cod_dep)
    {
        $arr_dep = null;
        $cn = $this->Conectar();

        $sql = "CALL sp_buscar_departamento_por_id(:cod_dep);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_dep', $cod_dep, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_dep = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;
        return $arr_dep;
    }


    public function MostrarDepartamentoPorCodigo($cod_dep)
    {
        $arr_dep = null;

        $cn = $this->Conectar();

        $sql = "call sp_mostrar_departamento_por_id(:cod_dep);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_dep', $cod_dep, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_dep = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;

        return $arr_dep;
    }
    public function ConsultarDepartamentoPorCodigo($cod_dep)
    {
        $arr_dep = null;

        $cn = $this->Conectar();

        $sql = "CALL sp_mostrar_departamento_por_id(:cod_dep);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_dep', $cod_dep, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_dep = $snt->fetch(PDO::FETCH_OBJ); // Guardar el resultado
        }

        $cn = null;

        return $arr_dep; // Retornar el departamento
    }


    public function FiltrarDepartamento($valor)
    {
        $arr_dep = null;

        $cn = $this->Conectar();

        $sql = "call sp_filtrar_departamento(:valor);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':valor', $valor, PDO::PARAM_STR, 40);

        $snt->execute();

        $arr_dep = $snt->fetchAll(PDO::FETCH_OBJ);

        $nr = $snt->rowCount();

        if ($nr > 0) {
            echo "<table class='table table-hover table-sm table-striped'>";
            echo "<tr class='table-pridepy'>";
            echo "<th>N°</th>";
            echo "<th>Código</th>";
            echo "<th>departamento</th>";
            echo "</tr>";

            $i = 0;
            foreach ($arr_dep as $dep) {
                $i++;
                echo "<tr>";
                echo "<td>" . $i . "</td>";
                echo "<td>" . $dep->id_departamento . "</td>";
                echo "<td>" . $dep->departamento . "</td>";
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


    public function RegistrarDepartamento(departamento $departamento)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_registrar_departamento(:id_dep, :dep);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':id_dep', $departamento->id_departamento);
            $snt->bindParam(':dep', $departamento->departamento);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }


    public function EditarDepartamento(departamento $departamento)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_editar_departamento(:cod_dep, :dep);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_dep', $departamento->id_departamento);
            $snt->bindParam(':dep', $departamento->departamento);
            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }

    
    public function BorrarDepartamento($id_departamento)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_borrar_departamento(:cod_dep);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_dep', $id_departamento);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }
}
