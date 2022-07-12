package tech.bananaz.spring.controllers;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.bananaz.spring.models.*;
import tech.bananaz.spring.services.SalesService;

@RestController
@RequestMapping(value = "/sales", produces = "application/json")
public class SalesController {
	
	@Autowired
	SalesService saleService;

	private final String WITH_SALE_ID = "/{salesId}";

	@PostMapping
	public ResponseEntity<Sales> createSales(HttpServletRequest request, @RequestBody Sales sale) throws Exception {
		return saleService.createSales(request, sale);
	}
	
	@GetMapping
	public ResponseEntity<?> readAllSales(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit, @RequestParam Optional<Boolean> showAll) {
		int getPage = (page.isPresent()) ? page.get() : 0;
		int withCount = (limit.isPresent()) ? limit.get() : 100;
		Boolean viewAll = (showAll.isPresent()) ? showAll.get() : false;
		return saleService.readAllSales(getPage, withCount, viewAll);
	}
	
	@GetMapping(WITH_SALE_ID)
	public  ResponseEntity<Sales> readSales(@PathVariable long salesId) {
		return saleService.readSales(salesId);
	}
	
	@PatchMapping(WITH_SALE_ID)
	public ResponseEntity<String> updateSales(@PathVariable long salesId, @RequestBody Sales sale) {
		sale.setId(salesId);
		return saleService.updateSales(salesId, sale);
	}
	
	@DeleteMapping(WITH_SALE_ID)
	public ResponseEntity<String>  deleteSales(@PathVariable long salesId) {
		return saleService.deleteSales(salesId);
	}
}
