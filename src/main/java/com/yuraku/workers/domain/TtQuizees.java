package com.yuraku.workers.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class TtQuizees {

	// もしM_T_NAMEのような列名だと@Column(name="m_t_name")が必要。残念。
    @Id
    @GeneratedValue
    protected Integer id;
    @NotNull
    @Size(min=2, max=30)
    protected String question;
    @NotNull
    @Size(min=1, max=10)
    protected String answer;
    protected String error1;
    protected String error2;
    protected String error3;

}
