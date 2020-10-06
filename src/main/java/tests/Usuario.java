package tests;

import java.util.HashMap;
import java.util.Random;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import helper.HelperUsuario;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import suport.Request;
import uteis.ReportListener;

@Listeners({ ReportListener.class })
@Epic("Teste de API")
@Feature("Realiza a consulta, cadastro, alteração e exclusão de usuario")
@Severity(SeverityLevel.TRIVIAL)
public class Usuario extends Request {

	private HelperUsuario helper;
	private String usuarioId;
	private HashMap<String, String>id,unknownId;
	private Random random = new Random();


	@BeforeClass
	public void init() {
		new Request();
		helper = new HelperUsuario();
		setBaseUri("https://reqres.in/api/");
	}
	
	@Test(groups = { "Fluxo básico" })
	public void CEN01_criacaoUsuario() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			post(helper.gerarBodyCadastro().toString(), casoTeste.get("path"));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
			usuarioId = response.jsonPath().getString("id");
		}
	}
	
	
	@Test(groups = { "Fluxo básico" })
	public void CEN02_recuperarListaUsuariosPorPagina() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			get(casoTeste.get("path"));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
			id = (HashMap<String, String>) response.jsonPath().getList("data").get(0);
			validatorBody(casoTeste.get("baseline"));
		}
	}
	
	
	@Test(groups = { "Fluxo básico" })
	public void CEN03_recuperarUsuarioPorId() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			get(casoTeste.get("path") + String.valueOf(id.get("id")));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
			validatorBody(casoTeste.get("baseline"));
		}
	}
	
	@Test(groups = { "Fluxo básico" })
	public void CEN04_recuperarUsuarioNaoCadastrado() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			random.nextInt(1000);
			get(casoTeste.get("path") + String.valueOf(id.get("id"))+random.nextInt(1000));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
			validatorBodyEmpty(casoTeste.get("baseline"));
		}
	}
	
	
	@Test(groups = { "Fluxo básico" })
	public void CEN05_recuperarListaResourceUsuario() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			get(casoTeste.get("path"));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
			unknownId = (HashMap<String, String>) response.jsonPath().getList("data").get(0);
			validatorBody(casoTeste.get("baseline"));
		}
	}
	
	@Test(groups = { "Fluxo básico" })
	public void CEN06_recuperarUsuarioResourcePorId() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			get(casoTeste.get("path") + String.valueOf(unknownId.get("id")));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
			validatorBody(casoTeste.get("baseline"));
		}
	}
	
	
	@Test(groups = { "Fluxo básico" })
	public void CEN07_recuperarUsuarioResourceNaoCadastrado() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			get(casoTeste.get("path") + String.valueOf(unknownId.get("id"))+random.nextInt(1000));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
			validatorBodyEmpty(casoTeste.get("baseline"));
		}
	}
	
	@Test(groups = { "Fluxo básico" })
	public void CEN08_alteracaoUsuario() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			put(helper.gerarBodyAlteracao().toString(), casoTeste.get("path") + usuarioId + "");
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
		}
	}
	
	@Test(groups = { "Fluxo básico" })
	public void CEN09_alteracaoUsuarioPatch() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			patch(helper.gerarBodyAlteracao().toString(), casoTeste.get("path") + usuarioId + "");
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
		}
	}
	
	
	@Test(groups = { "Fluxo básico" })
	public void CEN10_registroUsuario() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			post(helper.gerarBodyRegistro().toString(), casoTeste.get("path"));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
			usuarioId = response.jsonPath().getString("id");
		}
	}
	
	@Test(groups = { "Fluxo básico" })
	public void CEN11_registroUsuarioUnsuccessful() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			post(helper.gerarBodyRegistroUnsuccessful().toString(), casoTeste.get("path"));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
			validatorBody(casoTeste.get("baseline"));
		}
	}
	
	
	@Test(groups = { "Fluxo básico" })
	public void CEN12_realizaLogin() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			post(helper.gerarBodyRegistro().toString(), casoTeste.get("path"));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
		}
	}
	
	@Test(groups = { "Fluxo básico" })
	public void CEN13_realizaLoginUnsuccessful() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			post(helper.gerarBodyRegistroUnsuccessful().toString(), casoTeste.get("path"));
			validatorHeaders("Content-Type", casoTeste.get("contentType"));
			validatorStatusCode(casoTeste.get("statusCode"));
			validatorBody(casoTeste.get("baseline"));
		}
	}
	
	
	@Test(groups = { "Fluxo básico" })
	public void CEN14_excluirUsuario() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			delete(casoTeste.get("path") + usuarioId + "");
			validatorStatusCode(casoTeste.get("statusCode"));
		}
	}
	
	@Test(groups = { "Fluxo básico" })
	public void CEN15_responseDelayed() {
		for (JsonPath casoTeste : helper.dp.getTestCasesToJsonPath()) {
			anexaDescricao(casoTeste.get("CT"));
			get(casoTeste.get("path"));
			validatorStatusCode(casoTeste.get("statusCode"));
			validatorBody(casoTeste.get("baseline"));
		}
	}
}
