package com.xmcc.controller;

import com.xmcc.commons.ResultResponse;
import com.xmcc.dto.OrderMasterDto;
import com.xmcc.dto.OrderMasterPageBean;
import com.xmcc.service.OrderMasterService;
import com.xmcc.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("buyer/order")
@Api(value = "订单相关接口",description = "完成订单的增删改查")
public class OrderController {

    @Autowired
    private OrderMasterService masterService;
    //@Valid配合刚才在DTO上的JSR303注解完成校验
    //注意：JSR303的注解默认是在Contorller层进行校验
    //如果想在service层进行校验 需要使用javax.validation.Validator  也就是上个项目用到的工具

    /**创建订单*/
    @PostMapping("/create")
    @ApiOperation(value = "创建订单接口", httpMethod = "POST", response =ResultResponse.class)
    public ResultResponse create(
            @Valid @ApiParam(name="订单对象",value = "传入json格式",required = true)
                    OrderMasterDto orderMasterDto, BindingResult bindingResult){
        //判断是否有参数校验问题
        Map<String, String> map = new HashMap<>();
        if (bindingResult.hasErrors()){
            List<String> errList = bindingResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
            map.put("参数校验错误", JsonUtil.object2string(errList));
            //将参数校验的错误信息返回给前台
            return  ResultResponse.fail(map);
        }

        return masterService.insertOrder(orderMasterDto);
    }

    /**订单列表*/
    @GetMapping("/list")
    @ApiOperation(value = "订单列表", httpMethod = "GET", response =ResultResponse.class)
    public ResultResponse list(
            @Valid @ApiParam(name="订单分页对象",value = "传入对象格式",required = true)
            OrderMasterPageBean orderMasterPageBean,BindingResult bindingResult){
        //判断是否有参数校验问题
        Map<String, String> map = new HashMap<>();
        if (bindingResult.hasErrors()){
            List<String> errList = bindingResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
            map.put("参数校验错误", JsonUtil.object2string(errList));
            //将参数校验的错误信息返回给前台
            return  ResultResponse.fail(map);
        }

        return masterService.findByOrderMasterPageBean(orderMasterPageBean);
    }
    /**查询订单详情*/
    @GetMapping("/detail")
    @ApiOperation(value = "查询订单详情", httpMethod = "GET", response =ResultResponse.class)
    public ResultResponse detail(String openId,String orderId){
        //判断是否有参数校验问题
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(openId) || StringUtils.isBlank(orderId)){
            //将参数校验的错误信息返回给前台
            return  ResultResponse.fail("参数不能为空");
        }
        return masterService.findOrderMasterDetail(openId,orderId);
    }

    /**取消订单*/
    @PostMapping("/cancel")
    @ApiOperation(value = "取消订单", httpMethod = "POST", response =ResultResponse.class)
    public ResultResponse cancel(String openId,String orderId){
        //判断是否有参数校验问题
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(openId) || StringUtils.isBlank(orderId)){
            //将参数校验的错误信息返回给前台
            return  ResultResponse.fail("参数不能为空");
        }
        return masterService.cancelOrderMaster(openId,orderId);
    }
}
