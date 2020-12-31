using Microsoft.AspNetCore.Mvc;
using dataService.Domain.Models;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Linq;

namespace dataService.Controllers {

    [Route("/api/[controller]")]
    [ApiController]
    public class SponsorController : Controller {

      private readonly MainContext _context; 

        public SponsorController(MainContext context)
        {
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<sponsors>> GetAll()
        {
            return _context.sponsors.ToList();
        }




    }
    
}