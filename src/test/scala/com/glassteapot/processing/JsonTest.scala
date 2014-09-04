

import org.scalatest.FunSuite
import org.scalatest.MustMatchers

import net.liftweb.json._
import net.liftweb.json.JsonDSL._
/**
 * Created by Arun Manivannan on 9/4/14.
 */

case class Person(name:String, address:Address){
  var friends=List[Person]()
}
case class Address(city:String, state:String)

class JsonTest extends FunSuite with MustMatchers{

  implicit val formats=DefaultFormats
  
  test ("should convert Object to JSON"){
    
    val arun=Person("Arun", Address("chennai", "Tamilnadu"))
    val daisy=Person("Daisy", Address("Madurai", "Tamilnadu"))
    
    val jason=Person("Jason", Address("Singapore", "Singapore"))
    val all=List(arun, daisy)
    
    jason.friends=all
    
    
    val json=
      ("person" -> 
      	("name" -> jason.name) ~
      	("friends" -> jason.friends.map{
      			eachFriend => 
      			  ("name" -> eachFriend.name) ~
      			  ("city" -> eachFriend.address.city) ~
      			  ("state" -> eachFriend.address.state)
      			
      		}
      	)
      	
      	//getFriends(jason)*/
      )
      
  
      println (pretty(render(json)))
    
    
    

  }


}

