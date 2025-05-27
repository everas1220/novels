package com.example.novels.repository;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.novels.entity.Genre;
import com.example.novels.entity.Grade;
import com.example.novels.entity.Member;
import com.example.novels.entity.Novel;
import com.example.novels.repository.GenreRepository;
import com.example.novels.repository.GradeRepository;
import com.example.novels.repository.MemberRepository;
import com.example.novels.repository.NovelRepository;

@SpringBootTest
public class NovelRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void getNovelTest() {
        Object[] result = novelRepository.getNovelById(13L);
        // System.out.println(Arrays.toString(result));
        Novel novel = (Novel) result[0];
        Genre genre = (Genre) result[1];
        Double avgGrade = (Double) result[2];
        System.out.println(novel);
        System.out.println(genre);
        System.out.println(avgGrade);
    }
    // ^ 위의 결과값
    // [Novel(id=13, title=Harry Potter and the Sorcerer's Stone,
    // author=J.K. Rowling, publishedDate=1997-06-26, available=false),
    // Genre(id=3, name=Fantasy), 4.75]

    @Test
    public void getNovelListTest() {

        Pageable pageable = PageRequest.of(1, 10, Sort.by("id").descending());

        Page<Object[]> result = novelRepository.list(pageable);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    // user 50 삽입
    @Test
    public void userInsert() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .pw("1111")
                    .nickname("user" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    // grade 200 삽입
    @Test
    public void gradeInsert() {
        IntStream.rangeClosed(1, 50).forEach(i -> {

            // novel id 생성
            long nid = (long) (Math.random() * 20) + 1;

            int rating = (int) (Math.random() * 5) + 1;

            int uid = (int) (Math.random() * 50) + 1;

            Grade grade = Grade.builder()
                    .member(Member.builder().email("user" + uid + "@gmail.com").build())
                    .novel(Novel.builder().id(nid).build())
                    .rating(rating)
                    .build();
            gradeRepository.save(grade);
        });
    }
}
