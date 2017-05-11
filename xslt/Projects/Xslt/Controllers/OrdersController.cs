using System.Web.Mvc;

namespace Xslt.Controllers
{
    public class OrdersController : Controller
    {
        public ActionResult Get()
        {
            var mapPath = Server.MapPath("/App_Data/Order.xml");
            return File(mapPath, "application/xml");
        }
    }
}