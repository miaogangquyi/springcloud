
package com.mogo.service;

import com.mogo.domain.GenConfig;
import com.mogo.repository.GenConfigRepository;
import com.mogo.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author miaogang
 * @date 2019-01-14
 */
@Service
@RequiredArgsConstructor
public class GenConfigService  {

    private final GenConfigRepository genConfigRepository;

    public GenConfig find(String tableName) {
        GenConfig genConfig = genConfigRepository.findByTableName(tableName);
        if(genConfig == null){
            return new GenConfig(tableName);
        }
        return genConfig;
    }

    public GenConfig update(String tableName, GenConfig genConfig) {
        // 如果 api 路径为空，则自动生成路径
        if(StringUtils.isBlank(genConfig.getApiPath())){
            String separator = File.separator;
            String[] paths;
            String symbol = "\\";
            if (symbol.equals(separator)) {
                paths = genConfig.getPath().split("\\\\");
            } else {
                paths = genConfig.getPath().split(File.separator);
            }
            StringBuilder api = new StringBuilder();
            for (String path : paths) {
                api.append(path);
                api.append(separator);
                if ("src".equals(path)) {
                    api.append("api");
                    break;
                }
            }
            genConfig.setApiPath(api.toString());
        }
        return genConfigRepository.save(genConfig);
    }
}
