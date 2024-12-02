using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using app_compuware.Data;
using Microsoft.Data.SqlClient;
using app_compuware.Models;
using OfficeOpenXml;
using QuestPDF.Fluent;

namespace app_compuware.Controllers
{
    public class DistritoController : Controller
    {
        private readonly ApplicationDbContext _context;

        public DistritoController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Index()
        {
            return View();
        }
        [HttpGet]
        public JsonResult ListarProvincias()
        {
            var provincias = _context.Provincia
                .Select(p => new { p.id_provincia, p.provincia })
                .ToList();

            return Json(provincias);
        }


        [HttpGet]
        public JsonResult Listar()
        {
            string cad_sql = "EXEC sp_listar_distrito";
            List<Distrito> arr_distrito = _context.Distrito.FromSqlRaw(cad_sql).ToList();
            return Json(new { Data = arr_distrito });
        }

        [HttpGet]
        public JsonResult Consultar(string id_distrito)
        {
            string cad_sql = "EXEC sp_consultar_distrito @id_distrito";
            Distrito distrito = _context.Distrito
                .FromSqlRaw(cad_sql, new SqlParameter("@id_distrito", id_distrito))
                .ToList()
                .FirstOrDefault();
            return Json(distrito);
        }


        [HttpPost]
        public IActionResult Grabar([FromBody] Distrito distrito)
        {
            bool rpta = true;
            try
            {
                Distrito tmp_distrito = _context.Distrito
                    .FirstOrDefault(d => d.id_distrito == distrito.id_distrito);
                if (tmp_distrito == null)
                {
                    _context.Distrito.Add(distrito);
                }
                else
                {
                    tmp_distrito.distrito = distrito.distrito;
                    tmp_distrito.id_provincia = distrito.id_provincia;
                }
                _context.SaveChanges();
            }
            catch (Exception)
            {
                rpta = false;
            }
            return Json(new { resultado = rpta });
        }

        [HttpPost]
        public JsonResult Borrar(string id_distrito)
        {
            bool rpta = true;
            try
            {
                Distrito distrito = _context.Distrito
                    .FirstOrDefault(d => d.id_distrito == id_distrito);
                if (distrito != null)
                {
                    _context.Distrito.Remove(distrito);
                    _context.SaveChanges();
                }
                else
                {
                    rpta = false;
                }
            }
            catch (Exception)
            {
                rpta = false;
            }
            return Json(new { resultado = rpta });
        }

        public IActionResult ExportarPDF()
        {
            var distritos = _context.Distrito.Include(d => d.Provincia).ToList();
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
                            header.Cell().Text("ID Distrito").Bold();
                            header.Cell().Text("Nombre Distrito").Bold();
                            header.Cell().Text("Provincia").Bold();
                        });

                        foreach (var distrito in distritos)
                        {
                            table.Cell().Text(distrito.id_distrito);
                            table.Cell().Text(distrito.distrito);
                            table.Cell().Text(distrito.Provincia.provincia);
                        }
                    });
                });
            });

            byte[] pdfBytes = document.GeneratePdf();
            return File(pdfBytes, "application/pdf", "Distritos.pdf");
        }

        public IActionResult ExportarExcel()
        {
            var distritos = _context.Distrito.Include(d => d.Provincia)
                .Select(d => new
                {
                    d.id_distrito,
                    d.distrito,
                    Provincia = d.Provincia.provincia
                }).ToList();

            if (distritos == null || !distritos.Any())
            {
                return Content("No hay datos para exportar.");
            }

            using (var package = new ExcelPackage())
            {
                var worksheet = package.Workbook.Worksheets.Add("Distritos");

                worksheet.Cells[1, 1].Value = "ID Distrito";
                worksheet.Cells[1, 2].Value = "Nombre Distrito";
                worksheet.Cells[1, 3].Value = "Provincia";

                for (int i = 0; i < distritos.Count; i++)
                {
                    worksheet.Cells[i + 2, 1].Value = distritos[i].id_distrito;
                    worksheet.Cells[i + 2, 2].Value = distritos[i].distrito;
                    worksheet.Cells[i + 2, 3].Value = distritos[i].Provincia;
                }

                worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();

                var file = new FileContentResult(package.GetAsByteArray(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                {
                    FileDownloadName = "Distritos.xlsx"
                };

                return file;
            }
        }
    }
}
