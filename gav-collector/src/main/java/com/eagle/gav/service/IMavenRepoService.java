package com.eagle.gav.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eagle.gav.pojo.entity.MavenRepo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author eagle
 * @since 2023-09-29
 */
public interface IMavenRepoService extends IService<MavenRepo> {

    String getHtmlText(String url);
    String seekHtmlTextIfNotStored(String url);
    void seekHtmlTextIfNotStoredWithQueue(String fullUrl);

}
