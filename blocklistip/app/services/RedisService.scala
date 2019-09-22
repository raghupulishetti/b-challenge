package services

import javax.inject.Inject
import play.api.cache.redis.CacheApi

class RedisService @Inject()(cache: CacheApi){

  def isIpExists(ip:Option[String]) : Boolean = {
    var v = cache.list[String]("Blocked_IPList").toList
    return v.contains(ip.get)
  }
}
