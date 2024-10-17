<?php
    class CRUDCategoria extends Conexion{
        public function ListarCategoria(){
            $arr_cat= null;
            
            $cn= $this-> Conectar();

            $sql = "call sp_listar_categorias()";

            $snt = $cn->prepare($sql);
            
            $snt->execute();

            $arr_cat=$snt->fetchAll(PDO::FETCH_OBJ);

            $cn=null;

            return $arr_cat;
        }
    }