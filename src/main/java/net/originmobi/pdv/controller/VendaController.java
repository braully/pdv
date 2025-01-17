package net.originmobi.pdv.controller;

import net.originmobi.pdv.enumerado.VendaSituacao;
import net.originmobi.pdv.filter.VendaFilter;
import net.originmobi.pdv.model.*;
import net.originmobi.pdv.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/venda")
public class VendaController {

	private static final String VENDA_LIST = "venda/list";

	private static final String VENDA_FORM = "venda/form";

	@Autowired
	private VendaService vendas;

	@Autowired
	private PessoaService pessoas;

	@Autowired
	private ProdutoService produtos;

	@Autowired
	private VendaProdutoService vendaProdutos;

	@Autowired
	private PagamentoTipoService pagamentoTipos;

	@Autowired
	private TituloService tituloService;

	@GetMapping("/form")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView(VENDA_FORM);
		mv.addObject(new Venda());
		return mv;
	}

	@GetMapping("/status/{status}")
	public ModelAndView listaPedidos(@ModelAttribute("filter") VendaFilter filter,
			@PathVariable("status") String status, Pageable pageable, Model model) {
		ModelAndView mv = new ModelAndView(VENDA_LIST);
		Page<Venda> vendasPaginadas = vendas.busca(filter, status, pageable);
		mv.addObject("vendas", vendasPaginadas);

		model.addAttribute("qtdpaginas", vendasPaginadas.getTotalPages());
		model.addAttribute("pagAtual", vendasPaginadas.getPageable().getPageNumber());
		model.addAttribute("proxPagina", vendasPaginadas.getPageable().next().getPageNumber());
		model.addAttribute("pagAnterior", vendasPaginadas.getPageable().previousOrFirst().getPageNumber());
		model.addAttribute("hasNext", vendasPaginadas.hasNext());
		model.addAttribute("hasPrevious", vendasPaginadas.hasPrevious());
		
		if (!vendasPaginadas.getContent().isEmpty())
			model.addAttribute("statuVenda", vendasPaginadas.getContent().get(0).getSituacao());

		return mv;
	}

	@PostMapping
	public String abrirVenda(@Validated Venda venda, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors())
			return VENDA_FORM;

		Long codigo = null;

		try {
			codigo = vendas.abreVenda(venda);
			attributes.addFlashAttribute("mensagem", "Pedido Salvo");
		} catch (Exception e) {
			e.getStackTrace();
		}

		return "redirect:/venda/" + (codigo != null ? codigo.toString() : "");

	}

	@GetMapping("{codigo}")
	public ModelAndView buscaVenda(@PathVariable("codigo") Venda venda) {
		ModelAndView mv = new ModelAndView(VENDA_FORM);
		mv.addObject("venda", venda);
		mv.addObject("produtosVenda", vendaProdutos.listaProdutosVenda(venda));
		return mv;
	}

	@PostMapping("/addproduto")
	public @ResponseBody String addProdutoVenda(@RequestParam Map<String, String> request) {
		Long codVen = Long.decode(request.get("codigoVen"));
		Long codPro = Long.decode(request.get("codigoPro"));
		Double vlBalanca = Double.valueOf(request.get("valorBalanca"));

		String mensagem = "";

		try {
			mensagem = vendas.addProduto(codVen, codPro, vlBalanca);
		} catch (Exception e) {
			e.getStackTrace();
		}

		return mensagem;
	}

	@PostMapping("/removeproduto")
	public @ResponseBody String removeProdutoVenda(@RequestParam Map<String, String> request) {
		Long posicaoProd = Long.decode(request.get("posicaoPro"));
		Long venda = Long.decode(request.get("codigoVen"));

		String mensagem = "";
		try {
			mensagem = vendas.removeProduto(posicaoProd, venda);
		} catch (Exception e) {
			e.getStackTrace();
		}

		return mensagem;
	}

	@PostMapping("/fechar")
	public @ResponseBody String fechar(@RequestParam Map<String, String> request) {
		Long venda = Long.decode(request.get("venda"));
		Long pagamentotipo = Long.decode(request.get("pagamentotipo"));
		String valorProdutos = request.get("valorProdutos");
		String valorDesconto = request.get("valorDesconto");
		String valorAcrescimo = request.get("valorAcrescimo");

		String[] vlParcelas = request.get("valores").split(",");
		String[] titulos = request.get("titulos").split(",");

		Double vlprodutos = valorProdutos.isEmpty()
				? 0.0
				: Double.parseDouble(valorProdutos.replace(",", "."));
		Double vldesconto = valorDesconto.isEmpty()
				? 0.0
				: Double.parseDouble(valorDesconto.replace(",", "."));
		Double vlacrescimo = valorAcrescimo.isEmpty()
				? 0.0
				: Double.parseDouble(valorAcrescimo.replace(",", "."));

		return vendas.fechaVenda(venda, pagamentotipo, vlprodutos, vldesconto, vlacrescimo, vlParcelas, titulos);
	}

	@GetMapping(value = "/titulos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Titulo> titulos() {
		return tituloService.lista();
	}

	@ModelAttribute("clientes")
	public List<Pessoa> clientes() {
		return pessoas.lista();
	}

	@ModelAttribute("situacoes")
	public List<VendaSituacao> vendaSituacao() {
		return Arrays.asList(VendaSituacao.values());
	}

	@ModelAttribute("produtos")
	public List<Produto> produtos() {
		return produtos.listar();
	}
	
	@ModelAttribute("produtosVendaveis")
	public List<Produto> produtosVendaveis() {
		return produtos.listaProdutosVendaveis();
	}

	@ModelAttribute("formaPagamento")
	public List<PagamentoTipo> pagamentoTipo() {
		return pagamentoTipos.listar();
	}
}
