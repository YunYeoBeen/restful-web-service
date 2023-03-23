package com.example.restfulwebservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
//@JsonIgnoreProperties(value={"password","ssn"})
//@JsonFilter("UserInfo")
public class User {
    private Integer id;

    @Size(min = 2, message="Name은 2글자 이상 작성해주세요")
//    @ApiModelProperty(notes= "사용자 이름을 입력해주세요")
    private String name;
    @Past
//    @ApiModelProperty(notes= "등록일을 입력해주세요")
    private Date joinDate;

//    @ApiModelProperty(notes= "패스워드를 입력해주세요")
    private String password;
//    @ApiModelProperty(notes= "주민번호를 입력해주세요")
    private String ssn;
}
