<?php
class CRUDCategoria extends Conexion
{
    public function ListarCategoria()
    {
        $arr_cat = null;

        $cn = $this->Conectar();

        $sql = "call sp_listar_categorias()";

        $snt = $cn->prepare($sql);

        $snt->execute();

        $arr_cat = $snt->fetchAll(PDO::FETCH_OBJ);

        $cn = null;

        return $arr_cat;
    }
    public function BuscarCategoriaPorCodigo($cod_cat)
    {
        $arr_cat = null;
        $cn = $this->Conectar();

        $sql = "CALL sp_buscar_categoria_por_id(:cod_cat);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_cat', $cod_cat, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_cat = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;
        return $arr_cat;
    }


    public function MostrarCategoriaPorCodigo($cod_cat)
    {
        $arr_cat = null;

        $cn = $this->Conectar();

        $sql = "call sp_mostrar_categoria_por_id(:cod_cat);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_cat', $cod_cat, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_cat = $snt->fetch(PDO::FETCH_OBJ);
        }

        $cn = null;

        return $arr_cat;
    }
    public function ConsultarcategoriaPorCodigo($cod_cat)
    {
        $arr_cat = null;

        $cn = $this->Conectar();

        $sql = "CALL sp_mostrar_categoria_por_id(:cod_cat);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':cod_cat', $cod_cat, PDO::PARAM_STR, 5);

        $snt->execute();

        $nr = $snt->rowCount();

        if ($nr > 0) {
            $arr_cat = $snt->fetch(PDO::FETCH_OBJ); // Guardar el resultado
        }

        $cn = null;

        return $arr_cat; // Retornar el categoria
    }


    public function Filtrarcategoria($valor)
    {
        $arr_cat = null;

        $cn = $this->Conectar();

        $sql = "call sp_filtrar_categoria(:valor);";

        $snt = $cn->prepare($sql);

        $snt->bindParam(':valor', $valor, PDO::PARAM_STR, 40);

        $snt->execute();

        $arr_cat = $snt->fetchAll(PDO::FETCH_OBJ);

        $nr = $snt->rowCount();

        if ($nr > 0) {
            echo "<table class='table table-hover table-sm table-striped'>";
            echo "<tr class='table-primary'>";
            echo "<th>N°</th>";
            echo "<th>Código</th>";
            echo "<th>categoria</th>";
            echo "</tr>";

            $i = 0;
            foreach ($arr_cat as $cat) {
                $i++;
                echo "<tr>";
                echo "<td>" . $i . "</td>";
                echo "<td>" . $cat->id_categoria . "</td>";
                echo "<td>" . $cat->categoria . "</td>";
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


    public function Registrarcategoria(categoria $categoria)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_registrar_categoria(:id_cat, :cat);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':id_cat', $categoria->id_categoria);
            $snt->bindParam(':cat', $categoria->categoria);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }


    public function Editarcategoria(categoria $categoria)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_editar_categoria(:cod_cat, :cat);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_cat', $categoria->id_categoria);
            $snt->bindParam(':cat', $categoria->categoria);
            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }

    
    public function Borrarcategoria($id_categoria)
    {
        try {
            $cn = $this->Conectar();

            $sql = "call sp_borrar_categoria(:cod_cat);";

            $snt = $cn->prepare($sql);

            $snt->bindParam(':cod_cat', $id_categoria);

            $snt->execute();

            $cn = null;
        } catch (PDOException $ex) {
            die($ex->getMessage());
        }
    }
}
