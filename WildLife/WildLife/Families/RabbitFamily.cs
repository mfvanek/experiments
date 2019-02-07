using System;
using WildLife.Animals;

namespace WildLife.Families
{
    public sealed class RabbitFamily : AbstractAnimalFamily
    {
        private static IAnimalFamily instance = null;

        private RabbitFamily() {}

        public override Animal GetAdultSpecimen(bool isMale)
        {
            return Rabbit.GetAdult(isMale);
        }

        public override Animal GetOldSpecimen(bool isMale)
        {
            throw new NotSupportedException("Old rabbits don't survive");
        }

        public override Animal GetYoungSpecimen(bool isMale)
        {
            return Rabbit.GetYoung(isMale);
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
