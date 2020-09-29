package com.lxisoft.service.impl;

import com.lxisoft.service.ExamService;



import com.lxisoft.domain.*;
import com.lxisoft.repository.ExamRepository;
import com.lxisoft.repository.QuestionRepository;
import com.lxisoft.service.dto.ExamDTO;
import com.lxisoft.service.dto.QuestionDTO;
import com.lxisoft.service.mapper.ExamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Exam}.
 */
@Service
@Transactional
public class ExamServiceImpl implements ExamService {
	
	@Autowired
	QuestionRepository questionRepo;
	@Autowired
	QuestionServiceImpl questionServiceImpl;
	 @Autowired
	  private ExamRepository examRepo;
	 File file=new File("/home/user/git jhipster/TechQuiz/src/main/java/com/lxisoft/repository/Text.csv");

	 //File file=new File("D:\\TechQuizRepository\\TechQuiz\\src\\main\\java\\com\\lxisoft\\repository\\Text.csv");
//		FileWriter fw=null;
//		FileWriter f=null;
//		BufferedWriter bw=null;
		static  int id=0;
		List<Exam>examList=new ArrayList<>();
	 

    private final Logger log = LoggerFactory.getLogger(ExamServiceImpl.class);

    private final ExamRepository examRepository;

    private final ExamMapper examMapper;

    public ExamServiceImpl(ExamRepository examRepository, ExamMapper examMapper) {
        this.examRepository = examRepository;
        this.examMapper = examMapper;
    }

    @Override
    public ExamDTO save(ExamDTO examDTO) {
    	
//    	Set<QuestionDTO> questionList = new HashSet<>(); 
//		List<QuestionDTO> questions=questionServiceImpl.findAll();
//		
//		int count=examDTO.getCount();
//		int c=0;
//		int qc=0;
//		ExamLevel level=examDTO.getLevel();
//		for(QuestionDTO qn:questions)
//		{
//			if(qn.getQuestionlevel().equals(level))
//				c++;
//		}
//		if(c>=count)
//		{
//			for(QuestionDTO qn:questions)
//			{
//				if(qn.getQuestionlevel().equals(level) && (qc<count))
//				{
//					qc++;
//					questionList.add(qn);
//				}
//			}
//
//		}
		
        log.debug("Request to save Exam : {}", examDTO);
        Exam exam = examMapper.toEntity(examDTO);
        exam = examRepository.save(exam);
        return examMapper.toDto(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> findAll() {
        log.debug("Request to get all Exams");
        return examRepository.findAllWithEagerRelationships().stream()
            .map(examMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    public Page<ExamDTO> findAllWithEagerRelationships(Pageable pageable) {
        return examRepository.findAllWithEagerRelationships(pageable).map(examMapper::toDto);
    }


    

    @Transactional(readOnly = true)
    public Exam getOne(long id)
    {
        return examRepository.getOne(id);
    }
    
    
    public void saveExam(Exam exam)
    {
    	examRepository.save(exam);
    }
    

    @Override
    @Transactional(readOnly = true)
    public Optional<ExamDTO> findOne(Long id) {
        log.debug("Request to get Exam : {}", id);
        return examRepository.findOneWithEagerRelationships(id)
            .map(examMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exam : {}", id);
        examRepository.deleteById(id);
    }
    public List<Exam> listAll() {
        return examRepo.findAll();
    }

    
 /*   public ExamDTO saveFile(ExamDTO examDTO) {
		
        log.debug("Request to save Exam : {}", examDTO);
        Exam exam = examMapper.toEntity(examDTO);
        exam = examRepository.save(exam);
        return examMapper.toDto(exam);
    }
*/

    
    public Exam createOrUpdateExam(Exam exam) 
    {
        if(exam.getId()  == null) 
        {
        	exam = examRepo.save(exam);
             
            return exam;
        } 
        else
        {
            Optional<Exam> exams = examRepo.findById(exam.getId());
             
            if(exams.isPresent()) 
            {
            	Exam newEntity = exams.get();
                newEntity.setName(exam.getName());
                newEntity.setCount(exam.getCount());
                newEntity.setLevel(exam.getLevel());
 
                newEntity = examRepo.save(newEntity);
                 
                return newEntity;
            } else {
                exam = examRepo.save(exam);
                 
                return exam;
            }
        }
    } 


//    public ExamDTO saveFile(ExamDTO examDTO) {
//		
//        log.debug("Request to save Exam : {}", examDTO);
//        Exam exam = examMapper.toEntity(examDTO);
//        exam = examRepository.save(exam);
//        return examMapper.toDto(exam);
//    }

   
   
    	
//    public File createFile()
//	{
//		File file = new File("/home/user/exam.csv");
//		return file;
//	
//	}
//	
//		
//	public boolean fileExist(File file)
//	{
//		boolean isCheck=file.exists();
//		return isCheck;
//	}
//		 
//   
//    	public void writeToFile(List<ExamDTO> examDto,File file)
//    	{
//    		//File file = new File("/home/user/exam.csv");
//    		try
//    		{
//    			FileWriter fw = new FileWriter(file,false);
//    			BufferedWriter bw = new BufferedWriter(fw);
//    		
//    			for(int i=0; i<examDto.size(); i++)
//    			{
//    				bw.write(examDto.get(i).getId()+","+examDto.get(i).getCount()+","+examDto.get(i).getName()+","+examDto.get(i).getLevel());  
//    				bw.newLine();		
//    			}
//    			bw.flush();
//    			bw.close();
//    		}
//    		catch(Exception e)
//    		{
//    			System.out.println(e);
//    			e.printStackTrace();
//    		}
//    	}
//    	public List<Exam> listAllFile() {
//            return examRepo.findAll();
//        }
    	
    	public List<Exam> create(Exam e)
    	{
    		e=new Exam();
    		try
    		{
    			FileWriter fw = new FileWriter(file);
    			BufferedWriter bw = new BufferedWriter(fw);
//    			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
//    			fw=new FileWriter(file,true);

    			bw.write(id+","+e.getName()+","+e.getCount()+","+e.getLevel()+"\n");
    			bw.flush();
    		}
    		catch(IOException i)
    		{
    			System.out.println("an error occured");
    		}
    		return examList;	
    	}
   
    		@Override
		public ExamDTO saveFile(ExamDTO examDTO) {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }
    
