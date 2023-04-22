package com.zsapi.backend.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zsapi.backend.common.BaseResponse;
import com.zsapi.backend.common.ErrorCode;
import com.zsapi.backend.common.ResultUtils;
import com.zsapi.backend.exception.BusinessException;
import com.zsapi.backend.model.vo.InterfaceInfoVO;
import com.zsapi.common.model.entity.InterfaceInfo;
import com.zsapi.common.model.entity.UserInterfaceInfo;
import com.zsapi.common.service.InterfaceInfoService;
import com.zsapi.common.service.UserInterfaceInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @ClassName AnalysisController
 * @Author 23951
 * @Date 2023/4/6 22:30
 * @Version 1.0
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    private UserInterfaceInfoService userInterfaceInfoService;
    @Autowired
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoService.listTopInvokeInterfaceInfo(5);
        // 根据接口id分组
        Map<Long, List<UserInterfaceInfo>> userInterfaceInfoMap = userInterfaceInfos.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        // 根据接口id查询接口信息
        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(InterfaceInfo::getId, userInterfaceInfoMap.keySet());
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(wrapper);

        if (CollectionUtil.isEmpty(interfaceInfoList)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        List<InterfaceInfoVO> interfaceInfoVOList = interfaceInfoList.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            int totalNum = userInterfaceInfoMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());

        return ResultUtils.success(interfaceInfoVOList);
    }


}
