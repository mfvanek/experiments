using WildLife.Lifecycles;

namespace WildLife.Models
{
    public abstract class Animal
    {
        protected Animal(ILifecycle lifecycle, bool carnivorous, bool isMale, int age)
        {
            this.Lifecycle = lifecycle;
            this.IsCarnivorous = carnivorous;
            this.IsAlive = true;
            this.IsMale = isMale;
            this.Age = age;
        }

        protected ILifecycle Lifecycle { get; }

        public Generation Generation { get => this.Lifecycle.GetGeneration(this.Age); }

        public int Age { get; protected set; }

        public bool IsMale { get; }

        public bool IsFemale { get => !IsMale; }

        public bool IsAlive { get; protected set; }

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

        public virtual bool Attack(Animal target)
        {
            this.DoAttack(target);
            return this.Eat(target);
        }

        protected abstract void DoAttack(Animal target);

        public virtual bool Eat(object target)
        {
            bool isTargetAnimal = target is Animal;
            bool isTargetHerbivorous = (target as Animal)?.IsHerbivorous ?? false;

            if (this.IsCarnivorous)
            {
                // Carnivores eat only herbivores
                bool result = isTargetAnimal && isTargetHerbivorous;
                if (result)
                {
                    (target as Animal).IsAlive = false;
                }
            }

            // Herbivorous animals eat only plants
            return !isTargetAnimal;
        }
    }
}
