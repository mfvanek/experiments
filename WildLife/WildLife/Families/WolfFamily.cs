using System;
using WildLife.Models;

namespace WildLife.Families
{
    public sealed class WolfFamily : IAnimalFamily
    {
        private static WolfFamily instance = null;

        private WolfFamily() {}

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

        public static WolfFamily Instance
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
