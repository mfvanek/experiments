using Ninject;
using ResumeModels.Models;
using System;

namespace EntityFrameworkSample
{
    class Program
    {
        static readonly IKernel kernel = new StandardKernel(new ResumeModule());

        static void Main(string[] args)
        {
            var persons = kernel.Get<IPersonRepository>().GetPersons();
            foreach (var person in persons)
            {
                Console.WriteLine($"person = {person.FullName}, {person.Age} year(s)");
            }

            //using (var db = new PersonDbContext())
            //{
            //    db.Persons.AddRange(persons);
            //    db.SaveChanges();
            //    Console.WriteLine("Data saved");
            //}
            Console.ReadKey();
        }
    }
}
