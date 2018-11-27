namespace WildLife.Models
{
    public abstract class Animal
    {
        protected Animal(bool carnivorous)
        {
            this.IsCarnivorous = carnivorous;
        }

        public bool IsCarnivorous { get; }

        public bool IsHerbivorous { get => !IsCarnivorous; }

        public abstract string Cry();

        /// <summary>
        /// Jump
        /// </summary>
        /// <returns>Returns the length of the jump in meters</returns>
        public abstract double Jump();

        /// <summary>
        /// Run
        /// </summary>
        /// <returns>Returns the running speed in km\h</returns>
        public abstract double Run();

        public abstract void Bite(Animal target);

        public abstract bool Attack(Animal target);

        public virtual bool Eat(object target)
        {
            bool isTargetAnimal = target is Animal;
            bool isTargetHerbivorous = (target as Animal)?.IsHerbivorous ?? false;

            if (this.IsCarnivorous)
            {
                // Carnivores eat only herbivores
                return isTargetAnimal && isTargetHerbivorous;
            }

            // Herbivorous animals eat only plants
            return !isTargetAnimal;
        }
    }
}
