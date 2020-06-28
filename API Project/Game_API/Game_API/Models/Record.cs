using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Game_API.Models
{
    public class Record
    {
        public int id { get; set; }
        public string name { get; set; }
        public long time { get; set; }
        public string date { get; set; }
    }
}
