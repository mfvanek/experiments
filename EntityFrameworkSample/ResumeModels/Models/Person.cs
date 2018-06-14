using System;

namespace ResumeModels
{
    public class Person
    {
        public long PersonId { get; set; }
        public string FirstName { get; set; }
        public string MiddleName { get; set; }
        public string LastName { get; set; }
        public DateTime BirthDate { get; set; }

        public string FullName
        {
            get
            {
                return MiddleName != null ? $"{LastName} {MiddleName} {FirstName}" : $"{LastName} {FirstName}";
            }
        }

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
