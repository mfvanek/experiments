using WildLife.Models;

namespace WildLife.Lifecycles
{
    public sealed class RabbitLifecycle : ILifecycle
    {
        private static ILifecycle instance = null;

        private RabbitLifecycle() {}

        // TODO to base class
        public Generation GetGeneration(int age)
        {
            if (age >= this.GetOldAge())
            {
                return Generation.OLD;
            }
            if (age < this.GetAdultAge())
            {
                return Generation.YOUNG;
            }
            return Generation.ADULT;
        }

        public int GetMaxAge()
        {
            return 19;
        }

        public int GetOldAge()
        {
            return 12;
        }

        public int GetAdultAge()
        {
            return 4;
        }

        public static ILifecycle Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new RabbitLifecycle();
                }
                return instance;
            }
        }
    }
}
