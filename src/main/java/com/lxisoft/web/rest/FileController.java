package com.lxisoft.web.rest;
import java.io.IOException;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.prefs.CsvPreference;*/
import com.lxisoft.domain.Exam;
import com.lxisoft.service.dto.ExamDTO;
import com.lxisoft.service.impl.ExamServiceImpl;


public class FileController 
{
	 @Autowired
	 private ExamServiceImpl examServiceImpl;
	/* static void writeCSVFile(String csvFileName, List<Exam> exams) {
			ICsvBeanWriter beanWriter = null;
			CellProcessor[] processors = new CellProcessor[] { 
					new NotNull(), // Id
					new NotNull(), // name
					new NotNull(), // no of questions
					new NotNull(), // level
						};

			try {
				beanWriter = new CsvBeanWriter(new FileWriter(csvFileName),
						CsvPreference.STANDARD_PREFERENCE);
				String[] header =  {"ID", "Exam", "No.of Question", "Level"};
		        
				beanWriter.writeHeader(header);
				
				for (Exam examDto : exams) {
					beanWriter.write(examDto, header, processors);
				}
				
			} catch (IOException ex) {
				System.err.println("Error writing the CSV file: " + ex);
			} finally {
				if (beanWriter != null) {
					try {
						beanWriter.close();
					} catch (IOException ex) {
						System.err.println("Error closing the writer: " + ex);
					}
				}
			}
		}

		public static void main(String[] args) throws ParseException {
			// creates some dummy data
			//DateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");
			
			Exam exam1 = new Exam("1234",12, "Effective Java",5,"Easy");
			Exam exam2 = new Exam(02, "Head First Java",5, "Medium");
			
			List<Exam> exams = Arrays.asList(exam1,exam2);
			
			
			String csvFileName = "Exams.csv";
			writeCSVFile(csvFileName, exams);
		}

	/* @GetMapping("/users/export")
	    public void exportToCSV(HttpServletResponse response) throws IOException {
	        response.setContentType("text/csv");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
	        response.setHeader(headerKey, headerValue);
	         
	        List<ExamDTO> listUsers = examServiceImpl.findOne(id);
	        		
	 
	        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	        String[] csvHeader = {"ID", "Exam", "No.of Question", "Level"};
	        String[] nameMapping = {"id", "exam", "no.of questions", "level"};
	         
	        csvWriter.writeHeader(csvHeader);
	         
	        for (ExamDTO user : listUsers) {
	            csvWriter.write(user, nameMapping);
	        }
	         
	        csvWriter.close();
	         
	    // calling in html	<a th:href="/@{/exam/export}">Export to File</a>
		
	    }
*/
	
}	
		
