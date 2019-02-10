using System;
using WildLife.Families;
using WildLife.Animals;
using WildLife.Forest;
using WildLife.Plants;

namespace WildLife
{
    class Program
    {
        static void Main(string[] args)
        {

            Console.WriteLine("Hello WildLife!\n\n");

            IForest forest = new Forester()
                .AddPlant(new Carrot(), 3)
                .AddHerbivorous(RabbitFamily.One.Add(Rabbit.GetAdult(true)).Add(Rabbit.GetAdult(false)).Add(Rabbit.GetAdult(false)).Add(Rabbit.GetAdult(false)))
                .AddCarnivorous(WolfFamily.One.Add(Wolf.GetAdultMale()).Add(Wolf.GetAdultFemale()).Add(Wolf.GetYoungMale()).Add(Wolf.GetYoungMale()))
                .Make();

            forest.Simulate();

            Console.ReadKey();
        }
    }
}
