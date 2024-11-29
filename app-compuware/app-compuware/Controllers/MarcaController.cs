using app_compuware.Data;
using app_compuware.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;

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
    }
}
