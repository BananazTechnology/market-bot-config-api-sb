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
	public ResponseEntity<?> createSales(HttpServletRequest request, @RequestBody Sales sale) throws Exception {
		// Process response
		return ResponseEntity
					.created(saleService.createSales(request, sale))
					.build();
	}
	
	@GetMapping
	public ResponseEntity<?> readAllSales(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit, @RequestParam Optional<Boolean> showAll) {
		// Set defaults
		int getPage = (page.isPresent()) ? page.get() : 0;
		int withCount = (limit.isPresent()) ? limit.get() : 100;
		Boolean viewAll = (showAll.isPresent()) ? showAll.get() : false;
		// Process response
		return ResponseEntity.ok(saleService.readAllSales(getPage, withCount, viewAll));
	}
	
	@GetMapping(WITH_SALE_ID)
	public  ResponseEntity<Sales> readSales(@PathVariable long salesId) {
		// Process response
		return ResponseEntity.ok(saleService.readSales(salesId));
	}
	
	@PatchMapping(WITH_SALE_ID)
	public ResponseEntity<?> updateSales(@PathVariable long salesId, @RequestBody Sales sale) {
		// Assign the ID into the body
		sale.setId(salesId);
		// Update function
		saleService.updateSales(salesId, sale);
		// Process response
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(WITH_SALE_ID)
	public ResponseEntity<String>  deleteSales(@PathVariable long salesId) {
		// Process function
		saleService.deleteSales(salesId);
		// Process response
		return ResponseEntity.noContent().build();
	}
}
