package com.yuraku.workers.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="m_emp")
@Data
public class MEmp {

	// もしM_T_NAMEのような列名だと@Column(name="m_t_name")が必要。残念。
    @Id
    @GeneratedValue
    protected Integer id;
    protected String name;
    protected String mail;
    protected String tel;
    protected Integer age;

}
