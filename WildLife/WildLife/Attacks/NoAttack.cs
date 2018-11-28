using WildLife.Animals;

namespace WildLife.Attacks
{
    internal class NoAttack : IAttack
    {
        public void Attack(Animal attacker, Animal attacked)
        {
            attacker.Cry();
            attacker.Run();
        }
    }
}
