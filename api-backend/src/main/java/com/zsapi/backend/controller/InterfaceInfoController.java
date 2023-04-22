package com.zsapi.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.zsapi.backend.annotation.AuthCheck;
import com.zsapi.backend.common.*;
import com.zsapi.backend.constant.CommonConstant;
import com.zsapi.backend.exception.BusinessException;
import com.zsapi.backend.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.zsapi.backend.model.dto.interfaceInfo.InterfaceInfoInvokeRequest;
import com.zsapi.backend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.zsapi.backend.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.zsapi.backend.model.enums.InterfaceInfoStatusEnum;
import com.zsapi.backend.model.vo.InterfaceInfoUserVO;
import com.zsapi.client.client.ZsApiClient;
import com.zsapi.common.model.entity.InterfaceInfo;
import com.zsapi.common.model.entity.User;
import com.zsapi.common.service.InterfaceInfoService;
import com.zsapi.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 帖子接口
 *
 * @author yupi
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private UserService userService;
    @Resource

    private InterfaceInfoService interfaceInfoService;
    @Resource
    private ZsApiClient zsApiClient;


    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newPostId = interfaceInfo.getId();
        return ResultUtils.success(newPostId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!interfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                                     HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo infoServiceById = interfaceInfoService.getById(id);
        if (infoServiceById == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        log.info("true?" + infoServiceById.getUserId().equals(user.getId()));
        // 仅本人或管理员可修改
        if (!infoServiceById.getUserId().equals(user.getId()) || !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfoUserVO> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        InterfaceInfoUserVO interfaceInfoUserVO = new InterfaceInfoUserVO();
        BeanUtils.copyProperties(interfaceInfo, interfaceInfoUserVO);
        return ResultUtils.success(interfaceInfoUserVO);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfo);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfo);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfo);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfo);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    /**
     * @description: 上线接口
     * @author: zzs
     * @date: 2023/3/19 19:46
     * @param: interfaceInfoUpdateRequest
     * @param: request
     * @return: com.InterfaceInfo.common.BaseResponse<java.lang.Boolean>
     **/
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断接口是否存在
        Long id = idRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 判断接口是否可以调用：通过创建的第三方client发起调用
        // todo 后期需要修改
        String userName = zsApiClient.getUserNameByPost(new com.zsapi.client.modal.entity.User("zzs"));
        if (StringUtils.isBlank(userName)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口验证失败");
        }
        // 修改接口状态
        InterfaceInfo interfaceInfo1 = new InterfaceInfo();
        interfaceInfo1.setId(id);
        interfaceInfo1.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo1);
        return ResultUtils.success(result);
    }

    /**
     * @description: 下线接口
     * @author: zzs
     * @date: 2023/3/19 19:46
     * @param: interfaceInfoUpdateRequest
     * @param: request
     * @return: com.InterfaceInfo.common.BaseResponse<java.lang.Boolean>
     **/
    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                      HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断接口是否存在
        Long id = idRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 修改接口状态
        InterfaceInfo interfaceInfo1 = new InterfaceInfo();
        interfaceInfo1.setId(id);
        interfaceInfo1.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo1);
        return ResultUtils.success(result);
    }

    /**
     * @description: 调用接口
     * @author: zzs
     * @date: 2023/3/20 20:53
     * @param: interfaceInfoInvokeRequest
     * @param: request
     * @return: com.zsapi.backend.common.BaseResponse<java.lang.Object>
     **/
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                    HttpServletRequest request) {
        // 参数校验
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取请求参数
        String requestParams = interfaceInfoInvokeRequest.getUserRequestParams();
        log.info("请求参数=》" + requestParams);
        // 判断接口是否存在 接口是否在线
        Long id = interfaceInfoInvokeRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (interfaceInfo.getStatus() == InterfaceInfoStatusEnum.OFFLINE.getValue()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口关闭");
        }
        // 获取用户的 accessKey 和 secrectKey
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        // 通过ZsApiClient调用接口
        String interfaceInfoName = interfaceInfo.getName();
        Object result = null;
        try {
            result = reflectionInterface(ZsApiClient.class, interfaceInfoName, requestParams, accessKey, secretKey);
        }catch (Exception e) {
            log.info("接口调用出错 => " + e.getMessage());
        }
        return ResultUtils.success(result);
    }


    public Object reflectionInterface(Class<?> reflectionClass, String methName, String parameter, String accessKey, String secretKey) throws Exception {
        Object result = null;
        // 拿到Class对象的构造器
        Constructor<?> constructor = reflectionClass.getDeclaredConstructor(String.class, String.class);
        // 构造 ZsApiClient 的实例，同时传入 accessKey 和 secretKey
        ZsApiClient zsapiClient = (ZsApiClient) constructor.newInstance(accessKey, secretKey);
        // 获取 SDK 中所有的方法
        Method[] methods = zsapiClient.getClass().getMethods();
        // 根据方法名确定需要调用的方法
        for (Method method : methods) {
            if (method.getName().equals(methName)) {
                // 获取方法的参数类型
                Class<?>[] paramterTypes = method.getParameterTypes();
                Method method1;
                if (paramterTypes.length == 0) {
                    method1 = zsapiClient.getClass().getMethod(methName);
                    return method1.invoke(zsapiClient);
                }
                method1 = zsapiClient.getClass().getMethod(methName, paramterTypes[0]);
                Gson gson = new Gson();
                Object args = gson.fromJson(parameter, paramterTypes[0]);
                return result = method1.invoke(zsapiClient, args);
            }
        }
        return result;
    }

    // endregion
}
