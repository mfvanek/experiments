using WildLife.Animals;

namespace WildLife.Lifecycles
{
    public interface ILifecycle
    {
        Generation GetGeneration(int age);

        int GetMaxAge();

        int GetOldAge();

        int GetAdultAge();
    }
}
