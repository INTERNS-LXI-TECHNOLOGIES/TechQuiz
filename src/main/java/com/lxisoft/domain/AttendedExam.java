package com.lxisoft.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AttendedExam.
 */
@Entity
@Table(name = "attended_exam")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttendedExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Integer total;

    @Column(name = "result")
    private Boolean result;

    @OneToMany(mappedBy = "attendedExam")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Exam> exams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public AttendedExam total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean isResult() {
        return result;
    }

    public AttendedExam result(Boolean result) {
        this.result = result;
        return this;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public AttendedExam exams(Set<Exam> exams) {
        this.exams = exams;
        return this;
    }

    public AttendedExam addExam(Exam exam) {
        this.exams.add(exam);
        exam.setAttendedExam(this);
        return this;
    }

    public AttendedExam removeExam(Exam exam) {
        this.exams.remove(exam);
        exam.setAttendedExam(null);
        return this;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendedExam)) {
            return false;
        }
        return id != null && id.equals(((AttendedExam) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendedExam{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", result='" + isResult() + "'" +
            "}";
    }
}
