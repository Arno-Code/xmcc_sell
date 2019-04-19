package com.xmcc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单分页参数实体类")
public class OrderMasterPageBean implements Serializable {

    /** 买家微信Openid. */
    @NotBlank(message = "微信Openid不能为空")
    @ApiModelProperty(value = "微信Openid",dataType = "String")//swagger 参数的描述信息
    private String openid;

    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码",dataType = "Integer",example = "1")//swagger 参数的描述信息
    private Integer page;

    @NotNull(message = "每页显示条数不能为空")
    @ApiModelProperty(value = "每页显示条数",dataType = "Integer",example = "1")//swagger 参数的描述信息
    private Integer size;
}
