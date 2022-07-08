package com.aaronrenner.spring.controllers;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.aaronrenner.spring.models.*;
import com.aaronrenner.spring.services.SalesService;

@RestController
public class SalesController {
	
	@Autowired
	SalesService saleService;

	// Endpoints
	private final String CONTENT_TYPE = "application/json";
	private final String SALE_PATH = "/sales";
	private final String WITH_SALE_ID = "/{salesId}";

	@RequestMapping(
			method = RequestMethod.POST,
			value  = SALE_PATH,
			consumes = CONTENT_TYPE,
			produces = CONTENT_TYPE)
	public ResponseEntity<Sales> createSales(HttpServletRequest request, @RequestBody Sales sale) throws Exception {
		return saleService.createSales(request, sale);
	}
	
	@GetMapping(path = SALE_PATH, produces = CONTENT_TYPE)
	public ResponseEntity<?> readAllSales(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit, @RequestParam Optional<Boolean> showAll) {
		int getPage = (page.isPresent()) ? page.get() : 0;
		int withCount = (limit.isPresent()) ? limit.get() : 100;
		Boolean viewAll = (showAll.isPresent()) ? showAll.get() : false;
		return saleService.readAllSales(getPage, withCount, viewAll);
	}
	
	@GetMapping(path = SALE_PATH+WITH_SALE_ID, produces = CONTENT_TYPE)
	public  ResponseEntity<Sales> readSales(@PathVariable long salesId) {
		return saleService.readSales(salesId);
	}
	
	@PatchMapping(path = SALE_PATH+WITH_SALE_ID)
	public ResponseEntity<String> updateSales(@PathVariable long salesId, @RequestBody Sales sale) {
		sale.setId(salesId);
		return saleService.updateSales(salesId, sale);
	}
	
	@DeleteMapping(path = SALE_PATH+WITH_SALE_ID)
	public ResponseEntity<String>  deleteSales(@PathVariable long salesId) {
		return saleService.deleteSales(salesId);
	}
}
