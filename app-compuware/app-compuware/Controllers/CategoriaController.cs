using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using app_compuware.Data;
using Microsoft.Data.SqlClient;
using app_compuware.Models;


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

    }
}
