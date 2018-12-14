﻿using WildLife.Animals;

namespace WildLife.Lifecycles
{
    public sealed class WolfLifecycle : ILifecycle
    {
        private static ILifecycle instance = null;

        private WolfLifecycle() {}

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
            return 17;
        }

        public int GetOldAge()
        {
            return 11;
        }

        public int GetAdultAge()
        {
            return 3;
        }

        public static ILifecycle Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new WolfLifecycle();
                }
                return instance;
            }
        }
    }
}