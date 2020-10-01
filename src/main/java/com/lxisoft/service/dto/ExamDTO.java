package com.lxisoft.service.dto;

import java.io.Serializable;
import com.lxisoft.domain.enumeration.ExamLevel;

/**
 * A DTO for the {@link com.lxisoft.domain.Exam} entity.
 */
public class ExamDTO implements Serializable {
    
    private Long id;

    private Integer count;

    private String name;

    private ExamLevel level;


    private Long attendedExamId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExamLevel getLevel() {
        return level;
    }

    public void setLevel(ExamLevel level) {
        this.level = level;
    }

    public Long getAttendedExamId() {
        return attendedExamId;
    }

    public void setAttendedExamId(Long attendedExamId) {
        this.attendedExamId = attendedExamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamDTO)) {
            return false;
        }

        return id != null && id.equals(((ExamDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamDTO{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", name='" + getName() + "'" +
            ", level='" + getLevel() + "'" +
            ", attendedExamId=" + getAttendedExamId() +
            "}";
    }
}
