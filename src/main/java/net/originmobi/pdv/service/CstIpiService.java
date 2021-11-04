package net.originmobi.pdv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.originmobi.pdv.model.CstIPI;
import net.originmobi.pdv.repository.CstIpiRepository;

@Service
public class CstIpiService {

	@Autowired
	private CstIpiRepository repository;

	public List<CstIPI> lista() {
		return repository.findAll();
	}

}
