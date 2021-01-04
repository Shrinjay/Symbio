using Microsoft.AspNetCore.Mvc;
using dataService.Domain.Models;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System;

namespace dataService.Controllers {

    [Route("/")]
    [ApiController]
    public class SponsorController : Controller {

      private readonly MainContext _context; 

        public SponsorController(MainContext context)
        {
            _context = context;
        }

        [HttpGet]
        public List<sponsors> GetSpecific(sponsors querySponsor)
        {  
            Console.WriteLine(querySponsor);
            var sponsors = from s in _context.sponsors
            where (s.sponsorname.ToLower().Contains(querySponsor.sponsorname.ToLower()))
            where (s.contactname.ToLower().Contains(querySponsor.contactname.ToLower()))
            where (s.contactemail.ToLower().Contains(querySponsor.contactemail.ToLower()))
            where (s.status.ToLower().Contains(querySponsor.status.ToLower()))
            select s;
          
            return sponsors.ToList();
        }




    }
    
}