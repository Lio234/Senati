using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using app_compuware.Data;
using Microsoft.Data.SqlClient;
using app_compuware.Models;
using OfficeOpenXml;
using QuestPDF.Fluent;

namespace app_compuware.Controllers
{
    public class DepartamentoController : Controller
    {
        private readonly ApplicationDbContext _context;

        public DepartamentoController(ApplicationDbContext context)
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
            string cad_sql = "exec sp_listar_departamento";
            List<Departamento> arr_departamento = _context.Departamento.FromSqlRaw(cad_sql).ToList();
            return Json(new { Data = arr_departamento });
        }


        
        [HttpGet]
        public JsonResult Consultar(string id_departamento)
        {
            string cad_sql = "exec sp_consultar_departamento @id_departamento";

            Departamento departamento = new Departamento();
            departamento = _context.Departamento
                .FromSqlRaw(cad_sql, new SqlParameter("@id_departamento", id_departamento))
                .ToList()
                .FirstOrDefault();

            return Json(departamento);
        }

        
        [HttpPost]
        public IActionResult Grabar([FromBody] Departamento departamento)
        {
            bool rpta = true;
            try
            {
                Departamento tmp_departamento = null;
                tmp_departamento = (from dep in _context.Departamento
                                    where dep.id_departamento == departamento.id_departamento
                                    select dep).FirstOrDefault();
                if (tmp_departamento == null)
                {
                    _context.Departamento.Add(departamento);
                    _context.SaveChanges();
                }
                else
                {
                    tmp_departamento.id_departamento = departamento.id_departamento;
                    tmp_departamento.departamento = departamento.departamento;
                    _context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                rpta = false;
            }
            return Json(new { resultado = rpta });
        }

        
        [HttpPost]
        public JsonResult Borrar(string id_departamento)
        {
            bool rpta = true;
            try
            {
                Departamento departamento = new Departamento();
                departamento = (from dep in _context.Departamento
                                where dep.id_departamento == id_departamento
                                select dep).FirstOrDefault();
                _context.Departamento.Remove(departamento);
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
            var departamentos = _context.Departamento.ToList();

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
                            header.Cell().Text("ID Departamento").Bold();
                            header.Cell().Text("Nombre Departamento").Bold();
                        });

                        
                        foreach (var depto in departamentos)
                        {
                            table.Cell().Text(depto.id_departamento);
                            table.Cell().Text(depto.departamento);
                        }
                    });
                });
            });

            byte[] pdfBytes = document.GeneratePdf();
            return File(pdfBytes, "application/pdf", "ListadoDeDepartamentos.pdf");
        }

        
        public IActionResult ExportarExcel()
        {
            var departamentos = _context.Departamento
                .Select(d => new { d.id_departamento, d.departamento })
                .ToList();

            if (departamentos == null || !departamentos.Any())
            {
                return Content("No hay datos para exportar");
            }

            using (var package = new ExcelPackage())
            {
                var worksheet = package.Workbook.Worksheets.Add("Departamentos");

                
                worksheet.Cells[1, 1].Value = "ID Departamento";
                worksheet.Cells[1, 2].Value = "Nombre Departamento";

                
                for (int i = 0; i < departamentos.Count; i++)
                {
                    worksheet.Cells[i + 2, 1].Value = departamentos[i].id_departamento;
                    worksheet.Cells[i + 2, 2].Value = departamentos[i].departamento;
                }

                worksheet.Cells[worksheet.Dimension.Address].AutoFitColumns();

                
                var file = new FileContentResult(package.GetAsByteArray(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                {
                    FileDownloadName = "Departamentos.xlsx"
                };

                return file;
            }
        }
    }
}
