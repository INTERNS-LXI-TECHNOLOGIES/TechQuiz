package com.lxisoft.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;

import com.lxisoft.domain.enumeration.QuestionLevel;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "questionlevel")
    private QuestionLevel questionlevel;

    @OneToOne
    @JoinColumn(unique = true)
    private Answer answer;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<QnOption> qnOptions = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public Question question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionLevel getQuestionlevel() {
        return questionlevel;
    }

    public Question questionlevel(QuestionLevel questionlevel) {
        this.questionlevel = questionlevel;
        return this;
    }

    public void setQuestionlevel(QuestionLevel questionlevel) {
        this.questionlevel = questionlevel;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Question answer(Answer answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public List<QnOption> getQnOptions() {
        return qnOptions;
    }

    public Question qnOptions(List<QnOption> qnOptions) {
        this.qnOptions = qnOptions;
        return this;
    }

    public Question addQnOption(QnOption qnOption) {
        this.qnOptions.add(qnOption);
        qnOption.setQuestion(this);
        return this;
    }

    public Question removeQnOption(QnOption qnOption) {
        this.qnOptions.remove(qnOption);
        qnOption.setQuestion(null);
        return this;
    }

    public void setQnOptions(List<QnOption> qnOptions) {
        this.qnOptions = qnOptions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", questionlevel='" + getQuestionlevel() + "'" +
            "}";
    }
}
