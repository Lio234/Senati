using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using app_compuware.Data;
using app_compuware.Models;
using Microsoft.Data.SqlClient;
using OfficeOpenXml;
using QuestPDF.Fluent;

namespace app_compuware.Controllers
{
    public class ProvinciaController : Controller
    {
        private readonly ApplicationDbContext _context;

        public ProvinciaController(ApplicationDbContext context)
        {
            _context = context;
        }

        // Vista principal
        public IActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public JsonResult ListarDepartamentos()
        {
            var departamentos = _context.Departamento
                .Select(d => new { d.id_departamento, d.departamento })
                .ToList();

            return Json(departamentos);
        }


        [HttpGet]
        public JsonResult Listar()
        {
            string cad_sql = "exec sp_listar_provincia";
            var provincias = _context.Provincia
                                      .FromSqlRaw(cad_sql)
                                      .ToList();

            return Json(new { Data = provincias });
        }

        // Método para consultar una provincia específica
        [HttpGet]
        public JsonResult Consultar(string id_provincia)
        {
            string cad_sql = "exec sp_consultar_provincia @id_provincia"; 

            var provincia = _context.Provincia
                                     .FromSqlRaw(cad_sql, new SqlParameter("@id_provincia", id_provincia))
                                     .ToList()
                                     .FirstOrDefault();

            return Json(provincia);
        }

        // Método para insertar/actualizar una provincia
        [HttpPost]
        public IActionResult Grabar([FromBody] Provincia provincia)
        {
            bool rpta = true;

            try
            {
                var tmp_provincia = (from p in _context.Provincia
                                     where p.id_provincia == provincia.id_provincia
                                     select p).FirstOrDefault();

                if (tmp_provincia == null)
                {
                    // Insertar nueva provincia
                    _context.Provincia.Add(provincia);
                }
                else
                {
                    // Actualizar provincia existente
                    tmp_provincia.provincia = provincia.provincia;
                    tmp_provincia.id_departamento = provincia.id_departamento;
                }

                _context.SaveChanges();
            }
            catch
            {
                rpta = false;
            }

            return Json(new { resultado = rpta });
        }

        // Método para eliminar una provincia
        public JsonResult Borrar(string id_provincia)
        {
            bool rpta = true;

            try
            {
                var provincia = (from p in _context.Provincia
                                 where p.id_provincia == id_provincia
                                 select p).FirstOrDefault();

                if (provincia != null)
                {
                    _context.Provincia.Remove(provincia);
                    _context.SaveChanges();
                }
            }
            catch
            {
                rpta = false;
            }

            return Json(new { resultado = rpta });
        }

        // Método para exportar provincias a PDF
        public IActionResult ExportarPDF()
        {
            var provincias = _context.Provincia.ToList();

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
                        });

                        table.Header(header =>
                        {
                            header.Cell().Text("ID Provincia").Bold();
                            header.Cell().Text("Nombre Provincia").Bold();
                            header.Cell().Text("ID Departamento").Bold();
                        });

                        foreach (var provincia in provincias)
                        {
                            table.Cell().Text(provincia.id_provincia);
                            table.Cell().Text(provincia.provincia);
                            table.Cell().Text(provincia.id_departamento);
                        }
                    });
                });
            });

            byte[] pdfBytes = document.GeneratePdf();
            return File(pdfBytes, "application/pdf", "ListadoDeProvincias.pdf");
        }

        // Método para exportar provincias a Excel
        public IActionResult ExportarExcel()
        {
            var provincias = _context.Provincia
                .Select(p => new { p.id_provincia, p.provincia, p.id_departamento })
                .ToList();

            if (provincias == null || !provincias.Any())
            {
                return Content("No hay datos para exportar");
            }

            using (var package = new ExcelPackage())
            {
                var worksheet = package.Workbook.Worksheets.Add("Provincias");

                worksheet.Cells[1, 1].Value = "ID Provincia";
                worksheet.Cells[1, 2].Value = "Nombre Provincia";
                worksheet.Cells[1, 3].Value = "ID Departamento";

                for (int i = 0; i < provincias.Count; i++)
                {
                    worksheet.Cells[i + 2, 1].Value = provincias[i].id_provincia;
                    worksheet.Cells[i + 2, 2].Value = provincias[i].provincia;
                    worksheet.Cells[i + 2, 3].Value = provincias[i].id_departamento;
                }

                worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();

                var file = new FileContentResult(package.GetAsByteArray(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                {
                    FileDownloadName = "Provincias.xlsx"
                };

                return file;
            }
        }
    }
}
