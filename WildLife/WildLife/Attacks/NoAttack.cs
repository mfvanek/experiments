using WildLife.Models;

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
