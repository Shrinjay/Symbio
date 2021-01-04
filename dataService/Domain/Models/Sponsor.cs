using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace dataService.Domain.Models{

    public class sponsors {
        [Key]
        public long _id {get; set;}
        public string sponsorname{get; set;}
        public string contactname{get; set;}
        public string contactemail{get; set;}
        public string status{get; set;}

        public string image {get; set;}

        public IList<actions> sponsoractions {get; set;} = new List<actions>();
    }
}