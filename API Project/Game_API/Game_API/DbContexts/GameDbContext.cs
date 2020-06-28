using Game_API.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Game_API.DbContexts
{
    public class GameDbContext : DbContext
    {
        public GameDbContext(DbContextOptions<GameDbContext> options) : base(options) { }

        public DbSet<Record> records { get; set; }
        public DbSet<Setting> settings { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            //table name
            modelBuilder.Entity<Record>().ToTable("records");
            modelBuilder.Entity<Setting>().ToTable("setting");

            //specific the primary key
            modelBuilder.Entity<Record>().HasKey(record => record.id);
            modelBuilder.Entity<Setting>().HasKey(setting => setting.id);
        }
    }
}
