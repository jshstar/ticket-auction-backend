insert into users
    (id, created_at, modified_at, birth, email, is_deleted, name, nickname, password, phone_number, role)
    select * from(
    select 0 as id,
            now() as created_at,
             now() as modified_at,
             "1999-01-01" as birth,
             "admin@gmail.com" as email,
             false as is_deleted,
             "관리자" as name,
             "일번관리자" as nickname,
             "$2a$12$q1.iB0qthBEo3wAS0L59/.e3PVh5OSRDGR/.7zNbM/FtXYhazkKf6" as password,
             "0101111111" as phone_number,
             "ADMIN" as role
             ) as init
    where not exists (
        select 1 from users
        where users.email = init.email
    );

-- 비밀번호는 18Iamadmin18!@#

# 유저 추가
# 공연장 추가
insert into place (count_seats, id, name, address, created_at, modified_at)
values (100, 1, '스파르타 공연장', '서울특별시 어딘가', now(), now());

# 공연장 구역 추가
insert into zone (id, name, seat_number, place_id, created_at, modified_at)
values (1, 'A', 10, 1, now(), now());
insert into zone (id, name, seat_number, place_id, created_at, modified_at)
values (2, 'B', 20, 1, now(), now());
insert into zone (id, name, seat_number, place_id, created_at, modified_at)
values (3, 'C', 30, 1, now(), now());
insert into zone (id, name, seat_number, place_id, created_at, modified_at)
values (4, 'D', 40, 1, now(), now());

# 공연 카테고리 추가
INSERT INTO ticket_auction.goods_category (id, created_at, modified_at, name)
VALUES (1, '2024-01-19 12:49:21.969893', '2024-01-19 12:49:21.969893', '연극');
INSERT INTO ticket_auction.goods_category (id, created_at, modified_at, name)
VALUES (2, '2024-01-19 13:02:49.572789', '2024-01-19 13:02:49.572789', '무용');
INSERT INTO ticket_auction.goods_category (id, created_at, modified_at, name)
VALUES (3, '2024-01-19 13:03:06.331558', '2024-01-19 13:03:06.331558', '대중무용');
INSERT INTO ticket_auction.goods_category (id, created_at, modified_at, name)
VALUES (4, '2024-01-19 13:03:35.500130', '2024-01-19 13:03:35.500130', '클래식');
INSERT INTO ticket_auction.goods_category (id, created_at, modified_at, name)
VALUES (5, '2024-01-19 13:04:19.523021', '2024-01-19 13:04:19.523021', '국악');
INSERT INTO ticket_auction.goods_category (id, created_at, modified_at, name)
VALUES (6, '2024-01-19 13:04:45.160043', '2024-01-19 13:04:45.160043', '대중음악');
INSERT INTO ticket_auction.goods_category (id, created_at, modified_at, name)
VALUES (7, '2024-01-19 13:05:34.622163', '2024-01-19 13:05:34.622163', '복합');
INSERT INTO ticket_auction.goods_category (id, created_at, modified_at, name)
VALUES (8, '2024-01-19 13:06:18.355755', '2024-01-19 13:06:18.355755', '서커스마술');
INSERT INTO ticket_auction.goods_category (id, created_at, modified_at, name)
VALUES (9, '2024-01-19 13:07:37.598522', '2024-01-19 13:07:37.598522', '뮤지컬');


# 공연 정보 추가
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (1, '2024-01-19 12:49:21.976206', '2024-01-19 12:49:21.976206', 'AGE_15', '보보와 자자', '보보와 자자', 80, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (2, '2024-01-19 12:49:23.124712', '2024-01-19 12:49:23.124712', 'AGE_12', '월간연극, 하녀들', '월간연극, 하녀들', 70, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (3, '2024-01-19 12:49:24.369620', '2024-01-19 12:49:24.369620', 'AGE_19', '나의PS파트너', '나의PS파트너', 90, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (4, '2024-01-19 12:49:26.494705', '2024-01-19 12:49:26.494705', 'AGE_15', '다락방', '다락방', 165, 3);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (5, '2024-01-19 12:49:27.044679', '2024-01-19 12:49:27.044679', 'AGE_12', '고도를 기다리며', '고도를 기다리며', 150, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (6, '2024-01-19 12:49:28.137535', '2024-01-19 12:49:28.137535', 'AGE_15', '바냐의 시간', '바냐의 시간', 60, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (7, '2024-01-19 12:49:28.537425', '2024-01-19 12:49:28.537425', 'AGE_15', '만선', '만선', 100, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (8, '2024-01-19 12:49:29.667659', '2024-01-19 12:49:29.667659', 'AGE_15', '유림식당 [대학로]', '유림식당 [대학로]', 90, 7);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (9, '2024-01-19 12:49:31.202209', '2024-01-19 12:49:31.202209', 'AGE_ALL', '셰익스피어 리어왕 덧대어 쓰기, 와르르',
        '셰익스피어 리어왕 덧대어 쓰기, 와르르', 75, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (10, '2024-01-19 12:49:33.537784', '2024-01-19 12:49:33.537784', 'AGE_15', '갈매기', '갈매기', 140, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (11, '2024-01-19 12:49:35.222444', '2024-01-19 12:49:35.222444', 'AGE_12', '연극 아트 [대학로]', '연극 아트 [대학로]', 100, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (12, '2024-01-19 12:49:36.418617', '2024-01-19 12:49:36.418617', 'AGE_12', '벤자민 버튼의 시간은 거꾸로 간다',
        '벤자민 버튼의 시간은 거꾸로 간다', 70, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (13, '2024-01-19 12:49:37.274237', '2024-01-19 12:49:37.274237', 'AGE_12', '정동진', '정동진', 90, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (14, '2024-01-19 12:49:38.207489', '2024-01-19 12:49:38.207489', 'AGE_7', '제11회 실험무대702 정기공연, H',
        '제11회 실험무대702 정기공연, H', 45, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (15, '2024-01-19 12:49:39.383481', '2024-01-19 12:49:39.383481', 'AGE_12', '창작산실, 화전 [대학로]', '창작산실, 화전 [대학로]',
        120, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (16, '2024-01-19 12:49:43.500130', '2024-01-19 12:49:43.500130', 'AGE_12', '역사시비 프로젝트, 진짜 연극 불멸의 이기석 (2월)',
        '역사시비 프로젝트, 진짜 연극 불멸의 이기석 (2월)', 90, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (17, '2024-01-19 12:49:43.943172', '2024-01-19 12:49:43.943172', 'AGE_12', '역사시비 프로젝트, 양떼목장의 대혈투 (3월)',
        '역사시비 프로젝트, 양떼목장의 대혈투 (3월)', 80, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (18, '2024-01-19 12:49:44.411827', '2024-01-19 12:49:44.411827', 'AGE_ALL', '러브라인', '러브라인', 80, 1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (19, '2024-01-19 12:49:45.085924', '2024-01-19 12:49:45.085924', 'AGE_15', '평범한 거짓말 [대학로]', '평범한 거짓말 [대학로]', 80,
        1);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (21, '2024-01-19 13:02:49.578620', '2024-01-19 13:02:49.578620', 'AGE_ALL', '팡타의 춤, 서정숙의 비손', '팡타의 춤, 서정숙의 비손',
        70, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (22, '2024-01-19 13:02:52.989643', '2024-01-19 13:02:52.989643', 'AGE_7', 'a Dark room', 'a Dark room', 60, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (23, '2024-01-19 13:02:53.639601', '2024-01-19 13:02:53.639601', 'AGE_7', '서울시무용단, 국수호와 김재덕의 사계',
        '서울시무용단, 국수호와 김재덕의 사계', 80, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (24, '2024-01-19 13:02:54.356425', '2024-01-19 13:02:54.356425', 'AGE_7', '일무', '일무', 80, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (25, '2024-01-19 13:02:55.563196', '2024-01-19 13:02:55.563196', 'AGE_12', '정훈목, Yaras [대학로]',
        '정훈목, Yaras [대학로]', 60, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (26, '2024-01-19 13:02:56.057373', '2024-01-19 13:02:56.057373', 'AGE_ALL', '일소당 음악회, 채상묵', '일소당 음악회, 채상묵', 80,
        2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (27, '2024-01-19 13:02:57.398568', '2024-01-19 13:02:57.398568', 'AGE_7', '애니멀', '애니멀', 60, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (28, '2024-01-19 13:02:58.080091', '2024-01-19 13:02:58.080091', 'AGE_7', '유니버설발레단, 코리아 이모션 정',
        '유니버설발레단, 코리아 이모션 정', 75, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (29, '2024-01-19 13:02:59.192526', '2024-01-19 13:02:59.192526', 'AGE_12', '로미오와 줄리엣', '로미오와 줄리엣', 110, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (30, '2024-01-19 13:03:01.149346', '2024-01-19 13:03:01.149346', 'AGE_7', '국립발레단, KNB Movement Series 9',
        '국립발레단, KNB Movement Series 9', 70, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (31, '2024-01-19 13:03:01.547820', '2024-01-19 13:03:01.547820', 'AGE_7', '국립무용단 설명절 기획공연, 축제',
        '국립무용단 설명절 기획공연, 축제', 70, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (32, '2024-01-19 13:03:02.208020', '2024-01-19 13:03:02.208020', 'AGE_7', '국립무용단, 신선', '국립무용단, 신선', 60, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (33, '2024-01-19 13:03:02.831371', '2024-01-19 13:03:02.831371', 'AGE_7', '국립무용단, 사자의 서', '국립무용단, 사자의 서', 70, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (34, '2024-01-19 13:03:03.403903', '2024-01-19 13:03:03.403903', 'AGE_7', '국립현대무용단, 인잇', '국립현대무용단, 인잇', 60, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (35, '2024-01-19 13:03:03.755286', '2024-01-19 13:03:03.755286', 'AGE_7', '국립무용단, 몽유도원무', '국립무용단, 몽유도원무', 60, 2);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (36, '2024-01-19 13:03:06.334878', '2024-01-19 13:03:06.334878', 'AGE_7', '댄스콘서트 희노애락 시즌3', '댄스콘서트 희노애락 시즌3',
        100, 3);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (37, '2024-01-19 13:03:35.503605', '2024-01-19 13:03:35.503605', 'AGE_7', '차홍서 플루트 독주회', '차홍서 플루트 독주회', 90, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (38, '2024-01-19 13:03:36.246036', '2024-01-19 13:03:36.246036', 'AGE_7', '영화음악콘서트 in 성수, 겨울의 속삭임',
        '영화음악콘서트 in 성수, 겨울의 속삭임', 75, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (39, '2024-01-19 13:03:38.082826', '2024-01-19 13:03:38.082826', 'AGE_7', '제11회 시티필하모니오케스트라 초청, 우수신예 수상자 음악회',
        '제11회 시티필하모니오케스트라 초청, 우수신예 수상자 음악회', 110, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (40, '2024-01-19 13:03:39.521436', '2024-01-19 13:03:39.521436', 'AGE_7',
        '히사이시 조 & 지브리 영화음악 콘서트, Film & His Own Music', '히사이시 조 & 지브리 영화음악 콘서트, Film & His Own Music', 120, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (41, '2024-01-19 13:03:40.712253', '2024-01-19 13:03:40.712253', 'AGE_ALL', '강북문화재단 신년음악회, 한빛예술단 & 베이스 구본수',
        '강북문화재단 신년음악회, 한빛예술단 & 베이스 구본수', 80, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (42, '2024-01-19 13:03:41.570772', '2024-01-19 13:03:41.570772', 'AGE_7', '전미미 피아노 독주회', '전미미 피아노 독주회', 90, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (43, '2024-01-19 13:03:53.677098', '2024-01-19 13:03:53.677098', 'AGE_7', '홍채린 귀국 파이프오르간 독주회',
        '홍채린 귀국 파이프오르간 독주회', 90, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (44, '2024-01-19 13:03:55.091481', '2024-01-19 13:03:55.091481', 'AGE_ALL', '제4회 한화불꽃합창단 정기연주회',
        '제4회 한화불꽃합창단 정기연주회', 100, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (45, '2024-01-19 13:03:55.698296', '2024-01-19 13:03:55.698296', 'AGE_7', '하나 윈드 오케스트라 창단 연주회',
        '하나 윈드 오케스트라 창단 연주회', 100, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (46, '2024-01-19 13:03:57.199582', '2024-01-19 13:03:57.199582', 'AGE_7', '피아니스트 김소연 초청 리사이틀',
        '피아니스트 김소연 초청 리사이틀', 110, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (47, '2024-01-19 13:03:57.930561', '2024-01-19 13:03:57.930561', 'AGE_7', '제66회 시티필하모니오케스트라 정기연주회, 신년음악회',
        '제66회 시티필하모니오케스트라 정기연주회, 신년음악회', 110, 4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (48, '2024-01-19 13:03:58.561891', '2024-01-19 13:03:58.561891', 'AGE_7', '제4회 콘아니마 정기연주회', '제4회 콘아니마 정기연주회', 90,
        4);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (49, '2024-01-19 13:04:19.528351', '2024-01-19 13:04:19.528351', 'AGE_ALL', '장삼수의 아쟁: 이태백류 아쟁산조와 시나위',
        '장삼수의 아쟁: 이태백류 아쟁산조와 시나위', 60, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (50, '2024-01-19 13:04:20.934488', '2024-01-19 13:04:20.934488', 'AGE_7', '김은연 국악 콘서트: 서로를 마음에 두고',
        '김은연 국악 콘서트: 서로를 마음에 두고', 80, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (51, '2024-01-19 13:04:22.670720', '2024-01-19 13:04:22.670720', 'AGE_7', '제362회 서울시국악관현악단 정기연주회',
        '제362회 서울시국악관현악단 정기연주회', 90, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (52, '2024-01-19 13:04:23.199460', '2024-01-19 13:04:23.199460', 'AGE_7', '제361회 서울시국악관현악단 정기연주회',
        '제361회 서울시국악관현악단 정기연주회', 90, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (53, '2024-01-19 13:04:23.714643', '2024-01-19 13:04:23.714643', 'AGE_7', '제360회 서울시국악관현악단 정기연주회',
        '제360회 서울시국악관현악단 정기연주회', 90, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (54, '2024-01-19 13:04:24.180240', '2024-01-19 13:04:24.180240', 'AGE_7', '서울시국악관현악단, 실내악 시리즈 II',
        '서울시국악관현악단, 실내악 시리즈 II', 70, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (55, '2024-01-19 13:04:24.560570', '2024-01-19 13:04:24.560570', 'AGE_7', '서울시국악관현악단, 실내악 시리즈I',
        '서울시국악관현악단, 실내악 시리즈I', 70, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (56, '2024-01-19 13:04:25.035196', '2024-01-19 13:04:25.035196', 'AGE_7', '서울시국악관현악단, 명연주자 시리즈',
        '서울시국악관현악단, 명연주자 시리즈', 90, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (57, '2024-01-19 13:04:27.150018', '2024-01-19 13:04:27.150018', 'AGE_7', '토요명품', '토요명품', 80, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (58, '2024-01-19 13:04:29.766324', '2024-01-19 13:04:29.766324', 'AGE_ALL', '영재한음 (국악)회', '영재한음 (국악)회', 60, 5);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (59, '2024-01-19 13:04:45.164499', '2024-01-19 13:04:45.164499', 'AGE_15', '시황 단독 공연, 도화', '시황 단독 공연, 도화', 120,
        6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (60, '2024-01-19 13:04:46.144234', '2024-01-19 13:04:46.144234', 'AGE_ALL', '오리엔째이션: 채보훈', '오리엔째이션: 채보훈', 90, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (61, '2024-01-19 13:04:48.157936', '2024-01-19 13:04:48.157936', 'AGE_ALL', '유로댄스 뮤직 콘서트: 원스 어게인',
        '유로댄스 뮤직 콘서트: 원스 어게인', 100, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (62, '2024-01-19 13:04:51.311747', '2024-01-19 13:04:51.311747', 'AGE_7', '알레프 단독 콘서트: Colors',
        '알레프 단독 콘서트: Colors', 90, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (63, '2024-01-19 13:04:51.923507', '2024-01-19 13:04:51.923507', 'AGE_7', 'MPMG WEEK, 78AVENUE 78LIVE: naru(나루)',
        'MPMG WEEK, 78AVENUE 78LIVE: naru(나루)', 78, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (64, '2024-01-19 13:04:53.607263', '2024-01-19 13:04:53.607263', 'AGE_7',
        'MPMG WEEK, Another Nice Day #41: Chan, ecru(에크루), homezone',
        'MPMG WEEK, Another Nice Day #41: Chan, ecru(에크루), homezone', 80, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (65, '2024-01-19 13:04:55.541976', '2024-01-19 13:04:55.541976', 'AGE_12', 'HIPHOPPLAYA FESTIVAL',
        'HIPHOPPLAYA FESTIVAL', 480, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (66, '2024-01-19 13:04:56.643864', '2024-01-19 13:04:56.643864', 'AGE_7', 'JUNG YONG HWA LIVE, YOUR CITY',
        'JUNG YONG HWA LIVE, YOUR CITY', 120, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (67, '2024-01-19 13:05:04.487863', '2024-01-19 13:05:04.487863', 'AGE_ALL', '노갈 콘서트: 일상윤회', '노갈 콘서트: 일상윤회', 120,
        6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (68, '2024-01-19 13:05:07.819464', '2024-01-19 13:05:07.819464', 'AGE_7', 'WOODZ World Tour: OO-LI FINALE',
        'WOODZ World Tour: OO-LI FINALE', 120, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (69, '2024-01-19 13:05:14.656199', '2024-01-19 13:05:14.656199', 'AGE_ALL', '김현철 단독콘서트, 겨울아 내려라',
        '김현철 단독콘서트, 겨울아 내려라', 100, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (70, '2024-01-19 13:05:15.235034', '2024-01-19 13:05:15.235034', 'AGE_ALL', '이영훈 단독공연: 이영훈의 신년인사',
        '이영훈 단독공연: 이영훈의 신년인사', 70, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (71, '2024-01-19 13:05:17.371002', '2024-01-19 13:05:17.371002', 'AGE_ALL',
        '잭킹콩 단독 콘서트, JKC NEW YEAR: 롤링 29주년 기념 공연', '잭킹콩 단독 콘서트, JKC NEW YEAR: 롤링 29주년 기념 공연', 100, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (72, '2024-01-19 13:05:19.807263', '2024-01-19 13:05:19.807263', 'AGE_ALL', '신설희, 우리는 모두 누군가의 꿈이었지 앨범 발매기념 단독공연',
        '신설희, 우리는 모두 누군가의 꿈이었지 앨범 발매기념 단독공연', 80, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (73, '2024-01-19 13:05:20.346389', '2024-01-19 13:05:20.346389', 'AGE_12', '바톤콘서트', '바톤콘서트', 90, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (74, '2024-01-19 13:05:21.937118', '2024-01-19 13:05:21.937118', 'AGE_ALL', '이민휘 2집 발매 기념 단독공연: 미래의 고향',
        '이민휘 2집 발매 기념 단독공연: 미래의 고향', 80, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (75, '2024-01-19 13:05:22.500374', '2024-01-19 13:05:22.500374', 'AGE_19', '재즈소사이어티서울 재즈공연', '재즈소사이어티서울 재즈공연',
        100, 6);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (76, '2024-01-19 13:05:34.624467', '2024-01-19 13:05:34.624467', 'AGE_7', '적로: 이슬의 노래', '적로: 이슬의 노래', 80, 7);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (77, '2024-01-19 13:05:38.069675', '2024-01-19 13:05:38.069675', 'AGE_12', '노인과 바다', '노인과 바다', 65, 7);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (78, '2024-01-19 13:06:01.749674', '2024-01-19 13:06:01.749674', 'AGE_7', '새해국악연: 청룡, 하늘을 날다',
        '새해국악연: 청룡, 하늘을 날다', 90, 7);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (79, '2024-01-19 13:06:06.291069', '2024-01-19 13:06:06.291069', 'AGE_7', '청소년을 위한 음악과 클래식의 징검다리, 음악에 색을 입히다',
        '청소년을 위한 음악과 클래식의 징검다리, 음악에 색을 입히다', 90, 7);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (80, '2024-01-19 13:06:07.480631', '2024-01-19 13:06:07.480631', 'AGE_ALL',
        '그림 읽어주는 베토벤, THE CONCERT: 그리스 로마 신화', '그림 읽어주는 베토벤, THE CONCERT: 그리스 로마 신화', 70, 7);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (81, '2024-01-19 13:06:09.447874', '2024-01-19 13:06:09.447874', 'AGE_7', 'USW 신년음악회', 'USW 신년음악회', 120, 7);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (82, '2024-01-19 13:06:10.153707', '2024-01-19 13:06:10.153707', 'AGE_7', '서울신문 창간 120주년 기념, 봄날음악회',
        '서울신문 창간 120주년 기념, 봄날음악회', 120, 7);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (83, '2024-01-19 13:06:10.622914', '2024-01-19 13:06:10.622914', 'AGE_7', '필립 드쿠플레, 샤잠!', '필립 드쿠플레, 샤잠!', 90, 7);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (84, '2024-01-19 13:06:12.131036', '2024-01-19 13:06:12.131036', 'AGE_7', '라움 마티네 콘서트', '라움 마티네 콘서트', 60, 7);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (85, '2024-01-19 13:06:18.358080', '2024-01-19 13:06:18.358080', 'AGE_19', '어린이 스토리 마술쇼 [구로]',
        '어린이 스토리 마술쇼 [구로]', 55, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (86, '2024-01-19 13:06:19.654171', '2024-01-19 13:06:19.654171', 'AGE_19', '매직오브아트 시즌2 [서울]', '매직오브아트 시즌2 [서울]',
        60, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (87, '2024-01-19 13:06:20.425413', '2024-01-19 13:06:20.425413', 'AGE_ALL', '원더매직의 오리지널 과학마술콘서트 시즌2 [서울]',
        '원더매직의 오리지널 과학마술콘서트 시즌2 [서울]', 70, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (88, '2024-01-19 13:06:23.796641', '2024-01-19 13:06:23.796641', 'AGE_19', '어린이 스토리 마술쇼 [서울]',
        '어린이 스토리 마술쇼 [서울]', 55, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (89, '2024-01-19 13:06:24.813517', '2024-01-19 13:06:24.813517', 'AGE_19', '문준호의 슈퍼매직쇼 [서울]', '문준호의 슈퍼매직쇼 [서울]',
        60, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (90, '2024-01-19 13:06:25.591048', '2024-01-19 13:06:25.591048', 'AGE_15', 'JENNY의 상상의 조각2', 'JENNY의 상상의 조각2',
        75, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (91, '2024-01-19 13:06:26.327970', '2024-01-19 13:06:26.327970', 'AGE_12', '이은결 연출, 멜리에스 일루션',
        '이은결 연출, 멜리에스 일루션', 80, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (92, '2024-01-19 13:06:30.539597', '2024-01-19 13:06:30.539597', 'AGE_7', '제임스 띠에리, 룸', '제임스 띠에리, 룸', 100, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (93, '2024-01-19 13:06:36.358062', '2024-01-19 13:06:36.358062', 'AGE_19', '어린이 스토리 마술쇼 [서울 강동]',
        '어린이 스토리 마술쇼 [서울 강동]', 60, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (94, '2024-01-19 13:06:37.061695', '2024-01-19 13:06:37.061695', 'AGE_19', '어린이 마술쇼 [서울 중랑]', '어린이 마술쇼 [서울 중랑]',
        60, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (95, '2024-01-19 13:06:44.757231', '2024-01-19 13:06:44.757231', 'AGE_19', '매직랜드 매직벌룬쇼 [혜화]', '매직랜드 매직벌룬쇼 [혜화]',
        60, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (96, '2024-01-19 13:06:46.411048', '2024-01-19 13:06:46.411048', 'AGE_ALL', '오감만족 톡톡, 스노우 버블쇼 [목동]',
        '오감만족 톡톡, 스노우 버블쇼 [목동]', 55, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (97, '2024-01-19 13:06:47.087856', '2024-01-19 13:06:47.087856', 'AGE_19', '매직 사이언스 콘서트 [대학로]',
        '매직 사이언스 콘서트 [대학로]', 50, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (98, '2024-01-19 13:06:53.753529', '2024-01-19 13:06:53.753529', 'AGE_ALL', '미래상상마술쇼 [대학로]', '미래상상마술쇼 [대학로]', 60,
        8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (99, '2024-01-19 13:06:55.745264', '2024-01-19 13:06:55.745264', 'AGE_15', '박민호 롤러코스터', '박민호 롤러코스터', 65, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (100, '2024-01-19 13:07:01.141922', '2024-01-19 13:07:01.141922', 'AGE_19', '마술그리스신화 [대학로(앵콜)]',
        '마술그리스신화 [대학로(앵콜)]', 60, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (101, '2024-01-19 13:07:02.944519', '2024-01-19 13:07:02.944519', 'AGE_19', '사유의 시간 ep.2', '사유의 시간 ep.2', 70, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (102, '2024-01-19 13:07:03.523060', '2024-01-19 13:07:03.523060', 'AGE_12', '이재홍의 컬러라이즈', '이재홍의 컬러라이즈', 75, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (103, '2024-01-19 13:07:07.519164', '2024-01-19 13:07:07.519164', 'AGE_ALL', '매직쇼 블루문', '매직쇼 블루문', 60, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (104, '2024-01-19 13:07:11.724155', '2024-01-19 13:07:11.724155', 'AGE_15', '로멘틱 홀리데이 매직쇼', '로멘틱 홀리데이 매직쇼', 60,
        8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (105, '2024-01-19 13:07:12.561542', '2024-01-19 13:07:12.561542', 'AGE_12', 'VIP 매직쇼', 'VIP 매직쇼', 40, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (106, '2024-01-19 13:07:15.141264', '2024-01-19 13:07:15.141264', 'AGE_ALL', '요리하는 마술사 [대학로]', '요리하는 마술사 [대학로]',
        70, 8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (107, '2024-01-19 13:07:17.024141', '2024-01-19 13:07:17.024141', 'AGE_7', '1미터 마술공연 [강남]', '1미터 마술공연 [강남]', 60,
        8);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (108, '2024-01-19 13:07:37.601374', '2024-01-19 13:07:37.601374', 'AGE_ALL', '앤서니 브라운의 우리가족 [서울]',
        '앤서니 브라운의 우리가족 [서울]', 60, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (109, '2024-01-19 13:07:39.840686', '2024-01-19 13:07:39.840686', 'AGE_ALL', '금도끼 은도끼 [서울 금천]',
        '금도끼 은도끼 [서울 금천]', 55, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (110, '2024-01-19 13:07:41.492502', '2024-01-19 13:07:41.492502', 'AGE_7', '인사이드 미', '인사이드 미', 110, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (111, '2024-01-19 13:07:43.290780', '2024-01-19 13:07:43.290780', 'AGE_19', '오버코트', '오버코트', 50, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (112, '2024-01-19 13:07:44.340620', '2024-01-19 13:07:44.340620', 'AGE_7', '길', '길', 110, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (113, '2024-01-19 13:07:45.656035', '2024-01-19 13:07:45.656035', 'AGE_19', '아기돼지 삼형제 [대학로]', '아기돼지 삼형제 [대학로]',
        50, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (114, '2024-01-19 13:07:46.987936', '2024-01-19 13:07:46.987936', 'AGE_ALL', '히즈쇼 뮤지컬 시즌2, 드림호 ARK의 비밀 [서울 양천]',
        '히즈쇼 뮤지컬 시즌2, 드림호 ARK의 비밀 [서울 양천]', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (115, '2024-01-19 13:07:47.612878', '2024-01-19 13:07:47.612878', 'AGE_19', '영어 뮤지컬, Princess & Ondal 평강온달',
        '영어 뮤지컬, Princess & Ondal 평강온달', 60, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (116, '2024-01-19 13:07:48.301555', '2024-01-19 13:07:48.301555', 'AGE_12', '너를 위한 글자 [대학로]', '너를 위한 글자 [대학로]',
        100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (117, '2024-01-19 13:07:49.196213', '2024-01-19 13:07:49.196213', 'AGE_7', '내일은 스타 [대학로]', '내일은 스타 [대학로]', 90,
        9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (118, '2024-01-19 13:07:49.653927', '2024-01-19 13:07:49.653927', 'AGE_19', '오즈의 마법사 [대학로]', '오즈의 마법사 [대학로]', 50,
        9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (119, '2024-01-19 13:07:50.627019', '2024-01-19 13:07:50.627019', 'AGE_19', '신데렐라 [대학로]', '신데렐라 [대학로]', 50, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (120, '2024-01-19 13:07:52.124522', '2024-01-19 13:07:52.124522', 'AGE_19', '더 맨 얼라이브, 초이스', '더 맨 얼라이브, 초이스', 75,
        9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (121, '2024-01-19 13:07:53.191754', '2024-01-19 13:07:53.191754', 'AGE_ALL', '고추장 떡볶이', '고추장 떡볶이', 120, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (122, '2024-01-19 13:07:54.060003', '2024-01-19 13:07:54.060003', 'AGE_19', '최고다 호기심딱지: 빵빵 이야기 속으로 [서울]',
        '최고다 호기심딱지: 빵빵 이야기 속으로 [서울]', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (123, '2024-01-19 13:07:55.551825', '2024-01-19 13:07:55.551825', 'AGE_12', '라스트 파이브 이어스', '라스트 파이브 이어스', 100,
        9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (124, '2024-01-19 13:07:57.179711', '2024-01-19 13:07:57.179711', 'AGE_ALL', '흥부와 놀부', '흥부와 놀부', 50, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (125, '2024-01-19 13:07:58.293172', '2024-01-19 13:07:58.293172', 'AGE_19', '반짝반짝 백설공주', '반짝반짝 백설공주', 50, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (126, '2024-01-19 13:07:59.599450', '2024-01-19 13:07:59.599450', 'AGE_19', '슈퍼클로젯', '슈퍼클로젯', 60, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (127, '2024-01-19 13:08:00.731220', '2024-01-19 13:08:00.731220', 'AGE_7', '10주년 기념공연, 레베카 [서울(앵콜)]',
        '10주년 기념공연, 레베카 [서울(앵콜)]', 175, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (128, '2024-01-19 13:08:03.840008', '2024-01-19 13:08:03.840008', 'AGE_19', '헬로카봇 시즌7, 웰컴 투 파라다이스 [서울(앵콜)]',
        '헬로카봇 시즌7, 웰컴 투 파라다이스 [서울(앵콜)]', 65, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (129, '2024-01-19 13:08:05.226674', '2024-01-19 13:08:05.226674', 'AGE_ALL', '고양이 해결사 깜냥: 편의점을 환하게 밝혀라',
        '고양이 해결사 깜냥: 편의점을 환하게 밝혀라', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (130, '2024-01-19 13:08:06.547506', '2024-01-19 13:08:06.547506', 'AGE_12', '은하철도의 밤', '은하철도의 밤', 100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (131, '2024-01-19 13:08:08.021474', '2024-01-19 13:08:08.021474', 'AGE_7', '스쿨 오브 락 월드투어 [서울]',
        '스쿨 오브 락 월드투어 [서울]', 150, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (132, '2024-01-19 13:08:09.426999', '2024-01-19 13:08:09.426999', 'AGE_12', '겨울나그네', '겨울나그네', 150, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (133, '2024-01-19 13:08:10.321743', '2024-01-19 13:08:10.321743', 'AGE_12', '메리셸리', '메리셸리', 110, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (134, '2024-01-19 13:08:11.496131', '2024-01-19 13:08:11.496131', 'AGE_ALL', '토닥토닥 꼬모: 숲 속 캠핑놀이',
        '토닥토닥 꼬모: 숲 속 캠핑놀이', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (135, '2024-01-19 13:08:12.808340', '2024-01-19 13:08:12.808340', 'AGE_7', '천로역정', '천로역정', 100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (136, '2024-01-19 13:08:13.889345', '2024-01-19 13:08:13.889345', 'AGE_ALL', '꼬마버스 타요: 용감한 구조대 레스큐 타요 [목동]',
        '꼬마버스 타요: 용감한 구조대 레스큐 타요 [목동]', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (137, '2024-01-19 13:08:17.084626', '2024-01-19 13:08:17.084626', 'AGE_19', '이상한 과자 가게 전천당: 천옥원, 소원과자 대결전',
        '이상한 과자 가게 전천당: 천옥원, 소원과자 대결전', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (138, '2024-01-19 13:08:19.877628', '2024-01-19 13:08:19.877628', 'AGE_19', '오마이어스: 핑크버블의 습격 [서울]',
        '오마이어스: 핑크버블의 습격 [서울]', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (139, '2024-01-19 13:08:24.766406', '2024-01-19 13:08:24.766406', 'AGE_ALL', '페인터즈', '페인터즈', 75, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (140, '2024-01-19 13:08:26.555485', '2024-01-19 13:08:26.555485', 'AGE_12', '미드나잇: 액터뮤지션 [대학로]',
        '미드나잇: 액터뮤지션 [대학로]', 100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (141, '2024-01-19 13:08:27.563040', '2024-01-19 13:08:27.563040', 'AGE_12', '겨울 이불 [서울]', '겨울 이불 [서울]', 60, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (142, '2024-01-19 13:08:29.143206', '2024-01-19 13:08:29.143206', 'AGE_12', '결투', '결투', 110, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (143, '2024-01-19 13:08:30.441381', '2024-01-19 13:08:30.441381', 'AGE_12', '아가사 AGATHA', '아가사 AGATHA', 135, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (144, '2024-01-19 13:08:32.276405', '2024-01-19 13:08:32.276405', 'AGE_19', '두들팝ver.2, 폴리팝', '두들팝ver.2, 폴리팝', 60,
        9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (145, '2024-01-19 13:08:35.174072', '2024-01-19 13:08:35.174072', 'AGE_7', '홀연했던 사나이', '홀연했던 사나이', 110, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (146, '2024-01-19 13:08:39.100497', '2024-01-19 13:08:39.100497', 'AGE_7', '일 테노레', '일 테노레', 160, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (147, '2024-01-19 13:08:40.416179', '2024-01-19 13:08:40.416179', 'AGE_12', '더데빌: 파우스트', '더데빌: 파우스트', 110, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (148, '2024-01-19 13:08:41.708911', '2024-01-19 13:08:41.708911', 'AGE_19', '파워레인저 다이노포스: 새로운 세계',
        '파워레인저 다이노포스: 새로운 세계', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (149, '2024-01-19 13:08:42.993474', '2024-01-19 13:08:42.993474', 'AGE_7', '푸에르자부르타 웨이라 인 서울',
        '푸에르자부르타 웨이라 인 서울', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (150, '2024-01-19 13:08:44.061394', '2024-01-19 13:08:44.061394', 'AGE_19', '와일드 와일드: 애프터파티', '와일드 와일드: 애프터파티',
        75, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (151, '2024-01-19 13:08:45.555385', '2024-01-19 13:08:45.555385', 'AGE_7', '에곤 실레', '에곤 실레', 60, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (152, '2024-01-19 13:08:47.130370', '2024-01-19 13:08:47.130370', 'AGE_12', '키다리 아저씨 [대학로]', '키다리 아저씨 [대학로]',
        135, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (153, '2024-01-19 13:08:48.227208', '2024-01-19 13:08:48.227208', 'AGE_7', '모딜리아니', '모딜리아니', 60, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (154, '2024-01-19 13:08:49.603717', '2024-01-19 13:08:49.603717', 'AGE_7', '리진: 빛의 여인', '리진: 빛의 여인', 100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (155, '2024-01-19 13:08:50.476147', '2024-01-19 13:08:50.476147', 'AGE_7', '몬테크리스토', '몬테크리스토', 165, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (156, '2024-01-19 13:08:52.707035', '2024-01-19 13:08:52.707035', 'AGE_19', '공룡이 살아있다 [서울]', '공룡이 살아있다 [서울]', 80,
        9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (157, '2024-01-19 13:08:54.016845', '2024-01-19 13:08:54.016845', 'AGE_ALL', '컴프롬어웨이 [서울]', '컴프롬어웨이 [서울]', 130,
        9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (158, '2024-01-19 13:08:55.673861', '2024-01-19 13:08:55.673861', 'AGE_15', '난쟁이들', '난쟁이들', 120, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (159, '2024-01-19 13:08:57.128803', '2024-01-19 13:08:57.128803', 'AGE_12', '스모크', '스모크', 110, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (160, '2024-01-19 13:08:57.990047', '2024-01-19 13:08:57.990047', 'AGE_12', '드라큘라', '드라큘라', 165, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (161, '2024-01-19 13:08:59.075128', '2024-01-19 13:08:59.075128', 'AGE_7', '스토리 오브 마이라이프', '스토리 오브 마이라이프', 100,
        9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (162, '2024-01-19 13:09:00.076740', '2024-01-19 13:09:00.076740', 'AGE_7', '마리 퀴리', '마리 퀴리', 150, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (163, '2024-01-19 13:09:01.367650', '2024-01-19 13:09:01.367650', 'AGE_19', '장화 신은 고양이 비긴즈: 플레이 버전',
        '장화 신은 고양이 비긴즈: 플레이 버전', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (164, '2024-01-19 13:09:02.944602', '2024-01-19 13:09:02.944602', 'AGE_12', '렌트 [코엑스]', '렌트 [코엑스]', 160, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (165, '2024-01-19 13:09:05.506768', '2024-01-19 13:09:05.506768', 'AGE_7', '시스터 액트', '시스터 액트', 150, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (166, '2024-01-19 13:09:08.074779', '2024-01-19 13:09:08.074779', 'AGE_12', '빨래 [대학로]', '빨래 [대학로]', 165, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (167, '2024-01-19 13:09:09.281150', '2024-01-19 13:09:09.281150', 'AGE_7', '볼륨업', '볼륨업', 100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (168, '2024-01-19 13:09:10.500841', '2024-01-19 13:09:10.500841', 'AGE_7', '레미제라블 [블루스퀘어]', '레미제라블 [블루스퀘어]', 180,
        9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (169, '2024-01-19 13:09:12.238646', '2024-01-19 13:09:12.238646', 'AGE_12', '난타 [홍대]', '난타 [홍대]', 90, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (170, '2024-01-19 13:09:13.008733', '2024-01-19 13:09:13.008733', 'AGE_19', '내 친구 유관순', '내 친구 유관순', 80, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (171, '2024-01-19 13:09:16.503552', '2024-01-19 13:09:16.503552', 'AGE_12', '친구의 전설', '친구의 전설', 120, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (172, '2024-01-19 13:09:17.663041', '2024-01-19 13:09:17.663041', 'AGE_ALL', '옹알스 [대학로]', '옹알스 [대학로]', 70, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (173, '2024-01-19 13:09:19.351635', '2024-01-19 13:09:19.351635', 'AGE_7', '썸데이 [대학로]', '썸데이 [대학로]', 100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (174, '2024-01-19 13:09:27.102205', '2024-01-19 13:09:27.102205', 'AGE_19', '셰프 [서울]', '셰프 [서울]', 75, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (175, '2024-01-19 13:09:28.044718', '2024-01-19 13:09:28.044718', 'AGE_7', '김종욱 찾기', '김종욱 찾기', 100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (176, '2024-01-19 13:09:29.239757', '2024-01-19 13:09:29.239757', 'AGE_ALL', '런던 레코드', '런던 레코드', 90, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (177, '2024-01-19 13:09:30.531513', '2024-01-19 13:09:30.531513', 'AGE_12', '써니텐', '써니텐', 100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (178, '2024-01-19 13:09:31.907050', '2024-01-19 13:09:31.907050', 'AGE_19', '두들팝 [서울]', '두들팝 [서울]', 50, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (179, '2024-01-19 13:09:33.603834', '2024-01-19 13:09:33.603834', 'AGE_7', '스크루테이프의 편지', '스크루테이프의 편지', 80, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (180, '2024-01-19 13:09:35.037757', '2024-01-19 13:09:35.037757', 'AGE_ALL', '난타 [명동]', '난타 [명동]', 90, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (181, '2024-01-19 13:09:37.055567', '2024-01-19 13:09:37.055567', 'AGE_12', '알사탕 [서울숲]', '알사탕 [서울숲]', 60, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (182, '2024-01-19 13:09:38.525219', '2024-01-19 13:09:38.525219', 'AGE_12', '장수탕 선녀님 [서울숲]', '장수탕 선녀님 [서울숲]', 60,
        9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (183, '2024-01-19 13:09:39.763073', '2024-01-19 13:09:39.763073', 'AGE_ALL', '반짝반짝 인어공주', '반짝반짝 인어공주', 50, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (184, '2024-01-19 13:09:41.113761', '2024-01-19 13:09:41.113761', 'AGE_7', '프리즌 [H씨어터]', '프리즌 [H씨어터]', 100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (185, '2024-01-19 13:09:42.625390', '2024-01-19 13:09:42.625390', 'AGE_12', '세례요한', '세례요한', 100, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (186, '2024-01-19 13:09:44.220950', '2024-01-19 13:09:44.220950', 'AGE_19', '미래상상마술쇼', '미래상상마술쇼', 60, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (187, '2024-01-19 13:09:45.937917', '2024-01-19 13:09:45.937917', 'AGE_12', '라면에 파송송', '라면에 파송송', 95, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (188, '2024-01-19 13:09:47.456545', '2024-01-19 13:09:47.456545', 'AGE_ALL', '반짝반짝 라푼젤', '반짝반짝 라푼젤', 50, 9);
INSERT INTO ticket_auction.goods_info (id, created_at, modified_at, age_grade, description, name, running_time,
                                       goods_category_id)
VALUES (189, '2024-01-19 13:09:49.226373', '2024-01-19 13:09:49.226373', 'AGE_19', '아기돼지삼형제: 늑대숲 또옹돼지 원정대',
        '아기돼지삼형제: 늑대숲 또옹돼지 원정대', 50, 9);

# 공연 이미지 추가
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (1, '2024-01-19 12:49:22.993518', '2024-01-19 12:49:22.993518',
        'goods/thumbnail/1/2fa19954-2dd9-4522-9844-4f82f477bbbc', 'POSTER_IMG', 1);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (2, '2024-01-19 12:49:22.999517', '2024-01-19 12:49:22.999517',
        'goods/general/1/5d47ffbc-453e-4a17-8191-cb080abbf4d8', 'INFO_IMG', 1);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (3, '2024-01-19 12:49:24.279536', '2024-01-19 12:49:24.279536',
        'goods/thumbnail/2/69034f55-8a8c-4e07-b195-93d00e213c0a', 'POSTER_IMG', 2);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (4, '2024-01-19 12:49:24.282732', '2024-01-19 12:49:24.282732',
        'goods/general/2/1bbf4b9b-a034-468f-bc3a-d52e3ba6b151', 'INFO_IMG', 2);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (5, '2024-01-19 12:49:26.401213', '2024-01-19 12:49:26.401213',
        'goods/thumbnail/3/7f2020ac-1734-4ebd-b639-927621677144', 'POSTER_IMG', 3);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (6, '2024-01-19 12:49:26.403685', '2024-01-19 12:49:26.403685',
        'goods/general/3/3b2c5c03-a6b7-4d6c-9ecb-3aee4f4819e9', 'INFO_IMG', 3);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (7, '2024-01-19 12:49:26.907875', '2024-01-19 12:49:26.907875',
        'goods/thumbnail/4/bfba45b2-7713-44d2-8bfc-c11b9623ee49', 'POSTER_IMG', 4);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (8, '2024-01-19 12:49:26.910320', '2024-01-19 12:49:26.910320',
        'goods/general/4/52a4fe54-dd92-433e-aa7f-c5b8ca01bb8e', 'INFO_IMG', 4);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (9, '2024-01-19 12:49:28.048786', '2024-01-19 12:49:28.048786',
        'goods/thumbnail/5/07335e3b-f36f-446c-88b8-9e3324693839', 'POSTER_IMG', 5);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (10, '2024-01-19 12:49:28.050238', '2024-01-19 12:49:28.050238',
        'goods/general/5/d1fac273-71b4-41bc-94f2-38d27bf809c5', 'INFO_IMG', 5);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (11, '2024-01-19 12:49:28.439371', '2024-01-19 12:49:28.439371',
        'goods/thumbnail/6/dcfdb21e-d4b9-4bba-9e84-59a00890ebe6', 'POSTER_IMG', 6);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (12, '2024-01-19 12:49:28.441283', '2024-01-19 12:49:28.441283',
        'goods/general/6/ec50a2c3-425d-4fa9-80dd-986314797624', 'INFO_IMG', 6);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (13, '2024-01-19 12:49:29.565346', '2024-01-19 12:49:29.565346',
        'goods/thumbnail/7/4c2617f3-1a25-4afc-beb2-a708e7790a6c', 'POSTER_IMG', 7);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (14, '2024-01-19 12:49:29.569269', '2024-01-19 12:49:29.569269',
        'goods/general/7/c5d1e9e9-4a9d-40eb-9d96-eabcf09d381d', 'INFO_IMG', 7);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (15, '2024-01-19 12:49:31.029734', '2024-01-19 12:49:31.029734',
        'goods/thumbnail/8/e0c993c8-eb58-44ee-9fd5-eeae106c5b1c', 'POSTER_IMG', 8);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (16, '2024-01-19 12:49:31.032406', '2024-01-19 12:49:31.032406',
        'goods/general/8/4a216ba3-349e-4d9f-86f6-de19c4e051a6', 'INFO_IMG', 8);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (17, '2024-01-19 12:49:33.418119', '2024-01-19 12:49:33.418119',
        'goods/thumbnail/9/5540a592-80c9-4036-afa0-ff4d711efdbc', 'POSTER_IMG', 9);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (18, '2024-01-19 12:49:33.419212', '2024-01-19 12:49:33.419212',
        'goods/general/9/f8c5670c-a4fd-4645-bc0a-9f22d3248438', 'INFO_IMG', 9);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (19, '2024-01-19 12:49:33.419769', '2024-01-19 12:49:33.419769',
        'goods/general/9/0ea04e88-7167-48a4-80cf-5139799d70e6', 'INFO_IMG', 9);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (20, '2024-01-19 12:49:33.420295', '2024-01-19 12:49:33.420295',
        'goods/general/9/e60b8506-bbbb-48d9-ab45-9bb19d6b572f', 'INFO_IMG', 9);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (21, '2024-01-19 12:49:33.420748', '2024-01-19 12:49:33.420748',
        'goods/general/9/35c59737-2956-4762-bb62-b32a9e58a673', 'INFO_IMG', 9);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (22, '2024-01-19 12:49:35.093193', '2024-01-19 12:49:35.093193',
        'goods/thumbnail/10/0743e85d-609f-4a93-9ec9-1e191b9e32c3', 'POSTER_IMG', 10);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (23, '2024-01-19 12:49:35.095031', '2024-01-19 12:49:35.095031',
        'goods/general/10/07241d4d-61e5-4a5e-a9e6-edd004634851', 'INFO_IMG', 10);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (24, '2024-01-19 12:49:35.096430', '2024-01-19 12:49:35.096430',
        'goods/general/10/6f1f2011-5c18-4ad5-878c-a57fc1fd9255', 'INFO_IMG', 10);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (25, '2024-01-19 12:49:35.097013', '2024-01-19 12:49:35.097013',
        'goods/general/10/8fc4cbd3-5a62-450c-bffa-2a31060ac900', 'INFO_IMG', 10);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (26, '2024-01-19 12:49:35.097637', '2024-01-19 12:49:35.097637',
        'goods/general/10/6901dadc-c343-4635-996b-8cdf76617c70', 'INFO_IMG', 10);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (27, '2024-01-19 12:49:35.098178', '2024-01-19 12:49:35.098178',
        'goods/general/10/a2ba13c5-9570-40e9-9012-aec2fdfc6910', 'INFO_IMG', 10);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (28, '2024-01-19 12:49:36.326362', '2024-01-19 12:49:36.326362',
        'goods/thumbnail/11/5f857398-45c8-44e7-8b25-f2132c9ad068', 'POSTER_IMG', 11);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (29, '2024-01-19 12:49:36.327953', '2024-01-19 12:49:36.327953',
        'goods/general/11/e1333827-8d2f-4b3d-865b-82ce23c4e940', 'INFO_IMG', 11);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (30, '2024-01-19 12:49:36.329017', '2024-01-19 12:49:36.329017',
        'goods/general/11/9ddc5832-e1c4-45c3-89d8-4fc3d4fb4034', 'INFO_IMG', 11);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (31, '2024-01-19 12:49:37.170856', '2024-01-19 12:49:37.170856',
        'goods/thumbnail/12/0848eb46-f986-40bc-846e-15809b5db927', 'POSTER_IMG', 12);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (32, '2024-01-19 12:49:37.171870', '2024-01-19 12:49:37.171870',
        'goods/general/12/3f7310ce-5819-48a3-b63c-dc5e6f2bc040', 'INFO_IMG', 12);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (33, '2024-01-19 12:49:38.048302', '2024-01-19 12:49:38.048302',
        'goods/thumbnail/13/f628e3bf-d5c0-4e61-93a7-3482be7a7c55', 'POSTER_IMG', 13);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (34, '2024-01-19 12:49:38.049928', '2024-01-19 12:49:38.049928',
        'goods/general/13/e7d4d20c-485a-417c-86a2-a5653ddbb6f2', 'INFO_IMG', 13);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (35, '2024-01-19 12:49:39.274348', '2024-01-19 12:49:39.274348',
        'goods/thumbnail/14/f818fb3b-c130-430c-bf0f-b43e2a8b17bc', 'POSTER_IMG', 14);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (36, '2024-01-19 12:49:39.276777', '2024-01-19 12:49:39.276777',
        'goods/general/14/f520ebc8-d499-445c-b69c-4391847ff942', 'INFO_IMG', 14);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (37, '2024-01-19 12:49:39.278047', '2024-01-19 12:49:39.278047',
        'goods/general/14/4a626b7c-0172-44e3-834a-1f199016958f', 'INFO_IMG', 14);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (38, '2024-01-19 12:49:43.408294', '2024-01-19 12:49:43.408294',
        'goods/thumbnail/15/0ce64e16-e13f-4876-9d17-6be2239fe555', 'POSTER_IMG', 15);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (39, '2024-01-19 12:49:43.410271', '2024-01-19 12:49:43.410271',
        'goods/general/15/4b62c677-0992-45e3-b14a-f99ed499ed70', 'INFO_IMG', 15);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (40, '2024-01-19 12:49:43.880559', '2024-01-19 12:49:43.880559',
        'goods/thumbnail/16/7b846327-f663-4aa8-be2f-e8c65d893d89', 'POSTER_IMG', 16);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (41, '2024-01-19 12:49:43.882243', '2024-01-19 12:49:43.882243',
        'goods/general/16/45cf7e88-85f0-40d5-ac1d-6bea9650055e', 'INFO_IMG', 16);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (42, '2024-01-19 12:49:43.883719', '2024-01-19 12:49:43.883719',
        'goods/general/16/12956d28-ee55-43ad-84cb-68aba35e1f01', 'INFO_IMG', 16);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (43, '2024-01-19 12:49:44.322401', '2024-01-19 12:49:44.322401',
        'goods/thumbnail/17/827dd375-ec12-4615-93dc-a3d61680926d', 'POSTER_IMG', 17);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (44, '2024-01-19 12:49:44.324367', '2024-01-19 12:49:44.324367',
        'goods/general/17/8e11c618-80df-491b-9dea-76a636470b45', 'INFO_IMG', 17);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (45, '2024-01-19 12:49:44.325183', '2024-01-19 12:49:44.325183',
        'goods/general/17/453defd5-65b5-4c9c-a328-212ad27fa09f', 'INFO_IMG', 17);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (46, '2024-01-19 12:49:44.965456', '2024-01-19 12:49:44.965456',
        'goods/thumbnail/18/047a91ce-c43e-491c-9751-e85b07f65294', 'POSTER_IMG', 18);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (47, '2024-01-19 12:49:44.967363', '2024-01-19 12:49:44.967363',
        'goods/general/18/ed9fc5f8-7eb8-43c1-9893-6d61ea23440f', 'INFO_IMG', 18);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (48, '2024-01-19 12:49:46.317836', '2024-01-19 12:49:46.317836',
        'goods/thumbnail/19/1b86797b-834f-4525-969a-516c35698f0a', 'POSTER_IMG', 19);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (49, '2024-01-19 12:49:46.319305', '2024-01-19 12:49:46.319305',
        'goods/general/19/10da7142-3db6-4ff3-8828-e8c6a1e1c88d', 'INFO_IMG', 19);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (50, '2024-01-19 13:02:52.879466', '2024-01-19 13:02:52.879466',
        'goods/thumbnail/21/d6f521c4-6e99-425f-931a-c620981b1238', 'POSTER_IMG', 21);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (51, '2024-01-19 13:02:52.881502', '2024-01-19 13:02:52.881502',
        'goods/general/21/eb11c6c8-b6e0-4edd-ab4e-d6b64bd513e2', 'INFO_IMG', 21);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (52, '2024-01-19 13:02:52.882720', '2024-01-19 13:02:52.882720',
        'goods/general/21/c93abc1c-0094-4819-981b-61cff8d15aa8', 'INFO_IMG', 21);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (53, '2024-01-19 13:02:53.585146', '2024-01-19 13:02:53.585146',
        'goods/thumbnail/22/f8f9a7cb-39d1-4d87-8481-563e79d6599c', 'POSTER_IMG', 22);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (54, '2024-01-19 13:02:53.587194', '2024-01-19 13:02:53.587194',
        'goods/general/22/4fc769c6-f337-4e4a-93b0-d0f27dd966c9', 'INFO_IMG', 22);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (55, '2024-01-19 13:02:54.232599', '2024-01-19 13:02:54.232599',
        'goods/thumbnail/23/fba751ae-d590-45ff-9ed4-0365083a8b94', 'POSTER_IMG', 23);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (56, '2024-01-19 13:02:54.235031', '2024-01-19 13:02:54.235031',
        'goods/general/23/18aa3f64-5de5-4d48-afeb-7e5c49ab70d9', 'INFO_IMG', 23);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (57, '2024-01-19 13:02:55.504876', '2024-01-19 13:02:55.504876',
        'goods/thumbnail/24/1fa6942a-d057-4bad-aa7f-afd9db7f26b8', 'POSTER_IMG', 24);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (58, '2024-01-19 13:02:55.506510', '2024-01-19 13:02:55.506510',
        'goods/general/24/d386ecd8-2abe-4ce1-be3c-c2701f34823a', 'INFO_IMG', 24);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (59, '2024-01-19 13:02:55.945059', '2024-01-19 13:02:55.945059',
        'goods/thumbnail/25/00a607c3-f062-41bc-ac5b-ff023284dc7b', 'POSTER_IMG', 25);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (60, '2024-01-19 13:02:55.946774', '2024-01-19 13:02:55.946774',
        'goods/general/25/a83a3162-23b9-4840-81a1-768583ceb314', 'INFO_IMG', 25);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (61, '2024-01-19 13:02:57.327535', '2024-01-19 13:02:57.327535',
        'goods/thumbnail/26/fa466916-fdcb-43b4-89c3-6d3ffac5ead8', 'POSTER_IMG', 26);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (62, '2024-01-19 13:02:57.329762', '2024-01-19 13:02:57.329762',
        'goods/general/26/f33d06dd-178e-434d-a25f-797b16f250fe', 'INFO_IMG', 26);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (63, '2024-01-19 13:02:57.962523', '2024-01-19 13:02:57.962523',
        'goods/thumbnail/27/536e57e1-b5b8-456c-a1c4-3f778f521312', 'POSTER_IMG', 27);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (64, '2024-01-19 13:02:57.964216', '2024-01-19 13:02:57.964216',
        'goods/general/27/8b79a59d-8b0f-4f3c-97bf-5403a02b6858', 'INFO_IMG', 27);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (65, '2024-01-19 13:02:59.065025', '2024-01-19 13:02:59.065025',
        'goods/thumbnail/28/e9aad740-7f3f-4821-9921-456c19d1b78f', 'POSTER_IMG', 28);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (66, '2024-01-19 13:02:59.066482', '2024-01-19 13:02:59.066482',
        'goods/general/28/fa7364e3-8ff7-41bd-b77a-64dc632f2e41', 'INFO_IMG', 28);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (67, '2024-01-19 13:03:01.033837', '2024-01-19 13:03:01.033837',
        'goods/thumbnail/29/ad2568b2-19c7-477f-bd5f-1b36e1b26f06', 'POSTER_IMG', 29);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (68, '2024-01-19 13:03:01.035720', '2024-01-19 13:03:01.035720',
        'goods/general/29/07960ad8-1fea-438c-9a50-0a6cd8429b82', 'INFO_IMG', 29);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (69, '2024-01-19 13:03:01.037597', '2024-01-19 13:03:01.037597',
        'goods/general/29/d750de6f-0085-474a-b65b-2ba6b2bfa762', 'INFO_IMG', 29);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (70, '2024-01-19 13:03:01.492621', '2024-01-19 13:03:01.492621',
        'goods/thumbnail/30/c57e3fea-e432-4345-b278-53b76356ee11', 'POSTER_IMG', 30);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (71, '2024-01-19 13:03:01.494925', '2024-01-19 13:03:01.494925',
        'goods/general/30/9e6449e9-fb66-47f4-a1d5-25df373b9237', 'INFO_IMG', 30);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (72, '2024-01-19 13:03:02.100750', '2024-01-19 13:03:02.100750',
        'goods/thumbnail/31/74e4d7f1-ebb0-4d7f-8be0-53f5d92c1081', 'POSTER_IMG', 31);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (73, '2024-01-19 13:03:02.102380', '2024-01-19 13:03:02.102380',
        'goods/general/31/0133721e-7d4a-4f75-b1c2-425789a29424', 'INFO_IMG', 31);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (74, '2024-01-19 13:03:02.735959', '2024-01-19 13:03:02.735959',
        'goods/thumbnail/32/1ed5a64e-78f8-4611-a725-f0b4122f2854', 'POSTER_IMG', 32);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (75, '2024-01-19 13:03:02.737394', '2024-01-19 13:03:02.737394',
        'goods/general/32/07219a44-0bea-44b2-abfe-e7dd4adfe6f6', 'INFO_IMG', 32);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (76, '2024-01-19 13:03:03.352471', '2024-01-19 13:03:03.352471',
        'goods/thumbnail/33/9cc4c0ed-64bb-4380-a2f3-c3fd293ae460', 'POSTER_IMG', 33);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (77, '2024-01-19 13:03:03.354279', '2024-01-19 13:03:03.354279',
        'goods/general/33/a0a36684-ba04-4a91-ab72-52548c03f047', 'INFO_IMG', 33);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (78, '2024-01-19 13:03:03.700346', '2024-01-19 13:03:03.700346',
        'goods/thumbnail/34/45d91eca-b8dc-47cc-8f61-0514c686860f', 'POSTER_IMG', 34);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (79, '2024-01-19 13:03:03.701951', '2024-01-19 13:03:03.701951',
        'goods/general/34/b762031b-b409-4dd3-a42b-2abbbbe45916', 'INFO_IMG', 34);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (80, '2024-01-19 13:03:04.291573', '2024-01-19 13:03:04.291573',
        'goods/thumbnail/35/ac1e66ac-6dea-44df-964b-86ee6a8036b3', 'POSTER_IMG', 35);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (81, '2024-01-19 13:03:04.293092', '2024-01-19 13:03:04.293092',
        'goods/general/35/077eff19-6fb0-4347-8bdf-7cd59c449c55', 'INFO_IMG', 35);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (82, '2024-01-19 13:03:08.245835', '2024-01-19 13:03:08.245835',
        'goods/thumbnail/36/ea1171d4-1e8d-446f-b42e-51d835b8bcb3', 'POSTER_IMG', 36);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (83, '2024-01-19 13:03:08.249306', '2024-01-19 13:03:08.249306',
        'goods/general/36/0dea7624-4b56-41c9-bede-ef56bf6cee66', 'INFO_IMG', 36);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (84, '2024-01-19 13:03:36.140694', '2024-01-19 13:03:36.140694',
        'goods/thumbnail/37/e7696e78-e752-463c-81f4-1f5c50fe2c48', 'POSTER_IMG', 37);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (85, '2024-01-19 13:03:36.143119', '2024-01-19 13:03:36.143119',
        'goods/general/37/ced94b61-6897-4891-9973-0064882341a4', 'INFO_IMG', 37);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (86, '2024-01-19 13:03:37.989954', '2024-01-19 13:03:37.989954',
        'goods/thumbnail/38/25f6f799-527a-456d-a813-f41163a335fe', 'POSTER_IMG', 38);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (87, '2024-01-19 13:03:37.990932', '2024-01-19 13:03:37.990932',
        'goods/general/38/f28c1662-ffd7-42eb-95bd-cca8da86c6b8', 'INFO_IMG', 38);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (88, '2024-01-19 13:03:37.991442', '2024-01-19 13:03:37.991442',
        'goods/general/38/beeff28f-e45a-47b8-8923-19a243d0a129', 'INFO_IMG', 38);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (89, '2024-01-19 13:03:37.991929', '2024-01-19 13:03:37.991929',
        'goods/general/38/3a019a41-d873-4c50-8a79-666559f334db', 'INFO_IMG', 38);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (90, '2024-01-19 13:03:37.992410', '2024-01-19 13:03:37.992410',
        'goods/general/38/1d3c4fe2-4858-4449-a0ba-591ca3fed413', 'INFO_IMG', 38);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (91, '2024-01-19 13:03:39.398125', '2024-01-19 13:03:39.398125',
        'goods/thumbnail/39/d9a27127-272d-47b8-9770-6f66ac7fa6d3', 'POSTER_IMG', 39);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (92, '2024-01-19 13:03:39.399456', '2024-01-19 13:03:39.399456',
        'goods/general/39/d12f968a-1d20-4494-a485-193bd407642e', 'INFO_IMG', 39);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (93, '2024-01-19 13:03:39.400186', '2024-01-19 13:03:39.400186',
        'goods/general/39/d6bb8682-979f-499f-a3d1-4bebbc8fa662', 'INFO_IMG', 39);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (94, '2024-01-19 13:03:40.634822', '2024-01-19 13:03:40.634822',
        'goods/thumbnail/40/adb09ac3-ba55-4111-b0cc-0913f8c953b4', 'POSTER_IMG', 40);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (95, '2024-01-19 13:03:40.636074', '2024-01-19 13:03:40.636074',
        'goods/general/40/d8dcd338-21fb-40b0-8c41-7c5f90072484', 'INFO_IMG', 40);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (96, '2024-01-19 13:03:40.636875', '2024-01-19 13:03:40.636875',
        'goods/general/40/eda22eb4-9d79-4db4-9015-6c58a829a555', 'INFO_IMG', 40);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (97, '2024-01-19 13:03:41.466281', '2024-01-19 13:03:41.466281',
        'goods/thumbnail/41/72e156c8-4473-4d1b-8446-1902fb80b9a2', 'POSTER_IMG', 41);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (98, '2024-01-19 13:03:41.467743', '2024-01-19 13:03:41.467743',
        'goods/general/41/6080dfbe-c3d8-4f80-a2d9-002248d86572', 'INFO_IMG', 41);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (99, '2024-01-19 13:03:42.870503', '2024-01-19 13:03:42.870503',
        'goods/thumbnail/42/965e65b6-e37d-41b9-8683-f3cdce885646', 'POSTER_IMG', 42);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (100, '2024-01-19 13:03:42.871444', '2024-01-19 13:03:42.871444',
        'goods/general/42/5ed376ca-f844-4dd4-ac15-c799641ab204', 'INFO_IMG', 42);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (101, '2024-01-19 13:03:42.871857', '2024-01-19 13:03:42.871857',
        'goods/general/42/bc770d78-ae7b-4953-9612-2fc7b9c39ee1', 'INFO_IMG', 42);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (102, '2024-01-19 13:03:55.039042', '2024-01-19 13:03:55.039042',
        'goods/thumbnail/43/1b462d73-6d82-48ae-96c6-17f753927070', 'POSTER_IMG', 43);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (103, '2024-01-19 13:03:55.040290', '2024-01-19 13:03:55.040290',
        'goods/general/43/7349ecd1-5eb4-497c-8bd4-27e017019f79', 'INFO_IMG', 43);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (104, '2024-01-19 13:03:55.641178', '2024-01-19 13:03:55.641178',
        'goods/thumbnail/44/37345965-fb46-4061-80c7-4763341e89bd', 'POSTER_IMG', 44);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (105, '2024-01-19 13:03:55.642546', '2024-01-19 13:03:55.642546',
        'goods/general/44/c42fb4f9-e3c4-4332-a718-b5bd627d4a10', 'INFO_IMG', 44);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (106, '2024-01-19 13:03:56.796425', '2024-01-19 13:03:56.796425',
        'goods/thumbnail/45/b8033471-14c3-4ffb-b2a8-5c4bb12bfb7d', 'POSTER_IMG', 45);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (107, '2024-01-19 13:03:56.797828', '2024-01-19 13:03:56.797828',
        'goods/general/45/1b3f911f-1e5e-4bec-8fef-6be9f3aa1868', 'INFO_IMG', 45);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (108, '2024-01-19 13:03:56.798578', '2024-01-19 13:03:56.798578',
        'goods/general/45/8dc1314b-d598-4df7-9b92-82ef1a301f32', 'INFO_IMG', 45);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (109, '2024-01-19 13:03:57.878587', '2024-01-19 13:03:57.878587',
        'goods/thumbnail/46/31818f42-3aed-4341-9d1d-51d4c8f31ec2', 'POSTER_IMG', 46);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (110, '2024-01-19 13:03:57.879783', '2024-01-19 13:03:57.879783',
        'goods/general/46/b342bf62-cd20-4da8-b71d-d41fff86bd18', 'INFO_IMG', 46);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (111, '2024-01-19 13:03:58.506725', '2024-01-19 13:03:58.506725',
        'goods/thumbnail/47/7bb8880b-c77c-4e92-a534-538df7e2294d', 'POSTER_IMG', 47);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (112, '2024-01-19 13:03:58.508701', '2024-01-19 13:03:58.508701',
        'goods/general/47/ad8b8744-2e38-480e-ae36-41867a9d4fc9', 'INFO_IMG', 47);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (113, '2024-01-19 13:03:58.510063', '2024-01-19 13:03:58.510063',
        'goods/general/47/22e1dc06-5c57-48e7-a9fa-11fccb4bb298', 'INFO_IMG', 47);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (114, '2024-01-19 13:04:00.074989', '2024-01-19 13:04:00.074989',
        'goods/thumbnail/48/263ede59-bfe6-4c4c-bf49-b2881d23d45d', 'POSTER_IMG', 48);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (115, '2024-01-19 13:04:00.076124', '2024-01-19 13:04:00.076124',
        'goods/general/48/53e69e1b-3a93-4936-8b7d-0927834f3b2b', 'INFO_IMG', 48);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (116, '2024-01-19 13:04:20.841147', '2024-01-19 13:04:20.841147',
        'goods/thumbnail/49/3aa9e4ce-316e-4335-893f-e9af38898235', 'POSTER_IMG', 49);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (117, '2024-01-19 13:04:20.843219', '2024-01-19 13:04:20.843219',
        'goods/general/49/7d5d947f-3bba-4228-a440-49caac047b28', 'INFO_IMG', 49);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (118, '2024-01-19 13:04:22.602788', '2024-01-19 13:04:22.602788',
        'goods/thumbnail/50/132ea5c7-cc54-49d7-b0e0-abb8cc96b795', 'POSTER_IMG', 50);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (119, '2024-01-19 13:04:22.605686', '2024-01-19 13:04:22.605686',
        'goods/general/50/9f3608da-b9d4-4153-b2f9-99a7bfc8efb7', 'INFO_IMG', 50);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (120, '2024-01-19 13:04:22.607303', '2024-01-19 13:04:22.607303',
        'goods/general/50/11cb8eed-cabf-4e6c-acdd-031a8b53511d', 'INFO_IMG', 50);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (121, '2024-01-19 13:04:22.608302', '2024-01-19 13:04:22.608302',
        'goods/general/50/079f3edb-9766-4fc5-9fa2-7f851b4dd5b1', 'INFO_IMG', 50);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (122, '2024-01-19 13:04:22.609985', '2024-01-19 13:04:22.609985',
        'goods/general/50/26f790ee-84cd-4d63-a329-631123ecbc9f', 'INFO_IMG', 50);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (123, '2024-01-19 13:04:22.612504', '2024-01-19 13:04:22.612504',
        'goods/general/50/247298dd-4aae-4a3b-adc3-70b8a7fd75af', 'INFO_IMG', 50);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (124, '2024-01-19 13:04:23.144771', '2024-01-19 13:04:23.144771',
        'goods/thumbnail/51/7cf83bc6-f530-4286-b54a-1f70cc4e6428', 'POSTER_IMG', 51);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (125, '2024-01-19 13:04:23.146429', '2024-01-19 13:04:23.146429',
        'goods/general/51/cab1f48a-0784-4bca-b87e-d176207707e9', 'INFO_IMG', 51);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (126, '2024-01-19 13:04:23.651651', '2024-01-19 13:04:23.651651',
        'goods/thumbnail/52/d2c09e92-caee-468e-a7d9-36010ce6e6d2', 'POSTER_IMG', 52);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (127, '2024-01-19 13:04:23.653793', '2024-01-19 13:04:23.653793',
        'goods/general/52/66f5f7f7-729e-46d9-92e7-3f60f61ac9f1', 'INFO_IMG', 52);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (128, '2024-01-19 13:04:24.127767', '2024-01-19 13:04:24.127767',
        'goods/thumbnail/53/416b005c-4a02-441a-82be-b1934fc5a6a7', 'POSTER_IMG', 53);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (129, '2024-01-19 13:04:24.129924', '2024-01-19 13:04:24.129924',
        'goods/general/53/dc4e9c5a-78ef-4ab6-b62e-4e123e649288', 'INFO_IMG', 53);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (130, '2024-01-19 13:04:24.510529', '2024-01-19 13:04:24.510529',
        'goods/thumbnail/54/fb945378-b941-4fae-947f-3bacbf2d459b', 'POSTER_IMG', 54);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (131, '2024-01-19 13:04:24.511467', '2024-01-19 13:04:24.511467',
        'goods/general/54/fd74d146-03bf-49f1-81cd-44c9e741e856', 'INFO_IMG', 54);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (132, '2024-01-19 13:04:24.987604', '2024-01-19 13:04:24.987604',
        'goods/thumbnail/55/90e240e0-5d51-48ac-aa2f-ae0b78134af8', 'POSTER_IMG', 55);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (133, '2024-01-19 13:04:24.989296', '2024-01-19 13:04:24.989296',
        'goods/general/55/6b9b508d-5bca-4316-a2b2-4db5b9d3a40c', 'INFO_IMG', 55);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (134, '2024-01-19 13:04:25.630953', '2024-01-19 13:04:25.630953',
        'goods/thumbnail/56/a90f943c-2a3b-48a6-8a1a-1b2e67a4309f', 'POSTER_IMG', 56);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (135, '2024-01-19 13:04:25.632800', '2024-01-19 13:04:25.632800',
        'goods/general/56/6292f8cc-f72a-4e7c-a8db-b8e53e332e04', 'INFO_IMG', 56);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (136, '2024-01-19 13:04:29.641209', '2024-01-19 13:04:29.641209',
        'goods/thumbnail/57/2715a1d3-6c17-45f3-a0dc-f7f806d88120', 'POSTER_IMG', 57);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (137, '2024-01-19 13:04:29.642830', '2024-01-19 13:04:29.642830',
        'goods/general/57/a7720006-3888-443b-ad5f-2f22fecac2c5', 'INFO_IMG', 57);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (138, '2024-01-19 13:04:32.698737', '2024-01-19 13:04:32.698737',
        'goods/thumbnail/58/11667876-16c5-4bc0-94e3-3ee44281a774', 'POSTER_IMG', 58);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (139, '2024-01-19 13:04:32.700505', '2024-01-19 13:04:32.700505',
        'goods/general/58/c7a82f19-886b-4e75-9134-867aa25dc6c1', 'INFO_IMG', 58);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (140, '2024-01-19 13:04:32.701247', '2024-01-19 13:04:32.701247',
        'goods/general/58/ea8bb18b-63ad-4b2b-9abd-1962defd5f07', 'INFO_IMG', 58);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (141, '2024-01-19 13:04:32.701885', '2024-01-19 13:04:32.701885',
        'goods/general/58/415cfe8c-045b-4294-824f-142af067a924', 'INFO_IMG', 58);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (142, '2024-01-19 13:04:46.053944', '2024-01-19 13:04:46.053944',
        'goods/thumbnail/59/fc35d9b9-b0d4-48f1-92bb-330fd84b6b32', 'POSTER_IMG', 59);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (143, '2024-01-19 13:04:46.056583', '2024-01-19 13:04:46.056583',
        'goods/general/59/cbb4b6b8-67a4-43ff-88cd-46c50c88d671', 'INFO_IMG', 59);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (144, '2024-01-19 13:04:46.059851', '2024-01-19 13:04:46.059851',
        'goods/general/59/d2b77b10-16e0-44db-b898-9a4c61ea9113', 'INFO_IMG', 59);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (145, '2024-01-19 13:04:48.046453', '2024-01-19 13:04:48.046453',
        'goods/thumbnail/60/3f21401e-a436-472f-85bb-02c8a997f720', 'POSTER_IMG', 60);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (146, '2024-01-19 13:04:48.048489', '2024-01-19 13:04:48.048489',
        'goods/general/60/e864fc2c-677c-4409-b87d-92f532c16071', 'INFO_IMG', 60);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (147, '2024-01-19 13:04:51.218375', '2024-01-19 13:04:51.218375',
        'goods/thumbnail/61/4ab16786-c4e4-4b76-961f-a7d0be39e763', 'POSTER_IMG', 61);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (148, '2024-01-19 13:04:51.219550', '2024-01-19 13:04:51.219550',
        'goods/general/61/58ce342c-1c6e-4e51-945f-2f4625c8e821', 'INFO_IMG', 61);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (149, '2024-01-19 13:04:51.839807', '2024-01-19 13:04:51.839807',
        'goods/thumbnail/62/3dcf1ea3-8efc-4c98-9660-20eab6c87078', 'POSTER_IMG', 62);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (150, '2024-01-19 13:04:51.841632', '2024-01-19 13:04:51.841632',
        'goods/general/62/a921abfd-0f90-440b-8838-7918dc99b7ba', 'INFO_IMG', 62);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (151, '2024-01-19 13:04:53.550689', '2024-01-19 13:04:53.550689',
        'goods/thumbnail/63/d00c2d23-ec50-406a-8965-4da99ddddb67', 'POSTER_IMG', 63);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (152, '2024-01-19 13:04:53.552098', '2024-01-19 13:04:53.552098',
        'goods/general/63/2fdc8c08-23d7-41a9-9abc-c280cb6d7c32', 'INFO_IMG', 63);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (153, '2024-01-19 13:04:55.430454', '2024-01-19 13:04:55.430454',
        'goods/thumbnail/64/8a5ced10-d489-4252-a49d-b446f13894e8', 'POSTER_IMG', 64);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (154, '2024-01-19 13:04:55.432244', '2024-01-19 13:04:55.432244',
        'goods/general/64/231e2c20-efa0-4291-b3f5-6ac72517d681', 'INFO_IMG', 64);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (155, '2024-01-19 13:04:56.446067', '2024-01-19 13:04:56.446067',
        'goods/thumbnail/65/6b80289a-d802-40e9-82a5-cdbac81d77aa', 'POSTER_IMG', 65);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (156, '2024-01-19 13:04:56.448037', '2024-01-19 13:04:56.448037',
        'goods/general/65/e2459d4e-d391-485b-837e-4c4c52fd9652', 'INFO_IMG', 65);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (157, '2024-01-19 13:04:58.414954', '2024-01-19 13:04:58.414954',
        'goods/thumbnail/66/baa8e039-d4f6-451c-bbd9-e069d67605b2', 'POSTER_IMG', 66);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (158, '2024-01-19 13:04:58.417240', '2024-01-19 13:04:58.417240',
        'goods/general/66/885873c2-2200-4983-9b8f-9490685355b1', 'INFO_IMG', 66);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (159, '2024-01-19 13:05:07.216738', '2024-01-19 13:05:07.216738',
        'goods/thumbnail/67/62ddcad0-a418-4b59-ae39-1faeac6b55db', 'POSTER_IMG', 67);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (160, '2024-01-19 13:05:07.217579', '2024-01-19 13:05:07.217579',
        'goods/general/67/d93ece5f-3271-492a-a8ec-517fa3107dac', 'INFO_IMG', 67);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (161, '2024-01-19 13:05:07.217943', '2024-01-19 13:05:07.217943',
        'goods/general/67/628557d0-bdef-4a61-933c-cb3a13594b3f', 'INFO_IMG', 67);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (162, '2024-01-19 13:05:07.218272', '2024-01-19 13:05:07.218272',
        'goods/general/67/eae8998e-2f0e-4cb9-98cc-cb0adde43709', 'INFO_IMG', 67);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (163, '2024-01-19 13:05:14.571590', '2024-01-19 13:05:14.571590',
        'goods/thumbnail/68/3299265e-e2b3-4a41-abb1-7b0dbc95483f', 'POSTER_IMG', 68);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (164, '2024-01-19 13:05:14.573578', '2024-01-19 13:05:14.573578',
        'goods/general/68/bfa49e35-3ea0-4954-8b7c-da64f099c9c1', 'INFO_IMG', 68);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (165, '2024-01-19 13:05:15.138695', '2024-01-19 13:05:15.138695',
        'goods/thumbnail/69/68cd4ffb-bb52-4ecc-8ff6-bad2b8cba733', 'POSTER_IMG', 69);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (166, '2024-01-19 13:05:15.140792', '2024-01-19 13:05:15.140792',
        'goods/general/69/c6b36ceb-cffb-421d-9e94-c6e80054682e', 'INFO_IMG', 69);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (167, '2024-01-19 13:05:17.244095', '2024-01-19 13:05:17.244095',
        'goods/thumbnail/70/b5020ddb-ff0a-49df-9c66-16a3c33da49b', 'POSTER_IMG', 70);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (168, '2024-01-19 13:05:17.247847', '2024-01-19 13:05:17.247847',
        'goods/general/70/2c3d0c90-15bd-4777-9a31-e0d7a40262a3', 'INFO_IMG', 70);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (169, '2024-01-19 13:05:19.722668', '2024-01-19 13:05:19.722668',
        'goods/thumbnail/71/79084901-55c9-482b-a92b-f70ebf9e6cd9', 'POSTER_IMG', 71);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (170, '2024-01-19 13:05:19.724547', '2024-01-19 13:05:19.724547',
        'goods/general/71/01cface1-cba7-4b60-b976-bfa224001789', 'INFO_IMG', 71);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (171, '2024-01-19 13:05:20.251416', '2024-01-19 13:05:20.251416',
        'goods/thumbnail/72/1ad40784-0a82-4b9c-b6dd-10fa86264c7e', 'POSTER_IMG', 72);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (172, '2024-01-19 13:05:20.252702', '2024-01-19 13:05:20.252702',
        'goods/general/72/69f53a8c-a2f8-4900-bd8e-51d902c342c3', 'INFO_IMG', 72);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (173, '2024-01-19 13:05:21.844954', '2024-01-19 13:05:21.844954',
        'goods/thumbnail/73/fed3d35c-bdf8-49a7-9621-c736b4f946ac', 'POSTER_IMG', 73);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (174, '2024-01-19 13:05:21.847201', '2024-01-19 13:05:21.847201',
        'goods/general/73/51d42e6b-3d66-4754-a3ba-d0091fea3261', 'INFO_IMG', 73);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (175, '2024-01-19 13:05:22.366714', '2024-01-19 13:05:22.366714',
        'goods/thumbnail/74/2d36b4f7-5554-439a-9e98-7726e43088ba', 'POSTER_IMG', 74);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (176, '2024-01-19 13:05:22.369106', '2024-01-19 13:05:22.369106',
        'goods/general/74/db19f7b2-c0fd-459e-a51d-8f73f756ebc2', 'INFO_IMG', 74);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (177, '2024-01-19 13:05:23.819158', '2024-01-19 13:05:23.819158',
        'goods/thumbnail/75/0a4dd48a-0384-4347-9e4a-82ac60c7f4d8', 'POSTER_IMG', 75);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (178, '2024-01-19 13:05:23.820327', '2024-01-19 13:05:23.820327',
        'goods/general/75/caeffdb0-7f68-4252-aa28-d9af26569e43', 'INFO_IMG', 75);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (179, '2024-01-19 13:05:23.821114', '2024-01-19 13:05:23.821114',
        'goods/general/75/4213f87e-fa63-4d1e-a806-1c648ef53a26', 'INFO_IMG', 75);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (180, '2024-01-19 13:05:23.822135', '2024-01-19 13:05:23.822135',
        'goods/general/75/3f7da60e-f4cf-4b8f-a4d7-d9f33917d42a', 'INFO_IMG', 75);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (181, '2024-01-19 13:05:23.823786', '2024-01-19 13:05:23.823786',
        'goods/general/75/1be44c05-386e-4d2f-8ee0-c7331762184f', 'INFO_IMG', 75);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (182, '2024-01-19 13:05:38.002337', '2024-01-19 13:05:38.002337',
        'goods/thumbnail/76/d8b48623-c3cc-41a5-876c-a0824f1ab56e', 'POSTER_IMG', 76);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (183, '2024-01-19 13:05:38.003793', '2024-01-19 13:05:38.003793',
        'goods/general/76/5d4e51da-89cf-4ea7-a0f5-c34cdac052bf', 'INFO_IMG', 76);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (184, '2024-01-19 13:05:39.602702', '2024-01-19 13:05:39.602702',
        'goods/thumbnail/77/1f202e3f-edf2-4d99-85ce-dbf824aa4b96', 'POSTER_IMG', 77);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (185, '2024-01-19 13:05:39.604656', '2024-01-19 13:05:39.604656',
        'goods/general/77/91fcfeb9-f4de-4ede-af36-73ec6ca9cc22', 'INFO_IMG', 77);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (186, '2024-01-19 13:05:39.606007', '2024-01-19 13:05:39.606007',
        'goods/general/77/51137f45-8c4b-4c65-9764-3ab6cfed78f3', 'INFO_IMG', 77);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (187, '2024-01-19 13:05:39.607352', '2024-01-19 13:05:39.607352',
        'goods/general/77/4aa1010f-5121-48ae-89be-4e12474511ec', 'INFO_IMG', 77);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (188, '2024-01-19 13:05:39.608498', '2024-01-19 13:05:39.608498',
        'goods/general/77/94a46a7b-78fe-4696-8cd5-393261eb0a27', 'INFO_IMG', 77);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (189, '2024-01-19 13:05:39.609649', '2024-01-19 13:05:39.609649',
        'goods/general/77/c8e3b607-2a32-4379-ba30-e7e369cf26a5', 'INFO_IMG', 77);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (190, '2024-01-19 13:06:06.238259', '2024-01-19 13:06:06.238259',
        'goods/thumbnail/78/5f2139ad-a3ba-4dc5-ac9c-0a790ba1af2c', 'POSTER_IMG', 78);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (191, '2024-01-19 13:06:06.240020', '2024-01-19 13:06:06.240020',
        'goods/general/78/323712f4-d481-4179-a9e5-f1932c3ff648', 'INFO_IMG', 78);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (192, '2024-01-19 13:06:07.384402', '2024-01-19 13:06:07.384402',
        'goods/thumbnail/79/ba1dca2e-02e0-4ed1-8551-709088bbeaa0', 'POSTER_IMG', 79);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (193, '2024-01-19 13:06:07.387255', '2024-01-19 13:06:07.387255',
        'goods/general/79/2ed76291-44dc-4479-9118-e9d036f4a149', 'INFO_IMG', 79);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (194, '2024-01-19 13:06:07.388241', '2024-01-19 13:06:07.388241',
        'goods/general/79/8f99357f-0e86-4a62-8db4-4358b49ca58d', 'INFO_IMG', 79);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (195, '2024-01-19 13:06:07.388871', '2024-01-19 13:06:07.388871',
        'goods/general/79/d5f9c576-5d40-438e-b322-10ab9232ef65', 'INFO_IMG', 79);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (196, '2024-01-19 13:06:07.389609', '2024-01-19 13:06:07.389609',
        'goods/general/79/6a2286f1-395f-4773-8ede-ba5126e0e6d3', 'INFO_IMG', 79);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (197, '2024-01-19 13:06:09.392614', '2024-01-19 13:06:09.392614',
        'goods/thumbnail/80/eb3678cf-e8c9-411f-81b0-b341f60f72bb', 'POSTER_IMG', 80);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (198, '2024-01-19 13:06:09.394532', '2024-01-19 13:06:09.394532',
        'goods/general/80/5bfba916-82c2-4179-8eaf-613f0c73ff82', 'INFO_IMG', 80);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (199, '2024-01-19 13:06:10.109176', '2024-01-19 13:06:10.109176',
        'goods/thumbnail/81/9f44b06b-e5aa-440d-bdb7-69fe61a74e45', 'POSTER_IMG', 81);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (200, '2024-01-19 13:06:10.109830', '2024-01-19 13:06:10.109830',
        'goods/general/81/ddde285b-08b0-4e97-a645-d123e3f6e2e0', 'INFO_IMG', 81);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (201, '2024-01-19 13:06:10.556529', '2024-01-19 13:06:10.556529',
        'goods/thumbnail/82/15d79929-0b87-425a-82cd-40f91243988c', 'POSTER_IMG', 82);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (202, '2024-01-19 13:06:10.557723', '2024-01-19 13:06:10.557723',
        'goods/general/82/c3580647-4208-4606-b745-7808fad51525', 'INFO_IMG', 82);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (203, '2024-01-19 13:06:11.985128', '2024-01-19 13:06:11.985128',
        'goods/thumbnail/83/2d332a79-51a3-42e6-969c-25f71c80c46d', 'POSTER_IMG', 83);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (204, '2024-01-19 13:06:11.987523', '2024-01-19 13:06:11.987523',
        'goods/general/83/79b2adb9-c2f9-4d54-a5f0-1d2c1c8176d5', 'INFO_IMG', 83);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (205, '2024-01-19 13:06:11.988522', '2024-01-19 13:06:11.988522',
        'goods/general/83/a8e85385-adfc-4fde-ae87-9ce729b33ed4', 'INFO_IMG', 83);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (206, '2024-01-19 13:06:15.291873', '2024-01-19 13:06:15.291873',
        'goods/thumbnail/84/ab49ecd2-e9b3-4bee-852f-1f1431afda96', 'POSTER_IMG', 84);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (207, '2024-01-19 13:06:15.294255', '2024-01-19 13:06:15.294255',
        'goods/general/84/741a90c6-5f64-4408-a0a3-370c22a0439f', 'INFO_IMG', 84);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (208, '2024-01-19 13:06:19.549861', '2024-01-19 13:06:19.549861',
        'goods/thumbnail/85/ec666bd0-248a-4f2b-bce6-6cbda3db0046', 'POSTER_IMG', 85);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (209, '2024-01-19 13:06:19.551865', '2024-01-19 13:06:19.551865',
        'goods/general/85/f8375646-c13a-4e53-8242-ac1612979aba', 'INFO_IMG', 85);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (210, '2024-01-19 13:06:20.370133', '2024-01-19 13:06:20.370133',
        'goods/thumbnail/86/2756de4c-3193-44a3-a929-169e2ad2e2cf', 'POSTER_IMG', 86);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (211, '2024-01-19 13:06:20.371227', '2024-01-19 13:06:20.371227',
        'goods/general/86/76403e22-1a87-4764-97e9-98893ef57586', 'INFO_IMG', 86);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (212, '2024-01-19 13:06:23.687686', '2024-01-19 13:06:23.687686',
        'goods/thumbnail/87/93983a21-f96e-4dd4-801d-cca615ba960a', 'POSTER_IMG', 87);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (213, '2024-01-19 13:06:23.689022', '2024-01-19 13:06:23.689022',
        'goods/general/87/eaeb3bf0-ee04-45e7-9230-608b940fdd8a', 'INFO_IMG', 87);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (214, '2024-01-19 13:06:23.690709', '2024-01-19 13:06:23.690709',
        'goods/general/87/18047905-badd-4893-92c5-a2fda1bc63bc', 'INFO_IMG', 87);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (215, '2024-01-19 13:06:24.708193', '2024-01-19 13:06:24.708193',
        'goods/thumbnail/88/f6323160-1c38-489b-a78a-aa906db9455c', 'POSTER_IMG', 88);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (216, '2024-01-19 13:06:24.709948', '2024-01-19 13:06:24.709948',
        'goods/general/88/aef6162b-5c3b-426e-83a3-549c22a8395b', 'INFO_IMG', 88);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (217, '2024-01-19 13:06:25.435243', '2024-01-19 13:06:25.435243',
        'goods/thumbnail/89/ee7c9bf3-b5d7-42be-a258-5bb2f08c90af', 'POSTER_IMG', 89);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (218, '2024-01-19 13:06:25.436541', '2024-01-19 13:06:25.436541',
        'goods/general/89/7258bb6f-73cd-4be1-9c9e-7910a356f402', 'INFO_IMG', 89);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (219, '2024-01-19 13:06:26.207969', '2024-01-19 13:06:26.207969',
        'goods/thumbnail/90/2efb8095-cb10-46b7-b407-5c2277d47e1a', 'POSTER_IMG', 90);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (220, '2024-01-19 13:06:26.210539', '2024-01-19 13:06:26.210539',
        'goods/general/90/6f4e5147-80e6-4914-a424-dd9efc463fa6', 'INFO_IMG', 90);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (221, '2024-01-19 13:06:30.484428', '2024-01-19 13:06:30.484428',
        'goods/thumbnail/91/0cb30146-b142-4ac5-a054-49793d4e6c07', 'POSTER_IMG', 91);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (222, '2024-01-19 13:06:30.485831', '2024-01-19 13:06:30.485831',
        'goods/general/91/1327ea58-b2f7-4ad9-8f6c-6740a3135c15', 'INFO_IMG', 91);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (223, '2024-01-19 13:06:30.486590', '2024-01-19 13:06:30.486590',
        'goods/general/91/54c04b9e-72f0-4c49-9ae9-7650348fa9d3', 'INFO_IMG', 91);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (224, '2024-01-19 13:06:36.258129', '2024-01-19 13:06:36.258129',
        'goods/thumbnail/92/7462d9bd-c0d9-4bd5-814e-1c4e37b21872', 'POSTER_IMG', 92);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (225, '2024-01-19 13:06:36.260534', '2024-01-19 13:06:36.260534',
        'goods/general/92/1828e58f-0540-4ef0-a277-370b030564c2', 'INFO_IMG', 92);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (226, '2024-01-19 13:06:36.261233', '2024-01-19 13:06:36.261233',
        'goods/general/92/518303a6-d9ea-428a-8174-b3af9ac7cf18', 'INFO_IMG', 92);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (227, '2024-01-19 13:06:36.976165', '2024-01-19 13:06:36.976165',
        'goods/thumbnail/93/f19f95b2-8f9d-4fb4-9e12-441c910438d2', 'POSTER_IMG', 93);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (228, '2024-01-19 13:06:36.977486', '2024-01-19 13:06:36.977486',
        'goods/general/93/c5c7e19b-1900-4366-91ec-5f39df038a58', 'INFO_IMG', 93);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (229, '2024-01-19 13:06:37.562650', '2024-01-19 13:06:37.562650',
        'goods/thumbnail/94/f4339335-3a75-453a-98e5-ab6a36efaff3', 'POSTER_IMG', 94);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (230, '2024-01-19 13:06:37.563698', '2024-01-19 13:06:37.563698',
        'goods/general/94/30274335-0a43-4492-9c98-2ee229465c94', 'INFO_IMG', 94);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (231, '2024-01-19 13:06:46.315570', '2024-01-19 13:06:46.315570',
        'goods/thumbnail/95/cd17c71c-1c6c-4b48-a887-492f6f4e6d79', 'POSTER_IMG', 95);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (232, '2024-01-19 13:06:46.317818', '2024-01-19 13:06:46.317818',
        'goods/general/95/97c396c6-9082-40e1-b9d0-daf3e3c42890', 'INFO_IMG', 95);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (233, '2024-01-19 13:06:46.994945', '2024-01-19 13:06:46.994945',
        'goods/thumbnail/96/80b19a52-4810-47f3-a9ca-c0684671cea5', 'POSTER_IMG', 96);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (234, '2024-01-19 13:06:46.997445', '2024-01-19 13:06:46.997445',
        'goods/general/96/fdaa79a8-3db9-4b25-8f5c-e6a9f63121f4', 'INFO_IMG', 96);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (235, '2024-01-19 13:06:53.649615', '2024-01-19 13:06:53.649615',
        'goods/thumbnail/97/18faf901-4d6c-4ad1-894f-6fbe15179836', 'POSTER_IMG', 97);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (236, '2024-01-19 13:06:53.651771', '2024-01-19 13:06:53.651771',
        'goods/general/97/ffc57e09-9be1-4196-baaa-a65c65aa4675', 'INFO_IMG', 97);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (237, '2024-01-19 13:06:55.687894', '2024-01-19 13:06:55.687894',
        'goods/thumbnail/98/9f2c1cca-7f39-4748-ab82-a116a7a33d14', 'POSTER_IMG', 98);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (238, '2024-01-19 13:06:55.689115', '2024-01-19 13:06:55.689115',
        'goods/general/98/5f0a7762-7660-43fb-ac27-87ca2673a405', 'INFO_IMG', 98);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (239, '2024-01-19 13:07:01.030391', '2024-01-19 13:07:01.030391',
        'goods/thumbnail/99/16d21b56-f474-4ab0-941a-c74f7708135f', 'POSTER_IMG', 99);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (240, '2024-01-19 13:07:01.032937', '2024-01-19 13:07:01.032937',
        'goods/general/99/f787fb27-745a-40d7-aff9-1d2f5c146891', 'INFO_IMG', 99);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (241, '2024-01-19 13:07:01.035496', '2024-01-19 13:07:01.035496',
        'goods/general/99/7de846c0-1d5c-44cd-99b7-d571caf92ce7', 'INFO_IMG', 99);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (242, '2024-01-19 13:07:01.037937', '2024-01-19 13:07:01.037937',
        'goods/general/99/71cad4b3-4413-4b87-ba14-86b198d71f2b', 'INFO_IMG', 99);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (243, '2024-01-19 13:07:01.039897', '2024-01-19 13:07:01.039897',
        'goods/general/99/47802051-e1b5-46da-a61e-258291d388f4', 'INFO_IMG', 99);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (244, '2024-01-19 13:07:02.873752', '2024-01-19 13:07:02.873752',
        'goods/thumbnail/100/fcb906b6-2d69-4985-88c3-1e3d4edc0037', 'POSTER_IMG', 100);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (245, '2024-01-19 13:07:02.875914', '2024-01-19 13:07:02.875914',
        'goods/general/100/08b26cc8-7945-4c1f-bba2-b02e44f3bdb7', 'INFO_IMG', 100);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (246, '2024-01-19 13:07:03.475840', '2024-01-19 13:07:03.475840',
        'goods/thumbnail/101/ec5f74da-34c7-4a09-9555-88622d1de6de', 'POSTER_IMG', 101);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (247, '2024-01-19 13:07:03.476863', '2024-01-19 13:07:03.476863',
        'goods/general/101/786cfeaf-c8d2-48b8-a941-b063b3dfcca3', 'INFO_IMG', 101);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (248, '2024-01-19 13:07:07.442596', '2024-01-19 13:07:07.442596',
        'goods/thumbnail/102/80dae1c8-210d-48a6-86b5-dce251d7a50d', 'POSTER_IMG', 102);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (249, '2024-01-19 13:07:07.446857', '2024-01-19 13:07:07.446857',
        'goods/general/102/2e83976a-da3f-4a85-aa1f-90ba46f7031a', 'INFO_IMG', 102);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (250, '2024-01-19 13:07:11.659907', '2024-01-19 13:07:11.659907',
        'goods/thumbnail/103/00880e3b-dd01-40d1-a19e-be30606f9035', 'POSTER_IMG', 103);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (251, '2024-01-19 13:07:11.661196', '2024-01-19 13:07:11.661196',
        'goods/general/103/106c21bf-2f93-409e-82c1-413a1942a461', 'INFO_IMG', 103);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (252, '2024-01-19 13:07:11.661857', '2024-01-19 13:07:11.661857',
        'goods/general/103/51714320-f50e-490d-bf9a-8bf84525a0f8', 'INFO_IMG', 103);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (253, '2024-01-19 13:07:11.662600', '2024-01-19 13:07:11.662600',
        'goods/general/103/b807b290-471d-4d52-a221-47a48f666455', 'INFO_IMG', 103);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (254, '2024-01-19 13:07:11.663354', '2024-01-19 13:07:11.663354',
        'goods/general/103/d6271521-8979-40e6-9d09-a92191fc492e', 'INFO_IMG', 103);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (255, '2024-01-19 13:07:11.664352', '2024-01-19 13:07:11.664352',
        'goods/general/103/60096df5-1380-4920-9aee-101fe26290d5', 'INFO_IMG', 103);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (256, '2024-01-19 13:07:12.506805', '2024-01-19 13:07:12.506805',
        'goods/thumbnail/104/31afde8c-fe7d-489a-9b4b-c693b7844932', 'POSTER_IMG', 104);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (257, '2024-01-19 13:07:12.507969', '2024-01-19 13:07:12.507969',
        'goods/general/104/ca63c86a-e024-4df3-9e8e-8ff9b9e37693', 'INFO_IMG', 104);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (258, '2024-01-19 13:07:12.508602', '2024-01-19 13:07:12.508602',
        'goods/general/104/35a6d98d-1127-4ca8-98e0-af13b1616984', 'INFO_IMG', 104);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (259, '2024-01-19 13:07:14.937799', '2024-01-19 13:07:14.937799',
        'goods/thumbnail/105/3c3a0f21-aa05-4ab9-b38a-99c68f85f904', 'POSTER_IMG', 105);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (260, '2024-01-19 13:07:14.938945', '2024-01-19 13:07:14.938945',
        'goods/general/105/db6bf6a7-1dac-496b-888d-7799e585f1a4', 'INFO_IMG', 105);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (261, '2024-01-19 13:07:14.939524', '2024-01-19 13:07:14.939524',
        'goods/general/105/1ab3e209-0d03-497c-860f-1e5c085093bb', 'INFO_IMG', 105);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (262, '2024-01-19 13:07:16.866820', '2024-01-19 13:07:16.866820',
        'goods/thumbnail/106/45332950-751f-458e-976c-9ec9cdeeff1f', 'POSTER_IMG', 106);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (263, '2024-01-19 13:07:16.868522', '2024-01-19 13:07:16.868522',
        'goods/general/106/5b542db3-9435-4e65-a5e3-5bf5ec830121', 'INFO_IMG', 106);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (264, '2024-01-19 13:07:17.887675', '2024-01-19 13:07:17.887675',
        'goods/thumbnail/107/232c4e1f-2f94-4d9c-99c1-3cf9f0fc36b4', 'POSTER_IMG', 107);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (265, '2024-01-19 13:07:17.889876', '2024-01-19 13:07:17.889876',
        'goods/general/107/b28e2b96-e29b-4827-a387-7176841a31e6', 'INFO_IMG', 107);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (266, '2024-01-19 13:07:39.757241', '2024-01-19 13:07:39.757241',
        'goods/thumbnail/108/65fd6280-0394-4808-8ff4-9ee1032991ab', 'POSTER_IMG', 108);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (267, '2024-01-19 13:07:39.758357', '2024-01-19 13:07:39.758357',
        'goods/general/108/a6197f0d-df75-446d-b326-d5fc0579ebdc', 'INFO_IMG', 108);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (268, '2024-01-19 13:07:39.758722', '2024-01-19 13:07:39.758722',
        'goods/general/108/f87d376f-abf3-4fd8-90b6-c8e48faaddb6', 'INFO_IMG', 108);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (269, '2024-01-19 13:07:39.759066', '2024-01-19 13:07:39.759066',
        'goods/general/108/5a59f147-7115-4eec-9c19-06b1e6916b90', 'INFO_IMG', 108);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (270, '2024-01-19 13:07:39.759380', '2024-01-19 13:07:39.759380',
        'goods/general/108/5d3a63de-9b99-424d-b3e7-936563a964fe', 'INFO_IMG', 108);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (271, '2024-01-19 13:07:41.398724', '2024-01-19 13:07:41.398724',
        'goods/thumbnail/109/7b3872e9-b26b-4774-a974-36dccd337965', 'POSTER_IMG', 109);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (272, '2024-01-19 13:07:41.400283', '2024-01-19 13:07:41.400283',
        'goods/general/109/f0f87a82-51d7-48a8-824f-25261f60091e', 'INFO_IMG', 109);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (273, '2024-01-19 13:07:43.159709', '2024-01-19 13:07:43.159709',
        'goods/thumbnail/110/83358901-302e-426b-95a5-442972cb5b9d', 'POSTER_IMG', 110);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (274, '2024-01-19 13:07:43.160933', '2024-01-19 13:07:43.160933',
        'goods/general/110/d9c42864-8d5c-4755-ab90-78b98e1eeef1', 'INFO_IMG', 110);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (275, '2024-01-19 13:07:43.161711', '2024-01-19 13:07:43.161711',
        'goods/general/110/60fef551-8cd4-4b06-a34b-98261bd57fc1', 'INFO_IMG', 110);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (276, '2024-01-19 13:07:43.162525', '2024-01-19 13:07:43.162525',
        'goods/general/110/39dada6a-ec9d-4b97-a0a6-85d0a2c2e5cb', 'INFO_IMG', 110);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (277, '2024-01-19 13:07:44.286505', '2024-01-19 13:07:44.286505',
        'goods/thumbnail/111/89cf06f0-e73d-4950-b0cb-084b43ea9433', 'POSTER_IMG', 111);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (278, '2024-01-19 13:07:44.287875', '2024-01-19 13:07:44.287875',
        'goods/general/111/96b612ce-69b6-4e66-a1ab-26922975ed55', 'INFO_IMG', 111);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (279, '2024-01-19 13:07:45.572063', '2024-01-19 13:07:45.572063',
        'goods/thumbnail/112/83de3ece-184e-46a0-9c55-343d852da9df', 'POSTER_IMG', 112);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (280, '2024-01-19 13:07:45.572961', '2024-01-19 13:07:45.572961',
        'goods/general/112/3a7fa46f-a423-4eb8-be4b-4b5d7e8bfd25', 'INFO_IMG', 112);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (281, '2024-01-19 13:07:46.935116', '2024-01-19 13:07:46.935116',
        'goods/thumbnail/113/7ac9e2ad-2977-4565-bbf6-e28b0364f748', 'POSTER_IMG', 113);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (282, '2024-01-19 13:07:46.937873', '2024-01-19 13:07:46.937873',
        'goods/general/113/171b21f8-7cfe-4351-80ee-932d1245dde5', 'INFO_IMG', 113);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (283, '2024-01-19 13:07:46.939196', '2024-01-19 13:07:46.939196',
        'goods/general/113/22682547-4ceb-4a44-b812-8e07118fc040', 'INFO_IMG', 113);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (284, '2024-01-19 13:07:47.511701', '2024-01-19 13:07:47.511701',
        'goods/thumbnail/114/07c6f8fb-a98b-49b2-8c98-b7d82eec7dc7', 'POSTER_IMG', 114);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (285, '2024-01-19 13:07:47.513258', '2024-01-19 13:07:47.513258',
        'goods/general/114/b0c2945b-91ed-4c76-93e6-4fe400868011', 'INFO_IMG', 114);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (286, '2024-01-19 13:07:48.179367', '2024-01-19 13:07:48.179367',
        'goods/thumbnail/115/a228c5d4-0487-4e91-8a1f-5cfdda84b0d1', 'POSTER_IMG', 115);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (287, '2024-01-19 13:07:48.180873', '2024-01-19 13:07:48.180873',
        'goods/general/115/49a32d85-53f6-4288-9c12-9707c4c3d2f1', 'INFO_IMG', 115);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (288, '2024-01-19 13:07:49.144188', '2024-01-19 13:07:49.144188',
        'goods/thumbnail/116/cdc8b1d2-9e0f-4acf-8abb-c9058a6062e7', 'POSTER_IMG', 116);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (289, '2024-01-19 13:07:49.145249', '2024-01-19 13:07:49.145249',
        'goods/general/116/b139c1d7-5b19-4e0e-b7f8-5b52b90e8380', 'INFO_IMG', 116);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (290, '2024-01-19 13:07:49.145787', '2024-01-19 13:07:49.145787',
        'goods/general/116/9c6881d1-22ff-4057-8143-37ca4a373f79', 'INFO_IMG', 116);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (291, '2024-01-19 13:07:49.605283', '2024-01-19 13:07:49.605283',
        'goods/thumbnail/117/47e1641d-414a-43d6-9ad6-a6c184a4fd74', 'POSTER_IMG', 117);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (292, '2024-01-19 13:07:49.606248', '2024-01-19 13:07:49.606248',
        'goods/general/117/e1b29d19-2948-49c0-bf41-2d479a74244b', 'INFO_IMG', 117);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (293, '2024-01-19 13:07:50.538094', '2024-01-19 13:07:50.538094',
        'goods/thumbnail/118/ee619766-9e2b-47d1-8b3b-4bd64804c2e0', 'POSTER_IMG', 118);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (294, '2024-01-19 13:07:50.544489', '2024-01-19 13:07:50.544489',
        'goods/general/118/772be141-aa1e-49fd-b973-95446ec629e5', 'INFO_IMG', 118);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (295, '2024-01-19 13:07:51.998354', '2024-01-19 13:07:51.998354',
        'goods/thumbnail/119/d4f66b80-e27c-41e0-8599-cd537e6dccf3', 'POSTER_IMG', 119);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (296, '2024-01-19 13:07:52.000072', '2024-01-19 13:07:52.000072',
        'goods/general/119/30e2820d-57ba-487f-a65f-ad763f0173f6', 'INFO_IMG', 119);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (297, '2024-01-19 13:07:52.000871', '2024-01-19 13:07:52.000871',
        'goods/general/119/4146c38b-0b9b-4373-a9c8-512b7d8c4f22', 'INFO_IMG', 119);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (298, '2024-01-19 13:07:53.083337', '2024-01-19 13:07:53.083337',
        'goods/thumbnail/120/d16428b5-a383-4d81-8c9a-e40e62302864', 'POSTER_IMG', 120);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (299, '2024-01-19 13:07:53.095718', '2024-01-19 13:07:53.095718',
        'goods/general/120/d23ea05a-4941-424b-a56d-dcb125cbcbbf', 'INFO_IMG', 120);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (300, '2024-01-19 13:07:54.004702', '2024-01-19 13:07:54.004702',
        'goods/thumbnail/121/2316ff47-2d24-421e-9978-c0985f089423', 'POSTER_IMG', 121);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (301, '2024-01-19 13:07:54.005946', '2024-01-19 13:07:54.005946',
        'goods/general/121/25a34f27-9a0f-4217-82cf-ac4665f8e5f9', 'INFO_IMG', 121);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (302, '2024-01-19 13:07:55.405201', '2024-01-19 13:07:55.405201',
        'goods/thumbnail/122/222fb74b-2cdf-4191-8d53-a4099ae3daf8', 'POSTER_IMG', 122);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (303, '2024-01-19 13:07:55.406546', '2024-01-19 13:07:55.406546',
        'goods/general/122/f555e543-d24c-420a-9e5f-10a80bf4b34a', 'INFO_IMG', 122);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (304, '2024-01-19 13:07:57.024183', '2024-01-19 13:07:57.024183',
        'goods/thumbnail/123/cec1775c-97fa-4e9d-89e1-d6a2be9399e2', 'POSTER_IMG', 123);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (305, '2024-01-19 13:07:57.037429', '2024-01-19 13:07:57.037429',
        'goods/general/123/6f44edea-7be6-47ee-81f5-cb756c0280f6', 'INFO_IMG', 123);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (306, '2024-01-19 13:07:58.178568', '2024-01-19 13:07:58.178568',
        'goods/thumbnail/124/fa393c83-fb34-4c78-8cdb-cc7b6c990068', 'POSTER_IMG', 124);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (307, '2024-01-19 13:07:58.180779', '2024-01-19 13:07:58.180779',
        'goods/general/124/f5cce1dd-470e-4600-b061-6771098a39a8', 'INFO_IMG', 124);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (308, '2024-01-19 13:07:59.535045', '2024-01-19 13:07:59.535045',
        'goods/thumbnail/125/73a0d647-5b97-418b-bd11-a50089fa8faf', 'POSTER_IMG', 125);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (309, '2024-01-19 13:07:59.537655', '2024-01-19 13:07:59.537655',
        'goods/general/125/d2fcc288-8daa-47b5-aa4b-7f322355130e', 'INFO_IMG', 125);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (310, '2024-01-19 13:08:00.678443', '2024-01-19 13:08:00.678443',
        'goods/thumbnail/126/44d1f479-9d5d-42e3-94b2-e1e23b4bf396', 'POSTER_IMG', 126);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (311, '2024-01-19 13:08:00.679659', '2024-01-19 13:08:00.679659',
        'goods/general/126/85d21a40-593a-41da-8402-e454a3d0290c', 'INFO_IMG', 126);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (312, '2024-01-19 13:08:03.655017', '2024-01-19 13:08:03.655017',
        'goods/thumbnail/127/1f3a09da-7107-4096-84da-ed6a693f4340', 'POSTER_IMG', 127);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (313, '2024-01-19 13:08:03.656932', '2024-01-19 13:08:03.656932',
        'goods/general/127/97695746-9c5b-415e-88e6-2a37a38fb7a3', 'INFO_IMG', 127);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (314, '2024-01-19 13:08:05.101453', '2024-01-19 13:08:05.101453',
        'goods/thumbnail/128/5d40e036-5137-4d45-8301-c2f4fc44bd20', 'POSTER_IMG', 128);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (315, '2024-01-19 13:08:05.104100', '2024-01-19 13:08:05.104100',
        'goods/general/128/98c4dfa3-6646-454d-8926-f2f76d03ed73', 'INFO_IMG', 128);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (316, '2024-01-19 13:08:06.419951', '2024-01-19 13:08:06.419951',
        'goods/thumbnail/129/3ee4789a-cf27-4048-ae9c-d9c2be677add', 'POSTER_IMG', 129);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (317, '2024-01-19 13:08:06.421872', '2024-01-19 13:08:06.421872',
        'goods/general/129/86d52bba-95b4-4e8e-96c8-f8003482645c', 'INFO_IMG', 129);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (318, '2024-01-19 13:08:07.350347', '2024-01-19 13:08:07.350347',
        'goods/thumbnail/130/904d17c1-6e4d-415b-8c16-1808d6efd8a3', 'POSTER_IMG', 130);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (319, '2024-01-19 13:08:07.353644', '2024-01-19 13:08:07.353644',
        'goods/general/130/91642d20-0c36-49fe-8e3b-539e1c39fd23', 'INFO_IMG', 130);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (320, '2024-01-19 13:08:09.218340', '2024-01-19 13:08:09.218340',
        'goods/thumbnail/131/d955fffd-12c5-4bc5-b3f1-e05c5b4b8d6f', 'POSTER_IMG', 131);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (321, '2024-01-19 13:08:09.220402', '2024-01-19 13:08:09.220402',
        'goods/general/131/abeedf80-ff8e-4e27-b402-a60ff0b9a5e1', 'INFO_IMG', 131);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (322, '2024-01-19 13:08:10.185664', '2024-01-19 13:08:10.185664',
        'goods/thumbnail/132/f2575565-1650-4f1f-a882-f51755ad0a43', 'POSTER_IMG', 132);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (323, '2024-01-19 13:08:10.187660', '2024-01-19 13:08:10.187660',
        'goods/general/132/2801b364-65d4-455c-8230-91befc76ae15', 'INFO_IMG', 132);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (324, '2024-01-19 13:08:11.370522', '2024-01-19 13:08:11.370522',
        'goods/thumbnail/133/eb020c5f-e011-4c70-8359-20f7c069e5f8', 'POSTER_IMG', 133);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (325, '2024-01-19 13:08:11.372097', '2024-01-19 13:08:11.372097',
        'goods/general/133/932aba64-2a20-425b-9a32-460a60e92190', 'INFO_IMG', 133);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (326, '2024-01-19 13:08:12.665065', '2024-01-19 13:08:12.665065',
        'goods/thumbnail/134/9dab6ca1-25be-4cf4-84f3-c61a486f666a', 'POSTER_IMG', 134);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (327, '2024-01-19 13:08:12.667037', '2024-01-19 13:08:12.667037',
        'goods/general/134/f89b9098-f1ed-4034-b52d-701aa302f850', 'INFO_IMG', 134);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (328, '2024-01-19 13:08:13.750265', '2024-01-19 13:08:13.750265',
        'goods/thumbnail/135/244b73f9-a559-496d-a420-621c18e14a64', 'POSTER_IMG', 135);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (329, '2024-01-19 13:08:13.752974', '2024-01-19 13:08:13.752974',
        'goods/general/135/c26e58f3-3152-4abc-a4ef-681a122ff9c0', 'INFO_IMG', 135);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (330, '2024-01-19 13:08:16.890725', '2024-01-19 13:08:16.890725',
        'goods/thumbnail/136/ac732376-9ee4-42f5-addd-12c29181233b', 'POSTER_IMG', 136);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (331, '2024-01-19 13:08:16.892300', '2024-01-19 13:08:16.892300',
        'goods/general/136/c4f0587c-b109-4e9c-884b-c51edef04a39', 'INFO_IMG', 136);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (332, '2024-01-19 13:08:19.699990', '2024-01-19 13:08:19.699990',
        'goods/thumbnail/137/b145f64b-b0d7-4c04-81b5-9984c3868503', 'POSTER_IMG', 137);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (333, '2024-01-19 13:08:19.702450', '2024-01-19 13:08:19.702450',
        'goods/general/137/0b3fc36d-51cd-462d-b611-3765f59e06a5', 'INFO_IMG', 137);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (334, '2024-01-19 13:08:24.641906', '2024-01-19 13:08:24.641906',
        'goods/thumbnail/138/8baf0be2-c3d2-4259-badb-9852be3e24d5', 'POSTER_IMG', 138);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (335, '2024-01-19 13:08:24.642949', '2024-01-19 13:08:24.642949',
        'goods/general/138/7720a33c-c42e-411d-a135-e751eadb7904', 'INFO_IMG', 138);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (336, '2024-01-19 13:08:26.417035', '2024-01-19 13:08:26.417035',
        'goods/thumbnail/139/b6e51f45-fb13-41a6-8d8e-62bfbdc74f86', 'POSTER_IMG', 139);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (337, '2024-01-19 13:08:26.418299', '2024-01-19 13:08:26.418299',
        'goods/general/139/3234dd99-5d29-40b8-9bd3-8c8240a55cf1', 'INFO_IMG', 139);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (338, '2024-01-19 13:08:27.420763', '2024-01-19 13:08:27.420763',
        'goods/thumbnail/140/6dda710c-f076-40bc-9216-849cdebb12b7', 'POSTER_IMG', 140);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (339, '2024-01-19 13:08:27.422866', '2024-01-19 13:08:27.422866',
        'goods/general/140/b2dd76d0-7ee8-4490-85bc-f0f194eb54e7', 'INFO_IMG', 140);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (340, '2024-01-19 13:08:29.075089', '2024-01-19 13:08:29.075089',
        'goods/thumbnail/141/606f0ee8-efe9-4ae5-a5f7-ca08cc5c2472', 'POSTER_IMG', 141);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (341, '2024-01-19 13:08:29.076376', '2024-01-19 13:08:29.076376',
        'goods/general/141/48278b7a-ef4d-4a21-a0e8-f349ce9cc337', 'INFO_IMG', 141);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (342, '2024-01-19 13:08:29.077104', '2024-01-19 13:08:29.077104',
        'goods/general/141/57b0549e-e273-4ee3-968e-1279792c17b3', 'INFO_IMG', 141);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (343, '2024-01-19 13:08:30.261448', '2024-01-19 13:08:30.261448',
        'goods/thumbnail/142/d6d38f14-ae08-429c-a204-df38cd5bd4a1', 'POSTER_IMG', 142);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (344, '2024-01-19 13:08:30.262658', '2024-01-19 13:08:30.262658',
        'goods/general/142/2d1a6224-8495-41bc-a520-1ec9c4b05b28', 'INFO_IMG', 142);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (345, '2024-01-19 13:08:30.263297', '2024-01-19 13:08:30.263297',
        'goods/general/142/934201e6-8c25-4b63-a4a9-128c4bbf3c7e', 'INFO_IMG', 142);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (346, '2024-01-19 13:08:30.263913', '2024-01-19 13:08:30.263913',
        'goods/general/142/086ce912-f2aa-4b29-ab46-8e0017fac135', 'INFO_IMG', 142);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (347, '2024-01-19 13:08:30.264535', '2024-01-19 13:08:30.264535',
        'goods/general/142/277bce86-a056-41e4-a539-fad5ff9810e5', 'INFO_IMG', 142);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (348, '2024-01-19 13:08:31.469724', '2024-01-19 13:08:31.469724',
        'goods/thumbnail/143/637383bf-b1de-4735-ac71-254726c79b40', 'POSTER_IMG', 143);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (349, '2024-01-19 13:08:31.472152', '2024-01-19 13:08:31.472152',
        'goods/general/143/2dee660c-4009-4dcd-a0ca-52dbff49cefe', 'INFO_IMG', 143);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (350, '2024-01-19 13:08:35.045613', '2024-01-19 13:08:35.045613',
        'goods/thumbnail/144/ff58fc50-4161-43df-be84-84fa6c730073', 'POSTER_IMG', 144);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (351, '2024-01-19 13:08:35.047937', '2024-01-19 13:08:35.047937',
        'goods/general/144/5cef06ee-5f92-409b-a72b-0dab68b6b170', 'INFO_IMG', 144);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (352, '2024-01-19 13:08:38.044052', '2024-01-19 13:08:38.044052',
        'goods/thumbnail/145/21409ac5-a2fc-4151-aef1-e67b4c67b131', 'POSTER_IMG', 145);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (353, '2024-01-19 13:08:38.045411', '2024-01-19 13:08:38.045411',
        'goods/general/145/0a6b5138-3eb1-442e-b456-219ac1897707', 'INFO_IMG', 145);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (354, '2024-01-19 13:08:40.265659', '2024-01-19 13:08:40.265659',
        'goods/thumbnail/146/25d5f183-6517-453f-8708-c1716e0f288c', 'POSTER_IMG', 146);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (355, '2024-01-19 13:08:40.267683', '2024-01-19 13:08:40.267683',
        'goods/general/146/390ac07d-20b3-4b4c-8cb7-d5e00f64e9de', 'INFO_IMG', 146);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (356, '2024-01-19 13:08:40.268963', '2024-01-19 13:08:40.268963',
        'goods/general/146/e1cdf037-c06c-43a7-b7a0-068b21e35cdc', 'INFO_IMG', 146);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (357, '2024-01-19 13:08:41.524717', '2024-01-19 13:08:41.524717',
        'goods/thumbnail/147/bbd85c6d-eb9a-4f02-b9b2-84b74b287572', 'POSTER_IMG', 147);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (358, '2024-01-19 13:08:41.525868', '2024-01-19 13:08:41.525868',
        'goods/general/147/9f83feaf-0e76-4d51-9b9e-0bf2f4f9209f', 'INFO_IMG', 147);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (359, '2024-01-19 13:08:41.526710', '2024-01-19 13:08:41.526710',
        'goods/general/147/f21d9e75-acc5-430d-8126-ab378f483c38', 'INFO_IMG', 147);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (360, '2024-01-19 13:08:42.747786', '2024-01-19 13:08:42.747786',
        'goods/thumbnail/148/77022286-a6c0-43c8-811f-c5d5ff3834f3', 'POSTER_IMG', 148);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (361, '2024-01-19 13:08:42.748717', '2024-01-19 13:08:42.748717',
        'goods/general/148/bd3850d6-c753-4517-82a7-badd5a78e316', 'INFO_IMG', 148);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (362, '2024-01-19 13:08:43.893849', '2024-01-19 13:08:43.893849',
        'goods/thumbnail/149/c5166355-0c92-4a8e-a307-d2c17f05e60b', 'POSTER_IMG', 149);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (363, '2024-01-19 13:08:43.895893', '2024-01-19 13:08:43.895893',
        'goods/general/149/788c056d-be8e-4b77-9c11-5ea27b8b36ab', 'INFO_IMG', 149);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (364, '2024-01-19 13:08:45.389336', '2024-01-19 13:08:45.389336',
        'goods/thumbnail/150/04e8666f-c2ac-4d9c-a533-aab757380084', 'POSTER_IMG', 150);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (365, '2024-01-19 13:08:45.391434', '2024-01-19 13:08:45.391434',
        'goods/general/150/6ffbd47b-d65e-48fb-8c5d-217d22f3b5e8', 'INFO_IMG', 150);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (366, '2024-01-19 13:08:46.963505', '2024-01-19 13:08:46.963505',
        'goods/thumbnail/151/8a3e2857-7aac-499e-bb4d-06a7147dd468', 'POSTER_IMG', 151);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (367, '2024-01-19 13:08:46.966015', '2024-01-19 13:08:46.966015',
        'goods/general/151/7cd74cdd-2dfe-4cb7-b0df-aeb2e536a066', 'INFO_IMG', 151);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (368, '2024-01-19 13:08:48.156341', '2024-01-19 13:08:48.156341',
        'goods/thumbnail/152/bb95fb2e-6bce-42a4-82c7-3190d762696f', 'POSTER_IMG', 152);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (369, '2024-01-19 13:08:48.157571', '2024-01-19 13:08:48.157571',
        'goods/general/152/063e74e0-1e2e-4e31-b0f2-1e78c4a236e4', 'INFO_IMG', 152);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (370, '2024-01-19 13:08:49.402905', '2024-01-19 13:08:49.402905',
        'goods/thumbnail/153/4d56c830-f332-4484-93de-611ce8375c95', 'POSTER_IMG', 153);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (371, '2024-01-19 13:08:49.405170', '2024-01-19 13:08:49.405170',
        'goods/general/153/6298c92c-7fe4-4c09-a1b4-603554f6e2fc', 'INFO_IMG', 153);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (372, '2024-01-19 13:08:50.403843', '2024-01-19 13:08:50.403843',
        'goods/thumbnail/154/2d99fbd5-b3c0-4ecd-a157-456cf14734ca', 'POSTER_IMG', 154);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (373, '2024-01-19 13:08:50.406905', '2024-01-19 13:08:50.406905',
        'goods/general/154/cb082e6e-03c8-4f70-915c-9fb6f5d568e5', 'INFO_IMG', 154);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (374, '2024-01-19 13:08:52.488156', '2024-01-19 13:08:52.488156',
        'goods/thumbnail/155/5d58d5e4-f4d3-4a74-956d-a7b2ba6a534f', 'POSTER_IMG', 155);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (375, '2024-01-19 13:08:52.490430', '2024-01-19 13:08:52.490430',
        'goods/general/155/60be8b11-ed82-4f81-90e7-15931128c0fa', 'INFO_IMG', 155);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (376, '2024-01-19 13:08:53.796465', '2024-01-19 13:08:53.796465',
        'goods/thumbnail/156/8579f09b-bc65-4aaa-ae9d-90a27f8a5601', 'POSTER_IMG', 156);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (377, '2024-01-19 13:08:53.798299', '2024-01-19 13:08:53.798299',
        'goods/general/156/84ea85ca-b13a-47d0-ac57-18a9b0d7ac88', 'INFO_IMG', 156);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (378, '2024-01-19 13:08:55.507448', '2024-01-19 13:08:55.507448',
        'goods/thumbnail/157/ce0442e2-5d90-4b0e-b401-4039efa2f8af', 'POSTER_IMG', 157);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (379, '2024-01-19 13:08:55.508631', '2024-01-19 13:08:55.508631',
        'goods/general/157/688a87ff-90e8-4035-a05a-382633c7aece', 'INFO_IMG', 157);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (380, '2024-01-19 13:08:57.058159', '2024-01-19 13:08:57.058159',
        'goods/thumbnail/158/e5bae788-ec78-4210-a6a7-1f28cf384ace', 'POSTER_IMG', 158);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (381, '2024-01-19 13:08:57.059430', '2024-01-19 13:08:57.059430',
        'goods/general/158/8be430fa-a466-404c-82dc-af029e6a1f68', 'INFO_IMG', 158);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (382, '2024-01-19 13:08:57.060172', '2024-01-19 13:08:57.060172',
        'goods/general/158/fe2d3823-f865-4761-af8d-f4ee2cdb7bd9', 'INFO_IMG', 158);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (383, '2024-01-19 13:08:57.061035', '2024-01-19 13:08:57.061035',
        'goods/general/158/338994ce-9ec3-4472-b593-3f84f315450c', 'INFO_IMG', 158);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (384, '2024-01-19 13:08:57.733164', '2024-01-19 13:08:57.733164',
        'goods/thumbnail/159/94476763-96e9-445a-bb10-c6b2930fc918', 'POSTER_IMG', 159);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (385, '2024-01-19 13:08:57.735340', '2024-01-19 13:08:57.735340',
        'goods/general/159/807c6d44-b3fa-4c6e-b161-54090501e3ac', 'INFO_IMG', 159);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (386, '2024-01-19 13:08:58.914424', '2024-01-19 13:08:58.914424',
        'goods/thumbnail/160/70db495d-e75d-4543-8e3d-4982553a88bc', 'POSTER_IMG', 160);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (387, '2024-01-19 13:08:58.916315', '2024-01-19 13:08:58.916315',
        'goods/general/160/abdf9fd8-7f23-4390-ba9e-50f3b5936a58', 'INFO_IMG', 160);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (388, '2024-01-19 13:09:00.008443', '2024-01-19 13:09:00.008443',
        'goods/thumbnail/161/5fea69fc-456c-4a3b-b416-cb31008e9915', 'POSTER_IMG', 161);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (389, '2024-01-19 13:09:00.009542', '2024-01-19 13:09:00.009542',
        'goods/general/161/1dfb3f3e-501f-47c2-b956-e310ea73f091', 'INFO_IMG', 161);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (390, '2024-01-19 13:09:01.181337', '2024-01-19 13:09:01.181337',
        'goods/thumbnail/162/af7ec740-8576-47f8-9d31-23b3f986ef36', 'POSTER_IMG', 162);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (391, '2024-01-19 13:09:01.182701', '2024-01-19 13:09:01.182701',
        'goods/general/162/ca283696-480d-480d-aa14-790b26d864ca', 'INFO_IMG', 162);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (392, '2024-01-19 13:09:02.657944', '2024-01-19 13:09:02.657944',
        'goods/thumbnail/163/17b8c12d-f559-4d7c-ae7f-b3731ffcade7', 'POSTER_IMG', 163);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (393, '2024-01-19 13:09:02.659238', '2024-01-19 13:09:02.659238',
        'goods/general/163/fb58f38c-541e-4516-ad58-f92c8c49e831', 'INFO_IMG', 163);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (394, '2024-01-19 13:09:05.268446', '2024-01-19 13:09:05.268446',
        'goods/thumbnail/164/108f8018-d41d-492d-9b90-b22236ec5911', 'POSTER_IMG', 164);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (395, '2024-01-19 13:09:05.271450', '2024-01-19 13:09:05.271450',
        'goods/general/164/fc365184-383e-45ab-8f48-78af84bc8cdf', 'INFO_IMG', 164);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (396, '2024-01-19 13:09:07.913475', '2024-01-19 13:09:07.913475',
        'goods/thumbnail/165/3f9b24e6-d4a3-4e07-8eb2-25c53122f176', 'POSTER_IMG', 165);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (397, '2024-01-19 13:09:07.914675', '2024-01-19 13:09:07.914675',
        'goods/general/165/110a5ae5-d4a7-4295-83f4-0b0ae1c14d15', 'INFO_IMG', 165);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (398, '2024-01-19 13:09:09.195359', '2024-01-19 13:09:09.195359',
        'goods/thumbnail/166/b131a44c-f4bb-43ad-989c-65501322ad78', 'POSTER_IMG', 166);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (399, '2024-01-19 13:09:09.196488', '2024-01-19 13:09:09.196488',
        'goods/general/166/878a6e21-e5eb-42f5-83cc-f0d40826dbf3', 'INFO_IMG', 166);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (400, '2024-01-19 13:09:10.278688', '2024-01-19 13:09:10.278688',
        'goods/thumbnail/167/c21d399f-e2ff-4f4d-9f90-7848880365b7', 'POSTER_IMG', 167);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (401, '2024-01-19 13:09:10.280859', '2024-01-19 13:09:10.280859',
        'goods/general/167/8f0be1a1-060b-405f-afbb-81386eada55e', 'INFO_IMG', 167);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (402, '2024-01-19 13:09:12.066595', '2024-01-19 13:09:12.066595',
        'goods/thumbnail/168/bb2ac4e3-ad86-42f3-bb19-de9f9fe7d458', 'POSTER_IMG', 168);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (403, '2024-01-19 13:09:12.069768', '2024-01-19 13:09:12.069768',
        'goods/general/168/94f2be30-5898-4b2a-abd2-372db7094099', 'INFO_IMG', 168);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (404, '2024-01-19 13:09:12.836648', '2024-01-19 13:09:12.836648',
        'goods/thumbnail/169/a285d017-41aa-4a65-9937-7852aba71ee3', 'POSTER_IMG', 169);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (405, '2024-01-19 13:09:12.838979', '2024-01-19 13:09:12.838979',
        'goods/general/169/56fc43f7-69c5-448b-a076-428afc116f9f', 'INFO_IMG', 169);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (406, '2024-01-19 13:09:16.062830', '2024-01-19 13:09:16.062830',
        'goods/thumbnail/170/d751a72a-332e-42be-9584-3105382edfa2', 'POSTER_IMG', 170);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (407, '2024-01-19 13:09:16.064603', '2024-01-19 13:09:16.064603',
        'goods/general/170/7f26df7d-4a81-43db-9655-3afc1c9d6d05', 'INFO_IMG', 170);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (408, '2024-01-19 13:09:16.065211', '2024-01-19 13:09:16.065211',
        'goods/general/170/dc52c9b0-c363-4156-a5f2-c06e9ee2a1fe', 'INFO_IMG', 170);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (409, '2024-01-19 13:09:16.065726', '2024-01-19 13:09:16.065726',
        'goods/general/170/4356c41d-02ff-4e31-a5d4-a7af2883026f', 'INFO_IMG', 170);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (410, '2024-01-19 13:09:16.066227', '2024-01-19 13:09:16.066227',
        'goods/general/170/303425a5-989c-47ae-9f30-7592f950d687', 'INFO_IMG', 170);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (411, '2024-01-19 13:09:16.066723', '2024-01-19 13:09:16.066723',
        'goods/general/170/89a6cbea-7763-4faa-ab01-8785df27e973', 'INFO_IMG', 170);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (412, '2024-01-19 13:09:17.592553', '2024-01-19 13:09:17.592553',
        'goods/thumbnail/171/4f738283-ce5f-41eb-9ca3-e31f53fdcb16', 'POSTER_IMG', 171);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (413, '2024-01-19 13:09:17.593942', '2024-01-19 13:09:17.593942',
        'goods/general/171/92fb3fcc-3e17-4665-a729-f28e521ef0ad', 'INFO_IMG', 171);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (414, '2024-01-19 13:09:19.199171', '2024-01-19 13:09:19.199171',
        'goods/thumbnail/172/7e008fd4-582d-4fe6-ae9d-f95b24f9da54', 'POSTER_IMG', 172);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (415, '2024-01-19 13:09:19.201179', '2024-01-19 13:09:19.201179',
        'goods/general/172/5708f2ff-1807-4ba2-b8c4-bfe98a7ec638', 'INFO_IMG', 172);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (416, '2024-01-19 13:09:26.897979', '2024-01-19 13:09:26.897979',
        'goods/thumbnail/173/8dcbe922-d93a-4ccc-a73e-c0edf2475b6c', 'POSTER_IMG', 173);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (417, '2024-01-19 13:09:26.900840', '2024-01-19 13:09:26.900840',
        'goods/general/173/509fcc7d-5336-4d11-bd8a-366282592040', 'INFO_IMG', 173);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (418, '2024-01-19 13:09:27.819432', '2024-01-19 13:09:27.819432',
        'goods/thumbnail/174/f34d593c-36ac-4baf-8e87-7188119bfb6d', 'POSTER_IMG', 174);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (419, '2024-01-19 13:09:27.820928', '2024-01-19 13:09:27.820928',
        'goods/general/174/a76b1132-e7ed-4872-a028-0138648fa8c4', 'INFO_IMG', 174);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (420, '2024-01-19 13:09:29.024536', '2024-01-19 13:09:29.024536',
        'goods/thumbnail/175/d664ee97-98bb-4c9a-84bc-64a4c9539ff1', 'POSTER_IMG', 175);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (421, '2024-01-19 13:09:29.026459', '2024-01-19 13:09:29.026459',
        'goods/general/175/9da3202f-0830-4ed8-ada3-7541b0864f96', 'INFO_IMG', 175);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (422, '2024-01-19 13:09:30.326210', '2024-01-19 13:09:30.326210',
        'goods/thumbnail/176/fa8e3adc-9819-42e1-b215-ac10ff9f22ca', 'POSTER_IMG', 176);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (423, '2024-01-19 13:09:30.328506', '2024-01-19 13:09:30.328506',
        'goods/general/176/8e654a86-ea75-444b-8f01-0285194cb6f1', 'INFO_IMG', 176);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (424, '2024-01-19 13:09:31.779659', '2024-01-19 13:09:31.779659',
        'goods/thumbnail/177/0ce4da33-02b3-4e9c-988c-96ae38abf469', 'POSTER_IMG', 177);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (425, '2024-01-19 13:09:31.781961', '2024-01-19 13:09:31.781961',
        'goods/general/177/1b4683ae-a3c3-4a23-8502-cea6ae956c54', 'INFO_IMG', 177);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (426, '2024-01-19 13:09:33.411661', '2024-01-19 13:09:33.411661',
        'goods/thumbnail/178/216d0325-02b3-44bc-b2db-0ca27edc665d', 'POSTER_IMG', 178);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (427, '2024-01-19 13:09:33.414034', '2024-01-19 13:09:33.414034',
        'goods/general/178/836cb139-2623-48d0-b368-453d7d989803', 'INFO_IMG', 178);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (428, '2024-01-19 13:09:33.416008', '2024-01-19 13:09:33.416008',
        'goods/general/178/84a2a0a1-66a0-4816-b76d-df2dec801f28', 'INFO_IMG', 178);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (429, '2024-01-19 13:09:34.785120', '2024-01-19 13:09:34.785120',
        'goods/thumbnail/179/5f1da20e-962f-48f1-88e7-aac37e4a43d2', 'POSTER_IMG', 179);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (430, '2024-01-19 13:09:34.786227', '2024-01-19 13:09:34.786227',
        'goods/general/179/5c09cf6d-e9f3-4b50-852e-c16e99f9550b', 'INFO_IMG', 179);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (431, '2024-01-19 13:09:36.888216', '2024-01-19 13:09:36.888216',
        'goods/thumbnail/180/40046f8d-94b3-437e-973e-d2880f11656c', 'POSTER_IMG', 180);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (432, '2024-01-19 13:09:36.889468', '2024-01-19 13:09:36.889468',
        'goods/general/180/0fd3a468-8142-4024-bd16-d78aad248c35', 'INFO_IMG', 180);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (433, '2024-01-19 13:09:38.397256', '2024-01-19 13:09:38.397256',
        'goods/thumbnail/181/1d3e9c00-b461-4614-9b52-9943f98d2ce9', 'POSTER_IMG', 181);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (434, '2024-01-19 13:09:38.398579', '2024-01-19 13:09:38.398579',
        'goods/general/181/ff7efbba-0b56-42d6-800d-df5bcaf1bf1a', 'INFO_IMG', 181);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (435, '2024-01-19 13:09:39.653017', '2024-01-19 13:09:39.653017',
        'goods/thumbnail/182/472432ac-cbb3-4d42-b3d7-69f3b53d14fe', 'POSTER_IMG', 182);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (436, '2024-01-19 13:09:39.654299', '2024-01-19 13:09:39.654299',
        'goods/general/182/264c990f-42f5-46a1-8b84-f17615ae9dc1', 'INFO_IMG', 182);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (437, '2024-01-19 13:09:40.918321', '2024-01-19 13:09:40.918321',
        'goods/thumbnail/183/63401681-7512-4f36-9eec-3de69dfcf623', 'POSTER_IMG', 183);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (438, '2024-01-19 13:09:40.919569', '2024-01-19 13:09:40.919569',
        'goods/general/183/98dd231a-57c7-47ee-bf25-6b7db8dd3f88', 'INFO_IMG', 183);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (439, '2024-01-19 13:09:42.493576', '2024-01-19 13:09:42.493576',
        'goods/thumbnail/184/2babfd34-95e3-4e5c-8f07-1951cc884dfc', 'POSTER_IMG', 184);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (440, '2024-01-19 13:09:42.495330', '2024-01-19 13:09:42.495330',
        'goods/general/184/ab482af3-db32-4996-ac1c-59be47d0a3b4', 'INFO_IMG', 184);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (441, '2024-01-19 13:09:44.003705', '2024-01-19 13:09:44.003705',
        'goods/thumbnail/185/56653ceb-1cc2-4d83-a129-c586d267a1a5', 'POSTER_IMG', 185);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (442, '2024-01-19 13:09:44.004861', '2024-01-19 13:09:44.004861',
        'goods/general/185/e2351eaa-660c-4566-93c1-7b5611138d3f', 'INFO_IMG', 185);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (443, '2024-01-19 13:09:45.679041', '2024-01-19 13:09:45.679041',
        'goods/thumbnail/186/d53419db-06a1-45c3-aabd-8593d38f553a', 'POSTER_IMG', 186);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (444, '2024-01-19 13:09:45.681209', '2024-01-19 13:09:45.681209',
        'goods/general/186/7c53e9c4-2840-4b15-8f2f-03f32f5723a8', 'INFO_IMG', 186);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (445, '2024-01-19 13:09:47.324826', '2024-01-19 13:09:47.324826',
        'goods/thumbnail/187/62e0bc72-3fbb-4b80-a612-e31d081ee7b7', 'POSTER_IMG', 187);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (446, '2024-01-19 13:09:47.326894', '2024-01-19 13:09:47.326894',
        'goods/general/187/6fdef51e-3392-4be9-8f69-cb87f520cc5e', 'INFO_IMG', 187);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (447, '2024-01-19 13:09:48.968883', '2024-01-19 13:09:48.968883',
        'goods/thumbnail/188/e4677b41-0272-4205-ba64-e7191dbce8fe', 'POSTER_IMG', 188);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (448, '2024-01-19 13:09:48.970846', '2024-01-19 13:09:48.970846',
        'goods/general/188/6743377c-a393-441c-a667-6965a2b95410', 'INFO_IMG', 188);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (449, '2024-01-19 13:09:48.971643', '2024-01-19 13:09:48.971643',
        'goods/general/188/a266c2f3-550b-4b27-8100-e454456de622', 'INFO_IMG', 188);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (450, '2024-01-19 13:09:50.739365', '2024-01-19 13:09:50.739365',
        'goods/thumbnail/189/1906e217-7215-4e37-ba54-05fbdcc45cc7', 'POSTER_IMG', 189);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (451, '2024-01-19 13:09:50.740858', '2024-01-19 13:09:50.740858',
        'goods/general/189/fd73ed5c-343b-46b7-9aff-9b1315c05773', 'INFO_IMG', 189);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (452, '2024-01-19 13:09:50.741851', '2024-01-19 13:09:50.741851',
        'goods/general/189/0873ea0d-f0ff-4edc-97d1-b8024045790b', 'INFO_IMG', 189);
INSERT INTO ticket_auction.goods_image (id, created_at, modified_at, s3_key, type, goods_info_id)
VALUES (453, '2024-01-19 13:09:50.743219', '2024-01-19 13:09:50.743219',
        'goods/general/189/18520300-29dc-4fb3-bb74-b40ac92b2943', 'INFO_IMG', 189);


# 공연 추가
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (1, now(), now(), '2024-02-02', '2024-01-20', '아기돼지삼형제: 늑대숲 또옹돼지 원정대1', 189, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (2, now(), now(), '2024-02-02', '2024-01-20', '아기돼지삼형제: 늑대숲 또옹돼지 원정대2', 189, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (3, now(), now(), '2024-02-02', '2024-01-20', '아기돼지삼형제: 늑대숲 또옹돼지 원정대3', 189, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (4, now(), now(), '2024-02-02', '2024-01-20', '아기돼지삼형제: 늑대숲 또옹돼지 원정대4', 189, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (5, now(), now(), '2024-02-02', '2024-01-20', '아기돼지삼형제: 늑대숲 또옹돼지 원정대5', 189, 1);

insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (6, now(), now(), '2024-02-02', '2024-01-22', '오즈의 마법사 [대학로]1', 118, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (7, now(), now(), '2024-02-02', '2024-01-22', '오즈의 마법사 [대학로]2', 118, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (8, now(), now(), '2024-02-02', '2024-01-22', '오즈의 마법사 [대학로]3', 118, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (9, now(), now(), '2024-02-02', '2024-01-22', '오즈의 마법사 [대학로]4', 118, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (10, now(), now(), '2024-02-02', '2024-01-22', '오즈의 마법사 [대학로]5', 118, 1);

insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (11, now(), now(), '2024-02-02', '2024-01-22', '월간연극, 하녀들1', 2, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (12, now(), now(), '2024-02-02', '2024-01-22', '월간연극, 하녀들2', 2, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (13, now(), now(), '2024-02-02', '2024-01-22', '월간연극, 하녀들3', 2, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (14, now(), now(), '2024-02-02', '2024-01-22', '월간연극, 하녀들4', 2, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (15, now(), now(), '2024-02-02', '2024-01-22', '월간연극, 하녀들5', 2, 1);

insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (16, now(), now(), '2024-02-02', '2024-01-22', '나의PS파트너1', 3, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (17, now(), now(), '2024-02-02', '2024-01-22', '나의PS파트너2', 3, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (18, now(), now(), '2024-02-02', '2024-01-22', '나의PS파트너3', 3, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (19, now(), now(), '2024-02-02', '2024-01-22', '나의PS파트너4', 3, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (20, now(), now(), '2024-02-02', '2024-01-22', '나의PS파트너5', 3, 1);

insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (21, now(), now(), '2024-02-02', '2024-01-22', '다락방1', 4, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (22, now(), now(), '2024-02-02', '2024-01-22', '다락방2', 4, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (23, now(), now(), '2024-02-02', '2024-01-22', '다락방3', 4, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (24, now(), now(), '2024-02-02', '2024-01-22', '다락방4', 4, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (25, now(), now(), '2024-02-02', '2024-01-22', '다락방5', 4, 1);

insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (26, now(), now(), '2024-02-02', '2024-01-22', '고도를 기다리며1', 5, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (27, now(), now(), '2024-02-02', '2024-01-22', '고도를 기다리며2', 5, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (28, now(), now(), '2024-02-02', '2024-01-22', '고도를 기다리며3', 5, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (29, now(), now(), '2024-02-02', '2024-01-22', '고도를 기다리며4', 5, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (30, now(), now(), '2024-02-02', '2024-01-22', '고도를 기다리며5', 5, 1);

insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (31, now(), now(), '2024-02-02', '2024-01-22', '바냐의 시간1', 6, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (32, now(), now(), '2024-02-02', '2024-01-22', '바냐의 시간2', 6, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (33, now(), now(), '2024-02-02', '2024-01-22', '바냐의 시간3', 6, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (34, now(), now(), '2024-02-02', '2024-01-22', '바냐의 시간4', 6, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (35, now(), now(), '2024-02-02', '2024-01-22', '바냐의 시간5', 6, 1);

insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (36, now(), now(), '2024-02-02', '2024-01-22', '셰익스피어 리어왕 덧대어 쓰기, 와르르1', 9, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (37, now(), now(), '2024-02-02', '2024-01-22', '셰익스피어 리어왕 덧대어 쓰기, 와르르2', 9, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (38, now(), now(), '2024-02-02', '2024-01-22', '셰익스피어 리어왕 덧대어 쓰기, 와르르3', 9, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (39, now(), now(), '2024-02-02', '2024-01-22', '셰익스피어 리어왕 덧대어 쓰기, 와르르4', 9, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (40, now(), now(), '2024-02-02', '2024-01-22', '셰익스피어 리어왕 덧대어 쓰기, 와르르5', 9, 1);

insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (41, now(), now(), '2024-02-02', '2024-01-22', '유림식당 [대학로]1', 8, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (42, now(), now(), '2024-02-02', '2024-01-22', '유림식당 [대학로]2', 8, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (43, now(), now(), '2024-02-02', '2024-01-22', '유림식당 [대학로]3', 8, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (44, now(), now(), '2024-02-02', '2024-01-22', '유림식당 [대학로]4', 8, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (45, now(), now(), '2024-02-02', '2024-01-22', '유림식당 [대학로]5', 8, 1);

insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (46, now(), now(), '2024-02-02', '2024-01-22', '만선1', 7, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (47, now(), now(), '2024-02-02', '2024-01-22', '만선2', 7, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (48, now(), now(), '2024-02-02', '2024-01-22', '만선3', 7, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (49, now(), now(), '2024-02-02', '2024-01-22', '만선4', 7, 1);
insert into goods (id, created_at, modified_at, end_date, start_date, title, goods_info_id, place_id)
VALUES (50, now(), now(), '2024-02-02', '2024-01-22', '만선5', 7, 1);

# 등급 추가
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (1, now(), now(), 40000, 'C 등급', 50000, 1);
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (2, now(), now(), 140000, 'B 등급', 150000, 1);
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (3, now(), now(), 240000, 'A 등급', 250000, 1);
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (4, now(), now(), 20000, 'D 등급', 30000, 1);
#
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (5, now(), now(), 40000, 'C 등급', 50000, 1);
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (6, now(), now(), 140000, 'B 등급', 150000, 1);
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (7, now(), now(), 240000, 'A 등급', 250000, 1);
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (8, now(), now(), 20000, 'D 등급', 30000, 1);
#
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (9, now(), now(), 40000, 'C 등급', 50000, 1);
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (10, now(), now(), 140000, 'B 등급', 150000, 1);
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (11, now(), now(), 240000, 'A 등급', 250000, 1);
# insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
# values (12, now(), now(), 20000, 'D 등급', 30000, 1);

DELIMITER //

CREATE PROCEDURE GradeAddLoop()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 50
        DO
            -- 반복할 작업
            insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
            values (1 + ((i - 1) * 4), now(), now(), 240000, 'A 등급', 250000, i);
            insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
            values (2 + ((i - 1) * 4), now(), now(), 140000, 'B 등급', 150000, i);
            insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
            values (3 + ((i - 1) * 4), now(), now(), 40000, 'C 등급', 50000, i);
            insert into grade (id, created_at, modified_at, auction_price, name, normal_price, goods_id)
            values (4 + ((i - 1) * 4), now(), now(), 5000, 'D 등급', 10000, i);

            SET i = i + 1;
        END WHILE;
END //

DELIMITER ;
CALL GradeAddLoop();

# 좌석 등급 추가
# insert into zone_grade (id, created_at, modified_at, grade_id, zone_id)
# VALUES (1, now(), now(), 1, 1);
# insert into zone_grade (id, created_at, modified_at, grade_id, zone_id)
# VALUES (2, now(), now(), 2, 2);
# insert into zone_grade (id, created_at, modified_at, grade_id, zone_id)
# VALUES (3, now(), now(), 3, 3);
# insert into zone_grade (id, created_at, modified_at, grade_id, zone_id)
# VALUES (4, now(), now(), 4, 4);

DELIMITER //

CREATE PROCEDURE ZoneGradeAddLoop()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 50
        DO
            -- 반복할 작업
            insert into zone_grade (id, created_at, modified_at, grade_id, zone_id)
            VALUES (1 + ((i - 1) * 4), now(), now(), 1 + ((i - 1) * 4), 1);
            insert into zone_grade (id, created_at, modified_at, grade_id, zone_id)
            VALUES (2 + ((i - 1) * 4), now(), now(), 2 + ((i - 1) * 4), 2);
            insert into zone_grade (id, created_at, modified_at, grade_id, zone_id)
            VALUES (3 + ((i - 1) * 4), now(), now(), 3 + ((i - 1) * 4), 3);
            insert into zone_grade (id, created_at, modified_at, grade_id, zone_id)
            VALUES (4 + ((i - 1) * 4), now(), now(), 4 + ((i - 1) * 4), 4);

            SET i = i + 1;
        END WHILE;
END //

DELIMITER ;
CALL ZoneGradeAddLoop();

# 회차 추가
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (1, now(), now(), 1, '2024-01-20 19:00:00', 1);
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (2, now(), now(), 2, '2024-01-21 19:00:00', 1);
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (3, now(), now(), 3, '2024-01-22 19:00:00', 1);
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (4, now(), now(), 4, '2024-01-23 19:00:00', 1);
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (5, now(), now(), 5, '2024-01-24 19:00:00', 1);
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (6, now(), now(), 6, '2024-01-25 19:00:00', 1);
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (7, now(), now(), 7, '2024-01-26 19:00:00', 1);
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (8, now(), now(), 8, '2024-01-27 19:00:00', 1);
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (6, now(), now(), 6, '2024-01-25 19:00:00', 1);
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (7, now(), now(), 7, '2024-01-26 19:00:00', 1);
# insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
# VALUES (8, now(), now(), 8, '2024-01-27 19:00:00', 1);

DELIMITER //

CREATE PROCEDURE ScheduleAddLoop()
BEGIN
    DECLARE goods_id INT DEFAULT 1;

    WHILE goods_id <= 50
        DO
            -- 반복할 작업
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (1 + ((goods_id - 1) * 11), now(), now(), 1, '2024-01-22 19:00:00', goods_id);
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (2 + ((goods_id - 1) * 11), now(), now(), 2, '2024-01-23 19:00:00', goods_id);
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (3 + ((goods_id - 1) * 11), now(), now(), 3, '2024-01-24 19:00:00', goods_id);
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (4 + ((goods_id - 1) * 11), now(), now(), 4, '2024-01-25 19:00:00', goods_id);
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (5 + ((goods_id - 1) * 11), now(), now(), 5, '2024-01-26 19:00:00', goods_id);
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (6 + ((goods_id - 1) * 11), now(), now(), 6, '2024-01-27 19:00:00', goods_id);
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (7 + ((goods_id - 1) * 11), now(), now(), 7, '2024-01-28 19:00:00', goods_id);
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (8 + ((goods_id - 1) * 11), now(), now(), 8, '2024-01-29 19:00:00', goods_id);
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (9 + ((goods_id - 1) * 11), now(), now(), 6, '2024-01-30 19:00:00', goods_id);
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (10 + ((goods_id - 1) * 11), now(), now(), 7, '2024-01-31 19:00:00', goods_id);
            insert into schedule (id, created_at, modified_at, sequence, start_date_time, goods_id)
            VALUES (11 + ((goods_id - 1) * 11), now(), now(), 8, '2024-02-01 19:00:00', goods_id);

            SET goods_id = goods_id + 1;
        END WHILE;
END //

DELIMITER ;
CALL ScheduleAddLoop();

# 경매 추가
# insert into auction (id, created_at, modified_at, end_date, is_ended, seat_number, start_date, start_price, schedule_id, zone_grade_id)
# values (1, now(), now(), '2024-01-28 19:00:00', 0, 3, now(), 40000, 2, 1);
# insert into auction (id, created_at, modified_at, end_date, is_ended, seat_number, start_date, start_price, schedule_id,
#                      zone_grade_id)
# values (2, now(), now(), '2024-01-28 19:00:00', 0, 5, now(), 40000, 2, 1);

DELIMITER //

CREATE PROCEDURE AuctionAddLoop()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE scheduleId INT DEFAULT 1;
    WHILE i <= 50
        DO
            WHILE scheduleId <= 11
                DO
                    -- 반복할 작업
                    insert into auction (id, created_at, modified_at, end_date, is_ended, seat_number, start_date,
                                         start_price, schedule_id, zone_grade_id)
                    values (1 + ((scheduleId - 1) * 4) + ((i - 1) * 44), now(), now(), '2024-01-28 19:00:00', 0, 5,
                            now(), 240000, 1 + ((scheduleId - 1) * 4) + ((i - 1) * 11), 1 + ((i - 1) * 4));
                    insert into auction (id, created_at, modified_at, end_date, is_ended, seat_number, start_date,
                                         start_price, schedule_id, zone_grade_id)
                    values (2 + ((scheduleId - 1) * 4) + ((i - 1) * 44), now(), now(), '2024-01-28 19:00:00', 0, 5,
                            now(), 140000, 1 + ((scheduleId - 1) * 4) + ((i - 1) * 11), 2 + ((i - 1) * 4));
                    insert into auction (id, created_at, modified_at, end_date, is_ended, seat_number, start_date,
                                         start_price, schedule_id, zone_grade_id)
                    values (3 + ((scheduleId - 1) * 4) + ((i - 1) * 44), now(), now(), '2024-01-28 19:00:00', 0, 5,
                            now(), 40000, 1 + ((scheduleId - 1) * 4) + ((i - 1) * 11), 3 + ((i - 1) * 4));
                    insert into auction (id, created_at, modified_at, end_date, is_ended, seat_number, start_date,
                                         start_price, schedule_id, zone_grade_id)
                    values (4 + ((scheduleId - 1) * 4) + ((i - 1) * 44), now(), now(), '2024-01-28 19:00:00', 0, 5,
                            now(), 10000, 1 + ((scheduleId - 1) * 4) + ((i - 1) * 11), 4 + ((i - 1) * 4));
                    SET scheduleId = scheduleId + 1;
                END WHILE;
            SET i = i + 1;
            SET scheduleId = 1;
        END WHILE;
END //

DELIMITER ;
CALL AuctionAddLoop();


# 유저 추가
