using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

namespace dataService.Migrations
{
    public partial class initial : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "sponsors",
                columns: table => new
                {
                    _id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    sponsorname = table.Column<string>(type: "text", nullable: true),
                    contactname = table.Column<string>(type: "text", nullable: true),
                    contactemail = table.Column<string>(type: "text", nullable: true),
                    status = table.Column<string>(type: "text", nullable: true),
                    image = table.Column<string>(type: "text", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_sponsors", x => x._id);
                });

            migrationBuilder.CreateTable(
                name: "actions",
                columns: table => new
                {
                    _id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    actiontype = table.Column<string>(type: "text", nullable: true),
                    actiondate = table.Column<string>(type: "text", nullable: true),
                    actionuser = table.Column<string>(type: "text", nullable: true),
                    actiondetails = table.Column<string>(type: "text", nullable: true),
                    sponsors_id = table.Column<int>(type: "integer", nullable: false),
                    sponsors_id1 = table.Column<long>(type: "bigint", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_actions", x => x._id);
                    table.ForeignKey(
                        name: "FK_actions_sponsors_sponsors_id1",
                        column: x => x.sponsors_id1,
                        principalTable: "sponsors",
                        principalColumn: "_id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_actions_sponsors_id1",
                table: "actions",
                column: "sponsors_id1");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "actions");

            migrationBuilder.DropTable(
                name: "sponsors");
        }
    }
}
