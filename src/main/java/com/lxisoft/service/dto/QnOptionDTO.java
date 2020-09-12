package com.lxisoft.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.lxisoft.domain.QnOption} entity.
 */
public class QnOptionDTO implements Serializable {
    
    private Long id;

    private String option;


    private Long questionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QnOptionDTO)) {
            return false;
        }

        return id != null && id.equals(((QnOptionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QnOptionDTO{" +
            "id=" + getId() +
            ", option='" + getOption() + "'" +
            ", questionId=" + getQuestionId() +
            "}";
    }
}
