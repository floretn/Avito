package ru.mephi.avito.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.mephi.avito.mappers.AdMapper;
import ru.mephi.avito.mappers.LinkMapper;

import java.io.IOException;
import java.io.InputStream;

public class InitialClass {

    private static volatile InputStream inputStream;
    static {
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    private static final SqlSession sqlSession = sqlSessionFactory.openSession();
    static {
        InitialClass.sqlSessionFactory.getConfiguration().addMapper(AdMapper.class);
        InitialClass.sqlSessionFactory.getConfiguration().addMapper(LinkMapper.class);
    }

    public static volatile AdMapper adMapper = sqlSession.getMapper(AdMapper.class);
    public static volatile LinkMapper linkMapper = sqlSession.getMapper(LinkMapper.class);

    private InitialClass() {
    }
}
