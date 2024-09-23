package TestSample;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.Test;


public class HTTPRequests 
{
	int id;
	
	@Test(priority =1)
	void getUser() 
	{
		given()
		.when()
			.get("https://reqres.in/api/users?page=2")
		.then()
			.statusCode(200)
			.body("page", equalTo(2))
			.log().all();
		
	}
	
	@Test(priority =2)
	void createUser() 
	{
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", "R"
				+ "obster");
		data.put("job", "trainer");
		
		id=given()
			.contentType("application/json")
			.body(data)
		.when()
			.post("https://reqres.in/api/users")
			.jsonPath().getInt("id");
		//.then()
		//	.statusCode(201)
		//	.log().all();
		
	}
	
	@Test(priority =3, dependsOnMethods = {"createUser"})
	void updateUser() 
	{
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", "Mobster");
		data.put("job", "Tutor");
		
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.post("https://reqres.in/api/users"+id)
		.then()
			.statusCode(201)
			.log().all();
		
	}
	
	@Test(priority =4, dependsOnMethods = {"createUser"})
	void deleteUser() 
	{

		given()
			
		.when()
			.delete("https://reqres.in/api/users"+id)
		.then()
			.statusCode(204)
			.log().all();
		
	}
}
