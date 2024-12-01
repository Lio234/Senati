using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using app_compuware.Data;
using app_compuware.Models;
using OfficeOpenXml;
using QuestPDF.Fluent;
using Microsoft.Data.SqlClient;

namespace app_compuware.Controllers
{
    public class ClienteController : Controller
    {
        private readonly ApplicationDbContext _context;

        public ClienteController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Index()
        {
            // Obtener los distritos desde la base de datos
            var distritos = _context.Distrito.ToList(); // Aquí "_context" es el DbContext de la base de datos
            ViewBag.Distritos = distritos;

            return View();
        }

        // Acción para obtener los clientes
        public IActionResult Listar()
        {
            var clientes = (from c in _context.Cliente
                            join d in _context.Distrito on c.id_distrito equals d.id_distrito
                            select new
                            {
                                c.id_cliente,
                                c.nombre,
                                c.ap_paterno,
                                c.ap_materno,
                                c.direccion,
                                c.telefono,
                                c.correo,
                                distrito = d.distrito, // El nombre del distrito
                                acciones = "<button class='btn btn-outline-success' onclick='AbrirRegistro(" + c.id_cliente + ")'><i class='far fa-edit'></i> Editar</button> " +
                                           "<button class='btn btn-outline-danger' onclick='Eliminar(" + c.id_cliente + ")'><i class='far fa-trash-alt'></i> Borrar</button>"
                            }).ToList();

            return Json(new { data = clientes });
        }




        [HttpGet]
        public JsonResult Consultar(string id_cliente)
        {
            try
            {
                var cliente = (from c in _context.Cliente
                               join d in _context.Distrito on c.id_distrito equals d.id_distrito
                               where c.id_cliente == id_cliente // Filtrar por id_cliente
                               select new
                               {
                                   c.id_cliente,
                                   c.nombre,
                                   c.ap_paterno,
                                   c.ap_materno,
                                   c.direccion,
                                   c.telefono,
                                   c.correo,
                                   distrito = d.distrito // Obtener el nombre del distrito
                               }).FirstOrDefault(); // Devolver solo el primer (y único) resultado

                return cliente != null
                    ? Json(cliente)
                    : Json(new { Error = "Cliente no encontrado" });
            }
            catch (Exception ex)
            {
                return Json(new { Error = "Ocurrió un error al consultar el cliente", Detalle = ex.Message });
            }
        }


        [HttpPost]
        public IActionResult Grabar([FromBody] Cliente cliente)
        {
            bool resultado = true;

            try
            {
                var tmp_cliente = _context.Cliente
                    .FirstOrDefault(c => c.id_cliente == cliente.id_cliente);

                if (tmp_cliente == null)
                {
                    _context.Cliente.Add(cliente);
                }
                else
                {
                    tmp_cliente.nombre = cliente.nombre;
                    tmp_cliente.ap_paterno = cliente.ap_paterno;
                    tmp_cliente.ap_materno = cliente.ap_materno;
                    tmp_cliente.direccion = cliente.direccion;
                    tmp_cliente.correo = cliente.correo;
                    tmp_cliente.telefono = cliente.telefono;
                    tmp_cliente.id_distrito = cliente.id_distrito;  // Este es el campo que se actualiza correctamente
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
        public JsonResult Borrar(string id_cliente)
        {
            bool resultado = true;

            try
            {
                var cliente = _context.Cliente
                    .FirstOrDefault(c => c.id_cliente == id_cliente);

                if (cliente != null)
                {
                    _context.Cliente.Remove(cliente);
                    _context.SaveChanges();
                }
                else
                {
                    resultado = false; // Cliente no encontrado
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
                // Obtener los clientes junto con sus distritos
                var clientes = (from c in _context.Cliente
                                join d in _context.Distrito on c.id_distrito equals d.id_distrito
                                select new
                                {
                                    c.id_cliente,
                                    c.nombre,
                                    c.ap_paterno,
                                    c.ap_materno,
                                    c.direccion,
                                    c.telefono,
                                    c.correo,
                                    c.id_distrito,       // ID del distrito
                                    distrito = d.distrito // Nombre del distrito
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
                                columns.RelativeColumn();
                                columns.RelativeColumn();
                            });

                            table.Header(header =>
                            {
                                header.Cell().Text("ID Cliente").Bold();
                                header.Cell().Text("Nombre").Bold();
                                header.Cell().Text("Apellido Paterno").Bold();
                                header.Cell().Text("Apellido Materno").Bold();
                                header.Cell().Text("Direccion").Bold();
                                header.Cell().Text("Telefono").Bold();
                                header.Cell().Text("Correo").Bold();
                                header.Cell().Text("Distrito").Bold(); // Para mostrar el nombre del distrito
                            });

                            foreach (var cliente in clientes)
                            {
                                table.Cell().Text(cliente.id_cliente); // ID del cliente
                                table.Cell().Text(cliente.nombre); // Nombre del cliente
                                table.Cell().Text(cliente.ap_paterno); // Apellido paterno
                                table.Cell().Text(cliente.ap_materno); // Apellido materno
                                table.Cell().Text(cliente.direccion); // Dirección
                                table.Cell().Text(cliente.telefono); // Teléfono
                                table.Cell().Text(cliente.correo); // Correo
                                table.Cell().Text(cliente.distrito); // Nombre del distrito
                            }
                        });
                    });
                });

                byte[] pdfBytes = document.GeneratePdf();
                return File(pdfBytes, "application/pdf", "ListadoDeClientes.pdf");
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
                var clientes = _context.Cliente.ToList();

                if (!clientes.Any())
                {
                    return Content("No hay datos para exportar");
                }

                using (var package = new ExcelPackage())
                {
                    var worksheet = package.Workbook.Worksheets.Add("Clientes");

                    // Encabezados
                    worksheet.Cells[1, 1].Value = "ID Cliente";
                    worksheet.Cells[1, 2].Value = "Nombre";
                    worksheet.Cells[1, 3].Value = "Apellido Paterno";
                    worksheet.Cells[1, 4].Value = "Apellido Materno";
                    worksheet.Cells[1, 5].Value = "Direccion";
                    worksheet.Cells[1, 6].Value = "Correo";
                    worksheet.Cells[1, 7].Value = "Telefono";
                    worksheet.Cells[1, 8].Value = "Distrito";

                    // Datos
                    for (int i = 0; i < clientes.Count; i++)
                    {
                        worksheet.Cells[i + 2, 1].Value = clientes[i].id_cliente;
                        worksheet.Cells[i + 2, 2].Value = clientes[i].nombre;
                        worksheet.Cells[i + 2, 3].Value = clientes[i].ap_paterno;
                        worksheet.Cells[i + 2, 4].Value = clientes[i].ap_materno;
                        worksheet.Cells[i + 2, 5].Value = clientes[i].direccion;
                        worksheet.Cells[i + 2, 6].Value = clientes[i].correo;
                        worksheet.Cells[i + 2, 7].Value = clientes[i].telefono;
                        worksheet.Cells[i + 2, 8].Value = clientes[i].id_distrito ?? "N/A"; // Manejo de nulls
                    }

                    worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();

                    return File(package.GetAsByteArray(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Clientes.xlsx");
                }
            }
            catch (Exception ex)
            {
                return Content($"Error al exportar el Excel: {ex.Message}");
            }
        }
    }
}
