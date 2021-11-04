package net.originmobi.pdv.utilitarios;

public class TesteAssinatura {

	public static void main(String[] args) {
		String chaveAcesso = "11180512271266000158550010000000011994707452";
		
		AssinaXML assinatura = new AssinaXML();
		assinatura.assinaXML(chaveAcesso);
	}

}
