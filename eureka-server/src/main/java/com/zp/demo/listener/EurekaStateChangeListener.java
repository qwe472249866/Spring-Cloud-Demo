package com.zp.demo.listener;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.AbstractInstanceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zp
 * @Title: EurekaStateChangeListener
 * @Description:  async callback when eureka state changed
 * @date 2019/1/2412:56
 */
@Component
public class EurekaStateChangeListener {


    @EventListener
    public void listen(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
        //服务断线事件
        String appName = eurekaInstanceCanceledEvent.getAppName();
        String serverId = eurekaInstanceCanceledEvent.getServerId();
        System.out.println(appName);
        System.out.println(serverId);
        System.out.println("服务断线事件");

    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        System.out.println(instanceInfo);
        System.out.println("服务已经注册事件");
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        String appName = event.getAppName();
        String serverId = event.getServerId();
        System.out.println(appName);
        System.out.println(serverId);
        System.out.println("服务续约事件");
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {

        System.out.println("注册中心启动(注册可用)事件");

    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        System.out.println("EurekaServer启动事件");
    }



}
