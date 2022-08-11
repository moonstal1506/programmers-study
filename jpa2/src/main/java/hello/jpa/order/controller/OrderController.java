package hello.jpa.order.controller;

import hello.jpa.order.ApiResponse;
import hello.jpa.order.dto.OrderDto;
import hello.jpa.order.service.OrderService;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ExceptionHandler
    private ApiResponse<String> exceptionHandle(Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<String> notFoundHandle(NotFoundException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }


    @GetMapping("/orders/{uuid}")
    public ApiResponse<OrderDto> getOne (@PathVariable String uuid) throws NotFoundException {
        return ApiResponse.ok(orderService.findOne(uuid));
    }


    @GetMapping("/orders")
    public ApiResponse<Page<OrderDto>> getAll (Pageable pageable) {
        return ApiResponse.ok(orderService.findAll(pageable));
    }

    @PostMapping("/orders")
    public ApiResponse<String> save(@RequestBody OrderDto orderDto) {
        return ApiResponse.ok(orderService.save(orderDto));
    }

}
