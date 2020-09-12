package com.lxisoft.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.lxisoft.domain.AttendedExam} entity.
 */
public class AttendedExamDTO implements Serializable {
    
    private Long id;

    private Integer total;

    private Boolean result;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean isResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendedExamDTO)) {
            return false;
        }

        return id != null && id.equals(((AttendedExamDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendedExamDTO{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", result='" + isResult() + "'" +
            "}";
    }
}
