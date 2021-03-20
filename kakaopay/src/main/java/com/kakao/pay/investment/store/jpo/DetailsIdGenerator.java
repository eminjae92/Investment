package com.kakao.pay.investment.store.jpo;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/*
* PAYMENT 테이블 PMT_ID 키 생성
* */
public class DetailsIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {

        Connection connection = sharedSessionContractImplementor.connection();
        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("select count(SEQUENCE_NUMBER) as Id from INVESTMENT_DETAILS");

            if(rs.next())
            {
                int id = rs.getInt(1)+1;

                return id;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
