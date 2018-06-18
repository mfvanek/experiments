using Ninject.Modules;
using ResumeModels.Models;

namespace EntityFrameworkSample
{
    class ResumeModule : NinjectModule
    {
        public override void Load()
        {
            Bind<IPersonRepository>().To<EFPersonRepository>();
            Bind<IPersonRepository>().To<FakePersonRepository>().WhenInjectedInto<DbInitializer>();
        }
    }
}
