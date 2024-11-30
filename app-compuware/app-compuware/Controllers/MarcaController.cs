using app_compuware.Data;
using app_compuware.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using OfficeOpenXml;
using QuestPDF.Fluent;


namespace app_compuware.Controllers
{
    public class MarcaController : Controller
    {
        public readonly ApplicationDbContext _context;

        public MarcaController(ApplicationDbContext context)
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
            
            String cad_sql = "exec sp_listar_marca";
            List<Marca> arr_marca = _context.Marca.FromSqlRaw(cad_sql).ToList();

            return Json(new { Data = arr_marca });
        }

        [HttpGet]
        public JsonResult Consultar(string id_marca)
        {
            
            String cad_sql = "exec sp_consultar_marca @id_marca";

            Marca marca = new Marca();

            // Llama al procedimiento con el parámetro id_marca
            marca = _context.Marca.FromSqlRaw(cad_sql, new SqlParameter("@id_marca", id_marca)).ToList().FirstOrDefault();

            return Json(marca);
        }

        [HttpPost]
        public IActionResult Grabar([FromBody] Marca marca)
        {
            bool rpta = true;
            try
            {
                Marca tmp_marca = null;
                tmp_marca = (from mar in _context.Marca
                             where mar.id_marca == marca.id_marca
                             select mar).FirstOrDefault();
                if (tmp_marca == null)
                {
                    _context.Marca.Add(marca);
                    _context.SaveChanges();
                }
                else
                {
                    tmp_marca.id_marca = marca.id_marca;
                    tmp_marca.marca = marca.marca;
                    _context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                rpta = false;
            }
            return Json(new { resultado = rpta });
        }

        public JsonResult Borrar(String id_marca)
        {
            bool rpta = true;
            try
            {
                Marca marca = new Marca();
                marca = (from mar in _context.Marca
                         where mar.id_marca == id_marca
                         select mar).FirstOrDefault();
                _context.Marca.Remove(marca);
                _context.SaveChanges();

            }
            catch (Exception ex)
            {
                rpta = false;
            }
            return Json(new { resultado = rpta });
        }

        public IActionResult ExportarPDF()
        {
            var marcas = _context.Marca.ToList();
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
                        });

                        table.Header(header =>
                        {
                            header.Cell().Text("ID Marca").Bold();
                            header.Cell().Text("Nombre Marca").Bold();
                        });

                        
                        foreach (var marca in marcas)
                        {
                            table.Cell().Text(marca.id_marca.ToString());
                            table.Cell().Text(marca.marca);
                        }
                    });
                });
            });

            byte[] pdfBytes = document.GeneratePdf();
            return File(pdfBytes, "application/pdf", "ListadoDeMarcas.pdf");
        }


        public IActionResult ExportarExcel()
        {
            
            var marca = _context.Marca
                .Select(m => new { m.id_marca, m.marca }) 
                .ToList();

            
            if (marca == null || !marca.Any())
            {
                return Content("No hay datos para exportar");
            }

            using (var package = new ExcelPackage())
            {
                var worksheet = package.Workbook.Worksheets.Add("Marcas");

                worksheet.Cells[1, 1].Value = "ID Marca";
                worksheet.Cells[1, 2].Value = "Nombre Marca";

                for (int i = 0; i < marca.Count; i++)
                {
                    worksheet.Cells[i + 2, 1].Value = marca[i].id_marca;
                    worksheet.Cells[i + 2, 2].Value = marca[i].marca;
                }

                worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();

                var file = new FileContentResult(package.GetAsByteArray(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                {
                    FileDownloadName = "Marca.xlsx"
                };

                return file;
            }
        }
    }
}
