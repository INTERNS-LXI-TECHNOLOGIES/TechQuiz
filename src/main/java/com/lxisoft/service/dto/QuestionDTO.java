package com.lxisoft.service.dto;

import java.io.Serializable;
import com.lxisoft.domain.enumeration.QuestionLevel;

/**
 * A DTO for the {@link com.lxisoft.domain.Question} entity.
 */
public class QuestionDTO implements Serializable {
    
    private Long id;

    private String question;

    private QuestionLevel questionlevel;


    private Long answerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionLevel getQuestionlevel() {
        return questionlevel;
    }

    public void setQuestionlevel(QuestionLevel questionlevel) {
        this.questionlevel = questionlevel;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionDTO)) {
            return false;
        }

        return id != null && id.equals(((QuestionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionDTO{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", questionlevel='" + getQuestionlevel() + "'" +
            ", answerId=" + getAnswerId() +
            "}";
    }
}
