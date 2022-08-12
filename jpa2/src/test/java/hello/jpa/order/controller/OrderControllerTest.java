package hello.jpa.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.jpa.domain.order.OrderStatus;
import hello.jpa.order.dto.*;
import hello.jpa.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void save_test() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .uuid(uuid)
                .memo("문앞 보관 해주세요.")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(
                        MemberDto.builder()
                                .name("이름")
                                .nickName("닉네임")
                                .address("주소")
                                .age(33)
                                .description("---")
                                .build()
                )
                .orderItemDtos(List.of(
                        OrderItemDto.builder()
                                .price(1000)
                                .quantity(100)
                                .itemDto(ItemDto.builder()
                                        .type(ItemType.FOOD)
                                        .chef("백종원")
                                        .price(1000)
                                        .build()
                                )
                                .build()
                ))
                .build();
        // When
        String savedUuid = orderService.save(orderDto);

        // Then
        assertThat(uuid).isEqualTo(savedUuid);
    }

    @Test
    void saveCallTest() throws Exception {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .uuid(UUID.randomUUID().toString())
                .memo("문앞 보관 해주세요.")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(
                        MemberDto.builder()
                                .name("이름")
                                .nickName("닉네임")
                                .address("주소")
                                .age(33)
                                .description("---")
                                .build()
                )
                .orderItemDtos(List.of(
                        OrderItemDto.builder()
                                .price(1000)
                                .quantity(100)
                                .itemDto(ItemDto.builder()
                                        .type(ItemType.FOOD)
                                        .chef("백종원")
                                        .price(1000)
                                        .build()
                                )
                                .build()
                ))
                .build();
        // when, then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("order-save",
                        requestFields(
                                fieldWithPath("uuid").type(JsonFieldType.STRING).description("UUID"),
                                fieldWithPath("orderDatetime").type(JsonFieldType.STRING).description("orderDatetime"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("orderStatus"),
                                fieldWithPath("memo").type(JsonFieldType.STRING).description("memo"),
                                fieldWithPath("memberDto").type(JsonFieldType.OBJECT).description("memberDto"),
                                fieldWithPath("memberDto.id").type(JsonFieldType.NULL).description("memberDto.id"),
                                fieldWithPath("memberDto.name").type(JsonFieldType.STRING).description("memberDto.name"),
                                fieldWithPath("memberDto.nickName").type(JsonFieldType.STRING).description("memberDto.nickName"),
                                fieldWithPath("memberDto.age").type(JsonFieldType.NUMBER).description("memberDto.age"),
                                fieldWithPath("memberDto.address").type(JsonFieldType.STRING).description("memberDto.address"),
                                fieldWithPath("memberDto.description").type(JsonFieldType.STRING).description("memberDto.desc"),
                                fieldWithPath("orderItemDtos[]").type(JsonFieldType.ARRAY).description("orderItemDtos"),
                                fieldWithPath("orderItemDtos[].id").type(JsonFieldType.NULL).description("orderItemDtos.id"),
                                fieldWithPath("orderItemDtos[].price").type(JsonFieldType.NUMBER)
                                        .description("orderItemDtos.price"),
                                fieldWithPath("orderItemDtos[].quantity").type(JsonFieldType.NUMBER)
                                        .description("orderItemDtos.quantity"),
                                fieldWithPath("orderItemDtos[].itemDto").type(JsonFieldType.OBJECT)
                                        .description("orderItemDtos.itemDto"),
                                fieldWithPath("orderItemDtos[].itemDto.price").type(JsonFieldType.NUMBER)
                                        .description("orderItemDtos.itemDto.price"),
                                fieldWithPath("orderItemDtos[].itemDto.stockQuantity").type(JsonFieldType.NUMBER)
                                        .description("orderItemDtos.itemDto.stockQuantity"),
                                fieldWithPath("orderItemDtos[].itemDto.type").type(JsonFieldType.STRING)
                                        .description("orderItemDtos.itemDto.type"),
                                fieldWithPath("orderItemDtos[].itemDto.chef").type(JsonFieldType.STRING)
                                        .description("orderItemDtos.itemDto.chef")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"),    // uuid 넘겨줌
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        ))
                );
    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/orders/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/orders")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}