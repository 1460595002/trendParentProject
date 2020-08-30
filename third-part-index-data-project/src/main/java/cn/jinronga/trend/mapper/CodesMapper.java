package cn.jinronga.trend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/8/30 0030
 * Time: 17:21
 * E-mail:1460595002@qq.com
 * 类说明:
 */
@Mapper
public interface CodesMapper {

    int insertBatch(@Param("mapData") Map<String, Object> mapData);

}
