package com.example.bloom.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author wang.song
 * @date 2020-12-10 15:48
 * @Desc
 */
@Component
@Log4j2
@Order(1)
public class MethodApplicationRunner  implements ApplicationRunner {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("MethodApplicationRunner execute ................args:{}", args);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        if (map == null || map.isEmpty()) {
            return;
        }

        Map<String, List<String>> methodsMap = map.keySet().stream().filter(requestMappingInfo -> requestMappingInfo.getPatternsCondition() != null &&
                !CollectionUtils.isEmpty(requestMappingInfo.getPatternsCondition().getPatterns()) &&
                requestMappingInfo.getPatternsCondition().getPatterns().stream().findFirst().isPresent())
                .distinct().collect(Collectors.toMap(
                        requestMappingInfo -> requestMappingInfo.getPatternsCondition().getPatterns().stream().findFirst().get(),
                        requestMappingInfo -> requestMappingInfo.getMethodsCondition().getMethods().stream().map(RequestMethod::name).collect(toList()),
                        (v1, v2) -> v2
                ));
        log.info("collector url and method is:{}", JSONObject.toJSONString(methodsMap));
    }
}
