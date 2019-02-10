using System.Collections.Generic;
using WildLife.Families;
using WildLife.Plants;

namespace WildLife.Forest
{
    public class Forester
    {
        private readonly IList<IAnimalFamily> herbivorous = new List<IAnimalFamily>();
        private readonly IList<IAnimalFamily> carnivorous = new List<IAnimalFamily>();
        private readonly IList<Plant> plants = new List<Plant>();

        public Forester AddHerbivorous(IAnimalFamily animalFamily)
        {
            herbivorous.Add(animalFamily);
            return this;
        }

        public Forester AddCarnivorous(IAnimalFamily animalFamily)
        {
            carnivorous.Add(animalFamily);
            return this;
        }

        public Forester AddPlant(Plant plant)
        {
            plants.Add(plant);
            return this;
        }

        public Forester AddPlant(Plant plant, uint count)
        {
            for (uint i = 0; i < count; ++i)
            {
                plants.Add((Plant)plant.Clone());
            }
            return this;
        }

        public IForest Make()
        {
            return new Forest(herbivorous, carnivorous, plants);
        }
    }
}
