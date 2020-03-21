package com.upgrad.quora.service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question" , schema = "public")
@NamedQueries(
        {
                @NamedQuery(name = "allQuestions" , query = "select q from QuestionsEntity q"),
                @NamedQuery(name = "questionByUUid", query = "select q from QuestionsEntity q where q.uuid =:questionId"),
                @NamedQuery(name = "questionsByUser", query = "select q from QuestionsEntity q where q.users =:users")
        }
)

public class QuestionsEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @NotNull
    private String uuid;

    @Column(name = "CONTENT")
    @NotNull
    private String content;

    @Column(name = "DATE")
    @NotNull
    private ZonedDateTime Date;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private UsersEntity users;

    @OneToMany(mappedBy = "question")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<AnswersEntity> answer;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getDate() {
        return Date;
    }

    public void setDate(ZonedDateTime date) {
        Date = date;
    }

    public List<AnswersEntity> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswersEntity> answer) {
        this.answer = answer;
    }

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }
}
