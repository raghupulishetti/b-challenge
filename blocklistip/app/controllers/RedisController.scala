package controllers

import javax.inject._
import play.api.cache.redis.CacheApi
import play.api.mvc._
import services.RedisService

@Singleton
class RedisController @Inject()(cache: CacheApi, cc: ControllerComponents, redisService: RedisService) extends AbstractController(cc) {

  def index(ip: Option[String]) = Action { implicit request =>
    val isExists = redisService.isIpExists(ip)
    var message = "";
    if (isExists) {
      message = "IP Address is blocked for access..."
    } else {
      message = "IP Address is active for access..."
    }
    println(message)
    Ok(views.html.index(message))
  }

}

