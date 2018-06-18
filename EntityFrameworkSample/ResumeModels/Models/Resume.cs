using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ResumeModels.Models
{
    [Table("Resumes")]
    public class Resume
    {
        [Key]
        public long ResumeId { get; set; }
        public string Summary { get; set; }
        public long PersonId { get; set; }
        public virtual Person Person { get; set; }
    }
}
