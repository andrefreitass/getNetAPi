package helper;

import java.util.HashMap;

import org.json.simple.JSONObject;

import uteis.Datapool;

public class HelperUsuario {

	public Datapool dp;
	private HashMap<String, String> cadastro,alteracao,registro;
	
	@SuppressWarnings("unchecked")
	public HelperUsuario() {
		dp = new Datapool("usuarios.json");
		cadastro = (HashMap<String, String>) dp.getContent("BodyCadastro");
		alteracao = (HashMap<String, String>)dp.getContent("BodyAlteracao");
		registro = (HashMap<String, String>) dp.getContent("BodyRegistro");
	}
	
	
	@SuppressWarnings({"unchecked" })
	public JSONObject gerarBodyCadastro() {
		JSONObject usuarioJson = new JSONObject();
		usuarioJson.put("name", cadastro.get("nome"));
		usuarioJson.put("job", cadastro.get("cargo"));
		return usuarioJson;
	}
	
	
	@SuppressWarnings({"unchecked" })
	public JSONObject gerarBodyAlteracao() {
		JSONObject usuarioJson = new JSONObject();
		usuarioJson.put("name", alteracao.get("nome"));
		usuarioJson.put("job", alteracao.get("cargo"));
		return usuarioJson;
	}
	
	@SuppressWarnings({"unchecked" })
	public JSONObject gerarBodyRegistro() {
		JSONObject usuarioJson = new JSONObject();
		usuarioJson.put("email", registro.get("email"));
		usuarioJson.put("password", registro.get("senha"));
		return usuarioJson;
	}
	
	@SuppressWarnings({"unchecked" })
	public JSONObject gerarBodyRegistroUnsuccessful() {
		JSONObject usuarioJson = new JSONObject();
		usuarioJson.put("email", registro.get("email"));
		return usuarioJson;
	}
}
