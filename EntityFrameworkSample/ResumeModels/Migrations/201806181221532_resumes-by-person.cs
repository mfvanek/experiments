namespace ResumeModels.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class resumesbyperson : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Resumes",
                c => new
                    {
                        ResumeId = c.Long(nullable: false, identity: true),
                        Summary = c.String(),
                        PersonId = c.Long(nullable: false),
                    })
                .PrimaryKey(t => t.ResumeId)
                .ForeignKey("dbo.Persons", t => t.PersonId, cascadeDelete: true)
                .Index(t => t.PersonId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Resumes", "PersonId", "dbo.Persons");
            DropIndex("dbo.Resumes", new[] { "PersonId" });
            DropTable("dbo.Resumes");
        }
    }
}
