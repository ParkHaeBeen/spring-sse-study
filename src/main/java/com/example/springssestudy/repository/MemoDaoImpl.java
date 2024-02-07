package com.example.springssestudy.repository;

import com.example.springssestudy.domain.Memo;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemoDaoImpl implements MemoDao{


  private final EntityManager em;

  @Override
  public Memo getMemoDetail(Long id) {
    Memo memo = em.createQuery(
            "select distinct m from Memo m " +
                "left join fetch m.user u " +
                "left join fetch m.comments c " +
                "where m.id = :id", Memo.class
        )
        .setParameter("id", id)
        .getSingleResult();

    return memo;
  }
}
