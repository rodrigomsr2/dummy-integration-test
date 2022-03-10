package com.integration.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.integration.api.dto.input.CustomerDTO;
import com.integration.domain.model.Customer;
import com.integration.domain.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping
	public List<Customer> searchAll() {
		return this.customerService.findAll();
	}
	
	@GetMapping("/{id}")
	public Customer search(@PathVariable("id") Long id) {
		return this.customerService.findByIdOrFail(id);
	}
	
	@GetMapping("/cnpj")
	public Customer search(@RequestParam(value = "cnpj", required = true) String cnpj) {
		return this.customerService.findByCnpjOrFail(cnpj);
	}
	
	@PostMapping()
	@ResponseStatus(value = HttpStatus.CREATED)
	public Customer add(@RequestBody CustomerDTO customerDTO) {
		return this.customerService.save(customerDTO.toEntity());
	}
	
	@PostMapping("/batch")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Object add(@RequestParam(name = "file", required = true) MultipartFile file,
					@RequestHeader(name = "active", required = true) Boolean active) throws IOException {

		InputStream arquivo = file.getInputStream();
		
		HSSFWorkbook workbook = new HSSFWorkbook(arquivo);

        HSSFSheet sheetAlunos = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheetAlunos.iterator();        
        
        while (rowIterator.hasNext()) {
        	Row row = rowIterator.next();
        	
        	Iterator<Cell> cellIterator = row.cellIterator();
        	
        	while(cellIterator.hasNext()) {
        		Cell cell = cellIterator.next();
        		
        		switch (cell.getColumnIndex()) {
                case 0:
                	System.out.println(cell.getStringCellValue());
            		break;
                case 1:
                	System.out.println(cell.getNumericCellValue());
            		break;
                case 2:
            		System.out.println(cell.getDateCellValue());
            		break;
    		  }	
        	}
        }
        
        workbook.close();
        
        return null;
	}
	
	
	
}
