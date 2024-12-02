using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using app_compuware.Data;
using app_compuware.Models;
using OfficeOpenXml;
using QuestPDF.Fluent;
using System.Linq;

namespace app_compuware.Controllers
{
    public class ProductoController : Controller
    {
        private readonly ApplicationDbContext _context;

        public ProductoController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Index()
        {
            // Obtener las marcas desde la base de datos
            var marcas = _context.Marca.ToList();
            ViewBag.Marcas = marcas.Any() ? marcas : new List<Marca>();

            // Obtener las categorias desde la base de datos
            var categorias = _context.Categoria.ToList();
            ViewBag.Categorias = categorias.Any() ? categorias : new List<Categoria>();

            return View();
        }

        // Acción para obtener los productos
        public IActionResult Listar()
        {
            var productos = _context.Producto
                .Select(p => new
                {
                    p.id_producto,
                    p.producto,
                    // Convertir los valores de costo y ganancia a string
                    costo = p.costo.ToString(), // Convierte a string
                    ganancia = p.ganancia.ToString(), // Convierte a string
                    marca = p.Marca.marca,
                    categoria = p.Categoria.categoria, // Obtener nombre de la categoría
                    acciones = "<button class='btn btn-outline-success' onclick='AbrirRegistro(\"" + p.id_producto + "\")'><i class='far fa-edit'></i> Editar</button> " +
                               "<button class='btn btn-outline-danger' onclick='Eliminar(\"" + p.id_producto + "\")'><i class='far fa-trash-alt'></i> Borrar</button>"
                }).ToList();

            return Json(new { data = productos });
        }

        [HttpGet]
        public JsonResult Consultar(string id_producto)
        {
            try
            {
                // Consultar los datos del producto y sus relaciones
                var producto = (from p in _context.Producto
                                join m in _context.Marca on p.id_marca equals m.id_marca
                                join c in _context.Categoria on p.id_categoria equals c.id_categoria
                                where p.id_producto == id_producto // Filtrar por id_producto
                                select new
                                {
                                    p.id_producto,
                                    p.producto,
                                    p.costo,
                                    p.ganancia,
                                    p.id_marca,  // Asegúrate de incluir id_marca y id_categoria
                                    p.id_categoria,
                                    marca = m.marca,
                                    categoria = c.categoria // Obtener nombre de la categoría
                                }).FirstOrDefault(); // Devolver solo el primer (y único) resultado

                // Retornar los datos del producto
                return producto != null
                    ? Json(producto)
                    : Json(new { Error = "Producto no encontrado" });
            }
            catch (Exception ex)
            {
                return Json(new { Error = "Ocurrió un error al consultar el producto", Detalle = ex.Message });
            }
        }




        [HttpPost]
        public IActionResult Grabar([FromBody] Producto producto)
        {
            bool resultado = true;

            try
            {
                // Verificar si el producto ya existe
                var tmp_producto = _context.Producto
                    .FirstOrDefault(p => p.id_producto == producto.id_producto);

                // Si no existe, agregar el nuevo producto
                if (tmp_producto == null)
                {
                    _context.Producto.Add(producto);
                }
                else
                {
                    // Si existe, actualizar los datos del producto
                    tmp_producto.producto = producto.producto;
                    tmp_producto.costo = producto.costo;
                    tmp_producto.ganancia = producto.ganancia;
                    tmp_producto.id_marca = producto.id_marca;
                    tmp_producto.id_categoria = producto.id_categoria;
                }

                // Guardar los cambios en la base de datos
                _context.SaveChanges();
            }
            catch (Exception)
            {
                resultado = false; // Si ocurre un error, devolver false
            }

            // Retornar el resultado de la operación
            return Json(new { resultado });
        }

        [HttpPost]
        public IActionResult Borrar([FromBody] Producto producto)
        {
            bool resultado = true;

            try
            {
                var productoAEliminar = _context.Producto.FirstOrDefault(p => p.id_producto == producto.id_producto);

                if (productoAEliminar != null)
                {
                    _context.Producto.Remove(productoAEliminar);
                    _context.SaveChanges();  // Guardar los cambios en la base de datos
                }
                else
                {
                    resultado = false;
                }
            }
            catch (Exception)
            {
                resultado = false;  // Si ocurre un error, retornamos falso
            }

            return Json(new { resultado });
        }

        public IActionResult ExportarPDF()
        {
            try
            {
                var productos = (from p in _context.Producto
                                 join m in _context.Marca on p.id_marca equals m.id_marca
                                 join c in _context.Categoria on p.id_categoria equals c.id_categoria
                                 select new
                                 {
                                     p.id_producto,
                                     p.producto,
                                     // Asegurarse de convertir 'costo' y 'ganancia' a decimal si es necesario
                                     costo = p.costo.ToString("F2"), // Convertir a cadena con formato 2 decimales
                                     ganancia = p.ganancia.ToString("F2"), // Convertir a cadena con formato 2 decimales
                                     marca = m.marca,
                                     categoria = c.categoria
                                 }).ToList();

                var document = Document.Create(container =>
                {
                    container.Page(page =>
                    {
                        page.Content().Table(table =>
                        {
                            table.ColumnsDefinition(columns =>
                            {
                                columns.RelativeColumn();
                                columns.RelativeColumn();
                                columns.RelativeColumn();
                                columns.RelativeColumn();
                                columns.RelativeColumn();
                                columns.RelativeColumn();
                            });

                            table.Header(header =>
                            {
                                header.Cell().Text("ID Producto").Bold();
                                header.Cell().Text("Producto").Bold();
                                header.Cell().Text("Costo").Bold();
                                header.Cell().Text("Ganancia").Bold();
                                header.Cell().Text("Marca").Bold();
                                header.Cell().Text("Categoría").Bold();
                            });

                            foreach (var producto in productos)
                            {
                                table.Cell().Text(producto.id_producto);
                                table.Cell().Text(producto.producto);
                                table.Cell().Text(producto.costo); // Ahora 'costo' es un string con formato
                                table.Cell().Text(producto.ganancia); // Ahora 'ganancia' es un string con formato
                                table.Cell().Text(producto.marca);
                                table.Cell().Text(producto.categoria);
                            }
                        });
                    });
                });

                byte[] pdfBytes = document.GeneratePdf();
                return File(pdfBytes, "application/pdf", "ListadoDeProductos.pdf");
            }
            catch (Exception ex)
            {
                return Content($"Error al exportar el PDF: {ex.Message}");
            }
        }



        public IActionResult ExportarExcel()
        {
            try
            {
                var productos = (from p in _context.Producto
                                 join m in _context.Marca on p.id_marca equals m.id_marca
                                 join c in _context.Categoria on p.id_categoria equals c.id_categoria
                                 select new
                                 {
                                     p.id_producto,
                                     p.producto,
                                     p.costo,
                                     p.ganancia,
                                     marca = m.marca, // Obtener el nombre de la marca
                                     categoria = c.categoria // Obtener el nombre de la categoría
                                 }).ToList();

                if (!productos.Any())
                {
                    return Content("No hay datos para exportar");
                }

                using (var package = new ExcelPackage())
                {
                    var worksheet = package.Workbook.Worksheets.Add("Productos");

                    // Encabezados
                    worksheet.Cells[1, 1].Value = "ID Producto";
                    worksheet.Cells[1, 2].Value = "Producto";
                    worksheet.Cells[1, 3].Value = "Costo";
                    worksheet.Cells[1, 4].Value = "Ganancia";
                    worksheet.Cells[1, 5].Value = "Marca";
                    worksheet.Cells[1, 6].Value = "Categoría";

                    // Datos
                    for (int i = 0; i < productos.Count; i++)
                    {
                        worksheet.Cells[i + 2, 1].Value = productos[i].id_producto;
                        worksheet.Cells[i + 2, 2].Value = productos[i].producto;
                        worksheet.Cells[i + 2, 3].Value = productos[i].costo;
                        worksheet.Cells[i + 2, 4].Value = productos[i].ganancia;
                        worksheet.Cells[i + 2, 5].Value = productos[i].marca; // Asignar el nombre de la marca
                        worksheet.Cells[i + 2, 6].Value = productos[i].categoria; // Asignar el nombre de la categoría
                    }

                    worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();

                    return File(package.GetAsByteArray(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Productos.xlsx");
                }
            }
            catch (Exception ex)
            {
                return Content($"Error al exportar el Excel: {ex.Message}");
            }
        }

    }
}
