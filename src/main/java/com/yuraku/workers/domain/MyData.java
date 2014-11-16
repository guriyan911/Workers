package com.yuraku.workers.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="mydata")
@Data
public class MyData {

    @Id
    @GeneratedValue
    protected Integer id;
    protected String name;
    protected String mail;
    protected String tel;
    protected Integer age;

}
