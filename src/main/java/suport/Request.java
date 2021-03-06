package suport;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.proxy;

import java.util.Map;

import org.json.simple.JSONObject;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import uteis.Log;
import uteis.ReportListener;
import uteis.SoftAssert;


public class Request {

	RequestSpecification request;
	public JsonPath jsonObject;
	static SoftAssert soft;
	protected static Response response;
	protected String baseUri;
	
	public Request() {
		soft = new SoftAssert();
		ReportListener.setAsserts(soft);
	}
	
	
	/**
	 * Envia requisi��o  POST para host
	 * @param json
	 * @param path
	 * @return
	 */
	public void post(Object json,Object path) {
		anexaDescricao("Enviando requisi��o POST para host: <br><b>" + baseUri  +  path.toString() + "</b>");
		response = given().
				basePath(path.toString()).
				body(json).
				when().
				post(baseUri + path);
	}
	
	/**
	 * Envia requisi��o  PUT para host
	 * @param json
	 * @param path
	 * @return
	 */
	public void put(Object json,String path) {
		anexaDescricao("Enviando requisi��o PUT para host: <br><b>" + baseUri  +  path + "</b>");
		response = given().
				basePath(path).
				body(json).
				when().
				put(baseUri + path);
	}

	/**
	 * Envia requisi��o PATCH para host
	 * @param json
	 * @param path
	 * @return
	 */
	public void patch(Object json,String path) {
		anexaDescricao("Enviando requisi��o PATCH para host: <br><b>" + baseUri  +  path + "</b>");
		response = given().
				basePath(path).
				body(json).
				when().
				patch(baseUri + path);
	}
	/**
	 * Envia requisi��o  GET para host
	 * @param path
	 * @return
	 */
	public void get(Object path) {
		anexaDescricao("Enviando requisi��o GET para host: <br><b>" + baseUri + path.toString() + "</b>");
		response = given().	
				when().
				get(baseUri + path.toString());
	}
	
	/**
	 * Eniva requisi��o  DELETE para o host
	 * @param path
	 * @return
	 */
	public void delete(String path) {
		anexaDescricao("Enviando requisi��o DELETE para host: <br><b>" + baseUri + path + "</b>");
		response = given().
				when().
				delete(baseUri + path);
	}

	/**
	 * Anexa Descri��o  no relat�rio
	 * @param desc
	 */
	public void anexaDescricao(Object desc) {
		ReportListener.setSteps(desc.toString());
		Log.info(desc.toString());
	}	

	/**
	 * Validador de Body
	 * @param resp - mensagem a ser validada
	 * @param baseline - referencia a ser comparada com a mensagem
	 * @param casoTeste
	 */
	public void validatorBody(Object baseline){
		Log.info("Valida��o do BODY");
		try {
			soft.assertTrue(response.getBody().jsonPath().get().toString().equals(baseline.toString()), "Valida��o do Body " + "</br><b>Valor atual:</b> " + response.getBody().jsonPath().get().toString() + "</br><b>Valor esperado:</b> " + baseline.toString());
		}catch (Exception e) {
			soft.assertTrue(false, "Valida��o do Body");
		}
	}
	
	/**
	 * Validador de Body vazio
	 * @param resp - mensagem a ser validada
	 * @param baseline - referencia a ser comparada com a mensagem
	 * @param casoTeste
	 */
	public void validatorBodyEmpty(Object baseline) {
		Log.info("Valida��o do BODY");
		try {
			soft.assertTrue(response.getBody().asString().equals(baseline), "Valida��o do body vazio: " +  
		"</br><b>Valor atual:</b> " + response.getBody().asString().equals(baseline) + "</br><b>Valor esperado:</b> " + baseline);
		
		}catch (Exception e) {
			soft.assertTrue(false, "Valida��o de body vazio: " + response.getBody().asString());
		}
	}

	/**
	 * Validador de Status Code
	 * @param resp - mensagem a ser validada
	 * @param baseline - referencia a ser comparada com a mensagem
	 * @param casoTeste
	 */
	public void validatorStatusCode(Object baseline){
		Log.info("Valida��o do STATUS CODE: " + Integer.valueOf(baseline.toString()));
		try {
			soft.assertTrue(response.getStatusCode() == Integer.valueOf(baseline.toString()), "Valida��o do StatusCode " + "</br><b>StatusCode atual:</b> " + response.getStatusCode() + "</br><b>StatusCode esperado:</b> " + Integer.valueOf(baseline.toString()));
		}catch (Exception e) {
			soft.assertTrue(false, "Valida��o do StatusCode");
		}
	}
	

	/**
	 * Validador de campos do Header
	 * @param resp - mensagem a ser validada
	 * @param headerName - campo a ser validado do header
	 * @param baseline - campo a ser comparado com o campo da mensagem
	 * @param casoTeste
	 */
	public void validatorHeaders(Object headerName, Object baseline){
		Log.info("Valida��o do HEADER - Campo: " + headerName);
		try {
			soft.assertTrue(response.getHeader(headerName.toString()).equals(baseline), "Valida��o de campos do Header: " + headerName + "</br><b>Valor atual:</b> " + response.getHeader(headerName.toString()) + "</br><b>Valor esperado:</b> " + baseline);
		}catch (Exception e) {
			soft.assertTrue(false, "Valida��o de campos do Header: " + headerName);
		}
	}

	public String getBaseUri() {
		return baseUri;
	}

	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}
}
