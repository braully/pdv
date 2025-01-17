package net.originmobi.pdv.controller;

import net.originmobi.pdv.filter.PagarParcelaFilter;
import net.originmobi.pdv.model.Caixa;
import net.originmobi.pdv.model.Fornecedor;
import net.originmobi.pdv.model.PagarParcela;
import net.originmobi.pdv.model.PagarTipo;
import net.originmobi.pdv.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/pagar")
@SessionAttributes("filter")
public class PagarController {

	private static final String PAGAR_FORM = "pagar/list";

	@Autowired
	private PagarService pagarServ;

	@Autowired
	private FornecedorService fornecedores;

	@Autowired
	private PagarParcelaService pagarParcelas;

	@Autowired
	private PagarTipoService pagartipos;

	@Autowired
	private CaixaService caixas;

	@ModelAttribute("filter")
	public PagarParcelaFilter inicializerFilter() {
		return new PagarParcelaFilter();
	}
	
	@GetMapping
	public ModelAndView list(@ModelAttribute("filter") PagarParcelaFilter filter, Pageable pageable, Model model) {
		ModelAndView mv = new ModelAndView(PAGAR_FORM);
		
		Page<PagarParcela> paginas = pagarParcelas.lista(filter, pageable);
		mv.addObject("parcelas", paginas);

		model.addAttribute("qtdpaginas", paginas.getTotalPages());
		model.addAttribute("pagAtual", paginas.getPageable().getPageNumber());
		model.addAttribute("proxPagina", paginas.getPageable().next().getPageNumber());
		model.addAttribute("pagAnterior", paginas.getPageable().previousOrFirst().getPageNumber());
		model.addAttribute("hasNext", paginas.hasNext());
		model.addAttribute("hasPrevious", paginas.hasPrevious());

		return mv;
	}

	@PostMapping
	public @ResponseBody String cadastroDespesa(@RequestParam Map<String, String> request) {
		Long codFornecedor = Long.decode(request.get("fornecedor"));
		Long tipo = Long.decode(request.get("tipo"));
		Double valor = Double.valueOf(request.get("valor"));
		String obs = request.get("obs");

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate vencimento = LocalDate.parse(request.get("vencimento"), format);
		Optional<PagarTipo> pagarTipo = pagartipos.busca(tipo);

		return pagarServ.cadastrar(codFornecedor, valor, obs, vencimento, pagarTipo.orElse(null));
	}

	@PostMapping("/quitar")
	public @ResponseBody String quitar(@RequestParam Map<String, String> request) {
		Long codparcela = Long.decode(request.get("parcela"));
		Long codCaixa = Long.decode(request.get("caixa"));

		String pago = request.get("vlpago").replace(",", ".");
		String desc = request.get("desconto").replace(",", ".");
		String acre = request.get("acrescimo").replace(",", ".");

		Double vlpago = pago.isEmpty() ? 0.0 : Double.parseDouble(pago);
		Double vldesc = desc.isEmpty() ? 0.0 : Double.parseDouble(desc);
		Double vlacre = acre.isEmpty() ? 0.0 : Double.parseDouble(acre);

		return pagarServ.quitar(codparcela, vlpago, vldesc, vlacre, codCaixa);
	}

	@ModelAttribute("fornecedores")
	public List<Fornecedor> fornecedores() {
		return fornecedores.lista();
	}

	@ModelAttribute("pagartipos")
	public List<PagarTipo> pagartipo() {
		return pagartipos.lista();
	}

	@ModelAttribute("caixasabertos")
	public List<Caixa> caixasAbertos() {
		return caixas.caixasAbertos();
	}

}
