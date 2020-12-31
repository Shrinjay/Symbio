using Microsoft.AspNetCore.Mvc;
using dataService.Domain.Models;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System;

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
        public List<sponsors> Count(string sponsorname="", string contactname="", string contactemail="", string status="")
        {
            
            var sponsors = from s in _context.sponsors
            where (s.sponsorname.ToLower().Contains(sponsorname.ToLower()))
            where (s.contactname.ToLower().Contains(contactname.ToLower()))
            where (s.contactemail.ToLower().Contains(contactemail.ToLower()))
            where (s.status.ToLower().Contains(status.ToLower()))
            select s;
          
          
            return sponsors.ToList();

        }




    }
    
}