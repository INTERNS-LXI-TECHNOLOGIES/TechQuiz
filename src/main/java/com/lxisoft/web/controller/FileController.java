package com.lxisoft.web.controller;
import java.io.IOException;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.lxisoft.domain.Exam;
import com.lxisoft.domain.enumeration.ExamLevel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
//import org.supercsv.io.CsvBeanReader;
//import org.supercsv.io.ICsvBeanReader;
//import org.supercsv.prefs.CsvPreference;
//import org.supercsv.cellprocessor.ift.CellProcessor;



@Controller
public class FileController
{
	public File file = new File("/home/user/exam.csv");
	
	public boolean fileExist(File file)
	{
		boolean isCheck=file.exists();
		return isCheck;
	}
	public void writeToDatabase(List<Exam> exams)
	{
		try
		{
			FileWriter fw = new FileWriter(file,false);
			BufferedWriter bw = new BufferedWriter(fw);
			//for (Exam exam: exams) {
			//	bw.write(exams.get());
			//}
			for(int i=0; i<exams.size(); i++)
			{
				bw.write(exams.get(i).getId()+","+exams.get(i).getCount()+","+exams.get(i).getName()+","+exams.get(i).getLevel());  
				bw.newLine();		
			}
			bw.flush();
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	
	
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

				for (Exam exam: exams) {
					beanWriter.write(exam, header, processors);
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
			Exam exam3 = new Exam();
			Exam exam1 = new Exam(12, "Effective Java",5,"Easy");
			Exam exam2 = new Exam(11, "Head First Java",5, "Medium");

			List<Exam> exams = Arrays.asList(exam1,exam2,exam3);


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

