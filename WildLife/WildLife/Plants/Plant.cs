using System;

namespace WildLife.Plants
{
    public abstract class Plant : ICloneable
    {
        public abstract void GrowUp();

        public virtual object Clone()
        {
            return this.MemberwiseClone();
        }
    }
}
