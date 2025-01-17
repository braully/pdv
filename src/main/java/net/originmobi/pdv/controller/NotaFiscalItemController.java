package net.originmobi.pdv.controller;

import net.originmobi.pdv.enumerado.NotaFiscalTipo;
import net.originmobi.pdv.service.NotaFiscalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/notafiscalitem")
public class NotaFiscalItemController {
	
	@Autowired
	private NotaFiscalItemService itens;
	
	@PostMapping
	public @ResponseBody String insereItemNota(@RequestParam Map<String, String> request) {
		Long prod = Long.decode(request.get("codprod"));
		Long codnota = Long.decode(request.get("nota"));
		int qtd = Integer.parseInt(request.get("qtd"));
		String tipo = request.get("tipo");
		
		NotaFiscalTipo tipoNota = tipo.equals("ENTRADA") ? NotaFiscalTipo.ENTRADA : NotaFiscalTipo.SAIDA; 
		
		itens.insere(prod, codnota, qtd, tipoNota);
		
		return "ok";
	}
	
	@DeleteMapping
	public @ResponseBody String remove(@RequestParam Map<String, String> request) {
		Long notaitem = Long.decode(request.get("notaitem"));
		Long nota = Long.decode(request.get("nota"));
		
		itens.remove(notaitem, nota);
		
		return "ok";
	}

}
