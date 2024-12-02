using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using app_compuware.Data;
using app_compuware.Models;
using OfficeOpenXml;
using QuestPDF.Fluent;
using Microsoft.Data.SqlClient;

namespace app_compuware.Controllers
{
    public class PedidoController : Controller
    {
        private readonly ApplicationDbContext _context;

        public PedidoController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Index()
        {
            // Obtener los clientes desde la base de datos
            var clientes = _context.Cliente.ToList(); // Aquí "_context" es el DbContext de la base de datos
            ViewBag.Clientes = clientes;

            return View();
        }

        // Acción para obtener los pedidos
        public IActionResult Listar()
        {
            var pedidos = (from c in _context.Pedido
                            join d in _context.Cliente on c.id_cliente equals d.id_cliente
                            select new
                            {
                                c.id_pedido,
                                c.fecha,
                                c.total,
                                cliente = d.nombre, // El nombre del cliente
                                acciones = "<button class='btn btn-outline-success' onclick='AbrirRegistro(" + c.id_pedido + ")'><i class='far fa-edit'></i> Editar</button> " +
                                           "<button class='btn btn-outline-danger' onclick='Eliminar(" + c.id_pedido + ")'><i class='far fa-trash-alt'></i> Borrar</button>"
                            }).ToList();

            return Json(new { data = pedidos });
        }




        [HttpGet]
        public JsonResult Consultar(string id_pedido)
        {
            try
            {
                var pedido = (from c in _context.Pedido
                               join d in _context.Cliente on c.id_cliente equals d.id_cliente
                               where c.id_pedido == id_pedido // Filtrar por id_pedido
                               select new
                               {
                                   c.id_pedido,
                                   c.fecha,
                                   c.total,
                                   cliente = d.nombre // Obtener el nombre del cliente
                               }).FirstOrDefault(); // Devolver solo el primer (y único) resultado

                return pedido != null
                    ? Json(pedido)
                    : Json(new { Error = "Pedido no encontrado" });
            }
            catch (Exception ex)
            {
                return Json(new { Error = "Ocurrió un error al consultar el pedido", Detalle = ex.Message });
            }
        }


        [HttpPost]
        public IActionResult Grabar([FromBody] Pedido pedido)
        {
            bool resultado = true;

            try
            {
                var tmp_pedido = _context.Pedido
                    .FirstOrDefault(c => c.id_pedido == pedido.id_pedido);

                if (tmp_pedido == null)
                {
                    _context.Pedido.Add(pedido);
                }
                else
                {
                    tmp_pedido.fecha = pedido.fecha;
                    tmp_pedido.total = pedido.total;
                    tmp_pedido.id_cliente = pedido.id_cliente;  // Este es el campo que se actualiza correctamente
                }

                _context.SaveChanges();
            }
            catch (Exception)
            {
                resultado = false;
            }

            return Json(new { resultado });
        }


        [HttpPost]
        public JsonResult Borrar(string id_pedido)
        {
            bool resultado = true;

            try
            {
                var pedido = _context.Pedido
                    .FirstOrDefault(c => c.id_pedido == id_pedido);

                if (pedido != null)
                {
                    _context.Pedido.Remove(pedido);
                    _context.SaveChanges();
                }
                else
                {
                    resultado = false; // Pedido no encontrado
                }
            }
            catch (Exception)
            {
                resultado = false;
            }

            return Json(new { resultado });
        }

        public IActionResult ExportarPDF()
        {
            try
            {
                // Obtener los pedidos junto con sus clientes
                var pedidos = (from c in _context.Pedido
                                join d in _context.Cliente on c.id_cliente equals d.id_cliente
                                select new
                                {
                                    c.id_pedido,
                                    c.fecha,
                                    c.total,
                                    c.id_cliente,       // ID del cliente
                                    cliente = d.nombre// Nombre del cliente
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
                                
                            });

                            table.Header(header =>
                            {
                                header.Cell().Text("ID Pedido").Bold();
                                header.Cell().Text("fecha").Bold();
                                header.Cell().Text("Total").Bold();
                                header.Cell().Text("Cliente").Bold(); // Para mostrar el nombre del cliente
                            });

                            foreach (var pedido in pedidos)
                            {
                                table.Cell().Text(pedido.id_pedido); // ID del pedido
                                table.Cell().Text(pedido.fecha); // Nombre del pedido
                                table.Cell().Text(pedido.total); // Apellido paterno
                                table.Cell().Text(pedido.cliente); // Nombre del cliente
                            }
                        });
                    });
                });

                byte[] pdfBytes = document.GeneratePdf();
                return File(pdfBytes, "application/pdf", "ListadoDePedidos.pdf");
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
                var pedidos = _context.Pedido.ToList();

                if (!pedidos.Any())
                {
                    return Content("No hay datos para exportar");
                }

                using (var package = new ExcelPackage())
                {
                    var worksheet = package.Workbook.Worksheets.Add("Pedidos");

                    // Encabezados
                    worksheet.Cells[1, 1].Value = "ID Pedido";
                    worksheet.Cells[1, 2].Value = "Fecha";
                    worksheet.Cells[1, 3].Value = "Total";
                    worksheet.Cells[1, 4].Value = "Cliente";

                    // Datos
                    for (int i = 0; i < pedidos.Count; i++)
                    {
                        worksheet.Cells[i + 2, 1].Value = pedidos[i].id_pedido;
                        worksheet.Cells[i + 2, 2].Value = pedidos[i].fecha;
                        worksheet.Cells[i + 2, 3].Value = pedidos[i].total;
                        worksheet.Cells[i + 2, 8].Value = pedidos[i].id_cliente ?? "N/A"; // Manejo de nulls
                    }

                    worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();

                    return File(package.GetAsByteArray(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Pedidos.xlsx");
                }
            }
            catch (Exception ex)
            {
                return Content($"Error al exportar el Excel: {ex.Message}");
            }
        }
    }
}
