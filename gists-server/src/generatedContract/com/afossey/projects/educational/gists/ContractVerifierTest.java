package com.afossey.projects.educational.gists;

import com.afossey.projects.educational.gists.ContractTestsBase;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;
import org.junit.Test;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;

public class ContractVerifierTest extends ContractTestsBase {

	@Test
	public void validate_get_all_gists() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Accept", "application/json;charset=UTF-8");

		// when:
			ResponseOptions response = given().spec(request)
					.get("/gists");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).matches("application/json;charset=UTF-8.*");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).array().contains("['name']").isEqualTo("Foo");
			assertThatJson(parsedJson).array().contains("['id']").isEqualTo("1");
	}

	@Test
	public void validate_get_a_gist() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Accept", "application/json;charset=UTF-8");

		// when:
			ResponseOptions response = given().spec(request)
					.get("/gists/1");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).matches("application/json;charset=UTF-8.*");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['name']").isEqualTo("Foo");
			assertThatJson(parsedJson).field("['id']").isEqualTo("1");
	}

	@Test
	public void validate_create_a_gist() throws Exception {
		// given:
			MockMvcRequestSpecification request = given()
					.header("Accept", "application/json;charset=UTF-8")
					.header("Content-Type", "application/json;charset=UTF-8")
					.body("{\"name\":\"Foo\"}");

		// when:
			ResponseOptions response = given().spec(request)
					.post("/gists");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
			assertThat(response.header("Content-Type")).matches("application/json;charset=UTF-8.*");
		// and:
			DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
			assertThatJson(parsedJson).field("['name']").isEqualTo("Foo");
			assertThatJson(parsedJson).field("['id']").isEqualTo("1");
	}

}
