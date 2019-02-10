using System;
using System.Collections.Generic;
using System.Linq;
using WildLife.Families;
using WildLife.Plants;
using WildLife.Animals;

namespace WildLife.Forest
{
    internal class Forest : IForest
    {
        private readonly ICollection<IAnimalFamily> herbivorous;
        private readonly ICollection<IAnimalFamily> carnivorous;
        private readonly ICollection<Plant> plants;

        internal Forest(ICollection<IAnimalFamily> herbivorous, ICollection<IAnimalFamily> carnivorous, ICollection<Plant> plants)
        {
            this.herbivorous = herbivorous;
            this.carnivorous = carnivorous;
            this.plants = plants;
        }

        public void Simulate()
        {
            foreach (var plant in plants)
            {
                plant.GrowUp();
            }

            List<Animal> allHerbivorousAnimals = new List<Animal>();
            Plant anyPlant = plants.First();
            foreach (var herbivorousFamily in herbivorous)
            {
                foreach (var animal in herbivorousFamily.GetAll())
                {
                    allHerbivorousAnimals.Add(animal);
                    if (animal.Eat(anyPlant))
                    {
                        // TODO Add IsAlive for plants
                        Console.WriteLine($"Plant {anyPlant} has been eaten by {animal}");
                    }
                }
            }

            foreach (var carnivorousFamily in carnivorous)
            {
                foreach (var animal in carnivorousFamily.GetAll())
                {
                    Animal target = allHerbivorousAnimals.Where(a => a.IsAlive).First();
                    if (animal.Attack(target))
                    {
                        Console.WriteLine($"Animal {target} has been eaten by {animal}");
                    }
                }
            }
        }
    }
}
