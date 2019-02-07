using System;
using WildLife.Animals;

namespace WildLife.Families
{
    public sealed class WolfFamily : AbstractAnimalFamily
    {
        private static IAnimalFamily instance = null;

        private WolfFamily() {}

        public override Animal GetAdultSpecimen(bool isMale)
        {
            return isMale ? Wolf.GetAdultMale() : Wolf.GetAdultFemale();
        }

        public override Animal GetOldSpecimen(bool isMale)
        {
            throw new NotSupportedException("Old wolves don't survive");
        }

        public override Animal GetYoungSpecimen(bool isMale)
        {
            return isMale ? Wolf.GetYoungMale() : Wolf.GetYoungFemale();
        }

        public static IAnimalFamily Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new WolfFamily();
                }
                return instance;
            }
        }
    }
}
