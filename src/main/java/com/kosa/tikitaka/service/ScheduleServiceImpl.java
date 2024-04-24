package com.kosa.tikitaka.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.tikitaka.model.ScheduleDTO;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public List<ScheduleDTO> calendarList() {
        return sqlSession.selectList("calendarList");
    }

    @Override
    public void addSchedule(ScheduleDTO scheduleDTO) {
        sqlSession.insert("insertSchedule", scheduleDTO);
    }

    @Override
    public int updateSchedule(ScheduleDTO scheduleDTO) {
        return sqlSession.update("updateSchedule", scheduleDTO);
    }

    @Override
    public int deleteSchedule(int scheduleNo) {
        return sqlSession.delete("deleteSchedule", scheduleNo);
    }
}
