using Ninject.Modules;
using ResumeModels.Models;

namespace EntityFrameworkSample
{
    class ResumeModule : NinjectModule
    {
        public override void Load()
        {
            //Bind<IPersonRepository>().To<FakePersonRepository>();
            Bind<IPersonRepository>().To<EFPersonRepository>();
        }
    }
}
