<?php
   include "../includes/cargar_clases.php";

   $crudcategoria = new CRUDcategoria();
   
   if (isset($_POST["btn_registrar_cat"])) {
       $categoria = new categoria();
   
       $categoria->id_categoria = $_POST["txt_codcat"];
       $categoria->categoria = $_POST["txt_cat"];
   
       $tipo = $_POST["txt_tipo"];
   
       if ($tipo == "r") {
           $crudcategoria->Registrarcategoria($categoria);
       } else if ($tipo == "e") {
           $crudcategoria->Editarcategoria($categoria);
       }
   
       header("location: ../view/listar_categoria.php");
   }
   