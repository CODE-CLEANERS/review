package com.example.spring_games.member.repository.query;

import com.example.spring_games.member.domain.Member;
import com.example.spring_games.member.domain.vo.MemberLevel;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.spring_games.member.domain.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    private BooleanExpression filterByMemberName(String memberNameValue){
        if (memberNameValue == null || memberNameValue.isEmpty()){
            return null;
        }
        return member.memberName.value.eq(memberNameValue);
    }

    private BooleanExpression filterByMemberLevel(MemberLevel memberLevel){
        if (memberLevel == null){
            return null;
        }
        return member.memberLevel.eq(memberLevel);
    }

    @Override
    public List<Member> findAllByMemberNameValueAndMemberLevel(String memberNameValue, MemberLevel memberLevel){
        return queryFactory.selectFrom(member)
                .where(filterByMemberLevel(memberLevel), filterByMemberName(memberNameValue))
                .fetch();
    }
}
