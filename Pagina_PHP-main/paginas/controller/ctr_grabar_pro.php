<?php
   include "../includes/cargar_clases.php";

   $crudprovincia = new CRUDProvincia();
   
   if (isset($_POST["btn_registrar_pro"])) {
       $provincia = new Provincia();
   
       $provincia->id_provincia = $_POST["txt_codpro"];
       $provincia->provincia = $_POST["txt_pro"];
   
       $tipo = $_POST["txt_tipo"];
   
       if ($tipo == "r") {
           $crudprovincia->RegistrarProvincia($provincia);
       } else if ($tipo == "e") {
           $crudprovincia->EditarProvincia($provincia);
       }
   
       header("location: ../view/listar_provincia.php");
   }
   