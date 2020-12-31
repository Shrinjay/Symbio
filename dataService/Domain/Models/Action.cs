using System.ComponentModel.DataAnnotations;

namespace dataService.Domain.Models {
    
    public class actions {
        
        [Key]
        public long _id {get; set;}
        public string actiontype{get; set;}
        public string actiondate{get; set;}
        public string actionuser{get; set;}
        public string actiondetails{get; set;}

        public int sponsors_id {get; set;}
        public sponsors sponsors {get; set;}

    }
}