<?php
   include "../includes/cargar_clases.php";

   $crudmarca = new CRUDmarca();
   
   if (isset($_POST["btn_registrar_mar"])) {
       $marca = new marca();
   
       $marca->id_marca = $_POST["txt_codmar"];
       $marca->marca = $_POST["txt_mar"];
   
       $tipo = $_POST["txt_tipo"];
   
       if ($tipo == "r") {
           $crudmarca->Registrarmarca($marca);
       } else if ($tipo == "e") {
           $crudmarca->Editarmarca($marca);
       }
   
       header("location: ../view/listar_marca.php");
   }
   