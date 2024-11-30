using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using app_compuware.Data;
using Microsoft.Data.SqlClient;
using app_compuware.Models;
using OfficeOpenXml;
using QuestPDF.Fluent;


namespace app_compuware.Controllers
{
    public class CategoriaController : Controller
    {
        private readonly ApplicationDbContext _context;

        public CategoriaController(ApplicationDbContext context)
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

            String cad_sql = "exec sp_listar_categoria";
            List<Categoria> arr_categoria = _context.Categoria.FromSqlRaw(cad_sql).ToList();

            return Json(new { Data = arr_categoria });
        }

        [HttpGet]
        public JsonResult Consultar(string id_categoria)
        {
            String cad_sql = "exec sp_consultar_categoria @id_categoria";

            Categoria categoria = new Categoria();

            categoria = _context.Categoria.FromSqlRaw(cad_sql, new SqlParameter("@id_categoria", id_categoria)).ToList().FirstOrDefault();


            return Json(categoria);
        }
        [HttpPost]
        public IActionResult Grabar([FromBody] Categoria categoria)
        {
            bool rpta = true;
            try
            {
                Categoria tmp_categoria = null;
                tmp_categoria = (from cat in _context.Categoria
                                 where cat.id_categoria == categoria.id_categoria
                                 select cat).FirstOrDefault();
                if (tmp_categoria == null)
                {
                    _context.Categoria.Add(categoria);
                    _context.SaveChanges();
                }
                else
                {
                    tmp_categoria.id_categoria = categoria.id_categoria;
                    tmp_categoria.categoria = categoria.categoria;
                    _context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                rpta = false;
            }
            return Json(new { resultado = rpta });
        }
        public JsonResult Borrar(String id_categoria)
        {
            bool rpta = true;
            try
            {
                Categoria categoria = new Categoria();
                categoria = (from cat in _context.Categoria
                             where cat.id_categoria == id_categoria
                             select cat).FirstOrDefault();
                _context.Categoria.Remove(categoria);
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
            var categoria = _context.Categoria.ToList();
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
                            header.Cell().Text("ID Categoria").Bold();
                            header.Cell().Text("Nombre Categoria").Bold();
                        });

                        
                        foreach (var marca in categoria)
                        {
                            table.Cell().Text(marca.id_categoria.ToString());
                            table.Cell().Text(marca.categoria);
                        }
                    });
                });
            });

            byte[] pdfBytes = document.GeneratePdf();
            return File(pdfBytes, "application/pdf", "ListadoDeCategoria.pdf");
        }

        public IActionResult ExportarExcel()
        {
            
            var marca = _context.Categoria
                .Select(m => new { m.id_categoria, m.categoria })
                .ToList();

            
            if (marca == null || !marca.Any())
            {
                return Content("No hay datos para exportar");
            }

            
            using (var package = new ExcelPackage())
            {
                var worksheet = package.Workbook.Worksheets.Add("Marcas");

                
                worksheet.Cells[1, 1].Value = "ID Categori";
                worksheet.Cells[1, 2].Value = "Nombre Categoria";

                
                for (int i = 0; i < marca.Count; i++)
                {
                    worksheet.Cells[i + 2, 1].Value = marca[i].id_categoria;
                    worksheet.Cells[i + 2, 2].Value = marca[i].categoria;
                }

                
                worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();

                
                var file = new FileContentResult(package.GetAsByteArray(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                {
                    FileDownloadName = "Categoria.xlsx"
                };

                return file;
            }
        }

    }
}
