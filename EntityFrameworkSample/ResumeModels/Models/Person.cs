using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ResumeModels.Models
{
    [Table("Persons")]
    public class Person
    {
        [Key]
        public long PersonId { get; set; }
        [Required]
        public string FirstName { get; set; }
        public string MiddleName { get; set; }
        [Required]
        public string LastName { get; set; }
        [Required]
        public DateTime BirthDate { get; set; }
        public virtual ICollection<Resume> Resumes { get; set; }

        [NotMapped]
        public string FullName
        {
            get
            {
                return MiddleName != null ? $"{LastName} {MiddleName} {FirstName}" : $"{LastName} {FirstName}";
            }
        }

        [NotMapped]
        public int Age
        {
            get
            {
                int age = 0;
                if (BirthDate != null)
                {
                    var today = DateTime.Today;
                    age = today.Year - BirthDate.Year;
                    if (BirthDate > today.AddYears(-age))
                    {
                        --age;
                    }
                }
                return age;
            }
        }
    }
}
