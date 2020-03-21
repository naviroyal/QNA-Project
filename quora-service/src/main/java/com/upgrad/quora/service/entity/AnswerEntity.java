package com.upgrad.quora.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "answer" , schema = "public")
@NamedQueries(
        {
                @NamedQuery(name = "answerByUUid", query = "select x from AnswersEntity x where x.uuid = :answersId"),
                @NamedQuery(name = "answerByQuestionId", query = "select x from AnswersEntity x where x.question = :question")
        }
)

public class AnswersEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    private String uuid;

    @Column(name = "ANS")
    @NotNull
    private String ans;

    @Column(name = "DATE")
    @NotNull
    private ZonedDateTime Date;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private UsersEntity users;

    @ManyToOne
    @JoinColumn(name="QUESTION_ID")
    private QuestionsEntity question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUUid() {
        return uuid;
    }

    public void setUUid(String uuid) {
        this.uuid = uuid;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public ZonedDateTime getDate() {
        return Date;
    }

    public void setDate(ZonedDateTime date) {
        Date = date;
    }

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }

    public QuestionsEntity getQuestion() {
        return question;
    }

    public void setQuestions(QuestionsEntity question) {
        this.question = question;
    }
}
