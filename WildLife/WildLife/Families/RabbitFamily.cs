using System;
using WildLife.Animals;

namespace WildLife.Families
{
    public sealed class RabbitFamily : IAnimalFamily
    {
        private static IAnimalFamily instance = null;

        private RabbitFamily() {}

        public Animal GetAdultSpecimen(bool isMale)
        {
            return isMale ? Wolf.GetAdultMale() : Wolf.GetAdultFemale();
        }

        public Animal GetOldSpecimen(bool isMale)
        {
            throw new NotSupportedException("Old wolves don't survive");
        }

        public Animal GetYoungSpecimen(bool isMale)
        {
            return isMale ? Wolf.GetYoungMale() : Wolf.GetYoungFemale();
        }

        public static IAnimalFamily Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new RabbitFamily();
                }
                return instance;
            }
        }
    }
}
