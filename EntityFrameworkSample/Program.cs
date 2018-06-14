using Ninject;
using ResumeModels.Models;
using System;

namespace EntityFrameworkSample
{
    class Program
    {
        static readonly IKernel kernel = new StandardKernel();

        static Program()
        {
            AddBindings();
        }

        private static void AddBindings()
        {
            kernel.Bind<IPersonRepository>().To<FakePersonRepository>();
        }

        static void Main(string[] args)
        {
            var persons = kernel.Get<IPersonRepository>().GetPersons();
            foreach (var person in persons)
            {
                Console.WriteLine($"person = {person.FullName}, {person.Age} year(s)");
            }
            Console.ReadKey();
        }
    }
}
