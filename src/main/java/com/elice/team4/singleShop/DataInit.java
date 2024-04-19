package com.elice.team4.singleShop;

import com.elice.team4.singleShop.cart.repository.CartItemRepository;
import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.repository.CategoryRepository;
import com.elice.team4.singleShop.order.dto.OrdersDto;
import com.elice.team4.singleShop.order.repository.OrdersRepository;
import com.elice.team4.singleShop.order.repository.OrderRepository;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrdersRepository ordersRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("init stub data");
        userRepository.save(User.builder()
                .name("admin1")
                .email("admin1@gmail.com")
                .password(passwordEncoder.encode("password1"))
                .role(User.Role.ADMIN)
                .build());
        userRepository.save(User.builder()
                .name("seller1")
                .email("seller1@gmail.com")
                .password(passwordEncoder.encode("password2"))
                .role(User.Role.SELLER)
                .build());
        userRepository.save(User.builder()
                .name("consumer1")
                .email("consumer1@gmail.com")
                .password(passwordEncoder.encode("password3"))
                .role(User.Role.CONSUMER)
                .build());

        categoryRepository.save(new Category("간편조리식품", "신속하고 편리하게 조리할 수 있는 식품으로, 빠른 시간에 요리를 완성할 수 있도록 가공된 제품입니다."));
        categoryRepository.save(new Category("소포장 반찬", "작은 포장에 담긴 편리한 반찬으로, 한끼 식사나 간편한 식사 준비에 사용됩니다."));
        categoryRepository.save(new Category("소포장 야채", "신선한 야채를 통해 건강한 삶과 풍부한 음식을 즐겨보세요."));

        productRepository.save(new Product("옛날 떡볶이 300g", 1L, "탱글탱글한 떡과 부산어묵의 만남", "칼과 가위 필요 없이 초간단 조리가 가능한 모티의 옛날 떡볶이입니다. 맵지 않고 매콤달콤한 분식집 떡볶이로 아이들도 잘 먹어요.", "", 100, 2500));
        productRepository.save(new Product("숯불고추장불고기 150g", 1L, "매콤달콤 불향 가득 입맛 돋구는", "포장만 살짝 열어 전자레인지 약 2분이면 조리 끝! 고추장 바른 참숯향이 그대로인 숯불고추장불고기입니다. 초간단 조리로 편리하게 상을 차릴 수 있어요.", "", 100, 4600));
        productRepository.save(new Product("숯불간장불고기 150g", 1L, "직화로 구워내어 불향 가득", "포장만 살짝 열어 전자레인지 약 2분이면 조리 끝! 진짜 참숯향이 그대로인 숯불간장 불고기입니다. 초간단 조리로 편리하게 상을 차릴 수 있어요.", "", 100, 4600));
        productRepository.save(new Product("하림 치킨너겟 300G", 1L, "100% 국내산 닭고기 대한민국 1등 너겟", "100% 국내산 닭고기로 안심하고 먹는 하림의 너겟입니다. 하트, 별, 배 등 8가지 모양으로 아이들도 재미있게 즐겨요.", "", 100, 4560));
        productRepository.save(new Product("제일제당 햇반 200G", 1L, "당일 도정으로 갓 지은 밥맛", "국내산 햅쌀로 지어 신선한 밥맛 좋은 당일도정 햇반입니다. 15도의 온도로 저온보관해 쌀의 신선함이 살아있어요.", "", 100, 1500));
        productRepository.save(new Product("하림 용가리치킨 300g", 1L, "100% 국내산 닭고기로 만든 영양 간식", "국내산 닭고기로 안심하고 먹을 수 있는 하림의 용가리치킨입니다. 아이들이 좋아하는 5가지 공룡모양으로 식사 흥미를 올려요. 반찬, 간식, 야식 등으로 좋아요", "", 100, 4560));
        productRepository.save(new Product("오뚜기 X.O.미니군만두 고기 465g", 1L, "촉촉한 육즙과 고소한 감칠맛", "돼지고기, 두부, 부추 등 재료가 가득 들어간 만두입니다. 한입에 쏙! 먹기 좋은 사이즈로 다양하게 활용할 수 있어요.", "", 100, 11040));
        productRepository.save(new Product("오뚜기 X.O.굴림만두 김치 350g", 1L, "엄선한 고기와 신선한 김치의 조화", "얇디얇은 0.2mm 피로 감싸 식감이 살아있는 굴림만두입니다. 김치와 돼지고기의 조화! 씹는 맛이 만족스러워요.", "", 100, 5640));
        productRepository.save(new Product("오뚜기 X.O.굴림만두 고기 350g", 1L, "당면을 뺀 속이 알찬 만두", "맛의 앙상블! 균형을 지켜 최고의 재료로 만든 굴림만두입니다. 돼지고기 함량을 높여 육즙이 풍부하고 식감이 좋아요.", "", 100, 5880));
        productRepository.save(new Product("오뚜기 3분짜장 200G", 1L, "깊고 진한 맛 살아있는 간편 짜장", "구수하고 진한 맛이 살아있는 오뚜기의 3분 짜장입니다. 직화솥에 볶은 춘장과 양파, 푸짐한 원료가 어우러져 조화로워요!", "", 100, 1840));
        productRepository.save(new Product("국밥이지 육개장국밥 210g", 1L, "전자레인지 3분이면 OK! 초간단조리 육개장국밥", "깔끔한 감칠맛에 고기와 대파가 듬뿍 들어간 국밥이지 육개장국밥입니다. 간단한 조리법으로 쉽고 빠르고 든든하게 한끼 할 수 있어요.", "", 100, 2400));

        productRepository.save(new Product("명태회무침 100g", 2L, "밥 반찬으로도 안주로도 다양하게", "100g 포장으로 알뜰하게 즐길 수 있는 명태회무침입니다. 반찬도 안주도 되는 인기 아이템이에요.", "", 100, 3190));
        productRepository.save(new Product("비빔젓갈 100g", 2L, "오징어와 낙지 둘 다 들어간", "오징어와 낙지를 아끼지 않고 넣은 비빔젓갈입니다. 깔끔한 팩 포장으로 먹을만큼만 사서 알찬 식사를 즐기세요.", "", 100, 3190));
        productRepository.save(new Product("낙지젓 100g", 2L, "밥 반찬으로도 안주로도 다양하게", "100g 포장으로 알뜰하게 즐길 수 있는 명태회무침입니다. 반찬도 안주도 되는 인기 아이템이에요.", "", 100, 3190));
        productRepository.save(new Product("오징어젓 100g", 2L, "밥 반찬으로도 안주로도 다양하게", "100g 포장으로 알뜰하게 즐길 수 있는 명태회무침입니다. 반찬도 안주도 되는 인기 아이템이에요.", "", 100, 3360));
        productRepository.save(new Product("프리미엄 수제어묵 후랑크 손말이 어묵 160g", 2L, "밥 반찬으로도 안주로도 다양하게", "100g 포장으로 알뜰하게 즐길 수 있는 명태회무침입니다. 반찬도 안주도 되는 인기 아이템이에요.", "", 100, 3760));
        productRepository.save(new Product("순살양념게장 200g", 2L, "통통한 순살에 매콤달콤 양념을 섞은", "탱글탱글한 게살만 수제로 모은 모티의 순살양념게장입니다. 숟가락만으로 간편하게 떠서 먹을 수 있어요.", "", 100, 7290));
        productRepository.save(new Product("순살간장게장 200g", 2L, "통통한 순살에 달콤짭짤 양념을 섞은", "탱글탱글한 게살만 수제로 모은 모티의 순살간장게장입니다. 숟가락만으로 간편하게 떠서 먹을 수 있어요.", "", 100, 7290));
        productRepository.save(new Product("백김치 400g", 2L, "맵지 않아 아이들도 시원하게 즐길 수 있어요", "다시마 삻은 물에 무채를 버무려 담가 시원한 맛이 특징인 백김치입니다. 맛과 영양 모두 뛰어난 우리농산물로 만든 백김치예요.", "", 100, 4560));
        productRepository.save(new Product("총각김치 400g", 2L, "단맛이 풍부하고 아삭한", "아삭아삭 품질 좋은 총각무로 만든 총각김치입니다. 적은 양의 소금으로 절여 안심하고 먹을 수 있어요. 고슬고슬 쌀밥이나 육류와 잘 어울리는 밥도둑입니다.", "", 100, 4560));
        productRepository.save(new Product("깍두기 400g", 2L, "아삭아삭 식감이 일품", "무를 먹기 좋게 썰어 아삭아삭한 식감이 좋은 깍두기입니다. 멸치 액젓의 깊은 맛과 남해안 새우젓의 시원한 맛이 조화로워요! 설렁탕, 곰탕과 함께 드셔보세요.", "", 100, 4560));
        productRepository.save(new Product("명이나물 120g", 2L, "고기 옆 단짝친구", "짜지 않고 부드러워 무한정 입맛을 당기게 하는 명이나물이에요. 이동과 휴대가 간편한 팩 포장으로 캠핑이나 여행에도 쉽게 활용할 수 있어요.", "", 100, 3190));
        productRepository.save(new Product("일가집 쌈무새콤 350g", 2L, "고기와 함께 먹었을 때 제일 맛있는", "일가집의 비법 노하우로 만든 고기와 먹으면 맛있는 쌈무입니다. 고기 외 다른 쌈무 요리와도 잘 어울리는 새콤달콤함을 가졌어요. 치킨, 쌈무말이 등 다양하게 곁들여 먹어보세요.", "", 100, 2300));
        productRepository.save(new Product("맛이 있는 두부 350g", 2L, "진하고 부드러운 콩맛 그대로", "장인정신으로 정성스럽게 만들어 진하고 부드러운 두부입니다. 국물 요리, 부침, 마파두부 등 다양하게 활용해보세요.", "", 100, 2400));

        productRepository.save(new Product("방울토마토 1팩 180g", 3L, "방울토마토도 소량만 신선하게!", "톡톡 씹히는 식감이 신선한 국내산 방울토마토입니다. 간식, 주스, 샐러드까지 무궁무진하게 활용할 수 있어요.", "", 100, 2000));
        productRepository.save(new Product("흙당근 1개", 3L, "제주도 토양의 영양 가득", "선명한 주홍빛으로 싱싱하게! 색도 영양소도 가득한 흙당근입니다. 베타카로틴이 다량 함유된 달큰한 맛이 좋아요.", "", 100, 1400));
        productRepository.save(new Product("단호박 1/4개", 3L, "달큰한 맛의 단호박을 원하는 만큼만", "은은한 단맛이 기분 좋은 단호박을 원하는 만큼 사서 음식에 활용해보세요. 찜, 구이, 죽 등 단호박의 변신은 무죄!", "", 100, 1000));
        productRepository.save(new Product("우엉채 150g", 3L, "번거롭지 않고 편리한 밑반찬 재료", "활용도가 무한대인 우엉채! 간단하게 뜯어 사용해요. 손질의 번거로움을 덜어낸 깔끔한 소용량 진공포장입니다. 밑반찬, 주먹밥, 김밥 재료로 활용해보세요.", "", 100, 1000));
        productRepository.save(new Product("마늘쫑 1봉 130g", 3L, "아삭한 식감의 단골 밑반찬 재료", "입맛 돋우기 좋은 아삭아삭한 식감의 마늘쫑입니다. 살짝 볶아내면 풍미와 식감이 더욱 살아나는 식탁 위의 만능재료예요.", "", 100, 1300));
        productRepository.save(new Product("양송이버섯 2개입", 3L, "쫄깃한 식감의 하얀 버섯", "보드라운 식감이 좋은 쫄깃쫄깃 맛 좋은 양송이입니다. 고기와 곁들여도 좋고, 샐러드나 수프로 만들어보세요.", "", 100, 900));
        productRepository.save(new Product("다다기오이 1개", 3L, "청량한 식감이 살아있는", "알찬 식감이 먹기 좋은 다다기오이입니다. 아삭거리는 식감과 싱그러운 수분감이 좋아요. 1인 가구도 부담 없이 구매가 가능합니다.", "", 100, 1600));
        productRepository.save(new Product("홍고추 2개", 3L, "개운한 매콤함을 더하는", "단맛과 매운맛 전부 담긴 홍고추입니다. 소량으로 간편하게 토핑하기 좋아요. 기름을 내거나, 볶기도 편하답니다.", "", 100, 1200));
        productRepository.save(new Product("느타리버섯 80g", 3L, "쫄깃한 식감과 맛에 빠져드는", "감칠맛도 가득 챙긴 쫄깃쫄깃 느타리버섯입니다. 남길 걱정 없는 소용량으로 간편하게 영양 가득 채워요.", "", 100, 800));
        productRepository.save(new Product("미니 파프리카(3~4개)", 3L, "알록달록 색감 살리기 좋은", "미니 파프리카를 팩 포장으로! 알록달록 색감 살리기 좋은 미니 파프리카입니다. 크기가 작아 조리하기 쉽고 아삭아삭 달달해요.", "", 100, 2700));
        productRepository.save(new Product("풀무원 국산 다진마늘 80g 튜브형", 3L, "깔끔하게 짜서 쓰는", "국내산 좋은 마늘만을 엄선해 만든 풀무원 다진마늘입니다. 한식의 필수양념인 다진마늘을 번거롭지 않게 짜서 사용하세요.", "", 100, 5880));
        productRepository.save(new Product("콩나물 200g", 3L, "아삭아삭 식탁 위 단골 재료", "없으면 허전한 필수 채소 콩나물을 합리적인 가격에 만나보세요! 깔끔한 소용량 포장으로 물러지고 상할 걱정 없어요. 나물, 국, 탕으로 다채롭게 활용해보세요.", "", 100, 600));
        productRepository.save(new Product("찌개용 채소 320g", 3L, "맛있는 찌개를 위한 채소모음", "껍질 처리 걱정 없는 모티의 찌개용 채소 패키지입니다. 맑은 국물도, 매운 국물도 빠질 수 없는 필수 채소만 모아 신선하게 진공포장 해서 보내드려요. 1인 가구에 딱 맞는 부담없는 양입니다. (깐감자1/2+애호박1/2+깐양파1/2)", "", 100, 2000));
        productRepository.save(new Product("카레용 채소 550g", 3L, "맛있는 카레를 위한 채소모음", "껍질 처리 걱정 없는 모티의 카레용 채소 패키지입니다. 1인 가구에게 안성맞춤인 양으로 깔끔하게 구성했어요. (깐감자+깐양파+당근 1/2 구성)", "", 100, 2500));
        productRepository.save(new Product("아보카도 1개 200g", 3L, "싱싱한 숲 속의 버터", "신선하고 촉촉한 버터 질감이 특징인 아보카도입니다. 크리미한 맛이 특징으로 샐러드, 과카몰리, 샌드위치로 활용하면 좋아요.", "", 100, 1500));
        productRepository.save(new Product("부추 200g", 3L, "어떻게 먹어도 맛있는 만능채소", "무침, 전, 찜, 양념장 등 다양하게 활용할 수 있는 부추입니다. 대량 보관하기 힘든 부추를 모티에서는 소량 구매가 가능해요.", "", 100, 1600));
        productRepository.save(new Product("어린잎 믹스 50g", 3L, "1인 가구도 간편하게 즐기는 싱그러움", "비트, 항암초, 비타민, 경수채 여러가지 채소가 혼합된 어린잎 믹스입니다. 비빔밥, 샐러드, 샌드위치 등 다양하게 활용 가능해요! 한끼 해먹기 딱 좋은 50g 중량입니다.", "", 100, 1500));
        productRepository.save(new Product("청경채 1개", 3L, "소가족도 부담없이 담는 양", "국내산 농가에서 재배해 믿고 먹을 수 있는 청경채입니다. 무침, 볶음, 샤브샤브 등 다양하게 활용해보세요.", "", 100, 500));
        productRepository.save(new Product("깻잎 10장", 3L, "묶어파는 깻잎, 너무 많고 감당도 안된다면?", "1인 가구가 먹기 좋은 향긋한 국내산 깻잎입니다. 구운 고기와 쌈으로, 상큼하게 무침으로 드셔보세요.", "", 100, 600));
        productRepository.save(new Product("초가집 옥수수 미니 3구", 3L, "전자레인지에 간단히 조리하는 옥수수", "아이들도 안심하고 먹을 수 있는 웰빙 옥수수예요. 한국 고유의 맛 간직을 위해 가마솥에서 삶아 쫄깃하답니다. 무방부제, 천연감미료를 사용해 안심할 수 있어요!", "", 100, 2040));

        ordersRepository.save(new OrdersDto(null, 2500, LocalDateTime.now(), "Sample Summary Title", OrdersDto.Status.DELIVERY_COMPLETE));
        ordersRepository.save(new OrdersDto(null, 4600, LocalDateTime.now(), "Another Summary Title", OrdersDto.Status.DELIVERY_READY));
    }
}