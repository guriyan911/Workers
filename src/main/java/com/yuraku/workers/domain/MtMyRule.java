package com.yuraku.workers.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class MtMyRule {

    @Id
    @GeneratedValue
    protected Integer id;
    @NotNull
    @Size(min=1, max=5)
    protected String ruleId;
    protected String ruleName;

}
