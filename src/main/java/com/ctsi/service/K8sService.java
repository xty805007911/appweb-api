package com.ctsi.service;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @ClassName : K8sService
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-11-23 11:29
 */
@Service
public class K8sService {
    @Autowired
    @Qualifier("defaultKubernetesClient")
    DefaultKubernetesClient k8sClient;

    public Object NodeList() {
        return k8sClient.nodes().list();
    }
}
