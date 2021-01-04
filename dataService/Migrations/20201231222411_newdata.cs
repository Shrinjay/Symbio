using Microsoft.EntityFrameworkCore.Migrations;

namespace dataService.Migrations
{
    public partial class newdata : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<float>(
                name: "netfinancialchange",
                table: "actions",
                type: "real",
                nullable: false,
                defaultValue: 0f);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "netfinancialchange",
                table: "actions");
        }
    }
}
