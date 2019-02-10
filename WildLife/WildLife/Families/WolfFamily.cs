namespace WildLife.Families
{
    public sealed class WolfFamily : AbstractAnimalFamily
    {
        private static IAnimalFamily one = null;

        private WolfFamily() {}

        public static IAnimalFamily One
        {
            get
            {
                if (one == null)
                {
                    one = new WolfFamily();
                }
                return one;
            }
        }
    }
}
