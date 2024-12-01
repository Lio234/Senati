using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using app_compuware.Data;
using Microsoft.Data.SqlClient;
using app_compuware.Models;
using OfficeOpenXml;
using QuestPDF.Fluent;

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
            return View();
        }

        [HttpGet]
        public JsonResult Listar()
        {
            String cad_sql = "exec sp_listar_clientes"; // Ajustar si es necesario el procedimiento
            List<Cliente> arr_cliente = _context.Cliente.FromSqlRaw(cad_sql).ToList();

            return Json(new { Data = arr_cliente });
        }

        [HttpGet]
        public JsonResult Consultar(string id_cliente)
        {
            String cad_sql = "exec sp_consultar_cliente @id_cliente";

            Cliente cliente = _context.Cliente.FromSqlRaw(cad_sql, new SqlParameter("@id_cliente", id_cliente)).FirstOrDefault();

            return Json(cliente);
        }

        [HttpPost]
        public IActionResult Grabar([FromBody] Cliente cliente)
        {
            bool rpta = true;
            try
            {
                Cliente tmp_cliente = _context.Cliente
                    .Where(c => c.id_cliente == cliente.id_cliente)
                    .FirstOrDefault();

                if (tmp_cliente == null)
                {
                    _context.Cliente.Add(cliente);
                    _context.SaveChanges();
                }
                else
                {
                    tmp_cliente.nombre = cliente.nombre;
                    tmp_cliente.ap_paterno = cliente.ap_paterno;
                    tmp_cliente.ap_materno = cliente.ap_materno;
                    tmp_cliente.direccion = cliente.direccion;
                    tmp_cliente.correo = cliente.correo;
                    tmp_cliente.telefono = cliente.telefono;
                    tmp_cliente.id_distrito = cliente.id_distrito;
                    _context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                rpta = false;
            }
            return Json(new { resultado = rpta });
        }

        public JsonResult Borrar(string id_cliente)
        {
            bool rpta = true;
            try
            {
                Cliente cliente = _context.Cliente
                    .Where(c => c.id_cliente == id_cliente)
                    .FirstOrDefault();

                if (cliente != null)
                {
                    _context.Cliente.Remove(cliente);
                    _context.SaveChanges();
                }
                else
                {
                    rpta = false; // Si no existe el cliente
                }
            }
            catch (Exception ex)
            {
                rpta = false;
            }
            return Json(new { resultado = rpta });
        }

        public IActionResult ExportarPDF()
        {
            var clientes = _context.Cliente
                .Include(c => c.distrito) // Incluir información del distrito
                .ThenInclude(d => d.provincia) // Incluir la provincia del distrito
                .ThenInclude(p => p.departamento) // Incluir el departamento de la provincia
                .ToList();

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
                            header.Cell().Text("ID Cliente").Bold();
                            header.Cell().Text("Nombre").Bold();
                            header.Cell().Text("Apellido Paterno").Bold();
                            header.Cell().Text("Distrito").Bold();
                        });

                        foreach (var cliente in clientes)
                        {
                            table.Cell().Text(cliente.id_cliente);
                            table.Cell().Text(cliente.nombre);
                            table.Cell().Text(cliente.ap_paterno);
                            table.Cell().Text(cliente.distrito.distrito); // Información del distrito
                        }
                    });
                });
            });

            byte[] pdfBytes = document.GeneratePdf();
            return File(pdfBytes, "application/pdf", "ListadoDeClientes.pdf");
        }

        public IActionResult ExportarExcel()
        {
            var clientes = _context.Cliente
                .Include(c => c.distrito)
                .ThenInclude(d => d.provincia)
                .ThenInclude(p => p.departamento)
                .Select(c => new
                {
                    c.id_cliente,
                    c.nombre,
                    c.ap_paterno,
                    c.ap_materno,
                    c.direccion,
                    c.correo,
                    c.telefono,
                    Distrito = c.distrito.distrito // Incluye el nombre del distrito
                })
                .ToList();

            if (clientes == null || !clientes.Any())
            {
                return Content("No hay datos para exportar");
            }

            using (var package = new ExcelPackage())
            {
                var worksheet = package.Workbook.Worksheets.Add("Clientes");

                worksheet.Cells[1, 1].Value = "ID Cliente";
                worksheet.Cells[1, 2].Value = "Nombre";
                worksheet.Cells[1, 3].Value = "Apellido Paterno";
                worksheet.Cells[1, 4].Value = "Apellido Materno";
                worksheet.Cells[1, 5].Value = "Direccion";
                worksheet.Cells[1, 6].Value = "Correo";
                worksheet.Cells[1, 7].Value = "Telefono";
                worksheet.Cells[1, 8].Value = "Distrito";

                for (int i = 0; i < clientes.Count; i++)
                {
                    worksheet.Cells[i + 2, 1].Value = clientes[i].id_cliente;
                    worksheet.Cells[i + 2, 2].Value = clientes[i].nombre;
                    worksheet.Cells[i + 2, 3].Value = clientes[i].ap_paterno;
                    worksheet.Cells[i + 2, 4].Value = clientes[i].ap_materno;
                    worksheet.Cells[i + 2, 5].Value = clientes[i].direccion;
                    worksheet.Cells[i + 2, 6].Value = clientes[i].correo;
                    worksheet.Cells[i + 2, 7].Value = clientes[i].telefono;
                    worksheet.Cells[i + 2, 8].Value = clientes[i].Distrito;
                }

                worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();

                var file = new FileContentResult(package.GetAsByteArray(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                {
                    FileDownloadName = "Clientes.xlsx"
                };

                return file;
            }
        }
    }
}