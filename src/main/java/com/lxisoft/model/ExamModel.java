package com.lxisoft.model;

import com.lxisoft.domain.Answer;
import com.lxisoft.domain.Question;

public class ExamModel {
		private long id;
	    private Question question;
	    private String option1;
	    private String option2;
	    private String option3;
	    private String option4;
	    private Answer answer;
	    private String selectedOption;


	    public void setId(long id) {
	        this.id = id;
	    }

	    public Long getId() {
	        return id;
	    }

	    public Question getQuestion() {
	        return question;
	    }

	    public void setQuestion(Question question) {
	        this.question = question;
	    }

	    public String getOption1() {
	        return option1;
	    }

	    public String setOption1(String option1) {
	        this.option1 = option1;
	        return option1;
	    }

	    public String getOption2() {
	        return option2;
	    }

	    public void setOption2(String option2) {
	        this.option2 = option2;
	    }

	    public String getOption3() {
	        return option3;
	    }

	    public void setOption3(String option3) {
	        this.option3 = option3;
	    }

	    public String getOption4() {
	        return option4;
	    }

	    public void setOption4(String option4) {
	        this.option4 = option4;
	    }

	    public Answer getAnswer() {
	        return answer;
	    }

	    public void setAnswer(Answer answer) {
	        this.answer = answer;
	    }

	    public String getSelectedOption() {
			return selectedOption;
		}
		public void setSelectedOption(String selectedOption) {
			this.selectedOption = selectedOption;
		}

}
