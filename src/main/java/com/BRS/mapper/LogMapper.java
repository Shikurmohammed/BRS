package com.BRS.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.BRS.entity.Log;

@Mapper
public interface LogMapper {
    @Select("Select * from tbl_Log")
    List<Log> getLogList();

    @Select("Select * from tbl_Log where Id=#{id}")
    Optional<Log> getLog(@Param("id") Long id);

    @Select("Select  * from tbl_Log where Id=#{id}")
    List<Log> searchLogByKey(@Param("Key") Log Key);

    @Insert("INSERT into tbl_Log (Time,Level,Message,Exception)" +
            "values(#{time},#{level},#{message},#{exception})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveLog(Log Log);

    @Delete("Delete from tbl_Log where Id =#{id}")
    void removeLog(@Param("id") Long id);

}
