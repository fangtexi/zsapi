package com.zsapi.gateway;

import com.zsapi.client.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 网关全局过滤器
 * @ClassName CustomerGlobalFilter
 * @Author 23951
 * @Date 2023/3/20 22:11
 * @Version 1.0
 */
@Slf4j
@Component
public class CustomerGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1","localhost");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识：",request.getId());
        log.info("请求路径：",request.getPath().value());
        log.info("请求方法：",request.getMethod());
        log.info("请求参数：",request.getQueryParams());
        log.info("请求来源地址：",request.getRemoteAddress());
        String sourceAdress = request.getLocalAddress().getHostString();

        // 2. 黑白名单（防止用户恶意访问；限制用户的访问）
        // if (!IP_WHITE_LIST.contains(sourceAdress)) {
        //     response.setStatusCode(HttpStatus.FORBIDDEN);
        //     return response.setComplete();
        // }

        // 3. 用户鉴权（判断ak，sk是否合法）（请求级别的鉴权）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String sign = headers.getFirst("sign");
        // todo 防止重放攻击
        //todo 从数据库中查询accessKey是否已经分配给用户
        if (!"21c35ad418a380484550512bc0306992".equals(accessKey)) {
            return handleNoAuth(response);
        }
        //todo 从数据库中查询到secrectKey和accessKey生成签名，与用户传递的签名进行比较判断是否有权限
        String sign1 = SignUtils.getSign(accessKey, "3223d3e89b8bec50b2bab1095fb5cd9b");
        if (!sign.equals(sign1)) {
            return handleNoAuth(response);
        }

        // 4. 请求的模拟接口是否存在
        // todo 从数据库中根据请求方法、请求名称、请求类型来判断请求存在

        // 利用response装饰者，增强原有response的处理能力
        return handleResponse(exchange,chain);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * @description: 无权限
     * @author: zzs
     * @date: 2023/3/20 23:15
     * @param: response
     * @return: reactor.core.publisher.Mono<java.lang.Void>
     **/
    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    /**
     * @description: 接口调用错误
     * @author: zzs
     * @date: 2023/3/21 21:14
     * @param: response
     * @return: reactor.core.publisher.Mono<java.lang.Void>
     **/
    private Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    /**
     * @description: 增强response
     * @author: zzs
     * @date: 2023/3/21 23:05
     * @param: exchange
     * @param: chain
     * @return: reactor.core.publisher.Mono<java.lang.Void>
     **/
    public Mono<Void> handleResponse(ServerWebExchange exchange,GatewayFilterChain chain) {
        try {
            //从交换寄拿响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            //缓冲区工厂，拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            //拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if(statusCode == HttpStatus.OK){
                //装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    //等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        //对象是响应式的
                        if (body instanceof Flux) {
                            //我们拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //往返回值里面写数据
                            //拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 5. 接口调用成功 todo 接口调用次数+1
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                // 6. 记录响应日志
                                log.info("响应结果：" + data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // 7. 调用失败
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                //设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("网关处理响应异常：" + e);
            return chain.filter(exchange);
        }
    }
}
