using System.Web.Mvc;

namespace Xslt.Controllers
{
    public class TransformationsController : Controller
    {
        public ActionResult Get()
        {
            var mapPath = Server.MapPath("/App_Data/OrderTransformation.xsl");
            return File(mapPath, "application/xml");
        }
    }
}