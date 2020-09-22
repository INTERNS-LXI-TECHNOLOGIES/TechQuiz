package com.lxisoft.web.rest;
 
import java.io.BufferedReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lxisoft.domain.Exam;

import liquibase.util.csv.opencsv.bean.CsvToBean;


public class ExamController {
	 @GetMapping("/")
	    public String index() {
	        return "index";
	    }

	    @PostMapping("/upload-csv-file")
	    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

	        // validate file
	        if (file.isEmpty()) {
	            model.addAttribute("message", "Please select a CSV file to upload.");
	            model.addAttribute("status", false);
	        } else {

	            // parse CSV file to create a list of `User` objects
	            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

	                // create csv bean reader
	                CsvToBean<Exam> csvToBean = new CsvToBeanBuilder(reader)
	                        .withType(Exam.class)
	                        .withIgnoreLeadingWhiteSpace(true)
	                        .build();

	                // convert `CsvToBean` object to list of users
	                List<Exam> users = csvToBean.parse();

	                // TODO: save users in DB?

	                // save users list on model
	                model.addAttribute("users", users);
	                model.addAttribute("status", true);

	            } catch (Exception ex) {
	                model.addAttribute("message", "An error occurred while processing the CSV file.");
	                model.addAttribute("status", false);
	            }
	        }

	        return "file-upload-status";
	    }
	}