namespace WildLife.Models
{
    public abstract class Animal
    {

        public bool IsCarnivorous { get; protected set; }

        public bool IsHerbivorous { get => !IsCarnivorous; }

        public abstract string Cry();

        public abstract void Jump();

        public abstract void Run();

        public abstract void Bite(Animal target);

        public abstract bool Attack(Animal target);
    }
}
