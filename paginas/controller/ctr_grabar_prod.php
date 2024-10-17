<?php
   include "../includes/cargar_clases.php";

   $crudproducto = new CRUDProducto();
   
   if (isset($_POST["btn_registrar_prod"])) {
       $producto = new Producto();
   
       $producto->id_producto = $_POST["txt_codprod"];
       $producto->producto = $_POST["txt_prod"];
       $producto->stock_disponible = $_POST["txt_stk"];
       $producto->costo = $_POST["txt_cst"];
       $producto->ganancia = $_POST["txt_gnc"] / 100;
       $producto->producto_id_marca = $_POST["cbo_mar"];
       $producto->producto_id_categoria = $_POST["cbo_cat"];
   
       $tipo = $_POST["txt_tipo"];
   
       if ($tipo == "r") {
           $crudproducto->RegistrarProducto($producto);
       } else if ($tipo == "e") {
           $crudproducto->EditarProducto($producto);
       }
   
       header("location: ../view/listar_producto.php");
   }
   