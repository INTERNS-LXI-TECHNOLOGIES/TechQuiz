package com.lxisoft.web.rest;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.lxisoft.service.dto.ExamDTO;
import com.lxisoft.service.impl.ExamServiceImpl;

public class FileController 
{
	 @Autowired
	    private ExamServiceImpl examServiceImpl;

	 @GetMapping("/users/export")
	    public void exportToCSV(HttpServletResponse response) throws IOException {
	        response.setContentType("text/csv");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
	        response.setHeader(headerKey, headerValue);
	         
	       /* List<ExamDTO> listUsers = examServiceImpl.findOne(id);
	        		
	 
	        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	        String[] csvHeader = {"ID", "Exam", "No.of Question", "Level"};
	        String[] nameMapping = {"id", "exam", "no.of questions", "level"};
	         
	        csvWriter.writeHeader(csvHeader);
	         
	        for (ExamDTO user : listUsers) {
	            csvWriter.write(user, nameMapping);
	        }
	         
	        csvWriter.close();
	         
	    */}}

	
	
		
