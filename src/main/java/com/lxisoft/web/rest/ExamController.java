package com.lxisoft.web.rest;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

import com.lxisoft.domain.Exam;

import java.io.*;

public class ExamController 
{
	public File file = new File("D:\\exam.csv");
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
			for(int i=0; i<exams.size(); i++)
			{
				bw.write(exams.get(i).getId()+","+exams.get(i).getName());  
				bw.newLine();		
			}
			bw.flush();
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e+"Error eeeeeee");
			e.printStackTrace();
		}
	}
	public void appendFile(List<Exam> exams)
	{
		try
		{
			FileWriter fw = new FileWriter(file,true);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0; i<exams.size(); i++)
			{
				bw.write(exams.get(i).getId()+","+exams.get(i).getName());  
				bw.newLine();		
			}
			bw.flush();
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e+"Error eeeeeee");
			e.printStackTrace();
		}
	}
		
	public List<Exam> readFromDatabase(List<Exam> exams) throws Exception , IOException
	{
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String str;
		while((str=br.readLine())!=null)
		{	
			String[] s = str.split(",",3);	
			exams.add(new Exam());
			for(int i=0;i<exams.size();i++)
			{
				if(exams.get(i).getName()==null)
				{
					exams.get(i).setId(Long.parseLong(s[0]));
					exams.get(i).setName(s[1]);
				
				}
			}	
		}
		return exams;
	}	
}

	
	
		
