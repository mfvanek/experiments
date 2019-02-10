namespace WildLife.Families
{
    public sealed class RabbitFamily : AbstractAnimalFamily
    {
        private static IAnimalFamily one = null;

        private RabbitFamily() {}

        public static IAnimalFamily One
        {
            get
            {
                if (one == null)
                {
                    one = new RabbitFamily();
                }
                return one;
            }
        }
    }
}
